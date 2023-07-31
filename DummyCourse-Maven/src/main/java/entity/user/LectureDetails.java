package entity.user;

public class LectureDetails {
    /*
        LectureID          INT IDENTITY (1,1) PRIMARY KEY,
        UserID             INT,
        LectureName        VARCHAR(50),
        LectureDescription VARCHAR(255),
        lectureProfile     VARCHAR(255),
        FOREIGN KEY (UserID) REFERENCES Users (UserID)
         */
    private int lectureID;
    private int userID;
    private String lectureDescription;
    private byte[] lectureProfile;
    //accepted BIT,
    private boolean accepted;

    public LectureDetails(int userID, String lectureDescription, byte[] lectureProfile, boolean accepted) {
        this.userID = userID;
        this.lectureDescription = lectureDescription;
        this.lectureProfile = lectureProfile;
        this.accepted = accepted;
    }

    public LectureDetails(int lectureID, int userID, String lectureDescription, byte[] lectureProfile, boolean accepted) {
        this.lectureID = lectureID;
        this.userID = userID;
        this.lectureDescription = lectureDescription;
        this.lectureProfile = lectureProfile;
        this.accepted = accepted;
    }

    public LectureDetails() {
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public int getLectureID() {
        return lectureID;
    }

    public void setLectureID(int lectureID) {
        this.lectureID = lectureID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getLectureDescription() {
        return lectureDescription;
    }

    public void setLectureDescription(String lectureDescription) {
        this.lectureDescription = lectureDescription;
    }

    public byte[] getLectureProfile() {
        return lectureProfile;
    }

    public void setLectureProfile(byte[] lectureProfile) {
        this.lectureProfile = lectureProfile;
    }
}
