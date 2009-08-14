package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.ResearchOrganization;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 * Enum of possible sort criterion for ResearchOrganization.
 */
public enum ResearchOrganizationSortCriterion implements PoSortCriterion<ResearchOrganization> {


    /**
     * Sort by Research Organization's id.
     */
    ID("id", null),

    /**
     * Sort by Research Organization's status.
     */
    ROLE_STATUS("status", null),

    /**
     * Sort by Research Organization's type description.
     */
    TYPE_DESC("typeCode.description", null),

    /**
     * Sort by Research Organization's type code.
     */
    TYPE_CODE("typeCode.code", null),

    /**
     * Sort by Research Organization's funding mechanism code.
     */
    FUNDING("fundingMechanism.code", null),

    /**
     * Sort by Research Organization's status date.
     */
    STATUS_DATE("statusDate", null), 
    
    /**
     * Sort by Research Organization's name.
     */
    NAME("name", null);

    private final String orderField;
    private final String leftJoinField;
    private final List<ResearchOrganizationSortCriterion> fields;

    private ResearchOrganizationSortCriterion(String orderField, String leftJoinField) {
        this.orderField = orderField;
        this.leftJoinField = leftJoinField;
        this.fields = null;
    }

    private ResearchOrganizationSortCriterion(ResearchOrganizationSortCriterion... fields) {
        this.orderField = null;
        this.leftJoinField = null;
        this.fields = Arrays.asList(fields);
    }

    /**
     * {@inheritDoc}
     */
    public String getOrderField() {
        return this.orderField;
    }

    /**
     * {@inheritDoc}
     */
    public List<ResearchOrganizationSortCriterion> getOrderByList() {
        if (orderField != null) {
            return Collections.singletonList(this);
        }
        return fields;
    }

    /**
     * {@inheritDoc}
     */
    public String getLeftJoinField() {
        return leftJoinField;
    }
}
