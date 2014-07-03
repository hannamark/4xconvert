package gov.nih.nci.services.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OrganizationalContactType;
import gov.nih.nci.po.data.convert.GenericTypeCodeConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.IiDsetConverter;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.net.URISyntaxException;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Test;


public class OrganizationalContactDTOTest extends AbstractPersonRoleDTOTest {
    private static final String TEST_TITLE = "test title";
    private final OrganizationalContactType type = new OrganizationalContactType("For drug shipment");

    @Before
    public void initDbData() {
        PoHibernateUtil.getCurrentSession().save(getType());
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
    }

    private OrganizationalContactType getType() {
        return type;
    }

    @Override
    protected AbstractPersonRole getExampleTestClass() {
        OrganizationalContact oc = new OrganizationalContact();
        fillInExamplePersonRoleFields(oc);
        oc.setType(getType());
        oc.setTitle(TEST_TITLE);

        return oc;
    }

    @Override
    protected AbstractPersonRoleDTO getExampleTestClassDTO(Long personId, Long orgId) throws URISyntaxException {
        OrganizationalContactDTO dto = new OrganizationalContactDTO();
        fillInPersonRoleDTOFields(dto, personId, orgId);

        Ii ii = new Ii();
        ii.setExtension("" + 1L);
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setRoot(IdConverter.ORGANIZATIONAL_CONTACT_ROOT);
        ii.setIdentifierName(IdConverter.ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME);
        dto.setIdentifier(IiConverter.convertToDsetIi(ii));

        dto.setTypeCode(GenericTypeCodeConverter.convertToCd(getType()));

        St title = new St();
        title.setValue(TEST_TITLE);
        dto.setTitle(title);

        return dto;
    }

    @Override
    protected void verifyTestClassDTOFields(AbstractPersonRole pr) {
        OrganizationalContact organizationalContact = (OrganizationalContact) pr;

        String expectedValue = getCodeValue(getType());
        String actualValue = getCodeValue(organizationalContact.getType());
        assertEquals(expectedValue, actualValue);
        assertEquals(TEST_TITLE, organizationalContact.getTitle());
    }

    public static String getCodeValue(OrganizationalContactType ocType) {
        return ocType.getCode();
    }

    public static String getCodeValue(Cd cd) {
        return cd.getCode();
    }

    @Override
    protected void verifyTestClassFields(AbstractPersonRoleDTO dto) {
        //verify Ii
        Ii expectedIi = new Ii();
        expectedIi.setExtension("" + 1);
        expectedIi.setDisplayable(true);
        expectedIi.setScope(IdentifierScope.OBJ);
        expectedIi.setReliability(IdentifierReliability.ISS);
        expectedIi.setIdentifierName(IdConverter.ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME);
        expectedIi.setRoot(IdConverter.ORGANIZATIONAL_CONTACT_ROOT);
        OrganizationalContactDTO organizationalContactDTO = (OrganizationalContactDTO) dto;
        Ii actualIi = IiDsetConverter.convertToIi(organizationalContactDTO.getIdentifier());
        assertTrue(EqualsBuilder.reflectionEquals(expectedIi, actualIi));

        //verify OrganizationalContact
        String expectedValue = getCodeValue(getType());
        String actualValue = getCodeValue(organizationalContactDTO.getTypeCode());
        assertEquals(expectedValue, actualValue);
        assertEquals(TEST_TITLE, organizationalContactDTO.getTitle().getValue());
    }

    @Test
    public void contactTypeEdgeCases() throws Exception {
        Cd result = GenericTypeCodeConverter.convertToCd(null);
        assertNotNull(result);

        result = GenericTypeCodeConverter.convertToCd(new OrganizationalContactType("For drug shipment2"));
        assertNotNull(result);
        assertEquals(result.getCode(), "For drug shipment2");
    }
}
