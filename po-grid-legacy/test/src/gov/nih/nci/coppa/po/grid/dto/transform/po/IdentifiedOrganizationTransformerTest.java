package gov.nih.nci.coppa.po.grid.dto.transform.po;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.grid.dto.transform.po.IdentifiedOrganizationTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;

import org.iso._21090.II;

public class IdentifiedOrganizationTransformerTest extends
    AbstractTransformerTestBase<IdentifiedOrganizationTransformer , IdentifiedOrganization ,IdentifiedOrganizationDTO> {

	/**
     * The identifier name for for Identified org.
     */
    public static final String IDENTIFIED_ORG_IDENTIFIER_NAME = "Identified org identifier";

    /**
     * The ii root value for Identified org.
     */
    public static final String IDENTIFIED_ORG_ROOT = "2.16.840.1.113883.3.26.4.4.6";
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
		id.setReliability(IdentifierReliability.ISS);
	    id.setRoot(IDENTIFIED_ORG_ROOT);
	    id.setIdentifierName(IDENTIFIED_ORG_IDENTIFIER_NAME);
	    id.setExtension("123");
	       
        DSet<Ii> dsetii = new DSet<Ii>();
        dsetii.setItem(new HashSet<Ii>());
        dsetii.getItem().add(id);

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
		dto.setIdentifier(dsetii);
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
	    xml.setIdentifier(id);
	    xml.setPlayerIdentifier(player);
	    xml.setStatus(new CDTransformerTest().makeXmlSimple());
	    return xml;
	}

	@Override
	public void verifyDtoSimple(IdentifiedOrganizationDTO x) {
	    Ii identifier = x.getIdentifier().getItem().iterator().next();
		assertEquals(identifier.getExtension(), "123");
		assertEquals(identifier.getIdentifierName(),IDENTIFIED_ORG_IDENTIFIER_NAME);
		assertEquals(x.getAssignedId().getIdentifierName(),ASSIGNED_ID_NAME);
		assertEquals(x.getStatus().getCode(), new CDTransformerTest().makeDtoSimple().getCode());
		
	}

	@Override
	public void verifyXmlSimple(IdentifiedOrganization x) {
		assertEquals(x.getIdentifier().getExtension(), "123");
		assertEquals(x.getIdentifier().getIdentifierName(),IDENTIFIED_ORG_IDENTIFIER_NAME);
		assertEquals(x.getAssignedId().getIdentifierName(),ASSIGNED_ID_NAME);
		assertEquals(x.getStatus().getCode(), new CDTransformerTest().makeDtoSimple().getCode());
	}

}
