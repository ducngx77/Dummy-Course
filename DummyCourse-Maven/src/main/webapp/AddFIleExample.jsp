<%@ page import="entity.FileHandleTest" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="jakarta.xml.bind.DatatypeConverter" %>
<%@ page import="entity.quiz.Materials" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add file example</title>
    <style>
        .image-wrapper {
            display: flex;
            flex-direction: row;
            flex-wrap: wrap;
        }

        .image-wrapper img {
            width: 200px;
            height: 200px;
            object-fit: cover;
            margin: 10px;
        }

        .download-file-wrapper {
            display: flex;
            flex-direction: row;
            flex-wrap: wrap;
        }

        .download-file-wrapper a {
            margin: 10px;
        }
    </style>
</head>
<body>
<form action="handle-file-example" enctype="multipart/form-data" method="post">
    <input name="file" type="file"/>
    <input type="submit" value="Upload"/>

    <div class="download-file-wrapper">
        <%if (request.getAttribute("downloadFiles") != null) {%>
        <% for (Materials material : (List<Materials>) request.getAttribute("downloadFiles")) { %>
        <a href="handle-file-example?fileId=<%= material.getMaterialID() %>"><%= material.getMaterialName() %>
        </a>
        <% } %>
        <%} else {%>
        <p>No File to download</p>
        <%}%>
    </div>

    <div class="image-wrapper">
        <%if (request.getAttribute("images") != null) {%>
        <% for (Materials material : (List<Materials>) request.getAttribute("images")) { %>
        <img src="data:image/<%=material.getMaterialType()%>;base64,<%= DatatypeConverter.printBase64Binary(material.getMaterialFile()) %>"
             alt="<%=material.getMaterialName()%>">
        <% } %>
        <%} else {%>
        <p>No image currently</p>
        <%}%>
    </div>


</form>
</body>
</html>