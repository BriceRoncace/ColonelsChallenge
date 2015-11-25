<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page>
  <jsp:attribute name="body">
    <h2>Challenge Statistics</h2>
    
    <ul class="nav nav-tabs" role="tablist">
      <li><a href="<c:url value="/overviewStats.html"/>?employeeId=${currentUser.id}&challengeYear=${challengeYear}" role="tab">Overview</a></li>
      <li><a href="<c:url value="/leaderboardStats.html"/>?challengeYear=${challengeYear}" role="tab">Leaderboard</a></li>
      <li class="active"><a href="<c:url value="/customStats.html"/>?challengeYear=${challengeYear}" role="tab">Custom</a></li>
    </ul>
    <div class="tab-content">
      <div class="tab-pane active" id="custom">
        <form action="<c:url value="/customStats.html"/>" method="GET" role="form">
          <div class="form-group row">  
            <div class="col-sm-1">
              <label>District</label>
              <div class="input-group">
                <t:select from="${districts}" multiple="true" size="6" cssClass="form-control" optionValue="label" name="districts" value="${criteria.districts}" id="districts" />
              </div>
            </div>

            <div class="col-sm-2">
              <label>Department</label>
              <div class="input-group">
                <t:select from="${departments}" multiple="true" size="6" cssClass="form-control" name="departments" value="${criteria.departments}" id="department" />
              </div>
            </div>

            <div class="col-sm-8">
              <label>Commissioned/Non-Commissioned</label>
              <div>
                <label class="radio-inline">
                  <input type="radio" name="commissioned" id="gender-male" value="true" ${criteria.commissioned ? 'checked' : ''}> Commissioned
                </label>
                <label class="radio-inline">
                  <input type="radio" name="commissioned" id="gender-female" value="false" ${!criteria.commissioned ? 'checked' : ''}> Non-Commissioned
                </label>
                <label class="radio-inline">
                  <input type="radio" name="commissioned" id="gender-female" value="" ${criteria.commissioned == null ? 'checked' : ''}> Both
                </label>
              </div>
              <br/>
              <label >Gender</label>
              <div>
                <label class="radio-inline">
                  <input type="radio" name="gender" id="gender-male" value="M" ${criteria.gender == 'M' ? 'checked' : ''}> Male
                </label>
                <label class="radio-inline">
                  <input type="radio" name="gender" id="gender-female" value="F" ${criteria.gender == 'F' ? 'checked' : ''}> Female
                </label>
                <label class="radio-inline">
                  <input type="radio" name="gender" id="gender-male" value="" ${criteria.gender == null ? 'checked' : ''}> Both
                </label>
              </div>  

            </div>  
          </div>

          <div class="form-group row">  
            <div class="col-sm-3">
              <input type="submit" class="btn btn-warning" value="Get Stats"/>
            </div>
          </div>     
        </form>
        <hr/>

        <div class="row" style="margin-bottom:10px;"> 
          <div class="col-sm-12">
            <h4>${stats.label}</h4>
            <h5>Participation rate: ${stats.participantCount}/${stats.totalCount} or <fmt:formatNumber pattern="###.##%" value="${stats.participationRate}"/></h5>
          </div>
        </div>

        <div class="row"> 
          <div class="col-sm-3">
            <div id="situpsPushupsStatChart"></div>
            Average: ${stats.meanSitupsPushups} &nbsp;&#8226;&nbsp; Median: ${stats.medianSitupsPushups}
          </div>
          <div class="col-sm-3">
            <div id="resistanceStatChart"></div>
            Average: ${stats.meanResistance} &nbsp;&#8226;&nbsp; Median: ${stats.medianResistance}
          </div>
          <div class="col-sm-3">
            <div id="aerobicStatChart"></div>
            Average: ${stats.meanAerobic} &nbsp;&#8226;&nbsp; Median: ${stats.medianAerobic}
          </div>
          <div class="col-sm-3">
            <div id="distanceStatChart"></div>
            Average: ${stats.meanDistance} &nbsp;&#8226;&nbsp; Median: ${stats.medianDistance}
          </div>
        </div>
      </div>
    </div>
  </jsp:attribute>
  <jsp:attribute name="scripts">
  <script type="text/javascript" src="https://www.google.com/jsapi"></script>
  <script type="text/javascript">
    google.load("visualization", "1", {packages:["corechart"]});
    google.setOnLoadCallback(drawStatCharts);
    
    function drawStatCharts() {
      drawStatChart(${stats.meanSitupsPushups}, ${stats.medianSitupsPushups}, 'Situps/Pushups', 'Reps', 'situpsPushupsStatChart');
      drawStatChart(${stats.meanResistance}, ${stats.medianResistance}, 'Resistance', 'Pounds', 'resistanceStatChart');
      drawStatChart(${stats.meanAerobic}, ${stats.medianAerobic}, 'Aerobic / High Intensity', 'Hours', 'aerobicStatChart');
      drawStatChart(${stats.meanDistance}, ${stats.medianDistance}, 'Distance', 'Miles', 'distanceStatChart');
    }
    
    function drawStatChart(average, median, title, axisLabel, divId) {
      var data = google.visualization.arrayToDataTable([
        ['', 'Average', 'Median'],
        ['', average, median]
      ]);

      var options = {
        title: title,
        vAxis: {title: axisLabel},
        legend: {position: 'bottom'}
        //backgroundColor: {fill:'transparent', strokeWidth:1, stroke:'#ccc'},
      };

      var chart = new google.visualization.ColumnChart(document.getElementById(divId));
      chart.draw(data, options);
    }
  </script>
  </jsp:attribute>
    
</t:page>