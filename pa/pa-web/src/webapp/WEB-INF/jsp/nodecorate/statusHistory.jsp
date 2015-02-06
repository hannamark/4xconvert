<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css?1'/>" rel="stylesheet"
	type="text/css" media="all" />
<link
	href="<c:url value='/scripts/js/jquery-ui-1.11.2.custom/jquery-ui.css'/>"
	rel="stylesheet" media="all" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/scripts/js/DataTables-1.10.4/media/css/jquery.dataTables.min.css'/>">

<style type="text/css">
a,a:hover {
	text-decoration: none;
}

th {
	white-space: nowrap;
}

.error {
	color: red;
}

.warning {
	color: blue;
}

#audit-trail-table td:nth-child(4) > table {
	background: none;
	width: 95%;
}

#audit-trail-table td:nth-child(4) > table > thead > tr > th {
    background: none;
}

#audit-trail-table td:nth-child(4) > table td:nth-child(1) {
   white-space: nowrap;
   width: 30%;
}

#audit-trail-table td:nth-child(4) > table td:nth-child(2),  #audit-trail-table td:nth-child(4) > table td:nth-child(3){  
   width: 35%;
}

.dataTables_empty {
    background-image: url("<c:url value='/images/loading.gif'/>");
    background-repeat: no-repeat;
    background-position: center;
    background-size: 16px 16px;
    color: #f7f9ea;
    text-align: left !important;    
}

</style>

<script type="text/javascript"
	src="<c:url value='/scripts/js/jquery-1.11.1.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/scripts/js/jquery-ui-1.11.2.custom/jquery-ui.min.js'/>"></script>
<!-- DataTables -->
<script type="text/javascript" charset="utf8"
	src="<c:url value='/scripts/js/DataTables-1.10.4/media/js/jquery.dataTables.min.js'/>"></script>

<script type="text/javascript" language="javascript">		  
		  jQuery.noConflict();
		</script>

<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/coppa.js'/>"></script>
<script type="text/javascript" language="javascript">

            var trailAjax = '<c:url value='/protected/ajaxStudyOverallStatusHistorypopupgetAuditTrail.action'/>';
            var displaySuAbstractorAutoCheckoutMessage = ${displaySuAbstractorAutoCheckoutMessage};

            function closepopup() {
                window.top.hidePopWin(true); 
            }
            
            function deleteStatus(statusId) {            	
            		$('statusId').value = statusId;
            		document.forms['studyOverallStatus'].action='studyOverallStatusHistorypopupdelete.action';
            		showDeleteBox();
            }
                       
            function editStatus(statusId, statusCode, statusDate, reason, comment) {
            	document.forms['studyOverallStatus'].action='studyOverallStatusHistorypopupsave.action';
            	$('statusId').value = statusId;
            	$('statusCode').setValue(statusCode);
            	$('statusDate').setValue(statusDate);
            	$('reason').setValue(reason);
            	$('comment').setValue(comment);
            	$('edit-dialog-header').innerHTML = 'Edit Trial Status';
            	showEditBox();
            }   
            
            function addNewStatus() {
                document.forms['studyOverallStatus'].action='studyOverallStatusHistorypopupaddNew.action';
                $('statusId').value = '';
                $('statusCode').setValue('');
                $('statusDate').setValue('');
                $('reason').setValue('');
                $('comment').setValue('');
                $('edit-dialog-header').innerHTML = 'Add Trial Status';
                showEditBox();
            }   
            
            function validateStatusTransitions() {
            	document.forms['studyOverallStatus'].action='studyOverallStatusHistorypopupvalidateStatusTransitions.action';   
            	document.forms['studyOverallStatus'].submit();
            }
                        
            
            function showEditBox() {
                showBox('edit-dialog');
            }
            
            function showDeleteBox() {
                showBox('delete-dialog');
            }  
            
            function showBox(id) {       
                // retrieve required dimensions 
                var eltDims     = $(id).getDimensions();
                var browserDims = $(document).viewport.getDimensions();
                 
                // calculate the center of the page using the browser and element dimensions
                var y  = (browserDims.height - eltDims.height) / 2;
                var x = (browserDims.width - eltDims.width) / 2;    
                
                $(id).absolutize(); 
                $(id).style.left = x + 'px';
                $(id).style.top = y + 'px';
                $(id).show();
            }  
            
            function closepopupAndRefreshParentIfNeeded() {
            	closepopup();
            	if ($F('changesMadeFlag') == 'true') {
            		var parentForm = window.parent.document.forms['studyoverallstatus'];
            		if (parentForm != null) {
            			jQuery("#studyOverallStatusLink", window.parent.document).click();
            		}
            	}
            }
            
            var auditedStatusId = null;
            
            function auditTrail(statusId, statusCode) {
            	auditedStatusId = statusId;
            	jQuery( "#audit-trail" ).dialog('open');            	 
            	jQuery('#audit-trail-table').DataTable().clear().draw().ajax.reload();
            	jQuery('#auditedStatus').html(statusCode);
            }
            
            (function ($) {
            	 $(function() {
                     $( document ).tooltip();
                     
                     var table = $('#row').DataTable({
                    	 "paging":   false,
                    	 bFilter: false,
                         "columnDefs" : [ {
                             "targets" : 4,
                             "orderable" : false
                         }]
                     });
                     
                     var del = $('#del').DataTable({
                         "paging":   false,
                         bFilter: false,
                         "columnDefs" : [ {
                             "targets" : 5,
                             "orderable" : false
                         }]
                     });
                     
                     var trail = $('#audit-trail-table').DataTable({         
                    	 "sEmptyTable": "The table is really empty now!",                         
                         bFilter: false,
                         "columnDefs" : [ {
                             "targets" : 3,
                             "orderable" : false
                         }],
                         "ajax": {
                       	    "url": trailAjax,
                       	    "type": "POST",
                       	    "data": function ( d ) {
                       	    	d.statusId = auditedStatusId;
                       	    }
                         }
                     });
                     
                     $( "#audit-trail" ).dialog({
                         modal: true,
                         autoOpen : false,
                         height : $(window).height()*0.9,
                         width : $(window).width()*0.9,
                         buttons: {
                           OK: function() {
                             $( this ).dialog( "close" );
                           }
                         }
                     });
                     
                     if (displaySuAbstractorAutoCheckoutMessage) {
                         $( "#displaySuAbstractorAutoCheckoutMessage" ).dialog({
                                 autoOpen : true,                                
                                 height:220,
                                 width:430,
                                 modal: true,
                                 buttons: {                                        
                                         "Close": function() {
                                               $( this ).dialog( "close" );
                                             }
                                         }
                            });                      
                    }
                     
                 });
            })(jQuery);
            
           
            
        </script>
