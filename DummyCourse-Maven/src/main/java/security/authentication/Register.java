package security.authentication;

import dao.DAOLectureDetails;
import dao.DAORole;
import dao.DAOUser;
import entity.user.LectureDetails;
import entity.user.Role;
import entity.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import security.utils.Utility;

import java.io.IOException;

import static security.utils.Common.*;

/**
 * @author ADMIN
 */
@WebServlet(name = "register", urlPatterns = {"/register"})
public class Register extends HttpServlet {


    /**
     * Handle register request from register.jsp page and redirect to login.jsp page if register success or register.jsp page if register fail
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public static void handleRegister(HttpServletRequest request, HttpServletResponse response, String useRole) throws ServletException, IOException {
        String username = request.getParameter("userName");
        String password = request.getParameter(PASSWORD);
        String email = request.getParameter(EMAIL);
        String confirmPassword = request.getParameter("confirmPassword");
        String address = request.getParameter(ADDRESS);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String gender = request.getParameter(GENDER);

        if (!Utility.checkNotNull(username, password, email, confirmPassword, address, phoneNumber, gender)) {
            request.setAttribute("info-err", "Please fill all the fields");
            setRegisterAttributes(username, email, address, phoneNumber, gender, request);
            request.getRequestDispatcher("LoginJSP/register.jsp").forward(request, response);
            return;
        }

        if (checkValidRegisterInfo(username, password, confirmPassword, email, address, phoneNumber, request) > 0) {
            setRegisterAttributes(username, email, address, phoneNumber, gender, request);
            request.getRequestDispatcher("LoginJSP/register.jsp").forward(request, response);
            return;
        }

        DAOUser.registerUsers(username, password, email, address, phoneNumber, gender, useRole.equals("student") ? 1 : 0);
        User user = DAOUser.Login(username, password);
        Role role = new Role(user.getUserID(), useRole);
        DAORole.addUserRole(role);
        System.out.println("Register success with username: " + username + " password: " + password);
        request.getSession().setAttribute(USERNAME, username);
        request.getSession().setAttribute(PASSWORD, password);

        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("role", role);

        if (useRole.equals("lecturer")) {
            LectureDetails lectureDetails = new LectureDetails();
            lectureDetails.setUserID(user.getUserID());
            lectureDetails.setAccepted(false);
            lectureDetails.setLectureDescription("");
            lectureDetails.setLectureProfile(null);
            new DAOLectureDetails().insertLecturer(lectureDetails);
        }

    }

    /**
     * Set attributes for register page to retain user inputted information
     *
     * @param username    username
     * @param email       email
     * @param address     address
     * @param phoneNumber phone number
     * @param request     request
     */
    private static void setRegisterAttributes(String username, String email, String address, String phoneNumber, String gender, HttpServletRequest request) {
        request.setAttribute(USERNAME, username);
        request.setAttribute(EMAIL, email);
        request.setAttribute(ADDRESS, address);
        request.setAttribute(PHONE_NUMBER, phoneNumber);
        request.setAttribute(GENDER, gender);
    }

    /**
     * Handle forgot password request from forgot-password.jsp page and redirect to login.jsp page if register success or forgot-password.jsp page if register fails.
     * If any error occurs, the error message will be displayed on the forgot-password.jsp page by using request.setAttribute(ERROR, errorMessage)
     *
     * @param username        username of user
     * @param password        password of user
     * @param confirmPassword confirm password of user
     * @param email           email of user
     * @param address         address of user
     * @param phoneNumber     phone number of user
     * @param request         request from client
     * @return number of invalid information
     */
    private static int checkValidRegisterInfo(String username, String password, String confirmPassword, String email, String address, String phoneNumber, HttpServletRequest request) {
        int i = 0;
        if (!Utility.usernameValidation(username)) {
            request.setAttribute(USERNAME_ERR, Utility.INVALID_USERNAME_MESSAGE);
            i++;
        }
        if (!Utility.passwordValidation(password)) {
            request.setAttribute(ERROR, Utility.INVALID_PASSWORD_MESSAGE);
            i++;
        }
        if (!Utility.emailValidation(email)) {
            request.setAttribute(EMAIL_ERR, Utility.INVALID_EMAIL_MESSAGE);
            i++;
        }
        if (!Utility.addressValidation(address)) {
            request.setAttribute(ADDRESS_ERR, Utility.INVALID_ADDRESS_MESSAGE);
            i++;
        }
        if (!Utility.phoneNumberValidation(phoneNumber)) {
            request.setAttribute(PHONE_NUMBER_ERR, Utility.INVALID_PHONE_NUMBER_MESSAGE);
            i++;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute(ERROR, "Password and confirm password not match");
            i++;
        }

        if (DAOUser.checkUsername(username)) {
            request.setAttribute(EXIST_USERNAME, "Username already exists");
            i++;
        }

        if (DAOUser.checkEmail(email)) {
            request.setAttribute(EXIST_EMAIL, "Email already exists");
            i++;
        }

        return i;
    }

    /**
     * Handles the HTTP <code>GET</code> method. This method is called whenever a request is sent to the servlet by the client, unless the request method is <code>POST</code>.
     * The parameter "go" is used to determine which page to display.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("LoginJSP/register.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method. This method is called whenever a request is sent to the servlet by the client, unless the request method is <code>GET</code>.
     * The parameter "go" is used to determine which login function is chosen. The parameter "go" can be "login", "register", "forgot-password", or "changePassword".
     * If the parameter "go" is "login", the user will be logged in. If the parameter "go" is "register", the user will be registered. If the parameter "go" is "forgot-password", the user will be sent a new password. If the parameter "go" is "changePassword", the user will be able to change their password.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRegister(request, response, "student");
        request.getRequestDispatcher("login").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
