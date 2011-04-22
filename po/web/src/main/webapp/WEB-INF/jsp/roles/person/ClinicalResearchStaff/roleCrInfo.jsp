<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form action="ajax/organization/curate/no.action" id="curateCrForm" theme="css_xhtml_readonly">
    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
        <%@ include file="../personInfo.jsp" %>    
        <div class="boxouter">
            <h2><s:text name="clinicalResearchStaff"/> Information</h2>
            <div class="box_white">
            <po:copyButton id="copy_curateCrForm_role_scoper" 
                onclick="copyValueToTextField('${cr.scoper.id}', 'curateRoleForm.role.scoper.id'); showPopWinCallback(new IdValue('${cr.scoper.id}','${func:escapeJavaScript(cr.scoper.name)}'));" 
                bodyStyle="float:left;" buttonStyle="float:right;">
	            <po:field labelKey="clinicalResearchStaff.scoper.id">
	                ${cr.scoper.name} (${cr.scoper.id})
	            </po:field>
            </po:copyButton>
             
            <po:copyButton
             id="copy_curateCrForm_role_status"
             onclick="selectValueInSelectField('${func:escapeJavaScript(cr.status)}', 'curateRoleForm.role.status');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield label="%{getText('clinicalResearchStaff.status')}" name="cr.status" required="true" cssClass="required"/>
            </po:copyButton>
            
            <div class="clear"></div>
            </div>
        </div>
        <%@ include file="../crInfoMailable.jsp" %> 
        <%@ include file="../../../curate/crInfoContactable.jsp" %>
    </div>    
</s:form>