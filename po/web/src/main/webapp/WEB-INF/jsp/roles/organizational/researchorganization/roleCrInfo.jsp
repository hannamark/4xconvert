<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form action="ajax/organization/curate/no.action" id="curateCrForm" theme="css_xhtml_readonly">
    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
        <div class="boxouter">
        <h2>Organization Information</h2>
            <div class="box_white">
                <po:inputRow>
	                <po:inputRowElement><s:textfield key="organization.id" /></po:inputRowElement>
	                <po:inputRowElement><s:textfield key="organization.statusCode" /></po:inputRowElement>
                </po:inputRow>
                <s:textfield key="organization.name" required="false" cssClass="required" size="70"/>
                <div class="clear"></div>
            </div>
        </div>    
        <div class="boxouter">
            <h2>Research Organization Information</h2>
            <div class="box_white">
            <po:copyButton 
                id="copy_curateCrForm_role_typeCode" 
                onclick="selectValueInSelectField('${cr.typeCode.id}', 'curateRoleForm_role_typeCode');" 
                bodyStyle="float:left;" buttonStyle="float:right;">
                <s:textfield key="cr.typeCode.description" required="true" cssClass="required" />
            </po:copyButton>
               
            <po:copyButton
             id="copy_curateCrForm_role_fundingMechanism"
             onclick="selectValueInSelectField('${cr.fundingMechanism}', 'curateRoleForm_role_fundingMechanism');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield key="cr.fundingMechanism" required="true" cssClass="required" />
            </po:copyButton>
            <po:copyButton
             id="copy_curateCrForm_role_fundingMechanism"
             onclick="selectValueInSelectField('${cr.status}', 'curateRoleForm.role.status');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield key="cr.status" required="true" cssClass="required"/>
            </po:copyButton>
            <div class="clear"></div>
            </div>
        </div>
    </div>    
</s:form>