package gov.nih.nci.po.web.create;

import gov.nih.nci.po.data.bo.FamilyStatus;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.curation.CurateFamilyAction;

import java.util.Date;
import java.util.Set;

import org.apache.commons.collections.set.ListOrderedSet;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action class to handle the creation of Family entities.
 */
public class CreateFamilyAction extends CurateFamilyAction {
    private static final long serialVersionUID = 1L;

    /**
     * @return show start page
     */
    public String start() {
        getFamily().setStatusCode(FamilyStatus.ACTIVE);
        getFamily().setStartDate(new Date());
        return INPUT;
    }

    /**
     * @return success
     */
    @Validations(customValidators = {@CustomValidator(type = "hibernate", fieldName = "family") })
    public String save() {
        long familyId = PoRegistry.getFamilyService().create(getFamily());
        setFamily(PoRegistry.getFamilyService().getById(familyId));
        ActionHelper.saveMessage(getText("family.create.success", new String[] {getFamily().getName()}));
        return SUCCESS;
    }

    /**
     * @return the allowable FamilyStatus values
     */
    @SuppressWarnings("unchecked")
    public Set<FamilyStatus> getAvailableStatus() {
        ListOrderedSet set = new ListOrderedSet();
        set.add(FamilyStatus.ACTIVE);
        return set;
    }
}
