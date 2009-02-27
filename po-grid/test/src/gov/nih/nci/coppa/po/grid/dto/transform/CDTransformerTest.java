
package gov.nih.nci.coppa.po.grid.dto.transform;


import gov.nih.nci.coppa.iso.Cd;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import org.iso._21090.CD;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class CDTransformerTest extends AbstractTransformerTestBase<CDTransformer, org.iso._21090.CD, gov.nih.nci.coppa.iso.Cd> {

    @Override
    public Cd makeDtoSimple() {
        gov.nih.nci.coppa.iso.Cd i = new gov.nih.nci.coppa.iso.Cd();
        i.setCode("code");
        i.setCodeSystem("ignored");
        i.setCodeSystemName("ignored");
        i.setCodeSystemVersion("ignored");
        i.setDisplayName(new STTransformerTest().makeDtoSimple());
        i.setOriginalText(null);
        return i;
    }

    @Override
    public org.iso._21090.CD makeXmlSimple() {
        org.iso._21090.CD x = new org.iso._21090.CD();
        x.setCode("code");
        x.setCodeSystem("sys");
        x.setCodeSystemName("name");
        x.setCodeSystemVersion("v");
        x.setControlActExtension("cae");
        x.setControlActRoot("r");
        x.setDisplayName(new STTransformerTest().makeXmlSimple());
        
        return x;
    }

    @Override
    public void verifyXmlSimple(CD x) {
        assertEquals("code", x.getCode());
        assertNull(x.getCodeSystem());
        assertNull(x.getCodeSystemName());
        assertNull(x.getCodeSystemVersion());
        assertNull(x.getDisplayName());
        assertNull(x.getControlActRoot());
        assertNull(x.getDisplayName());
    }


    @Override
    public void verifyDtoSimple(gov.nih.nci.coppa.iso.Cd i) {
        assertEquals("code", i.getCode());
        assertNull(i.getCodeSystem());
        assertNull(i.getCodeSystemName());
        assertNull( i.getCodeSystemVersion());
        assertNull(i.getDisplayName());
    }
}