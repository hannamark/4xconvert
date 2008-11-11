package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.PlannedActivityConverter;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
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
@SuppressWarnings("PMD.CyclomaticComplexity")
public class PlannedActivityServiceBean
        extends AbstractStudyIsoService<PlannedActivityDTO, PlannedActivity, PlannedActivityConverter>
        implements PlannedActivityServiceRemote {

    private void businessRules(PlannedActivityDTO dto) throws PAException {
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            serviceError("PlannedActivity.studyProtocol must be set.  ");
        }
        if (PAUtil.isCdNull(dto.getCategoryCode())) {
            serviceError("PlannedActivity.categoryCode must be set.  ");
        }
        ActivityCategoryCode cc = ActivityCategoryCode.getByCode(CdConverter.convertCdToString(dto.getCategoryCode()));
        if (ActivityCategoryCode.INTERVENTION.equals(cc)) {
            if (PAUtil.isCdNull(dto.getSubcategoryCode())) {
                serviceError("Intervention type must be set.  ");
            }
            if (PAUtil.isIiNull(dto.getInterventionIdentifier())) {
                serviceError("An Intervention must be selected.  ");
            }
            if ((!ActivitySubcategoryCode.DRUG.getCode().equals(CdConverter.
                    convertCdToString(dto.getSubcategoryCode())))
                && (dto.getLeadProductIndicator() != null)) {
                getLogger().info("Setting lead product indicator to null for non-drug PlannedActivity.  ");
                dto.setLeadProductIndicator(null);
            }
            if ((ActivitySubcategoryCode.DRUG.getCode().equals(CdConverter.
                    convertCdToString(dto.getSubcategoryCode())))
                && (dto.getLeadProductIndicator() == null)) {
                getLogger().info("Generating Bl (false) for non-drug PlannedActivity.  ");
                dto.setLeadProductIndicator(BlConverter.convertToBl(false));
            }
        }
    }

    /**
     * @param dto planned activity to create
     * @return the created planned activity
     * @throws PAException exception.
     */
    @Override
    public PlannedActivityDTO create(PlannedActivityDTO dto) throws PAException {
        businessRules(dto);
        return super.create(dto);
    }

    /**
     * @param dto planned activity to update
     * @return the created planned activity
     * @throws PAException exception.
     */
    @Override
    public PlannedActivityDTO update(PlannedActivityDTO dto) throws PAException {
        businessRules(dto);
        return super.update(dto);
    }

    /**
     * @param ii index of arm
     * @return list of planned activities associated w/arm
     * @throws PAException exception
     */
    public List<PlannedActivityDTO> getByArm(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found.  ");
        }
        getLogger().info("Entering getByArm.  ");

        Session session = null;
        List<PlannedActivity> queryList = new ArrayList<PlannedActivity>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select pa "
                       + "from PlannedActivity pa "
                       + "join pa.arms a "
                       + "where a.id = :armId "
                       + "order by pa.id ";
            getLogger().info("query PlannedActivity = " + hql + ".  ");

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("armId", IiConverter.convertToLong(ii));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in getByArm.  ", hbe);
        }
        ArrayList<PlannedActivityDTO> resultList = new ArrayList<PlannedActivityDTO>();
        for (PlannedActivity bo : queryList) {
            resultList.add((PlannedActivityDTO) Converters.get(PlannedActivityConverter.class)
                    .convertFromDomainToDto(bo));
        }
        getLogger().info("Leaving getByArm, returning " + resultList.size() + " object(s).  ");
        return resultList;
    }

}
