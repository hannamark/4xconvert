<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:hidden name="trialDTO.sponsorIdentifier" id="trialDTO.sponsorIdentifier"/>
<s:hidden name="trialDTO.sponsorName" id="trialDTO.sponsorName" />
<s:hidden name="trialDTO.responsiblePartyType" id="trialDTO.responsiblePartyType" />
<s:hidden name="trialDTO.responsiblePersonIdentifier" id="trialDTO.responsiblePersonIdentifier"/>
<s:hidden name="trialDTO.responsiblePersonName" id="trialDTO.responsiblePersonName"/>
<s:hidden name="trialDTO.responsiblePersonTitle" id="trialDTO.responsiblePersonTitle"/>
<s:hidden name="trialDTO.responsiblePersonAffiliationOrgName" id="trialDTO.responsiblePersonAffiliationOrgName"/>
<s:hidden name="trialDTO.responsiblePersonAffiliationOrgId" id="trialDTO.responsiblePersonAffiliationOrgId"/>        

<c:if test="${trialDTO.xmlRequired == true}">
	<div class="accordion">
	<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section4"><fmt:message key="view.trial.sponsorResParty"/><span class="required">*</span></a></div>
	<div id="section4" class="accordion-body in">
	<div class="container">
      <reg-web:valueRowDiv labelKey="view.trial.sponsor" noLabelTag="true" required="true">
          <c:out value="${trialDTO.sponsorName}"/>
      </reg-web:valueRowDiv>
      <reg-web:valueRowDiv labelKey="view.trial.respParty" noLabelTag="true" required="true">
          <c:out value="${func:capitalize(trialDTO.responsiblePartyTypeReadable)}"/>
      </reg-web:valueRowDiv>
      <c:if test="${fn:trim(trialDTO.responsiblePersonName) != ''}">
          <reg-web:valueRowDiv labelKey="view.trial.respParty.investigator" noLabelTag="true" required="true">
              <c:out value="${trialDTO.responsiblePersonName}"/>
          </reg-web:valueRowDiv>
      </c:if>  
      <c:if test="${fn:trim(trialDTO.responsiblePersonTitle) != ''}">
          <reg-web:valueRowDiv labelKey="view.trial.respParty.investigatorTitle" noLabelTag="true"  required="true">
              <c:out value="${trialDTO.responsiblePersonTitle}"/>
          </reg-web:valueRowDiv>
      </c:if>
      <c:if test="${fn:trim(trialDTO.responsiblePersonAffiliationOrgName) != ''}">
          <reg-web:valueRowDiv labelKey="view.trial.respParty.investigatorAff" noLabelTag="true" required="true">
              <c:out value="${trialDTO.responsiblePersonAffiliationOrgName}"/>
          </reg-web:valueRowDiv>
      </c:if>
	</div>
	</div>
	</div>                                  
</c:if>