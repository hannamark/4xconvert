package gov.nih.nci.pa.dao;

import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Person DAO for accessing Person Obj.
 *
 * @author Naveen Amiruddin
 * @since 07/28/2007 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class PersonDAO {
    
    private static final Logger LOG  = Logger.getLogger(PersonDAO.class);
    
    /**
     * returns a list of PI name who have been associated with study protocol.
     * @return PersonDTOs
     * @throws PAException PAException
     */
    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    public List<Person> getAllPrincipalInvestigators() throws PAException {
        
        LOG.debug("Entering getAllPrincipalInvestigators");
        List<Person> persons = null;
        try {
            StringBuffer hql = new StringBuffer();
            hql.append(" select distinct p from Person  p " 
            + " join p.healthCareProviders as hc "
            + " join hc.studyContacts as sc" 
            + " join sc.studyProtocol as sp" 
            + " join sc.studyContactRoles as scr" 
            + " where scr.studyContactRoleCode = '" 
            + StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR + "'");
            Session session = HibernateUtil.getCurrentSession();
            persons = session.createQuery(hql.toString()).list();
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in getAllPrincipalInvestigators ", hbe);
            throw new PAException(" Hibernate exception in getAllPrincipalInvestigators ", hbe);
        }  finally {
            LOG.debug("Leaving getAllPrincipalInvestigators");
        }
        
        return persons; 
    }

}
