package gov.nih.nci.pa.service.util;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;

/**
 * 
 * @author Bala Nair
 * 
 */
@Stateless
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength",
    "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength", "PMD.NPathComplexity" })
public class RegistryUserServiceBean implements RegistryUserServiceRemote {

    private static final Logger LOG = Logger
            .getLogger(PAPersonServiceBean.class);

    /**
     * Create a new Registry User.
     * 
     * @param user user
     * @return user
     * @throws PAException PAException
     */
    public RegistryUser createUser(RegistryUser user) 
                    throws PAException {

        Session session = null;
        try {
            // first create the Registry user
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.flush();
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate Exception while " + "creating registry user ",
                                                            hbe);
            throw new PAException(" Hibernate exception while "
                    + "creating registry user ", hbe);
        }

        return user;

    }

    /**
     * Update an existing Registry User.
     * 
     * @param user  user
     * @return user
     * @throws PAException PAException
     */
    public RegistryUser updateUser(RegistryUser user) 
                    throws PAException {

        Session session = null;
        try {
            // first create the Registry user
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.flush();
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate Exception while updating registry user ",
                                                    hbe);
            throw new PAException(" Hibernate exception while "
                    + "updating registry user ", hbe);
        }

        return user;

    }

    /**
     * Retrieves user by login name.
     * 
     * @param loginName loginName
     * @return user
     * @throws PAException PAException
     */
    public RegistryUser getUser(String loginName) 
                            throws PAException {
        RegistryUser registryUser = null;
        Session session = null;
        List<RegistryUser> queryList = new ArrayList<RegistryUser>();
        try {
            // /first get the CSM user
            UserProvisioningManager upManager = SecurityServiceProvider
                    .getUserProvisioningManager("pa");
            User csmUser = upManager.getUser(loginName);

            // if csm user exists retrieve the registry user
            if (csmUser != null) {
                LOG.info(" CSM User ID = " + csmUser.getUserId());
                session = HibernateUtil.getCurrentSession();

                Query query = null;
                // HQL query
                String hql = "select reguser " 
                              + "from RegistryUser reguser "
                              + "where reguser.csmUserId = :csmuserId ";
                LOG.info(" query RegistryUser = " + hql);

                // construct query object
                query = session.createQuery(hql);
                query.setParameter("csmuserId", csmUser.getUserId());
                queryList = query.list();
                // there should only one user with the login name
                if (queryList != null) {
                    registryUser = queryList.get(0);
                }
            }

        } catch (HibernateException hbe) {
            LOG.error(" Hibernate Exception while retrieving user : " 
                                        + loginName, hbe);
            throw new PAException(" Hibernate exception while "
                    + "retrieving registry user :" + loginName, hbe);
        } catch (CSException cse) {
            LOG.error(" CSM Exception while retrieving  user : " 
                                        + loginName, cse);
            throw new PAException(" CSM exception while "
                    + "retrieving  user :" + loginName, cse);
        }

        return registryUser;

    }

}
