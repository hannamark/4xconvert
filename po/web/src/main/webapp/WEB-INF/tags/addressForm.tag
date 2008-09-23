<%@ tag display-name="addressForm" description="Renders the address form" body-content="empty" %>
<%@ attribute name="addressKeyBase" type="java.lang.String" required="true" %>
<%@ attribute name="address" type="gov.nih.nci.po.data.bo.Address" required="true" %>
<%@ attribute name="required" type="java.lang.Boolean" required="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:if test="%{#attr.required == null || #attr.required == true}">
<s:textfield name="%{#attr.addressKeyBase + '.streetAddressLine'}" required="true" cssClass="required" size="70"
             label="%{getText(#attr.addressKeyBase + '.streetAddressLine')}" />
<s:textfield name="%{#attr.addressKeyBase + '.deliveryAddressLine'}" size="70"
             label="%{getText(#attr.addressKeyBase + '.deliveryAddressLine')}" />
<po:inputRow>
    <po:inputRowElement>
        <s:textfield name="%{#attr.addressKeyBase + '.cityOrMunicipality'}" required="true" cssClass="required" size="31"
                     label="%{getText(#attr.addressKeyBase + '.cityOrMunicipality')}" />
    </po:inputRowElement>
    <po:inputRowElement>
        <s:textfield name="%{#attr.addressKeyBase + '.stateOrProvince'}" size="32"
                     label="%{getText(#attr.addressKeyBase + '.stateOrProvince')}" />
    </po:inputRowElement>
</po:inputRow>
<po:inputRow>
    <po:inputRowElement>
        <s:textfield name="%{#attr.addressKeyBase + '.postalCode'}" required="true" cssClass="required" size="18"
                     label="%{getText(#attr.addressKeyBase + '.postalCode')}" />
    </po:inputRowElement>
    <po:inputRowElement>
        <s:set name="configService" value="@gov.nih.nci.po.util.PoRegistry@getCountryService()" />
        <s:select name="%{#attr.addressKeyBase + '.country'}" label="%{getText(#attr.addressKeyBase + '.country')}"
            list="#configService.countries" listKey="id" listValue="name" cssClass="required" required="true"
            headerKey="" headerValue="--Select a Country--"
            value="#attr.address.country.id">
        </s:select>
    </po:inputRowElement>
</po:inputRow>
</s:if>
<s:else>
<s:textfield name="%{#attr.addressKeyBase + '.streetAddressLine'}" size="70"
             label="%{getText(#attr.addressKeyBase + '.streetAddressLine')}" />
<s:textfield name="%{#attr.addressKeyBase + '.deliveryAddressLine'}" size="70"
             label="%{getText(#attr.addressKeyBase + '.deliveryAddressLine')}" />
<po:inputRow>
    <po:inputRowElement>
        <s:textfield name="%{#attr.addressKeyBase + '.cityOrMunicipality'}" size="31"
                     label="%{getText(#attr.addressKeyBase + '.cityOrMunicipality')}" />
    </po:inputRowElement>
    <po:inputRowElement>
        <s:textfield name="%{#attr.addressKeyBase + '.stateOrProvince'}" size="32"
                     label="%{getText(#attr.addressKeyBase + '.stateOrProvince')}" />
    </po:inputRowElement>
</po:inputRow>
<po:inputRow>
    <po:inputRowElement>
        <s:textfield name="%{#attr.addressKeyBase + '.postalCode'}" size="18"
                     label="%{getText(#attr.addressKeyBase + '.postalCode')}" />
    </po:inputRowElement>
    <po:inputRowElement>
        <s:set name="configService" value="@gov.nih.nci.po.web.util.PoRegistry@getCountryService()" />
        <s:select name="%{#attr.addressKeyBase + '.country'}" label="%{getText(#attr.addressKeyBase + '.country')}"
            list="#configService.countries" listKey="id" listValue="name" 
            headerKey="" headerValue="--Select a Country--"
            value="#attr.address.country.id">
        </s:select>
    </po:inputRowElement>
</po:inputRow>
</s:else>