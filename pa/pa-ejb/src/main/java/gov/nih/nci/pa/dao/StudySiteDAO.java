package gov.nih.nci.pa.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import gov.nih.nci.pa.domain.HealthcareSite;
import gov.nih.nci.pa.dto.OrganizationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;

/**
 * DAO for accessing StudySite.
 *
 * @author Naveen Amiruddin
 * @since 06/22/2007 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class StudySiteDAO {
    
    private static final Logger LOG  = Logger.getLogger(StudySiteDAO.class);    
    
    /**
     * get all organization that have been associated with protocols.
     * @return OrganizationDTO list of OrganizationDTO
     * @throws PAException PAException
     */
    public List<OrganizationDTO> getOrganizationsAssociatedWithProtocol() 
    throws PAException {
        LOG.debug("Entering getOrganizationAssociatedWithProtocols ");

        Session session = null;
        List<OrganizationDTO> orgDtos = new ArrayList<OrganizationDTO>();
        OrganizationDTO orgDto;
        try {
        session = HibernateUtil.getCurrentSession();
        List organizations = session.createQuery("Select distinct healtcareSite from StudySite").list();
        // covert to Organization DTO
        // todo : Healthcare and Organization are use inter changingly, once Nelli gives a mapping
        // it will be all syn. up
            for (int i = 0; i < organizations.size(); i++) {
                orgDto = new OrganizationDTO();
                orgDto.setId(((HealthcareSite) organizations.get(i)).getId());
                orgDto.setName(((HealthcareSite) organizations.get(i)).getName());
                orgDtos.add(orgDto);
            }
        } catch (HibernateException hbe) {
            LOG.error("  Hibernate exception while fetching " 
                     + "getOrganizationAssociatedWithProtocols ", hbe);
            throw new PAException(" Hibernate exception while fetching " 
                    + "getOrganizationAssociatedWithProtocols ", hbe);
        } finally {
            LOG.debug("Leaving getOrganizationAssociatedWithProtocols ");
        }
        return orgDtos;
    }

}
