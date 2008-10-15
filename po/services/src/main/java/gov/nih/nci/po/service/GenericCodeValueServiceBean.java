package gov.nih.nci.po.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.po.data.bo.CodeValue;
import gov.nih.nci.po.util.PoHibernateUtil;

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
        return (T) q.uniqueResult();
    }

}
