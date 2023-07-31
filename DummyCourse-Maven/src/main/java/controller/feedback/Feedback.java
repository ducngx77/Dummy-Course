package controller.feedback;

import dao.*;
import entity.user.LectureReview;
import entity.user.Reviews;
import entity.user.SystemReview;
import entity.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author pthanh
 */
@WebServlet(name = "Feedback", urlPatterns = {"/feedback"})

public class Feedback extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String go = request.getParameter("go");
        if (go.equals("sendSystemFeedback")) {
                request.setAttribute("systemFeedbackStatus", "notYet");
                request.getRequestDispatcher("FeedBack/systemFeedback.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String go = request.getParameter("go");
        int courseID = (request.getParameter("courseID" )!= null)  ? Integer.parseInt(request.getParameter("courseID")) : -1;
        String rawRating = request.getParameter("rating");
        int userID = Integer.parseInt(request.getParameter("userID"));
        String comment = request.getParameter("comment");
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = currentDateTime.format(formatter);
        int rating = Integer.parseInt(rawRating);
        if (go.equals("sendSystemFeedback")) {
            if (rating == 0 ){
                request.setAttribute("rateError", "Please choose your rating!");
                request.setAttribute("systemFeedbackStatus", "notYet");
                request.setAttribute("systemFeedBackComment", "comment");
                request.getRequestDispatcher("FeedBack/systemFeedback.jsp").forward(request,response);
            } else if ( 6 > comment.length() || comment.length() > 1000){
                request.setAttribute("cmtError", "Please keep your comment in 6 - 1000 character");
                request.setAttribute("systemFeedbackStatus", "notYet");
                request.setAttribute("systemFeedBackComment", comment);
                request.getRequestDispatcher("FeedBack/systemFeedback.jsp").forward(request,response);
            } else {
                DAOSystemReview daoSR = new DAOSystemReview();
                SystemReview sr = new SystemReview(userID, rating, comment, date);
                daoSR.addSystemReview(sr);
                request.setAttribute("systemFeedbackStatus", "done");
                request.getRequestDispatcher("FeedBack/systemFeedback.jsp").forward(request,response);
            }
        } else if (go.equals("sendCourseFeedback")) {
            request.setAttribute("courseID", courseID);
            request.setAttribute("course-id", courseID);
            if (rating == 0 ){
                String rateError = "Please choose your rating!";
                response.sendRedirect("course-details?course-id=" + courseID + "&courseFeedBackComment=" + comment + "&rateError=" + rateError);
                return;
            } else if ( 6 > comment.length() || comment.length() > 1000){
                String cmtError = "Please keep your comment in 6 - 1000 character";
                response.sendRedirect("course-details?course-id=" + courseID + "&courseFeedBackComment=" + comment + "&cmtError=" + cmtError);
                return;
            }
            DAOReviews daoReviews = new DAOReviews();
            Reviews review = new Reviews(courseID, userID, rating, comment, date);
            daoReviews.addReviews(review);
            String courseFeedBackStatus = "Thank you for your feedback";
            response.sendRedirect("course-details?course-id=" + courseID + "&courseID=" + courseID + "&courseFeedBackStatus=" + courseFeedBackStatus);

        } else if (go.equals("sendLecturerFeedback")){
            if(request.getSession().getAttribute("user") == null){
                response.sendRedirect("login");
            }
            int lecturerID = Integer.parseInt(request.getParameter("lecturerID"));
            DAOLectureDetails daoLectureDetails = new DAOLectureDetails();
//            int userLID = daoLectureDetails.getUserByLecturerID(lecturerID).getUserID();
            request.setAttribute("lecturerID", lecturerID);
            if (rating == 0 ){
                String rateError = "Please choose your rating!";
                response.sendRedirect("lecturer-info?go=viewLecProf&lecturerID=" + lecturerID + "&lecturerFeedBackComment=" + comment + "&rateError=" + rateError);
                return;
            } else if ( 6 > comment.length() || comment.length() > 1000){
                String cmtError = "Please keep your comment in 6 - 1000 character";
                response.sendRedirect("lecturer-info?go=viewLecProf&lecturerID=" + lecturerID + "&lecturerFeedBackComment=" + comment + "&cmtError=" + cmtError);
                return;
            }
            LectureReview lectureReview = new LectureReview(lecturerID,userID,rating,comment,date);
            DAOLecturerReviews daoLecturerReviews = new DAOLecturerReviews();
            daoLecturerReviews.addLectureReview(lectureReview);
            String lecturerFeedBackStatus = "Thank you for your feedback";
            response.sendRedirect("lecturer-info?go=viewLecProf&lecturerID=" + lecturerID + "&lecturerFeedBackStatus=" + lecturerFeedBackStatus);

        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
