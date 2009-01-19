package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.convert.InterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.ObservationalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.JNDIUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
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
    "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength", "PMD.NPathComplexity" })
    public class StudyProtocolServiceBean  implements StudyProtocolServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyProtocolServiceBean.class);
    private static final int FIVE_5 = 5;

    private SessionContext ejbContext;
    
    @Resource
    void setSessionContext(SessionContext ctx) {
    this.ejbContext = ctx;
    }
    
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
        try {
            session = HibernateUtil.getCurrentSession();
            studyProtocol = (StudyProtocol)
            session.load(StudyProtocol.class, Long.valueOf(ii.getExtension()));
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
        
        enForceBusinessRules(studyProtocolDTO);

        StudyProtocolDTO  spDTO = null;
        Session session = null;
        Timestamp now = new Timestamp((new Date()).getTime());

        try {
            session = HibernateUtil.getCurrentSession();
            StudyProtocol sp = (StudyProtocol) session.load(StudyProtocol.class,
                    Long.valueOf(studyProtocolDTO.getIdentifier().getExtension()));         

            StudyProtocolConverter.convertFromDTOToDomain(studyProtocolDTO, sp);
            
            if (ejbContext != null) {
            sp.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
            }
            sp.setDateLastUpdated(now);
            session.update(sp);
            spDTO =  StudyProtocolConverter.convertFromDomainToDTO(sp);
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while updating StudyProtocol for id = "
                    + studyProtocolDTO.getIdentifier().getExtension() , hbe);
            throw new PAException(" Hibernate exception while updating StudyProtocol for id = "
                    + studyProtocolDTO.getIdentifier().getExtension() , hbe);
        } finally {
            session.flush();
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

        InterventionalStudyProtocol isp = null;
        try {
            session = HibernateUtil.getCurrentSession();           
            isp = (InterventionalStudyProtocol)
            session.load(InterventionalStudyProtocol.class, Long.valueOf(ii.getExtension()));
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
        int totBlindCodes = 0;
        if (ispDTO == null) {
            LOG.error(" InterventionalstudyProtocolDTO should not be null ");
            throw new PAException(" InterventionalstudyProtocolDTO should not be null ");

        }
        enForceBusinessRules(ispDTO);
        if (ispDTO.getBlindedRoleCode() != null && ispDTO.getBlindedRoleCode().getItem() != null) {
            totBlindCodes = ispDTO.getBlindedRoleCode().getItem().size();
        }
        if (ispDTO.getBlindingSchemaCode() != null) {
            if (BlindingSchemaCode.OPEN.getCode().equals(ispDTO.getBlindingSchemaCode().getCode()) 
                    && totBlindCodes > 0) {
                throw new PAException(" Open Blinding Schema code cannot have any Blinded codes ");
            }
            if (BlindingSchemaCode.SINGLE_BLIND.getCode().equals(ispDTO.getBlindingSchemaCode().getCode())
                    && totBlindCodes > 1) {
                throw new PAException(" Only one masking role must be specified for ‘Single Blind’ masking. ");
            }
            if (BlindingSchemaCode.SINGLE_BLIND.getCode().equals(ispDTO.getBlindingSchemaCode().getCode())
                           && totBlindCodes < 1) {
              throw new PAException(" Single Blinding Schema code must have 1 Blinded code ");
            }
            if (BlindingSchemaCode.DOUBLE_BLIND.getCode().equals(ispDTO.getBlindingSchemaCode().getCode())
                    && totBlindCodes < 2) {
                throw new PAException(" At least two masking roles must to be specified for ‘Double Blind’ masking. ");
            }

        }
        Timestamp now = new Timestamp((new Date()).getTime());
        InterventionalStudyProtocolDTO  ispRetDTO = null;
        Session session = null;

        try {
            session = HibernateUtil.getCurrentSession();

            InterventionalStudyProtocol isp = (InterventionalStudyProtocol) 
            session.load(InterventionalStudyProtocol.class, Long.valueOf(ispDTO.getIdentifier().getExtension()));

            InterventionalStudyProtocol upd = InterventionalStudyProtocolConverter.
            convertFromDTOToDomain(ispDTO);
            if (ejbContext != null) {
            upd.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
            }
            upd.setDateLastUpdated(now);
            isp = upd;
            session.merge(isp);
            session.flush();

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
        enForceBusinessRules(ispDTO);
        LOG.debug("Entering createInterventionalStudyProtocol");
        InterventionalStudyProtocol isp = InterventionalStudyProtocolConverter.
        convertFromDTOToDomain(ispDTO);
        isp.setIdentifier(generateNciIdentifier());
        if (ejbContext != null) {
            isp.setUserLastCreated(ejbContext.getCallerPrincipal().getName());
        }
        isp.setDateLastCreated(new Timestamp((new Date()).getTime()));
        if (ispDTO.getUserLastCreated() != null) {
            isp.setUserLastCreated(ispDTO.getUserLastCreated().getValue());
        }
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

        ObservationalStudyProtocol osp = null;
        try {
            session = HibernateUtil.getCurrentSession();                     
            osp = (ObservationalStudyProtocol)
            session.load(ObservationalStudyProtocol.class, Long.valueOf(ii.getExtension()));
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
        enForceBusinessRules(ospDTO);
        Timestamp now = new Timestamp((new Date()).getTime());
        ObservationalStudyProtocolDTO  ospRetDTO = null;
        Session session = null;

        try {
            session = HibernateUtil.getCurrentSession();            
            ObservationalStudyProtocol osp = (ObservationalStudyProtocol) 
            session.load(ObservationalStudyProtocol.class, Long.valueOf(ospDTO.getIdentifier().getExtension()));

            ObservationalStudyProtocol upd = ObservationalStudyProtocolConverter.
            convertFromDTOToDomain(ospDTO);
            if (ejbContext != null) {
                upd.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
            }
            upd.setDateLastUpdated(now);


            osp = upd;

            session.merge(osp);
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
        enForceBusinessRules(ispDTO);
        LOG.debug("Entering createObservationalStudyProtocol");
        ObservationalStudyProtocol osp = ObservationalStudyProtocolConverter.
        convertFromDTOToDomain(ispDTO);
        osp.setDateLastCreated(new Timestamp((new Date()).getTime()));
        if (ejbContext != null) {
            osp.setUserLastCreated(ejbContext.getCallerPrincipal().getName());
        }
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
        
        createDocumentWorkFlowStatus(osp);
        
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
            wfs.setStatusDateRangeLow(new Timestamp((new Date()).getTime()));
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
    

    private void enForceBusinessRules(StudyProtocolDTO studyProtocolDTO) throws PAException {
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
        if (sCode == null) {
            throw new PAException("Start date type must be set.  ");
        }
        if (cCode == null) {
            throw new PAException("Completion date type must be set.  ");
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
        //
        if ((studyProtocolDTO.getFdaRegulatedIndicator() != null) 
                && (studyProtocolDTO.getFdaRegulatedIndicator().getValue() != null) 
                && (!Boolean.valueOf(studyProtocolDTO.getFdaRegulatedIndicator().getValue()))) {
            StudyIndldeServiceLocal local = (StudyIndldeServiceLocal) 
                                JNDIUtil.lookup("pa/StudyIndldeServiceBean/local");
            List<StudyIndldeDTO> list = local.getByStudyProtocol(studyProtocolDTO.getIdentifier());
            if (!list.isEmpty()) {
                throw new PAException("Unable to set FDARegulatedIndicator to 'No', " 
                        + " Please remove IND/IDEs and try again");
            }
        }
        
    }
        
}
