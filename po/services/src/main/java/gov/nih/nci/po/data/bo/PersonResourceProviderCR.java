package gov.nih.nci.po.data.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SuppressWarnings("PMD.UselessOverridingMethod")
    public Long getId() {
        return super.getId();
    }

    /** {@inheritDoc} */
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
