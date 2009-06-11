package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.AbstractIdentifiedEntity;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
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
@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
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
    public void initialize(UniquePlayerScoperIdentifier parameters) {
        // no-op
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public boolean isValid(Object value) {
        if (!(value instanceof IdentifiedOrganization || value instanceof IdentifiedPerson)) {
            return false;
        }
        Session s = null;
        try {
            Connection conn = PoHibernateUtil.getCurrentSession().connection();
            s = PoHibernateUtil.getHibernateHelper().getSessionFactory().openSession(conn);
            AbstractIdentifiedEntity<?> ie = (AbstractIdentifiedEntity<?>) value;
            Criteria c = s.createCriteria(CGLIBUtils.unEnhanceCBLIBClass(ie.getClass()));
            LogicalExpression and = Restrictions.and(Restrictions.eq("player", ie.getPlayer()),
                    Restrictions.eq("scoper", ie.getScoper()));
            and = Restrictions.and(
                    and,
                    Restrictions.eq("assignedIdentifier.extension", ie.getAssignedIdentifier().getExtension()));
            and =  Restrictions.and(
                    and,
                    Restrictions.eq("assignedIdentifier.root", ie.getAssignedIdentifier().getRoot()));
            c.add(Restrictions.and(Restrictions.ne("status", RoleStatus.NULLIFIED), and));
            AbstractIdentifiedEntity<?> other = (AbstractIdentifiedEntity<?>) c.uniqueResult();
            return (other == null || other.getId().equals(ie.getId()));
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

}
