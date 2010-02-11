<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form action="ajax/organization/curate/no.action" id="curateCrForm" theme="css_xhtml_readonly">
    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
        <%@ include file="../orgInfo.jsp" %>    
        <div class="boxouter">
            <h2><s:text name="identifiedOrganization"/> Information</h2>
            <div class="box_white">
            <po:copyButton id="copy_curateCrForm_role_scoper" 
                onclick="copyValueToTextField('${cr.scoper.id}', 'curateRoleForm.role.scoper.id'); showPopWinCallback(new IdValue('${cr.scoper.id}','${cr.scoper.name}'));" 
                bodyStyle="float:left;" buttonStyle="float:right;">
	            <po:field labelKey="identifiedOrganization.scoper.id">
	                ${cr.scoper.name} (${cr.scoper.id})
	            </po:field>
            </po:copyButton>

            <po:copyButton
             id="copy_curateCrForm_role_status"
             onclick="selectValueInSelectField('${pofn:escapeJavaScript(cr.status)}', 'curateRoleForm.role.status');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield label="%{getText('identifiedOrganization.status')}" name="cr.status" required="true" cssClass="required"/>
            </po:copyButton>
            
            <script type="text/javascript">
            function copyIsoIIFields() {
                selectValueInSelectField('${pofn:escapeJavaScript(cr.assignedIdentifier.displayable)}', 'curateRoleForm.role.assignedIdentifier.displayable');
                copyValueToTextField('${pofn:escapeJavaScript(cr.assignedIdentifier.extension)}', 'curateRoleForm.role.assignedIdentifier.extension');
                copyValueToTextField('${pofn:escapeJavaScript(cr.assignedIdentifier.identifierName)}', 'curateRoleForm.role.assignedIdentifier.identifierName');
                selectValueInSelectField('${pofn:escapeJavaScript(cr.assignedIdentifier.reliability)}', 'curateRoleForm.role.assignedIdentifier.reliability');
                copyValueToTextField('${pofn:escapeJavaScript(cr.assignedIdentifier.root)}', 'curateRoleForm.role.assignedIdentifier.root');
                selectValueInSelectField('${pofn:escapeJavaScript(cr.assignedIdentifier.scope)}', 'curateRoleForm.role.assignedIdentifier.scope');
            }
            </script>            
            <div class="clear"></div>
            <fieldset>
                <legend><s:text name="identifiedOrganization.assignedIdentifier"/></legend>
            <po:copyButton 
                id="copy_curateCrForm_role_assignedIdentifier" 
                onclick="copyIsoIIFields();" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <po:isoIiForm formNameBase="curateCrForm" ii="${cr.assignedIdentifier}" iiKeyBase="cr.assignedIdentifier" 
                    iiLabelKeyBase="role.assignedIdentifier" required="true" showAllReliabilities="true"/>
            </po:copyButton>
            </fieldset>
            <div class="clear"></div>
            </div>
        </div>
    </div>    
</s:form>