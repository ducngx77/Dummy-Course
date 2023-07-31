package controller.management;

import dao.*;
import entity.course.Course;
import entity.course.CourseSection;
import entity.course.CourseType;
import entity.user.SystemReview;
import entity.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AdminController", urlPatterns = {"/admin"})
public class AdminController extends HttpServlet {
    public static final ArrayList<Course> TOP_3_RATED = new DAOCourse().getTop3Rated();
    public static final ArrayList<User> LECTURES = new DAOUser().getAllLectures();
    public static final ArrayList<User> STUDENTS = new DAOUser().getAllStudent();
    public static final ArrayList<User> ADMINS = new DAOUser().getAllAdmin();
    public static final List<SystemReview> REVIEWS = new DAOSystemReview().getFiveRandomSystemReviews();
    public static final List<CourseType> COURSE_TYPES = new DAOCourseType().getAllCourseType();
    private List<Course> allCourses;

    public static boolean validateCourseName(String input) {
        if (input == null || input.isEmpty() || input.length() < 6) {
            return false;
        }

        String[] words = input.split("\\s+");
        for (String word : words) {
            char firstChar = word.charAt(0);
            if (!Character.isUpperCase(firstChar) && !Character.isDigit(firstChar)) {
                return false;
            }

            for (int i = 1; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (!Character.isLetterOrDigit(ch)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean validateCourseDescription(String input) {
        return input != null && input.length() >= 10 && input.length() <= 100;
    }

    public static boolean validateSectionName(String input) {
        if (input == null || input.isEmpty() || input.length() < 6) {
            return false;
        }
        // Split the section name into words
        String[] words = input.split("\\s+");

        for (String word : words) {
            // Check if each word starts with an uppercase letter and has at least 6 characters
            char firstChar = word.charAt(0);
            if (!Character.isUpperCase(firstChar) && !Character.isDigit(firstChar)) {
                return false;
            }

            for (int i = 1; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (!Character.isLetterOrDigit(ch)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validateSectionNotice(String input) {
        return input != null && input.length() >= 10 && input.length() <= 100;
    }

    public static boolean validateMaterialFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }

        // Extract the file extension from the file name
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        // Define the valid file extensions
        String[] validExtensions = {"rar", "zip", "png", "jpg", "jpeg", "docx", "xlsx", "pptx"};

        // Check if the file extension is in the valid extensions list
        for (String extension : validExtensions) {
            if (extension.equals(fileExtension)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String go = request.getParameter("go");

        DAOCourse daoCourse = new DAOCourse();
        ResultSet courseList = daoCourse.getData("select * from Courses");
        DAOCourseType daoCourseType = new DAOCourseType();
        ResultSet courseTypeList = daoCourseType.getData("select * from CourseType");
        DAOUser daoUser = new DAOUser();
        ResultSet lecturerList = daoUser.getData("select * from Users u join Role r on u.UserID = r.UserID where r.UserType = 'lecturer'");
        request.setAttribute("listCourse", courseList);
        request.setAttribute("listCourseType", courseTypeList);
        request.setAttribute("listLecturer", lecturerList);

        request.setAttribute("allCourses", allCourses);
        request.setAttribute("courseTypes", COURSE_TYPES);
        request.setAttribute("lectures", LECTURES);
        request.setAttribute("students", STUDENTS);
        request.setAttribute("admins", ADMINS);

        if (go == null) {
            go = "listAll";
        }
        if (go.equals("listAll")) {
            request.getRequestDispatcher("Homepage/homeAdmin.jsp").forward(request, response);
        } else if (go.equals("createCourse")) {
            request.getRequestDispatcher("Admin/CreateCourse.jsp").forward(request, response);
        } else if (go.equals("createSection")) {

            request.getRequestDispatcher("Admin/CreateSection.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String go = request.getParameter("go");
        String courseType = request.getParameter("course-type");
        allCourses = new DAOCourse().getCourseByType(courseType);
        request.setAttribute("allCourses", allCourses);
        request.setAttribute("courseTypes", COURSE_TYPES);
        request.setAttribute("lectures", LECTURES);
        request.setAttribute("students", STUDENTS);
        request.setAttribute("admins", ADMINS);
        if (go == null) {
            go = "listAll";
        }
        if (go.equals("listAll")) {
            DAOCourse daoCourse = new DAOCourse();
            ResultSet courseList = daoCourse.getData("select * from Courses");
            DAOCourseType daoCourseType = new DAOCourseType();
            ResultSet courseTypeList = daoCourseType.getData("select * from CourseType");
            DAOUser daoUser = new DAOUser();
            ResultSet lecturerList = daoUser.getData("select * from Users u join Role r on u.UserID = r.UserID where r.UserType = 'lecturer'");
            request.setAttribute("listCourse", courseList);
            request.setAttribute("listCourseType", courseTypeList);
            request.setAttribute("listLecturer", lecturerList);

            request.setAttribute("allCourses", allCourses);
            request.setAttribute("courseTypes", COURSE_TYPES);
            request.setAttribute("lectures", LECTURES);
            request.setAttribute("students", STUDENTS);
            request.setAttribute("admins", ADMINS);

            request.getRequestDispatcher("Homepage/homeAdmin.jsp").forward(request, response);
        } else if (go.equals("createCourse")) {
            String courseName = request.getParameter("coursename");
            int courseTypeID = Integer.parseInt(request.getParameter("coursetype"));
            int lecturerID = Integer.parseInt(request.getParameter("lecturer"));
            String courseDescription = request.getParameter("coursedescription");
            int status = request.getParameter("status") != null ? 1 : 0;


            if (validateCourseName(courseName) && validateCourseDescription(courseDescription)) {
                // Create a new course object with the input data
                Course newCourse = new Course();
                newCourse.setCourseName(courseName);
                newCourse.setCourseTypeID(courseTypeID);
                newCourse.setLecturerID(lecturerID);
                newCourse.setCourseDescription(courseDescription);
                newCourse.setStatus(status);

                DAOCourse daoCourse = new DAOCourse();
                daoCourse.createCourse(newCourse);


                Course course = daoCourse.getNewestCourse();
                int courseID = course.getCourseID();
                request.setAttribute("courseID", courseID);

                request.getRequestDispatcher("Admin/CreateSection.jsp").forward(request, response);

            } else {

                if (!validateCourseName(courseName)) {
                    request.setAttribute("msgCourseNameError", "Invalid course name.");
                }

                if (!validateCourseDescription(courseDescription)) {
                    request.setAttribute("msgCourseDescriptionError", "Invalid course description.");
                }

                request.getRequestDispatcher("Admin/CreateCourse.jsp").forward(request, response);
            }
        } else if (go.equals("createSection")) {
            DAOCourseSection daoCourseSection = new DAOCourseSection();

            int courseID = Integer.parseInt(request.getParameter("courseID"));
            String sectionName = request.getParameter("sectionName");
            String sectionNotice = request.getParameter("sectionNotice");

//            Part filePart = request.getPart("material");
//            String fileName = filePart.getSubmittedFileName();

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createdDate = currentDateTime.format(formatter);

//            String duration = request.getParameter("duration");
//            int status = request.getParameter("status") != null ? 1 : 0;

            if (validateSectionName(sectionName) && validateSectionNotice(sectionNotice)
//                    && validateMaterialFileExtension(fileName)
            ) {
                // Save the file
                // Assuming you have a method to save the file to the desired location
//                String filePath = saveFile(filePart);

                // Create a new section object with the input data
                CourseSection newSection = new CourseSection();
                newSection.setCourseID(courseID);
                newSection.setSectionName(sectionName);
                newSection.setSectionNotice(sectionNotice);
                newSection.setCreateDate(createdDate);
                newSection.setSectionDuration(null);
//                newSection.setSectionDuration(duration);
//                newSection.setMaterialPath(filePath);
//                newSection.setStatus(status);

                // Add the new section to the database using your DAO or ORM
                daoCourseSection.createCourseSection(newSection);


                response.sendRedirect("course-details?course-id="+courseID);

            } else {
                if (!validateSectionName(sectionName)) {
                    request.setAttribute("sectionNameError", "Invalid section name.");
                }
                if (!validateSectionNotice(sectionNotice)) {
                    request.setAttribute("sectionNoticeError", "Invalid section notice.");
                }
                request.getRequestDispatcher("Admin/CreateSection.jsp").forward(request, response);
            }
        }


    }
}