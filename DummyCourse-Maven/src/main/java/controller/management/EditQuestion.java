package controller.management;

import dao.DAOCourse;
import dao.DAOQuiz;
import dao.DAOQuizQuestion;
import dao.DAOQuizQuestionChoice;
import entity.course.Course;
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
import java.util.List;

/**
 * @author pthanh
 */
@WebServlet(name = "EditQuestion", urlPatterns = {"/editquestion"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,  // 1MB
        maxFileSize = 1024 * 1024 * 10,   // 10MB
        maxRequestSize = 1024 * 1024 * 50  // 50MB
)
public class EditQuestion extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            String go = request.getParameter("go");
            if (go == null) {
                String questionID = request.getParameter("questionID");
                DAOQuizQuestion daoQQ = new DAOQuizQuestion();
                DAOQuizQuestionChoice daoQQC = new DAOQuizQuestionChoice();
                //Get the question:
                QuizQuestion quizquestion = daoQQ.getQuizQuestionByID(questionID);
                //Get the choice list:
                List<QuizQuestionChoice> choiceList = daoQQC.getAllByQuestionId(questionID);
                String[] choices = new String[choiceList.size()];
                String[] weights = new String[choiceList.size()];
                for (int i = 0; i < choiceList.size(); i++) {
                    weights[i] = String.valueOf(choiceList.get(i).getWeight());
                    choices[i] = choiceList.get(i).getChoiceText();
                }
                request.setAttribute("choices", choices);
                request.setAttribute("choiceList", choiceList);
                request.setAttribute("quizquestion", quizquestion);
                request.setAttribute("weight", weights);
                Course course = new DAOCourse().getCourseByID(Integer.parseInt(request.getParameter("course-id")));
                request.setAttribute("courseID", course.getCourseID());
                request.setAttribute("courseName", course.getCourseName());
                Quiz quiz = new DAOQuiz().getQuizByID(request.getParameter("quiz-id"));
                request.setAttribute("quizID", quiz.getQuizID());
                request.setAttribute("quizName", quiz.getQuizName());
                request.getRequestDispatcher("QuizManagement/EditQuestion.jsp").forward(request, response);
            }
            if (go.equals("editQuestion")) {
                String questionID = request.getParameter("questionID");
                String question = request.getParameter("question");
                String explanation = request.getParameter("explanation");
                Part filePart = request.getPart("picture");
                String fileName = filePart.getSubmittedFileName();
                String[] choices = request.getParameterValues("choice");
                String[] weights = request.getParameterValues("weight");
                byte[] fileContent = null;
                if (question.isEmpty()) {
                    String error = "Question must not be null";
                    sentError(error, question, explanation, filePart, choices, weights, request, response);
                    return;
                } else {
                    if (question.length() < 6) {
                        String error = "Question must be at least 6 character";
                        sentError(error, question, explanation, filePart, choices, weights, request, response);
                        return;
                    }
                }
                if (explanation.isEmpty()) {
                    String error = "Explanation must not be null";
                    sentError(error, question, explanation, filePart, choices, weights, request, response);
                    return;
                } else {
                    if (explanation.length() < 6) {
                        String error = "Explanation must be at least 6 character";
                        sentError(error, question, explanation, filePart, choices, weights, request, response);
                        return;
                    }
                }
                if (choices.length <= 1) {
                    String error = "Must have at least 2 choice";
                    sentError(error, question, explanation, filePart, choices, weights, request, response);
                    return;
                }
                for (String choice : choices) {
                    if (choice.isEmpty()) {
                        String error = "Choice must not be empty!";
                        sentError(error, question, explanation, filePart, choices, weights, request, response);
                        return;
                    }
                }
                Float[] weightsFloat = new Float[weights.length];
                for (int i = 0; i < weightsFloat.length; i++) {
                    try {
                        weightsFloat[i] = Float.parseFloat(weights[i]);
                    } catch (Exception ex) {
                        String error = "Weight format is wrong";
                        //sentError(error, question, explanation, filePart, choices, weights, quizID, courseID, sectionID, request, response);
                        sentError(error, question, explanation, filePart, choices, weights, request, response);
                        return;
                    }
                }
                float total = 0;
                for (float weightfloat : weightsFloat) {
                    total += weightfloat;
                    if (weightfloat < 0) {
                        String error = "Weight format is wrong";
                        //sentError(error, question, explanation, filePart, choices, weights, quizID, courseID, sectionID, request, response);
                        sentError(error, question, explanation, filePart, choices, weights, request, response);
                        return;
                    }
                }
                if (total != 1) {
                    String error = "Total weight must be 1";
                    //sentError(error, question, explanation, filePart, choices, weights, quizID, courseID, sectionID, request, response);
                    sentError(error, question, explanation, filePart, choices, weights, request, response);
                    return;
                }
//               int count = 0;
//               for (String correct : corrects) {
//                   if (correct.equals("0")) {
//                       count++;
//                   }
//               }
//               if (count == corrects.length) {
//                   String error = "Must have at least one correct answer";
//                   sentError(error, question, explanation, filePart, choices, corrects, request, response);
//                   return;
//               }
                if (!fileName.isEmpty()) {
                    String fileExtension = getFileExtension(fileName);
                    if (!(fileExtension.equals("png") || fileExtension.equals("jpeg") || fileExtension.equals("jpg"))) {
                        String error = "Only upload image file!";
                        sentError(error, question, explanation, filePart, choices, weights, request, response);
                        return;
                    }
                    fileContent = inputStreamToByteArray(filePart.getInputStream());
                }
                //check if question is exist in database
                // update question to database
                DAOQuizQuestion daoQQ = new DAOQuizQuestion();
                QuizQuestion oldQuizQuestion = daoQQ.getQuizQuestionByID(questionID);
                daoQQ.updateQuizQuestion(new QuizQuestion(questionID, oldQuizQuestion.getQuizID(), question, explanation, fileContent));
                //delete all questionchoice for the old question
                DAOQuizQuestionChoice daoQQC = new DAOQuizQuestionChoice();
                daoQQC.deleteQuestionChoiceByQuestionID(questionID);
                // Create new question choice for the question
//               int weight0 = 0;
//               int weight1 = 0;
//               for (int i = 0; i < corrects.length; i++) {
//                   if (corrects[i].equals("0")) {
//                       weight0++;
//                   } else weight1++;
//               }
                for (int i = 0; i < choices.length; i++) {
                    boolean isCorrect = weightsFloat[i] > 0;
                    int n1 = daoQQC.createQuestionChoice(new QuizQuestionChoice(questionID, choices[i], isCorrect, weightsFloat[i]));
                }
                response.sendRedirect("quiz-details?quizID=" + oldQuizQuestion.getQuizID());
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

    private void sentError(String error, String question, String explanation, Part filePart, String[] choices, String[] weight, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        DAOCourse daoC = new DAOCourse();
//        DAOCourseSection daoCS = new DAOCourseSection();
//        DAOQuiz daoQ = new DAOQuiz();
//        Course course = daoC.getCourseByID(courseID);
//        CourseSection section = daoCS.getCourseSectionbyID(sectionID);
//        Quiz quiz = daoQ.getQuizByID(quizID);
        String questionID = request.getParameter("questionID");
        DAOQuizQuestion daoQQ = new DAOQuizQuestion();
        DAOQuizQuestionChoice daoQQC = new DAOQuizQuestionChoice();
        //Get the question:
        QuizQuestion quizquestion = daoQQ.getQuizQuestionByID(questionID);
        //Get the choice list:
        List<QuizQuestionChoice> choiceList = daoQQC.getAllByQuestionId(questionID);
        request.setAttribute("error", error);
        request.setAttribute("question", question);
        request.setAttribute("explanation", explanation);
        request.setAttribute("filePart", filePart);
        request.setAttribute("choiceList", choiceList);
        request.setAttribute("quizquestion", quizquestion);
        request.setAttribute("choices", choices);
        request.setAttribute("weight", weight);
        request.getRequestDispatcher("QuizManagement/EditQuestion.jsp").forward(request, response);
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
