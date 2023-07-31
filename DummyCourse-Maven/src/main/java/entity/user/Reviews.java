package entity.user;

public class Reviews {
    private int reviewID;
    private int courseID;
    private int userID;
    private float rating;
    private String comment;
    private String reviewDate;

    public Reviews(int reviewID, int courseID, int userID, float rating, String comment, String reviewDate) {
        this.reviewID = reviewID;
        this.courseID = courseID;
        this.userID = userID;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public Reviews(int courseID, int userID, float rating, String comment, String reviewDate) {
        this.courseID = courseID;
        this.userID = userID;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public Reviews() {
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "reviewID=" + reviewID +
                ", courseID=" + courseID +
                ", userID=" + userID +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reviewDate='" + reviewDate + '\'' +
                '}';
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
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
