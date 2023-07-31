package dao;

import entity.quiz.QuizQuestion;
import entity.quiz.QuizQuestionChoice;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOQuizQuestion extends DBContext {
    /*
    CREATE TABLE QuizQuestionChoice (
                                    ChoiceID INT IDENTITY(1,1) PRIMARY KEY,
                                    QuestionID INT,
                                    ChoiceText VARCHAR(255),
                                    IsCorrect BIT,
                                    Weight FLOAT,
                                    FOREIGN KEY (QuestionID) REFERENCES QuizQuestion(QuestionID)
);
     */

    public static final String GET_ALL_QUESTION_CHOICE_SQL = "SELECT * FROM QuizQuestionChoice WHERE QuestionID = ?";
    public static final String GET_QUIZ_QUESTION_BY_ID_SQL = "SELECT * FROM QuizQuestion WHERE QuestionID = ?";
    public static final String GET_TOTAL_WEIGHT_SQL = "SELECT SUM(Weight) AS TotalWeight FROM QuizQuestionChoice WHERE QuestionID = ?";


    public static void main(String[] args) {
        DAOQuizQuestion dao = new DAOQuizQuestion();
        System.out.println(dao.getTotalWeightById("question1"));
    }

    private static ArrayList<QuizQuestion> getQuizQuestions(ResultSet rs) throws SQLException {
        ArrayList<QuizQuestion> quizQuestions = new ArrayList<>();
        while (rs.next()) {
            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setQuestionID(rs.getString("QuestionID"));
            quizQuestion.setQuizID(rs.getString("QuizID"));
            quizQuestion.setQuestionText(rs.getString("QuestionText"));
            quizQuestion.setExplanation(rs.getString("Explanation"));
            quizQuestion.setPicture(rs.getBytes("Picture"));
            quizQuestions.add(quizQuestion);
        }
        return quizQuestions;
    }


    public double getTotalWeightById(String quizQuestionId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_TOTAL_WEIGHT_SQL)) {
            preparedStatement.setString(1, quizQuestionId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("TotalWeight");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private QuizQuestion getQuizQuestion(ResultSet rs) throws SQLException {
        if (rs.next()) {
            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setQuestionID(rs.getString("QuestionID"));
            quizQuestion.setQuizID(rs.getString("QuizID"));
            quizQuestion.setQuestionText(rs.getString("QuestionText"));
            quizQuestion.setExplanation(rs.getString("Explanation"));
            quizQuestion.setPicture(rs.getBytes("Picture"));
            return quizQuestion;
        }
        return null;
    }

    public ArrayList<QuizQuestionChoice> getAllQuestionChoice(String questionID) {
        try (PreparedStatement ps = connection.prepareStatement(GET_ALL_QUESTION_CHOICE_SQL)) {
            ps.setString(1, questionID);
            ResultSet rs = ps.executeQuery();
            ArrayList<QuizQuestionChoice> quizQuestionChoices = new ArrayList<>();
            while (rs.next()) {
                QuizQuestionChoice quizQuestionChoice = new QuizQuestionChoice();
                quizQuestionChoice.setChoiceId(rs.getInt("ChoiceID"));
                quizQuestionChoice.setQuestionId(rs.getString("QuestionID"));
                quizQuestionChoice.setChoiceText(rs.getString("ChoiceText"));
                quizQuestionChoice.setCorrect(rs.getBoolean("IsCorrect"));
                quizQuestionChoice.setWeight(rs.getFloat("Weight"));
                quizQuestionChoices.add(quizQuestionChoice);
            }
            return quizQuestionChoices;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public QuizQuestion getQuizQuestionByID(String questionID) {
        String sql = "SELECT * FROM QuizQuestion WHERE QuestionID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, questionID);
            ResultSet rs = preparedStatement.executeQuery();
            QuizQuestion quizQuestion = getQuizQuestion(rs);
            if (quizQuestion != null) return quizQuestion;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<QuizQuestion> getQuizQuestionsByQuizID(String quizID) {
        String sql = "SELECT * FROM QuizQuestion WHERE QuizID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, quizID);
            ResultSet rs = preparedStatement.executeQuery();
            return getQuizQuestions(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public QuizQuestion getNewestQuizQuestion() {
        String sql = "SELECT *\n" +
                "FROM QuizQuestion\n" +
                "WHERE QuestionID = (\n" +
                "  SELECT 'question' + CAST(MAX(CAST(SUBSTRING(QuestionID, 9, LEN(QuestionID)) AS INT)) AS VARCHAR(50))\n" +
                "  FROM QuizQuestion\n" +
                "  WHERE QuestionID LIKE 'question%'\n" +
                ")";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            QuizQuestion quizQuestion = getQuizQuestion(rs);
            if (quizQuestion != null) return quizQuestion;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all quiz questions from the database.
     *
     * @return a list of all QuizQuestion objects
     */
    public List<QuizQuestion> getAllQuizQuestions() {
        String sql = "SELECT * FROM QuizQuestion";
        List<QuizQuestion> quizQuestions = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.setQuestionID(rs.getString("QuestionID"));
                quizQuestion.setQuizID(rs.getString("QuizID"));
                quizQuestion.setQuestionText(rs.getString("QuestionText"));
                quizQuestion.setExplanation(rs.getString("Explanation"));
                quizQuestion.setPicture(rs.getBytes("Picture"));
                quizQuestions.add(quizQuestion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizQuestions;
    }
    public List<QuizQuestion> getAllQuizQuestionsInCourseFilterBySection(String typeName, int sectionID) {
        String sql = "SELECT qq.*\n" +
                "FROM QuizQuestion qq\n" +
                "INNER JOIN Quiz q ON qq.QuizID = q.QuizID\n" +
                "INNER JOIN Courses c ON q.CourseID = c.CourseID\n" +
                "INNER JOIN CourseType ct ON c.CourseTypeID = ct.CourseTypeID\n" +
                "WHERE ct.TypeName = '" + typeName +"' And sectionID=" + sectionID;
        List<QuizQuestion> quizQuestions = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.setQuestionID(rs.getString("QuestionID"));
                quizQuestion.setQuizID(rs.getString("QuizID"));
                quizQuestion.setQuestionText(rs.getString("QuestionText"));
                quizQuestion.setExplanation(rs.getString("Explanation"));
                quizQuestion.setPicture(rs.getBytes("Picture"));
                quizQuestions.add(quizQuestion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizQuestions;
    }

    public String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }

    /**
     * Creates a new course in the database.
     *
     * @param quizQuestion the QuizQuestion object to be created
     * @return number of records inserted or -1 if an error occurs during execution
     */
    public String generateQuestionID(){
        DAOQuizQuestion daoQQ = new DAOQuizQuestion();
        QuizQuestion quizQuestion = daoQQ.getNewestQuizQuestion();
        String questionID = quizQuestion.getQuestionID();
        int number = Integer.parseInt(questionID.split("n")[1]) + 1;
        return "question" + (number + "");
    }
    public int createQuizQuestion(QuizQuestion quizQuestion) {
        String sql = "INSERT INTO QuizQuestion (QuestionID ,QuizID, QuestionText, Explanation, Picture) VALUES (?,?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, generateQuestionID());
            preparedStatement.setString(2, quizQuestion.getQuizID());
            preparedStatement.setString(3, quizQuestion.getQuestionText());
            preparedStatement.setString(4, quizQuestion.getExplanation());
            preparedStatement.setBytes(5, quizQuestion.getPicture());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * Updates an existing quiz question in the database.
     *
     * @param quizQuestion the QuizQuestion object to be updated
     */
    public void updateQuizQuestion(QuizQuestion quizQuestion) {
        String sql = "UPDATE QuizQuestion SET QuizID = ?, QuestionText = ?, Explanation = ?, Picture = ? WHERE QuestionID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, quizQuestion.getQuizID());
            preparedStatement.setString(2, quizQuestion.getQuestionText());
            preparedStatement.setString(3, quizQuestion.getExplanation());
            preparedStatement.setBytes(4, quizQuestion.getPicture());
            preparedStatement.setString(5, quizQuestion.getQuestionID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a quiz question from the database based on the question ID.
     *
     * @param questionID the ID of the question to delete
     */
    public void deleteQuizQuestion(String questionID) {
        String sql = "DELETE FROM QuizQuestion WHERE QuestionID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, questionID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
