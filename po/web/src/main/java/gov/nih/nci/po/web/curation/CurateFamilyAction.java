package gov.nih.nci.po.web.curation;

import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.FamilyStatus;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.set.ListOrderedSet;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action class to handle editing of Family entities.
 */
public class CurateFamilyAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 1285712121733778829L;

    private Family family = new Family();
    private List<FamilyOrganizationRelationship> familyOrganizationRelationships =
        new ArrayList<FamilyOrganizationRelationship>();
    private String rootKey;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (getRootKey() != null) {
            setFamily((Family) PoHttpSessionUtil.getSession().getAttribute(getRootKey()));
        }
    }

    /**
     * @return show start page
     */
    public String start() {
        setFamily(PoRegistry.getFamilyService().getById(getFamily().getId()));
        setRootKey(PoHttpSessionUtil.addAttribute(getFamily()));
        initializeCollections();
        return SUCCESS;
    }

    /**
     * @return submit form
     */
    @Validations(customValidators = {@CustomValidator(type = "hibernate", fieldName = "family") })
    public String submit() {
        try {
            PoRegistry.getFamilyService().updateEntity(family);
        } catch (EntityValidationException e) {
           // this should never really occur during normal usage
           // after implementing PO-3199 no need to swallow EntityValidationException
           addActionError(e.getErrorMessages());
        }
        if (FamilyStatus.INACTIVE.equals(family.getStatusCode())) {
            ActionHelper.saveMessage(getText("family.inactivate.success", new String[] {family.getName()}));
            return "list";
        } else if (FamilyStatus.NULLIFIED.equals(family.getStatusCode())) {
            ActionHelper.saveMessage(getText("family.nullify.success", new String[] {family.getName()}));
            return "list";
        } else {
            ActionHelper.saveMessage(getText("family.update.success", new String[] {family.getName()}));
            return SUCCESS;
        }
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

    /**
     * @return the rootKey
     */
    public String getRootKey() {
        return rootKey;
    }

    /**
     * @param rootKey the rootKey to set
     */
    public void setRootKey(String rootKey) {
        this.rootKey = rootKey;
    }
}
