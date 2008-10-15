package gov.nih.nci.pa.iso.convert;

import java.util.Date;

import gov.nih.nci.pa.domain.StratumGroup;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
/**
 * Convert Document from domain to DTO.
 *
 * @author Kalpana Guthikonda
 * @since 10/13/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({  "PMD.NPathComplexity" , "PMD.CyclomaticComplexity" })
public class StratumGroupConverter {
    
    /**
     * @param sg StratumGroup
     * @return SubGroupsDTO
     */
    public static StratumGroupDTO convertFromDomainToDTO(StratumGroup sg) {
        StratumGroupDTO sgDTO = new StratumGroupDTO();
        sgDTO.setIi(IiConverter.convertToIi(sg.getId()));
        sgDTO.setUserLastUpdated(StConverter.convertToSt(sg.getUserLastUpdated()));
        sgDTO.setDescription(StConverter.convertToSt(sg.getDescription()));
        sgDTO.setGroupNumberText(StConverter.convertToSt(sg.getGroupNumberText()));
        sgDTO.setStudyProtocolIi(IiConverter.convertToIi(sg.getStudyProtocol().getId()));        
        return sgDTO;
    }
    
    
    /**
     * @param sgDTO SubGroupsDTO
     * @return StratumGroup
     */
    public static StratumGroup convertFromDTOToDomain(StratumGroupDTO sgDTO) {
        StratumGroup sg = new StratumGroup();

        StudyProtocol spBo = new StudyProtocol();
        spBo.setId(IiConverter.convertToLong(sgDTO.getStudyProtocolIi()));  
        sg.setDateLastUpdated(new Date());
        sg.setStudyProtocol(spBo);
        if (sgDTO.getUserLastUpdated() != null) {
            sg.setUserLastUpdated(sgDTO.getUserLastUpdated().getValue());
        }
        if (sgDTO.getDescription() != null) {
            sg.setDescription(sgDTO.getDescription().getValue());
        } 
        if (sgDTO.getGroupNumberText() != null) {
            sg.setGroupNumberText(sgDTO.getGroupNumberText().getValue());
        }
        return sg;
    }

}
