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
            </table>

            <!-- Buttons to navigate back and toggle edit form -->
            <a href="${pageContext.request.contextPath}/admin/routes" class="btn btn-primary">Quay lại</a>
            <button type="button" class="btn btn-warning ml-2" id="toggleEditFormBtn">Thay đổi thông tin tuyến xe</button>

            <!-- Edit Route Form (Initially Hidden) -->
            <div class="form-container" id="editRouteForm" style="display: none;">
                <h3>Chỉnh sửa thông tin tuyến xe</h3>
                <form:form action="${pageContext.request.contextPath}/admin/routes/update/${route.routeId}" method="post" modelAttribute="route">
                    <div class="mb-3">
                        <label for="routeName" class="form-label">Tên tuyến đường</label>
                        <form:input id="routeName" path="routeName" cssClass="form-control" />
                    </div>
                    <div class="mb-3">
                        <label for="startPoint" class="form-label">Nơi bắt đầu</label>
                        <form:input id="startPoint" path="startPoint" cssClass="form-control" />
                    </div>
                    <div class="mb-3">
                        <label for="endPoint" class="form-label">Nơi kết thúc</label>
                        <form:input id="endPoint" path="endPoint" cssClass="form-control"  />
                    </div>
                    <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                </form:form>
            </div>

            <h3 class="mt-4">Các điểm dừng trên tuyến đường</h3>
            <table class="table table-striped table-bordered mt-3">
                <thead class="thead-dark">
                    <tr>
                        <th>Thứ tự</th>
                        <th>Tên điểm dừng</th>
                        <th>Địa chỉ</th>
                        <th>Vĩ độ</th>
                        <th>Kinh độ</th>
                        <th class="text-center"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="stop" items="${routeStops}">
                        <tr>
                            <td>${stop.stopOrder}</td>
                            <td>${stop.stopId.stopName}</td>
                            <td>${stop.stopId.address}</td>
                            <td>${stop.stopId.latitude}</td>
                            <td>${stop.stopId.longitude}</td>
                            <td class="text-center">
                                <form action="${pageContext.request.contextPath}/admin/routestop/delete/${stop.routeStopId}" method="post" style="display:inline;" onsubmit="return confirmDelete();">
                                    <button type="submit" class="btn btn-sm btn-danger">
                                        <i class="fas fa-trash"></i> Xóa
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Add Route Stop Form (Initially Hidden) -->
            <button type="button" class="btn btn-success mb-3" id="toggleFormBtn">Thêm Điểm Dừng</button>
            <div style="padding-bottom: 10px;" class="form-container" id="addBusForm" style="display: none;">
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
                        <label for="stopOrder" class="form-label">Order</label>
                        <form:input cssClass="form-control" id="stopOrder" path="stopOrder" value="${nextOrder}" readonly="true" />
                    </div>
                    <button type="submit" class="btn btn-primary">Add Stop</button>
                </form:form>
            </div>

            <!-- Nút sắp xếp các điểm dừng -->
            <button id="toggleReorder" class="btn btn-primary mb-3">Sắp xếp các điểm dừng</button>

            <!-- Div để sắp xếp các điểm dừng -->
            <div id="reorderContainer" style="display: none;" class="mt-3">
                <form id="reorderForm" action="${pageContext.request.contextPath}/admin/routestop/update" method="post">
                    <ul id="sortableStops" class="list-group">
                        <c:forEach var="stop" items="${routeStops}" varStatus="status">
                            <li class="list-group-item" data-id="${stop.routeStopId}">
                                <input type="hidden" name="routeStops[${status.index}].routeStopId" value="${stop.routeStopId}" />
                                <input type="hidden" name="routeStops[${status.index}].stopOrder" value="${status.index + 1}" />
                                <span class="badge badge-primary mr-2 stop-order">${status.index + 1}</span>
                                ${stop.stopId.stopName}
                            </li>
                        </c:forEach>
                    </ul>
                    <input type="hidden" name="routeId" value="${route.routeId}" />
                    <button type="submit" class="btn btn-success mt-3">Cập nhật</button>
                </form>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <!-- Bootstrap JS and dependencies -->
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

            <!-- Toggle Form Scripts -->
            <script>
                                    document.getElementById('toggleFormBtn').addEventListener('click', function () {
                                        var form = document.getElementById('addBusForm');
                                        form.style.display = form.style.display === 'none' ? 'block' : 'none';
                                    });

                                    document.getElementById('toggleEditFormBtn').addEventListener('click', function () {
                                        var form = document.getElementById('editRouteForm');
                                        form.style.display = form.style.display === 'none' ? 'block' : 'none';
                                    });
                                    function confirmDelete() {
                                        return confirm("Bạn chắc chắn muốn xóa?");
                                    }

            </script>

            <script>
                // Định nghĩa biến currentRouteId từ giá trị routeId trong JSP
                var currentRouteId = '${route.routeId}';
            </script>
            <script>
                $(document).ready(function () {
                    $("#toggleReorder").click(function () {
                        $("#reorderContainer").toggle(); // Toggle the display of the reorder container
                    });

                    $("#sortableStops").sortable({
                        update: function (event, ui) {
                            updateStopOrders(); // Update the stop order badges
                        }
                    });

                    // Function to update the displayed stop order
                    function updateStopOrders() {
                        $("#sortableStops li").each(function (index) {
                            $(this).find('.stop-order').text(index + 1); // Update the order number
                            $(this).find('input[name$=".stopOrder"]').val(index + 1); // Update the hidden input field
                        });
                    }
                });

            </script>
    </body>
</html>
