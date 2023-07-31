package controller.home;

import dao.*;
import entity.course.Course;
import entity.course.CourseType;
import entity.user.LectureDetails;
import entity.user.Role;
import entity.user.SystemReview;
import entity.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pthanh
 */
@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

    private final ArrayList<Course> topCoursesWithOffset = new DAOCourse().getTopCoursesWithOffset(0, 3);
    private final ArrayList<User> lecturersWithOffset = new DAOUser().getLecturersWithOffset(0);
    private final ArrayList<User> allStudent = new DAOUser().getAllStudent();
    private final ArrayList<User> allLecturer = new DAOUser().getAllLecturer();
    private final ArrayList<User> allAdmin = new DAOUser().getAllAdmin();
    private final List<SystemReview> REVIEWS = new DAOSystemReview().getFiveRandomSystemReviews();
    //    public static final List<SystemReview> REVIEWS = new DAOSystemReview().getAllSystemReviews();
    private final List<CourseType> allCourseType = new DAOCourseType().getAllCourseType();
    private List<Course> allCourses;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Role role = (Role) request.getSession().getAttribute("role");
        allCourses = new DAOCourse().getCoursesWithOffset(0);
        if (role == null) {
            generalHome(request, response);
        } else if ("student".equals(role.getUserType())) {
            String go = request.getParameter("go");
            request.setAttribute("yourCourses", new DAOCourse().getCoursesByStudentID(role.getUserID()));
            if ("registered".equals(go)) {
                allCourses = new DAOStudentCourseAssignment().getEnrolledCourseByID(role.getUserID());
                request.setAttribute("allCourses", allCourses);
                request.setAttribute("courseTypes", allCourseType);

                request.getRequestDispatcher("Homepage/homeStudent.jsp").forward(request, response);
            } else
                generalHome(request, response);
        } else if ("lecturer".equals(role.getUserType())) {
            allCourses = new DAOCourse().getCoursesByLectureID(role.getUserID());
            request.setAttribute("allCourses", allCourses);
            request.setAttribute("courseTypes", allCourseType);
            User u = (User) request.getSession().getAttribute("user");
            request.setAttribute("reviews", new DAOLecturerReviews().getLectureReviewsByLectureID(u.getUserID()));
            request.getRequestDispatcher("Homepage/homeLecture.jsp").forward(request, response);
        } else if ("admin".equals(role.getUserType())) {
            request.setAttribute("allCourses", allCourses);
            request.setAttribute("courseTypes", new DAOCourseType().getAllCourseType());
            request.setAttribute("lectures", lecturersWithOffset);
            request.setAttribute("ctError", request.getParameter("ctError"));
            System.out.println(request.getParameter("ctError"));
            DAOLectureDetails daoLD = new DAOLectureDetails();
            ArrayList<User> inactivatedLecturers = new ArrayList<>();
            for (User u : allLecturer) {
                LectureDetails ld = daoLD.getLectureByUserID(u.getUserID());
                if (ld == null) {
                    System.out.println(u.getUsername());
                } else {
                    if (!ld.isAccepted()) {
                        inactivatedLecturers.add(u);
                    }
                }
            }
            request.setAttribute("inactivatedLecturers", inactivatedLecturers);
            request.setAttribute("students", allStudent);
            request.setAttribute("admins", allAdmin);
            request.getRequestDispatcher("Homepage/homeAdmin.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("error", "Can't find role with ID: " + role.getUserType());
            request.getSession().setAttribute("page", "login");
            response.sendRedirect("error.jsp");
        }
    }

    private void generalHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Course> topRatedCourses = new DAOCourse().getTopRatedCoursesWithOffset(0);
        request.setAttribute("topRatedCourses", topRatedCourses);
        request.setAttribute("allCourses", allCourses);
        request.setAttribute("lectures", lecturersWithOffset);
        request.setAttribute("reviews", new DAOSystemReview().getAllSystemReviews());
        request.setAttribute("courseTypes", allCourseType);
        request.getRequestDispatcher("Homepage/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Role role = (Role) request.getSession().getAttribute("role");
        if (role == null) {
            String go = request.getParameter("go");
            if ("relist-course".equals(go)) {
                String courseType = request.getParameter("course-type");
                allCourses = new DAOCourse().getCourseByType(courseType);
                generalHome(request, response);
            }
        } else if ("admin".equals(role.getUserType())) {
            String go = request.getParameter("go");
            if ("relist-course".equals(go)) {
                String courseType = request.getParameter("course-type");
                allCourses = new DAOCourse().getCourseByType(courseType);
                request.setAttribute("allCourses", allCourses);
                request.setAttribute("ctError", request.getParameter("ctError"));
                request.setAttribute("courseTypes", allCourseType);
                request.setAttribute("lectures", lecturersWithOffset);
                request.getRequestDispatcher("Homepage/homeAdmin.jsp").forward(request, response);
            }
        } else if ("lecturer".equals(role.getUserType())) {
            String go = request.getParameter("go");
            if ("relist-course".equals(go)) {
                String courseType = request.getParameter("course-type");
                allCourses = new DAOCourse().getCourseByTypeAndLectureID(courseType, role.getUserID());
                request.setAttribute("allCourses", allCourses);
                request.setAttribute("courseTypes", allCourseType);
                User u = (User) request.getSession().getAttribute("user");
                request.setAttribute("reviews", new DAOLecturerReviews().getLectureReviewsByLectureID(u.getUserID()));
                request.getRequestDispatcher("Homepage/homeLecture.jsp").forward(request, response);
            }
        } else if ("student".equals(role.getUserType())) {
            String go = request.getParameter("go");
            if ("relist-course".equals(go)) {
                String courseType = request.getParameter("course-type");
                allCourses = new DAOStudentCourseAssignment().getEnrolledCourseByIDAndCourseType(courseType, role.getUserID());
                request.setAttribute("allCourses", allCourses);
                request.setAttribute("courseTypes", allCourseType);
                request.getRequestDispatcher("Homepage/homeStudent.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("error", "Can't find action: " + go);
                request.getSession().setAttribute("page", "home");
                response.sendRedirect("error.jsp");
            }
        } else {
            request.getSession().setAttribute("error", "Can't find role with ID: " + role.getUserType());
            request.getSession().setAttribute("page", "login");
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
