<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<s:set name="isCreate" value="role.id == null" /> 
<s:set name="isNotCreate" value="role.id != null" /> 
<s:if test="%{isCreate}">
    <title>Create <s:text name="healthCareProvider"/></title>
</s:if>
<s:else>
   <c:if test="${fn:length(role.changeRequests) > 0}">
      <title><s:text name="healthCareProvider"/> Details - Comparison</title>
   </c:if>
   <c:if test="${fn:length(role.changeRequests) == 0}">
      <title><s:text name="healthCareProvider"/> Details</title>
   </c:if>
</s:else>

<%@include file="../../roleStatusOnChange_handleDuplicateOf.jsp" %>
</head> 
<body>

<s:if test="%{isNotCreate}">
    <c:if test="${fn:length(role.changeRequests) > 0}">
    <s:form action="ajax/roles/person/HealthCareProvider/changeCurrentChangeRequest.action" id="changeCrForm">
        <s:hidden key="person"/>
        <s:hidden key="rootKey"/>
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
    <h2><s:text name="healthCareProvider"/> Information</h2>
        <%@ include file="../personInfo.jsp" %>
		<div class="boxouter">
			<s:if test="%{isCreate}">
				<s:set name="formAction"
					value="'roles/person/HealthCareProvider/add.action'" />
				<h2><s:text name="healthCareProvider"/> Role Information</h2>
			</s:if> <s:else>
				<s:set name="formAction"
					value="'roles/person/HealthCareProvider/edit.action'" />
				<h2><s:text name="healthCareProvider"/> Role Information</h2>
			</s:else>
		    <div class="box_white">
				<s:actionerror/> 
				<s:form action="%{formAction}" id="curateRoleForm" onsubmit="return confirmThenSubmit('curateRoleForm');">
				<s:hidden key="cr"/>
				<s:hidden key="person"/>
				<s:hidden key="rootKey"/>
                <po:scoper key="healthCareProvider.scoper.id"/>
                
				<s:select id="curateRoleForm.role.status"
				   label="%{getText('healthCareProvider.status')}"
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
				   <po:field labelKey="healthCareProvider.duplicateOf">
						<select id="curateRoleForm.role.duplicateOf" name="role.duplicateOf">
						<option value="">--Select a Duplicate Of Entry (ID - SCOPER - STATUS - DATE)--</option>
						<c:forEach var="dupEntry" items="${availableDuplicateOfs}"> 
						   <option value="${dupEntry.id}">${dupEntry.id} - ${dupEntry.scoper.name} -  ${dupEntry.status} - <fmt:formatDate value="${dupEntry.statusDate}" pattern="yyyy-MM-dd"/></option>
						</c:forEach>
						</select>
				   </po:field>
                </c:if>
                </div>	
                
                <s:textfield 
                    id="curateRoleForm.role.certificateLicenseText" 
                    label="%{getText('healthCareProvider.certificateLicenseText')}" name="healthCareProvider.certificateLicenseText" maxlength="255" size="50"/>
                
                <input id="enableEnterSubmit" type="submit"/>			    
			    </s:form>
			    
		    </div>
        </div>
       <div class="boxouter">
       <h2>Address Information</h2>
            <%@ include file="../../../mailable/include.jsp" %>
       </div>
       
       <div class="boxouter_nobottom">
       <h2>Contact Information</h2>
           <div class="box_white">
               <div class="clear"></div>
               <po:contacts contactableKeyBase="role" emailRequired="false" phoneRequired="true" />
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

<div style="clear:left;"></div>    
    <div class="btnwrapper" style="margin-bottom:20px;">
    <%@include file="../../organizational/confirmThenSubmit.jsp" %>    
    <po:buttonRow>
       <po:button id="save_button" href="javascript://noop/" onclick="confirmThenSubmit('curateRoleForm');" style="save" text="Save"/>
       <c:url var="managePage" value="/protected/roles/person/HealthCareProvider/start.action">
           <c:param name="person" value="${person.id}"/>
       </c:url>
       <s:set name="managePageTitle" value="%{'Return to ' + getText('healthCareProvider.manage.title')}"/>
       <po:button id="return_to_button" href="${managePage}" onclick="" style="continue" text="${managePageTitle}"/>
    </po:buttonRow>
    </div>
</body>
</html>    