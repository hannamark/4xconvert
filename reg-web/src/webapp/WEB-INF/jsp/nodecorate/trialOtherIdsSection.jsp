<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<tr>
    <th colspan="2"><fmt:message key="submit.trial.otherIdentifiers"/></th>
</tr>
       
<c:set var="rootConstant" value="<%=gov.nih.nci.pa.iso.util.IiConverter.STUDY_PROTOCOL_ROOT%>"/>
<s:iterator id="trialDTO.secondaryIdentifierList" value="trialDTO.secondaryIdentifierList" status="sstats">
    <tr>
        <s:if test="root != rootConstant || root == null" >
            <td scope="row" class="label">
                 <label for="updateTrial_otherIdentifiers">
                    <fmt:message key="submit.trial.otherIdentifier"/>
                 </label>
             </td>
              <td>
                <s:textfield id="updateTrial_otherIdentifiers" name="trialDTO.secondaryIdentifierList[%{#sstats.index}].extension" value="%{extension}" size="100" 
                             cssClass="%{#attr.updateOrAmendMode ? 'readonly' : ''}" cssStyle="width:200px" readonly="%{#attr.updateOrAmendMode}"/>
             </td>
        </s:if>     
    </tr>
</s:iterator>
<tr>
    <td scope="row" class="label">
        <label for="otherIdentifierOrg">
            <fmt:message key="submit.trial.otherIdentifier"/>
        </label>
    </td>
    <td>
      <input type="text" name="otherIdentifierOrg" id="otherIdentifierOrg" value=""/>&nbsp;
      <input type="button" id="otherIdbtnid" value="Add Other Identifier" onclick="addOtherIdentifier();" />
   </td>           
</tr> 
<tr>
    <td colspan="2" class="space">  
        <div id="otherIdentifierdiv">
            <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIdentifiersForUpdate.jsp" %>
        </div>
    </td>
</tr>      
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
       