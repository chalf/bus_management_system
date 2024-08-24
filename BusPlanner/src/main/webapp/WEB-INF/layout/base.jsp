<%-- 
    Document   : base
    Created on : Aug 15, 2024, 12:06:46 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><tiles:getAsString name="title"/></title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
</head>
<body>
    <header>
        <tiles:insertAttribute name="header" />
    </header>
    
    <main>
        <tiles:insertAttribute name="content" />
    </main>
    
    <footer>
        <tiles:insertAttribute name="footer" />
    </footer>
</body>
</html>
