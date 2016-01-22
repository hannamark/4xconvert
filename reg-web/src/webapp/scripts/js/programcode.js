// used by programCodeList jsp file.
var programCodeTable;

document.observe("dom:loaded", function() {
	loadProgramCodes(jQuery);
	});
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

function loadProgramCodes($) {
    	programCodeTable = $('#programCodesTable').DataTable({
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
                "data": function(row,type,val,meta){
                	return '<a href="#" class="mcapage" rel="' + row.programCode + '">' +row.programCode+ '</a>';
                }
            }, {
                "data": "programName"
            }, {
                "data":function(row,type,val,meta){
                	return '<a href="#"  title="Manage this Program Code\'s assignments to trials where members of your organization family are participants" class="fa fa-cog"   onclick="manageProgramCodes(\''+row.programCode+'\',\''+jQuery("#poID").val() +'\')" data-trigger="hover"></a>';
                }
            }],
            "ajax": {
                "url": "programCodesfetchProgramCodesForFamily.action",
                "data": {
                    "poId": jQuery("#poID").val()  
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
                "bSortable": false,
                "targets": [1]
            },{
               "render": function(data, type, row, meta) {
                   return ' <button type=\"button\" title=\"Edit this Program <br> Code and Name\" rel=\"tooltip\" data-placement=\"top\" data-trigger=\"hover\"><i class=\"fa fa-pencil-square-o\"></i></button>' + '&nbsp;&nbsp;&nbsp;&nbsp;' + '<a href="#"  title="Manage this Program Code\'s assignments to trials where members of your organization family are participants" class="fa fa-cog"   onclick="manageProgramCodes(\''+row.programCode+'\',\''+jQuery("#poID").val() +'\')" data-trigger="hover"></a>';
               },
               "bSortable": false,
               "targets": [2]
            }],
            "fnDrawCallback": function( oSettings ) {
                this.$('[rel="tooltip"]').tooltip( {
                    placement : 'top',
                    html : true
            } );       
            }
        });
    	$('#programCodesTable tbody').on('click', 'a.mcapage', function (evt) {
            evt.preventDefault();
            var curpgcfilter = $.attr(evt.target, 'rel');
            document.forms[0].action = "managePCAssignmentexecute.action?pgcFilter="+curpgcfilter+"&familyPoId="+ jQuery("#poID").val() ;
	        document.forms[0].submit();
        });
    	
    	 $('#programCodesTable tbody').on( 'click', 'button', function () {
 	        var data = programCodeTable.row( $(this).parents('tr') ).data();
 	        $(this).parents('tr').addClass( "selected" );
 	        $("#dialog-edit").data('currentProgramCode', data.programCode);
  	        $("#dialog-edit").data('currentProgramCodeId', data.programCodeId);
 	        $("#updatedProgramCode").val(data.programCode);
 	        $("#updatedProgramName").val(data.programName);
 	        $("#dialog-edit").dialog('open');
 	 } );
    	 
    		$("#dialog-edit")
			.dialog(
					{
						modal : true,
						autoOpen : false,
						width : 540,
						buttons :    {
							"Save" : function() {
								$(this).dialog("close");
								if(!jQuery('#updatedProgramCode').val()){
									jQuery('#programCodesErrorList').html("Program code is required");
									jQuery("#programCodeErrorMessageModal").modal('show'); 
									return;
								} else if($("#dialog-edit").data('currentProgramCode') != $('#updatedProgramCode').val()){
									$("#dialog-confirm").data('currentProgramCode', $("#dialog-edit").data('currentProgramCode'));
							 	    $("#dialog-confirm").data('currentProgramCodeId', $("#dialog-edit").data('currentProgramCodeId'));
									$("#dialog-confirm").dialog('open');
									return;
								}
								jQuery('#program_code_progress_indicator_panel').css('display', 'inline-block');
								$
										.ajax(
												{
													type : "POST",
													url : 'programCodesupdateProgramCode.action',
													data : {
														updatedProgramCode : $('#updatedProgramCode').val(),
														updatedProgramName : $('#updatedProgramName').val(),
														poId : jQuery("#poID").val(),
														currentProgramCode :  $("#dialog-edit").data('currentProgramCode'),
														currentProgramCodeId : $("#dialog-edit").data('currentProgramCodeId')
													},
													timeout : 30000
												})
										.always(function() {
											jQuery('#program_code_progress_indicator_panel').hide();
										})
										.done(
												function() {
													  $('tr.selected').find('td:nth-child(1)').html('<a href="#" class="mcapage" rel="' + $('#updatedProgramCode').val() + '">' +$('#updatedProgramCode').val()+ '</a>');
													  $('tr.selected').find('td:nth-child(2)').html($('#updatedProgramName').val());
													  
													  $('#programCodesTable tbody tr').each(function(){
														  $(this).removeClass("selected");
													  });
													  showProgramCodeUpdatedFlashMessage();
												})
										.fail(
												function(jqXHR,textStatus,errorThrown) {
													 $('#programCodesTable tbody tr').each(function(){
														  $(this).removeClass("selected");
													  });
													jQuery('#programCodesErrorList').html(jqXHR.getResponseHeader('msg'));
													jQuery("#programCodeErrorMessageModal").modal('show'); 
												});

							},
							"Cancel" : function() {
								$(this).dialog("close");
							}
						}
					});
    		

    		$("#dialog-confirm")
			.dialog(
					{
						modal : true,
						autoOpen : false,
						width : 460,
						buttons :    {
							"Yes" : function() {
								$(this).dialog("close");
								jQuery('#program_code_progress_indicator_panel').css('display', 'inline-block');
								$
										.ajax(
												{
													type : "POST",
													url : 'programCodesupdateProgramCode.action',
													data : {
														updatedProgramCode : $('#updatedProgramCode').val(),
														updatedProgramName : $('#updatedProgramName').val(),
														poId : jQuery("#poID").val(),
														currentProgramCode :  $("#dialog-confirm").data('currentProgramCode'),
														currentProgramCodeId : $("#dialog-confirm").data('currentProgramCodeId')
													},
													timeout : 30000
												})
										.always(function() {
											jQuery('#program_code_progress_indicator_panel').hide();
										})
										.done(
												function() {
													  $('tr.selected').find('td:nth-child(1)').html('<a href="#" class="mcapage" rel="' + $('#updatedProgramCode').val() + '">' + $('#updatedProgramCode').val()+ '</a>');
													  $('tr.selected').find('td:nth-child(2)').html($('#updatedProgramName').val());
													  $('#programCodesTable tbody tr').each(function(){
														  $(this).removeClass("selected");
													  });
													  showProgramCodeUpdatedFlashMessage();
												})
										.fail(
												function(jqXHR,textStatus,errorThrown) {
													 $('#programCodesTable tbody tr').each(function(){
														  $(this).removeClass("selected");
													  });
													jQuery('#programCodesErrorList').html(jqXHR.getResponseHeader('msg'));
													jQuery("#programCodeErrorMessageModal").modal('show'); 
												});

							},
							"No" : function() {
								$(this).dialog("close");
							}
						}
					});
    		
    }
    
    function manageProgramCodes(programCode, selectedPID) {
   	 document.forms[0].action = "managePCAssignmentexecute.action?pgcFilter="+programCode+"&familyPoId="+ selectedPID;
        document.forms[0].submit();
   }

