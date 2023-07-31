package dao;

import entity.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
@author supersanta
*/
public class DAOUser extends DBContext {
    //UserID      int identity
    //        primary key,
    //    Username    varchar(50),
    //    Email       varchar(100),
    //    Password    varchar(100),
    //    PhoneNumber varchar(10),
    //    bio         varchar(1000),
    //    Address     varchar(50),
    //    Gender      int,
    //    Status      int

    // Insert username, email, password, phone number, address, gender, status
    public static final String INSERT_USER_SQL = "INSERT INTO Users (Username, Email, Password, PhoneNumber, Address, Gender, Status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String USERS_WHERE_USER_ID_SQL = "SELECT Username FROM Users WHERE UserID = ?";
    private static final String GET_USER = "SELECT * FROM [Users] WHERE username = ? AND password = ?";
    private static final String GET_BY_EMAIL = "SELECT * FROM [Users] WHERE email = ?";

    private static final String UPDATE_USER_PROFILE = " UPDATE [Users] SET " +
            "PhoneNumber = ? ,bio = ?,Address = ?,Gender = ? " +
            "WHERE UserID = ?";
    private static final String UPDATE_USER_SQL = "UPDATE [dbo].[users]\n" +
            "   SET [Password] = ? " +
            " WHERE email=?";
    private static final String GET_LECTURERS_WITH_OFFSET = "SELECT * FROM GetLecturersWithOffset(?);";

    private static final String GET_TOP_LECTURER_WITH_OFFSET = "SELECT *\n" +
            "FROM dbo.GetTopLecturersWithOffset(?, ?) ";

    public static void main(String[] args) {
//        ArrayList<User> lectures = new DAOUser().getTopLecturerWithOffset(0, 4);
        ArrayList<User> lectures = new DAOUser().getAllLecturer();
        DAOLectureDetails daoLD = new DAOLectureDetails();
        for (User lecture : lectures) {
//            if(daoLD.getLectureByUserID(lecture.getUserID()).isAccepted()){
            System.out.println(lecture);
//            }
        }

    }

    /**
     * Handle the login from user
     *
     * @param username username
     * @param password password
     * @return User
     */
    public static User Login(String username, String password) {
        DAOUser daoUser = new DAOUser();
        return daoUser.getUser(username, password);
    }

    /**
     * Send email forgot password
     *
     * @param email email
     * @return User
     */
    public static User sentEmailForgotPassword(String email) {
        DAOUser dao = new DAOUser();
        return dao.getUser(email);
    }

    /**
     * Check if an email address already existed in the database.
     *
     * @param email email
     * @return true or false based on the return of emailExist(String email)
     */
    public static boolean checkEmail(String email) {
        return new DAOUser().emailExist(email);
    }

    /**
     * Check if a username already existed in the database.
     *
     * @param username username
     * @return true or false based on the return of existedUser(String username)
     */
    public static boolean checkUsername(String username) {
        return new DAOUser().existedUser(username);
    }

    /**
     * register a new user in the system
     *
     * @param username    username
     * @param password    password
     * @param email       email
     * @param address     address
     * @param phoneNumber phone number
     * @param gender      gender
     * @return number of records inserted based on register(String username, String password, String email, String address, String phoneNumber, String gender)
     */
    public static int registerUsers(String username, String password, String email, String address, String phoneNumber, String gender, int status) {
        return new DAOUser().register(username, password, email, address, phoneNumber, gender, status);
    }

