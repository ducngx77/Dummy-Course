<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.quiz.Quiz" %>
<%@ page import="entity.course.CourseSection" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entity.quiz.QuizQuestion" %>
<%@ page import="entity.quiz.QuizQuestionChoice" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: pthanh
  Date: 7/8/2023
  Time: 6:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quiz Details Managerment</title>
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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body style="margin-top: 4%">
<%
    Quiz quiz = (Quiz) request.getAttribute("quiz");
    CourseSection section = (CourseSection) request.getAttribute("section");
    ArrayList<QuizQuestion> questionsList = (ArrayList<QuizQuestion>) request.getAttribute("questionList");
    List<QuizQuestionChoice> questionChoiceList = (List<QuizQuestionChoice>) request.getAttribute("questionChoiceList");
    int mduration = (int) request.getAttribute("mduration");
    int sduration = (int) request.getAttribute("sduration");
%>
<jsp:include page="/Header/header.jsp"/>
<div class="content-page">
    <div class="wrap-content">
        <div class="navigation-box" id='breadcrumb'>
            <ul>
                <li><a href='home'>Home</a></li>
                <li><a href="course-details?course-id=${requestScope.courseID}">${requestScope.courseName}</a></li>
                <li><a href="quiz-details?quizID=<%=quiz.getQuizID()%>">Edit quiz: <%=quiz.getQuizName()%>
                </a></li>
            </ul>
        </div>
        <div class="quizContainer" id="createQuizTitle">
            <div class="row">
                <div class="col-md-4" id="crt-icon">
                    <span>
                        <i class="fa-solid fa-clipboard-question"></i>
                    </span>
                </div>
                <div class="col-md-8" id="crt-courseName">
                    <h3>Edit: <%=quiz.getQuizName()%>
                    </h3>
                    <h5>Section: <%=section.getSectionName()%>
                    </h5>
                </div>
            </div>
        </div>

        <div class="quizContainer">
            <div class="wrap row">
                <div class="col-md-2"></div>
                <div class="col-md-4 durationEdit">
                    <input type="hidden" name="quizID" id="quizID" value="<%=quiz.getQuizID()%>">
                    <h5>Duration</h5>
                    Minutes:
                    <input type="text" value="<%=mduration%>" name="duration" id="mduration">
                    <button type="button" class="iButton" onclick="decrease()">
                        <i class="fa-solid fa-minus"></i>
                    </button>
                    <button type="button" class="iButton" onclick="increase()">
                        <i class="fa-solid fa-plus"></i>
                    </button>
                    <br/>
                    Seconds:
                    <input type="text" value="<%=sduration%>" name="duration" id="sduration">
                    <button type="button" class="iButton" onclick="decrease1()">
                        <i class="fa-solid fa-minus"></i>
                    </button>
                    <button type="button" class="iButton" onclick="increase1()">
                        <i class="fa-solid fa-plus"></i>
                    </button>
                </div>
                <div class="col-md-4">
                    <h5>Start Time</h5>
                    <div>
                        <input type="datetime-local" name="starttime" id="starttime" value="<%=quiz.getStartTime()%>">
                    </div>
                </div>
                <div class="col-md-2"></div>
            </div>

            <div class="wrap row">
                <div class="col-md-2"></div>
                <div class="col-md-4 durationEdit">
                    <button type="button" class="Button" onclick="changeDuration()"
                            style="background-color: #ff6324; color: white; border-radius: 5px">
                        <i class="fa-solid fa-pencil"></i>&nbsp;Save
                    </button>

                </div>
                <div class="col-md-4">
                    <button type="button" class="Button" onclick="changeStartTime()"
                            style="background-color: #ff6324; color: white; border-radius: 5px">
                        <i class="fa-solid fa-pencil"></i>&nbsp;Save
                    </button>

                </div>
                <div class="col-md-2"></div>
            </div>
        </div>
        <div class="quizContainer">

            <div class="wrap" id="mngQLable">
                <h4>Manage questions:</h4>
            </div>
            <% for (QuizQuestion question : questionsList) { %>
            <div class="question-wrap" id="rm<%=question.getQuestionID()%>">
                <div class="questionEdit row">
                    <div class="col-md-8">
                        <h4><%=question.getQuestionText()%>
                        </h4>
                        <% for (QuizQuestionChoice choice : questionChoiceList) { %>
                        <%if (choice.getQuestionId().equals(question.getQuestionID())) {%>
                        <div class="row">
                            <div class="col">
                                <input type="checkbox" name="IsCorrect"
                                       id="IsCorrect" <%=(choice.isCorrect()? "checked" : "")%>>
                            </div>
                            <div class="col-md-10">
                                <label for="IsCorrect"><%=choice.getChoiceText()%>
                                </label>
                            </div>
                        </div>
                        <%}%>
                        <%}%>
                    </div>
                    <div class="col-md-3">
                        <div class="row justify-content-end">
                            <div class="col-0">
                                <button type="button" class="Button"
                                        style="background-color: #ff6324; color: white; border-radius: 5px; height: 100%">
                                    <i class="fa-solid fa-wrench"></i><br/>
                                    <a href="editquestion?questionID=<%=question.getQuestionID()%>&course-id=<%=section.getCourseID()%>&quiz-id=<%=quiz.getQuizID()%>"
                                       class="saveCQ">Edit
                                        question</a>
                                </button>
                            </div>
                            <div class="col-2">
                                <button type="button" class="Button"
                                        onclick="RemoveQuestion('<%=question.getQuestionID()%>')"
                                        style="background-color: #3732ff; color: white; border-radius: 5px">
                                    <i style="font-size:24px" class="fa">&#xf00d;</i>
                                    <p>Remove</p>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%}%>
            <div class="row justify-content-start">
                <div class="col-2">
                    <button type="button" class="Button"
                            style="background-color: #ff6324; color: white; border-radius: 5px; height: 100%">
                        <i class="fa fa-plus-square"></i>
                        <a style="color: white"
                           href="addquestion?quizID=<%=quiz.getQuizID()%>&sectionID=<%=quiz.getSectionID()%>&courseID=<%=quiz.getCourseID()%>">
                            Create new question</a>
                    </button>
                </div>
                <div class="col">
                    <button type="button" class="Button" onclick=""
                            style="background-color: #3732ff; color: white; border-radius: 5px">
                        <i style="font-size:24px" class="fa fa-university"></i>
                        <a style="color: white"
                           href="add-question-from-bank?quizID=<%=quiz.getQuizID()%>&courseID=<%=quiz.getCourseID()%>">
                            Add question from bank</a>
                    </button>
                </div>
            </div>
        </div>

    </div>
