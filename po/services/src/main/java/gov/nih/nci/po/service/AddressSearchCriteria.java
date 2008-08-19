package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * Class used to store search criteria for Address fields.
 * @author smatyas
 */
public class AddressSearchCriteria extends AbstractSearchCriteria implements SearchCriteria<Address> {
    private static final String POSTAL_CODE = "postalCode";
    private static final String STATE_OR_PROVINCE = "stateOrProvince";
    private static final String CITY_OR_MUNICIPALITY = "cityOrMunicipality";
    private static final String DELIVERY_ADDRESS_LINE = "deliveryAddressLine";
    private static final String STREET_ADDRESS_LINE = "streetAddressLine";
    
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
    
    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @param sc search criteria
     * @param rootType root entity type that references the Address type
     * @param rootTypeAddressPropertyName property name referencing the Address type
     * @return HQL-based select statement to find Address(s) that match the root type (Person or Organization)
     */
    public StringBuffer findMatchingAddress(Map<String, Object> namedParameters, AddressSearchCriteria sc,
            Class<? extends PersistentObject> rootType, String rootTypeAddressPropertyName) {
        StringBuffer subselect = new StringBuffer();
        if (sc.hasOneCriterionSpecified()) {

            String rootTypeAlias = "rootTypeAlias";
            String rtAddressAlias = "rtAddress";
            String joinAddress = rootTypeAlias + DOT + rootTypeAddressPropertyName + AS + rtAddressAlias;
            String countryAlias = "countryAlias";
            String joinCountry = rtAddressAlias + DOT + "country" + AS + countryAlias;

            subselect.append(SELECT).append(rootTypeAlias).append(DOT).append(ID).append(FROM).append(
                    tableAlias(rootType, rootTypeAlias)).append(JOIN).append(joinAddress);
            /*
             * since Address.country is required inner join otherwise, if that changes this logic will need to change
             * and need to be more complex
             */
            subselect.append(JOIN).append(joinCountry);
            List<String> subselectWhereClause = new ArrayList<String>();
            subselectWhereClause.add(addILike(rtAddressAlias + DOT + STREET_ADDRESS_LINE, STREET_ADDRESS_LINE, sc
                    .getAddress().getStreetAddressLine(), namedParameters));
            subselectWhereClause.add(addILike(rtAddressAlias + DOT + DELIVERY_ADDRESS_LINE, DELIVERY_ADDRESS_LINE, sc
                    .getAddress().getDeliveryAddressLine(), namedParameters));
            subselectWhereClause.add(addILike(rtAddressAlias + DOT + CITY_OR_MUNICIPALITY, CITY_OR_MUNICIPALITY, sc
                    .getAddress().getCityOrMunicipality(), namedParameters));
            subselectWhereClause.add(addILike(rtAddressAlias + DOT + STATE_OR_PROVINCE, STATE_OR_PROVINCE, sc
                    .getAddress().getStateOrProvince(), namedParameters));
            subselectWhereClause.add(addILike(rtAddressAlias + DOT + POSTAL_CODE, POSTAL_CODE, sc.getAddress()
                    .getPostalCode(), namedParameters));
            if (sc.getAddress().getCountry() != null) {
                if (sc.getAddress().getCountry().getId() != null) {
                    subselectWhereClause.add(addEqual(countryAlias + DOT + ID, "countryId", sc.getAddress()
                            .getCountry().getId(), namedParameters));
                } else {
                    subselectWhereClause.add(addILike(countryAlias + DOT + "alpha3", "countryAlpha3", sc.getAddress()
                            .getCountry().getAlpha3(), namedParameters));
                }
            }
            subselect.append(buildWhereClause(subselectWhereClause, WhereClauseOperator.CONJUNCTION));
        }
        return subselect;
    }
}