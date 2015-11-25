$(function () {
  $('.dateTimePicker').datetimepicker({format: 'mm/dd/yyyy hhii', autoclose: true, forceParse: false, showMeridian: true});
  $('.datePicker').datetimepicker({format: 'mm/dd/yyyy', autoclose: true, todayHighlight: true, minView: 2, forceParse: false});
  $('.timePicker').datetimepicker({format: "hhii", autoclose: true, startView: 1, forceParse: false, showMeridian: true });
});