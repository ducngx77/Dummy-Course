<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.DAOCourse" %>
<%@ page import="dao.DAOLectureDetails" %>
<%@ page import="dao.DAOReviews" %>
<%@ page import="entity.*" %>
<%@ page import="entity.course.CourseType" %>
<%@ page import="entity.course.Course" %>
<%@ page import="entity.user.Role" %>
<%@ page import="entity.user.User" %>
<%@ page import="dao.DAOUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

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
    <title>Document</title>
    <style>
        <%@include file="../CSS/home.css" %>
    </style>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="/Header/import.jsp"/>
</head>

<%
    ArrayList<Course> allCourses = (ArrayList<Course>) request.getAttribute("allCourses");
    ArrayList<CourseType> courseTypes = (ArrayList<CourseType>) request.getAttribute("courseTypes");
    if (allCourses == null || courseTypes == null) {
        request.getSession().setAttribute("error", "Internal error in home jsp file");
        request.getSession().setAttribute("page", "login");
        response.sendRedirect("error.jsp");
    }
    request.setAttribute("daoUser", new DAOUser());
    request.setAttribute("daoCourse", new DAOCourse());
    request.setAttribute("daoLecturer", new DAOLectureDetails());
    request.setAttribute("daoUser", new DAOUser());
//    request.setAttribute("dao");
    DAOCourse daoCourse = new DAOCourse();
    DAOLectureDetails daoLectureDetails = new DAOLectureDetails();
    boolean lectureStatus = daoLectureDetails.getLectureByUserID(((User) request.getSession().getAttribute("user")).getUserID()).isAccepted();
%>

<body>
<jsp:include page="/Header/header.jsp"/>

<div class="content-page">
    <div class="wrap-content">
        <%if (!lectureStatus) {%>
        <%if (daoLectureDetails.getLectureByUserID(((User) request.getSession().getAttribute("user")).getUserID()) == null) {%>
        <div class="homeContainer" id="lecturerNotice">
            <h3>Your lecturer account has been rejected.</h3>
            <p class="lecturerNT">You have been deemed not qualified because your resume does not meet the requirement.
                You can create a new account.</p>
        </div>
        <%} else {%>
        <div class="homeContainer" id="lecturerNotice">
            <h3>Your lecturer account is currently inactive.</h3>
            <p class="lecturerNT">This could be due to one of the following reasons:</p>
            <ul class="lecturerNote">
                <li><p> - The account has not been activated yet.</p></li>
                <li><p> - Your application form is still pending.</p></li>
            </ul>
            <p class="lecturerNT">Try:</p>
            <ul class="lecturerNote">
                <li><p> - Contact admin to re-activate the account.</p></li>
                <li><p> - Complete your application form. <a href="profile">Here</a></p></li>
            </ul>
        </div>
        <%}%>
        <%} else {%>
        <%--        <div class="banner">--%>
        <%--            <img src="<%=request.getContextPath()%>/resource/bannerimg.png" alt="" class="banner-img"/>--%>
        <%--        </div>--%>
        <div class="homeContainer" id="courseList">
            <h5>
                Have a good and full of energy teaching day &nbsp;<i class="fa-regular fa-face-laugh-squint"
                                                                     style="color: #34bfa3"></i>
            </h5>
            <h5>
                Course I'm teaching:
            </h5>
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
                                    <p class="item-content" style="color: var(--mainColor)"><i
                                            class="fa-solid fa-star"></i><a
                                            href="course-reviews?course-id=${course.courseID}">
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
        </div>

        <div class="homeContainer" id="feedback">
            <h5>Let see what others say about me:</h5>
            <div class="separator"></div>
            <div id="feedback-content">
                <c:forEach var="review" items="${requestScope.reviews}">
                    <div class="review-item">
                        <div class="courseTitle">
                            <div class="row">
                                <div class="col-md-3 mt-1 mr-auto">
                                    <h5>
                                            ${daoUser.getUsernameByID(review.userID)}
                                            <%--                                                    ${review.userID}--%>
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

        </div>
        <%}%>
    </div>
</div>

</body>
</html>


