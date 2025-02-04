package com.healthily.fixtures;

import com.healthily.dto.UserDto;
import com.healthily.model.Gender;

public class UserDtoFixture {

    private UserDtoFixture() {
    }

    public static UserDto aUserDto() {
        return UserDto.builder()
                .email("abc@gmail.com")
                .age(25)
                .gender(Gender.MALE)
                .password("strongPassword")
                .build();
    }

}
