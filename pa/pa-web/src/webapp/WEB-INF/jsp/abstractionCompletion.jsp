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

    <title>Abstraction Validation</title>
    <s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator) 
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page' 
}

function generateReport(pid) {
    //showPopWin('/pa/protected/ajaxCTGovgenerateXML.action?studyProtocolId='+pid, 900, 400, '', 'CLinical Trial XML Generation');
   document.aForm.target = "CLinical Trial XML Generation";
   document.aForm.action = "/pa/protected/ajaxCTGovgenerateXML.action?studyProtocolId="+pid;
   document.aForm.submit();
}
function generateTSR() {
   document.aForm.target = "TSR";
   document.aForm.action = "/pa/protected/ajaxAbstractionCompletionviewTSR.action";
   document.aForm.submit();
   
}
function generateTSRWord() {
  document.aForm.target = "TSR";
   document.aForm.action = "/pa/protected/ajaxAbstractionCompletionviewTSRWord.action";
   document.aForm.submit();

}
</SCRIPT>

<body>
<c:set  var="topic" scope="request" value="validate_abstract"/>
 <h1>Abstraction Validation</h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form name="aForm"><s:actionerror/>    
    <h2>
    <s:if test="%{abstractionError==true}">                
        Abstraction validation failed. Please check error(s).
    </s:if> 
    <s:if test="%{abstractionError==false}">
        Abstraction is valid.             
    </s:if>
    
    </h2>
    <s:if test="abstractionList != null">    
    <s:set name="abstractionList" value="abstractionList" scope="request"/>
    <display:table name="abstractionList" id="row" class="data" sort="list"  pagesize="30" requestURI="abstractionCompletionquery.action" export="false">
	    <display:column title="Type" property="errorType"  sortable="true" headerClass="sortable" />
    	<display:column title="Description" property="errorDescription" sortable="true" headerClass="sortable" />
	    <display:column title="Comment" property="comment" sortable="true" headerClass="sortable" />	
	</display:table>
	</s:if>
	<div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <s:if test="abstractionError == false">
                 <c:if test="${sessionScope.trialSummary.isProprietaryTrial == null || sessionScope.trialSummary.isProprietaryTrial == 'false'}">                                    
                        <li><a href="#" class="btn" onclick="generateReport('${sessionScope.trialSummary.studyProtocolId}');"><span class="btn_img"><span class="save">View XML</span></span></a></li>
                    </c:if>                                  
                   <!-- <li><a href="#" class="btn" onclick="generateReport('${sessionScope.trialSummary.studyProtocolId}');"><span class="btn_img"><span class="save">View XML</span></span></a></li>
                    <li><a href="#"  class="btn" onclick="generateTSR();"><span class="btn_img"><span class="save">View TSR</span></span></a></li> -->
                     <li><a href="#"  class="btn" onclick="generateTSRWord();"><span class="btn_img"><span class="save">View TSR</span></span></a></li>                                
                </s:if>
            </ul>   
        </del>
    </div> 

    
                   
    </s:form>
   </div>
 </body>
 </html>
 