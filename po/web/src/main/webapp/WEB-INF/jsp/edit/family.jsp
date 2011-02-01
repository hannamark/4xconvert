<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <s:set name="isCreate" value="family.id == null"/>
    <s:set name="isNotCreate" value="family.id != null"/>
    <s:if test="%{isCreate}">
        <title>Create <s:text name="family"/></title>
    </s:if>
    <s:else>
       <title><s:text name="family.details.title"/></title>
    </s:else>
</head>
<body> 
<po:successMessages />
<s:actionerror/>
<div class="boxouter">
    <h2><s:text name="family"/> Information</h2>

    <s:if test="%{isCreate}">
       <s:set name="formAction" value="'create.action'"/>
    </s:if>
    <s:else>
       <s:set name="formAction" value="'submit.action'"/>
    </s:else>
    <s:form action="%{formAction}" id="familyEntityForm" onsubmit="$('familyEntityForm.family.comments').value = $F('familyEntityForm.family.commentsText'); return isTelecomFieldsBlank() && confirmThenSubmit('familyEntityForm.family.statusCode',document.forms.familyEntityForm);">
        <input id="enableEnterSubmit" type="submit"/>
        <s:hidden key="rootKey"/>
        <s:hidden key="family.id"/>
 
        <div class="box_white">
        <s:if test="isCreate">
            <s:select
               label="%{getText('family.statusCode')}"
               name="family.statusCode"
               list="availableStatus"
               listKey="name()"
               listValue="name()"
               value="family.statusCode"
               required="true" cssClass="required"
               id="familyEntityForm.family.statusCode"/>
        </s:if>
        <s:else>
            <po:inputRow>
            <po:inputRowElement><po:field labelKey="family.id">${family.id}</po:field></po:inputRowElement>
            <po:inputRowElement>&nbsp;</po:inputRowElement>
            <po:inputRowElement><po:field labelKey="family.statusCode">${family.statusCode}</po:field></po:inputRowElement>
            <po:inputRowElement>&nbsp;</po:inputRowElement>
            </po:inputRow>
            <s:select
                   label="New %{getText('family.statusCode')}"
                   name="family.statusCode"
                   list="availableStatus"
                   listKey="name()"
                   listValue="name()"
                   value="family.statusCode"
                   headerKey="" headerValue="--Select a Status--"
                   required="true" cssClass="required"
                   id="familyEntityForm.family.statusCode"/>
        </s:else>
            <s:textfield key="family.name" required="true" cssClass="required" size="70"/>
            <div class="clear"></div>
        </div>
    </s:form>
</div>
<div class="btnwrapper" style="margin-bottom:20px;">
    <po:buttonRow>
        <po:button id="save_button" href="javascript://noop/" onclick="$('familyEntityForm').submit();" style="save" text="Save"/>
        <c:url var="listUrl" value="/protected/search/family/list.action" />
        <s:set name="returnToPageTitle" value="%{'Return to ' + getText('family.search.title')}"/>
        <po:button id="return_to_button" href="${listUrl}" style="continue" text="${returnToPageTitle}"/>
    </po:buttonRow>
</div>

<s:if test="isNotCreate">
<div class="clear"></div> 
<div class="line"></div> 
<div id="org_family"> 
    <%@include file="familyOrganizationList.jsp"%>
</div> 
</s:if>

</body>
</html>