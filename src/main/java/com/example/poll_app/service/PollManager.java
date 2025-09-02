package com.example.poll_app.service;

import com.example.poll_app.domain.Poll;
import com.example.poll_app.domain.User;
import com.example.poll_app.domain.Vote;
import com.example.poll_app.domain.VoteOption;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PollManager {
    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, Poll> polls = new HashMap<>();
    private final Map<Long, VoteOption> options = new HashMap<>();
    private final Map<Long, Vote> votes = new HashMap<>();

    private final AtomicLong userIdCounter = new AtomicLong(1);
    private final AtomicLong voteIdCounter = new AtomicLong(1);

    public User createUser(User user) {
        long id = userIdCounter.getAndIncrement();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public Vote createVote(Long pollId, Long voteId, Long optionId) {
        Vote vote = new Vote();
        vote.setId(voteIdCounter.getAndIncrement());
        vote.setPollId(pollId);
        vote.setOptionId(optionId);
        vote.setVoterId(voteId);
        vote.setPublishedAt(Instant.now());
        votes.put(vote.getVoterId(), vote);
        return vote;
    }

    public Vote updateVote(Long pollId, Long voterId, Long optionId) {
        for (Vote v : votes.values()) {
            if (v.getPollId().equals(pollId) && v.getVoterId().equals(voterId)) {
                v.setOptionId(optionId);
                v.setPublishedAt(Instant.now());
                return v;
            }
        }
        return null;
    }

    public Collection<Vote> getAllVotes() {
        return votes.values();
    }

    public void deleteVotesByPoll(Long pollId) {
        votes.values().removeIf(v -> v.getPollId().equals(pollId));
    }
}
