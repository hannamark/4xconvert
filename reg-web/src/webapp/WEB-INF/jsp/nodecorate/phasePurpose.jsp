<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
          <tr>
                <td  scope="row" class="label">
                    <reg-web:displayTooltip tooltip="tooltip.phase">
                        <label for="submitTrial_protocolWebDTO_trialPhase"> <fmt:message key="submit.trial.phase"/><span class="required">*</span></label>
                    </reg-web:displayTooltip>
                </td>

                <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
                <td>
                    <s:select headerKey="" headerValue="--Select--" name="trialDTO.phaseCode" list="#phaseCodeValues" cssStyle="width:206px" value="trialDTO.phaseCode"/>
                    <span class="formErrorMsg">
                        <s:fielderror>
                        <s:param>trialDTO.phaseCode</s:param>
                       </s:fielderror>
                     </span>
                </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="submitTrial_protocolWebDTO_otherPhaseText"> <fmt:message key="submit.trial.otherPhaseText"/></label>
                </td>
                <td>
                    <s:set name="phaseAdditionlQualiefierCodeValues" value="@gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode@getDisplayNames()" />
                    <s:select headerKey="" headerValue="" name="trialDTO.phaseAdditionalQualifier" list="#phaseAdditionlQualiefierCodeValues"
                    value="trialDTO.phaseAdditionalQualifier" cssStyle="width:120px" />
                </td>
          </tr>
                    <tr>
                <td  scope="row" class="label">
                    <label for="trialType"> <fmt:message key="submit.trial.type"/><span class="required">*</span></label>
                </td>
                <td>
                    <input type="radio" name="trialDTO.trialType" value="Interventional" checked="checked"> Interventional
                    <input type="radio" name="trialDTO.trialType" value="Observational" disabled="disabled"> Observational
                     <span class="formErrorMsg">
                        <s:fielderror>
                        <s:param>trialDTO.trialType</s:param>
                       </s:fielderror>
                     </span>
                </td>
          </tr>
          <%@ include file="/WEB-INF/jsp/nodecorate/primaryPurposeOther.jsp" %>