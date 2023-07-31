<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page import="entity.user.LectureReview" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.course.Course" %>
<%@ page import="entity.user.LectureDetails" %>
<%@ page import="entity.user.User" %>
<%@ page import="dao.DAOCourse" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.DAOUser" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 7/26/2023
  Time: 12:22 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    ArrayList<LectureReview> reviews = (ArrayList<LectureReview>) request.getAttribute("LecturerReviews");
    ArrayList<Course> courses = (ArrayList<Course>) request.getAttribute("listCourse");
    User userLec = (User) request.getAttribute("userLec");
    DAOCourse daoCourse = new DAOCourse();
    DAOUser daoUser = new DAOUser();
    request.setAttribute("daoCourse", new DAOCourse());
    LectureDetails lecturer = (LectureDetails) request.getAttribute("lecturer");
%>
<head>
    <title><%=userLec.getUsername()%></title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        <%@include file="../CSS/home.css"%>
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
                <li>&nbsp;&nbsp;<%=userLec.getUsername()%></li>
            </ul>
        </div>
        <div class="homeContainer">
            <div class="lecturerProfContainer" id="lecProfHeader">
                <div class="row">
                    <div class="col-md-2"></div>
                    <div class="col-md-2">
                        <p>&nbsp;</p>
                        <img src="<%=request.getContextPath()%>/resource/bannerimg.png" alt="" class="lecProfPic"/>
                    </div>
                    <div class="col-md-8" id="lecProfDetails">
                        <p>&nbsp;</p>
                        <p>&nbsp;</p>
                        <h2><%=userLec.getUsername()%>
                        </h2>
                        <p><%=lecturer.getLectureDescription()%>
                        </p>
                    </div>
                </div>

            </div>
            <div class="lecturerProfContainer" id="lecProfCourse">
                <h3>Courses:</h3>
                <div class="row">
                    <%for (Course course : courses) {%>
                    <div class="col-lg-3 col-md-4 col-sm-6 item mb-3">
                        <div class="wrap-course-item">
                            <div class="courseTitle">
                                <h5><%=course.getCourseName()%>
                                </h5>
                            </div>
                            <div class="courseSum">
                                <p class="item-content description">
                                    <%=course.getCourseDescription()%>
                                </p>
                                <p class="item-content" style="color: var(--mainColor)"><i class="fa-solid fa-star"></i><a
                                        href="course-reviews?course-id=<%=course.getCourseID()%>">
                                    Rating:
                                    <fmt:formatNumber
                                            value="<%=(daoCourse.getAverageRatingByCourseID(course.getCourseID()))%>"
                                            pattern="#.##"/>
                                </a>
                                </p>
                            </div>
                            <a href="course-details?course-id=<%=course.getCourseID()%>"
                               class="courseLink">
                                Go to course -->
                            </a>

                        </div>
                    </div>
                    <%}%>
                </div>
                <%--                </c:forEach>--%>
            </div>
            <div class="lecturerProfContainer" id="lecProfReview">
                <div class="row">
                    <div class="col-md-8">

                        <h5>What others say:</h5>
                        <div id="feedback-content">
                            <%--                            <c:forEach var="review" items="${requestScope.reviews}">--%>
                            <%for (LectureReview review : reviews) {%>
                            <div class="review-item">
                                <div class="courseTitle">
                                    <div class="row">
                                        <div class="col-md-3 mt-1 mr-auto">
                                            <h5>
                                                <%=daoUser.getUsernameByID(review.getUserID())%>
                                            </h5>
                                        </div>
                                        <div class="col-md-4 ml-auto">
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <p><a style="color: var(--mainColor)">Rating: <fmt:formatNumber
                                                            value="<%=review.getRating()%>"
                                                            pattern="#.##"/></a></p>
                                                </div>
                                                <div class="col-md-8">
                                                    <p style="color: var(--mainColor)">
                                                        Date: <%=review.getReviewDate()%>
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <p class="item-content">
                                    <i class="item-header">Commented: </i>
                                    <%=review.getComment()%>
                                </p>
                            </div>
                            <%}%>
                            <%--                            </c:forEach>--%>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <%if (request.getSession().getAttribute("user") != null) {%>
                        <h5>Give your own feeling:</h5>
                        <form action="feedback" method="post" class="feedback-title">
                            <input type="text" name="userID" value="${sessionScope.user.userID}" hidden>
                            <input type="text" name="lecturerID" value="<%=lecturer.getLectureID()%>" hidden>
                            <input type="text" name="go" value="sendLecturerFeedback" hidden>
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
                            <div class="separator"></div>
                            <p>Leave your comment here:</p>
                            <input type="text" class="feedback-text" name="comment"
                                   value="${requestScope.lecturerFeedBackComment}"
                                   placeholder="What do you think about this lecturer..." required>
                            <p style="color: red">${requestScope.cmtError}</p>
                            <div class="separator"></div>
                            <input type="submit" name="submit" value="Submit" class=submitBtn>
                        </form>
                        <%}%>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
