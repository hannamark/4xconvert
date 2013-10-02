<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table>
    <tr>
        <td>
             <s:textfield label="Summary4sponsorName"  id="trialDTO.summaryFourOrgName" size="30" readonly="true" cssClass="readonly" cssStyle="width:200px" /> 
        </td>
        <td class="value">
            <ul style="margin-top:-5px;">              
                <li style="padding-left:0">
                 <a href="javascript:void(0)" class="btn" onclick="lookup4loadSummary4Sponsor();" title="Opens a popup form to select Summary4 Sponsor">
                    <span class="btn_img"><span class="organization">Add Sponsor</span></span>
                 </a>
                </li>
            </ul>
        </td>
    </tr>
    <c:forEach items="${sessionScope.trialDTO.summaryFourOrgIdentifiers}" var="summaryFourOrgIdentifiers" varStatus="stat">
    <tr>
        <td>
            <input type="text" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" value="${summaryFourOrgIdentifiers.orgName}" size="30" readonly class="readonly" style="width:200px" />
            <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" value="${summaryFourOrgIdentifiers.orgId}"/> 
            <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].rowId" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].rowId" value="${summaryFourOrgIdentifiers.rowId}"/> 
        </td>
        <td class="value">
            <ul style="margin-top:-5px;">              
                <li style="padding-left:0">
                 <a href="javascript:void(0)" class="btn" onclick="deleteSummary4SponsorRow('${summaryFourOrgIdentifiers.rowId}');" title="Opens a popup form to select Summary4 Sponsor">
                    <span class="btn_img"><span class="organization">Delete Sponsor</span></span>
                 </a>
                </li>
            </ul>
        </td>
    </tr>
    
    </c:forEach>
    
</table>
<span class="formErrorMsg"> 
    <s:fielderror>
        <s:param>summary4FundingSponsor</s:param>
    </s:fielderror>                            
</span>