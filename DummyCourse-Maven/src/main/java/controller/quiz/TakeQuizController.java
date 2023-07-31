package controller.quiz;

import controller.exceptions.AddResultQuestionUnsuccessfullyException;
import controller.exceptions.AddResultUnsuccessfullyException;
import controller.utils.Utilities;
import dao.*;
import entity.quiz.*;
import entity.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pthanh
 */
@WebServlet(name = "TakeQuizController", urlPatterns = {"/take-quiz"})
public class TakeQuizController extends HttpServlet {

    private static final long MAXIMUM_QUIZ_FORM_WAIT_TIME = 20000;
    private static final ConcurrentHashMap<String, Thread> threadMap = new ConcurrentHashMap<>();
    private Thread backgroundThread;
    private int userID;
    private String quizID;

    private static void isNullQuizID(HttpServletRequest request, HttpServletResponse response, String quizID) throws IOException {
        if (quizID == null || "".equals(quizID)) {
            request.getSession().setAttribute("error", "Quiz ID is null");
            request.getSession().setAttribute("page", "home");
            response.sendRedirect("error.jsp");
        }
    }

    private void setQuizInfoToRequest(HttpServletRequest request, HttpServletResponse response, String quizID, long startTime, long maxWaitTime, long duration) throws IOException {
        ArrayList<QuizQuestion> quizQuestions = new DAOQuiz().getAllQuizQuestions(quizID);
        request.setAttribute("quiz-questions", quizQuestions);

        String threadID = new Utilities().getRandom32String();

        // A thread that is started to wait for the quiz to end. If the quiz time exceeds and the thread was not joined (User does not submit quiz form), The result will be automatically calculated
        backgroundThread = new Thread(() -> {
            try {
                Thread.sleep(maxWaitTime);

                calculateUnfinishedResult();
            } catch (AddResultQuestionUnsuccessfullyException | AddResultUnsuccessfullyException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException interruptedException) {
                System.out.println("User has submitted the quiz result. The default calculation will not be executed.");
            }
        });
        request.getSession().setAttribute("thread-id", threadID);
        threadMap.put(threadID, backgroundThread);

        backgroundThread.start();

        request.getSession().setAttribute("start-time", startTime);
        request.getSession().setAttribute("quiz-duration", duration);
        request.setAttribute("quiz-id", quizID);
        request.setAttribute("quiz-name", new DAOQuiz().getQuizByID(quizID).getQuizName());
        String courseID = request.getParameter("course-id");
        if (courseID == null) {
            request.getSession().setAttribute("error", "Course ID is null");
            request.getSession().setAttribute("page", "home");
            response.sendRedirect("error.jsp");
            return;
        }
        request.setAttribute("course-name", new DAOCourse().getCourseByID(Integer.parseInt(courseID)).getCourseName());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String go = request.getParameter("go");
        if (go == null) {
            handleTakeQuizPage(request, response);
        } else if ("get-remaining-time".equals(go)) {
            handleGetRemainingTime(request, response);
        }
    }

    /**
     * This method is to
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException      throws IOException
     * @throws ServletException throws ServletException
     */
    private void handleTakeQuizPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String quizID = request.getParameter("quiz-id");
        isNullQuizID(request, response, quizID);
        this.quizID = quizID;
        this.userID = ((User) request.getSession().getAttribute("user")).getUserID();

