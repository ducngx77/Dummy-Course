package dao;

import entity.quiz.QuizResultQuestion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOQuizResultQuestion extends DBContext {
    public static final String CREATE_QUIZ_RESULT_QUESTION_SQL = "INSERT INTO QuizResultQuestion (ResultQuestionID, ResultID, QuestionID, point) VALUES (?, ?, ?, ?)";
    public static final String READ_QUIZ_RESULT_QUESTION_SQL = "SELECT * FROM QuizResultQuestion WHERE ResultQuestionID = ?";
    public static final String UPDATE_QUIZ_RESULT_QUESTION_SQL = "UPDATE QuizResultQuestion SET ResultID = ?, QuestionID = ?, point = ? WHERE ResultQuestionID = ?";
    public static final String DELETE_QUIZ_RESULT_QUESTION_SQL = "DELETE FROM QuizResultQuestion WHERE ResultQuestionID = ?";
    public static final String GET_ALL_QUIZ_RESULT_QUESTION_SQL = "SELECT * FROM QuizResultQuestion";
    public static final String GET_ALL_QUIZ_RESULT_QUESTION_BY_ID_SQL = "SELECT * FROM QuizResultQuestion WHERE ResultID = ?";
    public static final String GET_LAST_RESULT_QUESTION_ID = "SELECT MAX(QuestionID) FROM QuizQuestion";
    public static final String REMOVE_ALL_QUIZ_RESULT_QUESTION_BY_QUESTION_ID = "DELETE FROM QuizResultQuestion WHERE QuestionID = ?";

    public static void main(String[] args) {
        ArrayList<QuizResultQuestion> resultQuestions = (ArrayList<QuizResultQuestion>) new DAOQuizResultQuestion().getAllQuizResultQuestionByResultId("423359f1dc2d47aba15355099bc8b5cc");
        for (QuizResultQuestion resultQuestion : resultQuestions) {
            System.out.println(resultQuestion);
        }
    }

    public int getLastResultQuestionId() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_RESULT_QUESTION_ID)) {
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * create new QuizResultQuestion
     *
     * @param quizResultQuestion
     * @return QuizResultQuestion
     */
    public int createQuizResultQuestion(QuizResultQuestion quizResultQuestion) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUIZ_RESULT_QUESTION_SQL);
            preparedStatement.setString(1, quizResultQuestion.getResultQuestionId());
            preparedStatement.setString(2, quizResultQuestion.getResultId());
            preparedStatement.setString(3, quizResultQuestion.getQuestionId());
            preparedStatement.setDouble(4, quizResultQuestion.getPoint());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * get QuizResultQuestion based on ID
     *
     * @param resultQuestionId
     * @return QuizResultQuestion
     */
    public QuizResultQuestion readQuizResultQuestion(int resultQuestionId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_QUIZ_RESULT_QUESTION_SQL);
            preparedStatement.setInt(1, resultQuestionId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new QuizResultQuestion(
                        rs.getString("ResultQuestionID"),
                        rs.getString("ResultID"),
                        rs.getString("QuestionID"),
                        rs.getDouble("point")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * edit QuizResultQuestion in database
     *
     * @param quizResultQuestion
     * @return QuizResultQuestion
     */
    public QuizResultQuestion updateQuizResultQuestion(QuizResultQuestion quizResultQuestion) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUIZ_RESULT_QUESTION_SQL);
            preparedStatement.setString(1, quizResultQuestion.getResultId());
            preparedStatement.setString(2, quizResultQuestion.getQuestionId());
            preparedStatement.setDouble(3, quizResultQuestion.getPoint());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * delete QuizResultQuestion in database
     *
     * @param resultQuestionId
     */
    public int deleteQuizResultQuestion(int resultQuestionId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUIZ_RESULT_QUESTION_SQL);
            preparedStatement.setInt(1, resultQuestionId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * get all QuizResultQuestion
     *
     * @return QuizResultQuestion
     */
    public List<QuizResultQuestion> getAllQuizResultQuestion() {
        List<QuizResultQuestion> quizResultQuestions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUIZ_RESULT_QUESTION_SQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                QuizResultQuestion quizResultQuestion = new QuizResultQuestion(
                        rs.getString("ResultQuestionID"),
                        rs.getString("ResultID"),
                        rs.getString("QuestionID"),
                        rs.getDouble("point")
                );
                quizResultQuestions.add(quizResultQuestion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizResultQuestions;
    }

    /**
     * get QuizResultQuestion based on id
     *
     * @param resultId
     * @return QuizResultQuestion
     */
    public List<QuizResultQuestion> getAllQuizResultQuestionByResultId(String resultId) {
        List<QuizResultQuestion> quizResultQuestions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUIZ_RESULT_QUESTION_BY_ID_SQL);
            preparedStatement.setString(1, resultId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                QuizResultQuestion quizResultQuestion = new QuizResultQuestion(
                        rs.getString("ResultQuestionID"),
                        rs.getString("ResultID"),
                        rs.getString("QuestionID"),
                        rs.getDouble("point")
                );
                quizResultQuestions.add(quizResultQuestion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizResultQuestions;
    }

    public void removeAllQuizResultQuestionByQuestionID(String questionID) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_ALL_QUIZ_RESULT_QUESTION_BY_QUESTION_ID);
            preparedStatement.setString(1, questionID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}