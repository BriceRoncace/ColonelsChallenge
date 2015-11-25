$(function () {
  $('.data-table').dataTable({
     paging: false,
     searching: false,
     aoColumnDefs: [{"bSortable" : false, "aTargets" : [ "no-sort" ]}]
  });
  
  $('.data-table-with-tools').dataTable({
     paging: false,
     searching: false,
     aoColumnDefs: [{"bSortable" : false, "aTargets" : [ "no-sort" ]}],
     dom: 'T<"clear">lfrtip',
     tableTools: {
       sSwfPath: "js/datatables/swf/copy_csv_xls.swf",
       aButtons: [ "copy", "csv" ]
     }
  });
});