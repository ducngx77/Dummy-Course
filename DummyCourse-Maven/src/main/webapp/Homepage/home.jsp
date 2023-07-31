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

<%--<!doctype html>--%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../CSS/home.css"/>
    <link rel="stylesheet" type="text/css" href="../CSS/common.css"/>
</head>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Home</title>
    <style>
        <%@include file="../CSS/home.css" %>
        <%@include file="../CSS/quiz.css"%>
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/search-bar.css">
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="/Header/import.jsp"/>
</head>

<%
    request.setAttribute("daoCourse", new DAOCourse());
    request.setAttribute("daoLecturer", new DAOLectureDetails());
     request.setAttribute("daoUser", new DAOUser());
%>

<body>
<jsp:include page="/Header/header.jsp"/>
<div class="content-page">
    <div class="wrap-content">
        <div class="banner">
            <img src="<%=request.getContextPath()%>/resource/bannerimg.png" alt="" class="banner-img"/>
        </div>
        <c:if test="${not empty sessionScope.message}">
            <div class="homeContainer" id="newFeature">
                <h2>News</h2>
                <p id="homeMessage">
                        ${sessionScope.message}
                </p>
            </div>
        </c:if>
        <%--        list top rated courses--%>
        <div class="homeContainer" id="topRate">

            <h5>Top Rating Course:</h5>

            <div class="separator"></div>
            <div class="row" id="top-course-list">
                <c:forEach var="course" items="${requestScope.topRatedCourses}">
                    <div class="col-lg-3 col-md-4 col-sm-6 item mb-3">
                        <div class="wrap-course-item">
                            <div class="courseTitle">
                                <h5>
                                        ${course.courseName}
                                </h5>
                            </div>
                            <div class="courseSum">
                                <p class="item-content description">
                                        ${course.courseDescription}
                                </p>
                                <p class="item-content"><i class="fa-solid fa-chalkboard-user"></i>
                                        ${daoCourse.getLectureName(course.courseID).username}
                                </p>
                                <p class="item-content" style="color: var(--mainColor)"><i class="fa-solid fa-star"></i><a href="course-reviews?course-id=${course.courseID}">
                                    Rating:
                                    <fmt:formatNumber
                                            value="${daoCourse.getAverageRatingByCourseID(course.courseID)}"
                                            pattern="#.##"/>
                                </a>
                                </p>
                            </div>
                            <a href="course-details?course-id=${course.courseID}"
                               class="courseLink">
                                Go to course -->
                            </a>

                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="row my-4">
                <div class="col-md-1 mx-auto">
                    <button class="drop-button" onclick="iterateTopCourse()">
                        <svg enable-background="new 0 0 32 32" height="32px" id="Слой_1" version="1.1"
                             viewBox="0 0 32 32"
                             width="32px" xml:space="preserve" xmlns="http://www.w3.org/2000/svg">
                            <style>
                                .drop-button path {
                                    fill: var(--mainColor);
                                }
                            </style>
                            <g id="Arrow_Drop_Down_Circle">
                                <path clip-rule="evenodd"
                                      d="M16,0C7.164,0,0,7.164,0,16c0,8.837,7.164,16,16,16c8.837,0,16-7.163,16-16C32,7.164,24.837,0,16,0z M16,30.031C8.28,30.031,2,23.72,2,16C2,8.28,8.28,2,16,2c7.72,0,14,6.28,14,14C30,23.72,23.72,30.031,16,30.031z"
                                      fill="var(--mainColor)" fill-rule="evenodd"></path>
                                <path clip-rule="evenodd"
                                      d="M23.374,13.08c-0.134-0.056-0.276-0.079-0.419-0.075H9.013c-0.887-0.024-1.38,1.07-0.742,1.702l6.999,6.899c0.386,0.382,1.043,0.382,1.429,0l6.991-6.892C24.207,14.221,24.034,13.346,23.374,13.08C23.24,13.025,23.74,13.228,23.374,13.08C23.24,13.025,23.74,13.228,23.374,13.08C23.24,13.025,23.74,13.228,23.374,13.08z M15.984,19.488l-4.559-4.494h9.118L15.984,19.488z"
                                      fill="var(--mainColor)" fill-rule="evenodd"></path>
                            </g>
                        </svg>
                    </button>
                </div>
            </div>
        </div>

        <%--        List all courses--%>
        <div class="homeContainer" id="courseList">
            <div class="wrap-courseCategory">
