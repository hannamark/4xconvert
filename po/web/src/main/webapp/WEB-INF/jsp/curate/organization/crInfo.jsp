<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
    <s:form action="ajax/organization/curate/no.action" id="curateOrgCrForm" theme="css_xhtml_readonly">
    <div class="boxouter">
    <h2>Basic Identifying Information</h2>
        <div class="box_white">
	        <po:field labelKey="cr.id"> 
	        ${cr.id} 
	        </po:field>
            
	        <po:copyButton id="copy_curateEntityForm_organization_statusCode" onclick="selectValueInSelectField('${pofn:escapeJavaScript(cr.statusCode)}', 'curateEntityForm.organization.statusCode');"
	            bodyStyle="float:left;" buttonStyle="float:right;">
	            <po:field labelKey="organization.statusCode">
		            ${cr.statusCode}
	            </po:field>
	        </po:copyButton>
            
            <po:copyButton id="copy_curateEntityForm_organization_name" onclick="copyValueToTextField('${pofn:escapeJavaScript(cr.name)}', 'curateEntityForm_organization_name');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield name="cr.name" label="%{getText('organization.name')}" required="false" cssClass="required" size="70"/>
            </po:copyButton>
            
            <div class="clear"></div>
        </div>
    </div>
    <div class="boxouter">
    <h2>Address Information</h2>
        <div class="box_white">
            <script type="text/javascript">
            function copyPostalAddressField() {
            	//set State before Country to ensure State is properly populated with the CR's State value after the Country onchange event is fired and complete
            	copyValueToTextField('${pofn:escapeJavaScript(cr.postalAddress.stateOrProvince)}', 'organization.postalAddress.stateOrProvince');
            	selectValueInSelectField('${pofn:escapeJavaScript(cr.postalAddress.stateOrProvince)}', 'organization.postalAddress.stateOrProvince');
            	selectValueInSelectField('${pofn:escapeJavaScript(cr.postalAddress.country.id)}', 'curateEntityForm.organization.postalAddress.country');
            	copyValueToTextField('${pofn:escapeJavaScript(cr.postalAddress.streetAddressLine)}', 'curateEntityForm_organization_postalAddress_streetAddressLine');
            	copyValueToTextField('${pofn:escapeJavaScript(cr.postalAddress.deliveryAddressLine)}', 'curateEntityForm_organization_postalAddress_deliveryAddressLine');
            	copyValueToTextField('${pofn:escapeJavaScript(cr.postalAddress.cityOrMunicipality)}', 'curateEntityForm_organization_postalAddress_cityOrMunicipality');
            	copyValueToTextField('${pofn:escapeJavaScript(cr.postalAddress.postalCode)}', 'curateEntityForm_organization_postalAddress_postalCode');
            }
            </script>
            <po:copyButton id="copy_curateEntityForm_organization_postalAddress" onclick="copyPostalAddressField();" bodyStyle="clear:left;float:left;" buttonStyle="float:right;">
                <po:address address="${cr.postalAddress}"/>
            </po:copyButton>
            <div class="clear"></div>
        </div>
    </div>
    <%@ include file="../crInfoContactable.jsp" %>
    </s:form>   
    </div>