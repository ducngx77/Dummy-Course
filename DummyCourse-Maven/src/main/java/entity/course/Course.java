package entity.course;

public class Course {
    private int courseID;
    private int courseTypeID;
    private String courseDescription;
    private String courseName;
    private int lecturerID;
    private int status;

    public Course() {
    }

    public Course(int courseTypeID, String courseDescription, String courseName, int lecturerID, int status) {
        this.courseTypeID = courseTypeID;
        this.courseDescription = courseDescription;
        this.courseName = courseName;
        this.lecturerID = lecturerID;
        this.status = status;
    }

    public Course(int courseID, int courseTypeID, String courseDescription, String courseName, int lecturerID, int status) {
        this.courseID = courseID;
        this.courseTypeID = courseTypeID;
        this.courseDescription = courseDescription;
        this.courseName = courseName;
        this.lecturerID = lecturerID;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", courseTypeID=" + courseTypeID +
                ", courseDescription='" + courseDescription + '\'' +
                ", courseName='" + courseName + '\'' +
                ", lecturerID=" + lecturerID +
                ", status=" + status +
                '}';
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getCourseTypeID() {
        return courseTypeID;
    }

    public void setCourseTypeID(int courseTypeID) {
        this.courseTypeID = courseTypeID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(int lecturerID) {
        this.lecturerID = lecturerID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
