package gov.nih.nci.po.web.create;

import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.curation.CurateOrganizationAction;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;

import java.util.Set;

import javax.jms.JMSException;

import org.apache.commons.collections.set.ListOrderedSet;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action class to handle the creation of Organization entities.
 */
public class CreateOrganizationAction extends CurateOrganizationAction implements Preparable {
    private static final long serialVersionUID = 1L;

    /**
     * @return show start page
     */
    @Override
    public String start() {
        getOrganization().setPostalAddress(new Address());
        getOrganization().getPostalAddress().setCountry(PoRegistry.getCountryService().getCountryByAlpha3("USA"));
        getOrganization().setStatusCode(EntityStatus.PENDING);
        setRootKey(PoHttpSessionUtil.addAttribute(getOrganization()));
        return INPUT;
    }

    /**
     * @return success
     * @throws JMSException if an error occurred while publishing the announcement
     */
    @Validations(customValidators = { @CustomValidator(type = "hibernate", fieldName = "organization") })
    public String create() throws JMSException {
        String result = super.curate();
        ActionHelper.getMessages().clear();
        ActionHelper.saveMessage(getText("organization.create.success"));
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
     * @return the person id as a string.
     */
    public String getOrganizationId() {
        if (getOrganization() != null && getOrganization().getId() != null) {
            return this.getOrganization().getId().toString();
        }
        return "";
    }

}
