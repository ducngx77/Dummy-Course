package controller.home;

import dao.DAOCourse;
import dao.DAOLectureDetails;
import dao.DAOUser;
import entity.course.Course;
import entity.user.LectureDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author pthanh
 */
@WebServlet(name = "AddCourse", urlPatterns = {"/add-courses"})
public class AddCoursesToBody extends HttpServlet {
    private static final int NEW_COURSES = 20;

    static void printCourses(ArrayList<Course> courses, PrintWriter out) {
        DecimalFormat df = new DecimalFormat("#.##");
        for (Course course : courses) {
            out.println("<div class=\"col-lg-3 col-md-4 col-sm-6 item mb-3\">\n" +
                    "<div class=\"wrap-course-item\">\n" +
                    "                            <div class=\"courseTitle\">\n" +
                    "                                <h5>\n" +
                    "                                       " + course.getCourseName() + "\n" +
                    "                                </h5>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"courseSum\">\n" +
                    "                                <p class=\"item-content description\">\n" +
                    "                                        " + course.getCourseDescription() + "\n" +
                    "                                </p>\n" +
                    "                                <p class=\"item-content\"><i class=\"fa-solid fa-chalkboard-user\"></i>\n" +
                    "                                        " + (new DAOUser().getUsernameByID(course.getLecturerID())) + "\n" +
                    "                                </p>\n" +
                    "                                <p class=\"item-content\" style=\"color: var(--mainColor)\"><i class=\"fa-solid fa-star\"></i><a href=\"course-reviews?course-id="+course.getCourseID()+"\">Rating:\n" +
                    "                                    " + df.format(new DAOCourse().getAverageRatingByCourseID(course.getCourseID())) + "\n" +

                    "                                </a>\n" +
                    "                                </p>\n" +
                    "                            </div>\n" +
                    "                            <a href=\"course-details?course-id=" + course.getCourseID() + "\"\n" +
                    "                               class=\"courseLink\">\n" +
                    "                                Go to course -->\n" +
                    "                            </a>\n" +
                    "                        </div>\n" +
                    "                    </div>");
        }
    }

    static void printSearchItem(ArrayList<Course> courses, ArrayList<LectureDetails> lecturers, PrintWriter out) {
        DecimalFormat df = new DecimalFormat("#.##");
        if (courses != null || lecturers != null){
            out.println("<nav class=\"popup-box\" id=\"search-bar-popup\">\n");
            if (courses != null) {
                out.println("<li><h6>Courses:</h6></li>");
                for (Course course : courses) {
                    out.println("<li class=\"option-content\">" +
                            " <a href=\"course-details?course-id="+course.getCourseID()+"\">\n" +
                            "                                <p>" + course.getCourseName()  + "</p>\n" +
                            "                            </a></li>");
                }
            }
            if (lecturers != null) {
                out.println("<li><h6>Lecturers:</h6></li>");
                DAOLectureDetails dld = new DAOLectureDetails();
                for (LectureDetails lecturer : lecturers) {
                    out.println("<li class=\"option-content\">" +
                            " <a href=\"lecturer-profile?lecturer-id="+lecturer.getLectureID()+"\">\n" +
                            "                                <p>" + dld.getUserByLecturerID(lecturer.getLectureID()).getUsername()  + "</p>\n" +
                            "                            </a></li>");
                }
            }
            out.println("</nav>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String go = request.getParameter("go");
        int offset = Integer.parseInt(request.getParameter("offset"));
        if ("add-courses".equals(go)) {

            ArrayList<Course> courses = new DAOCourse().getCoursesWithOffset(offset);

            try (PrintWriter out = response.getWriter()) {
                printCourses(courses, out);
            }
        }
        if ("add-courses-by-type".equals(go)) {
            int type = Integer.parseInt(request.getParameter("type"));
            ArrayList<Course> courses = new DAOCourse().getCourseByTypeWithOffset(type, offset, NEW_COURSES);

            try (PrintWriter out = response.getWriter()) {
                printCourses(courses, out);
            }
        }
        if ("get-course-by-type".equals(go)) {
            DecimalFormat df = new DecimalFormat("#.##");
            int typeID = Integer.parseInt(request.getParameter("type_id"));
            ArrayList<Course> courses = new DAOCourse().getCourseByTypeWithOffset(typeID, offset, NEW_COURSES);
            try (PrintWriter out = response.getWriter()) {
                printCourses(courses, out);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if ("search-course".equals(go)) {
            String keyword = request.getParameter("keyword");
            String type = request.getParameter("type");
            if ("all".equals(type)) {
                ArrayList<Course> courses = new DAOCourse().searchCourseWithOffset(keyword, offset, NEW_COURSES);
                try (PrintWriter out = response.getWriter()) {
                    printCourses(courses, out);
                }
            } else {
                int typeID = Integer.parseInt(type);
                ArrayList<Course> courses = new DAOCourse().searchCourseByTypeWithOffset(keyword, typeID, offset, NEW_COURSES);
                try (PrintWriter out = response.getWriter()) {
                    printCourses(courses, out);
                }
            }
        }
        if ("search-all".equals(go)) {
            String generalKey = request.getParameter("generalKey");
            if (generalKey != "") {
                ArrayList<Course> courses = new DAOCourse().searchCourseByGeneralKey(generalKey);
                if (courses.size() == 0){
                    courses = null;
                }
                ArrayList<LectureDetails> lecturers = new DAOLectureDetails().searchLectuerByName(generalKey);
                if (lecturers.size() == 0){
                    lecturers = null;
                }
                try (PrintWriter out = response.getWriter()) {
                    printSearchItem(courses, lecturers, out);
                }
            } else {
                try (PrintWriter out = response.getWriter()) {
                    printSearchItem(null, null, out);
                }
            }

        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
