/**
 * 
 */
package gov.nih.nci.po.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Denis G. Krylov
 * @author Rohit Gupta
 * 
 */
@SuppressWarnings("PMD.TooManyFields")
public class OrganizationSearchDTO extends BaseSearchResultDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 2833313620499941791L;

    private String name;
    private String familyName;
    private String roCtepId;
    private String hcfCtepId;
    private String ioCtepId;
    private int changeRequests;
    private int pendingROs;
    private int pendingHCFs;
    private Date statusDate;
    private int totalROs;
    private int totalHCFs;
    private int totalIdOrgs;
    private int totalOversightCommitees;
    private int totalOrgContacts;
    private List<AliasDTO> aliasDto;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the familyName
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * @param familyName
     *            the familyName to set
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * @return the roCtepId
     */
    public String getRoCtepId() {
        return roCtepId;
    }

    /**
     * @param roCtepId
     *            the roCtepId to set
     */
    public void setRoCtepId(String roCtepId) {
        this.roCtepId = roCtepId;
    }

    /**
     * @return the hcfCtepId
     */
    public String getHcfCtepId() {
        return hcfCtepId;
    }

    /**
     * @param hcfCtepId
     *            the hcfCtepId to set
     */
    public void setHcfCtepId(String hcfCtepId) {
        this.hcfCtepId = hcfCtepId;
    }
    
    /**
     * 
     * @return the ioCtepId
     */
    public String getIoCtepId() {
        return ioCtepId;
    }

    /**
     * 
     * @param ioCtepId 
     *         the ioCtepId to set
     */
    public void setIoCtepId(String ioCtepId) {
        this.ioCtepId = ioCtepId;
    }

    /**
     * @return the changeRequests
     */
    public int getChangeRequests() {
        return changeRequests;
    }

    /**
     * @param changeRequests
     *            the changeRequests to set
     */
    public void setChangeRequests(int changeRequests) {
        this.changeRequests = changeRequests;
    }

    /**
     * @return the pendingROs
     */
    public int getPendingROs() {
        return pendingROs;
    }

    /**
     * @param pendingROs
     *            the pendingROs to set
     */
    public void setPendingROs(int pendingROs) {
        this.pendingROs = pendingROs;
    }

    /**
     * @return the pendingHCFs
     */
    public int getPendingHCFs() {
        return pendingHCFs;
    }

    /**
     * @param pendingHCFs
     *            the pendingHCFs to set
     */
    public void setPendingHCFs(int pendingHCFs) {
        this.pendingHCFs = pendingHCFs;
    }

    /**
     * @return the statusDate
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * @param statusDate
     *            the statusDate to set
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * @return the totalROs
     */
    public int getTotalROs() {
        return totalROs;
    }

    /**
     * @param totalROs
     *            the totalROs to set
     */
    public void setTotalROs(int totalROs) {
        this.totalROs = totalROs;
    }

    /**
     * @return the totalHCFs
     */
    public int getTotalHCFs() {
        return totalHCFs;
    }

    /**
     * @param totalHCFs
     *            the totalHCFs to set
     */
    public void setTotalHCFs(int totalHCFs) {
        this.totalHCFs = totalHCFs;
    }

    /**
     * @return the totalIdOrgs
     */
    public int getTotalIdOrgs() {
        return totalIdOrgs;
    }

    /**
     * @param totalIdOrgs
     *            the totalIdOrgs to set
     */
    public void setTotalIdOrgs(int totalIdOrgs) {
        this.totalIdOrgs = totalIdOrgs;
    }

    /**
     * @return the totalOversightCommitees
     */
    public int getTotalOversightCommitees() {
        return totalOversightCommitees;
    }

    /**
     * @param totalOversightCommitees
     *            the totalOversightCommitees to set
     */
    public void setTotalOversightCommitees(int totalOversightCommitees) {
        this.totalOversightCommitees = totalOversightCommitees;
    }

    /**
     * @return the totalOrgContacts
     */
    public int getTotalOrgContacts() {
        return totalOrgContacts;
    }

    /**
     * @param totalOrgContacts
     *            the totalOrgContacts to set
     */
    public void setTotalOrgContacts(int totalOrgContacts) {
        this.totalOrgContacts = totalOrgContacts;
    }

    /**
     * 
     * @return AliasDto list
     */
    public List<AliasDTO> getAliasDto() {
        return aliasDto;
    }

    /**
     * 
     * @param aliasDto
     *            List<AliasDTO>
     */
    public void setAliasDto(List<AliasDTO> aliasDto) {
        this.aliasDto = aliasDto;
    }

    /**
     * 
     * @author Rohit Gupta
     * 
     */
    public static class AliasDTO implements Serializable {

        private static final long serialVersionUID = 1L;

        private String value;

        /**
         * @param value
         *            value
         */
        public AliasDTO(String value) {
            this.value = value;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }

        /**
         * @param value
         *            the value to set
         */
        public void setValue(String value) {
            this.value = value;
        }

    }

}
