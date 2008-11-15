package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.BiospecimenRetentionCode;
import gov.nih.nci.pa.enums.SamplingMethodCode;
import gov.nih.nci.pa.enums.StudyModelCode;
import gov.nih.nci.pa.enums.TimePerspectiveCode;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
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
     * @param osp ObservationalStudyProtocol
     * @return ObservationalStudyProtocolDTO ObservationalStudyProtocolDTO
     */
    public static ObservationalStudyProtocolDTO convertFromDomainToDTO(ObservationalStudyProtocol osp) {
        ObservationalStudyProtocolDTO ospDTO = (ObservationalStudyProtocolDTO) 
        StudyProtocolConverter.convertFromDomainToDTO(
                (StudyProtocol) osp, (StudyProtocolDTO) new ObservationalStudyProtocolDTO());

        ospDTO.setBiospecimenDescription(StConverter.convertToSt(osp.getBiospecimenDescription()));
        ospDTO.setBiospecimenRetentionCode(CdConverter.convertToCd(osp.getBiospecimenRetentionCode()));
        ospDTO.setNumberOfGroups(IntConverter.convertToInt(osp.getNumberOfGroups()));
        ospDTO.setSamplingMethodCode(CdConverter.convertToCd(osp.getSamplingMethodCode()));
        ospDTO.setStudyModelCode(CdConverter.convertToCd(osp.getStudyModelCode()));
        ospDTO.setStudyModelOtherText(StConverter.convertToSt(osp.getStudyModelOtherText()));
        ospDTO.setTimePerspectiveCode(CdConverter.convertToCd(osp.getTimePerspectiveCode()));
        ospDTO.setTimePerspectiveOtherText(StConverter.convertToSt(osp.getTimePerspectiveOtherText()));
        ospDTO.setStudyPopulationDescription(StConverter.convertToSt(osp.getStudyPopulationDescription()));
        return ospDTO;
    }
    /**
     * 
     * @param ospDTO ObservationalStudyProtocolDTO 
     * @return ObservationalStudyProtocol ObservationalStudyProtocol
     */
    public static ObservationalStudyProtocol convertFromDTOToDomain(ObservationalStudyProtocolDTO ospDTO) {
        ObservationalStudyProtocol osp =  (ObservationalStudyProtocol) StudyProtocolConverter.convertFromDTOToDomain(
                (StudyProtocolDTO) ospDTO , (StudyProtocol) new ObservationalStudyProtocol());

        osp.setBiospecimenDescription(StConverter.convertToString(ospDTO.getBiospecimenDescription()));
        if (ospDTO.getBiospecimenRetentionCode() != null) {
            osp.setBiospecimenRetentionCode(BiospecimenRetentionCode.getByCode(
                    ospDTO.getBiospecimenRetentionCode().getCode()));
        }
        if (ospDTO.getNumberOfGroups() != null) {
            osp.setNumberOfGroups(ospDTO.getNumberOfGroups().getValue());
        }
        if (ospDTO.getSamplingMethodCode() != null) {
            osp.setSamplingMethodCode(SamplingMethodCode.getByCode(ospDTO.getSamplingMethodCode().getCode()));
        }
        if (ospDTO.getStudyModelCode() != null) {
            osp.setStudyModelCode(StudyModelCode.getByCode(ospDTO.getStudyModelCode().getCode()));
        }
        osp.setStudyModelOtherText(StConverter.convertToString(ospDTO.getStudyModelOtherText()));
        if (ospDTO.getTimePerspectiveCode() != null) {
            osp.setTimePerspectiveCode(TimePerspectiveCode.getByCode(ospDTO.getTimePerspectiveCode().getCode()));
        }
        if (ospDTO.getTimePerspectiveOtherText() != null) {
            osp.setTimePerspectiveOtherText(StConverter.convertToString(ospDTO.getTimePerspectiveOtherText()));
        }
        if (ospDTO.getStudyPopulationDescription() != null) {
          osp.setStudyPopulationDescription(StConverter.convertToString(ospDTO.getStudyPopulationDescription()));
        }
        return osp;
    }
}
