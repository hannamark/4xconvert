/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.iso.convert.HealthCareFacilityConverter;
import gov.nih.nci.pa.iso.dto.HealthCareFacilityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 09/23/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
public class PAHealthCareFacilityServiceBean implements PAHealthCareFacilityServiceRemote {
    private static final Logger LOG  = Logger.getLogger(PAHealthCareFacilityServiceBean.class);
    private static String errMsgMethodNotImplemented = "Method not yet implemented.";

    /**
     * @param ii index
     * @return HealthCareFacilityDTO
     * @throws PAException PAException
     */ 
    public HealthCareFacilityDTO getHealthCareFacility(Ii ii)
    throws PAException {
        if (PAUtil.isIiNull(ii)) {
            LOG.error(" Ii should not be null ");
            throw new PAException(" Ii should not be null ");
        }
        LOG.info("Entering getHealthCareFacility");
        Session session = null;
        List<HealthCareFacility> queryList = new ArrayList<HealthCareFacility>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;
            
            // step 1: form the hql
            String hql = "select hf "
                       + "from HealthCareFacility hf "
                       + "where hf.id =  " + IiConverter.convertToString(ii);
            LOG.info(" query HealthCareFacility = " + hql);
            
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while retrieving "
                    + "StudyParticipation for pid = " + ii.getExtension() , hbe);
            throw new PAException(" Hibernate exception while retrieving " 
                    + "StudyParticipation for pid = " + ii.getExtension() , hbe);
        }
        
        HealthCareFacilityDTO result = HealthCareFacilityConverter.convertFromDomainToDTO(queryList.get(0));

        LOG.info("Leaving getHealthCareFacility");
        return result;
    }
    /**
     * @param dto HealthCareFacilityDTO
     * @return HealthCareFacilityDTO
     * @throws PAException PAException
     */
    public HealthCareFacilityDTO createHealthCareFacility(
            HealthCareFacilityDTO dto) throws PAException {
        throw new PAException(errMsgMethodNotImplemented);
    }

    /**
     * @param dto HealthCareFacilityDTO
     * @return HealthCareFacilityDTO
     * @throws PAException PAException
     */
    public HealthCareFacilityDTO updateHealthCareFacility(
            HealthCareFacilityDTO dto) throws PAException {
        throw new PAException(errMsgMethodNotImplemented);
    }
    
    /**
     * @param studyProtocolIi id of protocol
     * @return list HealthCareFacilityDTO   
     * @throws PAException on error 
     */
    public List<HealthCareFacilityDTO> getHealthCareFacilityByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        throw new PAException(errMsgMethodNotImplemented);
    }
}
