package io.auctionsystem.classes;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class GsonHandling {
    public static Gson gson;
    public static int UserIDCount = 0;
    public static int ListingIDCount = 0;

    public static void loadGson(AuctionSystem data) throws IOException {
        gson = new Gson();
        String usersJson = AzureAccess.getJSONString("users");
        ArrayList<User> users = gson.fromJson(usersJson, new TypeToken<ArrayList<User>>() {}.getType());
        data.setUsers(users);
        // Map users to ID
        HashMap<Integer, User> usersMap = new HashMap<>();
        for (User user : users) {
            usersMap.put(user.getId(), user);
        }
        // Load listings
        JsonDeserializer<User> usersDeserializer = (json, typeOfT, context) -> {
            JsonObject jsonObject = json.getAsJsonObject();
            return usersMap.get(jsonObject.get("id").getAsInt());
        };
        JsonDeserializer<LocalDateTime> dateTimeDeserializer = (json, typeOfT, context) -> {
            return LocalDateTime.parse(
                    json.getAsJsonPrimitive().getAsString(),
                    DateTimeFormatter.ISO_DATE_TIME);
        };
        gson = new GsonBuilder()
                .registerTypeAdapter(User.class, usersDeserializer)
                .registerTypeAdapter(LocalDateTime.class, dateTimeDeserializer)
                .create();
        String listingsJson = AzureAccess.getJSONString("listings");
        ArrayList<Listing> listings = gson.fromJson(listingsJson, new TypeToken<ArrayList<Listing>>() {}.getType());
        data.setListings(listings);

        setListingIDCount(data);
        setUserIDCount(data);
    }

    public static void saveGson(AuctionSystem data) throws IOException {
        // Update Users
        gson = new GsonBuilder().setPrettyPrinting().create();
        String content = gson.toJson(data.getUsers());
        AzureAccess.updateBlob(content, "users.json");
        // Update Listings
        // Using a custom serializers to avoid data repetition
        JsonSerializer<User> userSerializer = (user, typeOfSrc, context) -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", user.getId());
            return jsonObject;
        };
        JsonSerializer<LocalDateTime> dateTimeSerializer = (dateTime, typeOfSrc, context) -> {
            return new JsonPrimitive(dateTime.format(DateTimeFormatter.ISO_DATE_TIME));
        };

        // Registering the serializers
        gson = new GsonBuilder()
                .registerTypeAdapter(User.class, userSerializer)
                .registerTypeAdapter(LocalDateTime.class, dateTimeSerializer)
                .setPrettyPrinting()
                .create();
        content = gson.toJson(data.getListings());
        AzureAccess.updateBlob(content, "listings.json");
    }

    public static void setUserIDCount(AuctionSystem data) {
        int max = 0;
        for (User user : data.getUsers()) {
            max = Math.max(user.getId(), max);
        }
        UserIDCount = ++max;
    }

    public static void setListingIDCount(AuctionSystem data) {
        int max = 0;
        for (Listing listing : data.getListings()) {
            max = Math.max(listing.getId(), max);
        }
        ListingIDCount = ++max;
    }

    public static int getUserIDCount() {
        return UserIDCount++;
    }

    public static int getListingIDCount() {
        return ListingIDCount++;
    }

}






