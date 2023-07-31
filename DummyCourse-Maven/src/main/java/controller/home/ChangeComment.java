package controller.home;

import dao.DAOSystemReview;
import dao.DAOUser;
import entity.user.SystemReview;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author pthanh
 */
@WebServlet(name = "ChangeComment", urlPatterns = {"/change-comment"})
public class ChangeComment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String go = request.getParameter("go");
        if ("change-comment".equals(go)) {
            ArrayList<SystemReview> reviews = new DAOSystemReview().getFiveRandomSystemReviews();
            DecimalFormat df = new DecimalFormat("#.##");
            try (PrintWriter out = response.getWriter()) {
                for (SystemReview review : reviews) {
                    System.out.println(df.format(review.getRating()));
                    out.println("<div class=\"review-item\">\n" +
                            "                        <div class=\"courseTitle\">\n" +
                            "                            <div class=\"row\">\n" +
                            "                                <div class=\"col-md-3 mt-1 mr-auto\">\n" +
                            "                                    <h5>\n" +
                            "                                            " + new DAOUser().getUsernameByID(review.getUserID()) + "\n" +
                            "                                    </h5>\n" +
                            "                                </div>\n" +
                            "                                <div class=\"col-md-4 ml-auto\">\n" +
                            "                                    <div class=\"row\">\n" +
                            "                                        <div class=\"col-md-4\">\n" +
                            "                                            <p><a style=\"color: var(--mainColor)\">Rating: " + df.format(review.getRating()) + "</a></p>\n" +
                            "                                        </div>\n" +
                            "                                        <div class=\"col-md-8\">\n" +
                            "                                            <p style=\"color: var(--mainColor)\">Date: " + review.getReviewDate() + "</p>\n" +
                            "                                        </div>\n" +
                            "                                    </div>\n" +
                            "                                </div>\n" +
                            "                            </div>\n" +
                            "                        </div>\n" +
                            "                        <p class=\"item-content\">\n" +
                            "                            <i class=\"item-header\">Commented: </i>\n" +
                            "                                " + review.getComment() + "\n" +
                            "                        </p>\n" +
                            "                    </div>");
                }
            }
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
