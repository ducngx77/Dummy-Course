<%--
  Created by IntelliJ IDEA.
  User: trinh
  Date: 7/11/2023
  Time: 11:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style>
        p {
            padding-left: 16px;
            font-weight: 500;
        }

        input {
            border: none;
        }

        input[type="radio"] {
            border: none;
        }

        textarea {
            border: none;
        }

        input[type="radio"] {
            width: 18px;
            height: 18px;
            cursor: pointer;
            border: none;
        }
    </style>
    <style>
        <%@include file="../CSS/course.css"%>
    </style>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="/Header/import.jsp"/>
    <%
        String successMessage = (String) request.getSession().getAttribute("successMessage");
        if (successMessage != null && !successMessage.isEmpty()) {
    %>
    <script>
        alert("<%= successMessage %>");
    </script>
    <%
            request.getSession().removeAttribute("successMessage");
        }
    %>
</head>
<body>
<jsp:include page="/Header/header.jsp"/>

<div class="content-page">
    <%--    <img src="${dataUrl}" alt="${dataUrl}"/>--%>
    <div class="container" style="background-color: #EAEAEA;border-radius: 5px;">

        <div class="row" style="margin:15px;padding:15px">
            <div class="col-6" style=" border-right: 5px solid #FE6036;">
                <div class="detail-user" style="padding-bottom: 30px">
                    <h3>${user.username}</h3>
                    <p>Email : ${user.email}</p>
                </div>
                <c:if test="${ empty lectureDetails}">
                    <div class="detail-course" style="padding-bottom: 30px">
                        <h3>Total registered courses : ${totalCourses}</h3>
                        <p>Total test : ${totalTest}</p>
                        <p>Test taken : ${testTaken}</p>
                        <h3>Average test score ${avgTestTaken}</h3>
                    </div>
                </c:if>
                <c:if test="${not empty lectureDetails}">
                    <div class="total-assigns" style="padding-bottom: 30px">
                        <h3>Total assigns course: ${totalCoursesByLecturerID} </h3>
                        <p>Total test posted : ${totalTestPosted} </p>
                        <p>Student average score : ${studentAvgScore}</p>
                    </div>
                    <div class="total-student" style="padding-bottom: 30px">
                        <h3>Total Student: ${totalStudent} </h3>
                        <p>Reviewes received : ${reviewReceived} </p>
                        <p>Average review score : ${avgScore} </p>
                    </div>

                    <div class="user-image">
                        <img src="${dataUrl}" alt="user profile" style="width: 500px"/>
                    </div>
                </c:if>
            </div>
            <form action="profile" method="POST" enctype="multipart/form-data">

                <div class="col-6" style="padding-left: 40px;">
                    <div class="user-phone-number">
                        <p>Phone number:</p>
                        <input type="text" name="user-phone-number" style="width:400px" value="${user.phoneNumber}"/>
                    </div>
                    <div class="user-address">
                        <p>Address:</p>
                        <input type="text" style="width:400px" name="user-address" value="${user.address}"/>
                    </div>
                    <div class="user-gender" style="display: flex;margin: 10px 20px;">
                        <input type="radio" id="user-male" name="gender" value="1" ${user.gender == 1 ? "checked" : ""}>
                        <label for="user-male" style="padding-right: 40px;">Male</label>
                        <input type="radio" id="user-female" name="gender"
                               value="2" ${user.gender == 2 ? "checked" : ""}>
                        <label for="user-female">Female</label>
                    </div>
                    <div class="user-bio">
                        <p>Bio</p>
                        <textarea style="width:400px;  height: 150px;" name="user-bio">${user.bio}</textarea>
                    </div>
                    <c:if test="${not empty lectureDetails}">
                        <div class="user-description">
                            <p>Description about your experties:</p>
                            <textarea style="width:400px;  height: 100px;"
                                      name="user-description">${lectureDetails.lectureDescription}</textarea>
                        </div>
                        <div class="user-profile" style="padding-top: 15px;">
                            <p>Profile picture (This should be your resumé picture)</p>
                            <input type="file" name="user-img">
                        </div>
                    </c:if>
                    <button type="submit"
                            style="background-color: #FE6036;color: white;border: none; padding: 5px 18px;margin-top: 15px;">
                        Save
                    </button>
                </div>
            </form>
        </div>
    </div>

</body>
</html>
