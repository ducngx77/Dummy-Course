<%@ page import="entity.user.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Sign in to ShareFood</title>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/index.css">
    <link crossorigin="anonymous" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
          integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/login.css">
    <style>
        .background-image {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: -1;
            pointer-events: none; /* Disable pointer events on the background image */
        }

        .background-image img {
            position: absolute;
            top: 0;
            left: 50%;
            width: 30%;
            height: 30%;
            object-fit: cover;
            opacity: 0.5;
            transform: translate(-50%, -20%);
        }


    </style>
</head>

<%
    String error = (String) request.getAttribute("error");
    HttpSession loginSession = request.getSession();
    String username = "";
    if (loginSession != null) {
        if (loginSession.getAttribute("user") != null)
            username = ((User) loginSession.getAttribute("user")).getUsername();
    }
%>

<body>
<div class="login-page">
    <div class="background-image">
        <img src="<%=request.getContextPath()%>\resource\logo-removebg.png" alt="Logo">
    </div>

    <div class="header-wrapper">
        <a href="login">DummyCourse Login</a>
    </div>

    <div class="container mt-5 p-0 rounded">
        <h1 class="text-center text-dark rounded mt-5 sign-in-header">Sign in to your account</h1>
        <form action="login" method="post">
            <input type="hidden" name="go" value="login">
            <div class="form-group">
                <label class="pf-label" for="username">Username
                </label>
                <input class="form-control default-input" id="username" name="username"
                       type="text" value=<%=username%>>
                <% if (error != null) { %>
                <span class="login-error"><%=error%></span>
                <%}%>
            </div>
            <div class="form-group">
                <label class="pf-label" for="password">Password</label>
                <input class="form-control default-input" id="password" name="password" type="password">
                <% if (error != null) { %>
                <span class="login-error"><%=error%></span>
                <%}%>
            </div>
            <div class="form-group">
                <div class="row">
                    <div class="col-5 mr-auto">
                        <a href="forgot-password" style="float: left">Forgot password?</a>
                    </div>
                    <div class="col-5 ml-auto">
                        <a class="ml-auto" href="register-lecturer" style="float: left">Register as a lecturer</a>
                    </div>
                </div>
            </div>
            <div class="form-group text-center">
                <button class="btn btn-primary form-control mt-4 cf-button" name="login" type="submit">Sign in</button>
            </div>
        </form>
        <div class="new-register-wrapper">
            <p class="text-center pt-1">New user? <a href="register">Register</a></p>
        </div>

    </div>
</div>
<script crossorigin="anonymous"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script crossorigin="anonymous"
        integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
        src="https://cdn.jsdelivr.net/npm/popper.js@1.14.6/dist/umd/popper.min.js"></script>
<script crossorigin="anonymous"
        integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
        src="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/js/bootstrap.min.js"></script>

</body>
</html>