package gov.nih.nci.coppa.services.pa.trialregistrationservice.service;

import gov.nih.nci.coppa.po.grid.dto.transform.po.OrganizationTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.PersonTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.DocumentTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.EdTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.InterventionalStudyProtocolTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyContactTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyIndldeTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyOverallStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyRegulatoryAuthorityTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyResourcingTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteAccrualStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteContactTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.GridSecurityJNDIServiceLocator;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeProprietaryTrialManagementEjb;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeTrialRegistrationEjb;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.EDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.STTransformer;
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
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.rmi.RemoteException;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.globus.wsrf.security.SecurityManager;

/**
 * Grid service for managing trial registration.
 *
 * @created by Introduce Toolkit version 1.3
 *
 */
public class TrialRegistrationServiceImpl extends TrialRegistrationServiceImplBase {

    private static final Logger logger = LogManager.getLogger(TrialRegistrationServiceImpl.class);
    private final InvokeTrialRegistrationEjb trialRegService = new InvokeTrialRegistrationEjb();    
    private final InvokeProprietaryTrialManagementEjb propTrialMgmtService = new InvokeProprietaryTrialManagementEjb();

    static {
        gov.nih.nci.coppa.services.pa.grid.PAGridUtils.initIso21090Transformers();
    }


    public TrialRegistrationServiceImpl() throws RemoteException {
        super();
    }

