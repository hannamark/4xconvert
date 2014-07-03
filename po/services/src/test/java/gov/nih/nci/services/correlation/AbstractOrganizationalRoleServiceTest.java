package gov.nih.nci.services.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.po.data.bo.AbstractOrganizationRole;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.CuratableEntity;
import gov.nih.nci.po.data.bo.CuratableRole;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PlayedRole;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.AbstractCuratableServiceBean;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.junit.Test;

import com.fiveamsolutions.nci.commons.search.SearchCriteria;

public abstract class AbstractOrganizationalRoleServiceTest<T extends Correlation> extends
        AbstractStructrualRoleServiceTest<T> {
    
    protected abstract T getSampleCtepOwnedStructuralRole();
    
    protected CuratableRole<?, ?> createCtepOwnedSample() throws Exception {
        AbstractCuratableServiceBean<T> service = getService();
        T r = getSampleCtepOwnedStructuralRole();
        service.create(r);
        return r;
    }
    
    @Test
    public void testNestedPlayerSearchOnEmail() throws Exception {
        //Role -> Player -> Email
        AbstractCuratableServiceBean<T> service = getService();

        T hcf = getSampleStructuralRole();
        service.create(hcf);

        AbstractOrganizationRole example = (AbstractOrganizationRole) getNewStructuralRole();
        example.setPlayer(new Organization());
        example.getPlayer().getEmail().add(new Email("foo@example.com"));
        
        SearchCriteria<T> sc = new AnnotatedBeanSearchCriteria<T>((T) example);
        List<T> l = service.search(sc);
        assertNotNull(l);
        assertEquals(1, l.size());
        AbstractOrganizationRole role = (AbstractOrganizationRole) l.get(0);
        assertEquals("foo@example.com", role.getPlayer().getEmail().get(0).getValue());
       
    }
     
    /**
     * Test a simple create and get.
     */
    @Test
    public void testSimpleCreateCtepOwnedAndGet() throws Exception {
        T structuralRole = getSampleCtepOwnedStructuralRole();

        assertNull(((CuratableRole<?, ?>) structuralRole).getStatusDate());

        AbstractCuratableServiceBean<T> service = getService();

        service.create(structuralRole);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        T retrievedRole = service.getById(structuralRole.getId());
        verifyStructuralRole(structuralRole, retrievedRole);

        assertNotNull(((CuratableRole<?, ?>) retrievedRole).getStatusDate());
    }
    
    @Test
    @Override
    public void cascadePlayerStatusChange_Inactive() throws Exception {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> myType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        if (PlayedRole.class.isAssignableFrom(myType)) {
            // make everything ACTIVE
            CuratableRole<?, ?> r = createCtepOwnedSample();
            CuratableEntity<?, ?> player = ((PlayedRole<?>)r).getPlayer();
            player.setStatusCode(EntityStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().update(player);
            basicOrganization.setStatusCode(EntityStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().update(basicOrganization);
            basicPerson.setStatusCode(EntityStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().update(basicPerson);
            r.setStatus(RoleStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().update(r);
            PoHibernateUtil.getCurrentSession().flush();

            CuratableEntity<?, ?> entity = ((PlayedRole<?>)r).getPlayer();
            entity.setStatusCode(EntityStatus.INACTIVE);
            if (entity instanceof Organization){
                locator.getOrganizationService().curate((Organization)entity);
            } else {
                locator.getPersonService().curate((Person)entity);
            }
            assertEquals(RoleStatus.SUSPENDED, r.getStatus());
        }
    }
}
