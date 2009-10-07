package gov.nih.nci.coppa.services.pa.trialregistrationservice.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.grid.dto.transform.po.OrganizationTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.PersonTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.DocumentTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.IdTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.InterventionalStudyProtocolTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyContactTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyIndldeTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyOverallStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyRegulatoryAuthorityTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyResourcingTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteAccrualStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteContactTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteOverallStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeTrialRegistrationEjb;
import gov.nih.nci.coppa.services.pa.studyprotocolservice.service.StudyProtocolServiceImpl;
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
import gov.nih.nci.pa.iso.dto.StudySiteOverallStatusDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Grid service for managing trial registration.
 *
 * @created by Introduce Toolkit version 1.3
 *
 */
public class TrialRegistrationServiceImpl extends TrialRegistrationServiceImplBase {

    private static final Logger logger = LogManager.getLogger(StudyProtocolServiceImpl.class);
    private final InvokeTrialRegistrationEjb service = new InvokeTrialRegistrationEjb();

    public TrialRegistrationServiceImpl() throws RemoteException {
        super();
    }

  public gov.nih.nci.coppa.services.pa.Id createInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.InterventionalStudyProtocol studyProtocol,gov.nih.nci.coppa.services.pa.StudyOverallStatus studyOverallStatus,gov.nih.nci.coppa.services.pa.StudyIndlde[] studyIndlde,gov.nih.nci.coppa.services.pa.StudyResourcing[] studyResourcing,gov.nih.nci.coppa.services.pa.Document[] document,gov.nih.nci.coppa.po.Organization leadOrganization,gov.nih.nci.coppa.po.Person principalInvestigator,gov.nih.nci.coppa.po.Organization sponsorOrganization,gov.nih.nci.coppa.services.pa.StudySite leadOrganizationSiteIdentifier,gov.nih.nci.coppa.services.pa.StudySite nctIdentifierSiteIdentifier,gov.nih.nci.coppa.services.pa.StudyContact studyContact,gov.nih.nci.coppa.services.pa.StudySiteContact studySiteContact,gov.nih.nci.coppa.po.Organization summaryForOrganization,gov.nih.nci.coppa.services.pa.StudyResourcing summaryForStudyResourcing,gov.nih.nci.coppa.services.pa.Id responsiblePartyContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            StudyProtocolDTO studyProtocolDTO = InterventionalStudyProtocolTransformer.INSTANCE.toDto(studyProtocol);
            StudyOverallStatusDTO overallStatusDTO = StudyOverallStatusTransformer.INSTANCE.toDto(studyOverallStatus);
            List<StudyIndldeDTO> studyIndldeDTOs = StudyIndldeTransformer.INSTANCE.convert(studyIndlde);
            List<StudyResourcingDTO> studyResourcingDTOs = StudyResourcingTransformer.INSTANCE.convert(studyResourcing);
            List<DocumentDTO> documentDTOs = DocumentTransformer.INSTANCE.convert(document);
            OrganizationDTO leadOrganizationDTO = OrganizationTransformer.INSTANCE.toDto(leadOrganization);
            PersonDTO principalInvestigatorDTO = PersonTransformer.INSTANCE.toDto(principalInvestigator);
            OrganizationDTO sponsorOrganizationDTO = OrganizationTransformer.INSTANCE.toDto(sponsorOrganization);
            StudySiteDTO leadOrganizationSiteIdentifierDTO =
                    StudySiteTransformer.INSTANCE.toDto(leadOrganizationSiteIdentifier);
            StudySiteDTO nctIdentifierSiteIdentifierDTO =
                    StudySiteTransformer.INSTANCE.toDto(nctIdentifierSiteIdentifier);
            StudyContactDTO studyContactDTO = StudyContactTransformer.INSTANCE.toDto(studyContact);
            StudySiteContactDTO studySiteContactDTO = StudySiteContactTransformer.INSTANCE.toDto(studySiteContact);
            OrganizationDTO summary4organizationDTO = OrganizationTransformer.INSTANCE.toDto(summaryForOrganization);
            StudyResourcingDTO summary4studyResourcingDTO =
                    StudyResourcingTransformer.INSTANCE.toDto(summaryForStudyResourcing);
            Ii responsiblePartyContactIi = IITransformer.INSTANCE.toDto(responsiblePartyContact);
            Ii ii = service.createInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                            studyResourcingDTOs, documentDTOs, leadOrganizationDTO, principalInvestigatorDTO,
                            sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, nctIdentifierSiteIdentifierDTO,
                            studyContactDTO, studySiteContactDTO, summary4organizationDTO, summary4studyResourcingDTO,
                            responsiblePartyContactIi);
            return IdTransformer.INSTANCE.toXml(ii);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.services.pa.Id amendInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.InterventionalStudyProtocol studyProtocol,gov.nih.nci.coppa.services.pa.StudyOverallStatus studyOverallStatus,gov.nih.nci.coppa.services.pa.StudyIndlde[] studyIndlde,gov.nih.nci.coppa.services.pa.StudyResourcing[] studyResourcing,gov.nih.nci.coppa.services.pa.Document[] document,gov.nih.nci.coppa.po.Organization leadOrganization,gov.nih.nci.coppa.po.Person principalInvestigator,gov.nih.nci.coppa.po.Organization sponsorOrganization,gov.nih.nci.coppa.services.pa.StudySite leadOrganizationSiteIdentifier,gov.nih.nci.coppa.services.pa.StudySite nctIdentifierSiteIdentifier,gov.nih.nci.coppa.services.pa.StudyContact studyContact,gov.nih.nci.coppa.services.pa.StudySiteContact studySiteContact,gov.nih.nci.coppa.po.Organization summaryForOrganization,gov.nih.nci.coppa.services.pa.StudyResourcing summaryForStudyResourcing,gov.nih.nci.coppa.services.pa.Id responsiblePartyContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            StudyProtocolDTO studyProtocolDTO = InterventionalStudyProtocolTransformer.INSTANCE.toDto(studyProtocol);
            StudyOverallStatusDTO overallStatusDTO = StudyOverallStatusTransformer.INSTANCE.toDto(studyOverallStatus);
            List<StudyIndldeDTO> studyIndldeDTOs = StudyIndldeTransformer.INSTANCE.convert(studyIndlde);
            List<StudyResourcingDTO> studyResourcingDTOs = StudyResourcingTransformer.INSTANCE.convert(studyResourcing);
            List<DocumentDTO> documentDTOs = DocumentTransformer.INSTANCE.convert(document);
            OrganizationDTO leadOrganizationDTO = OrganizationTransformer.INSTANCE.toDto(leadOrganization);
            PersonDTO principalInvestigatorDTO = PersonTransformer.INSTANCE.toDto(principalInvestigator);
            OrganizationDTO sponsorOrganizationDTO = OrganizationTransformer.INSTANCE.toDto(sponsorOrganization);
            StudySiteDTO leadOrganizationSiteIdentifierDTO =
                    StudySiteTransformer.INSTANCE.toDto(leadOrganizationSiteIdentifier);
            StudySiteDTO nctIdentifierSiteIdentifierDTO =
                    StudySiteTransformer.INSTANCE.toDto(nctIdentifierSiteIdentifier);
            StudyContactDTO studyContactDTO = StudyContactTransformer.INSTANCE.toDto(studyContact);
            StudySiteContactDTO studySiteContactDTO = StudySiteContactTransformer.INSTANCE.toDto(studySiteContact);
            OrganizationDTO summary4organizationDTO = OrganizationTransformer.INSTANCE.toDto(summaryForOrganization);
            StudyResourcingDTO summary4studyResourcingDTO =
                    StudyResourcingTransformer.INSTANCE.toDto(summaryForStudyResourcing);
            Ii responsiblePartyContactIi = IITransformer.INSTANCE.toDto(responsiblePartyContact);
            Ii ii = service.amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                            documentDTOs, leadOrganizationDTO, principalInvestigatorDTO, sponsorOrganizationDTO,
                            leadOrganizationSiteIdentifierDTO, nctIdentifierSiteIdentifierDTO, studyContactDTO,
                            studySiteContactDTO, summary4organizationDTO, summary4studyResourcingDTO,
                            responsiblePartyContactIi);
            return IdTransformer.INSTANCE.toXml(ii);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public void updateInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.InterventionalStudyProtocol studyProtocol,gov.nih.nci.coppa.services.pa.StudyOverallStatus studyOverallStatus,gov.nih.nci.coppa.services.pa.StudySite studySite,gov.nih.nci.coppa.services.pa.StudyIndlde[] studyIndlde,gov.nih.nci.coppa.services.pa.StudyResourcing[] studyResourcing,gov.nih.nci.coppa.services.pa.Document[] document,gov.nih.nci.coppa.services.pa.StudyContact studyContact,gov.nih.nci.coppa.services.pa.StudySiteContact studySiteContact,gov.nih.nci.coppa.po.Organization summaryForOrganization,gov.nih.nci.coppa.services.pa.StudyResourcing summaryForStudyResourcing,gov.nih.nci.coppa.services.pa.Id responsiblePartyContact,gov.nih.nci.coppa.services.pa.StudyRegulatoryAuthority studyRegulatoryAuthority,gov.nih.nci.coppa.services.pa.StudySite[] collaborators,gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus[] studySiteAccrualStatuses,gov.nih.nci.coppa.services.pa.StudySite[] studySites) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            StudyProtocolDTO studyProtocolDTO = InterventionalStudyProtocolTransformer.INSTANCE.toDto(studyProtocol);
            StudyOverallStatusDTO overallStatusDTO = StudyOverallStatusTransformer.INSTANCE.toDto(studyOverallStatus);
            StudySiteDTO studySiteDTO = StudySiteTransformer.INSTANCE.toDto(studySite);
            List<StudyIndldeDTO> studyIndldeDTOs = StudyIndldeTransformer.INSTANCE.convert(studyIndlde);
            List<StudyResourcingDTO> studyResourcingDTOs = StudyResourcingTransformer.INSTANCE.convert(studyResourcing);
            List<DocumentDTO> documentDTOs = DocumentTransformer.INSTANCE.convert(document);
            StudyContactDTO studyContactDTO = StudyContactTransformer.INSTANCE.toDto(studyContact);
            StudySiteContactDTO studySiteContactDTO = StudySiteContactTransformer.INSTANCE.toDto(studySiteContact);
            OrganizationDTO summary4organizationDTO = OrganizationTransformer.INSTANCE.toDto(summaryForOrganization);
            StudyResourcingDTO summary4studyResourcingDTO =
                    StudyResourcingTransformer.INSTANCE.toDto(summaryForStudyResourcing);
            Ii responsiblePartyContactIi = IITransformer.INSTANCE.toDto(responsiblePartyContact);
            StudyRegulatoryAuthorityDTO studyRegAuthDTO =
                    StudyRegulatoryAuthorityTransformer.INSTANCE.toDto(studyRegulatoryAuthority);
            List<StudySiteDTO> collaboratorDTOs = StudySiteTransformer.INSTANCE.convert(collaborators);
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs =
                    StudySiteAccrualStatusTransformer.INSTANCE.convert(studySiteAccrualStatuses);
            List<StudySiteDTO> studySiteDTOs = StudySiteTransformer.INSTANCE.convert(studySites);

            service.update(studyProtocolDTO, overallStatusDTO, studySiteDTO, studyIndldeDTOs, studyResourcingDTOs,
                    documentDTOs,
                    studyContactDTO, studySiteContactDTO, summary4organizationDTO, summary4studyResourcingDTO,
                    responsiblePartyContactIi, studyRegAuthDTO, collaboratorDTOs, studySiteAccrualStatusDTOs,
                    studySiteDTOs);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.services.pa.Id createProprietaryInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.InterventionalStudyProtocol studyProtocol,gov.nih.nci.coppa.services.pa.StudySiteOverallStatus studySiteOverallStatus,gov.nih.nci.coppa.services.pa.Document[] documents,gov.nih.nci.coppa.po.Organization leadOrganization,gov.nih.nci.coppa.po.Person studySiteInvestigator,gov.nih.nci.coppa.services.pa.StudySite leadOrganizationStudySite,gov.nih.nci.coppa.po.Organization studySiteOrganization,gov.nih.nci.coppa.services.pa.StudySite studySite,gov.nih.nci.coppa.services.pa.StudySite nctIdentifier,gov.nih.nci.coppa.po.Organization summaryForOrganization,gov.nih.nci.coppa.services.pa.StudyResourcing summaryForStudyResourcing) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            StudyProtocolDTO studyProtocolDTO = InterventionalStudyProtocolTransformer.INSTANCE.toDto(studyProtocol);
            StudySiteOverallStatusDTO studySiteOverallStatusDTO =
                    StudySiteOverallStatusTransformer.INSTANCE.toDto(studySiteOverallStatus);
            List<DocumentDTO> documentDTOs = DocumentTransformer.INSTANCE.convert(documents);
            OrganizationDTO leadOrganizationDTO = OrganizationTransformer.INSTANCE.toDto(leadOrganization);
            PersonDTO studySiteInvestigatorDTO = PersonTransformer.INSTANCE.toDto(studySiteInvestigator);
            StudySiteDTO leadOrganizationStudySiteDTO = StudySiteTransformer.INSTANCE.toDto(leadOrganizationStudySite);
            OrganizationDTO studySiteOrganizationDTO = OrganizationTransformer.INSTANCE.toDto(studySiteOrganization);
            StudySiteDTO studySiteDTO = StudySiteTransformer.INSTANCE.toDto(studySite);
            StudySiteDTO nctIdentifierDTO = StudySiteTransformer.INSTANCE.toDto(nctIdentifier);
            OrganizationDTO summary4organizationDTO = OrganizationTransformer.INSTANCE.toDto(summaryForOrganization);
            StudyResourcingDTO summary4studyResourcingDTO =
                    StudyResourcingTransformer.INSTANCE.toDto(summaryForStudyResourcing);
            Ii ii = service.createProprietaryInterventionalStudyProtocol(studyProtocolDTO, studySiteOverallStatusDTO,
                            documentDTOs, leadOrganizationDTO, studySiteInvestigatorDTO, leadOrganizationStudySiteDTO,
                            studySiteOrganizationDTO, studySiteDTO, nctIdentifierDTO, summary4organizationDTO,
                            summary4studyResourcingDTO);
            return IdTransformer.INSTANCE.toXml(ii);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

}
