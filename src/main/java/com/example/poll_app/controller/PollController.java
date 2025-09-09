package com.example.poll_app.controller;

import com.example.poll_app.domain.Poll;
import com.example.poll_app.service.PollManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/polls")
public class PollController {
    private final PollManager pollManager;

    public PollController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @PostMapping
    public ResponseEntity<Poll> createPoll(@RequestBody Poll poll) {
        Poll createdPoll = pollManager.createPoll(poll);
        return ResponseEntity.status(201).body(createdPoll);
    }

    @GetMapping
    public List<Poll> getAllPolls() {
        return pollManager.getAllPolls();
    }

    @DeleteMapping("/{pollId}")
    public ResponseEntity<Poll> deletePoll(@PathVariable Long pollId) {
        pollManager.deletePollById(pollId);
        return ResponseEntity.noContent().build();
    }
}
