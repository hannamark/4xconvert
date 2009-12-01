<%@ tag display-name="addressForm" description="Renders the address form" body-content="empty" %>
<%@ attribute name="formNameBase" type="java.lang.String" required="true" %>
<%@ attribute name="addressKeyBase" type="java.lang.String" required="true" %>
<%@ attribute name="address" type="gov.nih.nci.po.data.bo.Address" required="true" %>
<%@ attribute name="required" type="java.lang.Boolean" required="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/WEB-INF/po-functions.tld" prefix="pofn" %>
<s:set name="countryService" value="@gov.nih.nci.po.util.PoRegistry@getCountryService()" />
<script type="text/javascript"><!--
/*
 Toggles the display of a stateOrProvince textfield or a select-box.
*/
function loadStateProvince(formNameBase, addressKeyBase, code, v) {
    var div_stateOrProvince = '' + formNameBase + '.' + addressKeyBase + '.div_stateOrProvince';
    var url = contextPath + "/protected/ajax/curate/states.action";
    var div = $(div_stateOrProvince);

    var stateFieldElem = '' + addressKeyBase +'.stateOrProvince';
    if (v == null) {
        var stateField = $(stateFieldElem);
        v = stateField.value;
        if (v == null) {
            v = stateField.options[stateField.selectedIndex].value;
        }
    }
    div.innerHTML = "Loading States..."
    var aj = new Ajax.Updater(div, url, {
        asynchronous: true,
        method: 'get',
        evalScripts: false,
        parameters: {
            countryId: code,
            value: v,
            field: stateFieldElem
            }
    });
   countryElem = ''+ formNameBase + addressKeyBase + '.country';
    switchContactNumberFormats(countryElem);
}

--></script>

<s:if test="%{#attr.required == null || #attr.required == true}">
<s:set name="cssClass" value="required"/>
<s:set name="required" value="true"/>
</s:if>
<s:else>
<s:set name="cssClass" value=""/>
</s:else>

<s:select
    name="%{#attr.addressKeyBase + '.country'}"
    label="%{getText(#attr.addressKeyBase + '.country')}"
    list="#countryService.countries" listKey="id" listValue="name"
    cssClass="%{cssClass}" required="%{#attr.required}"
    headerKey="" headerValue="--Select a Country--"
    value="#attr.address.country.id"
    onchange="loadStateProvince('%{#attr.formNameBase}' , '%{#attr.addressKeyBase}' , this.value);"
    id="%{#attr.formNameBase + '.' + #attr.addressKeyBase + '.country'}">
</s:select>
<s:textfield name="%{#attr.addressKeyBase + '.streetAddressLine'}" 
    required="%{#attr.required}" cssClass="%{cssClass}" size="70"
    label="%{getText(#attr.addressKeyBase + '.streetAddressLine')}" />
<s:textfield name="%{#attr.addressKeyBase + '.deliveryAddressLine'}" size="70"
    label="%{getText(#attr.addressKeyBase + '.deliveryAddressLine')}" />
<s:textfield name="%{#attr.addressKeyBase + '.cityOrMunicipality'}"
    required="%{#attr.required}" cssClass="%{cssClass}" size="25"
    label="%{getText(#attr.addressKeyBase + '.cityOrMunicipality')}" />
<po:inputRow>
    <po:inputRowElement>
	    <div id="${formNameBase}.${addressKeyBase}.div_stateOrProvince">
            Loading States...
        </div>
        <script type="text/javascript"><!--
            loadStateProvince('${formNameBase}', '${addressKeyBase}', $('${formNameBase}.${addressKeyBase}.country').value, '${pofn:escapeJavaScript(address.stateOrProvince)}');
        --></script>
    </po:inputRowElement>
    <po:inputRowElement>
        <s:textfield name="%{#attr.addressKeyBase + '.postalCode'}"
            required="%{#attr.required}" cssClass="%{cssClass}" size="18"
            label="%{getText(#attr.addressKeyBase + '.postalCode')}" />
    </po:inputRowElement>
</po:inputRow>

