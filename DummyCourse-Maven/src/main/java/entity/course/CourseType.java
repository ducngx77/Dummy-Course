package entity.course;

public class CourseType {
    private int courseTypeID;
    private String typeName;

    public CourseType(int courseTypeID, String typeName) {
        this.courseTypeID = courseTypeID;
        this.typeName = typeName;
    }

    public CourseType() {
    }

    @Override
    public String toString() {
        return "CourseType{" +
                "courseTypeID=" + courseTypeID +
                ", typeName='" + typeName + '\'' +
                '}';
    }

    public int getCourseTypeID() {
        return courseTypeID;
    }

    public void setCourseTypeID(int courseTypeID) {
        this.courseTypeID = courseTypeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


}
