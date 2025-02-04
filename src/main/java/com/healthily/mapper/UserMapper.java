package com.healthily.mapper;

import com.healthily.dto.UserDto;
import com.healthily.model.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {
    public User map(UserDto userDto, String passwordHash) {
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .email(userDto.getEmail())
                .age(userDto.getAge())
                .password(passwordHash)
                .gender(userDto.getGender())
                .build();
    }
}
