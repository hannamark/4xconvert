package gov.nih.nci.pa.action;

import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.RemoteApiUtil;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
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
    private List<OrganizationDTO> orgs = new ArrayList<OrganizationDTO>();
    private List<PersonDTO> persons = new ArrayList<PersonDTO>();
    private OrgSearchCriteria orgSearchCriteria = new OrgSearchCriteria();

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
            criteria.setPostalAddress(AddressConverterUtil.create(null, null, cityName, null, zipCode, countryName));
            orgs = PaRegistry.getPoOrganizationEntityService().search(criteria);
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
        persons = PaRegistry.getPoPersonEntityService().search(p);
        return SUCCESS;
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
        persons = PaRegistry.getPoPersonEntityService().search(p);
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
        persons = PaRegistry.getPoPersonEntityService().search(p);
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
            orgs = PaRegistry.getPoOrganizationEntityService().search(criteria);
            return "orgs";
        } catch (Exception e) {
            return "orgs";
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
     * @return the orgs
     */
    public List<OrganizationDTO> getOrgs() {
        return orgs;
    }

    /**
     * @param orgs the orgs to set
     */
    public void setOrgs(List<OrganizationDTO> orgs) {
        this.orgs = orgs;
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
}
