<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page>
  <jsp:attribute name="body">
    <h3>Setup Spouse</h3>
    You may track your spouse's progress too.  Begin by filling out the information below.  Once this information has been saved, you will be able to enter training for your spouse.
    <hr/>
    <c:set var="formAction" value="${currentUser.spouse.id != null ? '/updateSpouse.html' : '/saveSpouse.html'}"/>
    <form action="<c:url value="${formAction}"/>" method="POST" role="form">
      <input type="hidden" name="employeeId" value="${currentUser.id}"/>
      <input type="hidden" name="spouseId" value="${currentUser.spouse.id}"/>

      <div class="form-group row">  
        <div class="col-sm-2">
          <label class="required">First Name</label>
          <div class="input-group">
            <input type="text" class="form-control" id="firstName" name="firstName" value="${currentUser.spouse.firstName}">
          </div>
        </div>
          
        <div class="col-sm-2">
          <label class="required">Last Name</label>
          <div class="input-group">
            <input type="text" class="form-control" id="lastName" name="lastName" value="${currentUser.spouse != null ? currentUser.spouse.lastName : currentUser.lastName}">
          </div>
        </div>  
          
        <div class="col-sm-2">
          <label class="required">Starting Weight</label>
          <input type="text" class="form-control" id="weight" name="startingBodyWeight" value="${currentUser.spouse.startingBodyWeight}">
        </div>
     
        <div class="col-sm-2">
          <label class="required">Gender</label>
          <div>
            <label class="radio-inline">
              <input type="radio" name="gender" id="gender-male" value="M" ${currentUser.spouse.gender == 'M' ? 'checked' : ''}> Male
            </label>
            <label class="radio-inline">
              <input type="radio" name="gender" id="gender-female" value="F" ${currentUser.spouse.gender == 'F' ? 'checked' : ''}> Female
            </label>
          </div>
        </div>
            
        <div class="col-sm-2">
          <br/>
          <input type="submit" class="btn btn-warning" value="Save"/>
        </div>
        
      </div>
    </form>
  </jsp:attribute>
</t:page>  