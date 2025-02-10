package com.healthily.service.impl;

import com.healthily.dto.AssessmentAnswerRequestDto;
import com.healthily.dto.AssessmentRequestDto;
import com.healthily.dto.AssessmentResponseDto;
import com.healthily.dto.ResultResponseDto;
import com.healthily.exception.UserNotFoundException;
import com.healthily.model.Assessment;
import com.healthily.model.Condition;
import com.healthily.model.Symptom;
import com.healthily.repository.AssessmentRepository;
import com.healthily.repository.ConditionRepository;
import com.healthily.repository.SymptomRepository;
import com.healthily.repository.UserRepository;
import com.healthily.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;
import static java.util.stream.Stream.concat;
import static org.apache.commons.lang3.BooleanUtils.YES;


@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {
    private static final int MAX_QUESTIONS = 3;

    private final AssessmentRepository assessmentRepository;
    private final SymptomRepository symptomRepository;
    private final ConditionRepository conditionRepository;
    private final UserRepository userRepository;

    @Override
    public AssessmentResponseDto startAssessment(AssessmentRequestDto request) {
        validateUserLoggedIn(request);
        List<Condition> conditions = conditionRepository.findAll();
        Map<String, Double> initialProbabilities = getInitialProbabilities(conditions);

        processInitialAssessments(request, initialProbabilities);

        Assessment assessment = createAssessment(request, initialProbabilities);

        assessmentRepository.save(assessment);
        return AssessmentResponseDto.builder()
                .assessmentId(assessment.getAssessmentId())
                .nextQuestionId(selectNextQuestion(request.getInitialSymptoms(), initialProbabilities))
                .build();
    }

    @Override
    public AssessmentResponseDto answerQuestion(String assessmentId,
                                                AssessmentAnswerRequestDto answerRequestDto) {
        Assessment assessment = findAssessmentById(assessmentId);

        updateProbabilities(assessment.getConditionProbabilities(),
                answerRequestDto.getQuestionId(),
                answerRequestDto.getResponse());

        String nextQuestion = selectNextQuestion(getAllAskedQuestions(answerRequestDto, assessment),
                assessment.getConditionProbabilities());

        if (nextQuestion == null || assessment.getAskedQuestions().size() >= MAX_QUESTIONS) {
            assessmentRepository.update(assessment);
            return AssessmentResponseDto.builder()
                    .assessmentId(assessmentId)
                    .nextQuestionId(null)
                    .build();
        }
        assessmentRepository.update(assessment);
        return AssessmentResponseDto.builder()
                .assessmentId(assessmentId)
                .nextQuestionId(nextQuestion).build();
    }

    @Override
    public ResultResponseDto getResult(String assessmentId) {
        Assessment assessment = findAssessmentById(assessmentId);
        return ResultResponseDto.builder()
                .condition(getMostProbableCondition(assessment))
                .probabilities(assessment.getConditionProbabilities())
                .build();
    }

    private static String getMostProbableCondition(Assessment assessment) {
        return assessment.getConditionProbabilities().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private void updateProbabilities(Map<String, Double> conditionProbabilities,
                                     String symptom, String response) {
        Symptom symptomData = symptomRepository.findById(symptom)
                .orElseThrow(() -> new RuntimeException("Symptom not found"));

        conditionProbabilities.forEach((condition, value) -> {
            double prior = value;
            double likelihood = enumToBoolean(response) ? getConditional(symptomData, condition)
                    : getComplementProbability(symptomData, condition);
            conditionProbabilities.put(condition, prior * likelihood);
        });
        normalize(conditionProbabilities);
    }

    private static Double getConditional(Symptom symptomData, String condition) {
        return symptomData.getConditionProbabilities().get(condition);
    }

    private static double getComplementProbability(Symptom symptomData, String condition) {
        return 1 - symptomData.getConditionProbabilities().get(condition);
    }

    private String selectNextQuestion(List<String> askedQuestions, Map<String, Double> conditionProbabilities) {
        List<Symptom> symptoms = symptomRepository.findAll();
        return symptoms.stream()
                .filter(symptom -> !askedQuestions.contains(symptom.getDataId()))
                .max(Comparator.comparingDouble(symptom -> computeVariance(symptom, conditionProbabilities)))
                .map(Symptom::getDataId)
                .orElse(null);
    }

    //might not be 100% precise
    private double computeVariance(Symptom symptom, Map<String, Double> probabilities) {
        double mean = probabilities.entrySet().stream()
                .mapToDouble(entry -> entry.getValue() * symptom.getConditionProbabilities().getOrDefault(entry.getKey(), 0.0))
                .sum();
        return probabilities.entrySet().stream()
                .mapToDouble(entry -> Math.pow(symptom.getConditionProbabilities().getOrDefault(entry.getKey(), 0.0) - mean, 2) * entry.getValue())
                .sum();
    }

    private void normalize(Map<String, Double> probabilities) {
        double sum = probabilities.values().stream().mapToDouble(Double::doubleValue).sum();
        probabilities.replaceAll((k, v) -> v / sum);
    }

    private Boolean enumToBoolean(String response) {
        return YES.equalsIgnoreCase(response);
    }

    private static Map<String, Double> getInitialProbabilities(List<Condition> conditions) {
        return conditions.stream()
                .collect(Collectors.toMap(Condition::getDataId, Condition::getPrevalence));
    }

    private static Assessment createAssessment(AssessmentRequestDto request, Map<String, Double> initialProbabilities) {
        return Assessment.builder()
                .assessmentId(UUID.randomUUID().toString())
                .userId(request.getUserId())
                .reportedSymptoms(request.getInitialSymptoms())
                .conditionProbabilities(initialProbabilities)
                .askedQuestions(emptyMap())
                .build();
    }

    private List<String> getAllAskedQuestions(AssessmentAnswerRequestDto answerRequestDto,
                                              Assessment assessment) {
        assessment.getAskedQuestions()
                .put(answerRequestDto.getQuestionId(),
                        enumToBoolean(answerRequestDto.getResponse()));
        return concat(assessment.getAskedQuestions().keySet().stream(),
                assessment.getReportedSymptoms().stream()).toList();
    }

    private Assessment findAssessmentById(String assessmentId) {
        return assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));
    }

    private void processInitialAssessments(AssessmentRequestDto request, Map<String, Double> initialProbabilities) {
        request.getInitialSymptoms().forEach(symptom -> updateProbabilities(initialProbabilities, symptom, YES));
    }

    private void validateUserLoggedIn(AssessmentRequestDto request) {
        userRepository.findByUserId(request.getUserId()).orElseThrow(() -> new UserNotFoundException("Please register/login first!"));
    }
}