  public gov.nih.nci.iso21090.extensions.Id amendInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.InterventionalStudyProtocol studyProtocol,gov.nih.nci.coppa.services.pa.StudyOverallStatus studyOverallStatus,gov.nih.nci.coppa.services.pa.StudyIndlde[] studyIndlde,gov.nih.nci.coppa.services.pa.StudyResourcing[] studyResourcing,gov.nih.nci.coppa.services.pa.Document[] document,gov.nih.nci.coppa.po.Organization leadOrganization,gov.nih.nci.coppa.po.Person principalInvestigator,gov.nih.nci.coppa.po.Organization sponsorOrganization,gov.nih.nci.coppa.services.pa.StudySite leadOrganizationSiteIdentifier,gov.nih.nci.coppa.services.pa.StudySite[] nctIdentifierSiteIdentifiers,gov.nih.nci.coppa.services.pa.StudyContact studyContact,gov.nih.nci.coppa.services.pa.StudySiteContact studySiteContact,gov.nih.nci.coppa.po.Organization summary4Organization,gov.nih.nci.coppa.services.pa.StudyResourcing summary4StudyResourcing,gov.nih.nci.iso21090.extensions.Id responsiblePartyContact,gov.nih.nci.coppa.services.pa.StudyRegulatoryAuthority studyRegulatoryAuthority) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            checkForRegistryUserExistence();
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
            List<StudySiteDTO> studyIdentifierDTOs =
                StudySiteTransformer.INSTANCE.convert(nctIdentifierSiteIdentifiers);

            StudyContactDTO studyContactDTO = StudyContactTransformer.INSTANCE.toDto(studyContact);
            StudySiteContactDTO studySiteContactDTO = StudySiteContactTransformer.INSTANCE.toDto(studySiteContact);
            OrganizationDTO summary4organizationDTO = OrganizationTransformer.INSTANCE.toDto(summary4Organization);
            StudyResourcingDTO summary4studyResourcingDTO =
                    StudyResourcingTransformer.INSTANCE.toDto(summary4StudyResourcing);
            Ii responsiblePartyContactIi = IITransformer.INSTANCE.toDto(responsiblePartyContact);
            Bl isBatch = new Bl();
            isBatch.setValue(Boolean.FALSE);
            defaultingUserLoggedIn(studyProtocolDTO);

            StudyRegulatoryAuthorityDTO studyRegAuthDTO =
                StudyRegulatoryAuthorityTransformer.INSTANCE.toDto(studyRegulatoryAuthority);

            Ii ii = trialRegService.amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                            documentDTOs, leadOrganizationDTO, principalInvestigatorDTO, sponsorOrganizationDTO,
                            leadOrganizationSiteIdentifierDTO, studyIdentifierDTOs, studyContactDTO,
                            studySiteContactDTO, summary4organizationDTO, summary4studyResourcingDTO,
                            responsiblePartyContactIi, studyRegAuthDTO, isBatch);
            return IdTransformer.INSTANCE.toXml(ii);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public void updateInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.InterventionalStudyProtocol studyProtocol,gov.nih.nci.coppa.services.pa.StudyOverallStatus studyOverallStatus,gov.nih.nci.coppa.services.pa.StudySite[] studyIdentifiers,gov.nih.nci.coppa.services.pa.StudyIndlde[] studyIndlde,gov.nih.nci.coppa.services.pa.StudyResourcing[] studyResourcing,gov.nih.nci.coppa.services.pa.Document[] document,gov.nih.nci.coppa.services.pa.StudyContact studyContact,gov.nih.nci.coppa.services.pa.StudySiteContact studySiteContact,gov.nih.nci.coppa.po.Organization summary4Organization,gov.nih.nci.coppa.services.pa.StudyResourcing summary4StudyResourcing,gov.nih.nci.iso21090.extensions.Id responsiblePartyContact,gov.nih.nci.coppa.services.pa.StudyRegulatoryAuthority studyRegulatoryAuthority,gov.nih.nci.coppa.services.pa.StudySite[] collaborators,gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus[] studySiteAccrualStatuses,gov.nih.nci.coppa.services.pa.StudySite[] studySites) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            checkForRegistryUserExistence();
            StudyProtocolDTO studyProtocolDTO = InterventionalStudyProtocolTransformer.INSTANCE.toDto(studyProtocol);
            StudyOverallStatusDTO overallStatusDTO = StudyOverallStatusTransformer.INSTANCE.toDto(studyOverallStatus);
            List<StudySiteDTO> studyIdentifierDTOs = StudySiteTransformer.INSTANCE.convert(studyIdentifiers);
            List<StudyIndldeDTO> studyIndldeDTOs = StudyIndldeTransformer.INSTANCE.convert(studyIndlde);
            List<StudyResourcingDTO> studyResourcingDTOs = StudyResourcingTransformer.INSTANCE.convert(studyResourcing);
            List<DocumentDTO> documentDTOs = DocumentTransformer.INSTANCE.convert(document);
            StudyContactDTO studyContactDTO = StudyContactTransformer.INSTANCE.toDto(studyContact);
            StudySiteContactDTO studySiteContactDTO = StudySiteContactTransformer.INSTANCE.toDto(studySiteContact);
            OrganizationDTO summary4organizationDTO = OrganizationTransformer.INSTANCE.toDto(summary4Organization);
            StudyResourcingDTO summary4studyResourcingDTO =
                    StudyResourcingTransformer.INSTANCE.toDto(summary4StudyResourcing);
            Ii responsiblePartyContactIi = IITransformer.INSTANCE.toDto(responsiblePartyContact);
            StudyRegulatoryAuthorityDTO studyRegAuthDTO =
                    StudyRegulatoryAuthorityTransformer.INSTANCE.toDto(studyRegulatoryAuthority);
            List<StudySiteDTO> collaboratorDTOs = StudySiteTransformer.INSTANCE.convert(collaborators);
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs =
                    StudySiteAccrualStatusTransformer.INSTANCE.convert(studySiteAccrualStatuses);
            List<StudySiteDTO> studySiteDTOs = StudySiteTransformer.INSTANCE.convert(studySites);
            Bl isBatch = new Bl();
            isBatch.setValue(Boolean.FALSE);
            defaultingUserLoggedIn(studyProtocolDTO);

            trialRegService.update(studyProtocolDTO, overallStatusDTO, studyIdentifierDTOs, studyIndldeDTOs,
                    studyResourcingDTOs, documentDTOs, studyContactDTO, studySiteContactDTO, summary4organizationDTO,
                    summary4studyResourcingDTO, responsiblePartyContactIi, studyRegAuthDTO, collaboratorDTOs,
                    studySiteAccrualStatusDTOs, studySiteDTOs, isBatch);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  /**
   * @param studyProtocolDTO
   */
  private void defaultingUserLoggedIn(StudyProtocolDTO studyProtocolDTO) {
      //defaulting logged in user as UserLastCreated
      String identity = SecurityManager.getManager().getCaller();
      St loggedInUser = new St();
      loggedInUser.setValue(identity);
      studyProtocolDTO.setUserLastCreated(loggedInUser);
  }
  
    private void checkForRegistryUserExistence() throws NamingException,
            Exception {
        String identity = SecurityManager.getManager().getCaller();
        RegistryUserServiceRemote registryUserServiceRemote = GridSecurityJNDIServiceLocator
                .newInstance().getRegistryUserService();
        boolean exists = registryUserServiceRemote
                .doesRegistryUserExist(identity);
        if (!exists) {
            throw new PAException(
                    "Only valid Registry users can access the Trial Registration service. "
                            + "Please self-register in the Registry application on the current tier.");
        }
    }

  public gov.nih.nci.iso21090.extensions.Id createCompleteInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.InterventionalStudyProtocol studyProtocol,gov.nih.nci.coppa.services.pa.StudyOverallStatus studyOverallStatus,gov.nih.nci.coppa.services.pa.StudyIndlde[] studyIndlde,gov.nih.nci.coppa.services.pa.StudyResourcing[] studyResourcing,gov.nih.nci.coppa.services.pa.Document[] document,gov.nih.nci.coppa.po.Organization leadOrganization,gov.nih.nci.coppa.po.Person principalInvestigator,gov.nih.nci.coppa.po.Organization sponsorOrganization,gov.nih.nci.coppa.services.pa.StudySite leadOrganizationSiteIdentifier,gov.nih.nci.coppa.services.pa.StudySite[] nctIdentifierSiteIdentifiers,gov.nih.nci.coppa.services.pa.StudyContact studyContact,gov.nih.nci.coppa.services.pa.StudySiteContact studySiteContact,gov.nih.nci.coppa.po.Organization summary4Organization,gov.nih.nci.coppa.services.pa.StudyResourcing summary4StudyResourcing,gov.nih.nci.iso21090.extensions.Id responsiblePartyContact,gov.nih.nci.coppa.services.pa.StudyRegulatoryAuthority studyRegulatoryAuthority) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          checkForRegistryUserExistence();
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
          List<StudySiteDTO> studyIdentifierDTOs =
              StudySiteTransformer.INSTANCE.convert(nctIdentifierSiteIdentifiers);
          StudyContactDTO studyContactDTO = StudyContactTransformer.INSTANCE.toDto(studyContact);
          StudySiteContactDTO studySiteContactDTO = StudySiteContactTransformer.INSTANCE.toDto(studySiteContact);
          OrganizationDTO summary4organizationDTO = OrganizationTransformer.INSTANCE.toDto(summary4Organization);
          StudyResourcingDTO summary4studyResourcingDTO =
                  StudyResourcingTransformer.INSTANCE.toDto(summary4StudyResourcing);
          Ii responsiblePartyContactIi = IITransformer.INSTANCE.toDto(responsiblePartyContact);
          Bl isBatch = new Bl();
          isBatch.setValue(Boolean.FALSE);
          defaultingUserLoggedIn(studyProtocolDTO);

          StudyRegulatoryAuthorityDTO studyRegAuthDTO =
              StudyRegulatoryAuthorityTransformer.INSTANCE.toDto(studyRegulatoryAuthority);

          Ii ii = trialRegService.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                          studyResourcingDTOs, documentDTOs, leadOrganizationDTO, principalInvestigatorDTO,
                          sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, studyIdentifierDTOs,
                          studyContactDTO, studySiteContactDTO, summary4organizationDTO, summary4studyResourcingDTO,
                          responsiblePartyContactIi, studyRegAuthDTO, isBatch, studyProtocolDTO.getRecordOwners());
          
          return IdTransformer.INSTANCE.toXml(ii);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }
	

  public gov.nih.nci.iso21090.extensions.Id createAbbreviatedInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.InterventionalStudyProtocol studyProtocol,gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus studySiteAccrualStatus,gov.nih.nci.coppa.services.pa.Document[] documents,gov.nih.nci.coppa.po.Organization leadOrganization,gov.nih.nci.coppa.po.Person studySiteInvestigator,gov.nih.nci.coppa.services.pa.StudySite leadOrganizationStudySite,gov.nih.nci.coppa.po.Organization studySiteOrganization,gov.nih.nci.coppa.services.pa.StudySite studySite,gov.nih.nci.coppa.services.pa.StudySite nctIdentifier,gov.nih.nci.coppa.po.Organization summary4Organization,gov.nih.nci.coppa.services.pa.StudyResourcing summary4StudyResourcing) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          checkForRegistryUserExistence();
          StudyProtocolDTO studyProtocolDTO = InterventionalStudyProtocolTransformer.INSTANCE.toDto(studyProtocol);
          StudySiteAccrualStatusDTO studySiteAccrualStatusDTO =
              StudySiteAccrualStatusTransformer.INSTANCE.toDto(studySiteAccrualStatus);

          List<DocumentDTO> documentDTOs = DocumentTransformer.INSTANCE.convert(documents);
          OrganizationDTO leadOrganizationDTO = OrganizationTransformer.INSTANCE.toDto(leadOrganization);
          PersonDTO studySiteInvestigatorDTO = PersonTransformer.INSTANCE.toDto(studySiteInvestigator);
          StudySiteDTO leadOrganizationStudySiteDTO = StudySiteTransformer.INSTANCE.toDto(leadOrganizationStudySite);
          OrganizationDTO studySiteOrganizationDTO = OrganizationTransformer.INSTANCE.toDto(studySiteOrganization);
          StudySiteDTO studySiteDTO = StudySiteTransformer.INSTANCE.toDto(studySite);
          StudySiteDTO nctIdentifierDTO = StudySiteTransformer.INSTANCE.toDto(nctIdentifier);
          OrganizationDTO summary4organizationDTO = OrganizationTransformer.INSTANCE.toDto(summary4Organization);
          StudyResourcingDTO summary4studyResourcingDTO =
                  StudyResourcingTransformer.INSTANCE.toDto(summary4StudyResourcing);
          Bl isBatch = new Bl();
          isBatch.setValue(Boolean.FALSE);
          defaultingUserLoggedIn(studyProtocolDTO);

          Ii ii = trialRegService.createAbbreviatedInterventionalStudyProtocol(studyProtocolDTO, studySiteAccrualStatusDTO,
                          documentDTOs, leadOrganizationDTO, studySiteInvestigatorDTO, leadOrganizationStudySiteDTO,
                          studySiteOrganizationDTO, studySiteDTO, nctIdentifierDTO, summary4organizationDTO,
                          summary4studyResourcingDTO, isBatch, studyProtocolDTO.getRecordOwners());
          
          return IdTransformer.INSTANCE.toXml(ii);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.pa.Ed getCtGovXml(gov.nih.nci.iso21090.extensions.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {

     try {          
          Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
          return EdTransformer.INSTANCE.toXml(EDTransformer.INSTANCE.toXml(trialRegService.getCTGovXml(iiDto)));
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

    /**
     * @param interventionalStudyProtocol
     * @param leadOrganization
     * @param leadOrganizationIdentifier
     * @param nctIdentifier
     * @param summary4TypeCode
     * @param summary4Organization
     * @param participatingSites
     * @param participatingSiteAccrualStatuses
     * @param documents
     * @throws RemoteException
     * @throws gov.nih.nci.coppa.services.pa.faults.PAFault
     */
    public void updateAbbreviatedInterventionalStudyProtocol(
            gov.nih.nci.coppa.services.pa.InterventionalStudyProtocol studyProtocol,
            gov.nih.nci.coppa.po.Organization leadOrganization,
            gov.nih.nci.iso21090.extensions.St leadOrganizationIdentifier,
            gov.nih.nci.iso21090.extensions.St nctIdentifier,
            gov.nih.nci.iso21090.extensions.Cd summary4TypeCode,
            gov.nih.nci.coppa.po.Organization summary4Organization,
            gov.nih.nci.coppa.services.pa.StudySite[] participatingSites,
            gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus[] participatingSiteAccrualStatuses,
            gov.nih.nci.coppa.services.pa.Document[] documents)
            throws RemoteException,
            gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            checkForRegistryUserExistence();
            StudyProtocolDTO studyProtocolDTO = InterventionalStudyProtocolTransformer.INSTANCE
                    .toDto(studyProtocol);
            defaultingUserLoggedIn(studyProtocolDTO);
            OrganizationDTO leadOrganizationDTO = OrganizationTransformer.INSTANCE
                    .toDto(leadOrganization);
            St leadOrganizationIdentifierDTO = STTransformer.INSTANCE
                    .toDto(leadOrganizationIdentifier);
            St nctIdentifierDTO = STTransformer.INSTANCE.toDto(nctIdentifier);
            Cd summary4TypeCodeDTO = CDTransformer.INSTANCE
                    .toDto(summary4TypeCode);
            OrganizationDTO summary4OrganizationDTO = OrganizationTransformer.INSTANCE
                    .toDto(summary4Organization);
            List<StudySiteDTO> participatingSitesDTOs = StudySiteTransformer.INSTANCE
                    .convert(participatingSites);
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs = StudySiteAccrualStatusTransformer.INSTANCE
                    .convert(participatingSiteAccrualStatuses);
            List<DocumentDTO> documentDTOs = DocumentTransformer.INSTANCE
                    .convert(documents);
            propTrialMgmtService.update(studyProtocolDTO, leadOrganizationDTO,
                    summary4OrganizationDTO, leadOrganizationIdentifierDTO,
                    nctIdentifierDTO, summary4TypeCodeDTO, documentDTOs,
                    participatingSitesDTOs, studySiteAccrualStatusDTOs);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

}
