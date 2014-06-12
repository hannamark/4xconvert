package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.ResearchOrganization;
import gov.nih.nci.iso21090.Constants;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETADTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTelTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ENONTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformerTest;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import org.iso._21090.II;

import static org.junit.Assert.assertEquals;

public class ResearchOrganizationTransformerTest extends
    AbstractTransformerTestBase<ResearchOrganizationTransformer , ResearchOrganization ,ResearchOrganizationDTO> {

    /**
     * The identifier name for for Research org.
     */
    public static final String Research_ORG_IDENTIFIER_NAME = "Research org identifier";

    /**
     * The ii root value for Research org.
     */
    public static final String Research_ORG_ROOT = Constants.NCI_OID + ".4.6";
    /**
     * Player root.
     */
    public static final String PLAYER_ROOT = "1.2.3";

    /**
     * Player name.
     */
    public static final String PLAYER_NAME = "player name";

    /**
     * Assigned ID root.
     */
    public static final String ASSIGNED_ID_ROOT = "1.2.3";

    /**
     * Assigned ID name.
     */
    public static final String ASSIGNED_ID_NAME = "Assigned Id name";

    @Override
    public ResearchOrganizationDTO makeDtoSimple() {
        Ii id = new Ii();
        id.setRoot(Research_ORG_ROOT);
        id.setIdentifierName(Research_ORG_IDENTIFIER_NAME);
        id.setExtension("123");
        Ii player = new Ii();
        player.setRoot(PLAYER_ROOT);
        player.setIdentifierName(PLAYER_NAME);
        player.setExtension("346");

        Ii assignedId = new Ii();
        assignedId.setRoot(ASSIGNED_ID_ROOT);
        assignedId.setIdentifierName(ASSIGNED_ID_NAME);
        assignedId.setExtension("456");


        ResearchOrganizationDTO dto = new ResearchOrganizationDTO ();
        dto.setIdentifier(IdTransformerTest.convertIdToDSetIi(id));
        dto.setPlayerIdentifier(player);
        dto.setStatus(new CDTransformerTest().makeDtoSimple());
        dto.setFundingMechanism(new CDTransformerTest().makeDtoSimple());
        dto.setTypeCode(new CDTransformerTest().makeDtoSimple());
        dto.setName(new ENONTransformerTest().makeDtoSimple());
        dto.setPostalAddress(new DSETADTransformerTest().makeDtoSimple());
        dto.setTelecomAddress(new DSETTelTransformerTest().makeDtoSimple());
        return dto;
    }

    @Override
    public ResearchOrganization makeXmlSimple() {
        II id = new II();
        id.setRoot(Research_ORG_ROOT);
        id.setIdentifierName(Research_ORG_IDENTIFIER_NAME);
        id.setExtension("123");

        II player = new II();
        player.setRoot(PLAYER_ROOT);
        player.setIdentifierName(PLAYER_NAME);
        player.setExtension("346");

        II assignedId = new II();
        assignedId.setRoot(ASSIGNED_ID_ROOT);
        assignedId.setIdentifierName(ASSIGNED_ID_NAME);
        assignedId.setExtension("456");

        ResearchOrganization xml = new ResearchOrganization();
        xml.setIdentifier(IdTransformerTest.convertIIToDSETII(id));
        xml.setPlayerIdentifier(player);
        xml.setStatus(new CDTransformerTest().makeXmlSimple());
        xml.setFundingMechanism(new CDTransformerTest().makeXmlSimple());
        xml.setTypeCode(new CDTransformerTest().makeXmlSimple());
        xml.setName(new ENONTransformerTest().makeXmlSimple());
        xml.setPostalAddress(new DSETADTransformerTest().makeXmlSimple());
        xml.setTelecomAddress(new DSETTelTransformerTest().makeXmlSimple());
        return xml;
    }

    @Override
    public void verifyDtoSimple(ResearchOrganizationDTO x) {
        Ii ii = x.getIdentifier().getItem().iterator().next();
        assertEquals(ii.getExtension(), "123");
        assertEquals(ii.getIdentifierName(), Research_ORG_IDENTIFIER_NAME);
        assertEquals(x.getPlayerIdentifier().getExtension(), "346");
        assertEquals(x.getPlayerIdentifier().getIdentifierName(), PLAYER_NAME);
        new CDTransformerTest().verifyDtoSimple(x.getStatus());
        new CDTransformerTest().verifyDtoSimple(x.getTypeCode());
        new CDTransformerTest().verifyDtoSimple(x.getFundingMechanism());
        new ENONTransformerTest().verifyDtoSimple(x.getName());
    }

    @Override
    public void verifyXmlSimple(ResearchOrganization x) {
        II ii = x.getIdentifier().getItem().get(0);
        assertEquals(ii.getExtension(), "123");
        assertEquals(ii.getIdentifierName(),Research_ORG_IDENTIFIER_NAME);
        assertEquals(x.getPlayerIdentifier().getExtension(), "346");
        assertEquals(x.getPlayerIdentifier().getIdentifierName(), PLAYER_NAME);
        new CDTransformerTest().verifyXmlSimple(x.getStatus());
        new CDTransformerTest().verifyXmlSimple(x.getTypeCode());
        new CDTransformerTest().verifyXmlSimple(x.getFundingMechanism());
        new ENONTransformerTest().verifyXmlSimple(x.getName());
        assertEquals(x.getTelecomAddress().getFlavorId(), new DSETTelTransformerTest().makeXmlSimple().getFlavorId());
        assertEquals(x.getPostalAddress().getFlavorId(), new DSETADTransformerTest().makeXmlSimple().getFlavorId());
    }

}
