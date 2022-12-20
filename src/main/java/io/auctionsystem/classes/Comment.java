package io.auctionsystem.classes;

import java.time.LocalDateTime;

public class Comment {
    private User user;
    private String message;
    private LocalDateTime time;

    public Comment(User user, String message, LocalDateTime time) {
        this.user = user;
        this.message = message;
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("%s by %s", getMessage(), getUser().getName());
    }
}
