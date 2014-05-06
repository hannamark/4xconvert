<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-xs-12">
<s:set var="sum4Orgs" value="@gov.nih.nci.registry.util.FilterOrganizationUtil@getSponsorOrganization()" />
<div class="collapse navbar-collapse organization-dropdown">
        <div class="nav navbar-nav" style="width: 100%;">
          <div class="active dropdown"><a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown" id="trialDTO.summaryFourOrgName">Please Select the Summary 4 Sponsor Organization <b class="caret"></b></a>  
            <table class="dropdown-menu" id="dropdown-sum4Organization">
            	<tr><th>PO ID</th><th>CTEP ID</th><th>Name</th></tr>
            	<s:iterator var="orgItem" value="#sum4Orgs">
            	<s:if test="%{#orgItem.getType() < 0}">
            		<tr><td colspan="3"><hr/></td></tr>
            	</s:if>
            	<s:else>
        			<tr><td><a href="javascript:void(0)" onclick="lookup4loadSummary4Sponsor(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getName()"/>')"><s:property value="#orgItem.getPoId()"/></a></td>
        			<td><a href="javascript:void(0)" onclick="lookup4loadSummary4Sponsor(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getName()"/>')"><s:property value="#orgItem.getCtepId()"/></a></td>
        			<td><a href="javascript:void(0)" onclick="lookup4loadSummary4Sponsor(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getName()"/>')"><s:property value="#orgItem.getName()"/></a></td></tr>
        		</s:else>
        		</s:iterator>
        		<tr><td colspan="3"><a href="javascript:void(0)" onclick="lookup4loadSummary4Sponsor(-1, '')">Search...</a></td></tr>
            </table>
          </div>
      </div>
</div>
<div class="col-xs-6">
    <span class="alert-danger"> 
    <s:fielderror>
        <s:param>summary4FundingSponsor</s:param>
    </s:fielderror>                            
</span>    
</div>

<div class="col-xs-6">
    <s:set var="sum4SponsorOrgs" value="@gov.nih.nci.registry.util.FilterOrganizationUtil@getSponsorOrganization()" />
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
	    <button onclick="deleteSummary4SponsorRow('${summaryFourOrgIdentifiers.rowId}');" title="Remove this Summary4 Sponsor" type="button" class="btn btn-icon btn-default"><i class="fa-minus"></i>Delete Sponsor</button>
	</div> 

</div>
</c:forEach>