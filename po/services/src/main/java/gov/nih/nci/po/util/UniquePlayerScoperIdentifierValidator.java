package gov.nih.nci.po.util;

import com.fiveamsolutions.nci.commons.util.ProxyUtils;
import gov.nih.nci.po.data.bo.AbstractIdentifiedEntity;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.RoleStatus;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.validator.Validator;

import java.io.Serializable;
import java.sql.Connection;

/**
 * Used to validate that the scoper is unique for the given player, ignoring NULLIFIED records.
 *
 * @author smatyas
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public class UniquePlayerScoperIdentifierValidator implements Validator<UniquePlayerScoperIdentifier>, Serializable {

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
    @Override
    public void initialize(UniquePlayerScoperIdentifier parameters) {
        // no-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public boolean isValid(Object value) {
        if (!(value instanceof IdentifiedOrganization || value instanceof IdentifiedPerson)) {
            return false;
        }
        AbstractIdentifiedEntity<?> ie = (AbstractIdentifiedEntity<?>) value;
        AbstractIdentifiedEntity<?> other = findMatches(ie);
        return isValid(ie, other);
    }


    /**
     * Returns conflicting {@link AbstractIdentifiedEntity} if validation fails.  Otherwise returns null.
     *
     * @param ie role to check for conflicting role
     * @return AbstractIdentifiedEntity if a conflict exists
     */
    public AbstractIdentifiedEntity<?> getConflictingRole(AbstractIdentifiedEntity<?> ie) {
        AbstractIdentifiedEntity<?> other = findMatches(ie);
        if (!isValid(ie, other)) {
            return other;
        }
        return null;
    }

    private boolean isValid(AbstractIdentifiedEntity<?> input, AbstractIdentifiedEntity<?> match) {
        return (match == null || match.getId().equals(input.getId()));
    }

    private AbstractIdentifiedEntity<?> findMatches(AbstractIdentifiedEntity<?> ie) {
        Session s = null;
        try {
            Connection conn = PoHibernateUtil.getCurrentSession().connection();
            s = PoHibernateUtil.getHibernateHelper().getSessionFactory().openSession(conn);
            Criteria c = s.createCriteria(ProxyUtils.unEnhanceCGLIBClass(ie.getClass()));
            LogicalExpression and = Restrictions.and(Restrictions.eq("player", ie.getPlayer()),
                    Restrictions.eq("scoper", ie.getScoper()));
            and = Restrictions.and(
                    and,
                    Restrictions.eq("assignedIdentifier.extension", ie.getAssignedIdentifier().getExtension()));
            and =  Restrictions.and(
                    and,
                    Restrictions.eq("assignedIdentifier.root", ie.getAssignedIdentifier().getRoot()));
            c.add(Restrictions.and(Restrictions.ne("status", RoleStatus.NULLIFIED), and));
            return (AbstractIdentifiedEntity<?>) c.uniqueResult();
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}
