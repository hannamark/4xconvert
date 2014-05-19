package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.FundingMechanism;
import gov.nih.nci.po.data.bo.OrganizationalContactType;
import gov.nih.nci.po.data.bo.OversightCommitteeType;
import gov.nih.nci.po.data.bo.ResearchOrganizationType;
import gov.nih.nci.po.data.bo.FundingMechanism.FundingMechanismStatus;
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
    private FundingMechanism fundingMechanism;

    private User user;

    public Country getDefaultCountry() {
        return defaultCountry;
    }

    public ResearchOrganizationType getResearchOrgType() {
        return researchOrgType;
    }

    public void setResearchOrgType(ResearchOrganizationType researchOrgType) {
        this.researchOrgType = researchOrgType;
    }

    public FundingMechanism getFundingMechanism() {
        return fundingMechanism;
    }

    public void setFundingMechanism(FundingMechanism fundingMechanism) {
        this.fundingMechanism = fundingMechanism;
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

    public OversightCommitteeType getOversightCommitee() {
        return oversightCommitee;
    }

    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void loadData() {
        defaultCountry = CountryTestUtil.save(new Country("United States", "840", "US", "USA"));

        PoHibernateUtil.getCurrentSession().save(new OrganizationalContactType("IRB"));
        researchOrgType = new ResearchOrganizationType("DCY", "Cancer Center");
        PoHibernateUtil.getCurrentSession().saveOrUpdate(researchOrgType);
        researchOrgType = new ResearchOrganizationType("CLC", "Cancer Center");
        PoHibernateUtil.getCurrentSession().saveOrUpdate(researchOrgType);
        researchOrgType = new ResearchOrganizationType("CGP", "Cancer Center");
        PoHibernateUtil.getCurrentSession().saveOrUpdate(researchOrgType);
        researchOrgType = new ResearchOrganizationType("CSM", "Cancer Center");
        PoHibernateUtil.getCurrentSession().saveOrUpdate(researchOrgType);
        researchOrgType = new ResearchOrganizationType("NWK", "Cancer Center");
        PoHibernateUtil.getCurrentSession().saveOrUpdate(researchOrgType);
        researchOrgType = new ResearchOrganizationType("RSB", "Cancer Center");
        PoHibernateUtil.getCurrentSession().saveOrUpdate(researchOrgType);
        researchOrgType = new ResearchOrganizationType("COP", "Cancer Center");
        PoHibernateUtil.getCurrentSession().saveOrUpdate(researchOrgType);
        researchOrgType = new ResearchOrganizationType("NCP", "Cancer Center");
        PoHibernateUtil.getCurrentSession().saveOrUpdate(researchOrgType);

        oversightCommitee = new OversightCommitteeType("Ethics Committee");
        PoHibernateUtil.getCurrentSession().saveOrUpdate(oversightCommitee);
        fundingMechanism =
                new FundingMechanism("B09", "Mental Health Services Block Grant", "Block Grants",
                        FundingMechanismStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().saveOrUpdate(fundingMechanism);
        researchOrgType = new ResearchOrganizationType("CCR", "Cancer Center");
        researchOrgType.getFundingMechanisms().add(fundingMechanism);
        PoHibernateUtil.getCurrentSession().saveOrUpdate(researchOrgType);

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
