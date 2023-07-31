package entity.quiz;

public class QuizResult {
    private String resultId;
    private String quizId;
    private int studentID;
    private float score;
    private String testTime;


    public QuizResult() {
    }


    public QuizResult(String quizId, int studentID, float score, String testTime) {
        this.quizId = quizId;
        this.studentID = studentID;
        this.score = score;
        this.testTime = testTime;
    }

    public QuizResult(String resultId, String quizId, int studentID, float score, String testTime) {
        this.resultId = resultId;
        this.quizId = quizId;
        this.studentID = studentID;
        this.score = score;
        this.testTime = testTime;
    }

    @Override
    public String toString() {
        return "QuizResult{" +
                "resultId=" + resultId +
                ", quizId=" + quizId +
                ", studentID=" + studentID +
                ", score=" + score +
                ", testTime='" + testTime + '\'' +
                '}';
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }
}
