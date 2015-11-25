<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Outputs select html element" pageEncoding="UTF-8"%>

<%@attribute name="name" required="true" type="java.lang.String" %>
<%@attribute name="from" required="true" type="java.lang.Iterable" %>
<%@attribute name="cssClass" required="false" type="java.lang.String" %>
<%@attribute name="emptyOption" required="false" type="java.lang.String" %>
<%@attribute name="multiple" required="false" type="java.lang.Boolean" %>
<%@attribute name="size" required="false" type="java.lang.Integer" %>
<%@attribute name="id" required="false" type="java.lang.String" %>
<%@attribute name="value" required="false" type="java.lang.Object" %>
<%@attribute name="optionKey" required="false" type="java.lang.String" %>
<%@attribute name="optionValue" required="false" type="java.lang.String" %>

<select <c:if test="${multiple != null}">multiple</c:if> 
        <c:if test="${size != null}">size="${size}"</c:if> 
        <c:if test="${id != null}">id="${id}"</c:if> 
        name="${name}" 
        <c:if test="${cssClass != null}">class="${cssClass}"</c:if>>
  <c:if test="${emptyOption != null}"><option value="">${emptyOption}</option></c:if>
  <c:forEach var="item" items="${from}">
    <%
      if (shouldSelect(value, jspContext.getAttribute("item"))) {
        jspContext.setAttribute("selected", true);
      }
      else {
        jspContext.setAttribute("selected", false);
      }
    %>
    <option <c:if test="${selected}">selected</c:if> value="${optionKey != null ? item[optionKey] : item}">${optionValue != null ? item[optionValue] : item}</option>
  </c:forEach>
</select>
    
<%!
private boolean shouldSelect(Object selectedValues, Object value) {
  if (selectedValues != null) {
    if (selectedValues instanceof Iterable) {
      for (Object v : (Iterable) selectedValues) {
        if (v != null && v.equals(value)) {
          return true;
        }
      }
    }
    else {
      return selectedValues.equals(value);
    }
  }
  return false;
}
%>