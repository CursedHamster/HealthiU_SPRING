package com.example.healthiu.entity;

public enum MessageStatus {
    UNREAD("UNREAD"), READ("READ");

    private final String status;

    MessageStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
