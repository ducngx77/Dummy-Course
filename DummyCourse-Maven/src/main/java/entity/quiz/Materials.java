package entity.quiz;

import java.util.Arrays;

public class Materials {
    private int MaterialID;
    private int CourseID;
    private int SectionID;
    private String MaterialName;
    private String MaterialType;
    private byte[] MaterialFile;

    public Materials(int materialID, int courseID, int sectionID, String materialName, String materialType, byte[] materialFile) {
        MaterialID = materialID;
        CourseID = courseID;
        SectionID = sectionID;
        MaterialName = materialName;
        MaterialType = materialType;
        MaterialFile = materialFile;
    }
//constructer , getter and setter


    public Materials(int courseID, int sectionID, String materialName, String materialType, byte[] materialFile) {
        MaterialID = 0;
        CourseID = courseID;
        SectionID = sectionID;
        MaterialName = materialName;
        MaterialType = materialType;
        MaterialFile = materialFile;
    }

    @Override
    public String toString() {
        return "Materials{" +
                "MaterialID=" + MaterialID +
                ", CourseID=" + CourseID +
                ", SectionID=" + SectionID +
                ", MaterialName='" + MaterialName + '\'' +
                ", MaterialType='" + MaterialType + '\'' +
                ", MaterialFile=" + Arrays.toString(MaterialFile) +
                '}';
    }

    public int getMaterialID() {
        return MaterialID;
    }

    public void setMaterialID(int materialID) {
        MaterialID = materialID;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }

    public int getSectionID() {
        return SectionID;
    }

    public void setSectionID(int sectionID) {
        SectionID = sectionID;
    }

    public String getMaterialName() {
        return MaterialName;
    }

    public void setMaterialName(String materialName) {
        MaterialName = materialName;
    }

    public String getMaterialType() {
        return MaterialType;
    }

    public void setMaterialType(String materialType) {
        MaterialType = materialType;
    }

    public byte[] getMaterialFile() {
        return MaterialFile;
    }

    public void setMaterialFile(byte[] materialFile) {
        MaterialFile = materialFile;
    }
}
