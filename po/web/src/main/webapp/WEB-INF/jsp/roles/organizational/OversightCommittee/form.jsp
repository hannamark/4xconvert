<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<c:set var="isReadonly" value="${!role.isEditableBy(pageContext.request.remoteUser)}"/>
<s:set name="isCreate" value="role.id == null" />
<s:set name="isNotCreate" value="role.id != null" />
<s:if test="%{isCreate}">
    <title>Create <s:text name="oversightCommittee"/></title>
</s:if>
<s:else>
    <c:set var="topic" scope="request" value="editoc"/>
   <c:if test="${fn:length(role.changeRequests) > 0}">
      <title>Edit <s:text name="oversightCommittee"/> - Comparison</title>
   </c:if>
   <c:if test="${fn:length(role.changeRequests) == 0}">
      <title>Edit <s:text name="oversightCommittee"/></title>
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
        <s:param value="getText('oversightCommittee')"/>
        <s:param>${fn:length(role.changeRequests)}</s:param>
    </s:text>
    </p>
    </div>
    </c:if>
</s:if>

<po:successMessages/>

<div id="page" style="margin-top:10px;">
    <div class="boxouter_nobottom">
    <h2><s:text name="oversightCommittee"/> Information</h2>
        <%@ include file="../orgInfo.jsp" %>
        <div class="boxouter">
            <s:if test="%{isCreate}">
                <s:set name="formAction"
                    value="'roles/organizational/OversightCommittee/add.action'" />
            </s:if> <s:else>
                <s:set name="formAction"
                    value="'roles/organizational/OversightCommittee/edit.action'" />
            </s:else>            
            <h2><s:text name="oversightCommittee"/> Role Information</h2>
            <div class="box_white">
                <s:actionerror/>
                <s:form action="%{formAction}" id="curateRoleForm" onsubmit="submitForm('curateRoleForm');">
                <s:token/>
                <s:hidden key="cr"/>
                <s:hidden key="organization"/>
                <s:hidden key="rootKey"/>
                <s:if test="%{isNotCreate}"><s:hidden key="role.id" /></s:if>
                <s:set name="genericCodeValueService" value="@gov.nih.nci.po.util.PoRegistry@getGenericCodeValueService()" />
                <s:set name="codeValueClass" value="@gov.nih.nci.po.data.bo.OversightCommitteeType@class"/>
                <s:set name="oversightCommitteeTypes" value="#genericCodeValueService.list(#codeValueClass)" />
                <c:choose>
                       <c:when test="${isReadonly}">                      
                         <po:field labelKey="oversightCommittee.typeCode">${role.typeCode.code}</po:field>                     
                       </c:when>
                       <c:otherwise>                       
                         <s:select
		                   label="%{getText('oversightCommittee.typeCode')}"
		                   name="role.typeCode"
		                   list="#oversightCommitteeTypes"
		                   listKey="id"
		                   listValue="code"
		                   value="role.typeCode.id"
		                   headerKey="" headerValue="--Select a Type--"
		                   required="true" cssClass="required"
		                   />
                       </c:otherwise>
                </c:choose>
                
                <c:choose>
                       <c:when test="${isReadonly}">                      
                         <po:field labelKey="oversightCommittee.status">${role.status}</po:field> 
                       </c:when>
                       <c:otherwise>                       
                         <s:select id="curateRoleForm.role.status"
		                   label="%{getText('oversightCommittee.status')}"
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
                        <po:field labelKey="oversightCommittee.duplicateOf">
                            <select id="curateRoleForm.duplicateOf" name="duplicateOf">
                                <option value="">--Select a Duplicate Of Entry (ID - TYPE - STATUS - DATE)--</option>
                                <c:forEach var="dupEntry" items="${availableDuplicateOfs}">
                                    <option value="${dupEntry.id}">${dupEntry.id} - ${dupEntry.typeCode.code} - ${dupEntry.status} - <fmt:formatDate value="${dupEntry.statusDate}" pattern="yyyy-MM-dd"/></option>
                                </c:forEach>
                            </select>
                        </po:field>
                    </c:if>
                </div>
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
                    <po:overriddenBy overriddenByUserName="${role.overriddenByUserName}"/>
                    <c:if test="${isReadonly}">
                        <div style="margin-bottom:30px;" id="overcomm_override_div">
                            <c:url var="overrideUrl" value="/protected/roles/organizational/OversightCommittee/override.action">
                                <c:param name="organization">${organization.id}</c:param>                                
                                <c:param name="role.id" value="${role.id}"/>
                            </c:url>
                            <po:buttonRow>                              
                                <po:button id="overcomm_override_button" href="${overrideUrl}" style="entity_override" text="Override"/>                           
                            </po:buttonRow>
                        </div> 
                    </c:if>              
                </s:if>
            </div>
        </div>
        
        <div class="boxouter">
            <h2>Address Information</h2>
            <po:addresses readonly="${isReadonly}" usOrCanadaFormatForValidationOnly="true"/>
        </div>

        <div class="boxouter_nobottom">
            <h2>Contact Information</h2>
            <div class="box_white">
                <div class="clear"></div>
                <po:contacts usOrCanadaFormatForValidationOnly="true" contactableKeyBase="role" emailRequired="false" phoneRequired="false" readonly="${isReadonly}"/>
            </div>
        </div>
        
    </div>
</div>

<c:if test="${fn:length(role.changeRequests) > 0}">
<div id="page" style="margin-top:10px;">
    <c:if test="${fn:length(role.changeRequests) > 1}">
    <div class="crselect">
    <s:form action="ajax/roles/organizational/OversightCommittee/changeCurrentChangeRequest.action" id="changeCrForm" theme="simple">
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
       <c:url var="managePage" value="/protected/roles/organizational/OversightCommittee/start.action">
           <c:param name="organization" value="${organization.id}"/>
       </c:url>
       <s:set name="managePageTitle" value="%{'Return to ' + getText('oversightCommittee.manage.title')}"/>
       <po:button id="return_to_button" href="${managePage}" onclick="" style="continue" text="${managePageTitle}"/>
    </po:buttonRow>
    </div>
</body>
</html>
