package gov.nih.nci.po.data.bo;



/**
 * Dummy CR class. Non-entity. Patient structural role does not use the CR process for updates.
 * It will be updated directly.
 * This class is needed to satisfy testing framework.
 * @author mshestopalov
 */

public class PatientCR extends AbstractPatient
        implements CorrelationChangeRequest<Patient> {

    private static final long serialVersionUID = 1L;

    private Patient target;

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

    /**
     * default ctor.
     */
    public PatientCR() {
        super();
    }

    /**
     * useful ctor.
     * @param target affected Role.
     */
    public PatientCR(Patient target) {
        this();
        this.target = target;
    }

    /**
     * @return affected Role.
     */
    public Patient getTarget() {
        return target;
    }

    /**
     * @param target affected Role.
     */
    public void setTarget(Patient target) {
        this.target = target;
    }

}
