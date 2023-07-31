package dao;

import entity.course.Course;
import entity.user.StudentCourseEnrollment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOStudentCourseAssignment extends DBContext {
    public static void main(String[] args) {
        DAOStudentCourseAssignment daoStudentCourseAssignment = new DAOStudentCourseAssignment();
        ArrayList<Course> courses = daoStudentCourseAssignment.getEnrolledCourseByIDAndCourseType("Mathematics", 2);
        for (Course course : courses) {
            System.out.println(course.getCourseName());
        }
    }

    private static ArrayList<Course> getCourses(ResultSet resultSet) throws SQLException {
        ArrayList<Course> courses = new ArrayList<>();
        while (resultSet.next()) {
            Course course = new Course(
                    resultSet.getInt("CourseID"),
                    resultSet.getInt("CourseTypeID"),
                    resultSet.getString("CourseDescription"),
                    resultSet.getString("CourseName"),
                    resultSet.getInt("LecturerID"),
                    resultSet.getInt("Status")
            );
            courses.add(course);
        }
        return courses;
    }

    public ArrayList<Course> getEnrolledCourseByIDAndCourseType(String courseType, int id) {
        String sql = "SELECT * FROM dbo.GetAssignedCoursesByTypeAndUser(? , ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, courseType);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Course> courses = getCourses(resultSet);
            return courses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Course> getEnrolledCourseByID(int id) {
        String sql = "SELECT * FROM dbo.GetEnrolledCourses( ? )";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Course> courses = getCourses(resultSet);
            return courses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<StudentCourseEnrollment> getAllStudentCourseEnrollment() {
        String sql = "SELECT * FROM StudentCourseAssignment";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<StudentCourseEnrollment> studentCourseEnrollments = new ArrayList<>();
            while (resultSet.next()) {
                StudentCourseEnrollment studentCourseEnrollment = new StudentCourseEnrollment(
                        resultSet.getInt("AssignmentID"),
                        resultSet.getInt("CourseID"),
                        resultSet.getInt("StudentID"),
                        resultSet.getString("AssignmentDate")
                );
                studentCourseEnrollments.add(studentCourseEnrollment);
            }
            return studentCourseEnrollments;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
