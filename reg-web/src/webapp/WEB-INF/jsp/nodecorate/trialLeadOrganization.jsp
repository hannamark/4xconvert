<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-xs-8">
<s:set name="leadOrgs" value="@gov.nih.nci.registry.util.FilterOrganizationUtil@getLeadOrganization()" />
<s:hidden id="trialDTO.leadOrganizationIdentifier" value="" name="trialDTO.leadOrganizationIdentifier"/>
<s:hidden id="trialDTO.leadOrganizationName" value="" name="trialDTO.leadOrganizationName"/>
<div class="collapse navbar-collapse">
        <div class="nav navbar-nav" style="width: 100%;">
          <div class="active dropdown"><a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown" id="trialDTO.leadOrganizationNameField">Please Select the Lead Organization. <b class="caret"></b></a>  
            <table class="dropdown-menu">
            	<tr><th>PO ID</th><th>CTEP ID</th><th>Name</th></tr>
            	<s:iterator var="orgItem" value="#leadOrgs">
            	<s:if test="%{#orgItem.getType() < 0}">
            		<tr><td colspan="3"><hr/></td></tr>
            	</s:if>
            	<s:else>
        			<tr><td><a href="javascript:void(0)" onclick="lookup4loadleadorg(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getName()"/>')"><s:property value="#orgItem.getPoId()"/></a></td>
        			<td><a href="javascript:void(0)" onclick="lookup4loadleadorg(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getName()"/>')"><s:property value="#orgItem.getCtepId()"/></a></td>
        			<td><a href="javascript:void(0)" onclick="lookup4loadleadorg(<s:property value="#orgItem.getPoId()"/>, '<s:property value="#orgItem.getName()"/>')"><s:property value="#orgItem.getName()"/></a></td></tr>
        		</s:else>
        		</s:iterator>
        		<tr><td colspan="3"><a href="javascript:void(0)" onclick="lookup4loadleadorg(-1, '')">Search...</a></td></tr>
            </table>
          </div>
      </div>
</div>
<span class="formErrorMsg">
    <s:fielderror>
        <s:param>trialDTO.leadOrganizationIdentifier</s:param>
    </s:fielderror>
</span> 

</div>
