<%@page import="jakarta.servlet.http.HttpServletRequest" %>
<%@page import="security.utils.Utility" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Reset Password</title>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/index.css">
    <link crossorigin="anonymous" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
          integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" rel="stylesheet"/>
    <link href="<%=request.getContextPath()%>/CSS/login.css" rel="stylesheet">
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
            top: 0%;
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
    String link = (String) request.getAttribute("link");
    String OTP = (String) request.getAttribute("OTP");
%>
<body>
<div class="background-image">
    <img src="<%=request.getContextPath()%>\resource\logo-removebg.png" alt="">
</div>
<div class="login-page">

    <div class="header-wrapper">
        <a href="login">Reset Your password</a>
    </div>

    <div class="container mt-5 p-0 rounded">
        <h1 class="text-center text-dark rounded mt-5 sign-in-header">Change Password</h1>
        <form class="contact_form" action="login" method="post">
            <input type="hidden" name="go" value="forgot-password">
            <div class="form-group">
                <label class="pf-label" for="email">Email
                </label>
                <input class="form-control default-input email-name" name="email"
                       type="text">
                <%if (error != null) { %>
                <span class="email-error" style="color:red"><%=error%></span>
                <%}%>
                <% if (link != null) { %>
                <p style="color:red"> Don't receive Email, click <a id="btn1" href="#">here</a></p>
                <input type="hidden" name="email" id="email" value="<%=link%>">
                <%}%>
            </div>

            <div class="form-group text-center">
                <button class="btn btn-primary form-control mt-4 cp-button" name="submit" type="submit">
                    Sent
                </button>
            </div>
            <input type="hidden" name="submit" id="btn">
            <div class="form-group text-center">
                <a class="btn btn-primary form-control mt-4 cp-button" href="login"
                   style="color:white; font-weight: bold">Back to login</a>
            </div>
        </form>
        <div class="new-register-wrapper">
            <p class="text-center pt-1">New user? <a href="login?go=register">Register</a></p>
        </div>

    </div>
</div>
<script src="https://smtpjs.com/v3/smtp.js"></script>
<script>
    const btn = document.getElementById("btn1");

    function sendMsg(e) {
        e.preventDefault();
        const email = document.getElementById("email");
        console.log(email)
        Email.send({
            SecureToken: "606b2941-f90d-4c7f-9aa1-8f637e7e9cfc",
            To: email.value,
            From: 'thanhpche170611@fpt.edu.vn',
            Subject: 'Change your password',
            Body: 'Your otp is: '
        }).then(
            message => alert("Email sent!")
        );

    }

    btn.addEventListener("click", sendMsg);

</script>
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