 <!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set scope="request" var="disableDefaultJQuery" value="${true}" />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="reportCoverSheet.title"/></title>
	
	<link href="${scriptPath}/js/jquery-ui-1.11.2.custom/jquery-ui.css"
    rel="stylesheet" media="all" type="text/css" />
<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css"
    href="${scriptPath}/js/DataTables-1.10.4/media/css/jquery.dataTables.min.css">
    
 <style type="text/css">
fieldset>label,fieldset>input{
    display: block;
}

fieldset>label {
    font-weight: bold !important;
    text-align: left;
}

fieldset>input:not([id="actionCompletionDate"]):not([id="actionCompletionDateChangeType"]) {
    margin-bottom: 12x;
    width: 95%;
    padding: .4em;
}

fieldset {
    padding: 0;
    border: 0 !important;
    margin-top: 25px;
}

.ui-dialog .ui-state-error {
    padding: .3em;
}

fieldset>input[id="actionCompletionDate"] {
  display:inline-block;
}
fieldset>input[id="actionCompletionDateChangeType"] {
  display:inline-block;
}

</style>   
    
    <script type="text/javascript"
    src="${scriptPath}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
    src="${scriptPath}/js/jquery-ui-1.11.2.custom/jquery-ui.min.js"></script>
<script type="text/javascript"
    src="${scriptPath}/js/resultscoversheet/resultsCoverSheet.js"></script>
<!-- DataTables -->
<script type="text/javascript" charset="utf8"
    src="${scriptPath}/js/DataTables-1.10.4/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
</head>

<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}


//script needed for cover sheet screens
var table;
var editFunction;
var editFunctionChangeType;
var studyRecordChangeTable;
jQuery(document).ready(function() {
    
	
	
	table =initSectionDataTable('dataDisc');
	studyRecordChangeTable = initSectionDataTable('recordChanges');
	
	editFunction =initTableEditFunction('dataDisc' ,'disc' , table) ;
	editFunctionChangeType = initTableEditFunction('recordChanges' ,'studyRecord' , studyRecordChangeTable) ;
	
	initDeleteFunction("deleteBtn" , "dataDisc","recordChanges" , "trialViewdelete.action","disc");
	initDeleteFunction("deleteBtnChangeType" , "recordChanges" ,"dataDisc" , "trialViewdelete.action","studyrecord");
	
	//set url to be submitted in case of add/edit
    setCoverSheetUrls("trialViewaddOrEdit.action" ,"trialViewsuccessfulAdd.action",
    "trialViewaddOrEditRecordChange.action", "trialViewsuccessfulAddRecordChange.action",
    "trialViewsaveFinalChanges.action", "trialViewsendConverSheetEmail.action")
	
   

});






addCalendar("Cal1", "Select Date", "actionCompletionDate", "addDiscrepancyForm");
addCalendar("Cal2", "Select Date", "actionCompletionDateChangeType", "studyRecordChangesForm");
addCalendar("Cal3", "Select Date", "designeeAccessRevokedDate", "coverSheetForm");
addCalendar("Cal4", "Select Date", "changesInCtrpCtGovDate", "coverSheetForm");
setFormat("mm/dd/yyyy");

</SCRIPT>
</head>
 <body>

 <h1><fmt:message key="trialView.title"/></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
 <div class="box">
    <pa:sucessMessage/>
    <pa:failureMessage/>
    <s:form name="coverSheetForm" id ="coverSheetForm" action="">
        <s:actionerror/>
        <pa:studyUniqueToken/>
        
        <!-- jsp for cover sheet -->
        <jsp:include page="/WEB-INF/jsp/administrative/reportingCoverSheet/reportCoverSheetDiscType.jsp"/>
        <jsp:include page="/WEB-INF/jsp/administrative/reportingCoverSheet/reportCoverSheetStudyRecord.jsp"/>
         <jsp:include page="/WEB-INF/jsp/administrative/reportingCoverSheet/reportCoverSheetFinalCleanup.jsp"/>             
         
	</s:form>
	
	<!-- jsp for cover sheet -->
	<jsp:include page="/WEB-INF/jsp/administrative/reportingCoverSheet/reportCoverSheetDisTypeDialog.jsp"/>
      <jsp:include page="/WEB-INF/jsp/administrative/reportingCoverSheet/reportCoverSheetStudyRecordDialog.jsp"/> 
         
         
    
</div>
	 
 </body>
 </html>
