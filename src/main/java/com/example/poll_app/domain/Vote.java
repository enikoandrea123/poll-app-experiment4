package com.example.poll_app.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant publishedAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "voter_id", nullable = false)
    private User voter;

    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false)
    private VoteOption votesOn;

    public Vote() {}

    public Vote(User voter, Poll poll, VoteOption votesOn) {
        this.voter = voter;
        this.poll = poll;
        this.votesOn=votesOn;
    }


    public Long getId() {
        return id;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public User getVoter() {
        return voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public VoteOption getOption() {
        return votesOn;
    }

    public void setOption(VoteOption option) {
        this.votesOn = option;
    }
}