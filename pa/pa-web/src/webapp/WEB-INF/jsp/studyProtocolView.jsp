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
            </table>  
<!--        
 <div class="actionsrow">
	<del class="btnwrapper">
		<ul class="btnrow">
			<li><a href="studyProtocolEdit.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="btn" onclick="this.blur();"><span class="btn_img"><span class="edit">Edit</span></span></a></li>
		</ul>	
	</del>
</div>
-->
                  
    </s:form>
   </div>

 </body>
 </html>