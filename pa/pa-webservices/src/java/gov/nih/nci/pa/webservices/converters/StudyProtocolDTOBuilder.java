/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.InterventionalTrialDesign;
import gov.nih.nci.pa.webservices.types.NonInterventionalTrialDesign;
import gov.nih.nci.pa.webservices.types.PrimaryPurpose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author dkrylov
 * 
 */
public final class StudyProtocolDTOBuilder {

    /**
     * @param reg
     *            CompleteTrialRegistration
     * @return StudyProtocolDTO
     */
    public StudyProtocolDTO build(CompleteTrialRegistration reg) {
        StudyProtocolDTO dto = instantitateStudyProtocolDTO(reg);

        if (StringUtils.isNotEmpty(reg.getTitle())) {
            dto.setOfficialTitle(StConverter.convertToSt(reg.getTitle()));
        }
        dto.setNciGrant(BlConverter.convertToBl(reg.isFundedByNciGrant()));
        dto.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));

        convertPhase(reg, dto);
        convertPrimaryPurpose(reg, dto);
        convertTrialDates(reg, dto);

        dto.setStudyProtocolType(StConverter
                .convertToSt(dto instanceof InterventionalStudyProtocolDTO ? PAConstants.INTERVENTIONAL
                        : PAConstants.NON_INTERVENTIONAL));
        dto.setProgramCodeText(StConverter.convertToSt(reg.getProgramCode()));
        dto.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(reg
                .isClinicalTrialsDotGovXmlRequired()));

        convertRegulatoryInfo(reg, dto);
        convertOtherIdentifiers(reg, dto);

        dto.setAccrualDiseaseCodeSystem(StConverter.convertToSt(reg
                .getAccrualDiseaseTerminology().value()));

        convertStudyDesignSpecifics(reg, dto);
        dto.setStudySource(CdConverter
                .convertToCd(StudySourceCode.REST_SERVICE));
        setUser(dto);
        return dto;
    }

    /**
     * @param reg
     * @param dto
     */
    private void convertOtherIdentifiers(CompleteTrialRegistration reg,
            StudyProtocolDTO dto) {
        if (CollectionUtils.isNotEmpty(reg.getOtherTrialID())) {
            List<Ii> iis = new ArrayList<Ii>();
            for (String otherID : reg.getOtherTrialID()) {
                iis.add(IiConverter.convertToOtherIdentifierIi(otherID));
            }
            dto.setSecondaryIdentifiers(DSetConverter
                    .convertIiSetToDset(new HashSet<Ii>(iis)));
        }
    }

    /**
     * @param reg
     * @param dto
     */
    private void convertRegulatoryInfo(CompleteTrialRegistration reg,
            StudyProtocolDTO dto) {
        if (reg.getRegulatoryInformation() != null) {
            dto.setFdaRegulatedIndicator(BlConverter.convertToBl(reg
                    .getRegulatoryInformation().isFdaRegulated()));
            dto.setSection801Indicator(BlConverter.convertToBl(reg
                    .getRegulatoryInformation().isSection801()));
            dto.setDelayedpostingIndicator(BlConverter.convertToBl(reg
                    .getRegulatoryInformation().isDelayedPosting()));
            dto.setDataMonitoringCommitteeAppointedIndicator(BlConverter
                    .convertToBl(reg.getRegulatoryInformation()
                            .isDataMonitoringCommitteeAppointed()));
        }
    }

    /**
     * @param reg
     * @param dto
     */
    private void convertTrialDates(CompleteTrialRegistration reg,
            StudyProtocolDTO dto) {
        dto.setStartDate(TsConverter.convertToTs(reg.getTrialStartDate()
                .getValue().toGregorianCalendar().getTime()));
        dto.setStartDateTypeCode(CdConverter
                .convertToCd(ActualAnticipatedTypeCode.getByCode(reg
                        .getTrialStartDate().getType())));
        dto.setPrimaryCompletionDate(TsConverter.convertToTs((reg
                .getPrimaryCompletionDate().getValue().toGregorianCalendar()
                .getTime())));
        dto.setPrimaryCompletionDateTypeCode(CdConverter
                .convertToCd(ActualAnticipatedTypeCode.getByCode(reg
                        .getPrimaryCompletionDate().getType())));
        if (reg.getCompletionDate() != null) {
            dto.setCompletionDate(TsConverter.convertToTs(reg
                    .getCompletionDate().getValue().toGregorianCalendar()
                    .getTime()));
            dto.setCompletionDateTypeCode(CdConverter
                    .convertToCd(ActualAnticipatedTypeCode.getByCode(reg
                            .getCompletionDate().getType())));
        }
    }

    /**
     * @param reg
     * @param dto
     */
    private void convertPhase(CompleteTrialRegistration reg,
            StudyProtocolDTO dto) {
        dto.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(reg
                .getPhase())));
        dto.setPhaseAdditionalQualifierCode(CdConverter.convertToCd(PhaseAdditionalQualifierCode
                .getByCode(Boolean.TRUE.equals(reg.getPilot()) ? "Pilot" : "")));
    }

    private void convertStudyDesignSpecifics(CompleteTrialRegistration reg,
            StudyProtocolDTO dto) {
        if (reg.getInterventionalDesign() != null) {
            convertInterventionalDesign(reg.getInterventionalDesign(),
                    (InterventionalStudyProtocolDTO) dto);
        } else if (reg.getNonInterventionalDesign() != null) {
            convertNonInterventionalDesign(reg.getNonInterventionalDesign(),
                    (NonInterventionalStudyProtocolDTO) dto);
        }
    }

    private void convertNonInterventionalDesign(
            NonInterventionalTrialDesign design,
            NonInterventionalStudyProtocolDTO dto) {
        dto.setStudyModelCode(CdConverter.convertStringToCd(design
                .getStudyModelCode().value()));
        dto.setStudyModelOtherText(StConverter.convertToSt(design
                .getStudyModelCodeOtherDescription()));
        dto.setTimePerspectiveCode(CdConverter.convertStringToCd(design
                .getTimePerspectiveCode().value()));
        dto.setTimePerspectiveOtherText(StConverter.convertToSt(design
                .getTimePerspectiveCodeOtherDescription()));
        dto.setStudySubtypeCode(CdConverter.convertStringToCd(design
                .getTrialType().value()));

    }

    private void convertInterventionalDesign(InterventionalTrialDesign design,
            InterventionalStudyProtocolDTO dto) {
        if (design.getSecondaryPurpose() != null) {
            dto.setSecondaryPurposes(DSetConverter.convertListStToDSet(Arrays
                    .asList(design.getSecondaryPurpose().value())));
            dto.setSecondaryPurposeOtherText(StConverter.convertToSt(design
                    .getSecondaryPurposeOtherDescription()));
        }
    }

    private void convertPrimaryPurpose(CompleteTrialRegistration reg,
            StudyProtocolDTO dto) {
        dto.setPrimaryPurposeCode(CdConverter.convertStringToCd((reg
                .getPrimaryPurpose().value())));
        if (reg.getPrimaryPurpose() == PrimaryPurpose.OTHER) {
            dto.setPrimaryPurposeOtherText(StConverter.convertToSt(reg
                    .getPrimaryPurposeOtherDescription()));
            dto.setPrimaryPurposeAdditionalQualifierCode(CdConverter
                    .convertToCd(PrimaryPurposeAdditionalQualifierCode.OTHER));
        }

    }

    private StudyProtocolDTO instantitateStudyProtocolDTO(
            CompleteTrialRegistration reg) {
        return reg.getInterventionalDesign() != null ? new InterventionalStudyProtocolDTO()
                : new NonInterventionalStudyProtocolDTO();
    }

    /**
     * @param dto
     */
    private void setUser(StudyProtocolDTO dto) {
        String identity = UsernameHolder.getUser();
        St loggedInUser = new St();
        loggedInUser.setValue(identity);
        dto.setUserLastCreated(loggedInUser);
    }

}
