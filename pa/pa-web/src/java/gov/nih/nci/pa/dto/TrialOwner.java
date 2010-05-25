/**
 * 
 */
package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.domain.RegistryUser;

/**
 * @author vrushali
 *
 */
public class TrialOwner {
    private RegistryUser regUser;
    private boolean owner;
    /**
     * @param regUser the regUser to set
     */
    public void setRegUser(RegistryUser regUser) {
        this.regUser = regUser;
    }
    /**
     * @return the regUser
     */
    public RegistryUser getRegUser() {
        return regUser;
    }
    /**
     * @param owner the owner to set
     */
    public void setOwner(boolean owner) {
        this.owner = owner;
    }
    /**
     * @return the owner
     */
    public boolean isOwner() {
        return owner;
    }
}
