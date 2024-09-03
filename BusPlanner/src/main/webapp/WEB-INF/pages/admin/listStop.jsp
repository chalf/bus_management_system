<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta charset="UTF-8">

        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .highlight {
                background-color: yellow;
            }
            .form-container {
                display: none; /* Hide the form by default */
            }
        </style>
    </head>
    <body>   
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-10">
                    <h1 class="text-center mb-4">Danh sách các điểm dừng</h1>

                    <!-- Search Form -->
                    <h3>Tìm kiếm</h3>
                    <form method="get" action="${pageContext.request.contextPath}/admin/stops" class="form-inline mb-4">
                        <input type="text" name="q" value="${param.q}" class="form-control mr-2" placeholder="Tìm theo địa chỉ">
                        <input type="text" name="name" value="${param.name}" class="form-control mr-2" placeholder="Tìm theo tên điểm dừng">
                        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                        <button type="button" class="btn btn-success ml-2" id="addStopBtn">Thêm điểm dừng</button>
                    </form>

                    <!-- Add Stop Form (Initially Hidden) -->
                    <div class="form-container" id="addStopForm">
                        <h3>Thêm điểm dừng</h3>
                        <c:url value="/admin/stops" var="postStop" />
                        <form:form action="${postStop}" method="post" modelAttribute="stop">
                            <div class="mb-3">
                                <label for="stopName" class="form-label">Tên điểm dừng</label>
                                <form:input id="stopName" path="stopName" cssClass="form-control" />
                            </div>
                            <div class="mb-3">
                                <label for="address" class="form-label">Địa chỉ</label>
                                <form:input id="address" path="address" cssClass="form-control" />
                            </div>
                            <div class="mb-3">
                                <label for="latitude" class="form-label">Vĩ độ</label>
                                <form:input id="latitude" path="latitude" cssClass="form-control" />
                            </div>
                            <div class="mb-3">
                                <label for="longitude" class="form-label">Kinh độ</label>
                                <form:input id="longitude" path="longitude" cssClass="form-control" />
                            </div>
                            <button type="submit" class="btn btn-primary">Thêm điểm dừng</button>
                        </form:form>
                    </div>

                    <!-- Table of Stops -->
                    <table class="table table-hover table-bordered mt-4">
                        <thead class="thead-dark">
                            <tr>
                                <th>ID</th>
                                <th>Tên điểm dừng</th>
                                <th>Địa chỉ</th>
                                <th>Vĩ độ</th>
                                <th>Kinh độ</th>
                                <th class="text-center"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="stop" items="${stops}">
                                <tr>
                                    <td>${stop.stopId}</td>
                                    <td><span class="highlight-text">${stop.stopName}</span></td>
                                    <td><span class="highlight-text">${stop.address}</span></td>
                                    <td><span class="highlight-text">${stop.latitude}</span></td>
                                    <td><span class="highlight-text">${stop.longitude}</span></td>
                                    <td class="text-center">
                                        <!-- Edit Button -->
                                        <button type="button" class="btn btn-sm btn-warning" onclick="showUpdateForm(${stop.stopId}, '${stop.stopName}', '${stop.address}', '${stop.latitude}', '${stop.longitude}')">
                                            <i class="fas fa-edit"></i> Thay đổi
                                        </button>
                                        <!-- Delete Button -->
                                        <form action="${pageContext.request.contextPath}/admin/stops/delete/${stop.stopId}" method="post" style="display:inline;" onsubmit="return confirmDelete();">
                                            <button type="submit" class="btn btn-sm btn-danger">
                                                <i class="fas fa-trash"></i> Xóa
                                            </button>
                                        </form>
                                        
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    
                    <!-- Update Stop Form (Initially Hidden) -->
                    <div class="form-container" id="updateStopForm">
                        <h3>Thay đổi điểm dừng</h3>
                        <c:url value="/admin/stops/update" var="updateStopUrl" />
                        <form:form id="updateForm" action="${updateStopUrl}" method="post" modelAttribute="stop">
                            <input type="hidden" name="stopId" id="updateStopId">
                            <div class="mb-3">
                                <label for="updateStopName" class="form-label">Tên điểm dừng</label>
                                <form:input id="updateStopName" path="stopName" cssClass="form-control" />
                            </div>
                            <div class="mb-3">
                                <label for="updateAddress" class="form-label">Địa chỉ</label>
                                <form:input id="updateAddress" path="address" cssClass="form-control" />
                            </div>
                            <div class="mb-3">
                                <label for="updateLatitude" class="form-label">Vĩ độ</label>
                                <form:input id="updateLatitude" path="latitude" cssClass="form-control" />
                            </div>
                            <div class="mb-3">
                                <label for="updateLongitude" class="form-label">Kinh độ</label>
                                <form:input id="updateLongitude" path="longitude" cssClass="form-control" />
                            </div>
                            <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Scripts -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://kit.fontawesome.com/a076d05399.js"></script>
        <script>
            $(document).ready(function () {
                var searchText = "${param.q}";
                if (searchText) {
                    $(".highlight-text").each(function () {
                        var text = $(this).text();
                        var regex = new RegExp(searchText, "gi");
                        var newText = text.replace(regex, function (matchedText) {
                            return "<span class='highlight'>" + matchedText + "</span>";
                        });
                        $(this).html(newText);
                    });
                }

                // Toggle form visibility
                $("#addStopBtn").click(function () {
                    $("#addStopForm").toggle();
                });
            });

            function confirmDelete() {
                return confirm("Bạn chắc chắn muốn xóa?");
            }

            function showUpdateForm(stopId, stopName, address, latitude, longitude) {
                // Fill form fields with existing stop data
                $('#updateStopId').val(stopId);
                $('#updateStopName').val(stopName);
                $('#updateAddress').val(address);
                $('#updateLatitude').val(latitude);
                $('#updateLongitude').val(longitude);
                // Show the form
                $("#updateStopForm").show();
            }
        </script>
    </body>
</html>
