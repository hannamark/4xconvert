 <!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="trialDocument.title"/></title>
	<s:head />
	
</head>
<!-- DataTables -->
<script type="text/javascript" charset="utf8"
 src="${scriptPath}/js/DataTables-1.10.4/media/js/jquery.dataTables.min.js"></script>
<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css"
    href="${scriptPath}/js/DataTables-1.10.4/media/css/jquery.dataTables.min.css">
<style type="text/css">
table.dataTable tbody td {
    padding: 3px 3px;
}

</style>
<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
var speTable;
function callOnloadFunctions(){
	speTable = jQuery('#spe').DataTable({
        "paging":   true,
        "ordering": false,
        "info":     true,
        "bFilter" :false, 
        "columnDefs": [
                       { "visible": false, "targets": 13 }
                     ]
    });
	jQuery("#resolutionDate").datepicker();
	
	
}

function editActionTaken(id, cell) {
   var rowIdx = speTable.cell(cell).index().row;
   jQuery("#identifier").val(speTable.cell(rowIdx, 13).data());
   jQuery("#comment").val(speTable.cell(rowIdx, 5).data());
   jQuery("#errorType").val(speTable.cell(rowIdx, 6).data());
   jQuery("#cmsTicketId").val(speTable.cell(rowIdx, 7).data());
   jQuery("#actionTaken").val(speTable.cell(rowIdx, 8).data());
   jQuery("#resolutionDate").val(speTable.cell(rowIdx, 9).data());
   jQuery("#editActionTakenDialog").dialog({
	   title :"Add/Edit Actions Taken Record",
       autoOpen : false,
       height : 375,
       width : 500,
       modal : true,
       buttons : {
           "Save" : function() {
               jQuery("button.ui-button-text-only:first")
               .before(jQuery('#indicatorChangeType').show());
               jQuery.ajax(
                       {
                           type : "POST",
                           url : "resultsReportingActionsTakenupdateSpeAjax.action",
                           data : jQuery('#editActionTakenForm').serialize()
                       })
               
               .done(
                       function(data) {                            
                           if(data == 'success') {
                               jQuery("#editActionTakenDialog").dialog("close");
                               speTable.cell(rowIdx, 5).data(jQuery("#comment").val());
                               speTable.cell(rowIdx, 6).data(jQuery("#errorType").val());
                               speTable.cell(rowIdx, 7).data(jQuery("#cmsTicketId").val());
                               speTable.cell(rowIdx, 8).data(jQuery("#actionTaken").val());
                               speTable.cell(rowIdx, 9).data(jQuery("#resolutionDate").val());
                               speTable.cell(rowIdx, 10).data('<c:out value="${CsmHelper.username}"/>');
                               speTable.cell(rowIdx, 11).data(jQuery.datepicker.formatDate('mm/dd/yy',new Date()));
                           }else{
                               alert("Error updating study processing error actions, please refresh the page and try again: " +data);  
                           }
                       })
               .fail(
                       function(jqXHR, textStatus, errorThrown) {
                           alert("Error updating study processing error actions, please refresh the page and try again: " + errorThrown);
                       });
               
           },
           "Cancel" : function() {
               jQuery("#editActionTakenDialog").dialog("close");
           }
       } 
    }); 
   jQuery("#editActionTakenDialog").dialog("open");
}


</SCRIPT>
 <body>
 <div class="box">
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:actionerror/>
    <pa:studyUniqueToken/>
    <h2><fmt:message key="actionsTaken.title" /></h2>
    <s:if test="studyProcessingErrors != null">
       <s:set name="studyProcessingErrors" value="studyProcessingErrors" scope="request"/>
	   <display:table name="${studyProcessingErrors}" id="spe" class="data" sort="list"  pagesize="9999999"
	   requestURI="resultsReportingActionsTakenview.action" export="false"
	   decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator">
		    <display:column titleKey="actionsTaken.errorDate" property="errorDate" format="{0,date,MM/dd/yyyy}"/>
		    <display:column titleKey="actionsTaken.nciID" property="studyNCIId"/>
		    <display:column titleKey="actionsTaken.nctID" property="studyNCTId"/>
	        <display:column escapeXml="true" titleKey="actionsTaken.errorMessage" property="errorMessage" />
	        <display:column titleKey="actionsTaken.recurringError">
	          <c:out value="${spe.recurringError ? 'Yes' : 'No'}"/>
	        </display:column>
	        <display:column escapeXml="true" titleKey="actionsTaken.comments" property="comment" />
	        <display:column escapeXml="true" titleKey="actionsTaken.errorType" property="errorType" />
	        <display:column escapeXml="true" titleKey="actionsTaken.cmsTicketID" property="cmsTicketId" />
	        <display:column escapeXml="true" titleKey="actionsTaken.actionTaken" property="actionTaken" />
	        <display:column titleKey="actionsTaken.resolutionDate" property="resolutionDate" format="{0,date,MM/dd/yyyy}"/>
	        <display:column titleKey="actionsTaken.user" property="user"/>
	        <display:column titleKey="actionsTaken.date" property="date" format="{0,date,MM/dd/yyyy}"/>
	        <display:column title="Edit" class="action">
	              <s:a href="#" onclick="editActionTaken('%{#attr.spe.identifier}', this.parentElement)"><img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16"/></s:a>
	               
	    	 </display:column>
	    	 <display:column property="identifier"  class="hidden" headerClass="hidden"/>
	 	</display:table>
	</s:if>
   </div>
   <jsp:include page="editActionsTaken.jsp"/>
  </body>
 </html>
