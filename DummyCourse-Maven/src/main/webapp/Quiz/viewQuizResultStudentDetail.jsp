<%@ page import="java.util.ArrayList" %>
<%@ page import="entity.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.math.RoundingMode" %>
<%@ page import="entity.user.Role" %>
<%@ page import="entity.quiz.QuizResult" %>
<%@ page import="entity.quiz.QuizQuestionChoice" %>
<%@ page import="entity.quiz.QuizResultQuestion" %>
<%@ page import="entity.quiz.QuizResultAnswer" %>
<%@ page import="dao.*" %>
<%@ page import="entity.course.Course" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 6/20/2023
  Time: 11:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz Result</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        <%@include file="../CSS/quiz.css"%>
    </style>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="/Header/import.jsp"/>
</head>
<%
    Double result = (Double) request.getAttribute("result");
    String quizID = (String) request.getAttribute("quiz-id");
    Course course = (Course) request.getAttribute("course");
    ArrayList<QuizResultQuestion> quizResultQuestions = (ArrayList<QuizResultQuestion>) request.getAttribute("quiz-result-questions");
    int i = 0;
    int total = 0;
    for (QuizResultQuestion quizResultQuestion : quizResultQuestions) {
        if (quizResultQuestion.getPoint() != 0)
            i++;
        total++;
    }

    request.setAttribute("total", total);
    request.setAttribute("correctQuestion", i);

    if (Double.isNaN(result) || quizID == null) {
        session.setAttribute("error", "Error occurred in viewQuizResultStudentDetail.jsp, Can't find quizID or result");
        response.sendRedirect("home");
    }
    String quizName = new DAOQuiz().getQuizByID(quizID).getQuizName();
    DecimalFormat df = new DecimalFormat("#.###");
    df.setRoundingMode(RoundingMode.UP); // choose rounding mode as per your requirement
    DecimalFormat weightFormat = new DecimalFormat("#.##");

%>
<body>
<jsp:include page="/Header/header.jsp"/>
<div class="content-page">
    <div class="wrap-content">
        <div class="navigation-box" id='breadcrumb'>
            <ul>
                <li><a href='home'>Home</a></li>
                <li><a href="course-details?course-id=${course.courseID}">${course.courseName}</a></li>
                <li>&nbsp;&nbsp;Result details</li>
            </ul>
        </div>
        <h2 class="quizTitle quizContainer">Result details</h2>
        <div class="quizContainer">
            <div class="row">
                <div class="col-md-4 qr-studentInfo">
                    <h1 class="qr-stnName"><i class="fa-solid fa-user-pen"></i>
                        <%=new DAOUser().getUsernameByID(((Role) session.getAttribute("role")).getUserID())%>
                    </h1>
                    <h2 class="qr-status"><i class="fa-solid fa-clipboard-question"></i> <%=quizName%>
                    </h2>
                    <br>
                    <h2 class="qr-status"><i
                            class="fa-solid fa-square-poll-vertical"></i>Final: <%=Double.parseDouble(df.format(result / new DAOQuiz().getTotalQuizQuestionWeight(quizID) * 100))%>
                        / 100.0
                    </h2>
                    <br>
                    <h3 class="qr-status">
                        <i class="fa-solid fa-person-dots-from-line"></i> Total questions: ${requestScope.total}
                    </h3>
                    <br>
                    <h3 class="qr-status">
                        <i class="fa-solid fa-check"></i> Correct questions: ${requestScope.correctQuestion}
                    </h3>
                    <br>
                    <h3 class="qr-status">
                        <i class="fa-sharp fa-solid fa-xmark"></i> incorrect
                        questions: ${requestScope.total - requestScope.correctQuestion}
                    </h3>

                </div>
                <div class="col-md-8 qr-quizQuestion">
                    <%for (QuizResultQuestion quizResultQuestion : quizResultQuestions) {%>
                    <div class="qr-question">
                        <div class="row">
                            <div class="col-md-10">
                                <h4><%=new DAOQuizQuestion().getQuizQuestionByID(quizResultQuestion.getQuestionId()).getQuestionText()%>
                                </h4>
                                <%ArrayList<QuizResultAnswer> quizResultAnswers = new DAOQuizResultAnswer().getAllResultAnswerByResultQuestionID(quizResultQuestion.getResultQuestionId());%>
                                <%if (quizResultAnswers.size() > 0) {%>
                                <h5>your answer:</h5>
                                <%for (QuizResultAnswer quizResultAnswer : quizResultAnswers) {%>
                                <%=new DAOQuizQuestionChoice().getQuestionChoiceByID(quizResultAnswer.getChoiceID()).getChoiceText()%>
                                <br>
                                <%}%>
                                <%} else {%>
                                <h5>You have not answered this question</h5>
                                <%}%>

                                <div class="yourQDetails" style="display: none;"
                                     id="<%=new DAOQuizQuestion().getQuizQuestionByID(quizResultQuestion.getQuestionId()).getQuestionID()%>">
                                    <h5>correct answer:</h5>
                                    <%
                                        ArrayList<QuizQuestionChoice> quizQuestionChoices = (ArrayList<QuizQuestionChoice>) new DAOQuizQuestionChoice().getAllByQuestionId(quizResultQuestion.getQuestionId());%>
                                    <%for (QuizQuestionChoice choice : quizQuestionChoices) {%>
                                    <%if (choice.isCorrect()) { %>
                                    <%=choice.getChoiceText()%><br>
                                    <%}%>
                                    <%}%>
                                    <p>
                                        Explanation
                                        :<%=new DAOQuizQuestion().getQuizQuestionByID(quizResultQuestion.getQuestionId()).getExplanation()%>
                                    </p>
                                </div>
                            </div>
                            <div class="col-md-2 qr-questionPoint">
                                <p>Weight: <%=weightFormat.format(quizResultQuestion.getPoint())%>
                                </p>
                                <p class="reviewBtn"
                                   onclick="toggleDetails('<%=new DAOQuizQuestion().getQuizQuestionByID(quizResultQuestion.getQuestionId()).getQuestionID()%>')">
                                    Review</p>
                            </div>
                        </div>
                    </div>
                    <%}%>

                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function toggleDetails(qId) {
        var details = document.getElementById(qId);
        if (details.style.display === "none") {
            details.style.display = "block";
        } else {
            details.style.display = "none";
        }
    }
</script>
</body>
</html>
