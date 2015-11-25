<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page>
  <jsp:attribute name="body">
    <h3>Account Setup</h3>
    Please enter your starting weight and gender so that a resistance goal may be calculated for you.  The formula used to determine the target weight lifted over the 10 week challenge is <strong>1,500 times weight for males</strong> and <strong>1,000 times weight for females</strong>.
    <hr/>
    <form action="<c:url value="/completeSetup.html"/>" method="POST" role="form">
      <input type="hidden" name="employeeId" value="${currentUser.id}"/>

      <div class="form-group row">  
        <div class="col-sm-3">
          <label>Name</label>
          <div class="input-group">
            <c:out value="${currentUser.firstName}"/> <c:out value="${currentUser.lastName}"/> 
          </div>
        </div>

        <div class="col-sm-3">
          <label>Title/Description</label>
          <div class="input-group">
            <c:out value="${currentUser.title}"/>
          </div>
        </div>
          
        <div class="col-sm-2">
          <label>District</label>
          <div class="input-group">
            <c:out value="${currentUser.district.label}"/>
          </div>
        </div>
          
        <div class="col-sm-2">
          <label>Department</label>
          <div class="input-group">
            <c:out value="${currentUser.department}"/>
          </div>
        </div>  
          
        <div class="col-sm-2">
          <label>Commissioned</label>
          <div class="input-group">
            <c:out value="${currentUser.commissioned ? 'Yes' : 'No'}"/>
          </div>
        </div>  
      </div>
      
      <hr/>
      
      <div class="form-group row"> 
        <div class="col-sm-2">
          <label class="required">Starting Weight</label>
          <input type="text" class="form-control" id="weight" name="weight" value="${currentUser.startingBodyWeight}">
        </div>
     
        <div class="col-sm-2">
          <label class="required">Gender</label>
          <div>
            <label class="radio-inline">
              <input type="radio" name="gender" id="gender-male" value="M" ${currentUser.gender == 'M' ? 'checked' : ''}> Male
            </label>
            <label class="radio-inline">
              <input type="radio" name="gender" id="gender-female" value="F" ${currentUser.gender == 'F' ? 'checked' : ''}> Female
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