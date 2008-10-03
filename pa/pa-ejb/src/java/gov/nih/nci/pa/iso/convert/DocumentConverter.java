package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
/**
 * Convert Document from domain to DTO.
 *
 * @author Kalpana Guthikonda
 * @since 09/30/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({  "PMD.NPathComplexity" , "PMD.CyclomaticComplexity" })
public class DocumentConverter {
    
    /**
     * @param doc Document
     * @return DocumentDTO
     */
    public static DocumentDTO convertFromDomainToDTO(Document doc) {
        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setIi(IiConverter.convertToIi(doc.getId()));
        docDTO.setTypeCode(CdConverter.convertToCd(doc.getTypeCode()));
        docDTO.setUserLastUpdated(StConverter.convertToSt(doc.getUserLastUpdated()));
        docDTO.setFileName(StConverter.convertToSt(doc.getFileName()));
        return docDTO;
    }
    
    
    /**
     * @param docDTO DocumentDTO
     * @return Document
     */
    public static Document convertFromDTOToDomain(DocumentDTO docDTO) {
        Document doc = new Document();
        if (docDTO.getIi() != null) {
            doc.setId(Long.valueOf(docDTO.getIi().getExtension()));
        }
        if (docDTO.getTypeCode() != null) {
            doc.setTypeCode(DocumentTypeCode.getByCode(
                    docDTO.getTypeCode().getCode()));
        }
        if (docDTO.getUserLastUpdated() != null) {
            doc.setUserLastUpdated(docDTO.getUserLastUpdated().getValue());
        }
        if (docDTO.getFileName() != null) {
            doc.setFileName(docDTO.getFileName().getValue());
        }        
        return doc;
    }

}
