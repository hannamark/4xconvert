<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="nciSpecificInformation.title"/></title>
    <s:head />
</head>

<body onload="setFocusToFirstControl();">
 <h1><fmt:message key="nciSpecificInformation.title" /></h1>

   <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>

  <div class="box">
    <s:form >
        <s:actionerror/>
        <pa:studyUniqueToken/>
	<h2>Trial Identification</h2>

        <table class="form">
        
             <c:if test="${sessionScope.trialSummary  != null}">
                <jsp:include page="/WEB-INF/jsp/trialDetailSummaryEdit.jsp"/> 
            </c:if> 

        </table>  
 <div class="actionsrow">
	<del class="btnwrapper">
		<ul class="btnrow">
			<li><a href="studyProtocolview.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="save">Save</span></span></a></li>
			<li><a href="studyProtocolview.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="btn" onclick="this.blur();"><span class="btn_img"><span class="cancel">Cancel</span></span></a></li>
			<li><a href="nciSpecificInformationquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
		</ul>	
	</del>
</div>

                  
    </s:form>
   </div>
 </body>
 </html>