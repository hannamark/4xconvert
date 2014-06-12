package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.iso21090.Constants;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformerTest;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import org.iso._21090.II;

import static org.junit.Assert.assertEquals;

public class IdentifiedOrganizationTransformerTest extends
    AbstractTransformerTestBase<IdentifiedOrganizationTransformer , IdentifiedOrganization ,IdentifiedOrganizationDTO> {

    /**
     * The identifier name for for Identified org.
     */
    public static final String IDENTIFIED_ORG_IDENTIFIER_NAME = "Identified org identifier";

    /**
     * The ii root value for Identified org.
     */
    public static final String IDENTIFIED_ORG_ROOT = Constants.NCI_OID + ".4.6";
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
    public IdentifiedOrganizationDTO makeDtoSimple() {
        Ii id = new Ii();
        id.setRoot(IDENTIFIED_ORG_ROOT);
        id.setIdentifierName(IDENTIFIED_ORG_IDENTIFIER_NAME);
        id.setExtension("123");
        Ii player = new Ii();
        player.setRoot(PLAYER_ROOT);
        player.setIdentifierName(PLAYER_NAME);
        player.setExtension("346");

        Ii assignedId = new Ii();
        assignedId.setRoot(ASSIGNED_ID_ROOT);
        assignedId.setIdentifierName(ASSIGNED_ID_NAME);
        assignedId.setExtension("456");


        IdentifiedOrganizationDTO dto = new IdentifiedOrganizationDTO ();
        dto.setAssignedId(assignedId);
        dto.setIdentifier(IdTransformerTest.convertIdToDSetIi(id));
        dto.setPlayerIdentifier(player);
        dto.setStatus(new CDTransformerTest().makeDtoSimple());
        return dto;
    }

    @Override
    public IdentifiedOrganization makeXmlSimple() {
        II id = new II();
        id.setRoot(IDENTIFIED_ORG_ROOT);
        id.setIdentifierName(IDENTIFIED_ORG_IDENTIFIER_NAME);
        id.setExtension("123");

        II player = new II();
        player.setRoot(PLAYER_ROOT);
        player.setIdentifierName(PLAYER_NAME);
        player.setExtension("346");

        II assignedId = new II();
        assignedId.setRoot(ASSIGNED_ID_ROOT);
        assignedId.setIdentifierName(ASSIGNED_ID_NAME);
        assignedId.setExtension("456");

        IdentifiedOrganization xml = new IdentifiedOrganization();
        xml.setAssignedId(assignedId);
        xml.setIdentifier(IdTransformerTest.convertIIToDSETII(id));
        xml.setPlayerIdentifier(player);
        xml.setStatus(new CDTransformerTest().makeXmlSimple());
        return xml;
    }

    @Override
    public void verifyDtoSimple(IdentifiedOrganizationDTO x) {
        Ii ii = x.getIdentifier().getItem().iterator().next();
        assertEquals(ii.getExtension(), "123");
        assertEquals(ii.getIdentifierName(),IDENTIFIED_ORG_IDENTIFIER_NAME);
        assertEquals(x.getAssignedId().getIdentifierName(),ASSIGNED_ID_NAME);
        assertEquals(x.getStatus().getCode(), new CDTransformerTest().makeDtoSimple().getCode());

    }

    @Override
    public void verifyXmlSimple(IdentifiedOrganization x) {
        II ii = x.getIdentifier().getItem().get(0);
        assertEquals(ii.getExtension(), "123");
        assertEquals(ii.getIdentifierName(),IDENTIFIED_ORG_IDENTIFIER_NAME);
        assertEquals(x.getAssignedId().getIdentifierName(),ASSIGNED_ID_NAME);
        assertEquals(x.getStatus().getCode(), new CDTransformerTest().makeDtoSimple().getCode());
    }

}
