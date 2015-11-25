<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page>
  <jsp:attribute name="body">
    <h3>Results</h3>

    <a href='#'>View Spouse Results</a>
    
    <table class="table table-hover table-condensed col-lg-12 data-table">
      <thead>
        <tr>
          <th>First Name</th>
          <th>Last Name</th>
          <th>District</th>
          <th>Department</th>
          <th>Commissioned</th>
          <th>Time</th>
          <th>Distance</th>
          <th>Weight</th>
          <th>Reps</th>
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
          <td>${p.getTotalAerobicHours()}</td>
          <td>${p.getTotalDistance()}</td>
          <td>${p.getTotalWeight()}</td>
          <td>${p.getTotalSitupsPushups()}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    
  </jsp:attribute>
</t:page>  