        Object startTimeObject = request.getSession().getAttribute("start-time");
        // Time calculation
        long startTime;
        long duration = new Utilities().convertTimeStringToMillis(new DAOQuiz().getQuizByID(quizID).getDuration().toString());
        long maxWaitTime = duration + MAXIMUM_QUIZ_FORM_WAIT_TIME;
        if (startTimeObject == null)
            startTime = System.currentTimeMillis();
        else {
            startTime = (Long) startTimeObject;
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime > MAXIMUM_QUIZ_FORM_WAIT_TIME)
                startTime = currentTime;
        }

        setQuizInfoToRequest(request, response, quizID, startTime, maxWaitTime, duration);
        request.getRequestDispatcher("Quiz/takeQuizStudent.jsp").forward(request, response);
    }

    private void handleGetRemainingTime(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object durationObject = request.getSession().getAttribute("quiz-duration");
        Object userStartTimeObject = request.getSession().getAttribute("start-time");

        if (durationObject == null || userStartTimeObject == null) {
            response.getWriter().write("Test finished");
            return;
        }

        long duration = (Long) durationObject;
        long userStartTime = (Long) userStartTimeObject;
        long remainingTime = duration - (System.currentTimeMillis() - userStartTime);
        if (remainingTime > 0) {

            int hours = (int) (remainingTime / (60 * 60 * 1000));
            int minutes = (int) ((remainingTime % (60 * 60 * 1000)) / (60 * 1000));
            int seconds = (int) (((remainingTime % (60 * 60 * 1000)) % (60 * 1000)) / 1000);
            response.getWriter().write(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            return;
        }

        request.getSession().removeAttribute("quiz-duration");
        request.getSession().removeAttribute("start-time");
        response.getWriter().write("Test finished");
    }

    private void handleCalculateResult(HttpServletRequest request, HttpServletResponse response) {

        String threadID = (String) request.getSession().getAttribute("thread-id");
        Thread userBackgroundThread = threadMap.get(threadID);
        if (threadID == null || userBackgroundThread == null) {
            request.getSession().setAttribute("error", "You have not taken any test or the test has been finished");
            request.getSession().setAttribute("page", "home");
            return;
        }
        // Stop the waiting calculation background thread of the user's occurring test
        if (userBackgroundThread.isAlive()) {
            userBackgroundThread.interrupt();
            try {
                userBackgroundThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        request.getSession().removeAttribute("start-time");
        request.getSession().removeAttribute("quiz-duration");

        String quizID = request.getParameter("quiz-id");

        String resultID = new Utilities().getRandom32String();

        QuizResult quizResult = new QuizResult();
        quizResult.setQuizId(quizID);
        quizResult.setStudentID(((User) request.getSession().getAttribute("user")).getUserID());
        quizResult.setTestTime(new Utilities().getCurrentTime());
        quizResult.setResultId(resultID);
        quizResult.setScore((float) calculateWeightForResult(request, response, quizID));
        System.out.println(new DAOQuizResult().createQuizResult(quizResult) + " quiz result created");

        createQuizResultQuestions(request, response, quizResult);

        try {
            response.sendRedirect("check-result?go=results&quiz-id=" + quizID + "&result-id=" + resultID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createQuizResultQuestions(HttpServletRequest request, HttpServletResponse response, QuizResult quizResult) {
        ArrayList<QuizQuestion> quizQuestions = new DAOQuizQuestion().getQuizQuestionsByQuizID(quizResult.getQuizId());
        for (QuizQuestion quizQuestion : quizQuestions) {

            String quizResultQuestionID = new Utilities().getRandom32String();

            QuizResultQuestion quizResultQuestion = new QuizResultQuestion();
            quizResultQuestion.setQuestionId(quizQuestion.getQuestionID());
            quizResultQuestion.setResultId(quizResult.getResultId());
            quizResultQuestion.setResultQuestionId(quizResultQuestionID);
            quizResultQuestion.setPoint(calculateWeightForQuestion(request, response, quizQuestion));
            System.out.println(new DAOQuizResultQuestion().createQuizResultQuestion(quizResultQuestion) + " quiz result question created");
            createQuizResultAnswers(request, response, quizResultQuestion, quizQuestion);
        }
    }

    private double calculateWeightForResult(HttpServletRequest request, HttpServletResponse response, String quizID) {
        ArrayList<QuizQuestion> quizQuestions = new DAOQuizQuestion().getQuizQuestionsByQuizID(quizID);
        double resultScore = 0;
        for (QuizQuestion quizQuestion : quizQuestions) {
            resultScore += calculateWeightForQuestion(request, response, quizQuestion);
        }
        return resultScore;
    }

    private double calculateWeightForQuestion(HttpServletRequest request, HttpServletResponse response, QuizQuestion quizQuestion) {
        double weight = 0;

        ArrayList<QuizQuestionChoice> quizQuestionChoices = (ArrayList<QuizQuestionChoice>) new DAOQuizQuestionChoice().getAllByQuestionId(quizQuestion.getQuestionID());
        for (QuizQuestionChoice quizQuestionChoice : quizQuestionChoices) {

            String answerValue = request.getParameter("question-id=" + quizQuestion.getQuestionID() + "choice-id=" + quizQuestionChoice.getChoiceId());

            if (!quizQuestionChoice.isCorrect() && answerValue != null)
                return 0;

            if (quizQuestionChoice.isCorrect() && answerValue != null)
                weight += quizQuestionChoice.getWeight();

        }

        return weight;
    }

    private void createQuizResultAnswers(HttpServletRequest request, HttpServletResponse response, QuizResultQuestion quizResultQuestion, QuizQuestion quizQuestion) {
        ArrayList<QuizQuestionChoice> quizQuestionChoices = (ArrayList<QuizQuestionChoice>) new DAOQuizQuestionChoice().getAllByQuestionId(quizQuestion.getQuestionID());
        for (QuizQuestionChoice quizQuestionChoice : quizQuestionChoices) {
            String answerValue = request.getParameter("question-id=" + quizQuestion.getQuestionID() + "choice-id=" + quizQuestionChoice.getChoiceId());
            if (answerValue != null) {
                QuizResultAnswer quizResultAnswer = new QuizResultAnswer();
                quizResultAnswer.setResultQuestionID(quizResultQuestion.getResultQuestionId());
                quizResultAnswer.setChoiceID(quizQuestionChoice.getChoiceId());
                System.out.println(new DAOQuizResultAnswer().createQuizResultAnswer(quizResultAnswer) + " quiz result answer created :" + quizResultAnswer);
            }
        }
    }

    private void calculateUnfinishedResult() throws AddResultUnsuccessfullyException, AddResultQuestionUnsuccessfullyException {
        QuizResult quizResult = new QuizResult();
        quizResult.setQuizId(quizID);

        String resultId = new Utilities().getRandom32String();

        quizResult.setResultId(resultId);
        quizResult.setTestTime(new Utilities().getCurrentTime());
        quizResult.setStudentID(userID);
        quizResult.setScore(0);
        int i = new DAOQuizResult().createQuizResult(quizResult);
        if (i != 1) {
            throw new AddResultUnsuccessfullyException("Add result unsuccessfully. Return value: " + i);
        }

        ArrayList<QuizQuestion> quizQuestions = new DAOQuizQuestion().getQuizQuestionsByQuizID(quizID);
        for (QuizQuestion quizQuestion : quizQuestions) {
            QuizResultQuestion quizResultQuestion = new QuizResultQuestion();
            quizResultQuestion.setPoint(0);
            quizResultQuestion.setQuestionId(quizQuestion.getQuestionID());
            quizResultQuestion.setResultId(resultId);
            quizResultQuestion.setResultQuestionId(new Utilities().getRandom32String());
            int addResultQuestionResult = new DAOQuizResultQuestion().createQuizResultQuestion(quizResultQuestion);
            if (addResultQuestionResult != 1)
                throw new AddResultQuestionUnsuccessfullyException("Add result question unsuccessfully. Return value: " + addResultQuestionResult);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleCalculateResult(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
