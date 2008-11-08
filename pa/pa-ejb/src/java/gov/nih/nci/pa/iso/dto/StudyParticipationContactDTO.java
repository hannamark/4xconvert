/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;

/**
 * @author Hugh Reinhart
 * @since 09/29/2008
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class StudyParticipationContactDTO extends PersonFunctionalRoleDTO {
    private static final long serialVersionUID = 8652424790L;

    private Ad postalAddress;
    private Bl primaryIndicator;
    private Cd roleCode;
    private DSet<Tel> telecomAddresses;
    private Ii studyParticipationIi;
    private Ii organizationalContactIi;
    /**
     * @return the postalAddress
     */
    public Ad getPostalAddress() {
        return postalAddress;
    }
    /**
     * @param postalAddress the postalAddress to set
     */
    public void setPostalAddress(Ad postalAddress) {
        this.postalAddress = postalAddress;
    }
    /**
     * @return the primaryIndicator
     */
    public Bl getPrimaryIndicator() {
        return primaryIndicator;
    }
    /**
     * @param primaryIndicator the primaryIndicator to set
     */
    public void setPrimaryIndicator(Bl primaryIndicator) {
        this.primaryIndicator = primaryIndicator;
    }
    /**
     * @return the roleCode
     */
    public Cd getRoleCode() {
        return roleCode;
    }
    /**
     * @param roleCode the roleCode to set
     */
    public void setRoleCode(Cd roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * @return the telecomAddresses
     */
    public DSet<Tel> getTelecomAddresses() {
        return telecomAddresses;
    }
    /**
     * @param telecomAddresses the telecomAddresses to set
     */
    public void setTelecomAddresses(DSet<Tel> telecomAddresses) {
        this.telecomAddresses = telecomAddresses;
    }
    
    /**
     * 
     * @return Ii ii
     */
    public Ii getOrganizationalContactIi() {
        return organizationalContactIi;
    }
    /**
     * 
     * @param organizationalContactIi ii
     */
    public void setOrganizationalContactIi(Ii organizationalContactIi) {
        this.organizationalContactIi = organizationalContactIi;
    }
    /**
     * @return the studyParticipationIi
     */
    public Ii getStudyParticipationIi() {
        return studyParticipationIi;
    }
    /**
     * @param studyParticipationIi the studyParticipationIi to set
     */
    public void setStudyParticipationIi(Ii studyParticipationIi) {
        this.studyParticipationIi = studyParticipationIi;
    }
}
