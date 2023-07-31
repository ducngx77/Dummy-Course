<%--
  Created by IntelliJ IDEA.
  User: trinh
  Date: 7/11/2023
  Time: 9:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register for a course</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <style>
        <%@include file="../CSS/course.css"%>
    </style>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="/Header/import.jsp"/>
</head>
<body>
<jsp:include page="/Header/header.jsp"/>
<div class="content-page">

    <%--    <h1>${course.courseName}</h1>--%>
    <%--    <h1>${course.courseDescription}</h1>--%>
    <div class="hero" style="box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;padding:40px">
        <h1 style="font-size: 90px">Register course</h1>
        <div class="container" style="margin-top: 40px;text-align: center">
            <h1 style="padding-bottom: 20px;">
                ${course.courseName}
            </h1>
            <p class="desc">
                ${course.courseDescription}
            </p>
            <div class="cta-wrap" style="margin-top: 40px;">
                <a class="btn" href="./course-details?course-id=${course.courseID}&handleEnroll=true">
                    <span>Register</span>
                    <i class="icon fa-solid fa-chevron-right"></i>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
