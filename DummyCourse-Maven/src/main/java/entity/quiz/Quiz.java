package entity.quiz;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Quiz {
    private String quizID;
    private int courseID;
    private int sectionID;
    private String quizName;
    private LocalDateTime startTime;
    private LocalTime duration;
    private int status;

    public Quiz() {
    }

    public Quiz(int courseID, int sectionID, String quizName, LocalDateTime startTime, LocalTime duration, int status) {
        this.courseID = courseID;
        this.sectionID = sectionID;
        this.quizName = quizName;
        this.startTime = startTime;
        this.duration = duration;
        this.status = status;
    }

    public Quiz(String quizID, int courseID, int sectionID, String quizName, LocalDateTime startTime, LocalTime duration, int status) {
        this.quizID = quizID;
        this.courseID = courseID;
        this.sectionID = sectionID;
        this.quizName = quizName;
        this.startTime = startTime;
        this.duration = duration;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizID=" + quizID +
                ", courseID=" + courseID +
                ", sectionID=" + sectionID +
                ", quizName='" + quizName + '\'' +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", status=" + status +
                '}';
    }

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}