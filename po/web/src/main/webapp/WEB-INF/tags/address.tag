<%@ tag display-name="addressForm" description="Renders the address form" body-content="empty" %>
<%@ attribute name="address" type="gov.nih.nci.po.data.bo.Address" required="true" %>
<%@ attribute name="idSuffix" type="java.lang.String" required="false" %>
<%@ attribute name="cr" type="gov.nih.nci.po.data.bo.OrganizationCR" required="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<po:field fieldChanged="${cr!=null && cr.countryChanged}" labelKey="address.country" idSuffix="${idSuffix}">${address.country.name}&nbsp;</po:field>
<po:field fieldChanged="${cr!=null && cr.streetAddressLineChanged}" labelKey="address.streetAddressLine" idSuffix="${idSuffix}">${address.streetAddressLine}&nbsp;</po:field>
<po:field fieldChanged="${cr!=null && cr.deliveryAddressLineChanged}" labelKey="address.deliveryAddressLine" idSuffix="${idSuffix}">${address.deliveryAddressLine}&nbsp;</po:field>
<po:field fieldChanged="${cr!=null && cr.cityOrMunicipalityChanged}" labelKey="address.cityOrMunicipality" idSuffix="${idSuffix}">${address.cityOrMunicipality}&nbsp;</po:field>
<po:inputRow>
    <po:inputRowElement>
        <po:field fieldChanged="${cr!=null && cr.stateOrProvinceChanged}" labelKey="address.stateOrProvince" idSuffix="${idSuffix}">${address.stateOrProvince}&nbsp;</po:field>
    </po:inputRowElement>
    <po:inputRowElement>
        <po:field fieldChanged="${cr!=null && cr.postalCodeChanged}" labelKey="address.postalCode" idSuffix="${idSuffix}">${address.postalCode}&nbsp;</po:field>
    </po:inputRowElement>
</po:inputRow>