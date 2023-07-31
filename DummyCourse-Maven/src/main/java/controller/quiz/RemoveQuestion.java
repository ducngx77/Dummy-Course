package controller.quiz;

import dao.DAOQuizQuestion;
import dao.DAOQuizQuestionChoice;
import dao.DAOQuizResultAnswer;
import dao.DAOQuizResultQuestion;
import entity.quiz.QuizQuestionChoice;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author pthanh
 */
@WebServlet(name = "RemoveQuestion", urlPatterns = {"/remove-question"})
public class RemoveQuestion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String go = request.getParameter("go");
            if (go.equals("removeQuestion")) {
                String questionID = request.getParameter("questionID");
                DAOQuizQuestion daoQQ = new DAOQuizQuestion();
                DAOQuizQuestionChoice daoQQC = new DAOQuizQuestionChoice();
                daoQQC.deleteQuestionChoiceByQuestionID(questionID);
                List<QuizQuestionChoice> choiceList = daoQQC.getAllByQuestionId(questionID);
                DAOQuizResultAnswer daoQRA = new DAOQuizResultAnswer();
                for (QuizQuestionChoice choice : choiceList) {
                    int n = daoQRA.deleteQuizResultAnswerByChoiceID(choice.getChoiceId());
                }
                new DAOQuizResultQuestion().removeAllQuizResultQuestionByQuestionID(questionID);
                new DAOQuizQuestionChoice().deleteQuestionChoiceByQuestionID(questionID);
                daoQQ.deleteQuizQuestion(questionID);
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("Question and choices removed successfully");
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
