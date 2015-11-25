<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page>
  <jsp:attribute name="body">
    <c:set var="participant" value="${currentUser.spouse}"/>
    <%@include file="includes/dashboard.jspf" %>
  </jsp:attribute>
  <jsp:attribute name="scripts">
    <script type="text/javascript">
      $(function($){
        var href = $("#account-link").attr("href") + "?emp=false";
        $("#account-link").attr("href", href)
      });
    </script>
  </jsp:attribute>
</t:page>

<%@include file="includes/confirm.jspf" %>