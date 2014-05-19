/**
 * 
 */
package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.domain.RegistryUser;

import java.io.Serializable;

/**
 * @author vrushali
 *
 */
public class TrialOwner implements Serializable {
    private static final long serialVersionUID = -1587594233640048235L;
    private RegistryUser regUser;
    private boolean owner;
    private boolean enableEmails;
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
    /**
     * @return the enableEmails
     */
    public boolean isEnableEmails() {
        return enableEmails;
    }
    /**
     * @param enableEmails the enableEmails to set
     */
    public void setEnableEmails(boolean enableEmails) {
        this.enableEmails = enableEmails;
    }
}
