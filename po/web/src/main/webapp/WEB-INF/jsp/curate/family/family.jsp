<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<html>
<head>
    <s:set name="isCreate" value="family.id == null"/>
    <s:set name="isNotCreate" value="family.id != null"/>
    <s:if test="%{isCreate}">
        <title>Create <s:text name="family"/></title>
        <c:set var="topic" scope="request" value="createfamily"/>
    </s:if>
    <s:else>
       <title><s:text name="family.details.title"/></title>
       <c:set var="topic" scope="request" value="editfamily"/>
    </s:else>
    <script type="text/javascript">
        function confirmThenSubmit(fieldId, formId){
           var map = new Array();
           map['NULLIFIED'] = '<s:text name="entity.edit.nullified.confirmation"/>';
           map['INACTIVE'] = '<s:text name="entity.edit.inactive.confirmation"/>';
           finalConfirmThenSubmitNoDuplicateOfCheck($(fieldId),$(formId),map);
        }
    </script>
    <%@include file="../../confirmThenSubmit.jsp" %>
</head>
<body> 
<po:successMessages />
<s:actionerror/>
<div class="boxouter">
    <h2><s:text name="family"/> Information</h2>

    <s:if test="%{isCreate}">
       <s:set name="formAction" value="'family/create/save.action'"/>
    </s:if>
    <s:else>
       <s:set name="formAction" value="'family/curate/submit.action'"/>
    </s:else>
    <s:form action="%{formAction}" id="familyEntityForm">
        <s:token/>
        <div class="box_white">
        <s:if test="isCreate">
            <s:hidden id="familyEntityForm.family.statusCode" key="family.statusCode"/>
            <po:inputRowElement><po:field labelKey="family.statusCode">${family.statusCode}</po:field></po:inputRowElement>
        </s:if>
        <s:else>
            <s:hidden key="rootKey" id="rootKey"/>
            <s:hidden key="family.id"/>
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
                   onchange="handleEndDateDiv();"
                   id="familyEntityForm.family.statusCode"/>
        </s:else>
            <s:textfield key="family.name" required="true" cssClass="required" size="70"/>
            <po:field labelKey="family.startDate" fieldRequired="true">
                <sj:datepicker required="true" readonly="false" changeMonth="true" changeYear="true" name="family.startDate" displayFormat="mm/dd/yy"/>
            </po:field>
            
            <div id="endDateDiv" <s:if test="family.statusCode == @gov.nih.nci.po.data.bo.FamilyStatus@ACTIVE">style="display:none;"</s:if>>
                    <script type="text/javascript">
                        function handleEndDateDiv() {
                            var currentStatusCode = $('familyEntityForm.family.statusCode').value;
                            $('endDateDiv')[currentStatusCode == 'ACTIVE' ? 'hide':'show']();
                            if (currentStatusCode != 'NULLIFIED' && currentStatusCode != 'INACTIVE') {
                                $('familyEntityForm.family.endDate').value = '';
                            } else {
                                var toDay = new Date();
                                var month = toDay.getMonth() + 1;
                                var formattedDate =  month + '/' + toDay.getDate() + '/' + toDay.getFullYear();
                            	$('familyEntityForm.family.endDate').value = formattedDate;
                            }
                            return true;
                        }
                    </script>
                    <po:inputRow>
                        <po:inputRowElement>
                          <po:field labelKey="family.endDate">
                             <sj:datepicker required="true" readonly="false" changeMonth="true" changeYear="true" name="family.endDate" displayFormat="mm/dd/yy" id="familyEntityForm.family.endDate"/>
                          </po:field>
                        </po:inputRowElement>
                    </po:inputRow>
                </div>
            <div class="clear"></div>
        </div>
    </s:form>
</div>
<div class="btnwrapper" style="margin-bottom:20px;">
    <po:buttonRow>
        <po:button id="save_button" href="javascript://noop/" onclick="confirmThenSubmit('familyEntityForm.family.statusCode',document.forms.familyEntityForm);" style="save" text="Save"/>
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