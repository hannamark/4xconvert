/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.List;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteOverallStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.TrialRegistrationServiceRemote;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

/**
 * @author Vrushali
 *
 */
public class MockTrialRegistrationService implements TrialRegistrationServiceRemote{

    public Ii amend(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            StudySiteDTO nctIdentifierSiteIdentifierDTO,
            StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO,
            OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi) throws PAException {
                if (studyProtocolDTO.getOfficialTitle().getValue().equals("testthrowException")){
                    throw new PAException("test");
                }
                return IiConverter.convertToIi("2");
        }

    public Ii createInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            StudySiteDTO nctIdentifierSiteIdentifierDTO,
            StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO,
            OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi) throws PAException {
        if (studyProtocolDTO.getOfficialTitle().getValue().equals("testthrowException")){
            throw new PAException("test");
        }
        return IiConverter.convertToIi("3");
    }
    public void update(
            StudyProtocolDTO studyProtocolDTO ,
            StudyOverallStatusDTO overallStatusDTO ,
            StudySiteDTO ssDto,
            List<StudyIndldeDTO> studyIndldeDTOs ,
            List<StudyResourcingDTO> studyResourcingDTOs ,
            DocumentDTO documentDTO ,
            OrganizationDTO leadOrgDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrgDTO,
            StudyContactDTO studyContactDTO ,
            StudySiteContactDTO studyParticipationContactDTO, 
            OrganizationDTO summary4organizationDTO ,
            StudyResourcingDTO summary4studyResourcingDTO ,
            Ii responsiblePartyContactIi, 
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, 
            List<StudySiteDTO> collaborators, 
            List<StudySiteAccrualStatusDTO> participatingSites,
            List<StudySiteDTO> pgCdUpdatedList) throws PAException {
       
     }

    public void update(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO, StudySiteDTO ssDto,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, OrganizationDTO leadOrgDTO,
            PersonDTO principalInvestigatorDTO, OrganizationDTO sponsorOrgDTO,
            StudyContactDTO studyContactDTO,
            StudySiteContactDTO studyParticipationContactDTO,
            OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            List<StudySiteDTO> collaborators,
            List<StudySiteAccrualStatusDTO> participatingSites,
            List<StudySiteDTO> pgCdUpdatedList) throws PAException {
        // TODO Auto-generated method stub
        
    }

    public Ii createInterventionalProprietaryStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            StudySiteOverallStatusDTO siteOverallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            St leadOrganizationTrialIdentifier, PersonDTO siteInvestigatorDTO,
            OrganizationDTO studySiteDTO, St localSiteIdentifier,
            St siteProgramCodeText, St nctIdentifierSiteIdentifier,
            OrganizationDTO summary4organizationDTO, Cd summary4CategoryCode)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }
    

}
