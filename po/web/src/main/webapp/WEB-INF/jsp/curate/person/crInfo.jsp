<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
    <s:form action="ajax/person/curate/no.action" id="curatePersonCrForm" theme="css_xhtml_readonly">
    <div class="boxouter">
    <h2>Basic Identifying Information</h2>
        <div class="box_white">
	        <po:field labelKey="cr.id"> 
	        ${cr.id} 
	        </po:field>
            
	        <po:copyButton id="copy_curateEntityForm_person_statusCode" onclick="selectValueInSelectField('${cr.statusCode}', 'curateEntityForm.person.statusCode');"
	            bodyStyle="float:left;" buttonStyle="clear:right;float:right;">
	            <po:field labelKey="person.statusCode">
		            ${cr.statusCode}
	            </po:field>
	        </po:copyButton>
            
            <po:copyButton id="copy_curateEntityForm_person_prefix" onclick="copyValueToTextField('${cr.prefix}', 'curateEntityForm_person_prefix');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield name="cr.prefix" label="%{getText('person.prefix')}" size="10"/>
            </po:copyButton>
            <po:copyButton id="copy_curateEntityForm_person_firstName" onclick="copyValueToTextField('${cr.firstName}', 'curateEntityForm_person_firstName');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield name="cr.firstName" label="%{getText('person.firstName')}" required="false" cssClass="required" size="50"/>
            </po:copyButton>
            <po:copyButton id="copy_curateEntityForm_person_middleName" onclick="copyValueToTextField('${cr.middleName}', 'curateEntityForm_person_middleName');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield name="cr.middleName" label="%{getText('person.middleName')}" size="50"/>
            </po:copyButton>
            <po:copyButton id="copy_curateEntityForm_person_lastName" onclick="copyValueToTextField('${cr.lastName}', 'curateEntityForm_person_lastName');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield name="cr.lastName" label="%{getText('person.lastName')}" required="false" cssClass="required" size="50"/>
            </po:copyButton>
            <po:copyButton id="copy_curateEntityForm_person_suffix" onclick="copyValueToTextField('${cr.suffix}', 'curateEntityForm_person_suffix');" 
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield name="cr.suffix" label="%{getText('person.suffix')}" size="10"/>
            </po:copyButton>
            
            <div class="clear"></div>
        </div>
    </div>
    <div class="boxouter">
    <h2>Address Information</h2>
        <div class="box_white">
            <script type="text/javascript">
            function copyPostalAddressField() {
            	selectValueInSelectField('${cr.postalAddress.country.id}', 'curateEntityForm.person.postalAddress.country');
            	copyValueToTextField('${cr.postalAddress.streetAddressLine}', 'curateEntityForm_person_postalAddress_streetAddressLine');
            	copyValueToTextField('${cr.postalAddress.deliveryAddressLine}', 'curateEntityForm_person_postalAddress_deliveryAddressLine');
            	copyValueToTextField('${cr.postalAddress.cityOrMunicipality}', 'curateEntityForm_person_postalAddress_cityOrMunicipality');
            	copyValueToTextField('${cr.postalAddress.postalCode}', 'curateEntityForm_person_postalAddress_postalCode');
            	copyValueToTextField('${cr.postalAddress.stateOrProvince}', 'curateEntityForm.person.postalAddress.stateOrProvince');
            	selectValueInSelectField('${cr.postalAddress.stateOrProvince}', 'curateEntityForm.person.postalAddress._selectStateOrProvince');
            }
            </script>
            <po:copyButton id="copy_curateEntityForm_person_postalAddress" onclick="copyPostalAddressField();" bodyStyle="clear:left;float:left;" buttonStyle="float:right;">
	            <po:addressForm formNameBase="curatePersonCrForm" addressKeyBase="cr.postalAddress" address="${cr.postalAddress}" required="false"/>
            </po:copyButton>
            <div class="clear"></div>
        </div>
    </div>
    <%@include file="../crInfoContactable.jsp" %>
    </s:form>   
    </div>