package gov.nih.nci.po.data.bo;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.validator.Valid;

import com.fiveamsolutions.nci.commons.search.Searchable;

/**
 *
 * @author gax
 */
@SuppressWarnings("PMD.UselessOverridingMethod")
@Entity
public class HealthCareFacilityCR extends AbstractEnhancedOrganizationRole
        implements CorrelationChangeRequest<HealthCareFacility> {

    private static final String VALUE = "value";
    private static final String HCFCR_ID = "hcfcr_id";
    private static final String IDX = "idx";

    private static final long serialVersionUID = 1L;

    private HealthCareFacility target;

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
     * useful ctor.
     */
    public HealthCareFacilityCR() {
        super();
    }
    
    /**
     * useful ctor.
     * @param target the affected Correlation.
     */
    public HealthCareFacilityCR(HealthCareFacility target) {
        this.target = target;
    }

    /** {@inheritDoc} */
    @ManyToOne(optional = false)
    @JoinColumn(name = "target", nullable = false)
    @Index(name = "hcf_target_idx")
    @ForeignKey(name = "HCFCR_TARGET_HCF_FK")
    public HealthCareFacility getTarget() {
        return target;
    }

    /**
     * @param target the affected Correlation.
     */
    public void setTarget(HealthCareFacility target) {
        this.target = target;
    }

    /**
     * {@inheritDoc}
          */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SuppressWarnings({ "PMD.UselessOverridingMethod" })
    public Long getId() {
        return super.getId();
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
            name = "hcfcr_address",
            joinColumns = @JoinColumn(name = HCFCR_ID),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "HCFCR_ADDRESS_FK", inverseName = "ADDRESS_HCFCR_FK")
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
            name = "hcfcr_email",
            joinColumns = @JoinColumn(name = HCFCR_ID),
            inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "HCFCR_EMAIL_FK", inverseName = "EMAIL_HCFCR_FK")
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
            name = "hcfcr_fax",
            joinColumns = @JoinColumn(name = HCFCR_ID),
            inverseJoinColumns = @JoinColumn(name = "fax_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "HCFCR_FAX_FK", inverseName = "FAX_HCFCR_FK")
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
            name = "hcfcr_phone",
            joinColumns = @JoinColumn(name = HCFCR_ID),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "HCFCR_PHONE_FK", inverseName = "PHONE_HCFCR_FK")
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
            name = "hcfcr_tty",
            joinColumns = @JoinColumn(name = HCFCR_ID),
            inverseJoinColumns = @JoinColumn(name = "tty_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "HCFCR_TTY_FK", inverseName = "TTY_HCFCR_FK")
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
            name = "hcfcr_url",
            joinColumns = @JoinColumn(name = HCFCR_ID),
            inverseJoinColumns = @JoinColumn(name = "url_id")
    )
    @IndexColumn(name = IDX)
    @ForeignKey(name = "HCFCR_URL_FK", inverseName = "URL_HCFCR_FK")
    @Valid
    @Searchable(fields = VALUE, matchMode = Searchable.MATCH_MODE_CONTAINS)
    public List<URL> getUrl() {
        return super.getUrl();
    }
}