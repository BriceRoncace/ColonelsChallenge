<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page>
  <jsp:attribute name="body">
    <h3 class="row">Performance Badges</h3>
    <div class="row">
      <div class="col-sm-12">
        <p>The following performance badges are dynamically awarded to the top <strong>male and female</strong> performers.  To see current top performances visit the <a href="<c:url value="/leaderboardStats.html"/>">leaderboard</a>.</p>
      </div>
    </div>
    
    <div class="row">
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/alligator.png"/>" style="float:left;" />  <span class="badge-description"><strong>The Gator</strong><br/>Awarded to those with the highest rank in number of situps / pushups.</span>
      </div>
      
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/rhino.png"/>" style="float:left;" />  <span class="badge-description"><strong>The Beast</strong><br/>Awarded to those with the highest rank in resistance (i.e. greatest number of total pounds lifted).</span>
      </div>
      
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/camel.png"/>" style="float:left;" /> <span class="badge-description"><strong>The Long Hauler</strong><br/>Awarded to those with the highest rank in total aerobic hours.</span>
      </div>
    </div>
    
    <div class="row">
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/cheetah.png"/>" style="float:left;" /> <span class="badge-description"><strong>The Cheetah</strong><br/>Awarded to those with the highest rank in miles ran or walked.</span>
      </div>
      
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/bear.png"/>" style="float:left;" /> <span class="badge-description"><strong>The Cyclist</strong><br/>Awarded to those with the highest rank in miles biked.</span>
      </div>
      
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/shark.png"/>" style="float:left;" /> <span class="badge-description"><strong>The Shark</strong><br/>Awarded to those with the highest rank in miles swam.</span>
      </div>
    </div>
      
    <h3 class="row">Recognized Ranks</h3>
    
    <div class="row">
      <div class="col-sm-12">
        <p>Those in the number <strong>1, 2, or 3</strong> rank will receive a badge.  Those in the top ten outside of the top 3 spots will also receive a badge.  Examples:</p>
      </div>
    </div>
    
    <div class="row">
      <div>
        <div class="col-sm-3">
          <img src="<c:url value="/images/badges/rhino1.png"/>" />
        </div>
        <div class="col-sm-3">
          <img src="<c:url value="/images/badges/rhino2.png"/>" />
        </div>
        <div class="col-sm-3">
          <img src="<c:url value="/images/badges/rhino3.png"/>" />
        </div>
        <div class="col-sm-3">
          <img src="<c:url value="/images/badges/rhino10.png"/>" />
        </div>
      </div>
    </div>
    
    <h3 class="row">Completion Badges</h3>     
    <div class="row">
      <div class="col-sm-12">
        <p>Any fitness challenge participant completing 100% of their goal in any of the three areas will receive a completion badge.</p>
      </div>
    </div>
    
    <div class="row">
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/trophy-one-third-complete.png"/>" style="float:left;" />  <span class="badge-description"><strong>The One Thirder</strong><br/>Awarded to those with 100% completion in <b>one</b> challenge category.</span>
      </div>
      
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/trophy-two-thirds-complete.png"/>" style="float:left;" />  <span class="badge-description"><strong>The Two Thirder</strong><br/>Awarded to those with 100% completion in <b>two</b> challenge categories.</span>
      </div>
      
      <div class="col-sm-4">
        <img src="<c:url value="/images/badges/trophy.png"/>" style="float:left;" /> <span class="badge-description"><strong>The Victor</strong><br/>Awarded to those with 100% completion in <b>all three</b> challenge categories.  Well done.</span>
      </div>
    </div>
      
    <h3 class="row">Secret Badges</h3>     
    <div class="row">
      <div class="col-sm-12">
        <p>New for 2015, secret or rare badges are awarded based on an undisclosed, closely guarded algorithm. Keep working hard, and mayhap you will be lucky enough to get one!</p>
        <p><strong>Update: <a href="<c:url value="/secret-badges-revealed.html"/>">Secret badges revealed!</a></strong>
      </div>
    </div>
        
  </jsp:attribute>
</t:page>