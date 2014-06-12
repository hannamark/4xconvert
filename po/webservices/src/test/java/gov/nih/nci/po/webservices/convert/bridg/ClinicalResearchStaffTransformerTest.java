package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.iso21090.Constants;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETADTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformerTest;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import org.iso._21090.II;

import static org.junit.Assert.assertEquals;

public class ClinicalResearchStaffTransformerTest extends
     AbstractTransformerTestBase<ClinicalResearchStaffTransformer , ClinicalResearchStaff ,ClinicalResearchStaffDTO> {
    /**
     * The identifier name for ClinicalResearchStaff.
     */
    public static final String CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME = "NCI clinical research staff identifier";

    /**
     * The ii root value for ClinicalResearchStaff.
     */
    public static final String CLINICAL_RESEARCH_STAFF_ROOT = Constants.NCI_OID + ".4.1";
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
    public ClinicalResearchStaffDTO makeDtoSimple() {
        Ii id = new Ii();
        id.setRoot(CLINICAL_RESEARCH_STAFF_ROOT);
        id.setIdentifierName(CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
        id.setExtension("123");

        Ii player = new Ii();
        player.setRoot(PLAYER_ROOT);
        player.setIdentifierName(PLAYER_NAME);
        player.setExtension("346");

        Ii scoper = new Ii();
        scoper.setRoot(SCOPER_ROOT);
        scoper.setIdentifierName(SCOPER_NAME);
        scoper.setExtension("456");

        ClinicalResearchStaffDTO crs_dto = new ClinicalResearchStaffDTO();
        crs_dto.setIdentifier(IdTransformerTest.convertIdToDSetIi(id));
        crs_dto.setPlayerIdentifier(player);
        crs_dto.setScoperIdentifier(scoper);
        crs_dto.setPostalAddress(new DSETADTransformerTest().makeDtoSimple());
        crs_dto.setStatus(new CDTransformerTest().makeDtoSimple());
        return crs_dto;
    }

    @Override
    public ClinicalResearchStaff makeXmlSimple() {
        II id = new II();
        id.setRoot(CLINICAL_RESEARCH_STAFF_ROOT);
        id.setIdentifierName(CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
        id.setExtension("123");

        II player = new II();
        player.setRoot(PLAYER_ROOT);
        player.setIdentifierName(PLAYER_NAME);
        player.setExtension("346");

        II scoper = new II();
        scoper.setRoot(SCOPER_ROOT);
        scoper.setIdentifierName(SCOPER_NAME);
        scoper.setExtension("456");

        ClinicalResearchStaff crs_xml = new ClinicalResearchStaff();
        crs_xml.setIdentifier(IdTransformerTest.convertIIToDSETII(id));
        crs_xml.setPlayerIdentifier(player);
        crs_xml.setScoperIdentifier(scoper);
        crs_xml.setPostalAddress(new DSETADTransformerTest().makeXmlSimple());
        crs_xml.setStatus(new CDTransformerTest().makeXmlSimple());
        return crs_xml;
    }

    @Override
    public void verifyDtoSimple(ClinicalResearchStaffDTO x) {
        Ii ii = x.getIdentifier().getItem().iterator().next();
        assertEquals(ii.getExtension(), "123");
        assertEquals(ii.getIdentifierName(),CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
        assertEquals(x.getScoperIdentifier().getIdentifierName(),SCOPER_NAME);
        assertEquals(x.getStatus().getCode(), new CDTransformerTest().makeDtoSimple().getCode());
    }

    @Override
    public void verifyXmlSimple(ClinicalResearchStaff x) {
        II ii = x.getIdentifier().getItem().get(0);
        assertEquals(ii.getExtension(), "123");
        assertEquals(ii.getIdentifierName(),CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
        assertEquals(x.getScoperIdentifier().getIdentifierName(),SCOPER_NAME);
        assertEquals(x.getStatus().getCode(), new CDTransformerTest().makeDtoSimple().getCode());
    }

}
