package com.healthily.repository.impl;

import com.healthily.model.User;
import com.healthily.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.Optional;

import static com.healthily.model.User.USER_ID_INDEX;
import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.keyEqualTo;

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

    public Optional<User> findByUserId(String userId) {
        return userTable.index(USER_ID_INDEX)
                .query(r -> r.queryConditional(keyEqualTo(Key.builder().partitionValue(userId).build())))
                .stream()
                .flatMap(page -> page.items().stream())
                .findFirst();
    }
}
