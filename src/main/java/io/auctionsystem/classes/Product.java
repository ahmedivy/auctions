package io.auctionsystem.classes;

public class Product {

    public enum Category {
        CARS, JEWELRY, WATCH, HANDBAGS, FINE_ART, OTHER
    }
    private String name;
    private String description;
    private String category;

    public Product(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
