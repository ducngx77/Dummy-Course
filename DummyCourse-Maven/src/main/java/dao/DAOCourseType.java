package dao;

import entity.course.CourseType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOCourseType extends DBContext {
    private static final String CREATE_COURSE_TYPE_SQL = "INSERT INTO CourseType (TypeName) VALUES (?)";
    private static final String GET_COURSE_TYPE_BY_ID_SQL = "SELECT * FROM CourseType WHERE CourseTypeID = ?";
    private static final String UPDATE_COURSE_TYPE_SQL = "UPDATE CourseType SET TypeName = ? WHERE CourseTypeID = ?";
    private static final String DELETE_COURSE_TYPE_SQL = "DELETE FROM CourseType WHERE CourseTypeID = ?";
    private static final String GET_ALL_COURSE_TYPE_SQL = "SELECT * FROM CourseType";

    /**
     * Creates a new CourseType record in the database.
     *
     * @param courseType The CourseType object to be created.
     * @return The created CourseType object.
     */
    public int createCourseType(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_COURSE_TYPE_SQL);
            preparedStatement.setString(1, name);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
     * Retrieves a CourseType record from the database based on the given courseTypeId.
     *
     * @param courseTypeId The ID of the CourseType to be retrieved.
     * @return The retrieved CourseType object, or null if not found.
     */
    public CourseType getCourseTypeByID(int courseTypeId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_COURSE_TYPE_BY_ID_SQL);
            preparedStatement.setInt(1, courseTypeId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new CourseType(
                        rs.getInt("CourseTypeID"),
                        rs.getString("TypeName")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Updates an existing CourseType record in the database.
     *
     * @param courseType The CourseType object to be updated.
     * @return The updated CourseType object, or null if update fails.
     */
    public int updateCourseType(CourseType courseType) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COURSE_TYPE_SQL);
            preparedStatement.setString(1, courseType.getTypeName());
            preparedStatement.setInt(2, courseType.getCourseTypeID());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     * Deletes a CourseType record from the database based on the given courseTypeId.
     *
     * @param courseTypeId The ID of the CourseType to be deleted.
     */
    public int deleteCourseType(int courseTypeId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COURSE_TYPE_SQL);
            preparedStatement.setInt(1, courseTypeId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
     * Retrieves all CourseType records from the database.
     *
     * @return A list of all CourseType objects.
     */
    public List<CourseType> getAllCourseType() {
        List<CourseType> courseTypes = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_COURSE_TYPE_SQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CourseType courseType = new CourseType(
                        rs.getInt("CourseTypeID"),
                        rs.getString("TypeName")
                );
                courseTypes.add(courseType);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courseTypes;
    }
}
