/**
 * 
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.exception.DuplicateParticipatingSiteException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ValidationAware;

/**
 * @author Denis G. Krylov
 * 
 */
// CHECKSTYLE:OFF
public final class AddUpdateSiteHelper {

    private final PAServiceUtils paServiceUtil;

    private final SubmittedOrganizationDTO siteDTO;

    private final ParticipatingSiteServiceLocal participatingSiteService;

    private final StudyProtocolServiceLocal studyProtocolService;

    private final RegistryUser registryUser;

    private final RegistryUserServiceLocal registryUserService;

    private final StudySiteContactServiceLocal studySiteContactService;

    private final ValidationAware validationAware;

    private final String studyProtocolId;
    
    private final String orgPoId;

    /**
     * @param paServiceUtil
     *            paServiceUtil
     * @param siteDTO
     *            siteDTO
     * @param participatingSiteService
     *            participatingSiteService
     * @param studyProtocolService
     *            studyProtocolService
     * @param registryUser
     *            registryUser
     * @param registryUserService
     *            registryUserService
     * @param studySiteContactService
     *            studySiteContactService
     * @param validationAware
     *            validationAware
     */
    public AddUpdateSiteHelper( // NOPMD           
            PAServiceUtils paServiceUtil, SubmittedOrganizationDTO siteDTO,
            ParticipatingSiteServiceLocal participatingSiteService,
            StudyProtocolServiceLocal studyProtocolService,
            RegistryUser registryUser,
            RegistryUserServiceLocal registryUserService,
            StudySiteContactServiceLocal studySiteContactService,
            ValidationAware validationAware, String studyProtocolId, String orgPoId) {
        this.paServiceUtil = paServiceUtil;
        this.siteDTO = siteDTO;
        this.participatingSiteService = participatingSiteService;
        this.studyProtocolService = studyProtocolService;
        this.registryUser = registryUser;
        this.registryUserService = registryUserService;
        this.studySiteContactService = studySiteContactService;
        this.validationAware = validationAware;
        this.studyProtocolId = studyProtocolId;
        this.orgPoId = orgPoId;
    }

    public void addSite() throws PAException {
        Ii poHealthFacilID = getHealthcareFacilityID();
        StudySiteDTO studySiteDTO = getStudySite();
        StudySiteAccrualStatusDTO accrualStatusDTO = getStudySiteAccrualStatus();
        Ii studySiteID = null; // IiConverter.convertToStudySiteIi(studySiteIdentifier);

        try {
            studySiteID = participatingSiteService.createStudySiteParticipant(
                    studySiteDTO, accrualStatusDTO, siteDTO.getStatusHistory(), poHealthFacilID)
                    .getIdentifier();
        } catch (DuplicateParticipatingSiteException e) {
            validationAware.addFieldError("organizationName", e.getMessage());
            throw new PAException(e);
        }

        addInvestigator(studySiteID);
        createSiteRecordOwnership(studySiteID, registryUser);
    }
    
    public void updateSite() throws PAException {
        StudySiteDTO studySiteDTO = getStudySite();
        StudySiteAccrualStatusDTO accrualStatusDTO = getStudySiteAccrualStatus();

        Ii studySiteID = participatingSiteService.updateStudySiteParticipantWithStatusHistory(
                studySiteDTO, accrualStatusDTO, siteDTO.getStatusHistory())
                .getIdentifier();
        clearInvestigatorsForPropTrialSite(studySiteID);
        addInvestigator(studySiteID);
    }
    
    private void clearInvestigatorsForPropTrialSite(Ii ssIi) throws PAException {
        List<StudySiteContactDTO> ssContDtoList = studySiteContactService
                .getByStudySite(ssIi);
        for (StudySiteContactDTO item : ssContDtoList) {
            studySiteContactService.delete(item.getIdentifier());
        }
    }

    /**
     * @return
     * @throws PAException
     */
    private Ii getHealthcareFacilityID() throws PAException {
        return paServiceUtil.getPoHcfIi(orgPoId);        
    }

    private void createSiteRecordOwnership(Ii ssIi, RegistryUser registryUser)
            throws PAException {
        registryUserService.assignSiteOwnership(registryUser.getId(),
                IiConverter.convertToLong(ssIi));
    }

    /**
     * @param studySiteID
     * @throws PAException
     */
    private void addInvestigator(Ii studySiteID) throws PAException {
        Ii investigatorIi = IiConverter.convertToPoPersonIi(siteDTO
                .getInvestigatorId().toString());
        addInvestigator(studySiteID, investigatorIi,
                StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode(),
                participatingSiteService.getParticipatingSite(studySiteID)
                        .getSiteOrgPoId());
    }

    @SuppressWarnings("deprecation")
    private StudySiteDTO getStudySite() {
        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO
                .setIdentifier(StringUtils.isNotBlank(siteDTO.getId()) ? IiConverter
                        .convertToStudySiteIi(Long.parseLong(siteDTO.getId()))
                        : IiConverter.convertToIi((Long) null));
        studySiteDTO.setStatusCode(CdConverter
                .convertToCd(FunctionalRoleStatusCode.PENDING));
        studySiteDTO.setStatusDateRange(IvlConverter.convertTs().convertToIvl(
                new Timestamp(new Date().getTime()), null));
        studySiteDTO
                .setStudyProtocolIdentifier(IiConverter
                        .convertToStudyProtocolIi(Long
                                .parseLong(studyProtocolId)));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter
                .convertToSt(siteDTO.getSiteLocalTrialIdentifier()));
        studySiteDTO.setProgramCodeText(StConverter.convertToSt(siteDTO
                .getProgramCode()));
        if (StringUtils.isNotEmpty(siteDTO.getDateClosedforAccrual())
                && StringUtils.isNotEmpty(siteDTO.getDateOpenedforAccrual())) {
            studySiteDTO.setAccrualDateRange(IvlConverter.convertTs()
                    .convertToIvl(siteDTO.getDateOpenedforAccrual(),
                            siteDTO.getDateClosedforAccrual()));
        }
        if (StringUtils.isNotEmpty(siteDTO.getDateOpenedforAccrual())
                && StringUtils.isEmpty(siteDTO.getDateClosedforAccrual())) {
            studySiteDTO.setAccrualDateRange(IvlConverter.convertTs()
                    .convertToIvl(siteDTO.getDateOpenedforAccrual(), null));
        }
        return studySiteDTO;
    }

    @SuppressWarnings("deprecation")
    private StudySiteAccrualStatusDTO getStudySiteAccrualStatus() {
        StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
        ssas.setIdentifier(IiConverter.convertToIi((Long) null));
        ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode
                .getByCode(siteDTO.getRecruitmentStatus())));
        ssas.setStatusDate(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp(siteDTO.getRecruitmentStatusDate())));
        return ssas;
    }
    
    private void addInvestigator(Ii ssIi, Ii investigatorIi, String role,
            String poOrgId) throws PAException {
        ClinicalResearchStaffDTO crsDTO = paServiceUtil.getCrsDTO(
                investigatorIi, poOrgId);
        StudyProtocolDTO spDTO = studyProtocolService
                .getStudyProtocol(IiConverter.convertToStudyProtocolIi(Long
                        .parseLong(studyProtocolId)));
        HealthCareProviderDTO hcpDTO = paServiceUtil.getHcpDTO(spDTO
                .getStudyProtocolType().getValue(), investigatorIi, poOrgId);
        participatingSiteService.addStudySiteInvestigator(ssIi, crsDTO, hcpDTO,
                null, role);
    }

}
