<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<tr>
    <th colspan="2"><fmt:message key="update.trial.grantInfo"/></th>
</tr>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
<tr>
    <td colspan="2">
       <fmt:message key="update.trial.grantInstructionalText"/>
    </td>
</tr>
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
<tr>
    <td colspan="2">
        <table class="data2">
            <tr>
                <td colspan="2">
                    <table class="form">
                        <tbody>
                            <tr>
                                <th><label for="fundingMechanismCode"><fmt:message key="update.trial.fundingMechanism"/></label> </th>
                                <th><label for="nihInstitutionCode"><fmt:message key="update.trial.instituteCode"/></label></th>
                                <th><label for="serialNumber"><fmt:message key="update.trial.serialNumber"/></label></th>
                                <th><label for="nciDivisionProgramCode"><fmt:message key="update.trial.divProgram"/></label></th>
                                <th></th>
                            </tr>
                            <tr>
                                <s:set name="fundingMechanismValues" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getFundingMechanisms()" />
                                <td>
                                    <s:select headerKey="" headerValue="--Select--"
                                         name="fundingMechanismCode"
                                         list="#fundingMechanismValues"
                                         listKey="fundingMechanismCode"
                                         listValue="fundingMechanismCode"
                                         id="fundingMechanismCode"
                                         value="fundingMechanismCode"
                                         cssStyle="width:150px" />
                                </td>
                                <s:set name="nihInstituteCodes" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getNihInstitutes()" />
                                <td>
                                    <s:select headerKey="" headerValue="--Select--"
                                         name="nihInstitutionCode"
                                         list="#nihInstituteCodes"
                                         listKey="nihInstituteCode"
                                         listValue="nihInstituteCode"
                                         id="nihInstitutionCode"
                                         value="nihInstitutionCode"
                                         cssStyle="width:150px"  />
                                    <span class="formErrorMsg" >
                                        <s:fielderror>
                                            <s:param>nihInstitutionCode</s:param>
                                       </s:fielderror>
                                    </span>
                                </td>
                                <td>
                                    <s:textfield name="serialNumber" id="serialNumber" maxlength="200" size="100"  cssStyle="width:150px"  />
                                    <span class="formErrorMsg">
                                        <s:fielderror>
                                            <s:param>serialNumber</s:param>
                                        </s:fielderror>
                                    </span>
                                </td>
                                <s:set name="programCodes" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
                                <td>
                                    <s:select headerKey="" headerValue="--Select--" name="nciDivisionProgramCode" id="nciDivisionProgramCode" list="#programCodes"  value="nciDivisionProgramCode" cssStyle="width:150px" />
                                    <span class="formErrorMsg">
                                        <s:fielderror>
                                            <s:param>nciDivisionProgramCode</s:param>
                                        </s:fielderror>
                                    </span>
                                </td>
                                <td>
                                    <input type="button" id="grantbtnid" value="Add Grant" onclick="addGrant();" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="space">
                    <div id="grantdiv">
                        <%@ include file="/WEB-INF/jsp/nodecorate/updateTrialViewGrant.jsp" %>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="space">
                    <div id="grantadddiv">
                        <%@ include file="/WEB-INF/jsp/nodecorate/addGrantForUpdate.jsp" %>
                    </div>
                </td>
            </tr>
        </table>
   </td>
</tr>   
<tr>
    <td colspan="2" class="space">&nbsp;</td>
</tr>
