<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<tr style="display:none">
    <td>
        <s:hidden name="trialDTO.sponsorIdentifier" id="trialDTO.sponsorIdentifier"/>
        <s:hidden name="trialDTO.sponsorName" id="trialDTO.sponsorName" />
        <s:hidden name="trialDTO.responsiblePartyType" id="trialDTO.responsiblePartyType" />
        <s:hidden name="trialDTO.responsiblePersonIdentifier" id="trialDTO.responsiblePersonIdentifier"/>
        <s:hidden name="trialDTO.responsiblePersonName" id="trialDTO.responsiblePersonName"/>
        <s:hidden name="trialDTO.responsiblePersonTitle" id="trialDTO.responsiblePersonTitle"/>
        <s:hidden name="trialDTO.responsiblePersonAffiliationOrgName" id="trialDTO.responsiblePersonAffiliationOrgName"/>
        <s:hidden name="trialDTO.responsiblePersonAffiliationOrgId" id="trialDTO.responsiblePersonAffiliationOrgId"/>        
    </td>
</tr>

<c:if test="${trialDTO.xmlRequired == true}">
      <reg-web:titleRow titleKey="view.trial.sponsorResParty"/>
      <reg-web:valueRow labelKey="view.trial.sponsor" noLabelTag="true" required="true">
          <c:out value="${trialDTO.sponsorName}"/>
      </reg-web:valueRow>
      <reg-web:valueRow labelKey="view.trial.respParty" noLabelTag="true" required="true">
          <c:out value="${func:capitalize(trialDTO.responsiblePartyTypeReadable)}"/>
      </reg-web:valueRow>
      <c:if test="${fn:trim(trialDTO.responsiblePersonName) != ''}">
          <reg-web:valueRow labelKey="view.trial.respParty.investigator" noLabelTag="true" required="true">
              <c:out value="${trialDTO.responsiblePersonName}"/>
          </reg-web:valueRow>
      </c:if>  
      <c:if test="${fn:trim(trialDTO.responsiblePersonTitle) != ''}">
          <reg-web:valueRow labelKey="view.trial.respParty.investigatorTitle" noLabelTag="true"  required="true">
              <c:out value="${trialDTO.responsiblePersonTitle}"/>
          </reg-web:valueRow>
      </c:if>
      <c:if test="${fn:trim(trialDTO.responsiblePersonAffiliationOrgName) != ''}">
          <reg-web:valueRow labelKey="view.trial.respParty.investigatorAff" noLabelTag="true" required="true">
              <c:out value="${trialDTO.responsiblePersonAffiliationOrgName}"/>
          </reg-web:valueRow>
      </c:if>                                  
      <reg-web:spaceRow/>
</c:if>