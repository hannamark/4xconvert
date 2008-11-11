package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import java.util.Date;
/**
 * @author Kalpana Guthikonda
 * @since 11/07/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class DocumentWorkflowStatusConverter extends 
AbstractConverter<DocumentWorkflowStatusDTO, DocumentWorkflowStatus> {
    /**
     * @param dws DocumentWorkflowStatus
     * @return DocumentWorkflowStatusDTO
     */
    @Override
    public DocumentWorkflowStatusDTO convertFromDomainToDto(DocumentWorkflowStatus dws) {
        DocumentWorkflowStatusDTO dwsDTO = new DocumentWorkflowStatusDTO();
        dwsDTO.setIdentifier(IiConverter.convertToIi(dws.getId()));
        dwsDTO.setCommonText(StConverter.convertToSt(dws.getCommonText()));
        dwsDTO.setStatusCode(CdConverter.convertToCd(dws.getStatusCode()));
        dwsDTO.setStudyProtocolIi(IiConverter.convertToIi(dws.getStudyProtocol().getId()));
        return dwsDTO;
    }


    /**
     * @param dwsDTO DocumentWorkflowStatusDTO
     * @return DocumentWorkflowStatus
     */
    @Override
    public DocumentWorkflowStatus convertFromDtoToDomain(DocumentWorkflowStatusDTO dwsDTO) {
        DocumentWorkflowStatus dws = new DocumentWorkflowStatus();

        StudyProtocol spBo = new StudyProtocol();
        spBo.setId(IiConverter.convertToLong(dwsDTO.getStudyProtocolIi()));
        dws.setDateLastUpdated(new Date());
        dws.setStudyProtocol(spBo);
        if (dwsDTO.getCommonText() != null) {
            dws.setCommonText(dwsDTO.getCommonText().getValue());
        }
        if (dwsDTO.getStatusCode() != null) {
            dws.setStatusCode(DocumentWorkflowStatusCode.getByCode(dwsDTO.getStatusCode().getCode()));
        }
        return dws;
    }

}
