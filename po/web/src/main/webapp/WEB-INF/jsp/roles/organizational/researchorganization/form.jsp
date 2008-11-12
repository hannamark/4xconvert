<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<s:set name="isCreate" value="role.id == null" /> 
<s:if test="%{isCreate}">
<title>Create Research Organization</title>
</s:if>
<s:else>
   <c:if test="${fn:length(organization.changeRequests) > 0}">
      <title>Research Organization Details - Comparison</title>
   </c:if>
   <c:if test="${fn:length(organization.changeRequests) == 0}">
      <title>Research Organization Details</title>
   </c:if>
</s:else>

<script type="text/javascript">
function handleDuplicateOf() {
    $('duplicateOfDiv')[$('curateRoleForm.role.status').value == 'NULLIFIED' ? 'show' : 'hide'](); 
            
    if ($('curateRoleForm.role.status').value != 'NULLIFIED') {
        $('curateRoleForm.role.duplicateOf').value = '';
    }
    return true;
}
</script>
</head> 
<body>

<s:if test="%{!isCreate}">
    <c:if test="${fn:length(role.changeRequests) > 0}">
    <s:form action="ajax/roles/organizational/researchorganization/changeCurrentChangeRequest.action" id="changeCrForm">
        <s:hidden key="organization.id"/>
        <s:hidden key="role.id" />
        <s:hidden key="role.player.id"/>
        <s:select
           label="Current Change Request"
           name="cr"
           list="selectChangeRequests"
           value="cr.id"
           onchange="$('curateRoleForm_cr_id').value = this.value; submitAjaxForm('changeCrForm','crinfo', null, true);" 
           />
    </s:form>
    </c:if> 
</s:if> 

<po:successMessages/>

<div id="page" style="margin-top:10px;">
    <div class="boxouter_nobottom">
    <h2>Research Organization Information</h2>
        <%@ include file="../orgInfo.jsp" %>
		<div class="boxouter">
			<s:if test="%{isCreate}">
				<s:set name="formAction"
					value="'roles/organizational/researchorganization/addedit/add.action'" />
				<h2>Research Organization Role Information</h2>
			</s:if> <s:else>
				<s:set name="formAction"
					value="'roles/organizational/researchorganization/addedit/edit.action'" />
				<h2>Research Organization Role Information</h2>
			</s:else>
		    <div class="box_white">
				<s:actionerror/> 
				<s:form action="%{formAction}" id="curateRoleForm">
				<s:hidden key="cr.id"/>
				<s:hidden key="organization.id"/>
				<s:hidden key="role.id" />
				<s:hidden key="role.player.id"/>
				<s:set name="genericCodeValueService" value="@gov.nih.nci.po.util.PoRegistry@getGenericCodeValueService()" />
				<s:set name="codeValueClass" value="@gov.nih.nci.po.data.bo.ResearchOrganizationType@class"/>
				<s:set name="researchOrgTypes" value="#genericCodeValueService.list(#codeValueClass)" />
				<s:select 
				   label="Research Organization Type"
				   name="role.typeCode"
				   list="#researchOrgTypes"
				   listKey="id"
				   listValue="description"
				   value="role.typeCode.id" 
				   headerKey="" headerValue="--Select a Type--" 
				   required="true" cssClass="required" /> 
				<s:set name="researchOrgFundings" value="#{'U10':'U10', 'U01':'U01', 'P01':'P01', 'N01':'N01', 'P30':'P30'}" />
				<s:select
				   label="Funding Mechanism"
				   name="role.fundingMechanism"
				   list="#researchOrgFundings"
				   value="role.fundingMechanism" 
				   headerKey="" headerValue="--Select a Funding Mechanism--" 
				   required="true" cssClass="required" /> 
				<s:select id="curateRoleForm.role.status"
				   label="%{getText('researchOrganization.status')}"
				   name="role.status"
				   list="availableStatus"
				   listKey="name()"
				   listValue="name()"
				   value="role.status" 
				   headerKey="" headerValue="--Select a Role Status--" 
				   required="true" cssClass="required"
				   onchange="handleDuplicateOf();" 
				   />
                <div id="duplicateOfDiv" <s:if test="role.status != @gov.nih.nci.po.data.bo.RoleStatus@NULLIFIED">style="display:none;"</s:if>>
                    <div class="wwgrp" id="wwgrp_curateRoleForm_role_duplicateOf">
	                    <div class="wwlbl" id="wwlbl_curateRoleForm.role.duplicateOf">
		                    <label class="label" for="curateRoleForm.role.duplicateOf">        
		                    <s:text name="researchOrganization.duplicateOf"/>:
		                    </label>
	                    </div> 
	                    <br/>
	                    <div class="wwctrl" id="wwctrl_curateRoleForm.role.duplicateOf">
							<select id="curateRoleForm.role.duplicateOf" name="role.duplicateOf">
							<option value="">--Select a Duplicate Of Entry (ID - TYPE - FUNDING - STATUS - DATE)--</option>
							<c:forEach var="dupEntry" items="${availableDuplicateOfs}"> 
							   <option value="${dupEntry.id}">${dupEntry.id} - ${dupEntry.typeCode.description} - ${dupEntry.fundingMechanism} - ${dupEntry.status} - <fmt:formatDate value="${dupEntry.statusDate}" pattern="yyyy-MM-dd"/></option>
							</c:forEach>
							</select>
	                    </div>
                    </div>
                </div>	
                <input id="enableEnterSubmit" type="submit"/>			    
			    </s:form>
		    </div>
        </div>
    </div> 
</div>

<c:if test="${fn:length(role.changeRequests) > 0}">
<div id="page" style="margin-top:10px;">
<div id="crinfo">
<%@ include file="roleCrInfo.jsp" %>
</div>
</div>
</c:if>

<div style="clear:left;">
</div>    
    <div class="btnwrapper" style="margin-bottom:20px;">
    <po:buttonRow>
       <po:button id="save_button" href="javascript://noop/" onclick="$('curateRoleForm').submit();" style="save" text="Save"/>
       <c:url var="manageResearchOrgs" value="/protected/roles/organizational/researchorganization/start.action">
           <c:param name="organization.id" value="${organization.id}"/>
       </c:url>
       <po:button id="continue_button" href="${manageResearchOrgs}" onclick="" style="continue" text="Close"/>
    </po:buttonRow>
    </div>
</body>
</html>    