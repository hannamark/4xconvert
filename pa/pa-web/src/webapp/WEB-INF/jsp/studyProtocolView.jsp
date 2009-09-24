<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="studyProtocol.view.title"/></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
    <script type="text/javascript"> 
    function tooltip() {
		BubbleTips.activateTipOn("acronym");
		BubbleTips.activateTipOn("dfn"); 
	}
	function handleAction(){
	var studyProtocolId;
	studyProtocolId='${sessionScope.trialSummary.studyProtocolId }';
	input_box=confirm("Click OK to save changes or Cancel to Abort.");
	if (input_box==true){
	 		document.forms[0].action="studyProtocolcheckout.action?studyProtocolId="+studyProtocolId;
	 		document.forms[0].submit(); 
	 }
} 
	</SCRIPT>
</head>

<body onload="setFocusToFirstControl();">
 <c:set var="topic" scope="request" value="trial_details"/>
<!-- <div id="contentwide"> -->
 <h1>Trial Identification</h1>

<!--Help Content-->
   <!--  <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
   <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>

  <div class="box">
    <s:form ><s:actionerror/>
	<h2>Trial Identification</h2>

        <table class="form">
         
            <tr>
            <td scope="row" class="label">
                <label for="nciAccessionNumber">                
                    <fmt:message key="studyProtocol.nciIdentifier"/>
                </label>
            </td>
            <td class="value">
                <c:out value="${sessionScope.trialSummary.nciIdentifier }"/> 
            </td>
            </tr>
            <tr>
            <td scope="row" class="label">
                <label for="localProtocolIdentifer"> 
                    <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/>
                </label>
            </td>
            <td class="value">
             <c:out value="${sessionScope.trialSummary.localStudyProtocolIdentifier }"/> 
            </td>
            </tr>  
            <tr>
            <td scope="row" class="label">
                <label for="leadOrg"> 
                    <fmt:message key="studyProtocol.leadOrganization"/>
                </label>
            </td>
            <td class="value">
                <c:out value="${sessionScope.trialSummary.leadOrganizationName }"/> 
            </td>
            </tr> 
            <tr>
            <td scope="row" class="label">
                <label for="leadOrg"> 
                    <fmt:message key="studyProtocol.proprietaryTrial"/>
                </label>
            </td>
            <td class="value">
                <c:out value="${sessionScope.trialSummary.isProprietaryTrial }"/> 
            </td>
            </tr> 
            <tr>     
            <td scope="row" class="label">
                <label for="officialTitle">
                    <fmt:message key="studyProtocol.officialTitle"/>
                </label>
            </td>
            <td class="value">
                 <c:out value="${sessionScope.trialSummary.officialTitle }"/> 
            </td>
            </tr> 
             <tr>     
            <td scope="row" class="label">
                <label for="checkOutStatus">
                    <fmt:message key="studyProtocol.checkOutStatus"/>
                </label>
            </td>
            <td class="value">
            	<c:if test="${(sessionScope.trialSummary.studyCheckoutBy == null)
            						|| (sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)}">
                 <s:select  name="checkoutStatus" list="#{'false':'Check-In', 'true':'Check-Out'}" />
                 </c:if>
                 <c:if test="${sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName != sessionScope.trialSummary.studyCheckoutBy}">
                 		<b><c:out value="${sessionScope.trialSummary.studyCheckoutBy }"/> </b>
                 </c:if>
            </td>
            </tr>     
            </table>  
        <c:if test="${(sessionScope.trialSummary.studyCheckoutBy == null) || (sessionScope.trialSummary.studyCheckoutBy == sessionScope.loggedUserName)}">
 <div class="actionsrow">
	<del class="btnwrapper">
		<ul class="btnrow">
			<li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>
		</ul>	
	</del>
</div>
</c:if>
                  
    </s:form>
   </div>

 </body>
 </html>