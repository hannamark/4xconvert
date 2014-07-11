/**
 * 
 */
package gov.nih.nci.po.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Denis G. Krylov
 * 
 */
public class PersonSearchDTO extends BaseSearchResultDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 2580463343922551805L;

    private String ctepID;
    private String firstName;
    private String middleName;
    private String lastName;

    private int totalCrs;
    private int totalHcp;
    private int totalOc;
    private int totalOpi;
    private int totalPending;
    
    private Collection<Affiliation> affiliation;

    /**
     * @return the ctepID
     */
    public String getCtepID() {
        return ctepID;
    }

    /**
     * @param ctepID
     *            the ctepID to set
     */
    public void setCtepID(String ctepID) {
        this.ctepID = ctepID;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName
     *            the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return the totalCrs
     */
    public int getTotalCrs() {
        return totalCrs;
    }

    /**
     * @param totalCrs
     *            the totalCrs to set
     */
    public void setTotalCrs(int totalCrs) {
        this.totalCrs = totalCrs;
    }

    /**
     * @return the totalHcp
     */
    public int getTotalHcp() {
        return totalHcp;
    }

    /**
     * @param totalHcp
     *            the totalHcp to set
     */
    public void setTotalHcp(int totalHcp) {
        this.totalHcp = totalHcp;
    }

    /**
     * @return the totalOc
     */
    public int getTotalOc() {
        return totalOc;
    }

    /**
     * @param totalOc
     *            the totalOc to set
     */
    public void setTotalOc(int totalOc) {
        this.totalOc = totalOc;
    }

    /**
     * @return the totalOpi
     */
    public int getTotalOpi() {
        return totalOpi;
    }

    /**
     * @param totalOpi
     *            the totalOpi to set
     */
    public void setTotalOpi(int totalOpi) {
        this.totalOpi = totalOpi;
    }

    /**
     * @return the totalPending
     */
    public int getTotalPending() {
        return totalPending;
    }

    /**
     * @param totalPending
     *            the totalPending to set
     */
    public void setTotalPending(int totalPending) {
        this.totalPending = totalPending;
    }

    /**
     * @author Denis G. Krylov
     * 
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public static class Affiliation implements Serializable,
            Comparable<Affiliation> {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        private String orgName;
        private boolean pending;
        private RoleGroup group;

        /**
         * @param orgName orgName
         * @param pending pending
         * @param group group
         */
        public Affiliation(String orgName, boolean pending, RoleGroup group) {
            this.orgName = orgName;
            this.pending = pending;
            this.group = group;
        }

        /**
         * @author Denis G. Krylov
         * 
         */
        // CHECKSTYLE:OFF
        public static enum RoleGroup {
            CRS, HCP, OC, OPI;
        }

        /**
         * @return the orgName
         */
        public String getOrgName() {
            return orgName;
        }

        /**
         * @param orgName
         *            the orgName to set
         */
        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        /**
         * @return the pending
         */
        public boolean isPending() {
            return pending;
        }

        /**
         * @param pending
         *            the pending to set
         */
        public void setPending(boolean pending) {
            this.pending = pending;
        }

        /**
         * @return the group
         */
        public RoleGroup getGroup() {
            return group;
        }

        /**
         * @param group
         *            the group to set
         */
        public void setGroup(RoleGroup group) {
            this.group = group;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((group == null) ? 0 : group.hashCode());
            result = prime * result
                    + ((orgName == null) ? 0 : orgName.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @SuppressWarnings("PMD.CyclomaticComplexity")
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Affiliation)) {
                return false;
            }
            Affiliation other = (Affiliation) obj;
            if (group != other.group) {
                return false;
            }
            if (orgName == null) {
                if (other.orgName != null) {
                    return false;
                }
            } else if (!orgName.equals(other.orgName)) {
                return false;
            }
            return true;
        }

        @Override
        public int compareTo(Affiliation o) {
            return new CompareToBuilder().append(group, o.group)
                    .append(StringUtils.trim(orgName), StringUtils.trim(o.orgName)).toComparison();
        }

    }

    /**
     * @return the affiliation
     */
    public Collection<Affiliation> getAffiliation() {
        return affiliation;
    }

    /**
     * @param affiliation the affiliation to set
     */
    public void setAffiliation(Collection<Affiliation> affiliation) {
        this.affiliation = affiliation;
    }

    

}
