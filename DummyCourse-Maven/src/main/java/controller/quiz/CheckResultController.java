package controller.quiz;

import dao.DAOQuiz;
import dao.DAOQuizResult;
import dao.DAOQuizResultQuestion;
import entity.quiz.Quiz;
import entity.quiz.QuizResult;
import entity.quiz.QuizResultQuestion;
import entity.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author pthanh
 */
@WebServlet(name = "CheckResultController", urlPatterns = {"/check-result"})
public class CheckResultController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String go = request.getParameter("go");
        if ("get-quiz-attempts".equals(go)) {
            String quizID = request.getParameter("quiz-id");
            if (quizID == null) {
                request.getSession().setAttribute("error", "You have not taken any quiz or the quiz has not been created");
                request.getSession().setAttribute("page", "home");
            }
            request.setAttribute("quiz-id", quizID);
            String quizName = new DAOQuiz().getQuizByID(quizID).getQuizName();
            request.setAttribute("quiz-name", quizName);
            DAOQuiz dq = new DAOQuiz();
            request.setAttribute("course", dq.getCourseByQuizID(quizID));
            ArrayList<QuizResult> results = new DAOQuizResult().getAllQuizResultByStudentIDAbdQuizID(((User) request.getSession().getAttribute("user")).getUserID(), quizID);
            request.setAttribute("quiz-results", results);
            request.getRequestDispatcher("Quiz/viewQuizResultStudent.jsp").forward(request, response);
        } else if ("results".equals(go)) {

            String resultID = request.getParameter("result-id");
            String quizID = request.getParameter("quiz-id");
            String quizName = new DAOQuiz().getQuizByID(quizID).getQuizName();
            request.setAttribute("quiz-name", quizName);
            QuizResult quizResult = new DAOQuizResult().readQuizResult(resultID);

            request.setAttribute("result", (double) quizResult.getScore());
            request.setAttribute("quiz-id", quizID);
            DAOQuiz dq = new DAOQuiz();
            request.setAttribute("course", dq.getCourseByQuizID(quizID));

            ArrayList<QuizResultQuestion> quizResultQuestions = (ArrayList<QuizResultQuestion>) new DAOQuizResultQuestion().getAllQuizResultQuestionByResultId(resultID);
            request.setAttribute("quiz-result-questions", quizResultQuestions);

            request.getRequestDispatcher("Quiz/viewQuizResultStudentDetail.jsp").forward(request, response);


        } else {

            request.getSession().setAttribute("error", "You have not taken any quiz or the quiz has not been created");
            request.getSession().setAttribute("page", "home");
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
