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
import gov.nih.nci.accrual.outweb.dto.util.PersonNameConverter;
import gov.nih.nci.accrual.outweb.dto.util.PersonNameImpl;
import gov.nih.nci.accrual.outweb.dto.util.UserAccountWebDto;
import gov.nih.nci.accrual.outweb.util.AccrualConstants;
import gov.nih.nci.accrual.outweb.util.SessionEnvManager;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.outcomes.svc.dto.UserSvcDto;
import gov.nih.nci.outcomes.svc.util.SvcConstants;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Lisa Kelley
 *
 */
public class UserAccountAction extends AccountSupportAction {

    private static final long serialVersionUID = 1L;
    private UserAccountWebDto userAccount = new UserAccountWebDto();
    private String userAction = "";
    private static final String UNAVAIL_ORG_MSG = "This Organization is no longer available. Please select another.";
    private static final String UNAVAIL_PERSON_MSG = "This Person is no longer available. Please select another.";
    private boolean patients = false;

 
    /**
     * getTreatmentSite.
     * @param poOrganizationId the poOrganizationId
     * @return String
     * @throws RemoteException exception
     */
    public static String getTreatmentSite(Ii poOrganizationId) throws RemoteException {
        if (poOrganizationId == null) {
            return null;
        }
        OrganizationDTO organization;
        try {
            organization = PoRegistry.getOrganizationEntityService().getOrganization(poOrganizationId);
            return organization.getName().getPart().get(0).getValue();
        } catch (NullifiedEntityException e) {
            LOG.equals("NullifiedEntityException" + e.getMessage());
        }
        return UNAVAIL_ORG_MSG;
    }

    /**
     * getPhysician.
     * @param poPersonId the poPersonId
     * @return String
     * @throws RemoteException exception
     */
    public static String getPhysician(Ii poPersonId) throws RemoteException {
        if (poPersonId == null) {
            return null;
        }
        PersonNameImpl name = new PersonNameImpl("", null, "");

        try {
            PersonDTO person = PoRegistry.getPersonEntityService().getPerson(poPersonId);
            new PersonNameConverter().convert(person.getName().getPart(), name);
        } catch (NullifiedEntityException e) {
            LOG.equals("NullifiedEntityException" + e.getMessage());
            name.setFirstName(UNAVAIL_PERSON_MSG);
        }
        
        return name.getLastFirst();
    }

    /**
     * update user account information.
     * @return String
     */
    public String update() {
        String actionResult =  null;
        getUserAccount().setAction(SvcConstants.UPDATE);
        getUserAccount().validate(this);
        try {
            if (hasFieldErrors() || hasActionErrors()) {
                checkPatients(StConverter.convertToString(getUserAccount().getIdentity()));
                return ActionSupport.INPUT;
            }
            // update the user
            UserSvcDto svcDto = userSvc.updateUser(getUserAccount().getSvcDto());
            setUserAccount(convertToWebDto(svcDto));
            SessionEnvManager.setAttr(AccrualConstants.SESSION_ATTR_SUBMITTER_NAME,
                StConverter.convertToString(getUserAccount().getLastName()) + ", " 
                + StConverter.convertToString(getUserAccount().getFirstName()));
            SessionEnvManager.setAttr(AccrualConstants.SESSION_ATTR_PHYSICIAN_NAME,
                getPhysician(getUserAccount().getPhysicianIdentifier()));
            SessionEnvManager.setAttr(AccrualConstants.SESSION_ATTR_SUBMITTING_ORG_II,
                    getUserAccount().getTreatmentSiteIdentifier());
            SessionEnvManager.setAttr(AccrualConstants.SESSION_ATTR_SUBMITTING_ORG_NAME,
                getTreatmentSite(getUserAccount().getTreatmentSiteIdentifier()));

            userAction = "updateAccount";
            actionResult = ActionSupport.INPUT;
            checkPatients(StConverter.convertToString(getUserAccount().getIdentity()));
        } catch (Exception e) {
            LOG.error("error while updating user info", e);
            actionResult = ActionSupport.ERROR;
        }

        return actionResult;
    }

