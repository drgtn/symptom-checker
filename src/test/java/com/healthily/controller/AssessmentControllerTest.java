package com.healthily.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthily.dto.AssessmentAnswerRequestDto;
import com.healthily.dto.AssessmentRequestDto;
import com.healthily.dto.AssessmentResponseDto;
import com.healthily.dto.ResultResponseDto;
import com.healthily.exception.GlobalExceptionHandler;
import com.healthily.service.impl.AssessmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.healthily.fixtures.AssessmentAnswerRequestDtoFixture.aAssessmentAnswerRequestDto;
import static com.healthily.fixtures.AssessmentRequestDtoFixture.aAssessmentRequestSneezingRunnyNose;
import static com.healthily.fixtures.AssessmentResponseDtoFixture.aSoreThroatResponseDto;
import static com.healthily.fixtures.AssessmentResponseDtoFixture.aWateryOrItchyEyesAssessmentResponseDto;
import static com.healthily.fixtures.ResultResponseDtoFixture.aCommonCold;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AssessmentControllerTest {
    private static final String ASSESSMENT_ID = "12345";
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private AssessmentServiceImpl assessmentService;

    @InjectMocks
    private AssessmentController assessmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assessmentController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void startAssessment() throws Exception {
        AssessmentRequestDto request = aAssessmentRequestSneezingRunnyNose();
        AssessmentResponseDto response = aWateryOrItchyEyesAssessmentResponseDto(ASSESSMENT_ID);
        when(assessmentService.startAssessment(request)).thenReturn(response);

        mockMvc.perform(post("/assessment/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void answerQuestion() throws Exception {
        AssessmentAnswerRequestDto request = aAssessmentAnswerRequestDto("Watery or itchy eyes", "yes");
        AssessmentResponseDto response = aSoreThroatResponseDto(ASSESSMENT_ID);
        when(assessmentService.answerQuestion(ASSESSMENT_ID, request)).thenReturn(response);

        mockMvc.perform(post("/assessment/" + ASSESSMENT_ID + "/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void result() throws Exception {
        ResultResponseDto response = aCommonCold();
        when(assessmentService.getResult(ASSESSMENT_ID)).thenReturn(response);
        mockMvc.perform(get("/assessment/" + ASSESSMENT_ID + "/result")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
}
