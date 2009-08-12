package gov.nih.nci.coppa.po.grid.dto.transform.po;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;

public class IdTransformerTest extends
    AbstractTransformerTestBase<IdTransformer , Id ,Ii> {

    /**
     * The identifier name for for Identified org.
     */
    public static final String IDENTIFIED_ORG_IDENTIFIER_NAME = "Identified org identifier";

    /**
     * The ii root value for Identified org.
     */
    public static final String IDENTIFIED_ORG_ROOT = "2.16.840.1.113883.3.26.4.4.6";

    @Override
    public Ii makeDtoSimple() {
        Ii id = new Ii();
        id.setRoot(IDENTIFIED_ORG_ROOT);
        id.setIdentifierName(IDENTIFIED_ORG_IDENTIFIER_NAME);
        id.setExtension("123");

        return id;
    }

    @Override
    public Id makeXmlSimple() {
        Id id = new Id();
        id.setRoot(IDENTIFIED_ORG_ROOT);
        id.setIdentifierName(IDENTIFIED_ORG_IDENTIFIER_NAME);
        id.setExtension("123");
        return id;
    }

    @Override
    public void verifyDtoSimple(Ii x) {
        assertEquals(x.getRoot(), IDENTIFIED_ORG_ROOT);
        assertEquals(x.getExtension(), "123");
        assertEquals(x.getIdentifierName(),IDENTIFIED_ORG_IDENTIFIER_NAME);
    }

    @Override
    public void verifyXmlSimple(Id x) {
        assertEquals(x.getRoot(), IDENTIFIED_ORG_ROOT);
        assertEquals(x.getExtension(), "123");
        assertEquals(x.getIdentifierName(),IDENTIFIED_ORG_IDENTIFIER_NAME);
    }

}
