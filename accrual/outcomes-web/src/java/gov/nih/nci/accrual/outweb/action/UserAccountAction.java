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
import gov.nih.nci.accrual.dto.UserDto;
import gov.nih.nci.accrual.outweb.dto.util.PhysicianWebDTO;
import gov.nih.nci.accrual.outweb.dto.util.TreatmentSiteWebDTO;
import gov.nih.nci.accrual.outweb.dto.util.UserAccountWebDTO;
import gov.nih.nci.accrual.outweb.mail.MailManager;
import gov.nih.nci.accrual.outweb.util.AccrualConstants;
import gov.nih.nci.accrual.outweb.util.EncoderDecoder;
import gov.nih.nci.accrual.outweb.util.SessionEnvManager;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Lisa Kelley
 *
 */
@SuppressWarnings({ "PMD" })
public class UserAccountAction extends AbstractAccrualAction {

    private static final long serialVersionUID = 1L;
    private UserAccountWebDTO userAccount = new UserAccountWebDTO();
    private String userAction = "";
    private static final String CREATE = "create";
    private String lookupType;
    private TreatmentSiteWebDTO treatmentSiteSearchCriteria = new TreatmentSiteWebDTO();
    private List<TreatmentSiteWebDTO> treatmentSites = new ArrayList<TreatmentSiteWebDTO>();
    private PhysicianWebDTO physicianSearchCriteria = new PhysicianWebDTO();
    private List<PhysicianWebDTO> physicians = new ArrayList<PhysicianWebDTO>();
    private static final String UNAVAIL_ORG_MSG = "This Organization is no longer available. Please select another.";
    private static final String UNAVAIL_PERSON_MSG = "This Person is no longer available. Please select another.";
    private boolean patients = false;

    /**
     * @return success
     */
    @Override
    public String execute() {
        return SUCCESS;
    }

    /**
     * Send e-mail to the registering user.
     * @return String
     */
    public String request() {
        UserAccountWebDTO.validate(userAccount, this, false);
        if (hasFieldErrors()) {
            return ActionSupport.INPUT;
        }

        try {
            final MailManager mailManager = new MailManager();
            mailManager.sendConfirmationMail(userAccount.getLoginName(), userAccount.getPassword());
            LOG.info(" sending email to " + userAccount.getLoginName());
        } catch (Exception e) {
            LOG.error("error while sending e-mail");
            return ERROR;
        }

        return "requestConfirmation";
    }

    /**
     * activate user.
     * @return String
     */
    public String activate() {
        String encodedLoginName = ServletActionContext.getRequest().getParameter("loginName");
        String encodedPassword  = ServletActionContext.getRequest().getParameter("password");
        String loginName = null;
        String password = null;

        if (encodedLoginName != null) {
            loginName = EncoderDecoder.decodeString(encodedLoginName);
        }
        if (encodedPassword != null) {
            password = EncoderDecoder.decodeString(encodedPassword);
        }
        
        try {
            UserDto user = userSvc.getUser(StConverter.convertToSt(loginName));
            
            if (user == null) { // new user
                userAccount.setLoginName(loginName);
                userAccount.setPassword(password);
            } else { // user already exists
                String treatmentSite = getTreatmentSite(user.getPoOrganizationIdentifier());
                if (treatmentSite == null || treatmentSite.equals(UNAVAIL_ORG_MSG)) {
                    user.setPoOrganizationIdentifier(null);
                }
                String physician = getPhysician(user.getPoPersonIdentifier());
                if (physician == null || physician.equals(UNAVAIL_PERSON_MSG)) {
                    user.setPoPersonIdentifier(null);
                }
                userAccount = new UserAccountWebDTO(user, treatmentSite, physician);
            }
        } catch (Exception e) {
            LOG.error("error while activating user :" + loginName, e);
            return ERROR;
        }
        
        userAction = "activateAccount";
        return CREATE;
    }
    
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
        PersonDTO person;
        String lastName = null;
        String firstName = null;                

