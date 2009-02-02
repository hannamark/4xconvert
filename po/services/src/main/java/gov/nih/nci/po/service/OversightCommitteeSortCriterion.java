package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.OversightCommittee;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 * Enum of possible sort criterion for OversightCommittee.
 */
public enum OversightCommitteeSortCriterion implements PoSortCriterion<OversightCommittee> {


    /**
     * Sort by Research Organization's id.
     */
    ID("id"),

    /**
     * Sort by Research Organization's status.
     */
    ROLE_STATUS("status"),

    /**
     * Sort by Research Organization's type description.
     */
    TYPE_DESC("typeCode.description"),

    /**
     * Sort by Research Organization's type code.
     */
    TYPE_CODE("typeCode.code"),

    /**
     * Sort by Research Organization's status date.
     */
    STATUS_DATE("statusDate");

    private final String orderField;
    private final List<OversightCommitteeSortCriterion> fields;

    private OversightCommitteeSortCriterion(String orderField) {
        this.orderField = orderField;
        this.fields = null;
    }

    private OversightCommitteeSortCriterion(OversightCommitteeSortCriterion... fields) {
        this.orderField = null;
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
    public List<OversightCommitteeSortCriterion> getOrderByList() {
        if (orderField != null) {
            return Collections.singletonList(this);
        }
        return fields;
    }
}
