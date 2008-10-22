package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.StudyParticipationContact;
import gov.nih.nci.pa.dto.PAHealthCareProviderDTO;
import gov.nih.nci.pa.iso.dto.PersonWebDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Harsha
 * 
 * @since 10/09/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
public class PAHealthCareProviderServiceBean implements PAHealthCareProviderRemote {
    private static final Logger LOG = Logger.getLogger(PAHealthCareProviderServiceBean.class);
    private static final int THREE = 3;
   

    /**
     * 
     * @param identifier to search
     * @return Long id
     * @throws PAException on error.
     */
    public Long findHcpByIdentifier(Long identifier) throws PAException {
        LOG.debug("Entering  findHcpByIdentifier");
        Session session = null;
       
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            List<Object> queryList = new ArrayList<Object>();
            String queryString = "select hcp from HealthCareProvider as hcp where hcp.identifier = '" + identifier
                    + "'";
            query = session.createQuery(queryString);
            queryList = query.list();
            HealthCareProvider searchResult = null;
            for (int i = 0; i < queryList.size(); i++) {
                searchResult = (HealthCareProvider) queryList.get(i);
                if (searchResult == null) {
                    return null;
                }
                return searchResult.getId();
            }
            LOG.debug("Leaving  findHcpByIdentifier");
            return null;
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in findHcpByIdentifier ", hbe);
            throw new PAException(" Hibernate exception in findHcpByIdentifier ", hbe);
        } finally {
            session.flush();
        }
    }

    /**
     * Gets the person primary key (PA World) using the PO id (PO World).
     * 
     * @param poIdentifier for search
     * @param roleCode for the role code
     * @param spId as Study Participation Id
     * @return Long the primary key identifier
     * @throws PAException on error
     */
    public Long getStudyParticationContactByPersonAndSPId(Long poIdentifier, Long spId, String roleCode)
            throws PAException {
        LOG.debug("Entering  getPersonById");
        Session session = null;
       
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            List<Object> queryList = new ArrayList<Object>();
            String queryString = "select sp, spc, hcp from StudyParticipation as sp"
                    + " join sp.studyParticipationContacts as spc" + " join spc.healthCareProvider as hcp"
                    + " where sp.id = '" + spId + "' and hcp.identifier = '" + poIdentifier + "'"
                    + " and spc.roleCode = '" + roleCode + "'";
            query = session.createQuery(queryString);
            queryList = query.list();
            Object[] searchResult = null;
            StudyParticipationContact spc = null;
            if (!queryList.isEmpty()) {
                for (int i = 0; i < queryList.size(); i++) {
                    searchResult = (Object[]) queryList.get(i);
                    spc = (StudyParticipationContact) searchResult[1];
                    break;
                }
                LOG.debug("Leaving  getPersonById");
                return spc.getId();
            } else {
                LOG.debug("Leaving  getPersonById");
                return null;
            }
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in getPersonById ", hbe);
            throw new PAException(" Hibernate exception in getPersonById ", hbe);
        } finally {
            session.flush();
        }
    }

    /**
     * 
     * @param dto to be saved
     * @return Long id of the Health care provider
     * @throws PAException on error.
     */
    public Long createPAHealthCareProvider(PAHealthCareProviderDTO dto) throws PAException {
        LOG.debug("Entering  createPAHealthCareProvider");
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            Person person = new Person();
            person.setFirstName(dto.getFirstName());
            person.setLastName(dto.getLastName());
            person.setMiddleName(dto.getMiddleName());
            session.saveOrUpdate(person);
            HealthCareProvider careProvider = new HealthCareProvider();
            Long temp = dto.getIdentifier();
            careProvider.setIdentifier(temp);
            careProvider.setPerson(person);
            session.saveOrUpdate(careProvider);
            session.flush();
            LOG.debug("Leaving  createPAHealthCareProvider");
            return careProvider.getId();
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in createPAHealthCareProvider ", hbe);
            return null;
        }
    }

    /**
     * 
     * @param id to search
     * @param roleCd to search
     * @return List of personWebDTO
     * @throws PAException on error
     */
    public List<PersonWebDTO> getPersonsByStudyParticpationId(Long id, String roleCd) throws PAException {
        LOG.debug("Entering  getPersonsByStudyParticpationId");
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();            
            List<Object> queryList = new ArrayList<Object>();
            Query query = null;
            String queryStr = "select sp , spc , hcp , p from StudyParticipation as sp  "
                    + " join sp.studyParticipationContacts as spc " + " join spc.healthCareProvider as hcp "
                    + " join hcp.person as p " + " where sp.id = " + id + " and spc.roleCode = '" + roleCd + "'";
            query = session.createQuery(queryStr);
            queryList = query.list();
            session.flush();
            LOG.debug("Leaving  getPersonsByStudyParticpationId");
            return createPersonWebDTO(queryList);
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in getPersonsByStudyParticpationId ", hbe);
            return null;
        }
    }

    private List<PersonWebDTO> createPersonWebDTO(List<Object> queryList) {
        List<PersonWebDTO> retList = new ArrayList();
        PersonWebDTO personWebDTO;
        Object[] searchResult = null;
        for (int i = 0; i < queryList.size(); i++) {
            searchResult = (Object[]) queryList.get(i);
            if (searchResult == null) {
                break;
            }
            personWebDTO = new PersonWebDTO();
            personWebDTO.setFirstName(((Person) searchResult[THREE]).getFirstName());
            personWebDTO.setLastName(((Person) searchResult[THREE]).getLastName());
            personWebDTO.setId(((StudyParticipationContact) searchResult[1]).getId());           
            personWebDTO.setRoleName((((StudyParticipationContact) searchResult[1]).getRoleCode()));
            retList.add(personWebDTO);
        }
        return retList;
    }

    /**
     *  
     * @param id the study participation id
     * @return Long as the identifier
     * @throws PAException on error
     */
    public Long getIdentifierBySPCId(Long id) throws PAException {
        LOG.debug("Entering  getIdentifierBySPCId");
        Session session = null;
        HealthCareProvider careProvider = null;
        try {
            session = HibernateUtil.getCurrentSession();           
            List<Object> queryList = new ArrayList<Object>();
            Query query = null;
            String queryStr = "select spc , hcp from StudyParticipationContact as spc" 
                    + " join spc.healthCareProvider as hcp"
                    + " where spc.id = " + id
                    + " and spc.roleCode <> 'STUDY_PRIMARY_CONTACT'";
            query = session.createQuery(queryStr);
            queryList = query.list();           
            Object[] searchResult = null;
            for (int i = 0; i < queryList.size(); i++) {
                searchResult = (Object[]) queryList.get(i);
                if (searchResult == null) {
                    return null;
                }
                careProvider = ((HealthCareProvider) searchResult[1]);
                return careProvider.getIdentifier();
            }
            LOG.debug("Leaving  getIdentifierBySPCId");
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in getIdentifierBySPCId ", hbe);
            return null;
        } finally {
            session.flush();
        }     
        return null;
    }
    
}
