package entity.quiz;


public class QuizResultQuestion {

    private String resultQuestionId;
    private String resultId;
    private String questionId;
    private double point;

    public QuizResultQuestion() {
    }


    public QuizResultQuestion(String resultId, String questionId, double point) {
        this.resultId = resultId;
        this.questionId = questionId;
        this.point = point;
    }

    public QuizResultQuestion(String resultQuestionId, String resultId, String questionId, double point) {
        this.resultQuestionId = resultQuestionId;
        this.resultId = resultId;
        this.questionId = questionId;
        this.point = point;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getResultQuestionId() {
        return resultQuestionId;
    }

    public void setResultQuestionId(String resultQuestionId) {
        this.resultQuestionId = resultQuestionId;
    }

    @Override
    public String toString() {
        return "QuizResultQuestion{" +
                "resultQuestionId='" + resultQuestionId + '\'' +
                ", resultId='" + resultId + '\'' +
                ", questionId='" + questionId + '\'' +
                ", point=" + point +
                '}';
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
