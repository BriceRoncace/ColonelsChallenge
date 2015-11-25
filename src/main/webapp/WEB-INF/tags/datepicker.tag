<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="pickerType" required="true" type="java.lang.String" %>
<%@attribute name="id" required="true" type="java.lang.String" %>
<%@attribute name="name" required="true" type="java.lang.String" %>
<%@attribute name="extraClass" required="false" type="java.lang.String" %>
<%@attribute name="value" required="false" type="java.util.Date" %>
<%@attribute name="placeHolder" required="false" type="java.lang.String" %>

<c:choose>
  <c:when test="${pickerType == 'date'}">
    <input id="${id}" class="datePicker form-control ${extraClass}" type="text" value='<fmt:formatDate value="${value}" pattern="${dateFormat}" />' name="${name}" placeholder="${placeHolder}"/>
  </c:when>
  <c:when test="${pickerType == 'time'}">
    <input id="${id}" class="timePicker form-control ${extraClass}" type="text" value='<fmt:formatDate value="${value}" pattern="${timeFormat}" />' name="${name}" placeholder="${placeHolder}"/>
  </c:when>
  <c:when test="${pickerType == 'dateTime'}">
    <input id="${id}" class="dateTimePicker form-control ${extraClass}" type="text" value='<fmt:formatDate value="${value}" pattern="${dateTimeFormat}" />' name="${name}" placeholder="${placeHolder}"/>
  </c:when>
</c:choose>