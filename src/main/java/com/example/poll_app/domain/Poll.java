package com.example.poll_app.domain;

import java.time.Instant;
import java.util.List;

public class Poll {
    private Long id;
    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    private Long creatorId;
    private Boolean isPublic = true;
    private Boolean allowSingleVotePerUser = true;
    private List<VoteOption> options;

    public Poll() {
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public String getQuestion() {
        return question;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Long getId() {
        return id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOptions(List<VoteOption> options) {
        this.options = options;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public boolean isPublic() {
        return isPublic;
    }
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isAllowSingleVotePerUser() {
        return allowSingleVotePerUser;
    }
    public void setAllowSingleVotePerUser(boolean allowSingleVotePerUser) {
        this.allowSingleVotePerUser = allowSingleVotePerUser;
    }

    public List<VoteOption> getOptions() {
        return options;
    }
}

