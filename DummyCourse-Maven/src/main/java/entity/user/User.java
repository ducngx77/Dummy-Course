package entity.user;

public class User {

    private int userID;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String bio;
    private String address;
    private int gender;
    private int status;

    public User() {
    }

    public User(String username, String email, String password, String phoneNumber, String bio, String address, int gender, int status) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.address = address;
        this.gender = gender;
        this.status = status;
    }

    public User(int userID, String username, String email, String phoneNumber) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public User(int userID, String username, String email, String password, String phoneNumber, String bio, String address, int gender, int status) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.address = address;
        this.gender = gender;
        this.status = status;
    }


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", status=" + status +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
