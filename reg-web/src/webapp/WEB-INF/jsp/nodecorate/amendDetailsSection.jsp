<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<tr>
    <th colspan="2"><fmt:message key="submit.trial.trialDetails"/></th>
</tr>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
<tr>
    <td scope="row" class="label">
        <label for="submitTrial_protocolWebDTO_trialTitle"> 
            <fmt:message key="submit.trial.title"/><span class="required">*</span>
        </label>
    </td>
    <td>
        <s:textarea id="submitTrial_protocolWebDTO_trialTitle" name="trialDTO.officialTitle" cols="75" rows="4" 
            maxlength="4000" cssClass="charcounter"/>       
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>trialDTO.officialTitle</s:param>
            </s:fielderror>
        </span>
    </td>
</tr>
<%@ include file="/WEB-INF/jsp/nodecorate/phasePurpose.jsp" %>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>