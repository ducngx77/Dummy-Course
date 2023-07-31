<%@ page import="java.util.ArrayList" %>
<%@ page import="entity.quiz.QuizQuestion" %>
<%@ page import="entity.quiz.QuizQuestionChoice" %>
<%@ page import="dao.DAOQuizQuestion" %>
<%@ page import="dao.DAOQuizQuestionChoice" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../CSS/quiz.css"/>
    <title>Take Quiz</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        <%@include file="../CSS/home.css" %>
        <%@include file="../CSS/quiz.css"%>
        .popup {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 9999;
        }

        .popup-content {
            background-color: #fff;
            width: 300px;
            max-width: 90%;
            margin: 100px auto;
            padding: 20px;
            text-align: center;
        }

    </style>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="/Header/import.jsp"/>
</head>
<%
    ArrayList<QuizQuestion> quizQuestions = (ArrayList<QuizQuestion>) request.getAttribute("quiz-questions");
    String quizID = (String) request.getAttribute("quiz-id");
    String courseName = (String) request.getAttribute("course-name");
    String quizName = (String) request.getAttribute("quiz-name");
    if (quizQuestions == null || quizQuestions.size() == 0) {
        session.setAttribute("error", "No quiz questions found");
        session.setAttribute("page", "home");
        response.sendRedirect("error.jsp");
    }
%>
<body>
<jsp:include page="/Header/header.jsp"/>

<div class="time-end-popup">

</div>

