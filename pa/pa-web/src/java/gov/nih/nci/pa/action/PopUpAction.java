package gov.nih.nci.pa.action;

import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.iso.util.EnOnConverter;
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
public class PopUpAction extends ActionSupport {
    private List<CountryRegAuthorityDTO> countryRegDTO = new ArrayList<CountryRegAuthorityDTO>();
    private List<OrganizationDTO> orgs = new ArrayList<OrganizationDTO>();
    private List<PersonDTO> persons = new ArrayList<PersonDTO>();

    /**
     * 
     * @return String success or failure
     */
    public String lookuppersons() {
        return "persons";
    }

    /**
     * 
     * @return String success or failure
     */
    public String lookupcontactpersons() {
        return "contactpersons";
    }

    /**
     * 
     * @return String success or failure
     */
    public String lookuporgs() {
        try {
            countryRegDTO = PaRegistry.getRegulatoryInformationService().getDistinctCountryNames();
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
            // ServletActionContext.getRequest().getParameter("nciOrgName");
            // String countryName =
            // ServletActionContext.getRequest().getParameter("countryName");
            String cityName = ServletActionContext.getRequest().getParameter("cityName");
            String zipCode = ServletActionContext.getRequest().getParameter("zipCode");
            OrganizationDTO criteria = new OrganizationDTO();
            criteria.setName(EnOnConverter.convertToEnOn(orgName));
            criteria.setPostalAddress(AddressConverterUtil.create(null, null, cityName, null, zipCode, "USA"));
            orgs = PaRegistry.getPoOrganizationEntityService().search(criteria);
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getMessage());
            return ERROR;
        }
    }

    /**
     * 
     * @return result
     */
    public String displaycontactPersonsList() {
        String firstName = ServletActionContext.getRequest().getParameter("firstName");
        String lastName = ServletActionContext.getRequest().getParameter("lastName");
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
        PersonDTO p = new PersonDTO();      
        p.setName(RemoteApiUtil.convertToEnPn(firstName, null, lastName, null, null));
        persons = PaRegistry.getPoPersonEntityService().search(p);
        return "persons";
    }

    /**
     * @return the countryRegDTO
     */
    public List<CountryRegAuthorityDTO> getCountryRegDTO() {
        return countryRegDTO;
    }

    /**
     * @param countryRegDTO the countryRegDTO to set
     */
    public void setCountryRegDTO(List<CountryRegAuthorityDTO> countryRegDTO) {
        this.countryRegDTO = countryRegDTO;
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
