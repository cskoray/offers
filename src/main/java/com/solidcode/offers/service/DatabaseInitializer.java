package com.solidcode.offers.service;

import static java.time.LocalDateTime.now;

import java.sql.Timestamp;
import java.util.UUID;

import com.solidcode.offers.repository.UserRepository;
import com.solidcode.offers.repository.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseInitializer {

    private final UserRepository userRepository;

    @Autowired
    public DatabaseInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        if (!userRepository.existsById(1L)) {
            User user = User.builder()
                    .id(1L)
                    .userKey(UUID.randomUUID().toString())
                    .name("koray")
                    .email("koray@koray.com")
                    .createdDate(Timestamp.valueOf(now()))
                    .build();
            userRepository.save(user);
        }
    }
}
