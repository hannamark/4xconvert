<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
    <head>
        <s:set name="isCreate" id="isCreate" value="familyOrgRelationship.id == null"/>
        <s:set name="isEdit" value="familyOrgRelationship.id != null"/>
        <c:url value="/protected/selector/organization/start.action" var="searchUrl"/>
        <title><fmt:message key="familyOrgRelationship.details.title"/></title>
        <script type="text/javascript">
        Event.observe(window, 'load', function() {
        	if ($('familyOrgRelationship.id').value == '' && $('selectedOrgId').value == '') {
        		showPopWin('${searchUrl}', 1000, 600, orgSelectionCallback, false);
        		$('famOrgRelationshipOrgInfo').hide();
        	}
        });
        
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
        	 var url = '${removeUrl}' + '?orgRelationship.id=' + id;
        	 var aj = new Ajax.Request(url, {
        		 asynchronous: true,
        		 method: 'post',
        		 evalScripts: false,
        		 onComplete: function(transport) {
        			  reloadOrgRelationships(new IdValue('${familyOrgRelationship.family.id}', ''));	 
        		 }
        	 });
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
            <s:if test="%{isEdit || familyOrgRelationship.organization.id != null}">
                <%@include file="familyOrgRelationshipOrgInfo.jsp"%>
            </s:if>
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
                            <po:field labelKey="familyOrgRelationship.startDate" fieldRequired="true">
                                <sj:datepicker required="true" name="familyOrgRelationship.startDate" 
                                    displayFormat="mm/dd/yy"  labelposition="left" />
                                <s:fielderror>
                                    <s:param>familyOrgRelationship.startDate</s:param>
                                </s:fielderror>
                            </po:field>
                        </po:inputRowElement>
                        <po:inputRowElement>
                            <po:field labelKey="familyOrgRelationship.endDate">
                                <sj:datepicker name="familyOrgRelationship.endDate" 
                                     displayFormat="mm/dd/yy"  labelposition="left"/>
                                <s:fielderror>
                                    <s:param>familyOrgRelationship.endDate</s:param>
                                </s:fielderror>
                            </po:field>
                        </po:inputRowElement>
                    </po:inputRow>
                </s:form>
            </div>
        </div>
        <div class="clear"></div> 
        <div class="btnwrapper" style="margin-bottom:20px;">
            <po:buttonRow>
                <po:button id="save_button" href="javascript://noop/" onclick="$('familyOrgRelationshipForm').submit();" style="save" text="Save"/>
                <c:url var="cancelUrl" value="/protected/family/edit/start.action">
                    <c:param name="family.id" value="${familyOrgRelationship.family.id}"/>
                </c:url>
                <po:button id="cancel_button" href="${cancelUrl}" style="cancel" text="Cancel"/>
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