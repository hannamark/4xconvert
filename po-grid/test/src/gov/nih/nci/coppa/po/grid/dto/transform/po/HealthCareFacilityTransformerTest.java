package gov.nih.nci.coppa.po.grid.dto.transform.po;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.coppa.po.grid.dto.transform.po.HealthCareFacilityTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;

import org.iso._21090.II;

public class HealthCareFacilityTransformerTest 
       extends AbstractTransformerTestBase <HealthCareFacilityTransformer, HealthCareFacility , HealthCareFacilityDTO> {
    /**
     * The identifier name for healthCare Facility.
     */
    public static final String HEALTH_CARE_FACILITY_IDENTIFIER_NAME = "NCI health care facility identifier";

    /**
     * The ii root value for healthCare Facility.
     */
    public static final String HEALTH_CARE_FACILITY_ROOT = "2.16.840.1.113883.3.26.4.4.3";
    /**
     * Player root.
     */
    public static final String PLAYER_ROOT = "1.2.3";

    /**
     * Player name.
     */
    public static final String PLAYER_NAME = "player name";


	@Override
	public HealthCareFacilityDTO makeDtoSimple() {
	    Ii id = new Ii();
	    id.setRoot(HEALTH_CARE_FACILITY_ROOT);
	    id.setIdentifierName(HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
	    id.setExtension("123");
	    Ii player = new Ii();
	    player.setRoot(PLAYER_ROOT);
	    player.setIdentifierName(PLAYER_NAME);
	    player.setExtension("346");

		HealthCareFacilityDTO hcf_dto = new HealthCareFacilityDTO();
	    hcf_dto.setIdentifier(id);
	    hcf_dto.setPlayerIdentifier(player);
		hcf_dto.setStatus(new CDTransformerTest().makeDtoSimple());
		return hcf_dto;
	}

	@Override
	public HealthCareFacility makeXmlSimple() {
		II id = new II();
	    id.setRoot(HEALTH_CARE_FACILITY_ROOT);
	    id.setIdentifierName(HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
	    id.setExtension("123");
	    II player = new II();
	    player.setRoot(PLAYER_ROOT);
	    player.setIdentifierName(PLAYER_NAME);
	    player.setExtension("346");
		
	    HealthCareFacility hcf_xml = new HealthCareFacility();
		hcf_xml.setIdentifier(id);
		hcf_xml.setPlayerIdentifier(player);
		hcf_xml.setStatus(new CDTransformerTest().makeXmlSimple());
		return hcf_xml;
	}

	@Override
	public void verifyDtoSimple(HealthCareFacilityDTO x) {
		assertEquals(x.getIdentifier().getExtension(), "123");
		assertEquals(x.getIdentifier().getIdentifierName(),HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
		assertEquals(x.getStatus().getCode(), new CDTransformerTest().makeDtoSimple().getCode());
	}

	@Override
	public void verifyXmlSimple(HealthCareFacility x) {
		assertEquals(x.getIdentifier().getExtension(), "123");
		assertEquals(x.getIdentifier().getIdentifierName(),HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
		assertEquals(x.getStatus().getCode(), new CDTransformerTest().makeDtoSimple().getCode());		
	}

}
