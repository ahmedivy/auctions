package io.auctionsystem.classes;

import java.time.LocalDateTime;

public class Bid {
    private double amount;
    private User bidder;
    private LocalDateTime time;

    public Bid(double amount, User bidder) {
        this.amount = amount;
        this.bidder = bidder;
    }

    public Bid(double amount, User bidder, LocalDateTime time) {
        this.amount = amount;
        this.bidder = bidder;
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("%s by %s", getAmount(), getBidder().getName());
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
