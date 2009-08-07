<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form action="ajax/organization/curate/no.action" id="curateCrForm" theme="css_xhtml_readonly">
    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
        <%@ include file="../orgInfo.jsp" %>    
        <div class="boxouter">
            <h2><s:text name="oversightCommittee"/> Information</h2>
            <div class="box_white">
            <po:copyButton 
                id="copy_curateCrForm_role_typeCode" 
                onclick="selectValueInSelectField('${cr.typeCode.id}', 'curateRoleForm_role_typeCode');" 
                bodyStyle="float:left;" buttonStyle="float:right;">
                <s:textfield label="%{getText('oversightCommittee.typeCode')}" name="cr.typeCode.code" />
            </po:copyButton>
            <po:copyButton
             id="copy_curateCrForm_role_status"
             onclick="selectValueInSelectField('${pofn:escapeJavaScript(cr.status)}', 'curateRoleForm.role.status');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield label="%{getText('oversightCommittee.status')}" name="cr.status" required="true" cssClass="required"/>
            </po:copyButton>
            <div class="clear"></div>
            </div>
        </div>
    </div>    
</s:form>