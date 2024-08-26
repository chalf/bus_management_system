<%-- 
    Document   : bus
    Created on : Aug 24, 2024, 10:00:54 PM
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<main class="container mt-4">
    <h1 class="mb-4"><spring:message code="admin.bus.list"/></h1>
    <c:if test="${errorMessage != null}">
        <div class="alert alert-danger">
            ${errorMessage}
        </div>
    </c:if>
    <c:url value="/admin/buses" var="postBus" />
    <form:form action="${postBus}" method="post" modelAttribute="bus">
        <div class="mb-3 mt-3">
            <label for="busNumber" class="form-label"><spring:message code="admin.bus.busNumber"/></label>
            <form:input cssClass="form-control" id="busNumber" path="busNumber" />
        </div>
        <div class="mb-3 mt-3">
            <label for="browser"><spring:message code="admin.bus.routeId"/></label>
            <form:input cssClass="form-control" list="browsers" id="browser" path="routeId"/>
            <datalist id="browsers">
                <c:forEach items="${routes}" var="route">
                    <option value="${route.routeId}">${route.startPoint} - ${route.endPoint}</option>
                </c:forEach>
            </datalist>
        </div>
        <button type="submit" class="btn btn-primary"><spring:message code="admin.bus.add"/></button>
    </form:form>

    <!-- Table to display buses -->
    <h1 style="padding-top: 20px">Bus List</h1>
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


