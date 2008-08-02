package gov.nih.nci.pa.dao;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * DAO for accessing StudySite.
 *
 * @author Naveen Amiruddin
 * @since 06/22/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */

public class StudySiteDAO {

    private static final Logger LOG  = Logger.getLogger(StudySiteDAO.class);
    
    /**
     * get all lead organization that have been associated with study protocols.
     * @throws PAException p   
     * @return OrganizationDTO list of OrganizationDTO
     * 
     */
    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    public List<Organization> getOrganizationsAssociatedWithStudyProtocol()  
    throws PAException {
        LOG.debug("Entering getOrganizationsAssociatedWithStudyProtocol ");

        Session session = null;
        List<Organization> organizations = null;
        try {
            session = HibernateUtil.getCurrentSession();
            StringBuffer hql = new StringBuffer();
            hql.append(" Select distinct o from Organization o  join o.studySites as ss " 
            + "  where ss.leadOrganizationIndicator = '" + Boolean.TRUE + "'");
            organizations =  session.createQuery(hql.toString()).list();
        } catch (HibernateException hbe) {
            LOG.error("  Hibernate exception while fetching " 
                     + "getOrganizationsAssociatedWithStudyProtocol ", hbe);
            throw new PAException(" Hibernate exception while fetching " 
                    + "getOrganizationsAssociatedWithStudyProtocol ", hbe);
        } finally {
            LOG.debug("Leaving getOrganizationsAssociatedWithStudyProtocol ");
        }
        return organizations;
    }

}
