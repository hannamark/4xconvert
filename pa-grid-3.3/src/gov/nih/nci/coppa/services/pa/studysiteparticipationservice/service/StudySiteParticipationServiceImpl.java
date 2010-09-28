package gov.nih.nci.coppa.services.pa.studysiteparticipationservice.service;

import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeParticipatingSiteEjb;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.management.transformers.ClinicalResearchStaffParticipationSiteManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.management.transformers.HealthCareFacilityParticipationSiteManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.management.transformers.HealthCareProviderParticipationSiteManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.management.transformers.OrganizationParticipationSiteManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.management.transformers.OrganizationalContactParticipationSiteManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.management.transformers.PersonParticipationSiteManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.management.transformers.StudySiteAccrualStatusParticipationSiteManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.management.transformers.StudySiteParticipationSiteManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.management.transformers.StudySiteContactParticipationSiteManagementTransformer;
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
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.rmi.RemoteException;

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

  public gov.nih.nci.iso21090.extensions.Bl isParticipatingSite(gov.nih.nci.iso21090.extensions.Id studyProtocolId,gov.nih.nci.iso21090.extensions.Id organizationId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii orgIi = IITransformer.INSTANCE.toDto(organizationId);
            Ii studyProtocolIi = IITransformer.INSTANCE.toDto(studyProtocolId);
            gov.nih.nci.iso21090.Bl isPart = service.isParticipatingSite(studyProtocolIi, orgIi);
            gov.nih.nci.iso21090.extensions.Bl result = new gov.nih.nci.iso21090.extensions.Bl();
            result.setValue(BlConverter.convertToBoolean(isPart));
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    private void addStudySiteInvestigators(
            Ii studySiteIi,
            gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySite studySite)
            throws DtoTransformException, PAException {
        for (StudySiteContact studySiteContact : studySite.getStudySiteContacts()) {
            StudySiteContactDTO studySiteContactDTO = StudySiteContactParticipationSiteManagementTransformer.INSTANCE
                    .toDto(studySiteContact);
            PersonRole personRole = (PersonRole) (studySiteContact.getPersonRole().getContent().get(0));
            PersonDTO personDTO = PersonParticipationSiteManagementTransformer.INSTANCE.toDto(personRole
                    .getPlayer());
            String roleCode = CdConverter.convertCdToString(studySiteContactDTO.getRoleCode());
            boolean isPrimary = BlConverter.convertToBool(studySiteContactDTO.getPrimaryIndicator());
            DSet<Tel> telecom = DSETTELTransformer.INSTANCE.toDto(studySiteContact.getTelecomAddresses());
            
            if (personRole instanceof ClinicalResearchStaff) {
                ClinicalResearchStaffDTO crsDTO = ClinicalResearchStaffParticipationSiteManagementTransformer.INSTANCE
                        .toDto((ClinicalResearchStaff) personRole);
                service.addStudySiteInvestigator(studySiteIi, crsDTO, null, personDTO, roleCode);
                if ( isPrimary ) {
                    service.addStudySitePrimaryContact(studySiteIi, crsDTO, null, personDTO, telecom);
                }
            } else if (personRole instanceof HealthCareProvider) {
                HealthCareProviderDTO hcpDTO = HealthCareProviderParticipationSiteManagementTransformer.INSTANCE
                        .toDto((HealthCareProvider) personRole);
                service.addStudySiteInvestigator(studySiteIi, null, hcpDTO, personDTO, roleCode);
                if ( isPrimary ) {
                    service.addStudySitePrimaryContact(studySiteIi, null, hcpDTO, personDTO, telecom);
                }
            } else if (personRole instanceof OrganizationalContact) {
                OrganizationalContactDTO orgContactDTO = OrganizationalContactParticipationSiteManagementTransformer.INSTANCE
                        .toDto((OrganizationalContact) personRole);
                service.addStudySiteGenericContact(studySiteIi, orgContactDTO, isPrimary, telecom);
            } else {
                throw new PAException("Invalid PersonRole for StudySiteContact: " + studySiteContact);
            }
        }
    }

  public gov.nih.nci.iso21090.extensions.Id createParticipatingSite(gov.nih.nci.iso21090.extensions.Id studyProtocolId,gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySite studySite) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          Ii studyProtocolIi = IITransformer.INSTANCE.toDto(studyProtocolId);
          StudySiteDTO studySiteDTO = StudySiteParticipationSiteManagementTransformer.INSTANCE
                  .toDto(studySite);
          studySiteDTO.setStudyProtocolIdentifier(studyProtocolIi);
          StudySiteAccrualStatusDTO studySiteAccrualStatusDTO = StudySiteAccrualStatusParticipationSiteManagementTransformer.INSTANCE
                  .toDto(studySite.getAccrualStatus());
          HealthCareFacilityDTO hcfDTO = HealthCareFacilityParticipationSiteManagementTransformer.INSTANCE
                  .toDto(studySite.getOrganizationRole());
          OrganizationDTO hcfOrganizationDTO = OrganizationParticipationSiteManagementTransformer.INSTANCE
                  .toDto(studySite.getOrganizationRole().getPlayer());

          Ii studySiteIi = null;
          if (hcfDTO != null) {
              studySiteIi = service.createStudySiteParticipant(studySiteDTO, studySiteAccrualStatusDTO, DSetConverter
                      .convertToIi(hcfDTO.getIdentifier()));
          } else {
              studySiteIi = service.createStudySiteParticipant(studySiteDTO, studySiteAccrualStatusDTO,
                      hcfOrganizationDTO, hcfDTO);
          }

          addStudySiteInvestigators(studySiteIi, studySite);
          return IdTransformer.INSTANCE.toXml(studySiteIi);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public void updateParticipatingSite(gov.nih.nci.iso21090.extensions.Id studySiteId,gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySite studySite) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          StudySiteDTO studySiteDTO = StudySiteParticipationSiteManagementTransformer.INSTANCE
                  .toDto(studySite);
          studySiteDTO.setIdentifier(IITransformer.INSTANCE.toDto(studySiteId));
          StudySiteAccrualStatusDTO studySiteAccrualStatusDTO = StudySiteAccrualStatusParticipationSiteManagementTransformer.INSTANCE
                  .toDto(studySite.getAccrualStatus());

          service.updateStudySiteParticipant(studySiteDTO, studySiteAccrualStatusDTO);
          addStudySiteInvestigators(studySiteDTO.getIdentifier(), studySite);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

}
