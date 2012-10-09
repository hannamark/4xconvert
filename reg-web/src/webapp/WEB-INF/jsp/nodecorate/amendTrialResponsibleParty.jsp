    <%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table>
 <tr>
             <th colspan="2"><fmt:message key="submit.trial.sponsorResParty"/></th>
        </tr>          
        <tr><td colspan="2" class="space">&nbsp;</td></tr>
        <tr>
                    <td scope="row" class="label">
                        <label for="trialDTO.sponsorName"> Sponsor:<span class="required">*</span></label> 
                    </td>
                    <td class="value">
                        <div id="loadSponsorField">
                        <%@ include file="/WEB-INF/jsp/nodecorate/trialSponsor.jsp" %>
                        </div>      
                    </td>
        </tr>   
        <tr>
                <td scope="row" class="label-noinput">
                    <fmt:message key="submit.trial.responsibleParty"/><span class="required">*</span>
                </td>
                <td>
                <s:radio name="trialDTO.responsiblePartyType" id="trialDTO.responsiblePartyType" list="#{'PI':'PI', 'sponsor':'Sponsor'}" onclick="manageRespPartyLookUp();"/>
                </td>
        </tr>        
         <s:if test="trialDTO.responsiblePartyType == 'sponsor'">
             <tr id="rpcid" >
              <td scope="row" class="label">
                          <label for="trialDTO.responsiblePersonName"> <fmt:message key="submit.trial.responsiblePartyContact"/></label> 
              </td>                                        
              <td class="value">
              <div id="loadResponsibleContactField">
                   <%@ include file="/WEB-INF/jsp/nodecorate/trialresponsibleContact.jsp" %>
              </div>                                                                                             
              </td>
             </tr>
              <tr id="rpgcid">
                    <td scope="row" class="label">
                        <label for="trialDTO.responsibleGenericContactName"><fmt:message key="submit.trial.responsiblePartyGenericContact"/></label> 
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
                                 <label for="trialDTO.responsiblePersonName"> <fmt:message key="submit.trial.responsiblePartyContact"/></label> 
                     </td>                                        
                     <td class="value">
                               <div id="loadResponsibleContactField">
                                    <%@ include file="/WEB-INF/jsp/nodecorate/trialresponsibleContact.jsp" %>
                               </div>                                                                                             
                     </td>
            </tr>
            <tr id="rpgcid"  style="display:none">
                    <td scope="row" class="label">
                        <label for="trialDTO.responsibleGenericContactName"><fmt:message key="submit.trial.responsiblePartyGenericContact"/></label> 
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
                   <label for="trialDTO.contactEmail"> <fmt:message key="submit.trial.responsiblePartyEmail"/><span class="required">*</span></label> 
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
                 <label for="trialDTO.contactPhone"> <fmt:message key="submit.trial.responsiblePartyPhone"/><span class="required">*</span></label>
                </td>
                <td class="value">
                    <s:textfield name="trialDTO.contactPhone"  id="trialDTO.contactPhone" maxlength="200" size="100"  cssStyle="width:100px" />
                    <label>Extn:<s:textfield name="trialDTO.contactPhoneExtn"  id="trialDTO.contactPhoneExtn" maxlength="15" size="10"  cssStyle="width:60px" /></label>
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