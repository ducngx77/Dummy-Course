package controller.home;

import dao.DAOLectureDetails;
import dao.DAOUser;
import entity.user.User;
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
@WebServlet(name = "IterateLecturerList", urlPatterns = {"/iterate-lecturer"})
public class IterateLecturerList extends HttpServlet {
    private static final int NEW_TOP_COURSES = 4;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String go = request.getParameter("go");
        if ("iterate-lecturer".equals(go)) {
            DecimalFormat df = new DecimalFormat("#.##");
            int offset = Integer.parseInt(request.getParameter("offset"));
            ArrayList<User> lecturers = new DAOUser().getLecturersWithOffset(offset);
            try (PrintWriter out = response.getWriter()) {
                for (User lecturer : lecturers) {
                    out.println("<div class=\"col-lg-3 col-md-4 col-sm-6 item\">\n" +
                            "                        <div class=\"wrap-course-item\">\n" +
                            "                            <div class=\"courseTitle\">\n" +
                            "                                <h5>" + lecturer.getUsername() + "</h5>\n" +
                            "                            </div>\n" +
                            "                            <div class=\"courseSum\">\n" +
                            "\n" +
                            "                                <p class=\"item-content\">\n" +
                            "                                    <i class=\"item-header\">Experiences: </i>" + new DAOLectureDetails().getLectureByUserID(lecturer.getUserID()).getLectureDescription() + "\n" +
                            "                                </p>\n" +
                            "\n" +
                            "                            </div>\n" +
                            "                            <div class=\"row\">\n" +
                            "                                <div class=\"col-md-5 mr-auto mt-1 ml-2\">\n" +
                            "                                    <a href=\"lecturer-info?go=viewLecProf&lecturerID=${daoLecturer.getLectureByUserID(lecture.userID).lectureID}\" style=\"color: var(--mainColor)\">View Profile--></a>\n" +
                            "                                </div>\n" +
                            "                                <div class=\"col-md-6 ml-auto\">\n" +
                            "                                    <p><a style=\"color: var(--mainColor)\">Lecturer rating: " + df.format(new DAOLectureDetails().getAverageRating(new DAOLectureDetails().getLectureByUserID(lecturer.getUserID()).getLectureID())) + "</a></p>\n" +
                            "                                </div>\n" +
                            "                            </div>\n" +
                            "                        </div>\n" +
                            "                    </div>");
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
