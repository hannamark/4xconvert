package gov.nih.nci.po.service;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.po.data.bo.CodeValue;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.Query;
import org.hibernate.Session;


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
        } else {
            return ret;
        }
    }
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <T extends CodeValue> List<T> list(Class<T> clz) {
        Session s = PoHibernateUtil.getCurrentSession();
        Query q = s.createQuery("FROM " + clz.getName());
        return q.list();
    }
}
