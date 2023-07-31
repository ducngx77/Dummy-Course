package controller.home;

import dao.DAOCourse;
import entity.course.Course;
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
@WebServlet(name = "IterateTopCourses", urlPatterns = {"/iterate-top-courses"})
public class IterateTopCourses extends HttpServlet {
    private static final int NEW_TOP_COURSES = 4;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String go = request.getParameter("go");
        if ("iterate-top-courses".equals(go)) {
            DecimalFormat df = new DecimalFormat("#.##");
            int offset = Integer.parseInt(request.getParameter("offset"));
            ArrayList<Course> courses = new DAOCourse().getTopCoursesWithOffset(offset, NEW_TOP_COURSES);
            try (PrintWriter out = response.getWriter()) {
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
                            "                                        " + new DAOCourse().getLectureName(course.getCourseID()).getUsername() + "\n" +
                            "                                </p>\n" +
                            "                                <p class=\"item-content\" style=\"color: var(--mainColor)\"><i class=\"fa-solid fa-star\"></i><a href=\"course-reviews?course-id="+course.getCourseID()+"\">Rating:\n" +
//                    "                                    <fmt:formatNumber\n" +
//                    "                                            value=\"" +
                            "                                            " + df.format(new DAOCourse().getAverageRatingByCourseID(course.getCourseID())) + "\n" +
//                    "                                            pattern=\"#.##\"/>\n" +
                            "                                </a>\n" +
                            "                                </p>\n" +
                            "                            </div>\n" +
                            "                            <a href=\"course-details?course-id=" + course.getCourseID() + "\"\n" +
                            "                               class=\"courseLink\">\n" +
                            "                                Go to course -->\n" +
                            "                            </a>\n" +
                            "                        </div>\n"  +
                            "                    </div>");

//                    out.println("<div class=\"col-lg-3 col-md-4 col-sm-6 item\">\n" +
//                            "                        <div class=\"wrap-course-item\">\n" +
//                            "                            <div class=\"courseTitle\">\n" +
//                            "                                <h5>\n" +
//                            "                                        " + course.getCourseName() + "\n" +
//                            "                                </h5>\n" +
//                            "                            </div>\n" +
//                            "                            <div class=\"courseSum\">\n" +
//                            "                                <p class=\"item-content\"><i class=\"item-header\">Description: </i>\n" +
//                            "                                        " + course.getCourseDescription() + "\n" +
//                            "                                </p>\n" +
//                            "                                <p class=\"item-content\"><i class=\"item-header\">Lecturer: </i>\n" +
//                            "                                        " + new DAOCourse().getLectureName(course.getCourseID()).getUsername() + "\n" +
//                            "                                </p>\n" +
//                            "                            </div>\n" +
//                            "                            <div class=\"row\">\n" +
//                            "                                <div class=\"col-md-6 mr-auto mt-1\">\n" +
//                            "                                    <a href=\"course-details?course-id=" + course.getCourseID() + "\"\n" +
//                            "                                       class=\"courseLink\">\n" +
//                            "                                        Go to course -->\n" +
//                            "                                    </a>\n" +
//                            "                                </div>\n" +
//                            "                                <div class=\"col-md-4 ml-auto\">\n" +
//                            "                                    <p><a style=\"color: var(--mainColor)\">Rating:\n" +
//                            "                                        " + df.format(new DAOCourse().getAverageRatingByCourseID(course.getCourseID())) +
//                            "                                    </a>\n" +
//                            "                                    </p>\n" +
//                            "                                </div>\n" +
//                            "                            </div>\n" +
//                            "                        </div>\n" +
//                            "                    </div>");
                }
            } catch (IOException e) {
                e.printStackTrace();
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