function addProgramCode(){
	if (validateProgramCode()){
		createNewProgramCode();
	} 
}

function createNewProgramCode(){
	    jQuery.ajax(
		{
			type : "POST",
			url : 'programCodescreateProgramCode.action',
			beforeSend: function () {
				jQuery('#program_code_progress_indicator_panel').css('display', 'inline-block');
	        },
			data : {
				poId : jQuery("#poID").val(),
				newProgramCode : jQuery("#newProgramCode").val(),
				newProgramName : jQuery("#newProgramName").val()
			},
		    success: function(result) {
		    		jQuery('#program_code_progress_indicator_panel').hide();
					programCodeTable.ajax.reload();
		   },
		   timeout : 30000
		})			
		.fail(
			function(jqXHR,	textStatus,	errorThrown) {
				jQuery('#program_code_progress_indicator_panel').hide();
				$('programCodesErrorList').innerHTML = jqXHR
				.getResponseHeader('msg');
				jQuery("#programCodeErrorMessageModal").modal('show'); 
		});        	

}


function validateProgramCode(){
	if(!jQuery('#newProgramCode').val()) {
		jQuery('#programCodesErrorList').html("Program code is required");
		jQuery("#programCodeErrorMessageModal").modal('show'); 
		return false;
	}
	
	return true;
}

function showProgramCodeUpdatedFlashMessage() {
	jQuery('#programCodeUpdatedMessageDiv').effect("highlight", {}, 3000);
	  setTimeout(function() {
		  jQuery("#programCodeUpdatedMessageDiv").hide('blind', {}, 500);
	    }, 5000);
}
