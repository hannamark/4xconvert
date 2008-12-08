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
            
	        <po:copyButton id="copy_curateEntityForm_organization_statusCode" onclick="selectValueInSelectField('${cr.statusCode}', 'curateEntityForm.organization.statusCode');"
	            bodyStyle="float:left;" buttonStyle="float:right;">
	            <po:field labelKey="organization.statusCode">
		            ${cr.statusCode}
	            </po:field>
	        </po:copyButton>
            
            <po:copyButton id="copy_curateEntityForm_organization_name" onclick="copyValueToTextField('${cr.name}', 'curateEntityForm_organization_name');" 
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
            	selectValueInSelectField('${cr.postalAddress.country.id}', 'curateEntityForm.organization.postalAddress.country');
            	copyValueToTextField('${cr.postalAddress.streetAddressLine}', 'curateEntityForm_organization_postalAddress_streetAddressLine');
            	copyValueToTextField('${cr.postalAddress.deliveryAddressLine}', 'curateEntityForm_organization_postalAddress_deliveryAddressLine');
            	copyValueToTextField('${cr.postalAddress.cityOrMunicipality}', 'curateEntityForm_organization_postalAddress_cityOrMunicipality');
            	copyValueToTextField('${cr.postalAddress.postalCode}', 'curateEntityForm_organization_postalAddress_postalCode');
            	copyValueToTextField('${cr.postalAddress.stateOrProvince}', 'curateEntityForm.organization.postalAddress.stateOrProvince');
            	selectValueInSelectField('${cr.postalAddress.stateOrProvince}', 'curateEntityForm.organization.postalAddress._selectStateOrProvince');
            }
            </script>
            <po:copyButton id="copy_curateEntityForm_organization_postalAddress" onclick="copyPostalAddressField();" bodyStyle="clear:left;float:left;" buttonStyle="float:right;">
	            <po:addressForm formNameBase="curateOrgCrForm" addressKeyBase="cr.postalAddress" address="${cr.postalAddress}" required="false"/>
            </po:copyButton>
            <div class="clear"></div>
        </div>
    </div>
    <%@ include file="../crInfoContactable.jsp" %>
    </s:form>   
    </div>