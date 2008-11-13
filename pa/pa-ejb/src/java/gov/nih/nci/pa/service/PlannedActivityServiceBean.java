package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.PlannedEligibilityCriterion;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.PlannedActivityConverter;
import gov.nih.nci.pa.iso.convert.PlannedEligibilityCriterionConverter;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.Date;
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
    /**
     *  @param ii study protocol index
     *  @return list of PlannedEligibilityCriterion
     *  @throws PAException exception
     */   
    public List<PlannedEligibilityCriterionDTO> getPlannedEligibilityCriterionByStudyProtocol(Ii ii)
            throws PAException {
        if (PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; found null.  ");
        }
        
        Session session = null;
        List<PlannedEligibilityCriterion> queryList = new ArrayList<PlannedEligibilityCriterion>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select pa "
                       + "from PlannedEligibilityCriterion pa "
                       + "join pa.studyProtocol sp "
                       + "where sp.id = :studyProtocolId "
                       + "order by pa.id ";
           
            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in getByStudyProtocol.  ", hbe);
        }
        ArrayList<PlannedEligibilityCriterionDTO> resultList = new ArrayList<PlannedEligibilityCriterionDTO>();
        for (PlannedEligibilityCriterion bo : queryList) {
            resultList.add(PlannedEligibilityCriterionConverter.convertFromDomainToDTO(bo));
        }
        return resultList;
    }
    
    /**
     * @param ii index
     * @return the PlannedEligibilityCriterion
     * @throws PAException exception.
     */
    public PlannedEligibilityCriterionDTO getPlannedEligibilityCriterion(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; found null.  ");
        }
        PlannedEligibilityCriterionDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            PlannedEligibilityCriterion bo = (PlannedEligibilityCriterion) session.get(PlannedEligibilityCriterion.class
                    , IiConverter.convertToLong(ii));
            if (bo == null) {
                serviceError("Object not found using get() for id = "
                        + IiConverter.convertToString(ii) + ".  ");
            }
            resultDto = PlannedEligibilityCriterionConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in get().", hbe);
        }
        return resultDto;
    }
    /**
     * @param dto PlannedEligibilityCriterion to create
     * @return the created PlannedEligibilityCriterion
     * @throws PAException exception.
     */
    public PlannedEligibilityCriterionDTO createPlannedEligibilityCriterion(
            PlannedEligibilityCriterionDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            serviceError("Update method should be used to modify existing.  ");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            serviceError("StudyProtocol must be set.  ");
        }
        return createOrUpdatePlannedEligibilityCriterion(dto);
    }

    /**
     * @param dto PlannedEligibilityCriterion to update
     * @return the updated PlannedEligibilityCriterion
     * @throws PAException exception.
     */
    public PlannedEligibilityCriterionDTO updatePlannedEligibilityCriterion(
            PlannedEligibilityCriterionDTO dto) throws PAException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            serviceError("Create method should be used to modify existing.  ");
        }
        return createOrUpdatePlannedEligibilityCriterion(dto);
    }

    /**
     * @param ii index
     * @throws PAException exception.
     */
    public void deletePlannedEligibilityCriterion(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found.  ");
        }
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            PlannedEligibilityCriterion bo = (PlannedEligibilityCriterion) session.get(PlannedEligibilityCriterion.class
                    , IiConverter.convertToLong(ii));
            session.delete(bo);
            session.flush();
        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while deleting ii = "
                + IiConverter.convertToString(ii) + ".  ", hbe);
        }
    }
    private PlannedEligibilityCriterionDTO createOrUpdatePlannedEligibilityCriterion(
            PlannedEligibilityCriterionDTO dto) throws PAException {        
        PlannedEligibilityCriterion bo = null;
        PlannedEligibilityCriterionDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            if (PAUtil.isIiNull(dto.getIdentifier())) {
                bo = PlannedEligibilityCriterionConverter.convertFromDTOToDomain(dto);
            } else {
                bo = (PlannedEligibilityCriterion) session.get(PlannedEligibilityCriterion.class,
                        IiConverter.convertToLong(dto.getIdentifier()));

                PlannedEligibilityCriterion delta = PlannedEligibilityCriterionConverter.convertFromDTOToDomain(dto);
                bo.setCriterionName(delta.getCriterionName());
                bo.setInclusionIndicator(delta.getInclusionIndicator());
                bo.setOperator(delta.getOperator());
                bo.setCategoryCode(delta.getCategoryCode());
                bo.setEligibleGenderCode(delta.getEligibleGenderCode());
                bo.setAgeValue(delta.getAgeValue());
                bo.setUnit(delta.getUnit());
                bo.setDescriptionText(delta.getDescriptionText()); 
                bo.setUserLastUpdated(delta.getUserLastCreated());
            }
            bo.setDateLastUpdated(new Date());
            session.saveOrUpdate(bo);
            session.flush();
            resultDto = PlannedEligibilityCriterionConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in createOrUpdate().  ", hbe);
        }
        return resultDto;
    }

}
