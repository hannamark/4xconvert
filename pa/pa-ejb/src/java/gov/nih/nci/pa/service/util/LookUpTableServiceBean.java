package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.NIHinstitute;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/** 
* Bean implementation for providing access to look up tables.
* 
* @author Naveen Amiruddin
* @since 06/26/2008
* copyright NCI 2007.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class LookUpTableServiceBean implements LookUpTableServiceRemote {

    private static final Logger LOG  = Logger.getLogger(LookUpTableServiceBean.class);
    
    /**
     * 
     * @return fmList  FundingMechanism
     * @throws PAException PAException
     */
    public List<FundingMechanism> getFundingMechanisms() throws PAException {
        LOG.info("Entering getFundingMechanisms");
        Session session = null;
        List<FundingMechanism> fmList = new ArrayList<FundingMechanism>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            String hql = "select fm from FundingMechanism fm order by fundingMechanismCode";
            query = session.createQuery(hql);
            fmList = query.list();
        }  catch (HibernateException hbe) {
            LOG.error(" Unable to load funding Mechanism" , hbe);
            throw new PAException(" Unable to load funding Mechanism", hbe);
        } finally {
            session.flush();
        } 
        return fmList;
        
    }

    /**
     * 
     * @return nihList  FundingMechanism
     * @throws PAException PAException
     */
    public List<NIHinstitute> getNihInstitutes() throws PAException {
        LOG.info("Entering getFundingMechanisms");
        Session session = null;
        List<NIHinstitute> nihList = new ArrayList<NIHinstitute>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            String hql = "select nih from NIHinstitute nih order by nihInstituteCode";
            query = session.createQuery(hql);
            nihList = query.list();
        }  catch (HibernateException hbe) {
            LOG.error(" Unable to load NIHinstitute" , hbe);
            throw new PAException(" Unable to load NIHinstitute", hbe);
        } finally {
            session.flush();
        } 
        return nihList;
        
    }
    
    /**
     * 
     * @return country  Country
     * @throws PAException PAException
     */
    public List<Country> getCountries() throws PAException {    
        LOG.info("Entering getCountries");
        Session session = null;
        List<Country> countries = new ArrayList<Country>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = session.createQuery("select c from Country c order by name");
            countries = query.list();
        }  catch (HibernateException hbe) {
            LOG.error(" Unable to load Country" , hbe);
            throw new PAException(" Unable to load Country", hbe);
        } finally {
            session.flush();
        } 
        LOG.info("Leaving getCountries");
        return countries;
    }
    
    /**
     * @param name name
     * @return value  val
     * @throws PAException PAException
     */
    public String getPropertyValue(String name) throws PAException {    
        LOG.info("Entering getPropertyValue");
        Session session = null;
        String value = "";
        List<PAProperties> paProperties = new ArrayList<PAProperties>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = session.createQuery("select p from PAProperties p where p.name = '" + name + "'");
            paProperties =  query.list();
            if (paProperties == null || paProperties.isEmpty()) {
                throw new PAException(" PA_PROPERTIES does not have entry for  " + name);
            }
            value = paProperties.get(0).getValue();
        }  catch (HibernateException hbe) {
            LOG.error(" Unable to load value from PAProperties" , hbe);
            throw new PAException(" Unable to load from PAProperties", hbe);
        } finally {
            session.flush();
        } 
        LOG.info("Leaving getPropertyValue");
        return value;
    }

}
