package gov.nih.nci.coppa.po.grid.dto.transform.po;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.ResearchOrganization;
import gov.nih.nci.coppa.po.grid.dto.transform.po.ResearchOrganizationTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;

import org.iso._21090.II;

public class ResearchOrganizationTransformerTest extends
    AbstractTransformerTestBase<ResearchOrganizationTransformer , ResearchOrganization ,ResearchOrganizationDTO> {

    /**
     * The identifier name for for Research org.
     */
    public static final String Research_ORG_IDENTIFIER_NAME = "Research org identifier";

    /**
     * The ii root value for Research org.
     */
    public static final String Research_ORG_ROOT = "2.16.840.1.113883.3.26.4.4.6";
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
        dto.setIdentifier(id);
        dto.setPlayerIdentifier(player);
        dto.setStatus(new CDTransformerTest().makeDtoSimple());
        dto.setFundingMechanism(new CDTransformerTest().makeDtoSimple());
        dto.setTypeCode(new CDTransformerTest().makeDtoSimple());
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
        xml.setIdentifier(id);
        xml.setPlayerIdentifier(player);
        xml.setStatus(new CDTransformerTest().makeXmlSimple());
        xml.setFundingMechanism(new CDTransformerTest().makeXmlSimple());
        xml.setTypeCode(new CDTransformerTest().makeXmlSimple());
        return xml;
    }

    @Override
    public void verifyDtoSimple(ResearchOrganizationDTO x) {
        assertEquals(x.getIdentifier().getExtension(), "123");
        assertEquals(x.getIdentifier().getIdentifierName(), Research_ORG_IDENTIFIER_NAME);
        assertEquals(x.getPlayerIdentifier().getExtension(), "346");
        assertEquals(x.getPlayerIdentifier().getIdentifierName(), PLAYER_NAME);
        new CDTransformerTest().verifyDtoSimple(x.getStatus());
        new CDTransformerTest().verifyDtoSimple(x.getTypeCode());
        new CDTransformerTest().verifyDtoSimple(x.getFundingMechanism());
    }

    @Override
    public void verifyXmlSimple(ResearchOrganization x) {
        assertEquals(x.getIdentifier().getExtension(), "123");
        assertEquals(x.getIdentifier().getIdentifierName(),Research_ORG_IDENTIFIER_NAME);
        assertEquals(x.getPlayerIdentifier().getExtension(), "346");
        assertEquals(x.getPlayerIdentifier().getIdentifierName(), PLAYER_NAME);
        new CDTransformerTest().verifyXmlSimple(x.getStatus());
        new CDTransformerTest().verifyXmlSimple(x.getTypeCode());
        new CDTransformerTest().verifyXmlSimple(x.getFundingMechanism());
    }

}
