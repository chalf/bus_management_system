<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-10">
            <h1 class="text-center mb-4">Danh Sách Tuyến Đường Yêu Thích</h1>

            <!-- Display Favorite Routes -->
            <table class="table table-hover table-bordered mt-3">
                <thead class="thead-dark">
                    <tr>
                        <th>ID Tuyến Đường</th>
                        <th>Tên Tuyến Đường</th>
                        <th>Người Dùng</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="route" items="${favoriteRoutes}">
                        <tr>
                            <td><c:out value="${route.routeId.routeId}"/></td>
                            <td><c:out value="${route.routeId.routeName}"/></td>
                            <td><c:out value="${route.userId.username}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
