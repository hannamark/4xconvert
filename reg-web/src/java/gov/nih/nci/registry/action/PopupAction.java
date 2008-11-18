package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author Harsha
 * 
 */
@SuppressWarnings("PMD")
public class PopupAction extends ActionSupport implements Preparable {
    private List countryList = new ArrayList();
    private List<SearchOrgResultDisplay> orgs = new ArrayList<SearchOrgResultDisplay>();
    private List<PersonDTO> persons = new ArrayList<PersonDTO>();
    private OrgSearchCriteria orgSearchCriteria = new OrgSearchCriteria();
    private OrgSearchCriteria createOrg = new OrgSearchCriteria();
    private PersonDTO personDTO = null;
    private static final String PERS_CREATE_RESPONSE = "create_pers_response";

    /**
     * @throws Exception on error
     */
    public void prepare() throws Exception {
        populateCountryList();
    }

    /**
     * 
     * @return String success or failure
     */
    public String lookuppersons() {
        persons = null;
        return "persons";
    }

    /**
     * 
     * @return String success or failure
     */
    public String lookuporgs() {
        try {
            orgs = null;
            populateCountryList();
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
        return "orgs";
    }

    /**
     * 
     * @return result
     */
    public String displayOrgList() {
        return populateOrgs(false);
    }

    /**
     * @throws PAException on error
     * @return result
     */
    public String displayPersonsList() throws PAException {
        String firstName = ServletActionContext.getRequest().getParameter("firstName");
        String lastName = ServletActionContext.getRequest().getParameter("lastName");
        if ((firstName != null) && (firstName.equals("")) && (lastName != null) && (lastName.equals(""))) {
            String message = "Please enter at least one search criteria";
            persons = null;
            addActionError(message);
            ServletActionContext.getRequest().setAttribute("failureMessage", message);
            return SUCCESS;
        }
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtil.convertToEnPn(firstName, null, lastName, null, null));
        persons = RegistryServiceLocator.getPoPersonEntityService().search(p);
        return SUCCESS;
    }

    /**
     * 
     * @return result
     */
    public String displayOrgListDisplayTag() {
        return populateOrgs(true);
    }

    private String populateOrgs(boolean pagination) {
        try {
            String orgName = ServletActionContext.getRequest().getParameter("orgName");
            String countryName = ServletActionContext.getRequest().getParameter("countryName");
            String cityName = ServletActionContext.getRequest().getParameter("cityName");
            String zipCode = ServletActionContext.getRequest().getParameter("zipCode");
            if (orgName.equals("") && countryName.equals("")  && cityName.equals("") && zipCode.equals("")) {
                String message = "Please enter at least one search criteria";
                orgs = null;
                addActionError(message);
                ServletActionContext.getRequest().setAttribute("failureMessage", message);
                return pagination ? "orgs" : SUCCESS;
            }
            if (countryName.equals("")) {
                String message = "Please select a country";
                orgs = null;
                addActionError(message);
                ServletActionContext.getRequest().setAttribute("failureMessage", message);
                return pagination ? "orgs" : SUCCESS;
            }
            if (pagination) {
                orgSearchCriteria.setOrgName(orgName);
                orgSearchCriteria.setOrgCity(cityName);
                orgSearchCriteria.setOrgCountry(countryName);
                orgSearchCriteria.setOrgZip(zipCode);
            }
            OrganizationDTO criteria = new OrganizationDTO();
            criteria.setName(EnOnConverter.convertToEnOn(orgName));
            criteria.setPostalAddress(AddressConverterUtil.create(null, null, cityName, null, zipCode, countryName));
            List<OrganizationDTO> callConvert = new ArrayList<OrganizationDTO>();
            callConvert = RegistryServiceLocator.getPoOrganizationEntityService().search(criteria);
            convertPoOrganizationDTO(callConvert);
            return pagination ? "orgs" : SUCCESS;
        } catch (Exception e) {
            orgs = null;
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute("failureMessage", e.getMessage());
            return pagination ? "orgs" : SUCCESS;
        }
    }

    private String getCountryNameUsingCode(String code) {
        for (int i = 0; i < countryList.size(); i++) {
            gov.nih.nci.pa.domain.Country country = (gov.nih.nci.pa.domain.Country) countryList.get(i);
            if (country.getAlpha3().toString().equals(code)) {
                return country.getName();
            }
        }
        return null;
    }

    private void populateCountryList() throws PAException {
        countryList = (List) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
        if (countryList == null) {
            countryList = RegistryServiceLocator.getLookUpTableService().getCountries();
            ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
        }
    }

