<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<tr style="display:none">
    <td>
        <s:hidden name="trialDTO.sponsorIdentifier" id="trialDTO.sponsorIdentifier"/>
        <s:hidden name="trialDTO.sponsorName" id="trialDTO.sponsorName" />
        <s:hidden name="trialDTO.responsiblePartyType" id="trialDTO.responsiblePartyType" />
        <s:hidden name="trialDTO.responsiblePersonIdentifier" id="trialDTO.responsiblePersonIdentifier"/>
        <s:hidden name="trialDTO.responsiblePersonName" id="trialDTO.responsiblePersonName"/>
        <s:hidden name="trialDTO.responsibleGenericContactName" id="trialDTO.responsibleGenericContactName"/>
        <s:hidden name="trialDTO.contactEmail" id="trialDTO.contactEmail"/>
        <s:hidden name="trialDTO.contactPhone" id="trialDTO.contactPhone"/>
    </td>
</tr>
<tr>
    <th colspan="2"><fmt:message key="update.trial.sponsorResParty"/></th>
</tr>          
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
<tr>
    <td scope="row" class="label">
        <label>Sponsor:<span class="required">*</span></label> 
    </td>
    <td>
        <s:property value="trialDTO.sponsorName"/>
    </td>
</tr>   
<tr>
    <td scope="row" class="label">
        <label>
            <fmt:message key="update.trial.responsibleParty"/><span class="required">*</span>
        </label>
    </td>
    <td>
        <s:property value="trialDTO.responsiblePartyType"/>
    </td>
</tr>
<s:if test="trialDTO.responsiblePartyType == 'sponsor'">
    <tr>
        <td scope="row" class="label">
            <label> 
                <fmt:message key="update.trial.responsiblePartyContact"/>
            </label> 
        </td>                                        
        <td class="value">
            <s:property value="trialDTO.responsiblePersonName"/>
        </td>
    </tr>
    <tr>
        <td scope="row" class="label">
            <label>
                <fmt:message key="update.trial.responsiblePartyGenericContact"/>
            </label> 
        </td>
        <td class="value">
            <s:property value="trialDTO.responsibleGenericContactName"/>
        </td>
    </tr>   
</s:if>
<tr>
    <td scope="row" class="label">
        <label>
            <fmt:message key="update.trial.responsiblePartyEmail"/><span class="required">*</span>
        </label> 
    </td>
    <td class="value">
        <s:property value="trialDTO.contactEmail"/>
    </td>
</tr>
<tr>
    <td scope="row" class="label">
        <label>
            <fmt:message key="update.trial.responsiblePartyPhone"/><span class="required">*</span>
        </label>
    </td>
    <td class="value">
        <s:property value="trialDTO.contactPhone"/>
    </td>           
</tr>             
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>