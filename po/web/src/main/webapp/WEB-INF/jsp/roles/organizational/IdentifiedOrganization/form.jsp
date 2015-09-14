<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<c:set var="isReadonly" value="${!role.isEditableBy(pageContext.request.remoteUser)}"/>
<s:set name="isCreate" value="role.id == null" />
<s:set name="isNotCreate" value="role.id != null" />
<s:if test="%{isCreate}">
    <c:set var="topic" scope="request" value="createio"/>
    <title>Create <s:text name="identifiedOrganization"/></title>
</s:if>
<s:else>
   <c:if test="${fn:length(role.changeRequests) > 0}">
      <title>Edit <s:text name="identifiedOrganization"/> - Comparison</title>
   </c:if>
   <c:if test="${fn:length(role.changeRequests) == 0}">
      <title>Edit <s:text name="identifiedOrganization"/></title>
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
        <s:param value="getText('identifiedOrganization')"/>
        <s:param>${fn:length(role.changeRequests)}</s:param>
    </s:text>
    </p>
    </div>
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
                <s:form action="%{formAction}" id="curateRoleForm" onsubmit="submitForm('curateRoleForm');">
                <s:token/>
                <s:hidden key="cr"/>
                <s:hidden key="organization"/>
                <s:if test="%{isNotCreate}"><s:hidden key="role.id" /></s:if>
                <s:set name="genericCodeValueService" value="@gov.nih.nci.po.util.PoRegistry@getGenericCodeValueService()" />
                <c:choose>
	               <c:when test="${isReadonly}">
	                    <po:field labelKey="identifiedOrganization.scoper">${role.scoper.name} (${role.scoper.id})</po:field>             
                        <po:field labelKey="identifiedOrganization.status">${role.status}</po:field>
	               </c:when>
	               <c:otherwise>
                        <po:scoper key="identifiedOrganization.scoper.id"/>
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
	               </c:otherwise>
	            </c:choose>
                
                <div id="duplicateOfDiv" <s:if test="role.status != @gov.nih.nci.po.data.bo.RoleStatus@NULLIFIED">style="display:none;"</s:if>>
                <c:if test="${fn:length(availableDuplicateOfs) > 0}">
                    <po:field labelKey="identifiedOrganization.duplicateOf">
                        <select id="curateRoleForm.duplicateOf" name="duplicateOf">
                            <option value="">--Select a Duplicate Of Entry (ID - STATUS - DATE)--</option>
                            <c:forEach var="dupEntry" items="${availableDuplicateOfs}">
                                <option value="${dupEntry.id}">${dupEntry.id} -  ${dupEntry.status} - <fmt:formatDate value="${dupEntry.statusDate}" pattern="yyyy-MM-dd"/></option>
                            </c:forEach>
                        </select>
                    </po:field>
                </c:if>
                </div>
                <s:if test="isNotCreate">                        
                    <po:createdBy createdByUserName="${role.createdByUserName}"/>  
                    <po:overriddenBy overriddenByUserName="${role.overriddenByUserName}"/>
                    <c:if test="${isReadonly}">
                        <div style="margin-bottom:16px;" id="io_override_div">
                            <c:url var="overrideUrl" value="/protected/roles/organizational/IdentifiedOrganization/override.action">
                                <c:param name="organization">${organization.id}</c:param>                                
                                <c:param name="role.id" value="${role.id}"/>
                            </c:url>
                            <po:buttonRow>                              
                                <po:button id="io_override_button" href="${overrideUrl}" style="entity_override" text="Override"/>                           
                            </po:buttonRow>
                        </div>
                        <br/>
                    </c:if>              
                </s:if>
                <div id="ass_iden_div" style="margin-top:24px;">
                    <fieldset>
	                    <legend><s:text name="identifiedOrganization.assignedIdentifier"/></legend>
	                    <s:fielderror><s:param>role.assignedIdentifier</s:param></s:fielderror>
	                    <po:isoIiForm formNameBase="curateRoleForm" ii="${role.assignedIdentifier}" iiKeyBase="role.assignedIdentifier" 
	                        iiLabelKeyBase="role.assignedIdentifier" required="true" readonly="${isReadonly}"/>
                    </fieldset>
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
    <s:form action="ajax/roles/organizational/IdentifiedOrganization/changeCurrentChangeRequest.action" id="changeCrForm" theme="simple">
        <s:hidden key="organization"/>
        <s:hidden key="role.id" />
        <s:select
           name="cr"
           list="selectChangeRequests"
           value="cr.id"
           onchange="$('curateRoleForm_cr').value = this.value; submitAjaxForm('changeCrForm','crinfo', null, true);"
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
    <%@include file="../../defineMapToShowConfirm.jsp" %>
    <po:buttonRow>       
       <c:if test="${!isReadonly}">
            <po:button id="save_button" href="javascript://noop/" onclick="submitForm('curateRoleForm');" style="save" text="Save"/>
       </c:if>
       <c:url var="managePage" value="/protected/roles/organizational/IdentifiedOrganization/start.action">
           <c:param name="organization" value="${organization.id}"/>
       </c:url>
       <s:set name="managePageTitle" value="%{'Return to ' + getText('identifiedOrganization.manage.title')}"/>
       <po:button id="return_to_button" href="${managePage}" onclick="" style="continue" text="${managePageTitle}"/>
    </po:buttonRow>
    </div>
</body>
</html>
