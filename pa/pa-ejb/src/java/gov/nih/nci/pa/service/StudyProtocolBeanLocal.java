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

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.iso.convert.InterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.ObservationalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.search.StudyProtocolBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudyProtocolSortCriterion;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;

/**
 * @author Naveen Amiruddin
 * @since 11/03/2009
 */
@Stateless
@Interceptors({ HibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyProtocolBeanLocal extends AbstractBaseSearchBean<StudyProtocol> implements StudyProtocolServiceLocal {

    private static final Logger LOG  = Logger.getLogger(StudyProtocolBeanLocal.class);
    /**
     * The size of the counter portion of the NCI ID.
     */
    protected static final int NCI_ID_SIZE = 5;
    private static final String CREATE = "Create";
    private static final String UPDATE = "Update";
    @EJB
    private StudyIndldeServiceLocal studyIndldeService;
    private SessionContext ejbContext;
    private PAServiceUtils paServiceUtils = new PAServiceUtils();
    /**
     * Set the invocation context.
     * @param ctx EJB context
     */
    @Resource
    public void setSessionContext(SessionContext ctx) {
        this.ejbContext = ctx;
    }


    private StudyProtocolDTO getStudyProtocolById(Long id) throws PAException {

        Session session = HibernateUtil.getCurrentSession();
        StudyProtocol studyProtocol = (InterventionalStudyProtocol) session.get(InterventionalStudyProtocol.class,
                                                                                id);
        if (studyProtocol == null) {
            studyProtocol = (ObservationalStudyProtocol) session.get(ObservationalStudyProtocol.class,
                                                                     id);
        }
        if (studyProtocol == null) {
            throw new PAException("No matching study protocol for Ii.extension " + id);
        }
        return StudyProtocolConverter.convertFromDomainToDTO(studyProtocol);
    }

    /**
     *
     * @param studyProtocolDTO studyProtocolDTO
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    public StudyProtocolDTO updateStudyProtocol(StudyProtocolDTO studyProtocolDTO) throws PAException {
        // enforce business rules
        if (studyProtocolDTO == null) {
            throw new PAException(" studyProtocolDTO should not be null.");
        }

        enForceBusinessRules(studyProtocolDTO);

        StudyProtocolDTO  spDTO = null;
        Session session = HibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) session.load(StudyProtocol.class,
                Long.valueOf(studyProtocolDTO.getIdentifier().getExtension()));

        StudyProtocolConverter.convertFromDTOToDomain(studyProtocolDTO, sp);

        setDefaultValues(sp, spDTO, session, UPDATE);
        session.update(sp);
        spDTO =  StudyProtocolConverter.convertFromDomainToDTO(sp);
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
            throw new PAException("Ii should not be null");
        }
        Session session = HibernateUtil.getCurrentSession();
        InterventionalStudyProtocol isp = (InterventionalStudyProtocol) session.load(InterventionalStudyProtocol.class,
                    Long.valueOf(ii.getExtension()));
        return InterventionalStudyProtocolConverter.convertFromDomainToDTO(isp);
    }


    /**
     *
     * @param ispDTO studyProtocolDTO
     * @return InterventionalStudyProtocolDTO
     * @throws PAException PAException
     */
    public InterventionalStudyProtocolDTO updateInterventionalStudyProtocol(
            InterventionalStudyProtocolDTO ispDTO) throws PAException {
        // enforce business rules
        int totBlindCodes = 0;
        if (ispDTO == null) {
            throw new PAException("InterventionalstudyProtocolDTO should not be null");

        }
        enForceBusinessRules(ispDTO);
        if (ispDTO.getBlindedRoleCode() != null && ispDTO.getBlindedRoleCode().getItem() != null) {
            totBlindCodes = ispDTO.getBlindedRoleCode().getItem().size();
        }
        checkBlindingSchemaCode(ispDTO, totBlindCodes);
        InterventionalStudyProtocolDTO  ispRetDTO = null;
        Session session = HibernateUtil.getCurrentSession();
        InterventionalStudyProtocol isp = (InterventionalStudyProtocol)
        session.load(InterventionalStudyProtocol.class, Long.valueOf(ispDTO.getIdentifier().getExtension()));
        InterventionalStudyProtocol upd = InterventionalStudyProtocolConverter.convertFromDTOToDomain(ispDTO);
        setDefaultValues(upd , ispDTO , session , UPDATE);
        isp = upd;
        session.merge(isp);
        ispRetDTO =  InterventionalStudyProtocolConverter.convertFromDomainToDTO(isp);
        return ispRetDTO;

    }

    private void checkBlindingSchemaCode(InterventionalStudyProtocolDTO ispDTO, int totBlindCodes)
    throws PAException {
        if (ispDTO.getBlindingSchemaCode() != null) {
            if (BlindingSchemaCode.OPEN.getCode().equals(ispDTO.getBlindingSchemaCode().getCode())
                    && totBlindCodes > 0) {
                throw new PAException("Open Blinding Schema code cannot have any Blinded codes ");
            }
            if (BlindingSchemaCode.SINGLE_BLIND.getCode().equals(ispDTO.getBlindingSchemaCode().getCode())
                    && totBlindCodes > 1) {
               throw new PAException("Only one masking role must be specified for 'Single Blind' masking. ");
            }
            if (BlindingSchemaCode.SINGLE_BLIND.getCode().equals(ispDTO.getBlindingSchemaCode().getCode())
                           && totBlindCodes < 1) {
              throw new PAException("Single Blinding Schema code must have 1 Blinded code ");
            }
            if (BlindingSchemaCode.DOUBLE_BLIND.getCode().equals(ispDTO.getBlindingSchemaCode().getCode())
                    && totBlindCodes < 2) {
                throw new PAException("At least two masking roles must to be specified for ‘Double Blind’ masking. ");
            }

        }
    }

    /**
     * for creating a new ISP.
     * @param ispDTO  for isp
     * @return ii ii
     * @throws PAException exception
     */
    public Ii createInterventionalStudyProtocol(InterventionalStudyProtocolDTO ispDTO) throws PAException {
        if (ispDTO == null) {
            throw new PAException("studyProtocolDTO should not be null.");
        }
        if (ispDTO.getIdentifier() != null && ispDTO.getIdentifier().getExtension() != null) {
            throw new PAException("Extension should be null, but got  = " + ispDTO.getIdentifier().getExtension());

        }
        enForceBusinessRules(ispDTO);
        InterventionalStudyProtocol isp = InterventionalStudyProtocolConverter.convertFromDTOToDomain(ispDTO);
        Session session = HibernateUtil.getCurrentSession();
        setDefaultValues(isp , ispDTO , session , CREATE);
        session.save(isp);
        return IiConverter.convertToStudyProtocolIi(isp.getId());
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
            throw new PAException("Ii should not be null ");
        }
        Session  session = HibernateUtil.getCurrentSession();
        ObservationalStudyProtocol osp = (ObservationalStudyProtocol) session.load(ObservationalStudyProtocol.class,
                Long.valueOf(ii.getExtension()));
        return ObservationalStudyProtocolConverter.convertFromDomainToDTO(osp);
    }

    /**
     *
     * @param ospDTO ObservationalStudyProtocolDTO
     * @return ObservationalStudyProtocolDTO
     * @throws PAException PAException
     */
    public ObservationalStudyProtocolDTO updateObservationalStudyProtocol(ObservationalStudyProtocolDTO ospDTO)
        throws PAException {
        // enforce business rules
        if (ospDTO == null) {
            throw new PAException("studyProtocolDTO should not be null ");

        }
        //enForceBusinessRules(ospDTO);
        Session session = HibernateUtil.getCurrentSession();
        ObservationalStudyProtocol osp = (ObservationalStudyProtocol)
        session.load(ObservationalStudyProtocol.class, Long.valueOf(ospDTO.getIdentifier().getExtension()));
        ObservationalStudyProtocol upd = ObservationalStudyProtocolConverter.convertFromDTOToDomain(ospDTO);
        setDefaultValues(osp, ospDTO, session, UPDATE);
        osp = upd;
        session.merge(osp);
        return ObservationalStudyProtocolConverter.convertFromDomainToDTO(osp);
    }

    /**
     * for creating a new OSP.
     * @param ospDTO  for osp
     * @return ii ii
     * @throws PAException exception
     */
    public Ii createObservationalStudyProtocol(ObservationalStudyProtocolDTO ospDTO)
    throws PAException {
        if (ospDTO == null) {
            throw new PAException("studyProtocolDTO should not be null ");

        }
        if (ospDTO.getIdentifier() != null && ospDTO.getIdentifier().getExtension() != null) {
            throw new PAException("Extension should be null, but got  = " + ospDTO.getIdentifier().getExtension());

        }
        enForceBusinessRules(ospDTO);
        ObservationalStudyProtocol osp = ObservationalStudyProtocolConverter.
        convertFromDTOToDomain(ospDTO);
        Session session = HibernateUtil.getCurrentSession();
        setDefaultValues(osp, ospDTO, session , CREATE);
        session.save(osp);
        return IiConverter.convertToStudyProtocolIi(osp.getId());
    }

    /**
     * deletes protocol and all of its related classes.
     * @param ii ii of study Protocol
     * @throws PAException on any error
     */
    public void deleteStudyProtocol(Ii ii) throws PAException {
        throw new PAException("Method Not Yey Implemented ");
    }

    /**
     *
     * @param studyProtocolDTO study protocol Dto
     * @throws PAException error on any validation
     */
    public void validate(StudyProtocolDTO studyProtocolDTO) throws PAException {
        StringBuffer sb = new StringBuffer();
        sb.append(studyProtocolDTO == null ? "Study Protocol DTO cannot be null, " : "");
        sb.append(PAUtil.isStNull(studyProtocolDTO.getOfficialTitle()) ? "Official Title cannot be null, " : "");
    }

    /**
     * Generate a unique nci id.
     * @param session the session
     * @return string nci id.
     */
    protected String generateNciIdentifier(Session session) {
        Calendar today = Calendar.getInstance();
        int currentYear  = today.get(Calendar.YEAR);
        String query = "select nextval('nci_identifiers_seq')";
        StringBuffer nciIdentifier = new StringBuffer();
        nciIdentifier.append("NCI-");
        nciIdentifier.append(currentYear);
        nciIdentifier.append('-');

        Query queryObject = session.createSQLQuery(query);
        String maxValue = queryObject.uniqueResult().toString();
        String maxNumber = maxValue.substring(maxValue.lastIndexOf('-') + 1 , maxValue.length());
        String nextNumber = String.valueOf(Integer.parseInt(maxNumber) + 1);
        nciIdentifier.append(StringUtils.leftPad(nextNumber, NCI_ID_SIZE, "0"));

        return nciIdentifier.toString();
    }


    private void enForceBusinessRules(StudyProtocolDTO studyProtocolDTO) throws PAException {
        boolean dateRulesApply = false;

        ActStatusCode ascStatusCode = null;
        if (!PAUtil.isCdNull(studyProtocolDTO.getStatusCode())) {
            ascStatusCode  = ActStatusCode.getByCode(studyProtocolDTO.getStatusCode().getCode());
        }
        if (PAUtil.isIiNull(studyProtocolDTO.getIdentifier()) &&  ascStatusCode != null
                && ascStatusCode.equals(ActStatusCode.ACTIVE)) {
            dateRulesApply = true;
        }
        if (!PAUtil.isIiNull(studyProtocolDTO.getIdentifier())) {
            StudyProtocol oldBo = StudyProtocolConverter.convertFromDTOToDomain(
                    getStudyProtocolById(Long.valueOf(studyProtocolDTO.getIdentifier().getExtension())));
            boolean isProprietaryTrial = false;
            if (oldBo.getProprietaryTrialIndicator() != null && oldBo.getProprietaryTrialIndicator()) {
                isProprietaryTrial = true;
            }
            StudyProtocol newBo = StudyProtocolConverter.convertFromDTOToDomain(studyProtocolDTO);
            if (!isProprietaryTrial
                    && (!ObjectUtils.equals(oldBo.getPrimaryCompletionDate(), newBo.getPrimaryCompletionDate())
                    || !oldBo.getPrimaryCompletionDateTypeCode().equals(newBo.getPrimaryCompletionDateTypeCode())
                    || !oldBo.getStartDate().equals(newBo.getStartDate())
                    || !oldBo.getStartDateTypeCode().equals(newBo.getStartDateTypeCode()))) {
                dateRulesApply = true;
            }
        }
        if (dateRulesApply) {
            enForceDateRules(studyProtocolDTO);
        }
        if (isCorrelationRuleRequired(studyProtocolDTO)) {
            List<StudyIndldeDTO> list = getStudyIndldeService().getByStudyProtocol(studyProtocolDTO.getIdentifier());
            if (getPaServiceUtils().containsNonExemptInds(list)) {
                throw new PAException("Unable to set FDARegulatedIndicator to 'No', "
                        + " Please remove IND/IDEs and try again");
            }
        }

    }

    /**
     * @param studyProtocolDTO
     * @return
     */
    private boolean isCorrelationRuleRequired(StudyProtocolDTO studyProtocolDTO) {
        Boolean ctGovIndicator = BlConverter.convertToBoolean(studyProtocolDTO.getCtgovXmlRequiredIndicator());
        return BooleanUtils.isTrue(ctGovIndicator) && (studyProtocolDTO.getIdentifier() != null
                && studyProtocolDTO.getFdaRegulatedIndicator() != null)
                && (studyProtocolDTO.getFdaRegulatedIndicator().getValue() != null)
                && (!Boolean.valueOf(studyProtocolDTO.getFdaRegulatedIndicator().getValue()));
    }

    private void enForceDateRules(StudyProtocolDTO studyProtocolDTO) throws PAException {
        Timestamp sDate = TsConverter.convertToTimestamp(studyProtocolDTO.getStartDate());
        Timestamp cDate = TsConverter.convertToTimestamp(studyProtocolDTO.getPrimaryCompletionDate());
        boolean unknownPrimaryCompletionDate = studyProtocolDTO.getPrimaryCompletionDate() != null
            && studyProtocolDTO.getPrimaryCompletionDate().getNullFlavor() == NullFlavor.UNK;
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
        if (cDate == null && !unknownPrimaryCompletionDate) {
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
        if (!cCode.equals(ActualAnticipatedTypeCode.ANTICIPATED) && unknownPrimaryCompletionDate) {
            throw new PAException("Unknown primary completion dates must be marked as Anticipated. ");
        }
        if (cCode.equals(ActualAnticipatedTypeCode.ACTUAL) && !unknownPrimaryCompletionDate && now.before(cDate)) {
            throw new PAException("Actual primary completion dates cannot be in the future.  ");
        }
        if (sCode.equals(ActualAnticipatedTypeCode.ANTICIPATED)  && !unknownPrimaryCompletionDate && now.after(sDate)) {
            throw new PAException("Anticipated start dates must be in the future.  ");
        }
        if (cCode.equals(ActualAnticipatedTypeCode.ANTICIPATED) && !unknownPrimaryCompletionDate && now.after(cDate)) {
            throw new PAException("Anticipated primary completion dates must be in the future.  ");
        }
        if (!unknownPrimaryCompletionDate && cDate.before(sDate)) {
            throw new PAException("Primary completion date must be >= start date.");
        }
    }

    private void setDefaultValues(StudyProtocol sp , StudyProtocolDTO spDTO , Session session , String operation) {
        if (sp.getStatusCode() == null) {
            sp.setStatusCode(ActStatusCode.ACTIVE);
        }
        if (sp.getStatusDate() == null) {
            sp.setStatusDate(new Timestamp((new Date()).getTime()));
        }

        //check if the assigned identifier exists
        //if no - generate the nci identifier and set it in the sp.
        if (!PADomainUtils.checkAssignedIdentifier(sp)) {
          Ii spSecAssignedId = IiConverter.convertToAssignedIdentifierIi(generateNciIdentifier(session));
          if (sp.getOtherIdentifiers() != null) {
            sp.getOtherIdentifiers().add(spSecAssignedId);
          } else {
            Set<Ii> secondaryIds = new HashSet<Ii>();
            secondaryIds.add(spSecAssignedId);
            sp.setOtherIdentifiers(secondaryIds);
          }
        }
        if (CREATE.equals(operation)) {
            User user = null;
            try {
                user = spDTO.getUserLastCreated() != null
                        ? CSMUserService.getInstance().getCSMUser(spDTO.getUserLastCreated().getValue())
                        : CSMUserService.getInstance().lookupUser(ejbContext);
            } catch (PAException e) {
                LOG.info("Unable to set User for auditing", e);
            }
            sp.setUserLastCreated(user);
            sp.setDateLastCreated(new Timestamp((new Date()).getTime()));
        }

    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyProtocolDTO> search(StudyProtocolDTO dto, LimitOffset pagingParams) throws PAException,
            TooManyResultsException {
        if (dto == null) {
            throw new PAException("StudyProtocolDTO should not be null.");
        }

        StudyProtocol criteria = new StudyProtocol();
        criteria.setPhaseCode(PhaseCode.getByCode(CdConverter.convertCdToString(dto.getPhaseCode())));
        criteria.setOfficialTitle(StConverter.convertToString(dto.getOfficialTitle()));
        criteria.setPublicTitle(StConverter.convertToString(dto.getPublicTitle()));
        criteria.setStatusCode(ActStatusCode.getByCode(CdConverter.convertCdToString(dto.getStatusCode())));
        criteria.setOtherIdentifiers(DSetConverter.convertDsetToIiSet(dto.getSecondaryIdentifiers()));

        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        PageSortParams<StudyProtocol> params = new PageSortParams<StudyProtocol>(maxLimit, pagingParams.getOffset(),
                StudyProtocolSortCriterion.STUDY_PROTOCOL_ID, false);
        StudyProtocolBeanSearchCriteria crit = new StudyProtocolBeanSearchCriteria(criteria);
        List<StudyProtocol> results = search(crit, params);
        return convertFromDomainToDTO(results);
    }


    private List<StudyProtocolDTO> convertFromDomainToDTO(List<StudyProtocol> studyProtocolList) {
        List<StudyProtocolDTO> studyProtocolDTOList =  new ArrayList<StudyProtocolDTO>();
        for (StudyProtocol sp : studyProtocolList) {
            StudyProtocolDTO studyProtocolDTO = StudyProtocolConverter.convertFromDomainToDTO(sp);
            studyProtocolDTOList.add(studyProtocolDTO);
        }
        return studyProtocolDTOList;
    }

    /**
     * {@inheritDoc}
     */
    public void changeOwnership(StudyProtocolDTO studyProtocolDTO) throws PAException {
        if (studyProtocolDTO == null) {
            LOG.error(" studyProtocolDTO should not be null ");
            throw new PAException(" studyProtocolDTO should not be null ");

        }
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        StudyProtocol prevStudyProtocol = (StudyProtocol) session.load(StudyProtocol.class,
                Long.valueOf(studyProtocolDTO.getIdentifier().getExtension()));
        String newUserLastCreated = StConverter.convertToString(studyProtocolDTO.getUserLastCreated());
        User prevUserLastCreatedObj = prevStudyProtocol.getUserLastCreated();
        String prevUserLastCreated = prevUserLastCreatedObj != null ? prevUserLastCreatedObj.getLoginName() : null;
        if (StringUtils.isNotEmpty(newUserLastCreated) && StringUtils.isNotEmpty(prevUserLastCreated)
                && !prevUserLastCreated.equals(newUserLastCreated)) {
            session = HibernateUtil.getCurrentSession();
            String sql = "UPDATE STUDY_PROTOCOL SET USER_LAST_CREATED='" + newUserLastCreated
                + "' WHERE IDENTIFIER=" + prevStudyProtocol.getId();
            session.createSQLQuery(sql).executeUpdate();
            session.flush();
        }
        StudyProtocol newSp = (StudyProtocol) session.load(StudyProtocol.class,
                Long.valueOf(studyProtocolDTO.getIdentifier().getExtension()));

        StudyProtocolConverter.convertFromDTOToDomain(studyProtocolDTO, newSp);
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyProtocolDTO getStudyProtocol(Ii studyProtocolIi)
        throws PAException {
        StudyProtocolDTO studyProtocolDTO = null;
        if (PAUtil.isIiNull(studyProtocolIi)) {
            throw new PAException("Ii should not be null");
        }
        if (IiConverter.STUDY_PROTOCOL_ROOT.equals(studyProtocolIi.getRoot())
            && studyProtocolIi.getExtension().startsWith("NCI")) {
                StudyProtocolDTO spDTO = new StudyProtocolDTO();
                spDTO.setSecondaryIdentifiers(new DSet<Ii>());
                spDTO.getSecondaryIdentifiers().setItem(new HashSet<Ii>());
                spDTO.getSecondaryIdentifiers().getItem().add(studyProtocolIi);
                LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS , 0);
                List<StudyProtocolDTO> spList;
                try {
                    spList = search(spDTO, limit);
                } catch (TooManyResultsException e) {
                    throw new PAException("found too many trials with this identifier "
                            + studyProtocolIi.getExtension() + " when only 1 expected.", e);
                }
                if (spList.isEmpty() || spList.size() > 1) {
                    throw new PAException("could not find unique trial with this identifier "
                            + studyProtocolIi.getExtension());
                }
                studyProtocolDTO = spList.get(0);
        } else {
                studyProtocolDTO = getStudyProtocolById(Long.valueOf(studyProtocolIi.getExtension()));
        }

        return studyProtocolDTO;
    }

    /**
     * @param studyIndldeService the studyIndldeService to set
     */
    public void setStudyIndldeService(StudyIndldeServiceLocal studyIndldeService) {
        this.studyIndldeService = studyIndldeService;
    }

    /**
     * @return the studyIndldeService
     */
    public StudyIndldeServiceLocal getStudyIndldeService() {
        return studyIndldeService;
    }

    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

    /**
     * @return the paServiceUtils
     */
    public PAServiceUtils getPaServiceUtils() {
        return paServiceUtils;
    }
}
