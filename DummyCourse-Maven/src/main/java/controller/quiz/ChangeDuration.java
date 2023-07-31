package controller.quiz;

import java.io.IOException;
import java.io.PrintWriter;

import dao.DAOQuiz;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author pthanh
 */
@WebServlet(name = "ChangeDuration", urlPatterns = {"/change-duration"})
public class ChangeDuration extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String go = request.getParameter("go");
            DAOQuiz daoQ = new DAOQuiz();
            if(go.equals("changeDuration")){
                String quizID = request.getParameter("quizID");
                String mduration = request.getParameter("mduration");
                String sduration = request.getParameter("sduration");
                Long mdurationLong = Long.parseLong(mduration);
                Long sdurationLong = Long.parseLong(sduration);
                daoQ.updateDuration(mdurationLong,sdurationLong, quizID);
                response.setContentType("text/plain");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
