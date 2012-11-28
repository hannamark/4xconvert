package gov.nih.nci.po.web.roles;

import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;

import java.util.Calendar;
import java.util.Date;

import javax.jms.JMSException;

import org.apache.commons.lang.time.DateUtils;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class to handle editing of Family Organization Relationships for an Organization.
 */
public class OrganizationPerspectiveFamilyRelationshipsAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 1285712121733778829L;

    private Organization organization = new Organization();
    private String rootKey;
    private Long selectedFamilyOrgRelId;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (getRootKey() != null) {
            setOrganization(
                    (Organization) PoHttpSessionUtil.getSession().getAttribute(getRootKey()));
        }
    }


    /**
     * @return show start page
     */
    public String start() {
        setOrganization(PoRegistry.getOrganizationService().getById(organization.getId()));
        setRootKey(PoHttpSessionUtil.addAttribute(getOrganization()));
        initializeCollections();
        return SUCCESS;
    }

    /**
     * Removes the family organization relationship by setting its end date.
     * @return success
     * @throws JMSException exception
     */
    public String remove() throws JMSException {
        FamilyOrganizationRelationship familyOrganizationRelationship =
                PoRegistry.getFamilyOrganizationRelationshipService().getById(getSelectedFamilyOrgRelId());
        familyOrganizationRelationship.setEndDate(DateUtils.truncate(new Date(), Calendar.DATE));
        PoRegistry.getFamilyOrganizationRelationshipService().updateEntity(familyOrganizationRelationship);
        ActionHelper.saveMessage(getText("familyOrgRelationship.remove.success"));
        return SUCCESS;
    }

    private void initializeCollections() {
       getOrganization().getFamilyOrganizationRelationships();
    }

    /**
     * @return the organization
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
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

    /**
     * @return the selectedFamilyOrgRelId
     */
    public Long getSelectedFamilyOrgRelId() {
        return selectedFamilyOrgRelId;
    }

    /**
     * @param selectedFamilyOrgRelId the selectedFamilyOrgRelId to set
     */
    public void setSelectedFamilyOrgRelId(Long selectedFamilyOrgRelId) {
        this.selectedFamilyOrgRelId = selectedFamilyOrgRelId;
    }


}
