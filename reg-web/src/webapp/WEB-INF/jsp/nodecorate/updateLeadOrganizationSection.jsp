<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:hidden name="trialDTO.leadOrganizationIdentifier" id="trialDTO.leadOrganizationIdentifier"/>
<s:hidden name="trialDTO.leadOrganizationName" id="trialDTO.leadOrganizationName"/>
<s:hidden name="trialDTO.piIdentifier" id="trialDTO.piIdentifier"/>
<s:hidden name="trialDTO.piName" id="trialDTO.piName"/>
<div class="accordion">
<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section4"><fmt:message key="submit.trial.leadOrgInvestigator"/><span class="required">*</span></a></div>
<div id="section4" class="accordion-body in">
<div class="container">
<div class="form-group">
	<label class="col-xs-4 control-label"> 
        <fmt:message key="submit.trial.leadOrganization"/><span class="required">*</span>
    </label>
    <div class="col-xs-4">
        <s:property value="trialDTO.leadOrganizationName"/>
    </div>
</div>
	<label class="col-xs-4 control-label">
        <fmt:message key="submit.trial.principalInvestigator"/><span class="required">*</span></label>
    <div class="col-xs-4">
        <s:property value="trialDTO.piName"/>
    </div>
</div>