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
package gov.nih.nci.pa.iso.convert;


import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.NonInterventionalStudyProtocol;
import gov.nih.nci.pa.domain.SecondaryPurpose;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.AmendmentReasonCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.LinkedHashSet;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * Convert StudyProtocol domain to DTO.
 *
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StudyProtocolConverter {

    /**
     *
     * @param studyProtocol study Protocol
     * @return studyProtocolDTO
     */
    public static StudyProtocolDTO convertFromDomainToDTO(StudyProtocol studyProtocol) {
        return convertFromDomainToDTO(studyProtocol, new StudyProtocolDTO());
    }

    /**
    *
    * @param studyProtocolDTO studyProtocolDTO
    * @return StudyProtocol StudyProtocol
    * @throws PAException  when error.
    */
   public static StudyProtocol convertFromDTOToDomain(StudyProtocolDTO studyProtocolDTO) throws PAException {
       return convertFromDTOToDomain(studyProtocolDTO , new StudyProtocol());
   }


    /**
     *
     * @param studyProtocol sp
     * @param studyProtocolDTO spDTO
     * @return StudyProtocolDTO sp
     */
    @SuppressWarnings({ "deprecation", "PMD.ExcessiveMethodLength" })
    public static StudyProtocolDTO convertFromDomainToDTO(StudyProtocol studyProtocol,
            StudyProtocolDTO studyProtocolDTO) {
        AbstractStudyProtocolConverter.convertFromDomainToDTO(studyProtocol, studyProtocolDTO);
        convertSecondaryPurposeToDto(studyProtocol, studyProtocolDTO);
        studyProtocolDTO.setAcronym(StConverter.convertToSt(studyProtocol.getAcronym()));
        studyProtocolDTO.setAccrualReportingMethodCode(
                CdConverter.convertToCd(studyProtocol.getAccrualReportingMethodCode()));
        //TODO - as part of PO-2434 this should be moved to the AbstractStudyProtocolConverter
        //once the AbstractStudyProtocolDTO owns the SecondaryIdentifiers.
        if (studyProtocol.getOtherIdentifiers() != null) {
          studyProtocolDTO.setSecondaryIdentifiers(
            DSetConverter.convertIiSetToDset(studyProtocol.getOtherIdentifiers()));
        }
        studyProtocolDTO.setSummary4AnatomicSites(AnatomicSiteConverter
                .convertToDSet(studyProtocol.getSummary4AnatomicSites()));
        studyProtocolDTO.setRecordOwners(RegistryUserConverter.convertToDSet(studyProtocol.getStudyOwners()));
        studyProtocolDTO.setExpandedAccessIndicator(
                BlConverter.convertToBl(studyProtocol.getExpandedAccessIndicator()));
        studyProtocolDTO.setReviewBoardApprovalRequiredIndicator(
                BlConverter.convertToBl(studyProtocol.getReviewBoardApprovalRequiredIndicator()));
        studyProtocolDTO.setTargetAccrualNumber(
                IvlConverter.convertInt().convertToIvl(studyProtocol.getMinimumTargetAccrualNumber(),
                        studyProtocol.getMaximumTargetAccrualNumber()));
        studyProtocolDTO.setIdentifier(IiConverter.convertToStudyProtocolIi(studyProtocol.getId()));
        studyProtocolDTO.setPublicDescription(
                StConverter.convertToSt(studyProtocol.getPublicDescription()));
        studyProtocolDTO.setPublicTitle(
                StConverter.convertToSt(studyProtocol.getPublicTitle()));
        studyProtocolDTO.setRecordVerificationDate(TsConverter.convertToTs(studyProtocol.getRecordVerificationDate()));
        studyProtocolDTO.setScientificDescription(StConverter.convertToSt(studyProtocol.getScientificDescription()));
        studyProtocolDTO.setKeywordText(StConverter.convertToSt(studyProtocol.getKeywordText()));
        studyProtocolDTO.setAcceptHealthyVolunteersIndicator(BlConverter.convertToBl(
                studyProtocol.getAcceptHealthyVolunteersIndicator()));
        studyProtocolDTO.setCtroOverride(BlConverter.convertToBl(
                studyProtocol.getCtroOverride()));        
        setStudyProtocolType(studyProtocol, studyProtocolDTO);
        studyProtocolDTO.setStatusCode(CdConverter.convertToCd(studyProtocol.getStatusCode()));
        studyProtocolDTO.setStatusDate(TsConverter.convertToTs(studyProtocol.getStatusDate()));
        studyProtocolDTO.setAmendmentNumber(
                StConverter.convertToSt(studyProtocol.getAmendmentNumber()));
        studyProtocolDTO.setAmendmentReasonCode(CdConverter.convertToCd(studyProtocol.getAmendmentReasonCode()));
        studyProtocolDTO.setAmendmentDate(TsConverter.convertToTs(studyProtocol.getAmendmentDate()));
        studyProtocolDTO.setSubmissionNumber(IntConverter.convertToInt(studyProtocol.getSubmissionNumber()));
        
        studyProtocolDTO.setComments(StConverter.convertToSt(studyProtocol
                .getComments()));
        studyProtocolDTO.setProcessingPriority(IntConverter
                .convertToInt(studyProtocol.getProcessingPriority()));
        if (studyProtocol.getAssignedUser() != null) {
            studyProtocolDTO.setAssignedUser(IiConverter
                    .convertToIi(studyProtocol.getAssignedUser().getUserId()));
        } else {
            studyProtocolDTO.setAssignedUser(IiConverter
                    .convertToIi((Long) null));
        }
        return studyProtocolDTO;
    }

    private static void setStudyProtocolType(StudyProtocol studyProtocol, StudyProtocolDTO studyProtocolDTO) {
        if (studyProtocol instanceof NonInterventionalStudyProtocol) {
            studyProtocolDTO.setStudyProtocolType(StConverter.convertToSt("NonInterventionalStudyProtocol"));
        } else if (studyProtocol instanceof InterventionalStudyProtocol) {
            studyProtocolDTO.setStudyProtocolType(StConverter.convertToSt("InterventionalStudyProtocol"));
        } else {
            studyProtocolDTO.setStudyProtocolType(StConverter.convertToSt(studyProtocol.getClass().getName()));
        }
    }

    /**
    *
    * @param studyProtocolDTO studyProtocolDTO
    * @param studyProtocol studyProtocol
    * @return StudyProtocol StudyProtocol
    * @throws PAException when error.
    */
   @SuppressWarnings({ "deprecation", "PMD.ExcessiveMethodLength" })
   public static StudyProtocol convertFromDTOToDomain(StudyProtocolDTO studyProtocolDTO ,
           StudyProtocol studyProtocol) throws PAException {

       AbstractStudyProtocolConverter.convertFromDTOToDomain(studyProtocolDTO, studyProtocol);
       convertSecondaryPurposeToDomain(studyProtocolDTO, studyProtocol);
       studyProtocol.setId(IiConverter.convertToLong(studyProtocolDTO.getIdentifier()));
       studyProtocol.setAcronym(StConverter.convertToString(studyProtocolDTO.getAcronym()));
       setSecondaryIdentifiers(studyProtocolDTO, studyProtocol);
       studyProtocol.setSummary4AnatomicSites(AnatomicSiteConverter
               .convertToSet(studyProtocolDTO.getSummary4AnatomicSites()));
       if (studyProtocolDTO.getAccrualReportingMethodCode() != null) {
           studyProtocol.setAccrualReportingMethodCode(
                   AccrualReportingMethodCode.getByCode(studyProtocolDTO.getAccrualReportingMethodCode().getCode()));
       }
       studyProtocol.setExpandedAccessIndicator(
               BlConverter.convertToBoolean(studyProtocolDTO.getExpandedAccessIndicator()));
       studyProtocol.setReviewBoardApprovalRequiredIndicator(
               BlConverter.convertToBoolean(studyProtocolDTO.getReviewBoardApprovalRequiredIndicator()));
       studyProtocol.setMaximumTargetAccrualNumber(null);
       if (studyProtocolDTO.getTargetAccrualNumber() != null) {
            studyProtocol.setMinimumTargetAccrualNumber(IvlConverter.convertInt().convertLow(
                    studyProtocolDTO.getTargetAccrualNumber()));
            studyProtocol.setMaximumTargetAccrualNumber(IvlConverter.convertInt().convertHigh(
                    studyProtocolDTO.getTargetAccrualNumber()));
       }
       studyProtocol.setPublicDescription(StConverter.convertToString(studyProtocolDTO.getPublicDescription()));
       studyProtocol.setPublicTitle(StConverter.convertToString(studyProtocolDTO.getPublicTitle()));
       if (studyProtocolDTO.getRecordVerificationDate() != null) {
           studyProtocol.setRecordVerificationDate(
                   TsConverter.convertToTimestamp(studyProtocolDTO.getRecordVerificationDate()));
       }

       studyProtocol.setScientificDescription(StConverter.convertToString(
               studyProtocolDTO.getScientificDescription()));
       if (studyProtocolDTO.getKeywordText() != null) {
            studyProtocol.setKeywordText(studyProtocolDTO.getKeywordText().getValue());
       }
       studyProtocol.setAcceptHealthyVolunteersIndicator(BlConverter.convertToBoolean(
               studyProtocolDTO.getAcceptHealthyVolunteersIndicator()));
       studyProtocol.setCtroOverride(BlConverter.convertToBoolean(
               studyProtocolDTO.getCtroOverride()));

       
       setStatusFields(studyProtocolDTO, studyProtocol);
       setAmendmentFields(studyProtocolDTO, studyProtocol);
       if (studyProtocolDTO.getSubmissionNumber() != null) {
           studyProtocol.setSubmissionNumber(
               IntConverter.convertToInteger(studyProtocolDTO.getSubmissionNumber()));
       }
       
        studyProtocol.setComments(StConverter.convertToString(studyProtocolDTO
                .getComments()));
        studyProtocol.setProcessingPriority(IntConverter
                .convertToInteger(studyProtocolDTO.getProcessingPriority()));
        if (!ISOUtil.isIiNull(studyProtocolDTO.getAssignedUser())) {
            User user = new User();
            user.setUserId(IiConverter.convertToLong(studyProtocolDTO
                    .getAssignedUser()));
            studyProtocol.setAssignedUser(user);
        } else {
            studyProtocol.setAssignedUser(null);
        }
       return studyProtocol;
   }

    private static void setSecondaryIdentifiers(StudyProtocolDTO studyProtocolDTO, StudyProtocol studyProtocol) {
        // TODO - as part of PO-2434 this should be moved to the AbstractStudyProtocolConverter
        // once the AbstractStudyProtocolDTO owns the SecondaryIdentifiers.
        studyProtocol.setOtherIdentifiers(DSetConverter.convertDsetToIiSet(studyProtocolDTO.getSecondaryIdentifiers()));
    }

    private static void setStatusFields(StudyProtocolDTO studyProtocolDTO, StudyProtocol studyProtocol) {
        if (studyProtocolDTO.getStatusCode() != null) {
            studyProtocol.setStatusCode(ActStatusCode.getByCode(studyProtocolDTO.getStatusCode().getCode()));
        }
        if (studyProtocolDTO.getStatusDate() != null) {
            studyProtocol.setStatusDate(TsConverter.convertToTimestamp(studyProtocolDTO.getStatusDate()));
        }
    }

    private static void setAmendmentFields(StudyProtocolDTO studyProtocolDTO, StudyProtocol studyProtocol) {
        if (studyProtocolDTO.getAmendmentNumber() != null) {
            studyProtocol.setAmendmentNumber(StConverter.convertToString(studyProtocolDTO.getAmendmentNumber()));
        }
        if (studyProtocolDTO.getAmendmentReasonCode() != null) {
            studyProtocol.setAmendmentReasonCode(AmendmentReasonCode.getByCode(studyProtocolDTO
                    .getAmendmentReasonCode().getCode()));
        } else {
            studyProtocol.setAmendmentReasonCode(null);
        }

        if (studyProtocolDTO.getAmendmentDate() != null) {
            studyProtocol.setAmendmentDate(TsConverter.convertToTimestamp(studyProtocolDTO.getAmendmentDate()));
        }
    }
    
    private static void convertSecondaryPurposeToDto(StudyProtocol bo,
            StudyProtocolDTO dto) {
        if (bo.getSecondaryPurposes() != null) {
            DSet<St> dset = new DSet<St>();
            dset.setItem(new LinkedHashSet<St>());
            for (SecondaryPurpose secPurpose : bo.getSecondaryPurposes()) {
                dset.getItem().add(
                        StConverter.convertToSt(secPurpose.getName()));
            }
            dto.setSecondaryPurposes(dset);
        }
        dto.setSecondaryPurposeOtherText(StConverter.convertToSt(bo.getSecondaryPurposeOtherText()));
    }
    
    private static void convertSecondaryPurposeToDomain(
            StudyProtocolDTO dto, StudyProtocol bo) {
        if (dto.getSecondaryPurposes() != null
                && CollectionUtils.isNotEmpty(dto.getSecondaryPurposes()
                        .getItem())) {
            bo.getSecondaryPurposes().clear();
            for (St st : dto.getSecondaryPurposes().getItem()) {
                bo.getSecondaryPurposes().add(
                        getSecondaryPurpose(StConverter
                                .convertToString(st)));
            }
        }
        bo.setSecondaryPurposeOtherText(StConverter.convertToString(dto.getSecondaryPurposeOtherText()));
    }
    
    /**
     * @param name
     *            name
     * @return SecondaryPurpose
     */
    private static SecondaryPurpose getSecondaryPurpose(String name) {
        Session session = PaHibernateUtil.getCurrentSession();
        SecondaryPurpose example = new SecondaryPurpose();
        example.setName(name);
        return (SecondaryPurpose) session
                .createCriteria(SecondaryPurpose.class)
                .add(Example.create(example)).uniqueResult();
    }
}
