package gov.nih.nci.coppa.services.pa.trialregistrationservice.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.po.grid.dto.transform.po.OrganizationTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.PersonTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.pa.Document;
import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.StudyContact;
import gov.nih.nci.coppa.services.pa.StudyIndlde;
import gov.nih.nci.coppa.services.pa.StudyOverallStatus;
import gov.nih.nci.coppa.services.pa.StudyProtocol;
import gov.nih.nci.coppa.services.pa.StudyRegulatoryAuthority;
import gov.nih.nci.coppa.services.pa.StudyResourcing;
import gov.nih.nci.coppa.services.pa.StudySite;
import gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus;
import gov.nih.nci.coppa.services.pa.StudySiteContact;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.DocumentTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.IdTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyContactTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyIndldeTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyOverallStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyProtocolTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyRegulatoryAuthorityTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyResourcingTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteAccrualStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteContactTransformer;
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

    public Id createInterventionalStudyProtocol(StudyProtocol studyProtocol, StudyOverallStatus studyOverallStatus,
            StudyIndlde[] studyIndlde, StudyResourcing[] studyResourcing, Document[] document,
            Organization leadOrganization, Person principalInvestigator, Organization sponsorOrganization,
            StudySite leadOrganizationSiteIdentifier, StudySite nctIdentifierSiteIdentifier, StudyContact studyContact,
            StudySiteContact studySiteContact, Organization summaryForOrganization,
            StudyResourcing summaryForStudyResourcing, Id responsiblePartyContact) throws RemoteException, PAFault {
        try {
            StudyProtocolDTO studyProtocolDTO = StudyProtocolTransformer.INSTANCE.toDto(studyProtocol);
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
            Ii ii =
                    service.createInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
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

    public Id amend(StudyProtocol studyProtocol, StudyOverallStatus studyOverallStatus, StudyIndlde[] studyIndlde,
            StudyResourcing[] studyResourcing, Document[] document, Organization leadOrganization,
            Person principalInvestigator, Organization sponsorOrganization, StudySite leadOrganizationSiteIdentifier,
            StudySite nctIdentifierSiteIdentifier, StudyContact studyContact, StudySiteContact studySiteContact,
            Organization summaryForOrganization, StudyResourcing summaryForStudyResourcing, Id responsiblePartyContact)
            throws RemoteException, PAFault {
        try {
            StudyProtocolDTO studyProtocolDTO = StudyProtocolTransformer.INSTANCE.toDto(studyProtocol);
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
            Ii ii =
                    service.amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
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

    public void update(StudyProtocol studyProtocol, StudyOverallStatus studyOverallStatus, StudySite studySite,
            StudyIndlde[] studyIndlde, StudyResourcing[] studyResourcing, Document[] document,
            Organization leadOrganization, Person principalInvestigator, Organization sponsorOrganization,
            StudyContact studyContact, StudySiteContact studySiteContact, Organization summaryForOrganization,
            StudyResourcing summaryForStudyResourcing, Id responsiblePartyContact,
            StudyRegulatoryAuthority studyRegulatoryAuthority, StudySite[] collaborators,
            StudySiteAccrualStatus[] participatingSites, StudySite[] pgCdUpdatedList) throws RemoteException, PAFault {
        try {
            StudyProtocolDTO studyProtocolDTO = StudyProtocolTransformer.INSTANCE.toDto(studyProtocol);
            StudyOverallStatusDTO overallStatusDTO = StudyOverallStatusTransformer.INSTANCE.toDto(studyOverallStatus);
            StudySiteDTO studySiteDTO = StudySiteTransformer.INSTANCE.toDto(studySite);
            List<StudyIndldeDTO> studyIndldeDTOs = StudyIndldeTransformer.INSTANCE.convert(studyIndlde);
            List<StudyResourcingDTO> studyResourcingDTOs = StudyResourcingTransformer.INSTANCE.convert(studyResourcing);
            List<DocumentDTO> documentDTOs = DocumentTransformer.INSTANCE.convert(document);
            OrganizationDTO leadOrganizationDTO = OrganizationTransformer.INSTANCE.toDto(leadOrganization);
            PersonDTO principalInvestigatorDTO = PersonTransformer.INSTANCE.toDto(principalInvestigator);
            OrganizationDTO sponsorOrganizationDTO = OrganizationTransformer.INSTANCE.toDto(sponsorOrganization);
            StudyContactDTO studyContactDTO = StudyContactTransformer.INSTANCE.toDto(studyContact);
            StudySiteContactDTO studySiteContactDTO = StudySiteContactTransformer.INSTANCE.toDto(studySiteContact);
            OrganizationDTO summary4organizationDTO = OrganizationTransformer.INSTANCE.toDto(summaryForOrganization);
            StudyResourcingDTO summary4studyResourcingDTO =
                    StudyResourcingTransformer.INSTANCE.toDto(summaryForStudyResourcing);
            Ii responsiblePartyContactIi = IITransformer.INSTANCE.toDto(responsiblePartyContact);
            StudyRegulatoryAuthorityDTO studyRegAuthDTO =
                    StudyRegulatoryAuthorityTransformer.INSTANCE.toDto(studyRegulatoryAuthority);
            List<StudySiteDTO> collaboratorDTOs = StudySiteTransformer.INSTANCE.convert(collaborators);
            List<StudySiteAccrualStatusDTO> participatingSiteDTOs =
                    StudySiteAccrualStatusTransformer.INSTANCE.convert(participatingSites);
            List<StudySiteDTO> pgCdUpdatedDtoList = StudySiteTransformer.INSTANCE.convert(pgCdUpdatedList);

            service.update(studyProtocolDTO, overallStatusDTO, studySiteDTO, studyIndldeDTOs, studyResourcingDTOs,
                    documentDTOs, leadOrganizationDTO, principalInvestigatorDTO, sponsorOrganizationDTO,
                    studyContactDTO, studySiteContactDTO, summary4organizationDTO, summary4studyResourcingDTO,
                    responsiblePartyContactIi, studyRegAuthDTO, collaboratorDTOs, participatingSiteDTOs,
                    pgCdUpdatedDtoList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    public void reject(Id studyProtocol) throws RemoteException, PAFault {
        try {
            Ii studyProtocolIi = IITransformer.INSTANCE.toDto(studyProtocol);
            service.reject(studyProtocolIi);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

}
