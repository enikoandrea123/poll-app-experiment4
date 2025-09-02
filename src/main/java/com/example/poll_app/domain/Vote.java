package com.example.poll_app.domain;

import java.time.Instant;

public class Vote {
    private Long Id;
    private Long voterId;
    private Long optionId;
    private Long pollId;
    private Instant publishedAt;

    public Vote() {
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Long getId() {
        return Id;
    }

    public Long getOptionId() {
        return optionId;
    }

    public Long getPollId() {
        return pollId;
    }

    public Long getVoterId() {
        return voterId;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public void setVoterId(Long voterId) {
        this.voterId = voterId;
    }
}
