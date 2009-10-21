package gov.nih.nci.services;

import static org.junit.Assert.*;

import java.util.Map;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.correlation.NullifiedRoleInterceptorTest.TestInvocationContext;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import org.junit.Before;
import org.junit.Test;


public class NullifiedEntityNodeInterceptorTest {
    NullifiedEntityNodeInterceptor interceptor;
    TestInvocationContext testContext;

    @Before
    public void init() {
        interceptor = new NullifiedEntityNodeInterceptor();
        testContext = new TestInvocationContext();
    }

    @Test
    public void testCheckForNullifiedWithNullifiedPlayer() throws Exception {
        EntityNodeDto entityNodeDto = new EntityNodeDto();

        Organization org = new Organization();
        entityNodeDto.setEntityDto((OrganizationDTO) PoXsnapshotHelper.createSnapshot(org));

        ResearchOrganization ro = new ResearchOrganization();
        ro.setStatus(RoleStatus.PENDING);
        ro.setId(1L);

        ResearchOrganization ro2 = new ResearchOrganization();
        ro2.setStatus(RoleStatus.NULLIFIED);
        ro2.setDuplicateOf(ro);
        ro2.setId(2L);
        
        CorrelationDto[] players = new CorrelationDto[1];
        players[0] = (CorrelationDto) PoXsnapshotHelper.createSnapshot(ro2);
  
        entityNodeDto.setPlayers(players);
        entityNodeDto.setScopers(null);
        
        testContext.returnValue = entityNodeDto;
        try {
            interceptor.checkForNullified(testContext);
            fail("Expected NullifiedEntityNodeException");
        } catch (NullifiedEntityException ex) {
            Map<Ii, Ii> nullifiedEntities = ex.getNullifiedEntities();
            assertEquals(nullifiedEntities.size(), 1);
            
            assertTrue(ex.getNullifiedEntities().containsKey(
                    players[0].getIdentifier().getItem().iterator().next()));
            Ii duplicateIi =
                    ex.getNullifiedEntities().get(
                            players[0].getIdentifier().getItem().iterator().next());
            assertNotNull(duplicateIi);
            assertEquals(duplicateIi.getExtension(), ro.getId().toString());
        }
        
    }
    
    @Test
    public void testCheckForNullifiedWithNullifiedScoper() throws Exception {
        EntityNodeDto entityNodeDto = new EntityNodeDto();

        Organization org = new Organization();
        entityNodeDto.setEntityDto((OrganizationDTO) PoXsnapshotHelper.createSnapshot(org));
  
        IdentifiedOrganization idOrg1 = new IdentifiedOrganization();
        idOrg1.setStatus(RoleStatus.PENDING);
        idOrg1.setId(3L);        
        
        IdentifiedOrganization idOrg2 = new IdentifiedOrganization();
        idOrg2.setStatus(RoleStatus.NULLIFIED);
        idOrg2.setDuplicateOf(idOrg1);
        idOrg2.setId(2L);

        CorrelationDto[] scopers = new CorrelationDto[1];
        scopers[0] = (CorrelationDto) PoXsnapshotHelper.createSnapshot(idOrg2);
        
        entityNodeDto.setPlayers(null);
        entityNodeDto.setScopers(scopers);
        
        testContext.returnValue = entityNodeDto;
        try {
            interceptor.checkForNullified(testContext);
            fail("Expected NullifiedEntityNodeException");
        } catch (NullifiedEntityException ex) {
            Map<Ii, Ii> nullifiedEntities = ex.getNullifiedEntities();
            assertEquals(nullifiedEntities.size(), 1);
            
            assertTrue(ex.getNullifiedEntities().containsKey(
                    scopers[0].getIdentifier().getItem().iterator().next()));
            Ii duplicateIi =
                    ex.getNullifiedEntities().get(
                            scopers[0].getIdentifier().getItem().iterator().next());
            assertNotNull(duplicateIi);
            assertEquals(duplicateIi.getExtension(), idOrg1.getId().toString());
        }
        
    }
    
    @Test
    public void testCheckForNullifiedSuccess() throws Exception {
        EntityNodeDto entityNodeDto = new EntityNodeDto();

        Organization org = new Organization();
        org.setId(1L);        
        
        entityNodeDto.setEntityDto((OrganizationDTO) PoXsnapshotHelper.createSnapshot(org));

        ResearchOrganization ro = new ResearchOrganization();
        ro.setStatus(RoleStatus.PENDING);
        CorrelationDto[] players = new CorrelationDto[1];
        players[0] = (CorrelationDto) PoXsnapshotHelper.createSnapshot(ro);

        IdentifiedOrganization idOrg = new IdentifiedOrganization();
        idOrg.setStatus(RoleStatus.PENDING);
        CorrelationDto[] scopers = new CorrelationDto[1];
        scopers[0] = (CorrelationDto) PoXsnapshotHelper.createSnapshot(idOrg);
        
        entityNodeDto.setPlayers(players);
        entityNodeDto.setScopers(scopers);
        
        testContext.returnValue = entityNodeDto;
        
        assertEquals(testContext.returnValue, interceptor.checkForNullified(testContext));
    }
    
    @Test
    public void testCheckForNullifiedWithErrorCombinations() throws Exception {
        EntityNodeDto entityNodeDto = new EntityNodeDto();

        Person p = new Person();
        p.setId(1L);
        entityNodeDto.setEntityDto((PersonDTO) PoXsnapshotHelper.createSnapshot(p));

        Person p2 = new Person();
        p2.setId(2L);
        p2.setStatusCode(EntityStatus.NULLIFIED);
        p2.setDuplicateOf(p);
        
        ClinicalResearchStaff crs1 = new ClinicalResearchStaff();
        crs1.setId(3L);
        crs1.setStatus(RoleStatus.PENDING);
        
        ClinicalResearchStaff crs2 = new ClinicalResearchStaff();
        crs2.setId(4L);
        crs2.setStatus(RoleStatus.NULLIFIED);
        crs2.setDuplicateOf(crs1);

        CorrelationDto[] players = new CorrelationDto[2];
        players[0] = (CorrelationDto) PoXsnapshotHelper.createSnapshot(crs1);
        players[1] = (CorrelationDto) PoXsnapshotHelper.createSnapshot(crs2);
        
        entityNodeDto.setPlayers(players);
        entityNodeDto.setScopers(null);
        
        testContext.returnValue = entityNodeDto;
        try {
            interceptor.checkForNullified(testContext);
            fail("Expected NullifiedEntityNodeException");
        } catch (NullifiedEntityException ex) {
            Map<Ii, Ii> nullifiedEntities = ex.getNullifiedEntities();
            assertEquals(nullifiedEntities.size(), 1);
            
            assertTrue(ex.getNullifiedEntities().containsKey(
                    players[1].getIdentifier().getItem().iterator().next()));
            Ii duplicateIi =
                    ex.getNullifiedEntities().get(
                            players[1].getIdentifier().getItem().iterator().next());
            assertNotNull(duplicateIi);
            assertEquals(duplicateIi.getExtension(), crs1.getId().toString());
        }
        
    }
    

}
