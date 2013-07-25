<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/nodecorate/phase.jsp" %>
<tr>
    <td  scope="row" class="label-noinput">
        <fmt:message key="submit.trial.type"/><span class="required">*</span>
    </td>
    <td>
        <input type="radio" name="trialDTO.trialType" value="Interventional" id="trialDTO.trialType.Interventional"
            ${trialDTO.trialType!='NonInterventional'?'checked=checked':''}        
            onclick="hidePrimaryCompletionDate(), setDisplayBasedOnTrialType();"> <label for = "trialDTO.trialType.Interventional">Interventional</label>
        <input type="radio" name="trialDTO.trialType" value="NonInterventional" id="trialDTO.trialType.Noninterventional"
            ${trialDTO.trialType=='NonInterventional'?'checked=checked':''}
            onclick="hidePrimaryCompletionDate(), setDisplayBasedOnTrialType();"><label for = "trialDTO.trialType.Noninterventional">Non-interventional</label>
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>trialDTO.trialType</s:param>
            </s:fielderror>
        </span>
    </td>
</tr>

<tr class="non-interventional">        
      <td  scope="row" class="label">                    
         <label for="trialDTO.studySubtypeCode"><fmt:message key="submit.trial.studySubtypeCode"/><span class="required">*</span></label>                    
      </td>
      <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.StudySubtypeCode@getDisplayNames()" />
      <td>
          <s:select headerKey="" headerValue="--Select--" id ="trialDTO.studySubtypeCode" name="trialDTO.studySubtypeCode" list="#typeCodeValues"  cssStyle="width:206px" 
                    value="trialDTO.studySubtypeCode"/>
           <span class="formErrorMsg">
              <s:fielderror>
              <s:param>trialDTO.studySubtypeCode</s:param>
             </s:fielderror>
           </span>
      </td>
</tr>


<%@ include file="/WEB-INF/jsp/nodecorate/primaryPurposeOther.jsp" %>