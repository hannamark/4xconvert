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
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.convert.StudyMilestoneConverter;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.JNDIUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

/**
* @author Hugh Reinhart
* @since 1/15/2009
*/
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.TooManyMethods" })
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyMilestoneServiceBean
        extends AbstractStudyIsoService<StudyMilestoneDTO, StudyMilestone, StudyMilestoneConverter>
        implements StudyMilestoneServiceRemote, StudyMilestoneServicelocal {

    DocumentWorkflowStatusServiceRemote documentWorkflowStatusService = null;
    StudyProtocolServiceRemote studyProtocolService = null;

    @EJB
    StudyOnholdServiceRemote studyOnholdService;
    @EJB
    AbstractionCompletionServiceRemote abstractionCompletionService;
    @EJB
    MailManagerServiceLocal mailManagerService;

    /** For testing purposes only.  Set to false to bypass abstraction validations. */
    boolean validateAbstractions = true;

    /**
     * @param dto dto
     * @return created dto
     * @throws PAException exception
     */
    @Override
    public StudyMilestoneDTO create(StudyMilestoneDTO dto) throws PAException {
        StudyMilestoneDTO workDto = businessRules(dto);
        // Send TSR e-mail for the appropriate milestone
        sendTSREmail(workDto);
        StudyMilestoneDTO resultDto = super.create(workDto);
        createDocumentWorkflowStatuses(resultDto);
        updateRecordVerificationDates(resultDto);
        return resultDto;
    }

    /**
     * @param ii index of milestone
     * @throws PAException exception
     */
    @Override
    public void delete(Ii ii) throws PAException {
        throw new PAException("The delete() method in the StudyMilestoneService has been disabled.");
    }

    /**
     * @param dto dto
     * @return updated dto
     * @throws PAException exception
     */
    @Override
    public StudyMilestoneDTO update(StudyMilestoneDTO dto) throws PAException {
        throw new PAException("The update() method in the StudyMilestoneService has been disabled.");
    }

    private DocumentWorkflowStatusServiceRemote getDocumentWorkflowStatusService() {
        if (documentWorkflowStatusService == null) {
            documentWorkflowStatusService = (DocumentWorkflowStatusServiceRemote)
                    JNDIUtil.lookup("pa/DocumentWorkflowStatusServiceBean/remote");
        }
        return documentWorkflowStatusService;
    }

    private StudyProtocolServiceRemote getStudyProtocolService() {
        if (studyProtocolService == null) {
            studyProtocolService = (StudyProtocolServiceRemote) JNDIUtil.lookup("pa/StudyProtocolServiceBean/remote");
        }
        return studyProtocolService;
    }

    private DocumentWorkflowStatusCode getCurrentDocumentWorkflowStatus(Ii studyProtocolIi) throws PAException {
        DocumentWorkflowStatusDTO dw = getDocumentWorkflowStatusService().getCurrentByStudyProtocol(studyProtocolIi);
        return  (dw == null) ? null
                : DocumentWorkflowStatusCode.getByCode(CdConverter.convertCdToString(dw.getStatusCode()));
    }

    @SuppressWarnings({ "PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
    private StudyMilestoneDTO businessRules(StudyMilestoneDTO dto) throws PAException {
        Timestamp newDate = TsConverter.convertToTimestamp(dto.getMilestoneDate());
        MilestoneCode newCode = MilestoneCode.getByCode(CdConverter.convertCdToString(dto.getMilestoneCode()));

        List<StudyMilestoneDTO> existingDtoList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
        Timestamp lastDate = null;
        if (!existingDtoList.isEmpty()) {
            StudyMilestoneDTO last = existingDtoList.get(existingDtoList.size() - 1);
            lastDate = TsConverter.convertToTimestamp(last.getMilestoneDate());
        }

        // required data rules
        if (PAUtil.isCdNull(dto.getMilestoneCode())) {
            throw new PAException("Milestone code is required.");
        }
        if (newDate == null) {
            throw new PAException("Milestone date is required.");
        }

        // onhold rules
        if (!newCode.isAllowedIfOnhold()
                && BlConverter.covertToBool(studyOnholdService.isOnhold(dto.getStudyProtocolIdentifier()))) {
            throw new PAException("The milestone '" + newCode.getCode()
                    + "' cannot be recorded if there is an active on-hold record.");
        }

        // date rules
        if (newDate.after(new Timestamp(new Date().getTime()))) {
            throw new PAException("Milestone dates may not be in the future.");
        }
        if ((lastDate != null) && lastDate.after(newDate)) {
            throw new PAException("Milestone's must not predate existing milestones.  The prior milestone date is "
                    + PAUtil.normalizeDateString(lastDate.toString()) + ".");
        }

        // transition rules
        if (newCode.getPrerequisite() != null) {
            boolean prerequisiteFound = false;
            for (StudyMilestoneDTO edto : existingDtoList) {
                MilestoneCode cd = MilestoneCode.getByCode(CdConverter.convertCdToString(edto.getMilestoneCode()));
                if (newCode.getPrerequisite().getCode().equals(cd.getCode())) {
                    prerequisiteFound = true;
                    continue;
                }
            }
            if (!prerequisiteFound) {
                throw new PAException("'" + newCode.getPrerequisite().getCode() + "' is a prerequisite to '"
                        + newCode.getCode() + "'.");
            }
        }

        // uniqueness rules
        if (newCode.isUnique()) {
            for (StudyMilestoneDTO edto : existingDtoList) {
                if (newCode.getCode().equals(edto.getMilestoneCode().getCode())) {
                    throw new PAException("The milestone '" + newCode.getCode() + "' must be unique.  It was "
                            + "previously recorded on " + PAUtil.normalizeDateString(
                                    TsConverter.convertToTimestamp(edto.getMilestoneDate()).toString()) + ".");
                }
            }
        }
        
        newValidations(newCode, existingDtoList);
        
        // document work flow status rules
        DocumentWorkflowStatusCode dwStatus = getCurrentDocumentWorkflowStatus(dto.getStudyProtocolIdentifier());
        if ((!newCode.equals(MilestoneCode.SUBMISSION_RECEIVED)
                && !newCode.equals(MilestoneCode.SUBMISSION_ACCEPTED)
                && !newCode.equals(MilestoneCode.SUBMISSION_REJECTED))
                && !newCode.isValidDwfStatus(dwStatus)) {
            StringBuffer errMsg = new StringBuffer("The processing status must be ");
            int iSize = newCode.getValidDwfStatuses().size();
            for (int x = 0; x < iSize; x++) {
                errMsg.append("'" + newCode.getValidDwfStatuses().get(x).getCode() + "'");
                if ((iSize == 2) && (x == 0)) {
                    errMsg.append(" or ");
                }
                if ((iSize > 2) && (x < iSize - 2)) {
                    errMsg.append(", ");
                }
                if ((iSize > 2) && (x == iSize - 2)) {
                    errMsg.append(", or ");
                }
            }
            errMsg.append(" when entering the milestone '" + newCode.getCode() + "'.  The current processing "
                    + "status is " + ((dwStatus == null) ? "null." : "'" + dwStatus.getCode() + "'."));
            throw new PAException(errMsg.toString());
        }
       
        
        // validate abstraction
        if (validateAbstractions && newCode.isValidationTrigger()) {
            if (abstractionCompletionService == null) {
                throw new PAException("Error injecting reference to AbstractionCompletionService.");
            }
            List<AbstractionCompletionDTO> errorList =
                abstractionCompletionService.validateAbstractionCompletion(dto.getStudyProtocolIdentifier());
            if (!errorList.isEmpty() && hasAnyAbstractionErrors(errorList)) {
                throw new PAException("The milestone '" + newCode.getCode() + "' can only be recorded if the "
                        + "abstraction is valid.  There is a problem with the current abstraction.  Select "
                        + "'Abstraction Validation' under 'Completion' menu to view details.");
            }
             
        }
        return dto;
    }
    
    @SuppressWarnings({ "PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })  
    private void newValidations(MilestoneCode newCode,  List<StudyMilestoneDTO> existingDtoList) throws PAException
    {
        //check if the administrative and sceintific processing is completed.
        if (newCode.equals(MilestoneCode.READY_FOR_QC)) {
           List<String> mileStones = getExistingMilestones(existingDtoList);  
            if (!(mileStones.contains(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE.getCode()) 
                     && mileStones.contains(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE.getCode()))) {
                 throw new PAException("Ready for QC can only be recorded after " 
                         + "Administrative and Scientific processing are completed");
             }
            if (!hasBeenPaired(mileStones, MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE.getCode(),
                    MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE.getCode())) {
                throw new PAException("Scientific Processing Start Date must be followed by " 
                          + " Scientific Processing Completion Date");
            }
            if (!hasBeenPaired(mileStones, MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE.getCode(),
                    MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE.getCode())) {
                throw new PAException("Administartive Processing Start Date must be followed by " 
                        + " Administrative Processing Completion Date");
            }
         } 
       
        //if Admin processing already started ::
        //check if the administrative processing is completed before the start of Sceintific processing 
        if (newCode.equals(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE)) {
            List<String> mileStones = getExistingMilestones(existingDtoList); 
             if (mileStones.contains(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE.getCode()) 
                 && !mileStones.contains(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE.getCode())) {
                 throw new PAException("Scientific Processing Start Date cannot be recorded" 
                         + " if Administrative Processing started but was not completed");
             }
             if (!hasBeenPaired(mileStones, MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE.getCode(),
                     MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE.getCode())) {
                 throw new PAException("Scientific Processing Start Date must be followed by " 
                           + " Scientific Processing Completion Date");
             }
             if (!hasBeenPaired(mileStones, MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE.getCode(),
                     MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE.getCode())) {
                 throw new PAException("Administartive Processing Start Date must be followed by " 
                         + " Administrative Processing Completion Date");
             }
             
         } 
        //if Scientific processing already started ::
        //check if the sceintific processing is completed before the start of admin processing 
        if (newCode.equals(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE)) {
           List<String> mileStones = getExistingMilestones(existingDtoList); 
             if (mileStones.contains(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE.getCode()) 
                     && !mileStones.contains(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE.getCode())) {
                 throw new PAException("Administrative Processing Start Date cannot be recorded" 
                         + " if Scientific Processing started but was not completed");
             }
             
             if (!hasBeenPaired(mileStones, MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE.getCode(),
                     MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE.getCode())) {
                 throw new PAException("Administartive Processing Start Date must be followed by " 
                         + " Administrative Processing Completion Date");
             }
             if (!hasBeenPaired(mileStones, MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE.getCode(),
                     MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE.getCode())) {
                 throw new PAException("Scientific Processing Start Date must be followed by " 
                           + " Scientific Processing Completion Date");
             }
           } 
        
        if (newCode.equals(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE)) {
            List<String> mileStones = getExistingMilestones(existingDtoList); 
              if (!canBePaired(mileStones, MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE.getCode(),
                      MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE.getCode())) {
                  throw new PAException("Administartive Processing Completion Date must be preceded by " 
                          + " Administrative Processing Start Date");
              }
            } 
        
        if (newCode.equals(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE)) {
            List<String> mileStones = getExistingMilestones(existingDtoList); 
            if (!canBePaired(mileStones, MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE.getCode(),
                    MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE.getCode())) {
                throw new PAException("Scientific Processing Completion Date must be preceded by " 
                          + " Scientific Processing Start Date");
            }
            } 
        
       }
    
    private boolean hasAnyAbstractionErrors(List<AbstractionCompletionDTO> errorList) {
        boolean errorExist = false;
         for (AbstractionCompletionDTO  absDto : errorList) {
            if (absDto.getErrorType().equalsIgnoreCase("error")) {
                errorExist = true;
                break;
            }
         }
        return errorExist;
    }
    
    private List<String> getExistingMilestones(List<StudyMilestoneDTO> existingDTOs)
    {
     List<String> existingMilestones = null;
     if (existingDTOs != null) {
         existingMilestones = new ArrayList<String>();
         for (StudyMilestoneDTO edto : existingDTOs) {
             existingMilestones.add(edto.getMilestoneCode().getCode());
         }
     }
      return existingMilestones;
    }
    
    private boolean canBePaired(List<String> mileStones, String mileStone1, String mileStone2) {
       int mileStone1Count = 0;
       int mileStone2Count = 0;
       for (String mileStone : mileStones) {
           if (mileStone.equals(mileStone1)) {
               mileStone1Count++;
           }
           if (mileStone.equals(mileStone2)) {
               mileStone2Count++;
           }
       }
       if (mileStone1Count == mileStone2Count) {
           return false;
       } else if (mileStone1Count == 1 && mileStone2Count == 0) {
           return true;
       } else if (mileStone1Count % 2 == 0 && mileStone2Count % 2 == 0) {
           return false;
       } else if ((mileStone1Count % 2 == 0 && mileStone2Count % 2 == 1) && (mileStone2Count > mileStone1Count)) {
           return false;
       } else if (mileStone1Count % 2 != 0) {
           return true;
       } 
       return true;
    }
    
    private boolean hasBeenPaired(List<String> mileStones, String mileStone1, String mileStone2) {
        int mileStone1Count = 0;
        int mileStone2Count = 0;
        for (String mileStone : mileStones) {
            if (mileStone.equals(mileStone1)) {
                mileStone1Count++;
            }
            if (mileStone.equals(mileStone2)) {
                mileStone2Count++;
            }
        }
        if (mileStone1Count == mileStone2Count) {
            return true;
        } else if (mileStone1Count == 1 && mileStone2Count == 0) {
            return false;
        } else if (mileStone1Count % 2 == 0 && mileStone2Count % 2 == 0) {
            return true;
        } else if (mileStone1Count % 2 != 0) {
            return false;
        } 
        return false;
     }
       
    @SuppressWarnings({ "PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
    private void createDocumentWorkflowStatuses(StudyMilestoneDTO dto) throws PAException {
        MilestoneCode newCode = MilestoneCode.getByCode(CdConverter.convertCdToString(dto.getMilestoneCode()));
        DocumentWorkflowStatusCode dwStatus = getCurrentDocumentWorkflowStatus(dto.getStudyProtocolIdentifier());
        
        if (newCode.equals(MilestoneCode.SUBMISSION_RECEIVED)) {
                  
                    createDocumentWorkflowStatus(DocumentWorkflowStatusCode.SUBMITTED , dto);
        }
        if (newCode.equals(MilestoneCode.SUBMISSION_ACCEPTED) 
                && canTransition(dwStatus, DocumentWorkflowStatusCode.ACCEPTED)) {
                    
                    createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ACCEPTED , dto);
        }
        if (newCode.equals(MilestoneCode.SUBMISSION_REJECTED)
                && canTransition(dwStatus, DocumentWorkflowStatusCode.REJECTED)) {
                    
                    createDocumentWorkflowStatus(DocumentWorkflowStatusCode.REJECTED , dto);
        }
        if (newCode.equals(MilestoneCode.QC_COMPLETE)
                     && (dwStatus != null) 
                     && DocumentWorkflowStatusCode.ACCEPTED.equals(dwStatus)
                     && canTransition(dwStatus, DocumentWorkflowStatusCode.ABSTRACTED)) {
                
                      createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ABSTRACTED , dto);
            }
        
        if (newCode.equals(MilestoneCode.INITIAL_ABSTRACTION_VERIFY) 
                        && (dwStatus != null) 
                        && (DocumentWorkflowStatusCode.ABSTRACTED.equals(dwStatus)
                            || DocumentWorkflowStatusCode.VERIFICATION_PENDING.equals(dwStatus))) {
            
                if (milestoneExists(MilestoneCode.TRIAL_SUMMARY_FEEDBACK, dto)) {
                    if (canTransition(dwStatus, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE)) {
                        createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE , dto);
                    }
                } else {
                    if (canTransition(dwStatus, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE)) {
                        createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE , dto);
                    }   
                }
            }
        
        if (newCode.equals(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION)
                    && (dwStatus != null) 
                    && DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.equals(dwStatus)
                    && canTransition(dwStatus, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE)
                    && milestoneExists(MilestoneCode.TRIAL_SUMMARY_FEEDBACK, dto)) {
                
                    createDocumentWorkflowStatus(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE, dto);
            }
        
        
        if (newCode.equals(MilestoneCode.TRIAL_SUMMARY_SENT) 
                    && (dwStatus != null) 
                    && DocumentWorkflowStatusCode.ABSTRACTED.equals(dwStatus)
                    && canTransition(dwStatus, DocumentWorkflowStatusCode.VERIFICATION_PENDING)) {
                
                 createDocumentWorkflowStatus(DocumentWorkflowStatusCode.VERIFICATION_PENDING, dto);
            }
        
    }

    private void createDocumentWorkflowStatus(
            DocumentWorkflowStatusCode dwf, StudyMilestoneDTO dto) throws PAException {
        DocumentWorkflowStatusDTO dwfDto = new DocumentWorkflowStatusDTO();
        dwfDto.setStatusCode(CdConverter.convertToCd(dwf));
        dwfDto.setStatusDateRange(IvlConverter.convertTs().convertToIvl(
                           TsConverter.convertToTimestamp(dto.getMilestoneDate()), null));
        dwfDto.setStudyProtocolIdentifier(dto.getStudyProtocolIdentifier());
        if (dto.getCommentText() != null) {
            dwfDto.setCommentText(dto.getCommentText()); 
        }
        getDocumentWorkflowStatusService().create(dwfDto);
    }

    private void updateRecordVerificationDates(StudyMilestoneDTO dto) throws PAException {
        MilestoneCode newCode = MilestoneCode.getByCode(CdConverter.convertCdToString(dto.getMilestoneCode()));
        if (newCode.equals(MilestoneCode.QC_COMPLETE)) {
            DocumentWorkflowStatusCode dwStatus = getCurrentDocumentWorkflowStatus(dto.getStudyProtocolIdentifier());
            if ((dwStatus != null) && DocumentWorkflowStatusCode.ACCEPTED.equals(dwStatus)) {
                updateRecordVerificationDate(dto);
            }
        }
        if (newCode.equals(MilestoneCode.INITIAL_ABSTRACTION_VERIFY)
                || newCode.equals(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION)) {
            updateRecordVerificationDate(dto);
        }
    }

    private void updateRecordVerificationDate(StudyMilestoneDTO dto) throws PAException {
        StudyProtocolDTO sp = getStudyProtocolService().getStudyProtocol(dto.getStudyProtocolIdentifier());
        sp.setRecordVerificationDate(dto.getMilestoneDate());
        studyProtocolService.updateStudyProtocol(sp);
    }

    boolean milestoneExists(MilestoneCode milestoneCode, StudyMilestoneDTO dto) throws PAException {
        List<StudyMilestoneDTO> smList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
        for (StudyMilestoneDTO sm : smList) {
            MilestoneCode tempCode = MilestoneCode.getByCode(CdConverter.convertCdToString(sm.getMilestoneCode()));
            if (tempCode.equals(milestoneCode)) {
                return true;
            }
        }
        return false;
    }

    private void sendTSREmail(StudyMilestoneDTO workDto) throws PAException {
        MilestoneCode milestoneCode = MilestoneCode.getByCode(
                            CdConverter.convertCdToString(workDto.getMilestoneCode()));
        if ((MilestoneCode.TRIAL_SUMMARY_SENT.equals(milestoneCode))) {
            try {
                mailManagerService.sendTSREmail(workDto.getStudyProtocolIdentifier());
            } catch (PAException e) {
                throw new PAException(workDto.getMilestoneCode().getCode() + "' could not "
                        + "be recorded as sending the TSR report to the submitter  failed.", e);
            }
        }
    }
    
    private boolean canTransition(DocumentWorkflowStatusCode dwStatus, DocumentWorkflowStatusCode newCode)
     throws PAException {
        boolean canTransition = false;
        if (dwStatus.canTransitionTo(newCode)) {
            canTransition = true;
        } else {
            throw new PAException("Illegal DocumentWorkflow status transition from '" + dwStatus.getCode()
                    + "' to '" + newCode.getCode() + "'.  ");
        }
        return canTransition;
    }
}
