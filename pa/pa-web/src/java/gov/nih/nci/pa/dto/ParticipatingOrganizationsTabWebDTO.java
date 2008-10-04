/**
 * 
 */
package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.domain.Organization;

/**
 * DTO class for storing form data used in participating org.
 * 
 * @author Hugh Reinhart
 * @since 10/03/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class ParticipatingOrganizationsTabWebDTO {
    private Organization facilityOrganization;

    /**
     * @return the facilityOrganization
     */
    public Organization getFacilityOrganization() {
        return facilityOrganization;
    }

    /**
     * @param facilityOrganization the facilityOrganization to set
     */
    public void setFacilityOrganization(Organization facilityOrganization) {
        this.facilityOrganization = facilityOrganization;
    }

}
