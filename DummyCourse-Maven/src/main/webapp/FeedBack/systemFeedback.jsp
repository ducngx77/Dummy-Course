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
    String systemFeedbackStatus = request.getParameter("systemFeedbackStatus");
%>

<body>
<jsp:include page="/Header/header.jsp"/>
<div class="content-page">
    <div class="wrap-content">
        <div class="navigation-box" id='breadcrumb'>
            <ul>
                <li><a href='home'>Home</a></li>
                <li>&nbsp;&nbsp;FeedBack to DummyCourse</li>
            </ul>
        </div>
        <div class="homeContainer" id="feedback-back-home">
            <div class="row">
                <div class="col-md-2">&nbsp;</div>
                <div class="col-md-8">
                    <div class="feedbackContainer">
                        <div class="wrap-feedback">
                            <div class="row">
                                <div class="col-md-4 feedback-title">
                                    <img src="<%=request.getContextPath()%>/resource/logo-removebg.png"
                                         class="brand-logo">
                                    <p id="brand-name">Dummy Course</p>
                                </div>
                                <div class="col-md-8 feedback-title">
                                    <h3>Your Feedback</h3>
                                </div>
                            </div>
                            <div class="separator"></div>
                            <c:if test="${systemFeedbackStatus eq 'notYet'}">
                                <div class="row feedback-title">
                                    <p>We would like your feedback to improve our website. </p>&nbsp;
                                    <p>What is your opinion about our website?</p>
                                    <p>Whether you enjoy it or have something unsatisfied, let us know.</p>
                                </div>
                                <form action="feedback" method="post" class="feedback-title">
                                    <input type="text" name="userID" value="${sessionScope.user.userID}" hidden>
                                    <input type="text" name="go" value="sendSystemFeedback" hidden>
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
                                    <input type="text" class="feedback-text" name="comment" value="${requestScope.systemFeedBackComment}"
                                           placeholder="Tell us some of your feelings..." required>
                                    <p style="color: red">${requestScope.cmtError}</p>
                                    <div class="separator"></div>
                                    <input type="submit" name="submit" value="Submit" class=submitBtn>
                                </form>
                            </c:if>
                            <c:if test="${systemFeedbackStatus eq 'done'}">
                                <div class="row feedback-title">
                                    <p style="color: limegreen">Send feedback successfully! </p>&nbsp;
                                    <p>We greatly appreciate your contributions.</p>
                                    <p>We will continue to improve further to provide the best experience for everyone.</p>
                                </div>
                                <div class="separator"></div>
                                <div class="feedback-title">
                                    <a href="home"><button class="submitBtn">Home</button></a>
                                </div>
                            </c:if>

                        </div>
                    </div>
                </div>
                <div class="col-md-2">&nbsp;</div>
            </div>
        </div>

    </div>
</div>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const ratingLabels = document.querySelectorAll(".ratingLabel");
        const ratingInputs = document.querySelectorAll("input[type='radio'][name='rating']");

        ratingLabels.forEach((label, index) => {
            label.addEventListener("click", () => {
                ratingLabels.forEach((label) => {
                    label.classList.remove("checked");
                });
                ratingInputs[index].checked = true;
                label.classList.add("checked");
            });
        });
    });
</script>
</body>
</html>
