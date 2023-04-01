package com.example.healthiu.entity;
public enum MessageStatus {
    UNREAD("UNREAD"), READ("READ");

    private String status;

    MessageStatus() {
    }

    MessageStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
