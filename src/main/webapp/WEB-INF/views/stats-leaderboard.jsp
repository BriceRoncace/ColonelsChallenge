<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page>
  <jsp:attribute name="body">
    <style type="text/css">
      /*.dataTables_info { display: none; }*/
      .rank-table { background-color: #fff; }
      .separator { border-top: 5px solid #fff; margin: 10px 0 10px 0; }
    </style>
    
    <h2>Challenge Statistics</h2>
    
    <ul class="nav nav-tabs" role="tablist">
      <li><a href="<c:url value="/overviewStats.html"/>?employeeId=${currentUser.id}&challengeYear=${challengeYear}" role="tab">Overview</a></li>
      <li class="active"><a href="<c:url value="/leaderboardStats.html"/>?challengeYear=${challengeYear}" role="tab">Leaderboard</a></li>
      <li><a href="<c:url value="/customStats.html"/>?challengeYear=${challengeYear}" role="tab">Custom</a></li>
    </ul>
    <div class="tab-content">
      <div class="tab-pane active" id="leaderboard">
        
        <%-- GATOR --%>
        <div class="row">
          <div class="col-sm-6">
            <a href="<c:url value="/badges.html"/>"><img src="<c:url value="/images/badges/alligator.png"/>" style="float:left;" border="0" /></a> <span class="badge-description"><strong>The Gator</strong><br/>Situps / Pushups</span>
          </div>
        </div>
        
        <div class="row"> 
          <div class="col-xs-6">
            <p class="text-center"><strong>Men</strong></p>
          </div>
          
          <div class="col-xs-6 ">
            <p class="text-center"><strong>Women</strong></p>
          </div>
        </div>
          
        <div class="row"> 
          <div class="col-xs-6">
            <table class="table table-hover table-condensed col-lg-12 rank-table">
              <thead>
                <tr>
                  <th>Rank</th>
                  <th>Reps</th>
                  <th>Name (District)</th>
                  <th>Behind</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach varStatus="s" var="e" items="${ranks.maleSitupPushups}">
                  <c:if test="${s.count == 1}"><c:set var="topMaleReps" value="${e.getTotalSitupsPushups(challengeYear)}"/></c:if>
                  <tr>
                    <td>${s.count}</td>
                    <td><fmt:formatNumber value="${e.getTotalSitupsPushups(challengeYear)}"/></td>
                    <td>${e.nameLastFirst} (${e.district.label})</td>
                    <td><fmt:formatNumber value="${topMaleReps - e.getTotalSitupsPushups(challengeYear)}"/></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        
          <div class="col-xs-6">
            <table class="table table-hover table-condensed col-lg-12 rank-table">
              <thead>
                <tr>
                  <th>Rank</th>
                  <th>Reps</th>
                  <th>Name (District)</th>
                  <th>Behind</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach varStatus="s" var="e" items="${ranks.femaleSitupPushups}">
                  <c:if test="${s.count == 1}"><c:set var="topFemaleReps" value="${e.getTotalSitupsPushups(challengeYear)}"/></c:if>
                  <tr>
                    <td>${s.count}</td>
                    <td><fmt:formatNumber value="${e.getTotalSitupsPushups(challengeYear)}"/></td>
                    <td>${e.nameLastFirst} (${e.district.label})</td>
                    <td><fmt:formatNumber value="${topFemaleReps - e.getTotalSitupsPushups(challengeYear)}"/></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
        
        <div class="row">
          <div class="col-sm-12 separator">
          </div>
        </div>
          
          
        <%-- BEAST --%>
        <div class="row">
          <div class="col-sm-6">
            <a href="<c:url value="/badges.html"/>"><img src="<c:url value="/images/badges/rhino.png"/>" style="float:left;" border="0" /></a>  <span class="badge-description"><strong>The Beast</strong><br/>Resistance</span>
          </div>
        </div>
        
        <div class="row"> 
          <div class="col-xs-6">
            <p class="text-center"><strong>Men</strong></p>
          </div>
          
          <div class="col-xs-6 ">
            <p class="text-center"><strong>Women</strong></p>
          </div>
        </div>
          
        <div class="row"> 
          <div class="col-xs-6">
            <table class="table table-hover table-condensed col-lg-12 rank-table">
              <thead>
                <tr>
                  <th>Rank</th>
                  <th>Pounds</th>
                  <th>Name (District)</th>
                  <th>Behind</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach varStatus="s" var="e" items="${ranks.maleResistance}">
                  <c:if test="${s.count == 1}"><c:set var="topMaleWeight" value="${e.getTotalWeight(challengeYear)}"/></c:if>
                  <tr>
                    <td>${s.count}</td>
                    <td><fmt:formatNumber value="${e.getTotalWeight(challengeYear)}"/></td>
                    <td>${e.nameLastFirst} (${e.district.label})</td>
                    <td><fmt:formatNumber value="${topMaleWeight - e.getTotalWeight(challengeYear)}"/></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        
          <div class="col-xs-6">
            <table class="table table-hover table-condensed col-lg-12 rank-table">
              <thead>
                <tr>
                  <th>Rank</th>
                  <th>Pounds</th>
                  <th>Name (District)</th>
                  <th>Behind</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach varStatus="s" var="e" items="${ranks.femaleResistance}">
                  <c:if test="${s.count == 1}"><c:set var="topFemaleWeight" value="${e.getTotalWeight(challengeYear)}"/></c:if>
                  <tr>
                    <td>${s.count}</td>
                    <td><fmt:formatNumber value="${e.getTotalWeight(challengeYear)}"/></td>
                    <td>${e.nameLastFirst} (${e.district.label})</td>
                    <td><fmt:formatNumber value="${topFemaleWeight - e.getTotalWeight(challengeYear)}"/></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>  
          
        <div class="row">
          <div class="col-sm-12 separator">
          </div>
        </div>
          
          
        <%-- LONG HAULER --%>
        <div class="row">
          <div class="col-sm-6">
            <a href="<c:url value="/badges.html"/>"><img src="<c:url value="/images/badges/camel.png"/>" style="float:left;" border="0" /></a>  <span class="badge-description"><strong>The Long Hauler</strong><br/>Aerobic Hours</span>
          </div>
        </div>
        
        <div class="row"> 
          <div class="col-xs-6">
            <p class="text-center"><strong>Men</strong></p>
          </div>
          
          <div class="col-xs-6 ">
            <p class="text-center"><strong>Women</strong></p>
          </div>
        </div>
          
        <div class="row"> 
          <div class="col-xs-6">
            <table class="table table-hover table-condensed col-lg-12 rank-table">
              <thead>
                <tr>
                  <th>Rank</th>
                  <th>Hours</th>
                  <th>Name (District)</th>
                  <th>Behind</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach varStatus="s" var="e" items="${ranks.maleAerobicHours}">
                  <c:if test="${s.count == 1}"><c:set var="topMaleAerobicHours" value="${e.getTotalAerobicHours(challengeYear)}"/></c:if>
                  <tr>
                    <td>${s.count}</td>
                    <td><fmt:formatNumber value="${e.getTotalAerobicHours(challengeYear)}"/></td>
                    <td>${e.nameLastFirst} (${e.district.label})</td>
                    <td><fmt:formatNumber value="${topMaleAerobicHours - e.getTotalAerobicHours(challengeYear)}"/></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        
          <div class="col-xs-6">
            <table class="table table-hover table-condensed col-lg-12 rank-table">
              <thead>
                <tr>
                  <th>Rank</th>
                  <th>Hours</th>
                  <th>Name (District)</th>
                  <th>Behind</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach varStatus="s" var="e" items="${ranks.femaleAerobicHours}">
                  <c:if test="${s.count == 1}"><c:set var="topFemaleAerobicHours" value="${e.getTotalAerobicHours(challengeYear)}"/></c:if>
                  <tr>
                    <td>${s.count}</td>
                    <td><fmt:formatNumber value="${e.getTotalAerobicHours(challengeYear)}"/></td>
                    <td>${e.nameLastFirst} (${e.district.label})</td>
                    <td><fmt:formatNumber value="${topFemaleAerobicHours - e.getTotalAerobicHours(challengeYear)}"/></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>   
      
        <div class="row">
          <div class="col-sm-12 separator">
          </div>
        </div>
          
        <%-- Cheetah --%>
        <div class="row">
          <div class="col-sm-6">
            <a href="<c:url value="/badges.html"/>"><img src="<c:url value="/images/badges/cheetah.png"/>" style="float:left;" border="0" /></a>  <span class="badge-description"><strong>The Cheetah</strong><br/>Run / Walk Miles</span>
          </div>
        </div>
        
        <div class="row"> 
          <div class="col-xs-6">
            <p class="text-center"><strong>Men</strong></p>
          </div>
          
          <div class="col-xs-6 ">
            <p class="text-center"><strong>Women</strong></p>
          </div>
        </div>
          
        <div class="row"> 
          <div class="col-xs-6">
            <table class="table table-hover table-condensed col-lg-12 rank-table">
              <thead>
                <tr>
                  <th>Rank</th>
                  <th>Miles</th>
                  <th>Name (District)</th>
                  <th>Behind</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach varStatus="s" var="e" items="${ranks.maleRun}">
                  <c:if test="${s.count == 1}"><c:set var="topMaleRun" value="${e.getTotalRunDistance(challengeYear)}"/></c:if>
                  <tr>
                    <td>${s.count}</td>
                    <td><fmt:formatNumber value="${e.getTotalRunDistance(challengeYear)}"/></td>
                    <td>${e.nameLastFirst} (${e.district.label})</td>
                    <td><fmt:formatNumber value="${topMaleRun - e.getTotalRunDistance(challengeYear)}"/></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        
          <div class="col-xs-6">
            <table class="table table-hover table-condensed col-lg-12 rank-table">
              <thead>
                <tr>
                  <th>Rank</th>
                  <th>Miles</th>
                  <th>Name (District)</th>
                  <th>Behind</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach varStatus="s" var="e" items="${ranks.femaleRun}">
                  <c:if test="${s.count == 1}"><c:set var="topFemaleRun" value="${e.getTotalRunDistance(challengeYear)}"/></c:if>
                  <tr>
                    <td>${s.count}</td>
                    <td><fmt:formatNumber value="${e.getTotalRunDistance(challengeYear)}"/></td>
                    <td>${e.nameLastFirst} (${e.district.label})</td>
                    <td><fmt:formatNumber value="${topFemaleRun - e.getTotalRunDistance(challengeYear)}"/></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>   
          
        <div class="row">
          <div class="col-sm-12 separator">
          </div>
        </div>
          
        <%-- The Cyclist --%>
        <div class="row">
          <div class="col-sm-6">
            <a href="<c:url value="/badges.html"/>"><img src="<c:url value="/images/badges/bear.png"/>" style="float:left;" border="0" /></a>  <span class="badge-description"><strong>The Cyclist</strong><br/>Bike Miles</span>
          </div>
        </div>
        
        <div class="row"> 
          <div class="col-xs-6">
            <p class="text-center"><strong>Men</strong></p>
          </div>
          
          <div class="col-xs-6 ">
            <p class="text-center"><strong>Women</strong></p>
          </div>
        </div>
          
        <div class="row"> 
          <div class="col-xs-6">
            <table class="table table-hover table-condensed col-lg-12 rank-table">
              <thead>
                <tr>
                  <th>Rank</th>
                  <th>Miles</th>
                  <th>Name (District)</th>
                  <th>Behind</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach varStatus="s" var="e" items="${ranks.maleBike}">
                  <c:if test="${s.count == 1}"><c:set var="topMaleBike" value="${e.getTotalBikeDistance(challengeYear)}"/></c:if>
                  <tr>
                    <td>${s.count}</td>
                    <td><fmt:formatNumber value="${e.getTotalBikeDistance(challengeYear)}"/></td>
                    <td>${e.nameLastFirst} (${e.district.label})</td>
                    <td><fmt:formatNumber value="${topMaleBike - e.getTotalBikeDistance(challengeYear)}"/></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        
          <div class="col-xs-6">
            <table class="table table-hover table-condensed col-lg-12 rank-table">
              <thead>
                <tr>
                  <th>Rank</th>
                  <th>Miles</th>
                  <th>Name (District)</th>
                  <th>Behind</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach varStatus="s" var="e" items="${ranks.femaleBike}">
                  <c:if test="${s.count == 1}"><c:set var="topFemaleBike" value="${e.getTotalBikeDistance(challengeYear)}"/></c:if>
                  <tr>
                    <td>${s.count}</td>
                    <td><fmt:formatNumber value="${e.getTotalBikeDistance(challengeYear)}"/></td>
                    <td>${e.nameLastFirst} (${e.district.label})</td>
                    <td><fmt:formatNumber value="${topFemaleBike - e.getTotalBikeDistance(challengeYear)}"/></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div> 
          
        <div class="row">
          <div class="col-sm-12 separator">
          </div>
        </div>
          
        <%-- Shark --%>
        <div class="row">
          <div class="col-sm-6">
            <a href="<c:url value="/badges.html"/>"><img src="<c:url value="/images/badges/shark.png"/>" style="float:left;" border="0" /></a>  <span class="badge-description"><strong>The Shark</strong><br/>Swim Miles</span>
          </div>
        </div>
        
        <div class="row"> 
          <div class="col-xs-6">
            <p class="text-center"><strong>Men</strong></p>
          </div>
          
          <div class="col-xs-6 ">
            <p class="text-center"><strong>Women</strong></p>
          </div>
        </div>
          
        <div class="row"> 
          <div class="col-xs-6">
            <table class="table table-hover table-condensed col-lg-12 rank-table">
              <thead>
                <tr>
                  <th>Rank</th>
                  <th>Miles</th>
                  <th>Name (District)</th>
                  <th>Behind</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach varStatus="s" var="e" items="${ranks.maleSwim}">
                  <c:if test="${s.count == 1}"><c:set var="topMaleSwim" value="${e.getTotalSwimDistance(challengeYear)}"/></c:if>
                  <tr>
                    <td>${s.count}</td>
                    <td><fmt:formatNumber value="${e.getTotalSwimDistance(challengeYear)}"/></td>
                    <td>${e.nameLastFirst} (${e.district.label})</td>
                    <td><fmt:formatNumber value="${topMaleSwim - e.getTotalSwimDistance(challengeYear)}"/></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        
          <div class="col-xs-6">
            <table class="table table-hover table-condensed col-lg-12 rank-table">
              <thead>
                <tr>
                  <th>Rank</th>
                  <th>Miles</th>
                  <th>Name (District)</th>
                  <th>Behind</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach varStatus="s" var="e" items="${ranks.femaleSwim}">
                  <c:if test="${s.count == 1}"><c:set var="topFemaleSwim" value="${e.getTotalSwimDistance(challengeYear)}"/></c:if>
                  <tr>
                    <td>${s.count}</td>
                    <td><fmt:formatNumber value="${e.getTotalSwimDistance(challengeYear)}"/></td>
                    <td>${e.nameLastFirst} (${e.district.label})</td>
                    <td><fmt:formatNumber value="${topFemaleSwim - e.getTotalSwimDistance(challengeYear)}"/></td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>   
          
          
      </div>
    </div>
  </jsp:attribute>
</t:page>