<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html>
    <head>
        <s:set name="isCreate" id="isCreate" value="orgRelationship.id == null"/>
        <s:set name="isEdit" value="orgRelationship.id != null"/>
        <title><fmt:message key="organizationRelationship.popup.title"/></title>
        <s:if test="%{isCreate}">
            <c:url value="/protected/popup/organization/relationship/create/create.action" var="submitUrl"/>
            <s:set name="formAction" value="'create.action'"/>
        </s:if>
        <s:elseif test="%{isEdit}">
            <c:url value="/protected/popup/organization/relationship/curate/update.action" var="submitUrl"/>
            <s:set name="formAction" value="'update.action'"/>
        </s:elseif>
        
        <script type="text/javascript">
            function handleSubmission(event) {
                var options = {
                    asynchronous: true,
                    method: 'post',
                    evalScripts: false,
                    parameters: $('organizationRelationshipForm').serialize(true),
                    onComplete: function(transport) {
                        if ($('passedValidation').value != 'false') {
                            window.top.reloadOrgRelationships(new IdValue('${newOrgRelationship.family.id}', '${newOrgRelationship.family.name}'));
                        } else {
                            bindListeners();
                        }    
                    }    
                };
                var div = $('wrapper');
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="<c:url value='/images/loading.gif'/>"&nbsp;Loading...</div>';
                var aj = new Ajax.Updater(div, '${submitUrl}', options);
            }
            function handleCancel(event) {
                window.top.hidePopWin(false);
            }
            function bindListeners() {
            	jQuery('#add_relationship_button').bind('click', handleSubmission);
                jQuery('#cancel_button').bind('click', handleCancel);
            }    
            jQuery(bindListeners);    
        </script>
    </head>
    <body> 
        <div class="po_wrapper" id="wrapper">
            <div class="po_inner">
                <h2><fmt:message key="organizationRelationship.popup.header"/></h2>
                 <div class="box_white">
                    <div class="po_form">
                        <s:actionerror/>
                        <s:form action="%{formAction}" id="organizationRelationshipForm" theme="simple">
                            <s:hidden name="rootKey" id="rootKey"/>
                            <s:hidden name="passedValidation" id="passedValidation"/>
                            <s:hidden name="newOrgRelationship.family.id"/>
                            <s:hidden name="newOrgRelationship.family.name"/>
                            <s:hidden name="newOrgRelationship.organization.id"/>
                            <s:hidden name="newOrgRelationship.organization.name"/>
                            <s:hidden name="newOrgRelationship.relatedOrganization.id"/>
                            <s:hidden name="newOrgRelationship.relatedOrganization.name"/>
                            <s:if test="%{isEdit}">
                                <h2><fmt:message key="organizationRelationship.popup.oldRelationship"/></h2>
                                <po:inputRow>
                                    <po:inputRowElement>
                                        <po:field labelKey="organizationRelationship.popup.startDate">
                                            <s:date name="orgRelationship.startDate" format="MM-dd-yyyy"/>
                                        </po:field>
                                    </po:inputRowElement>
                                    <po:inputRowElement>&nbsp;&nbsp;&nbsp;</po:inputRowElement>
                                    <po:inputRowElement>
                                        <po:field labelKey="organizationRelationship.popup.endDate" fieldRequired="true">
                                            <sj:datepicker readonly="true" required="true" name="orgRelationship.endDate" id="orgRelationship.endDate" 
                                                displayFormat="mm/dd/yy" labelposition="left" minDate="orgRelationship.startDate" maxDate="new Date()"/>
                                        </po:field>
                                        <s:fielderror>
                                            <s:param>orgRelationship.endDate</s:param>
                                        </s:fielderror>
                                    </po:inputRowElement>
                                </po:inputRow>
                                <div class="clear"></div>
                                <div class="line"></div>
                            </s:if>
                            <h2><fmt:message key="organizationRelationship.popup.newRelationship"/></h2>
                            <po:inputRow>
                                <s:set name="familyHierarchicalTypes" value="@gov.nih.nci.po.data.bo.FamilyHierarchicalType@values()" />
                                <po:inputRowElement>
                                    <b>${orgRelationship.relatedOrganization.name}</b> is a
                                    <s:select 
                                        name="newOrgRelationship.hierarchicalType"
                                        id="newOrgRelationship.hierarchicalType"
                                        list="#familyHierarchicalTypes"
                                        listKey="name()"
                                        listValue="name()" 
                                        value="newOrgRelationship.hierarchicalType" 
                                        required="true" />
                                        of <b>${orgRelationship.organization.name}</b>
                                    <s:fielderror>
                                        <s:param>newOrgRelationship.hierarchicalType</s:param>
                                    </s:fielderror>
                                </po:inputRowElement>    
                            </po:inputRow>
                            <po:inputRow>
                                <po:inputRowElement>
                                    <po:field labelKey="organizationRelationship.popup.startDate" fieldRequired="true">
                                        <sj:datepicker readonly="true" required="true" name="newOrgRelationship.startDate" id="newOrgRelationship.startDate" 
                                         displayFormat="mm/dd/yy" labelposition="left" minDate="@gov.nih.nci.po.web.util.validator.ValidDateRangeHelper@getEarliestAllowableStartDate(orgRelationship)" maxDate="new Date()"/>
                                        <s:fielderror>
                                            <s:param>newOrgRelationship.startDate</s:param>
                                        </s:fielderror>
                                    </po:field>
                                </po:inputRowElement>
                            </po:inputRow>
                        </s:form>
                        
                        <div class="btnwrapper" style="margin-bottom:20px;">
                            <po:buttonRow>
                                <po:button href="javascript://nop/" id="add_relationship_button" style="search" textKey="organizationRelationship.popup.button.addRelationship"  />
                                <po:button href="javascript://nop/" id="cancel_button" style="cancel" textKey="button.cancel"/>
                            </po:buttonRow>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html> 