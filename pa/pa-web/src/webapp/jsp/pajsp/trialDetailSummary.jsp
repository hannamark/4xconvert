<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<%@ page import = "gov.nih.nci.pa.dto.StudyProtocolQueryDTO" %>
<!-- <tr>
<th colspan="2"><fmt:message key="trialSummary.title"/></th>
</tr> -->
<tr>
<td scope="row" class="label">
    <label for="nciAccessionNumber">
    <dfn title="Context sensitive help text or tooltip here."> 
        <fmt:message key="studyProtocol.nciIdentifier"/>
     </dfn>
    </label>
</td>
<td class="value">
	<c:out value="${sessionScope.trialSummary.nciIdentifier }"/> 
</td>
</tr>
<tr>
<td scope="row" class="label">
	<label for="nct">
	<dfn title="Context sensitive help text or tooltip here.">NCT Number:
	</dfn></label>
</td>
<td class="value"></td>
</tr>
<tr>
<td scope="row" class="label">
    <label for="localProtocolIdentifer"> 
    <dfn title="Context sensitive help text or tooltip here.">
        <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/>
    </dfn>
    </label>
</td>
<td class="value"></td>
</tr>       
<tr>
<tr>
	<td scope="row" class="label"><label for="sectrialid"><dfn title="Context sensitive help text or tooltip here.">Secondary Trial Identifier:</dfn></label></td>
	<td class="value"></td>
</tr>
<tr>
<td scope="row" class="label">
    <label for="leadOrg"> 
    <dfn title="Context sensitive help text or tooltip here.">
        <fmt:message key="studyProtocol.leadOrganization"/>
    </dfn>
    </label>
</td>
<td class="value">
    <c:out value="${sessionScope.trialSummary.leadOrganizationName }"/> 
</td>
</tr>      
<td scope="row" class="label">
    <label for="officialTitle">
    <dfn title="Context sensitive help text or tooltip here."> 
        <fmt:message key="studyProtocol.officialTitle"/>
     </dfn>
    </label>
</td>
<td class="value">
     <c:out value="${sessionScope.trialSummary.officialTitle }"/> 
</td>
</tr>       
 <tr>
	<td scope="row" class="label"><label for="acronym"><dfn title="Context sensitive help text or tooltip here.">Acronym:</dfn></label></td>
	<td class="value"></td>
</tr>
<!--<tr>
    <td scope="row" class="label">
        <label for="principalInvestigator"> 
        <dfn title="Context sensitive help text or tooltip here.">
            <fmt:message key="studyProtocol.principalInvestigator"/>
        </dfn>
        </label>
    </td>           
    <td class="value">
         <c:out value="${sessionScope.trialSummary.piFullName }"/> 
    </td>


</tr>
<tr>
    <td scope="row" class="label">
        <label for="principalInvestigator"> 
        <dfn title="Context sensitive help text or tooltip here.">
            <fmt:message key="studyProtocol.studyStatus"/>
   		</dfn>    
        </label>
    </td>           
    <td class="value">
         <c:out value="${sessionScope.trialSummary.studyStatusCode.code }"/> /
    </td>


</tr>
<tr>
    <td scope="row" class="label">
        <label for="principalInvestigator">
        <dfn title="Context sensitive help text or tooltip here."> 
            <fmt:message key="studyProtocol.documentWorkflowStatus"/>
        </dfn>
        </label>
    </td>           
    <td class="value">
         <c:out value="${sessionScope.trialSummary.documentWorkflowStatusCode.code }"/> /
    </td>


</tr>-->