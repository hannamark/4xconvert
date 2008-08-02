<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<tr>
<th colspan="2"><fmt:message key="trialSummary.title"/></th>
</tr>
<tr>
<td scope="row" class="label" nowrap>
    <label for="nciAccessionNumber"> 
        <fmt:message key="studyProtocol.nciIdentifier"/>
    </label>
</td>
<td >
     <c:out value="${sessionScope.trialSummary.nciIdentifier }"/>
</td>
</tr>
<tr>
<td scope="row" class="label" nowrap>
    <label for="officialTitle"> 
        <fmt:message key="studyProtocol.officialTitle"/>
    </label>
</td>
<td >
    <c:out value="${sessionScope.trialSummary.officialTitle }"/>
</td>
</tr>       
<tr>
<td scope="row" class="label" nowrap>
    <label for="leadOrg"> 
        <fmt:message key="studyProtocol.leadOrganization"/>
    </label>
</td>
<td >
     <c:out value="${sessionScope.trialSummary.leadOrganizationName }"/>
</td>
</tr>       
<tr>
    <td scope="row" class="label" nowrap>
        <label for="principalInvestigator"> 
            <fmt:message key="studyProtocol.principalInvestigator"/>
        </label>
    </td>           
    <td >
        <c:out value="${sessionScope.trialSummary.piFullName }"/>
    </td>


</tr>
<tr>
    <td scope="row" class="label" nowrap>
        <label for="principalInvestigator"> 
            <fmt:message key="studyProtocol.studyStatus"/>
        </label>
    </td>           
    <td >
        <c:out value="${sessionScope.trialSummary.studyStatusCode.code }"/>
    </td>


</tr>
<tr>
    <td scope="row" class="label" nowrap>
        <label for="principalInvestigator"> 
            <fmt:message key="studyProtocol.documentWorkflowStatus"/>
        </label>
    </td>           
    <td >
        <c:out value="${sessionScope.trialSummary.documentWorkflowStatusCode.code }"/>
    </td>


</tr>