<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-xs-6">
    <span class="alert-danger"> 
    <s:fielderror>
        <s:param>summary4FundingSponsor</s:param>
    </s:fielderror>                            
</span>    
</div>

<div class="col-xs-6">
    <s:set var="sum4SponsorOrgs" value="@gov.nih.nci.registry.util.FilterOrganizationUtil@getSponsorOrganization()" />
    <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.add_sponsor" />"  data-placement="top" data-trigger="hover"></i>
</div>

<c:forEach items="${sessionScope.trialDTO.summaryFourOrgIdentifiers}" var="summaryFourOrgIdentifiers" varStatus="stat">
<div class="col-xs-12">
	<div class="col-xs-6">
	   <input type="text" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" value="${summaryFourOrgIdentifiers.orgName}" size="30" readonly="true" 
	       class="form-control"  />
	   <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" value="${summaryFourOrgIdentifiers.orgId}"/> 
	   <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].rowId" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].rowId" value="${summaryFourOrgIdentifiers.rowId}"/>
	</div>
	<div class="col-xs-6">
	    <button onclick="deleteSummary4SponsorRow('${summaryFourOrgIdentifiers.rowId}');" title="Remove this Summary4 Sponsor" type="button" class="btn btn-icon btn-default"><i class="fa-minus"></i>Delete Sponsor</button>
	</div> 

</div>
</c:forEach>