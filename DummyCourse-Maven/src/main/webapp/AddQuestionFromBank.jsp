<%--
  Created by IntelliJ IDEA.
  User: pthanh
  Date: 6/18/2023
  Time: 9:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.course.Course" %>
<%@ page import="entity.course.CourseSection" %>
<%@ page import="entity.quiz.Quiz" %>
<%@ page import="java.util.Objects" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.quiz.QuizQuestion" %>
<html>
<head>
    <title>Add Question</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        <%@include file="/CSS/quiz.css"%>
    </style>
    <style>
        <%@include file="/CSS/home.css"%>
    </style>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@include file="/Header/import.jsp" %>
</head>
<%
    List<CourseSection> sectionList = (List<CourseSection>) request.getAttribute("sectionList");
    List<QuizQuestion> questionList = (List<QuizQuestion>) request.getAttribute("questionList");
    List<String> questionListCurrentQuiz = (List<String>) request.getAttribute("questionListCurrentQuiz");
    Quiz quiz = (Quiz) request.getAttribute("quiz");
    String sectionID = (String) request.getAttribute("sectionID");
%>
<body>
<%@include file="/Header/header.jsp" %>
<div class="content-page">
    <div class="homeContainer">
        <form action="add-question-from-bank" method="post">
            <input type="hidden" name="go" value="addQuestionFromBank">
            <input type="hidden" name="quizID" value="<%=quiz.getQuizID()%>">
            <div class="row">
                <div class="col-10"><input type="text" name="SearchBox" placeholder="Search a question" oninput="searchQuestions()" id="searchBox"></div>
                <div class="col-2">
                  <select name="section" id="section">
                    <% for (CourseSection section : sectionList) {%>
                      <%if(sectionID == null){%>
                    <option value="<%=section.getSectionID()%>" <%=(section.getSectionID() == quiz.getSectionID()?"selected" : "")%>>
                        <a href="add-question-from-bank?quizID=<%=quiz.getQuizID()%>&courseID=<%=quiz.getCourseID()%>&sectionID=<%=section.getSectionID()%>"><%=section.getSectionName()%></a>
                    </option>
                      <%} else {%>
                      <option value="<%=section.getSectionID()%>" <%=(section.getSectionID() == Integer.parseInt(sectionID)?"selected" : "")%>>
                          <a href="add-question-from-bank?quizID=<%=quiz.getQuizID()%>&courseID=<%=quiz.getCourseID()%>&sectionID=<%=section.getSectionID()%>"><%=section.getSectionName()%></a>
                      </option>
                      <%}%>
                    <%}%>
                  </select>
                </div>
            </div>
            <% for (QuizQuestion question : questionList){%>
                <%if(!(question.getQuizID().equals(quiz.getQuizID())) && !questionListCurrentQuiz.contains(question.getQuestionText())){%>
                <div class="row question-row">
                    <div class="col-md-2">
                        <input type="checkbox" name="questionID" value="<%=question.getQuestionID()%>" id="questionID">
                    </div>
                    <div class="col-md-9">
                        <label for="questionID"><%=question.getQuestionText()%></label>
                    </div>
                </div>
                <%}%>
            <%}%>
            <div class="row">
                <div class="col-md-9"></div>
                <div class="col-md-3">
                    <div class="cancel">
                            <button>
                                <input type="submit" name="submit" value="Add Question">
                            </button>
                        <input type="button" value="Cancel" onclick="Cancel('<%=quiz.getQuizID()%>')">
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
</div>
<script>
    // Event listener for section selection change
    document.getElementById("section").addEventListener("change", function() {
        var selectedSectionId = this.value;
        var quizId = "<%=quiz.getQuizID()%>";
        var courseId = "<%=quiz.getCourseID()%>";

        // Construct the URL based on the selected section and redirect the user
        var url = "add-question-from-bank?quizID=" + quizId + "&courseID=" + courseId + "&sectionID=" + selectedSectionId;
        window.location.href = url;
    });
</script>
<script>
    function Cancel(quizID){
        var url = "quiz-details?quizID=" + quizID;
        window.location.href = url;
    }
</script>
<script>
    function searchQuestions() {
        console.log("searchQuestions() function is called");
        var searchQuery = document.getElementById("searchBox").value.toLowerCase();
        console.log(searchQuery.trim());
        console.log("length:" + searchQuery.length);
        var questions = document.getElementsByClassName("question-row");
        var foundQuestion = false;
        for (var i = 0; i < questions.length; i++) {
            var questionText = questions[i].innerText.toLowerCase();
            console.log(questionText.trim());
            if(searchQuery.length == 0){
                console.log("false");
                questions[i].style.backgroundColor = "white"; // Set default background color for non-matching rows
            } else {
                if (questionText.includes(searchQuery)) {
                    console.log("true");
                    questions[i].style.backgroundColor = "yellow"; // Set background color for matching rows
                    if (!foundQuestion) {
                        questions[i].scrollIntoView({ behavior: 'smooth' }); // Scroll to the first matched question
                        foundQuestion = true;
                    }
                } else {
                    console.log("false");
                    questions[i].style.backgroundColor = "white"; // Set default background color for non-matching rows
                }
            }
        }
    }
</script>
</body>
</html>
