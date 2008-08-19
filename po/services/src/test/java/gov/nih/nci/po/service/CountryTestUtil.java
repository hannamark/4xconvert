package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.io.Serializable;

public class CountryTestUtil {

    public static Country save(Country c) {
        Serializable countryId = PoHibernateUtil.getCurrentSession().save(c);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        return (Country) PoHibernateUtil.getCurrentSession().get(Country.class, countryId);
    }
}
