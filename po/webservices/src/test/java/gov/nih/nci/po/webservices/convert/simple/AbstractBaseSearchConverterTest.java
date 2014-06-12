package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.service.BaseSearchResultDTO;
import gov.nih.nci.po.service.CountryServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.ServiceLocator;
import gov.nih.nci.po.webservices.types.BaseSearchCriteria;
import gov.nih.nci.po.webservices.types.BaseSearchResult;
import gov.nih.nci.po.webservices.types.CountryISO31661Alpha3Code;
import junit.framework.Assert;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This is a base class with common code to be used across different "search"
 * related testcases.
 * 
 * @author Rohit Gupta
 * 
 */
public abstract class AbstractBaseSearchConverterTest {

    protected ServiceLocator serviceLocator = null;

    /**
     * This method is used to setup different Mock objects.
     */
    @SuppressWarnings("unchecked")
    protected void setUpMockObjects() {
        try {
            serviceLocator = mock(ServiceLocator.class);
            PoRegistry.getInstance().setServiceLocator(serviceLocator);
            // Mock setup for getting Country
            CountryServiceLocal countryServiceLocal = mock(CountryServiceLocal.class);
            when(serviceLocator.getCountryService()).thenReturn(
                    countryServiceLocal);
            when(countryServiceLocal.getCountryByAlpha3("USA")).thenReturn(
                    new Country("United States", null, null, "USA"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to populate BaseSearchCriteria.
     * 
     * @param bsCriteria
     *            - PersonSearchCriteria or OrganizationSearchCriteria
     */
    protected void populateBaseSearchCriteria(BaseSearchCriteria bsCriteria) {
        bsCriteria.setLimit(200);
        bsCriteria.setOffset(10);

        bsCriteria.setLine1("13921 Park Centre Rd");
        bsCriteria.setLine2("Suite 420");
        bsCriteria.setCity("Reston");
        bsCriteria.setStateOrProvince("Virginia");
        bsCriteria.setCountryCode(CountryISO31661Alpha3Code.USA);
        bsCriteria.setPostalcode("12345");
    }

    /**
     * This method is having assertion checks for BaseSearchCriteria.
     */
    protected void assertAddressForConvertFromJaxbToBo(
            BaseSearchCriteria psCriteria,
            gov.nih.nci.po.data.bo.Address addressBo) {

        Assert.assertEquals(psCriteria.getLine1(),
                addressBo.getStreetAddressLine());
        Assert.assertEquals(psCriteria.getLine2(),
                addressBo.getDeliveryAddressLine());
        Assert.assertEquals(psCriteria.getCity(),
                addressBo.getCityOrMunicipality());
        Assert.assertEquals(psCriteria.getStateOrProvince(),
                addressBo.getStateOrProvince());
        Assert.assertEquals(psCriteria.getCountryCode().name(), addressBo
                .getCountry().getAlpha3());
        Assert.assertEquals(psCriteria.getPostalcode(),
                addressBo.getPostalCode());
    }

    /**
     * This method is used to populate BaseSearchResult.
     * 
     * @param bsrDto
     *            - PersonSearchDTO or OrganizationSearchDTO
     */
    protected void populateBaseSearchResultDTO(BaseSearchResultDTO bsrDto) {
        bsrDto.setId(1234l);
        bsrDto.setStatusCode("ACTIVE");
        bsrDto.setAddress1("13621 Leagcy Circle");
        bsrDto.setAddress2("Apt G");
        bsrDto.setCity("Herndon");
        bsrDto.setState("VA");
        bsrDto.setZipCode("20166");
        bsrDto.setCountry("United States");
        bsrDto.setCountryCode("USA");
        bsrDto.setDuplicateOf(1l);
        bsrDto.setEmailAddresses("rohit.gupta@sb.com;rgupta@gmail.com");
        bsrDto.setPhones("703-456-7809;703-123-5667");

    }

    /**
     * This method is having assertion checks for BaseSearchResult.
     */
    protected void assertBSRForConvertFromBOToJaxB(BaseSearchResultDTO bsrDto,
            BaseSearchResult bsr) {
        Assert.assertEquals(bsrDto.getId().longValue(), bsr.getId());
        Assert.assertEquals(bsrDto.getStatusCode(), bsr.getStatusCode().value());
        Assert.assertEquals(bsrDto.getAddress1(), bsr.getLine1());
        Assert.assertEquals(bsrDto.getAddress2(), bsr.getLine2());
        Assert.assertEquals(bsrDto.getCity(), bsr.getCity());
        Assert.assertEquals(bsrDto.getState(), bsr.getState());
        Assert.assertEquals(bsrDto.getCountry(), bsr.getCountryName());
        Assert.assertEquals(bsrDto.getCountryCode(), bsr.getCountryCode()
                .name());
        Assert.assertEquals(bsrDto.getZipCode(), bsr.getPostalcode());
        Assert.assertEquals(bsrDto.getDuplicateOf(), bsr.getDuplicateOf());
        Assert.assertEquals(bsrDto.getEmailAddresses(), bsr.getEmailAddresses());
        Assert.assertEquals(bsrDto.getPhones(), bsr.getPhones());
    }

}
