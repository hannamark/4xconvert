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
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.outcomes.svc.exception.OutcomesFieldException;
import gov.nih.nci.outcomes.svc.util.SvcConstants;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jboss.security.SecurityAssociation;

import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

/**
 * @author Hugh Reinhart
 * @since Sep 21, 2009
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.CyclomaticComplexity" })
public class ParticipantsAction extends AbstractListEditAccrualAction<ParticipantWebDto> {

    private static final long serialVersionUID = -6820189447703204634L;
    private static final Logger LOG = Logger.getLogger(ParticipantsAction.class);
    private static List<Country> listOfCountries = null;

    private SearchParticipantCriteriaWebDto criteria;
    private ParticipantWebDto participant;
    private boolean poAccessed = false;
    private String uuid;

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
        participant = new ParticipantWebDto();
        SessionEnvManager.putParticipantInSession(null, null);
        return super.create();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String retrieve() {
       loadParticipant(getSelectedRowIdentifier());
       if (hasErrors()) {
           return execute();
       }
       Set<String> raceCodes = removeUnderTabs(participant.getRaceCodes());
       participant.setRaceCodes(raceCodes);
       Cd ethniccd = removeUnderTabs(participant.getEthnicCode());
       participant.setEthnicCode(ethniccd);

       SessionEnvManager.putParticipantInSession(participant.getIdentifier(), participant.getAssignedIdentifier());
       return super.retrieve();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String update() {
        loadParticipant(getSelectedRowIdentifier());
        if (hasErrors()) {
            return execute();
        }
        SessionEnvManager.putParticipantInSession(participant.getIdentifier(), participant.getAssignedIdentifier());
        return super.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String delete() throws RemoteException {
     try {
         PatientSvcDto dto = new PatientSvcDto();
         dto.setIdentifier(IiConverter.convertToIi(getSelectedRowIdentifier()));
         dto.setAction(SvcConstants.DELETE);
         outcomesSvc.write(dto);
         SessionEnvManager.putParticipantInSession(null, null);
     } catch (OutcomesException e) {
        addActionError(e.getLocalizedMessage());
        return super.execute();
     }
     return super.delete();
    }
    
    /**
     * {@inheritDoc}
     * @throws Exception 
     */
    public String healthRecord() {
        try {
            String username = ServletActionContext.getRequest().getUserPrincipal().getName().split("CN=")[1];
            String passwd = getCredential();
            String id = getSelectedRowIdentifier();
            setUuid(phrService.getCDAIdentifierMessage(id, username, passwd));
            return "phrForward";
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
        }
        return execute();
    }

    /**
     * Get the users credential.
     * @return credential in String format
     */
    protected String getCredential() {
        return SecurityAssociation.getCredential().toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String add() throws RemoteException {
        PatientSvcDto result;
        PatientSvcDto svcDto = participant.getSvcDto();
        svcDto.setAction(SvcConstants.CREATE);
        try {
            getAuthorizedUser();  // to store before PO service call
            poAccessed = true;
            result = outcomesSvc.write(svcDto);
        } catch (OutcomesFieldException e) {
            addFieldError(ParticipantWebDto.svcFieldToWebField(e.getField()), e.getLocalizedMessage());
            return INPUT;
        } catch (OutcomesException e) {
            LOG.error("Outcomes exception calling outcomesSvc.put()", e);
            addActionError(e.getLocalizedMessage());
            return INPUT;
        } catch (Exception e) {
            LOG.error("Unexpected exception calling outcomesSvc.put()", e);
            addActionError(e.getLocalizedMessage());
            return INPUT;
        }
        SessionEnvManager.putParticipantInSession(result.getIdentifier(), result.getAssignedIdentifier());
        return super.add();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String edit() throws RemoteException {
        PatientSvcDto result;
        PatientSvcDto svcDto = participant.getSvcDto();
        svcDto.setAction(SvcConstants.UPDATE);
        try {
            getAuthorizedUser();  // to store before PO service call
            poAccessed = true;
            result = outcomesSvc.write(svcDto);
        } catch (OutcomesFieldException e) {
            addFieldError(ParticipantWebDto.svcFieldToWebField(e.getField()), e.getLocalizedMessage());
            return INPUT;
        } catch (OutcomesException e) {
            LOG.error("Outcomes exception calling outcomesSvc.put()", e);
            addActionError(e.getLocalizedMessage());
            return INPUT;
        } catch (Exception e) {
            LOG.error("Unexpected exception calling outcomesSvc.put()", e);
            addActionError(e.getLocalizedMessage());
            return INPUT;
        }
        SessionEnvManager.putParticipantInSession(result.getIdentifier(), result.getAssignedIdentifier());
        return super.edit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadDisplayList() {
        if (poAccessed) {
            loadDisplayListLowLevel();
            return;
        }
        setDisplayTagList(new ArrayList<ParticipantWebDto>());
        try {
            List<PatientSvcDto> pList = outcomesSvc.get(new PatientSvcDto());
            for (PatientSvcDto p : pList) {
                ParticipantWebDto wp = new ParticipantWebDto(p);
                getDisplayTagList().add(wp);
            }
        } catch (OutcomesException e) {
            addActionError(e.getLocalizedMessage());
        }
        sortListOfParticipants();
    }


    /**
     * This method is required to load the list after calls to PO have changed security
     * context.  getAuthorizedUser() must be called prior to PO call.
     */
    private void loadDisplayListLowLevel() {
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
        }
    }

    private void loadParticipant(String id) {
        participant = null;
        if (StringUtils.isNotEmpty(id)) {
            PatientSvcDto pat = new PatientSvcDto();
            pat.setIdentifier(IiConverter.convertToIi(id));
            List<PatientSvcDto> patList;
            try {
                patList = outcomesSvc.get(pat);
                if (!patList.isEmpty()) {
                    participant = new ParticipantWebDto(patList.get(0));
                    loadCountryName();
                }
            } catch (OutcomesException e) {
                addActionError(e.getLocalizedMessage());
            }
        }
        if (participant == null) {
            addActionError("Error retrieving patient information.");
        }
    }

    private void sortListOfParticipants() {
        TreeMap<String, Ii> ids = new TreeMap<String, Ii>();
        for (ParticipantWebDto pat : getDisplayTagList()) {
           String sortString = StConverter.convertToString(pat.getAssignedIdentifier())
             + IiConverter.convertToString(pat.getIdentifier());
            ids.put(sortString, pat.getIdentifier());
        }
        List<ParticipantWebDto> result = new ArrayList<ParticipantWebDto>();
        for (Ii ssId : ids.values()) {
            for (ParticipantWebDto pat : getDisplayTagList()) {
                if (ssId.equals(pat.getIdentifier()) && includeParticipant(pat)) {
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
        if (!WebUtil.stringEquals(pat.getBirth(), getCriteria().getBirthDate())) {
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

    private void loadCountryName() {
        if (participant != null && !PAUtil.isStNull(participant.getCountryAlpha3())) {
            String alpha3 = StConverter.convertToString(participant.getCountryAlpha3());
            for (Country country : getListOfCountries()) {
                if (alpha3.equals(country.getAlpha3())) {
                    participant.setCountryName(StConverter.convertToSt(country.getName()));
                }
            }
        }
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
