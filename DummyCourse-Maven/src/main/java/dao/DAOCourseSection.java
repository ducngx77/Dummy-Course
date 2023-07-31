package dao;

import entity.course.CourseSection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOCourseSection extends DBContext {
    public static final String CREATE_COURSE_SECTION_SQL = "INSERT INTO CourseSection (CourseID, SectionName, CreateDate, SectionDuration, SectionNotice) VALUES (?, ?, ?, ?, ?)";
    public static final String GET_COURSE_SECTION_BY_ID_SQL = "SELECT * FROM CourseSection WHERE SectionID = ?";
    public static final String UPDATE_COURSE_SECTION_SQL = "UPDATE CourseSection SET SectionName = ?, CreateDate = ?, SectionDuration = ?, SectionNotice = ? WHERE CourseID = ?";
    public static final String DELETE_COURSE_SECTION_SQL = "DELETE FROM CourseSection WHERE SectionID = ?";
    public static final String GET_ALL_COURSE_SECTION_SQL = "SELECT * FROM CourseSection";
    public static final String GET_ALL_COURSE_SECTION_BY_COURSE_ID_SQL = "SELECT * FROM CourseSection where CourseID = ?";

    /**
     * Create a new CourseSection in database
     *
     * @param courseSection
     * @return CourseSection
     */
    public int createCourseSection(CourseSection courseSection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_COURSE_SECTION_SQL);
            preparedStatement.setInt(1, courseSection.getCourseID());
            preparedStatement.setString(2, courseSection.getSectionName());
            preparedStatement.setString(3, courseSection.getCreateDate());
            preparedStatement.setString(4, courseSection.getSectionDuration());
            preparedStatement.setString(5, courseSection.getSectionNotice());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
     * get CourseSection based on id given
     *
     * @param sectionID
     * @return CourseSection
     */
    public CourseSection getCourseSectionbyID(int sectionID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_COURSE_SECTION_BY_ID_SQL);
            preparedStatement.setInt(1, sectionID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new CourseSection(
                        rs.getInt("SectionID"),
                        rs.getInt("CourseID"),
                        rs.getString("SectionName"),
                        rs.getString("CreateDate"),
                        rs.getString("SectionDuration"),
                        rs.getString("SectionNotice")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * edit information of the CourseSection
     *
     * @param courseSection
     */
    public int updateCourseSection(CourseSection courseSection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COURSE_SECTION_SQL);
            preparedStatement.setInt(1, courseSection.getCourseID());
            preparedStatement.setString(2, courseSection.getSectionName());
            preparedStatement.setString(3, courseSection.getCreateDate());
            preparedStatement.setString(4, courseSection.getSectionDuration());
            preparedStatement.setString(5, courseSection.getSectionNotice());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     * delete selected CourseSection in database
     *
     * @param sectionId
     */
    public int deleteCourseSection(int sectionId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COURSE_SECTION_SQL);
            preparedStatement.setInt(1, sectionId);
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     * return all the CourseSection in the database
     *
     * @return
     */
    public List<CourseSection> getAllCourseSection() {
        List<CourseSection> courseSections = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_COURSE_SECTION_SQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CourseSection courseSection = new CourseSection(
                        rs.getInt("SectionID"),
                        rs.getInt("CourseID"),
                        rs.getString("SectionName"),
                        rs.getString("CreateDate"),
                        rs.getString("SectionDuration"),
                        rs.getString("SectionNotice")
                );
                courseSections.add(courseSection);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courseSections;
    }

    /**
     * get CourseSection by CourseID
     *
     * @param courseID
     * @return CourseSection
     */
    public List<CourseSection> getAllCourseSectionbyCourseID(int courseID) {
        List<CourseSection> courseSections = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_COURSE_SECTION_BY_COURSE_ID_SQL);
            preparedStatement.setInt(1, courseID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CourseSection courseSection = new CourseSection(
                        rs.getInt("SectionID"),
                        rs.getInt("CourseID"),
                        rs.getString("SectionName"),
                        rs.getString("CreateDate"),
                        rs.getString("SectionDuration"),
                        rs.getString("SectionNotice")
                );
                courseSections.add(courseSection);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courseSections;
    }
}
