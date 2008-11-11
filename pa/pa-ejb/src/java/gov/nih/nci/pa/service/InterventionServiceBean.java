/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.iso.convert.InterventionConverter;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
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
 * @since 10/29/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings("PMD.CyclomaticComplexity")
public class InterventionServiceBean
        extends AbstractBasePaService<InterventionDTO>
        implements InterventionServiceRemote {

    private static final Logger LOG  = Logger.getLogger(InterventionServiceBean.class);

    /**
     * @return log4j Logger
     */
    @Override
    protected Logger getLogger() {
        return LOG;
    }

    /**
     * @param ii index
     * @return the intervention
     * @throws PAException Exception.
     */
    @Override
    public InterventionDTO get(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found.  ");
        }
        InterventionDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            Intervention bo = (Intervention) session.get(Intervention.class
                    , IiConverter.convertToLong(ii));
            if (bo == null) {
                serviceError("Object not found using get() for id = "
                        + IiConverter.convertToString(ii) + ".  ");
            }
            resultDto = InterventionConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in get().", hbe);
        }
        return resultDto;
    }

    /**
     * @param searchCriteria search string
     * @return all interventions of given type with names or alternate names matching search string
     * @throws PAException exception
     */
    public List<InterventionDTO> search(InterventionDTO searchCriteria) throws PAException {
        if (searchCriteria == null) {
            serviceError("Must pass in search criteria when calling search().");
        }
        if (searchCriteria.getName() == null) {
            serviceError("Must pass in a name when calling search().");
        }
        LOG.info("Entering search().  ");
        List<Intervention> queryList = new ArrayList<Intervention>();
        try {
            Session session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select distinct int "
                       + "from Intervention int "
                       + "left join int.interventionAlternateNames ian "
                       + "where (upper(int.name) like upper(:name) "
                       + "     or upper(ian.name) like upper(:name)) " 
                       + "order by int.name ";
            LOG.info("query Intervention = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("name", "%" + StConverter.convertToString(searchCriteria.getName()) + "%");
            
            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in search.().  ", hbe);
        }
        ArrayList<InterventionDTO> resultList = new ArrayList<InterventionDTO>();
        Long lastId = null;
        for (Intervention bo : queryList) {
            if (lastId != bo.getId()) {
              resultList.add(InterventionConverter.convertFromDomainToDTO(bo));
              lastId = bo.getId();
            }
        }

        LOG.info("Leaving search(), returning " + resultList.size() + " object(s).");
        return resultList;
    }


}
