package gov.nih.nci.po.service;

import com.fiveamsolutions.nci.commons.util.JndiUtils;
import com.fiveamsolutions.nci.commons.util.ProxyUtils;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.CuratableEntity;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Overridable;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.external.CtepOrganizationImporter;
import gov.nih.nci.po.util.CsmUserUtil;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoServiceUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import javax.jms.JMSException;
import java.util.List;
import java.util.Set;

/**
 * @param <T> Entity type.
 *
 * @author gax
 */
public abstract class AbstractCuratableEntityServiceBean <T extends CuratableEntity<?, ?>>
        extends AbstractCuratableServiceBean<T> {

    private static final String UNCHECKED = "unchecked";

    /**
     * property used in query.
     */
    protected static final String PLAYER_ID = "player.id";

    /**
     * property used in query.
     */
    protected static final String SCOPER_ID = "scoper.id";

    /**
     * {@inheritDoc}
     */
    @Override
    public void curate(T e) throws JMSException {
        cascadeStatusChange(e);
        super.curate(e);
        
        if (e.getPriorEntityStatus() != e.getStatusCode() && e.getStatusCode() == EntityStatus.ACTIVE) {
            activateCtepRoles(e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void curateWithoutCRProcessing(T e) throws JMSException {
        cascadeStatusChange(e);
        super.curateWithoutCRProcessing(e);
        
        if (e.getPriorEntityStatus() != e.getStatusCode() && e.getStatusCode() == EntityStatus.ACTIVE) {
            activateCtepRoles(e);
        }
    }

    private void cascadeStatusChange(T e) throws JMSException {
        if (e.getPriorEntityStatus() != e.getStatusCode()) {
            Session s = PoHibernateUtil.getCurrentSession();
            switch(e.getStatusCode()) {
                case NULLIFIED:
                    cascadeStatusChangeNullified(e, s);
                    break;
                case INACTIVE:
                    suspendCorrelations(e, s);
                    break;
                default:
            }
        }
    }
    
    @SuppressWarnings(UNCHECKED)
    private void suspendCorrelations(T e, Session s) throws JMSException {
        for (Correlation x : getAssociatedRoles(e, s)) {
            if (x.getStatus() == RoleStatus.ACTIVE) {
                x.setStatus(RoleStatus.SUSPENDED);
                GenericStructrualRoleServiceLocal service = getServiceForRole(x.getClass());

                boolean needsOverride = handleRoleUpdateOverride(x);

                if (needsOverride) {
                    ((Overridable) x).setOverriddenBy(CsmUserUtil.getUser(UsernameHolder.getUser()));
                }

                service.curate(x);
            }
        }
    }

    private boolean handleRoleUpdateOverride(Correlation x) {
        boolean result = false;

        boolean isOverridable = x instanceof Overridable;

        if (isOverridable) {
            //is it created by me?
            boolean createdByMe
                    = x instanceof AbstractRole && PoServiceUtil.isCreatedByCurrentUser((AbstractRole) x);

            //is it overidden by me?
            boolean overridden
                    = isOverridable && ((Overridable) x).getOverriddenBy() != null;

            boolean overriddenByMe
                    = isOverridable && PoServiceUtil.isOverriddenByCurrentUser((Overridable) x);

            if (!createdByMe || (overridden && !overriddenByMe)) {
                result = true;
            }
        }

       return result;
    }

    /**
     * Activate any pending CTEP roles associated with the given entity.
     * @param e entity checking associations.
     * @throws JMSException thrown when updating.
     */
    protected abstract void activateCtepRoles(T e) throws JMSException;
    
    /**
     * Check if structural role is from owned by ctep.
     * @param x structural role.
     * @return boolean.
     */
    protected boolean isCtepRole(Correlation x) {
        for (Ii ii : x.getOtherIdentifiers()) {
            if (CtepOrganizationImporter.CTEP_ORG_ROOT.equals(ii.getRoot())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param e entity to cascade status changes to roles for when NULLIFIED
     * @param s hibernate session
     * @throws JMSException if a problem occurs while publishing announcements
     */
    @SuppressWarnings(UNCHECKED)
    protected void cascadeStatusChangeNullified(T e, Session s) throws JMSException {
        for (Correlation x : getAssociatedRoles(e, s)) {
            x.setStatus(RoleStatus.NULLIFIED);
            GenericStructrualRoleServiceLocal service = getServiceForRole(x.getClass());
            service.curate(x);
        }
    }

    /**
     * @param <R> the type of {@link Correlation}
     * @param roleType class type of <R>
     * @return the Local service
     */
    @SuppressWarnings({ "PMD.AvoidThrowingRawExceptionTypes", UNCHECKED })
    protected <R extends Correlation> GenericStructrualRoleServiceLocal<R> getServiceForRole(Class<R> roleType) {
        String className = ProxyUtils.unEnhanceCGLIBClass(roleType).getSimpleName();
        String serviceName = String.format("java:app/po-services/%sServiceBean", className);
        return (GenericStructrualRoleServiceLocal<R>) JndiUtils.lookup(serviceName);
    }

    /**
     * Helper.
     * @param <C> role type.
     * @param entityId the entity id who playes or scopes the role.
     * @param type the role type.
     * @param property <code>scoper.id</code> or <code>player.id</code>
     * @param s the session to use for the query.
     * @return list of roles associated to the entity.
     */
    @SuppressWarnings(UNCHECKED)
    protected <C extends Correlation> List<C> getAssociatedRoles(Long entityId, Class<C> type, String property,
            Session s) {

        Criteria c = s.createCriteria(type);
        LogicalExpression and =
                Restrictions.and(Restrictions.eq(property, entityId), Restrictions.ne("status", RoleStatus.NULLIFIED));
        c.add(and);
        return c.list();
    }

    /**
     * @param entity the player or scoper
     * @param s session to pull from
     * @return all associated roles.
     */
    protected abstract Set<? extends Correlation> getAssociatedRoles(T entity, Session s);


}
