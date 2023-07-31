package entity.quiz;

import java.util.Arrays;

public class QuizQuestion {
    private String questionID;
    private String quizID;
    private String questionText;
    private String explanation;
    private byte[] picture;

    public QuizQuestion() {
    }


    public QuizQuestion(String quizID, String questionText, String explanation, byte[] picture) {
        this.quizID = quizID;
        this.questionText = questionText;
        this.explanation = explanation;
        this.picture = picture;
    }

    public QuizQuestion(String questionID, String quizID, String questionText, String explanation, byte[] picture) {
        this.questionID = questionID;
        this.quizID = quizID;
        this.questionText = questionText;
        this.explanation = explanation;
        this.picture = picture;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    @Override
    public String toString() {
        return "QuizQuestion{" +
                "questionID=" + questionID +
                ", quizID=" + quizID +
                ", questionText='" + questionText + '\'' +
                ", explanation='" + explanation + '\'' +
                ", picture=" + Arrays.toString(picture) +
                '}';
    }


    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}






