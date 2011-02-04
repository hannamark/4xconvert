package gov.nih.nci.po.web.curation;

import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.FamilyStatus;
import gov.nih.nci.po.util.PoRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.set.ListOrderedSet;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action class to handle editing of Family entities.
 */
public class CurateFamilyAction extends ActionSupport {
    private static final long serialVersionUID = 1285712121733778829L;

    private Family family = new Family();
    private List<FamilyOrganizationRelationship> familyOrganizationRelationships =
        new ArrayList<FamilyOrganizationRelationship>();

    /**
     * @return show start page
     */
    public String start() {
        family = PoRegistry.getFamilyService().getById(family.getId());
        initializeCollections();
        return SUCCESS;
    }

    /**
     * @return submit form
     */
    @Validations(customValidators = {@CustomValidator(type = "hibernate", fieldName = "family") })
    public String submit() {
        PoRegistry.getFamilyService().update(family);
        ActionHelper.saveMessage(getText("family.update.success", new String[] {family.getName()}));
        return SUCCESS;
    }

    private void initializeCollections() {
       familyOrganizationRelationships =
           PoRegistry.getFamilyOrganizationRelationshipService().getActiveRelationships(getFamily().getId());
    }

    /**
     * @return the family
     */
    public Family getFamily() {
        return family;
    }

    /**
     * @param family the family to set
     */
    public void setFamily(Family family) {
        this.family = family;
    }

    /**
     * @return the allowable FamilyStatus values
     */
    @SuppressWarnings("unchecked")
    public Set<FamilyStatus> getAvailableStatus() {
        ListOrderedSet set = new ListOrderedSet();
        set.add(FamilyStatus.ACTIVE);
        set.add(FamilyStatus.INACTIVE);
        set.add(FamilyStatus.NULLIFIED);
        return set;
    }

    /**
     * @return the familyOrganizationRelationships
     */
    public List<FamilyOrganizationRelationship> getFamilyOrganizationRelationships() {
        return familyOrganizationRelationships;
    }

    /**
     * @param familyOrganizationRelationships the familyOrganizationRelationships to set
     */
    public void setFamilyOrganizationRelationships(
            List<FamilyOrganizationRelationship> familyOrganizationRelationships) {
        this.familyOrganizationRelationships = familyOrganizationRelationships;
    }
}
