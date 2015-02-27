<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<html>
  <head>
    <title><c:out value="${pageTitle}" /></title>
    <link type="text/css" rel="stylesheet" href="/css/bootstrap.min.css"></link>
    <link type="text/css" rel="stylesheet" href="/css/app.extras.css"></link>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"></meta>
  </head>

  <body>
    <div class="container">
      <jsp:include page="breadcrumbs.jsp"/>
      
      <h1 class="page-header"><c:out value="${pageTitle}" /></h1>
      
      <h2 class="request-summary"><c:out value="${capturedRequest.method}" /> <c:out value="${capturedRequest.url}" /></h2>
      
      <h3>Headers</h2>
      <ul>
        <c:forEach var="myHeader" items="${capturedRequest.headers}">
          <li><c:out value="${myHeader.key}" />: <c:out value="${myHeader.value}" /></li>
        </c:forEach>
      </ul>
      
      <h3>Body</h2>
      <pre><c:out value="${capturedRequest.body}" /></pre>
    </div>
  </body>
</html>