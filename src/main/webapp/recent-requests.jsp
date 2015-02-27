<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="table table-bordered table-requests">
  <tbody>
  <c:forEach var="capturedRequest" items="${requests}">
    <tr>
      <td class="method"><c:out value="${capturedRequest.method}" /></td>
      <td class="url"><a href="/detail?id=${capturedRequest.id}&tag=${capturedRequest.friendlyTag}&bc=${breadcrumbs.urlFormat}"><c:out value="${capturedRequest.url}" /></a></td>
      <td class="timestamp"><c:out value="${capturedRequest.timestamp}" /></td>
    </tr>
  </c:forEach>
  </tbody>
</table>
