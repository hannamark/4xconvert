package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.service.BaseSearchResultDTO;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.types.BaseSearchCriteria;
import gov.nih.nci.po.webservices.types.BaseSearchResult;
import gov.nih.nci.po.webservices.types.CountryISO31661Alpha3Code;
import gov.nih.nci.po.webservices.util.PoWSUtil;
import org.apache.commons.lang.StringUtils;

/**
 * This is the base converter for Search.
 * 
 * @author Rohit Gupta
 * 
 */
public abstract class AbstractBaseSearchConverter {

    /**
     * This method is used to convert "address" in BaseSearchCriteria during
     * JaxB to BO conversion.
     * 
     * @param bsCriteria
     *            - bsCriteria
     * @return Address
     */
    public gov.nih.nci.po.data.bo.Address convertFromJaxbToBo(
            BaseSearchCriteria bsCriteria) {

        gov.nih.nci.po.data.bo.Address addressBo = null;
        if (bsCriteria != null) {
            addressBo = new gov.nih.nci.po.data.bo.Address();
            addressBo.setStreetAddressLine(StringUtils.trimToNull(bsCriteria
                    .getLine1()));
            addressBo.setDeliveryAddressLine(StringUtils.trimToNull(bsCriteria
                    .getLine2()));
            addressBo.setCityOrMunicipality(StringUtils.trimToNull(bsCriteria
                    .getCity()));
            addressBo.setStateOrProvince(StringUtils.trimToNull(bsCriteria
                    .getStateOrProvince()));
            if (bsCriteria.getCountryCode() != null) {
                gov.nih.nci.po.data.bo.Country countryBo = PoRegistry
                        .getCountryService().getCountryByAlpha3(
                                bsCriteria.getCountryCode().name());
                addressBo.setCountry(countryBo);
            }
            addressBo.setPostalCode(StringUtils.trimToNull(bsCriteria
                    .getPostalcode()));
        }

        return addressBo;
    }

    /**
     * This method is used to convert BaseSearchResult from BO to JaxB.
     * 
     * @param bsrDto
     *            - BO to be converted.
     * @param bsResult
     *            - JaxB instance - PersonSearchResult or
     *            OrganizaitonSearchResult
     * @return BaseSearchResult - Converted JaxB.
     */
    public BaseSearchResult convertBSRFromBOToJaxB(BaseSearchResultDTO bsrDto,
            BaseSearchResult bsResult) {

        bsResult.setId(bsrDto.getId());
        bsResult.setLine1(bsrDto.getAddress1());
        bsResult.setLine2(bsrDto.getAddress2());
        bsResult.setCity(bsrDto.getCity());
        bsResult.setState(bsrDto.getState());
        bsResult.setCountryName(bsrDto.getCountry());
        if (StringUtils.isNotBlank(bsrDto.getCountryCode())) {
            bsResult.setCountryCode(CountryISO31661Alpha3Code.fromValue(bsrDto
                    .getCountryCode()));
        }
        bsResult.setPostalcode(bsrDto.getZipCode());
        bsResult.setStatusCode(PoWSUtil.getEntityStatus(bsrDto.getStatusCode()));
        bsResult.setEmailAddresses(bsrDto.getEmailAddresses());
        bsResult.setPhones(bsrDto.getPhones());
        if (bsrDto.getDuplicateOf() != null) {
            bsResult.setDuplicateOf(bsrDto.getDuplicateOf().longValue());
        }

        return bsResult;
    }
}
