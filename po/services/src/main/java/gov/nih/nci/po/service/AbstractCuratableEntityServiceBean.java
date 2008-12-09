package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.CuratableEntity;
import gov.nih.nci.po.data.bo.CuratableRole;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.util.PoHibernateUtil;
import java.util.List;
import java.util.Set;
import javax.jms.JMSException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

/**
 * @param <T> Entity type.
 * 
 * @author gax
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public abstract class AbstractCuratableEntityServiceBean <T extends CuratableEntity>
        extends AbstractCuratableServiceBean<T> {

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
    }

    private void cascadeStatusChange(T e) {
        if (e.getPriorEntityStatus() != e.getStatusCode()) {
            Session s = PoHibernateUtil.getCurrentSession();
            switch(e.getStatusCode()) {
                case NULLIFIED:
                    for (CuratableRole x : getAssociatedRoles(e, s)) {
                        x.setStatus(RoleStatus.NULLIFIED);
                        s.update(x);
                    }
                    break;
                case INACTIVE:
                    for (CuratableRole x : getAssociatedRoles(e, s)) {
                        if (x.getStatus() == RoleStatus.ACTIVE) {
                            x.setStatus(RoleStatus.SUSPENDED);
                            s.update(x);
                        }
                    }
                    break;
                default:
            }
        }
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
    protected <C extends CuratableRole> List<C> getAssociatedRoles(
            Long entityId, Class<C> type, String property, Session s) {
        
        Criteria c = s.createCriteria(type);
        LogicalExpression and = Restrictions.and(
                Restrictions.eq(property, entityId),
                Restrictions.ne("status", RoleStatus.NULLIFIED));
        c.add(and);
        return c.list();
    }

    /**
     * @param entity the player or scoper
     * @param s session to pull from
     * @return all associated roles.
     */
    protected abstract Set<? extends CuratableRole> getAssociatedRoles(T entity, Session s);
}
