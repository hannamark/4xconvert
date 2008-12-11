<%@ tag display-name="addressForm" description="Renders the address form" body-content="empty" %>
<%@ attribute name="formNameBase" type="java.lang.String" required="true" %>
<%@ attribute name="addressKeyBase" type="java.lang.String" required="true" %>
<%@ attribute name="address" type="gov.nih.nci.po.data.bo.Address" required="true" %>
<%@ attribute name="required" type="java.lang.Boolean" required="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<s:set name="countryService" value="@gov.nih.nci.po.util.PoRegistry@getCountryService()" />
<s:set name="countryUSA" value="#countryService.getCountryByAlpha3('USA')" />
<script type="text/javascript"><!--
/*
 Toggles the display of a stateOrProvince textfield or a select-box. 
*/
function ${formNameBase}_displayStateProvince() {
	if ($('${formNameBase}.${addressKeyBase}.country').value == <s:property value="#countryUSA.id"/>) {
		$('${addressKeyBase}.div_stateOrProvince').hide();
		$('${addressKeyBase}.div_selectStateOrProvince').show();
	} else {
        $('${addressKeyBase}.div_stateOrProvince').show();
        $('${addressKeyBase}.div_selectStateOrProvince').hide();
	}
	$('${formNameBase}.${addressKeyBase}.stateOrProvince').value = '';
}
--></script>

<s:if test="%{#attr.required == null || #attr.required == true}">
<s:set name="cssClass" value="required"/>
<s:set name="required" value="true"/>
</s:if>
<s:else>
<s:set name="cssClass" value=""/>
</s:else>



<s:textfield name="%{#attr.addressKeyBase + '.streetAddressLine'}" required="%{#attr.required}" cssClass="%{cssClass}" size="70"
             label="%{getText(#attr.addressKeyBase + '.streetAddressLine')}" />
<s:textfield name="%{#attr.addressKeyBase + '.deliveryAddressLine'}" size="70"
             label="%{getText(#attr.addressKeyBase + '.deliveryAddressLine')}" />
<po:inputRow>
    <po:inputRowElement>
        <s:textfield name="%{#attr.addressKeyBase + '.postalCode'}" 
            required="%{#attr.required}" cssClass="%{cssClass}" size="18"
            label="%{getText(#attr.addressKeyBase + '.postalCode')}" />
    </po:inputRowElement>
    <po:inputRowElement>
        <s:select 
            name="%{#attr.addressKeyBase + '.country'}" 
            label="%{getText(#attr.addressKeyBase + '.country')}"
            list="#countryService.countries" listKey="id" listValue="name" 
            cssClass="%{cssClass}" required="%{#attr.required}"
            headerKey="" headerValue="--Select a Country--"
            value="#attr.address.country.id" 
            onchange="%{#attr.formNameBase}_displayStateProvince();" 
            id="%{#attr.formNameBase + '.' + #attr.addressKeyBase + '.country'}">
        </s:select>
    </po:inputRowElement>
</po:inputRow>             
<po:inputRow>
    <po:inputRowElement>
        <s:textfield name="%{#attr.addressKeyBase + '.cityOrMunicipality'}" 
            required="%{#attr.required}" cssClass="%{cssClass}" size="31" 
            label="%{getText(#attr.addressKeyBase + '.cityOrMunicipality')}" />
    </po:inputRowElement>
    <po:inputRowElement>
	    <%--
	        The stateOrProvince textfield will be hidden when the country is USA otherwise, it will be shown. 
	        The _selectStateOrProvince select-box will copy the selected value over to the hidden stateOrProvince textfield during onchange events
	     --%>
	     <s:set name="isCountrySelected" value="%{#attr.address.country != null && #attr.address.country.id != null}"/>
	     <s:set name="isCountryNotSelected" value="%{#attr.address.country == null || #attr.address.country.id == null}"/>
	     <s:set name="isUSA" value="%{#isCountrySelected && #countryUSA.id == #attr.address.country.id}"/>
	     <s:set name="isNotUSA" value="%{#isCountryNotSelected ||(#isCountrySelected && #countryUSA.id != #attr.address.country.id)}"/>
	     <s:set name="isNotUSAOrNoCountrySelected" value="%{#isCountryNotSelected || #isNotUSA}"/>
	    <div id="${addressKeyBase}.div_stateOrProvince" <s:if test="%{isUSA}">style="display:none;"</s:if>> 
		    <s:textfield 
		        id="%{#attr.formNameBase + '.' +  #attr.addressKeyBase + '.stateOrProvince'}"
		        name="%{#attr.addressKeyBase + '.stateOrProvince'}" size="32"
		        label="%{getText(#attr.addressKeyBase + '.stateOrProvince')}" 
		        />
	    </div>
	    <div id="${addressKeyBase}.div_selectStateOrProvince" <s:if test="%{isNotUSAOrNoCountrySelected}">style="display:none;"</s:if>>
	        <s:select 
			    id="%{#attr.formNameBase + '.' +  #attr.addressKeyBase + '._selectStateOrProvince'}"
	            name="%{#attr.addressKeyBase + '._selectStateOrProvince'}" 
	            label="%{getText(#attr.addressKeyBase + '.stateOrProvince')}"
			    list="#countryUSA.states" listKey="code" listValue="name" 
			    headerKey="" headerValue="--Select a State--"
			    value="#attr.address.stateOrProvince" 
			    onchange="$('%{#attr.formNameBase + '.' + #attr.addressKeyBase + '.stateOrProvince'}').value = $('%{#attr.formNameBase + '.' + #attr.addressKeyBase + '._selectStateOrProvince'}').value;"
			    required="%{#attr.required}" cssClass="%{cssClass}" 
			    />
	    </div>
    </po:inputRowElement>
</po:inputRow>

