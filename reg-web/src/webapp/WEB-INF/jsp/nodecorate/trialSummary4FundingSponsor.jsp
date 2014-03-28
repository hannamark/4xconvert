<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-xs-3">
    <s:textfield label="Summary4sponsorName"  id="trialDTO.summaryFourOrgName" size="30" readonly="true" 
        cssClass="readonly" cssStyle="width:200px" />
    <span class="formErrorMsg"> 
    <s:fielderror>
        <s:param>summary4FundingSponsor</s:param>
    </s:fielderror>                            
</span>    
</div>
<div class="col-xs-3">
    <button onclick="lookup4loadSummary4Sponsor();" title="Opens a popup form to select Summary4 Sponsor" type="button" class="btn btn-icon btn-default"><i class="fa-plus"></i>Add Sponsor</button>
    <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Click Add Sponsor, and select the name of the external sponsor or funding source as defined by the Summary 4 report."  data-placement="top" data-trigger="hover"></i>
</div>

 <c:forEach items="${sessionScope.trialDTO.summaryFourOrgIdentifiers}" var="summaryFourOrgIdentifiers" varStatus="stat">
 <div class="form-group">
     <div class="col-xs-4"></div>
     <div class="col-xs-3">
        <input type="text" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgName" value="${summaryFourOrgIdentifiers.orgName}" size="30" readonly 
            class="readonly" style="width:200px" />
        <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].orgId" value="${summaryFourOrgIdentifiers.orgId}"/> 
        <input type="hidden" name="trialDTO.summaryFourOrgIdentifiers[${stat.index}].rowId" id="trialDTO.summaryFourOrgIdentifiers[${stat.index}].rowId" value="${summaryFourOrgIdentifiers.rowId}"/>
    </div>
    <div class="col-xs-4">
        <button onclick="deleteSummary4SponsorRow('${summaryFourOrgIdentifiers.rowId}');" title="Opens a popup form to select Summary4 Sponsor" type="button" class="btn btn-icon btn-default"><i class="fa-minus"></i>Delete Sponsor</button>
    </div>
 </div>     
</c:forEach>