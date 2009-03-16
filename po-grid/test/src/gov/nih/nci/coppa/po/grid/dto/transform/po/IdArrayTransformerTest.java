package gov.nih.nci.coppa.po.grid.dto.transform.po;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.po.grid.dto.transform.IITransformerTest;

import org.junit.Test;

public class IdArrayTransformerTest {

    @Test
    public void toXml_null() throws DtoTransformException {
        assertNull(IdArrayTransformer.INSTANCE.toXml(null));
    }

    @Test
    public void toXml_empty() throws DtoTransformException {
        assertEquals(0, IdArrayTransformer.INSTANCE.toXml(new Ii[0]).length);
    }

    @Test
    public void toXml_values() throws DtoTransformException {
        Ii a = makeDtoSimple();
        Ii b = makeDtoSimple("456");
        Id[] xmls = IdArrayTransformer.INSTANCE.toXml(new Ii[] { a, b });

        assertNotNull(xmls);
        assertEquals(2, xmls.length);
        verifyXml(xmls[0], a);
        verifyXml(xmls[1], b);
    }

    @Test
    public void toDto_null() throws DtoTransformException {
        assertNull(IdArrayTransformer.INSTANCE.toDto(null));
    }

    @Test
    public void toDto_empty() throws DtoTransformException {
        assertEquals(0, IdArrayTransformer.INSTANCE.toDto(new Id[0]).length);
    }

    @Test
    public void toDto_values() throws DtoTransformException {
        Id a = makeXmlSimple();
        Id b = makeXmlSimple("456");
        Ii[] dtos = IdArrayTransformer.INSTANCE.toDto(new Id[] { a, b });

        assertNotNull(dtos);
        assertEquals(2, dtos.length);
        verifyDto(dtos[0], a);
        verifyDto(dtos[1], b);

    }

    private void verifyXml(Id expected, Ii value) {
        verifyDto(value, expected);
    }

    private void verifyDto(Ii expected, Id value) {
        assertEquals(expected.getDisplayable(), value.isDisplayable());
        assertEquals(expected.getRoot(), value.getRoot());
        assertEquals(expected.getIdentifierName(), value.getIdentifierName());
        assertEquals(expected.getExtension(), value.getExtension());
        assertEquals(expected.getNullFlavor().name(), value.getNullFlavor().name());
        assertEquals(expected.getReliability().name(), value.getReliability().name());
        assertEquals(expected.getScope().name(), value.getScope().name());
    }

    private Id makeXmlSimple(String extension) {
        Id id = makeXmlSimple();
        id.setExtension(extension);
        return id;
    }

    private Id makeXmlSimple() {
        Id id = new Id();
        id.setDisplayable(Boolean.TRUE);
        id.setRoot(IITransformerTest.ROOT);
        id.setIdentifierName(IITransformerTest.IDENTIFIER_NAME);
        id.setExtension("123");
        id.setNullFlavor(org.iso._21090.NullFlavor.OTH);
        id.setReliability(org.iso._21090.IdentifierReliability.ISS);
        id.setScope(org.iso._21090.IdentifierScope.VER);
        return id;
    }

    private Ii makeDtoSimple(String extension) {
        Ii id = makeDtoSimple();
        id.setExtension(extension);
        return id;
    }

    private Ii makeDtoSimple() {
        Ii id = new Ii();
        id.setDisplayable(Boolean.TRUE);
        id.setRoot(IITransformerTest.ROOT);
        id.setIdentifierName(IITransformerTest.IDENTIFIER_NAME);
        id.setExtension("123");
        id.setNullFlavor(NullFlavor.OTH);
        id.setReliability(IdentifierReliability.ISS);
        id.setScope(IdentifierScope.VER);
        return id;
    }
}
