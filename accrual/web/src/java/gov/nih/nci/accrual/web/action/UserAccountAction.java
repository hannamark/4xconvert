/**
 *
 */
package gov.nih.nci.accrual.web.action;

import gov.nih.nci.accrual.dto.UserDto;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.accrual.web.dto.util.PhysicianWebDTO;
import gov.nih.nci.accrual.web.dto.util.TreatmentSiteWebDTO;
import gov.nih.nci.accrual.web.dto.util.UserAccountWebDTO;
import gov.nih.nci.accrual.web.mail.MailManager;
import gov.nih.nci.accrual.web.util.AccrualConstants;
import gov.nih.nci.accrual.web.util.EncoderDecoder;
import gov.nih.nci.accrual.web.util.SessionEnvManager;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.rmi.RemoteException;
import java.util.ArrayList;
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
                userAccount = new UserAccountWebDTO(user, treatmentSite, physician);
            }
        } catch (Exception e) {
            LOG.error("error while displaying My Account page for user :" + loginName, e);
            return ERROR;
        }

        return CREATE;
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
}