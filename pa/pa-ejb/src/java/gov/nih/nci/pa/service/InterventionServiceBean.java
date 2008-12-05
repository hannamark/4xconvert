/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.iso.convert.InterventionConverter;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

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
        extends AbstractBaseIsoService<InterventionDTO, Intervention, InterventionConverter>
        implements InterventionServiceRemote {

    /**
     * @param searchCriteria search string
     * @return all interventions with names or alternate names matching search string
     * @throws PAException exception
     */
    public List<InterventionDTO> search(InterventionDTO searchCriteria) throws PAException {
        if (searchCriteria == null) {
            serviceError("Must pass in search criteria when calling search().");
        }
        if (searchCriteria.getName() == null) {
            serviceError("Must pass in a name when calling search().");
        }
        getLogger().info("Entering search().  ");
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
            getLogger().info("query Intervention = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("name", "%" + StConverter.convertToString(searchCriteria.getName()) + "%");
            
            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in search.().  ", hbe);
        }
        ArrayList<InterventionDTO> resultList = new ArrayList<InterventionDTO>();
        for (Intervention bo : queryList) {
            resultList.add(convertFromDomainToDto(bo));
        }
        getLogger().info("Leaving search(), returning " + resultList.size() + " object(s).");
        return resultList;
    }
}
