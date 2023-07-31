package entity.user;

public class StudentCourseEnrollment {
    private int assignmentID;
    private int courseID;
    private int studentID;
    private String assignmentDate;

    public StudentCourseEnrollment(int courseID, int studentID, String assignmentDate) {
        this.courseID = courseID;
        this.studentID = studentID;
        this.assignmentDate = assignmentDate;
    }

    public StudentCourseEnrollment(int assignmentID, int courseID, int studentID, String assignmentDate) {
        this.assignmentID = assignmentID;
        this.courseID = courseID;
        this.studentID = studentID;
        this.assignmentDate = assignmentDate;
    }

    public int getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    @Override
    public String toString() {
        return "StudentCourseEnrollment{" +
                "assignmentID=" + assignmentID +
                ", courseID=" + courseID +
                ", studentID=" + studentID +
                ", assignmentDate='" + assignmentDate + '\'' +
                '}';
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }
}
