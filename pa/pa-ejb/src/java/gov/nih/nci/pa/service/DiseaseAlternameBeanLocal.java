/**
 * 
 */
package gov.nih.nci.pa.service;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.DiseaseAltername;
import gov.nih.nci.pa.iso.convert.DiseaseAlternameConverter;
import gov.nih.nci.pa.iso.dto.DiseaseAlternameDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author asharma
 *
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity" })
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DiseaseAlternameBeanLocal
extends AbstractBaseIsoService<DiseaseAlternameDTO, DiseaseAltername, DiseaseAlternameConverter>
implements DiseaseAlternameServiceLocal {
    /**
     * @param ii index of the disease
     * @return list of alternate names for disease
     * @throws PAException exception
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DiseaseAlternameDTO> getByDisease(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            throw new PAException("Check the Ii value; null found.  ");
        }
        getLogger().info("Entering getByDisease.  ");

        Session session = null;
        List<DiseaseAltername> queryList = new ArrayList<DiseaseAltername>();
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
        ArrayList<DiseaseAlternameDTO> resultList = new ArrayList<DiseaseAlternameDTO>();
        for (DiseaseAltername bo : queryList) {
            resultList.add(convertFromDomainToDto(bo));
        }
        getLogger().info("Leaving getByDisease, returning " + resultList.size() + " object(s).  ");
        return resultList;
    }

}
