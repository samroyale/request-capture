<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<ul class="breadcrumb">
  <c:forEach var="breadcrumb" items="${breadcrumbs.links}">
    <li><a href="${breadcrumb.target}"><c:out value="${breadcrumb.text}" /></a> <span class="divider">/</span></li>
  </c:forEach>
  <li class="active"><c:out value="${pageTitle}" /></li>
</ul>
