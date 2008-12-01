/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.AbstractEntity;
import gov.nih.nci.pa.iso.convert.AbstractConverter;
import gov.nih.nci.pa.iso.dto.StudyDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
/**
 * Base class for services which implement the getByStudyProtocol method.
 * @author Hugh Reinhart
 * @since 09/30/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 * @param <DTO> data transfer object
 * @param <BO> domain object
 * @param <CONVERTER> converter class
 */
public abstract class AbstractStudyIsoService<DTO extends StudyDTO, BO extends AbstractEntity, 
                                        CONVERTER extends AbstractConverter<DTO, BO>> 
        extends AbstractBaseIsoService<DTO, BO, CONVERTER> 
        implements StudyPaService<DTO> {
    

    
    /**
     * @param ii index of object
     * @return null
     * @throws PAException exception
     */
    @SuppressWarnings("unchecked")
    public List<DTO> getByStudyProtocol(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found.  ");
        }
        getLogger().info("Entering getByStudyProtocol.  ");

        Session session = null;
        List<BO> queryList = new ArrayList<BO>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select alias "
                       + "from " + getTypeArgument().getName() + " alias "
                       + "join alias.studyProtocol sp "
                       + "where sp.id = :studyProtocolId "
                       + "order by alias.id ";
            getLogger().info("query " +  getTypeArgument().getName() + " = " + hql + ".  ");

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in getByStudyProtocol.  ", hbe);
        }
        ArrayList<DTO> resultList = new ArrayList<DTO>();
        for (BO bo : queryList) {
            resultList.add(convertFromDomainToDto(bo));
        }
        getLogger().info("Leaving getByStudyProtocol, returning " + resultList.size() + " object(s).  ");
        return resultList;
    }
}
