<%@tag description="Basic Page Layout" pageEncoding="UTF-8"%>
<%@attribute name="head" fragment="true" %>
<%@attribute name="defaultTitle" fragment="true" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title><jsp:invoke fragment="defaultTitle" /></title>
    <jsp:invoke fragment="head" />
  </head>
  <body>
    <jsp:doBody />
  </body>
</html>