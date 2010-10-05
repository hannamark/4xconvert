package gov.nih.nci.coppa.services.pa.studysiteparticipationservice.service;

import gov.nih.nci.coppa.services.pa.grid.GenericStudyPaGridServiceImpl;
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
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.ClinicalResearchStaff;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.HealthCareProvider;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.OrganizationalContact;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.PersonRole;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySiteContact;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.HealthCareFacility;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudyProtocol;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySite;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySiteAccrualStatus;
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
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.iso._21090.DSETII;
import org.iso._21090.II;

/**
 * Implementation of the StudySiteParticipationService for adding, updating participating sites as well as finding if
 * those sites exist based on NCI study protocol ids and ctep Organization ids.
 * 
 * @author mshestopalov
 */
public class StudySiteParticipationServiceImpl extends StudySiteParticipationServiceImplBase {

    private static final Logger logger = LogManager.getLogger(StudySiteParticipationServiceImpl.class);
    private final InvokeParticipatingSiteEjb service = new InvokeParticipatingSiteEjb();
    private final GenericStudyPaGridServiceImpl<StudySiteDTO, gov.nih.nci.coppa.services.pa.StudySite> impl =
        new GenericStudyPaGridServiceImpl<StudySiteDTO, gov.nih.nci.coppa.services.pa.StudySite>(gov.nih.nci.coppa.services.pa.StudySite.class, StudySiteDTO.class);

    public StudySiteParticipationServiceImpl() throws RemoteException {
        super();
    }

    public gov.nih.nci.iso21090.extensions.Bl isParticipatingSite(gov.nih.nci.iso21090.extensions.Id studyProtocolId,
            gov.nih.nci.iso21090.extensions.Id hcfId) throws RemoteException,
            gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii hcfIi = IITransformer.INSTANCE.toDto(hcfId);
            Ii studyProtocolIi = IITransformer.INSTANCE.toDto(studyProtocolId);
            gov.nih.nci.iso21090.Bl isPart = service.isParticipatingSite(studyProtocolIi, hcfIi);
            gov.nih.nci.iso21090.extensions.Bl result = new gov.nih.nci.iso21090.extensions.Bl();
            result.setValue(BlConverter.convertToBoolean(isPart));
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    private void addStudySiteInvestigators(Ii studySiteIi,
            gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySite studySite)
            throws DtoTransformException, PAException {
        for (StudySiteContact studySiteContact : studySite.getStudySiteContacts()) {
            StudySiteContactDTO studySiteContactDTO = StudySiteContactManagementTransformer.INSTANCE
                    .toDto(studySiteContact);
            PersonRole personRole = (PersonRole) (studySiteContact.getPersonRole().getContent().get(0));
            PersonDTO personDTO = PersonManagementTransformer.INSTANCE.toDto(personRole.getPlayer());
            String roleCode = CdConverter.convertCdToString(studySiteContactDTO.getRoleCode());
            boolean isPrimary = BlConverter.convertToBool(studySiteContactDTO.getPrimaryIndicator());
            DSet<Tel> telecom = DSETTELTransformer.INSTANCE.toDto(studySiteContact.getTelecomAddresses());

            if (personRole instanceof ClinicalResearchStaff) {
                ClinicalResearchStaffDTO crsDTO = ClinicalResearchStaffManagementTransformer.INSTANCE
                        .toDto((ClinicalResearchStaff) personRole);
                service.addStudySiteInvestigator(studySiteIi, crsDTO, null, personDTO, roleCode);
                if (isPrimary) {
                    service.addStudySitePrimaryContact(studySiteIi, crsDTO, null, personDTO, telecom);
                }
            } else if (personRole instanceof HealthCareProvider) {
                HealthCareProviderDTO hcpDTO = HealthCareProviderManagementTransformer.INSTANCE
                        .toDto((HealthCareProvider) personRole);
                service.addStudySiteInvestigator(studySiteIi, null, hcpDTO, personDTO, roleCode);
                if (isPrimary) {
                    service.addStudySitePrimaryContact(studySiteIi, null, hcpDTO, personDTO, telecom);
                }
            } else if (personRole instanceof OrganizationalContact) {
                OrganizationalContactDTO orgContactDTO = OrganizationalContactManagementTransformer.INSTANCE
                        .toDto((OrganizationalContact) personRole);
                service.addStudySiteGenericContact(studySiteIi, orgContactDTO, isPrimary, telecom);
            } else {
                throw new PAException("Invalid PersonRole for StudySiteContact: " + studySiteContact);
            }
        }
    }

