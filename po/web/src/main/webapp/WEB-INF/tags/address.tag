<%@ tag display-name="addressForm" description="Renders the address form" body-content="empty" %>
<%@ attribute name="address" type="gov.nih.nci.po.data.bo.Address" required="true" %>
<%@ attribute name="idSuffix" type="java.lang.String" required="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<po:field labelKey="address.country" idSuffix="${idSuffix}">${address.country.name}&nbsp;</po:field>
<po:field labelKey="address.streetAddressLine" idSuffix="${idSuffix}">${address.streetAddressLine}&nbsp;</po:field>
<po:field labelKey="address.deliveryAddressLine" idSuffix="${idSuffix}">${address.deliveryAddressLine}&nbsp;</po:field>
<po:field labelKey="address.cityOrMunicipality" idSuffix="${idSuffix}">${address.cityOrMunicipality}&nbsp;</po:field>
<po:inputRow>
    <po:inputRowElement>
        <po:field labelKey="address.stateOrProvince" idSuffix="${idSuffix}">${address.stateOrProvince}&nbsp;</po:field>
    </po:inputRowElement>
    <po:inputRowElement>
        <po:field labelKey="address.postalCode" idSuffix="${idSuffix}">${address.postalCode}&nbsp;</po:field>
    </po:inputRowElement>
</po:inputRow>