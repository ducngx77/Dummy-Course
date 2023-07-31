package controller.course;
import dao.*;
import entity.user.*;
import entity.course.Course;
import entity.course.CourseSection;
import entity.course.CourseType;
import entity.quiz.Materials;
import entity.quiz.Notice;
import entity.quiz.Quiz;
import entity.quiz.QuizResult;
import entity.user.Reviews;
import entity.user.Role;
import entity.user.SystemReview;
import entity.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet(name = "CourseItem", urlPatterns = {"/course-item"})
public class CourseItem extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int courseID = Integer.parseInt(request.getParameter("courseID"));
        Course course = new DAOCourse().getCourseByID(courseID);
        String courseName = course.getCourseName();
        request.setAttribute("courseID", courseID);
        request.setAttribute("courseName", courseName);
        request.getRequestDispatcher("CourseView/createSection.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int courseID = Integer.parseInt(request.getParameter("courseID"));
        Course course = new DAOCourse().getCourseByID(courseID);
        String courseName = course.getCourseName();
        request.setAttribute("courseID", courseID);
        request.setAttribute("courseName", courseName);
        String go = request.getParameter("go");
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createdDate = currentDateTime.format(formatter);
        if (go.equals("addSection")){
            String sectionName = request.getParameter("sectionName");
            if (!sectionName.equals("") && Character.isUpperCase(sectionName.charAt(0))){
                String sectionNotice = request.getParameter("sectionNotice");
                if (sectionNotice.length() > 6 && Character.isUpperCase(sectionNotice.charAt(0))){
                    DAOCourseSection daoCST = new DAOCourseSection();
                    daoCST.createCourseSection(new CourseSection(courseID, sectionName, createdDate, "", sectionNotice));
                    response.sendRedirect("course-details?course-id="+courseID);
                } else if (sectionNotice.equals("")){
                    DAOCourseSection daoCST = new DAOCourseSection();
                    daoCST.createCourseSection(new CourseSection(courseID, sectionName, createdDate, "", sectionNotice));
                    response.sendRedirect("course-details?course-id="+courseID);
                } else {
                    String addSecError = "SectionNotice must have at least 6 character and start by an Uppercase";
                    request.setAttribute("sectionNoticeError", addSecError);
                    request.setAttribute("sectionNotice", sectionNotice);
                    request.setAttribute("sectionName", sectionName);
                    request.getRequestDispatcher("CourseView/createSection.jsp").forward(request,response);
                }
            } else {
                String addSecError = "SectionName must start by an Uppercase";
                request.setAttribute("sectionNameError", addSecError);
                String sectionNotice = request.getParameter("sectionNotice");
                request.setAttribute("sectionNotice", sectionNotice);
                request.setAttribute("sectionName", sectionName);
                request.getRequestDispatcher("CourseView/createSection.jsp").forward(request,response);
            }
            response.sendRedirect("course-details?course-id="+courseID);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
