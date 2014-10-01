package gov.nih.nci.services;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.AbstractServiceBeanTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.NullifiedRoleInterceptorTest.TestInvocationContext;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.entity.NullifiedEntityInterceptorTest.OSvcBean;
import gov.nih.nci.services.entity.NullifiedEntityInterceptorTest.PSvcBean;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class NullifiedEntityNodeInterceptorTest extends AbstractServiceBeanTest {
    NullifiedEntityNodeInterceptor interceptor;
    TestInvocationContext testContext;

    @Before
    public void init() {
        interceptor = new NullifiedEntityNodeInterceptor();
        testContext = new TestInvocationContext();
    }

    @Test(expected = NullifiedEntityException.class)
    public void testCheckForNullifiedWithOrgEntity() throws Exception {
        PoHibernateUtil.getCurrentSession().beginTransaction();
        
        EntityNodeDto entityNodeDto = new EntityNodeDto();

        Organization dup = new Organization();
        dup.setId(2L);
        dup.setStatusCode(EntityStatus.PENDING);
        
        Organization org = new Organization();
        org.setStatusCode(EntityStatus.NULLIFIED);
        org.setId(1L);
        org.setDuplicateOf(dup);
        
        BusinessServiceBean bsb = new BusinessServiceBean();
        OSvcBean svcLocal = new OSvcBean();
        svcLocal.setOrgForTesting(org);
        bsb.setOrganizationServiceBean(svcLocal);
        PSvcBean psvcLocal = new PSvcBean();
        bsb.setPersonServiceBean(psvcLocal);
        testContext.target = bsb;
        
        OrganizationDTO orgDto = (OrganizationDTO) PoXsnapshotHelper.createSnapshot(org);
        
        entityNodeDto.setEntityDto(orgDto);
        
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
       
        interceptor.checkForNullified(testContext);
       
    }

    @Test(expected = NullifiedEntityException.class)
    public void testCheckForNullifiedWithPersonEntity() throws Exception {
        PoHibernateUtil.getCurrentSession().beginTransaction();
        EntityNodeDto entityNodeDto = new EntityNodeDto();

        Person dup = new Person();
        dup.setId(2L);
        dup.setStatusCode(EntityStatus.PENDING);
        
        Person per = new Person();
        per.setStatusCode(EntityStatus.NULLIFIED);
        per.setId(1L);
        per.setDuplicateOf(dup);
        
        BusinessServiceBean bsb = new BusinessServiceBean();
        OSvcBean svcLocal = new OSvcBean();
        bsb.setOrganizationServiceBean(svcLocal);
        PSvcBean psvcLocal = new PSvcBean();
        psvcLocal.setPersonForTesting(per);
        bsb.setPersonServiceBean(psvcLocal);
        testContext.target = bsb;
        
        entityNodeDto.setEntityDto((PersonDTO) PoXsnapshotHelper.createSnapshot(per));
        
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
        interceptor.checkForNullified(testContext);
    }
    
    @Test
    public void testCheckForNullifiedSuccess() throws Exception {
        PoHibernateUtil.getCurrentSession().beginTransaction();
        BusinessServiceBean bsb = new BusinessServiceBean();
        OSvcBean svcLocal = new OSvcBean();
        bsb.setOrganizationServiceBean(svcLocal);
        PSvcBean psvcLocal = new PSvcBean();
        bsb.setPersonServiceBean(psvcLocal);
            
        testContext.target = bsb;
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
    
    public static class BSvcBean implements BusinessServiceRemote {        
        
        public CorrelationNodeDTO getCorrelationByIdWithEntities(Ii id, Bl player, Bl scoper)
                throws NullifiedRoleException {
            return null;
        }

        public List<CorrelationNodeDTO> getCorrelationsByIdsWithEntities(Ii[] ids, Bl player, Bl scoper)
                throws NullifiedRoleException {
            return null;
        }

        public List<CorrelationNodeDTO> getCorrelationsByPlayerIdsWithEntities(Cd correlationType, Ii[] playerIds,
                Bl player, Bl scoper) throws NullifiedRoleException {
            return null;
        }
        public EntityNodeDto getEntityByIdWithCorrelations(Ii id, Cd[] players, Cd[] scopers)
                throws NullifiedEntityException {
            return null;
        }

        public List<CorrelationNodeDTO> searchCorrelationsWithEntities(CorrelationNodeDTO searchNode, Bl player, Bl scoper,
                LimitOffset limitOffset) throws TooManyResultsException {
            return null;
        }

        public List<EntityNodeDto> searchEntitiesWithCorrelations(EntityNodeDto searchNode, Cd[] players, Cd[] scopers,
                LimitOffset limitOffset) throws TooManyResultsException {
            return null;
        }
    }

}