<%--                <h5>Choose courses to your fitting</h5>--%>
                <h5>Courses for every thing you like:</h5>
            </div>

            <div class="search-wrapper row">
                <div class="search-bar-content ml-4 my-auto"
                     id="search-bar-content">
                    <div class="search-icon-content" id="search-icon-content">
                        <i class="fa fa-search" aria-hidden="true"></i>
                    </div>
                    <div class="search-input-content">
                        <input type="text" placeholder="Search course/lecturer name" id="search-course">
                    </div>
                </div>
                <div class="col-md-2 ml-auto mr-5 justify-content-center align-content-center">
                    <div class="question-choice">
                        <input type="radio" checked name="type" id="type-all"
                               value="all">
                        <label class="course-type-label" for="type-all">All courses</label>
                    </div>
                </div>
            </div>

            <div class="course-type-content">
                <div class="row type-wrapper" id="type-wrapper">
                    <c:forEach items="${requestScope.courseTypes}" var="type">
                        <div class="question-choice">
                            <input type="radio" name="type" id="type-${type.courseTypeID}"
                                   value="${type.courseTypeID}">
                            <label class="course-type-label" for="type-${type.courseTypeID}">${type.typeName}</label>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="separator"></div>
            <div id="courses-content">
                <div class="row" id="course-list">
                    <c:forEach var="course" items="${requestScope.allCourses}">
                        <div class="col-lg-3 col-md-4 col-sm-6 item mb-3">
                            <div class="wrap-course-item">
                                <div class="courseTitle">
                                    <h5>
                                            ${course.courseName}
                                    </h5>
                                </div>
                                <div class="courseSum">
                                    <p class="item-content description">
                                            ${course.courseDescription}
                                    </p>
                                    <p class="item-content"><i class="fa-solid fa-chalkboard-user"></i>
                                            ${daoCourse.getLectureName(course.courseID).username}
                                    </p>
                                    <p class="item-content" style="color: var(--mainColor)"><i class="fa-solid fa-star"></i><a href="course-reviews?course-id=${course.courseID}">
                                        Rating:
                                        <fmt:formatNumber
                                                value="${daoCourse.getAverageRatingByCourseID(course.courseID)}"
                                                pattern="#.##"/>
                                    </a>
                                    </p>
                                </div>
                                <a href="course-details?course-id=${course.courseID}"
                                   class="courseLink">
                                    Go to course -->
                                </a>

                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="row my-4">
                <div class="col-md-1 mx-auto">
                    <button class="drop-button" onclick="addCourses()">
                        <svg enable-background="new 0 0 32 32" height="32px" id="Слой_1" version="1.1"
                             viewBox="0 0 32 32"
                             width="32px" xml:space="preserve" xmlns="http://www.w3.org/2000/svg">
                            <style>
                                .drop-button path {
                                    fill: var(--mainColor);
                                }
                            </style>
                            <g id="Arrow_Drop_Down_Circle">
                                <path clip-rule="evenodd"
                                      d="M16,0C7.164,0,0,7.164,0,16c0,8.837,7.164,16,16,16c8.837,0,16-7.163,16-16C32,7.164,24.837,0,16,0z M16,30.031C8.28,30.031,2,23.72,2,16C2,8.28,8.28,2,16,2c7.72,0,14,6.28,14,14C30,23.72,23.72,30.031,16,30.031z"
                                      fill="var(--mainColor)" fill-rule="evenodd"></path>
                                <path clip-rule="evenodd"
                                      d="M23.374,13.08c-0.134-0.056-0.276-0.079-0.419-0.075H9.013c-0.887-0.024-1.38,1.07-0.742,1.702l6.999,6.899c0.386,0.382,1.043,0.382,1.429,0l6.991-6.892C24.207,14.221,24.034,13.346,23.374,13.08C23.24,13.025,23.74,13.228,23.374,13.08C23.24,13.025,23.74,13.228,23.374,13.08C23.24,13.025,23.74,13.228,23.374,13.08z M15.984,19.488l-4.559-4.494h9.118L15.984,19.488z"
                                      fill="var(--mainColor)" fill-rule="evenodd"></path>
                            </g>
                        </svg>
                    </button>
                </div>
            </div>

        </div>

        <%--        List top rated lecturers--%>
        <div class="homeContainer" id="listLecturer">
            <div class="wrap-courseCategory">
                <h5>Meet our awesome lecturers:</h5>

            </div>
            <div class="separator"></div>
            <div class="row" id="lecturer-list">
                <c:forEach var="lecture" items="${lectures}">
                    <div class="col-lg-3 col-md-4 col-sm-6 item">
                        <div class="wrap-course-item">
                            <div class="courseTitle">
                                <h5>${lecture.username}</h5>
                            </div>
                            <div class="courseSum">

                                <p class="item-content">
                                    <i class="item-header">Experiences: </i>${daoLecturer.getLectureByUserID(lecture.userID).lectureDescription}
                                </p>

                            </div>
                            <div class="row">
                                <div class="col-md-5 mr-auto mt-1 ml-2">
                                    <a href="lecturer-info?go=viewLecProf&lecturerID=${daoLecturer.getLectureByUserID(lecture.userID).lectureID}" style="color: var(--mainColor)">View Profile--></a>
