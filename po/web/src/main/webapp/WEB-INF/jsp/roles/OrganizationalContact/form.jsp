<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="setOCType.jsp" %>
<html>
<head>
<c:set var="isReadonly" value="${!role.isEditableBy(pageContext.request.remoteUser)}"/>
<s:set name="isCreate" value="role.id == null" />
<s:set name="isNotCreate" value="role.id != null" />
<c:set var="usePlayerDefaults" value="${role.id == null && input}"/>
<s:if test="%{isCreate}">
    <title>Create <s:text name="organizationalContact"/></title>
</s:if>
<s:else>
    <c:set var="topic" scope="request" value="editcontact"/>
   <c:if test="${fn:length(role.changeRequests) > 0}">
      <title>Edit <s:text name="organizationalContact"/> - Comparison</title>
   </c:if>
   <c:if test="${fn:length(role.changeRequests) == 0}">
      <title>Edit <s:text name="organizationalContact"/></title>
   </c:if>
</s:else>

<%@include file="../roleStatusOnChange_handleDuplicateOf.jsp" %>
<%@include file="../roleStatusOnChange_handlePhoneReq.jsp" %>
</head>
<body>

<s:if test="%{isNotCreate}">
    <c:if test="${fn:length(role.changeRequests) > 0}">
    <div>
    <p class="directions">
    <s:text name="curation.instructions.role.changerequests">
        <s:param value="getText('organizationalContact')"/>
        <s:param>${fn:length(role.changeRequests)}</s:param>
    </s:text>
    </p>
    </div>
    </c:if>
</s:if>

<po:successMessages/>

<div id="page" style="margin-top:10px;">
    <div class="boxouter_nobottom">
    <h2><s:text name="organizationalContact"/> Information</h2>
        <s:if test="%{#ocType == 'organizational'}">
            <%@ include file="../organizational/orgInfo.jsp" %>
        </s:if>
        <s:else>
            <%@ include file="../person/personInfo.jsp" %>
        </s:else>
        <div class="boxouter">
            <s:if test="%{isCreate}">
                <s:set name="formAction" value="'roles/' + #ocType + '/OrganizationalContact/add.action'" />
            </s:if> <s:else>
                <s:set name="formAction" value="'roles/' + #ocType + '/OrganizationalContact/edit.action'" />
            </s:else>
            <h2><s:text name="organizationalContact"/> Role Information</h2>
            <div class="box_white">
                <s:actionerror/>
                <s:form action="%{formAction}" id="curateRoleForm" onsubmit="return isTelecomFieldsBlank() && submitForm('curateRoleForm');">
                    <s:token/>
                    <s:hidden key="cr"/>
                    <s:hidden key="rootKey"/>
                    <s:if test="%{#ocType == 'organizational'}">
                        <s:hidden key="organization"/>
                        <c:choose>
			               <c:when test="${isReadonly}">
			                 <po:field labelKey="organizationalContact.title">${role.title}</po:field>        
			               </c:when>
			               <c:otherwise>
			                 <s:textfield label="%{getText('organizationalContact.title')}" name="role.title" required="true"/>
			               </c:otherwise>
			            </c:choose>                        
                        <s:hidden key="genericContact" value="true"/>
                    </s:if>
                    <s:else>
                        <s:hidden key="person"/>
                        <po:scoper key="organizationalContact.scoper.id"/>
                    </s:else>

                    <c:choose>
                       <c:when test="${(ocType == 'organizational') && isReadonly}">
                         <po:field labelKey="organizationalContact.status">${role.status}</po:field>        
                       </c:when>
                       <c:otherwise>
                         <s:select id="curateRoleForm.role.status"
	                       label="%{getText('organizationalContact.status')}"
	                       name="role.status"
	                       list="availableStatus"
	                       listKey="name()"
	                       listValue="name()"
	                       value="role.status"
	                       headerKey="" headerValue="--Select a Role Status--"
	                       required="true" cssClass="required"
	                       onchange="handlePhoneReq();handleDuplicateOf();"
	                       />
                       </c:otherwise>
                    </c:choose> 
                    
                    <div id="duplicateOfDiv" <s:if test="role.status != @gov.nih.nci.po.data.bo.RoleStatus@NULLIFIED">style="display:none;"</s:if>>
                        <c:if test="${fn:length(availableDuplicateOfs) > 0}">
                           <po:field labelKey="organizationalContact.duplicateOf">
                                <select id="curateRoleForm.duplicateOf" name="duplicateOf">
                                    <s:if test="%{#ocType == 'organizational'}">
                                        <option value="">--Select a Duplicate Of Entry (ID - TITLE - STATUS - DATE)--</option>
                                    </s:if>
                                    <s:else>
                                        <option value="">--Select a Duplicate Of Entry (ID - SCOPER - STATUS - DATE)--</option>
                                    </s:else>
                                    <c:forEach var="dupEntry" items="${availableDuplicateOfs}">
                                        <s:if test="%{#ocType == 'organizational'}">
                                            <s:set id="dupCaption">${dupEntry.title}</s:set>
                                        </s:if>
                                        <s:else>
                                            <s:set id="dupCaption">${dupEntry.scoper.name}</s:set>
                                        </s:else>
                                        <option value="${dupEntry.id}">${dupEntry.id} - ${dupCaption} -  ${dupEntry.status} - <fmt:formatDate value="${dupEntry.statusDate}" pattern="yyyy-MM-dd"/></option>
                                    </c:forEach>
                                </select>
                           </po:field>
                        </c:if>
                    </div>

                    <s:set name="genericCodeValueService" value="@gov.nih.nci.po.util.PoRegistry@getGenericCodeValueService()" />
                    <s:set name="codeValueClass" value="@gov.nih.nci.po.data.bo.OrganizationalContactType@class"/>
                    <s:set name="orgContactTypes" value="#genericCodeValueService.list(#codeValueClass)" />
                    <c:choose>
                       <c:when test="${(ocType == 'organizational') && isReadonly}">
                         <po:field labelKey="organizationalContact.type">${role.type.code}</po:field>        
                       </c:when>
                       <c:otherwise>
                         <s:select
	                       id="curateRoleForm.role.type"
	                       label="%{getText('organizationalContact.type')}"
	                       name="role.type"
	                       list="orgContactTypes"
	                       listKey="id"
	                       listValue="code"
	                       value="role.type.id"
	                       headerKey="" headerValue="--Select a Contact Type--"
	                       required="true" cssClass="required"
	                       />
                       </c:otherwise>
                    </c:choose>                    

                    <input id="enableEnterSubmit" type="submit"/>
                </s:form>
                <c:if test="${!empty role.otherIdentifiers}">
                    <label>Other IDs</label><br/>
                    <c:forEach var="otherId" items="${role.otherIdentifiers}">
                        ${otherId.identifierName}: ${otherId.extension}<br/>
                    </c:forEach>
                </c:if>
                <s:if test="isNotCreate">                        
                    <po:createdBy createdByUserName="${role.createdByUserName}"/>
                    <s:if test="%{#ocType == 'organizational'}">
                        <po:overriddenBy overriddenByUserName="${role.overriddenByUserName}"/>
	                    <c:if test="${isReadonly}">
	                        <div style="margin-bottom:30px;" id="orgcont_override_div">
	                            <c:url var="overrideUrl" value="/protected/roles/organizational/OrganizationalContact/override.action">
	                                <c:param name="organization">${organization.id}</c:param>                                
	                                <c:param name="role.id" value="${role.id}"/>
	                            </c:url>
	                            <po:buttonRow>                              
	                                <po:button id="orgcont_override_button" href="${overrideUrl}" style="entity_override" text="Override"/>                           
	                            </po:buttonRow>
	                        </div> 
	                    </c:if> 
                    </s:if>                                  
                </s:if>
            </div>
        </div>
        <div class="boxouter">
            <h2>Address Information</h2>
            <po:addresses usOrCanadaFormatForValidationOnly="true" readonly="${(ocType == 'organizational') && isReadonly}"/>
        </div>

        <div class="boxouter_nobottom">
        <h2>Contact Information</h2>
            <div class="box_white">
                <div class="clear"></div>
                <po:contacts contactableKeyBase="role" emailRequired="false" phoneRequired="false"
                    emailOrPhoneRequired="${role.status == 'ACTIVE'}" 
                    usOrCanadaFormatForValidationOnly="true"
                    defaultEmails="${usePlayerDefaults && person.id != null}" defaultPhones="${usePlayerDefaults && person.id != null}" 
                    defaultFaxes="${usePlayerDefaults && person.id != null}" readonly="${(ocType == 'organizational') && isReadonly}"/>
            </div>
        </div>
    </div>
