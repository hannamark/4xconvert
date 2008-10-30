package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.PersonDTO;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * 
 * @author Naveen Amiruddin
 * 
 *
 */
@Stateless
public class PAPersonServiceBean implements PAPersonServiceRemote {

    private static final Logger LOG  = Logger.getLogger(PAPersonServiceBean.class);

    /**
     * returns a list of PI name who have been associated with study protocol. 
     * @return list PersonDTO   
     * @throws PAException on error 
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PersonDTO> getAllPrincipalInvestigators() throws PAException {
       LOG.info("Entering getAllPrincipalInvestigators");
       List<Person> persons = generateDistinctPersonResults();
       LOG.info("Leaving getAllPrincipalInvestigators");
       return createPersonDTO(persons);
    }
    
    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    private List<Person> generateDistinctPersonResults()  
    throws PAException {
        LOG.debug("Entering generateDistinctPersonResults ");

        Session session = null;
        List<Person> persons = null;
        
        try {
            session = HibernateUtil.getCurrentSession();
            StringBuffer hql = new StringBuffer();
            hql.append(" select distinct p from Person  p " 
            + " join p.healthCareProviders as hc "
            + " join hc.studyContacts as sc" 
            + " join sc.studyProtocol as sp" 
            + " where sc.roleCode = '" 
            + StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR + "'");
            session = HibernateUtil.getCurrentSession();
            persons = session.createQuery(hql.toString()).list();

        } catch (HibernateException hbe) {
            LOG.error("  Hibernate exception while fetching " 
                     + "generateDistinctPersonResults ", hbe);
            throw new PAException(" Hibernate exception while fetching " 
                    + "generateDistinctPersonResults ", hbe);
        } finally {
            LOG.debug("Leaving generateDistinctPersonResults ");
        }
        return persons;
    }
    
    private List<PersonDTO> createPersonDTO(List<Person> persons) {
        LOG.debug("Entereing persons");
        
        List<PersonDTO> personDTOs = new ArrayList<PersonDTO>();
        PersonDTO personDTO = null;
        for (int i = 0; i < persons.size(); i++) {
            personDTO = new PersonDTO();
            personDTO.setId(((Person) persons.get(i)).getId());
            personDTO.setFirstName(((Person) persons.get(i)).getFirstName());
            personDTO.setLastName(((Person) persons.get(i)).getLastName());
            personDTO.setMiddleName(((Person) persons.get(i)).getMiddleName());
            personDTOs.add(personDTO);
        }
        LOG.debug("Leaving persons");
        return personDTOs;
    }
    
    
    
}
