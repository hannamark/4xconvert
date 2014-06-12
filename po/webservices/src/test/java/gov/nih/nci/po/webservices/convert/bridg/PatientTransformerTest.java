package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.Patient;
import gov.nih.nci.iso21090.Constants;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETADTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformerTest;
import gov.nih.nci.services.correlation.PatientDTO;
import org.iso._21090.II;

import static org.junit.Assert.assertEquals;

public class PatientTransformerTest extends
     AbstractTransformerTestBase<PatientTransformer , Patient ,PatientDTO> {
    
    /**
     * The base of all COPPA-related II roots.
     */
    public static final String BASE_ROOT = Constants.NCI_OID;
    /**
     * The sfx of all COPPA-related structural role II roots.
     */
    public static final String STRUCTURAL_ROLE_SFX = ".4";
    /**
     * The identifier name for.
     */
    public static final String PATIENT_IDENTIFIER_NAME = "Patient identifier";

    /**
     * The ii root value.
     */
    public static final String PATIENT_ROOT = BASE_ROOT + STRUCTURAL_ROLE_SFX + ".9";
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
    public PatientDTO makeDtoSimple() {
        Ii id = new Ii();
        id.setRoot(PATIENT_ROOT);
        id.setIdentifierName(PATIENT_IDENTIFIER_NAME);
        id.setExtension("123");

        Ii player = new Ii();
        player.setRoot(PLAYER_ROOT);
        player.setIdentifierName(PLAYER_NAME);
        player.setExtension("346");

        Ii scoper = new Ii();
        scoper.setRoot(SCOPER_ROOT);
        scoper.setIdentifierName(SCOPER_NAME);
        scoper.setExtension("456");

        PatientDTO crs_dto = new PatientDTO();
        crs_dto.setIdentifier(IdTransformerTest.convertIdToDSetIi(id));
        crs_dto.setPlayerIdentifier(player);
        crs_dto.setScoperIdentifier(scoper);
        crs_dto.setPostalAddress(new DSETADTransformerTest().makeDtoSimple());
        crs_dto.setStatus(new CDTransformerTest().makeDtoSimple());
        return crs_dto;
    }

    @Override
    public Patient makeXmlSimple() {
        II id = new II();
        id.setRoot(PATIENT_ROOT);
        id.setIdentifierName(PATIENT_IDENTIFIER_NAME);
        id.setExtension("123");

        II player = new II();
        player.setRoot(PLAYER_ROOT);
        player.setIdentifierName(PLAYER_NAME);
        player.setExtension("346");

        II scoper = new II();
        scoper.setRoot(SCOPER_ROOT);
        scoper.setIdentifierName(SCOPER_NAME);
        scoper.setExtension("456");

        Patient crs_xml = new Patient();
        crs_xml.setIdentifier(IdTransformerTest.convertIIToDSETII(id));
        crs_xml.setPlayerIdentifier(player);
        crs_xml.setScoperIdentifier(scoper);
        crs_xml.setPostalAddress(new DSETADTransformerTest().makeXmlSimple());
        crs_xml.setStatus(new CDTransformerTest().makeXmlSimple());
        return crs_xml;
    }

    @Override
    public void verifyDtoSimple(PatientDTO x) {
        Ii ii = x.getIdentifier().getItem().iterator().next();
        assertEquals(ii.getExtension(), "123");
        assertEquals(ii.getIdentifierName(),PATIENT_IDENTIFIER_NAME);
        assertEquals(x.getScoperIdentifier().getIdentifierName(),SCOPER_NAME);
        assertEquals(x.getStatus().getCode(), new CDTransformerTest().makeDtoSimple().getCode());
    }

    @Override
    public void verifyXmlSimple(Patient x) {
        II ii = x.getIdentifier().getItem().get(0);
        assertEquals(ii.getExtension(), "123");
        assertEquals(ii.getIdentifierName(),PATIENT_IDENTIFIER_NAME);
        assertEquals(x.getScoperIdentifier().getIdentifierName(),SCOPER_NAME);
        assertEquals(x.getStatus().getCode(), new CDTransformerTest().makeDtoSimple().getCode());
    }

}
