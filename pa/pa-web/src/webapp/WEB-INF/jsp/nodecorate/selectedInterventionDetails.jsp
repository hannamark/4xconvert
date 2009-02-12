<%@ taglib prefix="s" uri="/struts-tags"%>

<table class="form">
    <s:hidden name="currentAction"/>
    <s:hidden name="selectedRowIdentifier"/>
    <s:hidden name="interventionIdentifier"/>
    <tr>
        <td scope="row" class="label"><s:label>Intervention Name:</s:label><span
            class="required">*</span></td>
        <td class="value" style="width: 250px">
            <s:textfield disabled="true" name="interventionName" maxlength="160" size="160" 
                    cssStyle="width:280px;float:left"/> 
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
            <s:textarea disabled="true" name="interventionOtherNames" rows="3" cssStyle="width:280px;float:left"/>
        </td>
    </tr>
</table>
