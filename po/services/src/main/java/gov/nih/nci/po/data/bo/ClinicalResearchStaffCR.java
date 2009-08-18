package gov.nih.nci.po.data.bo;

import gov.nih.nci.po.util.PoRegistry;

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
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Valid;

import com.fiveamsolutions.nci.commons.search.Searchable;

/**
 *
 * @author gax
 */
@Entity
@SuppressWarnings({ "PMD.UselessOverridingMethod", "PMD.AvoidDuplicateLiterals" })
public class ClinicalResearchStaffCR extends AbstractPersonRole
        implements CorrelationChangeRequest<ClinicalResearchStaff> {

    private static final long serialVersionUID = 1L;

    private ClinicalResearchStaff target;

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
    public ClinicalResearchStaffCR() {
        super();
    }

    /**
     * useful ctor.
     * @param target affected Role.
     */
    public ClinicalResearchStaffCR(ClinicalResearchStaff target) {
        this();
        this.target = target;
    }

    /**
     * @return affected Role.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "target", nullable = false)
    @Index(name = "crs_target_idx")
    @ForeignKey(name = "CRSCR_TARGET_CRS_FK")
    public ClinicalResearchStaff getTarget() {
        return target;
    }

    /**
     * @param target affected Role.
     */
    public void setTarget(ClinicalResearchStaff target) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    @OneToMany
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
                      org.hibernate.annotations.CascadeType.DELETE_ORPHAN }
    )
    @JoinTable(
            name = "crscr_address",
            joinColumns = @JoinColumn(name = "crscr_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "CRSCR_ADDRESS_FK", inverseName = "ADDRESS_CRSCR_FK")
    @Valid
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
            name = "crscr_email",
            joinColumns = @JoinColumn(name = "crscr_id"),
            inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "CRSCR_EMAIL_FK", inverseName = "EMAIL_CRSCR_FK")
    @Valid
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
            name = "crscr_fax",
            joinColumns = @JoinColumn(name = "crscr_id"),
            inverseJoinColumns = @JoinColumn(name = "fax_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "CRSCR_FAX_FK", inverseName = "FAX_CRSCR_FK")
    @Valid
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
            name = "crscr_phone",
            joinColumns = @JoinColumn(name = "crscr_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "CRSCR_PHONE_FK", inverseName = "PHONE_CRSCR_FK")
    @Valid
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
            name = "crscr_tty",
            joinColumns = @JoinColumn(name = "crscr_id"),
            inverseJoinColumns = @JoinColumn(name = "tty_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "CRSCR_TTY_FK", inverseName = "TTY_CRSCR_FK")
    @Valid
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
            name = "crscr_url",
            joinColumns = @JoinColumn(name = "crscr_id"),
            inverseJoinColumns = @JoinColumn(name = "url_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "CRSCR_URL_FK", inverseName = "URL_CRSCR_FK")
    @Valid
    public List<URL> getUrl() {
        return super.getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @ManyToOne
    @NotNull
    @JoinColumn(name = "person_id")
    @ForeignKey(name = "personrole_per_fkey")
    @Searchable(fields = {"id" })
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "player")
    public Person getPlayer() {
        return super.getPlayer();
    }

}
