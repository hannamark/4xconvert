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
package gov.nih.nci.outcomes.svc;

import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.service.ActivityRelationshipServiceLocal;
import gov.nih.nci.accrual.service.PatientServiceLocal;
import gov.nih.nci.accrual.service.PerformedActivityServiceLocal;
import gov.nih.nci.accrual.service.PerformedObservationResultServiceLocal;
import gov.nih.nci.accrual.service.StudySubjectServiceLocal;
import gov.nih.nci.accrual.service.util.BaseLookUpService;
import gov.nih.nci.accrual.service.util.CountryService;
import gov.nih.nci.accrual.service.util.SearchTrialService;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.outcomes.svc.dto.AbstractUserDto;
import gov.nih.nci.outcomes.svc.dto.CycleSvcDto;
import gov.nih.nci.outcomes.svc.dto.DeathInformationSvcDto;
import gov.nih.nci.outcomes.svc.dto.DiagnosisSvcDto;
import gov.nih.nci.outcomes.svc.dto.DiseaseEvaluationSvcDto;
import gov.nih.nci.outcomes.svc.dto.DrugBiologicSvcDto;
import gov.nih.nci.outcomes.svc.dto.LesionAssessmentSvcDto;
import gov.nih.nci.outcomes.svc.dto.OffTreatmentSvcDto;
import gov.nih.nci.outcomes.svc.dto.PathologySvcDto;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.outcomes.svc.dto.PerformanceStatusSvcDto;
import gov.nih.nci.outcomes.svc.dto.PriorTherapySvcDto;
import gov.nih.nci.outcomes.svc.dto.RadiationSvcDto;
import gov.nih.nci.outcomes.svc.dto.StagingSvcDto;
import gov.nih.nci.outcomes.svc.dto.SurgerySvcDto;
import gov.nih.nci.outcomes.svc.dto.TreatmentRegimenSvcDto;
import gov.nih.nci.outcomes.svc.dto.TumorMarkerSvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.outcomes.svc.util.PatientSvcBean;
import gov.nih.nci.outcomes.svc.util.SvcContext;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

