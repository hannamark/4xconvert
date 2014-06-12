package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.iso21090.Constants;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETADTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTelTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ENONTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformerTest;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import org.iso._21090.II;

import static org.junit.Assert.assertEquals;

public class HealthCareFacilityTransformerTest
       extends AbstractTransformerTestBase<HealthCareFacilityTransformer, HealthCareFacility , HealthCareFacilityDTO> {
    /**
     * The identifier name for healthCare Facility.
     */
    public static final String HEALTH_CARE_FACILITY_IDENTIFIER_NAME = "NCI health care facility identifier";

    /**
     * The ii root value for healthCare Facility.
     */
    public static final String HEALTH_CARE_FACILITY_ROOT = Constants.NCI_OID + ".4.3";
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
        hcf_dto.setIdentifier(IdTransformerTest.convertIdToDSetIi(id));
        hcf_dto.setPlayerIdentifier(player);
        hcf_dto.setStatus(new CDTransformerTest().makeDtoSimple());
        hcf_dto.setName(new ENONTransformerTest().makeDtoSimple());
        hcf_dto.setPostalAddress(new DSETADTransformerTest().makeDtoSimple());
        hcf_dto.setTelecomAddress(new DSETTelTransformerTest().makeDtoSimple());
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
        hcf_xml.setIdentifier(IdTransformerTest.convertIIToDSETII(id));
        hcf_xml.setPlayerIdentifier(player);
        hcf_xml.setStatus(new CDTransformerTest().makeXmlSimple());
        hcf_xml.setName(new ENONTransformerTest().makeXmlSimple());
        hcf_xml.setPostalAddress(new DSETADTransformerTest().makeXmlSimple());
        hcf_xml.setTelecomAddress(new DSETTelTransformerTest().makeXmlSimple());
        return hcf_xml;
    }

    @Override
    public void verifyDtoSimple(HealthCareFacilityDTO x) {
        Ii ii = x.getIdentifier().getItem().iterator().next();
        assertEquals(ii.getExtension(), "123");
        assertEquals(ii.getIdentifierName(),HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
        assertEquals(x.getStatus().getCode(), new CDTransformerTest().makeDtoSimple().getCode());
        new ENONTransformerTest().verifyDtoSimple(x.getName());
    }

    @Override
    public void verifyXmlSimple(HealthCareFacility x) {
        II ii = x.getIdentifier().getItem().get(0);
        assertEquals(ii.getExtension(), "123");
        assertEquals(ii.getIdentifierName(),HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
        assertEquals(x.getStatus().getCode(), new CDTransformerTest().makeDtoSimple().getCode());
        new ENONTransformerTest().verifyXmlSimple(x.getName());
        assertEquals(x.getTelecomAddress().getFlavorId(), new DSETTelTransformerTest().makeXmlSimple().getFlavorId());
        assertEquals(x.getPostalAddress().getFlavorId(), new DSETADTransformerTest().makeXmlSimple().getFlavorId());
    }

}
