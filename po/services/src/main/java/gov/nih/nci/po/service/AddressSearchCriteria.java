package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Address;

import org.hibernate.Criteria;
import org.hibernate.Query;

/**
 * Class used to store search criteria for Address fields.
 * @author smatyas
 */
public class AddressSearchCriteria extends AbstractSearchCriteria implements SearchCriteria<Address> {

    private String cityOrMunicipality;
    private String stateOrProvince;
    private String postalCode;
    private String countryName;
    private Long countryId;

    /**
     * @return city/municipality of the mailing address for the person
     */
    public String getCityOrMunicipality() {
        return cityOrMunicipality;
    }

    /**
     * @param cityOrMunicipality city/municipality of the mailing address for the person
     */
    public void setCityOrMunicipality(String cityOrMunicipality) {
        this.cityOrMunicipality = cityOrMunicipality;
    }

    /**
     * @return state/province of the mailing address for the person
     */
    public String getStateOrProvince() {
        return stateOrProvince;
    }

    /**
     * @param stateOrProvince state/province of the mailing address for the person
     */
    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    /**
     * @return zip/post code of the mailing address for the person
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode zip/post code of the mailing address for the person
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return country name of the mailing address for the person
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName country name of the mailing address for the person
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return country system id
     */
    public Long getCountryId() {
        return countryId;
    }

    /**
     * @param countryId country system id
     */
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    /**
     * @return true if at least value is non-null otherwise, false.
     */
    @Override
    public boolean hasOneCriterionSpecified() {
        return (isValueSpecified(cityOrMunicipality)
                || (isValueSpecified(countryName) || countryId != null)
                || isValueSpecified(postalCode)
                || isValueSpecified(stateOrProvince));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public Criteria getCriteria() {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public Query getQuery(String orderByProperty, boolean isCountOnly) {
        throw new UnsupportedOperationException();
    }
}