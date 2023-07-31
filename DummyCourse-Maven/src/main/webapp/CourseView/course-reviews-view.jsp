<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.DAOCourse" %>
<%@ page import="entity.*" %>
<%@ page import="dao.DAOSystemReview" %>
<%@ page import="dao.DAOUser" %>
<%@ page import="entity.course.CourseType" %>
<%@ page import="entity.course.Course" %>
<%@ page import="entity.user.User" %>
<%@ page import="entity.user.SystemReview" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page import="java.util.Random" %>
<%@ page import="dao.DAOLectureDetails" %>
<%@ page import="entity.user.Reviews" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Course Reviews</title>
    <style>
        <%@include file="../CSS/home.css" %>
    </style>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="/Header/import.jsp"/>
</head>
<%
    request.setAttribute("daoCourse", new DAOCourse());
    request.setAttribute("daoLecturer", new DAOLectureDetails());
    request.setAttribute("daoUser", new DAOUser());
    DAOCourse daoC = new DAOCourse();
    int courseID = daoC.getCourseByID((Integer) request.getAttribute("course-id")).getCourseID();
%>
<body>
<jsp:include page="/Header/header.jsp"/>
    <div class="content-page">
        <div class="wrap-content">
            <div class="navigation-box" id='breadcrumb'>
                <ul>
                    <li><a href="home">Home</a></li>
                    <li><a href="course-details?course-id=<%=courseID%>">${courseName}></a></li>
                    <%--                <li><a href='/css/css_navbar.asp'>CSS Navbar</a></li>--%>
                    <li>&nbsp;&nbsp;Review for ${courseName}</li>
                </ul>
            </div>
            <div></div>
            <div><h1>Some reviews for: ${courseName}</h1></div>
            <div class="homeContainer" id="feedback">
                <form action="course-reviews" method="GET">
                    <label for="start-date">Start Date:</label>
                    <input type="date" id="start-date" name="start-date">

                    <label for="end-date">End Date:</label>
                    <input type="date" id="end-date" name="end-date">

                    <input type="hidden" name="course-id" value="<%=courseID%>">
                    <input type="submit" value="Filter">
                </form>
                <div class="separator"></div>
                <div id="feedback-content">
                    <c:forEach var="review" items="${reviews}">
                        <div class="review-item">
                            <div class="courseTitle">
                                <div class="row">
                                    <div class="col-md-3 mt-1 mr-auto">
                                        <h5>${daoUser.getUsernameByID(review.userID)}</h5>
                                    </div>
                                    <div class="col-md-4 ml-auto">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <p><a style="color: var(--mainColor)">Rating: ${review.getRating()}</a></p>
                                            </div>
                                            <div class="col-md-8">
                                                <p style="color: var(--mainColor)">Date: ${review.getReviewDate()}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <p class="item-content">
                                <i class="item-header">Commented: </i> ${review.getComment()}
                            </p>
                        </div>
                    </c:forEach>
                </div>
        </div>
    </div>
</body>
</html>
