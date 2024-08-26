<%-- 
    Document   : admin
    Created on : Aug 23, 2024, 12:06:35 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Bootstrap 5 JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center mb-4"><spring:message code="admin.dashboard.title"/></h1>
        <div class="list-group">
            <a href="<c:url value="/admin/buses" />" class="list-group-item list-group-item-action d-flex align-items-center">
                <i class="bi bi-bus-front me-3"></i> <spring:message code="admin.bus.heading"/>
            </a>
            <a href="<c:url value="/admin/routes" />" class="list-group-item list-group-item-action d-flex align-items-center">
                <i class="bi bi-map me-3"></i> <spring:message code="admin.route.heading"/>
            </a>
            <a href="<c:url value="/admin/stops" />" class="list-group-item list-group-item-action d-flex align-items-center">
                <i class="bi bi-geo-alt me-3"></i> <spring:message code="admin.stopsList.heading"/>
            </a>
            <a href="<c:url value="/admin/users" />" class="list-group-item list-group-item-action d-flex align-items-center">
                <i class="bi bi-people me-3"></i> <spring:message code="admin.user.heading" />
            </a>
        </div>
    </div>

    </body>
</html>

