package gov.nih.nci.po.util;

import com.fiveamsolutions.nci.commons.util.ProxyUtils;
import gov.nih.nci.po.data.bo.AbstractOrganizationalContact;
import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.RoleStatus;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.validator.Validator;

import java.io.Serializable;
import java.sql.Connection;

/**
 * Used to validate that the title of an organizational contact is unique for the scoper, ignoring NULLIFIED records.
 *
 * @author slustbader
 */
public class UniqueOrganizationalContactTitleScoperTypeValidator implements
        Validator<UniqueOrganizationalContactTitleScoperType>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(UniqueOrganizationalContactTitleScoperType parameters) {
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
        AbstractOrganizationalContact aoc = (AbstractOrganizationalContact) value;
        // UniquePlayerScoperValidator will validate if there's no title
        if (StringUtils.isBlank(aoc.getTitle())) {
            return true;
        }

        AbstractOrganizationalContact other = findMatches(aoc);
        return isValid(aoc, other);
    }

    /**
     * Returns conflicting {@link AbstractOrganizationalContact} if validation fails.  Otherwise returns null.
     *
     * @param aoc role to check for conflicting role
     * @return AbstractOrganizationalContact if a conflict exists
     */
    public AbstractOrganizationalContact getConflictingRole(AbstractOrganizationalContact aoc) {
        AbstractOrganizationalContact other = findMatches(aoc);
        if (!isValid(aoc, other)) {
            return other;
        }
        return null;
    }

    private boolean isValid(AbstractPersonRole input, AbstractPersonRole match) {
        return (match == null || match.getId().equals(input.getId()) || StringUtils
                .isBlank(((AbstractOrganizationalContact) match).getTitle()));
    }

    private AbstractOrganizationalContact findMatches(AbstractOrganizationalContact aoc) {
        Session s = null;
        try {
            Connection conn = PoHibernateUtil.getCurrentSession().connection();
            s = PoHibernateUtil.getHibernateHelper().getSessionFactory().openSession(conn);
            Criteria c = s.createCriteria(ProxyUtils.unEnhanceCGLIBClass(aoc.getClass()));
            c.add(Restrictions.eq("title", aoc.getTitle()));
            c.add(Restrictions.eq("scoper", aoc.getScoper()));
            c.add(Restrictions.eq("type", aoc.getType()));
            c.add(Restrictions.ne("status", RoleStatus.NULLIFIED));
            return  (AbstractOrganizationalContact) c.uniqueResult();
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

}
