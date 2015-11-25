<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page>
  <jsp:attribute name="body">
    <h3>Employee Results</h3>

    <a href="<c:url value="/spouseResults.html"/>?challengeYear=${challengeYear}">View Spouse Results</a>
    
    <table class="table table-hover table-condensed col-lg-12 data-table-with-tools">
      <thead>
        <tr>
          <th>First Name</th>
          <th>Last Name</th>
          <th>District</th>
          <th>Dept</th>
          <th>Commissioned</th>
          <th>Sex</th>
          <th>Goals Met</th>
          <th>Aerobic (hours)</th>
          <th>Aerobic Goal Met</th>
          <th>Run (miles)</th>
          <th>Bike (miles)</th>
          <th>Swim (miles)</th>
          <th>Resistance (lbs)</th>
          <th>Resistance Goal Met</th>
          <th>Situps / Pushups (reps)</th>
          <th>S/P Goal Met</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="p" items="${participants}">
        <tr>
          <td>${p.firstName}</td>
          <td>${p.lastName}</td>
          <td>${p.district.label}</td>
          <td>${p.department}</td>
          <td>${p.commissioned ? 'Yes' :'No'}</td>
          <td>${p.gender}</td>
          <td>${p.getNumberOfGoalsReached(challengeYear)}</td>
          <td>${p.getTotalAerobicHours(challengeYear)}</td>
          <td>${p.getDidCompleteAerobicHoursGoal(challengeYear) ? 'Yes' : 'No'}</td>
          <td>${p.getTotalRunDistance(challengeYear)}</td>
          <td>${p.getTotalBikeDistance(challengeYear)}</td>
          <td>${p.getTotalSwimDistance(challengeYear)}</td>
          <td><fmt:formatNumber value="${p.getTotalWeight(challengeYear)}"/></td>
          <td>${p.getDidCompleteWeightGoal(challengeYear) ? 'Yes' : 'No'}</td>
          <td><fmt:formatNumber value="${p.getTotalSitupsPushups(challengeYear)}"/></td>
          <td>${p.getDidCompleteSitupsPushupsGoal(challengeYear) ? 'Yes' : 'No'}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    
  </jsp:attribute>
</t:page>  