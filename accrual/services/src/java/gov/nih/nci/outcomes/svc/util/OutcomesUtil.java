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
/**
 * @author Naveen Amiruddin
 * A utility class for outcomes business service
 * 
 */
package gov.nih.nci.outcomes.svc.util;

import gov.nih.nci.accrual.dto.PerformedActivityDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.dto.PerformedObservationResultDto;
import gov.nih.nci.accrual.dto.PerformedSubjectMilestoneDto;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.enums.PerformedObservationResultTypeCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Class OutcomesUtil.
 * @author Naveen Amiruddin
 * @since Mar 2, 2010
 */
public class OutcomesUtil {


    /**
     * return a collection of dto's for a given name code and study subject id.
     * @param activityNameCode activity Name codee
     * @param cctx service context
     * @param studySubjectIi study subject identifier
     * @param isUnique throws exception if duplicate is found for a single name code.
     * @param isNotEmpty throws exception if none found.
     * @param belongsToMe if the passes obsercationIi and belongs to that patient during update
     * @param performedObservationIi passed PO Ii during update. belongsToMe should be used in conjunction
     * @return PerformedObservationDto dto
     * @throws OutcomesException exception
     */
    public static List<PerformedObservationDto> getPerformedObservationByNameCode(ActivityNameCode activityNameCode ,
            SvcContext cctx ,  Ii studySubjectIi , 
            boolean isUnique , boolean isNotEmpty , boolean belongsToMe , Ii performedObservationIi)
            throws OutcomesException {
        List<PerformedObservationDto> dtoList = null;
        List<PerformedObservationDto> out = new ArrayList<PerformedObservationDto>();
        boolean beLongsToMeFound = false;
        if (PAUtil.isIiNull(studySubjectIi)) {
            throw new OutcomesException("Study Subject Identifier cannot be null");            
        }
        if (activityNameCode == null) {
            throw new OutcomesException("name code cannot be null");            
        }
        if (belongsToMe && PAUtil.isIiNull(performedObservationIi)) {
            throw new OutcomesException("Performed Observation Ii cannot be null when belongstoMe=true ");
        }
        try {
            dtoList = cctx.getPerformedActivityService().getPerformedObservationByStudySubject(studySubjectIi);
            for (PerformedObservationDto dto : dtoList) {
                if (dto.getNameCode() != null && activityNameCode.getCode().equals(dto.getNameCode().getCode())) {
                    out.add(dto);
                    if (belongsToMe && performedObservationIi.getExtension().
                            equals(dto.getIdentifier().getExtension())) {
                        beLongsToMeFound = true;
                    }
                }
            }
            if (out.size() > 1 && isUnique) {
                throw new OutcomesException("More than one Performed Observation found for Patient identifier = "
                        + studySubjectIi.getExtension() + " and name code " + activityNameCode.getCode());
            }
            if (dtoList.isEmpty() && isNotEmpty) {
                throw new OutcomesException("Performed Observation not found for Patient identifier = "
                        + studySubjectIi.getExtension() + " and name code " + activityNameCode.getCode());
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in OutcomesUtil.getPerformedObservationByNameCode().", e);
        }
        if (belongsToMe && !beLongsToMeFound) {
            throw new OutcomesException(
                    "You don't have privilege to view/update the Performeded Observation Identifier " 
                    +  performedObservationIi.getExtension());
        }
        return out;
    }
    
    /**
     * return a boolean for a given identifier, category code and study subject id.
     * 
     * @param activityCategoryCode the activity category code
     * @param cctx the cctx
     * @param studySubjectIi the study subject ii
     * @param identifier the identifier
     * 
     * @return the by category code
     * 
     * @throws OutcomesException the outcomes exception
     */
    public static boolean getPerformedActivityByCategoryCode(ActivityCategoryCode activityCategoryCode,
            SvcContext cctx,  Ii studySubjectIi, Ii identifier) throws OutcomesException {
        boolean exists = false;
        List<PerformedActivityDto> dtoList = null;
        List<PerformedActivityDto> out = new ArrayList<PerformedActivityDto>();
        if (PAUtil.isIiNull(studySubjectIi)) {
            throw new OutcomesException("Study Subject Identifier cannot be null");            
        }
        if (activityCategoryCode == null) {
            throw new OutcomesException("Category code cannot be null");            
        }
        try {
            dtoList = cctx.getPerformedActivityService().getByStudySubject(studySubjectIi);
            for (PerformedActivityDto dto : dtoList) {
                if (dto.getCategoryCode() != null && activityCategoryCode.getCode().equals(
                        dto.getCategoryCode().getCode())) {
                    out.add(dto);
                }
            }
            for (PerformedActivityDto dto : out) {
                if (dto.getIdentifier().getExtension().equals(identifier.getExtension())) {
                    exists = true;
                }
            }
            if (!exists) {
                throw new OutcomesException("Performed Activity was not found for identifier = " 
                        + identifier.getExtension() + " and Patient identifier = " + studySubjectIi.getExtension() 
                        + " and name code " + activityCategoryCode.getCode());
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in OutcomesUtil.getPerformedActivityByCategoryCode().", e);
        }
        return exists;
    }

    /*
     * Get the Create Date. Done to make PMD happy.
     */
    private static Date getPaCreateDate(PerformedActivityDto pa) {
        if (pa.getCategoryCode() != null
                && pa.getCategoryCode().getCode() != null
                && pa.getCategoryCode().getCode().equals(ActivityCategoryCode.COURSE.getCode())) {
            Ts created = pa.getActualDateRange().getLow();
            if (created != null) {
                return created.getValue();
            }
        }
        return null;
    }

    /**
     * Get the earliest recorded course date.
     * @param cctx the cctx
     * @return today's date or earlier as reported by the activity
     * @throws RemoteException pass to caller
     */
    public static Date getEarliestCourseDate(SvcContext cctx) throws RemoteException {
        Date earliest = null;
        List<PerformedActivityDto> paList = cctx.getPerformedActivityService().getByStudySubject(
                cctx.getStudySubjectIi());
        for (PerformedActivityDto pa : paList) {
            Date temp = getPaCreateDate(pa);
            if (temp != null) {
                earliest = new Date();
                if (earliest.getTime() > temp.getTime()) {
                    earliest = temp;
                }
            }
        }

        return earliest;
    }

    private static Date getAdrLowDate(PerformedSubjectMilestoneDto psm) {
        if (psm.getNameCode() != null
                && psm.getNameCode().getCode() != null
                && psm.getNameCode().getCode().equals(ActivityNameCode.OFF_TREATMENT.getCode())) {
            Ivl<Ts> adr = psm.getActualDateRange();
            if (adr != null) {
                Ts low = adr.getLow();
                if (low != null) {
                    return low.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Get the earliest Off Treatment date.
     * @param cctx the cctx
     * @return today's date or earlier as reported by the activity
     * @throws RemoteException pass to caller
     */
    public static Date getEarliestOffTreatmentDate(SvcContext cctx) throws RemoteException {
        Date earliest = null;
        List<PerformedSubjectMilestoneDto> psmList =
            cctx.getPerformedActivityService().getPerformedSubjectMilestoneByStudySubject(cctx.getStudySubjectIi());
        for (PerformedSubjectMilestoneDto psm : psmList) {
            Date temp = getAdrLowDate(psm);
            if (temp != null) {
                earliest = new Date();
                if (earliest.getTime() > temp.getTime()) {
                    earliest = temp;
                }
            }
        }

        return earliest;
    }

    private static Date getRdrResultDate(PerformedObservationResultDto dto) throws RemoteException {
        if (dto.getTypeCode() != null
                && dto.getTypeCode().getCode() != null
                && dto.getTypeCode().getCode().
                       equals(PerformedObservationResultTypeCode.DEATH_DATE.getCode())) {
            Ivl<Ts> rdr = dto.getResultDateRange();
            if (rdr != null) {
                Ts low = rdr.getLow();
                if (low != null) {
                    return low.getValue();
                }
            }
        }
        return null;
    }

    private static Date getEarliestRdrResultDate(PerformedObservationDto po, SvcContext cctx) throws RemoteException {
        Date earliest = new Date();
        List<PerformedObservationResultDto> deathCauseList = cctx.getPerFormedObservationResultService().
        getPerformedObservationResultByPerformedActivity(po.getIdentifier());
        for (PerformedObservationResultDto dto : deathCauseList) {
            Date temp = getRdrResultDate(dto);
            if (temp != null && earliest.getTime() > temp.getTime()) {
                earliest = temp;
            }
        }
        return earliest;
    }

    private static Date getDeathDate(PerformedObservationDto poBean, SvcContext cctx) throws RemoteException {
        if (poBean.getNameCode() != null
                && poBean.getNameCode().getCode() != null
                && poBean.getNameCode().getCode().equals(ActivityNameCode.DEATH_INFORMATION.getCode())) {
            return getEarliestRdrResultDate(poBean, cctx);
        }
        return null;
    }
    
    /**
     * Get the earliest Death date.
     * @param cctx the cctx
     * @return today's date or earlier as reported by the activity
     * @throws RemoteException pass to caller
     */
    public static Date getEarliestDeathDate(SvcContext cctx) throws RemoteException {
        Date earliest = null;
        List<PerformedObservationDto> poList = cctx.getPerformedActivityService().getPerformedObservationByStudySubject(
                cctx.getStudySubjectIi());
        for (PerformedObservationDto poBean : poList) {
            Date temp = getDeathDate(poBean, cctx);
            if (temp != null) {
                earliest = new Date();
                if (earliest.getTime() > temp.getTime()) {
                    earliest = temp;
                }
            }
        }
        return earliest;
    }

    /**
     * Get the earliest known treatment date.
     *@param cctx the cctx
     * @return the earliest date
     * @throws RemoteException the remote exception
     */
    protected static Date getEarliestTreatmentsDate(SvcContext cctx) throws RemoteException  {
        Date earliest = null;
        Date temp;
        earliest = getEarliestCourseDate(cctx);

        temp = getEarliestOffTreatmentDate(cctx);
        if (earliest != null && temp != null && earliest.getTime() > temp.getTime()) {
            earliest = temp;
        }

        temp = getEarliestDeathDate(cctx);
        if (earliest != null && temp != null && earliest.getTime() > temp.getTime()) {
            earliest = temp;
        }
        return earliest;
    }

    /**
     * Gets the diagnosis date.
     * @param cctx the cctx
     * @return the diagnosis date
     * @throws RemoteException the remote exception
     */
    public static Date getDiagnosisDate(SvcContext cctx) throws RemoteException {
        Date earliest = null;
        List<PerformedObservationDto> poList = cctx.getPerformedActivityService().getPerformedObservationByStudySubject(
                cctx.getStudySubjectIi());
        for (PerformedObservationDto poBean : poList) {
            if (poBean.getNameCode() != null && poBean.getNameCode().getCode() != null
                    && ActivityNameCode.DIAGNOSIS.getCode().equals(
                            CdConverter.convertCdToString(poBean.getNameCode()))) {
                Ivl<Ts> dignosisDate = poBean.getActualDateRange();
                if (dignosisDate != null) {
                    Ts low = dignosisDate.getLow();
                    if (low != null) {
                        earliest = low.getValue();
                    }
                }
            }
        }
        return earliest;
    }
    
    /**
     * Check valid dates.
     * @param date the date
     * @return true, if successful
     */
    public static boolean checkValidDate(Date date) {
        Date curentdate = new Date(); 
        if (date.after(curentdate)) {
            return false;
        }
        return true;
    }

    /**
     * Gets the performed subject milestone by name code.
     * 
     * @param activityNameCode the activity name code
     * @param cctx the cctx
     * @param studySubjectIi the study subject ii
     * @param isUnique the is unique
     * @param isNotEmpty the is not empty
     * @param belongsToMe the belongs to me
     * @param performedSubjectMilestoneIi the performed subject milestone ii
     * 
     * @return the performed subject milestone by name code
     * 
     * @throws OutcomesException the outcomes exception
     */
    public static List<PerformedSubjectMilestoneDto> getPerformedSubjectMilestoneByNameCode(
            ActivityNameCode activityNameCode, SvcContext cctx ,  Ii studySubjectIi , 
            boolean isUnique , boolean isNotEmpty , boolean belongsToMe , Ii performedSubjectMilestoneIi)
            throws OutcomesException {
        List<PerformedSubjectMilestoneDto> dtoList = null;
        List<PerformedSubjectMilestoneDto> out = new ArrayList<PerformedSubjectMilestoneDto>();
        boolean beLongsToMeFound = false;
        if (PAUtil.isIiNull(studySubjectIi)) {
            throw new OutcomesException("Study Subject Identifier cannot be null");            
        }
        if (activityNameCode == null) {
            throw new OutcomesException("name code cannot be null");            
        }
        if (belongsToMe && PAUtil.isIiNull(performedSubjectMilestoneIi)) {
            throw new OutcomesException("PerformedSubjectMilestone Ii cannot be null when belongstoMe=true ");
        }
        try {
            dtoList = cctx.getPerformedActivityService().getPerformedSubjectMilestoneByStudySubject(studySubjectIi);
            for (PerformedSubjectMilestoneDto dto : dtoList) {
                if (dto.getNameCode() != null && activityNameCode.getCode().equals(dto.getNameCode().getCode())) {
                    out.add(dto);
                    if (belongsToMe && performedSubjectMilestoneIi.getExtension().
                            equals(dto.getIdentifier().getExtension())) {
                        beLongsToMeFound = true;
                    }
                }
            }
            if (out.size() > 1 && isUnique) {
                throw new OutcomesException("More than one PerformedSubjectMilestone found for Patient identifier = "
                        + studySubjectIi.getExtension() + " and name code " + activityNameCode.getCode());
            }
            if (dtoList.isEmpty() && isNotEmpty) {
                throw new OutcomesException("PerformedSubjectMilestone not found for Patient identifier = "
                        + studySubjectIi.getExtension() + " and name code " + activityNameCode.getCode());
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in OutcomesUtil.getPerformedSubjectMilestoneByNameCode().", e);
        }
        if (belongsToMe && !beLongsToMeFound) {
            throw new OutcomesException(
                    "You don't have privilege to view/update the PerformedSubjectMilestone Identifier " 
                    +  performedSubjectMilestoneIi.getExtension());
        }
        return out;
    }
}
