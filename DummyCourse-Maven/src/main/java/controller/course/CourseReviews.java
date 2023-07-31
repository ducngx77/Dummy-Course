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
import java.util.*;

@WebServlet(name = "CourseReviews", urlPatterns = {"/course-reviews"})
public class CourseReviews extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int courseID = Integer.parseInt(request.getParameter("course-id"));
        Course course = new DAOCourse().getCourseByID(courseID);
        String courseName = course.getCourseName();

        request.setAttribute("course-id", courseID);
        request.setAttribute("courseName", courseName);

        List<Reviews> reviews = new DAOReviews().getReviewByCourseID(courseID);

        String startDateString = request.getParameter("start-date");
        String endDateString = request.getParameter("end-date");
        if (startDateString != null && !startDateString.isEmpty() && endDateString != null && !endDateString.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = dateFormat.parse(startDateString);
                Date endDate = dateFormat.parse(endDateString);

                // Xóa các đánh giá không nằm trong khoảng ngày lựa chọn
                Iterator<Reviews> iterator = reviews.iterator();
                while (iterator.hasNext()) {
                    Reviews review = iterator.next();
                    Date reviewDate = dateFormat.parse(review.getReviewDate());

                    if (reviewDate.before(startDate) || reviewDate.after(endDate)) {
                        iterator.remove();
                    }
                }
            } catch (ParseException e) {
                // Xử lý lỗi khi không thể phân tích ngày
                e.printStackTrace();
            }
        }
        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("CourseView/course-reviews-view.jsp").forward(request, response);

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
