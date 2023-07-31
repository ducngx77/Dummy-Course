<%--
  Created by IntelliJ IDEA.
  User: DucNX
  Date: 6/20/2023
  Time: 4:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Section</title>
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
                <li><a href="home">Home</a></li>
                <li><a href="course-details?course-id=${requestScope.courseID}">${requestScope.courseName}</a></li>
                <%--                <li><a href='/css/css_navbar.asp'>CSS Navbar</a></li>--%>
                <li>&nbsp;&nbsp;Add Section for ${courseName}</li>
            </ul>
        </div>
        <div class="courseContainer" id="courseName">
            <h2>${courseName}</h2>
        </div>
        <div class="courseContainer" id="createSection">
            <h3>Add Section</h3>
            <form action="course-item" method="post">
                <div class="sectionCreationBox">
                    <div class="formPart">
                        <h5>Section name</h5>
                        <input type="text" name="sectionName" value="${requestScope.sectionName}">
                        <p>Must start with uppercase letter</p>
                        <p style="color: red">${requestScope.sectionNameError}</p>
                    </div>
                    <input type="text" name="courseID" value="${requestScope.courseID}" hidden>
                    <input type="text" name="go" value="addSection" hidden>
                    <div class="sectionNotice formPart">
                        <h5>Notice here(optional)</h5>
                        <input type="text" name="sectionNotice" value="${requestScope.sectionNotice}">
                        <p>At least 6 characters, start with an Uppercase</p>
                        <p style="color: red">${requestScope.sectionNoticeError}</p>
                    </div>
                </div>
                <div class="submitBtnBox">
                    <input type="submit" value="Submit" class="submitBtn">
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
