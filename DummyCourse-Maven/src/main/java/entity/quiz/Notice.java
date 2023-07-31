package entity.quiz;

public class Notice {
    private int NoticeID, CourseID, UserID;
    private String Notice, NoticeDate;

    public Notice(int noticeID, int courseID, int userID, String notice, String noticeDate) {
        NoticeID = noticeID;
        CourseID = courseID;
        UserID = userID;
        Notice = notice;
        NoticeDate = noticeDate;
    }

    public Notice(int courseID, int userID, String notice, String noticeDate) {
        CourseID = courseID;
        UserID = userID;
        Notice = notice;
        NoticeDate = noticeDate;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "NoticeID=" + NoticeID +
                ", CourseID=" + CourseID +
                ", UserID=" + UserID +
                ", Notice='" + Notice + '\'' +
                ", NoticeDate='" + NoticeDate + '\'' +
                '}';
    }

    public int getNoticeID() {
        return NoticeID;
    }

    public void setNoticeID(int noticeID) {
        NoticeID = noticeID;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getNotice() {
        return Notice;
    }

    public void setNotice(String notice) {
        Notice = notice;
    }

    public String getNoticeDate() {
        return NoticeDate.split(" ")[0];
    }

    public void setNoticeDate(String noticeDate) {
        NoticeDate = noticeDate;
    }
}
