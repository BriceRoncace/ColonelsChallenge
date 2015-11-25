<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page>
  <jsp:attribute name="body">
    <h3>Uh Oh!</h3>
    <p>Looks like you broke something.  If you want to see the details you can <a href="javascript://nop/" id="stacktrace-link">view the stack trace</a>, otherwise, go home and try again.</p>
    
    <div id="stacktrace" class="well hide">
      <c:forEach var="i" begin="1" end="10">
        <c:if test="${exception != null}">
          <%@include file="includes/exception.jspf" %>
          <c:set var="exception" value="${exception.cause}"/>
          <hr/>
        </c:if>
      </c:forEach>
    <div>
  </jsp:attribute>
  <jsp:attribute name="scripts">
    <script type="text/javascript">
      $(function() {
        $("#stacktrace-link").click(function() {
          $("#stacktrace").toggleClass("hide");
        });
      });
    </script>
  </jsp:attribute>
</t:page>  