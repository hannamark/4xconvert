/**
 *
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.AbstractDocument;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import java.util.Date;

/**
 * Convert document DTO to domain and vice versa.
 * @param <BO> domain object
 * @param <DTO> dto
 * @author vrushali
 *
 */
public abstract class AbstractDocumentConverter<DTO extends DocumentDTO, BO extends AbstractDocument>
      extends AbstractConverter<DTO, BO> {
    /**
     *@param doc domain
     *@param docDTO dto
     */
    public void convertFromDomainToDto(AbstractDocument doc, DocumentDTO docDTO) {
        docDTO.setIdentifier(IiConverter.convertToDocumentIi(doc.getId()));
        docDTO.setTypeCode(CdConverter.convertToCd(doc.getTypeCode()));
        docDTO.setFileName(StConverter.convertToSt(doc.getFileName()));

    }
    /**
     *
     * @param docDTO dto
     * @param doc domain
     */
    public void convertFromDtoToDomain(DocumentDTO docDTO, AbstractDocument doc) {
        doc.setId(IiConverter.convertToLong(docDTO.getIdentifier()));
        if (docDTO.getTypeCode() != null) {
            doc.setTypeCode(DocumentTypeCode.getByCode(docDTO.getTypeCode().getCode()));
        }
        if (docDTO.getFileName() != null) {
            doc.setFileName(docDTO.getFileName().getValue());
        }
        doc.setDateLastUpdated(new Date());
    }

}
