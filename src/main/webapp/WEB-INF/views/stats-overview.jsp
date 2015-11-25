<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page>
  <jsp:attribute name="body">
    <h2>Challenge Statistics</h2>
    
    <ul class="nav nav-tabs" role="tablist">
      <li class="active"><a href="<c:url value="/overviewStats.html"/>?employeeId=${currentUser.id}&challengeYear=${challengeYear}" role="tab">Overview</a></li>
      <li><a href="<c:url value="/leaderboardStats.html"/>?challengeYear=${challengeYear}" role="tab">Leaderboard</a></li>
      <li><a href="<c:url value="/customStats.html"/>?challengeYear=${challengeYear}" role="tab">Custom</a></li>
    </ul>
    <div class="tab-content">
      <div class="tab-pane active" id="overview">
        <div class="row"> 
          <div class="col-xs-12">
            <div id="participationQuickStatChart"></div>
          </div>
        </div>
        <br/>

        <div class="row"> 
          <div class="col-sm-6">
            <div id="situpsPushupsQuickStatChart"></div>
          </div>
          <div class="col-sm-6">
            <div id="resistanceQuickStatChart"></div>
          </div>
        </div>
        <br/>

        <div class="row"> 
          <div class="col-sm-6">
            <div id="aerobicQuickStatChart"></div>
          </div>
          <div class="col-sm-6">
            <div id="distanceQuickStatChart"></div>
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
      drawParticipationStatChart();
      drawSitupsPushupsStatChart();
      drawResistanceStatChart();
      drawAerobicStatChart();
      drawDistanceStatChart();
    }
    
    function drawParticipationStatChart() {
      var data = google.visualization.arrayToDataTable([
        ['District', 'Participation Rate'],
        ['All', <fmt:formatNumber pattern="###.##" value="${stats.ispAll.participationRate*100}"/>],
        ['D1', <fmt:formatNumber pattern="###.##" value="${stats.d1.participationRate*100}"/>],
        ['D2', <fmt:formatNumber pattern="###.##" value="${stats.d2.participationRate*100}"/>],
        ['D3', <fmt:formatNumber pattern="###.##" value="${stats.d3.participationRate*100}"/>],
        ['D4', <fmt:formatNumber pattern="###.##" value="${stats.d4.participationRate*100}"/>],
        ['D5', <fmt:formatNumber pattern="###.##" value="${stats.d5.participationRate*100}"/>],
        ['D6', <fmt:formatNumber pattern="###.##" value="${stats.d6.participationRate*100}"/>]
      ]);

      var options = {
        title: 'Participation Rate',
        vAxis: {title: 'Rate (%)'},
        hAxis: {title: 'District'},
        width: "100%"
      };

      var chart = new google.visualization.ColumnChart(document.getElementById('participationQuickStatChart'));
      chart.draw(data, options);
    }
    
    function drawSitupsPushupsStatChart() {
      var data = google.visualization.arrayToDataTable([
        ['District', 'Average', 'Median'],
        ['All', ${stats.ispAll.meanSitupsPushups}, ${stats.ispAll.medianSitupsPushups}],
        ['D1', ${stats.d1.meanSitupsPushups}, ${stats.d1.medianSitupsPushups}],
        ['D2', ${stats.d2.meanSitupsPushups}, ${stats.d2.medianSitupsPushups}],
        ['D3', ${stats.d3.meanSitupsPushups}, ${stats.d3.medianSitupsPushups}],
        ['D4', ${stats.d4.meanSitupsPushups}, ${stats.d4.medianSitupsPushups}],
        ['D5', ${stats.d5.meanSitupsPushups}, ${stats.d5.medianSitupsPushups}],
        ['D6', ${stats.d6.meanSitupsPushups}, ${stats.d6.medianSitupsPushups}],
        ['You', ${stats.individual.meanSitupsPushups}, ${stats.individual.medianSitupsPushups}]
      ]);

      var options = {
        title: 'Situps/Pushups',
        vAxis: {title: 'Reps'},
        hAxis: {title: 'District'}
      };

      var chart = new google.visualization.ColumnChart(document.getElementById('situpsPushupsQuickStatChart'));
      chart.draw(data, options);
    }
    
    function drawResistanceStatChart() {
      var data = google.visualization.arrayToDataTable([
        ['District', 'Average', 'Median'],
        ['All', ${stats.ispAll.meanResistance}, ${stats.ispAll.medianResistance}],
        ['D1', ${stats.d1.meanResistance}, ${stats.d1.medianResistance}],
        ['D2', ${stats.d2.meanResistance}, ${stats.d2.medianResistance}],
        ['D3', ${stats.d3.meanResistance}, ${stats.d3.medianResistance}],
        ['D4', ${stats.d4.meanResistance}, ${stats.d4.medianResistance}],
        ['D5', ${stats.d5.meanResistance}, ${stats.d5.medianResistance}],
        ['D6', ${stats.d6.meanResistance}, ${stats.d6.medianResistance}],
        ['You', ${stats.individual.meanResistance}, ${stats.individual.medianResistance}]
      ]);

      var options = {
        title: 'Resistance',
        vAxis: {title: 'Pounds'},
        hAxis: {title: 'District'}
      };

      var chart = new google.visualization.ColumnChart(document.getElementById('resistanceQuickStatChart'));
      chart.draw(data, options);
    }
    
    function drawAerobicStatChart() {
      var data = google.visualization.arrayToDataTable([
        ['District', 'Average', 'Median'],
        ['All', ${stats.ispAll.meanAerobic}, ${stats.ispAll.medianAerobic}],
        ['D1', ${stats.d1.meanAerobic}, ${stats.d1.medianAerobic}],
        ['D2', ${stats.d2.meanAerobic}, ${stats.d2.medianAerobic}],
        ['D3', ${stats.d3.meanAerobic}, ${stats.d3.medianAerobic}],
        ['D4', ${stats.d4.meanAerobic}, ${stats.d4.medianAerobic}],
        ['D5', ${stats.d5.meanAerobic}, ${stats.d5.medianAerobic}],
        ['D6', ${stats.d6.meanAerobic}, ${stats.d6.medianAerobic}],
        ['You', ${stats.individual.meanAerobic}, ${stats.individual.medianAerobic}]
      ]);

      var options = {
        title: 'Aerobic / High Intensity',
        vAxis: {title: 'Hours'},
        hAxis: {title: 'District'}
      };

      var chart = new google.visualization.ColumnChart(document.getElementById('aerobicQuickStatChart'));
      chart.draw(data, options);
    }
    
    function drawDistanceStatChart() {
      var data = google.visualization.arrayToDataTable([
        ['District', 'Average', 'Median'],
        ['All', ${stats.ispAll.meanDistance}, ${stats.ispAll.medianDistance}],
        ['D1', ${stats.d1.meanDistance}, ${stats.d1.medianDistance}],
        ['D2', ${stats.d2.meanDistance}, ${stats.d2.medianDistance}],
        ['D3', ${stats.d3.meanDistance}, ${stats.d3.medianDistance}],
        ['D4', ${stats.d4.meanDistance}, ${stats.d4.medianDistance}],
        ['D5', ${stats.d5.meanDistance}, ${stats.d5.medianDistance}],
        ['D6', ${stats.d6.meanDistance}, ${stats.d6.medianDistance}],
        ['You', ${stats.individual.meanDistance}, ${stats.individual.medianDistance}]
      ]);

      var options = {
        title: 'Distance',
        vAxis: {title: 'Miles'},
        hAxis: {title: 'District'}
      };

      var chart = new google.visualization.ColumnChart(document.getElementById('distanceQuickStatChart'));
      chart.draw(data, options);
    }
  </script>
  </jsp:attribute>
    
</t:page>