<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page>
  <jsp:attribute name="body">
    <c:set var="participant" value="${currentUser}"/>
    <%@include file="includes/dashboard.jspf" %>
  </jsp:attribute>
  <jsp:attribute name="scripts">
    <script src='<c:url value="/js/jquery.badges.js"/>' type="text/javascript"></script>  
    <script type="text/javascript">
      $(function() {
        $("#badge-container").badges({url: 'badges.json?employeeId=${currentUser.id}&challengeYear=${challengeYear}'});
      });
    </script>
    <%@include file="includes/confirm.jspf" %>
  </jsp:attribute>
</t:page>