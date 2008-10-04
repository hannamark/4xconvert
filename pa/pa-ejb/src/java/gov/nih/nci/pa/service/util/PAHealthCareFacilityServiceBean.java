/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.iso.convert.HealthCareFacilityConverter;
import gov.nih.nci.pa.iso.dto.HealthCareFacilityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.AbstractStudyPaService;
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
public class PAHealthCareFacilityServiceBean 
        extends AbstractStudyPaService<HealthCareFacilityDTO> 
        implements PAHealthCareFacilityServiceRemote {
    
    private static final Logger LOG  = Logger.getLogger(PAHealthCareFacilityServiceBean.class);
    
    /**
     * @return log4j Logger
     */
    @Override
    protected Logger getLogger() { return LOG; }

    /**
     * @param ii index
     * @return HealthCareFacilityDTO
     * @throws PAException PAException
     */ 
    public HealthCareFacilityDTO get(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError(" Ii should not be null ");
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
            serviceError(" Hibernate exception while retrieving "
                    + "StudyParticipation for pid = " + ii.getExtension() , hbe);
        }
        if (queryList.isEmpty()) {
            serviceError("HealthCareFacility not found for Ii = " + IiConverter.convertToString(ii));
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
    public HealthCareFacilityDTO create(
            HealthCareFacilityDTO dto) throws PAException {
        if ((dto.getIi() != null) && !PAUtil.isIiNull(dto.getIi())) {
            serviceError(" Update method should be used to modify existing. ");
        }
        HealthCareFacilityDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            HealthCareFacility bo = HealthCareFacilityConverter.convertFromDtoToDomain(dto);
            session.saveOrUpdate(bo);
            session.flush();
            resultDto = HealthCareFacilityConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError(" Hibernate exception in createStudyParticipation ", hbe);
        }
        return resultDto;
    }

    /**
     * @param organizationIi pa index of Organization
     * @return list of HealthCareFacilityDTO
     * @throws PAException exception
     */
    public List<HealthCareFacilityDTO> getByOrganization(Ii organizationIi) throws PAException {
        if ((organizationIi == null) || PAUtil.isIiNull(organizationIi)) {
            serviceError("Organization Ii should not be null.  ");
        }
        LOG.info("Entering getByOrganization.  ");
        Session session = null;
        List<HealthCareFacility> queryList = new ArrayList<HealthCareFacility>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;
            
            // step 1: form the hql
            String hql = "select hf "
                       + "from HealthCareFacility hf "
                       + "join hf.organization org "
                       + "where org.id = " + IiConverter.convertToString(organizationIi);
            LOG.info(" query HealthCareFacility = " + hql);
            
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            
        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while retrieving "
                    + "HealthCareFacility for org id = " + organizationIi.getExtension() , hbe);
        }
        LOG.info("Leaving getByOrganization.  ");
        ArrayList<HealthCareFacilityDTO> resultList = new ArrayList<HealthCareFacilityDTO>();
        for (HealthCareFacility hf : queryList) {
            resultList.add(HealthCareFacilityConverter.convertFromDomainToDTO(hf));
        }
        return resultList;
    }
}
