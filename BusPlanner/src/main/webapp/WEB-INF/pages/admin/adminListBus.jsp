<%-- 
    Document   : bus
    Created on : Aug 24, 2024, 10:00:54 PM
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<main class="container mt-4">
    <h1 class="mb-4">Danh sách Buses</h1>

    <!-- Button to add a new bus -->
    <a href="<c:url value='/buses/edit/0'/>" class="btn btn-primary mb-3">Thêm mới Bus</a>

    <!-- Table to display buses -->
    <h1>Bus List</h1>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Bus Number</th>
                <th>Route Name</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="bus" items="${buses}">
                <tr>
                    <td><c:out value="${bus.busId}"/></td>
                    <td><c:out value="${bus.busNumber}"/></td>
                    <td><c:out value="${bus.routeId.routeName}"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</main>


<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>


