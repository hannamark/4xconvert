package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.OversightCommittee;
import gov.nih.nci.iso21090.Constants;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformerTest;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import org.iso._21090.II;

import static org.junit.Assert.assertEquals;

public class OversightCommitteeTransformerTest extends
    AbstractTransformerTestBase<OversightCommitteeTransformer,OversightCommittee,OversightCommitteeDTO> {
    /**
     * The identifier name for for OversightCommittee.
     */
    public static final String OVERSIGHT_COMMITTEE_IDENTIFIER_NAME = "NCI oversight committee identifier";

    /**
     * The ii root value for OversightCommittee.
     */
    public static final String OVERSIGHT_COMMITTEE_ROOT = Constants.NCI_OID + ".4.4";
    /**
     * Player root.
     */
    public static final String PLAYER_ROOT = "1.2.3";

    /**
     * Player name.
     */
    public static final String PLAYER_NAME = "player name";

    @Override
    public OversightCommitteeDTO makeDtoSimple() {
        Ii id = new Ii();
        id.setRoot(OVERSIGHT_COMMITTEE_ROOT);
        id.setIdentifierName(OVERSIGHT_COMMITTEE_IDENTIFIER_NAME);
        id.setExtension("123");

        Ii player = new Ii();
        player.setRoot(PLAYER_ROOT);
        player.setIdentifierName(PLAYER_NAME);
        player.setExtension("346");

        OversightCommitteeDTO dto = new OversightCommitteeDTO();
        dto.setIdentifier(IdTransformerTest.convertIdToDSetIi(id));
        dto.setPlayerIdentifier(player);
        dto.setStatus(new CDTransformerTest().makeDtoSimple());
        dto.setTypeCode(new CDTransformerTest().makeDtoSimple());
        return dto;
    }

    @Override
    public OversightCommittee makeXmlSimple() {
        II id = new II();
        id.setRoot(OVERSIGHT_COMMITTEE_ROOT);
        id.setIdentifierName(OVERSIGHT_COMMITTEE_IDENTIFIER_NAME);
        id.setExtension("123");

        II player = new II();
        player.setRoot(PLAYER_ROOT);
        player.setIdentifierName(PLAYER_NAME);
        player.setExtension("346");

        OversightCommittee xml= new OversightCommittee();
        xml.setIdentifier(IdTransformerTest.convertIIToDSETII(id));
        xml.setPlayerIdentifier(player);
        xml.setStatus(new CDTransformerTest().makeXmlSimple());
        xml.setTypeCode(new CDTransformerTest().makeXmlSimple());
        return xml;
    }

    @Override
    public void verifyDtoSimple(OversightCommitteeDTO x) {
        Ii ii = x.getIdentifier().getItem().iterator().next();
        assertEquals(ii.getExtension(), "123");
        assertEquals(ii.getIdentifierName(),OVERSIGHT_COMMITTEE_IDENTIFIER_NAME);
        new CDTransformerTest().verifyDtoSimple(x.getStatus());
    }

    @Override
    public void verifyXmlSimple(OversightCommittee x) {
        II ii = x.getIdentifier().getItem().get(0);
        assertEquals(ii.getExtension(), "123");
        assertEquals(ii.getIdentifierName(),OVERSIGHT_COMMITTEE_IDENTIFIER_NAME);
        new CDTransformerTest().verifyXmlSimple(x.getStatus());

    }

}
