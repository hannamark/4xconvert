<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<s:set name="isCreate" value="role.id == null" /> 
<s:set name="isNotCreate" value="role.id != null" /> 
<s:if test="%{isCreate}">
    <title>Create <s:text name="researchOrganization"/></title>
</s:if>
<s:else>
   <c:if test="${fn:length(role.changeRequests) > 0}">
      <title>Edit <s:text name="researchOrganization"/> - Comparison</title>
   </c:if>
   <c:if test="${fn:length(role.changeRequests) == 0}">
      <title>Edit <s:text name="researchOrganization"/></title>
   </c:if>
</s:else>

<%@include file="../../roleStatusOnChange_handleDuplicateOf.jsp" %>
</head> 
<body>

<s:if test="%{isNotCreate}">
    <c:if test="${fn:length(role.changeRequests) > 0}">
    <div>
    <p class="directions">
    <s:text name="curation.instructions.role.changerequests">
        <s:param value="getText('researchOrganization')"/>
        <s:param>${fn:length(role.changeRequests)}</s:param>
    </s:text>
    </p>
    </div>
    </c:if> 
</s:if>


<po:successMessages/>

<div id="page" style="margin-top:10px;">
    <div class="boxouter_nobottom">
    <h2><s:text name="researchOrganization"/> Information</h2>
        <%@ include file="../orgInfo.jsp" %>
		<div class="boxouter">
			<s:if test="%{isCreate}">
				<s:set name="formAction"
					value="'roles/organizational/ResearchOrganization/add.action'" />
			</s:if> <s:else>
				<s:set name="formAction"
					value="'roles/organizational/ResearchOrganization/edit.action'" />
			</s:else>
			<h2><s:text name="researchOrganization"/> Role Information</h2>
		    <div class="box_white">
				<s:actionerror/>
				<s:form action="%{formAction}" id="curateRoleForm" onsubmit="return confirmThenSubmit('curateRoleForm.role.status', 'curateRoleForm');">
				<s:hidden key="cr"/>
				<s:hidden key="organization"/>
				<s:if test="%{isNotCreate}"><s:hidden key="role.id"/></s:if>
				<script type="text/javascript"><!--
				/*
				 Toggles the display of a stateOrProvince textfield or a select-box. 
				*/
				function curateRoleForm_displayFundingMechanism(ResearchOrganizationType_id) {
					var url =  '' + contextPath + '/protected/ajax/roles/organizational/ResearchOrganization/funding/changeResearchOrganizationType.action?researchOrganizationType.id=' + ResearchOrganizationType_id;
				    loadDiv(url, 'curateRoleForm_displayFundingMechanism', true);
				}
				--></script>
				<s:set name="genericCodeValueService" value="@gov.nih.nci.po.util.PoRegistry@getGenericCodeValueService()" />
				<s:set name="codeValueClass" value="@gov.nih.nci.po.data.bo.ResearchOrganizationType@class"/>
				<s:set name="researchOrgTypes" value="#genericCodeValueService.list(#codeValueClass)" />
				<s:select 
				   label="%{getText('researchOrganization.typeCode')}"
				   name="role.typeCode"
				   list="#researchOrgTypes"
				   listKey="id"
				   listValue="description"
				   value="role.typeCode.id" 
				   headerKey="" headerValue="--Select a Type--" 
				   required="true" cssClass="required" 
				   onchange="return curateRoleForm_displayFundingMechanism(this.value);"/> 
			    <s:hidden key="role.fundingMechanism" id="curateRoleForm.role.fundingMechanism"/>
				<div id="curateRoleForm_displayFundingMechanism">
				    <%@ include file="selectFundingMechanism.jsp" %>
				</div>
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
                <c:if test="${fn:length(availableDuplicateOfs) > 0}">
				   <po:field labelKey="researchOrganization.duplicateOf">
							<select id="curateRoleForm.role.duplicateOf" name="researchOrganization.duplicateOf">
							<option value="">--Select a Duplicate Of Entry (ID - TYPE - FUNDING - STATUS - DATE)--</option>
							<c:forEach var="dupEntry" items="${availableDuplicateOfs}"> 
							   <option value="${dupEntry.id}">${dupEntry.id} - ${dupEntry.typeCode.description} - ${dupEntry.fundingMechanism} - ${dupEntry.status} - <fmt:formatDate value="${dupEntry.statusDate}" pattern="yyyy-MM-dd"/></option>
							</c:forEach>
							</select>
				   </po:field>
      		    </c:if>
                </div>	
                <input id="enableEnterSubmit" type="submit"/>			    
			    </s:form>
		    </div>
        </div>
    </div> 
</div>

<c:if test="${fn:length(role.changeRequests) > 0}">
<div id="page" style="margin-top:10px;">
    <c:if test="${fn:length(role.changeRequests) > 1}">
    <div class="crselect">
    <s:form action="ajax/roles/organizational/ResearchOrganization/changeCurrentChangeRequest.action" id="changeCrForm" theme="simple">
        <s:hidden key="organization"/>
        <s:hidden key="role.id" />
        <s:select
           name="cr"
           list="selectChangeRequests"
           value="cr.id"
           onchange="$('curateRoleForm_cr_id').value = this.value; submitAjaxForm('changeCrForm','crinfo', null, true);"
           />
    </s:form>
    </div>
    </c:if>
<div id="crinfo">
<%@ include file="roleCrInfo.jsp" %>
</div>
</div>
</c:if>

<div style="clear:left;">
</div>    
    <div class="btnwrapper" style="margin-bottom:20px;">
    <%@include file="../../confirmThenSubmit.jsp" %>    
    <po:buttonRow>
       <po:button id="save_button" href="javascript://noop/" onclick="confirmThenSubmit('curateRoleForm.role.status', 'curateRoleForm');" style="save" text="Save"/>
       <c:url var="managePage" value="/protected/roles/organizational/ResearchOrganization/start.action">
           <c:param name="organization" value="${organization.id}"/>
       </c:url>
       <s:set name="managePageTitle" value="%{'Return to ' + getText('researchOrganization.manage.title')}"/>
       <po:button id="return_to_button" href="${managePage}" onclick="" style="continue" text="${managePageTitle}"/>
    </po:buttonRow>
    </div>
</body>
</html>    