</div>


<script>
    function decrease() {
        var quantity = document.getElementById("mduration");
        if (quantity.value > 0) {
            quantity.value--;
        }
    }

    function increase() {
        var quantity = document.getElementById("mduration");
        if (quantity.value < 120) {
            quantity.value++;
        }
    }

    const input = document.getElementById("mduration");
    input.addEventListener("input", function () {
        this.value = this.value.replace(/[^0-9]/g, '');
        (this.value == null ? alert("cannot be null!") : "");
    });
</script>
<script>
    function decrease1() {
        var quantity = document.getElementById("sduration");
        if (quantity.value > 0) {
            quantity.value--;
        }
    }

    function increase1() {
        var quantity = document.getElementById("sduration");
        if (quantity.value < 120) {
            quantity.value++;
        }
    }

    const input1 = document.getElementById("sduration");
    input1.addEventListener("input", function () {
        this.value = this.value.replace(/[^0-9]/g, '');
    });
</script>
<script>
    function changeDuration() {
        var mduration = document.getElementById("mduration").value;
        var sduration = document.getElementById("sduration").value;
        var quizID = document.getElementById("quizID").value;
        let data = {
            go: "changeDuration",
            mduration: mduration,
            sduration: sduration,
            quizID: quizID
        }
        $.ajax({
            url: 'change-duration',
            method: 'POST',
            data: data,
            success: function (response) {
            },
            error: function (xhr, status, error) {
                // Handle any errors
                console.log(error);
            }
        });

    }

    //
    // function EditQuestion(questionID){
    //     let data = {
    //         questionID : questionID
    //     };
    //
    //     $.ajax({
    //         url: 'editquestion',
    //         method: 'GET',
    //         data: data,
    //         success: function (response) {
    //
    //         },
    //         error: function (xhr, status, error) {
    //             // Handle any errors
    //             console.log(error);
    //         }
    //     });
    // }
    function changeStartTime() {
        var starttime = document.getElementById("starttime").value;
        var quizID = document.getElementById("quizID").value;
        let data = {
            go: "changeStartTime",
            starttime: starttime,
            quizID: quizID
        }
        $.ajax({
            url: 'change-starttime',
            method: 'POST',
            data: data,
            success: function (response) {
            },
            error: function (xhr, status, error) {
                // Handle any errors
                console.log(error);
            }
        });
    }

    function RemoveQuestion(questionID) {
        var confirmed = confirm("Are you sure want to remove this question")
        var quizID = document.getElementById("quizID").value;
        if (confirmed) {
            let data = {
                go: "removeQuestion",
                questionID: questionID,
                quizID: quizID
            };

            $.ajax({
                url: 'remove-question',
                method: 'POST',
                data: data,
                success: function (response) {
                    var questionContainer = document.getElementById("rm" + questionID);
                    questionContainer.parentNode.removeChild(questionContainer);
                },
                error: function (xhr, status, error) {
                    // Handle any errors
                    console.log(error);
                }
            });
        }
    }

    function AddQuestion(quizID, sectionID, courseID) {
        let data = {
            quizID: quizID,
            sectionID: sectionID,
            courseID: courseID
        };

        $.ajax({
            url: 'addquestion',
            method: 'GET',
            data: data,
            success: function (response) {

            },
            error: function (xhr, status, error) {
                // Handle any errors
                console.log(error);
            }
        });
    }
</script>
</body>
</html>
