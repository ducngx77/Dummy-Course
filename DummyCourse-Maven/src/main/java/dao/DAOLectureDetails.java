package dao;

import entity.user.LectureDetails;
import entity.user.User;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAOLectureDetails extends DBContext {
    private final String GET_ALL_LECTURER_SQL = "SELECT * FROM LectureDetails";
    private final String GET_LECTURER_BY_USER_ID_SQL = "SELECT * FROM LectureDetails WHERE userID = ?";
    private final String INSERT_LECTURER_SQL = "INSERT INTO LectureDetails(userID, lectureDescription, lectureProfile, accepted) VALUES(?, ?, ?, ?)";
    private final String UPDATE_LECTURER_SQL = "UPDATE LectureDetails SET userID = ?, lectureDescription = ?, lectureProfile = ?, accepted = ? WHERE lectureID = ?";
    private final String ACTIVATE_LECTURER_SQL = "UPDATE LectureDetails SET accepted = 1 WHERE userID = ?";
    private final String DELETE_LECTURER_SQL = "DELETE FROM LectureDetails WHERE lectureID = ?";

    private final String GET_USER_BY_LECTURER_ID_SQL = "SELECT u.* FROM Users u\n" +
            "JOIN LectureDetails ld ON u.UserID = ld.UserID\n" +
            "WHERE ld.LectureID = ?";
    private final String GET_AVERAGE_RATING_BY_LECTURER_ID_SQL = "SELECT AVG(rating) AS averageRating FROM lecture_reviews WHERE lecture_id = ?";
    private final String UPDATE_LECTURER_DETAILS_SQL = "UPDATE LectureDetails SET  lectureDescription = ?, lectureProfile = ? WHERE UserID = ?";
    private final String SEARCH_lECTURER_BY_KEY_SQL = "SELECT * FROM Users U, LectureDetails LD WHERE Username LIKE ? and U.UserID = LD.UserID";

    public static void main(String[] args) {
        System.out.println(new DAOLectureDetails().getUserByLecturerID(1));

//        // TEST ALL METHODS
//        // Test updateLecturer
//        System.out.println("- Update lecturer");
//        LectureDetails lectureDetails = new LectureDetails();
//        lectureDetails.setLectureID(1);
//        lectureDetails.setUserID(3);
//        lectureDetails.setLectureDescription("I am a lecturer (Edited)");
//        lectureDetails.setLectureProfile("I am a lecturer (Edited)".getBytes());
//        lectureDetails.setAccepted(true);
//        System.out.println(new DAOLectureDetails().updateLecturer(lectureDetails) + " row(s) affected");
//        // Test insertLecturer
//        System.out.println("- Insert lecturer");
//        lectureDetails = new LectureDetails();
//        lectureDetails.setUserID(6);
//        lectureDetails.setLectureDescription("I am a lecturer");
//        lectureDetails.setLectureProfile("I am a lecturer".getBytes());
//        lectureDetails.setAccepted(true);
//        System.out.println(new DAOLectureDetails().insertLecturer(lectureDetails) + " row(s) affected");
//        // Test deleteLecturer
//        System.out.println("- Delete lecturer");
//        System.out.println(new DAOLectureDetails().deleteLecturer(3) + " row(s) affected");
//        // Test getLecturerByUserID
//        System.out.println("- Get lecturer by userID");
//        System.out.println(new DAOLectureDetails().getLectureByUserID(1));
//        // Test getAllLecturer
//        System.out.println("- Get all lecturer");
//        System.out.println(new DAOLectureDetails().getAllLecturers());
//        // Test getUserByLecturerID
//        System.out.println("- Get user by lecturerID");
//        System.out.println(new DAOLectureDetails().getUserByLecturerID(1));

    }

    public double getAverageRating(int lecturerID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_AVERAGE_RATING_BY_LECTURER_ID_SQL);
            preparedStatement.setInt(1, lecturerID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("averageRating");
            }
        } catch (Exception e) {
            System.out.println("Error at DAOLectureDetails.getAverageRating: " + e.getMessage());
        }
        return 0;
    }

    public User getUserByLecturerID(int lecturerID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_LECTURER_ID_SQL);
            preparedStatement.setInt(1, lecturerID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("Password"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setBio(rs.getString("bio"));
                user.setAddress(rs.getString("Address"));
                user.setGender(rs.getInt("gender"));
                return user;
            }
        } catch (Exception e) {
            System.out.println("Error at DAOLectureDetails.getUserByLecturerID: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<LectureDetails> searchLectuerByName(String keyword) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_lECTURER_BY_KEY_SQL);
            preparedStatement.setString(1, "%" + keyword + "%");
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<LectureDetails> lecturers = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("Password"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setBio(rs.getString("bio"));
                user.setAddress(rs.getString("Address"));
                user.setGender(rs.getInt("gender"));
                lecturers.add(getLectureByUserID(user.getUserID()));
            }
            return lecturers;
        } catch (Exception e) {
            System.out.println("Error at DAOLectureDetails.searchLectuerByName: " + e.getMessage());
        }
        return null;
    }

    /**
     * Update lecturer by the param lectureDetails
     * The lecturer will be updated based on the lectureID
     *
     * @param lectureDetails The lectureDetails to be updated
     * @return The number of rows affected
     */
    public int updateLecturer(LectureDetails lectureDetails) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LECTURER_SQL);
            preparedStatement.setInt(1, lectureDetails.getUserID());
            preparedStatement.setString(2, lectureDetails.getLectureDescription());
            preparedStatement.setBytes(3, lectureDetails.getLectureProfile());
            preparedStatement.setBoolean(4, lectureDetails.isAccepted());
            preparedStatement.setInt(5, lectureDetails.getLectureID());
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error at DAOLectureDetails.updateLecturer: " + e.getMessage());
        }
        return 0;
    }

    public int activateLecturer(int userID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ACTIVATE_LECTURER_SQL);
            preparedStatement.setInt(1, userID);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error at DAOLectureDetails.activateLecturer: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Delete lecturer by the param lectureID
     *
     * @param lectureID The lectureID to be deleted
     * @return The number of rows affected
     */
    public int deleteLecturer(int lectureID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_LECTURER_SQL);
            preparedStatement.setInt(1, lectureID);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error at DAOLectureDetails.deleteLecturer: " + e.getMessage());
        }
        return 0;
    }


    /**
     * Insert lecturer by the param lectureDetails
     *
     * @param lectureDetails The lectureDetails to be inserted
     * @return The number of rows affected
     */
    public int insertLecturer(LectureDetails lectureDetails) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LECTURER_SQL);
            preparedStatement.setInt(1, lectureDetails.getUserID());
            preparedStatement.setString(2, lectureDetails.getLectureDescription());
            preparedStatement.setBytes(3, lectureDetails.getLectureProfile());
            preparedStatement.setBoolean(4, lectureDetails.isAccepted());
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error at DAOLectureDetails.insertLecturer: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Get lecturer by the param userID
     * The lecturer will be retrieved based on the userID
     *
     * @param userID The userID to be retrieved
     * @return The lecturer retrieved
     */
    public LectureDetails getLectureByUserID(int userID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_LECTURER_BY_USER_ID_SQL);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new LectureDetails(
                        resultSet.getInt("lectureID"),
                        resultSet.getInt("userID"),
                        resultSet.getString("lectureDescription"),
                        resultSet.getBytes("lectureProfile"),
                        resultSet.getBoolean("accepted")
                );
            }
        } catch (Exception e) {
            System.out.println("Error at DAOLectureDetails.getLectureByUserID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all lecturers
     * The lecturers will be retrieved from the database and returned as an ArrayList
     *
     * @return The ArrayList of lecturers
     */
    public ArrayList<LectureDetails> getAllLecturers() {
        ArrayList<LectureDetails> lecturers = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_LECTURER_SQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                lecturers.add(new LectureDetails(
                        resultSet.getInt("lectureID"),
                        resultSet.getInt("userID"),
                        resultSet.getString("lectureDescription"),
                        resultSet.getBytes("lectureProfile"),
                        resultSet.getBoolean("accepted")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error at DAOLectureDetails.getAllLecturers: " + e.getMessage());
        }
        return lecturers;
    }

    public ArrayList<LectureDetails> getAllActivatedLecturers() {
        ArrayList<LectureDetails> lecturers = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select ld.LectureID, ld.UserID, LectureDescription, lectureProfile, accepted\n" +
                    "from Users u, LectureDetails ld \n" +
                    "where u.UserID = ld.UserID and accepted = 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                lecturers.add(new LectureDetails(
                        resultSet.getInt("lectureID"),
                        resultSet.getInt("userID"),
                        resultSet.getString("lectureDescription"),
                        resultSet.getBytes("lectureProfile"),
                        resultSet.getBoolean("accepted")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error at DAOLectureDetails.getAllLecturers: " + e.getMessage());
        }
        return lecturers;
    }

    public boolean updateLectureDetailsProfile(int userId, String LectureDescription, InputStream img) {
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(UPDATE_LECTURER_DETAILS_SQL);
            pre.setString(1, LectureDescription);
            System.out.println(img);
            if (img != null) {
                pre.setBinaryStream(2, img);
            } else {
                try {
                    pre = connection.prepareStatement("UPDATE LectureDetails SET  lectureDescription = ? WHERE UserID = ?");
                    pre.setString(1, LectureDescription);
                    pre.setInt(2, userId);
                    pre.executeUpdate();
                    pre.close();
                    return true;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return false;
            }
            pre.setInt(3, userId);
            pre.executeUpdate();
            pre.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public int getTotalTestPosted(int id) {
        String sql = "  SELECT  COUNT(Q.[QuizID]) AS TotalQuizzes\n" +
                "FROM [DummiesCourse].[dbo].[Quiz] Q\n" +
                "JOIN [DummiesCourse].[dbo].[Courses] C ON Q.[CourseID] = C.[CourseID]\n" +
                "WHERE c.LecturerID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public double getStudentAvgScore(int id) {
        String sql = "SELECT FORMAT(AVG(qr.Score),'0,00') FROM QuizResult qr \n" +
                "LEFT JOIN quiz q ON qr.QuizID = q.QuizID\n" +
                "LEFT JOIN courses c ON q.CourseID = c.CourseID\n" +
                "WHERE c.LecturerID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int getTotalStudent(int id) {
        String sql = "SELECT  COUNT(E.[StudentID])\n" +
                "FROM [DummiesCourse].[dbo].[StudentCourseEnrollment] E\n" +
                "JOIN [DummiesCourse].[dbo].[Courses] C ON E.[CourseID] = C.[CourseID]\n" +
                "WHERE C.[LecturerID] = ?\n" +
                "GROUP BY c.LecturerID";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int getReviewReceived(int id) {
        String sql = "  Select count(*) FROM REVIEWS R JOIN COURSES C ON R.CourseID = C.CourseID\n" +
                "  WHERE LecturerID = ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public double getAvgScore(int id) {
        String sql = "   Select FORMAT(AVG(Rating),'0,00') FROM REVIEWS R JOIN COURSES C ON R.CourseID = C.CourseID\n" +
                "  WHERE LecturerID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
