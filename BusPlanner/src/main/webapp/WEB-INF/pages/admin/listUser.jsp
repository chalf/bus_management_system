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
                    <h1 class="text-center mb-4">Danh sách người dùng</h1>

                    <!-- Search Form -->
                    <h3>Tìm kiếm</h3>
                    <form method="get" action="${pageContext.request.contextPath}/admin/users" class="form-inline mb-4">
                        <input type="text" name="q" value="${param.q}" class="form-control mr-2" placeholder="Tìm theo tên đăng nhập">
                        <input type="text" name="fullname" value="${param.fullname}" class="form-control mr-2" placeholder="Tìm theo tên đầy đủ">
                        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                    </form>

                    <!-- Table of Users -->
                    <table class="table table-hover table-bordered mt-4">
                        <thead class="thead-dark">
                            <tr>
                                <th>Tài khoản</th>
                                <th>Email</th>
                                <th>Họ tên</th>
                                <th>Vai trò</th>
                                <th>Ngày tạo</th>
                                <th>Ngày cập nhật</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <td>
                                        <c:out value="${user.username}" escapeXml="false" />
                                    </td>
                                    <td>
                                        <c:out value="${user.email}" escapeXml="false" />
                                    </td>
                                    <td>
                                        <c:out value="${user.fullName}" escapeXml="false" />
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user.role == 'ROLE_CITIZEN'}">Người dùng</c:when>
                                            <c:when test="${user.role == 'ROLE_ADMIN'}">ADMIN</c:when>
                                            <c:otherwise>Không xác định</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:out value="${user.createdAt}" escapeXml="false" />
                                    </td>
                                    <td>
                                        <c:out value="${user.updatedAt}" escapeXml="false" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- If no users are found -->
                    <c:if test="${empty users}">
                        <p class="text-center">Không tìm thấy người dùng nào.</p>
                    </c:if>
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
                var searchQuery = "${param.q}";
                var fullNameQuery = "${param.fullname}";

                // Highlight username and email
                if (searchQuery || fullNameQuery) {
                    $(".table tbody td").each(function () {
                        var text = $(this).text();
                        var regex = new RegExp(searchQuery || fullNameQuery, "gi");
                        var newText = text.replace(regex, function (matchedText) {
                            return "<span class='highlight'>" + matchedText + "</span>";
                        });
                        $(this).html(newText);
                    });
                }
            });
        </script>
    </body>
</html>
