<%-- 
    Document   : bus
    Created on : Aug 24, 2024, 10:00:54 PM
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh sách Buses</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <!-- Include Header -->
        <jsp:include page="/WEB-INF/layout/header.jsp" />

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
                        <th>Route ID</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="bus" items="${buses}">
                        <tr>
                            <td><c:out value="${bus.busId}"/></td>
                            <td><c:out value="${bus.busNumber}"/></td>
                            <td><c:out value="${bus.routeId.routeId}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </main>

        <!-- Include Footer -->
        <jsp:include page="/WEB-INF/layout/footer.jsp" />

        <!-- Bootstrap JS Bundle -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>


