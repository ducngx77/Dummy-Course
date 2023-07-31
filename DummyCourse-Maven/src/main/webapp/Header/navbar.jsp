<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.user.User" %>
<nav class="nav" id="navbar">
    <div class="wrap-container" id="wrap-nav">
        <div id="brand-pic">
            <a href="home"><img src="<%=request.getContextPath()%>/resource/logo-removebg.png" class="brand-logo"></a>
        </div>
        <p id="brand-name"><a href="home">Dummy Course</a></p>

        <form id="search-bar" class="search-bar" action="#" method="get">
            <input type="text" class="input" id="search-input" placeholder="Search...">
            <button class="icon button" id="search-button"><i class="fas fa-search"></i></button>
        </form>
        <div id="search-bar-list">
        </div>


        <label class="icon search" for="search-checkbox-input" id="searchBtn">
            <i class="fa-solid fa-search"></i>
        </label>
        <input type="checkbox" id="search-checkbox-input" hidden>
        <nav class="popup-box" id="search-box">
            <label class="icon close" for="search-checkbox-input" id="closeSearchBtn">
                <i class="fa-solid fa-xmark"></i>
            </label>
            <form id="search-bar-2" action="#" method="get">
                <input type="text" class="input" id="search-input-2" placeholder="Search...">
                <button type="submit" class="icon button" id="search-button-2"><i class="fas fa-search"></i></button>
            </form>
        </nav>

        <label class="icon noti" for="noti-checkbox-input" id="notiPopupBtn">
            <i class="fa-solid fa-bell"></i>
        </label>
        <input type="checkbox" id="noti-checkbox-input" hidden>
        <nav class="popup-box" id="noti-list">
            <div class="noti-title">
                Notifications
            </div>
            <ul>
                <li class="option-content"><p> - Notification feature is still in the process of development. Please
                    wait for our 2.1 version.</p></li>
                <li class="option-content"><p> - Welcome to Dummy Course - The best online system where you can take a
                    quiz to test your knowledge! </p></li>
            </ul>
        </nav>

        <label class="icon user-prof" for="user-checkbox-input">
            <i class="fa-solid fa-user"></i>
        </label>
        <input type="checkbox" id="user-checkbox-input" hidden>
        <nav class="popup-box" id="user-list">
            <div class="user-title" style="font-size: large">
                ${sessionScope.role.userType}
                <c:if test="${sessionScope.role.userType == null}">
                    ${"Guest"}
                </c:if>
            </div>
            <div class="user-title">
                ${sessionScope.user.username}
            </div>
            <ul>

                <%if (session.getAttribute("user") != null) {%>
                <a href="profile">
                    <li class="option-content">My Profile</li>
                </a>
                <a href="feedback?go=sendSystemFeedback">
                    <li class="option-content">Feedback</li>
                </a>

                <a href="login?go=logout">
                    <li class="option-content">Logout</li>
                </a>
                <%} else {%>
                <a href="login">
                    <li class="option-content">Login</li>
                </a>
                <a href="register">
                    <li class="option-content">Register</li>
                </a>
                <a href="register-lecturer">
                    <li class="option-content">Register as a lecturer</li>
                </a>
                <%}%>
            </ul>
        </nav>
    </div>
</nav>