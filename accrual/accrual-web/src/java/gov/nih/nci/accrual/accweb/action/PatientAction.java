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
import gov.nih.nci.accrual.dto.SearchSSPCriteriaDto;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.accrual.dto.util.SearchStudySiteResultDto;
import gov.nih.nci.accrual.dto.util.SearchTrialResultDto;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.PerformedActivity;
import gov.nih.nci.pa.domain.PerformedSubjectMilestone;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.iso.dto.ICD9DiseaseDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.SDCDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

/**
 * Patient actions.
 * @author Hugh Reinhart
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveClassLength" })
public class PatientAction extends AbstractListEditAccrualAction<PatientWebDto> implements Preparable {
    private static final long serialVersionUID = -6820189447703204634L;
    private static List<Country> listOfCountries = null;
    private static Long unitedStatesId = null;
    private Long organizationId;
    private Long studyProtocolId;

    private SearchPatientsCriteriaWebDto criteria;
    private List<SearchStudySiteResultWebDto> listOfStudySites = null;
    private PatientWebDto patient;
    private EligibleGenderCode genderCriterion;
    private final PatientHelper helper = new PatientHelper(this);
    private String deleteReason;
    private static List<String> reasonsList = new ArrayList<String>();
    
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

    /**
     * @return success
     */
    public String getDeleteReasons() {
        if (reasonsList.isEmpty()) {
            String deleteReasons;
            try {
                deleteReasons = getLookupTableSvc().getPropertyValue("subject.delete.reasons");
                StringTokenizer st = new StringTokenizer(deleteReasons, ",");
                while (st.hasMoreTokens()) {
                    String reason = st.nextToken();
                    reasonsList.add(reason);
                }
            } catch (PAException e) {
                LOG.error("Error loading delete reasons.", e);
            }
        }
        return "deleteReason";
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
            loadPatient(getSelectedRowIdentifier());
            if (patient != null) {
                patient.setRaceCode(getParsedCode(patient.getRaceCode()));
                patient.setEthnicCode(getParsedCode(patient.getEthnicCode()));
            } else {
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
        try {
            loadPatient(getSelectedRowIdentifier());
            if (patient == null) {
                addActionError("Error retrieving study subject info for update.");
                return execute();
            }
        } catch (Exception e) {
            patient = null;
            LOG.error("Error in PatientAction.update().", e);
        }
        return super.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String delete() throws PAException {
        try {
            getSubjectAccrualSvc().deleteSubjectAccrual(IiConverter.convertToIi(getSelectedRowIdentifier()), 
                    getDeleteReason());
        } catch (PAException e) {
            LOG.error("Error in PatientAction.delete().", e);
        }
        return super.delete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String add() throws PAException {
        boolean checkDisease = checkDiseaseIsNeeded();
        helper.validate(checkDisease);
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
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return super.create();
        }
        ssub.setPatientIdentifier(pat.getIdentifier());
        ssub.setSubmissionTypeCode(CdConverter.convertToCd(AccrualSubmissionTypeCode.UI));
        ssub = getStudySubjectSvc().create(ssub);
        psm.setStudySubjectIdentifier(ssub.getIdentifier());
        setRegistrationDate(psm);
        return super.add();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String edit() throws PAException {
        boolean checkDisease = checkDiseaseIsNeeded();
        helper.validate(checkDisease);
        getListOfStudySites();
        if (hasActionErrors()) {
            return super.update();
        }
        getOrganizationIdentifier();
        PatientDto pat = patient.getPatientDto();
        pat.setOrganizationIdentifier(IiConverter.convertToIi(organizationId));
        StudySubjectDto ssub = patient.getStudySubjectDto();
        ssub.setSubmissionTypeCode(CdConverter.convertToCd(AccrualSubmissionTypeCode.UI));
        PerformedSubjectMilestoneDto psm = patient
                .getPerformedStudySubjectMilestoneDto();
        try {
            pat = getPatientSvc().update(pat);
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return super.update();
        }
        ssub = getStudySubjectSvc().update(ssub);
        setRegistrationDate(psm);
        return super.edit();
    }

    private void loadPatient(String id) throws PAException {
        if (id == null) {
            patient = null;
            return;
        }
        StudySubject ss = getStudySubjectSvc().get(Long.valueOf(id));
        ss.getPerformedActivities();
        patient = new PatientWebDto(ss);
    }

    private boolean checkDiseaseIsNeeded() throws PAException {
        boolean checkDisease = true;
        StudyProtocolDTO spDto = PaServiceLocator.getInstance().getStudyProtocolService()
               .getStudyProtocol(IiConverter.convertToStudyProtocolIi(patient.getStudyProtocolId()));
        if (PrimaryPurposeCode.getByCode(CdConverter.convertCdToString(spDto.getPrimaryPurposeCode()))
                .equals(PrimaryPurposeCode.PREVENTION)) {
            checkDisease = false;
        }
        return checkDisease;
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
        Map<Long, String> ssidToOrgNameMap = new HashMap<Long, String>();
        try {
            SearchSSPCriteriaDto searchCriteria = new SearchSSPCriteriaDto();
            searchCriteria.setPatientBirthDate(AccrualUtil.yearMonthStringToTimestamp(getCriteria().getBirthDate()));
            searchCriteria.setStudySubjectAssignedIdentifier(getCriteria().getAssignedIdentifier());
            searchCriteria.setStudySubjectStatusCode(FunctionalRoleStatusCode.ACTIVE);
            if (getCriteria().getStudySiteId() != null) {
                searchCriteria.getStudySiteIds().add(getCriteria().getStudySiteId());
                SearchStudySiteResultWebDto selectedSite = getSelectedStudySite(getCriteria().getStudySiteId());
                ssidToOrgNameMap.put(getCriteria().getStudySiteId(), selectedSite.getOrgName());
            } else {
                for (SearchStudySiteResultWebDto ss : getListOfStudySites()) {
                    searchCriteria.getStudySiteIds().add(Long.valueOf(ss.getSsIi()));
                    ssidToOrgNameMap.put(Long.valueOf(ss.getSsIi()), ss.getOrgName());
                }
            }
            List<StudySubject> sspr = getStudySubjectSvc().search(searchCriteria);
            addItemsToList(sspr, ssidToOrgNameMap);
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
        }
    }
    
    private void addItemsToList(List<StudySubject> sspr,  Map<Long, String> ssidToOrgNameMap) throws PAException {
        for (StudySubject ss : sspr) {
            PatientWebDto webDto = new PatientWebDto();
            webDto.setIdentifier(ss.getId().toString());
            webDto.setAssignedIdentifier(ss.getAssignedIdentifier());
            for (PerformedActivity pa : ss.getPerformedActivities()) {
                if (pa instanceof PerformedSubjectMilestone) {
                    PerformedSubjectMilestone psm = (PerformedSubjectMilestone) pa;
                    webDto.setRegistrationDate(PAUtil.normalizeDateString(psm.getRegistrationDate().toString()));
                }
            }
            webDto.setOrganizationName(ssidToOrgNameMap.get(ss.getStudySite().getId()));
            webDto.setDateLastUpdated(DateFormatUtils.format(ss.getDateLastUpdated(), "MM/dd/yyyy HH:mm"));
            getDisplayTagList().add(webDto);
        }
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
    public String getDisplayDisease() {
        DiseaseWebDTO webDTO = new DiseaseWebDTO();
        webDTO.setDiseaseIdentifier(ServletActionContext.getRequest().getParameter("diseaseId"));
        webDTO.setType(ServletActionContext.getRequest().getParameter("dType"));
        if (StringUtils.isEmpty(webDTO.getDiseaseIdentifier())) {
            webDTO = new DiseaseWebDTO();
        } else {
            Ii ii = IiConverter.convertToIi(webDTO.getDiseaseIdentifier());
            try {
                if (webDTO.getType().equals(DiseaseWebDTO.SDC_TYPE)) {
                    SDCDiseaseDTO dto = getSDCDiseaseSvc().get(ii);
                    webDTO.setPreferredName(StConverter.convertToString(dto.getPreferredName()));
                    webDTO.setDiseaseIdentifier(IiConverter.convertToString(dto.getIdentifier()));
                } else {
                    ICD9DiseaseDTO dto = getIcd9DiseaseSvc().get(ii);
                    webDTO.setPreferredName(StConverter.convertToString(dto.getPreferredName()));
                    webDTO.setDiseaseIdentifier(IiConverter.convertToString(dto.getIdentifier()));
                }
            } catch (Exception e) {
                return ERROR;
            }
        }
        setPatientDisease(webDTO);
        return "displayDiseases";
    }

    void setPatientDisease(DiseaseWebDTO webDTO) {
        patient = new PatientWebDto();
        if (webDTO.getType().equals(DiseaseWebDTO.SDC_TYPE)) {
            patient.setIcd9DiseasePreferredName(null);
            patient.setIcd9DiseaseIdentifier(null);
            patient.setSdcDiseasePreferredName(webDTO.getPreferredName());
            patient.setSdcDiseaseIdentifier(Long.valueOf(webDTO.getDiseaseIdentifier()));

        } else {
            patient.setSdcDiseasePreferredName(null);
            patient.setSdcDiseaseIdentifier(null);
            patient.setIcd9DiseasePreferredName(webDTO.getPreferredName());
            patient.setIcd9DiseaseIdentifier(Long.valueOf(webDTO.getDiseaseIdentifier()));
        }
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
            } catch (PAException e) {
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

    EligibleGenderCode getGenderCriterion() {
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

    PerformedSubjectMilestoneDto getRegistrationDate(StudySubjectDto sub) {
        List<PerformedSubjectMilestoneDto> smList;
        try {
            smList = getPerformedActivitySvc().getPerformedSubjectMilestoneByStudySubject(sub.getIdentifier());
        } catch (PAException e) {
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

    private Set<String> getParsedCode(Set<String> codes) {
        Set<String> newCodes = new HashSet<String>();
        for (String rc : codes) {
            newCodes.add(rc.replace('_', ' '));
        }
        return newCodes;
    }

    private String getParsedCode(String code) {
        return code.replace('_', ' ');
    }

    /**
     * @return the unitedStatesId
     */
    public Long getUnitedStatesId() {
        return PatientAction.unitedStatesId;
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

    /**
     * @return the deleteReason
     */
    public String getDeleteReason() {
        return deleteReason;
    }

    /**
     * @param deleteReason the deleteReason to set
     */
    public void setDeleteReason(String deleteReason) {
         this.deleteReason = deleteReason;
    }

    /**
     * @return reasonsList
     */
    public static List<String> getReasonsList() {
        return reasonsList;
    }
}
