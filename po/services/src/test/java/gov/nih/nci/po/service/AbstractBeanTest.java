package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.Date;
import java.util.Random;

import org.junit.Before;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

public abstract class AbstractBeanTest extends AbstractHibernateTestCase {

    private Country defaultCountry;

    public Country getDefaultCountry() {
        return defaultCountry;
    }

    /**
     * @param defaultCountry the defaultCountry to set
     */
    public void setDefaultCountry(Country defaultCountry) {
        this.defaultCountry = defaultCountry;
    }

    User user;

    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Before
    public void loadData() {
        defaultCountry = CountryTestUtil.save(new Country("United States", "840", "US", "USA"));

        user = new User();
        user.setLoginName("unittest" + new Random().nextLong());
        user.setFirstName("first");
        user.setLastName("last");
        user.setUpdateDate(new Date());
        PoHibernateUtil.getCurrentSession().save(user);
        UsernameHolder.setUser(user.getLoginName());
    }
}