        try {
            person = PoRegistry.getPersonEntityService().getPerson(poPersonId);
            List<Enxp> nameList = person.getName().getPart();
            Iterator<Enxp> nameIterator = nameList.iterator();
            while (nameIterator.hasNext()) {
                Enxp part = nameIterator.next();
                if (EntityNamePartType.FAM == part.getType()) {
                    lastName = part.getValue();
                } else if (EntityNamePartType.GIV == part.getType()) {
                    if (firstName == null) {
                        firstName = part.getValue();
                    }
                }
            }
        } catch (NullifiedEntityException e) {
            LOG.equals("NullifiedEntityException" + e.getMessage());
            firstName = UNAVAIL_PERSON_MSG;
        }
        String physician = (lastName == null) ? "" : lastName;
        physician += (firstName == null) ? "" : ", " + firstName;
        return physician;
    }

    /**
     * create/update user.
     * @return String
     */
    public String create() {
        UserAccountWebDTO.validate(userAccount, this, true);
        if (hasFieldErrors()) {
            return ActionSupport.INPUT;
        }

        String actionResult =  null;
        UserDto user = new UserDto();
        if (PAUtil.isNotEmpty(userAccount.getId())) {
            user.setIdentifier(IiConverter.convertToIi(userAccount.getId()));
        }
        user.setLoginName(StConverter.convertToSt(userAccount.getLoginName()));
        user.setPassword(StConverter.convertToSt(userAccount.getPassword()));
        user.setFirstName(StConverter.convertToSt(userAccount.getFirstName()));
        user.setLastName(StConverter.convertToSt(userAccount.getLastName()));
        user.setMiddleName(StConverter.convertToSt(userAccount.getMiddleName()));
        user.setAddress(StConverter.convertToSt(userAccount.getAddress()));
        user.setCity(StConverter.convertToSt(userAccount.getCity()));
        user.setState(StConverter.convertToSt(userAccount.getState()));
        user.setPostalCode(StConverter.convertToSt(userAccount.getZipCode()));
        user.setCountry(StConverter.convertToSt(userAccount.getCountry()));
        user.setPhone(StConverter.convertToSt(userAccount.getPhoneNumber()));
        user.setAffiliateOrg(StConverter.convertToSt(userAccount.getOrganization()));
        user.setPrsOrg(StConverter.convertToSt(userAccount.getPrsOrganization()));
        user.setPoOrganizationIdentifier(IiConverter.convertToPoOrganizationIi(userAccount.getTreatmentSiteId()));
        user.setPoPersonIdentifier(IiConverter.convertToPoPersonIi(userAccount.getPhysicianId()));
        
        try {
            if (PAUtil.isEmpty(userAccount.getId())) {
                // create the user
                userSvc.createUser(user);

                userAction = "createAccount";
                actionResult = "redirectToLogin";
            } else {
                // update the user
                userSvc.updateUser(user);

                if (ServletActionContext.getRequest().getRemoteUser() == null) {
                    userAction = "resetPassword";
                    actionResult = "redirectToLogin";
                } else {
                    SessionEnvManager.setAttr(AccrualConstants.SESSION_ATTR_SUBMITTER_NAME, 
                        userAccount.getLastName() + ", " + userAccount.getFirstName());            
                    SessionEnvManager.setAttr(AccrualConstants.SESSION_ATTR_PHYSICIAN_NAME, 
                        getPhysician(user.getPoPersonIdentifier()));            
                    SessionEnvManager.setAttr(AccrualConstants.SESSION_ATTR_SUBMITTING_ORG_II, 
                        user.getPoOrganizationIdentifier());            
                    SessionEnvManager.setAttr(AccrualConstants.SESSION_ATTR_SUBMITTING_ORG_NAME, 
                        getTreatmentSite(user.getPoOrganizationIdentifier()));
                        
                    userAction = "updateAccount";
                    actionResult = CREATE;
                    checkPatients();
                }
            }
        } catch (Exception e) {
            LOG.error("error while updating user info", e);
            actionResult = ERROR;
        }

        return actionResult;
    }

    /**
     * Show My Account Page.
     * @return String
     */
    public String updateAccount() {
        String loginName = ServletActionContext.getRequest().getRemoteUser();
        try {
            UserDto user = userSvc.getUser(StConverter.convertToSt(loginName));
            
            if (user != null) {
                String treatmentSite = getTreatmentSite(user.getPoOrganizationIdentifier());
                if (treatmentSite == null || treatmentSite.equals(UNAVAIL_ORG_MSG)) {
                    user.setPoOrganizationIdentifier(null);
                }
                String physician = getPhysician(user.getPoPersonIdentifier());
                if (physician == null || physician.equals(UNAVAIL_PERSON_MSG)) {
                    user.setPoPersonIdentifier(null);
                }
                checkPatients();
                userAccount = new UserAccountWebDTO(user, treatmentSite, physician);
            }
        } catch (Exception e) {
            LOG.error("error while displaying My Account page for user :" + loginName, e);
            return ERROR;
        }

        return CREATE;
    }

    private void checkPatients() throws RemoteException {
        List<StudySubjectDto> subList = studySubjectSvc.getOutcomes(getAuthorizedUser());
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
     * Lookup data.
     * @return result for next action
     */
    public String initTreatmentSiteLookup() {
        treatmentSites = null;        
        return "initTreatmentSiteLookup";
    }
    
    /**
     * Lookup data.
     * @return result for next action
     */
    public String initPhysicianLookup() {
        physicians = null;        
        return "initPhysicianLookup";
    }
    
    /**
     * Lookup data.
     * @return result for next action
     */
    public String lookupTreatmentSite() {                
        OrganizationDTO criteria = new OrganizationDTO();
        criteria.setName(EnOnConverter.convertToEnOn(treatmentSiteSearchCriteria.getName()));
        criteria.setPostalAddress(AddressConverterUtil.create(null, 
                                                              null, 
                                                              treatmentSiteSearchCriteria.getCity(), 
                                                              treatmentSiteSearchCriteria.getState(), 
                                                              treatmentSiteSearchCriteria.getZipCode(), 
                                                              treatmentSiteSearchCriteria.getCountry()));
            
        try {    
            List<OrganizationDTO> poOrgDtos = new ArrayList<OrganizationDTO>();
            if (criteria.getIdentifier() != null || criteria.getName() != null || criteria.getPostalAddress() != null) {
                LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
                poOrgDtos = PoRegistry.getOrganizationEntityService().search(criteria, limit);
            }

            for (int i = 0; i < poOrgDtos.size(); i++) {
                TreatmentSiteWebDTO treatmentSite = new TreatmentSiteWebDTO();
                treatmentSite.setId(poOrgDtos.get(i).getIdentifier().getExtension().toString());
                treatmentSite.setName(poOrgDtos.get(i).getName().getPart().get(0).getValue());
                
                int partSize = poOrgDtos.get(i).getPostalAddress().getPart().size();
                for (int j = 0; j < partSize; j++) {
                    String type = poOrgDtos.get(i).getPostalAddress().getPart().get(j).getType().name();
                    if (type.equals("CTY")) {
                        treatmentSite.setCity(poOrgDtos.get(i).getPostalAddress().getPart().get(j).getValue());
                    } else if (type.equals("STA")) {
                        treatmentSite.setState(poOrgDtos.get(i).getPostalAddress().getPart().get(j).getValue());
                    } else if (type.equals("ZIP")) {
                        treatmentSite.setZipCode(poOrgDtos.get(i).getPostalAddress().getPart().get(j).getValue());
                    } else if (type.equals("CNT")) {
                        treatmentSite.setCountry(
                            getCountryNameUsingCode(poOrgDtos.get(i).getPostalAddress().getPart().get(j).getCode()));
                    }                                                            
                }
                
                treatmentSites.add(treatmentSite);
            }                
        } catch (Exception e) {
            treatmentSites = null;
            LOG.error("Error occured while searching PO Organizations " + e.getMessage(), e);
        }

        return SUCCESS;
    }
    
    private String getCountryNameUsingCode(String code) {
        List<Country> countries = treatmentSiteSearchCriteria.getCountries();
        for (int i = 0; i < countries.size(); i++) {
            Country country = (Country) countries.get(i);
            if (country.getAlpha3().equals(code)) {
                return country.getName();
            }
        }
        
        return null;
    }
    
    /**
     * Lookup data.
     * @return result for next action
     */
    public String lookupPhysician() {        
        PersonDTO criteria = new PersonDTO();
        criteria.setName(EnPnConverter.convertToEnPn(
            physicianSearchCriteria.getFirstName(), null, physicianSearchCriteria.getLastName(), null, null));
        criteria.setPostalAddress(AddressConverterUtil.create(null, 
                                                              null, 
                                                              physicianSearchCriteria.getCity(), 
                                                              physicianSearchCriteria.getState(), 
                                                              physicianSearchCriteria.getZipCode(), 
                                                              physicianSearchCriteria.getCountry()));
        
        try {
            List<PersonDTO> poPersonDtos = new ArrayList<PersonDTO>();
            if (criteria.getIdentifier() != null || criteria.getName() != null) {                
                LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
                poPersonDtos = PoRegistry.getPersonEntityService().search(criteria, limit);
            }
            
            for (int i = 0; i < poPersonDtos.size(); i++) {
                PersonDTO poPersonDto = poPersonDtos.get(i);
                PhysicianWebDTO physician = new PhysicianWebDTO();
                physician.setId(poPersonDto.getIdentifier().getExtension().toString());
                
                List<Enxp> nameList = poPersonDto.getName().getPart();
                Iterator<Enxp> nameIterator = nameList.iterator();
                while (nameIterator.hasNext()) {
                    Enxp part = nameIterator.next();
                    if (EntityNamePartType.FAM == part.getType()) {
                        physician.setLastName(part.getValue());
                    } else if (EntityNamePartType.GIV == part.getType()) {
                        if (physician.getFirstName() == null) {
                            physician.setFirstName(part.getValue());
                        } else {
                            physician.setMiddleName(part.getValue());
                        }
                    }
                }
                
                int partSize = poPersonDto.getPostalAddress().getPart().size();
                for (int j = 0; j < partSize; j++) {
                    String type = poPersonDto.getPostalAddress().getPart().get(j).getType().name();
                    if (type.equals("CTY")) {
                        physician.setCity(poPersonDto.getPostalAddress().getPart().get(j).getValue());
                    } else if (type.equals("STA")) {
                        physician.setState(poPersonDto.getPostalAddress().getPart().get(j).getValue());
                    } else if (type.equals("ZIP")) {
                        physician.setZipCode(poPersonDto.getPostalAddress().getPart().get(j).getValue());
                    } else if (type.equals("CNT")) {
                        physician.setCountry(
                            getCountryNameUsingCode(poPersonDto.getPostalAddress().getPart().get(j).getCode()));
                    }                                                            
                }
                
                physicians.add(physician);
            }
        } catch (Exception e) {
            physicians = null;
            LOG.error("Error occured while searching PO Persons " + e.getMessage(), e);
        }

        return SUCCESS;
    }
    
    private static final String PERS_CREATE_RESPONSE = "create_pers_response";
    private static final String ORG_CREATE_RESPONSE = "create_org_response";
    private final int ausStateCodeLen = 3;
    
    /**
     * 
     * @return String result
     */
    public String lookupCreatePerson() {
        String firstName = ServletActionContext.getRequest().getParameter("firstName");
        if (firstName != null && !PAUtil.isNotEmpty(firstName)) {
            addActionError("First Name is a required field");
        }
        String lastName = ServletActionContext.getRequest().getParameter("lastName");
        if (lastName != null && !PAUtil.isNotEmpty(lastName)) {
            addActionError("Last Name is a required field");
        }
        String email = ServletActionContext.getRequest().getParameter("email");
        if (email != null && !PAUtil.isNotEmpty(email)) {
            addActionError("Email is a required field");
        } else if (!PAUtil.isValidEmail(email)) {
            addActionError("Email address is invalid");
        }
        String streetAddr = ServletActionContext.getRequest().getParameter("streetAddr");
        if (streetAddr != null && !PAUtil.isNotEmpty(streetAddr)) {
            addActionError("Street address is a required field");
        }
        String city = ServletActionContext.getRequest().getParameter("city");
        if (city != null && !PAUtil.isNotEmpty(city)) {
            addActionError("City is a required field");
        }
        String zip = ServletActionContext.getRequest().getParameter("zip");
        if (zip != null && !PAUtil.isNotEmpty(zip)) {
            addActionError("Zip is a required field");
        }
        String country = ServletActionContext.getRequest().getParameter("country");
        if (country != null && country.equals("aaa")) {
            addActionError("Country is a required field");
        }
        String state = ServletActionContext.getRequest().getParameter("state");
        if (country != null && (country.equalsIgnoreCase("USA")
                            || country.equalsIgnoreCase("CAN"))) {
            if (PAUtil.isEmpty(state) || state.trim().length() > 2) {
                addActionError("2-letter State/Province Code required for USA/Canada");
            }            
        }        
        if (country != null && country.equalsIgnoreCase("AUS")) {
            if (PAUtil.isEmpty(state) || state.trim().length() > ausStateCodeLen) {
                addActionError("2/3-letter State/Province Code required for Australia");
            }            
        }        

        if (hasActionErrors()) {
            StringBuffer sb = new StringBuffer();
            Iterator<String> i = getActionErrors().iterator();
            while (i.hasNext()) {
                sb.append(" - " + i.next().toString());
            }
            ServletActionContext.getRequest().setAttribute("failureMessage", sb.toString());
            return PERS_CREATE_RESPONSE;
        }
        
        String preFix = ServletActionContext.getRequest().getParameter("preFix");
        String midName = ServletActionContext.getRequest().getParameter("midName");        
        String phone = ServletActionContext.getRequest().getParameter("phone");
        String tty = ServletActionContext.getRequest().getParameter("tty");
        String fax = ServletActionContext.getRequest().getParameter("fax");
        String url = ServletActionContext.getRequest().getParameter("url");
        String suffix = ServletActionContext.getRequest().getParameter("suffix");
        //
        gov.nih.nci.services.person.PersonDTO dto = new gov.nih.nci.services.person.PersonDTO();
        dto.setName(new EnPn());
        Enxp part = new Enxp(EntityNamePartType.GIV);
        part.setValue(firstName);
        dto.getName().getPart().add(part);
        // if middel name exists stick it in here!
        if (midName != null && PAUtil.isNotEmpty(midName)) {
            Enxp partMid = new Enxp(EntityNamePartType.GIV);
            partMid.setValue(midName);
            dto.getName().getPart().add(partMid);
        }
        Enxp partFam = new Enxp(EntityNamePartType.FAM);
        partFam.setValue(lastName);
        dto.getName().getPart().add(partFam);
        if (preFix != null && PAUtil.isNotEmpty(preFix)) {
            Enxp partPfx = new Enxp(EntityNamePartType.PFX);
            partPfx.setValue(preFix);
            dto.getName().getPart().add(partPfx);
        }
        if (suffix != null && PAUtil.isNotEmpty(suffix)) {
            Enxp partSfx = new Enxp(EntityNamePartType.SFX);
            partSfx.setValue(suffix);
            dto.getName().getPart().add(partSfx);
        }
       // dto.getName().getPart().add(part);
        DSet<Tel> list = new DSet<Tel>();
        list.setItem(new HashSet<Tel>());
        try {
            if (phone != null && phone.length() > 0) {
                Tel t = new Tel();
                t.setValue(new URI("tel", phone, null));
                list.getItem().add(t);
            }
            if (fax != null && fax.length() > 0) {
                Tel faxTel = new Tel();
                faxTel.setValue(new URI("x-text-fax", fax, null));
                list.getItem().add(faxTel);
            }
            if (tty != null && tty.length() > 0) {
                Tel ttyTel = new Tel();
                ttyTel.setValue(new URI("x-text-tel", tty, null));
                list.getItem().add(ttyTel);
            }
            if (url != null && url.length() > 0) {
                TelUrl telurl = new TelUrl();
                telurl.setValue(new URI(url));
                list.getItem().add(telurl);
            }
            TelEmail telemail = new TelEmail();
            telemail.setValue(new URI("mailto:" + email));
            list.getItem().add(telemail);
            dto.setTelecomAddress(list);
            //PO Service requires upper case state codes for US and Canada
            if (PAUtil.isNotEmpty(state)) {
                state = state.trim().toUpperCase();            
            }
            dto.setPostalAddress(AddressConverterUtil.create(streetAddr, null, city, state, zip, country));
            PoRegistry.getPersonEntityService().createPerson(dto);
            physicianSearchCriteria.setFirstName(firstName);
            physicianSearchCriteria.setLastName(lastName);            
            physicianSearchCriteria.setCity(city); 
            physicianSearchCriteria.setState(state); 
            physicianSearchCriteria.setZipCode(zip); 
            physicianSearchCriteria.setCountry(country);
            lookupPhysician();
            physicianSearchCriteria = new PhysicianWebDTO();
        } catch (EntityValidationException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        } catch (RemoteException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        } catch (URISyntaxException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        } catch (CurationException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);        
        }
        return PERS_CREATE_RESPONSE;
    }

    /**
     * @return result
     */
    public String lookupCreateOrganization() {
        OrganizationDTO orgDto = new OrganizationDTO();
        String orgName = ServletActionContext.getRequest().getParameter("orgName");
        if (orgName != null && !PAUtil.isNotEmpty(orgName)) {
            addActionError("Organization is a required field");
        }
        String orgStAddress = ServletActionContext.getRequest().getParameter("orgStAddress");
        if (orgStAddress != null && !PAUtil.isNotEmpty(orgStAddress)) {
            addActionError("Street address is a required field");
        }
        String countryName = ServletActionContext.getRequest().getParameter("countryName");
        if (countryName != null && countryName.equals("aaa")) {
            addActionError("Country is a required field");
        }
        String cityName = ServletActionContext.getRequest().getParameter("cityName");
        if (cityName != null && !PAUtil.isNotEmpty(cityName)) {
            addActionError("City is a required field");
        }
        String zipCode = ServletActionContext.getRequest().getParameter("zipCode");
        if (zipCode != null && !PAUtil.isNotEmpty(zipCode)) {
            addActionError("Zip is a required field");
        }
        String stateName = ServletActionContext.getRequest().getParameter("stateName");
        if (countryName != null && (countryName.equalsIgnoreCase("USA")
                                || countryName.equalsIgnoreCase("CAN"))) {
            if (PAUtil.isEmpty(stateName) || stateName.trim().length() > 2) {
                addActionError("2-letter State/Province Code required for USA/Canada");
            }           
        }
        if (countryName != null && countryName.equalsIgnoreCase("AUS")) {
            if (PAUtil.isEmpty(stateName) || stateName.trim().length() > ausStateCodeLen) {
                addActionError("2/3-letter State/Province Code required for Australia");
            }           
        }

        String email = ServletActionContext.getRequest().getParameter("email");
        if (email != null && !PAUtil.isNotEmpty(email)) {
            addActionError("Email is a required field");
        } else if (!PAUtil.isValidEmail(email)) {
            addActionError("Email address is invalid");
        }
        String phoneNumer = ServletActionContext.getRequest().getParameter("phoneNumber");
        String faxNumber = ServletActionContext.getRequest().getParameter("fax");
        String ttyNumber = ServletActionContext.getRequest().getParameter("tty");
        String url = ServletActionContext.getRequest().getParameter("url");
        if (hasActionErrors()) {
            StringBuffer sb = new StringBuffer();
            Iterator<String> i = getActionErrors().iterator();
            while (i.hasNext()) {
                sb.append(" - " + i.next().toString());
            }
            ServletActionContext.getRequest().setAttribute("failureMessage", sb.toString());
            return "create_org_response";
        }
        orgDto.setName(EnOnConverter.convertToEnOn(orgName));
        //PO Service requires upper case state codes for US and Canada
        if (PAUtil.isNotEmpty(stateName)) {
            stateName = stateName.trim().toUpperCase();            
        }
        orgDto.setPostalAddress(AddressConverterUtil.create(orgStAddress, null, cityName, stateName, zipCode,
                countryName));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        try {
            if (phoneNumer != null && phoneNumer.length() > 0) {
                Tel t = new Tel();
                t.setValue(new URI("tel", phoneNumer, null));
                telco.getItem().add(t);
            }
            if (faxNumber != null && faxNumber.length() > 0) {
                Tel fax = new Tel();
                fax.setValue(new URI("x-text-fax", faxNumber, null));
                telco.getItem().add(fax);
            }
            if (ttyNumber != null && ttyNumber.length() > 0) {
                Tel tty = new Tel();
                tty.setValue(new URI("x-text-tel", ttyNumber, null));
                telco.getItem().add(tty);
            }
            if (url != null && url.length() > 0) {
                TelUrl telurl = new TelUrl();
                telurl.setValue(new URI(url));
                telco.getItem().add(telurl);
            }
            TelEmail telemail = new TelEmail();
            telemail.setValue(new URI("mailto:" + email));
            telco.getItem().add(telemail);
            orgDto.setTelecomAddress(telco);
            PoRegistry.getOrganizationEntityService().createOrganization(orgDto);
            treatmentSiteSearchCriteria.setName(orgName);
            treatmentSiteSearchCriteria.setCity(cityName);
            treatmentSiteSearchCriteria.setState(stateName);
            treatmentSiteSearchCriteria.setZipCode(zipCode);
            treatmentSiteSearchCriteria.setCountry(countryName);            
            lookupTreatmentSite();
            treatmentSiteSearchCriteria = new TreatmentSiteWebDTO();
        } catch (URISyntaxException e) {
            handleExceptions(e.getMessage(), ORG_CREATE_RESPONSE);
        } catch (EntityValidationException e) {
            handleExceptions(e.getMessage(), ORG_CREATE_RESPONSE);
        } catch (CurationException e) {
            handleExceptions(e.getMessage(), ORG_CREATE_RESPONSE);
        } catch (RemoteException e) {
            handleExceptions(e.getMessage(), ORG_CREATE_RESPONSE);
        }
        return ORG_CREATE_RESPONSE;
    }
    
    private String handleExceptions(String message, String returnString) {
        addActionError(message);
        ServletActionContext.getRequest().setAttribute("failureMessage", message);
        return returnString;
    }

    /**
     * @return the userAccount
     */
    public UserAccountWebDTO getUserAccount() {
        return userAccount;
    }

    /**
     * @param userAccount the userAccount to set
     */
    public void setUserAccount(UserAccountWebDTO userAccount) {
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
     * @param lookupType the lookupType to set
     */
    public void setLookupType(String lookupType) {
        this.lookupType = lookupType;
    }

    /**
     * @return the lookupType
     */
    public String getLookupType() {
        return lookupType;
    }
    
    /**
     * @return the treatmentSiteSearchCriteria
     */
    public TreatmentSiteWebDTO getTreatmentSiteSearchCriteria() {
        return treatmentSiteSearchCriteria;
    }

    /**
     * @param treatmentSiteSearchCriteria the treatmentSiteSearchCriteria to set
     */
    public void setTreatmentSiteSearchCriteria(TreatmentSiteWebDTO treatmentSiteSearchCriteria) {
        this.treatmentSiteSearchCriteria = treatmentSiteSearchCriteria;
    }
    
    /**
     * @return the treatmentSites
     */
    public List<TreatmentSiteWebDTO> getTreatmentSites() {
        return treatmentSites;
    }

    /**
     * @param treatmentSites the treatmentSites to set
     */
    public void setTreatmentSites(List<TreatmentSiteWebDTO> treatmentSites) {
        this.treatmentSites = treatmentSites;
    }
    
    /**
     * @return the physicianSearchCriteria
     */
    public PhysicianWebDTO getPhysicianSearchCriteria() {
        return physicianSearchCriteria;
    }

    /**
     * @param physicianSearchCriteria the physicianSearchCriteria to set
     */
    public void setPhysicianSearchCriteria(PhysicianWebDTO physicianSearchCriteria) {
        this.physicianSearchCriteria = physicianSearchCriteria;
    }
    
    /**
     * @return the physicians
     */
    public List<PhysicianWebDTO> getPhysicians() {
        return physicians;
    }

    /**
     * @param physicians the physicians to set
     */
    public void setPhysicians(List<PhysicianWebDTO> physicians) {
        this.physicians = physicians;
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
}