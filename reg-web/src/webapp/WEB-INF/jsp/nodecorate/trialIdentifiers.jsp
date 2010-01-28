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
                    <s:textfield name="trialDTO.localProtocolIdentifier"  maxlength="200" size="100"  cssStyle="width:200px"  />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.localProtocolIdentifier</s:param>
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
          <tr>
                <td scope="row" class="label">
                    <label for="submitTrial_CTEP_Identifier"> <fmt:message key="submit.trial.ctepIdentifier"/></label>
                </td>
                <td>
                    <s:textfield name="trialDTO.ctepIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>                
          </tr>
          
          <tr>
                <td scope="row" class="label">
                    <label for="submitTrial_DCP_Identifier"> <fmt:message key="submit.trial.dcpIdentifier"/></label>
                </td>
                <td>
                    <s:textfield name="trialDTO.dcpIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>                
          </tr>