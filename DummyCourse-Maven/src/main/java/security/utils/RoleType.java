package security.utils;

public enum RoleType {
    ADMIN(RoleType.adminType), STUDENT(RoleType.studentType), LECTURER(RoleType.lecturerType), GUEST(RoleType.guestType);
    static final String adminType = "admin";
    static final String studentType = "student";
    static final String lecturerType = "lecturer";
    static final String guestType = "guest";

    final String roleType;

    RoleType(String roleType) {
        this.roleType = roleType;
    }

    public RoleType getRoleType(String userType) {
        switch (userType) {
            case adminType:
                return ADMIN;
            case studentType:
                return STUDENT;
            case lecturerType:
                return LECTURER;
            default:
                return GUEST;
        }
    }

    public String getRoleType() {
        switch (this) {
            case ADMIN:
                return adminType;
            case STUDENT:
                return studentType;
            case LECTURER:
                return lecturerType;
            default:
                return guestType;
        }
    }
}
