package entity.course;

public class CourseSection {
    private int sectionID;
    private int courseID;
    private String sectionName;
    private String createDate;
    private String sectionDuration;
    private String sectionNotice;

    public CourseSection() {
    }

    public CourseSection(int courseID, String sectionName, String createDate, String sectionDuration, String sectionNotice) {
        this.courseID = courseID;
        this.sectionName = sectionName;
        this.createDate = createDate;
        this.sectionDuration = sectionDuration;
        this.sectionNotice = sectionNotice;
    }

    public CourseSection(int sectionID, int courseID, String sectionName, String createDate, String sectionDuration, String sectionNotice) {
        this.sectionID = sectionID;
        this.courseID = courseID;
        this.sectionName = sectionName;
        this.createDate = createDate;
        this.sectionDuration = sectionDuration;
        this.sectionNotice = sectionNotice;
    }

    @Override
    public String toString() {
        return "CourseSection{" +
                "sectionID=" + sectionID +
                ", courseID=" + courseID +
                ", sectionName='" + sectionName + '\'' +
                ", createDate='" + createDate + '\'' +
                ", sectionDuration='" + sectionDuration + '\'' +
                ", sectionNotice='" + sectionNotice + '\'' +
                '}';
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSectionDuration() {
        return sectionDuration;
    }

    public void setSectionDuration(String sectionDuration) {
        this.sectionDuration = sectionDuration;
    }

    public String getSectionNotice() {
        return sectionNotice;
    }

    public void setSectionNotice(String sectionNotice) {
        this.sectionNotice = sectionNotice;
    }
}
