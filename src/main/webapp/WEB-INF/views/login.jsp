<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:parseDate value="${challengeYear.start}" pattern="yyyy-MM-dd" var="startDate" type="date"/>
<fmt:parseDate value="${challengeYear.end}" pattern="yyyy-MM-dd" var="endDate" type="date"/>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" /> 
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Colonel's Challenge</title>
    <%@include file="../views/includes/css.jspf" %>
    <link href='<c:url value="/style/login.css"/>' rel="stylesheet" type="text/css" />
  </head>
  <body>
    <!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="http://intranet/documents/${challengeYear.label}_ISP_Colonel_PT_Challenge.pdf">About</a></li>
          </ul>
        </div>
      </div>
    </div>

    <div id="headerwrap">
      <div class="container">
        <div class="row">
          <div class="col-lg-10 col-lg-offset-2">
            <%@include file="../views/includes/messages.jspf" %>
            <h1>${challengeYear.label} ISP Colonel's PT Challenge</h1>
            <h4 style="margin-top:-15px; margin-left:350px;"><fmt:formatDate value="${startDate}" pattern="MMMM d, yyyy"/> - <fmt:formatDate value="${endDate}" pattern="MMMM d, yyyy"/></h4>
            <img class="img-responsive" src="<c:url value="/images/fit-logo.png"/>" style="width:55%;margin-left:60px;" alt="">
             
            <div style="margin-left:135px;">
              <div id="stats"></div>
              <h3><span id="msg"></span></h3>
              <c:if test="${failed}"><span id="login-failed">Login failed.  Please try again.</span></c:if>
              <form action="j_spring_security_check" method="POST" class="form-inline" role="form">
                <div class="form-group">
                  <input type="text" class="form-control" style="width:150px;" id="username" name="j_username" placeholder="Username">
                  <input type="password" class="form-control" style="width:150px;" id="password" name="j_password" placeholder="Password">
                </div>
                <button type="submit" class="btn btn-warning btn-lg">Login</button>
              </form>			
            </div>
          
          </div>
        </div>
      </div>
    </div>
              
   <script src='<c:url value="/js/jquery.min.js"/>' type="text/javascript"></script>
   <script type="text/javascript">
    $(function() {
      $.getJSON('loginStats.json?challengeYear=${challengeYear}', showStats)
      
      var daysUntilStart = daysUntil(getChallengeStartDate());
      if (daysUntilStart > 1) {
        $("#msg").html("The challenge starts in " + (daysUntilStart-1) + " days!");
      }
      else {
        var daysLeft = daysUntil(getChallengeEndDate());
        if (daysLeft > 1) {
          $("#msg").html(daysLeft + " days left. Let's get going!");
        }
        else if (daysLeft === 1) {
          $("#msg").html("Final day of the challenge!");
        }
        else if (daysLeft <= 0) {
          $("#msg").html("The ${challengeYear.start.year} challenge has finished,<br/> but that doesn't mean you have.");
        }
      }
      
      function getChallengeStartDate() {
        return new Date(${challengeYear.start.year}, ${challengeYear.start.monthValue-1}, ${challengeYear.start.dayOfMonth})
      }
      
      function getChallengeEndDate() {
        return new Date(${challengeYear.end.year}, ${challengeYear.end.monthValue-1}, ${challengeYear.end.dayOfMonth})
      }
      
      function showStats(data) {
        var stats = [];
        $.each( data, function(key, val) {
          if (key === "totalDistance") {
            stats.push("Total mileage: " + foramtNum(val) + " miles");
          }
          else if (key === "totalParticipants") {
            stats.push("Total number of participants: " + foramtNum(val) + " employees");
          }
          else if (key === "totalSitupsPushups") {
            stats.push("Total number of situps/pushups: " + foramtNum(val) + " reps");
          }
          else if (key === "totalResistance") {
            stats.push("Total pounds of resistance lifted: " + foramtNum(val) + " pounds");
          }
          else if (key === "totalAerobicHours") {
            stats.push("Total hours of aerobic exercise: " + foramtNum(val) + " hours");
          }
        });

        var i = 0;
        (function showNextStat() {
          if (i === stats.length) {
            i = 0;
          }
          var stat = stats[i++];
          $("#stats").html(stat).fadeIn(2000).delay(2000).fadeOut(2000, showNextStat);
        })();
      }

      function foramtNum(val) {
        return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      }

      function daysUntil(endDate) {
        var oneDay = 24*60*60*1000; 
        var today = new Date();
        var daysBetweenIncludingToday = Math.ceil((endDate.getTime() - today.getTime()) / oneDay) + 1;
        return daysBetweenIncludingToday;
      }
    });
  </script>
  </body>
</html>