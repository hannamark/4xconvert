package gov.nih.nci.registry.action;

import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * This action class manages the Organization contact(s).
 * 
 * @author Harsha
 * 
 */
@SuppressWarnings("PMD")
public class OrganizationContactAction extends ActionSupport implements Preparable {
    private List<PersonDTO> persons = new ArrayList<PersonDTO>();
    private List countryList = new ArrayList();
    private String personName;

    /**
     * @throws Exception on error
     */
    public void prepare() throws Exception {
        populateCountryList();
    }

    /**
     * 
     * @return res
     */
    public String getOrganizationContacts() {
        String orgContactIdentifier = ServletActionContext.getRequest().getParameter("orgContactIdentifier");
        OrganizationalContactDTO contactDTO = new OrganizationalContactDTO();
        contactDTO.setScoperIdentifier(IiConverter.converToPoOrganizationIi(orgContactIdentifier));
        try {
            List<OrganizationalContactDTO> list = RegistryServiceLocator.getPoOrganizationalContactCorrelationService()
                    .search(contactDTO);
            for (OrganizationalContactDTO organizationalContactDTO : list) {
                try {
                    persons.add(RegistryServiceLocator.getPoPersonEntityService().getPerson(
                            organizationalContactDTO.getPlayerIdentifier()));
                } catch (NullifiedEntityException e) {
                    addActionError(e.getMessage());
                    ServletActionContext.getRequest().setAttribute("failureMessage", e.getMessage());
                    LOG.error("NullifiedEntityException occured while getting organization contact : " + e);
                    return "display_org_contacts";
                }
            }
        } catch (Exception e) {
            //addActionError(e.getMessage());
            //ServletActionContext.getRequest().setAttribute("failureMessage", e.getMessage());
            LOG.error("Exception occured while getting organization contact : " + e);
            return "display_org_contacts";
        }
        return "display_org_contacts";
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
     * @return the countryList
     */
    public List getCountryList() {
        return countryList;
    }

    /**
     * @param countryList the countryList to set
     */
    public void setCountryList(List countryList) {
        this.countryList = countryList;
    }

    private void populateCountryList() throws PAException {
        countryList = (List) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
        if (countryList == null) {
            countryList = RegistryServiceLocator.getLookUpTableService().getCountries();
            ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
        }
    }

    /**
     * @return the personName
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * @param personName the personName to set
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
