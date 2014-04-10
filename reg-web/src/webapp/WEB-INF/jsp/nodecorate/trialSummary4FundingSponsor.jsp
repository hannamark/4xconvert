<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-xs-12">
<div class="col-xs-6">
    <s:textfield label="Summary4sponsorName"  id="trialDTO.summaryFourOrgName" size="30" readonly="true" cssClass="form-control"  />
    <span class="alert-danger"> 
    <s:fielderror>
        <s:param>summary4FundingSponsor</s:param>
    </s:fielderror>                            
</span>    
</div>

<div class="col-xs-6">
    <button onclick="lookup4loadSummary4Sponsor();" title="Opens a popup form to select Summary4 Sponsor" type="button" class="btn btn-icon btn-default"><i class="fa-plus"></i>Add Sponsor</button>
    <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Click Add Sponsor, and select the name of the external sponsor or funding source as defined by the Summary 4 report."  data-placement="top" data-trigger="hover"></i>
</div>
</div>
<br/><br/><br/>
<c:forEach items="${sessionScope.trialDTO.summaryFourOrgIdentifiers}" var="summaryFourOrgIdentifiers" varStatus="stat">
<div class="col-xs-12">
	<div class="col-xs-6">
	   <input type="text" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" value="${summaryFourOrgIdentifiers.orgName}" size="30" readonly="true" 
	       class="form-control"  />
	   <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" value="${summaryFourOrgIdentifiers.orgId}"/> 
	   <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].rowId" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].rowId" value="${summaryFourOrgIdentifiers.rowId}"/>
	</div>
	<div class="col-xs-6">
	    <button onclick="deleteSummary4SponsorRow('${summaryFourOrgIdentifiers.rowId}');" title="Opens a popup form to select Summary4 Sponsor" type="button" class="btn btn-icon btn-default"><i class="fa-minus"></i>Delete Sponsor</button>
	</div> 

</div>
</c:forEach>