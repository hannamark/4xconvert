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
public class OrganizationResourceProviderCR extends AbstractOrganizationResourceProvider
        implements CorrelationChangeRequest<OrganizationResourceProvider> {

    private static final long serialVersionUID = 1L;
    
    private OrganizationResourceProvider target;

    /** default ctor. */
    public OrganizationResourceProviderCR() {
        super();
    }

    /** 
     * useful ctor.
     * @param target the affected Correlation.
     */
    public OrganizationResourceProviderCR(OrganizationResourceProvider target) {
        this();
        this.target = target;
    }

    /**
     * {@inheritDoc}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SuppressWarnings({ "PMD.UselessOverridingMethod" })
    public Long getId() {
        return super.getId();
    }

    /** {@inheritDoc} */
    public OrganizationResourceProvider getTarget() {
        return target;
    }

    /**
     * @param target affected Correlation.
     */
    public void setTarget(OrganizationResourceProvider target) {
        this.target = target;
    }
}
