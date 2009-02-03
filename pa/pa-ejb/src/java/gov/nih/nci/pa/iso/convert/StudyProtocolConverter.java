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


import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;


/**
 * Convert StudyProtocol domain to DTO.
 *
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength"  })
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
    */
   public static StudyProtocol convertFromDTOToDomain(StudyProtocolDTO studyProtocolDTO) {
       return convertFromDTOToDomain(studyProtocolDTO , new StudyProtocol());
   }
    

    /**
     * 
     * @param studyProtocol sp 
     * @param studyProtocolDTO spDTO
     * @return StudyProtocolDTO sp
     */
    public static StudyProtocolDTO convertFromDomainToDTO(
            StudyProtocol studyProtocol , StudyProtocolDTO studyProtocolDTO) {
        studyProtocolDTO.setAcronym(StConverter.convertToSt(studyProtocol.getAcronym()));
        studyProtocolDTO.setAccrualReportingMethodCode(
                CdConverter.convertToCd(studyProtocol.getAccrualReportingMethodCode()));
        studyProtocolDTO.setAssignedIdentifier(IiConverter.convertToIi(studyProtocol.getIdentifier()));
        studyProtocolDTO.setDataMonitoringCommitteeAppointedIndicator(
                BlConverter.convertToBl(studyProtocol.getDataMonitoringCommitteeAppointedIndicator()));
        studyProtocolDTO.setDelayedpostingIndicator(
                BlConverter.convertToBl(studyProtocol.getDelayedpostingIndicator()));

        studyProtocolDTO.setExpandedAccessIndicator(
                BlConverter.convertToBl(studyProtocol.getExpandedAccessIndicator()));
        studyProtocolDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(studyProtocol.getFdaRegulatedIndicator()));
        studyProtocolDTO.setReviewBoardApprovalRequiredIndicator(
                BlConverter.convertToBl(studyProtocol.getReviewBoardApprovalRequiredIndicator()));
        studyProtocolDTO.setOfficialTitle(StConverter.convertToSt(studyProtocol.getOfficialTitle()));
        studyProtocolDTO.setMaximumTargetAccrualNumber(
                IntConverter.convertToInt(studyProtocol.getMaximumTargetAccrualNumber()));
        studyProtocolDTO.setIdentifier(IiConverter.converToStudyProtocolIi(studyProtocol.getId()));
        studyProtocolDTO.setPhaseCode(CdConverter.convertToCd(studyProtocol.getPhaseCode()));
        studyProtocolDTO.setPhaseOtherText(StConverter.convertToSt(studyProtocol.getPhaseOtherText()));
        studyProtocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(studyProtocol.getPrimaryCompletionDate()));
        studyProtocolDTO.setPrimaryCompletionDateTypeCode(
                CdConverter.convertToCd(studyProtocol.getPrimaryCompletionDateTypeCode()));
        studyProtocolDTO.setPrimaryPurposeCode(CdConverter.convertToCd(studyProtocol.getPrimaryPurposeCode()));
        studyProtocolDTO.setPrimaryPurposeOtherText(
                StConverter.convertToSt(studyProtocol.getPrimaryPurposeOtherText()));
        studyProtocolDTO.setPublicDescription(
                StConverter.convertToSt(studyProtocol.getPublicDescription()));
        studyProtocolDTO.setPublicTitle(
                StConverter.convertToSt(studyProtocol.getPublicTitle()));
        studyProtocolDTO.setRecordVerificationDate(TsConverter.convertToTs(studyProtocol.getRecordVerificationDate()));
        studyProtocolDTO.setScientificDescription(StConverter.convertToSt(studyProtocol.getScientificDescription()));
        studyProtocolDTO.setSection801Indicator(BlConverter.convertToBl(studyProtocol.getSection801Indicator()));
        studyProtocolDTO.setStartDate(TsConverter.convertToTs(studyProtocol.getStartDate()));
        studyProtocolDTO.setStartDateTypeCode(CdConverter.convertToCd(studyProtocol.getStartDateTypeCode()));
        studyProtocolDTO.setKeywordText(StConverter.convertToSt(studyProtocol.getKeywordText()));
        studyProtocolDTO.setAcceptHealthyVolunteersIndicator(BlConverter.convertToBl(
                studyProtocol.getAcceptHealthyVolunteersIndicator()));
        if (studyProtocol instanceof ObservationalStudyProtocol) {
            studyProtocolDTO.setStudyProtocolType(StConverter.convertToSt("ObservationalStudyProtocol"));
        } else if (studyProtocol instanceof InterventionalStudyProtocol) {
            studyProtocolDTO.setStudyProtocolType(StConverter.convertToSt("InterventionalStudyProtocol"));
        } else {
            studyProtocolDTO.setStudyProtocolType(StConverter.convertToSt(studyProtocol.getClass().getName()));
        }
        
        return studyProtocolDTO;
    }

    /**
    *
    * @param studyProtocolDTO studyProtocolDTO
    * @param studyProtocol studyProtocol
    * @return StudyProtocol StudyProtocol
    */
   public static StudyProtocol convertFromDTOToDomain(StudyProtocolDTO studyProtocolDTO , 
           StudyProtocol studyProtocol) {

       studyProtocol.setId(IiConverter.convertToLong(studyProtocolDTO.getIdentifier()));
       studyProtocol.setAcronym(StConverter.convertToString(studyProtocolDTO.getAcronym()));
       if (studyProtocolDTO.getAssignedIdentifier() != null) {
           studyProtocol.setIdentifier(studyProtocolDTO.getAssignedIdentifier().getExtension());
       }
       if (studyProtocolDTO.getAccrualReportingMethodCode() != null) {
           studyProtocol.setAccrualReportingMethodCode(
                   AccrualReportingMethodCode.getByCode(studyProtocolDTO.getAccrualReportingMethodCode().getCode()));
       }
       if (studyProtocolDTO.getAssignedIdentifier() != null) {
           studyProtocol.setIdentifier(IiConverter.convertToString(studyProtocolDTO.getAssignedIdentifier()));
       }
       studyProtocol.setDataMonitoringCommitteeAppointedIndicator(
               BlConverter.covertToBoolean(studyProtocolDTO.getDataMonitoringCommitteeAppointedIndicator()));
       studyProtocol.setDelayedpostingIndicator(
               BlConverter.covertToBoolean(studyProtocolDTO.getDelayedpostingIndicator()));
       studyProtocol.setExpandedAccessIndicator(
               BlConverter.covertToBoolean(studyProtocolDTO.getExpandedAccessIndicator()));
       studyProtocol.setFdaRegulatedIndicator(
               BlConverter.covertToBoolean(studyProtocolDTO.getFdaRegulatedIndicator()));
       studyProtocol.setReviewBoardApprovalRequiredIndicator(
               BlConverter.covertToBoolean(studyProtocolDTO.getReviewBoardApprovalRequiredIndicator()));
       studyProtocol.setMaximumTargetAccrualNumber(
               IntConverter.convertToInteger(studyProtocolDTO.getMaximumTargetAccrualNumber()));
       studyProtocol.setOfficialTitle(StConverter.convertToString(studyProtocolDTO.getOfficialTitle()));
       if (studyProtocolDTO.getPhaseCode() != null) {
           studyProtocol.setPhaseCode(PhaseCode.getByCode(studyProtocolDTO.getPhaseCode().getCode()));
       }
       studyProtocol.setPhaseOtherText(StConverter.convertToString(studyProtocolDTO.getPhaseOtherText()));
       if (studyProtocolDTO.getPrimaryCompletionDate() != null) {
           studyProtocol.setPrimaryCompletionDate(
                   TsConverter.convertToTimestamp(studyProtocolDTO.getPrimaryCompletionDate()));
       }
       if (studyProtocolDTO.getPrimaryCompletionDateTypeCode() != null) {
           studyProtocol.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.getByCode(
                   studyProtocolDTO.getPrimaryCompletionDateTypeCode().getCode()));
       }
       if (studyProtocolDTO.getPrimaryPurposeCode() != null) {
           studyProtocol.setPrimaryPurposeCode(PrimaryPurposeCode.getByCode(
                   studyProtocolDTO.getPrimaryPurposeCode().getCode()));
       }
       studyProtocol.setPrimaryPurposeOtherText(StConverter.convertToString(
               studyProtocolDTO.getPrimaryPurposeOtherText()));
       studyProtocol.setPublicDescription(StConverter.convertToString(
               studyProtocolDTO.getPublicDescription()));
       studyProtocol.setPublicTitle(StConverter.convertToString(studyProtocolDTO.getPublicTitle()));
       if (studyProtocolDTO.getRecordVerificationDate() != null) {
           studyProtocol.setRecordVerificationDate(
                   TsConverter.convertToTimestamp(studyProtocolDTO.getRecordVerificationDate()));
       }
       
       studyProtocol.setSection801Indicator(BlConverter.covertToBoolean(studyProtocolDTO.getSection801Indicator()));
       
       studyProtocol.setScientificDescription(StConverter.convertToString(
               studyProtocolDTO.getScientificDescription()));
       if (studyProtocolDTO.getStartDate() != null) {
           studyProtocol.setStartDate(
                   TsConverter.convertToTimestamp(studyProtocolDTO.getStartDate()));
       }
       if (studyProtocolDTO.getStartDateTypeCode() != null) {
           studyProtocol.setStartDateTypeCode(ActualAnticipatedTypeCode.getByCode(
                   studyProtocolDTO.getStartDateTypeCode().getCode()));

       }
       if (studyProtocolDTO.getKeywordText() != null) {
       studyProtocol.setKeywordText(studyProtocolDTO.getKeywordText().getValue());
       }
       studyProtocol.setAcceptHealthyVolunteersIndicator(BlConverter.covertToBoolean(
               studyProtocolDTO.getAcceptHealthyVolunteersIndicator()));
       return studyProtocol;
   }
    
}
