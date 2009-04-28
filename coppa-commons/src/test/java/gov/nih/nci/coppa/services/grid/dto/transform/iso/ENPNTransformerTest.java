package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.ENTransformer.ENPNTransformer;

import org.iso._21090.ENPN;
import org.iso._21090.ENXP;

public class ENPNTransformerTest extends AbstractTransformerTestBase<ENPNTransformer, ENPN, EnPn> {

    @Override
    public EnPn makeDtoSimple() {
        EnPn dto = new EnPn();
        Enxp o = new Enxp(EntityNamePartType.FAM);
        dto.getPart().add(o);
        return dto;
    }

    @Override
    public ENPN makeXmlSimple() {
        ENPN xml = new ENPN();
        ENXP o = new ENXP();
        o.setCode("value");
        o.setType(org.iso._21090.EntityNamePartType.FAM);
        xml.getPart().add(o);
        return xml;
    }

    @Override
    public void verifyDtoSimple(EnPn x) {
        assertEquals(x.getPart().size(), 1);
        assertEquals(x.getPart().get(0).getType(), EntityNamePartType.FAM);

    }

    @Override
    public void verifyXmlSimple(ENPN x) {
        assertEquals(x.getPart().size(), 1);
        assertEquals(x.getPart().get(0).getType(), org.iso._21090.EntityNamePartType.FAM);
    }

}
