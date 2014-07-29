/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.CompleteTrialUpdate;
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
     * @throws PAException PAException
     */
    public List<DocumentDTO> build(CompleteTrialRegistration reg)
            throws PAException {
        List<DocumentDTO> list = new ArrayList<>();
        convertAndStore(list, reg.getProtocolDocument(),
                DocumentTypeCode.PROTOCOL_DOCUMENT, null);
        convertAndStore(list, reg.getIrbApprovalDocument(),
                DocumentTypeCode.IRB_APPROVAL_DOCUMENT, null);
        convertAndStore(list, reg.getParticipatingSitesDocument(),
                DocumentTypeCode.PARTICIPATING_SITES, null);
        convertAndStore(list, reg.getInformedConsentDocument(),
                DocumentTypeCode.INFORMED_CONSENT_DOCUMENT, null);
        for (TrialDocument doc : reg.getOtherDocument()) {
            convertAndStore(list, doc, DocumentTypeCode.OTHER, null);
        }
        return list;
    }

    private void convertAndStore(List<DocumentDTO> list, TrialDocument doc,
            DocumentTypeCode type, Ii spID) throws PAException {
        if (doc != null) {
            DocumentDTO isoDTO = chooseNewOrExistingDocDTO(type, spID);
            isoDTO.setTypeCode(CdConverter.convertToCd(type));
            isoDTO.setFileName(StConverter.convertToSt(doc.getFilename()));
            isoDTO.setText(EdConverter.convertToEd(doc.getValue()));
            list.add(isoDTO);
        }
    }

    private DocumentDTO chooseNewOrExistingDocDTO(DocumentTypeCode type, Ii spID)
            throws PAException {
        if (ISOUtil.isIiNull(spID)) {
            return new DocumentDTO();
        }
        for (DocumentDTO doc : PaRegistry.getDocumentService()
                .getDocumentsByStudyProtocol(spID)) {
            if (type == CdConverter.convertCdToEnum(DocumentTypeCode.class,
                    doc.getTypeCode())) {
                return doc;
            }
        }
        return new DocumentDTO();
    }

    /**
     * @param spDTO
     *            StudyProtocolDTO
     * @param reg
     *            CompleteTrialUpdate
     * @return List<DocumentDTO>
     * @throws PAException PAException
     */
    public List<DocumentDTO> build(StudyProtocolDTO spDTO,
            CompleteTrialUpdate reg) throws PAException {
        List<DocumentDTO> list = new ArrayList<>();
        convertAndStore(list, reg.getProtocolDocument(),
                DocumentTypeCode.PROTOCOL_DOCUMENT, spDTO.getIdentifier());
        convertAndStore(list, reg.getIrbApprovalDocument(),
                DocumentTypeCode.IRB_APPROVAL_DOCUMENT, spDTO.getIdentifier());
        convertAndStore(list, reg.getParticipatingSitesDocument(),
                DocumentTypeCode.PARTICIPATING_SITES, spDTO.getIdentifier());
        convertAndStore(list, reg.getInformedConsentDocument(),
                DocumentTypeCode.INFORMED_CONSENT_DOCUMENT,
                spDTO.getIdentifier());
        for (TrialDocument doc : reg.getOtherDocument()) {
            convertAndStore(list, doc, DocumentTypeCode.OTHER, null);
        }
        return list;
    }

}
