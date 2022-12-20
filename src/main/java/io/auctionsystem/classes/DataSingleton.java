package io.auctionsystem.classes;

public class DataSingleton {
    private static DataSingleton instance = null;
    private AuctionSystem auctionSystem;

    private DataSingleton() {
        auctionSystem = new AuctionSystem();
    }

    public static DataSingleton getInstance() {
        if (instance == null) {
            instance = new DataSingleton();
        }
        return instance;
    }

    public AuctionSystem getAuctionSystem() {
        return auctionSystem;
    }

    public void setAuctionSystem(AuctionSystem auctionSystem) {
        this.auctionSystem = auctionSystem;
    }
}

