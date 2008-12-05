<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<s:set name="isCreate" value="role.id == null" /> 
<s:set name="isNotCreate" value="role.id != null" /> 
<s:if test="%{isCreate}">
    <title>Create <s:text name="identifiedOrganization"/></title>
</s:if>
<s:else>
   <c:if test="${fn:length(role.changeRequests) > 0}">
      <title><s:text name="identifiedOrganization"/> Details - Comparison</title>
   </c:if>
   <c:if test="${fn:length(role.changeRequests) == 0}">
      <title><s:text name="identifiedOrganization"/> Details</title>
   </c:if>
</s:else>

<script type="text/javascript">
function handleDuplicateOf() {
    $('duplicateOfDiv')[$('curateRoleForm.role.status').value == 'NULLIFIED' ? 'show' : 'hide'](); 
            
    if ($('curateRoleForm.role.status').value != 'NULLIFIED') {
        $('curateRoleForm.role.duplicateOf').value = '';
        $('wwctrl_curateRoleForm_role_scoper_id').innerHTML = '';
    }
    return true;
}
</script>
</head> 
<body>

<s:if test="%{isNotCreate}">
    <c:if test="${fn:length(role.changeRequests) > 0}">
    <s:form action="ajax/roles/organizational/IdentifiedOrganization/changeCurrentChangeRequest.action" id="changeCrForm">
        <s:hidden key="organization"/>
        <s:hidden key="role" />
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
    <h2><s:text name="identifiedOrganization"/> Information</h2>
        <%@ include file="../orgInfo.jsp" %>
		<div class="boxouter">
			<s:if test="%{isCreate}">
				<s:set name="formAction"
					value="'roles/organizational/IdentifiedOrganization/add.action'" />
				<h2><s:text name="identifiedOrganization"/> Role Information</h2>
			</s:if> <s:else>
				<s:set name="formAction"
					value="'roles/organizational/IdentifiedOrganization/edit.action'" />
				<h2><s:text name="identifiedOrganization"/> Role Information</h2>
			</s:else>
		    <div class="box_white">
				<s:actionerror/> 
				<s:form action="%{formAction}" id="curateRoleForm" onsubmit="return confirmThenSubmit('curateRoleForm');">
				<s:hidden key="cr"/>
				<s:hidden key="organization"/>
                <s:if test="%{isNotCreate}"><s:hidden key="role" value="%{role.id}"/></s:if>
                <s:set name="genericCodeValueService" value="@gov.nih.nci.po.util.PoRegistry@getGenericCodeValueService()" />
                <s:set name="codeValueClass" value="@gov.nih.nci.po.data.bo.IdentifiedOrganizationType@class"/>
                <s:set name="identifiedOrgTypes" value="#genericCodeValueService.list(#codeValueClass)" />
                <div class="wwgrp" id="wwgrp_curateRoleForm_role_scoper_id">
                    <s:fielderror>
                        <s:param value="%{'role.scoper'}"/>
                    </s:fielderror>
                    <div style="float:right;">
                        <c:url value="/protected/selector/organization/start.action" var="findScoperUrl">
                            <c:param name="source" value="${organization.id}"/>
                        </c:url>
                        <po:buttonRow>
                        <po:button id="select_scoper" href="javascript://noop/" onclick="showPopWin('${findScoperUrl}', 800, 800, showPopWinCallback);" style="search" text="Select Scoper"/>
                        </po:buttonRow>
                    </div>
                    <div class="wwlbl" id="wwlbl_curateRoleForm_role_scoper_id">
                        <label class="label" for="curateOrgForm_organization_duplicateOf_id">   
                        <span class="required">*</span>     
                        <s:text name="identifiedOrganization.scoper.id"/>:
                        </label>
                    </div>
                    <br/>
                    <div class="wwctrl" id="wwctrl_curateRoleForm_role_scoper_id">
                       ${role.scoper.id} 
                    </div>
                </div>
                <s:hidden key="role.scoper" id="curateRoleForm.role.scoper.id" required="true" cssClass="required"/>
                <s:select 
                   label="Identified Organization Type"
                   name="role.typeCode"
                   list="#identifiedOrgTypes"
                   listKey="id"
                   listValue="description"
                   value="role.typeCode.id" 
                   headerKey="" headerValue="--Select a Type--" 
                   required="true" cssClass="required" /> 
				<s:select id="curateRoleForm.role.status"
				   label="%{getText('identifiedOrganization.status')}"
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
                <c:if test="${fn:length(availableDuplicateOfs) > 0}">
				   <po:field labelKey="identifiedOrganization.duplicateOf">
						<select id="curateRoleForm.role.duplicateOf" name="role.duplicateOf">
						<option value="">--Select a Duplicate Of Entry (ID - TYPE - STATUS - DATE)--</option>
						<c:forEach var="dupEntry" items="${availableDuplicateOfs}"> 
						   <option value="${dupEntry.id}">${dupEntry.id} - ${dupEntry.typeCode.description} -  ${dupEntry.status} - <fmt:formatDate value="${dupEntry.statusDate}" pattern="yyyy-MM-dd"/></option>
						</c:forEach>
						</select>
				   </po:field>
                </c:if>
                </div>	
				<fieldset>
				    <legend><s:text name="identifiedOrganization.assignedIdentifier"/></legend>
				    <po:isoIiForm formNameBase="curateRoleForm" ii="${role.assignedIdentifier}" iiKeyBase="role.assignedIdentifier" iiLabelKeyBase="role.assignedIdentifier" required="true"/>
				</fieldset>                
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
    <script type="text/javascript">
        function showPopWinCallback(returnVal) {
            $('curateRoleForm.role.scoper.id').value = returnVal;
            $('wwctrl_curateRoleForm_role_scoper_id').innerHTML = $('curateRoleForm.role.scoper.id').value;
        }
    </script>    
    <%@include file="../confirmThenSubmit.jsp" %>    
    <po:buttonRow>
       <po:button id="save_button" href="javascript://noop/" onclick="confirmThenSubmit('curateRoleForm');" style="save" text="Save"/>
       <c:url var="managePage" value="/protected/roles/organizational/IdentifiedOrganization/start.action">
           <c:param name="organization" value="${organization.id}"/>
       </c:url>
       <s:set name="managePageTitle" value="%{'Return to ' + getText('identifiedOrganization.manage.title')}"/>
       <po:button id="return_to_button" href="${managePage}" onclick="" style="continue" text="${managePageTitle}"/>
    </po:buttonRow>
    </div>
</body>
</html>    