/**
 * <pre>
 * To summarize, the &quot;get&quot; options are:
 * Get one full patient record, with all associated child objects (getById)
 * Get one patient record with your choice of child objects (get with a specified ID)
 * Get all patient records for a user with no child objects (get without an ID)
 * </pre>
 * 
 * @author Hugh Reinhart
 * @since Mar 16, 2010
 *
 */
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OutcomesSvcBeanLocal extends BaseOutcomesSvc implements OutcomesSvcLocal {

    /** The activity relationship service. */
    @EJB
    protected ActivityRelationshipServiceLocal activityRelationshipService;

    /** The performed activity service. */
    @EJB
    protected PerformedActivityServiceLocal performedActivityService;

    /** The performed observation result service. */
    @EJB
    protected PerformedObservationResultServiceLocal performedObservationResultService;

    /** The study subject service. */
    @EJB
    protected StudySubjectServiceLocal studySubjectService;

    /** The patient service. */
    @EJB
    protected PatientServiceLocal patientService;

    /** The search trial service. */
    @EJB
    protected SearchTrialService searchTrialService;

    /** The outcomes user service. */
    @EJB
    protected OutcomesUserSvcLocal outcomesUserService;

    /** The country service. */
    @EJB
    protected CountryService countryService;

    /** The look up service. */
    @EJB
    protected BaseLookUpService lookUpService;

    /** The context used for service calls. */
    protected SvcContext context;

    /** The container context. */
    protected SessionContext ejbContext;

    /**
     * Sets the session context.
     *
     * @param ctx the new session context
     */
    @Resource
    protected void setSessionContext(SessionContext ctx) {
        ejbContext = ctx;
    }

    /**
     * {@inheritDoc}
     */
    public List<PatientSvcDto> get(PatientSvcDto dto)
            throws OutcomesException {
        getContext();
        List<PatientSvcDto> resultList = null;
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            resultList = PatientSvcBean.getInstance().search(context);
        } else {
            checkSecurity(dto.getIdentifier());
            PatientSvcDto result = PatientSvcBean.getInstance().get(dto, context, null);
            resultList = new ArrayList<PatientSvcDto>();
            resultList.add(result);
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    public PatientSvcDto getById(Ii patientIdentifier) throws OutcomesException {
        if (PAUtil.isIiNull(patientIdentifier)) {
            throw new OutcomesException("Must set patientIdentifier in calls to getById();");
        }
        getContext();
        checkSecurity(patientIdentifier);
        PatientSvcDto dto = new PatientSvcDto();
        dto.setIdentifier(patientIdentifier);
        dto.setTreatmentRegimens(new ArrayList<TreatmentRegimenSvcDto>());
        dto = PatientSvcBean.getInstance().get(dto, context, null);
        if (dto.getTreatmentRegimens() != null) {
            for (TreatmentRegimenSvcDto tr : dto.getTreatmentRegimens()) {
                tr.setCycles(new ArrayList<CycleSvcDto>());
            }
            dto = PatientSvcBean.getInstance().get(dto, context, null);
            for (TreatmentRegimenSvcDto tr : dto.getTreatmentRegimens()) {
                for (CycleSvcDto cy : tr.getCycles()) {
                    cy.setDrugBiologics(new ArrayList<DrugBiologicSvcDto>());
                    cy.setRadiations(new ArrayList<RadiationSvcDto>());
                    cy.setSurgeries(new ArrayList<SurgerySvcDto>());
                }
                tr.setDiseaseEvaluations(new ArrayList<DiseaseEvaluationSvcDto>());
                tr.setLesionAssessments(new ArrayList<LesionAssessmentSvcDto>());
                tr.setOffTreatment(new OffTreatmentSvcDto());
            }
        }
        dto.setDeathInformation(new DeathInformationSvcDto());
        dto.setDiagnosis(new DiagnosisSvcDto());
        dto.setPathology(new PathologySvcDto());
        dto.setPerformanceStatus(new PerformanceStatusSvcDto());
        dto.setPriorTherapy(new PriorTherapySvcDto());
        dto.setStaging(new StagingSvcDto());
        dto.setTumorMarkers(new ArrayList<TumorMarkerSvcDto>());
        return PatientSvcBean.getInstance().get(dto, context, null);
    }

    /**
     * {@inheritDoc}
     */
    public PatientSvcDto write(PatientSvcDto dto) throws OutcomesException {
        getContext();
        checkSecurity(dto.getIdentifier());
        return PatientSvcBean.getInstance().put(dto, context, null);
    }

    /**
     * Gets the context.
     *
     * @return the context
     *
     * @throws OutcomesException the outcomes exception
     */
    protected SvcContext getContext() throws OutcomesException {
        if (ejbContext == null || ejbContext.getCallerPrincipal() == null) {
            throw new OutcomesException("Error getting session context for ejb.");
        }
        context = new SvcContext();
        context.setName(StConverter.convertToSt(getCallerPrincipal(ejbContext)));
        AbstractUserDto user;
        try {
            user = outcomesUserService.getUser();
        } catch (RemoteException e) {
            throw new OutcomesException("Error getting account information for "
                    + StConverter.convertToString(context.getName()) + ".", e);
        }
        context.setPhysicianIi(user.getPhysicianIdentifier());
        context.setTreatingSiteIi(user.getTreatmentSiteIdentifier());
        context.setActivityRelationshipService(activityRelationshipService);
        context.setPatientService(patientService);
        context.setPerformedActivityService(performedActivityService);
        context.setPerFormedObservationResultService(performedObservationResultService);
        context.setSearchTrialService(searchTrialService);
        context.setStudySubjectService(studySubjectService);
        context.setCountryService(countryService);
        context.setLookUpService(lookUpService);
        return context;
    }

    private void checkSecurity(Ii studySubjectIi) throws OutcomesException {
        if (!PAUtil.isIiNull(studySubjectIi)) {
            StudySubjectDto ssDto;
            try {
                ssDto = studySubjectService.get(studySubjectIi);
                if (ssDto != null) {
                    context.setStudySubjectIi(ssDto.getIdentifier());
                }
            } catch (RemoteException e) {
                throw new OutcomesException("Error retrieving data for security check.", e);
            }
            if (ssDto == null) {
                throw new OutcomesException("Patient not found.");
            }
            if (!context.getName().getValue().equals(StConverter.convertToString(ssDto.getOutcomesLoginName()))) {
                throw new OutcomesException("Authorization error for " + StConverter.convertToString(context.getName())
                        + ".");
            }
        }
    }
}