<%--                                    <a href="lecturer-info&lecturerID=${lecture.lecturerID}" style="color: var(--mainColor)">View Profile</a>--%>
                                </div>
                                <div class="col-md-6 ml-auto">
                                    <p><a style="color: var(--mainColor)">Lecturer rating: <fmt:formatNumber
                                            value="${daoLecturer.getAverageRating(daoLecturer.getLectureByUserID(lecture.userID).lectureID)}"
                                            pattern="#.##"/></a></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="row my-4">
                <div class="col-md-1 mx-auto">
                    <button class="drop-button" onclick="iterateLecturer()">
                        <svg enable-background="new 0 0 32 32" height="32px" id="Слой_1" version="1.1"
                             viewBox="0 0 32 32"
                             width="32px" xml:space="preserve" xmlns="http://www.w3.org/2000/svg">
                            <style>
                                .drop-button path {
                                    fill: var(--mainColor);
                                }
                            </style>
                            <g id="Arrow_Drop_Down_Circle">
                                <path clip-rule="evenodd"
                                      d="M16,0C7.164,0,0,7.164,0,16c0,8.837,7.164,16,16,16c8.837,0,16-7.163,16-16C32,7.164,24.837,0,16,0z M16,30.031C8.28,30.031,2,23.72,2,16C2,8.28,8.28,2,16,2c7.72,0,14,6.28,14,14C30,23.72,23.72,30.031,16,30.031z"
                                      fill="var(--mainColor)" fill-rule="evenodd"></path>
                                <path clip-rule="evenodd"
                                      d="M23.374,13.08c-0.134-0.056-0.276-0.079-0.419-0.075H9.013c-0.887-0.024-1.38,1.07-0.742,1.702l6.999,6.899c0.386,0.382,1.043,0.382,1.429,0l6.991-6.892C24.207,14.221,24.034,13.346,23.374,13.08C23.24,13.025,23.74,13.228,23.374,13.08C23.24,13.025,23.74,13.228,23.374,13.08C23.24,13.025,23.74,13.228,23.374,13.08z M15.984,19.488l-4.559-4.494h9.118L15.984,19.488z"
                                      fill="var(--mainColor)" fill-rule="evenodd"></path>
                            </g>
                        </svg>
                    </button>
                </div>
            </div>
        </div>

        <%--        List random feedbacks--%>
        <div class="homeContainer" id="feedback">
            <h5>What people say about our courses</h5>
            <div class="separator"></div>
            <div id="feedback-content">
                <c:forEach var="review" items="${requestScope.reviews}">
                    <div class="review-item">
                        <div class="courseTitle">
                            <div class="row">
                                <div class="col-md-3 mt-1 mr-auto">
                                    <h5>
                                            ${daoUser.getUsernameByID(review.userID)}
                                    </h5>
                                </div>
                                <div class="col-md-4 ml-auto">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <p><a style="color: var(--mainColor)">Rating: <fmt:formatNumber
                                                    value="${review.rating}"
                                                    pattern="#.##"/></a></p>
                                        </div>
                                        <div class="col-md-8">
                                            <p style="color: var(--mainColor)">Date: ${review.reviewDate}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <p class="item-content">
                            <i class="item-header">Commented: </i>
                                ${review.comment}
                        </p>
                    </div>
                </c:forEach>
            </div>

            <div class="row my-4">
                <div class="col-md-1 mx-auto">
                    <button class="drop-button" onclick="changeComment()">
                        <svg enable-background="new 0 0 32 32" height="32px" id="Слой_1" version="1.1"
                             viewBox="0 0 32 32"
                             width="32px" xml:space="preserve" xmlns="http://www.w3.org/2000/svg">
                            <style>
                                .drop-button path {
                                    fill: var(--mainColor);
                                }
                            </style>
                            <g id="Arrow_Drop_Down_Circle">
                                <path clip-rule="evenodd"
                                      d="M16,0C7.164,0,0,7.164,0,16c0,8.837,7.164,16,16,16c8.837,0,16-7.163,16-16C32,7.164,24.837,0,16,0z M16,30.031C8.28,30.031,2,23.72,2,16C2,8.28,8.28,2,16,2c7.72,0,14,6.28,14,14C30,23.72,23.72,30.031,16,30.031z"
                                      fill="var(--mainColor)" fill-rule="evenodd"></path>
                                <path clip-rule="evenodd"
                                      d="M23.374,13.08c-0.134-0.056-0.276-0.079-0.419-0.075H9.013c-0.887-0.024-1.38,1.07-0.742,1.702l6.999,6.899c0.386,0.382,1.043,0.382,1.429,0l6.991-6.892C24.207,14.221,24.034,13.346,23.374,13.08C23.24,13.025,23.74,13.228,23.374,13.08C23.24,13.025,23.74,13.228,23.374,13.08C23.24,13.025,23.74,13.228,23.374,13.08z M15.984,19.488l-4.559-4.494h9.118L15.984,19.488z"
                                      fill="var(--mainColor)" fill-rule="evenodd"></path>
                            </g>
                        </svg>
                    </button>
                </div>
            </div>

        </div>


    </div>
</div>

<script src="${pageContext.request.contextPath}/Script/add-home-content.js"></script>
<script src="${pageContext.request.contextPath}/Script/searchbar.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</body>
</html>