    public ArrayList<User> getTopLecturerWithOffset(int offset, int limit) {
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_TOP_LECTURER_WITH_OFFSET);
            statement.setInt(1, offset);
            statement.setInt(2, limit);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setBio(rs.getString("Bio"));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public ArrayList<User> getLecturersWithOffset(int offset) {
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_LECTURERS_WITH_OFFSET);
            preparedStatement.setInt(1, offset);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt("UserID"), rs.getString("Username"), rs.getString("Email"), rs.getString("PhoneNumber")));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get user by userId
     *
     * @param id userId
     * @return User or null if not found
     */
    public String getUsernameByID(int id) {
        String sql = USERS_WHERE_USER_ID_SQL;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString("Username");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Get user by email
     *
     * @param sql sql query
     * @return User or null if not found
     */
    private ArrayList<User> getUsers(String sql) {
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt("UserID"), rs.getString("Username"), rs.getString("Email"), rs.getString("PhoneNumber")));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<User> getUsersForAdmin(String sql) {
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt("UserID"), rs.getString("Username"), rs.getString("Email"), rs.getString("Password"), rs.getString("PhoneNumber"), rs.getString("bio"), rs.getString("Address"), rs.getInt("Gender"), rs.getInt("Status")));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get all Lecturers
     *
     * @return ArrayList<User>
     */
    public ArrayList<User> getAllLectures() {
        String sql = "select * from Users u join Role r on u.UserID = r.UserID where r.UserType = 'lecturer'";
        return getUsers(sql);
    }

    public ArrayList<User> getAllLecturer() {
        String sql = "select * from Users u, Role r where u.UserID = r.UserID and r.UserType = 'lecturer'";
        return getUsersForAdmin(sql);
    }

    public ArrayList<User> getAllStudent() {
        String sql = "select * from Users u, Role r where u.UserID = r.UserID and r.UserType = 'student'";
        return getUsersForAdmin(sql);
    }

    public ArrayList<User> getAllAdmin() {
        String sql = "select * from Users u, Role r where u.UserID = r.UserID and r.UserType = 'admin'";
        return getUsersForAdmin(sql);
    }

    /**
     * Interact with the database to query and return user information.
     *
     * @param username username
     * @param password password
     * @return User or null if not found
     */
    private User getUser(String username, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getInt(9));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Search and return user information based on email address.
     *
     * @param email email
     * @return User or null if not found
     */
    private User getUser(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_EMAIL);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                return new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * change the password based on email of the user
     *
     * @param email    email
     * @param password password
     * @return 1 ìf changed successfully or 0 if fault
     */
    public int changePassword(String email, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);
            if (preparedStatement.execute()) {
                return 1;
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Check if an email address already existed in the database.
     *
     * @param email email
     * @return true ìf email existed or false ìf email non-existed
     */
    private boolean emailExist(String email) {
        String sql = GET_BY_EMAIL;
        return executeSQLStatement(email, sql);
    }

    /**
     * Check if a username already existed in the database.
     *
     * @param username username
     * @return true ìf username existed or false ìf username non-existed
     */
    private boolean existedUser(String username) {
        String sql = "SELECT * FROM [Users] WHERE username = ?";
        return executeSQLStatement(username, sql);
    }

    private boolean executeSQLStatement(String username, String sql) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * register a new user in the system
     *
     * @param username    username
     * @param password    password
     * @param email       email
     * @param address     address
     * @param phoneNumber phoneNumber
     * @param gender      gender
     * @return number of records inserted or -1 if an error occurs during execution
     */
    private int register(String username, String password, String email, String address, String phoneNumber, String gender, int status) {
        int genderStatus = ("male".equals(gender)) ? 1 : 0;
        if ("+84".equals(phoneNumber.substring(0, 3))) {
            phoneNumber = "0" + phoneNumber.substring(3);
        }
        if (!existedUser(username) && !emailExist(email)) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);
                //Username, Email, Password, PhoneNumber, Address, Gender, Status
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, phoneNumber);
                preparedStatement.setString(5, address);
                preparedStatement.setInt(6, genderStatus);
                preparedStatement.setInt(7, status);


                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return -1;
    }

    public boolean updateUser(int userId, String address, String bio, String phone, int gender) {
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(UPDATE_USER_PROFILE);
            pre.setString(1, phone);
            pre.setString(2, bio);
            pre.setString(3, address);
            pre.setInt(4, gender);
            pre.setInt(5, userId);
            pre.executeUpdate();
            pre.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}