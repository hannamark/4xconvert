package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.EnOn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.ENTransformer.ENONTransformer;

import org.iso._21090.ENON;
import org.iso._21090.ENXP;

public class ENONTransformerTest extends AbstractTransformerTestBase<ENONTransformer,ENON,EnOn>{

    @Override
    public EnOn makeDtoSimple() {
        EnOn dto = new EnOn();
        Enxp o = new Enxp(EntityNamePartType.DEL);
        dto.getPart().add(o);
        return dto;
    }

    @Override
    public ENON makeXmlSimple() {
        ENON xml = new ENON();
        ENXP o = new ENXP();
        o.setType(org.iso._21090.EntityNamePartType.DEL);
        xml.getPart().add(o);
        return xml;
    }

    @Override
    public void verifyDtoSimple(EnOn x) {
        assertEquals(x.getPart().size(),1);
        assertEquals(x.getPart().get(0).getType(),EntityNamePartType.DEL);
    }

    @Override
    public void verifyXmlSimple(ENON x) {
        assertEquals(x.getPart().size(),1);
        assertEquals(x.getPart().get(0).getType(),org.iso._21090.EntityNamePartType.DEL);

    }

}
