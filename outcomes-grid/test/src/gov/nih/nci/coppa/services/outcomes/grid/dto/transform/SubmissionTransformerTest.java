package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import java.util.Date;

import gov.nih.nci.accrual.dto.SubmissionDto;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IVLTSTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.TSTransformer;
import gov.nih.nci.coppa.services.outcomes.Submission;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.SubmissionTransformer;

import org.iso._21090.II;

import static org.junit.Assert.assertEquals;

public class SubmissionTransformerTest extends
    AbstractTransformerTestBase<SubmissionTransformer, Submission, SubmissionDto> {

    /**
     * Study Protocol root.
     */
    public static final String STUDY_PROTOCOL_ROOT = "1.2.3";

    /**
     * Study Protocol name.
     */
    public static final String STUDY_PROTOCOL_NAME = "protocol name";

    private St createSt = new St();
    {
        createSt.setValue("create user");
    }
    
    private Ts cutoffts = new Ts();
    {
        cutoffts.setValue(new Date());
    }
    
    private St descriptionSt = new St();
    {
        descriptionSt.setValue("a description");
    }
    
    private St labelSt = new St();
    {
        labelSt.setValue("a label");
    }
        
    private Ivl<Ts> ivlTs = new Ivl<Ts>();
    {
        ivlTs.setHigh(cutoffts);
    }
    
    private St submSt = new St();
    {
        submSt.setValue("submitter's name");
    }
    
    @Override
    public SubmissionDto makeDtoSimple() {        
        Ii studyProtocol = new Ii();
        studyProtocol.setRoot(STUDY_PROTOCOL_ROOT);
        studyProtocol.setIdentifierName(STUDY_PROTOCOL_NAME);
        studyProtocol.setExtension("346");

        SubmissionDto dto = new SubmissionDto();
        dto.setIdentifier(new IITransformerTest().makeDtoSimple());
        dto.setStudyProtocolIdentifier(studyProtocol);
        dto.setStatusCode(new CDTransformerTest().makeDtoSimple());   
        dto.setCreateUser(createSt);
        dto.setCutOffDate(cutoffts);
        dto.setDescription(descriptionSt);     
        dto.setLabel(labelSt);
        dto.setStatusDateRange(ivlTs);
        dto.setSubmitUser(submSt);
        
        return dto;
    }

    @Override
    public Submission makeXmlSimple() throws DtoTransformException {
        II study_prot_id = new II();
        study_prot_id.setRoot(STUDY_PROTOCOL_ROOT);
        study_prot_id.setIdentifierName(STUDY_PROTOCOL_NAME);
        study_prot_id.setExtension("346");

        Submission sub_xml = new Submission();
        sub_xml.setIdentifier(new IITransformerTest().makeXmlSimple());
        sub_xml.setDescription(STTransformer.INSTANCE.toXml(descriptionSt));
        sub_xml.setCreateUser(STTransformer.INSTANCE.toXml(createSt));
        sub_xml.setLabel(STTransformer.INSTANCE.toXml(labelSt));
        sub_xml.setSubmitUser(STTransformer.INSTANCE.toXml(submSt));
        sub_xml.setStatusCode(new CDTransformerTest().makeXmlSimple());
        sub_xml.setStudyProtocolIdentifier(study_prot_id);
        sub_xml.setCutOffDate(TSTransformer.INSTANCE.toXml(cutoffts));
        sub_xml.setStatusDateRange(IVLTSTransformer.INSTANCE.toXml(ivlTs));
        
        return sub_xml;
    }

    @Override
    public void verifyDtoSimple(SubmissionDto dto) {
        assertEquals(dto.getIdentifier().getExtension(), "123");
        assertEquals(dto.getStudyProtocolIdentifier().getExtension(), "346");
        assertEquals(dto.getCreateUser().getValue(), "create user");
        assertEquals(dto.getCutOffDate(), cutoffts);
        assertEquals(dto.getDescription().getValue(), "a description");
        assertEquals(dto.getLabel().getValue(), "a label");
        assertEquals(dto.getStatusCode().getCode(), new CDTransformerTest().makeDtoSimple().getCode());
        assertEquals(dto.getStatusDateRange().getHigh(), cutoffts);
        assertEquals(dto.getSubmitUser().getValue(), "submitter's name");        
    }

    @Override
    public void verifyXmlSimple(Submission xml) throws DtoTransformException {
        assertEquals(xml.getIdentifier().getExtension(), "123");
        assertEquals(xml.getStudyProtocolIdentifier().getExtension(), "346");
        assertEquals(xml.getCreateUser().getValue(), "create user");
        assertEquals(xml.getCutOffDate().getValue(), TSTransformer.INSTANCE.toXml(cutoffts).getValue());
        assertEquals(xml.getDescription().getValue(), "a description");
        assertEquals(xml.getLabel().getValue(), "a label");
        assertEquals(xml.getStatusCode().getCode(), new CDTransformerTest().makeDtoSimple().getCode());
        assertEquals(xml.getStatusDateRange().getHigh().getValue(), TSTransformer.INSTANCE.toXml(cutoffts).getValue());

        assertEquals(xml.getSubmitUser().getValue(), "submitter's name"); 
    }
}
    
