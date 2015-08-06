<div class="col-xs-12">
<s:set var="sum4Orgs" value="@gov.nih.nci.registry.util.FilterOrganizationUtil@getSponsorOrganization()" />
<div class="collapse navbar-collapse organization-dropdown">
        <div class="nav navbar-nav" style="width: 100%;">
          <div class="active dropdown"><a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown" id="trialDTO.summaryFourOrgName">Please Select the Data Table 4 Sponsor Organization <b class="caret"></b></a>  
            <table class="dropdown-menu" id="dropdown-sum4Organization">
                <tr><th>PO ID</th><th>CTEP ID</th><th>Name</th></tr>
                <s:iterator var="orgItem" value="#sum4Orgs">
                <s:if test="%{#orgItem.getType() < 0}">
                    <tr><td colspan="3"><hr/></td></tr>
                </s:if>
                <s:else>
                    <tr><td><a href="javascript:void(0)" onclick="lookup4loadSummary4Sponsor(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getJSName()"/>')"><s:property value="#orgItem.getPoId()"/></a></td>
                    <td><a href="javascript:void(0)" onclick="lookup4loadSummary4Sponsor(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getJSName()"/>')"><s:property value="#orgItem.getCtepId()"/></a></td>
                    <td><a href="javascript:void(0)" onclick="lookup4loadSummary4Sponsor(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getJSName()"/>')"><s:property value="#orgItem.getHTMLName()"/></a></td></tr>
                </s:else>
                </s:iterator>
                <tr><td colspan="3"><a href="javascript:void(0)" onclick="lookup4loadSummary4Sponsor(-1, '')">Search...</a></td></tr>
            </table>
          </div>
      </div>
</div>
<div id="loadSummary4FundingSponsorField" >
<%@ include file="/WEB-INF/jsp/nodecorate/trialSummary4FundingSponsor.jsp" %>
</div>
