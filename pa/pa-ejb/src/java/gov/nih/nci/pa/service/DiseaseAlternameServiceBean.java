package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.DiseaseAltername;
import gov.nih.nci.pa.iso.convert.DiseaseAlternameConverter;
import gov.nih.nci.pa.iso.dto.DiseaseAlternameDTO;
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
public class DiseaseAlternameServiceBean 
        extends AbstractBaseIsoService<DiseaseAlternameDTO, DiseaseAltername, DiseaseAlternameConverter> 
        implements DiseaseAlternameServiceRemote {

    /**
     * @param ii index of the disease
     * @return list of alternate names for disease
     * @throws PAException exception
     */
    public List<DiseaseAlternameDTO> getByDisease(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found.  ");
        }
        getLogger().info("Entering getByDisease.  ");

        Session session = null;
        List<DiseaseAltername> queryList = new ArrayList<DiseaseAltername>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select alt "
                       + "from DiseaseAltername alt "
                       + "join alt.disease dis "
                       + "where dis.id = :diseaseId "
                       + "order by alt.id ";
            getLogger().info("query DiseaseAltername = " + hql + ".  ");

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("diseaseId", IiConverter.convertToLong(ii));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in getByDisease.  ", hbe);
        }
        ArrayList<DiseaseAlternameDTO> resultList = new ArrayList<DiseaseAlternameDTO>();
        for (DiseaseAltername bo : queryList) {
            resultList.add(convertFromDomainToDto(bo));
        }
        getLogger().info("Leaving getByDisease, returning " + resultList.size() + " object(s).  ");
        return resultList;
    }
}