</div>

<c:if test="${fn:length(role.changeRequests) > 0}">
    <div id="page" style="margin-top:10px;">
        <c:if test="${fn:length(role.changeRequests) > 1}">
            <div class="crselect">
            <s:form action="ajax/roles/%{#ocType}/OrganizationalContact/changeCurrentChangeRequest.action" id="changeCrForm">
                <s:if test="%{#ocType == 'organizational'}">
                    <s:hidden key="organization"/>
                </s:if>
                <s:else>
                    <s:hidden key="person"/>
                </s:else>
                <s:hidden key="rootKey"/>
                <s:select name="cr" list="selectChangeRequests" value="cr.id"
                          onchange="$('curateRoleForm_cr').value = this.value; submitAjaxForm('changeCrForm','crinfo', null, true);" />
            </s:form>
            </div>
        </c:if>
        <div id="crinfo">
            <%@ include file="roleCrInfo.jsp" %>
        </div>
    </div>
</c:if>

<div style="clear:left;"></div>
    <div class="btnwrapper" style="margin-bottom:20px;">
        <%@include file="../defineMapToShowConfirm.jsp" %>
        <po:buttonRow>            
            <c:if test="${(ocType != 'organizational') || !isReadonly}">
	            <po:button id="save_button" href="javascript://noop/" onclick="return ((isTelecomFieldsBlank()==true) ? submitForm('curateRoleForm'):false);" style="save" text="Save"/>
	        </c:if>
            <c:url var="managePage" value="/protected/roles/${ocType}/OrganizationalContact/start.action">
                <s:if test="%{#ocType == 'organizational'}">
                    <c:param name="organization" value="${organization.id}"/>
                </s:if>
                <s:else>
                    <c:param name="person" value="${person.id}"/>
                </s:else>
            </c:url>
            <s:set name="managePageTitle" value="%{'Return to ' + getText('organizationalContact.manage.title')}"/>
            <po:button id="return_to_button" href="${managePage}" onclick="" style="continue" text="${managePageTitle}"/>
        </po:buttonRow>
    </div>
</body>
</html>
