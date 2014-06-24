<%@ tag display-name="addressForm" description="Renders the address form" body-content="empty" %>
<%@ attribute name="address" type="gov.nih.nci.po.data.bo.Address" required="true" %>
<%@ attribute name="idSuffix" type="java.lang.String" required="false" %>
<%@ attribute name="cr" type="gov.nih.nci.po.data.bo.OrganizationCR" required="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<po:field fieldChanged="${(cr == null or cr.target == null)? false : cr.countryChanged}" labelKey="address.country" idSuffix="${idSuffix}"><c:out value="${address.country.name}"></c:out>&nbsp;</po:field>
<po:field fieldChanged="${(cr == null or cr.target == null)? false : cr.streetAddressLineChanged}" labelKey="address.streetAddressLine" idSuffix="${idSuffix}"><c:out value="${address.streetAddressLine}"></c:out>&nbsp;</po:field>
<po:field fieldChanged="${(cr == null or cr.target == null)? false : cr.deliveryAddressLineChanged}" labelKey="address.deliveryAddressLine" idSuffix="${idSuffix}"><c:out value="${address.deliveryAddressLine}"></c:out>&nbsp;</po:field>
<po:field fieldChanged="${(cr == null or cr.target == null)? false : cr.cityOrMunicipalityChanged}" labelKey="address.cityOrMunicipality" idSuffix="${idSuffix}"><c:out value="${address.cityOrMunicipality}"></c:out>&nbsp;</po:field>
<po:inputRow>
    <po:inputRowElement>
        <po:field fieldChanged="${(cr == null or cr.target == null)? false : cr.stateOrProvinceChanged}" labelKey="address.stateOrProvince" idSuffix="${idSuffix}"><c:out value="${address.stateOrProvince}"></c:out>&nbsp;</po:field>
    </po:inputRowElement>
    <po:inputRowElement>
        <po:field fieldChanged="${(cr == null or cr.target == null)? false : cr.postalCodeChanged}" labelKey="address.postalCode" idSuffix="${idSuffix}"><c:out value="${address.postalCode}"></c:out>&nbsp;</po:field>
    </po:inputRowElement>
</po:inputRow>