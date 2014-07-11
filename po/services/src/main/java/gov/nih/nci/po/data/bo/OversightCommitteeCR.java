
package gov.nih.nci.po.data.bo;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author gax
 * @author Rohit Gupta
 */
@Entity
@SuppressWarnings("PMD.CyclomaticComplexity")
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
     * @return boolean
     */
    @Transient
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public boolean isTypeCodeChanged() {   
        if (getTypeCode() == null && target.getTypeCode() == null) {
            return false;
        }
        if ((getTypeCode() == null && target.getTypeCode() != null) 
                || (getTypeCode() != null && target.getTypeCode() == null)) {
            return true;
        }
        
        return !StringUtils.equalsIgnoreCase(getTypeCode().getCode(), 
                target.getTypeCode().getCode());
    }
    
    /**
     * @return boolean
     */
    @Transient
    public boolean isStatusCodeChanged() {
        return getStatus() != target.getStatus();
    }

    /**
     *
     * @param target affected role.
     */
    public void setTarget(OversightCommittee target) {
        this.target = target;
    }

    /**
     * blank implementation of method from Curatable interface.
     * @return null.
     */
    @Transient
    public PersistentObject getDuplicateOf() {        
        return null;
    }

    /**
     * blank implementation of method from Curatable interface.
     * @return null.
     */
    @SuppressWarnings("rawtypes")
    @Transient
    public Set getChangeRequests() {        
        return new HashSet<HealthCareFacilityCR>();
    }


    /**
     * @return boolean
     */
    @Transient
    public boolean isNoChange() {
        return !(isTypeCodeChanged() || isStatusCodeChanged());
    }


}