    /**
     * @throws PAException on error
     * @return result
     */
    public String createOrganization() throws PAException {
        OrganizationDTO orgDto = new OrganizationDTO();
        String orgName = ServletActionContext.getRequest().getParameter("orgName");
        String orgAbrName = ServletActionContext.getRequest().getParameter("orgAbrName");
        String orgStAddress = ServletActionContext.getRequest().getParameter("orgStAddress");
        String orgDelAddress = ServletActionContext.getRequest().getParameter("orgDelAddress");
        String countryName = ServletActionContext.getRequest().getParameter("countryName");
        String cityName = ServletActionContext.getRequest().getParameter("cityName");
        String zipCode = ServletActionContext.getRequest().getParameter("zipCode");
        String stateName = ServletActionContext.getRequest().getParameter("stateName");
        String phoneNumer = ServletActionContext.getRequest().getParameter("phoneNumber");
        orgDto.setName(EnOnConverter.convertToEnOn(orgName));
        //orgDto.setAbbreviatedName(StringConverter.convertToEnOn(orgAbrName));
        orgDto.setPostalAddress(AddressConverterUtil.create(orgStAddress, orgDelAddress, cityName, stateName, zipCode,
                countryName));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        Tel t = new Tel();
        try {
            t.setValue(new URI("tel", phoneNumer, null));
            telco.getItem().add(t);
            orgDto.setTelecomAddress(telco);
            Ii id = RegistryServiceLocator.getPoOrganizationEntityService().createOrganization(orgDto);
            List<OrganizationDTO> callConvert = new ArrayList<OrganizationDTO>();
            callConvert.add(RegistryServiceLocator.getPoOrganizationEntityService().getOrganization(id));
            convertPoOrganizationDTO(callConvert);
        } catch (NullifiedEntityException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (EntityValidationException e) {
            e.printStackTrace();
        } catch (PAException e) {
            e.printStackTrace();
        }
        return "create_org_response";
    }

    /**
     * 
     * @return String rest
     */
    public String createPerson() {
        String firstName = ServletActionContext.getRequest().getParameter("firstName");
        String lastName = ServletActionContext.getRequest().getParameter("lastName");
        String preFix = ServletActionContext.getRequest().getParameter("preFix");
        String midName = ServletActionContext.getRequest().getParameter("midName");
        String streetAddr = ServletActionContext.getRequest().getParameter("streetAddr");
        String city = ServletActionContext.getRequest().getParameter("city");
        String state = ServletActionContext.getRequest().getParameter("state");
        String zip = ServletActionContext.getRequest().getParameter("zip");
        String country = ServletActionContext.getRequest().getParameter("country");
        //
        PersonDTO dto = new PersonDTO();
        dto.setName(new EnPn());
        Enxp part = new Enxp(EntityNamePartType.GIV);
        part.setValue(firstName);
        dto.getName().getPart().add(part);
        part = new Enxp(EntityNamePartType.FAM);
        part.setValue(lastName);
        dto.getName().getPart().add(part);
        // part = new Enxp(EntityNamePartType.PFX); do this later! figure out
        // the middle name
        dto.setPostalAddress(AddressConverterUtil.create(streetAddr, null, city, state, zip, country));
        try {
            Ii id = RegistryServiceLocator.getPoPersonEntityService().createPerson(dto);
            persons.add(RegistryServiceLocator.getPoPersonEntityService().getPerson(id));
        } catch (NullifiedEntityException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        } catch (EntityValidationException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        } catch (PAException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        }
        return PERS_CREATE_RESPONSE;
    }

    private String handleExceptions(String message, String returnString) {
        addActionError(message);
        ServletActionContext.getRequest().setAttribute("failureMessage", message);
        return returnString;
    }

    private void convertPoOrganizationDTO(List<OrganizationDTO> poOrgDtos) {
        SearchOrgResultDisplay displayElement = null;
        for (int i = 0; i < poOrgDtos.size(); i++) {
            displayElement = new SearchOrgResultDisplay();
            displayElement.setId(poOrgDtos.get(i).getIdentifier().getExtension().toString());
            displayElement.setName(poOrgDtos.get(i).getName().getPart().get(0).getValue());
            //
            int partSize = poOrgDtos.get(i).getPostalAddress().getPart().size();
            AddressPartType type = null;
            for (int k = 0; k < partSize; k++) {
                type = poOrgDtos.get(i).getPostalAddress().getPart().get(k).getType();               
                if (type.name().equals("CNT")) {
                    displayElement.setCountry(getCountryNameUsingCode(poOrgDtos.get(i).getPostalAddress().getPart().
                            get(k).getCode()));
                }
                if (type.name().equals("ZIP")) {
                    displayElement.setZip(poOrgDtos.get(i).getPostalAddress().getPart().get(k).getValue());
                }
                if (type.name().equals("CTY")) {
                    displayElement.setCity(poOrgDtos.get(i).getPostalAddress().getPart().get(k).getValue());
                }              
                if (type.name().equals("STA")) {
                    displayElement.setState(poOrgDtos.get(i).getPostalAddress().getPart().get(k).getValue());
                }
            }
            orgs.add(displayElement);
        }
    }

    /**
     * @return the countryList
     */
    public List<Country> getCountryList() {
        return countryList;
    }

    /**
     * @param countryList the countryList to set
     */
    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    /**
     * @return the persons
     */
    public List<PersonDTO> getPersons() {
        return persons;
    }

    /**
     * @param persons the persons to set
     */
    public void setPersons(List<PersonDTO> persons) {
        this.persons = persons;
    }

    /**
     * @return the orgSearchCriteria
     */
    public OrgSearchCriteria getOrgSearchCriteria() {
        return orgSearchCriteria;
    }

    /**
     * @param orgSearchCriteria the orgSearchCriteria to set
     */
    public void setOrgSearchCriteria(OrgSearchCriteria orgSearchCriteria) {
        this.orgSearchCriteria = orgSearchCriteria;
    }

    /**
     * @return the createOrg
     */
    public OrgSearchCriteria getCreateOrg() {
        return createOrg;
    }

    /**
     * @param createOrg the createOrg to set
     */
    public void setCreateOrg(OrgSearchCriteria createOrg) {
        this.createOrg = createOrg;
    }

    /**
     * @return the personDTO
     */
    public PersonDTO getPersonDTO() {
        return personDTO;
    }

    /**
     * @param personDTO the personDTO to set
     */
    public void setPersonDTO(PersonDTO personDTO) {
        this.personDTO = personDTO;
    }

    /**
     * @return the orgs
     */
    public List<SearchOrgResultDisplay> getOrgs() {
        return orgs;
    }

    /**
     * @param orgs the orgs to set
     */
    public void setOrgs(List<SearchOrgResultDisplay> orgs) {
        this.orgs = orgs;
    }
}
