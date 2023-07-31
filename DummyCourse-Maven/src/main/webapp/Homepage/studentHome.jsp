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

<html>
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
    request.setAttribute("daoCourse", new DAOCourse());
    request.setAttribute("daoUser", new DAOUser());
%>

<body>
<jsp:include page="/Header/header.jsp"/>
<div class="content-page">
    <div class="wrap-content">
        <c:set var="sm" value="${sessionScope.systemMessage}"></c:set>
        <c:if test="${not empty sm}">
            <div class="homeContainer" id="newFeature">
                <h2>News</h2>
                <p id="homeMessage">
                        ${sm.text}
                </p>
            </div>
        </c:if>

        <div class="homeContainer" id="listLecturer">
            <div class="wrap-courseCategory">
                <h5>Meet our awesome lecturers:</h5>
                <a href="#">View other...</a>
            </div>
            <div class="row courses">
                <c:forEach var="lecture" items="${lectures}">
                    <div class="col-lg-3 col-md-4 col-sm-6 item">
                        <div class="wrap-course-item">
                            <div class="courseTitle">
                                <h5>${lecture.username}</h5>
                            </div>
                            <div class="courseSum">
                                <p>Master degree at smt I don’t know./ A veri friendly lecture and sometimes roast you
                                    with
                                    everything she’ve got</p>
                                <p><a href="#">Rating: 5.00</a></p>
                            </div>
                            <a href="#">View Profile --></a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="homeContainer" id="category">
            <nav id="cate-nav">
                <h5>Category:</h5>
                <%for (CourseType courseType : courseTypes) {%>
                <form action="home" method="post">
                    <input type="hidden" name="go" value="relist-course">
                    <input type="hidden" name="course-type" value="<%=courseType.getTypeName()%>">
                    <input type="submit" value="<%=courseType.getTypeName()%>" class="cateInput">
                </form>
                <%}%>
            </nav>
        </div>

        <div class="homeContainer" id="courseList">
            <div class="wrap-courseCategory">
                <h5>Recommended for you</h5>
            </div>
            <div class="row courses">
                <%for (Course course : allCourses) {%>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-item">
                        <div class="courseTitle">
                            <h5><%=course.getCourseName()%>
                            </h5>
                        </div>
                        <div class="courseSum">
                            <p><%=course.getCourseDescription()%>
                            </p>
                            <p>Lecturer: <%=new DAOCourse().getLectureName(course.getCourseID()).getUsername()%>
                            </p>
                        </div>
                        <a href="course-details?course-id=<%=course.getCourseID()%>&student-id=<%=course.getCourseID()%>">
                            Go to course -->
                        </a>
                    </div>
                </div>
                <%}%>
            </div>

            <div id="wrap-pagination">
                <ul class="pagination">
                    <li><a href="#" class="prev">&laquo</a></li>
                    <li><a href="#" class="active">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li><a href="#">6</a></li>
                    <li><a href="#">7</a></li>
                    <li><a href="#">8</a></li>
                    <li><a href="#">9</a></li>
                    <li><a href="#" class="next">&raquo;</a></li>
                </ul>
                <br>
            </div>
        </div>

        <jsp:include page="/Header/footer.jsp"/>

        <script>
            document.getElementById('testss').addEventListener('click', function () {
                window.onload = function () {
                    var button = document.getElementById('testss');
                    if (button) {
                        button.scrollIntoView({behavior: 'smooth'});
                    }
                };
            });

            var paginationLinks = document.querySelectorAll('#wrap-pagination .pagination a');
            paginationLinks.forEach(function (link, index) {
                link.addEventListener('click', function (event) {
                    event.preventDefault();
                    paginationLinks.forEach(function (link) {
                        link.classList.remove('active');
                    });
                    var currentIndex = Array.from(paginationLinks).indexOf(this);
                    if (this.classList.contains('prev')) {
                        if (currentIndex > 0) {
                            paginationLinks[currentIndex].classList.remove('active');
                            paginationLinks[currentIndex - 1].classList.add('active');
                        }
                    } else if (this.classList.contains('next')) {
                        if (currentIndex < paginationLinks.length - 1) {
                            paginationLinks[currentIndex].classList.remove('active');
                            paginationLinks[currentIndex + 1].classList.add('active');
                        }
                    } else {
                        this.classList.add('active');
                    }
                });
            });
        </script>
    </div>

</div>
</body>
