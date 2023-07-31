package dao;

import entity.quiz.Materials;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOMaterials extends DBContext {
    public static void main(String[] args) {
        DAOMaterials dao = new DAOMaterials();
    }

    /**
     * @param material
     * @return 1 or 0 if can't add
     */
    public int addMaterials(Materials material) {
        String sql = "INSERT INTO [dbo].[Materials]\n" +
                "           ([CourseID]\n" +
                "           ,[SectionID]\n" +
                "           ,[MaterialName]\n" +
                "           ,[MaterialType]\n" +
                "           ,[MaterialFile])\n" +
                "     VALUES\n" +
                "           (?,?,?,?,?)";
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, material.getCourseID());
            pre.setInt(2, material.getSectionID());
            pre.setString(3, material.getMaterialName());
            pre.setString(4, material.getMaterialType());
            pre.setBytes(5, material.getMaterialFile());
            pre.executeUpdate();
            pre.close();
            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     * @param materialID
     * @return Material object or null if can't get
     */
    public Materials getMaterialsByMaterialID(int materialID) {
        Materials material = null;
        String sql = "SELECT [MaterialID]\n" +
                "      ,[CourseID]\n" +
                "      ,[SectionID]\n" +
                "      ,[MaterialName]\n" +
                "      ,[MaterialType]\n" +
                "      ,[MaterialFile]\n" +
                "  FROM [dbo].[Materials] where MaterialID=?";
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, materialID);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return new Materials(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getBytes(6));
            }
            pre.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param courseID
     * @return material or null if can't get
     */
    public Materials getMaterialsByCourseID(int courseID) {
        Materials material = null;
        String sql = "SELECT [MaterialID]\n" +
                "      ,[CourseID]\n" +
                "      ,[SectionID]\n" +
                "      ,[MaterialName]\n" +
                "      ,[MaterialType]\n" +
                "      ,[MaterialFile]\n" +
                "  FROM [dbo].[Materials] where CourseID=?";
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, courseID);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return new Materials(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getBytes(6));
            }
            pre.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param materialID
     * @param courseID
     * @param materialName
     * @param materialType
     * @param materialFile
     * @return 0 or 1 if can't update
     */
    public int updateMaterial(int materialID, int courseID, int sectionID, String materialName, String materialType, byte[] materialFile) {
        String sql = "UPDATE [dbo].[Materials]\n" +
                "   SET [CourseID] = ?" +
                "      ,[SectionID]   = ?" +
                "      ,[MaterialName] = ?" +
                "      ,[MaterialType] = ?" +
                "      ,[MaterialFile] = ?" +
                " WHERE MaterialID = ?";
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, courseID);
            pre.setInt(2, sectionID);
            pre.setString(3, materialName);
            pre.setString(4, materialType);
            pre.setBytes(5, materialFile);
            pre.setInt(6, materialID);
            pre.executeUpdate();
            pre.close();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * @param materialID
     * @return 0 or -1 if can't remove
     */
    public int deleteMaterials(int materialID) {
        String sql = "delete from Materials where MaterialID=?";
        PreparedStatement pre = null;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, materialID);
            pre.executeUpdate();
            pre.close();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Materials> getAllMaterialsByCourse(int courseID) {
        List<Materials> materialsList = new ArrayList<>();
        String sql = "Select MaterialID, CourseID, SectionID ,MaterialName, MaterialType, MaterialFile from Materials where CourseID=?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, courseID);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int materialID = rs.getInt(1);
                int sectionID = rs.getInt(3);
                String materialName = rs.getString(4);
                String materialType = rs.getString(5);
                byte[] materialFile = rs.getBytes("MaterialFile");
                materialsList.add(new Materials(materialID, courseID, sectionID, materialName, materialType, materialFile));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return materialsList;
    }

    public List<Materials> getAllFile(String sql) {
        List<Materials> materialList = new ArrayList<>();


        try {
            // Create a statement object
            Statement stmt = connection.createStatement();

            // Execute the SQL query
            ResultSet rs = stmt.executeQuery(sql);

            // Loop through the result set and create FileHandleTest objects
            while (rs.next()) {
                int materialID = rs.getInt(1);
                int CourseID = rs.getInt(2);
                int sectionID = rs.getInt(3);
                String fileName = rs.getString(4);
                String fileType = rs.getString(5);
                byte[] materialFile = rs.getBytes("MaterialFile");

                // Create a new FileHandleTest object and set its properties
                Materials material = new Materials(materialID, CourseID, sectionID, fileName, fileType, materialFile);

                // Add the file object to the list
                materialList.add(material);
            }

        } catch (SQLException e) {
            System.out.println("Error getting all files: " + e.getMessage());
            return null;
        }
        return materialList;
    }
}
