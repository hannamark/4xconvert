package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.RoleStatus;

import java.io.Serializable;
import java.sql.Connection;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.validator.Validator;

import com.fiveamsolutions.nci.commons.util.CGLIBUtils;

/**
 * Used to validate that the scoper is unique for the given player, ignoring NULLIFIED records.
 *
 * @author smatyas
 */
public class UniquePlayerScoperValidator implements Validator<UniquePlayerScoper>, Serializable {

    private static final long serialVersionUID = 1L;
    private String friendlyName;

    /**
     * @return friendly name for type being validated
     */
    public String getFriendlyName() {
        return friendlyName;
    }

    /**
     * {@inheritDoc}
     */
    public void initialize(UniquePlayerScoper parameters) {
        this.friendlyName = parameters.friendlyName();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid(Object value) {
        if (!(value instanceof AbstractPersonRole)) {
            return false;
        }

        return validate((AbstractPersonRole) value);
    }

    /**
     * Performs the actual validation of player and scoper.
     * @param apr role to validate
     * @return true iff player and scoper are unique for non-nullified roles
     */
    static boolean validate(AbstractPersonRole apr) {
        Session s = null;
        try {
            Connection conn = PoHibernateUtil.getCurrentSession().connection();
            s = PoHibernateUtil.getHibernateHelper().getSessionFactory().openSession(conn);
            Criteria c = s.createCriteria(CGLIBUtils.unEnhanceCBLIBClass(apr.getClass()));
            LogicalExpression scoperPlayerComposite = Restrictions.and(Restrictions.eq("player", apr.getPlayer()),
                    Restrictions.eq("scoper", apr.getScoper()));
            c.add(Restrictions.and(Restrictions.ne("status", RoleStatus.NULLIFIED), scoperPlayerComposite));
            AbstractPersonRole other = (AbstractPersonRole) c.uniqueResult();
            return (other == null || other.getId().equals(apr.getId()));
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

}
