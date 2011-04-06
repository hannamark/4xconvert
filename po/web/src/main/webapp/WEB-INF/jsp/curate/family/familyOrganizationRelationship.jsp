<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
    <head>
        <s:set name="isCreate" id="isCreate" value="familyOrgRelationship.id == null"/>
        <s:set name="isEdit" value="familyOrgRelationship.id != null"/>
        <c:url value="/protected/selector/organization/start.action" var="searchUrl"/>
        <title><fmt:message key="familyOrgRelationship.details.title"/></title>
        <script type="text/javascript">
            function orgSelectionCallback(returnValue) {
                <c:url value="/protected/ajax/family/organization/relationship/loadOrganizationInfo.action" var="loadOrgUrl">
                   <c:param name="rootKey" value="${rootKey}"/>
                </c:url>
                $('selectedOrgId').value = returnValue.id;
                var url = '${loadOrgUrl}' + '&selectedOrgId=' + returnValue.id;
                loadDiv(url, 'famOrgRelationshipOrgInfo', true, null, false);
            }

            function reloadOrgRelationships(returnValue) {
                window.top.hidePopWin(true);
                <c:url value="/protected/ajax/family/organization/relationship/loadOrgRelationships.action" var="loadOrgRelationshipUrl">
                    <c:param name="rootKey" value="${rootKey}"/>
                </c:url>
                var url = '${loadOrgRelationshipUrl}' + '&familyOrgRelationship.family.id=' + returnValue.id;
                var div = $('org_relationships');
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="<c:url value='/images/loading.gif'/>"&nbsp;Loading...</div>';
                var aj = new Ajax.Updater(div, url, {
                   asynchronous: true,
                   method: 'get',
                   evalScripts: false
                });
            }

            function removeOrgRelationship(id) {
                 <c:url value="/protected/popup/organization/relationship/create/remove.action" var="removeUrl"/>
                 <s:text name="organizationRelationship.confirmationMessage" var="confirmationMessage"/>
                 var confirmation = confirm('${confirmationMessage}');
                 var url = '${removeUrl}' + '?orgRelationship.id=' + id;
                 if (confirmation) {
                      var aj = new Ajax.Request(url, {
                            asynchronous: true,
                            method: 'post',
                            evalScripts: false,
                            onComplete: function(transport) {
                                   reloadOrgRelationships(new IdValue('${familyOrgRelationship.family.id}', ''));
                             }
                      });
                 }
            }

            function confirmThenSubmit() {
                var confirmation = ${isCreate} ||
                    $('familyOrgRelationshipForm.familyOrgRelationship.endDate').value.match(/^\s*$/) ||
                    confirm('<s:text name="familyOrgRelationship.remove.confirmationMessage" />');
                if (confirmation) {
                    $('familyOrgRelationshipForm').submit();
                }
            }
        </script>
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
                                <sj:datepicker readonly="true" required="true" name="familyOrgRelationship.startDate"
                                    displayFormat="mm/dd/yy"  labelposition="left" minDate="familyOrgRelationship.family.startDate" maxDate="@gov.nih.nci.po.web.util.validator.ValidDateRangeHelper@getLatestAllowableStartDate(familyOrgRelationship)"/>
                            </po:field>
                        </po:inputRowElement>
                        <s:if test="%{isEdit}" >
                            <po:inputRowElement>
                                <s:fielderror>
                                    <s:param>familyOrgRelationship.endDate</s:param>
                                </s:fielderror>
                                <po:field labelKey="familyOrgRelationship.endDate">
                                    <sj:datepicker name="familyOrgRelationship.endDate" readonly="true" 
                                         displayFormat="mm/dd/yy"  labelposition="left"
                                         id="familyOrgRelationshipForm.familyOrgRelationship.endDate" minDate="@gov.nih.nci.po.web.util.validator.ValidDateRangeHelper@getEarliestAllowableEndDate(familyOrgRelationship)" maxDate="new Date()"/>
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
