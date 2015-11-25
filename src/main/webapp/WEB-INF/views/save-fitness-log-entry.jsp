<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page>
  <jsp:attribute name="body">
    <h3>${entry.id != null ? 'Edit' : 'Save'} Training Entry</h3>
    
    <hr/>
    <form action="<c:url value="/saveEntry.html"/>" method="POST" role="form">
      <input type="hidden" name="id" value="${entry.id}"/>
      <input type="hidden" name="employee.id" value="${currentUser.id}"/>
      <input type="hidden" name="spouse.id" value="${currentUser.spouse.id}"/>
      <input type="hidden" name="challengeYear" value="${challengeYear.getCurrentYear()}"/>

      <div class="form-group row"> 
        <div class="col-sm-4">
          <label for="activityDate" class="required">Date</label>
          <jsp:useBean id="dateValue" class="java.util.Date" />
          <c:if test="${entry.activityDate != null}"><jsp:setProperty name="dateValue" property="time" value="${entry.activityDateEpochMilli}" /></c:if>
          <t:datepicker pickerType="date" id="activityDate" name="activityDate" value="${dateValue}" />
        </div>

        <div class="col-sm-4">
          <label for="title">Title/Description</label>
          <input type="text" class="form-control" id="title" name="title" value="${entry.title}">
        </div>
        
        <div class="col-sm-4">
          <label for="title">Training for</label>
          <c:choose>
            <c:when test="${currentUser.spouse == null}">
              <div class="input-group">
                <c:out value="${currentUser.firstName}"/> <c:out value="${currentUser.lastName}"/>
                <input type="hidden" name="forSpouse" value="false"/>
              </div>
            </c:when>
            <c:otherwise>
              <div class="input-group">
                <label class="radio-inline">
                  <input type="radio" name="forSpouse" id="gender-male" value="false" ${forSpouse == false || param.emp ? 'checked' : ''}> <c:out value="${currentUser.firstName}"/> <c:out value="${currentUser.lastName}"/>
                </label>
                <label class="radio-inline">
                  <input type="radio" name="forSpouse" id="gender-female" value="true" ${forSpouse == true || param.emp == false ? 'checked' : ''}> <c:out value="${currentUser.spouse.firstName}"/> <c:out value="${currentUser.spouse.lastName}"/>
                </label>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
        
      <div class="form-group row">  
        <div class="col-sm-12">
          <label for="notes">Notes</label>
          <textarea class="form-control" id="notes" name="notes">${entry.notes}</textarea>
        </div>
      </div>
      
      <div class="form-group row">  
        <div class="col-sm-3">
          <label for="exerciseType" class="required">Type</label>
          <t:select from="${exerciseTypes}" emptyOption="" cssClass="form-control" optionValue="label" name="exerciseType" value="${entry.exerciseType}" id="exerciseType" />
        </div>
        
        <div id="areobic-fields" class="extra-entry-fields">
          <div class="col-sm-3">
            <label for="category">Category</label>
            <t:select from="${cardioTypes}" emptyOption="" cssClass="form-control" optionValue="label" name="cardioType" value="${entry.cardioType}" id="category" />
          </div>
          
          <div class="col-sm-3">
            <label for="time">Time (hh:mm:ss)</label>
            <div class="input-group">
              <input type="text" class="form-control" id="hours" name="hours" placeholder="hh" value="${entry.hours}"/><span class="input-group-addon">:</span>
              <input type="text" class="form-control" name="minutes" placeholder="mm" value="${entry.minutes}"/><span class="input-group-addon">:</span>
              <input type="text" class="form-control" name="seconds" placeholder="ss" value="${entry.seconds}"/>
            </div>
          </div>
            
          <div class="col-sm-3">
            <label for="distance">Distance (miles)</label>
            <input type="text" class="form-control" id="distance" name="distance" value="${entry.distance}">
          </div>
        </div>
      
        <div id="resistance-fields" class="extra-entry-fields">
          <div class="col-sm-3">
            <label for="weight">Total Weight (lbs)</label>
            <input type="text" class="form-control" id="weight" name="totalWeight" value="${entry.totalWeight}">
          </div>
        </div>
        
        <div id="situp-pushup-fields" class="extra-entry-fields">
          <div class="col-sm-3">
            <label for="reps">Reps</label>
            <input type="text" class="form-control" id="reps" name="reps" value="${entry.reps}">
          </div>
        </div>
      </div>    
          
      <div class="form-group row">  
        <div class="col-sm-3">
          <a href="<c:url value="/dashboard.html"/>" class="btn btn-default">Cancel</a>
          <input id="save-btn" type="submit" class="btn btn-warning" value="Save"/>
        </div>
      </div>
          
      <div class="modal" id="hour-check-dialog" tabindex="-1" role="dialog" aria-labelledby="hourCheckDialog" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-body">
              Did you mean to enter <span style="font-weight:bold" id="hour-verify"></span> hours?
            </div>
            <div class="modal-footer">
              <input type="hidden" id="confirm-href">
              <button class="btn btn-warning" type="submit">Yes</button>
              <button class="btn" data-dismiss="modal" aria-hidden="true">No</button>
            </div>
          </div>
        </div>
      </div>       
          
    </form>
  </jsp:attribute>
  <jsp:attribute name="scripts">
    <script type="text/javascript">
      $(function () {
        hideExtraFields();
        showExtraFieldsBasedForType($("#exerciseType").val());
       
        $("#exerciseType").change(function() {
          hideExtraFields();
          showExtraFieldsBasedForType($(this).val());
        });
        
        $("#save-btn").click(submitTraining);
      });
      
      function submitTraining(e) {
        $(".extra-entry-fields:hidden").find("input").val("");    
        hourValueCheck(e);
      }
      
      function hourValueCheck(e) {
        if ($("#hours").val() > 1) {
          e.preventDefault();
          $("#hour-verify").html($("#hours").val());
          $('#hour-check-dialog').modal();
        }
      }
      
      function showExtraFieldsBasedForType(type) {
        if (type === 'AEROBIC') {
          $("#areobic-fields").show();
        }
        else if (type === 'SITUPS_PUSHUPS') {
          $("#situp-pushup-fields").show();
        }
        else if (type === 'RESISTANCE') {
          $("#resistance-fields").show();
        }
      }
      
      function hideExtraFields() {
        $(".extra-entry-fields").hide();
      }
    </script>
  </jsp:attribute>  
</t:page>
    
  