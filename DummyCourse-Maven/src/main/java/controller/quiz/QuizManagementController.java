package controller.quiz;

import dao.DAOCourse;
import dao.DAOCourseSection;
import dao.DAOQuiz;
import entity.course.Course;
import entity.course.CourseSection;
import entity.quiz.Quiz;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author pthanh
 */
@WebServlet(name = "TestController", urlPatterns = {"/AddQuestion-actions"})
public class QuizManagementController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            DAOCourse daoC = new DAOCourse();
            String courseName = daoC.getCourseByID(courseID).getCourseName();
            request.setAttribute("courseID", courseID);
            request.setAttribute("courseName", courseName);
            int sectionID = Integer.parseInt(request.getParameter("sectionID"));
            String go = request.getParameter("go");
            if (go == null) {
                Course course = daoC.getCourseByID(courseID);
                if (course == null) {
                    request.setAttribute("error", "Can't find course with ID: " + courseID);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
                DAOCourseSection daoCS = new DAOCourseSection();
                CourseSection courseSection = daoCS.getCourseSectionbyID(sectionID);
                request.setAttribute("course", course);
                request.setAttribute("section", courseSection);
                request.getRequestDispatcher("QuizManagement/CreateTest.jsp").forward(request, response);
            }
            if (go.equals("createTest")) {
                String testName = request.getParameter("testname");
                String sduration = request.getParameter("sduration");
                String mduration = request.getParameter("mduration");
                String startTime = request.getParameter("starttime");
                String courseIDstr = request.getParameter("courseID");
                String sectionIDstr = request.getParameter("sectionID");
                if (testName.isEmpty()) {
                    String error = "Test name could not be blank!";
                    sentError(error, testName, mduration, sduration, startTime, courseIDstr, sectionIDstr, request, response);
                    return;
                } else if (testName.length() < 6) {
                    String error = "Test name must have at least 6 characters";
                    sentError(error, testName, mduration, sduration, startTime, courseIDstr, sectionIDstr, request, response);
                    return;
                } else {
                    String[] testNameWord = testName.split(" ");
                    for (String word : testNameWord) {
                        if (Character.isLowerCase(word.charAt(0))) {
                            String error = "Each word must have first uppercase digit!";
                            sentError(error, testName, mduration, sduration, startTime, courseIDstr, sectionIDstr, request, response);
                            return;
                        }
                    }
                }
                if (startTime.isEmpty()) {
                    String error = "Start Time must not be Empty!";
                    sentError(error, testName, mduration, sduration, startTime, courseIDstr, sectionIDstr, request, response);
                }
//                startTime = Yeart-Month-DayThourse:Minutes
                else {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date dateobj = new Date();
                    String currentDateTime = df.format(dateobj);
                    String currentDate = currentDateTime.split(" ")[0];
                    String currentTime = currentDateTime.split(" ")[1];
                    String checkDate = startTime.substring(0, 10);
                    String checkTime = startTime.substring(11);
                    if (checkDate.compareTo(currentDate) < 0) {
                        String error = "Start Time is not valid!";
                        sentError(error, testName, mduration, sduration, startTime, courseIDstr, sectionIDstr, request, response);
                        return;
                    } else if (checkDate.compareTo(currentDate) == 0) {
                        LocalTime currentT = LocalTime.of(Integer.parseInt(currentTime.split(":")[0]), Integer.parseInt(currentTime.split(":")[1]));
                        LocalTime checkT = LocalTime.of(Integer.parseInt(checkTime.split(":")[0]), Integer.parseInt(checkTime.split(":")[1]));
                        LocalTime currentPlus30Mins = currentT.plusMinutes(30);
                        if (currentPlus30Mins.isAfter(checkT)) {
                            String error = "Start Time is not valid!";
                            sentError(error, testName, mduration, sduration, startTime, courseIDstr, sectionIDstr, request, response);
                            return;
                        }
                    }
                    if (mduration.isEmpty() && sduration.isEmpty()) {
                        String error = "Duration is not valid!";
                        sentError(error, testName, "0", "0", startTime, courseIDstr, sectionIDstr, request, response);
                        return;
                    }
                    if (mduration.isEmpty()) {
                        String error = "Duration is not valid!";
                        sentError(error, testName, "0", sduration, startTime, courseIDstr, sectionIDstr, request, response);
                        return;
                    }
                    if (sduration.isEmpty()) {
                        String error = "Duration is not valid!";
                        sentError(error, testName, mduration, "0", startTime, courseIDstr, sectionIDstr, request, response);
                        return;
                    }
                    DAOQuiz daoQ = new DAOQuiz();
                    LocalTime lt = LocalTime.of(0, 0, 0);
                    lt = lt.plusMinutes(Long.parseLong(mduration));
                    lt = lt.plusSeconds(Long.parseLong(sduration));
                    LocalDateTime ldt = LocalDateTime.of(Integer.parseInt(checkDate.split("-")[0]), Integer.parseInt(checkDate.split("-")[1]), Integer.parseInt(checkDate.split("-")[2]), Integer.parseInt(checkTime.split(":")[0]), Integer.parseInt(checkTime.split(":")[1]));
                    int n = daoQ.createQuiz(new Quiz(daoQ.generateNewQuizID(), Integer.parseInt(courseIDstr), Integer.parseInt(sectionIDstr), testName, ldt, lt, 1));
                    Quiz quiz = daoQ.getNewestQuiz();
//                    request.getRequestDispatcher("addquestion?quizID=" + quiz.getQuizID() + "&courseID=" + courseIDstr + "&sectionID=" + sectionIDstr).forward(request,response);
                    response.sendRedirect("quiz-details?quizID=" + quiz.getQuizID());
                }
            }
        }
    }

    private void sentError(String error, String testName, String mduration, String sduration, String startTime, String courseIDstr, String sectionIDstr, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", error);
        request.setAttribute("testname", testName);
        request.setAttribute("mduration", mduration);
        request.setAttribute("sduration", sduration);
        request.setAttribute("starttime", startTime);
        DAOCourseSection daoCS = new DAOCourseSection();
        CourseSection section = daoCS.getCourseSectionbyID(Integer.parseInt(sectionIDstr));
        DAOCourse daoC = new DAOCourse();
        Course course = daoC.getCourseByID(Integer.parseInt(courseIDstr));
        request.setAttribute("course", course);
        request.setAttribute("section", section);
        request.getRequestDispatcher("QuizManagement/CreateTest.jsp").forward(request, response);
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
