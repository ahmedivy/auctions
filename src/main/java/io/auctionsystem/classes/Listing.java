package io.auctionsystem.classes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Listing {

    private int id;
    private boolean isActive;
    private Product product;
    private double startingPrice;
    private double currentPrice;
    private User seller;
    private User winner;
    private ArrayList<Bid> bids;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String imageSrc;
    private ArrayList<Comment> comments = new ArrayList<>();

    public Listing(Product product, double startingPrice, User seller, LocalDateTime startTime, LocalDateTime endTime) {
        this.isActive = true;
        this.product = product;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice;
        this.seller = seller;
        this.winner = null;
        this.bids = new ArrayList<>();
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = GsonHandling.getListingIDCount();
    }

    public Listing(int id, boolean isActive, Product product, double startingPrice,
                   double currentPrice, User seller, User winner, ArrayList<Bid> bids,
                   LocalDateTime startTime, LocalDateTime endTime, String imageSrc,
                   ArrayList<Comment> comments) {
        this.id = id;
        this.isActive = isActive;
        this.product = product;
        this.startingPrice = startingPrice;
        this.currentPrice = currentPrice;
        this.seller = seller;
        this.winner = winner;
        this.bids = bids;
        this.startTime = startTime;
        this.endTime = endTime;
        this.imageSrc = imageSrc;
        this.comments = comments;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getTimeLeft() {
        Duration timeLeft = Duration.between(LocalDateTime.now(), endTime);
        return String.format("%02d D %02d Hrs", timeLeft.toDays(), timeLeft.toHours() % 24);
    }

    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", product=" + product +
                ", startingPrice=" + startingPrice +
                ", currentPrice=" + currentPrice +
                ", seller=" + seller +
                ", winner=" + winner +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", imageSrc='" + imageSrc + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }

    public void setBids(ArrayList<Bid> bids) {
        this.bids = bids;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
        this.currentPrice = bid.getAmount();
    }

    public void removeBid(Bid bid) {
        this.bids.remove(bid);
    }

    public void endListing() {
        this.isActive = false;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public String getProductName() {
        return this.product.getName();
    }

    public String getProductCategory() {
        return this.product.getCategory();
    }

    public String getProductDescription() {
        return this.product.getDescription();
    }

    public int getTotalBids() {
        return this.bids.size();
    }
}
