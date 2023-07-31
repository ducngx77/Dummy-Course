package dao;

import entity.course.Course;
import entity.quiz.QuizResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOQuizResult extends DBContext {
    public static final String GET_QUIZ_RESULT_BY_QUIZ_ID_AND_STUDENT_ID_SQL = "SELECT * FROM QuizResult WHERE QuizID = ? AND StudentID = ?";
    public static final String GET_NUMBER_OF_LAST_12HOURS_TESTS_SQL = "SELECT dbo.GetQuizzesDoneByStudentForTest(?, ?) AS QuizzesDone";
    private static final String CREATE_QUIZ_RESULT_SQL = "INSERT INTO QuizResult (ResultID, QuizID, StudentID, Score, TestTime) VALUES (?, ?, ?, ?, ?)";
    private static final String READ_QUIZ_RESULT_SQL = "SELECT * FROM QuizResult WHERE ResultID = ?";
    private static final String UPDATE_QUIZ_RESULT_SQL = "UPDATE QuizResult SET QuizID = ?, StudentID = ?, Score = ?, TestTime = ? WHERE ResultID = ?";
    private static final String DELETE_QUIZ_RESULT_SQL = "DELETE FROM QuizResult WHERE ResultID = ?";
    private static final String GET_ALL_QUIZ_RESULT_SQL = "SELECT * FROM QuizResult";
    private static final String GET_ALL_QUIZ_RESULT_BY_STUDENT_ID_SQL = "SELECT  QuizID, count(QuizID) as Number, max(ResultID) as ResultID  from QuizResult where StudentID = ? group by QuizID";
    private static final String GET_ALL_QUIZ_RESULT_BY_QUIZ_ID_SQL = "SELECT dbo.GetQuizzesDoneByStudentForTest(?, ?) AS QuizzesDone;";
    private static final String GET_USER_QUIZ_RESULTS_SQL = "Select * from QuizResult where StudentID = ? And QuizID = ?";
    private static final String GET_FINAL_QUIZ_RESULT_ID = "SELECT MAX(ResultID) FROM QuizResult";
    private static final String GET_ATTEMPTS_FORM_LAST_N_HOURS_SQL = "SELECT * from GetQuizResultCountInLastNHours(?)";
    private static final String GET_ALL_QUIZ_RESULTS_BY_STUDENT_ID_AND_QUIZ_ID = "SELECT * FROM QuizResult WHERE StudentID = ? AND QuizID = ?";


    public static void main(String[] args) {
        ResultSet rs = new DAOQuizResult().getAllQuizResultByStudentID(5);
        try {
            while (rs.next()) {
                System.out.println(rs.getString("QuizID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<QuizResult> getAllQuizResultByStudentIDAbdQuizID(int studentID, String quizID) {
        ArrayList<QuizResult> quizResults = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUIZ_RESULTS_BY_STUDENT_ID_AND_QUIZ_ID)) {
            preparedStatement.setInt(1, studentID);
            preparedStatement.setString(2, quizID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                quizResults.add(new QuizResult(
                        rs.getString("ResultID"),
                        rs.getString("QuizID"),
                        rs.getInt("StudentID"),
                        rs.getInt("Score"),
                        rs.getString("TestTime")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizResults;
    }

    public int getAttemptsFromLastNHours(int hours) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ATTEMPTS_FORM_LAST_N_HOURS_SQL)) {
            preparedStatement.setInt(1, hours);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public int getFinalQuizResultId() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_FINAL_QUIZ_RESULT_ID)) {
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

    public ArrayList<QuizResult> getQuizResultsByStudentID(int studentID, String quizID) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_QUIZ_RESULTS_SQL)) {
            preparedStatement.setInt(1, studentID);
            preparedStatement.setString(2, quizID);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<QuizResult> quizResults = new ArrayList<>();
            while (rs.next()) {
                quizResults.add(new QuizResult(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getFloat(4), rs.getString(5)));
            }
            return quizResults;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getNumberOfLastHalfdayAttemps(int userID, String testID) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_NUMBER_OF_LAST_12HOURS_TESTS_SQL)) {
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, testID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("QuizzesDone");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public String getQuizResultID(String quizID, int userID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_QUIZ_RESULT_BY_QUIZ_ID_AND_STUDENT_ID_SQL);
            preparedStatement.setString(1, quizID);
            preparedStatement.setInt(2, userID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString("ResultID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * create new QuizResult in database
     *
     * @param quizResult
     * @return QuizResult
     */
    public int createQuizResult(QuizResult quizResult) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUIZ_RESULT_SQL);
            preparedStatement.setString(1, quizResult.getResultId());
            preparedStatement.setString(2, quizResult.getQuizId());
            preparedStatement.setInt(3, quizResult.getStudentID());
            preparedStatement.setFloat(4, quizResult.getScore());
            preparedStatement.setString(5, quizResult.getTestTime());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Read QuizResult based on id
     *
     * @param quizResultID
     * @return QuizResult
     */
    public QuizResult readQuizResult(String quizResultID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_QUIZ_RESULT_SQL);
            preparedStatement.setString(1, quizResultID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new QuizResult(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getFloat(4), rs.getString(5));
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * edit the QuizResult in database
     *
     * @param quizResult
     * @return QuizResult
     */
    public int updateQuizResult(QuizResult quizResult) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUIZ_RESULT_SQL);
            preparedStatement.setString(1, quizResult.getQuizId());
            preparedStatement.setInt(2, quizResult.getStudentID());
            preparedStatement.setFloat(3, quizResult.getScore());
            preparedStatement.setString(4, quizResult.getTestTime());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * delete QuizResult in the database
     *
     * @param quizResultID
     */
    public int deleteQuizResult(String quizResultID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUIZ_RESULT_SQL);
            preparedStatement.setString(1, quizResultID);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }



    /**
     * get all QuizResult
     *
     * @return list of QuizResult
     */
    public List<QuizResult> getAllQuizResult() {
        List<QuizResult> quizResults = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUIZ_RESULT_SQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                QuizResult quizResult = new QuizResult(
                        rs.getString("ResultID"),
                        rs.getString("QuizID"),
                        rs.getInt("StudentID"),
                        rs.getFloat("Score"),
                        rs.getString("TestTime")
                );
                quizResults.add(quizResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizResults;
    }

    public ResultSet getAllQuizResultByStudentID(int studentID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUIZ_RESULT_BY_STUDENT_ID_SQL);
            preparedStatement.setInt(1, studentID);
            ResultSet rs = preparedStatement.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get QuizResult based on ID
     *
     * @param quizID
     * @return QuizResult
     */
    public List<QuizResult> getAllByQuizId(String quizID) {
        List<QuizResult> quizResults = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUIZ_RESULT_BY_QUIZ_ID_SQL);
            preparedStatement.setString(1, quizID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                QuizResult quizResult = new QuizResult(
                        rs.getString("ResultID"),
                        rs.getString("QuizID"),
                        rs.getInt("StudentID"),
                        rs.getFloat("Score"),
                        rs.getString("TestTime")
                );
                quizResults.add(quizResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizResults;
    }

}
