package gov.nih.nci.pa.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;


import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.OrganizationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;

/**
 * DAO for accessing StudyCoordinatingCenter.
 *
 * @author Naveen Amiruddin
 * @since 06/22/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */

public class StudyCoordinatingCenterDAO {

    private static final Logger LOG  = Logger.getLogger(StudyCoordinatingCenterDAO.class);
    
    /**
     * get all lead organization that have been associated with study protocols.
     * @throws PAException p   
     * @return OrganizationDTO list of OrganizationDTO
     * 
     */
    public List<OrganizationDTO> getLeadOrganizations()  
    throws PAException {
        LOG.debug("Entering getLeadOrganizations ");

        Session session = null;
        List<OrganizationDTO> orgDtos = new ArrayList<OrganizationDTO>();
        OrganizationDTO orgDto;
        try {
            session = HibernateUtil.getCurrentSession();
            
            List organizations = 
                session.createQuery("Select distinct organization from StudyCoordinatingCenter").list();
            for (int i = 0; i < organizations.size(); i++) {
                orgDto = new OrganizationDTO();
                orgDto.setId(((Organization) organizations.get(i)).getId());
                orgDto.setName(((Organization) organizations.get(i)).getName());
                orgDtos.add(orgDto);
            }
        } catch (HibernateException hbe) {
            LOG.error("  Hibernate exception while fetching " 
                     + "getOrganizationAssociatedWithProtocols ", hbe);
            throw new PAException(" Hibernate exception while fetching " 
                    + "getOrganizationAssociatedWithProtocols ", hbe);
        } finally {
            LOG.debug("Leaving getLeadOrganizations ");
        }
        return orgDtos;
    }

}
