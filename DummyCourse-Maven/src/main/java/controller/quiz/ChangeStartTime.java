package controller.quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import dao.DAOQuiz;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author pthanh
 */
@WebServlet(name = "ChangeStartTime", urlPatterns = {"/change-starttime"})
public class ChangeStartTime extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String go = request.getParameter("go");
            DAOQuiz daoQ = new DAOQuiz();
            if(go.equals("changeStartTime")) {
                String quizID = request.getParameter("quizID");
                String starttime = request.getParameter("starttime");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date dateobj = new Date();
                String currentDateTime = df.format(dateobj);
                String currentDate = currentDateTime.split(" ")[0];
                String currentTime = currentDateTime.split(" ")[1];
                String checkDate = starttime.substring(0, 10);
                String checkTime = starttime.substring(11);
                if (checkDate.compareTo(currentDate) >= 0) {
                    LocalTime currentT = LocalTime.of(Integer.parseInt(currentTime.split(":")[0]), Integer.parseInt(currentTime.split(":")[1]));
                    LocalTime checkT = LocalTime.of(Integer.parseInt(checkTime.split(":")[0]), Integer.parseInt(checkTime.split(":")[1]));
                    LocalTime currentPlus30Mins = currentT.plusMinutes(30);
                    if (!currentPlus30Mins.isAfter(checkT)) {
                        LocalTime lt = LocalTime.of(0, 0, 0);
                        LocalDateTime ldt = LocalDateTime.of(Integer.parseInt(checkDate.split("-")[0]), Integer.parseInt(checkDate.split("-")[1]), Integer.parseInt(checkDate.split("-")[2]), Integer.parseInt(checkTime.split(":")[0]), Integer.parseInt(checkTime.split(":")[1]));
                        daoQ.updateStartTime(ldt, quizID);
                    } else return;
                } else return;
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
