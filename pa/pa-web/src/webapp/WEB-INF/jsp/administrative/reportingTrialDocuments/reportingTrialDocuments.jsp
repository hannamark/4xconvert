 <!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set scope="request" var="disableDefaultJQuery" value="${true}" />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="trialDocument.title"/></title>

    <script type="text/javascript"
    src="${scriptPath}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
    src="${scriptPath}/js/jquery-ui-1.11.2.custom/jquery-ui.min.js"></script>
	<link href="${scriptPath}/js/jquery-ui-1.11.2.custom/jquery-ui.css"
    rel="stylesheet" media="all" type="text/css" />
	<script type="text/javascript"   src="${scriptPath}/js/resultsDocuments/resultsDocuments.js"></script>
</head>

<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}

setDocumentPageUrl("resultsReportingDocumentinput.action?parentPage=coverSheet","resultsReportingDocumentedit.action?parentPage=coverSheet",
		"resultsReportingDocumentdelete.action","resultsReportingDocumentreviewCtro.action","resultsReportingDocumentreviewCcct.action");


</SCRIPT>
 <body>

 <h1><fmt:message key="reportTrialDocument.title"/></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
 <div class="box">
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:set name="requestUrl" value="resultsReportingDocumentquery.action" />
    
    <jsp:include page="reportingTrialDocumentsList.jsp"/>
   
	</div>
	
 </body>
 </html>
