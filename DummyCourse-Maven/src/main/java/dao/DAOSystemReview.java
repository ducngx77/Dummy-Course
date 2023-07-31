package dao;

import entity.user.SystemReview;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAOSystemReview extends DBContext {
    public static final String DELETE_SYSTEM_REVIEW_SQL = "DELETE FROM system_reviews WHERE user_id = ?";
    public static final String ADD_SYSTEM_REVIEW_SQL = "INSERT INTO system_reviews(user_id, rating, comment, review_date) VALUES(?,?,?,?)";
    public static final String UPDATE_SYSTEM_REVIEWS_SQL = "UPDATE system_reviews SET rating = ?, comment = ?, review_date = ? WHERE user_id = ?";
    public static final String GET_ALL_SYSTEM_REVIEWS_SQL = "SELECT * FROM system_reviews ORDER BY review_id desc";
    public static final String GET_SYSTEM_REVIEW_BY_USER_ID_SQL = "SELECT * FROM system_reviews WHERE user_id = ?";
    public static final String FIVE_RANDOM_REVIEW_SQL = "SELECT * FROM RandomSystemReviews";
    public static final String GET_USER_NAME_SQL = "SELECT * FROM dbo.GetUserBySystemReviewID( ? )";

    public static void main(String[] args) {
        ArrayList<SystemReview> systemReviews = new DAOSystemReview().getFiveRandomSystemReviews();
        for (SystemReview systemReview : systemReviews) {
            System.out.println(systemReview);
        }
//        System.out.println("Test getAllSystemReviews");
//        ArrayList<SystemReview> systemReviews = new DAOSystemReview().getAllSystemReviews();
//        for (SystemReview systemReview : systemReviews) {
//            System.out.println(systemReview);
//        }
//        //Test getSystemReviewByUserID
//        System.out.println("Test getSystemReviewByUserID");
//        SystemReview systemReview = new DAOSystemReview().getSystemReviewByUserID(4);
//        System.out.println(systemReview);
//        //Test addSystemReview
//        System.out.println("Test addSystemReview");
//        SystemReview systemReview1 = new SystemReview(5, 5, "Good", "2021-05-05");
//        System.out.println(new DAOSystemReview().addSystemReview(systemReview1) + " row(s) affected");
//        //Test updateSystemReview
//        System.out.println("Test updateSystemReview");
//        SystemReview systemReview2 = new SystemReview(5, 3, "Good", "2021-05-05");
//        System.out.println(new DAOSystemReview().updateSystemReview(systemReview2) + " row(s) affected");
//        //Test getFiveRandomSystemReviews
//        System.out.println("Test getFiveRandomSystemReviews");
//        ArrayList<SystemReview> systemReviews1 = new DAOSystemReview().getFiveRandomSystemReviews();
//        for (SystemReview systemReview3 : systemReviews1) {
//            System.out.println(systemReview3);
//        }
//        //Test getUsernameByID
//        System.out.println("Test getUsernameByID");
//        System.out.println(new DAOSystemReview().getUsernameByID(1));
//
//
//        //Test deleteSystemReview
//        System.out.println("Test deleteSystemReview");
//        System.out.println(new DAOSystemReview().deleteSystemReview(5) + " row(s) affected");
    }

    /**
     * Delete system review by user id
     *
     * @param systemReviewID system review id
     * @return number of row affected
     */
    public String getUsernameByID(int systemReviewID) {
        String username = "";
        try {
            PreparedStatement ps = connection.prepareStatement(GET_USER_NAME_SQL);
            ps.setInt(1, systemReviewID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                username = rs.getString("Username");
            }
        } catch (Exception e) {
            System.out.println("Error at DAOSystemReview/getUsernameByID: " + e.getMessage());
        }
        return username;
    }

    /**
     * Delete system review by user id
     *
     * @return numbers of row affected
     */
    public ArrayList<SystemReview> getFiveRandomSystemReviews() {
        ArrayList<SystemReview> systemReviews = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(FIVE_RANDOM_REVIEW_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                systemReviews.add(new SystemReview(rs.getInt("review_id"),
                        rs.getInt("user_id"),
                        rs.getFloat("rating"),
                        rs.getString("comment"),
                        rs.getString("review_date")));
            }
        } catch (Exception e) {
            System.out.println("Error at DAOSystemReview/getFiveRandomSystemReviews: " + e.getMessage());
        }
        return systemReviews;
    }

    /**
     * Query list of all system reviews
     *
     * @return list of system reviews
     */
    public ArrayList<SystemReview> getAllSystemReviews() {
        ArrayList<SystemReview> systemReviews = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_ALL_SYSTEM_REVIEWS_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                systemReviews.add(new SystemReview(rs.getInt("review_id"),
                        rs.getInt("user_id"),
                        rs.getFloat("rating"),
                        rs.getString("comment"),
                        rs.getString("review_date")));
            }
        } catch (Exception e) {
            System.out.println("Error at DAOSystemReview/getAllSystemReviews: " + e.getMessage());
        }
        return systemReviews;
    }

    /**
     * @param user_id
     * @return
     */
    public SystemReview getSystemReviewByUserID(int user_id) {
        try {
            PreparedStatement ps = connection.prepareStatement(GET_SYSTEM_REVIEW_BY_USER_ID_SQL);
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new SystemReview(rs.getInt("review_id"),
                        rs.getInt("user_id"),
                        rs.getFloat("rating"),
                        rs.getString("comment"),
                        rs.getString("review_date"));
            }
        } catch (Exception e) {
            System.out.println("Error at DAOSystemReview/getSystemReviewByUserID: " + e.getMessage());
        }
        return null;
    }

    public int deleteSystemReview(int user_id) {
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_SYSTEM_REVIEW_SQL);
            ps.setInt(1, user_id);
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error at DAOSystemReview/deleteSystemReview: " + e.getMessage());
        }
        return 0;
    }

    public int updateSystemReview(SystemReview systemReview) {
        try {
            PreparedStatement ps = connection.prepareStatement(UPDATE_SYSTEM_REVIEWS_SQL);
            ps.setFloat(1, systemReview.getRating());
            ps.setString(2, systemReview.getComment());
            ps.setString(3, systemReview.getReviewDate());
            ps.setInt(4, systemReview.getUserID());
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error at DAOSystemReview/editSystemReview: " + e.getMessage());
        }
        return 0;
    }

    public int addSystemReview(SystemReview systemReview) {
        try {
            String sql = ADD_SYSTEM_REVIEW_SQL;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, systemReview.getUserID());
            ps.setFloat(2, systemReview.getRating());
            ps.setString(3, systemReview.getComment());
            ps.setString(4, systemReview.getReviewDate());
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error at DAOSystemReview/addSystemReview: " + e.getMessage());
        }
        return 0;
    }
}
