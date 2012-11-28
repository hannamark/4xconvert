package gov.nih.nci.po.web.curation;

import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyStatus;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;

import java.util.Set;

import javax.jms.JMSException;

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
        return INPUT;
    }
    
    /**
     * @return submit form
     * @throws JMSException exception
     */
    @Validations(customValidators = {@CustomValidator(type = "hibernate", fieldName = "family") })
    public String submit() throws JMSException {
        PoRegistry.getFamilyService().updateEntity(family);
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
     * Method for pulling this value in struts xml.
     * @return the family id as a string.
     */
    public String getFamilyId() {
        if (getFamily() != null && getFamily().getId() != null) {
            return this.getFamily().getId().toString();
        }
        return "";
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
