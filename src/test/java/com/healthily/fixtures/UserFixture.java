package com.healthily.fixtures;

import com.healthily.model.Gender;
import com.healthily.model.User;

public class UserFixture {
    private UserFixture() {
    }

    public static User aUser() {
        return User.builder()
                .userId("userId")
                .email("abc@gmail.com")
                .age(25)
                .gender(Gender.MALE)
                .password("strongPassword")
                .build();
    }
}
