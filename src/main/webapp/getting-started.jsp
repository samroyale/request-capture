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
      function showResponse(response) {
      	$('#response').html('<pre>' + response + '</pre>');
      }

      $(document).on('click', '#eg-get', function(e) {
      	e.preventDefault();
      	$.ajax({
          url: '/capture/start/get/request',
          success: function(response) {
          	showResponse(response);
          }
        });
      });

      $(document).on('click', '#eg-post', function(e) {
      	e.preventDefault();
      	$.ajax({
      	  type: 'POST',
          url: '/capture/start/post/request',
          data: JSON.stringify({ Message: 'This POST request has been captured' }),
          contentType: 'application/json',
          dataType: 'text',
          success: function(response) {
          	showResponse(response);
          }
        });
      });

      $(document).on('click', '#eg-put', function(e) {
      	e.preventDefault();
      	$.ajax({
      	  type: "PUT",
          url: '/capture/start/put/request',
          data: JSON.stringify({ Message: 'This PUT request has been captured' }),
          contentType: 'application/json',
          dataType: 'text',
          success: function(response) {
          	showResponse(response);
          }
        });
      });
    </script>
  </head>

  <body>
    <div class="container">
      <jsp:include page="breadcrumbs.jsp"/>

      <h1 class="page-header"><c:out value="${pageTitle}" /></h1>

      <p>Any requests you send to <i>/capture/[tag]/...</i> will be stored and can then be viewed.  This is useful for API's that accept a callback URL and send back their response asynchronously.</p>

      <p class="with-break">For example, give the following requests a try and see them captured under the <a href="/list?tag=start&bc=h%2Cs">start</a> tag:</p>
      <ul>
      	<li class="padded">GET: <a href="#" id="eg-get">/capture/start/get/request</a></li>
      	<li class="padded">POST: <a href="#" id="eg-post">/capture/start/post/request</a></li>
      	<li class="padded">PUT: <a href="#" id="eg-put">/capture/start/put/request</a></li>
      </ul>

      <p class="with-break">Alternatively, grab the key from the response below (it's the bit that looks like <i>xxxx/start</i>) and use it to find the request by key:</p>
      <div id="response"></div>
    </div>
  </body>
</html>
