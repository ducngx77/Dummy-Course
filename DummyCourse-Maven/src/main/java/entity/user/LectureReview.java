package entity.user;

public class LectureReview {
    /*
    review_id INT IDENTITY(1,1) PRIMARY KEY,
                                 lecture_id INT,
                                 user_id INT,
                                 rating FLOAT,
                                 comment VARCHAR(255),
                                 review_date DATETIME,
                                 FOREIGN KEY (lecture_id) REFERENCES Courses(CourseID),
                                 FOREIGN KEY (user_id) REFERENCES Users(UserID)

     */
    private int reviewID;
    private int lectureID;
    private int userID;
    private float rating;
    private String comment;
    private String reviewDate;

    public LectureReview() {
    }

    public LectureReview(int lectureID, int userID, float rating, String comment, String reviewDate) {
        this.lectureID = lectureID;
        this.userID = userID;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public LectureReview(int reviewID, int lectureID, int userID, float rating, String comment, String reviewDate) {
        this.reviewID = reviewID;
        this.lectureID = lectureID;
        this.userID = userID;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    @Override
    public String toString() {
        return "LectureReview{" +
                "reviewID=" + reviewID +
                ", lectureID=" + lectureID +
                ", userID=" + userID +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reviewDate='" + reviewDate + '\'' +
                '}';
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }


}
