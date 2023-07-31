package dao;

import entity.quiz.QuizResultAnswer;
import entity.quiz.QuizResultQuestion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOQuizResultAnswer extends DBContext {

    private static final String GET_ALL_RESULT_ANSWER_BY_RESULT_QUESTION_ID_SQL = "SELECT * FROM QuizResultAnswer WHERE ResultQuestionID = ?";
    private static final String DELETE_QUIZ_RESULT_ANSWER_BY_QUESTION_ID = "DELETE ra\n" +
            "FROM QuizResultAnswer ra\n" +
            "INNER JOIN QuizResultQuestion rq ON ra.ResultQuestionID = rq.ResultQuestionID\n" +
            "WHERE rq.QuestionID = ?;\n";

    public static void main(String[] args) {
        ArrayList<QuizResultQuestion> quizResultQuestions = (ArrayList<QuizResultQuestion>) new DAOQuizResultQuestion().getAllQuizResultQuestionByResultId("9f9748f27b194502affc13ce890830d9");
        for (QuizResultQuestion quizResultQuestion : quizResultQuestions) {
            System.out.println("Question ID: " + quizResultQuestion.getResultQuestionId());
            ArrayList<QuizResultAnswer> quizResultAnswers = new DAOQuizResultAnswer().getAllResultAnswerByResultQuestionID(quizResultQuestion.getResultQuestionId());
            for (QuizResultAnswer quizResultAnswer : quizResultAnswers) {
                System.out.println(quizResultAnswer.getResultChoiceID());
            }
        }
    }

    public ArrayList<QuizResultAnswer> getAllResultAnswerByResultQuestionID(String resultQuestionID) {
        return getQuizResultAnswers(resultQuestionID, GET_ALL_RESULT_ANSWER_BY_RESULT_QUESTION_ID_SQL);
    }

    private ArrayList<QuizResultAnswer> getQuizResultAnswers(String resultQuestionID, String getAllResultAnswerByResultQuestionIdSql) {
        ArrayList<QuizResultAnswer> quizResultAnswers = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getAllResultAnswerByResultQuestionIdSql)) {
            preparedStatement.setString(1, resultQuestionID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                quizResultAnswers.add(new QuizResultAnswer(
                        rs.getInt("ResultChoiceID"),
                        rs.getString("ResultQuestionID"),
                        rs.getInt("ChoiceID")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizResultAnswers;
    }

    /**
     * @param quizresultanswer
     * @return 0 or -1 if can't create Quiz Result Answer
     */
    public int createQuizResultAnswer(QuizResultAnswer quizresultanswer) {
        String sql = "INSERT INTO [dbo].[QuizResultAnswer]\n" +
                "           ([ResultQuestionID]\n" +
                "           ,[ChoiceID])\n" +
                "     VALUES\n" +
                "           (?,?)";
        PreparedStatement pre = null;
        try {
            pre = connection.prepareStatement(sql);
            pre.setString(1, quizresultanswer.getResultQuestionID());
            pre.setInt(2, quizresultanswer.getChoiceID());
            return pre.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
     * @param ResultChoiceiD
     * @param ResultQuestionID
     * @param ChoiceID
     * @return 0 or -1 if can't update QuizResultAnswer
     */
    public int updateQuizResultAnswer(int ResultChoiceiD, int ResultQuestionID, int ChoiceID) {
        String sql = "UPDATE [dbo].[QuizResultAnswer]\n" +
                "   SET [ResultQuestionID] = ?" +
                "      ,[ChoiceID] = ?" +
                " WHERE ResultChoiceID=?";
        PreparedStatement pre = null;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, ResultQuestionID);
            pre.setInt(2, ChoiceID);
            pre.setInt(3, ResultChoiceiD);
            pre.executeUpdate();
            return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
     * @param ResultChoiceID
     * @return 0 or -1 if can't deleteQuizResultAnswer
     */
    public int deleteQuizResultAnswer(int ResultChoiceID) {
        String sql = "Delete From QuizResultAnswer where ResultChoiceID = ?";
        PreparedStatement pre = null;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, ResultChoiceID);
            pre.executeUpdate();
            return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int deleteQuizResultAnswerByChoiceID(int choiceId) {
        String sql = "Delete From QuizResultAnswer where ChoiceID = ?";
        PreparedStatement pre = null;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, choiceId);
            pre.executeUpdate();
            return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public void deleteQuizResultAnswerByQuestionID(String questionID) {
        PreparedStatement pre = null;
        try {
            pre = connection.prepareStatement(DELETE_QUIZ_RESULT_ANSWER_BY_QUESTION_ID);
            pre.setString(1, questionID);
            pre.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
