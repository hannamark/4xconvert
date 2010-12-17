<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script language="javascript">
	function deleteDocument(typeCode){
	    var url = '/registry/protected/ajaxUploaddeleteDocument.action?typeCode='+typeCode+'&pageFrom='+document.forms[0].pageFrom.value;
	    var div = document.getElementById('uploadDocDiv');
	    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
	    var aj = new Ajax.Updater(div, url, {
	        asynchronous: true,
	        method: 'get',
	        evalScripts: false
	    });
	    return false;
	}
</script>
<reg-web:failureMessage/>
<s:hidden name="pageFrom" id="pageFrom"/>
<s:if test="%{pageFrom == 'submitTrial'}">
<table class="form">
        <tr>
              <th colspan="2"><fmt:message key="submit.trial.documents"/></th>
        </tr>
        <tr>
              <td colspan="2" class="space">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2">
               <fmt:message key="submit.trial.docInstructionalText"/>
            </td>
        </tr>
        <tr>
              <td colspan="2" class="space">&nbsp;</td>
        </tr>
        <tr>
              <td scope="row" class="label">
                <reg-web:displayTooltip tooltip="tooltip.protocol_document">
                    <label for="submitTrial_protocolDoc"><fmt:message key="submit.trial.protocolDocument"/><span class="required">*</span></label>
                </reg-web:displayTooltip>
             </td>
             <td class="value">
             	<s:if test="%{#session.protocolDoc.typeCode.equals('Protocol Document')}">
	             	<s:property value="%{#session.protocolDoc.fileName}"/>
	             	<input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.protocolDoc.typeCode}'/>')"/>
	             </s:if>
	             <s:else>
	                 <s:file name="protocolDoc" value="true" cssStyle="width:270px"/>
	                 <span class="formErrorMsg">
	                    <s:fielderror>
	                    <s:param>trialDTO.protocolDocFileName</s:param>
	                   </s:fielderror>
	                 </span>
                 </s:else>
               </td>
         </tr>

         <tr>
              <td scope="row" class="label">
                <reg-web:displayTooltip tooltip="tooltip.irb_approval">
                    <label for="submitTrial_irbApproval"><fmt:message key="submit.trial.irbApproval"/><span class="required">*</span></label>
                </reg-web:displayTooltip>
             </td>
             <td class="value">
             	<s:if test="%{#session.irbApprovalDoc.typeCode.equals('IRB Approval Document')}">
	             	<s:property value="%{#session.irbApprovalDoc.fileName}"/>
	             	<input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.irbApprovalDoc.typeCode}'/>')"/>
	             </s:if>
	             <s:else>
	                 <s:file name="irbApproval" cssStyle="width:270px"/>
	                 <span class="formErrorMsg">
	                    <s:fielderror>
	                    <s:param>trialDTO.irbApprovalFileName</s:param>
	                   </s:fielderror>
	                 </span>
                 </s:else>
               </td>
         </tr>
         <tr>
             <td scope="row" class="label">
                <reg-web:displayTooltip tooltip="tooltip.list_of_participating_sites">
                    <label for="submitTrial_participatingSites"><fmt:message key="submit.trial.participatingSites"/></label>
                </reg-web:displayTooltip>
             </td>
             <td class="value">
             	<s:if test="%{#session.participatingSitesDoc.typeCode.equals('Participating sites')}">
	             	<s:property value="%{#session.participatingSitesDoc.fileName}"/>
	             	<input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.participatingSitesDoc.typeCode}'/>')"/>
	             </s:if>
	             <s:else>
	                 <s:file name="participatingSites" cssStyle="width:270px"/>
	                 <span class="formErrorMsg">
	                    <s:fielderror>
	                    <s:param>trialDTO.participatingSitesFileName</s:param>
	                   </s:fielderror>
	                 </span>
                 </s:else>
               </td>
         </tr>

         <tr>
             <td scope="row" class="label">
                <reg-web:displayTooltip tooltip="tooltip.informed_consent_document">
                    <label for="submitTrial_informedConsentDocument"><fmt:message key="submit.trial.informedConsent"/></label>
                </reg-web:displayTooltip>
             </td>
             <td class="value">
             	<s:if test="%{#session.informedConsentDoc.typeCode.equals('Informed Consent Document')}">
	             	<s:property value="%{#session.informedConsentDoc.fileName}"/>
	             	<input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.informedConsentDoc.typeCode}'/>')"/>
	             </s:if>
	             <s:else>
	                 <s:file name="informedConsentDocument" cssStyle="width:270px"/>
	                 <span class="formErrorMsg">
	                    <s:fielderror>
	                    <s:param>trialDTO.informedConsentDocumentFileName</s:param>
	                   </s:fielderror>
	                 </span>
                 </s:else>
               </td>
         </tr>
         <tr>
             <td scope="row" class="label">
                <reg-web:displayTooltip tooltip="tooltip.other">
                    <label for="submitTrial_otherDocument"><fmt:message key="submit.trial.otherDocument"/></label>
                </reg-web:displayTooltip>
             </td>
             <td class="value">
             	<s:if test="%{#session.otherDoc.typeCode.equals('Other')}">
	             	<s:property value="%{#session.otherDoc.fileName}"/>
	             	<input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.otherDoc.typeCode}'/>')"/>
	             </s:if>
	             <s:else>
	                 <s:file name="otherDocument" cssStyle="width:270px"/>
	                 <span class="formErrorMsg">
	                    <s:fielderror>
	                    <s:param>trialDTO.otherDocumentFileName</s:param>
	                   </s:fielderror>
	                 </span>
                 </s:else>
               </td>
         </tr>
         </table>
