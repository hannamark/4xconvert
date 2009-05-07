/**
 * 
 */
package gov.nih.nci.registry.service;

import java.util.List;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
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
            StudyParticipationDTO leadOrganizationParticipationIdentifierDTO,
            StudyParticipationDTO nctIdentifierParticipationIdentifierDTO,
            StudyContactDTO studyContactDTO,
            StudyParticipationContactDTO studyParticipationContactDTO,
            OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            PersonDTO responsiblePartyContactDTO) throws PAException {
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
            StudyParticipationDTO leadOrganizationParticipationIdentifierDTO,
            StudyParticipationDTO nctIdentifierParticipationIdentifierDTO,
            StudyContactDTO studyContactDTO,
            StudyParticipationContactDTO studyParticipationContactDTO,
            OrganizationDTO summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO,
            PersonDTO responsiblePartyContactDTO) throws PAException {
        if (studyProtocolDTO.getOfficialTitle().getValue().equals("testthrowException")){
            throw new PAException("test");
        }
        return IiConverter.convertToIi("3");
    }

}
