<%-- 
    Document   : users
    Created on : Aug 12, 2024, 9:40:27 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!--<h1>Hello</h1>-->
        <ol>
            
                <li>${user.userId}</li>
                <li>${user.username}</li>
                <li>${user.fullName}</li>
                <li>${user.email}</li>
                <li>${user.avatarUrl}</li>
            
        </ol>
    </body>
</html>
