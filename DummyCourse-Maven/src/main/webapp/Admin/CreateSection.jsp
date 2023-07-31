<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String sectionNameError = (String)request.getAttribute("sectionNameError");
    String sectionNoticeError = (String)request.getAttribute("sectionNoticeError");
%>
<head>
    <title>Create section page</title>
    <style>
        body {
            padding-left: 100px;
            padding-right: 100px;
            font-family: NTR;
            line-height: 42px;
            line-height: 100%;
        }

        label {
            font-family: NTR;
            font-size: 20px;
            line-height: 42px;
            line-height: 100%;
            align: Left Vertical;
            align: Top;
        }
        .search-bar{
            width: 250px;
        }
        form {
            margin: 100px;
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

        .banner {
            display: flex;
            flex-wrap: nowrap;
        }
        hr{
            color: #fd663f;
            width: 300px;
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
        }
    </style>
    <jsp:include page="/Header/import.jsp"/>
</head>
<body>
<jsp:include page="/Header/header.jsp"/>
<div class="content-page">
    <div class="wrap-content" id="wrap-content">
        <form action="admin" method="post">
            <input type="hidden" name="go" value="createSection">
            <div class="banner">
                <img
                        src="<%=request.getContextPath()%>/resource/Vector.png"
                        alt="icon"
                />
                <p>Create a section</p>
            </div>

            <p></p>
            <table>
                <tr>
                    <td>
                        <label>Course ID:</label>
                        <input name="courseID" value="<%=request.getAttribute("courseID")==null?"":request.getAttribute("courseID")%>">
                    </td>
                </tr>
                <tr>
                    <td><label for="sectionName">Section name</label></td>
                </tr>
                <tr>
                    <td>
                        <input
                                type="text"
                                name="sectionName"
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
                        <p>Every word must start with uppercase letter, at least 6 characters required</p>

                        <p style="color: red"><%=sectionNameError==null?"":sectionNameError%></p>
                    </td>
                </tr>
<%--                <tr>--%>
<%--                    <td>--%>
<%--                        <label for="material">Add material here</label>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td>--%>
<%--                        <p></p>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td>--%>
<%--                        <p><input name="material" type="file"/></p>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td>--%>
<%--                        <p>All materials must be kept within a compressed file (.rar or .zip or .png or .jpg or .jpeg or--%>
<%--                            .docx or .xlsx or .pptx )</p>--%>
<%--                    </td>--%>
<%--                </tr>--%>
                <tr>
                    <td>
                        <label for="sectionNotice">Section notice</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input
                                type="text"
                                name="sectionNotice"
                                id="sectionNotice"
                                checked
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

                        <p style="color: red"><%=sectionNoticeError==null?"":sectionNoticeError%></p>
                    </td>
                </tr>
<%--                <tr>--%>
<%--                    <td>--%>
<%--                        <label for="duration">Duration (in minutes)</label>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td>--%>
<%--                        <input type="number" name="duration" min="1" required />--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td>--%>
<%--                        <input--%>
<%--                                type="checkbox"--%>
<%--                                name="status"--%>
<%--                                id="status"--%>
<%--                                value="1"--%>
<%--                                style="width: 20px; height: 15px"--%>
<%--                        />--%>
<%--                        <label for="status">Active this section now</label>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <tbody id="sectionContainer"></tbody>--%>
<%--                    <td>--%>
<%--                        <input type="button" value="More section" onclick="addSection()">--%>
<%--                    </td>--%>
<%--                </tr>--%>
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
                <hr/>
                <tr>
                    <td>
                        <input
                                type="submit"
                                name="submit"
                                id="submit"
                                value="Add sections"
                                style="
                font-family: NTR;
                font-size: 20px;
                font-weight: 400;
                line-height: 42px;
                letter-spacing: 0em;
                text-align: center;
                color: #fd663f;
                align-items: center;
                margin-left: 50%;
                height: 57px;
                width: 150px;
                border: solid 1px #fffdfd;
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
<%--<script>--%>
<%--    function addSection() {--%>
<%--        const sectionContainer = document.getElementById("sectionContainer");--%>

<%--        const newRowSectionName = document.createElement("tr");--%>
<%--        newRowSectionName.innerHTML = `--%>
<%--            <td>--%>
<%--                <label for="sectionName">Section Name</label>--%>
<%--            </td>--%>
<%--        `;--%>

<%--        sectionContainer.appendChild(newRowSectionName);--%>

<%--        const inputRowSectionName = document.createElement("tr");--%>
<%--        inputRowSectionName.innerHTML = `--%>
<%--            <td>--%>
<%--                <input type="text" name="sectionName">--%>
<%--            </td>--%>
<%--        `;--%>

<%--        sectionContainer.appendChild(inputRowSectionName);--%>

<%--        const requiredRowSectionName = document.createElement("tr");--%>
<%--        requiredRowSectionName.innerHTML = `--%>
<%--            <td>--%>
<%--                <p>Every word must start with uppercase letter, at least 6 characters required</p>--%>
<%--            </td>--%>
<%--        `;--%>

<%--        sectionContainer.appendChild(requiredRowSectionName);--%>

<%--        const newRowMaterial =  document.createElement("tr");--%>
<%--        newRowMaterial.innerHTML = `--%>
<%--            <td>--%>
<%--                 <label for="material">Add material here</label>--%>
<%--            </td>--%>
<%--        `;--%>
<%--        sectionContainer.appendChild(newRowMaterial);--%>

<%--        const inputRowMaterial = document.createElement("tr");--%>
<%--        inputRowMaterial.innerHTML = `--%>
<%--            <td>--%>
<%--                <p><input name="material" type="file"/></p>--%>
<%--            </td>--%>
<%--        `;--%>
<%--        sectionContainer.appendChild(inputRowMaterial);--%>

<%--        const requiredRowMaterial = document.createElement("tr");--%>
<%--        requiredRowMaterial.innerHTML = `--%>
<%--            <td>--%>
<%--                <p>All materials must be kept within a compressed file (.rar or .zip or .png or .jpg or .jpeg or .docx or .xlsx or .pptx )</p>--%>
<%--            </td>--%>
<%--        `;--%>

<%--        sectionContainer.appendChild(requiredRowMaterial);--%>

<%--        const newRowSectionNotice = document.createElement("tr");--%>
<%--        newRowSectionNotice.innerHTML = `--%>
<%--            <td>--%>
<%--                 <label for="material">Section notice</label>--%>
<%--            </td>--%>
<%--        `;--%>
<%--        sectionContainer.appendChild(newRowSectionNotice);--%>

<%--        const inputRowSectionNotice = document.createElement("tr");--%>
<%--        inputRowSectionNotice.innerHTML = `--%>
<%--            <td>--%>
<%--                <input type="text" name="sectionNotice">--%>
<%--            </td>--%>
<%--        `;--%>
<%--        sectionContainer.appendChild(inputRowSectionNotice);--%>

<%--        const requiredRowSectionNotice = document.createElement("tr");--%>
<%--        requiredRowSectionNotice.innerHTML = `--%>
<%--            <td>--%>
<%--                <p>At least 10 characters and no more than 100 characters</p>--%>
<%--            </td>--%>
<%--        `;--%>

<%--        sectionContainer.appendChild(requiredRowSectionNotice);--%>
<%--        document.createElement("tr");--%>
<%--        document.createElement("hr");--%>
<%--    }--%>
<%--</script>--%>
</body>
</html>
