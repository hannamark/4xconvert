package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DocumentServiceBeanTest {
    private DocumentServiceRemote remoteEjb = new DocumentServiceBean();
    Ii pid;

    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        pid = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
    }

    @Test
    public void get() throws Exception {
        List<DocumentDTO> statusList =
            remoteEjb.getDocumentsByStudyProtocol(pid);
        assertEquals(2, statusList.size());

        DocumentDTO dto =
            remoteEjb.get(statusList.get(1).getIdentifier());
        assertEquals(IiConverter.convertToLong(statusList.get(1).getIdentifier())
                , (IiConverter.convertToLong(dto.getIdentifier())));
        dto.setText(EdConverter.convertToEd("test".getBytes()));
        DocumentDTO dto2 = null;
        try {
            dto2 = new DocumentDTO();
            dto2 = remoteEjb.update(dto);
        } catch(PAException e) {
            // expected behavior
        }

        remoteEjb.delete(dto);
        DocumentDTO dto3 =
            remoteEjb.get(dto.getIdentifier());
        assertEquals(dto3.getInactiveCommentText(), dto.getInactiveCommentText());

    }

    @Test
    public void create() throws Exception {
        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setStudyProtocolIi(pid);
        docDTO.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.Other));
        docDTO.setFileName(StConverter.convertToSt("Protocol_Document.doc"));
        docDTO.setText(EdConverter.convertToEd("test".getBytes()));
        DocumentDTO docDTO2 = null;
        try {
            docDTO2 = new DocumentDTO();
            docDTO2 = remoteEjb.create(docDTO);
        } catch(PAException e) {
            // expected behavior
        }
    }

}
