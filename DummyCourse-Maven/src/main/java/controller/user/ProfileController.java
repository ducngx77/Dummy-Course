package controller.user;

import dao.*;
import entity.course.Course;
import entity.user.LectureDetails;
import entity.user.Role;
import entity.user.StudentCourseEnrollment;
import entity.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static controller.user.LecturerInfoController.checkLecturerID;

/**
 * @author pthanh
 */
@WebServlet(name = "ProfileController", urlPatterns = {"/profile"})
@MultipartConfig
public class ProfileController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        Role role = (Role) request.getSession().getAttribute("role");
        if (role == null) {
            request.getRequestDispatcher("login").forward(request, response);
            return;
        }
        String roleType = role.getUserType();
        int userID = role.getUserID();
        if (roleType.equals("lecturer")) {
            DAOLectureDetails daoLD = new DAOLectureDetails();
            DAOCourse daoC = new DAOCourse();
            LectureDetails lectureDetails = daoLD.getLectureByUserID(userID);
            User user = (User) request.getSession().getAttribute("user");
            byte[] data = lectureDetails.getLectureProfile();
            if (data != null) {
                String base64Image = Base64.getEncoder().encodeToString(data);
                String dataUrl = "data:image/png;base64," + base64Image;
                request.setAttribute("dataUrl", dataUrl);
            }


            ArrayList<Course> totalCoursesByLecturerID = daoC.getCoursesByLectureID(lectureDetails.getLectureID());
            int totalTestPosted = daoLD.getTotalTestPosted(lectureDetails.getLectureID());
            double studentAvgScore = daoLD.getStudentAvgScore(lectureDetails.getLectureID());
            int totalStudent = daoLD.getTotalStudent(lectureDetails.getLectureID());
            int reviewReceived = daoLD.getReviewReceived(lectureDetails.getLectureID());
            double avgScore = daoLD.getAvgScore(lectureDetails.getLectureID());

            request.setAttribute("totalTestPosted", totalTestPosted);
            request.setAttribute("studentAvgScore", studentAvgScore);
            request.setAttribute("totalStudent", totalStudent);
            request.setAttribute("reviewReceived", reviewReceived);
            request.setAttribute("avgScore", avgScore);

            request.setAttribute("totalCoursesByLecturerID", totalCoursesByLecturerID.size());

            request.setAttribute("user", user);
            request.setAttribute("lectureDetails", lectureDetails);
        } else if (roleType.equals("student")) {
            DAOStudentCourseEnrollment daoSCE = new DAOStudentCourseEnrollment();
            DAOLecturerReviews daoLR = new DAOLecturerReviews();

            User user = (User) request.getSession().getAttribute("user");
            ArrayList<StudentCourseEnrollment> listStudentCourseEnrollment = daoSCE.getCourseStudentEnrolled(userID);

            int totalTest = daoSCE.getTotalTest(userID);
            int testTaken = daoSCE.getTotalTestTaken(userID);
            double avgTestTaken = daoSCE.getAvgTestScore(userID);

            request.setAttribute("totalTest", totalTest);
            request.setAttribute("testTaken", testTaken);
            request.setAttribute("avgTestTaken", avgTestTaken);

            request.setAttribute("totalCourses", listStudentCourseEnrollment.size());
            request.setAttribute("user", user);
        }
        if ("view-lecturer-details".equals(request.getParameter("go"))) {
            int lecturerUserID = (request.getParameter("lecturer-user-id") != null) ? Integer.parseInt(request.getParameter("lecturer-user-id")) : -1;
            checkLecturerID(request, response, lecturerUserID);
            LectureDetails lecturerDetails = new DAOLectureDetails().getLectureByUserID(lecturerUserID);
            String base64Image = Base64.getEncoder().encodeToString(lecturerDetails.getLectureProfile());
            String dataUrl = "data:image/png;base64," + base64Image;
            // Setting attributes in the request scope
            request.setAttribute("lectureDetails", lecturerDetails); // Corrected attribute name
            request.setAttribute("dataUrl", dataUrl);
            request.getRequestDispatcher("User/LecturerDetails.jsp").forward(request, response);

            return;
        }
        request.getRequestDispatcher("User/Profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Role role = (Role) request.getSession().getAttribute("role");
        if (role == null) {
            request.getRequestDispatcher("login").forward(request, response);
            return;
        }
        String roleType = role.getUserType();
        int userID = role.getUserID();
        if (roleType.equals("lecturer")) {
            String phone = request.getParameter("user-phone-number");
            String address = request.getParameter("user-address");
            String bio = request.getParameter("user-bio");
            String gender = request.getParameter("gender");
            String REGEX_PHONE = "^0[0-9]{9}$";
            Pattern pattern_p = Pattern.compile(REGEX_PHONE);
            Matcher matcher = pattern_p.matcher(phone);
            System.out.print(phone);
            System.out.print(!matcher.matches());
            if (!matcher.matches()) {
                String message = "Invalid input";
                request.getSession().setAttribute("successMessage", message);
                doGet(request, response);
                return;
            }
            Part filePart = request.getPart("user-img");
            InputStream fileInputStream = filePart.getInputStream();
            String description = request.getParameter("user-description");
            DAOUser daoU = new DAOUser();
            DAOLectureDetails daoLD = new DAOLectureDetails();

            if (filePart.getSize() == 0) {
                daoLD.updateLectureDetailsProfile(userID, description, null);
            } else {
                daoLD.updateLectureDetailsProfile(userID, description, fileInputStream);
            }

            if (daoU.updateUser(userID, address, bio, phone, Integer.parseInt(gender))) {
                User user = (User) request.getSession().getAttribute("user");
                user.setPhoneNumber(phone);
                user.setAddress(address);
                user.setBio(bio);
                user.setGender(Integer.parseInt(gender));
            }
        } else if (roleType.equals("student")) {
            String phone = request.getParameter("user-phone-number");
            String address = request.getParameter("user-address");
            String bio = request.getParameter("user-bio");
            String gender = request.getParameter("gender");

            DAOUser daoU = new DAOUser();
            if (daoU.updateUser(userID, address, bio, phone, Integer.parseInt(gender))) {
                User user = (User) request.getSession().getAttribute("user");
                user.setPhoneNumber(phone);
                user.setAddress(address);
                user.setBio(bio);
                user.setGender(Integer.parseInt(gender));
            }
        }

        String message = "Update Successfully";
        request.getSession().setAttribute("successMessage", message);
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}