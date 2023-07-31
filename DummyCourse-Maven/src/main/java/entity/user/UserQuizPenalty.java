package entity.user;

import java.util.Date;

public class UserQuizPenalty {
    //PenaltyID INT IDENTITY(1,1) PRIMARY KEY,
    //    UserID INT,
    //    QuizID varchar(32),
    //    penaltyStartTime DATETIME,
    //    penaltyDuration INT,
    //    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    //    FOREIGN KEY (QuizID) REFERENCES Quiz(QuizID)
    private int penaltyID;
    private int userID;
    private String quizID;
    private Date penaltyStartTime;
    private int penaltyDuration;

    public UserQuizPenalty(int userID, String quizID, Date penaltyStartTime, int penaltyDuration) {
        this.userID = userID;
        this.quizID = quizID;
        this.penaltyStartTime = penaltyStartTime;
        this.penaltyDuration = penaltyDuration;
    }

    public UserQuizPenalty(int penaltyID, int userID, String quizID, Date penaltyStartTime, int penaltyDuration) {
        this.penaltyID = penaltyID;
        this.userID = userID;
        this.quizID = quizID;
        this.penaltyStartTime = penaltyStartTime;
        this.penaltyDuration = penaltyDuration;
    }

    public int getPenaltyID() {
        return penaltyID;
    }

    public void setPenaltyID(int penaltyID) {
        this.penaltyID = penaltyID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    @Override
    public String toString() {
        return "UserQuizPenalty{" +
                "penaltyID=" + penaltyID +
                ", userID=" + userID +
                ", quizID='" + quizID + '\'' +
                ", penaltyStartTime=" + penaltyStartTime +
                ", penaltyDuration=" + penaltyDuration +
                '}';
    }

    public Date getPenaltyStartTime() {
        return penaltyStartTime;
    }

    public void setPenaltyStartTime(Date penaltyStartTime) {
        this.penaltyStartTime = penaltyStartTime;
    }

    public int getPenaltyDuration() {
        return penaltyDuration;
    }

    public void setPenaltyDuration(int penaltyDuration) {
        this.penaltyDuration = penaltyDuration;
    }
}
