<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>             
          <tr>
                <td  scope="row" class="label">
                    <label for="submitTrial_protocolWebDTO_trialPhase"> <fmt:message key="submit.trial.phase"/><span class="required">*</span></label> 
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
          <tr>
                <td  scope="row" class="label">
                    <label for="submitTrial_protocolWebDTO_trialPurpose"><fmt:message key="submit.trial.purpose"/><span class="required">*</span></label> 
                </td>
                    <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="--Select--" name="trialDTO.primaryPurposeCode" list="#typeCodeValues"  cssStyle="width:206px" value="trialDTO.primaryPurposeCode"/>
                     <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.primaryPurposeCode</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="submitTrial_protocolWebDTO_otherPurposeText"> <fmt:message key="submit.trial.otherPurposeText"/></label>

                </td>
                <td>
                    <s:set name="primaryPurposeAdditionlQualiefierCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode@getDisplayNames()" />
                    <s:select headerKey="" headerValue="" name="trialDTO.primaryPurposeAdditionalQualifierCode" list="#primaryPurposeAdditionlQualiefierCodeValues" 
                    value="trialDTO.primaryPurposeAdditionalQualifierCode" cssStyle="width:120px" />
                </td>                
          </tr>          
          