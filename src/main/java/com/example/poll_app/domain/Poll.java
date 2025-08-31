package com.example.poll_app.domain;

import java.time.Instant;

public class Poll {
    private String question;
    private Instant publishedAt;
    private Instant validUntil;

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
}
