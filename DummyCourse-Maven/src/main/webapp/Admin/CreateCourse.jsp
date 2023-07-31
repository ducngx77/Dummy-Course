<%@ page import="java.util.List" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%
    ResultSet listCourseType = (ResultSet) request.getAttribute("listCourseType");
    ResultSet listLecturer = (ResultSet) request.getAttribute("listLecturer");
    String msgCourseNameError = (String) request.getAttribute("msgCourseNameError");
    String msgCourseDescriptionError = (String) request.getAttribute("msgCourseDescriptionError");
%>
<head>
    <meta charset="UTF-8"/>

    <title>Create course page</title>
    <jsp:include page="/Header/import.jsp"/>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        <%@include file="../CSS/home.css"%>
        body {
            padding-left: 100px;
            padding-right: 100px;
            font-family: NTR;
            font-style: Regular;
            line-height: 42px;
            line-height: 100%;
            align-items: Left Vertical;
            align-items: Top;
        }

        label {
            font-family: NTR;
            font-style: Regular;
            font-size: 20px;
            line-height: 42px;
            line-height: 100%;
            align: Left Vertical;
            align: Top;
        }

        form {
            margin-top: 50px;
            border: solid 4px #fffdfd;
            box-shadow: 0px 0px 1px 1px rgba(0, 0, 0, 0.25);
            border-radius: 2px;
            width: inherit;
            height: 100%;
            padding: 50px;
            padding-top: 10px;
        }

        table {
            width: 100%;
            height: 100%;
        }

        .search-bar {
            width: 280px;
        }

        .banner {
            display: flex;
            flex-wrap: nowrap;
        }

        #breadcrumb ul {
            list-style: none;
            display: inline-table;
            padding-inline-start: 1px;
            font-size: 12px;
            margin-block-start: 6px;
            margin-block-end: 6px;
        }

        #breadcrumb li {
            display: inline;
        }

        #breadcrumb a {
            float: left;
            background: var(--borderColor);
            padding: 3px 10px 3px 20px;
            position: relative;
            margin: 0 5px 0 0;
            text-decoration: none;
            color: #555;
        }

        #breadcrumb a:after {
            content: "";
            border-top: 12px solid transparent;
            border-bottom: 12px solid transparent;
            border-left: 12px solid var(--borderColor);
            position: absolute;
            right: -12px;
            top: 0;
            z-index: 1;
        }

        #breadcrumb a:before {
            content: "";
            border-top: 12px solid transparent;
            border-bottom: 12px solid transparent;
            border-left: 12px solid var(--backgroundColor);
            position: absolute;
            left: 0;
            top: 0;
            transition: all linear 0.25s;
        }

        #breadcrumb ul li:first-child a:before {
            display: none;
            transition: all linear 0.25s;
        }

        #breadcrumb ul:last-child li {
            padding-right: 5px;
            transition: all linear 0.25s;
        }

        #breadcrumb ul li a:hover {
            background: var(--subColor);
            color: var(--backgroundColor);
            transition: all linear 0.25s;
        }

        #breadcrumb ul li a:hover:after {
            border-left-color: var(--subColor);
            color: var(--backgroundColor);
            transition: all linear 0.25s;
        }

        #breadcrumb li:last-child {
            display: inline-block !important;
            margin-top: 3px !important;
            transition: all linear 0.25s;
        }

        .banner p {
            margin-top: 30px;
            margin-left: 20px;
            font-family: NTR;
            font-size: 30px;
            font-weight: 500;
            line-height: 85px;
            letter-spacing: 0em;
            text-align: left;
        }

        .banner img {
            width: 100px;
            height: 150px;
        }

        input[type="checkbox"] {
            background-color: #023047;
        }

        input[type="checkbox"]:checked:after {
            content: "\2713";
            color: white;
        }

        table {
        }    </style>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="/Header/import.jsp"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body>
<jsp:include page="/Header/header.jsp"/>
<div class="content-page">
    <div class="wrap-content">
        <div class="navigation-box" id='breadcrumb'>
            <ul>
                <li><a href='home'>Home</a></li>
                <li>&nbsp;&nbsp;Create new course</li>
            </ul>
        </div>
        <form action="admin" method="post">
            <input type="hidden" name="go" value="createCourse">
            <div class="banner">
                <img
                        src="<%=request.getContextPath()%>/resource/Vector.png"
                        alt="icon"
                />
                <p>Create a course</p>
            </div>
            <p></p>
            <table>
                <tr>
                    <td><label for="coursename">Course name</label></td>
                </tr>
                <tr>
                    <td>
                        <input
                                type="text"
                                name="coursename"
                                style="
                width: 356px;
                border: solid 4px #fffdfd;
                box-shadow: 0px 0px 1px 1px rgba(0, 0, 0, 0.25);
                border-radius: 2px;
              "
                        />
                    </td>
                </tr>
                <tr>
                    <td>
                        <p>Every word must start with uppercase letter, at least 6 character required</p>
                        <p style="color: red"><%=msgCourseNameError == null ? "" : msgCourseNameError%>
                        </p>
                    </td>
                </tr>
                <tr>
                    <td><label for="coursetype">Choose course type</label></td>
                </tr>
                <tr>
                    <td>
                        <select
                                name="coursetype"
                                id="coursetype"
                                style="
                width: 300px;
                border: solid 4px #fffdfd;
                box-shadow: 0px 0px 1px 1px rgba(0, 0, 0, 0.25);
                border-radius: 2px;
              "
                        >
                            <% while (listCourseType.next()) {%>
                            <option value="<%=listCourseType.getInt(1)%>"><%=listCourseType.getString(2)%>
                            </option>
                            <%}%>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p></p>
                    </td>
                </tr>
                <tr>
                    <td><label for="lecturer">Choose lecturer</label></td>
                </tr>
                <tr>
                    <td>
                        <select
                                name="lecturer"
                                id="lecturer"
                                style="
                width: 200px;
                border: solid 4px #fffdfd;
                box-shadow: 0px 0px 1px 1px rgba(0, 0, 0, 0.25);
                border-radius: 2px;
              "
                        >
                            <% while (listLecturer.next()) {%>
                            <option value="<%=listLecturer.getInt(1)%>"><%=listLecturer.getString(2)%>
                            </option>
                            <%}%>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p></p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="coursedescription">Course description</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input
                                type="text"
                                name="coursedescription"
                                id="coursedescription"
                                style="
                width: 924px;
                height: 60px;
                border: solid 4px #fffdfd;
                box-shadow: 0px 0px 1px 1px rgba(0, 0, 0, 0.25);
                border-radius: 2px;
              "
                        />
                    </td>
                </tr>
                <tr>
                    <td>
                        <p>At least 10 characters and no more than 100 characters</p>
                        <p style="color: red"><%=msgCourseDescriptionError == null ? "" : msgCourseDescriptionError%>
                        </p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input
                                type="checkbox"
                                name="status"
                                id="status"
                                value="1"
                                checked
                                style="width: 20px; height: 15px"
                        />
                        <label for="status">Active this course now</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p></p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p></p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input
                                type="submit"
                                name="submit"
                                id="submit"
                                value="Add new course"
                                style="
                font-family: NTR;
                font-size: 20px;
                font-weight: 400;
                line-height: 42px;
                letter-spacing: 0em;
                text-align: left;
                color: #fd663f;
                align-items: center;
                margin-left: 50%;
                height: 57px;
                width: 150px;
                border: solid 4px #fffdfd;
                box-shadow: 0px 0px 1px 1px rgba(0, 0, 0, 0.25);
                border-radius: 2px;
              "
                        />
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

</body>
</html>
