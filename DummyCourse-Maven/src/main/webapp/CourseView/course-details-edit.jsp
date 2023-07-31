<%@ page import="java.util.List" %>
<%@ page import="entity.*" %>
<%@ page import="entity.course.CourseSection" %>
<%@ page import="entity.course.Course" %>
<%@ page import="entity.quiz.QuizResult" %>
<%@ page import="entity.quiz.Notice" %>
<%@ page import="entity.quiz.Materials" %>
<%@ page import="entity.quiz.Quiz" %><%--
  Created by IntelliJ IDEA.
  User: pthanh
  Date: 6/16/2023
  Time: 9:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<% Course course = (Course) request.getAttribute("course");
    List<Notice> noticeList = (List<Notice>) request.getAttribute("noticeList");
    List<CourseSection> sectionList = (List<CourseSection>) request.getAttribute("sectionList");
    List<Materials> materialsList = (List<Materials>) request.getAttribute("materialList");
    List<Quiz> quizList = (List<Quiz>) request.getAttribute("quizList");
    List<QuizResult> quizResultList = (List<QuizResult>) request.getAttribute("quizResultList");
    int quizPercentage = (int) request.getAttribute("quizPercentage");
%>
<head>
    <title><%=course.getCourseName()%></title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        <%@include file="../CSS/course.css"%>
    </style>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="/Header/import.jsp"/>
</head>
<body>
<jsp:include page="/Header/header.jsp"/>
<div class="content-page">
    <div class="wrap-content">
        <div class="navigation-box" id='breadcrumb'>
            <ul>
                <li><a href='home'>Home</a></li>
                <li>&nbsp;&nbsp;<%=course.getCourseName()%>
                </li>
            </ul>
        </div>
        <div class="courseContainer" id="courseName">
            <h2><%=course.getCourseName()%>
            </h2>
        </div>
        <div class="courseContainer" id="noticeBox">
            <h4>Notice</h4>
            <input id="noticeBoxInput" type="checkbox" hidden>
            <label class="iButton" for="noticeBoxInput" id="noticeBtn">
                <i class="fa-solid fa-chevron-down"></i>
            </label>
            <div class="courseNoticeList">
                <% if (noticeList.isEmpty()) { %>
                <div class="row">
                    <nav class="col-md-2 noticeDate">
                        --/--/--
                    </nav>
                    <nav class="col-md-10 noticeMess">
                        There are no notice yet!
                    </nav>
                </div>
                <%} else {%>
                <% for (Notice notice : noticeList) {%>
                <div class="row">
                    <nav class="col-md-2 noticeDate">
                        <%=notice.getNoticeDate()%>
                    </nav>
                    <nav class="col-md-10 noticeMess">
                        <%=notice.getNotice()%>
                    </nav>
                </div>
                <%}%>
                <%}%>
            </div>
        </div>
        <div class="courseContainer" id="courseSection">
            <div class="row">
                <div class="col-md-7">
                    <%--                    <h6>Total: <%=materialsList.size()%> Materials</h6>--%>
                    <%--                    <h6>Test: <%= quizPercentage %>/<%=quizList.size()%> Done</h6>--%>
                    <h5>Section:</h5>
                    <% if (sectionList.isEmpty()) { %>
                    <h5>No section found!</h5>
                    <% } else { %>
                    <% for (CourseSection section : sectionList) {%>
                    <% int sectionID = section.getSectionID(); %>
                    <div class="courseSection">
                        <h5><%=section.getSectionName()%>
                        </h5>
                        <div class="row">
                            <div class="sectionMaterial col-md-6">
                                <ul>
                                    <% for (Materials material : materialsList) { %>
                                    <% if (material.getSectionID() == sectionID) { %>
                                    <li>
                                        <a href="course-details?go=downloadMaterial&fileId=<%=material.getMaterialID()%>">
                                            <p>
                                                <i class="fa-solid fa-folder-open"></i>&nbsp;<%=material.getMaterialName()%>
                                            </p>
                                        </a>
                                    </li>
                                    <%}%>
                                    <%}%>
                                </ul>
                            </div>
                            <div class="sectionTest col-md-6">
                                <ul>
                                    <% for (Quiz quiz : quizList) {%>
                                    <% if (quiz.getSectionID() == sectionID) { %>
                                    <li class="row">
                                        <a href="course-detail?go=takeQuiz&quizID=<%=quiz.getQuizID()%>"
                                           class="col-md-8 test">
                                            <p>
                                                <i class="fa-solid fa-clipboard-question"></i>&nbsp;<%=quiz.getQuizName()%>
                                            </p>
                                        </a>
                                        <a href="quiz-details?quizID=<%=quiz.getQuizID()%>" class="col-md-4">
                                            <p>
                                                <i></i> Edit Quiz
                                            </p>
                                        </a>
                                    </li>
                                    <%}%>
                                    <%}%>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <%}%>
                    <%}%>
                </div>
                <div class="col-md-5">
                    <h5>Adding items</h5>
                    <div class="courseSection editTable">
                        <h5>Add section</h5>
                        <div class="editTableOption">
                            <ul>
                                <li>
                                    <a href="course-item?courseID=<%=course.getCourseID()%>">More section here</a>
                                </li>
                            </ul>
                        </div>
                        <div class="editTableOption">
                            <h5>Create quiz</h5>
                            <ul>
                                <% for (CourseSection section : sectionList) {%>
                                <% int sectionID = section.getSectionID(); %>
                                <li>
                                    <a href="AddQuestion-actions?courseID=<%=course.getCourseID()%>&sectionID=<%=sectionID%>">
                                        <%=section.getSectionName()%>
                                    </a>
                                    <%}%>
                                </li>
                            </ul>
                        </div>
                        <div class="editTableOption">
                            <h5>Add material</h5>
                            <ul>
                                <% for (CourseSection section : sectionList) {%>
                                <% int sectionID = section.getSectionID(); %>
                                <li>
                                    <a href="course-details?go=addMaterial&sectionID=<%=sectionID%>&courseID=<%=course.getCourseID()%>">
                                        <%=section.getSectionName()%>
                                    </a>
                                </li>
                                <%}%>
                            </ul>
                        </div>
                        <div class="editTableOption">
                            <h5>Add notice</h5>
                            <ul>
                                <li>
                                    <a href="#">More notice here</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

