
package gov.nih.nci.po.data.bo;

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
public class ResearchOrganizationCR extends AbstractResearchOrganization
        implements CorrelationChangeRequest<ResearchOrganization> {

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







}
