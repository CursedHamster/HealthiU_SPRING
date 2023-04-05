package com.example.healthiu.entity.table;

import com.example.healthiu.entity.MessageStatus;
import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String senderLogin;

    @Column
    private String recipientLogin;

    @Column
    private String content;

    @Column
    private Timestamp timestamp;

    @Column
    private String status;

    public Message(String senderLogin, String recipientId, String content) {
        this.senderLogin = senderLogin;
        this.recipientLogin = recipientId;
        this.content = content;
        this.timestamp = new Timestamp(new Date().getTime());
        this.status = MessageStatus.UNREAD.toString();
    }

    @Override
    public String toString() {
        return "Message [from=" + senderLogin + ", to=" + recipientLogin + ", message=" + content + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Message message = (Message) o;
        return id != null && Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
