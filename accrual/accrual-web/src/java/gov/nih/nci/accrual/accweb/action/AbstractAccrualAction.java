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
package gov.nih.nci.accrual.accweb.action;

import gov.nih.nci.accrual.accweb.util.AccrualConstants;
import gov.nih.nci.accrual.dto.util.SearchTrialResultDto;
import gov.nih.nci.accrual.service.PatientServiceLocal;
import gov.nih.nci.accrual.service.PerformedActivityService;
import gov.nih.nci.accrual.service.StudySubjectServiceLocal;
import gov.nih.nci.accrual.service.SubjectAccrualServiceLocal;
import gov.nih.nci.accrual.service.batch.BatchFileService;
import gov.nih.nci.accrual.service.batch.CdusBatchUploadReaderServiceLocal;
import gov.nih.nci.accrual.service.util.AccrualDiseaseServiceLocal;
import gov.nih.nci.accrual.service.util.CountryService;
import gov.nih.nci.accrual.service.util.SearchStudySiteService;
import gov.nih.nci.accrual.service.util.SearchTrialService;
import gov.nih.nci.accrual.service.util.SubmissionHistoryService;
import gov.nih.nci.accrual.util.AccrualServiceLocator;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.util.ISOUtil;

import java.util.HashSet;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Hugh Reinhart
 * @since 4/16/2009
 */
