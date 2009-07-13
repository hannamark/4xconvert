<%@ taglib prefix="s" uri="/struts-tags"%>

<table class="form">
    <s:hidden name="currentAction"/>
    <s:hidden name="selectedRowIdentifier"/>
    <s:hidden name="interventionIdentifier"/>
    <tr>
        <td class="label">
            <s:label>Intervention Type:
            </s:label><span class="required">*</span>
        </td>
        <s:set name="interventionTypeValues"
                    value="@gov.nih.nci.pa.enums.ActivitySubcategoryCode@getDisplayNames()" />
        <td class="value" colspan="2">
            <s:select onchange="statusChange()" headerKey="" headerValue="--Select--" 
            name="interventionType" value="interventionType" list="#interventionTypeValues" />
        </td>
    </tr>
    <tr><td/>
        <td class="value">
        <s:if test="%{interventionType == 'Drug'}">
            <s:checkbox name="interventionLeadIndicator"/>
            <s:label name="leadIndicatorLabel" for="interventionLeadIndicator">Lead Product (Drug interventions only)</s:label>
        </s:if><s:else>
            <s:checkbox disabled="true" name="interventionLeadIndicator"/>
            <s:label disabled="true" name="leadIndicatorLabel" for="interventionLeadIndicator">Lead Product (Drug interventions only)</s:label>
        </s:else>
    </td>
    </tr>
    <tr>
        <td scope="row" class="label"><s:label>Intervention Name:</s:label><span
            class="required">*</span></td>
        <td class="value" style="width: 250px">
            <s:textfield readonly="true" name="interventionName" maxlength="160" size="160" 
                    cssStyle="width:280px;float:left" cssClass="readonly"/> 
        </td>
        <td class="value">
            <s:if test="%{currentAction != 'edit'}">
            <ul style="margin-top: -6px;">
                <li style="padding-left: 0"><a href="#" class="btn"
                    onclick="lookup();" /><span class="btn_img"><span
                    class="search">Look Up</span></span></a></li>
            </ul>
            </s:if>
        </td>
    </tr>
    <tr>
        <td class="label"><s:label>Intervention Description:</s:label></td>
        <td class="value" colspan="2">
            <s:textarea name="interventionDescription" rows="3" cssStyle="width:280px;float:left"/>
        </td>
    </tr>
    <tr>
        <td class="label"><s:label>Other Names:</s:label></td>
        <td class="value" colspan="2">
            <s:textarea readonly="true" name="interventionOtherNames" rows="3" cssStyle="width:280px;float:left" cssClass="readonly"/>
        </td>
    </tr>
</table>
