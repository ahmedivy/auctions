package io.auctionsystem.classes;

import java.util.ArrayList;

public class AuctionSystem {
    private User authenticatedUser;
    private ArrayList<User> users;
    private ArrayList<Listing> listings;

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(User authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }


    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Listing> getListings() {
        return listings;
    }

    public void setListings(ArrayList<Listing> listings) {
        this.listings = listings;
    }

    public ArrayList<Listing> getActiveListings() {
        ArrayList<Listing> activeListings = new ArrayList<>();
        for (Listing listing : listings) {
            if (listing.isActive()) {
                activeListings.add(listing);
            }
        }
        return activeListings;
    }

    public ArrayList<Listing> getInactiveListings() {
        ArrayList<Listing> inactiveListings = new ArrayList<>();
        for (Listing listing : listings) {
            if (!listing.isActive()) {
                inactiveListings.add(listing);
            }
        }
        return inactiveListings;
    }

    public ArrayList<Listing> getListingsExcludingUser() {
        ArrayList<Listing> list = new ArrayList<>();
        for (Listing listing : listings) {
            if (!listing.getSeller().equals(authenticatedUser)) {
                list.add(listing);
            }
        }
        return list;
    }

    public ArrayList<Listing> getTrendingListings() {
        ArrayList<Listing> list = new ArrayList<>();
        for (Listing listing : listings) {
            if (listing.getBids().size() > 5) {
                list.add(listing);
            }
        }
        return list;
    }

    public ArrayList<Listing> getUsersListings() {
        ArrayList<Listing> list = new ArrayList<>();
        for (Listing listing : listings) {
            if (listing.getSeller().equals(authenticatedUser)) {
                list.add(listing);
            }
        }
        return list;
    }

    public void addListing(Listing listing) {
        listings.add(listing);
    }

    public ArrayList<Listing> getWatchList() {
        ArrayList<Listing> list = new ArrayList<>();
        for (Listing listing : listings) {
            for (Bid bid : listing.getBids()) {
                if (bid.getBidder().equals(authenticatedUser)) {
                    list.add(listing);
                    break;
                }
            }
        }
        return list;
    }

    public ArrayList<Listing> getByCategory(String category) {
        ArrayList<Listing> list = new ArrayList<>();
        for (Listing listing : listings) {
            if (listing.getProduct().getCategory().equals(category)) {
                list.add(listing);
            }
        }
        return list;
    }

    public void setAuthenticatedUser(String username, String password) {
        for (User user: users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                this.authenticatedUser = user;
            }
        }
    }

    public int validateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return 0;
            }
        }
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                return 1;
            }
        }
        return -1;
    }

    public boolean userExists(String username) {
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean validateEmail(String email) {
        for (User user: users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void changePassword(String email, String password) {
        for (User user: users) {
            if (user.getEmail().equals(email)) {
                user.setPassword(password);
                return;
            }
        }
    }

    public void addUser(User user) {
        users.add(user);
    }
}
