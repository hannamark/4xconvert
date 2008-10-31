package gov.nih.nci.po.data.bo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

/**
 *
 * @author gax
 */
@Entity
public class OrganizationResourceProviderCR extends AbstractOrganizationResourceProvider
        implements CorrelationChangeRequest<OrganizationResourceProvider> {

    private static final long serialVersionUID = 1L;

    private OrganizationResourceProvider target;

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

    /** {@inheritDoc} */
    @ManyToOne(optional = false)
    @JoinColumn(name = "target", nullable = false)
    @Index(name = "orp_target_idx")
    @ForeignKey(name = "ORPCR_TARGET_ORP_FK")
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
