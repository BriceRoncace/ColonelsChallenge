<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page>
  <jsp:attribute name="body">
    <h3 class="row">Secret Badges: <span style="color:#AC4442; font-weight:bold;">Revealed!</span></h3>
    <div class="row">
      
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/bird.png"/>" style="float:left;" />
        <span class="badge-description"><strong>The Early Bird</strong>&nbsp;&nbsp;<span class="badges-awarded">${badgeCountMap.get('EARLY_BIRD') != null ? badgeCountMap.get('EARLY_BIRD') : '0'} awarded</span><br/>Awarded to those with five or more workout days within the first week of the challenge.</span>
      </div>
      
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/ant.png"/>" style="float:left;" />  
        <span class="badge-description"><strong>The Worker</strong>&nbsp;&nbsp;<span class="badges-awarded">${badgeCountMap.get('WORKER') != null ? badgeCountMap.get('WORKER') : '0'} awarded</span><br/>Awarded to those having a single workout at or over 2 hours in duration.</span>
      </div>
      
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/gazelle.png"/>" style="float:left;" />  
        <span class="badge-description"><strong>The Gazelle</strong>&nbsp;&nbsp;<span class="badges-awarded">${badgeCountMap.get('GAZELLE') != null ? badgeCountMap.get('GAZELLE') : '0'} awarded</span><br/>Awarded to those having a single run of 10 or more miles.</span>
      </div>
    </div>
      
    <div class="row">
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/eagle.png"/>" style="float:left;" />  
        <span class="badge-description"><strong>The Highflier</strong>&nbsp;&nbsp;<span class="badges-awarded">${badgeCountMap.get('HIGH_FLIER') != null ? badgeCountMap.get('HIGH_FLIER') : '0'} awarded</span><br/>Awarded to those accomplishing a goal that was not accomplished last year.</span>
      </div>
      
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/barbell.png"/>" style="float:left;" />  
        <span class="badge-description"><strong>The Extreme Lifter</strong>&nbsp;&nbsp;<span class="badges-awarded">${badgeCountMap.get('EXTREME_LIFTER') != null ? badgeCountMap.get('EXTREME_LIFTER') : '0'} awarded</span><br/>Awarded to those lifting 100,000 or more pounds in a single workout.</span>
      </div>
      
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/bee.png"/>" style="float:left;" />  
        <span class="badge-description"><strong>The Worker Bee</strong>&nbsp;&nbsp;<span class="badges-awarded">${badgeCountMap.get('BEE') != null ? badgeCountMap.get('BEE') : '0'} awarded</span><br/>Awarded to those having 50 or more workout days during the 10 week challenge.</span>
      </div>
    </div>    
    
    <div class="row">
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/duck.png"/>" style="float:left;" />
        <span class="badge-description"><strong>The Lucky Duck</strong>&nbsp;&nbsp;<span class="badges-awarded">${badgeCountMap.get('LUCKY_DUCK') != null ? badgeCountMap.get('LUCKY_DUCK') : '0'} awarded</span><br/>Randomly awarded to one lucky participant per week. (Must play to win!)</span>
      </div>
      
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/mermaid.png"/>" style="float:left;" />
        <span class="badge-description"><strong>The Mermaid</strong>&nbsp;&nbsp;<span class="badges-awarded">${badgeCountMap.get('MERMAID') != null ? badgeCountMap.get('MERMAID') : '0'} awarded</span><br/>Awarded to the first female to swim one mile.</span>
      </div>
      
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/merman.png"/>" style="float:left;" />
        <span class="badge-description"><strong>The Sea King</strong>&nbsp;&nbsp;<span class="badges-awarded">${badgeCountMap.get('SEA_KING') != null ? badgeCountMap.get('SEA_KING') : '0'} awarded</span><br/>Awarded to the first male to swim ten miles.</span>
      </div>
    </div>
    
    
  </jsp:attribute>
</t:page>