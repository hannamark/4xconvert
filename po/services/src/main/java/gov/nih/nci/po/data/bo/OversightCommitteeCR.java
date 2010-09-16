
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
public class OversightCommitteeCR extends AbstractOversightCommittee
        implements CorrelationChangeRequest<OversightCommittee> {

    private static final long serialVersionUID = 1L;

    private OversightCommittee target;

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
    public OversightCommitteeCR() {
        super();
    }

    /** useful ctor.
     * @param target affected role.
     */
    public OversightCommitteeCR(OversightCommittee target) {
        this();
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

    /** {@inheritDoc} */
    @ManyToOne(optional = false)
    @JoinColumn(name = "target", nullable = false)
    @Index(name = "oc_target_idx")
    @ForeignKey(name = "OCCR_TARGET_OC_FK")
    public OversightCommittee getTarget() {
        return target;
    }

    /**
     *
     * @param target affected role.
     */
    public void setTarget(OversightCommittee target) {
        this.target = target;
    }







}
