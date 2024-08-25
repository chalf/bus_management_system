<%-- 
    Document   : admin
    Created on : Aug 23, 2024, 12:06:35 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Chủ Quản Lý</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center mb-4">Trang Chủ Quản Lý</h1>
        <div class="list-group">
            <a href="admin/buses" class="list-group-item list-group-item-action d-flex align-items-center">
                <i class="bi bi-bus-front me-3"></i> Quản lý Xe buýt
            </a>
            <a href="admin/routes" class="list-group-item list-group-item-action d-flex align-items-center">
                <i class="bi bi-map me-3"></i> Quản lý Tuyến đường
            </a>
            <a href="admin/stops" class="list-group-item list-group-item-action d-flex align-items-center">
                <i class="bi bi-geo-alt me-3"></i> Quản lý Điểm dừng
            </a>
            <a href="admin/users" class="list-group-item list-group-item-action d-flex align-items-center">
                <i class="bi bi-people me-3"></i> Quản lý Người dùng
            </a>
        </div>
    </div>

    <!-- Bootstrap 5 JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
