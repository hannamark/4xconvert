package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.enums.BiospecimenRetentionCode;
import gov.nih.nci.pa.enums.SamplingMethodCode;
import gov.nih.nci.pa.enums.StudyModelCode;
import gov.nih.nci.pa.enums.TimePerspectiveCode;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
/**
*
* @author Kalpana Guthikonda
* @since 10/23/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@SuppressWarnings({"PMD.NPathComplexity" , "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" })
public class ObservationalStudyProtocolConverter extends StudyProtocolConverter {
    
    /**
     * 
     * @param osp InterventionalStudyProtocol
     * @return InterventionalStudyProtocolDTO InterventionalStudyProtocolDTO
     */
    public static ObservationalStudyProtocolDTO convertFromDomainToDTO(ObservationalStudyProtocol osp) {
        ObservationalStudyProtocolDTO ospDTO = new ObservationalStudyProtocolDTO();
        ospDTO.setAssignedIdentifier(IiConverter.convertToIi(osp.getIdentifier()));
        ospDTO.setIdentifier(IiConverter.convertToIi(osp.getId()));
        ospDTO.setStudyModelCode(
                CdConverter.convertToCd(osp.getStudyModelCode()));
        ospDTO.setStudyModelOtherText(StConverter.convertToSt(osp.getStudyModelOtherText()));
        ospDTO.setTimePerspectiveCode(
                CdConverter.convertToCd(osp.getTimePerspectiveCode()));
        ospDTO.setTimePerspectiveOtherText(StConverter.convertToSt(osp.getTimePerspectiveOtherText()));
        ospDTO.setBiospecimenDescription(StConverter.convertToSt(osp.getBiospecimenDescription()));
        ospDTO.setBiospecimenRetentionCode(CdConverter.convertToCd(osp.getBiospecimenRetentionCode()));
        ospDTO.setNumberOfGroups(IntConverter.convertToInt(osp.getNumberOfGroups()));
        ospDTO.setSamplingMethodCode(CdConverter.convertToCd(osp.getSamplingMethodCode()));
        ospDTO.setMaximumTargetAccrualNumber(
                IntConverter.convertToInt(osp.getMaximumTargetAccrualNumber()));
        return ospDTO;
    }
    /**
     * 
     * @param ospDTO InterventionalStudyProtocolDTO 
     * @return InterventionalStudyProtocol InterventionalStudyProtocol
     */
    public static ObservationalStudyProtocol convertFromDTOToDomain(ObservationalStudyProtocolDTO ospDTO) {
        ObservationalStudyProtocol osp = new ObservationalStudyProtocol();
        if (ospDTO.getIdentifier() != null) {
            osp.setId(IiConverter.convertToLong(ospDTO.getIdentifier()));
        }
        if (ospDTO.getAssignedIdentifier() != null) {
            osp.setIdentifier(ospDTO.getAssignedIdentifier().getExtension());
        }
        if (ospDTO.getSamplingMethodCode() != null) {
            osp.setSamplingMethodCode(SamplingMethodCode.getByCode(ospDTO.getSamplingMethodCode().getCode()));
        }
        if (ospDTO.getStudyModelCode() != null) {
            osp.setStudyModelCode(StudyModelCode.getByCode(ospDTO.getStudyModelCode().getCode()));
        }
        if (ospDTO.getTimePerspectiveCode() != null) {
            osp.setTimePerspectiveCode(TimePerspectiveCode.getByCode(ospDTO.getTimePerspectiveCode().getCode()));
        }
        if (ospDTO.getBiospecimenRetentionCode() != null) {
            osp.setBiospecimenRetentionCode(BiospecimenRetentionCode.getByCode(
                    ospDTO.getBiospecimenRetentionCode().getCode()));
        }
        if (ospDTO.getMaximumTargetAccrualNumber() != null) {
            osp.setMaximumTargetAccrualNumber(ospDTO.getMaximumTargetAccrualNumber().getValue());
        } 
        if (ospDTO.getStudyModelOtherText() != null) {
            osp.setStudyModelOtherText(StConverter.convertToString(ospDTO.getStudyModelOtherText()));
        }
        if (ospDTO.getTimePerspectiveOtherText() != null) {
            osp.setTimePerspectiveOtherText(StConverter.convertToString(ospDTO.getTimePerspectiveOtherText()));
        }
        if (ospDTO.getBiospecimenDescription() != null) {
            osp.setBiospecimenDescription(StConverter.convertToString(ospDTO.getBiospecimenDescription()));
        }
        if (ospDTO.getNumberOfGroups() != null) {
            osp.setNumberOfGroups(ospDTO.getNumberOfGroups().getValue());
        }
        return osp;
    }
}
