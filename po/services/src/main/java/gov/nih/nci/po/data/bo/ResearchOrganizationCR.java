
package gov.nih.nci.po.data.bo;

import java.util.List;
import java.util.Set;

import gov.nih.nci.po.util.VaildResearchOrganizationTypeWithFundingMechanism;

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
@VaildResearchOrganizationTypeWithFundingMechanism
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
    @SuppressWarnings({ "PMD.UselessOverridingMethod" })
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
}


