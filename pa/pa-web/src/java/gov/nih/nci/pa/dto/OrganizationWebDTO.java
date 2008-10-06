/**
 * 
 */
package gov.nih.nci.pa.dto;

/**
 * DTO class for displaying organizations as a list.
 * 
 * @author Hugh Reinhart
 * @since 09/24/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class OrganizationWebDTO {
    String id;
    String name;
    String nciNumber;
    String recruitmentStatus;
    String recruitmentStatusDate;
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the nciNumber
     */
    public String getNciNumber() {
        return nciNumber;
    }
    /**
     * @param nciNumber the nciNumber to set
     */
    public void setNciNumber(String nciNumber) {
        this.nciNumber = nciNumber;
    }
    /**
     * @return the recruitmentStatus
     */
    public String getRecruitmentStatus() {
        return recruitmentStatus;
    }
    /**
     * @param recruitmentStatus the recruitmentStatus to set
     */
    public void setRecruitmentStatus(String recruitmentStatus) {
        this.recruitmentStatus = recruitmentStatus;
    }
    /**
     * @return the recruitmentStatusDate
     */
    public String getRecruitmentStatusDate() {
        return recruitmentStatusDate;
    }
    /**
     * @param recruitmentStatusDate the recruitmentStatusDate to set
     */
    public void setRecruitmentStatusDate(String recruitmentStatusDate) {
        this.recruitmentStatusDate = recruitmentStatusDate;
    }
}