    public StudySite createParticipatingSite(
            gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySite studySite)
            throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            StudySiteDTO studySiteDTO = StudySiteManagementTransformer.INSTANCE.toDto(studySite);
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO = StudySiteAccrualStatusManagementTransformer.INSTANCE
                    .toDto(studySite.getAccrualStatus());
            HealthCareFacilityDTO hcfDTO = HealthCareFacilityManagementTransformer.INSTANCE
                    .toDto(studySite.getOrganizationRole());
            OrganizationDTO hcfOrganizationDTO = OrganizationManagementTransformer.INSTANCE
                    .toDto(studySite.getOrganizationRole().getPlayer());

            Ii studySiteIi = null;
            if (PAUtil.isIiNotNull(DSetConverter.convertToIi(hcfDTO.getIdentifier()))) {
                studySiteIi = service.createStudySiteParticipant(studySiteDTO, studySiteAccrualStatusDTO, DSetConverter
                        .convertToIi(hcfDTO.getIdentifier()));
            } else {
                studySiteIi = service.createStudySiteParticipant(studySiteDTO, studySiteAccrualStatusDTO,
                        hcfOrganizationDTO, hcfDTO);
            }

            if ( !studySite.getStudySiteContacts().isEmpty() ) {
                addStudySiteInvestigators(studySiteIi, studySite);
            }
            return getStudySite(IITransformer.INSTANCE.toXml(studySiteIi));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    public StudySite updateParticipatingSite(gov.nih.nci.iso21090.extensions.Id studySiteId,
            gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySite studySite)
            throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            StudySiteDTO studySiteDTO = StudySiteManagementTransformer.INSTANCE.toDto(studySite);
            studySiteDTO.setIdentifier(IITransformer.INSTANCE.toDto(studySiteId));
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO = StudySiteAccrualStatusManagementTransformer.INSTANCE
                    .toDto(studySite.getAccrualStatus());

            service.updateStudySiteParticipant(studySiteDTO, studySiteAccrualStatusDTO);
            if ( !studySite.getStudySiteContacts().isEmpty() ) {
                addStudySiteInvestigators(studySiteDTO.getIdentifier(), studySite);
            }
            return getStudySite(studySiteId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * @param studySiteIi
     * @return
     * @throws PAException
     * @throws DtoTransformException
     * @throws RemoteException 
     */
    private StudySite getStudySite(II studySiteIi) throws PAException, DtoTransformException, RemoteException {
        gov.nih.nci.coppa.services.pa.StudySite studySite = impl.get(IdTransformer.INSTANCE
                .toXml(IITransformer.INSTANCE.toDto(studySiteIi)));
        StudySite result = new StudySite();
        
        result.setAccrualDateRange(studySite.getAccrualDateRange());
        StudySiteAccrualStatus accrualStatus = new StudySiteAccrualStatus();
        accrualStatus.setStatusCode(studySite.getStatusCode());
        result.setAccrualStatus(accrualStatus);
        if (PAUtil.isIiNotNull(IITransformer.INSTANCE.toDto(studySite.getHealthcareFacility()))) {
            HealthCareFacility hcf = new HealthCareFacility();
            DSETII dSetIi = new DSETII();
            dSetIi.getItem().add(studySite.getHealthcareFacility());
            hcf.setIdentifier(dSetIi);
            result.setOrganizationRole(hcf);
        }
        result.setIdentifier(studySite.getIdentifier());
        result.setLocalStudyProtocolIdentifier(studySite.getLocalStudyProtocolIdentifier());
        result.setProgramCodeText(studySite.getProgramCodeText());
        result.setReviewBoardApprovalDate(studySite.getReviewBoardApprovalDate());
        result.setReviewBoardApprovalNumber(studySite.getReviewBoardApprovalNumber());
        result.setReviewBoardApprovalStatusCode(studySite.getReviewBoardApprovalStatusCode());
        result.setReviewBoardOrganizationalAffiliation(studySite.getReviewBoardOrganizationalAffiliation());
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setIdentifier(studySite.getStudyProtocolIdentifier());
        result.setStudyProtocol(studyProtocol);
        result.setTargetAccrualNumber(studySite.getTargetAccrualNumber());
        return result;
    }
}
