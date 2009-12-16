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
*/
package gov.nih.nci.accrual.web.action;

import gov.nih.nci.accrual.dto.ActivityRelationshipDto;
import gov.nih.nci.accrual.dto.PerformedActivityDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.dto.PerformedObservationResultDto;
import gov.nih.nci.accrual.dto.PerformedSubjectMilestoneDto;
import gov.nih.nci.accrual.service.ActivityRelationshipService;
import gov.nih.nci.accrual.service.PerformedActivityService;
import gov.nih.nci.accrual.service.PerformedObservationResultService;
import gov.nih.nci.accrual.service.StudySubjectService;
import gov.nih.nci.accrual.service.SubmissionService;
import gov.nih.nci.accrual.service.UserService;
import gov.nih.nci.accrual.service.util.CountryService;
import gov.nih.nci.accrual.service.util.PatientService;
import gov.nih.nci.accrual.service.util.SearchStudySiteService;
import gov.nih.nci.accrual.service.util.SearchTrialService;
import gov.nih.nci.accrual.web.dto.util.CourseWebDto;
import gov.nih.nci.accrual.web.util.AccrualConstants;
import gov.nih.nci.accrual.web.util.AccrualServiceLocator;
import gov.nih.nci.accrual.web.util.PaServiceLocator;
import gov.nih.nci.accrual.web.util.SessionEnvManager;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.enums.PerformedObservationResultTypeCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.DiseaseParentServiceRemote;
import gov.nih.nci.pa.service.DiseaseServiceRemote;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceRemote;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.util.PAUtil;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Hugh Reinhart
 * @since 4/16/2009
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity" })
public abstract class AbstractAccrualAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = -5423491292515161915L;

    /** SearchTrialService. */
    protected SearchTrialService searchTrialSvc;
    /** SearchStudySiteService. */
    protected SearchStudySiteService searchStudySiteSvc;
    /** SubmissionService. */
    protected SubmissionService submissionSvc;
    /** StudySubjectService. */
    protected StudySubjectService studySubjectSvc;
    /** PatientService. */
    protected PatientService patientSvc;
    /** PerformedActivityService. */
    protected PerformedActivityService performedActivitySvc;
    /** CountryService. */
    protected CountryService countrySvc;
    /** DiseaseService. */
    protected DiseaseServiceRemote diseaseSvc;
    /** PlannedActivityService. */
    protected PlannedActivityServiceRemote plannedActivitySvc;
    /** DiseaseService. */
    protected DiseaseParentServiceRemote diseaseParentSvc;
    /** PerformedObservationResultService. */
    protected PerformedObservationResultService performedObservationResultSvc;
    /** ActivityRelationshipService. */
    protected ActivityRelationshipService activityRelationshipSvc;    
    /** The intervention alternate name svc. */
    protected InterventionAlternateNameServiceRemote interventionANameSvc;    
    /** The intervention svc. */
    protected InterventionServiceRemote interventionSvc;
    /** UserService. */
    protected UserService userSvc;

    /**
     * {@inheritDoc}
     */
    public void prepare() {
        searchTrialSvc = AccrualServiceLocator.getInstance().getSearchTrialService();
        searchStudySiteSvc = AccrualServiceLocator.getInstance().getSearchStudySiteService();
        submissionSvc = AccrualServiceLocator.getInstance().getSubmissionService();
        studySubjectSvc = AccrualServiceLocator.getInstance().getStudySubjectService();
        patientSvc = AccrualServiceLocator.getInstance().getPatientService();
        performedActivitySvc = AccrualServiceLocator.getInstance().getPerformedActivityService();
        countrySvc = AccrualServiceLocator.getInstance().getCountryService();
        diseaseSvc = PaServiceLocator.getInstance().getDiseaseService();
        plannedActivitySvc = PaServiceLocator.getInstance().getPlannedActivityService();
        diseaseParentSvc = PaServiceLocator.getInstance().getDiseaseParentService();
        performedObservationResultSvc = AccrualServiceLocator.getInstance().getPerformedObservationResultService();
        activityRelationshipSvc = AccrualServiceLocator.getInstance().getActivityRelationshipService();
        interventionANameSvc = PaServiceLocator.getInstance().getInterventionAlternateNameService();
        interventionSvc = PaServiceLocator.getInstance().getInterventionService();    
        userSvc = AccrualServiceLocator.getInstance().getUserService();
    }
    /**
     * Default execute method for action classes.
     * @return action result
     */
    @Override
    public String execute() {
        // make sure user authorized
        if (getUserRole() == null
                || !getUserRole().equals(AccrualConstants.ROLE_OUTCOMES)) {
            return AccrualConstants.AR_LOGOUT;
        }
        
        return SUCCESS;
    }
    /**
     * @return the role from the session
     */
    protected String getUserRole() {
        return (String) SessionEnvManager.getAttr(AccrualConstants.SESSION_ATTR_ROLE);
    }
    /**
     * @return the Ii of the current participant, null if none selected
     */
    protected Ii getParticipantIi() {
        return (Ii) SessionEnvManager.getAttr(AccrualConstants.SESSION_ATTR_PARTICIPANT_II);
    }
    /**
     * @return the Ii of the outcomes StudyProtocol Ii
     */
    protected Ii getSpIi() {
        return (Ii) SessionEnvManager.getAttr(AccrualConstants.SESSION_ATTR_STUDYPROTOCOL_II);
    }
    
    /**
     * @return the tp ii of outcomes TreatmentPlan Ii
     */
    protected Ii getTpIi() {
        return (Ii) SessionEnvManager.getAttr(AccrualConstants.SESSION_ATTR_TREATMENT_PLAN_II);
    }
    
    /**
     * @return the tp ii of outcomes Course Ii
     */
    protected Ii getCourseIi() {
        return (Ii) SessionEnvManager.getAttr(AccrualConstants.SESSION_ATTR_COURSE_II);
    }
    
    /**
     * @return user login name as iso string
     */
    protected St getAuthorizedUser() {
        return StConverter.convertToSt(ServletActionContext.getRequest().getRemoteUser());
    }
    /**
     * @param value object being tested
     * @param errorMsg action error message to put on stack if object is empty
     */
    public void addActionErrorIfEmpty(Object value, String errorMsg) {
        if (value instanceof String) {
            if (PAUtil.isEmpty((String) value)) {
                addActionError(errorMsg);
            }
        } else {
            if (value == null) {
                addActionError(errorMsg);
            }
        }
    }

    /**
     * @param fieldValue fieldValue
     * @param fieldName fieldName
     * @param errorMessage action error message to put on stack if object is empty
     */
    public void addFieldErrorIfEmpty(Object fieldValue, String fieldName, String errorMessage) {
        if (fieldValue instanceof String) {
            if (PAUtil.isEmpty((String) fieldValue)) {
                addFieldError(fieldName, errorMessage);
            }
        } else {
            if (fieldValue == null) {
                addFieldError(fieldName, errorMessage);
            }
        }
    }

    /**
     * Load treatment plans.
     * @throws RemoteException the remote exception
     */
    public void loadTreatmentPlans() throws RemoteException {
        List<PerformedActivityDto> dtoList = performedActivitySvc.getByStudySubject(getParticipantIi());
        Map<String, String> treatmentPlans = new LinkedHashMap<String, String>();
        for (PerformedActivityDto dto : dtoList) {
            if (ActivityCategoryCode.TREATMENT_PLAN.getCode().equals(dto.getCategoryCode().getCode())) {
                treatmentPlans.put(dto.getIdentifier().getExtension(), dto.getName().getValue());
            }
        }
        SessionEnvManager.setAttr(SessionEnvManager.TREATMENT_PLANS, treatmentPlans);
    }

    /*
     * Get the Create Date. Done to make PMD happy.
     */
    private Date getPaCreateDate(PerformedActivityDto pa) {
        if (pa.getCategoryCode() != null
                && pa.getCategoryCode().getCode() != null
                && pa.getCategoryCode().getCode().equals(ActivityCategoryCode.COURSE.getCode())) {
            Ts created = new CourseWebDto(pa).getCreateDate();
            if (created != null) {
                return created.getValue();
            }
        }
        return null;
    }
    
    /**
     * Get the earliest recorded course date.
     * 
     * @return today's date or earlier as reported by the activity
     * @throws RemoteException pass to caller
     */
    public Date getEarliestCourseDate() throws RemoteException {
        Date earliest = new Date();
        List<PerformedActivityDto> paList = performedActivitySvc.getByStudySubject(getParticipantIi());
        for (PerformedActivityDto pa : paList) {
            Date temp = getPaCreateDate(pa);
            if (temp != null && earliest.getTime() > temp.getTime()) {
                earliest = temp;
            }
        }
        
        return earliest;
    }

    private Date getAdrLowDate(PerformedSubjectMilestoneDto psm) {
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
     * 
     * @return today's date or earlier as reported by the activity
     * @throws RemoteException pass to caller
     */
    public Date getEarliestOffTreatmentDate() throws RemoteException {
        Date earliest = new Date();
        List<PerformedSubjectMilestoneDto> psmList =
            performedActivitySvc.getPerformedSubjectMilestoneByStudySubject(getParticipantIi());
        for (PerformedSubjectMilestoneDto psm : psmList) {
            Date temp = getAdrLowDate(psm);
            if (temp != null && earliest.getTime() > temp.getTime()) {
                earliest = temp;
            }
        }

        return earliest;
    }

    private Date getRdrResultDate(PerformedObservationResultDto dto) throws RemoteException {
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
    
    private Date getEarliestRdrResultDate(PerformedObservationDto po) throws RemoteException {
        Date earliest = new Date();
        List<PerformedObservationResultDto> deathCauseList =
            performedObservationResultSvc.getPerformedObservationResultByPerformedActivity(po.getIdentifier());
        for (PerformedObservationResultDto dto : deathCauseList) {
            Date temp = getRdrResultDate(dto);
            if (temp != null && earliest.getTime() > temp.getTime()) {
                earliest = temp;
            }
        }
        return earliest;
    }

    private Date getDeathDate(PerformedObservationDto poBean) throws RemoteException {
        if (poBean.getNameCode() != null
                && poBean.getNameCode().getCode() != null
                && poBean.getNameCode().getCode().equals(ActivityNameCode.DEATH_INFORMATION.getCode())) {
            List<ActivityRelationshipDto> arList =
                activityRelationshipSvc.getByTargetPerformedActivity(poBean.getIdentifier(),
                        CdConverter.convertStringToCd(AccrualConstants.COMP));
            PerformedObservationDto po =
                performedActivitySvc.getPerformedObservation(arList.get(0).getTargetPerformedActivityIdentifier());
            return getEarliestRdrResultDate(po);
        }
        return null;
    }
    /**
     * Get the earliest Death date.
     * 
     * @return today's date or earlier as reported by the activity
     * @throws RemoteException pass to caller
     */
    public Date getEarliestDeathDate() throws RemoteException {
        Date earliest = new Date();
        List<PerformedObservationDto> poList = performedActivitySvc.getPerformedObservationByStudySubject(
                getParticipantIi());
        for (PerformedObservationDto poBean : poList) {
            Date temp = getDeathDate(poBean);
            if (temp != null && earliest.getTime() > temp.getTime()) {
                earliest = temp;
            }
        }
        return earliest;
    }
    
    /**
     * Get the earliest known treatment date.
     * 
     * @return the earliest date
     */
    protected Date getEarliestTreatmentsDate() {
        Date earliest = null;
        Date temp;
        try {
            earliest = getEarliestCourseDate();

            temp = getEarliestOffTreatmentDate();
            if (earliest.getTime() > temp.getTime()) {
                earliest = temp;
            }

            temp = getEarliestDeathDate();
            if (earliest.getTime() > temp.getTime()) {
                earliest = temp;
            }
        } catch (RemoteException ex) {
            LOG.error(ex.getLocalizedMessage(), ex);
            addActionError("Service error. " + ex.getLocalizedMessage());
        }
        return earliest;
    }
    
    /**
     * Gets the diagnosis date.
     * @return the diagnosis date
     * @throws RemoteException the remote exception
     */
    public Date getDiagnosisDate() throws RemoteException {
        Date earliest = null;
        List<PerformedObservationDto> poList = performedActivitySvc.getPerformedObservationByStudySubject(
                getParticipantIi());
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
}
