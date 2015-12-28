// used by programCodeList jsp file.
jQuery(function() { 	        		
        	jQuery('#datetimepicker').datetimepicker().on('hide', function(e){
        		
       		var selectedDate = jQuery("#datetimepicker :input").val();
       		var poId = jQuery("#poID").val();        		
        		
      		jQuery.ajax(
				{
					type : "POST",
					url : 'programCodesajaxChangeDate.action',
					data : {
						reportingDate : selectedDate,
						poId : poId
					},
					success: function(result){
					jQuery('#date_flash').delay(100).fadeIn('normal', function() {
               	    jQuery(this).delay(2500).fadeOut();
               	});
	        },
			timeout : 30000
		})			
		.fail(
			function(jqXHR,
				textStatus,
			errorThrown) {
 			alert(jqXHR
 					.getResponseHeader('msg'));
 		});
   	});        	
 });


function changeReportingPeriodLength(element) {
        	
    var selectedLength = element.value;
    var poId = jQuery("#poID").val();    		
        	
    jQuery.ajax(
	{
		type : "POST",
		url : 'programCodesajaxChangeLength.action',
		data : {
			reportingLength : selectedLength,
			poId : poId
		},
	    success: function(result){
				jQuery('#reporting_flash').delay(100).fadeIn('normal', function() {
				jQuery(this).delay(2500).fadeOut();
			});
	   },
	   timeout : 30000
	})			
	.fail(
		function(jqXHR,
				textStatus,
				errorThrown) {
				alert(jqXHR
						.getResponseHeader('msg'));
	});        	
}

(function ($) {
    $(function() {

        var table = $('#programCodesTable').DataTable({
            "dom": 'lprftip<"row">B',
            "pagingType": "full_numbers",
            "order": [
                [0, "asc"]
            ],
            "oLanguage": {
                "sInfo": "Showing _START_ to _END_ of _TOTAL_",
                "sLengthMenu": "Show _MENU_",
                "oPaginate": {
                    "sFirst": "<<",
                    "sPrevious": "<",
                    "sNext": ">",
                    "sLast": ">>"
                }
            },
            "serverSide": false,
            "columns": [{
                "data": "programCode"
            }, {
                "data": "programName"
            }, {
                "data": "",
                "defaultContent": ""
            }],
            "ajax": {
                "url": "programCodesfetchProgramCodesForFamily.action",
                "data": {
                    "selectedDTOId": $("#selectedDTOId").val()
                },
                "type": "POST"
            },
            "columnDefs": [{
                "render": function(data, type, full) {
                    if (full.isActive === false) {
                        return ' <span style="color:red;font-weight:bold"> (INACTIVE) </span>' + data;
                    } else {
                        return data;
                    }
                },
                "targets": [1]
            }]
        });
    });
})(jQuery);

