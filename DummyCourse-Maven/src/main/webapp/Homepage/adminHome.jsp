<html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Admin Homepage</title>
    <style>
        <%@include file="../CSS/home.css" %>
    </style>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="/Header/import.jsp"/>
</head>
<body>
<jsp:include page="/Header/header.jsp"/>
<div class="content-page">
    <div class="wrap-content">
        <c:set var="sm" value="${sessionScope.systemMessage}"></c:set>
        <c:if test="${not empty sm}">
            <div class="homeContainer" id="newFeature">
                <h2>News</h2>
                <p id="homeMessage">
                        ${sm.text}
                </p>
            </div>
        </c:if>

        <div class="title">
            <h4>All course</h4>
            <a href="admin?go=createCourse">Add new course
                <label>
                    <i class="fas fa-plus-circle"></i>
                </label>
            </a>
        </div>
        <div class="homeContainer" id="courseTypeList">
            <div class="row course type">
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-type-item">
                        <div class="courseTypeTitle">
                            <h6>Software Design</h6>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-type-item">
                        <div class="courseTypeTitle">
                            <h6>Software Design</h6>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-type-item">
                        <div class="courseTypeTitle">
                            <h6>Software Design</h6>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-type-item">
                        <div class="courseTypeTitle">
                            <h6>Software Design</h6>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="homeContainer" id="courseList">
            <div class="row courses">
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-item">
                        <div class="courseTitle">
                            <h5>Software Requirement - Yêu cầu phần mềm</h5>
                        </div>
                        <div class="courseSum">
                            <p>Software Requirement_Yêu cầu phần mềm</p>
                            <p>Lecturer: KienNT</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Remove Course</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-item">
                        <div class="courseTitle">
                            <h5>Software Requirement - Yêu cầu phần mềm</h5>
                        </div>
                        <div class="courseSum">
                            <p>Software Requirement_Yêu cầu phần mềm</p>
                            <p>Lecturer: KienNT</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Remove Course</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-item">
                        <div class="courseTitle">
                            <h5>Software Requirement - Yêu cầu phần mềm</h5>
                        </div>
                        <div class="courseSum">
                            <p>Software Requirement_Yêu cầu phần mềm</p>
                            <p>Lecturer: KienNT</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Remove Course</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-item">
                        <div class="courseTitle">
                            <h5>Software Requirement - Yêu cầu phần mềm</h5>
                        </div>
                        <div class="courseSum">
                            <p>Software Requirement_Yêu cầu phần mềm</p>
                            <p>Lecturer: KienNT</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Remove Course</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-item">
                        <div class="courseTitle">
                            <h5>Software Requirement - Yêu cầu phần mềm</h5>
                        </div>
                        <div class="courseSum">
                            <p>Software Requirement_Yêu cầu phần mềm</p>
                            <p>Lecturer: KienNT</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Remove Course</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-item">
                        <div class="courseTitle">
                            <h5>Software Requirement - Yêu cầu phần mềm</h5>
                        </div>
                        <div class="courseSum">
                            <p>Software Requirement_Yêu cầu phần mềm</p>
                            <p>Lecturer: KienNT</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Remove Course</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-item">
                        <div class="courseTitle">
                            <h5>Software Requirement - Yêu cầu phần mềm</h5>
                        </div>
                        <div class="courseSum">
                            <p>Software Requirement_Yêu cầu phần mềm</p>
                            <p>Lecturer: KienNT</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Remove Course</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-item">
                        <div class="courseTitle">
                            <h5>Software Requirement - Yêu cầu phần mềm</h5>
                        </div>
                        <div class="courseSum">
                            <p>Software Requirement_Yêu cầu phần mềm</p>
                            <p>Lecturer: KienNT</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Remove Course</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-item">
                        <div class="courseTitle">
                            <h5>Software Requirement - Yêu cầu phần mềm</h5>
                        </div>
                        <div class="courseSum">
                            <p>Software Requirement_Yêu cầu phần mềm</p>
                            <p>Lecturer: KienNT</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Remove Course</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-item">
                        <div class="courseTitle">
                            <h5>Software Requirement - Yêu cầu phần mềm</h5>
                        </div>
                        <div class="courseSum">
                            <p>Software Requirement_Yêu cầu phần mềm</p>
                            <p>Lecturer: KienNT</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Remove Course</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-item">
                        <div class="courseTitle">
                            <h5>Software Requirement - Yêu cầu phần mềm</h5>
                        </div>
                        <div class="courseSum">
                            <p>Software Requirement_Yêu cầu phần mềm</p>
                            <p>Lecturer: KienNT</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Remove Course</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-item">
                        <div class="courseTitle">
                            <h5>Software Requirement - Yêu cầu phần mềm</h5>
                        </div>
                        <div class="courseSum">
                            <p>Software Requirement_Yêu cầu phần mềm</p>
                            <p>Lecturer: KienNT</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Remove Course</a>
                    </div>
                </div>
            </div>
            <%--            testcourslist--%>
            <c:set var="listCourse" value="${requestScope.listCourse}"></c:set>
            <c:if test="${not empty listCourse}">
                <div class="row courseCategory">
                    <c:forEach items="${requestScope.listCourse}" var="lco" varStatus="status">
                        <div class="col-lg-3 col-md-4 col-sm-6 item">
                            <div class="wrap-course-item">
                                <div class="courseTitle">
                                        <%--                                    <a href="#/courseID=${lco.courseID}">--%>
                                    <h5>${lco.courseName}</h5>
                                        <%--                                    </a>--%>
                                </div>
                                <div class="courseSum">
                                    <p>${lco.courseName}</p>
                                    <p>Lecturer: ${lco.lecturerID}</p>
                                </div>
                                <a href="#/courseID=${lco.courseID}">Go to course --></a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div id="wrap-pagination">
                    <ul class="pagination">
                        <li><a href="#" class="prev">&laquo</a></li>
                        <li><a href="#" class="active">1</a></li>
                        <c:forEach begin="1" end="${pageCount}" var="pageNumber">
                        <li><a href="#">${pageNumber}</a></li>
                        </c:forEach>
                </div>
            </c:if>
            <%--            testpaging--%>
            <div id="wrap-pagination">
                <ul class="pagination">
                    <li><a href="#" class="prev">&laquo</a></li>
                    <li><a href="#" class="active">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li><a href="#">6</a></li>
                    <li><a href="#">7</a></li>
                    <li><a href="#">8</a></li>
                    <li><a href="#">9</a></li>
                    <li><a href="#" class="next">&raquo;</a></li>
                </ul>
                <br>
            </div>
            <%--            testpaging--%>
            <script>
                var paginationLinks = document.querySelectorAll('#wrap-pagination .pagination a');
                paginationLinks.forEach(function (link, index) {
                    link.addEventListener('click', function (event) {
                        event.preventDefault();
                        paginationLinks.forEach(function (link) {
                            link.classList.remove('active');
                        });
                        var currentIndex = Array.from(paginationLinks).indexOf(this);
                        if (this.classList.contains('prev')) {
                            if (currentIndex > 0) {
                                paginationLinks[currentIndex].classList.remove('active');
                                paginationLinks[currentIndex - 1].classList.add('active');
                            }
                        } else if (this.classList.contains('next')) {
                            if (currentIndex < paginationLinks.length - 1) {
                                paginationLinks[currentIndex].classList.remove('active');
                                paginationLinks[currentIndex + 1].classList.add('active');
                            }
                        } else {
                            this.classList.add('active');
                        }
                    });
                });
            </script>
        </div>

        <div class="title">
            <h4>All lecturer</h4>
            <a href="#">Add new lecturer
                <label>
                    <i class="fas fa-plus-circle"></i>
                </label>
            </a>
        </div>
        <div class="homeContainer" id="lecturerList">
            <div class="row lecturer">
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-lecturer-item">
                        <div class="lecturerTitle">
                            <h5>ThanhDT59</h5>
                        </div>
                        <div class="lecturerSum">
                            <p>A very friendly lecturer</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Manage lecturer</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-lecturer-item">
                        <div class="lecturerTitle">
                            <h5>ThanhDT59</h5>
                        </div>
                        <div class="lecturerSum">
                            <p>A very friendly lecturer</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Manage lecturer</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-lecturer-item">
                        <div class="lecturerTitle">
                            <h5>ThanhDT59</h5>
                        </div>
                        <div class="lecturerSum">
                            <p>A very friendly lecturer</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Manage lecturer</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-lecturer-item">
                        <div class="lecturerTitle">
                            <h5>ThanhDT59</h5>
                        </div>
                        <div class="lecturerSum">
                            <p>A very friendly lecturer</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Manage lecturer</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-lecturer-item">
                        <div class="lecturerTitle">
                            <h5>ThanhDT59</h5>
                        </div>
                        <div class="lecturerSum">
                            <p>A very friendly lecturer</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Manage lecturer</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-lecturer-item">
                        <div class="lecturerTitle">
                            <h5>ThanhDT59</h5>
                        </div>
                        <div class="lecturerSum">
                            <p>A very friendly lecturer</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Manage lecturer</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-lecturer-item">
                        <div class="lecturerTitle">
                            <h5>ThanhDT59</h5>
                        </div>
                        <div class="lecturerSum">
                            <p>A very friendly lecturer</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Manage lecturer</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-lecturer-item">
                        <div class="lecturerTitle">
                            <h5>ThanhDT59</h5>
                        </div>
                        <div class="lecturerSum">
                            <p>A very friendly lecturer</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Manage lecturer</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-lecturer-item">
                        <div class="lecturerTitle">
                            <h5>ThanhDT59</h5>
                        </div>
                        <div class="lecturerSum">
                            <p>A very friendly lecturer</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Manage lecturer</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-lecturer-item">
                        <div class="lecturerTitle">
                            <h5>ThanhDT59</h5>
                        </div>
                        <div class="lecturerSum">
                            <p>A very friendly lecturer</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Manage lecturer</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-lecturer-item">
                        <div class="lecturerTitle">
                            <h5>ThanhDT59</h5>
                        </div>
                        <div class="lecturerSum">
                            <p>A very friendly lecturer</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Manage lecturer</a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-lecturer-item">
                        <div class="lecturerTitle">
                            <h5>ThanhDT59</h5>
                        </div>
                        <div class="lecturerSum">
                            <p>A very friendly lecturer</p>
                        </div>
                        <a>Rating: 8</a>
                        <div></div>
                        <a href="#">Manage lecturer</a>
                    </div>
                </div>
            </div>
            <%--            testpaging--%>
            <div id="wrap-pagination">
                <ul class="pagination">
                    <li><a href="#" class="prev">&laquo</a></li>
                    <li><a href="#" class="active">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li><a href="#">6</a></li>
                    <li><a href="#">7</a></li>
                    <li><a href="#">8</a></li>
                    <li><a href="#">9</a></li>
                    <li><a href="#" class="next">&raquo;</a></li>
                </ul>
                <br>
            </div>
            <%--            testpaging--%>
            <script>
                var paginationLinks = document.querySelectorAll('#wrap-pagination .pagination a');
                paginationLinks.forEach(function (link, index) {
                    link.addEventListener('click', function (event) {
                        event.preventDefault();
                        paginationLinks.forEach(function (link) {
                            link.classList.remove('active');
                        });
                        var currentIndex = Array.from(paginationLinks).indexOf(this);
                        if (this.classList.contains('prev')) {
                            if (currentIndex > 0) {
                                paginationLinks[currentIndex].classList.remove('active');
                                paginationLinks[currentIndex - 1].classList.add('active');
                            }
                        } else if (this.classList.contains('next')) {
                            if (currentIndex < paginationLinks.length - 1) {
                                paginationLinks[currentIndex].classList.remove('active');
                                paginationLinks[currentIndex + 1].classList.add('active');
                            }
                        } else {
                            this.classList.add('active');
                        }
                    });
                });
            </script>
        </div>

        <div class="title">
            <h4>All course types</h4>
            <a href="#">Add new course type
                <label>
                    <i class="fas fa-plus-circle"></i>
                </label>
            </a>
        </div>
        <div class="homeContainer" id="courseTypeList">
            <div class="row course type">
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-type-item">
                        <div class="courseTypeTitle">
                            <h6>Software Design</h6>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-type-item">
                        <div class="courseTypeTitle">
                            <h6>Software Design</h6>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-type-item">
                        <div class="courseTypeTitle">
                            <h6>Software Design</h6>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6 item">
                    <div class="wrap-course-type-item">
                        <div class="courseTypeTitle">
                            <h6>Software Design</h6>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

