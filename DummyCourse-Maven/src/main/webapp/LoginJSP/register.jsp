<!DOCTYPE html>
<html lang="en">
<head>
    <title>Create new account</title>
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

<body>
<div class="background-image">
    <img src="<%=request.getContextPath()%>\resource\logo-removebg.png" alt="">
</div>

<div class="login-page">


    <div class="header-wrapper">
        <a href="login">Sign up to your student account</a>
    </div>

    <%
        String error = (String) request.getAttribute("error");
        String exist = (String) request.getAttribute("exist");
        String username = (String) request.getAttribute("username");
        String email = (String) request.getAttribute("email");
        String address = (String) request.getAttribute("address");
        String phoneNumber = (String) request.getAttribute("phoneNumber");
        String infoErr = (String) request.getAttribute("info-err");
        String emailErr = (String) request.getAttribute("email-err");
        String usernameErr = (String) request.getAttribute("username-err");
        String existMail = (String) request.getAttribute("exist-mail");
        String addressErr = (String) request.getAttribute("address-err");
        String phoneNumberErr = (String) request.getAttribute("phone-number-err");
        String gender = (String) request.getAttribute("gender");
    %>

    <div class="container mt-5 p-0 rounded">
        <h1 class="text-center text-dark rounded mt-5 sign-in-header">Register</h1>
        <form action="register" method="post">
            <input type="hidden" name="go" value="register">
            <div class="form-group">
                <label class="pf-label" for="username">Username</label>
                <input required class="form-control default-input" id="username" name="userName"
                       type="text" value="${username!=null?username:''}">
                <p class="login-error">
                    <%if (exist != null) {%>
                    <%=exist%>
                    <%}%>
                </p>
                <p class="login-error">
                    <%if (usernameErr != null) {%>
                    <%=usernameErr%>
                    <%}%>
                </p>
            </div>

            <div class="form-group">
                <label class="pf-label" for="email">Email</label>
                <input required class="form-control default-input" id="email" name="email"
                       type="text" value="${email!=null?email:''}">
                <p class="login-error">
                    <%if (emailErr != null) {%>
                    <%=emailErr%>
                    <%}%>
                </p>
                <p class="login-error">
                    <%if (existMail != null) {%>
                    <%=existMail%>
                    <%}%>
                </p>
            </div>

            <div class="form-group">
                <label class="pf-label" for="password">Password</label>
                <input required class="form-control default-input" id="password"
                       name="password" type="password" value="${password!=null?password:''}">
                <p class="login-error">
                    <%if (error != null) {%>
                    <%=error%>
                    <%}%>
                </p>
            </div>

            <div class="form-group">
                <label class="pf-label" for="confirmPassword">Confirm password</label>
                <input required class="form-control default-input" id="confirmPassword" name="confirmPassword"
                       type="password" value="${confirmPassword!=null?confirmPassword:''}">
            </div>

            <div class="form-group">
                <label class="pf-label" for="address">Address</label>
                <input required class="form-control default-input" id="address" name="address"
                       type="text" value="${address!=null?address:''}">
                <p class="login-error">
                    <%if (addressErr != null) {%>
                    <%=addressErr%>
                    <%}%>
                </p>
            </div>

            <div class="form-group">
                <label class="pf-label" for="phoneNumber">Phone number</label>
                <input required class="form-control default-input" id="phoneNumber" name="phoneNumber"
                       type="text" value="${phoneNumber!=null?phoneNumber:''}">
                <p class="login-error">
                    <%if (phoneNumberErr != null) {%>
                    <%=phoneNumberErr%>
                    <%}%>
                </p>
            </div>
            <div class="form-group">
                <div class="form-check">
                    <input class="form-check-input" value="male" type="radio" id="male" required name="gender"
                        <%if("male".equals(gender)){%>
                           checked
                        <%}%>
                    >
                    <label for="male" class="form-check-label">male</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" value="female" type="radio" id="female" name="gender"
                        <%if("female".equals(gender)){%>
                           checked
                        <%}%>
                    >
                    <label for="female" class="form-check-label">female</label>
                </div>
            </div>
            <div class="login-error">
                <%if (infoErr != null) {%>
                <%=infoErr%>
                <%}%>
            </div>
            <div class="form-group">
                <a href="login"><< Back to login</a>
            </div>
            <div>
                <p class="login-error">
                </p>
            </div>
            <div class="form-group text-center">
                <button class="btn btn-primary form-control mt-4 cf-button" style="margin-bottom: 3vh;" name="register"
                        type="submit">Register
                </button>
            </div>
        </form>
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