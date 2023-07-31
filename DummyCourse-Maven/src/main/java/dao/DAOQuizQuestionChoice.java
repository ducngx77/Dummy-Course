package dao;

import entity.quiz.QuizQuestionChoice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOQuizQuestionChoice extends DBContext {
    private static final String CREATE_QUESTION_CHOICE_SQL = "INSERT INTO QuizQuestionChoice (QuestionID, ChoiceText, IsCorrect, Weight) VALUES (?, ?, ?, ?)";
    private static final String GET_QUESTION_CHOICE_BY_ID_SQL = "SELECT * FROM QuizQuestionChoice WHERE ChoiceID = ?";
    private static final String UPDATE_QUESTION_CHOICE_SQL = "UPDATE QuizQuestionChoice SET QuestionID = ?, ChoiceText = ?, IsCorrect = ?, Weight = ? WHERE ChoiceID = ?";
    private static final String DELETE_QUESTION_CHOICE_SQL = "DELETE FROM QuizQuestionChoice WHERE ChoiceID = ?";
    private static final String GET_ALL_QUESTION_CHOICES_SQL = "SELECT * FROM QuizQuestionChoice";
    private static final String GET_ALL_QUESTION_CHOICES_BY_QUESTION_ID_SQL = "SELECT * FROM QuizQuestionChoice WHERE QuestionID = ?";

    /**
     * Create a new choice for the question
     *
     * @param quizQuestionChoice
     * @return QuizQuestionChoice
     */
    public int createQuestionChoice(QuizQuestionChoice quizQuestionChoice) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUESTION_CHOICE_SQL);
            preparedStatement.setString(1, quizQuestionChoice.getQuestionId());
            preparedStatement.setString(2, quizQuestionChoice.getChoiceText());
            preparedStatement.setBoolean(3, quizQuestionChoice.isCorrect());
            preparedStatement.setFloat(4, quizQuestionChoice.getWeight());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getCorrectAnswerNumbers(String questionID) {
        ArrayList<QuizQuestionChoice> quizQuestionChoices = (ArrayList<QuizQuestionChoice>) new DAOQuizQuestionChoice().getAllByQuestionId(questionID);
        int count = 0;
        for (QuizQuestionChoice quizQuestionChoice : quizQuestionChoices) {
            if (quizQuestionChoice.isCorrect()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Read the question choice based on the choiceid
     *
     * @param choiceId
     * @return QuizQuestionChoice
     */
    public QuizQuestionChoice getQuestionChoiceByID(int choiceId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_QUESTION_CHOICE_BY_ID_SQL);
            preparedStatement.setInt(1, choiceId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new QuizQuestionChoice(
                        rs.getInt("ChoiceID"),
                        rs.getString("QuestionID"),
                        rs.getString("ChoiceText"),
                        rs.getBoolean("IsCorrect"),
                        rs.getFloat("Weight")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update QuizQuestionChoice based on ChoiceID
     *
     * @param quizQuestionChoice
     * @return QuizQuestionChoice if updated successfully or null if fault
     */
    public int updateQuestionChoice(QuizQuestionChoice quizQuestionChoice) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUESTION_CHOICE_SQL);
            preparedStatement.setString(1, quizQuestionChoice.getQuestionId());
            preparedStatement.setString(2, quizQuestionChoice.getChoiceText());
            preparedStatement.setBoolean(3, quizQuestionChoice.isCorrect());
            preparedStatement.setFloat(4, quizQuestionChoice.getWeight());
            preparedStatement.setInt(5, quizQuestionChoice.getChoiceId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Delete the choice from the quiz question
     *
     * @param choiceId
     */
    public int deleteQuestionChoiceByID(int choiceId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUESTION_CHOICE_SQL);
            preparedStatement.setInt(1, choiceId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteQuestionChoiceByQuestionID(String questionID) {
        try {
            new DAOQuizResultAnswer().deleteQuizResultAnswerByQuestionID(questionID);
            PreparedStatement preparedStatement = connection.prepareStatement("Delete from QuizQuestionChoice where QuestionID = ?");
            preparedStatement.setString(1, questionID);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * List all the quiz question choice from the database
     *
     * @return QuizQuestionChoice
     */
    public List<QuizQuestionChoice> getAll() {
        List<QuizQuestionChoice> quizQuestionChoices = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUESTION_CHOICES_SQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                QuizQuestionChoice quizQuestionChoice = new QuizQuestionChoice(
                        rs.getInt("ChoiceID"),
                        rs.getString("QuestionID"),
                        rs.getString("ChoiceText"),
                        rs.getBoolean("IsCorrect"),
                        rs.getFloat("Weight")
                );
                quizQuestionChoices.add(quizQuestionChoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizQuestionChoices;
    }

    /**
     * List all the choice for the question based on the questionID
     *
     * @param questionID
     * @return QuizQuestionChoice
     */
    public List<QuizQuestionChoice> getAllByQuestionId(String questionID) {
        List<QuizQuestionChoice> quizQuestionChoices = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUESTION_CHOICES_BY_QUESTION_ID_SQL);
            preparedStatement.setString(1, questionID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                QuizQuestionChoice quizQuestionChoice = new QuizQuestionChoice(
                        rs.getInt("ChoiceID"),
                        rs.getString("QuestionID"),
                        rs.getString("ChoiceText"),
                        rs.getBoolean("IsCorrect"),
                        rs.getFloat("Weight")
                );
                quizQuestionChoices.add(quizQuestionChoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizQuestionChoices;
    }

}