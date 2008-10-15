<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="view.trial.page.title"/></title>
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
<!-- <div id="contentwide"> -->
 <h1><fmt:message key="view.trial.page.title" /></h1>

<!--Help Content-->
   <!--  <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->

  <div class="box">
    <s:form ><s:actionerror/>
    <h1><fmt:message key="submit.trial.success.message"/> <c:out value="${sessionScope.trialSummary.nciAccessionNumber }"/> </h1>
	<h2><fmt:message key="view.trial.trialDetails"/></h2>

        <table class="form">
            <tr>
	            <td scope="row" class="label">
	                <label for="nct">
	                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
	                   <fmt:message key="view.trial.nciAccessionNumber"/>
	                </dfn></label>
	            </td>
                <td class="value">
                    <c:out value="${sessionScope.trialSummary.nciAccessionNumber}"/> 
                </td>
            </tr>
            <tr>
            <tr>
                <td scope="row" class="label">
                    <label for="nct">
                    <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                        <fmt:message key="view.trial.leadOrgTrialIdentifier"/>
                    </dfn></label>
                </td>
                <td class="value">
                </td>
            </tr>
            <tr>     
            <td scope="row" class="label">
                <label for="officialTitle">
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    <fmt:message key="view.trial.title"/>
                 </dfn>
                </label>
            </td>
            <td class="value">
                 <c:out value="${sessionScope.trialSummary.trialTitle }"/> 
            </td>
            </tr>       
             <tr>
                <td scope="row" class="label"><label for="acronym"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">Acronym:</dfn></label></td>
                <td class="value"></td>
            </tr>
            <tr>            
            <td scope="row" class="label">
                <label for="trialPhase">
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    <fmt:message key="view.trial.phase"/>
                 </dfn>
                </label>
            </td>
            <td class="value">
                <c:out value="${sessionScope.trialSummary.trialPhase }"/> 
            </td>
            </tr>
            <tr>
            <td scope="row" class="label">
                <label for="leadOrg"> 
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                    <fmt:message key="view.trial.leadOrganization"/>
                </dfn>
                </label>
            </td>
            <td class="value">
            </td>
            </tr> 
            <tr>
            <td scope="row" class="label">
                <label for="leadOrg"> 
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                    <fmt:message key="view.trial.principalInvestigator"/>
                </dfn>
                </label>
            </td>
            <td class="value">
            </td>
            </tr>
            <s:if test="${trialFundingList} != null">
                <input type="hidden" name="page" />
                <input type="hidden" name="cbValue" />
	            <h2><fmt:message key="view.trial.grantInfo"/></h2>
				<display:table name="${trialFundingList}" id="row" class="data" sort="list"  pagesize="5" export="false">    
				<display:column titleKey="trialFunding.funding.mechanism" property="fundingMechanismCode" sortable="true" headerClass="sortable" />
				<display:column titleKey="trialFunding.institution.code" property="nihInstitutionCode" sortable="true" headerClass="sortable" />
				<display:column titleKey="trialFunding.serial.number" property="serialNumber"  sortable="true" headerClass="sortable" />
				<display:column titleKey="studyProtocol.monitorCode" property="nciDivisionProgramCode" sortable="true" headerClass="sortable" />
				</display:table>
		   </s:if>

        </table>  
                  
    </s:form>
   </div>

 </body>
 </html>