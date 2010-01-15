/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Ii;

/**
 * @author Vrushali
 *
 */
public class SiteDTO extends BaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    Ii studySiteIdentifier;

    /**
     * @return the studySiteIdentifier
     */
    public Ii getStudySiteIdentifier() {
        return studySiteIdentifier;
    }

    /**
     * @param studySiteIdentifier the studySiteIdentifier to set
     */
    public void setStudySiteIdentifier(Ii studySiteIdentifier) {
        this.studySiteIdentifier = studySiteIdentifier;
    }
    
}
