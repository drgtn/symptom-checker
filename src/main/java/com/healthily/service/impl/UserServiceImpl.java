package com.healthily.service.impl;

import com.healthily.dto.UserDto;
import com.healthily.exception.UserAlreadyExistsException;
import com.healthily.exception.UserNotFoundException;
import com.healthily.mapper.UserMapper;
import com.healthily.model.User;
import com.healthily.repository.UserRepository;
import com.healthily.repository.impl.UserRepositoryImpl;
import com.healthily.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    @Override
    public void register(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists!");
        }
        userRepository.save(userMapper.map(userDto, bCryptPasswordEncoder.encode(userDto.getPassword())));
    }

    @Override
    public String login(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new UserNotFoundException("Invalid email or password"));
        if (!bCryptPasswordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid email or password!");
        }
        return user.getUserId();
    }
}
