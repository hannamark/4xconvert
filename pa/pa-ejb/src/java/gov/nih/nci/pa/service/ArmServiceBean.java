package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.iso.convert.ArmConverter;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
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
 * @since 11/05/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
@SuppressWarnings("PMD.CyclomaticComplexity")
public class ArmServiceBean extends AbstractStudyIsoService<ArmDTO, Arm, ArmConverter> 
        implements ArmServiceRemote {
    /**
     * @param ii index of planned activity
     * @return list of arms associated w/planned activity
     * @throws PAException exception
     */
    public List<ArmDTO> getByPlannedActivity(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found.  ");
        }
        getLogger().info("Entering getByPlannedActivity.  ");

        Session session = null;
        List<Arm> queryList = new ArrayList<Arm>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select ar "
                       + "from Arm ar "
                       + "join ar.plannedActivity pa "
                       + "where pa.id = :plannedActivityId "
                       + "order by ar.id ";
            getLogger().info("query Arm = " + hql + ".  ");

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("plannedActivityId", IiConverter.convertToLong(ii));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in getByArm.  ", hbe);
        }
        ArrayList<ArmDTO> resultList = new ArrayList<ArmDTO>();
        for (Arm bo : queryList) {
            resultList.add(convertFromDomainToDto(bo));
        }
        getLogger().info("Leaving getByArm, returning " + resultList.size() + " object(s).  ");
        return resultList;
    }

    private void businessRules(ArmDTO dto)  throws PAException {
        if (dto == null) {
            return;
        }
        if (PAUtil.isEmpty(StConverter.convertToString(dto.getName()))) {
            serviceError("The arm/group label (name) must be set.  ");
        }
        if (PAUtil.isEmpty(StConverter.convertToString(dto.getDescriptionText()))) {
            serviceError("The arm/group description must be set.  ");
        }
    }

    /**
     * @param dto Arm transer object
     * @return created Arm
     * @throws PAException exception
     */
    @Override
    public ArmDTO create(ArmDTO dto) throws PAException {
        businessRules(dto);
        return super.create(dto);
    }

    /**
     * @param dto Arm transer object
     * @return updated Arm
     * @throws PAException exception
     */
    @Override
    public ArmDTO update(ArmDTO dto) throws PAException {
        businessRules(dto);
        return super.update(dto);
    }
}
