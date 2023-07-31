<%@ page import="dao.DAOLectureDetails" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<input type="checkbox" id="side-btn-input" hidden>
<label class="iButton" for="side-btn-input" id="sideBtnIcon">
    <i class="fa-solid fa-chevron-right"></i>
</label>
<nav class="side" id="sidebar">
    <nav id="sidebar-course-content">
        <div class="wrap side-content-wrap" id="wrap-side-content">
            <div class="side-item" id="side-brand-logo">
                <div id="side-brand-pic">
                    <a href="home"><img src="<%=request.getContextPath()%>/resource/logo-removebg.png"
                                        class="brand-logo"></a>
                </div>
                <p id="side-brand-name"><a href="home">Dummy Course</a></p>
            </div>
            <div class="side-item" id="home">
                <i class="fa-solid fa-house"></i><h5>Home</h5>
                <ul>
                    <li class="option-content">
                        <a href="home?go=registered">
                            <p>Registered Courses</p>
                        </a>
                    </li>
                    <li class="option-content">
                        <a href="profile">
                            <p>My profile</p>
                        </a>
                    </li>
                </ul>
            </div>
            <%request.setAttribute("daoLecturerDetails", new DAOLectureDetails());%>
            <div class="side-item" id="myCourse">
                <i class="fa-solid fa-book"></i><h5>My Courses</h5>
                <ul>
                    <c:forEach var="course" items="${requestScope.yourCourses}">
                        <li class="option-content">
                            <a href="course-details?course-id=${course.courseID}">
                                <p>${course.courseName}
                                    - ${daoLecturerDetails.getUserByLecturerID(course.lecturerID).username}</p>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div id="sideFooter">
            <div class="footerContent" id="projName">
                <h8 class="footerTitle">SWP391 Project</h8>
            </div>
            <div class="footerContent" id="contact">
                <h8 class="footerTitle">Contact Us</h8>
                <p>Phone: +84 24 7300 1866</p>
                <p>Address: Education Zone, Hoa Lac Hi-tech Park, Km29, Thang Long Boulevard, Thach Hoa,
                    Thach That, Ha
                    Noi, Vietnam</p>
            </div>
            <div class="footerContent" id="group member">
                <h8 class="footerTitle">Group: Dummies Course</h8>
                <p>Nguyen Ngoc Hoang San</p>
                <p>Nguyen Xuan Duc</p>
                <p>Pham Cong Thanh</p>
                <p>Cao Viet Hung</p>
                <p>Pham Van Viet Anh</p>
            </div>
        </div>
    </nav>
</nav>
