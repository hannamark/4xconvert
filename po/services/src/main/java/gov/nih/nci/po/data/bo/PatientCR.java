package gov.nih.nci.po.data.bo;

import java.util.List;
import java.util.Set;


/**
 * Dummy CR class. Non-entity. Patient structural role does not use the CR process for updates.
 * It will be updated directly.
 * This class is needed to satisfy testing framework.
 * @author mshestopalov
 */

@SuppressWarnings({ "PMD.UselessOverridingMethod", "PMD.AvoidDuplicateLiterals" })
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

    /**
     * {@inheritDoc}
     */
    public Long getId() {
        return super.getId();
    }

    /**
     * {@inheritDoc}
     */
    public Set<Address> getPostalAddresses() {
        return super.getPostalAddresses();
    }

    /**
     * {@inheritDoc}
     */
    public List<Email> getEmail() {
        return super.getEmail();
    }

    /**
     * {@inheritDoc}
     */
    public List<PhoneNumber> getFax() {
        return super.getFax();
    }

    /**
     * {@inheritDoc}
     */
    public List<PhoneNumber> getPhone() {
        return super.getPhone();
    }

    /**
     * {@inheritDoc}
     */
    public List<PhoneNumber> getTty() {
        return super.getTty();
    }

    /**
     * {@inheritDoc}
     */
    public List<URL> getUrl() {
        return super.getUrl();
    }

    /**
     * {@inheritDoc}
     */
    public Person getPlayer() {
        return super.getPlayer();
    }
    
}
