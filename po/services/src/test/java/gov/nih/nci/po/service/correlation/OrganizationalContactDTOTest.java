package gov.nih.nci.po.service.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OrganizationalContactType;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.IiDsetConverter;
import gov.nih.nci.po.data.convert.OrganizationalContactTypeConverter;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.correlation.AbstractPersonRoleDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Test;


public class OrganizationalContactDTOTest extends AbstractPersonRoleDTOTest {
    private static final String TEST_TITLE = "test title";
    private final Set<OrganizationalContactType> types = new HashSet<OrganizationalContactType>();

    @Before
    public void initDbData() {
        types.add(new OrganizationalContactType("For drug shipment"));
        types.add(new OrganizationalContactType("For safety issues"));
        for (OrganizationalContactType obj : getTypes()) {
            PoHibernateUtil.getCurrentSession().save(obj);
        }
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
    }

    private Set<OrganizationalContactType> getTypes() {
        return types;
    }

    @Override
    protected AbstractPersonRole getExampleTestClass() {
        OrganizationalContact oc = new OrganizationalContact();
        fillInExamplePersonRoleFields(oc);
        oc.setTypes(getTypes());
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

        dto.setTypeCode(OrganizationalContactTypeConverter.convertToDsetOfCd(getTypes()));

        St title = new St();
        title.setValue(TEST_TITLE);
        dto.setTitle(title);

        return dto;
    }

    @Override
    protected void verifyTestClassDTOFields(AbstractPersonRole pr) {
        OrganizationalContact organizationalContact = (OrganizationalContact) pr;

        assertEquals(getTypes().size(), organizationalContact.getTypes().size());
        List<String> expectedValues = getCodeValues(getTypes());
        List<String> actualValues = getCodeValues(organizationalContact.getTypes());
        assertEquals(expectedValues.size(), actualValues.size());
        assertTrue(expectedValues.containsAll(actualValues));
        assertTrue(actualValues.containsAll(expectedValues));

        assertEquals(TEST_TITLE, organizationalContact.getTitle());

    }

    public static List<String> getCodeValues(Collection<OrganizationalContactType> list) {
        List<String> codeValues = new ArrayList<String>();
        for (OrganizationalContactType organizationalContactType : list) {
            OrganizationalContactType type = organizationalContactType;
            codeValues.add(type.getCode());
        }
        return codeValues;
    }

    public static List<String> getCodeValues(DSet<Cd> list) {
        List<String> codeValues = new ArrayList<String>();
        for (Cd cd : list.getItem()) {
            Cd type = cd;
            codeValues.add(type.getCode());
        }
        return codeValues;
    }

    @SuppressWarnings("unchecked")
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
        List<String> expectedValues = getCodeValues(getTypes());
        List<String> actualValues = getCodeValues(organizationalContactDTO.getTypeCode());
        assertEquals(expectedValues.size(), actualValues.size());
        assertTrue(expectedValues.containsAll(actualValues));
        assertTrue(actualValues.containsAll(expectedValues));
        assertEquals(TEST_TITLE, organizationalContactDTO.getTitle().getValue());

    }

    @Test
    public void contactTypeEdgeCases() throws Exception {
        DSet<Cd> result = OrganizationalContactTypeConverter.convertToDsetOfCd(null);
        assertNotNull(result);
        assertNotNull(result.getItem());
        assertTrue(result.getItem().isEmpty());

        result = OrganizationalContactTypeConverter.convertToDsetOfCd(new HashSet<OrganizationalContactType>());
        assertNotNull(result);
        assertNotNull(result.getItem());
        assertTrue(result.getItem().isEmpty());
    }
}
