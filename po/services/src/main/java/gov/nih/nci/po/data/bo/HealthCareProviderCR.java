
package gov.nih.nci.po.data.bo;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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

/**
 *
 * @author gax
 */
@Entity
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.UselessOverridingMethod" })
public class HealthCareProviderCR  extends AbstractHealthCareProvider
        implements CorrelationChangeRequest<HealthCareProvider>  {
    private static final long serialVersionUID = 1L;
    private HealthCareProvider target;

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
    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
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
            joinColumns = @JoinColumn(name = "hcpcr_id"),
            inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    @IndexColumn(name = "idx")
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
            joinColumns = @JoinColumn(name = "hcpcr_id"),
            inverseJoinColumns = @JoinColumn(name = "fax_id")
    )
    @IndexColumn(name = "idx")
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
            joinColumns = @JoinColumn(name = "hcpcr_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id")
    )
    @IndexColumn(name = "idx")
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
            joinColumns = @JoinColumn(name = "hcpcr_id"),
            inverseJoinColumns = @JoinColumn(name = "tty_id")
    )
    @IndexColumn(name = "idx")
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
            joinColumns = @JoinColumn(name = "hcpcr_id"),
            inverseJoinColumns = @JoinColumn(name = "url_id")
    )
    @IndexColumn(name = "idx")
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
            joinColumns = @JoinColumn(name = "hcpcr_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    @IndexColumn(name = "idx")
    @ForeignKey(name = "HCPRC_ADDRESS_FK", inverseName = "ADDRESS_HCPRC_FK")
    @Valid
    @Override
    public Set<Address> getPostalAddresses() {
        return super.getPostalAddresses();
    }
}
