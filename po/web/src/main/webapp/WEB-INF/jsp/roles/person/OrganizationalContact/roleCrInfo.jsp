<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form action="ajax/organization/curate/no.action" id="curateCrForm" theme="css_xhtml_readonly">
    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
        <%@ include file="../personInfo.jsp" %>    
        <div class="boxouter">
            <h2><s:text name="organizationalContact"/> Information</h2>
            <div class="box_white">
            <po:copyButton id="copy_curateCrForm_role_scoper" 
                onclick="copyValueToTextField('${cr.scoper.id}', 'curateRoleForm.role.scoper.id'); showPopWinCallback('${cr.scoper.id}');" 
                bodyStyle="float:left;" buttonStyle="float:right;">
	            <po:field labelKey="organizationalContact.scoper.id">
	                ${cr.scoper.id}
	            </po:field>
            </po:copyButton>
             
            <po:copyButton
             id="copy_curateCrForm_role_status"
             onclick="selectValueInSelectField('${cr.status}', 'curateRoleForm.role.status');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield label="%{getText('organizationalContact.status')}" name="cr.status" required="true" cssClass="required"/>
            </po:copyButton>
            <po:copyButton
             id="copy_curateCrForm_role_primaryIndicator"
             onclick="selectValueInSelectField('${cr.primaryIndicator}', 'curateRoleForm.role.primaryIndicator');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield label="%{getText('organizationalContact.primaryIndicator')}" name="cr.primaryIndicator" required="true" cssClass="required"/>
            </po:copyButton>
            <po:copyButton
             id="copy_curateCrForm_role_types"
             onclick="alert('Not Yet Implemented');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:select 
                   id="curateRoleForm.role.types"
                   label="%{getText('organizationalContact.types')}"
                   name="cr.types"
                   list="cr.types"
                   listKey="id"
                   listValue="code"
                   value="%{cr.types.{id}}" 
                   multiple="true"
                   />                  
            </po:copyButton>
            <div class="clear"></div>
            </div>
        </div>
        <%@ include file="../crInfoMailable.jsp" %> 
        <%@ include file="../../../curate/crInfoContactable.jsp" %>            
    </div>    
</s:form>