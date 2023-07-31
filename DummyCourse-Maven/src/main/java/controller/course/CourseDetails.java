package controller.course;

import dao.*;
import entity.course.Course;
import entity.course.CourseSection;
import entity.quiz.Materials;
import entity.quiz.Notice;
import entity.quiz.Quiz;
import entity.quiz.QuizResult;
import entity.user.Role;
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
import java.util.List;

/**
 * @author pthanh
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,  // 1MB
        maxFileSize = 1024 * 1024 * 10,   // 10MB
        maxRequestSize = 1024 * 1024 * 50  // 50MB
)
@WebServlet(name = "CourseDetail", urlPatterns = {"/course-details"})

public class CourseDetails extends HttpServlet {

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
        /* TODO output your page here. You may use following sample code. */
        String go = request.getParameter("go");
        if (go == null) {
            int courseID = Integer.parseInt(request.getParameter("course-id"));
//            String studentInt = request.getParameter("student-id");
            Role role = (Role) request.getSession().getAttribute("role");
            if (role == null) {
                request.getRequestDispatcher("login").forward(request, response);
                return;
            }
            String roleType = role.getUserType();
            if (roleType.equals("student")) {
                DAOStudentCourseEnrollment daoSCE = new DAOStudentCourseEnrollment();
                int studentID = role.getUserID();
                //handle enroll student when student click register
                boolean handleEnroll = Boolean.parseBoolean(request.getParameter("handleEnroll"));
                if (handleEnroll) {
                    System.out.print("handleEnroll" + handleEnroll);
                    if (daoSCE.handleEnrollStudent(studentID, courseID)) ;
                    response.sendRedirect("course-details?course-id=" + courseID);
                    return;
                }
                // handle redirect student if student didn't enrolled
                if (!daoSCE.checkStudentEnrolled(studentID, courseID)) {
                    DAOCourse daoC = new DAOCourse();
                    Course course = daoC.getCourseByID(courseID);
                    request.setAttribute("course", course);
                    request.getRequestDispatcher("CourseView/register-for-course.jsp").forward(request, response);
                    return;
                }

                // Create DAO
                DAOCourse daoC = new DAOCourse();
                DAONotice daoN = new DAONotice();
                DAOCourseSection daoCS = new DAOCourseSection();
                DAOMaterials daoM = new DAOMaterials();
                DAOQuiz daoQ = new DAOQuiz();
                DAOQuizResult daoQR = new DAOQuizResult();
                Course course = daoC.getCourseByID(courseID);
                if (course == null) {
                    request.setAttribute("error", "Can't find course with ID: " + courseID);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
                //Attribute Notice List
                List<Notice> noticeList = daoN.getAllNoticeByCourseID(courseID);

                // List of section
                List<CourseSection> courseSectionList = daoCS.getAllCourseSectionbyCourseID(courseID);
                //list of material
                List<Materials> materialsList = daoM.getAllMaterialsByCourse(courseID);
                //list of quiz
                List<Quiz> quizList = daoQ.getAllQuizzesByCourseID(courseID);
                //List of quizResult
                ResultSet quizResultList = daoQR.getAllQuizResultByStudentID(studentID);
                ResultSet copyOfQuizResultList = daoQR.getAllQuizResultByStudentID(studentID);
                // probility of quiz finish
                int total = 0;
                for (Quiz quiz : quizList) {
                    String quizID = quiz.getQuizID();
//                    for (QuizResult quizResult : quizResultList) {
//                        if (quizResult.getQuizId() == quizID) {
//                            total++;
//                        }
//                    }
                    try {
                        while (copyOfQuizResultList.next()) {
                            if (copyOfQuizResultList.getString("QuizID").equals(quizID)) {
                                total++;
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                // sent data to jsp
                String cmtError = request.getParameter("cmtError");
                String rateError = request.getParameter("rateError");
                String courseFeedBackComment = request.getParameter("courseFeedBackComment");
                String courseFeedBackStatus = request.getParameter("courseFeedBackStatus");
                request.setAttribute("cmtError", cmtError);
                request.setAttribute("rateError", rateError);
                request.setAttribute("courseFeedBackComment", courseFeedBackComment);
                request.setAttribute("courseFeedBackStatus", courseFeedBackStatus);
                request.setAttribute("course", course);
                request.setAttribute("noticeList", noticeList);
                request.setAttribute("sectionList", courseSectionList);
                request.setAttribute("materialList", materialsList);
                request.setAttribute("quizList", quizList);
                request.setAttribute("quizPercentage", total);
                request.setAttribute("quizResultList", quizResultList);
                request.getRequestDispatcher("CourseView/course-details-view.jsp").forward(request, response);
            } else if (roleType.equals("lecturer")) {
                int userID = role.getUserID();
                // Create DAO
                DAOCourse daoC = new DAOCourse();
                DAONotice daoN = new DAONotice();
                DAOCourseSection daoCS = new DAOCourseSection();
                DAOMaterials daoM = new DAOMaterials();
                DAOQuiz daoQ = new DAOQuiz();
                DAOQuizResult daoQR = new DAOQuizResult();
                Course course = daoC.getCourseByID(courseID);
                if (course == null) {
                    request.getSession().setAttribute("error", "Can't find course with ID: " + courseID);
                    request.getSession().setAttribute("page", "home");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }

                //Attribute Notice List
                List<Notice> noticeList = daoN.getAllNoticeByCourseID(courseID);

                // List of section
                List<CourseSection> courseSectionList = daoCS.getAllCourseSectionbyCourseID(courseID);
                //list of material
                List<Materials> materialsList = daoM.getAllMaterialsByCourse(courseID);
                //list of quiz
                List<Quiz> quizList = daoQ.getAllQuizzesByCourseID(courseID);
                //List of quizResult
                List<QuizResult> quizResultList = daoQR.getAllQuizResult();
                // probility of quiz finish
                int total = 0;
                for (Quiz quiz : quizList) {
                    String quizID = quiz.getQuizID();
                    for (QuizResult quizResult : quizResultList) {
                        if (quizID.equals(quizResult.getQuizId())) {
                            total++;
                        }
                    }
                }
                // sent data to jsp
                request.setAttribute("course", course);
                request.setAttribute("noticeList", noticeList);
                request.setAttribute("sectionList", courseSectionList);
                request.setAttribute("materialList", materialsList);
                request.setAttribute("quizList", quizList);
                request.setAttribute("quizResultList", quizResultList);
                request.setAttribute("quizPercentage", total);
                request.getRequestDispatcher("CourseView/course-details-edit.jsp?userID=" + userID).forward(request, response);
            } else {
                int userID = role.getUserID();
                // Create DAO
                DAOCourse daoC = new DAOCourse();
                DAONotice daoN = new DAONotice();
                DAOCourseSection daoCS = new DAOCourseSection();
                DAOMaterials daoM = new DAOMaterials();
                DAOQuiz daoQ = new DAOQuiz();
                DAOQuizResult daoQR = new DAOQuizResult();
                Course course = daoC.getCourseByID(courseID);
                if (course == null) {
                    request.getSession().setAttribute("error", "Can't find course with ID: " + courseID);
                    request.getSession().setAttribute("page", "home");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }

                //Attribute Notice List
                List<Notice> noticeList = daoN.getAllNoticeByCourseID(courseID);

                // List of section
                List<CourseSection> courseSectionList = daoCS.getAllCourseSectionbyCourseID(courseID);
                //list of material
                List<Materials> materialsList = daoM.getAllMaterialsByCourse(courseID);
                //list of quiz
                List<Quiz> quizList = daoQ.getAllQuizzesByCourseID(courseID);
                //List of quizResult
                List<QuizResult> quizResultList = daoQR.getAllQuizResult();
                // probility of quiz finish
                int total = 0;
                for (Quiz quiz : quizList) {
                    String quizID = quiz.getQuizID();
                    for (QuizResult quizResult : quizResultList) {
                        if (quizID.equals(quizResult.getQuizId())) {
                            total++;
                        }
                    }
                }
                // sent data to jsp
                request.setAttribute("course", course);
                request.setAttribute("noticeList", noticeList);
                request.setAttribute("sectionList", courseSectionList);
                request.setAttribute("materialList", materialsList);
                request.setAttribute("quizList", quizList);
                request.setAttribute("quizResultList", quizResultList);
                request.setAttribute("quizPercentage", total);
                request.getRequestDispatcher("CourseView/course-details-manager.jsp?userID=" + userID).forward(request, response);

            }
        } else if (go.equals("downloadMaterial")) {
            // Get the file id from the request
            String fileIdString = request.getParameter("fileId");
            // If user enter the servlet without downloading
            if (fileIdString == null) {
                return;
            }
            int fileId = Integer.parseInt(fileIdString);
            DAOMaterials dao = new DAOMaterials();
            Materials materials = dao.getMaterialsByMaterialID(fileId);
            // Set the content type and headers for the response
            String contentType = getServletContext().getMimeType(materials.getMaterialName());
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment;filename=\"" + materials.getMaterialName() + "\"" + "." + materials.getMaterialType());

            // Get the output stream from the response object
            OutputStream out1 = response.getOutputStream();
            // Write the file content to the output stream
            out1.write(materials.getMaterialFile());

            // Close the output stream
            out1.flush();
            out1.close();
            request.getRequestDispatcher("CourseView/course-details-view.jsp").forward(request, response);
        } else if (go.equals("addMaterial")) {
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            int sectionID = Integer.parseInt((request.getParameter("sectionID")));
            try (PrintWriter out = response.getWriter()) {
                out.print("<form action=\"course-details\" enctype=\"multipart/form-data\" method=\"post\">\n" +
                        "<input name=\"go\" value=\"handleAddMaterial\" type=\"text\" style=\"display: none\" />\n" +
                        "<input name=\"courseID\" value=\"" + courseID + "\" type=\"text\" style=\"display: none\"/>\n" +
                        "<input name=\"sectionID\" value=\"" + sectionID + "\" type=\"text\" style=\"display: none\"/>\n" +
                        "    <input name=\"file\" type=\"file\"/>\n" +
                        "    <input type=\"submit\" value=\"Upload\"/>");
            }
        } else if (go.equals("handleAddMaterial")) {
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            int sectionID = Integer.parseInt((request.getParameter("sectionID")));
            Part filePart = request.getPart("file");

            // If user does not choose any file, then do not insert the file to the database
            if (filePart == null) {
                return;
            }

            // Create a file name
            String fileName = filePart.getSubmittedFileName();

            String fileExtension = getFileExtension(fileName);
            if (fileExtension == null) {
                response.getWriter().println("File name is invalid");
                return;
            }

            byte[] fileContent = inputStreamToByteArray(filePart.getInputStream());

            // Create a file handle AddQuestion object
            Materials materials = new Materials(courseID, sectionID, fileName, fileExtension, fileContent);

            // Insert the file to the database
            DAOMaterials dao = new DAOMaterials();
            int n = dao.addMaterials(materials);
            PrintWriter out = response.getWriter();
            if (n == 1) {
                out.println("Add successful!");
            }
            response.sendRedirect("course-details?course-id=" + courseID);
        }
    }

    public String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".") || fileName.endsWith(".")) {
            return null;
        }
        int dotIndex = fileName.lastIndexOf(".");
        return fileName.substring(dotIndex + 1);
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
