<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<script type="text/javascript">
            function removeChangeRequest(crId) {
            	if (confirm('<s:text name="organization.changeRequest.remove"/>')) {
            		$('curateEntityForm').action = '${func:escapeJavaScript(removeCrUrl)}';
            		$('curateEntityForm').submit();
            	}
            }
</script>

    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
    <c:set var="isReadonly" value="${!organization.isEditableBy(pageContext.request.remoteUser)}"/>
    <s:form action="ajax/organization/curate/no.action" id="curateOrgCrForm" theme="css_xhtml_readonly">
    <div class="boxouter">
    <h2>Basic Identifying Information</h2>
        <div class="box_white">
	        <po:field labelKey="cr.id"> 
	        ${cr.id} 
	        </po:field>
            
            <c:if test="${!isReadonly && cr.noChange}">
	                <po:removeButton id="remove_curateEntityForm_organization_statusCode" onclick="removeChangeRequest();"
	                    buttonStyle="float:right;"/>                               
            </c:if>
            
            <c:choose>
	          <c:when test="${isReadonly}">
	            <po:field labelKey="organization.statusCode" fieldChanged="${cr.statusCodeChanged}">
                    ${cr.statusCode}
                </po:field>
	          </c:when>
	          <c:otherwise>
	            <po:copyButton id="copy_curateEntityForm_organization_statusCode" onclick="selectValueInSelectField('${func:escapeJavaScript(cr.statusCode)}', 'curateEntityForm.organization.statusCode');"
                bodyStyle="float:left;" buttonStyle="float:right;">
	                <po:field labelKey="organization.statusCode" fieldChanged="${cr.statusCodeChanged}">
	                    ${cr.statusCode}
	                </po:field>
	            </po:copyButton>
	          </c:otherwise>
	        </c:choose>                  
	        
	        <c:if test="${!isReadonly && cr.noChange}">
	                <po:removeButton id="remove_curateEntityForm_organization_name" onclick="removeChangeRequest();"
	                    buttonStyle="float:right; clear:left"/>                               
	        </c:if>
	        
	        <c:choose>
              <c:when test="${isReadonly}">
                <po:field labelKey="organization.name" fieldChanged="${cr.nameChanged}"><c:out value="${cr.name}"></c:out></po:field>
              </c:when>
              <c:otherwise>
                <po:copyButton id="copy_curateEntityForm_organization_name" onclick="copyValueToTextField('${func:escapeJavaScript(cr.name)}', 'curateEntityForm_organization_name');" 
                bodyStyle="clear:left; float:left;" buttonStyle="float:right;">
	                <po:field labelKey="organization.name" fieldChanged="${cr.nameChanged}"><c:out value="${cr.name}"></c:out></po:field>
	            </po:copyButton>
              </c:otherwise>
            </c:choose>        
                        
            <div class="clear"></div>
        </div>
    </div>
    <div class="boxouter">
    <h2>Address Information</h2>
        <div class="box_white">
            <script type="text/javascript">
            function copyPostalAddressField() {
            	//set State before Country to ensure State is properly populated with the CR's State value after the Country onchange event is fired and complete
            	copyValueToTextField('${func:escapeJavaScript(cr.postalAddress.stateOrProvince)}', 'organization.postalAddress.stateOrProvince');
            	selectValueInSelectField('${func:escapeJavaScript(cr.postalAddress.stateOrProvince)}', 'organization.postalAddress.stateOrProvince');
            	selectValueInSelectField('${func:escapeJavaScript(cr.postalAddress.country.id)}', 'curateEntityForm.organization.postalAddress.country');
            	copyValueToTextField('${func:escapeJavaScript(cr.postalAddress.streetAddressLine)}', 'curateEntityForm_organization_postalAddress_streetAddressLine');
            	copyValueToTextField('${func:escapeJavaScript(cr.postalAddress.deliveryAddressLine)}', 'curateEntityForm_organization_postalAddress_deliveryAddressLine');
            	copyValueToTextField('${func:escapeJavaScript(cr.postalAddress.cityOrMunicipality)}', 'curateEntityForm_organization_postalAddress_cityOrMunicipality');
            	copyValueToTextField('${func:escapeJavaScript(cr.postalAddress.postalCode)}', 'curateEntityForm_organization_postalAddress_postalCode');
            }
            </script>
            
            <c:if test="${!isReadonly && cr.noChange}">
                <po:removeButton id="remove_curateEntityForm_organization_postalAddress" onclick="removeChangeRequest();"
                    buttonStyle="float:right;"/>                               
            </c:if>
            
            <c:choose>
              <c:when test="${isReadonly}">
                <po:address address="${cr.postalAddress}" cr="${cr}"/>
              </c:when>
              <c:otherwise>
                <po:copyButton id="copy_curateEntityForm_organization_postalAddress" onclick="copyPostalAddressField();" bodyStyle="clear:left;float:left;" buttonStyle="float:right;">
	                <po:address address="${cr.postalAddress}" cr="${cr}"/>
	            </po:copyButton>
              </c:otherwise>
            </c:choose>             
            
            <div class="clear"></div>
        </div>
    </div>
    <%@ include file="../crInfoContactable.jsp" %>
    </s:form>   
    </div>