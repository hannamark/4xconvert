package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.iso.convert.DiseaseConverter;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
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
* @since 11/30/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Stateless
public class DiseaseServiceBean 
        extends AbstractBaseIsoService<DiseaseDTO, Disease, DiseaseConverter> 
        implements DiseaseServiceRemote {
    /**
     * @param searchCriteria search string
     * @return all diseases with preferred names or alternate names matching search string
     * @throws PAException exception
     */
    public List<DiseaseDTO> search(DiseaseDTO searchCriteria) throws PAException {
        if (searchCriteria == null) {
            serviceError("Must pass in search criteria when calling search().");
        }
        if (searchCriteria.getPreferredName() == null) {
            serviceError("Must pass in a name when calling search().");
        }
        getLogger().info("Entering search().  ");
        List<Disease> queryList = new ArrayList<Disease>();
        try {
            Session session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select distinct dis "
                       + "from Disease dis "
                       + "left join dis.diseaseAlternames alt "
                       + "where (dis.statusCode = 'ACTIVE') "
                       + "  and ((upper(dis.preferredName) like upper(:name)) "
                       + "       or ((upper(alt.alternateName) like upper(:name)) and (dis.statusCode = 'ACTIVE'))) " 
                       + "order by dis.preferredName ";
            getLogger().info("query Disease = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("name", "%" + StConverter.convertToString(searchCriteria.getPreferredName()) + "%");
            
            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in search.().  ", hbe);
        }
        ArrayList<DiseaseDTO> resultList = new ArrayList<DiseaseDTO>();
        for (Disease bo : queryList) {
            resultList.add(convertFromDomainToDto(bo));
        }
        getLogger().info("Leaving search(), returning " + resultList.size() + " object(s).");
        return resultList;
    }
}
