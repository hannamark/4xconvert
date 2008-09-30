
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
public class OversightCommitteeCR extends AbstractOversightCommittee
        implements CorrelationChangeRequest<OversightCommittee> {

    private static final long serialVersionUID = 1L;
    
    private OversightCommittee target;
    
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
    @SuppressWarnings({ "PMD.UselessOverridingMethod" })
    public Long getId() {
        return super.getId();
    }

    /** {@inheritDoc} */
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
