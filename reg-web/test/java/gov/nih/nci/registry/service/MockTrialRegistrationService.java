/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.List;

/**
 * @author Vrushali
 *
 */
public class MockTrialRegistrationService implements TrialRegistrationServiceLocal{

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
            Ii responsiblePartyContactIi, Bl isBatchMode) throws PAException {
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
            Ii responsiblePartyContactIi, Bl isBatchMode) throws PAException {
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
            List<StudySiteDTO> pgCdUpdatedList, Bl isBatchMode) throws PAException {
       
     }
    
    public void reject(Ii studyProtocolIi, St rejectionReason) throws PAException {
        // TODO Auto-generated method stub
        
    }

    public Ii createProprietaryInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO studySiteInvestigatorDTO,
            StudySiteDTO leadOrganizationStudySiteDTO,
            OrganizationDTO studySiteOrganizationDTO,
            StudySiteDTO studySiteDTO, StudySiteDTO nctIdentifierDTO,
            OrganizationDTO summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, Bl isBatchMode) throws PAException {
        if (studyProtocolDTO.getOfficialTitle().getValue().equals("testthrowException")){
            throw new PAException("test");
        }
        return IiConverter.convertToIi("3");
    }

    public void update(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO, StudySiteDTO studySiteDto,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studyParticipationContactDTO,
            OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            List<StudySiteDTO> collaborators,
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatuses,
            List<StudySiteDTO> studySites, Bl isBatchMode) throws PAException {
        // TODO Auto-generated method stub
        
    }

   
    

}
