package entity.user;

public class Role {

    private int roleID;
    private int userID;
    private String userType;

    public Role(int userID, String userType) {
        this.userID = userID;
        this.userType = userType;
    }

    public Role(int roleID, int userID, String userType) {
        this.roleID = roleID;
        this.userID = userID;
        this.userType = userType;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleID=" + roleID +
                ", userID=" + userID +
                ", userType='" + userType + '\'' +
                '}';
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
