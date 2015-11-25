<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page>
  <jsp:attribute name="body">
    <h3>Fitness Log Entry</h3>
    <hr/>
    <form role="form">
      <input type="hidden" name="entryId" value="${entry.id}"/>
      
      <div class="form-group row">  
        <div class="col-sm-4">
          <label for="activityDate">Date</label>
          <div class="input-group">
            <fmt:formatDate value="${entry.activityDateAsUtilDate}" pattern="${dateFormat}" />
          </div>
        </div>

        <div class="col-sm-4">
          <label for="title">Title/Description</label>
          <div class="input-group">
            <c:out value="${entry.title}"/>
          </div>
        </div>
          
        <div class="col-sm-4">
          <label for="title">Training for</label>
          <div class="input-group">
            <c:choose>
              <c:when test="${entry.belongsToEmployee}">
                <c:out value="${entry.employee.firstName}"/> <c:out value="${entry.employee.lastName}"/>
              </c:when>
              <c:otherwise>
                <c:out value="${entry.spouse.firstName}"/> <c:out value="${entry.spouse.lastName}"/>
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
        
      <div class="form-group row">  
        <div class="col-sm-12">
          <label for="notes">Notes</label>
          <textarea class="form-control" id="notes" readonly="true" name="notes"><c:out value="${entry.notes}"/></textarea>
        </div>
      </div>
      
      <div class="form-group row">
        <div class="col-sm-3">
          <label>Type</label>
          <div class="input-group">
            ${entry.exerciseType.label}
          </div>
        </div>

        <c:choose>
          <c:when test="${entry.exerciseType == 'AEROBIC'}">
            <div class="col-sm-3">
              <label>Category</label>
              <div class="input-group">
                ${entry.cardioType.label}
              </div>
            </div>

            <div class="col-sm-3">
              <label>Time</label>
              <div class="input-group">
                ${entry.formattedTime}
              </div>
            </div>

            <div class="col-sm-3">
              <label>Distance (miles)</label>
              <div class="input-group">
                ${entry.distance}
              </div>
            </div>
          </c:when>
          <c:when test="${entry.exerciseType == 'SITUPS_PUSHUPS'}">
            <div class="col-sm-3">
              <label for="distance">Reps</label>
              <div class="input-group">
                ${entry.reps}
              </div>
            </div>
          </c:when>
          <c:when test="${entry.exerciseType == 'RESISTANCE'}">
            <div class="col-sm-3">
              <label>Total Weight (lbs)</label>
              <div class="input-group">
                ${entry.totalWeight}
              </div>
            </div>
          </c:when>
        </c:choose>
      </div>
          
      <div class="form-group row">  
        <div class="col-sm-3">
          <c:choose>
            <c:when test="${entry.belongsToEmployee}">
              <a href="<c:url value="/dashboard.html"/>" class="btn btn-default"><span class="glyphicon glyphicon-arrow-left"></span>&nbsp; Return to Dashboard</a>
            </c:when>
            <c:otherwise>
              <a href="<c:url value="/spouseDashboard.html"/>" class="btn btn-default"><span class="glyphicon glyphicon-arrow-left"></span>&nbsp; Return to Dashboard</a>
            </c:otherwise>
          </c:choose>
          <a href="<c:url value="/editEntry.html"/>?id=${entry.id}" class="btn btn-warning">Edit</a>  
        </div>
      </div>    
    </form>
  </jsp:attribute>
</t:page>  