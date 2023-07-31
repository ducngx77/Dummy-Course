package controller.course;

import dao.*;
import entity.course.Course;
import entity.course.CourseSection;
import entity.quiz.Materials;
import entity.quiz.Notice;
import entity.quiz.Quiz;
import entity.quiz.QuizResult;
import entity.user.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author pthanh
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,  // 1MB
        maxFileSize = 1024 * 1024 * 10,   // 10MB
        maxRequestSize = 1024 * 1024 * 50  // 50MB
)
@WebServlet(name = "CourseType", urlPatterns = {"/courseType"})

public class CourseType extends HttpServlet {

    public static final List<entity.course.CourseType> COURSE_TYPES = new DAOCourseType().getAllCourseType();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        /* TODO output your page here. You may use following sample code. */
        String go = request.getParameter("go");
        if (go.equals("add")){
            String ctName = request.getParameter("ctName");
            if (!ctName.equals("")) {
                if ( Character.isUpperCase(ctName.charAt(0))) {
                    DAOCourseType daoCT = new DAOCourseType();
                    daoCT.createCourseType(ctName);
                    response.sendRedirect("home");
//                    request.getRequestDispatcher("/home").forward(request, response);
                } else if ( !Character.isUpperCase(ctName.charAt(0))) {
                    String ctError = "First char must be Uppercase";
                    request.setAttribute("ctError", ctError);
                    response.sendRedirect("home");
//                    request.getRequestDispatcher("/home").forward(request, response);
                }
            } else {
                String ctError = "Invalid input";
                request.setAttribute("ctError", ctError);
                response.sendRedirect("home");
//                    request.getRequestDispatcher("/home").forward(request, response);
            }
        } else if (go.equals("update")){
            String ctName = request.getParameter("newCtName");
            String rctId = request.getParameter("typeId");
            if (!rctId.equals("")){
                int ctId = Integer.parseInt(rctId);
                if (!ctName.equals("")) {
                    if ( Character.isUpperCase(ctName.charAt(0))) {
                        entity.course.CourseType ct = new entity.course.CourseType(ctId,ctName);
                        DAOCourseType daoCT = new DAOCourseType();
                        daoCT.updateCourseType(ct);
                        response.sendRedirect("home");
//                    request.getRequestDispatcher("/home").forward(request, response);
                    } else if ( !Character.isUpperCase(ctName.charAt(0))) {
                        String ctError = "First char must be Uppercase";
                        request.setAttribute("ctError", ctError);
                        response.sendRedirect("home");
//                    request.getRequestDispatcher("/home").forward(request, response);
                    }
                } else {
                    String ctError = "Invalid input";
                    request.setAttribute("ctError", ctError);
                    response.sendRedirect("home");
//                    request.getRequestDispatcher("/home").forward(request, response);
                }
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
