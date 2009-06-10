package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.IdentifiedOrganization;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 * Enum of possible sort criterion for IdentifiedOrganization.
 */
public enum IdentifiedOrganizationSortCriterion implements PoSortCriterion<IdentifiedOrganization> {


    /**
     * Sort by Identified Organization's id.
     */
    ID("id", null),

    /**
     * Sort by Identified Organization's status.
     */
    ROLE_STATUS("status", null),

    /**
     * Sort by Identified Organization's type description.
     */
    TYPE_DESC("typeCode.description", null),

    /**
     * Sort by Identified Organization's type code.
     */
    TYPE_CODE("typeCode.code", null),

    /**
     * Sort by Identified Organization's scoper's name.
     */
    SCOPER_NAME("scoper.name", null),

    /**
     * Sort by Identified Organization's scoper's name.
     */
    SCOPER_ID("scoper.id", null),

    /**
     * Sort by Identified Organization's status date.
     */
    STATUS_DATE("statusDate", null);

    private final String orderField;
    private final String leftJoinField;
    private final List<IdentifiedOrganizationSortCriterion> fields;

    private IdentifiedOrganizationSortCriterion(String orderField, String leftJoinField) {
        this.orderField = orderField;
        this.leftJoinField = leftJoinField;
        this.fields = null;
    }

    private IdentifiedOrganizationSortCriterion(IdentifiedOrganizationSortCriterion... fields) {
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
    public List<IdentifiedOrganizationSortCriterion> getOrderByList() {
        if (orderField != null) {
            return Collections.singletonList(this);
        }
        return fields;
    }

    /**
     * {@inheritDoc}
     */
    public String getLeftJoinField() {
        return this.leftJoinField;
    }
}
