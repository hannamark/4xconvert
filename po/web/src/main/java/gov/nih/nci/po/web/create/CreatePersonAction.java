package gov.nih.nci.po.web.create;

import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.curation.CuratePersonAction;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;

import java.util.Set;

import javax.jms.JMSException;

import org.apache.commons.collections.set.ListOrderedSet;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action class to handle the creation of Person entities.
 */
public class CreatePersonAction extends CuratePersonAction implements
        Preparable {
    private static final long serialVersionUID = 1L;

    /**
     * @return show start page
     */
    @Override
    public String start() {
        getPerson().setPostalAddress(new Address());
        getPerson().getPostalAddress().setCountry(
                PoRegistry.getCountryService().getCountryByAlpha3("USA"));
        getPerson().setStatusCode(EntityStatus.PENDING);
        setRootKey(PoHttpSessionUtil.addAttribute(getPerson()));
        return INPUT;
    }

    /**
     * @return success
     * @throws JMSException
     *             if an error occurred while publishing the announcement
     * @throws CSException
     *             CSException
     */
    @Validations(customValidators = { @CustomValidator(type = "hibernate", fieldName = "person"),
            @CustomValidator(type = "usOrCanadaPhone", fieldName = "person.phone",
                message = "US and Canadian telephone numbers must match ###-###-####(x#*).") ,
            @CustomValidator(type = "usOrCanadaPhone", fieldName = "person.fax",
                message = "US and Canadian fax numbers must match ###-###-####(x#*)."),
            @CustomValidator(type = "usOrCanadaPhone", fieldName = "person.tty",
                message = "US and Canadian tty numbers must match ###-###-####(x#*).")
            })
    public String create() throws JMSException, CSException {
        User createdBy = getLoggedInUser();
        getPerson().setCreatedBy(createdBy);
        String result = super.curate();
        ActionHelper.getMessages().clear();
        ActionHelper.saveMessage(getText("person.create.success"));
        return result;
    }

    /**
     * @return the allowable EntityStatus values
     */
    @SuppressWarnings("unchecked")
    public Set<EntityStatus> getAvailableStatus() {
        ListOrderedSet set = new ListOrderedSet();
        set.add(EntityStatus.PENDING);
        set.add(EntityStatus.ACTIVE);
        return set;
    }

    /**
     * Method for pulling this value in struts xml.
     * 
     * @return the person id as a string.
     */
    public String getPersonId() {
        if (getPerson() != null && getPerson().getId() != null) {
            return this.getPerson().getId().toString();
        }
        return "";
    }

}
