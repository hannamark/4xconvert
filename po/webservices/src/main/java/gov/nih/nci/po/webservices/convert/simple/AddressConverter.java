package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.CountryISO31661Alpha3Code;
import org.apache.commons.lang.StringUtils;

/**
 * This is Converter class for Address.
 * 
 * @author Rohit Gupta
 */
public class AddressConverter {

    /**
     * This method is used to convert JaxB object into corresponding BO.
     * 
     * @param address
     *            -object to be mapped
     * @return mapped BO address
     */
    public gov.nih.nci.po.data.bo.Address convertFromJaxbToBO(
            gov.nih.nci.po.webservices.types.Address address) {

        gov.nih.nci.po.data.bo.Address addressBo = null;
        if (address != null) {
            addressBo = new gov.nih.nci.po.data.bo.Address();
            addressBo.setStreetAddressLine(StringUtils.trimToNull(address
                    .getLine1()));
            addressBo.setDeliveryAddressLine(StringUtils.trimToNull(address
                    .getLine2()));
            addressBo.setCityOrMunicipality(StringUtils.trimToNull(address
                    .getCity()));
            addressBo.setStateOrProvince(StringUtils.trimToNull(address
                    .getStateOrProvince()));
            gov.nih.nci.po.data.bo.Country countryBo = PoRegistry
                    .getCountryService().getCountryByAlpha3(
                            address.getCountry().name());
            addressBo.setCountry(countryBo);
            addressBo.setPostalCode(StringUtils.trimToNull(address
                    .getPostalcode()));
        }

        return addressBo;
    }

    /**
     * This method is used to convert BO object into corresponding JaxB object.
     * 
     * @param addressBo
     *            - object to be mapped
     * @return mapped JaxB object
     */
    public gov.nih.nci.po.webservices.types.Address convertFromBOToJaxB(
            gov.nih.nci.po.data.bo.Address addressBo) {

        gov.nih.nci.po.webservices.types.Address address = null;
        if (addressBo != null) {
            address = new Address();
            address.setCity(StringUtils.trimToNull(addressBo
                    .getCityOrMunicipality()));
            address.setLine1(StringUtils.trimToNull(addressBo
                    .getStreetAddressLine()));
            address.setLine2(StringUtils.trimToNull(addressBo
                    .getDeliveryAddressLine()));
            address.setStateOrProvince(StringUtils.trimToNull(addressBo
                    .getStateOrProvince()));
            address.setPostalcode(StringUtils.trimToNull(addressBo
                    .getPostalCode()));
            address.setCountry(CountryISO31661Alpha3Code.fromValue(StringUtils
                    .trimToNull(addressBo.getCountry().getAlpha3())));
        }
        return address;
    }

}
