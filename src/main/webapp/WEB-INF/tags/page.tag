<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Page Layout" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@attribute name="title" fragment="true" %>
<%@attribute name="css" fragment="true" %>
<%@attribute name="body" fragment="true" %>
<%@attribute name="scripts" fragment="true" %>

<t:basic-layout>
  <jsp:attribute name="defaultTitle">
    <c:choose>
      <c:when test="${title == null}">
        ISP Colonel's Challenge
      </c:when>
      <c:otherwise>
        <jsp:invoke fragment="title" />
      </c:otherwise>
    </c:choose>
  </jsp:attribute>

  <jsp:attribute name="head">
    <%@include file="../views/includes/css.jspf" %>
    <jsp:invoke fragment="css" />
  </jsp:attribute>

  <jsp:body>
    <c:if test="${challengeYear != null && !challengeYear.current}">
      <span id="banner"><a href="<c:url value="/dashboard.html"/>?challengeYear=${challengeYear}">Colonel's Challenge ${challengeYear.label}</a></span>
    </c:if>
    
    <%@include file="../views/includes/navbar.jspf" %>
    
    <div id="main-container" class="container">
      <%@include file="../views/includes/messages.jspf" %>
      <jsp:invoke fragment="body" />
    </div>

    <%@include file="../views/includes/javascript-links.jspf" %>  
    <jsp:invoke fragment="scripts" />
  </jsp:body>
</t:basic-layout>