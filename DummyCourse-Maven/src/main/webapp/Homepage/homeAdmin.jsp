<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.DAOCourse" %>
<%@ page import="dao.DAOReviews" %>
<%@ page import="entity.*" %>
<%@ page import="entity.course.Course" %>
<%@ page import="entity.course.CourseType" %>
<%@ page import="entity.user.User" %>
<%@ page import="entity.user.Role" %>
<%@ page import="dao.DAOLectureDetails" %>
<%@ page import="dao.DAOUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Admin Homepage</title>
    <style>
        <%@include file="../CSS/home.css" %>
    </style>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="/Header/import.jsp"/>
</head>


<%
    /*
              request.setAttribute("allCourses", allCourses);
              request.setAttribute("courseTypes", courseTypes);
              request.setAttribute("lectures", lectures);
     */
    ArrayList<Course> allCourses = (ArrayList<Course>) request.getAttribute("allCourses");
    ArrayList<CourseType> courseTypes = (ArrayList<CourseType>) request.getAttribute("courseTypes");
    ArrayList<User> lectures = (ArrayList<User>) request.getAttribute("lectures");
    ArrayList<User> students = (ArrayList<User>) request.getAttribute("students");
    ArrayList<User> inactivatedLecturers = (ArrayList<User>) request.getAttribute("inactivatedLecturers");
    ArrayList<User> admins = (ArrayList<User>) request.getAttribute("admins");
    if (allCourses == null || lectures == null || courseTypes == null) {
        request.getSession().setAttribute("error", "Internal error in home jsp file");
        request.getSession().setAttribute("page", "login");
        response.sendRedirect("error.jsp");
    }
    DAOCourse daoCourse = new DAOCourse();
    DAOReviews daoReviews = new DAOReviews();
    request.setAttribute("daoCourse", new DAOCourse());
    request.setAttribute("daoLecturer", new DAOLectureDetails());
    request.setAttribute("daoUser", new DAOUser());
%>

