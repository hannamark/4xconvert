/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.TrialDocument;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dkrylov
 * 
 */
public class DocumentDTOBuilder {

    /**
     * @param reg
     *            CompleteTrialRegistration
     * @return List<DocumentDTO>
     */
    public List<DocumentDTO> build(CompleteTrialRegistration reg) {
        List<DocumentDTO> list = new ArrayList<>();
        convertAndStore(list, reg.getProtocolDocument(),
                DocumentTypeCode.PROTOCOL_DOCUMENT);
        convertAndStore(list, reg.getIrbApprovalDocument(),
                DocumentTypeCode.IRB_APPROVAL_DOCUMENT);
        convertAndStore(list, reg.getParticipatingSitesDocument(),
                DocumentTypeCode.PARTICIPATING_SITES);
        convertAndStore(list, reg.getInformedConsentDocument(),
                DocumentTypeCode.INFORMED_CONSENT_DOCUMENT);
        for (TrialDocument doc : reg.getOtherDocument()) {
            convertAndStore(list, doc, DocumentTypeCode.OTHER);
        }
        return list;
    }

    private void convertAndStore(List<DocumentDTO> list, TrialDocument doc,
            DocumentTypeCode type) {
        if (doc != null) {
            DocumentDTO isoDTO = new DocumentDTO();
            isoDTO.setTypeCode(CdConverter.convertToCd(type));
            isoDTO.setFileName(StConverter.convertToSt(doc.getFilename()));
            isoDTO.setText(EdConverter.convertToEd(doc.getValue()));
            list.add(isoDTO);
        }
    }

}
