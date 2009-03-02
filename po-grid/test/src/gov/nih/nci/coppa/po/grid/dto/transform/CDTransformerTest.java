
package gov.nih.nci.coppa.po.grid.dto.transform;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gov.nih.nci.coppa.iso.Cd;

import org.iso._21090.CD;

/**
 *
 * @author gax
 */
public class CDTransformerTest extends AbstractTransformerTestBase<CDTransformer, CD, Cd> {

    @Override
    public Cd makeDtoSimple() {
        Cd i = new Cd();
        i.setCode("code");
        i.setCodeSystem("ignored");
        i.setCodeSystemName("ignored");
        i.setCodeSystemVersion("ignored");
        i.setDisplayName(new STTransformerTest().makeDtoSimple());
        i.setOriginalText(null);
        return i;
    }

    @Override
    public CD makeXmlSimple() {
        CD x = new CD();
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
    public void verifyDtoSimple(Cd i) {
        assertEquals("code", i.getCode());
        assertNull(i.getCodeSystem());
        assertNull(i.getCodeSystemName());
        assertNull( i.getCodeSystemVersion());
        assertNull(i.getDisplayName());
    }
}