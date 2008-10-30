<%@ tag display-name="addressForm" description="Renders the address form" body-content="empty" %>
<%@ attribute name="addressKeyBase" type="java.lang.String" required="true" %>
<%@ attribute name="address" type="gov.nih.nci.po.data.bo.Address" required="true" %>
<%@ attribute name="required" type="java.lang.Boolean" required="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

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
        <s:textfield name="%{#attr.addressKeyBase + '.cityOrMunicipality'}" required="%{#attr.required}" cssClass="%{cssClass}" size="31"
                     label="%{getText(#attr.addressKeyBase + '.cityOrMunicipality')}" />
    </po:inputRowElement>
    <po:inputRowElement>
        <s:textfield name="%{#attr.addressKeyBase + '.stateOrProvince'}" size="32"
                     label="%{getText(#attr.addressKeyBase + '.stateOrProvince')}" />
    </po:inputRowElement>
</po:inputRow>
<po:inputRow>
    <po:inputRowElement>
        <s:textfield name="%{#attr.addressKeyBase + '.postalCode'}" required="%{#attr.required}" cssClass="%{cssClass}" size="18"
                     label="%{getText(#attr.addressKeyBase + '.postalCode')}" />
    </po:inputRowElement>
    <po:inputRowElement>
        <s:set name="countryService" value="@gov.nih.nci.po.util.PoRegistry@getCountryService()" />
        <s:select name="%{#attr.addressKeyBase + '.country'}" label="%{getText(#attr.addressKeyBase + '.country')}"
            list="#countryService.countries" listKey="id" listValue="name" cssClass="%{cssClass}" required="%{#attr.required}"
            headerKey="" headerValue="--Select a Country--"
            value="#attr.address.country.id">
        </s:select>
    </po:inputRowElement>
</po:inputRow>