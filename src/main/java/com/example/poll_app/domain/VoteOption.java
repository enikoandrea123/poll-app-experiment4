package com.example.poll_app.domain;

import java.time.Instant;

public class VoteOption {
    private String caption;
    private int presentationOrder;

    public VoteOption() {
    }

    public int getPresentationOrder() {
        return presentationOrder;
    }

    public String getCaption() {
        return caption;
    }

    public void setPresentationOrder(int presentationOrder) {
        this.presentationOrder = presentationOrder;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}