package io.auctionsystem.classes;

public class User {
    private int id;
    private String username;
    private String name;
    private String email;
    private String password;
    private Address address;
    private String phone;

    public User(String username, String name, String email, String password, Address address, String phone) {
        this.id = GsonHandling.getUserIDCount();
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    public User(int id, String username, String name, String email, String password, Address address, String phone) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof User && ((User) obj).getId() == this.id && ((User) obj).getUsername().equals(this.username);
    }

}