<body>
<jsp:include page="/Header/header.jsp"/>
<div class="content-page">
    <div class="wrap-content">

        <div class="homeContainer">
            <div class="row">
                <div class="col-md-5 admin-box">
                    <div class="wrap-admin-box" id="inActLecBox">
                        <h5>Not accepted lecturer forms</h5>
                        <div class="row adminCourseRow">
                            <div class="col-md-6">
                                <h6>Lecturer name</h6>
                            </div>
                            <div class="col-md-6">
                                <h6>Action</h6>
                            </div>

                        </div>
                        <c:forEach var="lecturer" items="${requestScope.inactivatedLecturers}">
                            <div class="row adminCourseRow">
                                <div class="col-md-6">
                                    <p>${lecturer.username}</p>
                                </div>
                                <div class="col-md-4">
                                        <%--                                    <a href="lecturer-info?go=activate&userActId=${lecturer.userID}">View details</a>--%>
                                    <a href="lecturer-info?go=view-details&lecturer-user-id=${lecturer.userID}">View
                                        details</a>
                                </div>
                            </div>
                        </c:forEach>
                        <c:if test="${empty requestScope.inactivatedLecturers}">
                            <p>(There's no inactivated lecturer)</p>
                        </c:if>
                    </div>
                </div>
                <div class="col-md-3 admin-box">
                    <div class="wrap-admin-box" id="category-box">
                        <form action="courseType" method="get">
                            <input type="checkbox" id="cate-check-input" hidden>
                            <div class="row">
                                <h5 class="col-md-8">Category:</h5>
                                <div class="col-md-4" id="cate-add-display">
                                    <label for="cate-check-input">
                                        <i class="fas fa-plus-circle"></i>&nbsp;Add
                                    </label>
                                </div>
                            </div>
                            <input class="col-md-8" id="cate-check-text" type="text" name="ctName" value="">
                            <input type="text" name="go" value="add" hidden>
                            <input class="col-md-3" id="cate-check-submit" type="submit" value="Add">
                        </form>
                        <p style="color: red">${requestScope.ctError}</p>
                        <div class="row adminCourseRow">
                            <h6 class="col-md-8">Name</h6>
                            <h6 class="col-md-3">Action</h6>
                        </div>
                        <c:forEach items="${requestScope.courseTypes}" var="type">
                            <div class="row adminCourseRow" id="form${type.courseTypeID}" style="display: none">
                                <form action="courseType" method="get">
                                    <input type="text" name="typeId" value="${type.courseTypeID}" hidden>
                                    <input type="text" name="go" value="update" hidden>
                                    <input type="text" class="col-md-8" value="${type.typeName}" name="newCtName">
                                    <input type="submit" value="Save" class="col-md-3">
                                </form>
                            </div>
                            <div class="row adminCourseRow" id="btn${type.courseTypeID}">
                                <p class="col-md-8">${type.typeName}</p>
                                <p class="col-md-3 cate-edit-btn" onclick="toggleCateForm('${type.courseTypeID}')">
                                    Edit</p>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="col-md-4 temp">
                </div>
            </div>
        </div>


        <div class="homeContainer" id="courseList">
            <div class="row wrap-courseCategory">
                <h5 class="col-md-10">All courses available in system</h5>
                <a href="admin?go=createCourse" class="col-md-2"><i class="fa-solid fa-circle-plus"></i>&nbsp Add course</a>
            </div>

            <%--            <div class="search-wrapper row">--%>
            <%--                <div class="search-bar-content ml-4 my-auto"--%>
            <%--                     id="search-bar-content">--%>
            <%--                    <div class="search-icon-content" id="search-icon-content">--%>
            <%--                        <i class="fa fa-search" aria-hidden="true"></i>--%>
            <%--                    </div>--%>
            <%--                    <div class="search-input-content">--%>
            <%--&lt;%&ndash;                        <input type="text" placeholder="Search course/lecturer name" id="search-course">&ndash;%&gt;--%>
            <%--                    </div>--%>
            <%--                </div>--%>

            <%--                <div class="col-md-2 ml-auto mr-5 justify-content-center align-content-center">--%>
            <%--                </div>--%>
            <%--            </div>--%>

            <div class="filer-content row">
                <div class="col-md-9" id="cate-filter">
                    <h6>Category</h6>
                    <div class="cate-option-choice">
                        <input type="radio" checked name="type" id="type-all"
                               value="all" hidden>
                        <label class="cate-option" for="type-all">All courses</label>
                    </div>
                    <c:forEach items="${requestScope.courseTypes}" var="type">
                        <div class="cate-option-choice">
                            <input type="radio" name="type" id="type-${type.courseTypeID}"
                                   value="${type.courseTypeID}" hidden>
                            <label class="cate-option" for="type-${type.courseTypeID}">${type.typeName}</label>
                        </div>
                    </c:forEach>
                </div>
                <div class="col-md-3" id="search-filter">
                    <h6>Search by course/lecturer:</h6>
                    <input type="text" placeholder="Search..." id="search-course">
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
                                    <p class="item-content" style="color: var(--mainColor)"><i
                                            class="fa-solid fa-star"></i><a>Rating:
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

        <div class="homeContainer" id="listLecturer">
            <div class="row wrap-courseCategory">
                <h5 class="col-md-10">ALl lecturers:</h5>
                <a class="col-md-2"><i class="fa-solid fa-circle-plus"></i>&nbsp Manage request</a>
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
                                    <a href="lecturer-info?go=viewLecProf&lecturerID=${daoLecturer.getLectureByUserID(lecture.userID).lectureID}" style="color: var(--mainColor)">View Profile --></a>
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


        <div class="title">
            <h4>All category</h4>
            <a href="#">Add new category
                <label>
                    <i class="fas fa-plus-circle"></i>
                </label>
            </a>
        </div>
        <div class="homeContainer courseTypeList">
            <div class="course type">
                <div class="row">
                    <div class="col-md-2"><h5>Category ID</h5></div>
                    I
                    <div class="col-md-4"><h5>Name</h5></div>
                    I
                    <div class="col-md-4"><h5>Option</h5></div>
                    I
                </div>
                <c:forEach var="category" items="${requestScope.courseTypes}">
                    <div class="row">
                        <div class="col-md-2">${category.courseTypeID}</div>
                        I
                        <div class="col-md-4">${category.typeName}</div>
                        I
                        <div class="col-md-4">Change name</div>
                        I
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/Script/add-home-content.js"></script>
<script src="${pageContext.request.contextPath}/Script/searchbar.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    function toggleCateForm(cId) {
        var form = document.getElementById("form" + cId);
        var btn = document.getElementById("btn" + cId);
        if (form.style.display === "none") {
            form.style.display = "block";
        } else {
            form.style.display = "none";
        }
        if (btn.style.display === "none") {
            btn.style.display = "block";
        } else {
            btn.style.display = "none";
        }
    }
</script>
</body>
</html>