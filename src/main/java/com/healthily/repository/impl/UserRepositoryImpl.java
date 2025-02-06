package com.healthily.repository.impl;

import com.healthily.model.User;
import com.healthily.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DynamoDbTable<User> userTable;

    public void save(User user) {
        userTable.putItem(user);
    }

    public void remove(String email) {
        findByEmail(email).ifPresent(userTable::deleteItem);
    }


    public Optional<User> findByEmail(String email) {
        User user = userTable.getItem(r -> r.key(k -> k.partitionValue(email)));
        return Optional.ofNullable(user);
    }
}
