package com.example.poll_app.service;

import com.example.poll_app.domain.Poll;
import com.example.poll_app.domain.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PollManager {
    private final Map<String, Poll> polls=new HashMap<>();
    private final Map<String, User> users=new HashMap<>();

}
