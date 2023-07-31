package controller.user;

import dao.DAOCourse;
import dao.DAOLectureDetails;
import dao.DAOLecturerReviews;
import entity.course.Course;
import entity.user.LectureDetails;
import entity.user.LectureReview;
import entity.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LecturerInfoController", urlPatterns = {"/lecturer-info"})
public class LecturerInfoController extends HttpServlet {
    public static void checkLecturerID(HttpServletRequest request, HttpServletResponse response, int lecturerUserID) throws IOException {
        if (lecturerUserID == -1) {
            request.getSession().setAttribute("error", "Lecturer not found");
            request.getSession().setAttribute("page", "home");
            response.sendRedirect("error.jsp");
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String go = request.getParameter("go");
        if (go.equals("delete")) {
            int lecturerUserID = (request.getParameter("lecturer-user-id") != null) ? Integer.parseInt(request.getParameter("lecturer-user-id")) : -1;
            checkLecturerID(request, response, lecturerUserID);

            LectureDetails lectureDetails = new DAOLectureDetails().getLectureByUserID(lecturerUserID);
            if (lectureDetails != null) {
                new DAOLectureDetails().deleteLecturer(lectureDetails.getLectureID());
            }
            response.sendRedirect("home");
            return;
        }
        if (go.equals("view-details")) {
            int lecturerUserID = (request.getParameter("lecturer-user-id") != null) ? Integer.parseInt(request.getParameter("lecturer-user-id")) : -1;
            checkLecturerID(request, response, lecturerUserID);

            response.sendRedirect("profile?go=view-lecturer-details&lecturer-user-id=" + lecturerUserID);
            return;
        }
        if (go.equals("activate")) {
            String rid = request.getParameter("userActId");
            if (!rid.equals("")) {
                int actId = Integer.parseInt(rid);
                DAOLectureDetails daoLD = new DAOLectureDetails();
                daoLD.activateLecturer(actId);
                response.sendRedirect("home");
            }
        } else if (go.equals("viewLecProf")) {
            int lecturerID = Integer.parseInt(request.getParameter("lecturerID"));
            System.out.println(lecturerID);
            ArrayList<LectureReview> LecturerReviews = new DAOLecturerReviews().getLectureReviewsByLectureID(lecturerID);
            DAOCourse daoCourse = new DAOCourse();
            List<Course> listCourse = daoCourse.get4CoursesByLectureID(lecturerID);
            request.setAttribute("listCourse", listCourse);
            String lecturerFeedBackComment = request.getParameter("lecturerFeedBackComment");
            String rateError = request.getParameter("rateError");
            String cmtError = request.getParameter("cmtError");
            request.setAttribute("lecturerFeedBackComment", lecturerFeedBackComment);
            request.setAttribute("rateError", rateError);
            request.setAttribute("cmtError", cmtError);
            User userLec = new DAOLectureDetails().getUserByLecturerID(lecturerID);
            LectureDetails lecturer = new DAOLectureDetails().getLectureByUserID(userLec.getUserID());
            request.setAttribute("LecturerReviews", LecturerReviews);
            request.setAttribute("userLec", userLec);
            request.setAttribute("lecturer", lecturer);
            request.getRequestDispatcher("User/LecturerProfile.jsp").forward(request, response);
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
