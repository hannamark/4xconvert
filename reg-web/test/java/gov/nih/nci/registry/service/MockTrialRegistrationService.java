/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
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
            List<StudySiteDTO> studyIdentifierDTOs,
            StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO,
            OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi, StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            Bl isBatchMode) throws PAException {
                if (studyProtocolDTO.getOfficialTitle().getValue().equals("testthrowException")){
                    throw new PAException("test");
                }
                return IiConverter.convertToIi("2");
        }

    public Ii createCompleteInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO,
            OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi, StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            Bl isBatchMode) throws PAException {
        if (studyProtocolDTO.getOfficialTitle().getValue().equals("testthrowException")){
            throw new PAException("test");
        }
        return IiConverter.convertToIi("3");
    }
    
    public void reject(Ii studyProtocolIi, St rejectionReason, Cd rejectionReasonCode) throws PAException {
        // TODO Auto-generated method stub

    }

    public Ii createAbbreviatedInterventionalStudyProtocol(
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
            StudyOverallStatusDTO overallStatusDTO, List<StudySiteDTO> studyIdentifierDTOs,
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

    /**
     * {@inheritDoc}
     */
    public void update(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs, List<StudySiteDTO> studySiteDTOs, 
            Bl isBatchMode)
            throws PAException {
    }




}
