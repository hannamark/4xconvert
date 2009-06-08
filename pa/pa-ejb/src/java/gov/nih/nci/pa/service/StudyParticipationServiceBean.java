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

import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.convert.StudyParticipationConverter;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 09/23/2008
 */
@Stateless
@SuppressWarnings("PMD.CyclomaticComplexity")
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyParticipationServiceBean
        extends AbstractRoleIsoService<StudyParticipationDTO, StudyParticipation, StudyParticipationConverter>
        implements StudyParticipationServiceRemote, StudyParticipationServiceLocal {

    /**
     * @param dto StudyParticipationDTO
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    @Override
    public StudyParticipationDTO create(StudyParticipationDTO dto) throws PAException {
        StudyParticipationDTO createDto = businessRules(dto);
        createDto.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        StudyParticipationDTO resultDto = super.create(createDto);
        enforceOnlyOneOversightCommittee(resultDto);
        return resultDto;
    }

    /**
     * @param dto StudyParticipationDTO
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    @Override
    public StudyParticipationDTO update(StudyParticipationDTO dto)
            throws PAException {
        StudyParticipationDTO updateDto = businessRules(dto);
        StudyParticipationDTO resultDto = super.update(updateDto);
        enforceOnlyOneOversightCommittee(resultDto);
        return resultDto;
    }

    
    @SuppressWarnings("PMD.NPathComplexity")
    private StudyParticipationDTO businessRules(StudyParticipationDTO dto) throws PAException {
        if (PAUtil.isIiNull(dto.getHealthcareFacilityIi()) && PAUtil.isIiNull(dto.getResearchOrganizationIi()) 
                && PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
            throw new PAException("Either healthcare facility or research organization or Oversight committee" 
                    + " must be set.  ");
        }
        if (!PAUtil.isIiNull(dto.getHealthcareFacilityIi()) && !PAUtil.isIiNull(dto.getResearchOrganizationIi())) {
            throw new PAException("Healthcare facility , research organization cannot both be set.  ");
        }
        if (!PAUtil.isIiNull(dto.getHealthcareFacilityIi()) && !PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
            throw new PAException("Healthcare facility and over sight " 
                    + "committee cannot both be set.  ");
        }
        if (!PAUtil.isIiNull(dto.getResearchOrganizationIi()) && !PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
            throw new PAException("research organization and over sight " 
                    + "committee cannot both be set.  ");
        }
        ReviewBoardApprovalStatusCode code = ReviewBoardApprovalStatusCode.getByCode(
                CdConverter.convertCdToString(dto.getReviewBoardApprovalStatusCode()));
        if (code != null) {
            String approvalNumber = StConverter.convertToString(dto.getReviewBoardApprovalNumber());
            if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode().toString().equals(code.getCode().toString())
                    && ((approvalNumber == null) || (approvalNumber.length() == 0))) {
                throw new PAException("Review board approval number must be set for status '"
                        + ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getDisplayName() + "'.  ");
            }
            if (ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode().toString().equals(code.getCode().toString())
             || ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode().toString().equals(code.getCode().toString())
             || ReviewBoardApprovalStatusCode.SUBMITTED_DENIED.getCode().toString()
                 .equals(code.getCode().toString()) && ((approvalNumber == null) || (approvalNumber.length() == 0))) {
                dto.setReviewBoardApprovalNumber(StConverter.convertToSt(null));
            }
            if (PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
                throw new PAException("Oversight committee (board) must be set when review board approval status is '"
                        + ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getDisplayName() + "' or '"
                        + ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getDisplayName() + "'.  ");
            }
        } else {
            dto.setOversightCommitteeIi(null);
            dto.setReviewBoardApprovalDate(null);
            dto.setReviewBoardApprovalNumber(null);
        }
        enforceNoDuplicate(dto);
        enforceNoDuplicateTrial(dto);
        return dto;
    }

    private Long getOrganizationId(StudyParticipationDTO dto) {
        if (PAUtil.isIiNull(dto.getHealthcareFacilityIi())) {
            return (PAUtil.isIiNull(dto.getResearchOrganizationIi())
                    ? IiConverter.convertToLong(dto.getOversightCommitteeIi())
                    : IiConverter.convertToLong(dto.getResearchOrganizationIi()));
        } else {
            return (IiConverter.convertToLong(dto.getHealthcareFacilityIi()));
        }
    }

    private String getFunctionalCode(StudyParticipationDTO dto) {
        return (PAUtil.isCdNull(dto.getFunctionalCode())) ? "" : CdConverter.convertCdToString(dto.getFunctionalCode());
    }

    private void enforceNoDuplicate(StudyParticipationDTO dto) throws PAException {
        Long newOrgId = getOrganizationId(dto);
        String newFunction = getFunctionalCode(dto);
        List<StudyParticipationDTO> spList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
        for (StudyParticipationDTO sp : spList) {
            boolean sameParticipation = IiConverter.convertToLong(sp.getIdentifier())
                    .equals(IiConverter.convertToLong(dto.getIdentifier()));
            boolean sameOrg = newOrgId.equals(getOrganizationId(sp));
            boolean sameFunction = newFunction.equals(getFunctionalCode(sp));
            if (!sameParticipation && sameOrg && sameFunction) {
                throw new PADuplicateException("This organization has already been entered as a '"
                        + newFunction + "' for this study.");
            }
        }
        
        
    }

    private void enforceOnlyOneOversightCommittee(StudyParticipationDTO dto) throws PAException {
        if (!PAUtil.isCdNull(dto.getReviewBoardApprovalStatusCode())) {
            List<StudyParticipationDTO> spList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
            for (StudyParticipationDTO sp : spList) {
                if (!IiConverter.convertToLong(dto.getIdentifier()).
                        equals(IiConverter.convertToLong(sp.getIdentifier()))
                        && !PAUtil.isCdNull(sp.getReviewBoardApprovalStatusCode())) {
                    sp.setReviewBoardApprovalStatusCode(null);
                    update(sp);
                }
            }
        }
    }
    /**
     * 
     * @param dto dto
     * @throws PAException e
     */
    private void enforceNoDuplicateTrial(StudyParticipationDTO dto) throws PAException {
        Session session = null;
        List<StudyParticipation> queryList = new ArrayList<StudyParticipation>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            // step 1: form the hql
            String hql = " select spart "
                       + " from StudyParticipation spart "
                       + " join spart.researchOrganization as ro "
                       + " join spart.studyProtocol as sp "
                       + " join sp.documentWorkflowStatuses as dws  "
                       + " where spart.localStudyProtocolIdentifier = :localStudyProtocolIdentifier "
                       + " and spart.functionalCode = '"
                       +   StudyParticipationFunctionalCode.LEAD_ORGANIZATION  + "'"
                       + " and dws.statusCode  <> '" + DocumentWorkflowStatusCode.REJECTED + "'"
                       + " and sp.statusCode ='" + ActStatusCode.ACTIVE + "'"
                       + " and ( dws.id in (select max(id) from DocumentWorkflowStatus as dws1 "
                       + "  where dws.studyProtocol = dws1.studyProtocol ) or dws.id is null ) "
                       + " and ro.id = :orgIdentifier";

            getLogger().info("query study_participation = " + hql + ".  ");
            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("localStudyProtocolIdentifier",
                    StConverter.convertToString(dto.getLocalStudyProtocolIdentifier()));
            query.setParameter("orgIdentifier",
                    IiConverter.convertToLong(dto.getResearchOrganizationIi()));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in getByLocalStudyProtocolIdentifier.  ", hbe);
        }
        for (StudyParticipation sp : queryList) {
            //When create DTO get Id will be null and if queryList is having value then its duplicate
            //When update check if the record is same if not then throw ex
            if ((dto.getIdentifier() == null) 
                    || (!String.valueOf(sp.getId()).equals(dto.getIdentifier().getExtension()))) {
                throw new PAException("Duplicate Trial Submission: A trial exists in the system with the same "
                        + "Lead Organization Trial Identifier for the selected Lead Organization");
            }
        }
        getLogger().info("Leaving enforceNoDuplicateTrial..");
    
        }

}
