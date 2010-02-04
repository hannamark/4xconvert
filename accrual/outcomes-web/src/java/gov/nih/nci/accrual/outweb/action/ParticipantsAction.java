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
package gov.nih.nci.accrual.outweb.action;

import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.accrual.outweb.dto.util.ParticipantWebDto;
import gov.nih.nci.accrual.outweb.dto.util.SearchParticipantCriteriaWebDto;
import gov.nih.nci.accrual.outweb.util.AccrualConstants;
import gov.nih.nci.accrual.outweb.util.SessionEnvManager;
import gov.nih.nci.accrual.outweb.util.WebUtil;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

/**
 * @author Hugh Reinhart
 * @since Sep 21, 2009
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.CyclomaticComplexity" })
public class ParticipantsAction extends AbstractListEditAccrualAction<ParticipantWebDto> {

    private static final long serialVersionUID = -6820189447703204634L;
    private static String deletedStatusCode = FunctionalRoleStatusCode.TERMINATED.getCode();
    private static List<Country> listOfCountries = null;
    static Long unitedStatesId = null;

    private SearchParticipantCriteriaWebDto criteria;
    private ParticipantWebDto participant;

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() {
        try {
            loadListOfCountries();
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
        }
        return super.execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String create() {
        participant = new ParticipantWebDto(getSpIi(), unitedStatesId);
        SessionEnvManager.putParticipantInSession(null, null);
        return super.create();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String retrieve() {
        try {
            participant = null;
            loadDisplayList();
            for (ParticipantWebDto pat : getDisplayTagList()) {
                if (pat.getIdentifier().getExtension().equals(getSelectedRowIdentifier())) {
                    participant = pat;
                    Set<String> raceCodes = removeUnderTabs(participant.getRaceCode());
                    participant.setRaceCode(raceCodes);
                    Cd ethniccd = removeUnderTabs(participant.getEthnicCode());
                    participant.setEthnicCode(ethniccd);
                    break;
                }
            }
            if (participant == null) {
                addActionError("Error retrieving study subject info.");
                return execute();
            }
       } catch (Exception e) {
           participant = null;
           LOG.error("Error in ParticipantAction.retrieve().", e);
       }
       SessionEnvManager.putParticipantInSession(participant.getStudySubjectIi(), participant.getAssignedIdentifier());
       return super.retrieve();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String update() {
        participant = null;
        try {
            loadDisplayList();
            for (ParticipantWebDto pat : getDisplayTagList()) {
                if (pat.getIdentifier().getExtension().equals(getSelectedRowIdentifier())) {
                    participant = pat;
                }
            }
        } catch (Exception e) {
            participant = null;
            LOG.error("Error in ParticipantAction.update().", e);
        }
        if (participant == null) {
            addActionError("Error retrieving study subject info for update.");
            return super.update();
        }
        SessionEnvManager.putParticipantInSession(participant.getStudySubjectIi(), participant.getAssignedIdentifier());
        return super.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String delete() throws RemoteException {
     try {
       List<StudySubjectDto> subList = studySubjectSvc.getOutcomes(getAuthorizedUser());
       for (StudySubjectDto dto : subList) {
        if (dto.getIdentifier().getExtension().equals(getSelectedRowIdentifier())) {
           dto.setStatusCode(CdConverter.convertStringToCd(deletedStatusCode));
           dto.getStatusDateRange().setHigh(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
           studySubjectSvc.update(dto);
           SessionEnvManager.putParticipantInSession(null, null);  
           break;
        }
       }
     } catch (RemoteException e) {
        addActionError("Not authorized to delete the Patient");
        return super.execute(); 
     }
     return super.delete(); 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String add() throws RemoteException {
        ParticipantWebDto.validate(participant, this);
        businessRules();
        if (hasActionErrors() || hasFieldErrors()) {
            return INPUT;
         }
        PatientDto pat = participant.getPatientDto();
        pat.setOrganizationIdentifier((Ii) SessionEnvManager.getAttr(AccrualConstants.SESSION_ATTR_SUBMITTING_ORG_II));
        StudySubjectDto ssub = participant.getStudySubjectDto(getAuthorizedUser());
        try {
            pat = patientSvc.create(pat);
        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
            return super.create();
        }
        ssub.setPatientIdentifier(pat.getIdentifier());
        ssub = studySubjectSvc.createOutcomes(ssub);
        SessionEnvManager.putParticipantInSession(ssub.getIdentifier(), ssub.getAssignedIdentifier());
        return super.add();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String edit() throws RemoteException {
        ParticipantWebDto.validate(participant, this);
        businessRules();
        if (hasActionErrors() || hasFieldErrors()) {
            return INPUT;
        }
        PatientDto pat = participant.getPatientDto();
        pat.setOrganizationIdentifier((Ii) SessionEnvManager.getAttr(AccrualConstants.SESSION_ATTR_SUBMITTING_ORG_II));
        StudySubjectDto ssub = participant.getStudySubjectDto(getAuthorizedUser());
        try {
            pat = patientSvc.update(pat);
        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
            return super.edit();
        }
        ssub = studySubjectSvc.update(ssub);
        SessionEnvManager.putParticipantInSession(ssub.getIdentifier(), ssub.getAssignedIdentifier());
        return super.edit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadDisplayList() {
        setDisplayTagList(new ArrayList<ParticipantWebDto>());
        try {
            List<StudySubjectDto> subList = studySubjectSvc.getOutcomes(getAuthorizedUser());
            for (StudySubjectDto sub : subList) {
                String statusCode = CdConverter.convertCdToString(sub.getStatusCode());
                if (!AccrualConstants.validStatusCodes.contains(statusCode)) {
                    continue;
                }
                PatientDto pat = patientSvc.get(sub.getPatientIdentifier());
                getDisplayTagList().add(new ParticipantWebDto(pat, sub, getListOfCountries()));
            }
        } catch (RemoteException e) {
            addActionError(e.getLocalizedMessage());
        }
        sortListOfParticipants();
    }

    /**
     * @return the criteria
     */
    public SearchParticipantCriteriaWebDto getCriteria() {
        if (criteria == null) {
            setCriteria(new SearchParticipantCriteriaWebDto());
        }
        return criteria;
    }
    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(SearchParticipantCriteriaWebDto criteria) {
        this.criteria = criteria;
    }
    /**
     * @return the participant
     */
    @VisitorFieldValidator(message = "> ")
    public ParticipantWebDto getParticipant() {
        return participant;
    }
    /**
     * @param participant the participant to set
     */
    public void setParticipant(ParticipantWebDto participant) {
        this.participant = participant;
    }
    /**
     * @return the listOfCountries
     */
    public List<Country> getListOfCountries() {
        return listOfCountries;
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

    private void businessRules() {
        if (!hasActionErrors()) {
            validateUnitedStatesRules();
            validateNoPatientDuplicates();
        }
    }

    private void validateUnitedStatesRules() {
        if (!unitedStatesId.equals(Long.parseLong(participant.getCountryIdentifier().getExtension()))
                && !PAUtil.isEmpty(participant.getPaymentMethodCode().getCode())) {
            addActionError("Method of payment should only be entered if country is United States.");
        }
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private void validateNoPatientDuplicates() {
        List<StudySubjectDto> allSss = null;
        StudySubjectDto ssub = participant.getStudySubjectDto(getAuthorizedUser());
        try {
            allSss = studySubjectSvc.getOutcomes(getAuthorizedUser());
        } catch (RemoteException e) {
            LOG.error("Error in PatientAction.validateNoPatientDuplicates().", e);
            addActionError(e.getLocalizedMessage());
        }
        
        for (StudySubjectDto ss : allSss) {
            if (ss.getAssignedIdentifier().getValue().equals(ssub.getAssignedIdentifier().getValue())
                    && (PAUtil.isIiNull(ssub.getIdentifier())
                        || !(ssub.getIdentifier().getExtension().equals(ss.getIdentifier().getExtension())))
                    && !deletedStatusCode.equals(CdConverter.convertCdToString(ss.getStatusCode()))) {
                addActionError("This Patient Id (" + ssub.getAssignedIdentifier().getValue()
                        + ") has already been added to this study.");
                break;
            }
        }
    }
    private void sortListOfParticipants() {
        TreeMap<String, Ii> ids = new TreeMap<String, Ii>();
        for (ParticipantWebDto pat : getDisplayTagList()) {
           String sortString = StConverter.convertToString(pat.getAssignedIdentifier()) 
             + IiConverter.convertToString(pat.getStudySubjectIi());
            ids.put(sortString, pat.getStudySubjectIi());
        }
        List<ParticipantWebDto> result = new ArrayList<ParticipantWebDto>();
        for (Ii ssId : ids.values()) {
            for (ParticipantWebDto pat : getDisplayTagList()) {
                if (ssId.equals(pat.getStudySubjectIi()) && includeParticipant(pat)) {
                    result.add(pat);
                }
            }
        }
        setDisplayTagList(result);
    }

    private boolean includeParticipant(ParticipantWebDto pat) {
        boolean result = true;
        if (!WebUtil.stringContains(StConverter.convertToString(pat.getAssignedIdentifier()),
                getCriteria().getAssignedIdentifier())) {
            result = false;
        }
        if (!WebUtil.stringEquals(pat.getBirthDate(), getCriteria().getBirthDate())) {
            result = false;
        }
        return result;
    }
    private Set<String> removeUnderTabs(Set<String> codes) {
        Set<String> newCodes = new HashSet<String>();
        for (String rc : codes) {
          newCodes.add(rc.replace('_', ' '));
        }
        return newCodes;
       }
       
       private Cd removeUnderTabs(Cd code) {
         Cd newCode = new Cd();
          newCode.setCode(code.getCode().replace('_', ' '));
          newCode.setDisplayName(code.getDisplayName());
         return newCode;
       }
    
}
