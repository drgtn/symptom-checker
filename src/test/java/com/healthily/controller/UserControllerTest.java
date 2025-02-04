package com.healthily.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthily.dto.UserDto;
import com.healthily.exception.GlobalExceptionHandler;
import com.healthily.exception.UserAlreadyExistsException;
import com.healthily.exception.UserNotFoundException;
import com.healthily.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.healthily.fixtures.UserDtoFixture.aUserDto;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testRegisterUser() throws Exception {
        UserDto userDto = aUserDto();
        String userJson = objectMapper.writeValueAsString(userDto);

        doNothing().when(userService).register(userDto);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully!"));
    }

    @Test
    void testRegisterUserEmailAlreadyExists() throws Exception {
        UserDto userDto = aUserDto();
        String userJson = objectMapper.writeValueAsString(userDto);

        doThrow(new UserAlreadyExistsException("Email already registered")).when(userService).register(userDto);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email already registered"));
    }


    @Test
    void testLoginUser() throws Exception {
        UserDto userDto = aUserDto();
        String userJson = objectMapper.writeValueAsString(userDto);

        when(userService.login(userDto)).thenReturn("jwt_token");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().string("jwt_token"));
    }

    @Test
    void testLoginUserNotFound() throws Exception {
        UserDto userDto = aUserDto();
        String userJson = objectMapper.writeValueAsString(userDto);
        doThrow(new UserNotFoundException("Invalid email or password")).when(userService).login(userDto);
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Invalid email or password"));
    }
}
