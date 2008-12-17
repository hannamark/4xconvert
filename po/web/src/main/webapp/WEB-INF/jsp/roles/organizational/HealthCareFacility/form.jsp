<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<s:set name="isCreate" value="role.id == null" /> 
<s:set name="isNotCreate" value="role.id != null" /> 
<s:if test="%{isCreate}">
    <title>Create Health Care Facility</title>
</s:if>
<s:else>
    <title>Edit Health Care Facility</title>
</s:else>
</head> 
<body>

<po:successMessages/>

<div id="page" style="margin-top:10px;">
    <div class="boxouter_nobottom">
    <h2>Health Care Facility Information</h2>
        <%@ include file="../orgInfo.jsp" %>
		<div class="boxouter">
			<s:if test="%{isCreate}">
				<s:set name="formAction"
					value="'roles/organizational/HealthCareFacility/add.action'" />
			</s:if> 
            <s:else>
				<s:set name="formAction"
					value="'roles/organizational/HealthCareFacility/edit.action'" />
			</s:else>
				<h2>Health Care Facility Role Information</h2>
		    <div class="box_white">
				<s:actionerror />
				<s:form action="%{formAction}" id="curateRoleForm" onsubmit="return confirmThenSubmit('curateRoleForm.role.status', 'curateRoleForm');">
                <s:if test="%{isCreate}">
                    This Organization does not have a role of Health Care Facility assigned to it. Please select a Status and click 'Save' button to assign the new role.
                </s:if>
                <s:else>
                    This Organization is currently a Health Care Facility.  To change the Role, please select a new Status and click the 'SAVE' button.
                </s:else>

				<s:hidden key="organization"/>
     			<s:select
     			   id="curateRoleForm.role.status"
				   label="%{getText('hcf.status')}"
				   name="role.status"
				   list="availableStatus"
				   listKey="name()"
				   listValue="name()"
				   value="role.status" 
				   headerKey="" headerValue="--Select a Role Status--" 
				   required="true" cssClass="required"
				   />
                 <input id="enableEnterSubmit" type="submit"/>			    
			    </s:form>
		    </div>
        </div>
    </div> 
</div>

<c:if test="${fn:length(role.changeRequests) > 0}">
<div id="page" style="margin-top:10px;">
<div id="crinfo">
    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
        <%@ include file="../orgInfo.jsp" %>    
        <div class="boxouter">
            <h2>Health Care Facility Role Change Request(s)</h2>
            <div class="box_white">
                <p>The table below lists the change requests for this Health Care Facility.</p>
                <display:table class="data" uid="row" name="role.changeRequests">
		        <po:displayTagProperties/>
		        <display:column titleKey="hcf.status" property="status" />
		        <display:column titleKey="th.action" class="action">
		           <po:copyButton
		             id="copy_curateCrForm_role_status"
		             onclick="selectValueInSelectField('${row.status}', 'curateRoleForm.role.status');" 
		                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
		           </po:copyButton>
		        </display:column>
		    </display:table>
            <div class="clear"></div>
            </div>
        </div>    
    </div>
</div>
</div>
</c:if>

<div style="clear:left;">
</div>    
    <div class="btnwrapper" style="margin-bottom:20px;">
    <%@include file="../../confirmThenSubmit.jsp" %> 
    <po:buttonRow>
       <po:button id="save_button" href="javascript://noop/" onclick="confirmThenSubmit('curateRoleForm.role.status', 'curateRoleForm');" style="save" text="Save"/>
        <c:url var="curateUrl" value="/protected/organization/curate/start.action">
            <c:param name="organization.id" value="${organization.id}"/>
        </c:url>
        <s:set name="returnToPageTitle" value="%{'Return to ' + getText('organization.details.title')}"/>
        <po:button id="return_to_button" href="${curateUrl}" style="continue" text="${returnToPageTitle}"/>
    </po:buttonRow>
    </div>
</body>
</html>    