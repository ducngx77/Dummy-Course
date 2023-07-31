<%--
  Created by IntelliJ IDEA.
  User: pthanh
  Date: 6/18/2023
  Time: 9:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.course.Course" %>
<%@ page import="entity.course.CourseSection" %>
<%@ page import="entity.quiz.Quiz" %>
<%@ page import="java.util.Objects" %>
<html>
<head>
    <title>Add Question</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        <%@include file="/CSS/quiz.css"%>
    </style>
    <style>
        <%@include file="/CSS/home.css"%>
    </style>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@include file="/Header/import.jsp" %>
</head>
<%
    Course course = (Course) request.getAttribute("course");
    CourseSection section = (CourseSection) request.getAttribute("section");
    Quiz quiz = (Quiz) request.getAttribute("quiz");
    String error = (String) request.getAttribute("error");
    String question = (String) request.getAttribute("question");
    String explanation = (String) request.getAttribute("explanation");
    Part filePart = (Part) request.getAttribute("filePart");
    String[] choices = (String[]) request.getAttribute("choices");
    String[] weight = (String[]) request.getAttribute("weight");
%>
<body>
<%@include file="/Header/header.jsp" %>
<div class="content-page">
    <div class="wrap-content">
        <div class="navigation-box" id='breadcrumb'>
            <ul>
                <li><a href='home'>Home</a></li>
                <li><a href="course-details?course-id=${requestScope.courseID}">${requestScope.courseName}</a></li>
                <li>&nbsp;&nbsp;Create quiz for ${requestScope.courseName}</li>
            </ul>
        </div>
        <div class="homeContainer quizTitle">
            <h3>Create Question for <%=course.getCourseName()%>
            </h3>
            <h5>Section: <%=section.getSectionName()%>
            </h5>
            <h5>Quiz name: <%=quiz.getQuizName()%>
            </h5>
        </div>
        <div class="homeContainer">
            <form action="addquestion" method="post" enctype="multipart/form-data">
                <input type="text" name="go" value="addQuestion" style="display: none">
                <input type="text" name="quizID" value="<%=quiz.getQuizID()%>" style="display: none">
                <input type="text" name="courseID" value="<%=course.getCourseID()%>" style="display: none">
                <input type="text" name="sectionID" value="<%=section.getSectionID()%>" style="display: none">
                <table id="addQtbl">
                    <tr>
                        <td><label for="question">Question:</label></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="question" value="<%=(question==null?"":question)%>"></td>
                    </tr>
                    <tr>
                        <td><p>At least 6 character required</p></td>
                    </tr>
                    <tr>
                        <td><label for="explanation">Explanation</label></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="explanation" value="<%=(explanation==null?"":explanation)%>"></td>
                    </tr>
                    <tr>
                        <td><p>At least 6 character required</p></td>
                    </tr>
                    <tr>
                        <td><label for="picture"></label></td>
                    </tr>
                    <tr>
                        <td><input name="picture" type="file" value="<%= filePart==null?"":filePart %>"/></td>
                    </tr>
                    <%if (choices == null) {%>
                    <tr>
                        <td><label for="choice">Choice</label></td><td><label for="weight">Weight</label></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="choice"></td><td><input type="text" name="weight"></td>
                    </tr>
                    <tr>
                        <td><p>At least 6 character required</p></td>
                    </tr>
                            <%} else {%>
                            <%for(int i = 0; i< choices.length;i++){%>
                    <tr>
                        <td><label for="choice">Choice</label></td><td><label for="weight">Weight</label></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="choice" value="<%=choices[i]%>"></td><td><input type="text" name="weight" value="<%=weight[i]%>"></td>
                    </tr>
                    <tr>
                        <td><p>At least 6 character required</p></td>
                    </tr>
                        <%}%>
                    <%}%>
                        <tbody id="choicesContainer"></tbody>
                        <td>
                            <input type="button" value="Add Choice" onclick="addChoice()">
                        </td>
                    <tr>
                        <td><p style="color: red"><%=(error == null ? "" : error)%>
                        </p></td>
                    </tr>
                    <tr class="submitBtnBox">
                        <td><input type="submit" class="submitBtn" name="submit" value="Submit"></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<script>
    function addChoice() {
        const choicesContainer = document.getElementById("choicesContainer");

        const newRow = document.createElement("tr");
        newRow.innerHTML = `
            <td><label for="choice">Choice</label></td><td><label for="weight">Weight</label></td>
        `;

        choicesContainer.appendChild(newRow);

        const inputRow = document.createElement("tr");
        inputRow.innerHTML = `
            <td><input type="text" name="choice"></td><td><input type="text" name="weight"></td>
        `;

        choicesContainer.appendChild(inputRow);

        const requiredRow = document.createElement("tr");
        requiredRow.innerHTML = `
            <td>
                <p>At least 6 characters required</p>
            </td>
        `;

        choicesContainer.appendChild(requiredRow);

        // const checkboxRow = document.createElement("tr");
        // checkboxRow.innerHTML = `
        //     <td>
        //         <label for="weight">Weight</label>
        //         <input type="text" name="weight">
        //     </td>
        // `;
        // choicesContainer.appendChild(checkboxRow);
    }

    // function updateCorrectChoice(checkbox) {
    //     const correctInput = checkbox.parentElement.querySelector('input[name="correct"]');
    //     if (checkbox.checked) {
    //         correctInput.value = "1";
    //     } else {
    //         correctInput.value = "0";
    //     }
    // }
</script>
</body>
</html>
