<%@ taglib prefix="s" uri="/struts-tags"%>
<table class="form">
    <s:hidden name="currentAction"/>
    <s:hidden name="interventionIdentifier"/>
    <s:hidden name="selectedRowIdentifier"/>
    
    <tr>
        <td class="label"><s:label for="interventionType">Intervention Type:
            </s:label><span class="required">*</span></td>
        <s:set name="interventionTypeValues"
            value="@gov.nih.nci.pa.enums.ActionSubcategoryCode@getDisplayNames()" />
        <td class="value" colspan="2">
            <s:if test="%{(interventionIdentifier == null) && (currentAction == 'create')}">
                <s:select onchange="statusChange()" headerKey=""
                headerValue="--Select--" name="interventionType" list="#interventionTypeValues" />
            </s:if>
            <s:else>
                <s:textfield disabled="true" name="interventionType"/>
            </s:else>
        </td>
    </tr>
    <tr>
		<td scope="row" class="label"><s:label for="interventionName">Intervention Name:</s:label><span
			class="required">*</span></td>
		<td class="value" style="width: 250px">
	        <s:if test="%{interventionIdentifier == null}">
	            <s:textfield name="interventionName" maxlength="160" size="160" cssStyle="width:280px;float:left"/> 
		        <s:if test="%{currentAction == 'create'}">
			        <span class="info">Click <strong>Look Up</strong> to choose an intervention.</span>
		        </s:if>
		    </s:if>
		    <s:else>
                <s:textfield disabled="true" name="interventionName" maxlength="160" size="160" 
                    cssStyle="width:280px;float:left"/> 
            </s:else>
        </td>
		<td class="value"><s:if test="%{(currentAction == 'create') && (interventionIdentifier == null)}">
			<ul style="margin-top: -6px;">
				<li style="padding-left: 0"><a href="#" class="btn"
					onclick="lookup();" /><span class="btn_img"><span
					class="search">Look Up</span></span></a></li>
			</ul>
		</s:if></td>
	</tr>
	<tr>
		<td class="label"><s:label for="interventionDescription">Intervention Description:</s:label></td>
		<td class="value" colspan="2">
		    <s:if test="%{interventionIdentifier == null}">
		          <s:textarea name="interventionDescription" rows="3" cssStyle="width:280px;float:left"/>
		    </s:if>
		    <s:else>
                  <s:textarea disabled="true" name="interventionDescription" rows="3" cssStyle="width:280px;float:left"/>
		    </s:else>
		</td>
	</tr>
    <tr>
        <td class="label"><s:label for="otherName">Other Names:</s:label></td>
        <td class="value" colspan="2">
            <s:if test="%{interventionIdentifier == null}">
                <s:textarea name="otherName" rows="3" cssStyle="width:280px;float:left"/>
            </s:if>
            <s:else>
                <s:textarea disabled="true" name="otherName" rows="3" cssStyle="width:280px;float:left"/>
            </s:else>
        </td>
    </tr>
    <tr>
        <td/>
        <td class="value" colspan="2">    
            <s:if test="%{interventionType == 'Drug'}">
                <s:checkbox name="leadIndicator"/>
                <s:label name="leadIndicatorLabel" for="leadIndicator">Lead Product (Drug interventions only)</s:label>
            </s:if><s:else>
                <s:checkbox disabled="true" name="leadIndicator"/>
                <s:label disabled="true" name="leadIndicatorLabel" for="leadIndicator">Lead Product (Drug interventions only)</s:label>
            </s:else>
        </td>
    </tr>
</table>