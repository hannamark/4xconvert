/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.iso21090.St;

/**
 * @author Denis G. Krylov
 *
 */
public class SecondaryPurposeDTO extends BaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5257537132174436558L;

    private St name;

    /**
     * @return the name
     */
    public St getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(St name) {
        this.name = name;
    }
  

}
