package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IntConverter;

import java.util.ArrayList;
import java.util.List;


/**
 * Convert InterventionalStudyProtocol domain to DTO.
 *
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({"PMD.NPathComplexity" , "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" })
public class InterventionalStudyProtocolConverter extends
        StudyProtocolConverter {

    /**
     *
     * @param isp InterventionalStudyProtocol
     * @return InterventionalStudyProtocolDTO InterventionalStudyProtocolDTO
     */
    public static InterventionalStudyProtocolDTO convertFromDomainToDTO(InterventionalStudyProtocol isp) {
        InterventionalStudyProtocolDTO ispDTO = (InterventionalStudyProtocolDTO) 
                StudyProtocolConverter.convertFromDomainToDTO(
                        (StudyProtocol) isp, (StudyProtocolDTO) new InterventionalStudyProtocolDTO());
        ispDTO.setAllocationCode(CdConverter.convertToCd(isp.getAllocationCode()));
        ispDTO.setBlindingSchemaCode(CdConverter.convertToCd(isp.getBlindingSchemaCode()));
        ispDTO.setDesignConfigurationCode(CdConverter.convertToCd(isp.getDesignConfigurationCode()));
        ispDTO.setNumberOfInterventionGroups(IntConverter.convertToInt(isp.getNumberOfInterventionGroups()));
        ispDTO.setStudyClassificationCode(CdConverter.convertToCd(isp.getStudyClassificationCode()));
        // convert to dset
        List<Cd> cds = new ArrayList();
        if (isp.getBlindingRoleCodeCaregiver() != null) {
            cds.add(CdConverter.convertToCd(isp.getBlindingRoleCodeCaregiver()));
        }
        if (isp.getBlindingRoleCodeInvestigator() != null) {
            cds.add(CdConverter.convertToCd(isp.getBlindingRoleCodeInvestigator()));
        }
        if (isp.getBlindingRoleCodeOutcome() != null) {
            cds.add(CdConverter.convertToCd(isp.getBlindingRoleCodeOutcome()));
        }
        if (isp.getBlindingRoleCodeSubject() != null) {
            cds.add(CdConverter.convertToCd(isp.getBlindingRoleCodeSubject()));
        }
        ispDTO.setBlindedRoleCode(DSetConverter.convertCdListToDSet(cds));
        return ispDTO;
    }

    /**
     *
     * @param ispDTO InterventionalStudyProtocolDTO
     * @return InterventionalStudyProtocol InterventionalStudyProtocol
     */
    public static InterventionalStudyProtocol convertFromDTOToDomain(InterventionalStudyProtocolDTO ispDTO) {
        InterventionalStudyProtocol isp =  (InterventionalStudyProtocol) StudyProtocolConverter.convertFromDTOToDomain(
                    (StudyProtocolDTO) ispDTO , (StudyProtocol) new InterventionalStudyProtocol());
        if (ispDTO.getAllocationCode() != null) {
            isp.setAllocationCode(AllocationCode.getByCode(ispDTO.getAllocationCode().getCode()));
        }
        if (ispDTO.getBlindingSchemaCode() != null) {
            isp.setBlindingSchemaCode(BlindingSchemaCode.getByCode(ispDTO.getBlindingSchemaCode().getCode()));
        }
        if (ispDTO.getDesignConfigurationCode() != null) {
            isp.setDesignConfigurationCode(
                    DesignConfigurationCode.getByCode(ispDTO.getDesignConfigurationCode().getCode()));
        }
        isp.setNumberOfInterventionGroups(IntConverter.convertToInteger(ispDTO.getNumberOfInterventionGroups()));
        List<Cd> cds =  DSetConverter.convertDsetToCdList(ispDTO.getBlindedRoleCode());
        if (cds != null) {
            for (Cd cd : cds) {
                if (BlindingRoleCode.CAREGIVER.getCode().equals(cd.getCode())) {
                   isp.setBlindingRoleCodeCaregiver(BlindingRoleCode.CAREGIVER);
                   continue;
                }
                if (BlindingRoleCode.INVESTIGATOR.getCode().equals(cd.getCode())) {
                    isp.setBlindingRoleCodeInvestigator(BlindingRoleCode.INVESTIGATOR);
                    continue;
                }
                if (BlindingRoleCode.OUTCOMES_ASSESSOR.getCode().equals(cd.getCode())) {
                    isp.setBlindingRoleCodeOutcome(BlindingRoleCode.OUTCOMES_ASSESSOR);
                    continue;
                }
                if (BlindingRoleCode.SUBJECT.getCode().equals(cd.getCode())) {
                    isp.setBlindingRoleCodeSubject(BlindingRoleCode.SUBJECT);
                    continue;
                }
                
            }
        }
        if (ispDTO.getStudyClassificationCode() != null) {
            isp.setStudyClassificationCode(
                    StudyClassificationCode.getByCode(ispDTO.getStudyClassificationCode().getCode()));
        }

        return isp;
    }


}
