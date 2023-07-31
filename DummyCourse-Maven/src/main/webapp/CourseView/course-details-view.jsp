<%@ page import="java.util.List" %>
<%@ page import="entity.*" %>
<%@ page import="javax.xml.transform.Result" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="entity.course.CourseSection" %>
<%@ page import="entity.course.Course" %>
<%@ page import="entity.quiz.Notice" %>
<%@ page import="entity.quiz.Materials" %>
<%@ page import="entity.quiz.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.DAOCourse" %>
<%@ page import="entity.user.User" %>
<%@ page import="dao.DAOLectureDetails" %><%--
  Created by IntelliJ IDEA.
  User: pthanh
  Date: 6/16/2023
  Time: 9:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<% Course course = (Course) request.getAttribute("course");
    User user = (User) request.getSession().getAttribute("user");

    List<Notice> noticeList = (List<Notice>) request.getAttribute("noticeList");
    List<CourseSection> sectionList = (List<CourseSection>) request.getAttribute("sectionList");
    List<Materials> materialsList = (List<Materials>) request.getAttribute("materialList");
    List<Quiz> quizList = (List<Quiz>) request.getAttribute("quizList");
    ResultSet quizResultList = (ResultSet) request.getAttribute("quizResultList");
    List<String> quizResultListString = new ArrayList<String>();
    DAOLectureDetails daoLectureDetails = new DAOLectureDetails();
    DAOCourse daoCourse = new DAOCourse();
    try{
        while(quizResultList.next()){
            quizResultListString.add(quizResultList.getString("quizID"));
        }
    } catch (Exception e){
    }
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
                    <h6>Total: <%=materialsList.size()%> Materials</h6>
                    <h6>Quiz: <%= quizPercentage %>/<%=quizList.size()%> Done</h6>
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
                                        <a href="take-quiz?quiz-id=<%=quiz.getQuizID()%>&course-id=<%=course.getCourseID()%>"
                                           class="col-md-7 test">
                                            <p>
                                                <i class="fa-solid fa-clipboard-question"></i>&nbsp;<%=quiz.getQuizName()%>
                                            </p>
                                        </a>
                                        <% for (String quizID : quizResultListString) {%>
                                        <% if (quizID.equals(quiz.getQuizID())) { %>
                                        <a href="check-result?go=get-quiz-attempts&quiz-id=<%=quizID%>"
                                           class="col-md-5 viewResult">
                                            <p><i class="fa-regular fa-circle-check"></i>View Result</p></a>
                                        <%}%>
                                        <%}%>
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
                    <div class="courseSection" id="courseInfoBox">
                        <h5>Information:</h5>
                        <div class="separator"></div>
                        <p> - <%=course.getCourseDescription()%></p>
                        <p> - Lecturer: <%=daoCourse.getLectureName(course.getCourseID()).getUsername()%></p>
                    </div>
                    <div class="courseSection" id="courseFeedBack">
                        <h5>Leave some review for this courses:</h5>
                        <div class="separator"></div>
                        <form action="feedback" method="post" class="feedback-title">
                            <input type="text" name="userID" value="${sessionScope.user.userID}" hidden>
                            <input type="text" name="courseID" value="<%=course.getCourseID()%>" hidden>
                            <input type="text" name="go" value="sendCourseFeedback" hidden>
                            <input type="radio" name="rating" id="rate0" value="0" hidden checked>
                            <input type="radio" name="rating" id="rate1" value="1" hidden>
                            <input type="radio" name="rating" id="rate2" value="2" hidden>
                            <input type="radio" name="rating" id="rate3" value="3" hidden>
                            <input type="radio" name="rating" id="rate4" value="4" hidden>
                            <input type="radio" name="rating" id="rate5" value="5" hidden>
                            <label for="rate1" class="ratingLabel" id="rateLabel1">
                                <i class="fa-regular fa-face-angry"></i>
                            </label>
                            <label for="rate2" class="ratingLabel" id="rateLabel2">
                                <i class="fa-regular fa-face-meh"></i>
                            </label>
                            <label for="rate3" class="ratingLabel" id="rateLabel3">
                                <i class="fa-regular fa-face-smile"></i>
                            </label>
                            <label for="rate4" class="ratingLabel" id="rateLabel4">
                                <i class="fa-regular fa-face-laugh-beam"></i>
                            </label>
                            <label for="rate5" class="ratingLabel" id="rateLabel5">
                                <i class="fa-regular fa-face-laugh-squint"></i>
                            </label>
                            <p style="color: red">${requestScope.rateError}</p>
                            <p>Do you enjoy it?</p>
                            <input type="text" class="feedback-text" name="comment" value="${requestScope.courseFeedBackComment}"
                                   placeholder="Tell us..." required>
                            <p style="color: red">${requestScope.cmtError}</p>
                            <div class="separator"></div>
                            <p style="color: limegreen">${requestScope.courseFeedBackStatus}</p>
                            <input style="background-color: #34bfa3" type="submit" name="submit" value="Send" class="submitBtn">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>






