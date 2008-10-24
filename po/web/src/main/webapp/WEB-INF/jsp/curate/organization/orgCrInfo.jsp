<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <div class="boxouter_nobottom">
    <h2>Change Request Information</h2>
    <s:form action="ajax/organization/curate/no.action" id="curateOrgCrForm" theme="css_xhtml_readonly">
    <div class="boxouter">
    <h2>Basic Identifying Information</h2>
        <div class="box_white">
	        <po:copyButton onclick="selectValueInSelectField('${cr.statusCode}', 'curateOrgForm_organization_statusCode');"
	            bodyStyle="float:left;" buttonStyle="float:right;">
		            <div class="wwgrp" id="wwgrp_curateOrgCrForm_cr_statusCode" >
			            <div class="wwlbl" id="wwlbl_curateOrgCrForm_cr_statusCode">
			            <label class="label" for="curateOrgCrForm_cr_statusCode">     
			            <s:text name="cr.statusCode"/>:
			            </label></div> <br/><div class="wwctrl" id="wwctrl_curateOrgCrForm_cr_statusCode">
			            ${cr.statusCode}
			            </div>
		            </div>        
	        </po:copyButton>
	        
            <div class="wwgrp" id="wwgrp_curateOrgCrForm_cr_id">
            <div class="wwlbl" id="wwlbl_curateOrgCrForm_cr_id">
            <label class="label" for="curateOrgCrForm_cr_id">        
            <s:text name="cr.id"/>:
            </label></div> <br/><div class="wwctrl" id="wwctrl_curateOrgCrForm_cr_id">
            ${cr.id} 
            </div></div> 
            
            <po:copyButton onclick="copyValueToTextField('${cr.name}', 'curateOrgForm_organization_name');" 
                bodyStyle="float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield key="cr.name" required="false" cssClass="required" size="70"/>
            </po:copyButton>
            <po:copyButton onclick="copyValueToTextField('${cr.abbreviatedName}', 'curateOrgForm_organization_abbreviatedName');"
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield key="cr.abbreviatedName" required="false" cssClass="required" size="70"/>
            </po:copyButton>            
            <po:copyButton onclick="copyValueToTextField('${cr.description}', 'curateOrgForm_organization_description');"
                bodyStyle="clear:left; float:left;" buttonStyle="clear:right;float:right;">
                <s:textfield key="cr.description" required="false" cssClass="required" size="70"/>
            </po:copyButton>            
            
            <div class="clear"></div>
        </div>
    </div>
    <div class="boxouter">
    <h2>Address Information</h2>
        <div class="box_white">
            <script type="text/javascript">
            function copyPostalAddressField() {
            	copyValueToTextField('${cr.postalAddress.streetAddressLine}', 'curateOrgForm_organization_postalAddress_streetAddressLine');
            	copyValueToTextField('${cr.postalAddress.deliveryAddressLine}', 'curateOrgForm_organization_postalAddress_deliveryAddressLine');
            	copyValueToTextField('${cr.postalAddress.cityOrMunicipality}', 'curateOrgForm_organization_postalAddress_cityOrMunicipality');
            	copyValueToTextField('${cr.postalAddress.stateOrProvince}', 'curateOrgForm_organization_postalAddress_stateOrProvince');
            	copyValueToTextField('${cr.postalAddress.postalCode}', 'curateOrgForm_organization_postalAddress_postalCode');
            	selectValueInSelectField('${cr.postalAddress.country.id}', 'curateOrgForm_organization_postalAddress_country');
            }
            </script>
            <po:copyButton onclick="copyPostalAddressField();" bodyStyle="clear:left;float:left;" buttonStyle="float:right;">
	            <po:addressForm addressKeyBase="cr.postalAddress" address="${cr.postalAddress}" required="false"/>
            </po:copyButton>
            <div class="clear"></div>
        </div>
    </div>
    <div class="boxouter_nobottom">
    <h2>Contact Information</h2>
        <script type="text/javascript">
        function addValue(value, textId, buttonId) {
        	copyValueToTextField(value, textId);
            fireEvent($(buttonId), 'click', 'onclick');
        }
        </script>
        <div class="box_white">
            <div class="clear"></div>
            <fieldset>
                <legend>Email Addresses</legend>
                <div id="email-list-ro">
                <ul>
                    <s:iterator value="cr.email" status="e">
                        <po:copyButton onclick="addValue('${value}', 'emailEntry_value','email-add');" 
                            bodyStyle="clear:left;float:left;" buttonStyle="float:right;margin-left:10px;">
                        <li id="email-entry-${e.index}">
                            ${value}
                        </li>
                        </po:copyButton>
                    </s:iterator>
                </ul>
                </div>
            </fieldset>
            <fieldset>
                <legend>Phone Numbers</legend>
                <div id="phone-list-ro">
                <ul>
                    <s:iterator value="cr.phone" status="e">
                       <po:copyButton onclick="addValue('${value}', 'phoneEntry_value','phone-add');" 
                            bodyStyle="clear:left;float:left;" buttonStyle="float:right;margin-left:10px;">
                        <li id="phone-entry-${e.index}">
                            ${value}
                        </li>
                       </po:copyButton>
                    </s:iterator>
                </ul>
                </div>
            </fieldset>
            <fieldset>
                <legend>Fax Numbers</legend>
                <div id="fax-list-ro">
                <ul>
                    <s:iterator value="cr.fax" status="e">
                       <po:copyButton onclick="addValue('${value}', 'faxEntry_value','fax-add');" 
                            bodyStyle="clear:left;float:left;" buttonStyle="float:right;margin-left:10px;">
                        <li id="fax-entry-${e.index}">
                            ${value}
                        </li>
                       </po:copyButton>                    
                    </s:iterator>
                </ul>           
                </div>
            </fieldset>
            <fieldset>
                <legend>TTY Numbers</legend>
                <div id="tty-list-ro">
                <ul>
                    <s:iterator value="cr.tty" status="e">
                       <po:copyButton onclick="addValue('${value}', 'ttyEntry_value','tty-add');" 
                            bodyStyle="clear:left;float:left;" buttonStyle="float:right;margin-left:10px;">
                        <li id="tty-entry-${e.index}">
                            ${value}
                        </li>
                       </po:copyButton>                    
                    </s:iterator>
                </ul>
                </div>
            </fieldset>
            <fieldset>
                <legend>Web Sites</legend>
                <div id="url-list-ro">
                <ul>
                    <s:iterator value="cr.url" status="e">
                       <po:copyButton onclick="addValue('${value}', 'urlEntry_value','url-add');" 
                            bodyStyle="clear:left;float:left;" buttonStyle="float:right;margin-left:10px;">
                        <li id="url-entry-${e.index}">          
                            <s:property value="@java.net.URLDecoder@decode(value)" />
                            | <a href="${value}" target="_blank">Visit...</a>
                        </li>
                       </po:copyButton>                    
                    </s:iterator>
                </ul>
                </div>
            </fieldset>  
        </div>
    </div>
    </s:form>   
    </div>