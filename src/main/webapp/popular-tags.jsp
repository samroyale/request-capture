<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<c:forEach var="tagCount" items="${tagCounts}">
  <p><a href="/list?tag=${tagCount.tag}"><c:out value="${tagCount.tag}" /></a> : <c:out value="${tagCount.count}" /></p>
</c:forEach>
