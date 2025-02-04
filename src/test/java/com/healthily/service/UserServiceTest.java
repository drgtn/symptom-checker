package com.healthily.service;

import com.healthily.dto.UserDto;
import com.healthily.exception.UserAlreadyExistsException;
import com.healthily.exception.UserNotFoundException;
import com.healthily.mapper.UserMapper;
import com.healthily.model.User;
import com.healthily.repository.UserRepository;
import com.healthily.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.healthily.fixtures.UserDtoFixture.aUserDto;
import static com.healthily.fixtures.UserFixture.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder bCryptPasswordEncoder;
    @Mock
    private UserMapper userMapper;

    @Test
    void testRegister() {
        UserDto aUserDto = aUserDto();
        User aUser = aUser();
        when(userRepository.findByEmail(aUserDto.getEmail())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(aUserDto.getPassword())).thenReturn("hashedStrongPassword");
        when(userMapper.map(aUserDto, "hashedStrongPassword")).thenReturn(aUser);
        userService.register(aUserDto);
        verify(userRepository).save(aUser);
    }

    @Test
    void testRegisterEmailExists() {
        UserDto aUserDto = aUserDto();
        User aUser = aUser();
        when(userRepository.findByEmail(aUserDto.getEmail())).thenReturn(Optional.of(aUser));
        assertThat(catchThrowable(() -> userService.register(aUserDto))).isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("Email already exists!");
        verify(userRepository, never()).save(aUser);
    }

    @Test
    void testLogin() {
        UserDto aUserDto = aUserDto();
        User aUser = aUser();
        when(userRepository.findByEmail(aUserDto.getEmail())).thenReturn(Optional.of(aUser));
        when(bCryptPasswordEncoder.matches(aUser.getPassword(), aUser.getPassword())).thenReturn(true);
        assertThat(userService.login(aUserDto)).isNotBlank();
    }

    @Test
    void testLoginUserDoesntExists() {
        UserDto aUserDto = aUserDto();
        when(userRepository.findByEmail(aUserDto.getEmail())).thenReturn(Optional.empty());
        assertThat(catchThrowable(() -> userService.login(aUserDto)))
                .isInstanceOf(UserNotFoundException.class).hasMessageContaining("Invalid email or password");
    }

    @Test
    void testLoginUserExistsWrongPassword() {
        UserDto aUserDto = aUserDto();
        User aUser = aUser();
        when(userRepository.findByEmail(aUserDto.getEmail())).thenReturn(Optional.of(aUser));
        when(bCryptPasswordEncoder.matches(aUser.getPassword(), aUser.getPassword())).thenReturn(false);
        assertThat(catchThrowable(() -> userService.login(aUserDto)))
                .isInstanceOf(UserNotFoundException.class).hasMessageContaining("Invalid email or password");
    }
}
