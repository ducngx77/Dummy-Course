<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 6/20/2023
  Time: 6:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="entity.course.Course" %>
<%@ page import="entity.course.CourseSection" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Quiz</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        <%@include file="../CSS/quiz.css"%>
    </style>
    <style>
        <%@include file="../CSS/home.css"%>
    </style>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="/Header/import.jsp"/>
</head>

<%
    Course course = (Course) request.getAttribute("course");
    CourseSection section = (CourseSection) request.getAttribute("section");
    String error = (String) request.getAttribute("error");
    String testName = (String) request.getAttribute("testname");
    String duration = (String) request.getAttribute("duration");
    int durationInt = 0;
    if (duration != null) durationInt = Integer.parseInt(duration);
    String starttime = (String) request.getAttribute("starttime");
%>

<body>
<jsp:include page="/Header/header.jsp"/>
<div class="quiz-content-page">
    <div class="wrap-content">
        <div class="navigation-box" id='breadcrumb'>
            <ul>
                <li><a href='home'>Home</a></li>
                <li><a href="course-details?course-id=<%=course.getCourseID()%>"><%=course.getCourseName()%></a></li>
                <li>&nbsp;&nbsp;Create quiz for <%=course.getCourseName()%></li>
            </ul>
        </div>
        <div class="quizContainer" id="createQuizTitle">
            <div class="row">
                <div class="col-md-4" id="crt-icon">
                    <span>
                        <i class="fa-solid fa-clipboard-question"></i>
                    </span>
                </div>
                <div class="col-md-8" id="crt-courseName">
                    <h3>Create quiz for <%= course.getCourseName() %>
                    </h3>
                    <h5>Section: <%=section.getSectionName()%>
                    </h5>
                </div>
            </div>
        </div>
        <div class="quizContainer">
            <form action="AddQuestion-actions" method="post">
                <input type="hidden" name="go" value="createTest">
                <input type="hidden" name="courseID" value="<%=course.getCourseID()%>">
                <input type="hidden" name="sectionID" value="<%=section.getSectionID()%>">
                <div class="addQuizContainer quizInfo">
                    <h5>Quiz name:</h5>
                    <input type="text" name="testname" value="<%=(testName == null? "" : testName)%>" id="nameInput">
                    <p>Every word must start with uppercase letter, at least 6 character required</p>
                    <div class="row">
                        <div class="col-md-4">
                            <label for="duration">Choose quiz durarion: </label>
                            <div>
                                <input type="text" value="15" name="duration" id="duration">
                                <button type="button" class="iButton" onclick="decrease()">
                                    <i class="fa-solid fa-minus"></i>
                                </button>
                                <button type="button" class="iButton" onclick="increase()">
                                    <i class="fa-solid fa-plus"></i>
                                </button>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <label>Choose date:</label><br>
                            <input type="date" name="starttime">
                            <p>Can’t be less than current time + 30 minues</p>
                        </div>
                    </div>
                </div>
                <div class="submitBtnBox">
                    <input type="submit" value="Submit" class="submitBtn">
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    function decrease() {
        var quantity = document.getElementById("duration");
        if (quantity.value > 0) {
            quantity.value--;
        }
    }

    function increase() {
        var quantity = document.getElementById("duration");
        if (quantity.value < 120) {
            quantity.value++;
        }
    }

    const input = document.getElementById("duration");
    input.addEventListener("input", function () {
        this.value = this.value.replace(/[^0-9]/g, '');
    });

</script>
</body>
</html>

<form action="AddQuestion-actions" method="post">
    <input type="hidden" name="go" value="createTest">
    <input type="hidden" name="courseID" value="<%=course.getCourseID()%>">
    <input type="hidden" name="sectionID" value="<%=section.getSectionID()%>">
    Create test for course <%= course.getCourseName() %>
    <p></p>
    <%=section.getSectionName()%>
    <table>
        <tr>
            <td><label for="testname">Test name</label></td>
        </tr>
        <tr>
            <td><input type="text" name="testname" value="<%=(testName == null? "" : testName)%>"></td>
        </tr>
        <tr>
            <td>Every word must start with uppercase letter, at least 6 character required</td>
        </tr>
        <tr>
            <td><label for="duration">Choose duration</label></td>
            <td><label for="starttime">Choose Start time</label></td>
        </tr>
        <tr>
            <td>
                <select name="duration" id="duration">
                    <% for (int i = 15; i <= 120; i += 5) {%>
                    <option value="<%=i%>" <%= (i == durationInt ? "selected" : "") %>><%=i%>
                    </option>
                    <%}%>
                </select>
            </td>
            <td><input type="datetime-local" name="starttime" id="starttime" value="<%=starttime%>"></td>
        </tr>
        <tr>
            <td><p>15-120 minutes</p></td>
            <td><p>Can’t be less than current time + 30 minues</p>
            </td>
        </tr>
        <tr>
            <td><p style="color:red"><%=(error == null ? "" : error)%>
            </p></td>
        </tr>
        <tr>
            <td><input type="submit" name="submit" value="Add test"></td>
        </tr>
    </table>
</form>