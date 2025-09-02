package com.example.poll_app.controller;

import com.example.poll_app.domain.Vote;
import com.example.poll_app.service.PollManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping
public class VoteController {
    private final PollManager pollManager;

    public VoteController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @PostMapping("/polls/{pollId}/votes")
    public ResponseEntity<Vote> createVote(@PathVariable Long pollId, @RequestBody Vote voteRequest) {
        Vote vote = pollManager.createVote(pollId, voteRequest.getVoterId(), voteRequest.getOptionId());
        return ResponseEntity.created(URI.create("votes/" + vote.getId())).body(vote);
    }

    @PutMapping("/polls/{pollId}/votes")
    public ResponseEntity<Vote> updateVote(@PathVariable Long pollId, @RequestBody Vote voteRequest) {
        Vote updatedVote = pollManager.updateVote(pollId, voteRequest.getVoterId(), voteRequest.getOptionId());
        if (updatedVote == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedVote);
    }

    @GetMapping("/votes")
    public Collection<Vote> getAllVotes(){
        return pollManager.getAllVotes();
    }

}
