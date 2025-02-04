package com.healthily.service;

import com.healthily.dto.UserDto;

public interface UserService {
    void register(UserDto userDto);

    String login(UserDto userDto);
}
