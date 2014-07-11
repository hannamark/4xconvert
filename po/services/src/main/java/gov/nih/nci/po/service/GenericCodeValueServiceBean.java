package gov.nih.nci.po.service;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.po.data.bo.CodeValue;
import gov.nih.nci.po.util.PoHibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;


/**
 * Implementation of interface.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GenericCodeValueServiceBean implements GenericCodeValueServiceLocal {

    /**
     * {@inheritDoc}
     */
    public <T extends CodeValue> T getByCode(Class<T> clz, Cd code) {
        return getByCode(clz, code.getCode());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <T extends CodeValue> T getByCode(Class<T> clz, String code) {
        Session s = PoHibernateUtil.getCurrentSession();
        Query q = s.createQuery("FROM " + clz.getName() + " oct WHERE oct.code = :code");
        q.setString("code", code);
        T ret = (T) q.uniqueResult();
        if (ret == null) {
            q = s.createQuery("SELECT code FROM " + clz.getName());
            List<String> values = q.list();
            throw new IllegalArgumentException("allowed values for " + clz.getSimpleName()
                    + " are: " + values.toString());
        }
        return ret;
    }
    /**
     * {@inheritDoc}
     */
    public <T extends CodeValue> List<T> list(Class<T> clz) {
        return list(clz, null);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <T extends CodeValue> List<T> list(Class<T> clz, String orderBy) {
        Session s = PoHibernateUtil.getCurrentSession();
        StringBuilder querySb  = new StringBuilder("FROM ");
        querySb.append(clz.getName());
        if (orderBy != null) {
            querySb.append(" ORDER BY lower(");
            querySb.append(orderBy);
            querySb.append(")");
        }
        Query q = s.createQuery(querySb.toString());
        return q.list();
    }

}
