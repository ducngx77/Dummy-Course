package dao;

import entity.user.Reviews;
import entity.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOReviews extends DBContext {

    public static final String GET_REVIEW_USER = "SELECT * FROM dbo.GetUserByReviewID( ? )";
    public static final String INSERT_REVIEW_SQL = "INSERT INTO Reviews (CourseID, UserID, Rating, Comment, ReviewDate) VALUES (?, ?, ?, ?, ?)";
    public static final String GET_REVIEW_BY_ID_SQL = "SELECT * FROM Reviews WHERE UserID = ?";
    public static final String UPDATE_REVIEW = "UPDATE Reviews SET Rating = ?, Comment = ?, ReviewDate = ? WHERE CourseID = ? AND UserID = ?";
    public static final String DELETE_REVIEW_BY_ID = "DELETE FROM Reviews WHERE CourseID = ? AND UserID = ?";
    private static final String GET_AVERAGE_SCORE_SQL = "SELECT dbo.GetCourseReviewAverageScore( ? ) as 'Average Score'";
    private static final String GET_REVIEW_BY_USER_AND_COURSE = "SELECT * FROM Reviews WHERE UserID = ? AND CourseID = ?";
    private static final String GET_TOP_REVIEW_WITH_OFFSET_SQL = "SELECT * FROM GetTopReviewsWithOffset(0);";
    private static final String GET_RANDOM_REVIEW_SQL = "SELECT TOP 3 *\n" +
            "FROM Reviews\n" +
            "ORDER BY NEWID();";
    private static final String GET_REVIEW_BY_COURSE_ID = "SELECT * FROM Reviews WHERE CourseID = ?";

    public static void main(String[] args) {
        // TEST getTopReviewsWithOffset(int offset)
//        ArrayList<Reviews> reviews = new DAOReviews().getTopReviewsWithOffset(0);
//        for (Reviews review : reviews) {
//            System.out.println(review);
//        }
        // TEST getReviewByCourseID
        ArrayList<Reviews> reviews1 = new DAOReviews().getReviewByCourseID(1);
        for (Reviews review : reviews1) {
            System.out.println(review);
        }
//        //TEST ALL METHODS
//        //Test getAllReviews
//        System.out.println("Test getAllReviews");
//        List<Reviews> reviews = new DAOReviews().getAllReviews();
//        for (Reviews review : reviews) {
//            System.out.println(review);
//        }
//        //Test getReviewByID
//        System.out.println("Test getReviewByID");
//        ArrayList<Reviews> reviews1 = new DAOReviews().getReviewsByUser(2);
//        for (Reviews review : reviews1) {
//            System.out.println(review);
//        }
//        //Test addReview
//        System.out.println("Test addReview");
//        Reviews review1 = new Reviews(20, 3, 4, "Good", "2021-05-05");
//        System.out.println(new DAOReviews().addReviews(review1) + " row(s) affected");
//        System.out.println("Added review:" + new DAOReviews().getReviewByUserAndCourse("3", "20"));
//        //Test updateReview
//        System.out.println("Test updateReview");
//        Reviews review2 = new Reviews(20, 3, 3, "Good Job", "2021-05-06");
//        System.out.println(new DAOReviews().updateReview(review2) + " row(s) affected");
//        System.out.println("Updated review:" + new DAOReviews().getReviewByUserAndCourse("3", "20"));
//        //Test deleteReview
//        System.out.println("Test deleteReview");
//        System.out.println(new DAOReviews().deleteReview(20, 3) + " row(s) affected");
//        //Test getReviewScore
//        System.out.println("Test getReviewScore");
//        System.out.println(new DAOReviews().getReviewScore(1));
//        //Test deleteReview
    }

    public ArrayList<Reviews> getTopReviewsWithOffset(int offset) {
        ArrayList<Reviews> reviews = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_TOP_REVIEW_WITH_OFFSET_SQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Reviews review = new Reviews();
                review.setCourseID(rs.getInt("CourseID"));
                review.setUserID(rs.getInt("UserID"));
                review.setRating(rs.getInt("Rating"));
                review.setComment(rs.getString("Comment"));
                review.setReviewDate(rs.getString("ReviewDate"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return reviews;
    }

    public Reviews getReviewByUserAndCourse(String userID, String courseID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_REVIEW_BY_USER_AND_COURSE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, courseID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Reviews(rs.getInt("ReviewID"), rs.getInt("CourseID"), rs.getInt("UserID"), rs.getInt("Rating"), rs.getString("Comment"), rs.getString("ReviewDate"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Reviews> getReviewByCourseID(int courseID) {
        String sql = GET_REVIEW_BY_COURSE_ID;
        ArrayList<Reviews> reviewsList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, courseID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Reviews review = new Reviews(
                        rs.getInt("ReviewID"),
                        rs.getInt("CourseID"),
                        rs.getInt("UserID"),
                        rs.getFloat("Rating"),
                        rs.getString("Comment"),
                        rs.getString("ReviewDate")
                );
                reviewsList.add(review);
            }
            return reviewsList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public double getReviewScore(int courseID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_AVERAGE_SCORE_SQL);
            preparedStatement.setInt(1, courseID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("Average Score");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public User getUserReview(int reviewID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_REVIEW_USER, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setInt(1, reviewID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("UserID"), rs.getString("Username"), rs.getString("Email"), rs.getString("PhoneNumber"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * create new Reviews in database
     *
     * @param review
     * @return Reviews
     */
    public int addReviews(Reviews review) {
        String sql = INSERT_REVIEW_SQL;
        try {
            return getReviewPS(review, sql).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * get Reviews based on ID
     *
     * @param userID
     * @return Reviews
     */
    public ArrayList<Reviews> getReviewsByUser(int userID) {
        String sql = GET_REVIEW_BY_ID_SQL;
        ArrayList<Reviews> reviewsList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Reviews review = new Reviews(
                        rs.getInt("ReviewID"),
                        rs.getInt("CourseID"),
                        rs.getInt("UserID"),
                        rs.getFloat("Rating"),
                        rs.getString("Comment"),
                        rs.getString("ReviewDate")
                );
                reviewsList.add(review);
            }
            return reviewsList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * update new Reviews
     *
     * @param review
     * @return Reviews
     */
    public int updateReview(Reviews review) {
        String sql = UPDATE_REVIEW;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, review.getRating());
            preparedStatement.setString(2, review.getComment());
            preparedStatement.setString(3, review.getReviewDate());
            preparedStatement.setInt(4, review.getCourseID());
            preparedStatement.setInt(5, review.getUserID());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private PreparedStatement getReviewPS(Reviews review, String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, review.getCourseID());
        preparedStatement.setInt(2, review.getUserID());
        preparedStatement.setFloat(3, review.getRating());
        preparedStatement.setString(4, review.getComment());
        preparedStatement.setString(5, review.getReviewDate());
        return preparedStatement;
    }

    /**
     * delete Reviews based on ID
     *
     * @param courseID
     * @param userID
     * @return
     */
    public int deleteReview(int courseID, int userID) {
        String sql = DELETE_REVIEW_BY_ID;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, courseID);
            preparedStatement.setInt(2, userID);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * get all Reviews
     *
     * @return Reviews
     */
    public List<Reviews> getAllReviews() {
        List<Reviews> reviewsList = new ArrayList<>();
        String sql = "SELECT * FROM Reviews";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Reviews review = new Reviews(
                        rs.getInt("ReviewID"),
                        rs.getInt("CourseID"),
                        rs.getInt("UserID"),
                        rs.getFloat("Rating"),
                        rs.getString("Comment"),
                        rs.getString("ReviewDate")
                );
                reviewsList.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviewsList;
    }

}




