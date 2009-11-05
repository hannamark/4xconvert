package gov.nih.nci.accrual.web.dto.util;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;

import java.io.Serializable;

/**
 * The diagnosis search item dto.
 * 
 * @author lhebel
 */
public class DiagnosisItemWebDto implements Serializable {

    private static final long serialVersionUID = -1168678080154048341L;

    private St name;
    private Ii identifier;
    
    /**
     * Default constructor.
     */
    public DiagnosisItemWebDto() {
        // Default constructor.
    }

    /**
     * @param name the name to set
     */
    public void setName(St name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public St getName() {
        return name;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(Ii identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the identifier
     */
    public Ii getIdentifier() {
        return identifier;
    }
}