    /**
     * Show My Account Page.
     * @return String
     */
    public String start() {
        String loginName = ServletActionContext.getRequest().getRemoteUser();
        try {
            UserSvcDto userSvcDto =  userSvc.getUser();

            if (userSvcDto != null) {
                setUserAccount(convertToWebDto(userSvcDto));
                checkPatients(loginName);
            }
        } catch (Exception e) {
            LOG.error("error while displaying My Account page for user :" + loginName, e);
            return ERROR;
        }

        return ActionSupport.INPUT;
    }

    private void checkPatients(String loginName) throws RemoteException {
        //List<StudySubjectDto> subList = studySubjectSvc.getOutcomes(getAuthorizedUser());
        List<StudySubjectDto> subList = studySubjectSvc.getOutcomes(StConverter.convertToSt(loginName));
        List<StudySubjectDto> patientsList = new ArrayList<StudySubjectDto>();
        for (StudySubjectDto sub : subList) {
            String statusCode = CdConverter.convertCdToString(sub.getStatusCode());
            if (!AccrualConstants.validStatusCodes.contains(statusCode)) {
                continue;
            }
            patientsList.add(sub);
        }
        if (!patientsList.isEmpty()) {
            setPatients(true);
        }
    }


    /**
     * @return the userAccount
     */
    public UserAccountWebDto getUserAccount() {
        return userAccount;
    }

    /**
     * @param userAccount the userAccount to set
     */
    public void setUserAccount(UserAccountWebDto userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * @return the userAction
     */
    public String getUserAction() {
        return userAction;
    }

    /**
     * @param userAction the userAction to set
     */
    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    /**
     * @return patients
     */
    public boolean isPatients() {
        return patients;
    }

    /**
     * @param patients patients
     */
    public void setPatients(boolean patients) {
        this.patients = patients;
    }
    /**
     * @param userSvcDto - the dto
     * @throws RemoteException - error
     */
    private UserAccountWebDto convertToWebDto(UserSvcDto userSvcDto)
            throws RemoteException {
        UserAccountWebDto usrAcct = new UserAccountWebDto();
        usrAcct.setIdentifier(userSvcDto.getIdentifier());
        usrAcct.setIdentity(userSvcDto.getIdentity());
        usrAcct.setPassword(userSvcDto.getPassword());
        usrAcct.setRetypePassword(userSvcDto.getPassword());
        usrAcct.setFirstName(userSvcDto.getFirstName());
        usrAcct.setMiddleName(userSvcDto.getMiddleName());
        usrAcct.setLastName(userSvcDto.getLastName());
        usrAcct.setAddress(userSvcDto.getAddress());
        usrAcct.setCity(userSvcDto.getCity());
        usrAcct.setState(userSvcDto.getState());
        usrAcct.setCountry(userSvcDto.getCountry());
        usrAcct.setPostalCode(userSvcDto.getPostalCode());
        usrAcct.setPhone(userSvcDto.getPhone());
        usrAcct.setAffiliateOrg(userSvcDto.getAffiliateOrg());
        usrAcct.setPrsOrg(userSvcDto.getPrsOrg());
        String treatmentSite = getTreatmentSite(userSvcDto.getTreatmentSiteIdentifier());
        if (treatmentSite == null || treatmentSite.equals(UNAVAIL_ORG_MSG)) {
            usrAcct.setTreatmentSiteIdentifier(null);
        } else {
            usrAcct.setTreatmentSiteIdentifier(userSvcDto.getTreatmentSiteIdentifier());
            usrAcct.setTreatmentSite(StConverter.convertToSt(treatmentSite));
        }
        String physician = getPhysician(userSvcDto.getPhysicianIdentifier());
        if (physician == null || physician.equals(UNAVAIL_PERSON_MSG)) {
            usrAcct.setPhysician(null);
        } else {
            usrAcct.setPhysicianIdentifier(userSvcDto.getPhysicianIdentifier());
            usrAcct.setPhysician(StConverter.convertToSt(physician));
        }
        usrAcct.setEmail(userSvcDto.getEmail());
        return usrAcct;
    }
}