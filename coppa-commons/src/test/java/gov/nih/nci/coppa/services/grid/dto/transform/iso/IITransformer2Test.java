package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;

import org.iso._21090.II;
import org.iso._21090.NullFlavor;
import org.junit.Test;


public class IITransformer2Test extends AbstractTransformerTestBase<IITransformer,II,Ii>{
     /**
     * The identifier name.
     */
    public static final String IDENTIFIER_NAME = "identifier name";

    /**
     * The ii root value.
     */
    public static final String ROOT = "2.16.840.1.113883.3.26.4.4.3";

    @Override
    public Ii makeDtoSimple() {
          Ii id = new Ii();
          id.setDisplayable(Boolean.TRUE);
          id.setRoot(ROOT);
          id.setIdentifierName(IDENTIFIER_NAME);
          id.setExtension("123");
          id.setReliability(IdentifierReliability.UNV);
          id.setScope(IdentifierScope.VER);
          return id;
    }

    @Override
    public II makeXmlSimple() {
          II id = new II();
          id.setDisplayable(Boolean.TRUE);
          id.setRoot(ROOT);
          id.setIdentifierName(IDENTIFIER_NAME);
          id.setExtension("123");
          id.setReliability(org.iso._21090.IdentifierReliability.USE);
          id.setScope(org.iso._21090.IdentifierScope.VER);
          return id;
    }

    @Override
    public void verifyDtoSimple(Ii x) {
        assertEquals(x.getDisplayable(),Boolean.TRUE);
        assertEquals(x.getRoot(),ROOT);
        assertEquals(x.getIdentifierName(),IDENTIFIER_NAME);
        assertEquals(x.getExtension(), "123");
        assertNull(x.getNullFlavor());
        assertEquals(x.getReliability(),IdentifierReliability.UNV);
        assertEquals(x.getScope(),IdentifierScope.VER);

    }

    @Override
    public void verifyXmlSimple(II x) {
        assertEquals(x.getRoot(), ROOT);
        assertEquals(x.getIdentifierName(), IDENTIFIER_NAME);
        assertEquals(x.getExtension(), "123");
        assertNull(x.getNullFlavor());
        assertEquals(x.getReliability(), org.iso._21090.IdentifierReliability.USE);
        assertEquals(x.getScope(), org.iso._21090.IdentifierScope.VER);
    }

    @Test
    public void testNullFlavorConversion() {
        II x = new II();
        x.setNullFlavor(NullFlavor.ASKU);
        Ii y = IITransformer.INSTANCE.toDto(x);
        assertNotNull(y);
        assertEquals(y.getNullFlavor(), gov.nih.nci.coppa.iso.NullFlavor.ASKU);
        x = IITransformer.INSTANCE.toXml(y);
        assertNotNull(x);
        assertEquals(x.getNullFlavor(), NullFlavor.ASKU);
    }
}
