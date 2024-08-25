<%-- 
    Document   : admin
    Created on : Aug 23, 2024, 12:06:35 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="<c:url value="/css/admin.css"/>">
    <title><spring:message code="admin.dashboard.title"/></title>
</head>
<body>
    <h2><spring:message code="admin.dashboard.heading"/></h2>
    
    <a href="<c:url value='/admin/stops'/>"><button type="button"><spring:message code="admin.dashboard.manageStops"/></button></a>
    <!-- Bạn có thể thêm các liên kết khác cho các tài nguyên khác ở đây -->

</body>
</html>
