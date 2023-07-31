package dao;

import entity.user.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAORole extends DBContext {

    public static void main(String[] args) {
        System.out.println(new DAORole().getRole("2"));
    }

    /**
     * Get role from the userID
     *
     * @param userID
     * @return Role
     */
    public static Role getRoleFromID(String userID) {
        return new DAORole().getRole(userID);
    }

    /**
     * Add the new Role into the database
     *
     * @param role
     * @return Role
     */
    public static int addUserRole(Role role) {
        return new DAORole().addRole(role);
    }

    /**
     * Interact with the database to query and return role information.
     *
     * @param userID
     * @return Role or null if not found
     */

    private Role getRole(String userID) {

        try {
            String sql = "SELECT * FROM [Role] WHERE userID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Role(rs.getInt(1), rs.getInt(2), rs.getString(3));
            }
        } catch (Exception e) {
            System.out.println("Error at getRole: " + e.getMessage());
        }
        return null;
    }

    /**
     * Interact with the database to query and insert a new role information.
     *
     * @param role
     * @return 1 if added successfully or 0 if fault
     */
    private int addRole(Role role) {
        try {
            String sql = "INSERT INTO [Role] VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, role.getUserID());
            ps.setString(2, role.getUserType());
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error at addRole: " + e.getMessage());
        }
        return 0;
    }
}