public abstract class AbstractAccrualAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = -5423491292515161915L;

    private SearchTrialService searchTrialSvc;
    private SearchStudySiteService searchStudySiteSvc;
    private StudySubjectServiceLocal studySubjectSvc;
    private PatientServiceLocal patientSvc;
    private PerformedActivityService performedActivitySvc;
    private CountryService countrySvc;
    private AccrualDiseaseServiceLocal diseaseSvc;
    private PlannedActivityServiceRemote plannedActivitySvc;
    private CdusBatchUploadReaderServiceLocal cdusBatchUploadReaderSvc;
    private BatchFileService batchFileSvc;
    private SubjectAccrualServiceLocal subjectAccrualSvc;
    private LookUpTableServiceRemote lookupTableSvc;
    private SubmissionHistoryService submissionHistorySvc;
    private boolean notCtepDcpTrial;

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        searchTrialSvc = AccrualServiceLocator.getInstance().getSearchTrialService();
        searchStudySiteSvc = AccrualServiceLocator.getInstance().getSearchStudySiteService();
        studySubjectSvc = AccrualServiceLocator.getInstance().getStudySubjectService();
        patientSvc = AccrualServiceLocator.getInstance().getPatientService();
        performedActivitySvc = AccrualServiceLocator.getInstance().getPerformedActivityService();
        countrySvc = AccrualServiceLocator.getInstance().getCountryService();
        cdusBatchUploadReaderSvc = AccrualServiceLocator.getInstance().getBatchUploadReaderService();
        submissionHistorySvc = AccrualServiceLocator.getInstance().getSubmissionHistoryService();
        diseaseSvc = AccrualServiceLocator.getInstance().getAccrualDiseaseService();
        plannedActivitySvc = PaServiceLocator.getInstance().getPlannedActivityService();
        batchFileSvc = AccrualServiceLocator.getInstance().getBatchFileService();
        subjectAccrualSvc = AccrualServiceLocator.getInstance().getSubjectAccrualService();
        lookupTableSvc = PaServiceLocator.getInstance().getLookUpTableService();
    }
    /**
     * Default execute method for action classes.
     * @return action result
     */
    @Override
    public String execute() {
        // make sure user authorized
        if (getUserRole() == null || !getUserRole().equals(AccrualConstants.ROLE_PUBLIC)) {
            return AccrualConstants.AR_LOGOUT;
        }
        return SUCCESS;
    }
    /**
     * @return the role from the session
     */
    protected String getUserRole() {
        return (String) ServletActionContext.getRequest().getSession().getAttribute(AccrualConstants.SESSION_ATTR_ROLE);
    }

    /**
     * @throws PAException the error
     */
    protected void loadTrialSummaryIntoSession() throws PAException {
        SearchTrialResultDto trialSummary = getSearchTrialSvc().getTrialSummaryByStudyProtocolIi(getSpIi());
        ServletActionContext.getRequest().getSession().setAttribute("trialSummary", trialSummary);
        notCtepDcpTrial = !getSearchStudySiteSvc().isStudyHasCTEPId(getSpIi()) 
                && !getSearchStudySiteSvc().isStudyHasDCPId(getSpIi());
    }
    
    /**
     * @throws PAException the error
     */
    protected void checkIfNonInterventionalTrialChanges() throws PAException {
        SearchTrialResultDto trialSummary = (SearchTrialResultDto) ServletActionContext.getRequest().getSession()
                .getAttribute("trialSummary");
        if (!StConverter.convertToString(trialSummary.getTrialType()).equals(AccrualUtil.INTERVENTIONAL)
                && !ISOUtil.isStNull(trialSummary.getAccrualSubmissionLevel())
                && (trialSummary.getAccrualSubmissionLevel().getValue().equals(AccrualUtil.BOTH)
                        || trialSummary.getAccrualSubmissionLevel().getValue().equals(AccrualUtil.SUBJECT_LEVEL))) {
            loadTrialSummaryIntoSession();
        }
    }
    
    /**
     * @return the spIi
     */
    public Ii getSpIi() {
        return (Ii) ServletActionContext.getRequest().getSession().getAttribute(AccrualConstants.SESSION_ATTR_SPII);
    }
    
    /**
     * @param spIi the spIi to set
     * @throws PAException on error
     */
    public void setSpIi(Ii spIi) throws PAException {
        RegistryUser ru = 
            PaServiceLocator.getInstance().getRegistryUserService().getUser(CaseSensitiveUsernameHolder.getUser());
        Ii ruIi = IiConverter.convertToIi(ru.getId());
        if (BlConverter.convertToBool(searchTrialSvc.isAuthorized(spIi, ruIi))) {
            ServletActionContext.getRequest().getSession().setAttribute(AccrualConstants.SESSION_ATTR_SPII, spIi);
        } else {
            throw new PAException("User does not have permissions to view the selected trial.");
        }
    }
    
    /**
     * @param value object being tested
     * @param errorMsg action error message to put on stack if object is empty
     */
    public void addActionErrorIfEmpty(Object value, String errorMsg) {
        if (value instanceof String && StringUtils.isEmpty((String) value)) {
            addActionError(errorMsg);
        } else if (value instanceof HashSet && ((HashSet) value).isEmpty()) {
            addActionError(errorMsg);
        } else if (value == null) {
            addActionError(errorMsg);
        }
    }

    /**
     * @return the searchTrialSvc
     */
    public SearchTrialService getSearchTrialSvc() {
        return searchTrialSvc;
    }

    /**
     * @return the searchStudySiteSvc
     */
    public SearchStudySiteService getSearchStudySiteSvc() {
        return searchStudySiteSvc;
    }

    /**
     * @return the studySubjectSvc
     */
    public StudySubjectServiceLocal getStudySubjectSvc() {
        return studySubjectSvc;
    }

    /**
     * @return the patientSvc
     */
    public PatientServiceLocal getPatientSvc() {
        return patientSvc;
    }

    /**
     * @return the performedActivitySvc
     */
    public PerformedActivityService getPerformedActivitySvc() {
        return performedActivitySvc;
    }

    /**
     * @return the countrySvc
     */
    public CountryService getCountrySvc() {
        return countrySvc;
    }

    /**
     * @return the diseaseSvc
     */
    public AccrualDiseaseServiceLocal getDiseaseSvc() {
        return diseaseSvc;
    }    

    /**
     * @return the plannedActivitySvc
     */
    public PlannedActivityServiceRemote getPlannedActivitySvc() {
        return plannedActivitySvc;
    }
    
    /**
     * @return the cdus batch upload reader service
     */
    public CdusBatchUploadReaderServiceLocal getCdusBatchUploadReaderSvc() {
        return cdusBatchUploadReaderSvc;
    }
    
    /**
     * @return the batch file service
     */
    public BatchFileService getBatchFileSvc() {
        return batchFileSvc;
    }
    
    /**
     * @return the subjectAccrualSvc
     */
    public SubjectAccrualServiceLocal getSubjectAccrualSvc() {
        return subjectAccrualSvc;
    }
    
    /**
     * @return the lookupTableSvc
     */
    public LookUpTableServiceRemote getLookupTableSvc() {
        return lookupTableSvc;
    }
    /**
     * @return the submissionHistorySvc
     */
    public SubmissionHistoryService getSubmissionHistorySvc() {
        return submissionHistorySvc;
    }
    /**
     * @return isNotCtepDcpTrial
     */
    public boolean isNotCtepDcpTrial() {
        return notCtepDcpTrial;
    }    
}
