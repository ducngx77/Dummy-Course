package controller.course;

import dao.*;
import entity.course.Course;
import entity.course.CourseType;
import entity.user.LectureDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pthanh
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,  // 1MB
        maxFileSize = 1024 * 1024 * 10,   // 10MB
        maxRequestSize = 1024 * 1024 * 50  // 50MB
)
@WebServlet(name = "UpdateCourse", urlPatterns = {"/updateCourse"})

public class UpdateCourse extends HttpServlet {

    public static final List<entity.course.CourseType> COURSE_TYPES = new DAOCourseType().getAllCourseType();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int courseID =Integer.parseInt(request.getParameter("courseID"));
        request.setAttribute("course", new DAOCourse().getCourseByID(courseID));
        DAOCourseType daoCT = new DAOCourseType();
        List<CourseType> listCoTy = daoCT.getAllCourseType();
        request.setAttribute("listCoTy", listCoTy);
        DAOLectureDetails daoLD = new DAOLectureDetails();
        List<LectureDetails> listLec = daoLD.getAllActivatedLecturers();
        request.setAttribute("listLec", listLec);
        request.getRequestDispatcher("CourseView/UpdateCourse.jsp").forward(request,response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String go = request.getParameter("go");
        DAOCourseType daoCT = new DAOCourseType();
        DAOLectureDetails daoLD = new DAOLectureDetails();
        int courseID =Integer.parseInt(request.getParameter("courseID"));
        request.setAttribute("course", new DAOCourse().getCourseByID(courseID));
        request.setAttribute("courseID", courseID);
        if (go.equals("updateCourse")){
            String newCourseName = request.getParameter("newCourseName");
            if (newCourseName.length( ) < 6 || !Character.isUpperCase(newCourseName.charAt(0))){
                request.setAttribute("ncnError", "Course Name must longer then 6 characters and start by an UpperCase");
                List<CourseType> listCoTy = daoCT.getAllCourseType();
                request.setAttribute("listCoTy", listCoTy);
                List<LectureDetails> listLec = daoLD.getAllActivatedLecturers();
                request.setAttribute("listLec", listLec);
                request.getRequestDispatcher("CourseView/UpdateCourse.jsp").forward(request,response);
            }
            String newCourseDesc = request.getParameter("newCourseDesc");
            if (newCourseDesc.length() < 6  || !Character.isUpperCase(newCourseDesc.charAt(0))){
                request.setAttribute("ncdError", "Course Description must longer then 6 characters and start by an UpperCase");
                List<CourseType> listCoTy = daoCT.getAllCourseType();
                request.setAttribute("listCoTy", listCoTy);
                List<LectureDetails> listLec = daoLD.getAllActivatedLecturers();
                request.setAttribute("listLec", listLec);
                request.getRequestDispatcher("CourseView/UpdateCourse.jsp").forward(request,response);
            }
            int newCourseTypeID =Integer.parseInt(request.getParameter("newCourseTypeID"));
            int newLecturerID =Integer.parseInt(request.getParameter("newLecturerID"));
            int newUserID = daoLD.getUserByLecturerID(newLecturerID).getUserID();
            int newStatus =Integer.parseInt(request.getParameter("newStatus")) ;
            DAOCourse daoC = new DAOCourse();
            Course c = new Course(courseID,newCourseTypeID, newCourseDesc, newCourseName, newUserID, newStatus);
            daoC.updateCourse(c);
            response.sendRedirect("course-details?course-id=" + courseID);
        } else if (go.equals("disableCourse")) {
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
