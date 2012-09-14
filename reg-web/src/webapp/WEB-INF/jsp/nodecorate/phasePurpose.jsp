<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/nodecorate/phase.jsp" %>
<tr>
    <td  scope="row" class="label">
        <label> <fmt:message key="submit.trial.type"/><span class="required">*</span></label>
    </td>
    <td>
        <input type="radio" name="trialDTO.trialType" value="Interventional" checked="checked" id="trialDTO.trialType.Interventional"> <label for = "trialDTO.trialType.Interventional">Interventional</label>
        <input type="radio" name="trialDTO.trialType" value="Observational" disabled="disabled" id="trialDTO.trialType.Observational"><label for = "trialDTO.trialType.Observational">Observational</label>
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>trialDTO.trialType</s:param>
            </s:fielderror>
        </span>
    </td>
</tr>
<%@ include file="/WEB-INF/jsp/nodecorate/primaryPurposeOther.jsp" %>