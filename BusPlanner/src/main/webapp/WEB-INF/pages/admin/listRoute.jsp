<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
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
        </style>
    </head>
    <body>   
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-10">
                    <h1 class="text-center mb-4">Danh sách các tuyến đường</h1>

                    <!-- Search Form -->
                    <h3>Tìm kiếm</h3>
                    <form method="get" action="${pageContext.request.contextPath}/admin/routes" class="form-inline mb-4">
                        <input type="text" name="q" value="${param.q}" class="form-control mr-2" placeholder="Tìm theo tên tuyến đường">
                        <input type="text" name="start" value="${param.start}" class="form-control mr-2" placeholder="Nơi bắt đầu">
                        <input type="text" name="end" value="${param.end}" class="form-control mr-2" placeholder="Nơi kết thúc">
                        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                        <button type="button" class="btn btn-success ml-2" id="addRouteBtn">Thêm tuyến đường</button>
                    </form>

                    <!-- Add Route Form (Initially Hidden) -->
                    <div class="form-container" id="addRouteForm">
                        <h3>Thêm tuyến đường</h3>
                        <c:url value="/admin/routes" var="postRoute" />
                        <form:form action="${postRoute}" method="post" modelAttribute="route">
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
                            <div class="mb-3">
                                <label for="direction" class="form-label">Hướng</label>
                                <form:select id="direction" path="direction" class="form-control" >
                                    <option value="inbound">Hướng về</option>
                                    <option value="outbound">Hướng đi</option>
                                </form:select>
                            </div>
                            <button type="submit" class="btn btn-primary">Thêm tuyến đường</button>
                        </form:form>
                    </div>

                    <!-- Table of Routes -->
                    <table class="table table-hover table-bordered mt-4">
                        <thead class="thead-dark">
                            <tr>
                                <th>ID</th>
                                <th>Tên tuyến đường</th>
                                <th>Nơi bắt đầu</th>
                                <th>Nơi kết thúc</th>
                                <th>Hướng</th>
                                <th class="text-center"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="route" items="${routes}">
                                <tr>
                                    <td>${route.routeId}</td>
                                    <td><span class="highlight-text">${route.routeName}</span></td>
                                    <td><span class="highlight-text">${route.startPoint}</span></td>
                                    <td><span class="highlight-text">${route.endPoint}</span></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${route.direction == 'inbound'}">Hướng về</c:when>
                                            <c:otherwise>Hướng đi</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center">
                                        <a href="<c:url value='/admin/route/${route.routeId}' />" class="btn btn-sm btn-warning">
                                            <i class="fas fa-edit"></i> Chi tiết
                                        </a>

                                        <a href="#" class="btn btn-sm btn-danger">
                                            <i class="fas fa-trash"></i> Xóa
                                        </a>
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
        <!-- FontAwesome for icons -->
        <script src="https://kit.fontawesome.com/a076d05399.js"></script>
        <!-- Highlighting Script -->
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
                $("#addRouteBtn").click(function () {
                    $("#addRouteForm").toggle();
                });
            });
        </script>
    </body>
</html>
