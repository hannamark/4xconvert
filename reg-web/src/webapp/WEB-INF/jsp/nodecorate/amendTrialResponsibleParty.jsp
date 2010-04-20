    <%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table>s
 <tr>
             <th colspan="2"><fmt:message key="submit.trial.sponsorResParty"/></th>
        </tr>          
        <tr><td colspan="2" class="space">&nbsp;</td></tr>
        <tr>
                    <td scope="row" class="label">
                        <label for="submitTrial_selectedSponsor_name_part_0__value"> Sponsor:<span class="required">*</span></label> 
                    </td>
                    <td class="value">
                        <div id="loadSponsorField">
                        <%@ include file="/WEB-INF/jsp/nodecorate/trialSponsor.jsp" %>
                        </div>      
                    </td>
        </tr>   
        <tr>
                <td scope="row" class="label">
                    <label for="submitTrial_resppartysponsor"> <fmt:message key="submit.trial.responsibleParty"/><span class="required">*</span></label>
                </td>
                <td>
                <s:radio name="trialDTO.responsiblePartyType" id="trialDTO.responsiblePartyType" list="#{'pi':'PI', 'sponsor':'Sponsor'}" onclick="manageRespPartyLookUp();"/>
                </td>
        </tr>        
         <s:if test="trialDTO.responsiblePartyType == 'sponsor'">
             <tr id="rpcid" >
              <td scope="row" class="label">
                          <label for="submitTrial_resPartyContactFullName"> <fmt:message key="submit.trial.responsiblePartyContact"/></label> 
              </td>                                        
              <td class="value">
              <div id="loadResponsibleContactField">
                   <%@ include file="/WEB-INF/jsp/nodecorate/trialresponsibleContact.jsp" %>
              </div>                                                                                             
              </td>
             </tr>
              <tr id="rpgcid">
                    <td scope="row" class="label">
                        <label for="submitTrial_resPartyGenericContact"><fmt:message key="submit.trial.responsiblePartyGenericContact"/></label> 
                    </td>
                    <td class="value">
                        <div id="loadResponsiblePartyGenericContactField">
                        <%@ include file="/WEB-INF/jsp/nodecorate/trialResPartyGenericContact.jsp" %>
                        </div>      
                    </td>
          </tr>   
         </s:if>
         <s:else>
            <tr id="rpcid" style="display:none">
                     <td scope="row" class="label">
                                 <label for="submitTrial_resPartyContactFullName"> <fmt:message key="submit.trial.responsiblePartyContact"/></label> 
                     </td>                                        
                     <td class="value">
                               <div id="loadResponsibleContactField">
                                    <%@ include file="/WEB-INF/jsp/nodecorate/trialresponsibleContact.jsp" %>
                               </div>                                                                                             
                     </td>
            </tr>
            <tr id="rpgcid"  style="display:none">
                    <td scope="row" class="label">
                        <label for="submitTrial_resPartyGenericContact"><fmt:message key="submit.trial.responsiblePartyGenericContact"/></label> 
                    </td>
                    <td class="value">
                        <div id="loadResponsiblePartyGenericContactField">
                        <%@ include file="/WEB-INF/jsp/nodecorate/trialResPartyGenericContact.jsp" %>
                        </div>      
                    </td>
          </tr>   
         </s:else>                                          
                
         <tr>
         <td colspan="2">
          <p><b><I>Please provide professional contact information only.</I></b></p>
         </td>
         </tr>
          <tr>
                <td scope="row" class="label">
                   <label for="submitTrial_contactEmail"> <fmt:message key="submit.trial.responsiblePartyEmail"/><span class="required">*</span></label> 
                </td>
                <td class="value">
                    <s:textfield id="trialDTO.contactEmail" name="trialDTO.contactEmail"  maxlength="200" size="100"  cssStyle="width:200px"/>
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.contactEmail</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
                </tr>
                <tr>
                <td scope="row" class="label">
                 <label for="submitTrial_contactPhone"> <fmt:message key="submit.trial.responsiblePartyPhone"/><span class="required">*</span></label>
                </td>
                <td class="value">
                    <s:textfield name="trialDTO.contactPhone"  id="trialDTO.contactPhone" maxlength="200" size="100"  cssStyle="width:100px" />
                    Extn:<s:textfield name="trialDTO.contactPhoneExtn"  id="trialDTO.contactPhoneExtn" maxlength="15" size="10"  cssStyle="width:60px" />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.contactPhone</s:param>
                       </s:fielderror>                            
                     </span>
                </td>           
          </tr>             
          <tr>
         <td colspan="2">
          <p><b><I>Contact information required for internal administrative use only; not revealed to public</I></b></p>
         </td>
         </tr>      
         </table>    