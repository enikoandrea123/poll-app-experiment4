package com.example.poll_app.controller;

import com.example.poll_app.domain.Poll;
import com.example.poll_app.domain.VoteOption;
import com.example.poll_app.service.PollManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/polls/{pollId}/options")
public class VoteOptionController {
    private final PollManager pollManager;

    public VoteOptionController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @GetMapping
    public List<VoteOption> getOptions(@PathVariable Long pollId) {
        Poll poll = pollManager.getPollById(pollId);
        return poll != null ? poll.getOptions() : List.of();
    }
}
