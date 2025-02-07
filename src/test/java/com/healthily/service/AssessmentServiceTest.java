package com.healthily.service;

import com.healthily.dto.AssessmentRequestDto;
import com.healthily.dto.AssessmentResponseDto;
import com.healthily.model.Assessment;
import com.healthily.model.User;
import com.healthily.repository.AssessmentRepository;
import com.healthily.repository.ConditionRepository;
import com.healthily.repository.SymptomRepository;
import com.healthily.repository.UserRepository;
import com.healthily.service.impl.AssessmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.healthily.fixtures.AssessmentFixture.aInitialAssessment;
import static com.healthily.fixtures.AssessmentRequestDtoFixture.aAssessmentRequestSneezingRunnyNose;
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

    @Test
    void testStartAssessment() {
        when(userRepository.findByUserId(any())).thenReturn(Optional.of(User.builder().build()));
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
        when(conditionRepository.findAll()).thenReturn(of(aCommonCold(),
                aHayfever(),
                aCovid19()));

        AssessmentRequestDto request = aAssessmentRequestSneezingRunnyNose();
        when(symptomRepository.findById(request.getInitialSymptoms().get(0)))
                .thenReturn(Optional.of(aSneezing()));
        when(symptomRepository.findById(request.getInitialSymptoms().get(1)))
                .thenReturn(Optional.of(aRunnyNose()));
        AssessmentResponseDto responseOne = assessmentService.startAssessment(request);
        verify(assessmentRepository).save(assessmentCaptor.capture());
        assertThat(assessmentCaptor.getValue()).usingRecursiveComparison().ignoringFields("assessmentId")
                .isEqualTo(aInitialAssessment(request));
        assertThat(responseOne).usingRecursiveComparison().isEqualTo(aWateryOrItchyEyesAssessmentResponseDto(responseOne));
    }
}
