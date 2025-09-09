package com.example.poll_app.controller;

import com.example.poll_app.domain.Poll;
import com.example.poll_app.domain.VoteOption;
import com.example.poll_app.service.PollManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
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
