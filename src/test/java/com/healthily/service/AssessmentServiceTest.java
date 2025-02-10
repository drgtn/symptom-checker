package com.healthily.service;

import com.healthily.dto.AssessmentAnswerRequestDto;
import com.healthily.dto.AssessmentRequestDto;
import com.healthily.dto.AssessmentResponseDto;
import com.healthily.dto.ResultResponseDto;
import com.healthily.model.Assessment;
import com.healthily.model.User;
import com.healthily.repository.AssessmentRepository;
import com.healthily.repository.ConditionRepository;
import com.healthily.repository.SymptomRepository;
import com.healthily.repository.UserRepository;
import com.healthily.service.impl.AssessmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DecimalFormat;
import java.util.Optional;

import static com.healthily.fixtures.AssessmentAnswerRequestDto.aAssessmentAnswerRequestDto;
import static com.healthily.fixtures.AssessmentFixture.aInitialAssessment;
import static com.healthily.fixtures.AssessmentFixture.aWateryOrIchyEyesAssessment;
import static com.healthily.fixtures.AssessmentRequestDtoFixture.aAssessmentRequestSneezingRunnyNose;
import static com.healthily.fixtures.AssessmentResponseDtoFixture.aSoreThroatResponseDto;
import static com.healthily.fixtures.AssessmentResponseDtoFixture.aWateryOrItchyEyesAssessmentResponseDto;
import static com.healthily.fixtures.ConditionFixture.*;
import static com.healthily.fixtures.SymptomFixture.*;
import static java.util.List.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssessmentServiceTest {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    @InjectMocks
    private AssessmentServiceImpl assessmentService;

    @Mock
    private AssessmentRepository assessmentRepository;

    @Mock
    private SymptomRepository symptomRepository;

    @Mock
    private ConditionRepository conditionRepository;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<Assessment> assessmentCaptor;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testStartAssessment() {
        AssessmentRequestDto request = aAssessmentRequestSneezingRunnyNose();
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(User.builder().build()));
        when(conditionRepository.findAll()).thenReturn(of(aCommonCold(),
                aHayfever(),
                aCovid19()));
        when(symptomRepository.findById(request.getInitialSymptoms().get(0)))
                .thenReturn(Optional.of(aSneezing()));
        when(symptomRepository.findById(request.getInitialSymptoms().get(1)))
                .thenReturn(Optional.of(aRunnyNose()));
        when(symptomRepository.findAll()).thenReturn(
                of(
                        aWateryOrItchyEyes(),
                        aCough(),
                        aFever(),
                        aLossOfSmellOrTaste(),
                        aFatigue(),
                        aShortnessOfBreath(),
                        aRunnyNose(),
                        aHeadache(),
                        aSneezing(),
                        aNasalCongestion(),
                        aSoreThroat()
                )
        );

        AssessmentResponseDto responseOne = assessmentService.startAssessment(request);

        assertThat(responseOne).usingRecursiveComparison().isEqualTo(aWateryOrItchyEyesAssessmentResponseDto(responseOne.getAssessmentId()));
        verify(assessmentRepository).save(assessmentCaptor.capture());
        assertThat(assessmentCaptor.getValue()).usingRecursiveComparison().ignoringFields("assessmentId")
                .isEqualTo(aInitialAssessment(request, null));

    }

    @Test
    void testAnswerAssessment() {
        String assessmentId = "assessmentId";
        AssessmentAnswerRequestDto answerRequestDto = aAssessmentAnswerRequestDto();
        AssessmentRequestDto request = aAssessmentRequestSneezingRunnyNose();
        Optional<Assessment> assessment = Optional.of(aInitialAssessment(request, null));

        when(assessmentRepository.findById(assessmentId)).thenReturn(assessment);
        when(symptomRepository.findById(answerRequestDto.getQuestionId())).thenReturn(Optional.of(aWateryOrItchyEyes()));
        when(symptomRepository.findAll()).thenReturn(
                of(
                        aWateryOrItchyEyes(),
                        aCough(),
                        aFever(),
                        aLossOfSmellOrTaste(),
                        aFatigue(),
                        aShortnessOfBreath(),
                        aRunnyNose(),
                        aHeadache(),
                        aSneezing(),
                        aNasalCongestion(),
                        aSoreThroat()
                )
        );

        assertThat(assessmentService.answerQuestion(assessmentId, answerRequestDto))
                .usingRecursiveComparison()
                .isEqualTo(aSoreThroatResponseDto(assessmentId));
        verify(assessmentRepository).update(assessmentCaptor.capture());
        assertThat(assessmentCaptor.getValue()).usingRecursiveComparison()
                .isEqualTo(aWateryOrIchyEyesAssessment(request));
    }

    @Test
    void testGetResult() {
        String assessmentId = "assessmentId";
        AssessmentRequestDto request = aAssessmentRequestSneezingRunnyNose();
        Optional<Assessment> assessment = Optional.of(aInitialAssessment(request, DECIMAL_FORMAT));
        when(assessmentRepository.findById(assessmentId)).thenReturn(assessment);
        ResultResponseDto resultResponseDto = assessmentService.getResult(assessmentId);
        assertThat(resultResponseDto).usingRecursiveComparison().isEqualTo(ResultResponseDto.builder()
                .condition("Common Cold")
                .probabilities(aInitialAssessment(request, DECIMAL_FORMAT).getConditionProbabilities())
                .build());

    }
}
