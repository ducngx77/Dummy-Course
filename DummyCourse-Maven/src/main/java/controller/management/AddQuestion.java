package controller.management;

import dao.*;
import entity.course.Course;
import entity.course.CourseSection;
import entity.quiz.Quiz;
import entity.quiz.QuizQuestion;
import entity.quiz.QuizQuestionChoice;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * @author pthanh
 */

@WebServlet(name = "AddQuestion", urlPatterns = {"/addquestion"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,  // 1MB
        maxFileSize = 1024 * 1024 * 10,   // 10MB
        maxRequestSize = 1024 * 1024 * 50  // 50MB
)
public class AddQuestion extends HttpServlet {

    public static byte[] inputStreamToByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String go = request.getParameter("go");
            String quizID = request.getParameter("quizID");
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            DAOCourse daoC = new DAOCourse();
            String courseName = daoC.getCourseByID(courseID).getCourseName();
            request.setAttribute("courseID", courseID);
            request.setAttribute("courseName", courseName);
            int sectionID = Integer.parseInt(request.getParameter("sectionID"));
            if (go == null) {
                DAOCourseSection daoCS = new DAOCourseSection();
                DAOQuiz daoQ = new DAOQuiz();
                Course course = daoC.getCourseByID(courseID);
                CourseSection section = daoCS.getCourseSectionbyID(sectionID);
                Quiz quiz = daoQ.getQuizByID(quizID);
                request.setAttribute("course", course);
                request.setAttribute("section", section);
                request.setAttribute("quiz", quiz);
                request.getRequestDispatcher("QuizManagement/AddQuestion.jsp").forward(request, response);
            } else if (go.equals("addQuestion")) {
                String question = request.getParameter("question");
                String explanation = request.getParameter("explanation");
                Part filePart = request.getPart("picture");
                String fileName = filePart.getSubmittedFileName();
                String[] choices = request.getParameterValues("choice");
                String[] weights = request.getParameterValues("weight");
                byte[] fileContent = null;
                if (question.isEmpty()) {
                    String error = "Question must not be null";
                    sentError(error, question, explanation, filePart, choices, weights, quizID, courseID, sectionID, request, response);
                    return;
                } else {
                    if (question.length() < 6) {
                        String error = "Question must be at least 6 character";
                        sentError(error, question, explanation, filePart, choices, weights, quizID, courseID, sectionID, request, response);
                        return;
                    }
                }
                if (explanation.isEmpty()) {
                    String error = "Explanation must not be null";
                    sentError(error, question, explanation, filePart, choices, weights, quizID, courseID, sectionID, request, response);
                    return;
                } else {
                    if (explanation.length() < 6) {
                        String error = "Explanation must be at least 6 character";
                        sentError(error, question, explanation, filePart, choices, weights, quizID, courseID, sectionID, request, response);
                        return;
                    }
                }
                if (choices.length <= 1) {
                    String error = "Must have at least 2 choice";
                    sentError(error, question, explanation, filePart, choices, weights, quizID, courseID, sectionID, request, response);
                    return;
                }
                for (String choice : choices) {
                    if (choice.isEmpty()) {
                        String error = "Choice must not be empty!";
                        sentError(error, question, explanation, filePart, choices, weights, quizID, courseID, sectionID, request, response);
                        return;
                    }
                }
                Float[] weightsFloat = new Float[weights.length];
                for (int i = 0; i < weightsFloat.length; i++) {
                    try {
                        weightsFloat[i] = Float.parseFloat(weights[i]);
                    } catch (Exception ex) {
                        String error = "Weight format is wrong";
                        sentError(error, question, explanation, filePart, choices, weights, quizID, courseID, sectionID, request, response);
                        return;
                    }
                }
                float total = 0;
                for (float weightfloat : weightsFloat) {
                    total += weightfloat;
                    if (weightfloat < 0) {
                        String error = "Weight format is wrong";
                        sentError(error, question, explanation, filePart, choices, weights, quizID, courseID, sectionID, request, response);
                        return;
                    }
                }
                if (total != 1) {
                    String error = "Total weight must be 1";
                    sentError(error, question, explanation, filePart, choices, weights, quizID, courseID, sectionID, request, response);
                    return;
                }
//                for (String weight : weights) {
//
//                }
//                if (count == corrects.length) {
//                    String error = "Must have at least one correct answer";
//                    sentError(error, question, explanation, filePart, choices, corrects, quizID, courseID, sectionID, request, response);
//                    return;
//                }
                if (!fileName.isEmpty()) {
                    String fileExtension = getFileExtension(fileName);
                    if (!(fileExtension.equals("png") || fileExtension.equals("jpeg") || fileExtension.equals("jpg"))) {
                        String error = "Only upload image file!";
                        sentError(error, question, explanation, filePart, choices, weights, quizID, courseID, sectionID, request, response);
                        return;
                    }
                    fileContent = inputStreamToByteArray(filePart.getInputStream());
                }
                //check if question is exist in database
                // add question to database
                DAOQuizQuestion daoQQ = new DAOQuizQuestion();

                int n = daoQQ.createQuizQuestion(new QuizQuestion(quizID, question, explanation, fileContent));
                //add answer to database
                //get questionID
                QuizQuestion quizQuestion = daoQQ.getNewestQuizQuestion();
                String questionID = quizQuestion.getQuestionID();
                DAOQuizQuestionChoice daoQQC = new DAOQuizQuestionChoice();
//                int weight0 = 0;
//                int weight1 = 0;
//                for (int i = 0; i < corrects.length; i++) {
//                    if (corrects[i].equals("0")) {
//                        weight0++;
//                    } else weight1++;
//                }
                for (int i = 0; i < choices.length; i++) {
                    boolean isCorrect = weightsFloat[i] > 0;
                    int n1 = daoQQC.createQuestionChoice(new QuizQuestionChoice(questionID, choices[i], isCorrect, weightsFloat[i]));
                }
                response.sendRedirect("quiz-details?quizID=" + quizID);
            }
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".") || fileName.endsWith(".")) {
            return null;
        }
        int dotIndex = fileName.lastIndexOf(".");
        return fileName.substring(dotIndex + 1);
    }

    private void sentError(String error, String question, String explanation, Part filePart, String[] choices, String[] weight, String quizID, int courseID, int sectionID, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOCourse daoC = new DAOCourse();
        DAOCourseSection daoCS = new DAOCourseSection();
        DAOQuiz daoQ = new DAOQuiz();
        Course course = daoC.getCourseByID(courseID);
        CourseSection section = daoCS.getCourseSectionbyID(sectionID);
        Quiz quiz = daoQ.getQuizByID(quizID);
        request.setAttribute("course", course);
        request.setAttribute("section", section);
        request.setAttribute("quiz", quiz);
        request.setAttribute("error", error);
        request.setAttribute("question", question);
        request.setAttribute("explanation", explanation);
        request.setAttribute("filePart", filePart);
        request.setAttribute("choices", choices);
        request.setAttribute("weight", weight);
        request.getRequestDispatcher("QuizManagement/AddQuestion.jsp").forward(request, response);
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
