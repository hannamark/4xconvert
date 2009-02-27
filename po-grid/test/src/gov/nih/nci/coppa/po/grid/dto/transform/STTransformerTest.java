package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.St;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import org.iso._21090.ST;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class STTransformerTest extends AbstractTransformerTestBase<STTransformer, org.iso._21090.ST, gov.nih.nci.coppa.iso.St>{

    @Override
    public org.iso._21090.ST makeXml() {
        org.iso._21090.ST x = new org.iso._21090.ST();
        x.setValue("v");
        return x;
    }

    @Override
    public gov.nih.nci.coppa.iso.St makeDto() {
        gov.nih.nci.coppa.iso.St x = new gov.nih.nci.coppa.iso.St();
        x.setValue("v");
        return x;
    }

    @Override
    public void verifyXml(org.iso._21090.ST x) {
        assertEquals("v", x.getValue());
    }

    @Override
    public void verifyDto(gov.nih.nci.coppa.iso.St x) {
        assertEquals("v", x.getValue());
    }
}