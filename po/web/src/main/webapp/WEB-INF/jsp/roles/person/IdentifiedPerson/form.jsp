<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<s:set name="isCreate" value="role.id == null" />
<s:set name="isNotCreate" value="role.id != null" />
<s:if test="%{isCreate}">
    <c:set var="topic" scope="request" value="createopi"/>
    <title>Create <s:text name="identifiedPerson"/></title>
</s:if>
<s:else>
    <c:set var="topic" scope="request" value="editopi"/>
   <c:if test="${fn:length(role.changeRequests) > 0}">
      <title>Edit <s:text name="identifiedPerson"/> - Comparison</title>
   </c:if>
   <c:if test="${fn:length(role.changeRequests) == 0}">
      <title>Edit <s:text name="identifiedPerson"/></title>
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
        <s:param value="getText('identifiedPerson')"/>
        <s:param>${fn:length(role.changeRequests)}</s:param>
    </s:text>
    </p>
    </div>
    </c:if>
</s:if>


<po:successMessages/>

<div id="page" style="margin-top:10px;">
    <div class="boxouter_nobottom">
    <h2><s:text name="identifiedPerson"/> Information</h2>
        <%@ include file="../personInfo.jsp" %>
        <div class="boxouter">
            <s:if test="%{isCreate}">
                <s:set name="formAction"
                    value="'roles/person/IdentifiedPerson/add.action'" />
                <h2><s:text name="identifiedPerson"/> Role Information</h2>
            </s:if> <s:else>
                <s:set name="formAction"
                    value="'roles/person/IdentifiedPerson/edit.action'" />
                <h2><s:text name="identifiedPerson"/> Role Information</h2>
            </s:else>
            <div class="box_white">
                <s:actionerror/>
                <s:form action="%{formAction}" id="curateRoleForm" onsubmit="return confirmThenSubmit('curateRoleForm.role.status', 'curateRoleForm');">
                <s:token/>
                <s:hidden key="cr"/>
                <s:hidden key="person"/>
                <s:if test="%{isNotCreate}"><s:hidden key="role.id" /></s:if>
                <s:set name="genericCodeValueService" value="@gov.nih.nci.po.util.PoRegistry@getGenericCodeValueService()" />
                <po:scoper key="identifiedPerson.scoper.id"/>
                <s:select id="curateRoleForm.role.status"
                   label="%{getText('identifiedPerson.status')}"
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
                   <po:field labelKey="identifiedPerson.duplicateOf">
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
                </s:if>
                
                <fieldset>
                    <legend><s:text name="identifiedPerson.assignedIdentifier"/></legend>
                    <s:fielderror><s:param>role.assignedIdentifier</s:param></s:fielderror>
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
    <c:if test="${fn:length(role.changeRequests) > 1}">
    <div class="crselect">
    <s:form action="ajax/roles/person/IdentifiedPerson/changeCurrentChangeRequest.action" id="changeCrForm" theme="simple">
        <s:hidden key="person"/>
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
       <po:button id="save_button" href="javascript://noop/" onclick="confirmThenSubmit('curateRoleForm.role.status', 'curateRoleForm');" style="save" text="Save"/>
       <c:url var="managePage" value="/protected/roles/person/IdentifiedPerson/start.action">
           <c:param name="person" value="${person.id}"/>
       </c:url>
       <s:set name="managePageTitle" value="%{'Return to ' + getText('identifiedPerson.manage.title')}"/>
       <po:button id="return_to_button" href="${managePage}" onclick="" style="continue" text="${managePageTitle}"/>
    </po:buttonRow>
    </div>
</body>
</html>
