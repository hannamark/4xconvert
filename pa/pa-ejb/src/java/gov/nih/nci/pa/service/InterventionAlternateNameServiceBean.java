/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.InterventionAlternateName;
import gov.nih.nci.pa.iso.convert.InterventionAlternateNameConverter;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
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
 * @since 10/29/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
public class InterventionAlternateNameServiceBean
        extends AbstractBaseIsoService
                <InterventionAlternateNameDTO, InterventionAlternateName, InterventionAlternateNameConverter>
        implements InterventionAlternateNameServiceRemote {

    /**
     * @param interventionIi Primary key assigned to an Intervention.
     * @return List.
     * @throws PAException Exception.
     */
    public List<InterventionAlternateNameDTO> getByIntervention(
            Ii interventionIi) throws PAException {
        if (PAUtil.isIiNull(interventionIi)) {
            serviceError(" Ii should not be null ");
        }
        getLogger().info("Entering getByIntervention.  ");

        Session session = null;
        List<InterventionAlternateName> queryList = new ArrayList<InterventionAlternateName>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select ian "
                       + "from InterventionAlternateName ian "
                       + "join ian.intervention int "
                       + "where int.id = :interventionId "
                       + "order by ian.id ";
            getLogger().info("query InterventionAlternateName = " + hql + "  ");

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("interventionId", IiConverter.convertToLong(interventionIi));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in getIntervention.  ", hbe);
        }
        ArrayList<InterventionAlternateNameDTO> resultList = new ArrayList<InterventionAlternateNameDTO>();
        for (InterventionAlternateName bo : queryList) {
            resultList.add(convertFromDomainToDto(bo));
        }
        getLogger().info("Leaving getByIntervention, returning " + resultList.size() + " object(s).  ");
        return resultList;
    }

}
