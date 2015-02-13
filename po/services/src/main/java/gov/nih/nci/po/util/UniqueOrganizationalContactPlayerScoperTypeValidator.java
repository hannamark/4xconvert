package gov.nih.nci.po.util;

import com.fiveamsolutions.nci.commons.util.ProxyUtils;
import gov.nih.nci.po.data.bo.AbstractOrganizationalContact;
import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.RoleStatus;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.validator.Validator;

import java.io.Serializable;
import java.sql.Connection;

/**
 * Validates that the player of an org contact is unique for the scoper and type, ignoring NULLIFIED records.
 *
 * @author kkanchinadam
 */
public class UniqueOrganizationalContactPlayerScoperTypeValidator implements
        Validator<UniqueOrganizationalContactPlayerScoperType>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(UniqueOrganizationalContactPlayerScoperType parameters) {
        // nothing to do here
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(Object value) {
        if (!(value instanceof AbstractOrganizationalContact)) {
            return false;
        }
        
        AbstractPersonRole apr = (AbstractPersonRole) value;
        AbstractPersonRole other = findMatches(apr);
        return isValid(apr, other);
        
    }
    
    /**
     * Returns conflicting {@link AbstractOrganizationalContact} if validation
     * fails. Otherwise returns null.
     * 
     * @param aoc
     *            role to check for conflicting role
     * @return AbstractOrganizationalContact if a conflict exists
     */
    public AbstractPersonRole getConflictingRole(AbstractPersonRole aoc) {
        AbstractPersonRole other = findMatches(aoc);
        if (!isValid(aoc, other)) {
            return other;
        }
        return null;
    }

    private boolean isValid(AbstractPersonRole input, AbstractPersonRole match) {
        return (match == null || match.getId().equals(input.getId()) || match.getPlayer() == null);
    }

    private AbstractPersonRole findMatches(AbstractPersonRole apr) {
        Session s = null;
        AbstractOrganizationalContact aoc = (AbstractOrganizationalContact) apr;

        try {
            Connection conn = PoHibernateUtil.getCurrentSession().connection();
            s = PoHibernateUtil.getHibernateHelper().getSessionFactory().openSession(conn);
            Criteria c = s.createCriteria(ProxyUtils.unEnhanceCGLIBClass(aoc.getClass()));
            c.add(Restrictions.eq("player", aoc.getPlayer()));
            c.add(Restrictions.eq("scoper", aoc.getScoper()));
            c.add(Restrictions.eq("type", aoc.getType()));
            c.add(Restrictions.ne("status", RoleStatus.NULLIFIED));
            return (AbstractPersonRole) c.uniqueResult();
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}
