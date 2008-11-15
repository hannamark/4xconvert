<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<s:set name="isCreate" value="role.id == null" /> 
<s:set name="isNotCreate" value="role.id != null" /> 
<s:if test="%{isCreate}">
    <title>Create Health Care Facility</title>
</s:if>
<s:else>
    <title>Health Care Facility Details</title>
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
				<s:actionerror/> 
				<s:form action="%{formAction}" id="curateRoleForm">
                <s:if test="%{isCreate}">
                    The organization is not currently a health care facility.  Please select a status for the role and click 'Save' to add this role.
                </s:if>
                <s:else>
                    The organization is currently a health care facility.  You can modify the status of this role using the form below.
                </s:else>

				<s:hidden key="organization"/>
     			<s:select
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
<div style="clear:left;">
</div>    
    <div class="btnwrapper" style="margin-bottom:20px;">
    <po:buttonRow>
       <po:button id="save_button" href="javascript://noop/" onclick="$('curateRoleForm').submit();" style="save" text="Save"/>
        <c:url var="curateUrl" value="/protected/organization/curate/start.action">
            <c:param name="organization" value="${organization.id}"/>
        </c:url>
        <s:set name="returnToPageTitle" value="%{'Return to ' + getText('organization.details.title')}"/>
        <po:button id="return_to_button" href="${curateUrl}" style="continue" text="${returnToPageTitle}"/>
    </po:buttonRow>
    </div>
</body>
</html>    