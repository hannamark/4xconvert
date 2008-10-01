package gov.nih.nci.po.data.bo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

/**
 *
 * @author gax
 */
@Entity
public class PersonResourceProviderCR extends AbstractPersonResourceProvider 
        implements CorrelationChangeRequest<PersonResourceProvider> {

    private static final long serialVersionUID = -4866225509121969001L;
    
    private PersonResourceProvider target;

    /** default ctor. */
    public PersonResourceProviderCR() {
        super();
    }

    /** 
     * useful ctor.
     * @param target the affected Correlation.
     */
    public PersonResourceProviderCR(PersonResourceProvider target) {
        this();
        this.target = target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SuppressWarnings("PMD.UselessOverridingMethod")
    public Long getId() {
        return super.getId();
    }

    /** {@inheritDoc} */
    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "target", nullable = false)
    @Index(name = "prp_target_idx")
    @ForeignKey(name = "PRPCR_TARGET_PRP_FK")
    public PersonResourceProvider getTarget() {
        return target;
    }

    /**
     * @param target affected Correlation.
     */
    public void setTarget(PersonResourceProvider target) {
        this.target = target;
    }
}
