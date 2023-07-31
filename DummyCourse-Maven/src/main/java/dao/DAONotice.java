package dao;

import entity.quiz.Notice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAONotice extends DBContext {
    public Notice getNoticeByCourseID(int courseID) {
        String sql = "SELECT [NoticeID]\n" +
                "      ,[CourseID]\n" +
                "      ,[UserID]\n" +
                "      ,[Notice]\n" +
                "      ,[NoticeDate]\n" +
                "  FROM [dbo].[Notice]\n where CourseID = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, courseID);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                return new Notice(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Notice> getAllNoticeByCourseID(int courseID) {
        List<Notice> notices = new ArrayList<>();
        String sql = "SELECT [NoticeID]\n" +
                "      ,[CourseID]\n" +
                "      ,[UserID]\n" +
                "      ,[Notice]\n" +
                "      ,[NoticeDate]\n" +
                "  FROM [dbo].[Notice]\n where CourseID = ? order by NoticeDate desc";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, courseID);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Notice notice = new Notice(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5));
                notices.add(notice);
            }
            return notices;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
