<%-- 
    Document   : admin
    Created on : Aug 21, 2024, 11:13:00 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>">
    </head>
    <body>

        <h2><spring:message code="webapp.admin.title"/></h2>

        <c:if test="${param.error != null}">
            <div class="alert alert-danger">
                <!-- nếu có lỗi thì xuất ra message user.login.error  -->
                <spring:message code="webapp.login.error" />
            </div>
        </c:if>

        <spring:url value="/" var="adminLogin"/>
        <form action="${adminLogin}" method="post" id="loginForm">
            <div class="imgcontainer">
                <img src="<c:url value="/images/login.jpg"/>" alt="Avatar" class="avatar">
            </div>

            <div class="container">
                <label for="uname"><b><spring:message code="webapp.admin.username"/></b></label>
                <input type="text" placeholder="Enter Username" id="uname" name="username" required>

                <label for="psw"><b><spring:message code="webapp.admin.password"/></b></label>
                <input type="password" placeholder="Enter Password" id="psw" name="password" required>

                <button type="submit"><spring:message code="webapp.admin.loginButton"/></button>
                <label>
                    <input type="checkbox" checked="checked" name="remember"> Remember me
                </label>
            </div>

            <div class="container" style="background-color:#f1f1f1">
                <button type="button" class="cancelbtn" onclick="clearForm()">Cancel</button>
                <span class="psw">Forgot <a href="#">password?</a></span>
            </div>
        </form>
        <script>
            function clearForm() {
                var form = document.getElementById("loginForm");
                form.reset(); // Reset the form fields
            }
        </script>

    </body>
</html>
