<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/showhide.js"/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
    <link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all"/>

    <title>Abstraction Completion</title>
    <s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">

function generateReport(pid) {
    showPopWin('/pa/protected/ajaxCTGovgenerateXML.action?studyProtocolId='+pid, 1050, 400, '', 'CLinical Trial XML Generation');
}

</SCRIPT>

<body onload="setFocusToFirstControl();">
 <h1>Abstraction Completion</h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form><s:actionerror/>    
    <h2>
    <s:if test="${abstractionError==true}">                
        Abstraction Completion  has Errors, please correct it
    </s:if> 
    <s:elseif test="${abstractionError==false}">
        No Abstraction Completion Errors found             
    </s:elseif>
    
    </h2>
    <display:table name="${abstractionList}" id="row" class="data" sort="list"  pagesize="30" requestURI="abstractionCompletionquery.action" export="false">
    	<display:column title="errorDescription" property="errorDescription" sortable="true" headerClass="sortable" />
	    <display:column title="errorType" property="errorType"  sortable="true" headerClass="sortable" />
	    <display:column title="comment" property="comment" sortable="true" headerClass="sortable" />	
	</display:table>
	<div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <s:if test="${abstractionError==false}">                                        
                    <li><s:a href="#" cssClass="btn" onclick="generateReport('${sessionScope.trialSummary.studyProtocolId}');"><span class="btn_img"><span class="save">View XML</span></span></s:a></li>                                
                    <s:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code == 'Accepted'}">
                    <li><s:a href="abstractionCompletioncomplete.action" cssClass="btn"><span class="btn_img"><span class="save">Complete Abstraction</span></span></s:a></li>
                    </s:if>
                </s:if>
                
                <s:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code == 'Abstracted'}">
                <li><s:a href="abstractionCompletionverified.action" cssClass="btn"><span class="btn_img"><span class="save">Verify Abstraction</span></span></s:a></li>
                </s:if>
                <s:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code != 'Accepted'}">
                <li><s:a href="abstractionCompletionsendEmail.action" cssClass="btn"><span class="btn_img"><span class="save">Send Email</span></span></s:a></li>		    
				</s:if>     
            </ul>   
        </del>
    </div> 

    
                   
    </s:form>
   </div>
 </body>
 </html>