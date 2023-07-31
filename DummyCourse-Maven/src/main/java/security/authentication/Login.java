package security.authentication;

import dao.DAOCourse;
import dao.DAORole;
import dao.DAOUser;
import entity.course.Course;
import entity.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import static security.utils.Common.*;

/**
 * @author ADMIN
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class Login extends HttpServlet {


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
        if ("logout".equals(go)) {
            request.getSession().invalidate();
            response.sendRedirect("home");
            return;
        }
        request.getRequestDispatcher("LoginJSP/login.jsp").forward(request, response);
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
        handleLogin(request, response);
    }

    /**
     * Handles the login request. If the login is successful, the user will be redirected to the index page. Otherwise, the user will be redirected to the login page with an error message.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);
        if (username == null || password == null) {
            username = (String) request.getSession().getAttribute(USERNAME);
            password = (String) request.getSession().getAttribute(PASSWORD);
        }
        System.out.println(username + " " + password);
        User user = DAOUser.Login(username, password);
        if (user == null) {
            request.setAttribute(ERROR, "Login failed, username or password incorrect");
            request.getRequestDispatcher("LoginJSP/login.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("role", DAORole.getRoleFromID(Integer.toString(user.getUserID())));
            ArrayList<Course> topCourses = new DAOCourse().getTopCourses(user.getUserID());
            request.getSession().setAttribute("topCourses", topCourses);
            response.sendRedirect("home");
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
