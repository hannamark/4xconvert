<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<s:set name="isReadonly" value="role.ctepOwned" />
<s:set name="isCreate" value="role.id == null" />
<s:set name="isNotCreate" value="role.id != null" />
<s:if test="%{isCreate}">
    <title>Create <s:text name="healthCareFacility"/></title>
</s:if>
<s:else>
   <c:if test="${fn:length(role.changeRequests) > 0}">
      <title>Edit <s:text name="healthCareFacility"/> - Comparison</title>
   </c:if>
   <c:if test="${fn:length(role.changeRequests) == 0}">
      <title>Edit <s:text name="healthCareFacility"/></title>
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
        <s:param value="getText('healthCareFacility')"/>
        <s:param>${fn:length(role.changeRequests)}</s:param>
    </s:text>
    </p>
    </div>
    </c:if>
</s:if>

<po:successMessages/>

<div id="page" style="margin-top:10px;">
    <div class="boxouter_nobottom">
    <h2><s:text name="healthCareFacility"/> Information</h2>
        <%@ include file="../orgInfo.jsp" %>
        <div class="boxouter">
            <s:if test="%{isCreate}">
                <s:set name="formAction" value="'roles/organizational/HealthCareFacility/add.action'" />
            </s:if>
            <s:else>
                <s:set name="formAction" value="'roles/organizational/HealthCareFacility/edit.action'" />
            </s:else>
            <s:if test="%{isReadonly}">
                <s:set name="formTheme" value="'css_xhtml_readonly'" />
            </s:if>
            <s:else>
                <s:set name="formTheme" value="'css_xhtml'" />
            </s:else>
            <h2><s:text name="healthCareFacility"/> Role Information</h2>
            <div class="box_white">
                <s:actionerror/>
                <s:form action="%{formAction}" theme="%{formTheme}" id="curateRoleForm" onsubmit="return isTelecomFieldsBlank() && isAliasFieldBlank() && confirmThenSubmit('curateRoleForm.role.status', 'curateRoleForm');">
                <s:token/>
                <s:hidden key="cr"/>
                <s:hidden key="organization"/>
                <s:hidden key="rootKey"/>

                <s:textfield
                    id="curateRoleForm.role.name"
                    label="%{getText('healthCareFacility.name')}" name="role.name" value="%{role.name}" maxlength="160" size="50"/>
                <s:if test="%{isReadonly}">
                    <s:textfield label="%{getText('healthCareFacility.status')}" name="role.status" required="true" cssClass="required"/>
                </s:if>
                <s:else>
                    <s:select id="curateRoleForm.role.status"
                       label="%{getText('healthCareFacility.status')}"
                       name="role.status"
                       list="availableStatus"
                       listKey="name()"
                       listValue="name()"
                       value="role.status"
                       headerKey="" headerValue="--Select a Role Status--"
                       required="true" cssClass="required"
                       onchange="handleDuplicateOf();"
                       />
                </s:else>
                <div id="duplicateOfDiv" <s:if test="role.status != @gov.nih.nci.po.data.bo.RoleStatus@NULLIFIED">style="display:none;"</s:if>>
                <c:if test="${fn:length(availableDuplicateOfs) > 0}">
                   <po:field labelKey="healthCareFacility.duplicateOf">
                        <select id="curateRoleForm.duplicateOf" name="duplicateOf">
                        <option value="">--Select a Duplicate Of Entry (ID - STATUS - DATE)--</option>
                        <c:forEach var="dupEntry" items="${availableDuplicateOfs}">
                            <option value="${dupEntry.id}">${dupEntry.id} -  ${dupEntry.status} - <fmt:formatDate value="${dupEntry.statusDate}" pattern="yyyy-MM-dd"/></option>
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
                </s:if>
            </div>
        </div>
        
        <div class="boxouter">
        <h2>Aliases</h2>
            <div class="box_white">                
                <div class="clear"></div>                
                <po:aliases aliasKeyBase="organization" readonly="${role.ctepOwned}"/>
            </div>
        </div>
        
       <div class="boxouter">
       <h2>Address Information</h2>
            <po:addresses readonly="${role.ctepOwned}" usOrCanadaFormatForValidationOnly="true"/>
       </div>

       <div class="boxouter_nobottom">
       <h2>Contact Information</h2>
           <div class="box_white">
               <div class="clear"></div>
               <po:contacts usOrCanadaFormatForValidationOnly="true" contactableKeyBase="role" emailRequired="false" phoneRequired="false" readonly="${role.ctepOwned}"/>
           </div>
       </div>
    </div>
</div>

<c:if test="${fn:length(role.changeRequests) > 0}">
<div id="page" style="margin-top:10px;">
    <c:if test="${fn:length(role.changeRequests) > 1}">
    <div class="crselect">
    <s:form action="ajax/roles/organizational/HealthCareFacility/changeCurrentChangeRequest.action" id="changeCrForm" theme="simple">
        <s:hidden key="organization"/>
        <s:hidden key="rootKey"/>
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

<div style="clear:left;"></div>
    <div class="btnwrapper" style="margin-bottom:20px;">
    <%@include file="../../defineMapToShowConfirm.jsp" %>
    <po:buttonRow>
       <s:if test="!#isReadonly">
       <po:button id="save_button" href="javascript://noop/" onclick="return ((isTelecomFieldsBlank()==true && isAliasFieldBlank()==true) ? confirmThenSubmit('curateRoleForm.role.status', 'curateRoleForm'):false);" style="save" text="Save"/>
       </s:if>
       <c:url var="managePage" value="/protected/roles/organizational/HealthCareFacility/start.action">
           <c:param name="organization" value="${organization.id}"/>
       </c:url>
       <s:set name="managePageTitle" value="%{'Return to ' + getText('healthCareFacility.manage.title')}"/>
       <po:button id="return_to_button" href="${managePage}" onclick="" style="continue" text="${managePageTitle}"/>
    </po:buttonRow>
    </div>
</body>
</html>
