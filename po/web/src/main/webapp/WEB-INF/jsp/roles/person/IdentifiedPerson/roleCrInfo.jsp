<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form action="ajax/person/curate/no.action" id="curateCrForm" theme="css_xhtml_readonly">
    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
        <%@ include file="../personInfo.jsp" %>    
        <div class="boxouter">
            <h2><s:text name="identifiedPerson"/> Information</h2>
            <div class="box_white">
            <po:copyButton id="copy_curateCrForm_role_typeCode" 
                onclick="copyValueToTextField('${cr.scoper.id}', 'curateRoleForm.role.scoper.id'); showPopWinCallback('${cr.scoper.id}');" 
                bodyStyle="float:left;" buttonStyle="float:right;">
	            <po:field labelKey="identifiedPerson.scoper.id">
	                ${cr.scoper.name} (${cr.scoper.id})
	            </po:field>
            </po:copyButton>

            <po:copyButton
             id="copy_curateCrForm_role_status"
             onclick="selectValueInSelectField('${cr.status}', 'curateRoleForm.role.status');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield label="%{getText('identifiedPerson.status')}" name="cr.status" required="true" cssClass="required"/>
            </po:copyButton>
            
            <script type="text/javascript">
            function copyIsoIIFields() {
                selectValueInSelectField('${cr.assignedIdentifier.displayable}', 'curateRoleForm.role.assignedIdentifier.displayable');
                copyValueToTextField('${cr.assignedIdentifier.extension}', 'curateRoleForm.role.assignedIdentifier.extension');
                copyValueToTextField('${cr.assignedIdentifier.identifierName}', 'curateRoleForm.role.assignedIdentifier.identifierName');
                selectValueInSelectField('${cr.assignedIdentifier.reliability}', 'curateRoleForm.role.assignedIdentifier.reliability');
                copyValueToTextField('${cr.assignedIdentifier.root}', 'curateRoleForm.role.assignedIdentifier.root');
                selectValueInSelectField('${cr.assignedIdentifier.scope}', 'curateRoleForm.role.assignedIdentifier.scope');
            }
            </script>            
            <div class="clear"></div>
            <fieldset>
                <legend><s:text name="identifiedPerson.assignedIdentifier"/></legend>
            <po:copyButton 
                id="copy_curateCrForm_role_assignedIdentifier" 
                onclick="copyIsoIIFields();" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <po:isoIiForm formNameBase="curateRoleForm" ii="${cr.assignedIdentifier}" iiKeyBase="cr.assignedIdentifier" iiLabelKeyBase="role.assignedIdentifier" required="true"/>
            </po:copyButton>
            </fieldset>
            <div class="clear"></div>
            </div>
        </div>
    </div>    
</s:form>