package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Address;

import org.hibernate.Criteria;
import org.hibernate.Query;

/**
 * Class used to store search criteria for Address fields.
 * @author smatyas
 */
public class AddressSearchCriteria extends AbstractSearchCriteria implements SearchCriteria<Address> {

    private Address address;
    
    /**
     * No arg ctor.
     */
    public AddressSearchCriteria() {
        //empty
    }
    /**
     * @param postalAddress the address
     */
    public AddressSearchCriteria(Address postalAddress) {
        address = postalAddress;
    }

    /**
     * @return address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return true if at least value is non-null otherwise, false.
     */
    @Override
    public boolean hasOneCriterionSpecified() {
        return address != null && (isValueSpecified(address.getCityOrMunicipality())
                || isValueSpecified(address.getPostalCode())
                || (address.getCountry() != null && isValueSpecified(address.getCountry().getAlpha3()))
                || isValueSpecified(address.getStateOrProvince())
                || isValueSpecified(address.getStreetAddressLine())
                || isValueSpecified(address.getDeliveryAddressLine()));
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