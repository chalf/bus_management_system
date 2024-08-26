<%-- 
    Document   : header
    Created on : Aug 15, 2024, 12:09:42 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Latest compiled and minified CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
// Initialization for ES Users
    import { Collapse, Ripple, initMDB } from "mdb-ui-kit";

    initMDB({Collapse, Ripple});
</script>

<header>
    <!-- Intro settings -->
    <style>
        /* Default height for small devices */
        #intro-example {
            height: 400px;
        }

        /* Height for devices larger than 992px */
        @media (min-width: 992px) {
            #intro-example {
                height: 600px;
            }
        }
        .user-actions {
            font-weight: bold; /* Make the text bold */
            color: #007bff; /* Change the text color */
        }

        .user-actions:hover {
            color: #0056b3; /* Change color on hover */
        </style>

        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg navbar-light bg-white">
            <div class="container-fluid">
                <button
                    data-mdb-collapse-init
                    class="navbar-toggler"
                    type="button"
                    data-mdb-target="#navbarExample01"
                    aria-controls="navbarExample01"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                    >
                    <i class="fas fa-bars"></i>
                </button>
                <div class="collapse navbar-collapse" id="navbarExample01">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item active">
                            <a class="nav-link" aria-current="page" href="<c:url value="/admin" />">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Features</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Pricing</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">About</a>
                        </li>
                    </ul>
                    <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                        <c:choose>
                            <c:when test="${pageContext.request.userPrincipal.name == null}">
                                <li class="nav-item">
                                    <a class="nav-link user-actions" href="<c:url value="/" />">Login</a>
                                </li> 
                            </c:when>
                            <c:when test="${pageContext.request.userPrincipal.name != null}">
                                <li class="nav-item">
                                    <a class="nav-link user-actions" href="#">${pageContext.request.userPrincipal.name}</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link user-actions" href="<c:url value="/logout" />">Logout</a>
                                </li>
                            </c:when>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Navbar -->
    </header>