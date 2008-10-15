package gov.nih.nci.po.data.bo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.validator.NotNull;

/**
 *
 * @author gax
 */
@Entity
public class QualifiedEntityCR extends AbstractQualifiedEntity
        implements CorrelationChangeRequest<QualifiedEntity> {

    private static final long serialVersionUID = 1L;

    private QualifiedEntity target;

    /**
     * default ctor.
     */
    public QualifiedEntityCR() {
        super();
    }

    /**
     * useful ctor.
     * @param target the affected Correlation.
     */
    public QualifiedEntityCR(QualifiedEntity target) {
        this();
        this.target = target;
    }

    /** {@inheritDoc} */
    @ManyToOne(optional = false)
    @JoinColumn(name = "target", nullable = false)
    @Index(name = "qe_target_idx")
    @ForeignKey(name = "QECR_TARGET_QE_FK")
    public QualifiedEntity getTarget() {
        return target;
    }

    /**
     * @param target affected Correlation.
     */
    public void setTarget(QualifiedEntity target) {
        this.target = target;
    }

    /**
     * {@inheritDoc}
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "person_id")
    @ForeignKey(name = "qecr_per_fkey")
    @Override
    @SuppressWarnings("PMD.UselessOverridingMethod")
    public Person getPlayer() {
        return super.getPlayer();
    }

    /**
     * {@inheritDoc}
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "organization_id")
    @ForeignKey(name = "qecr_org_fkey")
    @Override
    @SuppressWarnings("PMD.UselessOverridingMethod")
    public Organization getScoper() {
        return super.getScoper();
    }
}
