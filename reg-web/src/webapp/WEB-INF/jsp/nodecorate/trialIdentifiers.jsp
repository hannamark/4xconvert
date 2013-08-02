<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:titleRow titleKey="view.trial.trialIDs"/>
<reg-web:spaceRow/>
<reg-web:valueRow labelFor="trialDTO.leadOrgTrialIdentifier" labelKey="submit.trial.leadOrgidentifier" required="true" tooltip="tooltip.trial_id">
    <s:textfield id="trialDTO.leadOrgTrialIdentifier" name="trialDTO.leadOrgTrialIdentifier" maxlength="30" size="100"  cssStyle="width:200px" cssClass="charcounter"/>
    <span class="formErrorMsg">
        <s:fielderror>
            <s:param>trialDTO.leadOrgTrialIdentifier</s:param>
       </s:fielderror>
     </span>
</reg-web:valueRow>   
<reg-web:valueRow labelFor="trialDTO.nctIdentifier" labelKey="submit.trial.nctNumber" tooltip="tooltip.nct_number">
    <s:textfield id="trialDTO.nctIdentifier" name="trialDTO.nctIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
    <span class="formErrorMsg">
       <s:fielderror cssStyle = "white-space:pre-line;">
            <s:param>trialDTO.nctIdentifier</s:param>
       </s:fielderror>
    </span> 
</reg-web:valueRow>          
