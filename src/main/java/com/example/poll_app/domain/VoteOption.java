package com.example.poll_app.domain;

public class VoteOption {
    private Long id;
    private String caption;
    private int presentationOrder;

    public VoteOption() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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