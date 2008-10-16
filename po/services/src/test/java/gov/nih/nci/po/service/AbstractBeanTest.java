package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.OversightCommitteeType;
import gov.nih.nci.po.data.bo.QualifiedEntityType;
import gov.nih.nci.po.data.bo.ResearchOrganizationType;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.Date;
import java.util.Random;

import org.junit.Before;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

public abstract class AbstractBeanTest extends AbstractHibernateTestCase {

    private Country defaultCountry;
    private OversightCommitteeType oversightCommitee;
    private ResearchOrganizationType researchOrgType;
    private QualifiedEntityType qualifiedEntityType;

    public Country getDefaultCountry() {
        return defaultCountry;
    }

    public ResearchOrganizationType getResearchOrgType() {
        return researchOrgType;
    }

    public void setResearchOrgType(ResearchOrganizationType researchOrgType) {
        this.researchOrgType = researchOrgType;
    }

    /**
     * @param defaultCountry the defaultCountry to set
     */
    public void setDefaultCountry(Country defaultCountry) {
        this.defaultCountry = defaultCountry;
    }

    public void setOversightCommitee(OversightCommitteeType oversightCommitee) {
        this.oversightCommitee = oversightCommitee;
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

        oversightCommitee = new OversightCommitteeType("Ethics Committee");
        PoHibernateUtil.getCurrentSession().saveOrUpdate(oversightCommitee);
        researchOrgType = new ResearchOrganizationType("Cancer Center");
        PoHibernateUtil.getCurrentSession().saveOrUpdate(researchOrgType);
        qualifiedEntityType = new QualifiedEntityType("MD");
        PoHibernateUtil.getCurrentSession().saveOrUpdate(qualifiedEntityType);

        user = new User();
        user.setLoginName("unittest" + new Random().nextLong());
        user.setFirstName("first");
        user.setLastName("last");
        user.setUpdateDate(new Date());
        PoHibernateUtil.getCurrentSession().save(user);
        UsernameHolder.setUser(user.getLoginName());

        PoHibernateUtil.getCurrentSession().flush();
    }
}