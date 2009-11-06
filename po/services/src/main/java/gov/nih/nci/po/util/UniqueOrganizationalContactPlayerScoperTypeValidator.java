package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.AbstractOrganizationalContact;
import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.RoleStatus;

import java.io.Serializable;
import java.sql.Connection;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.validator.Validator;

import com.fiveamsolutions.nci.commons.util.CGLIBUtils;

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
    public void initialize(UniqueOrganizationalContactPlayerScoperType parameters) {
        // nothing to do here
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid(Object value) {
        if (!(value instanceof AbstractOrganizationalContact)) {
            return false;
        }
        AbstractOrganizationalContact aoc = (AbstractOrganizationalContact) value;

        if (aoc.getPlayer() == null) {
            return true;
        }

        Session s = null;
        try {
            Connection conn = PoHibernateUtil.getCurrentSession().connection();
            s = PoHibernateUtil.getHibernateHelper().getSessionFactory().openSession(conn);
            Criteria c = s.createCriteria(CGLIBUtils.unEnhanceCBLIBClass(aoc.getClass()));
            c.add(Restrictions.eq("player", aoc.getPlayer()));
            c.add(Restrictions.eq("scoper", aoc.getScoper()));
            c.add(Restrictions.eq("type", aoc.getType()));
            c.add(Restrictions.ne("status", RoleStatus.NULLIFIED));
            AbstractPersonRole other = (AbstractPersonRole) c.uniqueResult();
            return (other == null || other.getId().equals(aoc.getId()));
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}
