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
package gov.nih.nci.accrual.web.action;

import gov.nih.nci.accrual.dto.PerformedSubjectMilestoneDto;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.accrual.dto.util.SearchStudySiteResultDto;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.web.dto.util.DiseaseWebDTO;
import gov.nih.nci.accrual.web.dto.util.PatientWebDto;
import gov.nih.nci.accrual.web.dto.util.SearchPatientsCriteriaWebDto;
import gov.nih.nci.accrual.web.dto.util.SearchStudySiteResultWebDto;
import gov.nih.nci.accrual.web.util.PaServiceLocator;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.struts2.ServletActionContext;

/**
 * @author Hugh Reinhart
 * @since Sep 21, 2009
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.CyclomaticComplexity" })
public class PatientAction extends AbstractAccrualAction {

    private static final long serialVersionUID = -6820189447703204634L;
    private static String deletedStatusCode = FunctionalRoleStatusCode.TERMINATED.getCode();
    private static List<String> validStatusCodes;
    static {
        validStatusCodes = new ArrayList<String>();
        validStatusCodes.add(FunctionalRoleStatusCode.PENDING.getCode());
        validStatusCodes.add(FunctionalRoleStatusCode.ACTIVE.getCode());
    }
    private static List<Country> listOfCountries = null;
    private static Long unitedStatesId = null;

    private SearchPatientsCriteriaWebDto criteria = null;
    private List<SearchStudySiteResultWebDto> listOfStudySites = null;
    private List<PatientWebDto> listOfPatients;
    private PatientWebDto patient;
    private EligibleGenderCode genderCriterion = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() {
        if (criteria == null) {
            criteria = new SearchPatientsCriteriaWebDto();
        }
        try {
            loadListOfCountries();
            loadListOfStudySites();
            loadListOfPatients();
        } catch (Exception e) {
            return ERROR;
        }
        ServletActionContext.getRequest().setAttribute("listOfPatients", listOfPatients);
        return super.execute();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String create() {
        try {
             loadListOfStudySites();
        } catch (RemoteException e) {
            return ERROR;
        }
        patient = new PatientWebDto(getSpIi(), unitedStatesId);
        return super.create();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String retrieve() {
        try {
            patient = null;
            loadListOfStudySites();
            loadListOfPatients();
            for (PatientWebDto pat : listOfPatients) {
                if (pat.getIdentifier().equals(getSelectedRowIdentifier())) {
                    patient = pat;
                }
            }
       } catch (Exception e) {
           return ERROR;
       }
       return super.retrieve();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String update() {
        patient = null;
        try {
            loadListOfStudySites();
            loadListOfPatients();
            for (PatientWebDto pat : listOfPatients) {
                if (pat.getIdentifier().equals(getSelectedRowIdentifier())) {
                    patient = pat;
                }
            }
        } catch (Exception e) {
           patient = null;
            LOG.error("Error in PatientAction.update().", e);
        }
        if (patient == null) {
            addActionError("Error retrieving study subject info for update.");
            return execute();
        }
        return super.update();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String delete() throws RemoteException {
        StudySubjectDto dto = studySubjectSvc.get(IiConverter.convertToIi(getSelectedRowIdentifier()));
        dto.setStatusCode(CdConverter.convertStringToCd(deletedStatusCode));
        dto.getStatusDateRange().setHigh(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        studySubjectSvc.update(dto);
        return super.delete();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String add() throws RemoteException {
        if (!hasAllRequiredFields()) {
            loadListOfStudySites();
            return super.create();
        }
        PatientDto pat = patient.getPatientDto();
        StudySubjectDto ssub = patient.getStudySubjectDto();
        ssub.setStudyProtocolIdentifier(getSpIi());
        PerformedSubjectMilestoneDto psm = patient.getPerformedStudySubjectMilestoneDto();
        pat = patientSvc.create(pat);
        ssub.setPatientIdentifier(pat.getIdentifier());
        ssub = studySubjectSvc.create(ssub);
        psm.setStudySubjectIdentifier(ssub.getIdentifier());
        performedSubjectMilestoneSvc.create(psm);
        return super.add();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String edit() throws RemoteException {
        if (!hasAllRequiredFields()) {
            loadListOfStudySites();
            return super.update();
        }
        PatientDto pat = patient.getPatientDto();
        StudySubjectDto ssub = patient.getStudySubjectDto();
        PerformedSubjectMilestoneDto psm = patient.getPerformedStudySubjectMilestoneDto();
        pat = patientSvc.update(pat);
        ssub = studySubjectSvc.update(ssub);
        setRegistrationDate(psm);
        return super.edit();
    }

    /**
     * Method called from pop-up.  Loads selected disease.
     * @return result
     */
    public String displayDisease() {
        DiseaseWebDTO webDTO = new DiseaseWebDTO();
        webDTO.setDiseaseIdentifier(ServletActionContext.getRequest().getParameter("diseaseId"));
        if (webDTO == null || PAUtil.isEmpty(webDTO.getDiseaseIdentifier())) {
            webDTO = new DiseaseWebDTO();
        } else {
            Ii ii = IiConverter.convertToIi(webDTO.getDiseaseIdentifier());
            try {
                DiseaseDTO dto = diseaseSvc.get(ii);
                webDTO.setPreferredName(StConverter.convertToString(dto.getPreferredName()));
                webDTO.setDiseaseIdentifier(IiConverter.convertToString(dto.getIdentifier()));
            } catch (Exception e) {
                return ERROR;
            }
        }
        patient = new PatientWebDto();
        patient.setDiseasePreferredName(webDTO.getPreferredName());
        patient.setDiseaseIdentifier(Long.valueOf(webDTO.getDiseaseIdentifier()));
        return SUCCESS;
    }

    /**
     * @return the criteria
     */
    public SearchPatientsCriteriaWebDto getCriteria() {
        return criteria;
    }
    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(SearchPatientsCriteriaWebDto criteria) {
        this.criteria = criteria;
    }
    /**
     * @return the patient
     */
    public PatientWebDto getPatient() {
        return patient;
    }
    /**
     * @param patient the patient to set
     */
    public void setPatient(PatientWebDto patient) {
        this.patient = patient;
    }
    /**
     * @return the listOfCountries
     */
    public List<Country> getListOfCountries() {
        return listOfCountries;
    }
    /**
     * @return the listOfStudySites
     */
    public List<SearchStudySiteResultWebDto> getListOfStudySites() {
        return listOfStudySites;
    }

    private void loadListOfStudySites() throws RemoteException {
        List<SearchStudySiteResultDto> isoStudySiteList = null;
        if (listOfStudySites == null) {
            isoStudySiteList = searchStudySiteSvc.search(getSpIi(), getAuthorizedUser());
            listOfStudySites = SearchStudySiteResultWebDto.getWebList(isoStudySiteList);
        }
    }

    private void loadListOfCountries() throws RemoteException {
        if (listOfCountries == null) {
            listOfCountries = countrySvc.getCountries();
            for (Country c : listOfCountries) {
                if ("United States".equals(c.getName())) {
                    unitedStatesId = c.getId();
                }
            }
        }
    }

    private void loadListOfPatients() throws RemoteException {
        listOfPatients = new ArrayList<PatientWebDto>();
        for (SearchStudySiteResultWebDto ss : listOfStudySites) {
            List<StudySubjectDto> subList = studySubjectSvc.getByStudySite(IiConverter.convertToIi(ss.getSsIi()));
            for (StudySubjectDto sub : subList) {
                String statusCode = CdConverter.convertCdToString(sub.getStatusCode());
                if (!validStatusCodes.contains(statusCode)) {
                    continue;
                }
                PatientDto pat = patientSvc.get(sub.getPatientIdentifier());
                DiseaseDTO dto;
                try {
                    dto = diseaseSvc.get(sub.getDiseaseIdentifier());
                } catch (Exception e) {
                    dto = null;
                }
                List<PerformedSubjectMilestoneDto> smList =
                    performedSubjectMilestoneSvc.getByStudySubject(sub.getIdentifier());
                PerformedSubjectMilestoneDto psmDto = null;
                for (PerformedSubjectMilestoneDto sm : smList) {
                    if (!PAUtil.isTsNull(sm.getRegistrationDate())) {
                        psmDto = sm;
                    }
                }
                listOfPatients.add(new PatientWebDto(pat, sub, ss.getOrgName(), psmDto,
                        getListOfCountries(), dto));
            }
        }
        sortListOfPatients();
    }

    private EligibleGenderCode getGenderCriterion() {
        if (genderCriterion == null) {
            genderCriterion = EligibleGenderCode.BOTH;
            List<PlannedEligibilityCriterionDTO> pecList;
            try {
                pecList = plannedActivitySvc.getPlannedEligibilityCriterionByStudyProtocol(getSpIi());
                for (PlannedEligibilityCriterionDTO pec : pecList) {
                    if (PaServiceLocator.ELIG_CRITERION_NAME_GENDER.equals(
                            StConverter.convertToString(pec.getCriterionName()))) {
                        genderCriterion = EligibleGenderCode.getByCode(
                                CdConverter.convertCdToString(pec.getEligibleGenderCode()));
                    }
                }
            } catch (Exception e) {
                genderCriterion = EligibleGenderCode.BOTH;
            }
        }
        return genderCriterion;
    }

    private void setRegistrationDate(PerformedSubjectMilestoneDto dto) throws RemoteException {
        if (!PAUtil.isTsNull(dto.getRegistrationDate())) {
            if (!PAUtil.isIiNull(dto.getIdentifier())) {
                performedSubjectMilestoneSvc.update(dto);
            } else {
                performedSubjectMilestoneSvc.create(dto);
            }
        } else {
            if (!PAUtil.isIiNull(dto.getIdentifier())) {
                performedSubjectMilestoneSvc.delete(dto.getIdentifier());
            }
        }
    }

    @SuppressWarnings({ "PMD.ExcessiveMethodLength", "PMD.NPathComplexity" })
    private boolean hasAllRequiredFields() throws RemoteException {
        clearActionErrors();
        addActionErrorIfEmpty(patient, "Error inputing study subject data.");
        if (!hasActionErrors()) {
            addActionErrorIfEmpty(patient.getAssignedIdentifier(), "Study Subject ID is required.");
            addActionErrorIfEmpty(patient.getBirthDate(), "Birth date is required.");
            addActionErrorIfEmpty(patient.getGenderCode(), "Gender is required.");
            addActionErrorIfEmpty(patient.getRaceCode(), "Race is required.");
            addActionErrorIfEmpty(patient.getEthnicCode(), "Ethnicity is required.");
            addActionErrorIfEmpty(patient.getCountryIdentifier(), "Country is required.");
            addActionErrorIfEmpty(patient.getDiseaseIdentifier(), "Disease is required.");
            addActionErrorIfEmpty(patient.getStudySiteId(), "Participating site is required.");
        }
        if (!hasActionErrors()) {
            List<StudySubjectDto> allSss = studySubjectSvc.getByStudyProtocol(getSpIi());
            for (StudySubjectDto ss : allSss) {
                if (StConverter.convertToString(ss.getAssignedIdentifier()).equals(patient.getAssignedIdentifier())
                        && (patient.getStudySubjectId() == null
                             || !patient.getStudySubjectId().equals(IiConverter.convertToLong(ss.getIdentifier())))) {
                    addActionError("This Study Subject Id (" + patient.getAssignedIdentifier()
                            + ") has already been added to this study.");
                }
            }
            if (!PAUtil.isEmpty(patient.getRegistrationDate())) {
                Timestamp registration = AccrualUtil.yearMonthStringToTimestamp(patient.getRegistrationDate());
                Timestamp birth = AccrualUtil.yearMonthStringToTimestamp(patient.getBirthDate());
                if (birth.after(registration)) {
                    addActionError("The birth date must be less then or equal to the registration date.");
                }
            }
            if (unitedStatesId.equals(patient.getCountryIdentifier())) {
                addActionErrorIfEmpty(patient.getZip(), "Zip code is mandatory if country is United States.");
            } else {
                if (!PAUtil.isEmpty(patient.getZip())) {
                    addActionError("Zip code should only be entered if country is United States.");
                }
            }
            if (!PAUtil.isEmpty(patient.getRegistrationDate())) {
                Timestamp registrationDate = PAUtil.dateStringToTimestamp(patient.getRegistrationDate());
                if (registrationDate.after(getCutOffDate())) {
                    addActionError("Registration date must not be after the cut off date for the current submission ("
                            + PAUtil.normalizeDateString(getCutOffDate().toString()) + ").");
                }
            }
            if (!unitedStatesId.equals(patient.getCountryIdentifier())
                    && !PAUtil.isEmpty(patient.getPaymentMethodCode())) {
                addActionError("Method of payment should only be entered if country is United States.");
            }
            if (EligibleGenderCode.FEMALE.equals(getGenderCriterion())
                    && patient.getGenderCode().equals(PatientGenderCode.MALE.getCode())
                || EligibleGenderCode.MALE.equals(getGenderCriterion())
                        && patient.getGenderCode().equals(PatientGenderCode.FEMALE.getCode())) {
                addActionError("Gender must not be " + patient.getGenderCode() + " for subjects in this study.");
            }
        }
        return !hasActionErrors();
    }

    private void sortListOfPatients() {
        TreeMap<String, Long> ids = new TreeMap<String, Long>();
        for (PatientWebDto pat : listOfPatients) {
            ids.put(pat.getAssignedIdentifier(), pat.getStudySubjectId());
        }
        List<PatientWebDto> result = new ArrayList<PatientWebDto>();
        for (Long ssId : ids.values()) {
            for (PatientWebDto pat : listOfPatients) {
                if (ssId.equals(pat.getStudySubjectId()) && includePatient(pat)) {
                    result.add(pat);
                }
            }
        }
        listOfPatients = result;
    }

    private boolean includePatient(PatientWebDto pat) {
        if (criteria == null) {
            return true;
        }
        boolean result = true;
        if (!PAUtil.isEmpty(criteria.getAssignedIdentifier())
                && !pat.getAssignedIdentifier().contains(criteria.getAssignedIdentifier())) {
            result = false;
        }
        if (criteria.getStudySiteId() != null
                && !criteria.getStudySiteId().equals(pat.getStudySiteId())) {
            result = false;
        }
        if (!PAUtil.isEmpty(criteria.getBirthDate())
                && !criteria.getBirthDate().equals(pat.getBirthDate())) {
            result = false;
        }
        if (!PAUtil.isEmpty(criteria.getStatusCode())
                && !criteria.getStatusCode().equals(pat.getStatusCode())) {
            result = false;
        }
        return result;
    }
}
