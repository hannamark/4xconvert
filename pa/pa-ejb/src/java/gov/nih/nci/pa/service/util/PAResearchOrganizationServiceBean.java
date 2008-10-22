/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.dto.PAResearchOrganizationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 10/21/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class PAResearchOrganizationServiceBean 
        implements PAResearchOrganizationServiceRemote {

    private static final Logger LOG  = Logger.getLogger(PAResearchOrganizationServiceBean.class);

    /**
     * @param researchOrganization research organization
     * @return the created research organization
     * @throws PAException exception
     */
    public PAResearchOrganizationDTO create(PAResearchOrganizationDTO researchOrganization)
            throws PAException {
        if (researchOrganization.getId() != null) {
            LOG.error("Create() should not be used for updates.  ");
            throw new PAException("Create() should not be used for updates.  ");
        }
        LOG.debug("Entering create.  ");
        try {
            Session session = HibernateUtil.getCurrentSession();
            researchOrganization.setDateLastUpdated(new Date());
            ResearchOrganization ro = researchOrganization.getBo();
            session.saveOrUpdate(ro);
            LOG.debug("Leaving create.  ");
            return new PAResearchOrganizationDTO(ro);
        } catch (HibernateException hbe) {
            LOG.error("Hibernate exception in create.  ", hbe);
            throw new PAException("Hibernate exception in create.  ", hbe);
        }    
    }

    /**
     * @return list containing the PAResearchOrganizationDTO or empty list if not found
     * @param organizationId organization id
     * @throws PAException exception
     */
    public List<PAResearchOrganizationDTO> getByOrganization(Long organizationId) throws PAException {
        LOG.debug("Entering getByOrganization.  ");
        try {
            Session session = HibernateUtil.getCurrentSession();
            List<ResearchOrganization> queryList = new ArrayList<ResearchOrganization>();
            String queryString = "select ro from ResearchOrganization as ro "
                    + " join ro.organization org where org.id = :orgId ";
            Query query = session.createQuery(queryString);
            query.setParameter("orgId", organizationId);
            queryList = query.list();
            List<PAResearchOrganizationDTO> resultList = new ArrayList<PAResearchOrganizationDTO>();
            for (ResearchOrganization researchOrganization : queryList) {
                resultList.add(new PAResearchOrganizationDTO(researchOrganization));
            }
            LOG.debug("Leaving getByOrganization.  ");
            return resultList;
        } catch (HibernateException hbe) {
            LOG.error("Hibernate exception in getByOrganization.  ", hbe);
            throw new PAException("Hibernate exception in getByOrganization.  ", hbe);
        }    
    }

    /**
     * @param researchOrganizationId the research organization id
     * @return the research organization object
     * @throws PAException exception
     */
    public PAResearchOrganizationDTO get(Long researchOrganizationId)
            throws PAException {
        if (researchOrganizationId == null) {
            throw new PAException("Attempted to call getget(Long researchOrganizationId) with null argument.  ");
        }
        LOG.debug("Entering getByOrganization.  ");
        try {
            Session session = HibernateUtil.getCurrentSession();
            List<ResearchOrganization> queryList = new ArrayList<ResearchOrganization>();
            String queryString = "select ro from ResearchOrganization as ro "
                    + " where ro.id = :researchOrgId ";
            Query query = session.createQuery(queryString);
            query.setParameter("researchOrgId", researchOrganizationId);
            queryList = query.list();
            if (queryList.isEmpty()) {
                LOG.error("Research organization not found.  ");
                throw new PAException("Research organization not found.  ");
            }
            LOG.debug("Leaving getByOrganization.  ");
            return new PAResearchOrganizationDTO(queryList.get(0));
        } catch (HibernateException hbe) {
            LOG.error("Hibernate exception in getByOrganization.  ", hbe);
            throw new PAException("Hibernate exception in getByOrganization.  ", hbe);
        }    
    }
}
