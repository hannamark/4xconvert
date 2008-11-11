package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StratumGroup;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import java.util.Date;
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
public class StratumGroupConverter extends AbstractConverter<StratumGroupDTO, StratumGroup> {

    /**
     * @param sg StratumGroup
     * @return SubGroupsDTO
     */
    @Override
    public StratumGroupDTO convertFromDomainToDto(StratumGroup sg) {
        StratumGroupDTO sgDTO = new StratumGroupDTO();
        sgDTO.setIdentifier(IiConverter.convertToIi(sg.getId()));
        sgDTO.setDescription(StConverter.convertToSt(sg.getDescription()));
        sgDTO.setGroupNumberText(StConverter.convertToSt(sg.getGroupNumberText()));
        sgDTO.setStudyProtocolIi(IiConverter.convertToIi(sg.getStudyProtocol().getId()));
        return sgDTO;
    }


    /**
     * @param sgDTO SubGroupsDTO
     * @return StratumGroup
     */
    @Override
    public StratumGroup convertFromDtoToDomain(StratumGroupDTO sgDTO) {
        StratumGroup sg = new StratumGroup();

        StudyProtocol spBo = new StudyProtocol();
        spBo.setId(IiConverter.convertToLong(sgDTO.getStudyProtocolIi()));
        sg.setDateLastUpdated(new Date());
        sg.setStudyProtocol(spBo);
        if (sgDTO.getDescription() != null) {
            sg.setDescription(sgDTO.getDescription().getValue());
        }
        if (sgDTO.getGroupNumberText() != null) {
            sg.setGroupNumberText(sgDTO.getGroupNumberText().getValue());
        }
        return sg;
    }

}
