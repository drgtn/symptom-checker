package com.healthily.service;

import com.healthily.BaseIT;
import com.healthily.dto.AssessmentResponseDto;
import com.healthily.repository.AssessmentRepository;
import com.healthily.repository.UserRepository;
import com.healthily.service.impl.AssessmentServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.healthily.fixtures.AssessmentAnswerRequestDtoFixture.aAssessmentAnswerRequestDto;
import static com.healthily.fixtures.AssessmentRequestDtoFixture.aAssessmentRequestLossOfSmellOrTaste;
import static com.healthily.fixtures.AssessmentRequestDtoFixture.aAssessmentRequestSneezingRunnyNose;
import static com.healthily.fixtures.ResultResponseDtoFixture.*;
import static com.healthily.fixtures.UserFixture.aUser;
import static org.apache.commons.lang3.BooleanUtils.NO;
import static org.apache.commons.lang3.BooleanUtils.YES;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class AssessmentServiceIT extends BaseIT {
    @Autowired
    private AssessmentServiceImpl assessmentService;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        assessmentRepository.remove(aUser().getEmail());
    }

    @BeforeEach
    void setUp() {
        userRepository.save(aUser());
    }

    @Test
    void testFlowSneezingRunnyNoseResponseYesYesYes() {
        AssessmentResponseDto responseOne = assessmentService.startAssessment(aAssessmentRequestSneezingRunnyNose());
        String assessmentId = responseOne.getAssessmentId();
        AssessmentResponseDto responseTwo = assessmentService.answerQuestion(assessmentId,
                aAssessmentAnswerRequestDto(responseOne.getNextQuestionId(), YES));

        AssessmentResponseDto responseThree = assessmentService.answerQuestion(assessmentId,
                aAssessmentAnswerRequestDto(responseTwo.getNextQuestionId(), YES));

        AssessmentResponseDto responseFour = assessmentService.answerQuestion(assessmentId,
                aAssessmentAnswerRequestDto(responseThree.getNextQuestionId(), YES));

        assertThat(assessmentService.getResult(assessmentId)).usingRecursiveComparison().isEqualTo(
                aCommonCold());
        assertThat(responseFour.getNextQuestionId()).isNull();
        assessmentRepository.remove(assessmentId);
    }

    @Test
    void testFlowSneezingRunnyNoseResponseYesNoNo() {
        AssessmentResponseDto responseOne = assessmentService.startAssessment(aAssessmentRequestSneezingRunnyNose());
        String assessmentId = responseOne.getAssessmentId();
        AssessmentResponseDto responseTwo = assessmentService.answerQuestion(assessmentId,
                aAssessmentAnswerRequestDto(responseOne.getNextQuestionId(), YES));

        AssessmentResponseDto responseThree = assessmentService.answerQuestion(assessmentId,
                aAssessmentAnswerRequestDto(responseTwo.getNextQuestionId(), NO));

        AssessmentResponseDto responseFour = assessmentService.answerQuestion(assessmentId,
                aAssessmentAnswerRequestDto(responseThree.getNextQuestionId(), NO));

        assertThat(assessmentService.getResult(assessmentId)).usingRecursiveComparison().isEqualTo(
                aHayfever());
        assertThat(responseFour.getNextQuestionId()).isNull();
        assessmentRepository.remove(assessmentId);
    }

    @Test
    void testFlowLossOfSmellOrTasteResponseYesYesYes() {
        AssessmentResponseDto responseOne = assessmentService.startAssessment(aAssessmentRequestLossOfSmellOrTaste());
        String assessmentId = responseOne.getAssessmentId();
        AssessmentResponseDto responseTwo = assessmentService.answerQuestion(assessmentId,
                aAssessmentAnswerRequestDto(responseOne.getNextQuestionId(), YES));

        AssessmentResponseDto responseThree = assessmentService.answerQuestion(assessmentId,
                aAssessmentAnswerRequestDto(responseTwo.getNextQuestionId(), YES));

        AssessmentResponseDto responseFour = assessmentService.answerQuestion(assessmentId,
                aAssessmentAnswerRequestDto(responseThree.getNextQuestionId(), YES));

        assertThat(assessmentService.getResult(assessmentId)).usingRecursiveComparison().isEqualTo(
                aCovid19());
        assertThat(responseFour.getNextQuestionId()).isNull();
        assessmentRepository.remove(assessmentId);
    }
}
