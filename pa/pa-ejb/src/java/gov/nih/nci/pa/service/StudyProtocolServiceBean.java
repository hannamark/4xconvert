package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.convert.InterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.ObservationalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 08/13/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength",
    "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength" })
public class StudyProtocolServiceBean  implements StudyProtocolServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyProtocolServiceBean.class);
    private static final int FIVE_5 = 5;

    /**
     *
     * @param ii primary id of StudyProtocol
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyProtocolDTO getStudyProtocol(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            LOG.error(" Ii should not be null ");
            throw new PAException(" Ii should not be null ");
        }
        LOG.info("Entering getStudyProtocol");
        Session session = null;
        StudyProtocol studyProtocol = null;
        List<StudyProtocol> queryList = new ArrayList<StudyProtocol>();
        try {
            session = HibernateUtil.getCurrentSession();
            //@todo : session.load is not working, so using query to retrieve
            // studyProtocol = (StudyProtocol)
            // session.load(StudyProtocol.class, Long.valueOf(ii.getExtension()));
            //session.flush();

            Query query = null;

            // step 1: form the hql
            String hql = "select sp "
                       + "from StudyProtocol sp "
                       + "where sp.id =  " + Long.valueOf(ii.getExtension());
            LOG.info(" query StudyOverallStatus = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();

            studyProtocol = queryList.get(0);
            session.flush();

        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while retrieving StudyProtocol for id = " + ii.getExtension() , hbe);
            throw new PAException(" Hibernate exception while retrieving "
                    + "StudyProtocol for id = " + ii.getExtension() , hbe);
        }

        StudyProtocolDTO studyProtocolDTO =
            StudyProtocolConverter.convertFromDomainToDTO(studyProtocol);


        LOG.info("Leaving getStudyProtocol");
        return studyProtocolDTO;

    }



    /**
     *
     * @param studyProtocolDTO studyProtocolDTO
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    @SuppressWarnings("PMD.NPathComplexity")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyProtocolDTO updateStudyProtocol(StudyProtocolDTO studyProtocolDTO) throws PAException {
        // enforce business rules
        if (studyProtocolDTO == null) {
            LOG.error(" studyProtocolDTO should not be null ");
            throw new PAException(" studyProtocolDTO should not be null ");

        }
        Timestamp sDate = TsConverter.convertToTimestamp(studyProtocolDTO.getStartDate());
        Timestamp cDate = TsConverter.convertToTimestamp(studyProtocolDTO.getPrimaryCompletionDate());
        ActualAnticipatedTypeCode sCode = ActualAnticipatedTypeCode.getByCode(
                studyProtocolDTO.getStartDateTypeCode().getCode());
        ActualAnticipatedTypeCode cCode = ActualAnticipatedTypeCode.getByCode(
                studyProtocolDTO.getPrimaryCompletionDateTypeCode().getCode());
        Timestamp now = new Timestamp((new Date()).getTime());
        if (sDate == null) {
            throw new PAException("Start date must be set.  ");
        }
        if (cDate == null) {
            throw new PAException("Completion date must be set.  ");
        }
        if (sCode.equals(ActualAnticipatedTypeCode.ACTUAL) && now.before(sDate)) {
            throw new PAException("Actual start dates cannot be in the future.  ");
        }
        if (cCode.equals(ActualAnticipatedTypeCode.ACTUAL) && now.before(cDate)) {
            throw new PAException("Actual primary completion dates cannot be in the future.  ");
        }
        if (sCode.equals(ActualAnticipatedTypeCode.ANTICIPATED) && now.after(sDate)) {
            throw new PAException("Anticipated start dates must be in the future.  ");
        }
        if (cCode.equals(ActualAnticipatedTypeCode.ANTICIPATED) && now.after(cDate)) {
            throw new PAException("Anticipated primary completion dates must be in the future.  ");
        }
        if ((sDate != null) && (cDate != null) && (cDate.before(sDate))) {
            throw new PAException("Primary completion date must be >= start date.");

        }


        StudyProtocolDTO  spDTO = null;
        Session session = null;
        List<StudyProtocol> queryList = new ArrayList<StudyProtocol>();

        try {
            session = HibernateUtil.getCurrentSession();
            //@todo : session.load is not working, so using query to retrieve
            //StudyProtocol sp = (StudyProtocol) session.load(StudyProtocol.class,
            //        Long.valueOf(studyProtocolDTO.getIdentifier().getExtension()));


            Query query = null;

            // step 1: form the hql
            String hql = "select sp "
                       + "from StudyProtocol sp "
                       + "where sp.id =  " + Long.valueOf(studyProtocolDTO.getIdentifier().getExtension());
            LOG.info(" query StudyOverallStatus = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();

            StudyProtocol sp = queryList.get(0);

            StudyProtocol studyProtocol = StudyProtocolConverter.convertFromDTOToDomain(studyProtocolDTO);
            //@todo : merge is not working so using ind. update
            /*
            if (!studyProtocol.equals(sp)) {
                sp = (StudyProtocol) session.merge(studyProtocol);
            } else {
                sp = studyProtocol;
            }
            */
            sp.setDateLastUpdated(now);
            //sp.setMonitorCode(studyProtocol.getMonitorCode());
            sp.setAccrualReportingMethodCode(studyProtocol.getAccrualReportingMethodCode());
            sp.setStartDate(sDate);
            sp.setPrimaryCompletionDate(cDate);
            sp.setStartDateTypeCode(sCode);
            sp.setPrimaryCompletionDateTypeCode(cCode);
            sp.setUserLastUpdated(StConverter.convertToString(studyProtocolDTO.getUserLastUpdated()));
            session.update(sp);
            session.flush();

            //spDTO =  StudyProtocolConverter.convertFromDomainToDTO(sp);
            spDTO =  StudyProtocolConverter.convertFromDomainToDTO(studyProtocol);
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while updating StudyProtocol for id = "
                    + studyProtocolDTO.getIdentifier().getExtension() , hbe);
            throw new PAException(" Hibernate exception while updating StudyProtocol for id = "
                    + studyProtocolDTO.getIdentifier().getExtension() , hbe);
        }

        return spDTO;

    }



    /**
     *
     * @param ii ii
     * @return InterventionalStudyProtocolDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public InterventionalStudyProtocolDTO getInterventionalStudyProtocol(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            LOG.error(" Ii should not be null ");
            throw new PAException(" Ii should not be null ");
        }
        LOG.info("Entering getInterventionalStudyProtocol");
        Session session = null;

        List<InterventionalStudyProtocol> queryList = new ArrayList<InterventionalStudyProtocol>();
        InterventionalStudyProtocol isp = null;
        try {
            Query query = null;
            session = HibernateUtil.getCurrentSession();
            // step 1: form the hql
            String hql = "select isp "
                       + "from InterventionalStudyProtocol isp "
                       + "where isp.id =  " + Long.valueOf(ii.getExtension());
            LOG.info(" query InterventionalStudyProtocol = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();

            isp = queryList.get(0);
            session.flush();


        }  catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving InterventionalStudyProtocol for id = "
                    + ii.getExtension() , hbe);
            throw new PAException(" Hibernate exception while retrieving "
                    + "InterventionalStudyProtocol for id = " + ii.getExtension() , hbe);
        }
        InterventionalStudyProtocolDTO ispDTO =
            InterventionalStudyProtocolConverter.convertFromDomainToDTO(isp);

        LOG.info("Leaving getInterventionalStudyProtocol");
        return ispDTO;

    }


    /**
     *
     * @param ispDTO studyProtocolDTO
     * @return InterventionalStudyProtocolDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public InterventionalStudyProtocolDTO updateInterventionalStudyProtocol(
            InterventionalStudyProtocolDTO ispDTO) throws PAException {
        // enforce business rules
        if (ispDTO == null) {
            LOG.error(" studyProtocolDTO should not be null ");
            throw new PAException(" studyProtocolDTO should not be null ");

        }
        Timestamp now = new Timestamp((new Date()).getTime());
        InterventionalStudyProtocolDTO  ispRetDTO = null;
        Session session = null;
//        List<InterventionalStudyProtocol> queryList = new ArrayList<InterventionalStudyProtocol>();

        try {
            session = HibernateUtil.getCurrentSession();
//            Query query = null;
//            String hql = "select isp "
//                       + "from InterventionalStudyProtocol isp "
//                       + "where isp.id =  " + Long.valueOf(ispDTO.getIdentifier().getExtension());
//            LOG.info(" query InterventionalStudyProtocol = " + hql);
//            query = session.createQuery(hql);
//            queryList = query.list();
//            InterventionalStudyProtocol isp = queryList.get(0);

            InterventionalStudyProtocol isp = (InterventionalStudyProtocol) 
                session.load(InterventionalStudyProtocol.class, Long.valueOf(ispDTO.getIdentifier().getExtension()));

            InterventionalStudyProtocol upd = InterventionalStudyProtocolConverter.
                                convertFromDTOToDomain(ispDTO);
          upd.setUserLastUpdated(StConverter.convertToString(ispDTO.getUserLastUpdated()));
          upd.setDateLastUpdated(now);

          if (!upd.equals(isp)) {
              isp = (InterventionalStudyProtocol) session.merge(upd);
          } else {
              isp = upd;
          }

            
            // overwrite the values
//            isp.setAccrualReportingMethodCode(upd.getAccrualReportingMethodCode());
//            isp.setAcronym(upd.getAcronym());
//            isp.setDataMonitoringCommitteeAppointedIndicator(upd.getDataMonitoringCommitteeAppointedIndicator());
//            isp.setDelayedpostingIndicator(upd.getDelayedpostingIndicator());
//            isp.setExpandedAccessIndicator(upd.getExpandedAccessIndicator());
//            isp.setFdaRegulatedIndicator(upd.getFdaRegulatedIndicator());
//            isp.setIndIdeIndicator(upd.getIndIdeIndicator());
//            isp.setOfficialTitle(upd.getOfficialTitle());
//            isp.setPhaseCode(upd.getPhaseCode());
//            isp.setPrimaryCompletionDate(upd.getPrimaryCompletionDate());
//            isp.setPrimaryCompletionDateTypeCode(upd.getPrimaryCompletionDateTypeCode());
//            isp.setPrimaryPurposeCode(upd.getPrimaryPurposeCode());
//            isp.setSection801Indicator(upd.getSection801Indicator());
//            isp.setStartDate(upd.getStartDate());
//            isp.setStartDateTypeCode(upd.getStartDateTypeCode());
//            isp.setDesignConfigurationCode(upd.getDesignConfigurationCode());
//            isp.setNumberOfInterventionGroups(upd.getNumberOfInterventionGroups());
//            isp.setBlindingSchemaCode(upd.getBlindingSchemaCode());
//            isp.setAllocationCode(upd.getAllocationCode());
//            isp.setPhaseOtherText(upd.getPhaseOtherText());
//            isp.setPrimaryPurposeOtherText(upd.getPrimaryPurposeOtherText());
//            //isp.setBlindingRoleCode(upd.getBlindingRoleCode());
//            isp.setStudyClassificationCode(upd.getStudyClassificationCode());
//            isp.setMaximumTargetAccrualNumber(upd.getMaximumTargetAccrualNumber());

//            isp.setUserLastUpdated(StConverter.convertToString(ispDTO.getUserLastUpdated()));
//            isp.setDateLastUpdated(now);



            session.update(isp);
            session.flush();
            //spDTO =  StudyProtocolConverter.convertFromDomainToDTO(sp);
            ispRetDTO =  InterventionalStudyProtocolConverter.convertFromDomainToDTO(isp);
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while updating InterventionalStudyProtocol for id = "
                    + ispDTO.getIdentifier().getExtension() , hbe);
            throw new PAException(" Hibernate exception while updating InterventionalStudyProtocol for id = "
                    + ispDTO.getIdentifier().getExtension() , hbe);
        }
        return ispRetDTO;

    }

    /**
     * for creating a new ISP.
     * @param ispDTO  for isp
     * @return ii ii
     * @throws PAException exception
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Ii createInterventionalStudyProtocol(InterventionalStudyProtocolDTO ispDTO)
    throws PAException {
        if (ispDTO == null) {
            LOG.error(" studyProtocolDTO should not be null ");
            throw new PAException(" studyProtocolDTO should not be null ");

        }
        if (ispDTO.getIdentifier() != null && ispDTO.getIdentifier().getExtension() != null) {
            LOG.error(" Extension should be null = " + ispDTO.getIdentifier().getExtension());
            throw new PAException("  Extension should be null, but got  = " + ispDTO.getIdentifier().getExtension());

        }
        LOG.debug("Entering createInterventionalStudyProtocol");
        InterventionalStudyProtocol isp = InterventionalStudyProtocolConverter.
        convertFromDTOToDomain(ispDTO);
        isp.setDateLastUpdated(new Timestamp((new Date()).getTime()));
        isp.setIdentifier(generateNciIdentifier());
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(isp);
            LOG.info("Creating isp for id = " + isp.getId());
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while creating InterventionalStudyProtocol for id = "
                    + ispDTO.getIdentifier().getExtension() , hbe);
            throw new PAException(" Hibernate exception while updating InterventionalStudyProtocol for id = "
                    + ispDTO.getIdentifier().getExtension() , hbe);
        } finally {
            session.flush();
        }
        createDocumentWorkFlowStatus(isp);
        LOG.debug("Leaving createInterventionalStudyProtocol");
        return IiConverter.convertToIi(isp.getId());

    }



    private String generateNciIdentifier() throws PAException {
        Session session = null;
        Calendar today = Calendar.getInstance();
        int currentYear  = today.get(Calendar.YEAR);
        String query = "select max(sp.identifier) from StudyProtocol sp where "
            + "sp.identifier like '%" + currentYear + "%' ";
        String nciIdentifier;

        try {
            session = HibernateUtil.getCurrentSession();
            Query queryObject = session.createQuery(query);
            List result = queryObject.list();
            String maxValue = (String) result.get(0);
            if (maxValue != null && PAUtil.isNotEmpty(maxValue)) {
                String maxNumber = maxValue.substring(maxValue.lastIndexOf('-') + 1 , maxValue.length());
                StringBuffer nextNumber = new StringBuffer(String.valueOf(Integer.parseInt(maxNumber) + 1));
                while (nextNumber.length() < FIVE_5) {
                    nextNumber.insert(0, "0");
                }
                nciIdentifier = "NCI-" + currentYear + "-" + nextNumber;
            } else {
                nciIdentifier = "NCI-" + currentYear + "-00001";
            }

        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while creating NciIdentifier " , hbe);
            throw new PAException(" Hibernate exception while creating NciIdentifier " , hbe);
        } finally {
            session.flush();
        }
        return nciIdentifier;
    }

    /**
     *
     * @param ii ii
     * @return ObservationalStudyProtocolDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ObservationalStudyProtocolDTO getObservationalStudyProtocol(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            LOG.error(" Ii should not be null ");
            throw new PAException(" Ii should not be null ");
        }
        LOG.info("Entering getObservationalStudyProtocol");
        Session session = null;

        List<ObservationalStudyProtocol> queryList = new ArrayList<ObservationalStudyProtocol>();
        ObservationalStudyProtocol osp = null;
        try {
            Query query = null;
            session = HibernateUtil.getCurrentSession();
            // step 1: form the hql
            String hql = "select isp "
                       + "from ObservationalStudyProtocol isp "
                       + "where isp.id =  " + Long.valueOf(ii.getExtension());
            LOG.info(" query ObservationalStudyProtocol = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();

            osp = queryList.get(0);
            session.flush();


        }  catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving getObservationalStudyProtocol for id = " 
                    + ii.getExtension() , hbe);
            throw new PAException(" Hibernate exception while retrieving "
                    + "getObservationalStudyProtocol for id = " + ii.getExtension() , hbe);
        }
        ObservationalStudyProtocolDTO ospDTO = 
            ObservationalStudyProtocolConverter.convertFromDomainToDTO(osp);

        LOG.info("Leaving getObservationalStudyProtocol");
        return ospDTO;
        
    }  
    
    /**
     * 
     * @param ospDTO ObservationalStudyProtocolDTO
     * @return ObservationalStudyProtocolDTO
     * @throws PAException PAException
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ObservationalStudyProtocolDTO updateObservationalStudyProtocol(
            ObservationalStudyProtocolDTO ospDTO) throws PAException {
        // enforce business rules
        if (ospDTO == null) {
            LOG.error(" studyProtocolDTO should not be null ");
            throw new PAException(" studyProtocolDTO should not be null ");
            
        }
        Timestamp now = new Timestamp((new Date()).getTime());
        ObservationalStudyProtocolDTO  ospRetDTO = null;
        Session session = null;
        List<ObservationalStudyProtocol> queryList = new ArrayList<ObservationalStudyProtocol>();
        
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            String hql = "select isp "
                       + "from ObservationalStudyProtocol isp "
                       + "where isp.id =  " + Long.valueOf(ospDTO.getIdentifier().getExtension());
            LOG.info(" query ObservationalStudyProtocol = " + hql);
            query = session.createQuery(hql);
            queryList = query.list();
            ObservationalStudyProtocol osp = queryList.get(0);
            
            ObservationalStudyProtocol upd = ObservationalStudyProtocolConverter.
                                convertFromDTOToDomain(ospDTO);
            // overwrite the values
            osp.setSamplingMethodCode(upd.getSamplingMethodCode());
            osp.setStudyModelCode(upd.getStudyModelCode());
            osp.setStudyModelOtherText(upd.getStudyModelOtherText());
            osp.setTimePerspectiveCode(upd.getTimePerspectiveCode());
            osp.setTimePerspectiveOtherText(upd.getTimePerspectiveOtherText());
            osp.setBiospecimenDescription(upd.getBiospecimenDescription());
            osp.setBiospecimenRetentionCode(upd.getBiospecimenRetentionCode());
            osp.setNumberOfGroups(upd.getNumberOfGroups());
            osp.setMaximumTargetAccrualNumber(upd.getMaximumTargetAccrualNumber());
            osp.setUserLastUpdated(StConverter.convertToString(ospDTO.getUserLastUpdated()));
            osp.setDateLastUpdated(now);

            session.update(osp);
            session.flush();
            ospRetDTO =  ObservationalStudyProtocolConverter.convertFromDomainToDTO(osp);
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while updating ObservationalStudyProtocol for id = " 
                    + ospDTO.getIdentifier().getExtension() , hbe);
            throw new PAException(" Hibernate exception while updating ObservationalStudyProtocol for id = " 
                    + ospDTO.getIdentifier().getExtension() , hbe);
        }
        return ospRetDTO;
        
    }
    
    /**
     * for creating a new OSP.
     * @param ispDTO  for osp
     * @return ii ii
     * @throws PAException exception
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Ii createObservationalStudyProtocol(ObservationalStudyProtocolDTO ispDTO)
    throws PAException {
        if (ispDTO == null) {
            LOG.error(" studyProtocolDTO should not be null ");
            throw new PAException(" studyProtocolDTO should not be null ");
            
        }
        if (ispDTO.getIdentifier() != null && ispDTO.getIdentifier().getExtension() != null) {
            LOG.error(" Extension should be null = " + ispDTO.getIdentifier().getExtension());
            throw new PAException("  Extension should be null, but got  = " + ispDTO.getIdentifier().getExtension());
            
        }
        LOG.debug("Entering createObservationalStudyProtocol");
        ObservationalStudyProtocol osp = ObservationalStudyProtocolConverter.
        convertFromDTOToDomain(ispDTO);
        osp.setDateLastUpdated(new Timestamp((new Date()).getTime()));
        osp.setIdentifier(generateNciIdentifier());
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(osp);
            LOG.info("Creating osp for id = " + osp.getId());
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while creating createObservationalStudyProtocol for id = " 
                    + ispDTO.getIdentifier().getExtension() , hbe);
            throw new PAException(" Hibernate exception while createObservationalStudyProtocol for id = " 
                    + ispDTO.getIdentifier().getExtension() , hbe);
        } finally {
            session.flush();
        }
        LOG.debug("Leaving createInterventionalStudyProtocol");
        return IiConverter.convertToIi(osp.getId());
        
    }


    private void createDocumentWorkFlowStatus(StudyProtocol sp) throws PAException {
        Session session = null;
        LOG.info("Entering createDocumentWorkFlowStatus");
        try {
            session = HibernateUtil.getCurrentSession();
            DocumentWorkflowStatus wfs = new DocumentWorkflowStatus();
            wfs.setStudyProtocol(sp);
            wfs.setStatusCode(DocumentWorkflowStatusCode.SUBMITTED);
            wfs.setStatusDateRangeLow(new Timestamp(sp.getDateLastUpdated().getTime()));
            wfs.setUserLastUpdated(sp.getUserLastUpdated());
            wfs.setDateLastUpdated(sp.getDateLastUpdated());
            session.save(wfs);
            LOG.info("Creating wfs for id = " + wfs.getId());
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while creating DocumentWorkflow statys for protocol id = "
                    + sp.getId() , hbe);
            throw new PAException(" Hibernate exception while creating DocumentWorkflow statys for protocol id = "
                    + sp.getId() , hbe);
        } finally {
            session.flush();
        }
        LOG.info("Leaving createDocumentWorkFlowStatus");
    }

}
