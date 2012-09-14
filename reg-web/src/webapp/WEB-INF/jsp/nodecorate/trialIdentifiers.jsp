<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:titleRow titleKey="view.trial.trialIDs"/>
<reg-web:spaceRow/>
<reg-web:valueRow labelFor="submitTrial_trialDTO_leadOrgTrialIdentifier" labelKey="submit.trial.leadOrgidentifier" required="true" tooltip="tooltip.trial_id">
    <s:textfield name="trialDTO.leadOrgTrialIdentifier"  maxlength="200" size="100"  cssStyle="width:200px"  />
    <span class="formErrorMsg">
        <s:fielderror>
            <s:param>trialDTO.leadOrgTrialIdentifier</s:param>
       </s:fielderror>
     </span>
</reg-web:valueRow>   
<reg-web:valueRow labelFor="submitTrial_trialDTO_nctIdentifier" labelKey="submit.trial.nctNumber" tooltip="tooltip.nct_number">
    <s:textfield name="trialDTO.nctIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
</reg-web:valueRow>          
