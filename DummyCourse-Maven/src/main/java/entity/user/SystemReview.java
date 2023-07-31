package entity.user;

public class SystemReview {
    //review_id INT IDENTITY(1,1) PRIMARY KEY,
//                                user_id INT,
//                                rating FLOAT,
//                                comment VARCHAR(255),
//                                review_date DATETIME,
//                                FOREIGN KEY (user_id) REFERENCES Users(UserID)
    private int reviewID;
    private int userID;
    private float rating;
    private String comment;
    private String reviewDate;

    public SystemReview() {
    }

    public SystemReview(int userID, float rating, String comment, String reviewDate) {
        this.userID = userID;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public SystemReview(int reviewID, int userID, float rating, String comment, String reviewDate) {
        this.reviewID = reviewID;
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

    @Override
    public String toString() {
        return "SystemReview{" +
                "reviewID=" + reviewID +
                ", userID=" + userID +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reviewDate='" + reviewDate + '\'' +
                '}';
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }
}
