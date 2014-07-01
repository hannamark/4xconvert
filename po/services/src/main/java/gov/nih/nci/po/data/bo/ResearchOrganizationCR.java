
package gov.nih.nci.po.data.bo;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.search.Searchable;
import gov.nih.nci.po.util.VaildResearchOrganizationTypeWithFundingMechanism;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.validator.Valid;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author gax
 * @author Rohit Gupta
 */
@Entity
@VaildResearchOrganizationTypeWithFundingMechanism
@SuppressWarnings("PMD")
public class ResearchOrganizationCR extends AbstractResearchOrganization
        implements CorrelationChangeRequest<ResearchOrganization> {

    private static final String VALUE = "value";
    private static final String ROCR_ID = "rocr_id";
    private static final String IDX = "idx";

    private static final long serialVersionUID = 1L;

    private ResearchOrganization target;

    private boolean processed;

    /**
     * {@inheritDoc}
     */
    public boolean isProcessed() {
        return this.processed;
    }

    /**
     * {@inheritDoc}
     */
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    /** default ctor. */
    public ResearchOrganizationCR() {
        super();
    }

    /** useful ctor.
     * @param target affected role.
     */
    public ResearchOrganizationCR(ResearchOrganization target) {
        this();
        this.target = target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return super.getId();
    }

    /** {@inheritDoc} */
    @ManyToOne(optional = false)
    @JoinColumn(name = "target", nullable = false)
    @Index(name = "ro_target_idx")
    @ForeignKey(name = "ROCR_TARGET_RO_FK")
    public ResearchOrganization getTarget() {
        return target;
    }

    /**
     *
     * @param target affected role.
     */
    public void setTarget(ResearchOrganization target) {
        this.target = target;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "rocr_address",
            joinColumns = @JoinColumn(name = ROCR_ID),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "ROCR_ADDRESS_FK", inverseName = "ADDRESS_ROCR_FK")
    @Valid
    @Searchable(fields = { "streetAddressLine", "deliveryAddressLine", "cityOrMunicipality",
            "stateOrProvince", "postalCode", "country" }, matchMode = Searchable.MATCH_MODE_CONTAINS)
    public Set<Address> getPostalAddresses() {
        return super.getPostalAddresses();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "rocr_email",
            joinColumns = @JoinColumn(name = ROCR_ID),
            inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "ROCR_EMAIL_FK", inverseName = "EMAIL_ROCR_FK")
    @Valid
    @Searchable(fields = { VALUE }, matchMode = Searchable.MATCH_MODE_CONTAINS)
    public List<Email> getEmail() {
        return super.getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "rocr_fax",
            joinColumns = @JoinColumn(name = ROCR_ID),
            inverseJoinColumns = @JoinColumn(name = "fax_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "ROCR_FAX_FK", inverseName = "FAX_ROCR_FK")
    @Valid
    @Searchable(fields = VALUE, matchMode = Searchable.MATCH_MODE_CONTAINS)
    public List<PhoneNumber> getFax() {
        return super.getFax();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "rocr_phone",
            joinColumns = @JoinColumn(name = ROCR_ID),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "ROCR_PHONE_FK", inverseName = "PHONE_ROCR_FK")
    @Valid
    @Searchable(fields = VALUE, matchMode = Searchable.MATCH_MODE_CONTAINS)
    public List<PhoneNumber> getPhone() {
        return super.getPhone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "rocr_tty",
            joinColumns = @JoinColumn(name = ROCR_ID),
            inverseJoinColumns = @JoinColumn(name = "tty_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "ROCR_TTY_FK", inverseName = "TTY_ROCR_FK")
    @Valid
    @Searchable(fields = VALUE, matchMode = Searchable.MATCH_MODE_CONTAINS)
    public List<PhoneNumber> getTty() {
        return super.getTty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "rocr_url",
            joinColumns = @JoinColumn(name = ROCR_ID),
            inverseJoinColumns = @JoinColumn(name = "url_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "ROCR_URL_FK", inverseName = "URL_ROCR_FK")
    @Valid
    @Searchable(fields = VALUE, matchMode = Searchable.MATCH_MODE_CONTAINS)
    public List<URL> getUrl() {
        return super.getUrl();
    }
    
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isStatusCodeChanged() {
        return getStatus() != target.getStatus();
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isNameChanged() {
        return !StringUtils.equals(getName(), target.getName());
    }

    /**
     *
     * @return True if the value changed, false otherwise.
     */
    @Transient
    public boolean isAliasChanged() {
        return !ListUtils.isEqualList(target.getAlias(), getAlias());
    }

    /**
     * @return boolean
     */
    @Transient
    public boolean isTypeCodeChanged() {    
        if (getTypeCode() == null && target.getTypeCode() == null) {
            return false;
        }
        if ((getTypeCode() == null && target.getTypeCode() != null) 
                || (getTypeCode() != null && target.getTypeCode() == null)) {
            return true;
        }
        
        return !StringUtils.equalsIgnoreCase(getTypeCode().getDescription(), 
                target.getTypeCode().getDescription());
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isFundingMechanismChanged() {     
        if (getFundingMechanism() == null && target.getFundingMechanism() == null) {
            return false;
        }
        if ((getFundingMechanism() == null && target.getFundingMechanism() != null) 
                || (getFundingMechanism() != null && target.getFundingMechanism() == null)) {
            return true;
        }
        
        return !StringUtils.equalsIgnoreCase(getFundingMechanism().getDescription(), 
                target.getFundingMechanism().getDescription());       
    }
    /**
     * @return boolean
     */
    @Transient
    public boolean isCountryChanged() {
        return (getPostalAddresses().size() != target.getPostalAddresses()
                .size())
                || (!getPostalAddresses().isEmpty()
                        && !target.getPostalAddresses().isEmpty() && !StringUtils
                            .equals(getPostalAddresses().iterator().next()
                                    .getCountry().getName(), target
                                    .getPostalAddresses().iterator().next()
                                    .getCountry().getName()));
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isStreetAddressLineChanged() {
        return (getPostalAddresses().size() != target.getPostalAddresses()
                .size())
                || (!StringUtils.equals(getPostalAddresses().iterator().next()
                        .getStreetAddressLine(), target.getPostalAddresses()
                        .iterator().next().getStreetAddressLine()));
    }

    /**
     * @return boolean
     */
    @Transient
    public boolean isDeliveryAddressLineChanged() {
        return (getPostalAddresses().size() != target.getPostalAddresses()
                .size())
                || (!StringUtils.equals(getPostalAddresses().iterator().next()
                        .getDeliveryAddressLine(), target.getPostalAddresses()
                        .iterator().next().getDeliveryAddressLine()));
    }

    /**
     * @return boolean
     */
    @Transient
    public boolean isCityOrMunicipalityChanged() {
        return (getPostalAddresses().size() != target.getPostalAddresses()
                .size())
                || (!StringUtils.equals(getPostalAddresses().iterator().next()
                        .getCityOrMunicipality(), target.getPostalAddresses()
                        .iterator().next().getCityOrMunicipality()));
    }

    /**
     * @return boolean
     */
    @Transient
    public boolean isStateOrProvinceChanged() {
        return (getPostalAddresses().size() != target.getPostalAddresses()
                .size())
                || (!StringUtils.equals(getPostalAddresses().iterator().next()
                        .getStateOrProvince(), target.getPostalAddresses()
                        .iterator().next().getStateOrProvince()));
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isPostalCodeChanged() {
        return (getPostalAddresses().size() != target.getPostalAddresses()
                .size())
                || (!StringUtils.equals(getPostalAddresses().iterator().next()
                        .getPostalCode(), target.getPostalAddresses()
                        .iterator().next().getPostalCode()));
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isEmailChanged() {
        return isContactChanged(target.getEmail(), getEmail());
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isUrlChanged() {
        return isContactChanged(target.getUrl(), getUrl());

    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isPhoneChanged() {
        return isContactChanged(target.getPhone(), getPhone());
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isFaxChanged() {
        return isContactChanged(target.getFax(), getFax());
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isTtyChanged() {
        return isContactChanged(target.getTty(), getTty());
    }


    private boolean isContactChanged(List<? extends Contact> oldContacts,
            List<? extends Contact> newContacts) {
        TreeSet<Contact> set = new TreeSet<Contact>(new Comparator<Contact>() {
            public int compare(Contact o1, Contact o2) {
                return StringUtils.equalsIgnoreCase(o1.getValue(),
                        o2.getValue()) ? 0 : -1;
            }
        });
        set.addAll(oldContacts);
        set.addAll(newContacts);
        if (set.size() != oldContacts.size()) {
            return true;
        }
        return false;
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isNoChange() { 
        return !isCityOrMunicipalityChanged() && !isCountryChanged()
                && !isDeliveryAddressLineChanged() && !isEmailChanged()
                && !isFaxChanged() && !isPhoneChanged()
                && !isPostalCodeChanged() && !isStateOrProvinceChanged()
                && !isStatusCodeChanged() && !isStreetAddressLineChanged()
                && !isTtyChanged() && !isUrlChanged()
                && !isNameChanged()
                && !isTypeCodeChanged()
                && !isFundingMechanismChanged()
                && !isAliasChanged();
    }
    
    /**
     * blank implementation of method from Curatable interface.
     * @return null.
     */
    @Transient
    public PersistentObject getDuplicateOf() {        
        return null;
    }

    /**
     * blank implementation of method from Curatable interface.
     * @return null.
     */
    @SuppressWarnings("rawtypes")
    @Transient
    public Set getChangeRequests() {        
        return new HashSet<HealthCareFacilityCR>();
    }
    
}