</s:if>

<s:if test="%{pageFrom == 'updateTrial'}">
	<table class="data2">
         <tr>
             <th colspan="2" >IRB Approval</th>
          </tr>
           <tr>
              <td colspan="2" class="space">&nbsp;</td>
           </tr>
            <tr>
              <td scope="row" class="label">
              <label for="updateTrial_irbApproval">
                     <fmt:message key="update.trial.irbApproval"/>
              </label>
             </td>
             <td class="value">
             	<s:if test="%{#session.irbApprovalDoc.typeCode.equals('IRB Approval Document')}">
	             	<s:property value="%{#session.irbApprovalDoc.fileName}"/>
	             	<input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.irbApprovalDoc.typeCode}'/>')"/>
	             </s:if>
	             <s:else>
                    <s:file name="irbApproval" id="irbApproval" cssStyle="width:270px"/>
                    <span class="formErrorMsg">
                        <s:fielderror>
                            <s:param>trialDTO.irbApprovalFileName</s:param>
                       </s:fielderror>
                    </span>
                 </s:else>
             </td>
         </tr>
      </table>
</s:if>

<s:if test="%{pageFrom == 'proprietaryTrial' || pageFrom == 'updateProprietaryTrial'}">
	<table class="form">
        <tr>
              <th colspan="2"><fmt:message key="submit.trial.documents"/></th>
        </tr>
        <tr>
              <td colspan="2" class="space">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2">
               <fmt:message key="submit.proprietary.trial.docInstructionalText"/>
            </td>
        </tr>
        <tr>
              <td colspan="2" class="space">&nbsp;</td>
        </tr>

        <tr>
              <td scope="row" class="label">
              <label for="submitTrial_protocolDoc">
                     <fmt:message key="submit.proprietary.trial.protocolDocument"/>
              </label>
             </td>
             <td class="value">
                 <s:if test="%{#session.protocolDoc.typeCode.equals('Protocol Document')}">
	             	<s:property value="%{#session.protocolDoc.fileName}"/>
	             	<input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.protocolDoc.typeCode}'/>')"/>
	             </s:if>
	             <s:else>
	                 <s:file name="protocolDoc" value="true" cssStyle="width:270px"/>
	                 <span class="formErrorMsg">
	                    <s:fielderror>
	                    <s:param>trialDTO.protocolDocFileName</s:param>
	                   </s:fielderror>
	                 </span>
                 </s:else>
               </td>
         </tr>
         <tr>
              <td scope="row" class="label">
              <label for="submitTrial_otherDocument"><fmt:message key="submit.trial.otherDocument"/></label>
             </td>
             <td class="value">
                 <s:if test="%{#session.otherDoc.typeCode.equals('Other')}">
	             	<s:property value="%{#session.otherDoc.fileName}"/>
	             	<input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.otherDoc.typeCode}'/>')"/>
	             </s:if>
	             <s:else>
	                 <s:file name="otherDocument" cssStyle="width:270px"/>
	                 <span class="formErrorMsg">
	                    <s:fielderror>
	                    <s:param>trialDTO.otherDocumentFileName</s:param>
	                   </s:fielderror>
	                 </span>
                 </s:else>
               </td>
         </tr>
         </table>
</s:if>

