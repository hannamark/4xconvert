<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form action="ajax/organization/curate/no.action" id="curateCrForm" theme="css_xhtml_readonly">
    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
        <%@ include file="../orgInfo.jsp" %>    
        <div class="boxouter">
            <h2><s:text name="healthCareFacility"/> Information</h2>
            <div class="box_white">
            <po:copyButton
             id="copy_curateCrForm_role_name"
             onclick="copyValueToTextField('${pofn:escapeJavaScript(cr.name)}', 'curateRoleForm.role.name');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield label="%{getText('healthCareFacility.name')}" name="cr.name" maxlength="160" size="50"/>
            </po:copyButton>             
            <po:copyButton
             id="copy_curateCrForm_role_status"
             onclick="selectValueInSelectField('${pofn:escapeJavaScript(cr.status)}', 'curateRoleForm.role.status');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield label="%{getText('healthCareFacility.status')}" name="cr.status" required="true" cssClass="required"/>
            </po:copyButton>
            
            <div class="clear"></div>
            </div>
        </div>
        <%@ include file="../../person/crInfoMailable.jsp" %> 
        <%@ include file="../../../curate/crInfoContactable.jsp" %>        
    </div>    
</s:form>