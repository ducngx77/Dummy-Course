package dao;

import entity.user.StudentCourseEnrollment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOStudentCourseEnrollment extends DBContext{

    public boolean handleEnrollStudent(int studentId,int courseId) {
        String sql = "INSERT INTO StudentCourseEnrollment (CourseID, StudentID, AssignmentDate)\n" +
                "VALUES (?, ?, GETDATE())";
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, courseId);
            pre.setInt(2, studentId);
            pre.executeUpdate();
            pre.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean checkStudentEnrolled(int studentId,int courseId){
        String sql = "SELECT * FROM [StudentCourseEnrollment] " +
                "WHERE CourseID = ? AND StudentID = ?";
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, courseId);
            pre.setInt(2, studentId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                return true;
            }
            pre.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<StudentCourseEnrollment> getCourseStudentEnrolled(int studentId) {
        String sql = "SELECT * FROM [StudentCourseEnrollment] WHERE StudentID = ? ";
        ArrayList<StudentCourseEnrollment> list = new ArrayList<>();
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, studentId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                list.add(new StudentCourseEnrollment(rs.getInt(1),rs.getInt(2), rs.getInt(3), rs.getString(4)));
            }
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public int getTotalTest(int id) {
        String sql = "  SELECT COUNT(*) FROM Quiz q \n" +
                "  LEFT JOIN courses c on c.CourseID = q.CourseID\n" +
                "  LEFT JOIN StudentCourseEnrollment sce on c.CourseID = sce.CourseID\n" +
                "  WHERE sce.StudentID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int getTotalTestTaken(int id) {
        String sql = "  SELECT COUNT(*) FROM QuizResult qr \n" +
                "  LEFT JOIN Users u on qr.StudentID = u.UserID\n" +
                "  WHERE u.UserID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public double getAvgTestScore(int id) {
        String sql = "  SELECT FORMAT(AVG(qr.Score),'0,00') FROM QuizResult qr \n" +
                "  LEFT JOIN Users u on qr.StudentID = u.UserID\n" +
                "  WHERE u.UserID = 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
