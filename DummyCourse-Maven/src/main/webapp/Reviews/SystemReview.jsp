<%--
  Created by IntelliJ IDEA.
  User: VietAnh
  Date: 7/18/2023
  Time: 9:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>System reviews</title>
    <style>
        <%@include file="../CSS/home.css" %>
    </style>
    <style>
        .main {
            background-color: #F1F1F1;
            padding: 2%;
        }

        .sort-select-range {
            display: flex;
            margin-bottom: 15px;
        }

        .btn-delete {
            background-color: #FD663F !important;
            color: white !important;
        }

        .display-reviews {
            margin-bottom: 20px;
        }

        .review-info {
            background-color: #D9D9D9;
            padding: 18px 18px 2px 18px;
        }

        .review-title {
            display: flex;
            justify-content: space-between;
        }

        .title-info {
            display: flex;
            color: #FD663F;
        }

        .delete-review {
            margin-bottom: 15px;
        }

        .review-text {
            font-weight: 500;
            line-height: 2;
            padding-right: 50px;
        }
    </style>
    </style>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="/Header/import.jsp"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/Header/header.jsp"/>
<div class="content-page">
    <h2>${information}</h2>
    <div class="main w-75">
        <div class="sort-select-range">
            <h5>Select Range:</h5>
            <div class="dropdown">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu2"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                        style="background-color: #9A9A9A">
                    ${range == null || range == "" ? "Range" : range}
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenu2" style="background-color: #B5B5B5">
                    <button class="dropdown-item" type="button" onclick="window.location.href = './${href}sort='">None</button>
                    <button class="dropdown-item" type="button" onclick="window.location.href = './${href}sort=1-2'">1-2 stars</button>
                    <button class="dropdown-item" type="button" onclick="window.location.href = './${href}sort=2-3'">2-3 stars</button>
                    <button class="dropdown-item" type="button" onclick="window.location.href = './${href}sort=3-4'">3-4 stars</button>
                    <button class="dropdown-item" type="button" onclick="window.location.href = './${href}sort=4-5'">4-5 stars</button>

                </div>
            </div>
        </div>
        <c:forEach items="${systemReviews}" var="systemReviews">
            <div class="display-reviews">
                <div class="delete-review">
                    <a class="btn btn-delete" href="./${href}deleteSystemReview=${systemReviews.reviewID}"> Delete review</a>
                </div>
                <div class="review-info">
                    <div class="review-title">
                        <div>
                            <h3>User : ${systemReviews.userName}</h3>
                        </div>
                        <div class="title-info">
                            <p style="margin-right: 50px">Rating : ${systemReviews.rating}</p>
                            <p>From : ${systemReviews.reviewDate}</p>
                        </div>
                    </div>
                    <div class="review-body">
                        <p class="review-text">
                                ${systemReviews.comment}
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
        <div id="pagination" class="pagination-responsive d-flex justify-content-center"
             style="justify-content: center;justify-content: space-around !important">
            <ul class="pagination justify-content-end" style="justify-items:  center">
                <c:if test="${index > 1 }">
                    <li class="page-item "><a class="page-link debtor-paging" href="./${href}index=${index-1}">Previous</a>
                    </li>
                </c:if>
                <c:forEach begin="1" end="${endPage}" var="i">
                    <li class="page-item ${index == i?"active":"" }" style="background-color: aliceblue !important;"><a class="page-link debtor-paging"
                                                                                                                        href="./${href}index=${i}">${i}</a></li>
                </c:forEach>
                <c:if test="${index < endPage}">
                    <li class="page-item"><a class="page-link debtor-paging"
                                             href="./${href}index=${index+1}">Next</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</div>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</html>
