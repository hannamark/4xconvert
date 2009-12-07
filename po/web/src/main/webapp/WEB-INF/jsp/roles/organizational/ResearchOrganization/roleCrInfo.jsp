<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form action="ajax/organization/curate/no.action" id="curateCrForm" theme="css_xhtml_readonly">
    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
        <%@ include file="../orgInfo.jsp" %>    
        <div class="boxouter">
            <h2><s:text name="researchOrganization"/> Information</h2>
            <div class="box_white">
            <po:copyButton
             id="copy_curateCrForm_role_name"
             onclick="copyValueToTextField('${pofn:escapeJavaScript(cr.name)}', 'curateRoleForm.role.name');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield label="%{getText('researchOrganization.name')}" name="cr.name" maxlength="160" size="50"/>
            </po:copyButton> 
            <po:copyButton 
                id="copy_curateCrForm_role_typeCode" 
                onclick="selectValueInSelectField('${cr.typeCode.id}', 'curateRoleForm_role_typeCode');" 
                bodyStyle="float:left;" buttonStyle="float:right;">
                <s:textfield label="%{getText('researchOrganization.typeCode')}" name="cr.typeCode.description" required="true" cssClass="required" />
            </po:copyButton>
            <po:copyButton
             id="copy_curateCrForm_role_fundingMechanism"
             onclick="copyValueToTextField('${cr.fundingMechanism.id}', 'curateRoleForm.role.fundingMechanism'); selectValueInSelectField('${cr.fundingMechanism.id}', 'curateRoleForm.role._selectFundingMechanism');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield label="%{getText('researchOrganization.fundingMechanism')}" name="cr.fundingMechanism.description" required="true" cssClass="required" />
            </po:copyButton>
            <po:copyButton
             id="copy_curateCrForm_role_status"
             onclick="selectValueInSelectField('${pofn:escapeJavaScript(cr.status)}', 'curateRoleForm.role.status');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield label="%{getText('researchOrganization.status')}" name="cr.status" required="true" cssClass="required"/>
            </po:copyButton>
            <div class="clear"></div>
            </div>
        </div>
        <%@ include file="../../person/crInfoMailable.jsp" %> 
        <%@ include file="../../../curate/crInfoContactable.jsp" %>        
    </div>    
</s:form>