<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table>
    <tr>
        <td>
            <s:textfield label="Summary4sponsorName" name="trialDTO.summaryFourOrgName" id="trialDTO.summaryFourOrgName" size="30" readonly="true" cssClass="readonly" cssStyle="width:200px" /> 
        </td>
        <td class="value">
            <ul style="margin-top:-5px;">              
                <li style="padding-left:0">
                 <a href="javascript:void(0)" class="btn" onclick="lookup4loadSummary4Sponsor();" title="Opens a popup form to select Summary4 Sponsor">
                    <span class="btn_img"><span class="organization">Look Up Sponsor</span></span>
                 </a>
                </li>
            </ul>
        </td>
    </tr>
</table>
<span class="formErrorMsg"> 
    <s:fielderror>
        <s:param>summary4FundingSponsor</s:param>
    </s:fielderror>                            
</span>