<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .highlight {
            background-color: yellow;
        }
        .form-container {
            display: none; /* Hide the form by default */
        }
        .form-label {
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-10">
            <h1 class="text-center mb-4">Danh Sách Xe Buýt</h1>

            <!-- Error Message -->
            <c:if test="${errorMessage != null}">
                <div class="alert alert-danger">
                    ${errorMessage}
                </div>
            </c:if>

            <!-- Toggle Add Bus Form Button -->
            <button type="button" class="btn btn-success mb-3" id="toggleAddFormBtn">Thêm Xe Buýt</button>

            <!-- Add Bus Form (Initially Hidden) -->
            <div class="form-container" id="addBusForm">
                <h3>Thêm Xe Buýt</h3>
                <c:url value="/admin/buses" var="postBus"/>
                <form:form action="${postBus}" method="post" modelAttribute="bus">
                    <div class="mb-3">
                        <label for="busNumber" class="form-label">Số Xe</label>
                        <form:input cssClass="form-control" id="busNumber" path="busNumber"/>
                    </div>
                    <div class="mb-3">
                        <label for="browser">Tuyến Đường</label>
                        <form:input cssClass="form-control" list="browsers" id="browser" path="routeId"/>
                        <datalist id="browsers">
                            <c:forEach items="${routes}" var="route">
                                <option value="${route.routeId}">${route.startPoint} - ${route.endPoint}</option>
                            </c:forEach>
                        </datalist>
                    </div>
                    <button type="submit" class="btn btn-primary">Thêm</button>
                </form:form>
            </div>

            <!-- Edit Bus Form (Initially Hidden) -->
            <div class="form-container" id="editBusForm">
                <h3>Chỉnh Sửa Xe Buýt</h3>
                <c:url value="/admin/buses/update" var="updateBus"/>
                <form:form action="${updateBus}" method="post" modelAttribute="bus">
                    <form:hidden path="busId"/> <!-- Hidden field for bus ID -->
                    <div class="mb-3">
                        <label for="editBusNumber" class="form-label">Số Xe</label>
                        <form:input cssClass="form-control" id="editBusNumber" path="busNumber"/>
                    </div>
                    <div class="mb-3">
                        <label for="editRouteId">Tuyến Đường</label>
                        <form:input cssClass="form-control" list="editRoutes" id="editRouteId" path="routeId"/>
                        <datalist id="editRoutes">
                            <c:forEach items="${routes}" var="route">
                                <option value="${route.routeId}">${route.startPoint} - ${route.endPoint}</option>
                            </c:forEach>
                        </datalist>
                    </div>
                    <button type="submit" class="btn btn-primary">Cập Nhật</button>
                </form:form>
            </div>

            <!-- Table to Display Buses -->
            <h3 class="mt-4">Danh sách Xe Buýt</h3>
            <table class="table table-hover table-bordered mt-4">
                <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Số Xe</th>
                        <th>Tuyến Đường</th>
                        <th class="text-center"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="bus" items="${buses}">
                        <tr>
                            <td><c:out value="${bus.busId}"/></td>
                            <td><c:out value="${bus.busNumber}"/></td>
                            <td><c:out value="${bus.routeId.routeName}"/></td>
                            <td class="text-center">
                                <!-- Edit Button -->
                                <button type="button" class="btn btn-sm btn-warning editBusBtn" data-bus-id="${bus.busId}" data-bus-number="${bus.busNumber}" data-route-id="${bus.routeId.routeId}">
                                    <i class="fas fa-edit"></i> Thay đổi
                                </button>
                                <!-- Delete Button -->
                                <form action="${pageContext.request.contextPath}/admin/buses/delete/${bus.busId}" method="post" style="display:inline;" onsubmit="return confirmDelete();">
                                    <button type="submit" class="btn btn-sm btn-danger">
                                        <i class="fas fa-trash"></i> Xóa
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<!-- Script to Toggle Form Visibility and Edit Bus -->
<script>
document.addEventListener('DOMContentLoaded', function () {
    var toggleAddFormBtn = document.getElementById('toggleAddFormBtn');
    var addBusForm = document.getElementById('addBusForm');
    var editBusForm = document.getElementById('editBusForm');

    toggleAddFormBtn.addEventListener('click', function () {
        addBusForm.style.display = addBusForm.style.display === 'none' ? 'block' : 'none';
        editBusForm.style.display = 'none';
    });

    // Handle Edit Bus Button Click
    document.querySelectorAll('.editBusBtn').forEach(function (button) {
        button.addEventListener('click', function () {
            var busId = this.getAttribute('data-bus-id');
            var busNumber = this.getAttribute('data-bus-number');
            var routeId = this.getAttribute('data-route-id');

            // Populate the edit form
            document.getElementById('editBusForm').style.display = 'block';
            document.getElementById('addBusForm').style.display = 'none';
            document.getElementById('editBusNumber').value = busNumber;
            document.getElementById('editRouteId').value = routeId;
            document.querySelector('input[name="busId"]').value = busId;
        });
    });
});

function confirmDelete() {
    return confirm("Bạn có chắc muốn xóa xe buýt này?");
}
</script>
</body>
</html>