<div class="quiz-content-page">

    <div class="wrap-content" id="quiz-wrap-content">

        <div id="popup" class="popup">
            <div class="popup-content">
                <h2>Test finished</h2>
                <p>Do you want to submit your quiz?</p>
                <div class="row">
                    <div class="col-md-12">
                        <div class="submitBtnBox">
                            <button class="submitBtn" onclick="submitQuiz()">
                                Yes
                            </button>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <p>Your quiz score will be auto submitted in <span id="time-countdown"></span> seconds</p>
                </div>
            </div>
        </div>

        <div class="quizContainer quizTitle">
            <div>
                <h3>Quiz: <%=quizName%>
                </h3>
                <h5>Course: <%=courseName%>
            </div>
        </div>
        <form class="quizContainer row" id="take-quiz-page" action="take-quiz" method="post">
            <input type="hidden" name="quiz-id" value="<%=quizID%>">
            <input type="hidden" name="go" value="submit">
            <div class="col-md-9 quiz-question-list">
                <% for (int i = 0; i < quizQuestions.size(); i++) { %>
                <% QuizQuestion quizQuestion = quizQuestions.get(i); %>
                <div class="sQuizQuestion" id="ansId<%=quizQuestion.getQuestionID()%>">
                    <div class="question-header">
                        <h5>Question<%=" " + (i + 1)%>
                        </h5>
                        <p><%=quizQuestion.getQuestionText()%>
                        </p>

                        <div class="flag-choice">
                            <input type="checkbox" id="<%=quizQuestion.getQuestionID()%>"
                                   onchange="flagQuestion('<%="question-" + quizQuestion.getQuestionID()%>')">
                            <label for="<%=quizQuestion.getQuestionID()%>" class="answerCtn">
                                Flag
                            </label>
                        </div>
                        <%--                <img src="#">--%>
                    </div>
                    <h7 class="ml-3">
                        <%="Select " + new DAOQuizQuestionChoice().getCorrectAnswerNumbers(quizQuestion.getQuestionID()) + " answer(s)"%>
                    </h7>
                    <%
                        ArrayList<QuizQuestionChoice> quizQuestionChoices = new DAOQuizQuestion().getAllQuestionChoice(quizQuestion.getQuestionID()); %>
                    <div class="ansList">
                        <ul>
                            <% for (QuizQuestionChoice quizQuestionChoice : quizQuestionChoices) { %>
                            <li class="question-choice">
                                <input type="checkbox"
                                       name="question-id=<%=quizQuestion.getQuestionID()%>choice-id=<%=quizQuestionChoice.getChoiceId()%>"
                                       id="ansId<%=quizQuestionChoice.getChoiceId()%>"
                                       class="answerCheckInput"
                                       value="<%=quizQuestionChoice.getChoiceId()%>"
                                       onclick="chooseAnswer('<%="question-" + quizQuestion.getQuestionID()%>', 'ansId<%=quizQuestion.getQuestionID()%>')">
                                <label class="answerCtn" for="ansId<%=quizQuestionChoice.getChoiceId()%>">
                                    <i class="fa-regular fa-square"></i>
                                    <i class="fa-regular fa-square-check"></i>
                                    <%=quizQuestionChoice.getChoiceText()%>
                                </label>
                            </li>
                            <% } %>
                        </ul>
                    </div>

                </div>
                <% } %>
                <div class="submitBtnBox">
                    <input type="submit" value="Submit" class="submitBtn">
                </div>
            </div>
            <div class="col-md-3">
                <div class="status-box">
                    <h5>Your Progress</h5>
                    <div class="wrap-row">
                                <span class="row str-status-box">
                                    <% for (int i = 0; i < quizQuestions.size(); i++) { %>
                                    <% QuizQuestion quizQuestion = quizQuestions.get(i); %>

                                        <div class="col-md-3 col-sm-6 questionNoBox"
                                             id="<%="question-" + quizQuestion.getQuestionID()%>">
                                            <a href="#ansId<%=quizQuestion.getQuestionID()%>"
                                               onclick="scrollTopOfScreen('#ansId<%=quizQuestion.getQuestionID()%>')">
                                                <p><%=i + 1%></p>
                                            </a>
                                        </div>

                                    <% } %>
                                </span>
                    </div>
                    <p>Note: </p>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="marked">Marked</div>
                        </div>
                        <div class="col-md-4">
                            <div class="done">Done</div>
                        </div>
                        <div class="col-md-4">
                            <div class="notDone">Not-done</div>
                        </div>
                    </div>
                    <div class="row mt-4">
                        <div class="col-md-12">
                            <p>Time left: </p>
                            <i class="time-left" id="time-left"></i>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="submitBtnBox">
                                <input type="submit" value="Submit" class="submitBtn" onclick="submitQuiz()">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    let submitCountdownTime = 10;
    let submitCountdownInterval;


    function
    submitCountdown() {
        let timeDisplay = document.getElementById("time-countdown");
        timeDisplay.textContent = '' + submitCountdownTime;
        if (submitCountdownTime === 0) {
            clearInterval(submitCountdownInterval);
            submitQuiz();
        }
        submitCountdownTime--;
    }

    function submitQuiz() {
        document.getElementById("take-quiz-page").submit();
    }

    function openPopup() {
        document.getElementById("popup").style.display = "block";
        submitCountdownInterval = setInterval(submitCountdown, 1000);
    }

    function closePopup() {
        document.getElementById("popup").style.display = "none";
        clearInterval(submitCountdownInterval);
    }


    let updateTimeInterval = setInterval(updateTime, 1000);

    function updateTime() {
        $.ajax({
            url: 'take-quiz',
            method: 'GET',
            data: {
                go: "get-remaining-time"
            },
            success: function (response) {
                let timeLeft = document.getElementById("time-left");
                timeLeft.innerHTML = response;
                if (timeLeft.innerText === "Test finished") {
                    clearInterval(updateTimeInterval);
                    openPopup();
                }
            },
            error: function (xhr, status, error) {
                // Handle any errors
                console.log(error);
            }
        });
    }

    function toggleActiveClass(id) {
        let ans = document.getElementById(id);
        if (ans.checked) {
            ans.classList.remove("not-selected");
            ans.classList.add("selected");
        } else {
            ans.classList.remove("selected");
            ans.classList.add("not-selected");
        }
    }

    function chooseAnswer(questionID, box) {
        let theBox = document.getElementById(box);
        let checkboxes = theBox.querySelectorAll('input[type="checkbox"]');


        let checked = false;
        checkboxes.forEach(checkbox => {
            if (checkbox.checked) {
                checked = true;
            }
        });
        if (!document.getElementById(questionID).classList.contains("flagged")) {
            if (checked) {
                document.getElementById(questionID).classList.add("selectedAnswer");
            } else {
                document.getElementById(questionID).classList.remove("selectedAnswer");
            }
        }
    }

    function flagQuestion(boxId) {
        let item = document.getElementById(boxId);
        if (item.classList.contains("selectedAnswer"))
            item.classList.remove("selectedAnswer");
        item.classList.toggle("flagged");
    }

    function scrollTopOfScreen(targetSelector) {

        setTimeout(() => {
            window.scrollTo({
                top: 0,
                behavior: 'smooth' // For smooth scrolling, you can also use 'auto' or 'instant'
            });
        }, 200); // The delay time in milliseconds (200ms in this case)

    }

</script>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</body>
</html>
