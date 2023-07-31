package entity.quiz;

public class QuizResultAnswer {
    private int ResultChoiceID;
    private String ResultQuestionID;
    private int ChoiceID;

    public QuizResultAnswer(String resultQuestionID, int choiceID) {
        ResultQuestionID = resultQuestionID;
        ChoiceID = choiceID;
    }

    public QuizResultAnswer(int resultChoiceID, String resultQuestionID, int choiceID) {
        ResultChoiceID = resultChoiceID;
        ResultQuestionID = resultQuestionID;
        ChoiceID = choiceID;
    }

    public QuizResultAnswer() {
    }

    @Override
    public String toString() {
        return "QuizResultAnswer{" +
                "ResultChoiceID=" + ResultChoiceID +
                ", ResultQuestionID='" + ResultQuestionID + '\'' +
                ", ChoiceID=" + ChoiceID +
                '}';
    }

    public int getResultChoiceID() {
        return ResultChoiceID;
    }

    public void setResultChoiceID(int resultChoiceID) {
        ResultChoiceID = resultChoiceID;
    }

    public String getResultQuestionID() {
        return ResultQuestionID;
    }

    public void setResultQuestionID(String resultQuestionID) {
        ResultQuestionID = resultQuestionID;
    }

    public int getChoiceID() {
        return ChoiceID;
    }

    public void setChoiceID(int choiceID) {
        ChoiceID = choiceID;
    }
}
