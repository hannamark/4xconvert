package gov.nih.nci.coppa.services.pa.studysiteparticipationservice.service;

import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeParticipatingSiteEjb;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.ClinicalResearchStaffManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.HealthCareFacilityManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.HealthCareProviderManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.OrganizationManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.OrganizationalContactManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.PersonManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.StudySiteAccrualStatusManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.StudySiteContactManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.StudySiteManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.view.StudySiteViewTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.ClinicalResearchStaff;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.HealthCareProvider;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.OrganizationalContact;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.PersonRole;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySiteContact;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTELTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Implementation of the StudySiteParticipationService for adding, updating participating sites as well as finding if
 * those sites exist based on NCI study protocol ids and ctep Organization ids.
 *
 * @author mshestopalov
 */
public class StudySiteParticipationServiceImpl extends StudySiteParticipationServiceImplBase {

    private static final Logger logger = LogManager.getLogger(StudySiteParticipationServiceImpl.class);
    private final InvokeParticipatingSiteEjb service = new InvokeParticipatingSiteEjb();

    public StudySiteParticipationServiceImpl() throws RemoteException {
        super();
    }

    private void addStudySiteInvestigators(Ii studySiteIi,
            gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySite studySite)
            throws DtoTransformException, PAException {
        for (StudySiteContact studySiteContact : studySite.getStudySiteContacts()) {
            StudySiteContactDTO studySiteContactDTO =
                StudySiteContactManagementTransformer.INSTANCE.toDto(studySiteContact);
            PersonRole personRole = (PersonRole) (studySiteContact.getPersonRole().getContent().get(0));
            PersonDTO personDTO = PersonManagementTransformer.INSTANCE.toDto(personRole.getPlayer());
            String roleCode = CdConverter.convertCdToString(studySiteContactDTO.getRoleCode());
            boolean isPrimary = BlConverter.convertToBool(studySiteContactDTO.getPrimaryIndicator());
            DSet<Tel> telecom = DSETTELTransformer.INSTANCE.toDto(studySiteContact.getTelecomAddresses());

            if (personRole instanceof ClinicalResearchStaff) {
                ClinicalResearchStaffDTO crsDTO =
                    ClinicalResearchStaffManagementTransformer.INSTANCE.toDto((ClinicalResearchStaff) personRole);
                service.addStudySiteInvestigator(studySiteIi, crsDTO, null, personDTO, roleCode);
                if (isPrimary) {
                    service.addStudySitePrimaryContact(studySiteIi, crsDTO, null, personDTO, telecom);
                }
            } else if (personRole instanceof HealthCareProvider) {
                HealthCareProviderDTO hcpDTO =
                    HealthCareProviderManagementTransformer.INSTANCE.toDto((HealthCareProvider) personRole);
                service.addStudySiteInvestigator(studySiteIi, null, hcpDTO, personDTO, roleCode);
                if (isPrimary) {
                    service.addStudySitePrimaryContact(studySiteIi, null, hcpDTO, personDTO, telecom);
                }
            } else if (personRole instanceof OrganizationalContact) {
                OrganizationalContactDTO orgContactDTO =
                    OrganizationalContactManagementTransformer.INSTANCE.toDto((OrganizationalContact) personRole);
                service.addStudySiteGenericContact(studySiteIi, orgContactDTO, isPrimary, telecom);
            } else {
                throw new PAException("Invalid PersonRole for StudySiteContact: " + studySiteContact);
            }
        }
    }

  public gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySite createParticipatingSite(gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySite studySite) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            StudySiteDTO studySiteDTO = StudySiteManagementTransformer.INSTANCE.toDto(studySite);
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO =
                StudySiteAccrualStatusManagementTransformer.INSTANCE.toDto(studySite.getAccrualStatus());
            HealthCareFacilityDTO hcfDTO =
                HealthCareFacilityManagementTransformer.INSTANCE.toDto(studySite.getOrganizationRole());
            OrganizationDTO hcfOrganizationDTO =
                OrganizationManagementTransformer.INSTANCE.toDto(studySite.getOrganizationRole().getPlayer());

            ParticipatingSiteDTO  participatingSite = null;
            if (PAUtil.isIiNotNull(DSetConverter.convertToIi(hcfDTO.getIdentifier()))) {
                participatingSite = service.createStudySiteParticipant(studySiteDTO, studySiteAccrualStatusDTO,
                        DSetConverter.convertToIi(hcfDTO.getIdentifier()));
            } else {
                participatingSite = service.createStudySiteParticipant(studySiteDTO, studySiteAccrualStatusDTO,
                        hcfOrganizationDTO, hcfDTO);
            }

            if (!studySite.getStudySiteContacts().isEmpty()) {
                addStudySiteInvestigators(participatingSite.getIdentifier(), studySite);
            }
            return StudySiteViewTransformer.INSTANCE.toXml(participatingSite);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySite updateParticipatingSite(gov.nih.nci.iso21090.extensions.Id studySiteId,gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySite studySite) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            StudySiteDTO studySiteDTO = StudySiteManagementTransformer.INSTANCE.toDto(studySite);
            studySiteDTO.setIdentifier(IITransformer.INSTANCE.toDto(studySiteId));
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO =
                StudySiteAccrualStatusManagementTransformer.INSTANCE.toDto(studySite.getAccrualStatus());

            ParticipatingSiteDTO participatingSite =
                service.updateStudySiteParticipant(studySiteDTO, studySiteAccrualStatusDTO);
            if (!studySite.getStudySiteContacts().isEmpty()) {
                addStudySiteInvestigators(studySiteDTO.getIdentifier(), studySite);
            }
            return StudySiteViewTransformer.INSTANCE.toXml(participatingSite);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

  public gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySite[] getParticipatingSitesByStudyProtocol(gov.nih.nci.iso21090.extensions.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          Ii ii = IITransformer.INSTANCE.toDto(studyProtocolId);
          List<ParticipatingSiteDTO> sites = service.getParticipatingSitesByStudyProtocol(ii);
          return StudySiteViewTransformer.INSTANCE.convert(sites);
      } catch(Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

}
