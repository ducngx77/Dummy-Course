package dao;

import entity.course.Course;
import entity.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOCourse extends DBContext {
    public static final String GET_TOP_COURSES = "SELECT * FROM dbo.GetTopCoursesByUserReviews(?)";
    public static final String GET_TOP_COURSES_WITH_OFFSET = "SELECT *\n" +
            "FROM dbo.GetTopRatedCoursesWithOffset(?, ?)"; // First set for offset, second set for limit
    public static final String GET_TOP_RATED_COURSES_WITH_OFFSET = "SELECT * FROM dbo.GetTopRatedCourses(?);";
    public static final String GET_COURSES_WITH_OFFSET = "SELECT *\n" +
            "FROM Courses\n" +
            "ORDER BY CourseID\n" +
            "OFFSET ? ROWS\n" +
            "FETCH NEXT 20 ROWS ONLY;";
    public static final String GET_COURSE_BY_TYPE_WITH_OFFSET = "SELECT *\n" +
            "FROM dbo.GetCoursesByTypeWithOffset(?, ?, ?)"; // First set for type, second set for offset, third set for limit

    public static final String SEARCH_COURSE_WITH_OFFSET = "SELECT * FROM dbo.SearchCoursesWithOffset(?, ?, ?)"; // Search term, offset, limit
    public static final String SEARCH_COURSE_BY_TYPE_WITH_OFFSET = "SELECT * FROM SearchCourseByTypeWithOffset(?, ?, ?, ?)"; // Search term, typeID, offset, limit

    public static final String SEARCH_COURSE_BY_GENERALKEY = "SELECT * FROM Courses WHERE CourseName LIKE ? ";
    private static final String GET_COURSES_BY_STUDENT_ID = "SELECT * FROM Courses WHERE CourseID IN (SELECT CourseID FROM StudentCourseEnrollment WHERE StudentID = ?)";

    public static void main(String[] args) {
        List<Course> courses = new DAOCourse().getCoursesByStudentID(1);

        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private static ArrayList<Course> getCourseListFromRs(ResultSet rs) throws SQLException {
        ArrayList<Course> courseList = new ArrayList<>();
        while (rs.next()) {
            courseList.add(getCourseFromRs(rs));
        }
        return courseList;
    }

    private static Course getCourseFromRs(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setCourseID(rs.getInt("CourseID"));
        course.setCourseTypeID(rs.getInt("CourseTypeID"));
        course.setCourseName(rs.getString("CourseName"));
        course.setCourseDescription(rs.getString("CourseDescription"));
        course.setLecturerID(rs.getInt("LecturerID"));
        course.setStatus(rs.getInt("Status"));
        return course;
    }

    public ArrayList<Course> searchCourseByTypeWithOffset(String searchTerm, int typeID, int offset, int limit) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_COURSE_BY_TYPE_WITH_OFFSET)) {
            preparedStatement.setString(1, searchTerm);
            preparedStatement.setInt(2, typeID);
            preparedStatement.setInt(3, offset);
            preparedStatement.setInt(4, limit);
            ResultSet rs = preparedStatement.executeQuery();
            return getCourseListFromRs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Course> searchCourseWithOffset(String searchTerm, int offset, int limit) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_COURSE_WITH_OFFSET)) {
            preparedStatement.setString(1, searchTerm);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);
            ResultSet rs = preparedStatement.executeQuery();
            return getCourseListFromRs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Course> searchCourseByGeneralKey(String searchTerm) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_COURSE_BY_GENERALKEY)) {
            preparedStatement.setString(1, "%" + searchTerm + "%");
            ResultSet rs = preparedStatement.executeQuery();
            return getCourseListFromRs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Course> getCourseByTypeWithOffset(int type, int offset, int limit) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_COURSE_BY_TYPE_WITH_OFFSET)) {
            preparedStatement.setInt(1, type);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);
            ResultSet rs = preparedStatement.executeQuery();
            return getCourseListFromRs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Course> getTopCoursesWithOffset(int offset, int limit) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_TOP_COURSES_WITH_OFFSET)) {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, limit);
            ResultSet rs = preparedStatement.executeQuery();
            return getCourseListFromRs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Course> getCoursesWithOffset(int offset) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_COURSES_WITH_OFFSET)) {
            preparedStatement.setInt(1, offset);
            ResultSet rs = preparedStatement.executeQuery();
            return getCourseListFromRs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getAverageRatingByCourseID(int courseID) {
        String sql = "SELECT dbo.GetAverageRatingByCourseID(?) AS 'Average Rating'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, courseID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Course> getTopRatedCoursesWithOffset(int offset) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_TOP_RATED_COURSES_WITH_OFFSET)) {
            preparedStatement.setInt(1, offset);
            ResultSet rs = preparedStatement.executeQuery();
            return getCourseListFromRs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Course> getTopCourses(int userID) {
        /*
        SELECT *
        FROM dbo.GetTopCoursesByUserReviews(1);
         */
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_TOP_COURSES)) {
            preparedStatement.setInt(1, userID);
            ResultSet rs = preparedStatement.executeQuery();
            return getCourseListFromRs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Course> getCourseByTypeAndLectureID(String type, int id) {
        String sql = "SELECT * FROM dbo.GetCoursesByCourseTypeAndLecturerID(?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, type);
            preparedStatement.setInt(2, id);
            ResultSet rs = preparedStatement.executeQuery();
            //20,Database Systems 101,Database Management Systems,4.3
            return getCourseListFromRs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Course> getCoursesByLectureID(int id) {
        String sql = "EXEC GetActiveCoursesByLecturerID @lecturerID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            return getCourseListFromRs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Course> get4CoursesByLectureID(int id) {
        String sql = "  SELECT TOP (4) C.CourseID, C.CourseTypeID, C.CourseDescription, C.CourseName, C.LecturerID, C.Status\n" +
                "    FROM Courses AS C\n" +
                "    WHERE C.LecturerID = ? AND C.Status = 1 order by NEWID()";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            return getCourseListFromRs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Course> getCourseByType(String type) {
        String sql = "SELECT * FROM GetCoursesByCourseType( ? )";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, type);
            ResultSet rs = preparedStatement.executeQuery();
            return getCourseListFromRs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getLectureName(int courseID) {
        String sql = "SELECT * FROM dbo.GetLecturerInformation( ? )";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, courseID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("LecturerID"), rs.getString("Username"), rs.getString("Email"), rs.getString("PhoneNumber"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCourseInstructor(String courseID) {
        return null;
    }

    /**
     * Retrieves the top 3 rated courses from the database.
     *
     * @return a list of the top 3 rated Course objects
     */
    public ArrayList<Course> getTop3Rated() {
        String sql = "SELECT * FROM TopRatedCourses";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            return getCourseListFromRs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getNumberOfStudentByCourseId(int courseID) {
        String sql = "SELECT count(StudentID) as number from [StudentCourseEnrollment] WHERE courseID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, courseID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Retrieves a course from the database based on the course ID.
     *
     * @param courseID the ID of the course to retrieve
     * @return the Course object, or null if not found
     */
    public Course getCourseByID(int courseID) {
        String sql = "SELECT * FROM Courses WHERE CourseID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, courseID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return getCourseFromRs(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Course getNewestCourse() {
        String sql = "select top 1 * from Courses order by CourseID desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return getCourseFromRs(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all courses from the database.
     *
     * @return a list of all Course objects
     */
    public List<Course> getAllCourses() {
        String sql = "SELECT * FROM Courses";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            return getCourseListFromRs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a new course in the database.
     *
     * @param course the Course object to be created
     * @return number of records inserted or -1 if an error occurs during execution
     */
    public int createCourse(Course course) {
        String sql = "INSERT INTO Courses (CourseTypeID, CourseName, CourseDescription, LecturerID, Status) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, course.getCourseTypeID());
            preparedStatement.setString(2, course.getCourseName());
            preparedStatement.setString(3, course.getCourseDescription());
            preparedStatement.setInt(4, course.getLecturerID());
            preparedStatement.setInt(5, course.getStatus());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Updates an existing course in the database.
     *
     * @param course the Course object to be updated
     */
    public void updateCourse(Course course) {
        String sql = "UPDATE Courses SET CourseTypeID = ?, CourseName = ?, CourseDescription = ?,  LecturerID = ?, Status = ? WHERE CourseID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, course.getCourseTypeID());
            preparedStatement.setString(2, course.getCourseName());
            preparedStatement.setString(3, course.getCourseDescription());
            preparedStatement.setInt(4, course.getLecturerID());
            preparedStatement.setInt(5, course.getStatus());
            preparedStatement.setInt(6, course.getCourseID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCourseName(Course course) {
        String sql = "UPDATE Courses SET CourseTypeID = ?, CourseName = ?, CourseDescription = ?,  LecturerID = ?, Status = ? WHERE CourseID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, course.getCourseTypeID());
            preparedStatement.setString(2, course.getCourseName());
            preparedStatement.setString(3, course.getCourseDescription());
            preparedStatement.setInt(4, course.getLecturerID());
            preparedStatement.setInt(5, course.getStatus());
            preparedStatement.setInt(6, course.getCourseID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a course from the database based on the course ID.
     *
     * @param courseID the ID of the course to delete
     */
    public void deleteCourse(int courseID) {
        String sql = "DELETE FROM Courses WHERE CourseID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, courseID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getCoursesByStudentID(int userID) {
        String sql = "SELECT * FROM Courses WHERE CourseID IN (SELECT CourseID FROM StudentCourseEnrollment WHERE StudentID = ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            ResultSet rs = preparedStatement.executeQuery();
            return getCourseListFromRs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
