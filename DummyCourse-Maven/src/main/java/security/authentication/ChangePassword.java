package security.authentication;

import dao.DAOUser;
import entity.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import security.utils.Utility;

import java.io.IOException;

import static security.utils.Common.*;

/**
 * @author ADMIN
 */
@WebServlet(name = "forgot-password", urlPatterns = {"/forgot-password"})
public class ChangePassword extends HttpServlet {


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
        String go = request.getParameter("go");
        if (go == null) {
            request.setAttribute("error", "Can't find what you looking for");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } else if (go.equals(FORGOT_PASSWORD)) {
            request.getSession().setAttribute("OTP", Utility.randomString());
            request.getRequestDispatcher("LoginJSP/forgot-password.jsp").forward(request, response);
        } else if (go.equals(POST_FORGOT_PASSWORD)) {
            request.getRequestDispatcher("LoginJSP/post-forgot-password.jsp").forward(request, response);
        }
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
        String go = request.getParameter("go");
        if (POST_FORGOT_PASSWORD.equals(go)) {
            handlePostForgotPassword(request, response);
        } else if (FORGOT_PASSWORD.equals(go)) {
            handleForgotPassword(request, response);
        }
    }

    private void handleForgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        User user = DAOUser.sentEmailForgotPassword(email);
        if (user == null || user.getStatus() == 0) {
            request.setAttribute("error", "Checking failed, email incorrect");
            request.getRequestDispatcher("LoginJSP/forgot-password.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "An email has sent. Check your email to get Password Change Link");
            request.setAttribute("link", user.getEmail());
            request.getRequestDispatcher("LoginJSP/forgot-password.jsp").forward(request, response);
        }
    }


    /**
     * Handles the login request. If the login is successful, the user will be redirected to the index page. Otherwise, the user will be redirected to the login page with an error message.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void handlePostForgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter(EMAIL);
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        HttpSession session = request.getSession();
        String OTPcheck = (String) session.getAttribute("OTP");
        String OTP = request.getParameter("OTP");
        DAOUser dao = new DAOUser();
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            request.setAttribute(ERROR, "Password must be not null");
            request.setAttribute(EMAIL, email);
            request.getRequestDispatcher(CHANGE_PASSWORD_JSP).forward(request, response);
        } else if (!newPassword.equals(confirmPassword)) {
            request.setAttribute(ERROR, "New password and confirm password not match");
            request.setAttribute(EMAIL, email);
            request.getRequestDispatcher(CHANGE_PASSWORD_JSP).forward(request, response);
        } else if (!newPassword.matches("[\\w\\d !@#$%^&*(),.:]{6,}")) {
            request.setAttribute(ERROR, "Password is not valid");
            request.setAttribute(EMAIL, email);
            request.getRequestDispatcher(CHANGE_PASSWORD_JSP).forward(request, response);
        } else if (!OTP.equals(OTPcheck)) {
            request.setAttribute(ERROR, "OTP not valid!");
            request.setAttribute(EMAIL, email);
            request.getRequestDispatcher(CHANGE_PASSWORD_JSP).forward(request, response);
        } else {
            dao.changePassword(email, newPassword);
            request.setAttribute(ERROR, "Password changed successfully");
            request.getRequestDispatcher(CHANGE_PASSWORD_JSP).forward(request, response);
        }
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
