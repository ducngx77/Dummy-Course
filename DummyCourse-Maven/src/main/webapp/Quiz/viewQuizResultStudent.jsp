<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.DAOCourse" %>
<%@ page import="entity.*" %>
<%@ page import="dao.DAOSystemReview" %>
<%@ page import="dao.DAOUser" %>
<%@ page import="java.math.RoundingMode" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="dao.DAOQuiz" %>
<%@ page import="entity.user.Role" %>
<%@ page import="entity.quiz.QuizResult" %>
<%@ page import="entity.course.Course" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--<!doctype html>--%>
<html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Home</title>
    <style>
        <%@include file="../CSS/home.css" %>
    </style>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="/Header/import.jsp"/>
</head>

<%
    ArrayList<QuizResult> quizResults = (ArrayList<QuizResult>) request.getAttribute("quiz-results");
    String quizName = (String) request.getAttribute("quiz-name");
    String quizID = (String) request.getAttribute("quiz-id");
    Course course = (Course) request.getAttribute("course");
    if (quizResults == null) {
        session.setAttribute("error", "You have not taken any quiz yet");
        session.setAttribute("page", "home");
        response.sendRedirect("error.jsp");
        return;
    }
    DecimalFormat df = new DecimalFormat("#.###");
    df.setRoundingMode(RoundingMode.UP);
%>
<body>
<jsp:include page="/Header/header.jsp"/>
<div class="content-page">
    <div class="wrap-content">
        <div class="navigation-box" id='breadcrumb'>
            <ul>
                <li><a href="home">Home</a></li>
                <li><a href="course-details?course-id=<%=course.getCourseID()%>"><%=course.getCourseName()%></a></li>
                <%--                <li><a href='/css/css_navbar.asp'>CSS Navbar</a></li>--%>
                <li>&nbsp;Result for <%=quizName%></li>
            </ul>
        </div>
        <div class="homeContainer" id="topRate">
            <h5>Quiz name: <%=quizName%>
            </h5>
            <h6>Student name: <%=new DAOUser().getUsernameByID(((Role) session.getAttribute("role")).getUserID())%>
            </h6>
            <br>
            <h5>All taken attempts of <%=quizName%>:</h5>

            <div class="row courses">
                <%for (QuizResult quizResult : quizResults) {%>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-item">
                        <div class="courseTitle">
                            <h5>Attempt <%=quizResult.getTestTime()%>
                            </h5>
                        </div>
                        <div class="courseSum">
                            <p>weight: <%=Double.parseDouble(df.format(quizResult.getScore()))%>
                                / <%=Double.parseDouble(df.format(new DAOQuiz().getTotalQuizQuestionWeight(quizID)))%>
                            </p>
                            <p>
                                Score: <%=Double.parseDouble(df.format(Double.parseDouble(df.format(quizResult.getScore())) / new DAOQuiz().getTotalQuizQuestionWeight(quizID) * 100))%>
                                / 100.0
                            </p>
                        </div>
                        <a href="check-result?go=results&result-id=<%=quizResult.getResultId()%>&quiz-id=<%=quizID%>">
                            Go to details --></a>
                    </div>
                </div>
                <%}%>
            </div>
        </div>


        <script>
            document.getElementById('testss').addEventListener('click', function () {
                window.onload = function () {
                    var button = document.getElementById('testss');
                    if (button) {
                        button.scrollIntoView({behavior: 'smooth'});
                    }
                };
            });

            var paginationLinks = document.querySelectorAll('#wrap-pagination .pagination a');
            paginationLinks.forEach(function (link, index) {
                link.addEventListener('click', function (event) {
                    event.preventDefault();
                    paginationLinks.forEach(function (link) {
                        link.classList.remove('active');
                    });
                    var currentIndex = Array.from(paginationLinks).indexOf(this);
                    if (this.classList.contains('prev')) {
                        if (currentIndex > 0) {
                            paginationLinks[currentIndex].classList.remove('active');
                            paginationLinks[currentIndex - 1].classList.add('active');
                        }
                    } else if (this.classList.contains('next')) {
                        if (currentIndex < paginationLinks.length - 1) {
                            paginationLinks[currentIndex].classList.remove('active');
                            paginationLinks[currentIndex + 1].classList.add('active');
                        }
                    } else {
                        this.classList.add('active');
                    }
                });
            });
        </script>
    </div>
</div>

</body>
</html>
