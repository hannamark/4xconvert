package gov.nih.nci.po.web.create;

import gov.nih.nci.po.data.bo.FamilyStatus;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.edit.EditFamilyAction;

import java.util.Set;

import org.apache.commons.collections.set.ListOrderedSet;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action class to handle the creation of Family entities.
 */
public class CreateFamilyAction extends EditFamilyAction {
    private static final long serialVersionUID = 1L;

    /**
     * @return show start page
     */
    @Override
    public String start() {
        getFamily().setStatusCode(FamilyStatus.ACTIVE);
        return INPUT;
    }

    /**
     * @return success
     * @throws EntityValidationException if validation exception while creating family.
     */
    @Validations(customValidators = { @CustomValidator(type = "hibernate", fieldName = "family") })
    public String create() throws EntityValidationException {
        PoRegistry.getFamilyService().create(getFamily());
        ActionHelper.saveMessage(getText("family.create.success"));
        return SUCCESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Set<FamilyStatus> getAvailableStatus() {
        ListOrderedSet set = new ListOrderedSet();
        set.add(FamilyStatus.ACTIVE);
        return set;
    }
}
