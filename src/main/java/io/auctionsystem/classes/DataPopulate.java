package io.auctionsystem.classes;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


// Class to place random bids on listings as a sample of how the system works
public class DataPopulate {
    public static void main(String[] args) throws IOException {
        System.out.println("Confirm...???");    // Added so that didn't run by mistake
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().equals("yes")) {
            GsonHandling.loadGson(DataSingleton.getInstance().getAuctionSystem());
            Random random = new Random();
            DataSingleton.getInstance().getAuctionSystem().getListings().forEach(listing -> {
                for (int i = 0; i < random.nextInt(10 + 1); i++) {
                    listing.addBid(new Bid(listing.getCurrentPrice() + 10,
                            DataSingleton.getInstance().getAuctionSystem().getUsers().get(random.nextInt(30 + 1))));
                }
            });
            GsonHandling.saveGson(DataSingleton.getInstance().getAuctionSystem());
        }
    }
}
