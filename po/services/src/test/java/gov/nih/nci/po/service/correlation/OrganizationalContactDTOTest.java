package gov.nih.nci.po.service.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OrganizationalContactType;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.OrganizationalContactTypeConverter;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.correlation.AbstractPersonRoleDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Before;


public class OrganizationalContactDTOTest extends AbstractPersonRoleDTOTest {
    private Set<OrganizationalContactType> types = new HashSet<OrganizationalContactType>();

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
        OrganizationalContact hcp = new OrganizationalContact();
        fillInExamplePersonRoleFields(hcp);
        hcp.setPrimaryIndicator(Boolean.TRUE);
        Set<OrganizationalContactType> types = getTypes();
        hcp.setTypes(types);
        
        return hcp;
    }
    
    @Override
    @SuppressWarnings("unchecked")
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
        dto.setIdentifier(ii);

        Bl primary = new Bl();
        primary.setValue(Boolean.TRUE);
        dto.setPrimaryIndicator(primary);
        dto.setTypeCode(OrganizationalContactTypeConverter.convertToDsetOfCd(getTypes()));
        return dto;
    }

    @Override
    protected void verifyTestClassDTOFields(AbstractPersonRole pr) {
        OrganizationalContact organizationalContact = (OrganizationalContact) pr;
        assertEquals(Boolean.TRUE, organizationalContact.getPrimaryIndicator());
        
        assertEquals(getTypes().size(), organizationalContact.getTypes().size());
        List<String> expectedValues = getCodeValues(getTypes());
        List<String> actualValues = getCodeValues(organizationalContact.getTypes());
        assertEquals(expectedValues.size(), actualValues.size());
        assertTrue(expectedValues.containsAll(actualValues));
        assertTrue(actualValues.containsAll(expectedValues));
    }

    public static List<String> getCodeValues(Collection<OrganizationalContactType> list) {
        List<String> codeValues = new ArrayList<String>();
        for (Iterator<OrganizationalContactType> iterator = list.iterator(); iterator.hasNext();) {
            OrganizationalContactType type = (OrganizationalContactType) iterator.next();
            codeValues.add(type.getCode());
        }
        return codeValues;
    }
    
    public static List<String> getCodeValues(DSet<Cd> list) {
        List<String> codeValues = new ArrayList<String>();
        for (Iterator<Cd> iterator = list.getItem().iterator(); iterator.hasNext();) {
            Cd type = (Cd) iterator.next();
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
        assertTrue(EqualsBuilder.reflectionEquals(expectedIi, organizationalContactDTO.getIdentifier()));
        
        //verify OrganizationalContact 
        assertEquals(Boolean.TRUE, organizationalContactDTO.getPrimaryIndicator().getValue());
        List<String> expectedValues = getCodeValues(getTypes());
        List<String> actualValues = getCodeValues(organizationalContactDTO.getTypeCode());
        assertEquals(expectedValues.size(), actualValues.size());
        assertTrue(expectedValues.containsAll(actualValues));
        assertTrue(actualValues.containsAll(expectedValues));
        
    }

}
