<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <title>404 Error</title>
    <link type="text/css" rel="stylesheet" href="/css/bootstrap.min.css"></link>
    <link type="text/css" rel="stylesheet" href="/css/app.extras.css"></link>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"></meta>
  </head>

  <body>
    <div class="container">
      <ul class="breadcrumb">
          <li><a href="/">Home</a></li>
      </ul>
      <h1 class="page-header">404 Error</h1>
      <p class="with-gap">Not found...</p>
      <p><c:out value="${requestScope['javax.servlet.error.message']}" /></p>
    </div>
  </body>
</html>
