<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
    <head>
        <s:set name="isCreate" id="isCreate" value="familyOrgRelationship.id == null"/>
        <s:set name="isEdit" value="familyOrgRelationship.id != null"/>
        <c:url value="/protected/selector/organization/start.action" var="searchUrl"/>
        <title><fmt:message key="familyOrgRelationship.details.title"/></title>
        <c:set var="topic" scope="request" value="relationship"/>
        <script type="text/javascript" language="javascript">
            function orgSelectionCallback(returnValue) {
                <c:url value="/protected/ajax/family/organization/relationship/loadOrganizationInfo.action" var="loadOrgUrl">
                   <c:param name="rootKey" value="${rootKey}"/>
                </c:url>
                $('selectedOrgId').value = returnValue.id;
                var url = '${loadOrgUrl}' + '&selectedOrgId=' + returnValue.id;
                loadDiv(url, 'famOrgRelationshipOrgInfo', true, null, false);
            }
    
            function confirmThenSubmit() {
                var confirmation = ${isCreate} ||
                    $('familyOrgRelationshipForm.familyOrgRelationship.endDate').value.match(/^\s*$/) ||
                    confirm('<s:text name="familyOrgRelationship.enddate.confirmationMessage" />');
                if (confirmation) {
                    $('familyOrgRelationshipForm').submit();
                }
            }
        </script>
        <%@include file="familyOrganizationRelationshipCommon.jsp"%>
    </head>
    <body>
        <po:successMessages/>
        <s:actionerror/>
        <div class="boxouter">
            <h2><fmt:message key="family"/>: ${familyOrgRelationship.family.name}</h2>
            <s:if test="%{isCreate}">
                <s:set name="formAction" value="'create.action'"/>
            </s:if>
            <s:else>
                <s:set name="formAction" value="'submit.action'"/>
            </s:else>
            <div class="box_white">
                <po:inputRow>
                    <po:inputRowElement><po:field labelKey="family.id">${familyOrgRelationship.family.id}</po:field></po:inputRowElement>
                    <po:inputRowElement>&nbsp;</po:inputRowElement>
                    <po:inputRowElement><po:field labelKey="family.name">${familyOrgRelationship.family.name}</po:field></po:inputRowElement>
                    <po:inputRowElement>&nbsp;</po:inputRowElement>
                    <po:inputRowElement><po:field labelKey="family.statusCode">${familyOrgRelationship.family.statusCode}</po:field></po:inputRowElement>
                    <po:inputRowElement>&nbsp;</po:inputRowElement>
                </po:inputRow>
            </div>
            <div class="line"></div>
        </div>
        <div class="clear"></div>
        <div id="famOrgRelationshipOrgInfo">
            <%@include file="familyOrgRelationshipOrgInfo.jsp"%>
        </div>
        <div class="box_outer">
            <div class="box_white">
                 <s:form action="%{formAction}" id="familyOrgRelationshipForm" theme="simple">
                    <s:token/>
                    <input id="enableEnterSubmit" type="submit"/>
                    <s:hidden key="rootKey" id="rootKey"/>
                    <s:hidden key="familyOrgRelationship.id" id="familyOrgRelationship.id"/>
                    <s:hidden key="familyOrgRelationship.family.id"/>
                    <s:hidden key="selectedOrgId" id="selectedOrgId"/>
                    <po:inputRow>
                        <po:inputRowElement>
                            <po:field labelKey="familyOrgRelationship.functionalType" fieldRequired="true">
                                <s:set name="functionalTypes" value="@gov.nih.nci.po.data.bo.FamilyFunctionalType@values()" />
                                <s:select
                                    name="familyOrgRelationship.functionalType"
                                    list="#functionalTypes"
                                    listKey="name()"
                                    listValue="name()"
                                    value="familyOrgRelationship.functionalType"
                                    required="true" id="familyOrgRelationship.functionalType" />
                                </po:field>
                                <s:fielderror>
                                    <s:param>familyOrgRelationship.functionalType</s:param>
                                </s:fielderror>
                        </po:inputRowElement>
                    </po:inputRow>
                    <h2><fmt:message key="familyOrgRelationship.effectiveDates"/></h2>
                    <po:inputRow>
                        <po:inputRowElement>
                            <s:fielderror>
                                <s:param>familyOrgRelationship.startDate</s:param>
                            </s:fielderror>
                            <po:field labelKey="familyOrgRelationship.startDate" fieldRequired="true">
                                <sj:datepicker readonly="true" required="false" name="familyOrgRelationship.startDate"
                                    displayFormat="mm/dd/yy"  labelposition="left"/>
                            </po:field>
                        </po:inputRowElement>
                        <s:if test="%{isEdit}" >
                            <po:inputRowElement>
                                <s:fielderror>
                                    <s:param>familyOrgRelationship.endDate</s:param>
                                </s:fielderror>
                                <po:field labelKey="familyOrgRelationship.endDate">
                                    <sj:datepicker name="familyOrgRelationship.endDate" readonly="false" 
                                         displayFormat="mm/dd/yy"  labelposition="left"
                                         id="familyOrgRelationshipForm.familyOrgRelationship.endDate"/>
                                </po:field>
                            </po:inputRowElement>
                        </s:if>
                    </po:inputRow>
                </s:form>
            </div>
        </div>
        <div class="clear"></div>
        <div class="btnwrapper" style="margin-bottom:20px;">
            <po:buttonRow>
                <po:button id="save_button" href="javascript://noop/" onclick="confirmThenSubmit();" style="save" text="Save"/>
                <c:url var="cancelUrl" value="/protected/family/curate/start.action">
                    <c:param name="family.id" value="${familyOrgRelationship.family.id}"/>
                </c:url>
                <s:set name="returnToPageTitle" value="%{'Return to ' + getText('family') + ' Information'}"/>
                <po:button id="return_to_button" href="${cancelUrl}" style="continue" text="${returnToPageTitle}"/>
            </po:buttonRow>
        </div>
        <s:if test="isEdit">
            <div class="clear"></div>
            <div class="line"></div>
            <div id="org_relationships">
                <%@include file="organizationRelationshipList.jsp"%>
            </div>
        </s:if>
    </body>
</html>
