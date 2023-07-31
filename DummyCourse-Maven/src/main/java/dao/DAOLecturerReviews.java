package dao;

import entity.user.LectureReview;
import entity.user.StudentCourseEnrollment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOLecturerReviews extends DBContext {
    public static final String GET_LECTURE_REVIEWS_SQL = "SELECT * FROM lecture_reviews WHERE lecture_id = ? order by review_id desc";
    public static final String GET_COURSE_REVIEW_SQL = "SELECT * FROM lecture_reviews WHERE user_id = ? AND lecture_id = ?";
    public static final String UPDATE_LECTURE_REVIEW_SQL = "UPDATE lecture_reviews SET rating = ?, comment = ? WHERE user_id = ? AND lecture_id = ?";
    public static final String GET_AVARGE_LECTURE_REVIEW_SQL = "SELECT AVG(rating) AS avg_rating FROM lecture_reviews WHERE lecture_id = ?";
    public static final String GET_ALL_LECTURE_REVIEWS_SQL = "SELECT * FROM lecture_reviews";
    public static final String DELETE_LECTURE_REVIEW_SQL = "DELETE FROM lecture_reviews WHERE user_id = ? AND lecture_id = ?";
    public static final String DELETE_BY_ID_SQL = "DELETE FROM lecture_reviews WHERE review_id = ?";
    public static final String ADD_LECTURE_REVIEW_SQL = "INSERT INTO lecture_reviews (lecture_id, user_id, rating, comment, review_date) VALUES (?, ?, ?, ?, ?)";

    public static void main(String[] args) {
        //Test all functions
        /*
        get
        add
        update
        delete
        getAll
        getByID
         */
        //Test getLectureReviewsByLectureID
        System.out.println("Test getLectureReviewsByLectureID");
        ArrayList<LectureReview> lectureReviews = new DAOLecturerReviews().getLectureReviewsByLectureID(6);
        for (LectureReview lectureReview : lectureReviews) {
            System.out.println(lectureReview);
        }
        //Test getLectureReview
        System.out.println("Test getLectureReview");
        LectureReview lectureReview = new DAOLecturerReviews().getLectureReview(2, 7);
        System.out.println(lectureReview);
        //Test addLectureReview
        System.out.println("Test addLectureReview");
        LectureReview lectureReview1 = new LectureReview(6, 3, 3, "Good", "2021-05-05");
        LectureReview lectureReview2 = new LectureReview(6, 4, 4, "Good", "2021-05-05");
        System.out.println(new DAOLecturerReviews().addLectureReview(lectureReview1) + " row(s) inserted");
        System.out.println(new DAOLecturerReviews().addLectureReview(lectureReview2) + " row(s) inserted");
        //Test updateLectureReview
        System.out.println("Test updateLectureReview");
        LectureReview lectureReview3 = new LectureReview(1, 6, 4, 2, "Not Good", "2021-05-05");
        System.out.println(new DAOLecturerReviews().updateLectureReview(lectureReview3) + " row(s) updated");
        //Test getAllLectureReview
        System.out.println("Test getAllLectureReview");
        ArrayList<LectureReview> lectureReviews1 = new DAOLecturerReviews().getAllLectureReviews();
        for (LectureReview lectureReview4 : lectureReviews1) {
            System.out.println(lectureReview4);
        }
        //Test getLectureReviewsByLectureID
        System.out.println("Test getLectureReviewsByLectureID");
        ArrayList<LectureReview> lectureReviews2 = new DAOLecturerReviews().getLectureReviewsByLectureID(6);
        for (LectureReview lectureReview5 : lectureReviews2) {
            System.out.println(lectureReview5);
        }
        //Test getLectureReviewScore
        System.out.println("Test getLectureReviewScore");
        System.out.println(new DAOLecturerReviews().getLectureReviewScore(6));
        //TestDeleteLectureReview
        System.out.println("Test deleteLectureReview");
        System.out.println(new DAOLecturerReviews().deleteLectureReview(3, 6) + " row(s) deleted");
        System.out.println(new DAOLecturerReviews().deleteLectureReview(4, 6) + " row(s) deleted");

    }

    public ArrayList<LectureReview> getLectureReviewsByLectureID(int lectureID) {
        ArrayList<LectureReview> lectureReviews = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_LECTURE_REVIEWS_SQL)) {
            ps.setInt(1, lectureID);
            var rs = ps.executeQuery();
            while (rs.next()) {
                lectureReviews.add(getRSLectureReview(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lectureReviews;
    }

    public LectureReview getLectureReview(int userID, int lectureID) {
        try (PreparedStatement ps = connection.prepareStatement(GET_COURSE_REVIEW_SQL)) {
            ps.setInt(1, userID);
            ps.setInt(2, lectureID);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return getRSLectureReview(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private LectureReview getRSLectureReview(ResultSet rs) throws SQLException {
        LectureReview lectureReview = new LectureReview();
        lectureReview.setReviewID(rs.getInt("review_id"));
        lectureReview.setLectureID(rs.getInt("lecture_id"));
        lectureReview.setUserID(rs.getInt("user_id"));
        lectureReview.setRating(rs.getFloat("rating"));
        lectureReview.setComment(rs.getString("comment"));
        lectureReview.setReviewDate(rs.getString("review_date"));
        return lectureReview;
    }

    public int updateLectureReview(LectureReview lectureReview) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_LECTURE_REVIEW_SQL)) {
            ps.setFloat(1, lectureReview.getRating());
            ps.setString(2, lectureReview.getComment());
            ps.setInt(3, lectureReview.getUserID());
            ps.setInt(4, lectureReview.getLectureID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public double getLectureReviewScore(int lectureID) {
        try (PreparedStatement ps = connection.prepareStatement(GET_AVARGE_LECTURE_REVIEW_SQL)) {
            ps.setInt(1, lectureID);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public ArrayList<LectureReview> getAllLectureReviews() {
        ArrayList<LectureReview> lectureReviews = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_ALL_LECTURE_REVIEWS_SQL)) {
            var rs = ps.executeQuery();
            while (rs.next()) {
                lectureReviews.add(getRSLectureReview(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lectureReviews;
    }

    public int deleteLectureReview(int userID, int lectureID) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_LECTURE_REVIEW_SQL)) {
            ps.setInt(1, userID);
            ps.setInt(2, lectureID);
            ps.executeUpdate();
            return 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int deleteLectureReview(int reviewID) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            ps.setInt(1, reviewID);
            ps.executeUpdate();
            return 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int addLectureReview(LectureReview lectureReview) {
        try (PreparedStatement ps = connection.prepareStatement(ADD_LECTURE_REVIEW_SQL)) {
            ps.setInt(1, lectureReview.getLectureID());
            ps.setInt(2, lectureReview.getUserID());
            ps.setFloat(3, lectureReview.getRating());
            ps.setString(4, lectureReview.getComment());
            ps.setString(5, lectureReview.getReviewDate());
            ps.executeUpdate();
            return 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

}
