<%@ tag display-name="addressForm" description="Renders the address form" body-content="empty" %>
<%@ attribute name="address" type="gov.nih.nci.po.data.bo.Address" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<po:field labelKey="address.streetAddressLine">${address.streetAddressLine}&nbsp;</po:field>
<po:field labelKey="address.deliveryAddressLine">${address.deliveryAddressLine}&nbsp;</po:field>
<po:inputRow>
    <po:inputRowElement>
        <po:field labelKey="address.cityOrMunicipality">${address.cityOrMunicipality}&nbsp;</po:field>
    </po:inputRowElement>
    <po:inputRowElement>
        <po:field labelKey="address.stateOrProvince">${address.stateOrProvince}&nbsp;</po:field>
    </po:inputRowElement>
</po:inputRow>
<po:inputRow>
    <po:inputRowElement>
        <po:field labelKey="address.postalCode">${address.postalCode}&nbsp;</po:field>
    </po:inputRowElement>
    <po:inputRowElement>
        <po:field labelKey="address.country">${address.country.name}&nbsp;</po:field>
    </po:inputRowElement>
</po:inputRow>