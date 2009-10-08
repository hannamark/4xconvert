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

package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyMilestoneServiceRemote;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAConstants;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalHome;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;

/**
 * @author Anupama Sharma
 * @since 07/29/2009
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
@Interceptors({ HibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings({ "PMD.CyclomaticComplexity" })
@Local(StudyMilestoneTasksServiceLocal.class)
@LocalHome(StudyMilestoneTasksServiceLocalHome.class)
public class StudyMilestoneTasksServiceBean implements StudyMilestoneTasksServiceLocal {

    private static final Logger LOG = Logger.getLogger(StudyMilestoneTasksServiceBean.class);
    private static final int PAST_5_DAYS = 5;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    @EJB
    StudyMilestoneServiceRemote smRemote;
    
    /**
     * Perform task.
     * @throws PAException exception
     */
    @SuppressWarnings({ "PMD" })
    public void performTask() throws PAException {
     
    LOG.info("Entering Perform Task");    
    
    // create the criteria search object
    StudyMilestoneDTO studyMilestoneDTO = new StudyMilestoneDTO();
    studyMilestoneDTO.setMilestoneCode(
    CdConverter.convertStringToCd(MilestoneCode.TRIAL_SUMMARY_SENT.getCode()));
    
    LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS , 0); 
    Calendar offsetTime = Calendar.getInstance();
    Calendar milestoneDate = Calendar.getInstance();
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);     
    
    
    try {
        LOG.info("Performing the Search");    
        List<StudyMilestoneDTO> studyMilestoneDTOList = smRemote.
                                                       search(studyMilestoneDTO, limit);
        
        LOG.info("The Search results returned" + studyMilestoneDTOList.size());
        
        if (studyMilestoneDTOList != null && !studyMilestoneDTOList.isEmpty()) {
      
            for (StudyMilestoneDTO smdto : studyMilestoneDTOList) { 
          
                milestoneDate.setTime(smdto.getMilestoneDate().getValue());
          
                //get the offset of 5 business days
                offsetTime.setTime(smdto.getMilestoneDate().getValue());
                offsetTime.add(Calendar.DAY_OF_YEAR, -PAST_5_DAYS);
           
                String milestoneDtString = sdf.format(milestoneDate.getTime());
                String currDtString = sdf.format(Calendar.getInstance().getTime());
           
                if (!milestoneDtString.equals(currDtString) 
                        && checkIfBusinessdays(PAST_5_DAYS, milestoneDate)
                        && !checkMilestoneExists(smdto)) {
                    LOG.info("Creating a new milestone with code - initial abstration verify");   
                    StudyMilestoneDTO newDTO = new StudyMilestoneDTO();
                    newDTO.setCommentText(
                             StConverter.convertToSt("Milestone auto-set based on Non-Response within 5 days"));
                    newDTO.setMilestoneCode(CdConverter.convertStringToCd(
                                               MilestoneCode.INITIAL_ABSTRACTION_VERIFY.getCode()));
                    newDTO.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
                    newDTO.setStudyProtocolIdentifier(smdto.getStudyProtocolIdentifier());
                    smRemote.create(newDTO);
               }
            } 
          }
          LOG.info("Done with the task.");
       } catch (TooManyResultsException e) {
         LOG.error("Too Many Results Exception" + e.getLocalizedMessage());  
         throw new PAException("ToomanyReusltsException occured");
       } 
    }   
    /**
     * Check if businessdays.
     * 
     * @param weekdays the weekdays
     * @param cal the cal
     * 
     * @return true, if successful
     */
    @SuppressWarnings({"PMD" }) 
    private static boolean checkIfBusinessdays(int weekdays, Calendar cal) {
        boolean allBusinessDays = false;
        while (weekdays > 0) {
            int day = cal.get(Calendar.DAY_OF_WEEK);
            if ((day != Calendar.SUNDAY) && (day != Calendar.SATURDAY)) {
                weekdays--;
                allBusinessDays = true;
            } else {
                allBusinessDays = false;
                break;
            }
         
            cal.add(Calendar.DAY_OF_WEEK, -1);
        }
        return allBusinessDays;
    }

    /**
     * Check milestone exists.
     * 
     * @param dto the dto
     * 
     * @return true, if successful
     * 
     * @throws PAException the PA exception
     */
    private boolean checkMilestoneExists(StudyMilestoneDTO dto) throws PAException {
        boolean milestoneExits = false;
        List<StudyMilestoneDTO> existingDtoList = smRemote.getByStudyProtocol(dto.getStudyProtocolIdentifier());
        for (StudyMilestoneDTO smdto : existingDtoList) {
            if (smdto.getMilestoneCode().getCode().equals(MilestoneCode.INITIAL_ABSTRACTION_VERIFY.getCode())) {
                milestoneExits = true;
            }
        }
        return milestoneExits;
    }

  }
