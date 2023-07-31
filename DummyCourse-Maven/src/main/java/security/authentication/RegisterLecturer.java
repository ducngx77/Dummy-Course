package security.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static security.authentication.Register.handleRegister;

@WebServlet(name = "RegisterLecturer", urlPatterns = {"/register-lecturer"})
public class RegisterLecturer extends HttpServlet {


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
            request.getRequestDispatcher("LoginJSP/register-lecturer.jsp").forward(request, response);
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
        handleRegister(request, response, "lecturer");
        response.sendRedirect("profile");
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