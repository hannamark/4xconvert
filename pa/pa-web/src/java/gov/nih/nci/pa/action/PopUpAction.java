package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.RemoteApiUtil;
import gov.nih.nci.pa.util.SearchOrgResultDisplay;
import gov.nih.nci.pa.util.SearchPersonResultDisplay;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Common module to call the PO Search and related functionalities.
 * 
 * @author Harsha
 * 
 */
@SuppressWarnings("PMD")
public class PopUpAction extends ActionSupport {
    private List<Country> countryList = new ArrayList<Country>();
    private List<SearchOrgResultDisplay> orgs = new ArrayList<SearchOrgResultDisplay>();
    private List<SearchPersonResultDisplay> persons = new ArrayList<SearchPersonResultDisplay>();
    private OrgSearchCriteria orgSearchCriteria = new OrgSearchCriteria();

    /**
     * @return the orgSearchCriteria
     */
    public OrgSearchCriteria getOrgSearchCriteria() {
        return orgSearchCriteria;
    }

    /**
     * @param orgSearchCriteria
     *            the orgSearchCriteria to set
     */
    public void setOrgSearchCriteria(OrgSearchCriteria orgSearchCriteria) {
        this.orgSearchCriteria = orgSearchCriteria;
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
    public String lookupcontactpersons() {
        persons = null;
        String email = ServletActionContext.getRequest().getParameter("email");
        String telephone = ServletActionContext.getRequest().getParameter("tel");
        if (email != null) {
            ServletActionContext.getRequest().getSession().setAttribute("emailEntered", email);
        }
        if (telephone != null) {
            ServletActionContext.getRequest().getSession().setAttribute("telephoneEntered", telephone);
        }
        return "contactpersons";
    }

    /**
     * 
     * @return String success or failure
     */
    public String lookuporgs() {
        try {
            countryList = (List<Country>) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
            if (countryList == null) {
                countryList = PaRegistry.getLookUpTableService().getCountries();
                ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
            }
            orgs = null;
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
        try {
            String orgName = ServletActionContext.getRequest().getParameter("orgName");
            // String nciOrgName =
            // ServletActionContext.getRequest().getParameter("nciNumber");
            String countryName = ServletActionContext.getRequest().getParameter("countryName");
            String cityName = ServletActionContext.getRequest().getParameter("cityName");
            String zipCode = ServletActionContext.getRequest().getParameter("zipCode");
            if (orgName.equals("") && countryName.equals("aaa") && cityName.equals("") && zipCode.equals("")) {
                String message = "Please enter at least one search criteria";
                orgs = null;
                addActionError(message);
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
                return SUCCESS;
            }
            OrganizationDTO criteria = new OrganizationDTO();
            criteria.setName(EnOnConverter.convertToEnOn(orgName));
            criteria.setPostalAddress(AddressConverterUtil.create("", "", cityName, "", zipCode, countryName));
            List<OrganizationDTO> callConvert = new ArrayList<OrganizationDTO>();
            callConvert = PaRegistry.getPoOrganizationEntityService().search(criteria);
            convertPoOrganizationDTO(callConvert);
            return SUCCESS;
        } catch (Exception e) {
            return SUCCESS;
        }
    }

    /**
     * 
     * @return result
     */
    public String displaycontactPersonsList() {
        String firstName = ServletActionContext.getRequest().getParameter("firstName");
        String lastName = ServletActionContext.getRequest().getParameter("lastName");
        if (firstName.equals("") && lastName.equals("")) {
            String message = "Please enter at least one search criteria";
            persons = null;
            addActionError(message);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
            return SUCCESS;
        }
        PersonDTO p = new PersonDTO();
        // (RemoteApiUtils.convertToEnPn(fName, mName, lName, prefix, suffix));
        p.setName(RemoteApiUtil.convertToEnPn(firstName, null, lastName, null, null));
        try {
            List<PersonDTO> list = new ArrayList<PersonDTO>();
            list = PaRegistry.getPoPersonEntityService().search(p);
            for (PersonDTO dto : list) {
                persons.add(convertToPaPerson(dto));
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return SUCCESS;
        }
        return SUCCESS;
    }

    /**
     * 
     * @return result
     */
    public String displaycontactPersonsListDisplayTag() {

        String firstName = ServletActionContext.getRequest().getParameter("firstName");
        String lastName = ServletActionContext.getRequest().getParameter("lastName");
        if (firstName.equals("") && lastName.equals("")) {
            String message = "Please enter at least one search criteria";
            persons = null;
            addActionError(message);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
            return SUCCESS;
        }
        PersonDTO p = new PersonDTO();
        // (RemoteApiUtils.convertToEnPn(fName, mName, lName, prefix, suffix));
        p.setName(RemoteApiUtil.convertToEnPn(firstName, null, lastName, null, null));
        try {
            List<PersonDTO> list = new ArrayList<PersonDTO>();
            list = PaRegistry.getPoPersonEntityService().search(p);
            for (PersonDTO dto : list) {
                persons.add(convertToPaPerson(dto));
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return "contactpersons";
        }
        return "contactpersons";
    }

    /**
     * 
     * @return result
     */
    public String displayPersonsList() {
        String firstName = ServletActionContext.getRequest().getParameter("firstName");
        String lastName = ServletActionContext.getRequest().getParameter("lastName");
        if (firstName.equals("") && lastName.equals("")) {
            String message = "Please enter at least one search criteria";
            persons = null;
            addActionError(message);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
            return SUCCESS;
        }
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtil.convertToEnPn(firstName, null, lastName, null, null));
        try {
            List<PersonDTO> list = new ArrayList<PersonDTO>();
            list = PaRegistry.getPoPersonEntityService().search(p);
            for (PersonDTO dto : list) {
                persons.add(convertToPaPerson(dto));
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return SUCCESS;
        }
        return SUCCESS;
    }

    /**
     * 
     * @return result
     */
    public String displayPersonsListDisplayTag() {
        String firstName = ServletActionContext.getRequest().getParameter("firstName");
        String lastName = ServletActionContext.getRequest().getParameter("lastName");
        if (firstName.equals("") && lastName.equals("")) {
            String message = "Please enter at least one search criteria";
            persons = null;
            addActionError(message);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
            return SUCCESS;
        }
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtil.convertToEnPn(firstName, null, lastName, null, null));
        try {
            List<PersonDTO> list = new ArrayList<PersonDTO>();
            list = PaRegistry.getPoPersonEntityService().search(p);
            for (PersonDTO dto : list) {
                persons.add(convertToPaPerson(dto));
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return SUCCESS;
        }
        return "persons";
    }

    /**
     * 
     * @return result
     */
    public String displayOrgListDisplayTag() {
        try {
            countryList = (List) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
            if (countryList == null) {
                countryList = PaRegistry.getLookUpTableService().getCountries();
                ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
            }
            String orgName = ServletActionContext.getRequest().getParameter("orgName");
            // String nciOrgName =
            // ServletActionContext.getRequest().getParameter("nciNumber");
            String countryName = ServletActionContext.getRequest().getParameter("countryName");
            String cityName = ServletActionContext.getRequest().getParameter("cityName");
            String zipCode = ServletActionContext.getRequest().getParameter("zipCode");
            if (orgName.equals("") && countryName.equals("aaa") && cityName.equals("") && zipCode.equals("")) {
                String message = "Please enter at least one search criteria";
                orgs = null;
                addActionError(message);
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
                return SUCCESS;
            }
            // Set the values
            orgSearchCriteria.setOrgName(orgName);
            orgSearchCriteria.setOrgCity(cityName);
            orgSearchCriteria.setOrgCountry(countryName);
            orgSearchCriteria.setOrgZip(zipCode);
            //
            OrganizationDTO criteria = new OrganizationDTO();
            criteria.setName(EnOnConverter.convertToEnOn(orgName));
            criteria.setPostalAddress(AddressConverterUtil.create(null, null, cityName, null, zipCode, countryName));
            List<OrganizationDTO> callConvert = new ArrayList<OrganizationDTO>();
            callConvert = PaRegistry.getPoOrganizationEntityService().search(criteria);
            convertPoOrganizationDTO(callConvert);
            return "orgs";
        } catch (Exception e) {
            return "orgs";
        }
    }

    private void convertPoOrganizationDTO(List<OrganizationDTO> poOrgDtos) throws PAException {
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
                    displayElement.setCountry(getCountryNameUsingCode(poOrgDtos.get(i).getPostalAddress().getPart()
                            .get(k).getCode()));
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

    private String getCountryNameUsingCode(String code) throws PAException {
        if (!(countryList.size() > 0)) {
            countryList = (List<Country>) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
            if (countryList == null) {
                countryList = PaRegistry.getLookUpTableService().getCountries();
                ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
            }
        }
        for (int i = 0; i < countryList.size(); i++) {
            gov.nih.nci.pa.domain.Country country = (gov.nih.nci.pa.domain.Country) countryList.get(i);
            if (country.getAlpha3().toString().equals(code)) {
                return country.getName();
            }
        }
        return null;
    }

    private SearchPersonResultDisplay convertToPaPerson(PersonDTO poPerson) {
        SearchPersonResultDisplay prs = new SearchPersonResultDisplay();
        List<Enxp> list = poPerson.getName().getPart();
        Iterator ite = list.iterator();
        while (ite.hasNext()) {
            Enxp part = (Enxp) ite.next();
            if (EntityNamePartType.FAM == part.getType()) {
                prs.setLastName(part.getValue());
            } else if (EntityNamePartType.GIV == part.getType()) {
                if (prs.getFirstName() == null) {
                    prs.setFirstName(part.getValue());
                } else {
                    prs.setMiddleName(part.getValue());
                }
            }
        }
        StringBuffer emailList = new StringBuffer();
        List<String> emails = DSetConverter.convertDSetToList(poPerson.getTelecomAddress(), "EMAIL");
        for (String email : emails) {
            emailList.append(email + ", \n");
        }
        prs.setId(Long.valueOf(poPerson.getIdentifier().getExtension()));
        prs.setEmail(emailList.toString());
        prs.setId(Long.valueOf(poPerson.getIdentifier().getExtension().toString()));
        return prs;
    }

    /**
     * @return the countryList
     */
    public List<Country> getCountryList() {
        return countryList;
    }

    /**
     * @param countryList
     *            the countryList to set
     */
    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    /**
     * @return the orgs
     */
    public List<SearchOrgResultDisplay> getOrgs() {
        return orgs;
    }

    /**
     * @param orgs
     *            the orgs to set
     */
    public void setOrgs(List<SearchOrgResultDisplay> orgs) {
        this.orgs = orgs;
    }

    /**
     * @return the persons
     */
    public List<SearchPersonResultDisplay> getPersons() {
        return persons;
    }

    /**
     * @param persons
     *            the persons to set
     */
    public void setPersons(List<SearchPersonResultDisplay> persons) {
        this.persons = persons;
    }
}
