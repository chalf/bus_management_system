<%-- 
    Document   : admin_add_stop
    Created on : Aug 25, 2024, 10:38:43 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="<c:url value="/css/admin.css"/>">
    <title><spring:message code="admin.addStop.title"/></title>
</head>
<body>
    <h2><spring:message code="admin.addStop.heading"/></h2>

    <form:form method="post" action="/admin/addStop" modelAttribute="stop">
        <form:label path="stopName"><spring:message code="admin.addStop.stopName"/></form:label>
        <form:input path="stopName" type="text" required="true" />

        <form:label path="latitude"><spring:message code="admin.addStop.latitude"/></form:label>
        <form:input path="latitude" type="text" required="true" />

        <form:label path="longitude"><spring:message code="admin.addStop.longitude"/></form:label>
        <form:input path="longitude" type="text" required="true" />

        <form:label path="address"><spring:message code="admin.addStop.address"/></form:label>
        <form:input path="address" type="text" required="true" />

        <button type="submit"><spring:message code="admin.addStop.submitButton"/></button>
    </form:form>
</body>
</html>
