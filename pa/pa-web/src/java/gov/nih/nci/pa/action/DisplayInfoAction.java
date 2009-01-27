package gov.nih.nci.pa.action;

import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.dto.PersonWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.RemoteApiUtil;
import gov.nih.nci.pa.util.SearchPersonResultDisplay;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Harsha
 * @since 01/23/2009 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 * 
 */
@SuppressWarnings("PMD") 
public class DisplayInfoAction extends ActionSupport {
    PersonWebDTO webDTO = new PersonWebDTO();

    /**
     * 
     * @return String success or failure
     */
    public String query() {
        try {
            StudyProtocolQueryDTO studyProtocolQueryDTO = (StudyProtocolQueryDTO) ServletActionContext.getRequest()
                    .getSession().getAttribute(Constants.TRIAL_SUMMARY);
            String user = studyProtocolQueryDTO.getUserLastCreated();
            RegistryUser userInfo = PaRegistry.getRegisterUserService().getUser(user);
            webDTO.setFirstName(userInfo.getFirstName());
            webDTO.setLastName(userInfo.getLastName());
            webDTO.setEmail(user);
            webDTO.setMiddleName(userInfo.getMiddleName());
            webDTO.setCity(userInfo.getCity());
            webDTO.setState(userInfo.getState());
            webDTO.setCountry(userInfo.getCountry());
            webDTO.setZip(userInfo.getPostalCode());
            return SUCCESS;
        } catch (PAException pax) {
            return ERROR;
        }
    }

    /**
     * 
     * @return String success or failure
     */
    public String queryPiInfo() {
        try {
            StudyProtocolQueryDTO studyProtocolQueryDTO = (StudyProtocolQueryDTO) ServletActionContext.getRequest()
                    .getSession().getAttribute(Constants.TRIAL_SUMMARY);
            CorrelationUtils cUtils = new CorrelationUtils();
            Person userInfo = cUtils.getPAPersonByIndetifers(studyProtocolQueryDTO.getPiId(), null);
            PersonDTO poPerson = PaRegistry.getPoPersonEntityService().getPerson(
                    IiConverter.converToPoPersonIi(userInfo.getIdentifier()));
            SearchPersonResultDisplay paPerson = RemoteApiUtil.convertToPaPerson(poPerson);
            webDTO.setFirstName(paPerson.getFirstName());
            webDTO.setLastName(paPerson.getLastName());
            webDTO.setEmail(paPerson.getEmail());
            webDTO.setMiddleName((paPerson.getMiddleName() != null) ? paPerson.getMiddleName() : "-");
            webDTO.setCity((paPerson.getCity() != null) ? paPerson.getCity() : "-");
            webDTO.setState((paPerson.getState() != null) ? paPerson.getState() : "-");
            webDTO.setCountry((paPerson.getCountry() != null) ? paPerson.getCountry() : "-");
            webDTO.setZip((paPerson.getZip() != null) ? paPerson.getZip() : "-");
            return SUCCESS;
        } catch (PAException e) {
            return ERROR;
        } catch (NullifiedEntityException e) {
            return ERROR;
        }
    }

    /**
     * @return the webDTO
     */
    public PersonWebDTO getWebDTO() {
        return webDTO;
    }

    /**
     * @param webDTO
     *            the webDTO to set
     */
    public void setWebDTO(PersonWebDTO webDTO) {
        this.webDTO = webDTO;
    }
}
