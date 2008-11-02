<%@ taglib prefix="s" uri="/struts-tags"%>
<table class="form">
    <s:hidden name="interventionIdentifier"/>
    <tr>
        <td class="label"><s:label for="interventionType">Intervention Type:
            </s:label><span class="required">*</span></td>
        <s:set name="interventionTypeValues"
            value="@gov.nih.nci.pa.enums.ActionSubcategoryCode@getDisplayNames()" />
        <s:if test="%{currentAction == 'create'}">
            <td class="value" colspan="2"><s:select headerKey=""
                headerValue="--Select--" name="interventionType" list="#interventionTypeValues" /></td>
        </s:if>
        <s:else>
            <td class="value" colspan="2"><s:textfield readonly="true" disabled="true" name="interventionType"/></td>
        </s:else>
    </tr>
    <tr>
		<td scope="row" class="label"><s:label for="interventionName">Intervention Name:</s:label><span
			class="required">*</span></td>
		<td class="value" style="width: 250px"><s:textfield
			name="interventionName" maxlength="60" size="60"
			cssStyle="width:280px;float:left" readonly="true" disabled="true" /> 
		    <s:if test="%{currentAction == 'create'}">
			     <span class="info">Click <strong>Look Up</strong> to choose an intervention.</span>
		    </s:if>
		</td>
		<td class="value"><s:if test="%{currentAction == 'create'}">
			<ul style="margin-top: -6px;">
				<li style="padding-left: 0"><a href="#" class="btn"
					onclick="lookup();" /><span class="btn_img"><span
					class="search">Look Up</span></span></a></li>
			</ul>
		</s:if></td>
	</tr>
	<tr>
		<td class="label"><s:label for="interventionDescription">Intervention Description:</s:label></td>
		<td class="value" colspan="2"><s:textarea
			name="interventionDescription" rows="3"
			cssStyle="width:280px;float:left" readonly="true" disabled="true" /></td>
	</tr>
    <tr>
        <td class="label"><s:label for="otherName">Other Names:</s:label></td>
        <td class="value" colspan="2"><s:textarea name="otherName"
            rows="3" cssStyle="width:280px;float:left" readonly="true" disabled="true" /></td>
    </tr>
    <s:if test="%{interventionType == 'Drug'}"><tr>
        <td/>
        <td class="value" colspan="2"><s:checkbox name="leadIndicator"/>Lead Product</td>
    </tr></s:if>
</table>