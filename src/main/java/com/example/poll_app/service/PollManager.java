package com.example.poll_app.service;

import com.example.poll_app.domain.Poll;
import com.example.poll_app.domain.User;
import com.example.poll_app.domain.Vote;
import com.example.poll_app.domain.VoteOption;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PollManager {
    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, Poll> polls = new HashMap<>();
    private final Map<Long, VoteOption> options = new HashMap<>();
    private final Map<Long, Vote> votes = new HashMap<>();

    private final AtomicLong userIdCounter = new AtomicLong(1);

    public User createUser(User user) {
        long id = userIdCounter.getAndIncrement();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }
}
