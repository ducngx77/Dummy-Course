package controller.quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import dao.*;
import entity.course.Course;
import entity.course.CourseSection;
import entity.quiz.Quiz;
import entity.quiz.QuizQuestion;
import entity.quiz.QuizQuestionChoice;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author pthanh
 */
@WebServlet(name = "QuizDetails", urlPatterns = {"/quiz-details"})
public class QuizDetails extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String go = request.getParameter("go");
            String quizID = request.getParameter("quizID");
            DAOQuiz daoQ = new DAOQuiz();
            DAOCourse daoC = new DAOCourse();
            Course course = daoC.getCourseByID(daoQ.getCourseByQuizID(quizID).getCourseID());
            request.setAttribute("courseID", course.getCourseID());
            request.setAttribute("courseName", course.getCourseName());
            request.setAttribute("course", course);
            DAOCourseSection daoCS = new DAOCourseSection();
            DAOQuizQuestion daoQQ = new DAOQuizQuestion();
            DAOQuizQuestionChoice daoQQC = new DAOQuizQuestionChoice();
            if(go == null){
                Quiz quiz = daoQ.getQuizByID(quizID);
                LocalTime lt = quiz.getDuration();
                int mduration = lt.getMinute();
                int sduration = lt.getSecond();
                CourseSection section = daoCS.getCourseSectionbyID(quiz.getSectionID());
                ArrayList<QuizQuestion> questionList = daoQQ.getQuizQuestionsByQuizID(quiz.getQuizID());
                List<QuizQuestionChoice> questionChoiceList = daoQQC.getAll();
                request.setAttribute("quiz", quiz);
                request.setAttribute("mduration", mduration);
                request.setAttribute("sduration", sduration);
                request.setAttribute("section", section);
                request.setAttribute("questionList", questionList);
                request.setAttribute("questionChoiceList", questionChoiceList);
                request.getRequestDispatcher("QuizManagement/QuizDetailsManagerment.jsp").forward(request,response);
            }
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
