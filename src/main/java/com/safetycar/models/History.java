package com.safetycar.models;

import com.safetycar.models.users.UserDetails;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "histories")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    int id;

    @Column(name = "history")
    private String history;

    @Column(name = "action")
    private String action;

    @ManyToOne
    @JoinColumn(name = "username")
    private UserDetails actor;

    @Column(name = "timestamp")
    @Temporal(value = TemporalType.TIMESTAMP)
    private final Date timestamp;

    public History() {
        timestamp = new Date(System.currentTimeMillis());
    }

    public UserDetails getActor() {
        return actor;
    }

    public int getId() {
        return id;
    }

    public String getHistory() {
        return history;
    }

    public String getAction() {
        return action;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setActor(UserDetails actor) {
        this.actor = actor;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history1 = (History) o;
        return id == history1.id &&
                Objects.equals(history, history1.history) &&
                Objects.equals(action, history1.action) &&
                Objects.equals(actor, history1.actor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, history, action, actor);
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", history='" + history + '\'' +
                ", action='" + action + '\'' +
                ", actor=" + actor +
                ", timestamp=" + timestamp +
                '}';
    }
}
