<%-- 
    Document   : welcome
    Created on : Aug 24, 2024, 9:34:19 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container">
    <h1>Chào mừng đến với hệ thống quản lý Bus Planner</h1>
    <p>Chọn một mục để quản lý dữ liệu:</p>
    <ul>
        <li><a href="<c:url value='/buses'/>">Quản lý Buses</a></li>
        <li><a href="<c:url value='/favoriteroutes'/>">Quản lý Favorite Routes</a></li>
        <li><a href="<c:url value='/routes'/>">Quản lý Routes</a></li>
        <li><a href="<c:url value='/routestops'/>">Quản lý Route Stops</a></li>
        <li><a href="<c:url value='/schedules'/>">Quản lý Schedules</a></li>
        <li><a href="<c:url value='/stops'/>">Quản lý Stops</a></li>
        <li><a href="<c:url value='/trafficreports'/>">Quản lý Traffic Reports</a></li>
        <li><a href="<c:url value='/users'/>">Quản lý Users</a></li>
    </ul>
</div>

