package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.OrganizationalContact;
import gov.nih.nci.iso21090.Constants;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETADTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTelTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformerTest;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import org.iso._21090.II;

import static org.junit.Assert.assertEquals;

public class OrganizationalContactTransformerTest extends
     AbstractTransformerTestBase<OrganizationalContactTransformer,OrganizationalContact,OrganizationalContactDTO>{
    /**
     * The identifier name for.
     */
    public static final String ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME = "Organizational contact identifier";

    /**
     * The ii root value.
     */
    public static final String ORGANIZATIONAL_CONTACT_ROOT = Constants.NCI_OID + ".4.8";
    /**
     * Player root.
     */
    public static final String PLAYER_ROOT = "1.2.3";

    /**
     * Player name.
     */
    public static final String PLAYER_NAME = "player name";
    /**
     * Scoper root.
     */
    public static final String SCOPER_ROOT = "1.2.3";

    /**
     * Scoper name.
     */
    public static final String SCOPER_NAME = "scoper name";

    @Override
    public OrganizationalContactDTO makeDtoSimple() {
        Ii id = new Ii();
        id.setRoot(ORGANIZATIONAL_CONTACT_ROOT);
        id.setIdentifierName(ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME);
        id.setExtension("123");

        Ii player = new Ii();
        player.setRoot(PLAYER_ROOT);
        player.setIdentifierName(PLAYER_NAME);
        player.setExtension("346");

        Ii scoper = new Ii();
        scoper.setRoot(SCOPER_ROOT);
        scoper.setIdentifierName(SCOPER_NAME);
        scoper.setExtension("567");

        OrganizationalContactDTO dto = new OrganizationalContactDTO();
        dto.setIdentifier(IdTransformerTest.convertIdToDSetIi(id));
        dto.setPlayerIdentifier(player);
        dto.setScoperIdentifier(scoper);
        dto.setPostalAddress(new DSETADTransformerTest().makeDtoSimple());
        dto.setTelecomAddress(new DSETTelTransformerTest().makeDtoSimple());
        dto.setStatus(new CDTransformerTest().makeDtoSimple());
        dto.setTypeCode(new CDTransformerTest().makeDtoSimple());

        return dto;
    }

    @Override
    public OrganizationalContact makeXmlSimple() {
        II id = new II();
        id.setRoot(ORGANIZATIONAL_CONTACT_ROOT);
        id.setIdentifierName(ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME);
        id.setExtension("123");

        II player = new II();
        player.setRoot(PLAYER_ROOT);
        player.setIdentifierName(PLAYER_NAME);
        player.setExtension("346");

        II scoper = new II();
        scoper.setRoot(SCOPER_ROOT);
        scoper.setIdentifierName(SCOPER_NAME);
        scoper.setExtension("567");

        OrganizationalContact xml = new OrganizationalContact();
        xml.setIdentifier(IdTransformerTest.convertIIToDSETII(id));
        xml.setPlayerIdentifier(player);
        xml.setScoperIdentifier(scoper);
        xml.setPostalAddress(new DSETADTransformerTest().makeXmlSimple());
        xml.setTelecomAddress(new DSETTelTransformerTest().makeXmlSimple());
        xml.setStatus(new CDTransformerTest().makeXmlSimple());
        xml.setTypeCode(new CDTransformerTest().makeXmlSimple());
        return xml;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void verifyDtoSimple(OrganizationalContactDTO x) {
        Ii ii = x.getIdentifier().getItem().iterator().next();
        assertEquals(ii.getExtension(), "123");
        assertEquals(ii.getIdentifierName(),ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME);
        assertEquals(x.getPlayerIdentifier().getIdentifierName(),PLAYER_NAME);
        new CDTransformerTest().verifyDtoSimple(x.getStatus());
        new DSETTelTransformerTest().verifyDtoSimple(x.getTelecomAddress());
        new DSETADTransformerTest().verifyDtoSimple(x.getPostalAddress());
        new CDTransformerTest().verifyDtoSimple(x.getTypeCode());
    }

    @Override
    public void verifyXmlSimple(OrganizationalContact x) {
        II ii = x.getIdentifier().getItem().get(0);
        assertEquals(ii.getExtension(), "123");
        assertEquals(ii.getIdentifierName(),ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME);
        assertEquals(x.getPlayerIdentifier().getIdentifierName(),PLAYER_NAME);
        assertEquals(x.getScoperIdentifier().getIdentifierName(),SCOPER_NAME);
        new CDTransformerTest().verifyXmlSimple(x.getStatus());
        new DSETTelTransformerTest().verifyXmlSimple(x.getTelecomAddress());
        new DSETADTransformerTest().verifyXmlSimple(x.getPostalAddress());
        new CDTransformerTest().verifyXmlSimple(x.getTypeCode());
    }
}
