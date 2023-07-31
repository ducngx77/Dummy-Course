package controller.management;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import dao.*;
import entity.course.Course;
import entity.course.CourseSection;
import entity.course.CourseType;
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
@WebServlet(name = "AddQuestionFrombank", urlPatterns = {"/add-question-from-bank"})
public class AddQuestionFromBank extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String go = request.getParameter("go");
            String quizID = request.getParameter("quizID");
            String courseID = request.getParameter("courseID");
            String sectionID = request.getParameter("sectionID");
            if(go == null){
                DAOCourseType daoCT = new DAOCourseType();
                DAOCourse daoC = new DAOCourse();
                DAOQuiz daoQ = new DAOQuiz();
                DAOQuizQuestion daoQQ = new DAOQuizQuestion();
                DAOCourseSection daoCS = new DAOCourseSection();
                List<CourseSection> sectionList = daoCS.getAllCourseSectionbyCourseID(Integer.parseInt(courseID));
                // get the quiz:
                Quiz quiz = daoQ.getQuizByID(quizID);
                // get the course by CourseID
                Course course = daoC.getCourseByID(Integer.parseInt(courseID));
                // get the course Type
                CourseType courseType = daoCT.getCourseTypeByID(course.getCourseTypeID());
                List<QuizQuestion> questionList;
                List<String> questionListCurrentQuiz = new ArrayList<String>();
                for (QuizQuestion question : daoQQ.getQuizQuestionsByQuizID(quizID)){
                    questionListCurrentQuiz.add(question.getQuestionText());
                }
                if(sectionID != null){
                    int sectionIDint = Integer.parseInt(sectionID);
                    questionList = daoQQ.getAllQuizQuestionsInCourseFilterBySection(courseType.getTypeName(), sectionIDint);
                } else {
                    questionList = daoQQ.getAllQuizQuestionsInCourseFilterBySection(courseType.getTypeName(), quiz.getSectionID());
                }
                request.setAttribute("quiz", quiz);
                request.setAttribute("sectionID", sectionID);
                request.setAttribute("questionList", questionList);
                request.setAttribute("questionListCurrentQuiz", questionListCurrentQuiz);
                request.setAttribute("sectionList", sectionList);
                request.getRequestDispatcher("AddQuestionFromBank.jsp").forward(request,response);
            }
            if(go.equals("addQuestionFromBank")){
                DAOQuizQuestion daoQQ = new DAOQuizQuestion();
                DAOQuizQuestionChoice daoQQC = new DAOQuizQuestionChoice();
                String[] selectedQuestionIDs = request.getParameterValues("questionID");
                String quizIDtemp = "";
                if (selectedQuestionIDs != null) {
                    for (String questionID : selectedQuestionIDs) {
                        //get the question in database:
                        QuizQuestion quizquestion = daoQQ.getQuizQuestionByID(questionID);
                        //Add Question to current quiz
                        daoQQ.createQuizQuestion(new QuizQuestion(quizID, quizquestion.getQuestionText(), quizquestion.getExplanation(), quizquestion.getPicture()));
                        QuizQuestion neweastquizquestion = daoQQ.getNewestQuizQuestion();
                        //Get Choice for the question
                        List<QuizQuestionChoice> choiceList = daoQQC.getAllByQuestionId(quizquestion.getQuestionID());
                        //Add Choice to database
                        for (QuizQuestionChoice choice : choiceList){
                            daoQQC.createQuestionChoice(new QuizQuestionChoice(neweastquizquestion.getQuestionID(), choice.getChoiceText(), choice.isCorrect(), choice.getWeight()));
                        }
                    }
                }
                response.sendRedirect("quiz-details?quizID=" + quizID);
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
