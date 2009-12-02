package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.PlannedEligibilityCriterion;
import gov.nih.nci.pa.domain.PlannedProcedure;
import gov.nih.nci.pa.domain.PlannedSubstanceAdministration;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.PlannedActivityConverter;
import gov.nih.nci.pa.iso.convert.PlannedEligibilityCriterionConverter;
import gov.nih.nci.pa.iso.convert.PlannedProcedureConverter;
import gov.nih.nci.pa.iso.convert.PlannedSubstanceAdministrationConverter;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.PlannedProcedureDTO;
import gov.nih.nci.pa.iso.dto.PlannedSubstanceAdministrationDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
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
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.TooManyMethods", "PMD.ExcessiveClassLength", "PMD.TooManyFields" })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(HibernateSessionInterceptor.class)
public class PlannedActivityBeanLocal 
 extends AbstractStudyIsoService<PlannedActivityDTO, PlannedActivity, PlannedActivityConverter>
 implements PlannedActivityServiceLocal , PlannedActivityServiceRemote { 
 
  private static final String II_NOTFOUND = "Check the Ii value; found null.  ";
 
  @EJB
  InterventionServiceLocal interventionSrv;
 

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
  @SuppressWarnings({"PMD" })
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<PlannedActivityDTO> getByArm(Ii ii) throws PAException {
     if (PAUtil.isIiNull(ii)) {
         return null;
     }
     getLogger().info("Entering getByArm.  ");

     Session session = null;
     List<PlannedActivity> queryList = new ArrayList<PlannedActivity>();
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
     ArrayList<PlannedActivityDTO> resultList = new ArrayList<PlannedActivityDTO>();
     for (PlannedActivity bo : queryList) {
         resultList.add(Converters.get(PlannedActivityConverter.class)
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
  @SuppressWarnings({"PMD" })
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<PlannedEligibilityCriterionDTO> getPlannedEligibilityCriterionByStudyProtocol(Ii ii)
  throws PAException {
     if (PAUtil.isIiNull(ii)) {
        return null;
     }

     Session session = null;
     List<PlannedEligibilityCriterion> queryList = new ArrayList<PlannedEligibilityCriterion>();
     session = HibernateUtil.getCurrentSession();
     Query query = null;

     // step 1: form the hql
     String hql = "select pa "
         + "from PlannedEligibilityCriterion pa "
         + "join pa.studyProtocol sp "
         + "where sp.id = :studyProtocolId "
         + "order by pa.displayOrder,pa.id";

     // step 2: construct query object
     query = session.createQuery(hql);
     query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

     // step 3: query the result
     queryList = query.list();
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
   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
   public PlannedEligibilityCriterionDTO getPlannedEligibilityCriterion(Ii ii) throws PAException {
     if (PAUtil.isIiNull(ii)) {
         return null;
     }
     PlannedEligibilityCriterionDTO resultDto = null;
     Session session = null;
     session = HibernateUtil.getCurrentSession();
     PlannedEligibilityCriterion bo = (PlannedEligibilityCriterion) session.get(PlannedEligibilityCriterion.class
             , IiConverter.convertToLong(ii));
     if (bo == null) {
         throw new PAException("Object not found using get() for id = "
                 + IiConverter.convertToString(ii) + ".  ");
     }
     resultDto = PlannedEligibilityCriterionConverter.convertFromDomainToDTO(bo);
     return resultDto;
  }
  /**
   * @param dto PlannedEligibilityCriterion to create
   * @return the created PlannedEligibilityCriterion
   * @throws PAException exception.
   */
   public PlannedEligibilityCriterionDTO createPlannedEligibilityCriterion(
         PlannedEligibilityCriterionDTO dto) throws PAException {
     if (PAUtil.isIiNotNull(dto.getIdentifier())) {
         throw new PAException("Update method should be used to modify existing.  ");
     }
     if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
         throw new PAException("StudyProtocol must be set.  ");
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
         throw new PAException("Create method should be used to modify existing.  ");
     }
     return createOrUpdatePlannedEligibilityCriterion(dto);
   }

   /**
    * @param ii index
    * @throws PAException exception.
    */
     public void deletePlannedEligibilityCriterion(Ii ii) throws PAException {
      if (PAUtil.isIiNull(ii)) {
         throw new PAException(II_NOTFOUND);
     }
     Session session = null;
     session = HibernateUtil.getCurrentSession();
     PlannedEligibilityCriterion bo = (PlannedEligibilityCriterion) session.get(PlannedEligibilityCriterion.class
             , IiConverter.convertToLong(ii));
     session.delete(bo);
   }

  /**
   * copies the study protocol record from source to target.
   * @param fromStudyProtocolIi source
   * @param toStudyProtocolIi target
   * @throws PAException exception.
   */
   public void copyPlannedEligibilityStudyCriterions(Ii fromStudyProtocolIi , Ii toStudyProtocolIi)
   throws PAException {
     List<PlannedEligibilityCriterionDTO> dtos = getPlannedEligibilityCriterionByStudyProtocol(fromStudyProtocolIi);
     for (PlannedEligibilityCriterionDTO dto : dtos) {
         dto.setIdentifier(null);
         dto.setStudyProtocolIdentifier(toStudyProtocolIi);
         createPlannedEligibilityCriterion(dto);
     }
  }
 
 /***
  * Planned Substance Methods
  */
 /**
  * {@inheritDoc}
  */
 public PlannedSubstanceAdministrationDTO createPlannedSubstanceAdministration(
         PlannedSubstanceAdministrationDTO dto) throws PAException {
     if (PAUtil.isIiNotNull(dto.getIdentifier())) {
         throw new PAException("Update method should be used to modify existing.  ");
     }
     if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
         throw new PAException("StudyProtocol must be set.  ");
     }
     validatePlannedSubstance(dto);
     return createOrUpdatePlannedSubstanceAdministration(dto);
 }
 
 /**
  * {@inheritDoc}
  */
 public PlannedSubstanceAdministrationDTO updatePlannedSubstanceAdministration(
         PlannedSubstanceAdministrationDTO dto) throws PAException {
     if (PAUtil.isIiNull(dto.getIdentifier())) {
         throw new PAException("Create method should be used to modify existing.  ");
     }
     return createOrUpdatePlannedSubstanceAdministration(dto);
 }
 /**
  * {@inheritDoc}
  */
 @SuppressWarnings({"PMD" })
 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
 public List<PlannedSubstanceAdministrationDTO> getPlannedSubstanceAdministrationByStudyProtocol(Ii ii)
 throws PAException {
     if (PAUtil.isIiNull(ii)) {
         return null;
     }

     Session session = null;
     List<PlannedSubstanceAdministration> queryList = new ArrayList<PlannedSubstanceAdministration>();
     session = HibernateUtil.getCurrentSession();
     Query query = null;

     // step 1: form the hql
     String hql = "select pa "
         + "from PlannedSubstanceAdministration pa "
         + "join pa.studyProtocol sp "
         + "where sp.id = :studyProtocolId "
         + "order by pa.id ";

     // step 2: construct query object
     query = session.createQuery(hql);
     query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

     // step 3: query the result
     queryList = query.list();
     ArrayList<PlannedSubstanceAdministrationDTO> resultList = new ArrayList<PlannedSubstanceAdministrationDTO>();
     for (PlannedSubstanceAdministration bo : queryList) {
         resultList.add(PlannedSubstanceAdministrationConverter.convertFromDomainToDTO(bo));
     }
     return resultList;
 }
 
 /**
  * {@inheritDoc}
  */
 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
 public PlannedSubstanceAdministrationDTO getPlannedSubstanceAdministration(Ii ii) throws PAException {
     if (PAUtil.isIiNull(ii)) {
         return null;
     }
     PlannedSubstanceAdministrationDTO resultDto = null;
     Session session = null;
     session = HibernateUtil.getCurrentSession();
     PlannedSubstanceAdministration bo = (PlannedSubstanceAdministration) session.get(
             PlannedSubstanceAdministration.class, IiConverter.convertToLong(ii));
     if (bo == null) {
         throw new PAException("Object not found using get() for id = "
                 + IiConverter.convertToString(ii) + ".  ");
     }
     resultDto = PlannedSubstanceAdministrationConverter.convertFromDomainToDTO(bo);
     return resultDto;
 }
 
 
 /**
  *  Planned Procedure methods
  */
 /**
  * {@inheritDoc}
  */
 public PlannedProcedureDTO createPlannedProcedure(
     PlannedProcedureDTO dto) throws PAException {
     if (PAUtil.isIiNotNull(dto.getIdentifier())) {
         throw new PAException("Update method should be used to modify existing.  ");
     }
     if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
         throw new PAException("StudyProtocol must be set.  ");
     }
     validatePlannedProcedure(dto);
     return createOrUpdatePlannedProcedure(dto);
 }
 
 /**
  * {@inheritDoc}
  */
 public PlannedProcedureDTO updatePlannedProcedure(
          PlannedProcedureDTO dto) throws PAException {
     if (PAUtil.isIiNull(dto.getIdentifier())) {
         throw new PAException("Create method should be used to modify existing.  ");
     }
     return createOrUpdatePlannedProcedure(dto);
 }
 /**
  * {@inheritDoc}
  */
 @SuppressWarnings({"PMD" })
 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
 public List<PlannedProcedureDTO> getPlannedProcedureByStudyProtocol(Ii ii)
 throws PAException {
     if (PAUtil.isIiNull(ii)) {
         return null;
     }

     Session session = null;
     List<PlannedProcedure> queryList = new ArrayList<PlannedProcedure>();
     session = HibernateUtil.getCurrentSession();
     Query query = null;

     // step 1: form the hql
     String hql = "select pa "
         + "from PlannedProcedure pa "
         + "join pa.studyProtocol sp "
         + "where sp.id = :studyProtocolId "
         + "order by pa.id ";

     // step 2: construct query object
     query = session.createQuery(hql);
     query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

     // step 3: query the result
     queryList = query.list();
     ArrayList<PlannedProcedureDTO> resultList = new ArrayList<PlannedProcedureDTO>();
     for (PlannedProcedure bo : queryList) {
         resultList.add(PlannedProcedureConverter.convertFromDomainToDTO(bo));
     }
     return resultList;
 }
 
 /**
  * {@inheritDoc}
  */
 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
 public PlannedProcedureDTO getPlannedProcedure(Ii ii) throws PAException {
     if (PAUtil.isIiNull(ii)) {
         return null;
     }
     PlannedProcedureDTO resultDto = null;
     Session session = null;
     session = HibernateUtil.getCurrentSession();
     PlannedProcedure bo = (PlannedProcedure) session.get(
            PlannedProcedure.class, IiConverter.convertToLong(ii));
     if (bo == null) {
         throw new PAException("Object not found using get() for id = "
                 + IiConverter.convertToString(ii) + ".  ");
     }
     resultDto = PlannedProcedureConverter.convertFromDomainToDTO(bo);
     return resultDto;
 }
 
 
 private void validatePlannedSubstance(PlannedSubstanceAdministrationDTO dto) throws PAException {
   super.validate(dto);
   businessRules(dto);
   drugBusinessRules(dto);
   checkIfValuesExist(dto);
 }
 
 private void validatePlannedProcedure(PlannedProcedureDTO dto) throws PAException {
     super.validate(dto);
     businessRules(dto);
     checkIfValuesExist(dto);
   }
 
 /**
  * Check if values exist.
  * 
  * @param dto the dto
  * 
  * @throws PAException the PA exception
  */
 @SuppressWarnings({"PMD" })
 private void checkIfValuesExist(PlannedSubstanceAdministrationDTO dto) throws PAException {
  StringBuffer errorBuffer = new StringBuffer();  
  if (!PAUtil.isCdNull(dto.getDoseFormCode())) {
   boolean doseFormExists = PAUtil.checkIfValueExists(dto.getDoseFormCode().getCode(), "DOSE_FORM", "CODE");
   if (!doseFormExists) {
       errorBuffer.append("Error while checking for value ")
                  .append(dto.getDoseFormCode().getCode())
                  .append(" from table DOSE_FORM\n");
    }
  }
  if (!PAUtil.isCdNull(dto.getDoseFrequencyCode())) {
      boolean doseFreqExists = PAUtil
          .checkIfValueExists(dto.getDoseFrequencyCode().getCode(), "DOSE_FREQUENCY", "CODE");
      if (!doseFreqExists) {
          errorBuffer.append("Error while checking for value ")
                     .append(dto.getDoseFrequencyCode().getCode())
                     .append(" from table DOSE_FREQUENCY\n");
       }
     }
  if (!PAUtil.isCdNull(dto.getRouteOfAdministrationCode())) {
      boolean roaExists = PAUtil
          .checkIfValueExists(dto.getRouteOfAdministrationCode().getCode(), "ROUTE_OF_ADMINISTRATION", "CODE");
      if (!roaExists) {
          errorBuffer.append("Error while checking for value ")
                     .append(dto.getRouteOfAdministrationCode().getCode())
                     .append(" from table ROUTE_OF_ADMINSTRATION\n");
       }
     }
  if (dto.getDose() != null &&  dto.getDose().getHigh().getUnit() != null) {
      boolean doseUOMExists = PAUtil
         .checkIfValueExists(dto.getDose().getHigh().getUnit(), "UNIT_OF_MEASUREMENT", "CODE");
      if (!doseUOMExists) {
          errorBuffer.append("Error while checking for value ")
                     .append(dto.getDose().getHigh().getUnit())
                     .append(" from table UNIT_OF_MEASUREMENT\n");
       }
  }
  if (dto.getDoseTotal() != null && dto.getDoseTotal().getHigh().getUnit() != null) {
      boolean dosetotalExists = PAUtil
            .checkIfValueExists(dto.getDoseTotal().getHigh().getUnit(), "UNIT_OF_MEASUREMENT", "CODE");
      if (!dosetotalExists) {
          errorBuffer.append("Error while checking for value ")
                     .append(dto.getDoseTotal().getHigh().getUnit())
                     .append(" from table UNIT_OF_MEASUREMENT\n");
       }
  }
 if (dto.getDoseDuration() != null && dto.getDoseDuration().getUnit() != null) {
   boolean doseDurExists = PAUtil
     .checkIfValueExists(dto.getDoseDuration().getUnit(), "UNIT_OF_MEASUREMENT", "CODE");
   if (!doseDurExists) {
     errorBuffer.append("Error while checking for value ")
                .append(dto.getDoseDuration().getUnit())
                .append(" from table UNIT_OF_MEASUREMENT\n");
   }
 }
 if (dto.getSubcategoryCode().getCode().equals(ActivitySubcategoryCode.RADIATION.getCode())) {
   if (!PAUtil.isCdNull(dto.getApproachSiteCode())) {
     boolean approachSite = PAUtil
          .checkIfValueExists(dto.getApproachSiteCode().getCode(), "TARGET_SITE", "CODE");
     if (!approachSite) {
        errorBuffer.append("Error while checking for value ")
        .append(dto.getApproachSiteCode().getCode())
        .append(" from table TARGET_SITE\n");
     }
   }
   if (!PAUtil.isCdNull(dto.getTargetSiteCode())) {
    boolean targetSite = PAUtil
         .checkIfValueExists(dto.getTargetSiteCode().getCode(), "TARGET_SITE", "CODE");
    if (!targetSite) {
      errorBuffer.append("Error while checking for value ")
      .append(dto.getTargetSiteCode().getCode())
      .append(" from table TARGET_SITE\n");
    }
  }
 }
 if (errorBuffer.length() > 0) {
     throw new PAException("Validation Exception " + errorBuffer.toString());
 }
}  
 
 /**
  * Check if values exist.
  * 
  * @param dto the dto
  * 
  * @throws PAException the PA exception
  */
 private void checkIfValuesExist(PlannedProcedureDTO dto) throws PAException {
  StringBuffer errorBuffer = new StringBuffer();  
  if (!PAUtil.isCdNull(dto.getMethodCode())) {
     boolean approachSite = PAUtil
             .checkIfValueExists(dto.getMethodCode().getCode(), "METHOD_CODE", "CODE");
    if (!approachSite) {
           errorBuffer.append("Error while checking for value ")
           .append(dto.getMethodCode().getCode())
           .append(" from table METHOD_CODE\n");
     }
  }
  if (!PAUtil.isCdNull(dto.getTargetSiteCode())) {
     boolean targetSite = PAUtil
            .checkIfValueExists(dto.getTargetSiteCode().getCode(), "TARGET_SITE", "CODE");
     if (!targetSite) {
         errorBuffer.append("Error while checking for value ")
         .append(dto.getTargetSiteCode().getCode())
         .append(" from table TARGET_SITE\n");
     }
  }
  if (errorBuffer.length() > 0) {
      throw new PAException("Validation Exception " + errorBuffer.toString());
  }
 }

 
 private PlannedSubstanceAdministrationDTO createOrUpdatePlannedSubstanceAdministration(
         PlannedSubstanceAdministrationDTO dto) throws PAException {
     PlannedSubstanceAdministration bo = null;
     PlannedSubstanceAdministrationDTO resultDto = null;
     Session session = null;
     session = HibernateUtil.getCurrentSession();
     if (PAUtil.isIiNull(dto.getIdentifier())) {
         bo = PlannedSubstanceAdministrationConverter.convertFromDTOToDomain(dto);
     } else {
         bo = (PlannedSubstanceAdministration) session.load(PlannedSubstanceAdministration.class,
                 IiConverter.convertToLong(dto.getIdentifier()));
         
         PlannedSubstanceAdministration delta = PlannedSubstanceAdministrationConverter.convertFromDTOToDomain(dto);
         bo = delta;
         bo.setDateLastUpdated(new Date());
         session.evict(bo);
     }
    
     session.merge(bo);
     resultDto = PlannedSubstanceAdministrationConverter.convertFromDomainToDTO(bo);
     return resultDto;
 }

 
 private PlannedProcedureDTO createOrUpdatePlannedProcedure(
           PlannedProcedureDTO dto) throws PAException {
     PlannedProcedure bo = null;
     PlannedProcedureDTO resultDto = null;
     Session session = null;
     session = HibernateUtil.getCurrentSession();
     if (PAUtil.isIiNull(dto.getIdentifier())) {
         bo = PlannedProcedureConverter.convertFromDTOToDomain(dto);
     } else {
         bo = (PlannedProcedure) session.load(PlannedProcedure.class,
                 IiConverter.convertToLong(dto.getIdentifier()));
         
         PlannedProcedure delta = PlannedProcedureConverter.convertFromDTOToDomain(dto);
         bo = delta;
         bo.setDateLastUpdated(new Date());
         session.evict(bo);
     }
    
     session.merge(bo);
     resultDto = PlannedProcedureConverter.convertFromDomainToDTO(bo);
     return resultDto;
 }
 
 private PlannedEligibilityCriterionDTO createOrUpdatePlannedEligibilityCriterion(
         PlannedEligibilityCriterionDTO dto) throws PAException {
     PlannedEligibilityCriterion bo = null;
     PlannedEligibilityCriterionDTO resultDto = null;
     Session session = null;
     session = HibernateUtil.getCurrentSession();
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
         bo.setMaxUnit(delta.getMaxUnit());
         bo.setMinUnit(delta.getMinUnit());
         bo.setMaxValue(delta.getMaxValue());
         bo.setMinValue(delta.getMinValue());
         bo.setUnit(delta.getUnit());
         bo.setTextDescription(delta.getTextDescription());
         bo.setUserLastUpdated(delta.getUserLastCreated());
         bo.setDisplayOrder(delta.getDisplayOrder());
         bo.setStructuredIndicator(delta.getStructuredIndicator());
         bo.setTextValue(delta.getTextValue());
     }
     bo.setDateLastUpdated(new Date());
     session.saveOrUpdate(bo);
     resultDto = PlannedEligibilityCriterionConverter.convertFromDomainToDTO(bo);
     return resultDto;
 }

 private void businessRules(PlannedActivityDTO dto) throws PAException {
     if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
         throw new PAException("PlannedActivity.studyProtocol must be set.");
     }
     if (PAUtil.isCdNull(dto.getCategoryCode())) {
         throw new PAException("PlannedActivity.categoryCode must be set.");
     }
     if (PAUtil.isTypeIntervention(dto.getCategoryCode())) {
         
         if (PAUtil.isCdNull(dto.getSubcategoryCode())) {
             throw new PAException("Intervention type must be set.");
         }
         if (PAUtil.isIiNull(dto.getInterventionIdentifier())) {
             throw new PAException("An Intervention must be selected.");
         }            
         if (checkDuplicate(dto)) {
             throw new PAException("Redundancy error:  this trial already includes the selected intervention. ");
         }
      }
 }
 
 private boolean checkDuplicate(PlannedActivityDTO dto) throws PAException {
     boolean duplicate = false;
     InterventionDTO iDto = interventionSrv.get(dto.getInterventionIdentifier());
     String interventionName = iDto.getName().getValue();
    
     List<PlannedActivityDTO> paList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
      for (PlannedActivityDTO padto : paList) {
       if (!PAUtil.isIiNull(padto.getInterventionIdentifier())) {
            InterventionDTO interDto = interventionSrv.get(padto.getInterventionIdentifier());
            String interName = interDto.getName().getValue();
            if (interName.equals(interventionName) 
               && padto.getSubcategoryCode().getCode().equals(dto.getSubcategoryCode().getCode()) 
               && ((padto.getTextDescription().getValue() == null && dto.getTextDescription().getValue() == null)
                 || (padto.getTextDescription().getValue() != null && dto.getTextDescription().getValue() != null
                     && padto.getTextDescription().getValue().equals(dto.getTextDescription().getValue())))
               && ((PAUtil.isBlNull(dto.getLeadProductIndicator()) 
                       && PAUtil.isBlNull(padto.getLeadProductIndicator()))
                    || (!PAUtil.isBlNull(dto.getLeadProductIndicator()) 
                        && !PAUtil.isBlNull(padto.getLeadProductIndicator()) 
                        && padto.getLeadProductIndicator().getValue()
                                .equals(dto.getLeadProductIndicator().getValue())))) {
                    duplicate = true;
                    if (PAUtil.isIiNotNull(dto.getIdentifier())) {
                        String comp1 = padto.getIdentifier().getExtension();
                        String comp2 = dto.getIdentifier().getExtension();
                        if (comp1.equals(comp2)) {
                            // skip if the id is same, this will happen during update
                            duplicate = false;
                        }
                    }
                    
                }
             }
         }    
       return duplicate;
 }
 @SuppressWarnings({"PMD" })
 private void drugBusinessRules(PlannedSubstanceAdministrationDTO dto) throws PAException {
   boolean isDrug = ActivitySubcategoryCode.DRUG.getCode()
             .equals(CdConverter.convertCdToString(dto.getSubcategoryCode()));

     if (!isDrug && (dto.getLeadProductIndicator() != null)) {
         getLogger().info("Setting lead product indicator to null for non-drug PlannedActivity.");
         dto.setLeadProductIndicator(null);
     }
     if (dto.getLeadProductIndicator() == null || PAUtil.isBlNull(dto.getLeadProductIndicator())) {
         getLogger().info("Generating Bl (false) for non-drug PlannedActivity.");
         dto.setLeadProductIndicator(BlConverter.convertToBl(false));

     }
     // only one lead drug per study
     if (BlConverter.covertToBoolean(dto.getLeadProductIndicator())) {
         Long dtoId = IiConverter.convertToLong(dto.getIdentifier());
         boolean dtoIsNew = (dtoId == null);
         List<PlannedSubstanceAdministrationDTO> paList = 
              getPlannedSubstanceAdministrationByStudyProtocol(dto.getStudyProtocolIdentifier());
         for (PlannedSubstanceAdministrationDTO pa : paList) {
             boolean paIsLead = (null == BlConverter.covertToBoolean(pa.getLeadProductIndicator()))
                     ? false : BlConverter.covertToBoolean(pa.getLeadProductIndicator());
             if ((!PAUtil.isIiNull(pa.getInterventionIdentifier()))
                     && (dtoIsNew || !dtoId.equals(IiConverter.convertToLong(pa.getIdentifier())))
                     && paIsLead) {
                 getLogger().warn("It should throw error");
                 throw new PAException("Only one drug may be marked as lead for a given study.");
             }
         }
     }
 }

}
