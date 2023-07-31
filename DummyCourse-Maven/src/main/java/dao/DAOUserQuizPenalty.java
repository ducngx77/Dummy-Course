package dao;

import entity.user.UserQuizPenalty;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class DAOUserQuizPenalty extends DBContext {

    //PenaltyID INT IDENTITY(1,1) PRIMARY KEY,
    //    UserID INT,
    //    QuizID varchar(32),
    //    penaltyStartTime DATETIME,
    //    penaltyDuration INT,
    //    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    //    FOREIGN KEY (QuizID) REFERENCES Quiz(QuizID)

    private static final String INSERT_USER_QUIZ_PENALTY = "INSERT INTO UserQuizPenalty(UserID, QuizID, penaltyStartTime, penaltyDuration) VALUES(?,?,?,?)";
    private static final String UPDATE_USER_QUIZ_PENALTY = "UPDATE UserQuizPenalty SET penaltyStartTime = ?, penaltyDuration = ? WHERE UserID = ? AND QuizID = ?";
    private static final String DELETE_USER_QUIZ_PENALTY = "DELETE FROM UserQuizPenalty WHERE UserID = ? AND QuizID = ?";
    private static final String SELECT_USER_QUIZ_PENALTY = "SELECT * FROM UserQuizPenalty WHERE UserID = ? AND QuizID = ?";
    private static final String SELECT_USER_QUIZ_PENALTY_BY_USER_ID = "SELECT * FROM UserQuizPenalty WHERE UserID = ?";
    private static final String SELECT_USER_QUIZ_PENALTY_BY_QUIZ_ID = "SELECT * FROM UserQuizPenalty WHERE QuizID = ?";
    private static final String SELECT_ALL_USER_QUIZ_PENALTY = "SELECT * FROM UserQuizPenalty";

    public static void main(String[] args) {
        //Test all methods form this dao
        // Test add
        System.out.println("Test add");
        DAOUserQuizPenalty daoUserQuizPenalty = new DAOUserQuizPenalty();
        System.out.println(daoUserQuizPenalty.insertUserQuizPenalty(new UserQuizPenalty(5, "quiz1", new Date(), 3)) + " row(s) inserted");
        // Test get by userID
        System.out.println("Test get by userID");
        System.out.println(daoUserQuizPenalty.getUserQuizPenalty(5, "quiz1"));
        // Test update
        System.out.println("Test update");
        System.out.println(daoUserQuizPenalty.updateUserQuizPenalty(new UserQuizPenalty(5, "quiz1", new Date(), 5)) + " row(s) updated");
        // Test get by quizID
        System.out.println("Test get by quizID");
        System.out.println(daoUserQuizPenalty.getUserQuizPenaltyByQuizID("quiz1"));
        // Test get all
        System.out.println("Test get all");
        System.out.println(daoUserQuizPenalty.getAllUserQuizPenalty());
        // Test delete
        System.out.println("Test delete");
        System.out.println(daoUserQuizPenalty.deleteUserQuizPenalty(5, "quiz1") + " row(s) deleted");
    }

    public ArrayList<UserQuizPenalty> getUserQuizPenaltyByQuizID(String quizID) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_QUIZ_PENALTY_BY_QUIZ_ID)) {
            preparedStatement.setString(1, quizID);
            return getUserQuizPenalties(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<UserQuizPenalty> getUserQuizPenalties(PreparedStatement preparedStatement) throws SQLException {
        ArrayList<UserQuizPenalty> userQuizPenalties = new ArrayList<>();
        var resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            userQuizPenalties.add(new UserQuizPenalty(
                    resultSet.getInt("PenaltyID"),
                    resultSet.getInt("UserID"),
                    resultSet.getString("QuizID"),
                    resultSet.getDate("penaltyStartTime"),
                    resultSet.getInt("penaltyDuration")
            ));
        }
        return userQuizPenalties;
    }

    public ArrayList<UserQuizPenalty> getUserQUizPenaltyByUserID(int userID) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_QUIZ_PENALTY_BY_USER_ID)) {
            preparedStatement.setInt(1, userID);
            return getUserQuizPenalties(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<UserQuizPenalty> getAllUserQuizPenalty() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USER_QUIZ_PENALTY)) {
            return getUserQuizPenalties(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public UserQuizPenalty getUserQuizPenalty(int userID, String quizID) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_QUIZ_PENALTY)) {
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, quizID);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new UserQuizPenalty(
                        resultSet.getInt("PenaltyID"),
                        resultSet.getInt("UserID"),
                        resultSet.getString("QuizID"),
                        resultSet.getDate("penaltyStartTime"),
                        resultSet.getInt("penaltyDuration")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int deleteUserQuizPenalty(int userID, String quizID) {
        return userIDQuizIDActions(userID, quizID, DELETE_USER_QUIZ_PENALTY);
    }

    private int userIDQuizIDActions(int userID, String quizID, String deleteUserQuizPenalty) {
        try {
            PreparedStatement ps = connection.prepareStatement(deleteUserQuizPenalty);
            ps.setInt(1, userID);
            ps.setString(2, quizID);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int updateUserQuizPenalty(UserQuizPenalty userQuizPenalty) {
        try {
            PreparedStatement ps = connection.prepareStatement(UPDATE_USER_QUIZ_PENALTY);
            ps.setDate(1, new java.sql.Date(userQuizPenalty.getPenaltyStartTime().getTime()));
            ps.setInt(2, userQuizPenalty.getPenaltyDuration());
            ps.setInt(3, userQuizPenalty.getUserID());
            ps.setString(4, userQuizPenalty.getQuizID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int insertUserQuizPenalty(UserQuizPenalty userQuizPenalty) {
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_USER_QUIZ_PENALTY);
            ps.setInt(1, userQuizPenalty.getUserID());
            ps.setString(2, userQuizPenalty.getQuizID());
            ps.setDate(3, new java.sql.Date(userQuizPenalty.getPenaltyStartTime().getTime()));
            ps.setInt(4, userQuizPenalty.getPenaltyDuration());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
