package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.DiseaseParent;
import gov.nih.nci.pa.iso.convert.DiseaseParentConverter;
import gov.nih.nci.pa.iso.dto.DiseaseParentDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

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
public class DiseaseParentServiceBean 
        extends AbstractBaseIsoService<DiseaseParentDTO, DiseaseParent, DiseaseParentConverter> 
        implements DiseaseParentServiceRemote {

    private List<DiseaseParentDTO> getByDisease(Ii ii, String assoc) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found.  ");
        }
        getLogger().info("Entering getByDisease for " + assoc + ".  ");

        Session session = null;
        List<DiseaseParent> queryList = new ArrayList<DiseaseParent>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select dp "
                       + "from DiseaseParent dp "
                       + "join dp." + assoc + " dis "
                       + "where dis.id = :diseaseId "
                       + "order by dp.id ";
            getLogger().info("query DiseaseParent = " + hql + ".  ");

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("diseaseId", IiConverter.convertToLong(ii));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in getByDisease.  ", hbe);
        }
        ArrayList<DiseaseParentDTO> resultList = new ArrayList<DiseaseParentDTO>();
        for (DiseaseParent bo : queryList) {
            resultList.add(convertFromDomainToDto(bo));
        }
        getLogger().info("Leaving getByDisease, returning " + resultList.size() + " object(s).  ");
        return resultList;
    }
    /**
     * @param ii index of disease
     * @return list of DiseaseParent associations for children of disease
     * @throws PAException exception
     */
    public List<DiseaseParentDTO> getByChildDisease(Ii ii)
            throws PAException {
        return getByDisease(ii, "disease");
    }

    /**
     * @param ii index of disease
     * @return list of DiseaseParent associations for parents of disease
     * @throws PAException exception
     */
    public List<DiseaseParentDTO> getByParentDisease(Ii ii)
            throws PAException {
        return getByDisease(ii, "parentDisease");
    }
}
