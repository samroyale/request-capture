<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <title><c:out value="${pageTitle}" /></title>
    <link type="text/css" rel="stylesheet" href="/css/bootstrap.min.css"></link>
    <link type="text/css" rel="stylesheet" href="/css/app.extras.css"></link>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"></meta>
    <script type="text/javascript" src="/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript">
      function refreshRequests() {
        $.ajax({
          url: '${recentRequestsUrl}${breadcrumbs.urlFormat}',
          success: function(response) {
            // update requests element
            $('#requests').html(response);
            applyStripes();
          }
        });
      }

      function applyStripes() {
        $("table tr:nth-child(odd)").addClass("odd");
      }

      $(function() {
        refreshRequests();
        setInterval(refreshRequests, 10000);
      });
    </script>
  </head>

  <body>
    <div class="container">
      <jsp:include page="breadcrumbs.jsp"/>

      <h1 class="page-header"><c:out value="${pageTitle}" /></h1>

      <div id="requests"></div>
    </div>
  </body>
</html>
