<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.*" %>
<%@ page import="entity.course.CourseSection" %>
<%@ page import="entity.course.Course" %>
<%@ page import="entity.quiz.QuizResult" %>
<%@ page import="entity.quiz.Notice" %>
<%@ page import="entity.quiz.Materials" %>
<%@ page import="entity.quiz.Quiz" %>
<%@ page import="entity.user.LectureDetails" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.DAOUser" %>
<%@ page import="entity.course.CourseType" %><%--
  Created by IntelliJ IDEA.
  User: pthanh
  Date: 6/16/2023
  Time: 9:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Course Detail</title>
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
<% Course course = (Course) request.getAttribute("course");
    List<LectureDetails> listLec= (List<LectureDetails>) request.getAttribute("listLec");
    List<CourseType> listCoTy = (List<CourseType>) request.getAttribute("listCoTy");
    DAOUser daoU = new DAOUser();
    List<Notice> noticeList = (List<Notice>) request.getAttribute("noticeList");
    List<CourseSection> sectionList = (List<CourseSection>) request.getAttribute("sectionList");
    List<Materials> materialsList = (List<Materials>) request.getAttribute("materialList");
%>

<div class="content-page">
    <div class="wrap-content">
        <div class="navigation-box" id='breadcrumb'>
            <ul>
                <li><a href='home'>Home</a></li>
<%--                <li><a href="course-details?course-id=<%=course.getCourseID()%>"><%=course.getCourseName()%></a></li>--%>
<%--                <li>&nbsp;&nbsp;Create quiz for <%=course.getCourseName()%></li>--%>
            </ul>
        </div>
        <div class="courseContainer" id="courseName">
            <h2>Update course: <%=course.getCourseName()%>
            </h2>
        </div>
        <div class="courseContainer" id="updateCourseForm">
            <form action="updateCourse" method="post">
                <input type="text" name="go" value="updateCourse" hidden>
                <input type="text" name="courseID" value="${requestScope.course.courseID}" hidden>
                <h5>Course Name:</h5>
                <input type="text" name="newCourseName" value="${requestScope.course.courseName}" required>
                <p style="color: red">${requestScope.ncnError}</p>
                <h5>Description:</h5>
                <input type="text" name="newCourseDesc" value="${requestScope.course.courseDescription}" required>
                <p style="color: red">${requestScope.ncdError}</p>
                <h5>Category:</h5>
                <select name="newCourseTypeID">
                    <%for(CourseType courseType : listCoTy) {%>
                    <%if (course.getCourseTypeID() == courseType.getCourseTypeID()) {%>
                        <option value="<%=courseType.getCourseTypeID()%>" selected>
                        <%=courseType.getTypeName()%>
                    </option>
                    <%} else {%>
                    <option value="<%=courseType.getCourseTypeID()%>">
                        <%=courseType.getTypeName()%>
                    </option>
                    <%}%>
                    <%}%>
                </select>
                <h5>Lecturer:</h5>
                <select name="newLecturerID" >
                    <%for(LectureDetails lecturer : listLec) {%>
                    <%if (course.getLecturerID() == lecturer.getUserID()) {%>
                    <option value="<%=lecturer.getLectureID()%>" selected>
                        <%=daoU.getUsernameByID(lecturer.getUserID())%>
                    </option>
                    <%} else {%>
                    <option value="<%=lecturer.getUserID()%>">
                        <%=daoU.getUsernameByID(lecturer.getUserID())%>
                    </option>
                    <%}%>
                    <%}%>
                </select>
                <h5>ActivateStatus:</h5>
                <select name="newStatus" >
                    <option value="1">Activate</option>
                    <option value="0">Deactivate</option>
                </select><br/>
                <input type="submit" value="Submit" class="submitBtn">
            </form>
        </div>
    </div>
</div>
</body>
</html>

