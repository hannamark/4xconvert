<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<tr>
    <th colspan="2"><fmt:message key="submit.trial.leadOrgInvestigator"/></th>
</tr>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
<%-- Lead organization --%>
<tr>
    <td scope="row" class="label">
        <label for="trialDTO.leadOrganizationName">
            <fmt:message key="submit.trial.leadOrganization"/><span class="required">*</span>
        </label>
    </td>
    <td class="value">
        <div id="loadOrgField">
            <table>
                <tr>
                    <td>
                        <reg-web:displayTooltip tooltip="tooltip.lead_organization">
                            <s:textfield label="Organization Name" name="trialDTO.leadOrganizationName"  id="trialDTO.leadOrganizationName" size="30" readonly="true" cssClass="readonly" cssStyle="width:200px" />
                        </reg-web:displayTooltip>
                    </td>
                    <td class="value">
                        <ul style="margin-top:-5px;">
                            <li style="padding-left:0">
                                <a href="javascript:void(0)" class="btn" onclick="lookup4loadleadorg();" title="Opens a popup form to select Lead Organization">
                                    <span class="btn_img"><span class="organization">Look Up Org</span></span>
                                </a>
                            </li>
                        </ul>
                    </td>
                </tr>
            </table>
            <span class="formErrorMsg">
                <s:fielderror>
                    <s:param>trialDTO.leadOrganizationIdentifier</s:param>
                </s:fielderror>
            </span>
        </div>
    </td>
</tr>
<%-- principal investigator --%>
<tr>
    <td scope="row" class="label">
        <label for="trialDTO.piName"><fmt:message key="submit.trial.principalInvestigator"/><span class="required">*</span></label>
    </td>
    <td class="value">
        <div id="loadPersField">
            <table>
                <tr>
                    <td>
                        <s:textfield label="poLeadPi Full Name" name="trialDTO.piName" id="trialDTO.piName" size="30"  readonly="true" cssClass="readonly" cssStyle="width:200px"/>
                    </td>
                    <td class="value">
                        <ul style="margin-top:-5px;">              
                            <li style="padding-left:0">
                             <a href="javascript:void(0)" class="btn" onclick="lookup4loadleadpers();" title="Opens a popup form to select Principal Investigator">
                             <span class="btn_img"><span class="person">Look Up Person</span></span></a>
                            </li>
                        </ul>
                    </td>
                </tr>
            </table>
            <span class="formErrorMsg" id="piIdentifierErr"> 
                <s:fielderror>
                    <s:param>trialDTO.piIdentifier</s:param>
                </s:fielderror>                            
            </span>
        </div>
    </td>
</tr>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>