<s:if test="%{pageFrom == 'amendTrial'}">
<div class="box">
            <h3>Amendment Related Documents</h3>
            <table class="form">
            <tr>
              <td colspan="2" class="space">&nbsp;</td>
            </tr>
            <tr>
            <td colspan="2">
               <fmt:message key="submit.trial.docInstructionalText"/>
            </td>
            </tr>
            <tr>
              <td colspan="2" class="space">&nbsp;</td>
            </tr>

            <tr>
              <td scope="row" class="label">
              <label for="submitTrial_protocolDoc">
                     <fmt:message key="amend.trial.protocolDocument"/>
                     <span class="required">*</span>
              </label>
             </td>
             <td class="value">
             	<s:if test="%{#session.protocolDoc.typeCode.equals('Protocol Document')}">
	             	<s:property value="%{#session.protocolDoc.fileName}"/>
	             	<input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.protocolDoc.typeCode}'/>')"/>
	             </s:if>
	             <s:else>
	               <s:file name="protocolDoc" id="protocolDoc" value="true" cssStyle="width:270px"/>
	                 <span class="formErrorMsg">
	                    <s:fielderror>
	                    <s:param>trialDTO.protocolDocFileName</s:param>
	                   </s:fielderror>
	                 </span>
                 </s:else>
               </td>
         </tr>
         <tr>
              <td scope="row" class="label">
              <label for="submitTrial_otherDocument"><fmt:message key="amend.trial.changeMemo"/>
              <span class="required">*</span>
              </label>

             </td>
             <td class="value">
             	<s:if test="%{#session.changeMemoDoc.typeCode.equals('Change Memo Document')}">
	             	<s:property value="%{#session.changeMemoDoc.fileName}"/>
	             	<input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.changeMemoDoc.typeCode}'/>')"/>
	             </s:if>
	             <s:else>
	                 <s:file name="changeMemoDoc" id="changeMemoDoc" cssStyle="width:270px"/>
	                 <span class="formErrorMsg">
	                    <s:fielderror>
	                    <s:param>trialDTO.changeMemoDocFileName</s:param>
	                   </s:fielderror>
	                 </span>
                 </s:else>
               </td>
         </tr>
         <tr>
              <td scope="row" class="label">
              <label for="submitTrial_otherDocument"><fmt:message key="amend.trial.protocolHighlight"/></label>
             </td>
             <td class="value">
             	<s:if test="%{#session.protocolHighlightDoc.typeCode.equals('Protocol Highlighted Document')}">
	             	<s:property value="%{#session.protocolHighlightDoc.fileName}"/>
	             	<input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.protocolHighlightDoc.typeCode}'/>')"/>
	             </s:if>
	             <s:else>
	                 <s:file name="protocolHighlightDocument" id="protocolHighlightDocument" cssStyle="width:270px"/>
	                 <span class="formErrorMsg">
	                    <s:fielderror>
	                    <s:param>trialDTO.protocolHighlightDocumentFileName</s:param>
	                   </s:fielderror>
	                 </span>
                 </s:else>
               </td>
         </tr>
         <tr>
              <td scope="row" class="label">
              <label for="submitTrial_irbApproval">
                     <fmt:message key="submit.trial.irbApproval"/>
                     <span class="required">*</span>
              </label>
             </td>
             <td class="value">
             	<s:if test="%{#session.irbApprovalDoc.typeCode.equals('IRB Approval Document')}">
	             	<s:property value="%{#session.irbApprovalDoc.fileName}"/>
	             	<input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.irbApprovalDoc.typeCode}'/>')"/>
	             </s:if>
	             <s:else>
	                 <s:file name="irbApproval" id="irbApproval" cssStyle="width:270px"/>
	                 <span class="formErrorMsg">
	                    <s:fielderror>
	                    <s:param>trialDTO.irbApprovalFileName</s:param>
	                   </s:fielderror>
	                 </span>
                 </s:else>
               </td>
         </tr>
         <tr>
              <td scope="row" class="label">
              <label for="submitTrial_participatingSites"><fmt:message key="submit.trial.participatingSites"/></label>
             </td>
             <td class="value">
             	<s:if test="%{#session.participatingSitesDoc.typeCode.equals('Participating sites')}">
	             	<s:property value="%{#session.participatingSitesDoc.fileName}"/>
	             	<input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.participatingSitesDoc.typeCode}'/>')"/>
	             </s:if>
	             <s:else>
	                 <s:file name="participatingSites" id="participatingSites" cssStyle="width:270px"/>
	                 <span class="formErrorMsg">
	                    <s:fielderror>
	                    <s:param>trialDTO.participatingSitesFileName</s:param>
	                   </s:fielderror>
	                 </span>
                 </s:else>
               </td>
         </tr>

         <tr>
              <td scope="row" class="label">
              <label for="submitTrial_informedConsentDocument"><fmt:message key="submit.trial.informedConsent"/></label>
             </td>
             <td class="value">
             	<s:if test="%{#session.informedConsentDoc.typeCode.equals('Informed Consent Document')}">
	             	<s:property value="%{#session.informedConsentDoc.fileName}"/>
	             	<input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.informedConsentDoc.typeCode}'/>')"/>
	             </s:if>
	             <s:else>
	                 <s:file name="informedConsentDocument" id="informedConsentDocument" cssStyle="width:270px"/>
	                 <span class="formErrorMsg">
	                    <s:fielderror>
	                    <s:param>trialDTO.informedConsentDocumentFileName</s:param>
	                   </s:fielderror>
	                 </span>
                 </s:else>
               </td>
         </tr>
        </table>
        </div>
</s:if>