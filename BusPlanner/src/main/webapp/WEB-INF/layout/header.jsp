<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Latest compiled and minified CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<header>
  <!-- Navbar -->
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="<c:url value='/home'/>">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/buses'/>">Buses</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/favoriteroutes'/>">Favorite Routes</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/routes'/>">Routes</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/routestops'/>">Route Stops</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/schedules'/>">Schedules</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/stops'/>">Stops</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/trafficreports'/>">Traffic Reports</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/users'/>">Users</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <!-- Navbar -->
</header>
