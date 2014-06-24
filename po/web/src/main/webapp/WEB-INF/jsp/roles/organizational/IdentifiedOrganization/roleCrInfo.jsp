<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form action="ajax/organization/curate/no.action" id="curateCrForm" theme="css_xhtml_readonly">
    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
        <c:set var="isReadonly" value="${!role.isEditableBy(pageContext.request.remoteUser)}"/>
        <%@ include file="../orgInfo.jsp" %>    
        <div class="boxouter">
            <h2><s:text name="identifiedOrganization"/> Information</h2>
            <div class="box_white">
            <c:choose>
              <c:when test="${isReadonly}">               
                <po:field labelKey="identifiedOrganization.scoper.id" fieldChanged="${cr.scoperChanged}"><c:out value="${cr.scoper.name}"></c:out>(${cr.scoper.id})</po:field>                
                <po:field labelKey="identifiedOrganization.status" fieldChanged="${cr.statusCodeChanged}">${cr.status}</po:field>
              </c:when>
              <c:otherwise>
                <po:copyButton id="copy_curateCrForm_role_scoper" 
	                onclick="copyValueToTextField('${cr.scoper.id}', 'curateRoleForm.role.scoper.id'); showPopWinCallback(new IdValue('${cr.scoper.id}','${cr.scoper.name}'));" 
	                bodyStyle="float:left;" buttonStyle="float:right;">
	                <po:field labelKey="identifiedOrganization.scoper.id">
	                    ${cr.scoper.name} (${cr.scoper.id})
	                </po:field>
	            </po:copyButton>	
	            <po:copyButton
	             id="copy_curateCrForm_role_status"
	             onclick="selectValueInSelectField('${func:escapeJavaScript(cr.status)}', 'curateRoleForm.role.status');" 
	                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
	                <s:textfield label="%{getText('identifiedOrganization.status')}" name="cr.status" required="true" cssClass="required"/>
	            </po:copyButton>
              </c:otherwise>
            </c:choose>              
            
            <script type="text/javascript">
            function copyIsoIIFields() {
                selectValueInSelectField('${func:escapeJavaScript(cr.assignedIdentifier.displayable)}', 'curateRoleForm.role.assignedIdentifier.displayable');
                copyValueToTextField('${func:escapeJavaScript(cr.assignedIdentifier.extension)}', 'curateRoleForm.role.assignedIdentifier.extension');
                copyValueToTextField('${func:escapeJavaScript(cr.assignedIdentifier.identifierName)}', 'curateRoleForm.role.assignedIdentifier.identifierName');
                selectValueInSelectField('${func:escapeJavaScript(cr.assignedIdentifier.reliability)}', 'curateRoleForm.role.assignedIdentifier.reliability');
                copyValueToTextField('${func:escapeJavaScript(cr.assignedIdentifier.root)}', 'curateRoleForm.role.assignedIdentifier.root');
                selectValueInSelectField('${func:escapeJavaScript(cr.assignedIdentifier.scope)}', 'curateRoleForm.role.assignedIdentifier.scope');
            }
            </script>            
            <div class="clear"></div>
            <fieldset>
                <legend><s:text name="identifiedOrganization.assignedIdentifier"/></legend>
                <c:choose>
	              <c:when test="${isReadonly}">               
	                <po:isoIiForm formNameBase="curateCrForm" ii="${cr.assignedIdentifier}" iiKeyBase="cr.assignedIdentifier" 
                             iiLabelKeyBase="role.assignedIdentifier" required="true" showAllReliabilities="true" readonly="${isReadonly}"/>
	              </c:when>
	              <c:otherwise>
	                <po:copyButton 
			             id="copy_curateCrForm_role_assignedIdentifier" 
			             onclick="copyIsoIIFields();" 
			             bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
			             <po:isoIiForm formNameBase="curateCrForm" ii="${cr.assignedIdentifier}" iiKeyBase="cr.assignedIdentifier" 
			                 iiLabelKeyBase="role.assignedIdentifier" required="true" showAllReliabilities="true"/>
	                </po:copyButton>
	              </c:otherwise>
	            </c:choose>            
            </fieldset>
            <div class="clear"></div>
            </div>
        </div>
    </div>    
</s:form>