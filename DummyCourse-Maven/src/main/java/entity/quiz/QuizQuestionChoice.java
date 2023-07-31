package entity.quiz;

public class QuizQuestionChoice {
    private int choiceId;
    private String questionId;
    private String choiceText;
    private boolean isCorrect;
    private float weight;

    public QuizQuestionChoice() {
    }

    public QuizQuestionChoice(String questionId, String choiceText, boolean isCorrect, float weight) {
        this.questionId = questionId;
        this.choiceText = choiceText;
        this.isCorrect = isCorrect;
        this.weight = weight;
    }

    public QuizQuestionChoice(int choiceId, String questionId, String choiceText, boolean isCorrect, float weight) {
        this.choiceId = choiceId;
        this.questionId = questionId;
        this.choiceText = choiceText;
        this.isCorrect = isCorrect;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "QuizQuestionChoice{" +
                "choiceId=" + choiceId +
                ", questionId=" + questionId +
                ", choiceText='" + choiceText + '\'' +
                ", isCorrect=" + isCorrect +
                ", weight=" + weight +
                '}';
    }

    public int getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
