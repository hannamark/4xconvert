package gov.nih.nci.coppa.po.grid.dto.transform.po;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;

import java.util.LinkedHashSet;
import java.util.Set;

import org.iso._21090.DSETII;
import org.iso._21090.II;

public class IdTransformerTest extends AbstractTransformerTestBase<IdTransformer, Id, Ii> {

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
        id.setReliability(IdentifierReliability.ISS);
        id.setScope(IdentifierScope.OBJ);

        return id;
    }

    @Override
    public Id makeXmlSimple() {
        Id id = new Id();
        id.setRoot(IDENTIFIED_ORG_ROOT);
        id.setIdentifierName(IDENTIFIED_ORG_IDENTIFIER_NAME);
        id.setExtension("123");
        id.setReliability(org.iso._21090.IdentifierReliability.ISS);
        id.setScope(org.iso._21090.IdentifierScope.OBJ);
        return id;
    }

    @Override
    public void verifyDtoSimple(Ii x) {
        assertEquals(x.getRoot(), IDENTIFIED_ORG_ROOT);
        assertEquals(x.getExtension(), "123");
        assertEquals(x.getIdentifierName(), IDENTIFIED_ORG_IDENTIFIER_NAME);
        assertEquals(IdentifierReliability.ISS, x.getReliability());
        assertEquals(IdentifierScope.OBJ, x.getScope());
    }

    @Override
    public void verifyXmlSimple(Id x) {
        assertEquals(x.getRoot(), IDENTIFIED_ORG_ROOT);
        assertEquals(x.getExtension(), "123");
        assertEquals(x.getIdentifierName(), IDENTIFIED_ORG_IDENTIFIER_NAME);
        assertEquals(org.iso._21090.IdentifierReliability.ISS, x.getReliability());
        assertEquals(org.iso._21090.IdentifierScope.OBJ, x.getScope());
    }

    /**
     * Converts a single Ii into a DSet<Ii> containing just that Ii.
     *
     * @param id Ii to add to set
     * @return DSet<Ii> containing the id
     */
    static DSet<Ii> convertIdToDSetIi(Ii id) {
        Set<Ii> identifiers = new LinkedHashSet<Ii>();
        identifiers.add(id);
        DSet<Ii> identifier = new DSet<Ii>();
        identifier.setItem(identifiers);
        return identifier;
    }

    static DSETII convertIIToDSETII(II ii) {
        DSETII identifier = new DSETII();
        identifier.getItem().add(ii);
        return identifier;
    }
}
