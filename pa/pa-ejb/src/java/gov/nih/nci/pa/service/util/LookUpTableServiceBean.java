package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.NIHinstitute;
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
* Bean implementation for providing access to look up tables.
* 
* @author Naveen Amiruddin
* @since 06/26/2008
* copyright NCI 2007.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Stateless

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

}
