<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form action="ajax/organization/curate/no.action" id="curateCrForm" theme="css_xhtml_readonly">
    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
    <c:set var="isReadonly" value="${!role.isEditableBy(pageContext.request.remoteUser)}"/>
        <%@ include file="../orgInfo.jsp" %>
        <div class="boxouter">
            <h2><s:text name="researchOrganization"/> Information</h2>
            <div class="box_white">
            <c:choose>
              <c:when test="${isReadonly}">               
                <po:field labelKey="researchOrganization.name" fieldChanged="${cr.nameChanged}"><c:out value="${cr.name}"></c:out></po:field> 
                <po:field labelKey="researchOrganization.typeCode" fieldChanged="${cr.typeCodeChanged}">${cr.typeCode.description}</po:field>
                <po:field labelKey="researchOrganization.fundingMechanism" fieldChanged="${cr.fundingMechanismChanged}">${cr.fundingMechanism.description}</po:field>               
                <po:field labelKey="researchOrganization.status" fieldChanged="${cr.statusCodeChanged}">${cr.status}</po:field>
              </c:when>
              <c:otherwise>
                <po:copyButton
	             id="copy_curateCrForm_role_name"
	             onclick="copyValueToTextField('${func:escapeJavaScript(cr.name)}', 'curateRoleForm.role.name');"
	             bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
	                <s:textfield label="%{getText('researchOrganization.name')}" name="cr.name" maxlength="160" size="50"/>
	            </po:copyButton>
	            <po:copyButton
	                id="copy_curateCrForm_role_typeCode"
	                onclick="selectValueInSelectField('${cr.typeCode.id}', 'curateRoleForm_role_typeCode');"
	                bodyStyle="float:left;" buttonStyle="float:right;">
	                <s:textfield label="%{getText('researchOrganization.typeCode')}" name="cr.typeCode.description"/>
	            </po:copyButton>
	            <po:copyButton
		            id="copy_curateCrForm_role_fundingMechanism"
		            onclick="copyValueToTextField('${cr.fundingMechanism.id}', 'curateRoleForm.role.fundingMechanism'); if (checkForValidFundingMech('${cr.fundingMechanism.id}', 'curateRoleForm.role._selectFundingMechanism', 'curateRoleForm_role_typeCode', '${cr.fundingMechanism.description}')) {selectValueInSelectField('${cr.fundingMechanism.id}', 'curateRoleForm.role._selectFundingMechanism');}"
	                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
	                <s:textfield label="%{getText('researchOrganization.fundingMechanism')}" name="cr.fundingMechanism.description" required="true" cssClass="required" />
	            </po:copyButton>
	            <po:copyButton
	                id="copy_curateCrForm_role_status"
	                onclick="selectValueInSelectField('${func:escapeJavaScript(cr.status)}', 'curateRoleForm.role.status');"
	                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
	                <s:textfield label="%{getText('researchOrganization.status')}" name="cr.status" required="true" cssClass="required"/>
	            </po:copyButton>
              </c:otherwise>
            </c:choose> 
            
            <div class="clear"></div>
            </div>
        </div>
        <%@ include file="../../person/crInfoMailable.jsp" %>
        <%@ include file="../../../curate/crInfoContactable.jsp" %>
    </div>
</s:form>