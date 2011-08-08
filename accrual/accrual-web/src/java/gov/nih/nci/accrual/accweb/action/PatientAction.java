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

import gov.nih.nci.accrual.accweb.dto.util.DiseaseWebDTO;
import gov.nih.nci.accrual.accweb.dto.util.PatientWebDto;
import gov.nih.nci.accrual.accweb.dto.util.SearchPatientsCriteriaWebDto;
import gov.nih.nci.accrual.accweb.dto.util.SearchStudySiteResultWebDto;
import gov.nih.nci.accrual.dto.PerformedSubjectMilestoneDto;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.accrual.dto.util.SearchStudySiteResultDto;
import gov.nih.nci.accrual.dto.util.SearchTrialResultDto;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.SDCDiseaseDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

/**
 * Patient actions.
 * @author Hugh Reinhart
 */
public class PatientAction extends AbstractListEditAccrualAction<PatientWebDto> implements Preparable {
    private static final long serialVersionUID = -6820189447703204634L;
    private static String deletedStatusCode = FunctionalRoleStatusCode.TERMINATED.getCode();
    private static List<Country> listOfCountries = null;
    private static Long unitedStatesId = null;
    private Long organizationId;
    private Long studyProtocolId;

    private SearchPatientsCriteriaWebDto criteria;
    private List<SearchStudySiteResultWebDto> listOfStudySites = null;
    private PatientWebDto patient;
    private EligibleGenderCode genderCriterion;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        super.prepare();
        try {
            if (getStudyProtocolId() != null) {
                setSpIi(IiConverter.convertToStudyProtocolIi(getStudyProtocolId()));
                SearchTrialResultDto trialSummary = getSearchTrialSvc().getTrialSummaryByStudyProtocolIi(getSpIi());
                // put an entry in the session
                ServletActionContext.getRequest().getSession().setAttribute("trialSummary", trialSummary);
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() {
        if (isSessionTrialIndustrial()) {
            addActionError(getText("error.invalidaccess.industrial"));
            return "invalid";
        } 

        getListOfCountries();
        getListOfStudySites();
        return super.execute();
    }

    private boolean isSessionTrialIndustrial() {
        SearchTrialResultDto trialSummary = (SearchTrialResultDto) ServletActionContext.getRequest().getSession()
                .getAttribute("trialSummary");
        return BlConverter.convertToBoolean(trialSummary.getIndustrial());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String create() {
        getListOfStudySites();
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
            getListOfStudySites();
            loadDisplayList();
            for (PatientWebDto pat : getDisplayTagList()) {
                if (pat.getIdentifier().equals(getSelectedRowIdentifier())) {
                    patient = pat;
                }
            }
            if (patient != null) {
                patient.setRaceCode(removeUnderTabs(patient.getRaceCode()));
                patient.setEthnicCode(removeUnderTabs(patient.getEthnicCode()));
            }
            if (patient == null) {
                addActionError("Error retrieving study subject info.");
                return execute();
            }
        } catch (Exception e) {
            patient = null;
            LOG.error("Error in PatientAction.retrieve().", e);
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
            getListOfStudySites();
            loadDisplayList();
            for (PatientWebDto pat : getDisplayTagList()) {
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
        StudySubjectDto dto = getStudySubjectSvc().get(IiConverter.convertToIi(getSelectedRowIdentifier()));
        dto.setStatusCode(CdConverter.convertStringToCd(deletedStatusCode));
        dto.getStatusDateRange().setHigh(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        getStudySubjectSvc().update(dto);
        return super.delete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String add() throws RemoteException {
        businessRules();
        getListOfStudySites();
        if (hasActionErrors()) {
            return super.create();
        }
        getOrganizationIdentifier();
        PatientDto pat = patient.getPatientDto();
        pat.setOrganizationIdentifier(IiConverter.convertToIi(organizationId));
        StudySubjectDto ssub = patient.getStudySubjectDto();
        ssub.setStudyProtocolIdentifier(getSpIi());
        PerformedSubjectMilestoneDto psm = patient
                .getPerformedStudySubjectMilestoneDto();
        try {
            pat = getPatientSvc().create(pat);
        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
            return super.create();
        }
        ssub.setPatientIdentifier(pat.getIdentifier());
        ssub = getStudySubjectSvc().create(ssub);
        psm.setStudySubjectIdentifier(ssub.getIdentifier());
        setRegistrationDate(psm);
        return super.add();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String edit() throws RemoteException {
        businessRules();
        getListOfStudySites();
        if (hasActionErrors()) {
            return super.update();
        }
        getOrganizationIdentifier();
        PatientDto pat = patient.getPatientDto();
        pat.setOrganizationIdentifier(IiConverter.convertToIi(organizationId));
        StudySubjectDto ssub = patient.getStudySubjectDto();
        PerformedSubjectMilestoneDto psm = patient
                .getPerformedStudySubjectMilestoneDto();
        try {
            pat = getPatientSvc().update(pat);
        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
            return super.update();
        }
        ssub = getStudySubjectSvc().update(ssub);
        setRegistrationDate(psm);
        return super.edit();
    }

    private void getOrganizationIdentifier() {
        if (patient.getStudySiteId() != null) {
            for (SearchStudySiteResultWebDto ss : getListOfStudySites()) {
                if (Long.valueOf(ss.getSsIi()).equals(patient.getStudySiteId())) {
                    organizationId = Long.valueOf(ss.getOrgIi());
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadDisplayList() {
        setDisplayTagList(new ArrayList<PatientWebDto>());
        try {
            if (getCriteria().getStudySiteId() != null) {
                SearchStudySiteResultWebDto selectedSite = getSelectedStudySite(getCriteria().getStudySiteId());
                getDisplayTagList().addAll(convertToWebDTOs(getStudySubjects(getCriteria().getStudySiteId()), 
                        selectedSite.getOrgName()));
            } else {
                for (SearchStudySiteResultWebDto ss : getListOfStudySites()) {
                   getDisplayTagList().addAll(convertToWebDTOs(getStudySubjects(Long.valueOf(ss.getSsIi())), 
                           ss.getOrgName()));
                }
            }
        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
        }
    }
    
    private List<StudySubjectDto> getStudySubjects(Long studySiteId) throws PAException {
        FunctionalRoleStatusCode statusCode = FunctionalRoleStatusCode.getByCode(getCriteria().getStatusCode());
        Date birthDate = StringUtils.isEmpty(getCriteria().getBirthDate()) 
            ? null : AccrualUtil.yearMonthStringToTimestamp(getCriteria().getBirthDate());
        return getStudySubjectSvc().getStudySubjects(getCriteria().getAssignedIdentifier(), studySiteId, birthDate, 
                statusCode);
    }
    
    private List<PatientWebDto> convertToWebDTOs(List<StudySubjectDto> dtos, String orgName) throws RemoteException {
        List<PatientWebDto> results = new ArrayList<PatientWebDto>();
        for (StudySubjectDto dto : dtos) {
            PatientDto pat = getPatientSvc().get(dto.getPatientIdentifier());
            SDCDiseaseDTO disease;
            try {
                disease = getSDCDiseaseSvc().get(dto.getDiseaseIdentifier());
            } catch (Exception e) {
                disease = null;
            }
            PerformedSubjectMilestoneDto psmDto = getRegistrationDate(dto);
            results.add(new PatientWebDto(pat, dto, orgName, psmDto, getListOfCountries(), disease));
        }        
        return results;
    }
    
    private SearchStudySiteResultWebDto getSelectedStudySite(Long studySiteId) {
        for (SearchStudySiteResultWebDto dto : getListOfStudySites()) {
            if (StringUtils.equalsIgnoreCase(dto.getSsIi(), studySiteId.toString())) {
                return dto;
            }
        }
        return new SearchStudySiteResultWebDto(new SearchStudySiteResultDto());
    }
    
    /**
     * Method called from pop-up. Loads selected disease.
     *
     * @return result
     */
    public String displayDisease() {
        DiseaseWebDTO webDTO = new DiseaseWebDTO();
        webDTO.setDiseaseIdentifier(ServletActionContext.getRequest().getParameter("diseaseId"));
        if (StringUtils.isEmpty(webDTO.getDiseaseIdentifier())) {
            webDTO = new DiseaseWebDTO();
        } else {
            Ii ii = IiConverter.convertToIi(webDTO.getDiseaseIdentifier());
            try {
                SDCDiseaseDTO dto = getSDCDiseaseSvc().get(ii);
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
        if (criteria == null) {
            setCriteria(new SearchPatientsCriteriaWebDto());
        }
        return criteria;
    }

    /**
     * @param criteria
     *            the criteria to set
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
     * @param patient
     *            the patient to set
     */
    public void setPatient(PatientWebDto patient) {
        this.patient = patient;
    }

    /**
     * @return the listOfCountries
     */
    public List<Country> getListOfCountries() {
        if (listOfCountries == null) {
            try {
                listOfCountries = getCountrySvc().getCountries();
                for (Country c : listOfCountries) {
                    if ("United States".equals(c.getName())) {
                        unitedStatesId = c.getId();
                    }
                }
            } catch (RemoteException e) {
                LOG.error("Error loading countries.", e);
            }
        }
        return listOfCountries;
    }

    /**
     * @return the listOfStudySites
     */
    public List<SearchStudySiteResultWebDto> getListOfStudySites() {
        if (listOfStudySites == null) {
            try {
                RegistryUser ru = PaServiceLocator.getInstance().getRegistryUserService().getUser(
                        CaseSensitiveUsernameHolder.getUser());
                Ii ruIi = IiConverter.convertToIi(ru.getId());
                List<SearchStudySiteResultDto> isoStudySiteList = getSearchStudySiteSvc().search(getSpIi(), ruIi);
                listOfStudySites = new ArrayList<SearchStudySiteResultWebDto>();
                for (SearchStudySiteResultDto iso : isoStudySiteList) {
                    listOfStudySites.add(new SearchStudySiteResultWebDto(iso));
                }
            } catch (PAException e) {
                LOG.error("Error loading study sites.", e);
            }
        }
        return listOfStudySites;
    }

    private EligibleGenderCode getGenderCriterion() {
        if (genderCriterion == null) {
            genderCriterion = EligibleGenderCode.BOTH;
            try {
                List<PlannedEligibilityCriterionDTO> pecList = 
                    getPlannedActivitySvc().getPlannedEligibilityCriterionByStudyProtocol(getSpIi());
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

    private PerformedSubjectMilestoneDto getRegistrationDate(StudySubjectDto sub) {
        List<PerformedSubjectMilestoneDto> smList;
        try {
            smList = getPerformedActivitySvc().getPerformedSubjectMilestoneByStudySubject(sub.getIdentifier());
        } catch (RemoteException e) {
            LOG.error("Error in PatientAction.getRegistrationDate().", e);
            return null;
        }
        PerformedSubjectMilestoneDto psmDto = null;
        for (PerformedSubjectMilestoneDto sm : smList) {
            if (!ISOUtil.isTsNull(sm.getRegistrationDate())) {
                psmDto = sm;
            }
        }
        return psmDto;
    }

    private void setRegistrationDate(PerformedSubjectMilestoneDto dto) {
        try {
            if (!ISOUtil.isTsNull(dto.getRegistrationDate())) {
                if (!ISOUtil.isIiNull(dto.getIdentifier())) {
                    getPerformedActivitySvc().updatePerformedSubjectMilestone(dto);
                } else {
                    getPerformedActivitySvc().createPerformedSubjectMilestone(dto);
                }
            } else {
                if (!ISOUtil.isIiNull(dto.getIdentifier())) {
                    getPerformedActivitySvc().delete(dto.getIdentifier());
                }
            }
        } catch (Exception e) {
            LOG.error("Exception setting registration date.", e);
            addActionError(e.getLocalizedMessage());
        }
    }

    private void businessRules() {
        PatientWebDto.validate(patient, this);
        if (!hasActionErrors()) {
            validateNoPatientDuplicates();
            validateUnitedStatesRules();
            validateEligibilityCriteria();
            if (StringUtils.isEmpty(patient.getRegistrationDate())) {
                addActionError("Registration Date is required.");
            }
        }
    }

    private void validateNoPatientDuplicates() {
        List<StudySubjectDto> allSss = null;
        try {
            allSss = getStudySubjectSvc().getByStudyProtocol(getSpIi());
        } catch (RemoteException e) {
            LOG.error("Error in PatientAction.validateNoPatientDuplicates().", e);
            addActionError(e.getLocalizedMessage());
        }
        for (StudySubjectDto ss : allSss) {
            if (isDuplicatePatient(ss)) {
                addActionError("This Study Subject Id (" + patient.getAssignedIdentifier()
                        + ") has already been added to this study.");
            }
        }
    }

    private boolean isDuplicatePatient(StudySubjectDto ss) {
        return StConverter.convertToString(ss.getAssignedIdentifier()).equals(patient.getAssignedIdentifier())
                && (patient.getStudySubjectId() == null
                    || !patient.getStudySubjectId().equals(IiConverter.convertToLong(ss.getIdentifier())))
                && !deletedStatusCode.equals(CdConverter.convertCdToString(ss.getStatusCode()));
    }

    private void validateUnitedStatesRules() {
        if (unitedStatesId.equals(patient.getCountryIdentifier())) {
            if (StringUtils.isEmpty(patient.getZip())) {
                addActionError("Zip code is mandatory if country is United States.");
            }
        } else {
            if (StringUtils.isNotEmpty(patient.getZip())) {
                addActionError("Zip code should only be entered if country is United States.");
                if (StringUtils.isNotEmpty(patient.getPaymentMethodCode())) {
                    addActionError("Method of payment should only be entered if country is United States.");
                }
            }
        }
    }

    private void validateEligibilityCriteria() {
        if (EligibleGenderCode.FEMALE.equals(getGenderCriterion())
                && patient.getGenderCode().equals(PatientGenderCode.MALE.getCode())
            || EligibleGenderCode.MALE.equals(getGenderCriterion())
                && patient.getGenderCode().equals(PatientGenderCode.FEMALE.getCode())) {
            addActionError("Gender must not be " + patient.getGenderCode()
                    + " for subjects in this study.");
        }
    }

    private Set<String> removeUnderTabs(Set<String> codes) {
        Set<String> newCodes = new HashSet<String>();
        for (String rc : codes) {
            newCodes.add(rc.replace('_', ' '));
        }
        return newCodes;
    }

    private String removeUnderTabs(String code) {
        return code.replace('_', ' ');
    }

    /**
     * @param unitedStatesId the unitedStatesId to set
     */
    public void setUnitedStatesId(Long unitedStatesId) {
        PatientAction.unitedStatesId = unitedStatesId;
    }

    /**
     * @return the studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * @param studyProtocolId the studyProtocolId to set
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }
}
