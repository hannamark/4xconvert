
package gov.nih.nci.po.data.bo;

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
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author gax
 */
@Entity
@SuppressWarnings("PMD")
public class HealthCareProviderCR  extends AbstractHealthCareProvider
        implements CorrelationChangeRequest<HealthCareProvider>  {
    private static final long serialVersionUID = 1L;
    private static final String INDEX_NAME = "idx";
    private static final String JOIN_COLUMN = "hcpcr_id";
    private HealthCareProvider target;

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

    /**
     * default ctor.
     */
    public HealthCareProviderCR() {
        super();
    }

    /**
     * useful ctor.
     * @param target the HealthCareProvider that should changed.
     */
    public HealthCareProviderCR(HealthCareProvider target) {
        this();
        this.target = target;
    }


    /** {@inheritDoc} */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return super.getId();
    }

    /**
     * @return the person that should have this proposed state
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "target", nullable = false)
    @Index(name = "hcpcr_target_idx")
    @ForeignKey(name = "HCPCR_TARGET_HCP_FK")
    public HealthCareProvider getTarget() {
        return target;
    }

    /**
     * @param target affected role.
     */
    public void setTarget(HealthCareProvider target) {
        this.target = target;
    }


    /**
     * @return the email
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "hcpcr_email",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @ForeignKey(name = "HCPRC_EMAIL_FK", inverseName = "EMAIL_HCPRC_FK")
    @Valid
    @Override
    public List<Email> getEmail() {
        return super.getEmail();
    }

    /**
     * @return the fax
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "hcpcr_fax",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "fax_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @ForeignKey(name = "HCPRC_FAX_FK", inverseName = "FAX_HCPRC_FK")
    @Valid
    @Override
    public List<PhoneNumber> getFax() {
        return super.getFax();
    }

    /**
     * @return the phone
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "hcpcr_phone",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @ForeignKey(name = "HCPRC_PHONE_FK", inverseName = "PHONE_HCPRC_FK")
    @Valid
    @Override
    public List<PhoneNumber> getPhone() {
        return super.getPhone();
    }

     /**
     * @return the tty
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "hcpcr_tty",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "tty_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @ForeignKey(name = "HCPRC_TTY_FK", inverseName = "TTY_HCPRC_FK")
    @Valid
    @Override
    public List<PhoneNumber> getTty() {
        return super.getTty();
    }

    /**
     * @return the url
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "hcpcr_url",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "url_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @ForeignKey(name = "HCPRC_URL_FK", inverseName = "URL_HCPRC_FK")
    @Valid
    @Override
    public List<URL> getUrl() {
        return super.getUrl();
    }

    /**
     * @return the postalAddresses
     */
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "hcpcr_address",
            joinColumns = @JoinColumn(name = JOIN_COLUMN),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    @IndexColumn(name = INDEX_NAME)
    @ForeignKey(name = "HCPRC_ADDRESS_FK", inverseName = "ADDRESS_HCPRC_FK")
    @Valid
    @Override
    public Set<Address> getPostalAddresses() {
        return super.getPostalAddresses();
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
                && !isTtyChanged() && !isUrlChanged();
    }

}
