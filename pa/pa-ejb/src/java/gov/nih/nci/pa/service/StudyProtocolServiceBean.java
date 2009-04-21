/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.convert.InterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.ObservationalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRelationshipDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.JNDIUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * @author Hugh Reinhart
 * @since 08/13/2008
 */
@Stateless
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength",
    "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength", "PMD.NPathComplexity", "PMD.TooManyMethods" })
    public class StudyProtocolServiceBean
                    implements StudyProtocolServiceRemote, StudyProtocolServiceLocal {

    private static final Logger LOG  = Logger.getLogger(StudyProtocolServiceBean.class);
    private static final int FIVE_5 = 5;
    @EJB
    StudyRelationshipServiceLocal studyRelationshipService = null;
    private SessionContext ejbContext;

    @Resource
    void setSessionContext(SessionContext ctx) {
    this.ejbContext = ctx;
    }

    DocumentWorkflowStatusServiceRemote documentWorkflowStatusService;

    /**
     * @param documentWorkflowStatusService the documentWorkflowStatusService to set
     */
    @EJB
    void setDocumentWorkflowStatusService(
            DocumentWorkflowStatusServiceRemote documentWorkflowStatusService) {
        this.documentWorkflowStatusService = documentWorkflowStatusService;
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
    * @param dto of StudyProtocolDTO
    * @return List StudyProtocolDTO
    * @throws PAException PAException
    */
   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
   public List<StudyProtocolDTO> search(StudyProtocolDTO dto) throws PAException {
       if (dto == null) {
           LOG.error(" StudyProtocolDTO should not be null ");
           throw new PAException(" StudyProtocolDTO should not be null ");
       }
       LOG.info("Entering getStudyProtocol");
       Session session = null;
       List <StudyProtocol> studyProtocolList = null;
       try {
           session = HibernateUtil.getCurrentSession();
           StudyProtocol exampleDO = new StudyProtocol();
           exampleDO.setIdentifier(IiConverter.convertToString(dto.getAssignedIdentifier()));
           Example example = Example.create(exampleDO);
           studyProtocolList = session.createCriteria(StudyProtocol.class).add(example).list();
           session.flush();

       }  catch (HibernateException hbe) {
           LOG.error(" Hibernate exception while retrieving StudyProtocol for dto = " + hbe);
           throw new PAException(" Hibernate exception while retrieving "
                   + "StudyProtocol for dto = " +  hbe);
       }

       LOG.info("Leaving getStudyProtocol");
       
       List<StudyProtocolDTO> studyProtocolDTOList = null;
       if (studyProtocolList != null) {
    studyProtocolDTOList = new ArrayList<StudyProtocolDTO>();
    for (StudyProtocol sp : studyProtocolList) {
    StudyProtocolDTO studyProtocolDTO =
              StudyProtocolConverter.convertFromDomainToDTO(sp);
    studyProtocolDTOList.add(studyProtocolDTO);
    }
    }
       return studyProtocolDTOList;

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
        Session session = null;

        try {
            session = HibernateUtil.getCurrentSession();
            setDefaultValues(isp , ispDTO , session);
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
        return IiConverter.converToStudyProtocolIi(isp.getId());

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
     * @param ospDTO  for osp
     * @return ii ii
     * @throws PAException exception
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Ii createObservationalStudyProtocol(ObservationalStudyProtocolDTO ospDTO)
    throws PAException {
        if (ospDTO == null) {
            LOG.error(" Observational studyProtocolDTO should not be null ");
            throw new PAException(" studyProtocolDTO should not be null ");

        }
        if (ospDTO.getIdentifier() != null && ospDTO.getIdentifier().getExtension() != null) {
            LOG.error(" Extension should be null = " + ospDTO.getIdentifier().getExtension());
            throw new PAException("  Extension should be null, but got  = " + ospDTO.getIdentifier().getExtension());

        }
        enForceBusinessRules(ospDTO);
        LOG.debug("Entering createObservationalStudyProtocol");
        ObservationalStudyProtocol osp = ObservationalStudyProtocolConverter.
            convertFromDTOToDomain(ospDTO);
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            setDefaultValues(osp, ospDTO, session);
            session.save(osp);
            LOG.info("Creating osp for id = " + osp.getId());
        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while creating createObservationalStudyProtocol for id = "
                    + ospDTO.getIdentifier().getExtension() , hbe);
            throw new PAException(" Hibernate exception while createObservationalStudyProtocol for id = "
                    + ospDTO.getIdentifier().getExtension() , hbe);
        } finally {
            session.flush();
        }

        createDocumentWorkFlowStatus(osp);

        LOG.debug("Leaving createInterventionalStudyProtocol");
        return IiConverter.converToStudyProtocolIi(osp.getId());

    }

    /**
     * deletes protocol and all of its related classes.
     * @param ii ii of study Protocol
     * @throws PAException on any error
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteStudyProtocol(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            LOG.error(" Ii should not be null ");
            throw new PAException(" Ii should not be null ");
        }
        LOG.info("Entering getStudyProtocol");
        Session session = null;
        StudyProtocol studyProtocol = null;
        Ii targetSpIi = null;
        try {
            session = HibernateUtil.getCurrentSession();
            studyProtocol = (StudyProtocol)
                session.load(StudyProtocol.class, Long.valueOf(ii.getExtension()));
            List<StudyRelationshipDTO> dtos = studyRelationshipService.getByStudyProtocol(ii);
           
            for (StudyRelationshipDTO dto : dtos) {
                targetSpIi = dto.getTargetStudyProtocolIdentifier();
                break;
            }
            session.delete(studyProtocol);
            if (targetSpIi != null) {
                StudyProtocol spTarget = (StudyProtocol)
                session.load(StudyProtocol.class, Long.valueOf(targetSpIi.getExtension()));
                spTarget.setStatusCode(ActStatusCode.ACTIVE);
                spTarget.setStatusDate(new Timestamp(spTarget.getDateLastCreated().getTime()));
                session.update(spTarget);
            }
        } catch (HibernateException hbe) {
            ejbContext.setRollbackOnly();
            LOG.error(" Hibernate exception while retrieving StudyProtocol for id = " + ii.getExtension() , hbe);
            throw new PAException(" Hibernate exception while retrieving "
                    + "StudyProtocol for id = " + ii.getExtension() , hbe);
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        }
    }

    private void createDocumentWorkFlowStatus(StudyProtocol sp) throws PAException {
        LOG.debug("Entering createDocumentWorkFlowStatus().");
        DocumentWorkflowStatusDTO dwDto = new DocumentWorkflowStatusDTO();
        dwDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.SUBMITTED));
        dwDto.setStatusDateRange(TsConverter.convertToTs(new Timestamp((new Date()).getTime())));
        dwDto.setStudyProtocolIdentifier(IiConverter.convertToIi(sp.getId()));
        LOG.debug("Creating wfs for id = " + sp.getIdentifier());
        documentWorkflowStatusService.create(dwDto);
        LOG.debug("Leaving createDocumentWorkFlowStatus().");
    }

    private String generateNciIdentifier(Session session) {
        Calendar today = Calendar.getInstance();
        int currentYear  = today.get(Calendar.YEAR);
        String query = "select max(sp.identifier) from StudyProtocol sp where "
            + "sp.identifier like '%" + currentYear + "%' ";
        String nciIdentifier;

        Query queryObject = session.createQuery(query);
        String maxValue = (String) queryObject.list().get(0);
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

        return nciIdentifier;
    }


    private void enForceBusinessRules(StudyProtocolDTO studyProtocolDTO) throws PAException {
        boolean dateRulesApply = false;
        if (PAUtil.isIiNull(studyProtocolDTO.getIdentifier())) {
            dateRulesApply = true;
        } else {
            StudyProtocol oldBo = StudyProtocolConverter.convertFromDTOToDomain(
                    getStudyProtocol(studyProtocolDTO.getIdentifier()));
            StudyProtocol newBo = StudyProtocolConverter.convertFromDTOToDomain(studyProtocolDTO);
            if (!oldBo.getPrimaryCompletionDate().equals(newBo.getPrimaryCompletionDate())
                    || !oldBo.getPrimaryCompletionDateTypeCode().equals(newBo.getPrimaryCompletionDateTypeCode())
                    || !oldBo.getStartDate().equals(newBo.getStartDate())
                    || !oldBo.getStartDateTypeCode().equals(newBo.getStartDateTypeCode())) {
                dateRulesApply = true;
            }
        }
        if (dateRulesApply) {
            enForceDateRules(studyProtocolDTO);
        }

        //
        if ((studyProtocolDTO.getIdentifier() != null && studyProtocolDTO.getFdaRegulatedIndicator() != null)
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

    private void enForceDateRules(StudyProtocolDTO studyProtocolDTO) throws PAException {
        Timestamp sDate = TsConverter.convertToTimestamp(studyProtocolDTO.getStartDate());
        Timestamp cDate = TsConverter.convertToTimestamp(studyProtocolDTO.getPrimaryCompletionDate());
        ActualAnticipatedTypeCode sCode =  null;
        ActualAnticipatedTypeCode cCode = null;
        if (studyProtocolDTO.getStartDateTypeCode() != null) {
            sCode = ActualAnticipatedTypeCode.getByCode(
                studyProtocolDTO.getStartDateTypeCode().getCode());
        }
        if (studyProtocolDTO.getPrimaryCompletionDateTypeCode() != null) {
            cCode = ActualAnticipatedTypeCode.getByCode(
                    studyProtocolDTO.getPrimaryCompletionDateTypeCode().getCode());
        }
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
        if (cDate.before(sDate)) {
            throw new PAException("Primary completion date must be >= start date.");
        }
    }

    private void setDefaultValues(StudyProtocol sp , StudyProtocolDTO spDTO , Session session) {
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setStatusDate(new Timestamp((new Date()).getTime()));

        if (sp.getIdentifier() == null) {
            // this is a first submission
            sp.setIdentifier(generateNciIdentifier(session));
            sp.setSubmissionNumber(Integer.valueOf(1));
        } else {
            // this is an amendment, so generate only submission number and maintain the nci identifier
            sp.setSubmissionNumber(generateSubmissionNumber(sp.getIdentifier(), session));
        }
        if (ejbContext != null) {
            sp.setUserLastCreated(ejbContext.getCallerPrincipal().getName());
        }
        sp.setDateLastCreated(new Timestamp((new Date()).getTime()));
        if (spDTO.getUserLastCreated() != null) {
            sp.setUserLastCreated(spDTO.getUserLastCreated().getValue());
        }
    }

    private Integer generateSubmissionNumber(String identifier , Session session) {
        String query = "select max(sp.submissionNumber) from StudyProtocol sp where "
            + "sp.identifier = '" + identifier + "' ";
        Integer maxValue = (Integer) session.createQuery(query).list().get(0);
        return (maxValue == null ? 1 : maxValue + 1);


    }
}
