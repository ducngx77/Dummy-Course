package dao;

import entity.course.Course;
import entity.quiz.Quiz;
import entity.quiz.QuizQuestion;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The QuizDAO class provides data access methods for the Quiz table.
 */
public class DAOQuiz extends DBContext {
    private final String GET_QUIZ_QUESTIONS = "SELECT * FROM QuizQuestion WHERE QuizID = ?";
    public double getTotalQuizQuestionWeight(String quizID) {
        ArrayList<QuizQuestion> quizQuestions = getAllQuizQuestions(quizID);
        double totalWeight = 0;
        for (QuizQuestion quizQuestion : quizQuestions) {
            totalWeight += new DAOQuizQuestion().getTotalWeightById(quizQuestion.getQuestionID());
        }
        return totalWeight;
    }

    public ArrayList<QuizQuestion> getAllQuizQuestions(String quizID) {
        try (PreparedStatement ps = connection.prepareStatement(GET_QUIZ_QUESTIONS)) {
            ps.setString(1, quizID);
            ResultSet rs = ps.executeQuery();
            ArrayList<QuizQuestion> quizQuestions = new ArrayList<>();
            while (rs.next()) {
                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.setQuizID(rs.getString("QuizID"));
                quizQuestion.setQuestionID(rs.getString("QuestionID"));
                quizQuestion.setQuestionText(rs.getString("QuestionText"));
                quizQuestion.setExplanation(rs.getString("Explanation"));
                quizQuestion.setPicture(rs.getBytes("Picture"));
                quizQuestions.add(quizQuestion);
            }
            return quizQuestions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a quiz from the database based on the quiz ID.
     *
     * @param quizID the ID of the quiz to retrieve
     * @return the Quiz object, or null if not found
     */
    public Quiz getQuizByID(String quizID) {
        String sql = "SELECT * FROM Quiz WHERE QuizID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, quizID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizID(rs.getString("QuizID"));
                quiz.setCourseID(rs.getInt("CourseID"));
                quiz.setSectionID(rs.getInt("SectionID"));
                quiz.setQuizName(rs.getString("QuizName"));
                quiz.setStartTime(rs.getTimestamp("StartTime").toLocalDateTime());
                quiz.setDuration(rs.getTime("Duration").toLocalTime());
                quiz.setStatus(rs.getInt("Status"));
                return quiz;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all quizzes from the database.
     *
     * @return a list of all Quiz objects
     */
    public List<Quiz> getAllQuizzes() {
        String sql = "SELECT * FROM Quiz";
        List<Quiz> quizzes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizID(rs.getString("QuizID"));
                quiz.setCourseID(rs.getInt("CourseID"));
                quiz.setSectionID(rs.getInt("SectionID"));
                quiz.setQuizName(rs.getString("QuizName"));
                quiz.setStartTime(rs.getTimestamp("StartTime").toLocalDateTime());
                quiz.setDuration(rs.getTime("Duration").toLocalTime());
                quiz.setStatus(rs.getInt("Status"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public List<Quiz> getAllQuizzesByCourseID(int courseID) {
        String sql = "SELECT * FROM Quiz where CourseID=" + courseID;
        List<Quiz> quizzes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizID(rs.getString("QuizID"));
                quiz.setCourseID(rs.getInt("CourseID"));
                quiz.setSectionID(rs.getInt("SectionID"));
                quiz.setQuizName(rs.getString("QuizName"));
                quiz.setStartTime(rs.getTimestamp("StartTime").toLocalDateTime());
                quiz.setDuration(rs.getTime("Duration").toLocalTime());
                quiz.setStatus(rs.getInt("Status"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    /**
     * Creates a new quiz in the database.
     *
     * @param quiz the Quiz object to be created
     * @return the number of rows affected (1 if successful, -1 if an error occurred)
     */
    public int createQuiz(Quiz quiz) {
        String sql = "INSERT INTO Quiz (QuizID,CourseID, SectionID, QuizName, StartTime, Duration, Status) VALUES (?,?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, quiz.getQuizID());
            preparedStatement.setInt(2, quiz.getCourseID());
            preparedStatement.setInt(3, quiz.getSectionID());
            preparedStatement.setString(4, quiz.getQuizName());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(quiz.getStartTime()));
            preparedStatement.setTime(6, Time.valueOf(quiz.getDuration()));
            preparedStatement.setInt(7, quiz.getStatus());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Updates an existing quiz in the database.
     *
     * @param quiz the Quiz object to be updated
     */
    public void updateQuiz(Quiz quiz) {
        String sql = "UPDATE Quiz SET CourseID = ?, SectionID =?, QuizName = ?, StartTime = ?, Duration = ?, Status = ? WHERE QuizID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, quiz.getCourseID());
            preparedStatement.setInt(2, quiz.getSectionID());
            preparedStatement.setString(3, quiz.getQuizName());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(quiz.getStartTime()));
            preparedStatement.setTime(5, Time.valueOf(quiz.getDuration()));
            preparedStatement.setInt(6, quiz.getStatus());
            preparedStatement.setString(7, quiz.getQuizID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a quiz from the database based on the quiz ID.
     *
     * @param quizID the ID of the quiz to delete
     */
    public void deleteQuiz(String quizID) {
        String sql = "DELETE FROM Quiz WHERE QuizID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, quizID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Quiz getQuizBySectionAndCourse(int courseID, int sectionID) {
        String sql = "SELECT * FROM Quiz WHERE CourseID=? and SectionID=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, courseID);
            preparedStatement.setInt(2, sectionID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizID(rs.getString("QuizID"));
                quiz.setCourseID(rs.getInt("CourseID"));
                quiz.setSectionID(rs.getInt("SectionID"));
                quiz.setQuizName(rs.getString("QuizName"));
                quiz.setStartTime(rs.getTimestamp("StartTime").toLocalDateTime());
                quiz.setDuration(rs.getTime("Duration").toLocalTime());
                quiz.setStatus(rs.getInt("Status"));
                return quiz;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Quiz getNewestQuiz() {
        String sql = "select * from Quiz order by QuizID desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizID(rs.getString("QuizID"));
                quiz.setCourseID(rs.getInt("CourseID"));
                quiz.setSectionID(rs.getInt("SectionID"));
                quiz.setQuizName(rs.getString("QuizName"));
                quiz.setStartTime(rs.getTimestamp("StartTime").toLocalDateTime());
                quiz.setDuration(rs.getTime("Duration").toLocalTime());
                quiz.setStatus(rs.getInt("Status"));
                return quiz;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String generateNewQuizID(){
        String result = "";
        String sql = "Select COUNT(*) as Amount from Quiz";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            if(rs.next()){
                result = "quiz" + (rs.getInt(1) + 1 + "");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public void updateDuration(long mduration, long sduration,String quizID) {
        LocalTime lt = LocalTime.of(0,0,0);
        lt = lt.plusMinutes(mduration);
        lt = lt.plusSeconds(sduration);
        String sql = "UPDATE Quiz SET Duration = ? WHERE QuizID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTime(1, Time.valueOf(lt));
            preparedStatement.setString(2, quizID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStartTime(LocalDateTime ldt, String quizID) {
        String sql = "UPDATE Quiz SET StartTime = ? WHERE QuizID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(ldt));
            preparedStatement.setString(2, quizID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Course getCourseByQuizID(String quizId){
        DAOCourse dc = new DAOCourse();
        return dc.getCourseByID(getQuizByID(quizId).getCourseID());
    }
}