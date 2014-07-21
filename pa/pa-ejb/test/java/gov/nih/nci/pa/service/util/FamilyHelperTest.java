package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.po.ws.common.types.EntityStatus;
import gov.nih.nci.po.ws.common.types.Family;
import gov.nih.nci.po.ws.common.types.FamilyMember;
import gov.nih.nci.po.ws.common.types.FamilyMemberType;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.webservices.rest.client.FamilyRestServiceClient;
import gov.nih.nci.webservices.rest.client.util.PoRestServiceLocator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FamilyHelperTest {

    private OrganizationEntityServiceRemote oes;
    private FamilyServiceRemote fs;
    private FamilyRestServiceClient fsc;

    @Before
    public void setUp() throws Exception {
        PoServiceLocator psl = mock(PoServiceLocator.class);
        PoRestServiceLocator prsl = mock(PoRestServiceLocator.class);
        oes = mock(OrganizationEntityServiceRemote.class);
        when(psl.getOrganizationEntityService()).thenReturn(oes);
        fs = mock(FamilyServiceRemote.class);
        when(psl.getFamilyService()).thenReturn(fs);
        PoRegistry.getInstance().setPoServiceLocator(psl);
        fsc = mock(FamilyRestServiceClient.class);
        when(prsl.getFamilyService()).thenReturn(fsc);
        when(prsl.getFamilyServiceRemote()).thenReturn(fs);
        PoRegistry.getInstance().setPoResPoServiceLocator(prsl);
    }

    @Test
    public void getByOrgIdNullTest() throws Exception {
        assertTrue(FamilyHelper.getByOrgId(null).isEmpty());
    }

    @Test
    public void getByOrgIdTest() throws Exception {
        // no family associated with org
        List<Family> result = new ArrayList<Family>();
        when(fsc.search(any(Family.class))).thenReturn(result);
        assertTrue(FamilyHelper.getByOrgId(1L).isEmpty());
        
        // one family
        Family family = getFamily(1L);
        List<Family> familyList = new ArrayList<Family>();
        familyList.add(family);
        when(fsc.search(any(Family.class))).thenReturn(familyList);
        assertEquals(1, FamilyHelper.getByOrgId(1L).size());

        // n results
        familyList.add(getFamily(2L));
        assertEquals(2, FamilyHelper.getByOrgId(1L).size());
    }

    private Family getFamily(long index) {
        Family family = new Family();
        family.setName("some family name"+index);
        family.setStatus(EntityStatus.ACTIVE);
        family.setId(index);
        FamilyMember fm = new FamilyMember();
        fm.setFamilyId(index);
        fm.setOrganizationId(index);
        fm.setType(FamilyMemberType.ORGANIZATIONAL);
        family.getMember().add(fm);
        return family;
    }

    @Test
    public void getAllRelatedOrgsNull() throws Exception {
        assertTrue(FamilyHelper.getAllRelatedOrgs((Long) null).isEmpty());
    }

    @Test
    public void getAllRelatedOrgsNotFound() throws Exception {
        assertTrue(FamilyHelper.getAllRelatedOrgs(1L).isEmpty());
    }

    @Test
    public void getAllRelatedOrgsTest() throws Exception {
        List<Family> familyList = new ArrayList<Family>();
        familyList.add(getFamily(1L));
        familyList.add(getFamily(2L));
        when(fsc.search(any(Family.class))).thenReturn(familyList);
        
        // 2 families 1 common org and 1 unique per family
        when(fs.getActiveRelationships(1L)).thenReturn(getRelationships(new Long[] {1L, 2L}));
        when(fs.getActiveRelationships(2L)).thenReturn(getRelationships(new Long[] {1L, 3L}));
        assertEquals(3, FamilyHelper.getAllRelatedOrgs(1L).size());

        // 2 families with the same 2 members
        when(fs.getActiveRelationships(2L)).thenReturn(getRelationships(new Long[] {1L, 2L}));
        assertEquals(2, FamilyHelper.getAllRelatedOrgs(1L).size());
    }

    @Test
    public void getP30GrantSerialNumberNotFoundTest() throws Exception {
        assertNull(FamilyHelper.getP30GrantSerialNumber(1L));
    }

    @Test
    public void getP30GrantSerialNumberTest() throws Exception {
        String sn = "293481";
        Family family = getFamily(2L);
        family.setP30SerialNumber(sn);
        List<Family> familyList = new ArrayList<Family>();
        familyList.add(family);
        when(fsc.search(any(Family.class))).thenReturn(familyList);
        
        //assertEquals(1, FamilyHelper.getByOrgId(1L).size());
       
        assertEquals(sn, FamilyHelper.getP30GrantSerialNumber(2L));

        // test non-organizational
        family.getMember().get(0).setType(FamilyMemberType.AFFILIATION);
        assertNull(FamilyHelper.getP30GrantSerialNumber(1L));
    }

    public static List<FamilyOrganizationRelationshipDTO> getRelationships(Long[] orgIds) {
        List<FamilyOrganizationRelationshipDTO> result = new ArrayList<FamilyOrganizationRelationshipDTO>();
        for (Long orgId : orgIds) {
            FamilyOrganizationRelationshipDTO rel = new FamilyOrganizationRelationshipDTO();
            rel.setOrgIdentifier(IiConverter.convertToPaOrganizationIi(orgId));
            result.add(rel);
        }
        return result;
    }
}
