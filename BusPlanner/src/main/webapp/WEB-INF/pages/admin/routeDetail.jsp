<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <title>Route Details</title>
    </head>
    <body>
        <div class="container mt-5">
            <h1>Chi tiết tuyến đường</h1>
            <table class="table table-bordered">
                <tr>
                    <th>ID</th>
                    <td>${route.routeId}</td>
                </tr>
                <tr>
                    <th>Tên tuyến đường</th>
                    <td>${route.routeName}</td>
                </tr>
                <tr>
                    <th>Nơi bắt đầu</th>
                    <td>${route.startPoint}</td>
                </tr>
                <tr>
                    <th>Nơi kết thúc</th>
                    <td>${route.endPoint}</td>
                </tr>
                <tr>
                    <th>Hướng</th>
                    <td>
                        <c:choose>
                            <c:when test="${route.direction == 'inbound'}">Hướng về</c:when>
                            <c:when test="${route.direction == 'outbound'}">Hướng đi</c:when>
                            <c:otherwise>Unknown</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
            <a href="${pageContext.request.contextPath}/admin/routes" class="btn btn-primary">Quay lại</a>

            <h3 class="mt-4">Các điểm dừng trên tuyến đường</h3>
            <table class="table table-striped table-bordered mt-3">
                <thead class="thead-dark">
                    <tr>
                        <th>Thứ tự</th>
                        <th>Tên điểm dừng</th>
                        <th>Hướng</th>
                        <th>Địa chỉ</th>
                        <th>Vĩ độ</th>
                        <th>Kinh độ</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="stop" items="${routeStops}">
                        <tr>
                            <td>${stop.stopOrder}</td>
                            <td>${stop.stopId.stopName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${stop.direction == 'inbound'}">Hướng về</c:when>
                                    <c:when test="${stop.direction == 'outbound'}">Hướng đi</c:when>
                                    <c:otherwise>Unknown</c:otherwise>
                                </c:choose>
                            </td>
                            <td>${stop.stopId.address}</td>
                            <td>${stop.stopId.latitude}</td>
                            <td>${stop.stopId.longitude}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Toggle Form Button -->
            <button type="button" class="btn btn-success mb-3" id="toggleFormBtn">Thêm Điểm Dừng</button>

            <!-- Add Route Stop Form (Initially Hidden) -->
            <div class="form-container" id="addBusForm" style="display: none;">
                <h3>Thêm Điểm Dừng</h3>
                <c:url value="/admin/routes/${route.routeId}/" var="postRouteStop" />
                <form:form action="${postRouteStop}" method="post" modelAttribute="routeStop">
                    <div class="mb-3">
                        <label for="stopId" class="form-label">Stop ID</label>
                        <form:input cssClass="form-control" id="stopId" path="stopId" list="stopsList" />
                        <datalist id="stopsList">
                            <c:forEach items="${stops}" var="stop">
                                <option value="${stop.stopId}">${stop.stopName}</option>
                            </c:forEach>
                        </datalist>
                    </div>
                    <div class="mb-3">
                        <label for="direction" class="form-label">Direction</label>
                        <form:input cssClass="form-control" id="direction" path="direction" value="${route.direction}" readonly="true" />
                    </div>
                    <div class="mb-3">
                        <label for="stopOrder" class="form-label">Order</label>
                        <form:input cssClass="form-control" id="stopOrder" path="stopOrder" value="${nextOrder}" readonly="true" />
                    </div>
                    <button type="submit" class="btn btn-primary">Add Stop</button>
                </form:form>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <!-- Toggle Form Script -->
        <script>
            document.getElementById('toggleFormBtn').addEventListener('click', function () {
                var form = document.getElementById('addBusForm');
                form.style.display = form.style.display === 'none' ? 'block' : 'none';
            });
        </script>
    </body>
</html>
