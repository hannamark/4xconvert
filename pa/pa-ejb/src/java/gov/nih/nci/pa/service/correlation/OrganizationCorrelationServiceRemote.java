package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.List;

import javax.ejb.Remote;

/**
 * An interface for the OrganizationCorrealtion service.
 * 
 * @author Hugh Reinhart
 * @since 01/05/2009 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Remote
public interface OrganizationCorrelationServiceRemote {
    /**
     * 
     * @param orgPoIdentifier org id
     * @return Long
     * @throws PAException pe
     */
    Long createHealthCareFacilityCorrelations(String orgPoIdentifier) throws PAException;
    
    /**
     * @param orgPoIdentifier org id
     * @return Long
     * @throws PAException pe
     */
    Long createResearchOrganizationCorrelations(String orgPoIdentifier) throws PAException;
    
    /**
     * @param orgPoIdentifier org id
     * @return Long
     * @throws PAException pe
     */
    Long createOversightCommitteeCorrelations(String orgPoIdentifier) throws PAException;
    
    /***
     * 
     * @param studyProtocolId sp id
     * @param functionalCode functional code
     * @return List org
     * @throws PAException e
     */
    List<Organization> getOrganizationByStudyParticipation(Long studyProtocolId , 
            StudyParticipationFunctionalCode functionalCode) throws PAException;

    /**
     * 
     * @param poOrg po
     * @return Organization o
     * @throws PAException pe
     */
    Organization createPAOrganizationUsingPO(OrganizationDTO poOrg) throws PAException;
    
    /**
     * 
     * @param studyProtocolIi sp id
     * @param cd functional role code
     * @return Organization
     * @throws PAException onError
     */
    Organization getOrganizationByFunctionRole(Ii studyProtocolIi , Cd cd) throws PAException;
    
    
}
