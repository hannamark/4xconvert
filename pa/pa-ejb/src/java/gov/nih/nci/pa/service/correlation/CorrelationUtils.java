package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.AdxpCnt;
import gov.nih.nci.coppa.iso.AdxpCty;
import gov.nih.nci.coppa.iso.AdxpZip;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.pa.domain.AbstractEntity;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * A Service class for handing common methods for all correlations.
 * 
 * @author Naveen Amiruddin
 * @since 04/11/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength", "PMD.CyclomaticComplexity",
        "PMD.ExcessiveClassLength", "PMD.NPathComplexity" })
public class CorrelationUtils {
    private static final Logger LOG = Logger.getLogger(CorrelationUtils.class);

    /**
     * 
     * @param organization Organization
     * @return Organization
     * @throws PAException PAException
     */
    private Organization createPAOrganization(Organization organization) throws PAException {
        if (organization == null) {
            LOG.error(" organization should not be null ");
            throw new PAException(" organization should not be null ");
        }
        LOG.debug("Entering createOrganization ");
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(organization);
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while createOrganization ", hbe);
            throw new PAException(" Hibernate exception while createOrganization ", hbe);
        } finally {
            session.flush();
        }
        LOG.debug("Leaving createStudyResourcing ");
        return organization;
    }
    
    /**
     * method to create pa org from po.
     * 
     * @param poOrg
     * @return
     * @throws PAException
     */
    Organization createPAOrganization(OrganizationDTO poOrg) throws PAException {
        if (poOrg == null) {
            throw new PAException(" PO Organization cannot be null");
        }
        Organization paOrg = new Organization();
        paOrg.setName(EnOnConverter.convertEnOnToString(poOrg.getName()));
        paOrg.setStatusCode(convertPOEntifyStatusToPAEntityStatus(poOrg.getStatusCode()));
        paOrg.setIdentifier(poOrg.getIdentifier().getExtension());
        List<Adxp> partList = poOrg.getPostalAddress().getPart();
        for (Adxp part : partList) {
            if (part instanceof AdxpCty) {
                paOrg.setCity(part.getValue());
            }
            if (part instanceof AdxpZip) {
                paOrg.setPostalCode(part.getValue());
            }
            if (part instanceof AdxpCnt) {
                paOrg.setCountryName(part.getCode());
            }
        }
        return createPAOrganization(paOrg);
    }

    /**
     * 
     * @param person person
     * @return Person
     * @throws PAException PAException
     */
    private Person createPAPerson(Person person) throws PAException {
        if (person == null) {
            LOG.error(" Person should not be null ");
            throw new PAException(" Person should not be null ");
        }
        LOG.debug("Entering createPerson ");
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(person);
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while createPerson ", hbe);
            throw new PAException(" Hibernate exception while create Person ", hbe);
        } finally {
            session.flush();
        }
        LOG.debug("Leaving create Person ");
        return person;
    }

    /**
     * 
     * method to create pa person from po.
     * 
     * @param poOrg
     * @return
     * @throws PAException
     */
    Person createPAPerson(PersonDTO poPerson) throws PAException {
        if (poPerson == null) {
            throw new PAException(" PO Person cannot be null");
        }
        Person per = new Person();
        
        try {
            if (poPerson.getName().getPart().get(0).getType().equals(EntityNamePartType.GIV)) {
                per.setFirstName(poPerson.getName().getPart().get(0).getValue());
            }
            if (poPerson.getName().getPart().get(0).getType().equals(EntityNamePartType.FAM)) {
                per.setLastName(poPerson.getName().getPart().get(0).getValue());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOG.error("Error while converting Person ISO " , e);
        }
        per.setStatusCode(convertPOEntifyStatusToPAEntityStatus(poPerson.getStatusCode()));
        per.setIdentifier(poPerson.getIdentifier().getExtension());
        return createPAPerson(per);
    }

    /**
     * 
     * @param poIdentifer id
     * @param paIdentifer id
     * @return Organization
     * @throws PAException e
     */
    public Organization getPAOrganizationByIndetifers(Long paIdentifer, String poIdentifer) throws PAException {
        Organization org = null;
        if (poIdentifer == null && paIdentifer == null) {
            LOG.error(" Atleast one identifier must be entered");
            throw new PAException(" Atleast one identifier must be entered");
        }
        if (poIdentifer != null && paIdentifer != null) {
            LOG.error(" Only one identifier must be entered");
            throw new PAException(" Only one identifier must be entered");
        }
        Session session = null;
        List<Organization> queryList = new ArrayList<Organization>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select org from Organization org  where 1 = 1 ");
        if (paIdentifer != null) {
            hql.append(" and org.id = ").append(paIdentifer);
        }
        if (poIdentifer != null) {
            hql.append(" and org.identifier = '").append(poIdentifer).append('\'');
        }
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            query = session.createQuery(hql.toString());
            queryList = query.list();
            if (queryList.size() > 1) {
                LOG.error(" Organization  should not be more than 1 record for a Po Indetifer = " + poIdentifer);
                throw new PAException(" Organization  should not be more than 1 " + "record for a Po Indetifer = "
                        + poIdentifer);
            }
        } catch (HibernateException hbe) {
            throw new PAException(" Error while retrieving Organization for id = " + paIdentifer + " PO Identifier = "
                    + poIdentifer, hbe);
        } finally {
            session.flush();
        }
        if (!queryList.isEmpty()) {
            org = queryList.get(0);
        }
        return org;
    }
    
    /**
     * @param paResearchOrganizationId id
     * @return Organization
     * @throws PAException e
     */
    public Organization getPAOrganizationByPAResearchOrganizationId(Long paResearchOrganizationId) throws PAException {
        if (paResearchOrganizationId == null) {
            LOG.error("Check the id value.  Null found.  ");
            throw new PAException("Check the id value.  Null found.  ");
        }
        Organization organization = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            ResearchOrganization researchOrg = (ResearchOrganization) session.get(ResearchOrganization.class, 
                    paResearchOrganizationId);
            if (researchOrg == null) {
                String errMsg = "Object not found using getPAOrganizationByPAResearchOrganizationId() for id = " 
                    + paResearchOrganizationId + ".  ";
                LOG.error(errMsg);
                throw new PAException(errMsg);
            }
            organization = researchOrg.getOrganization();
        } catch (HibernateException hbe) {
            LOG.error("Hibernate exception in  getPAOrganizationByPAResearchOrganizationId().  ", hbe);
            throw new PAException("Hibernate exception in  getPAOrganizationByPAResearchOrganizationId().  ", hbe);
        }
        return organization;
    }
    
    /**
     * @param paHealthCareFacilityId id
     * @return Organization
     * @throws PAException e
     */
    public Organization getPAOrganizationByPAHealthCareFacilityId(Long paHealthCareFacilityId) throws PAException {
        if (paHealthCareFacilityId == null) {
            LOG.error("Check the id value.  Null found.  ");
            throw new PAException("Check the id value.  Null found.  ");
        }
        Organization organization = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            HealthCareFacility healthCareFac = (HealthCareFacility) session.get(HealthCareFacility.class, 
                    paHealthCareFacilityId);
            if (healthCareFac == null) {
                String errMsg = "Object not found using getPAOrganizationByPAHealthCareFacilityId() for id = " 
                    + paHealthCareFacilityId + ".  ";
                LOG.error(errMsg);
                throw new PAException(errMsg);
            }
            organization = healthCareFac.getOrganization();
        } catch (HibernateException hbe) {
            LOG.error("Hibernate exception in getPAOrganizationByPAHealthCareFacilityId().  ", hbe);
            throw new PAException("Hibernate exception in getPAOrganizationByPAHealthCareFacilityId().  ", hbe);
        }
        return organization;
    }

    /**
     * 
     * @param poIdentifer id
     * @param paIdentifer id
     * @return Person
     * @throws PAException e
     */
    Person getPAPersonByIndetifers(Long paIdentifer, String poIdentifer) throws PAException {
        Person per = null;
        if (poIdentifer == null && paIdentifer == null) {
            LOG.error(" Atleast one identifier must be entered");
            throw new PAException(" Atleast one identifier must be entered");
        }
        if (poIdentifer != null && paIdentifer != null) {
            LOG.error(" Only one identifier must be entered");
            throw new PAException(" Only one identifier must be entered");
        }
        Session session = null;
        List<Person> queryList = new ArrayList<Person>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select per from Person per  where 1 = 1 ");
        if (paIdentifer != null) {
            hql.append(" and per.id = ").append(paIdentifer);
        }
        if (poIdentifer != null) {
            hql.append(" and per.identifier = '").append(poIdentifer).append('\'');
        }
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            query = session.createQuery(hql.toString());
            queryList = query.list();
            if (queryList.size() > 1) {
                LOG.error(" Person  should not be more than 1 record for a Po Indetifer = " + poIdentifer);
                throw new PAException(" Person  should not be more than 1 " + "record for a Po Indetifer = "
                        + poIdentifer);
            }
        } catch (HibernateException hbe) {
            throw new PAException(" Error while retrieving Organization for id = " + paIdentifer + " PO Identifier = "
                    + poIdentifer, hbe);
        } finally {
            session.flush();
        }
        if (!queryList.isEmpty()) {
            per = queryList.get(0);
        }
        return per;
    }

    /**
     * @param paClinicalResearchStaffId id
     * @return Person
     * @throws PAException e
     */
    public Person getPAPersonByPAClinicalResearchStaffId(Long paClinicalResearchStaffId) throws PAException {
        if (paClinicalResearchStaffId == null) {
            LOG.error("Check the id value.  Null found.  ");
            throw new PAException("Check the id value.  Null found.  ");
        }
        Person person = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            ClinicalResearchStaff clinicalResearchStaff = 
                (ClinicalResearchStaff) session.get(ClinicalResearchStaff.class, paClinicalResearchStaffId);
            if (clinicalResearchStaff == null) {
                String errMsg = "Object not found using getPAPersonByPAClinicalResearchStaffId() for id = " 
                    + paClinicalResearchStaffId + ".  ";
                LOG.error(errMsg);
                throw new PAException(errMsg);
            }
            person = clinicalResearchStaff.getPerson();
        } catch (HibernateException hbe) {
            LOG.error("Hibernate exception in getPAOrganizationByPAHealthCareFacilityId().  ", hbe);
            throw new PAException("Hibernate exception in getPAOrganizationByPAHealthCareFacilityId().  ", hbe);
        }
        return person;
    }

    /**
     * 
     * @param crs ClinicalResearchStaff
     * @return ClinicalResearchStaff
     * @throws PAException PAException
     */
    AbstractEntity createPADomain(AbstractEntity ae) throws PAException {
        if (ae == null) {
            LOG.error(" domain should not be null ");
            throw new PAException(" domaon should not be null ");
        }
        LOG.debug("Entering createPA Domain ");
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(ae);
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while creating domain ", hbe);
            throw new PAException(" Hibernate exception while creating domain", hbe);
        } finally {
            session.flush();
        }
        LOG.debug("Leaving create PA Domain ");
        return ae;
    }

    /**
     * 
     * @param cd
     * @return
     * @throws PAException
     */
    StatusCode convertPOEntifyStatusToPAEntityStatus(Cd cd) throws PAException {
        if (cd == null) {
            throw new PAException(" Cd cannot be null");
        }
        if ("ACTIVE".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.ACTIVE;
        } else if ("INACTIVE".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.INACTIVE;
        } else if ("NULLIFIED".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.NULLIFIED;
        } else if ("PENDING".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.PENDING;
        } else {
            throw new PAException(" Unsuported PA known status " + cd.getCode());
        }
    }

    /**
     * 
     * @param cd
     * @return
     * @throws PAException
     */
    StatusCode convertPORoleStatusToPARoleStatus(Cd cd) throws PAException {
        if (cd == null) {
            throw new PAException(" Cd cannot be null");
        }
        if ("ACTIVE".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.ACTIVE;
        } else if ("INACTIVE".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.INACTIVE;
        } else if ("NULLIFIED".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.NULLIFIED;
        } else if ("PENDING".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.PENDING;
        } else {
            throw new PAException(" Unsuported PA known status " + cd.getCode());
        }
    }
}
