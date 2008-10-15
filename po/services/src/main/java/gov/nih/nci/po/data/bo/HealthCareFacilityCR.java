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
public class HealthCareFacilityCR extends AbstractHealthCareFacility
        implements CorrelationChangeRequest<HealthCareFacility> {

    private static final long serialVersionUID = 1L;

    private HealthCareFacility target;

    /**
     * useful ctor.
     * @param target the affected Correlation.
     */
    public HealthCareFacilityCR(HealthCareFacility target) {
        this.target = target;
    }

    /** {@inheritDoc} */
    @ManyToOne(optional = false)
    @JoinColumn(name = "target", nullable = false)
    @Index(name = "hcf_target_idx")
    @ForeignKey(name = "HCFCR_TARGET_HCF_FK")
    public HealthCareFacility getTarget() {
        return target;
    }

    /**
     * @param target the affected Correlation.
     */
    public void setTarget(HealthCareFacility target) {
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
}
