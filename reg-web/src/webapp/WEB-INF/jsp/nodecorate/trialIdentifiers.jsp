    <%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
    <tr>
            <th colspan="2"><fmt:message key="view.trial.trialIDs"/></th>
          </tr>
          <tr><td colspan="2" class="space">&nbsp;</td></tr>
          <tr>
                <td scope="row" class="label">
                    <label for="submitTrial_participationWebDTO_localProtocolIdentifier"> <fmt:message key="submit.trial.leadOrgidentifier"/><span class="required">*</span></label>
                </td>
                <td>
                    <s:textfield name="trialDTO.leadOrgTrialIdentifier"  maxlength="200" size="100"  cssStyle="width:200px"  />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.leadOrgTrialIdentifier</s:param>
                       </s:fielderror>                            
                     </span>
                </td>                
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="submitTrial_participationWebDTO_nctNumber"> <fmt:message key="submit.trial.nctNumber"/></label>
                </td>
                <td>
                    <s:textfield name="trialDTO.nctIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>                
          </tr>
          