</head>
<body>
	<div class="box">
		<s:form action="studyOverallStatus">

			<s:hidden name="statusId" value="" id="statusId"></s:hidden>
			<s:hidden name="changesMadeFlag" id="changesMadeFlag"></s:hidden>
			<h2>
				<fmt:message key="statusHistory.title" />
			</h2>
			<br />
			<pa:sucessMessage />
			<pa:failureMessage />

			<s:set name="overallStatusList" value="overallStatusList"
				scope="request" />
			<display:table class="data"
				decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator"
				sort="list" uid="row" name="overallStatusList" export="false">
				<display:column title="Status Date" sortable="false">
					<span style="display: none;"><fmt:formatDate
							value="${row.statusDateRaw}" pattern="yyyy-MM-dd" /></span>
					<c:out value="${row.statusDate}" />
				</display:column>
				<display:column escapeXml="true" property="statusCode"
					title="Status" sortable="false" />
				<display:column escapeXml="true" property="comments"
					title="Comments" sortable="false" />
				<display:column title="Validation Messages" sortable="false">
					<c:if test="${not empty row.errors}">
						<span class="error"><b>ERROR:</b> <c:out
								value="${row.errors}" /></span>
						<br />
					</c:if>
					<c:if test="${not empty row.warnings}">
						<span class="warning"><b>WARNING:</b> <c:out
								value="${row.warnings}" /></span>
						<br />
					</c:if>
				</display:column>
				<display:column titleKey="studyOverallStatus.actions"
					sortable="false">
					<s:if test="%{#attr.row.editable}">
						<a href="javascript:void(0);"
							onclick="editStatus(<s:property value="%{#attr.row.id}"/>,
                               '<s:property value="%{#attr.row.statusCode}"/>', '<s:property value="%{#attr.row.statusDate}"/>',
                                   '<s:property value="%{@org.apache.commons.lang.StringEscapeUtils@escapeJavaScript(#attr.row.reason)}"/>',
                                   '<s:property value="%{@org.apache.commons.lang.StringEscapeUtils@escapeJavaScript(#attr.row.comments)}"/>');">
							<img src='<c:url value="/images/ico_edit.gif"/>'
							alt="<fmt:message key="studyOverallStatus.edit" />"
							title="<fmt:message key="studyOverallStatus.edit" />" width="16"
							height="16" />
						</a>

					</s:if>
					<s:if test="%{#attr.row.deletable}">
						<a href="javascript:void(0);"
							onclick="deleteStatus(<s:property value="%{#attr.row.id}"/>);">
							<img src='<c:url value="/images/ico_delete.gif"/>'
							alt="<fmt:message key="studyOverallStatus.delete" />"
							title="<fmt:message key="studyOverallStatus.delete" />"
							width="16" height="16" />
						</a>
					</s:if>
					<a href="javascript:void(0);"
						onclick="auditTrail(<s:property value="%{#attr.row.id}"/>,
                               '<s:property value="%{#attr.row.statusCode}"/>');">
						<img src='<c:url value="/images/ico_audittrail.png"/>'
						alt="Audit trail icon"
						title="Click to view Audit Trail Information for this status record"
						width="16" height="16" />
					</a>
					<c:if test="${not empty row.reason}">
						<a href="javascript:void(0);"
							title="Why study stopped: <c:out value="${row.reason}"/>"> <img
							src='<c:url value="/images/ico_callout.png"/>'
							alt="Why Study Stopped?" width="16" height="16" /></a>
					</c:if>
				</display:column>
			</display:table>

			<div id="delete-dialog" style="display: none;">
				<div class="header">Please provide a comment:</div>
				<div class="body">
					<div class="label">Comments</div>
					<div>
						<s:textarea name="deleteComment" id="deleteComment" rows="3"></s:textarea>
					</div>
					<div align="center">
						<input type="button" value="Delete Status"
							onclick="$('delete-dialog').hide();displayWaitPanel();this.form.submit();" />&nbsp;<input
							type="button" value="Cancel" onclick="$('delete-dialog').hide();" />
					</div>
				</div>
			</div>

			<div id="edit-dialog" style="display: none;">
				<div class="header" id="edit-dialog-header">
					<fmt:message key="studyOverallStatus.edit.header" />
				</div>
				<div class="body">
					<div class="label">
						<fmt:message key="studyOverallStatus.edit.status" />
					</div>
					<div>
						<s:select name="statusCode" id="statusCode"
							list="@gov.nih.nci.pa.enums.StudyStatusCode@values()"
							listKey="code" listValue="code" value="" />
					</div>
					<div class="label">
						<fmt:message key="studyOverallStatus.edit.date" />
					</div>
					<div>
						<s:textfield name="statusDate" id="statusDate" maxlength="10"></s:textfield>
					</div>
					<div class="label">
						<fmt:message key="studyOverallStatus.edit.reason" />
					</div>
					<div>
						<s:textarea name="reason" id="reason" rows="3"></s:textarea>
					</div>
					<div class="label">Comments</div>
					<div>
						<s:textarea name="comment" id="comment" rows="3"></s:textarea>
					</div>
					<div align="center">
						<input type="button" value="Save"
							onclick="$('edit-dialog').hide();displayWaitPanel();this.form.submit();" />&nbsp;<input
							type="button" value="Cancel" onclick="$('edit-dialog').hide();" />
					</div>
				</div>
			</div>
		</s:form>

		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
					<li><s:a href="javascript:void(0)" cssClass="btn"
							onclick="addNewStatus();">
							<span class="btn_img"><span class="add">Add New
									Status</span></span>
						</s:a></li>
					<li><s:a href="javascript:void(0)" cssClass="btn"
							onclick="validateStatusTransitions();">
							<span class="btn_img"><span class="confirm">Validate
									Status Transitions</span></span>
						</s:a></li>
					<li><s:a href="javascript:void(0)" cssClass="btn"
							onclick="closepopupAndRefreshParentIfNeeded();">
							<span class="btn_img"><span class="cancel">Cancel</span></span>
						</s:a></li>
				</ul>
			</del>
		</div>

		<c:if test="${not empty deletedList}">
			<h2>Deleted Trial Status Records</h2>
			<br />
			<s:set name="deletedList" value="deletedList" scope="request" />
			<display:table class="data"
				decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator"
				sort="list" uid="del" name="deletedList" id="del" export="false">
				<display:column escapeXml="true" property="statusDate"
					title="Status Date" sortable="false" />
				<display:column escapeXml="true" property="statusCode"
					title="Status" sortable="false" />
				<display:column escapeXml="true" property="comments"
					title="Comments" sortable="false" />
				<display:column escapeXml="true" property="updatedBy"
					title="Deleted By" sortable="false" />
				<display:column property="updatedOn"
					format="{0,date,MM/dd/yyyy hh:mm aaa}" title="Deleted On"
					sortable="false" />
				<display:column titleKey="studyOverallStatus.actions"
					sortable="false">
					<a href="javascript:void(0);"
						onclick="auditTrail(<s:property value="%{#attr.del.id}"/>,
                               '<s:property value="%{#attr.del.statusCode}"/>');">
						<img src='<c:url value="/images/ico_audittrail.png"/>'
						alt="Audit trail icon"
						title="Click to view Audit Trail Information for this status record"
						width="16" height="16" />
					</a>
					<c:if test="${not empty del.reason}">
						<a href="javascript:void(0);"
							title="Why study stopped: <c:out value="${del.reason}"/>"> <img
							src='<c:url value="/images/ico_callout.png"/>'
							alt="Why Study Stopped?" width="16" height="16" /></a>
					</c:if>
				</display:column>
			</display:table>
		</c:if>
	</div>
	<div id="displaySuAbstractorAutoCheckoutMessage"
		title="Trial Status Validation" style="display: none;">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 15px 0;"></span> The system has
			checked-out this trial under your name because Trial Status
			Transition errors were found. Trial record cannot be checked-in until
			all Status Transition Errors have been resolved. Please use the Trial
			Status History button to review and make corrections, or Cancel to
			dismiss this message.
		</p>
	</div>

	<div id="audit-trail" title="Study Status Record Audit Information">
		<div class="label">Status: <span id="auditedStatus"></span></div>
		<br />
		<div>
			<table id="audit-trail-table" class="data" width="100%">
				<thead>
					<tr>
						<th>Date</th>
						<th>User</th>
						<th>Change Type</th>
						<th>Changes</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<jsp:include page="/WEB-INF/jsp/common/misc.jsp" />

</body>
</html>