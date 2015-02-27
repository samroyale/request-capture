<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <title>500 Error</title>
    <link type="text/css" rel="stylesheet" href="/css/bootstrap.min.css"></link>
    <link type="text/css" rel="stylesheet" href="/css/app.extras.css"></link>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"></meta>
  </head>

  <body>
    <div class="container">
      <ul class="breadcrumb">
          <li><a href="/">Home</a></li>
      </ul>
      <h1 class="page-header">500 Error</h1>
      <p class="with-gap">Something bad happened...</p>
      <p><c:out value="${pageContext.exception.message}" /></p>
    </div>
  </body>
</html>
