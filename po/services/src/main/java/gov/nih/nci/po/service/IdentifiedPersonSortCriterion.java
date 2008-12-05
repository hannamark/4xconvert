package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.IdentifiedPerson;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 * Enum of possible sort criterion for IdentifiedPerson.
 */
public enum IdentifiedPersonSortCriterion implements SortCriterion<IdentifiedPerson> {


    /**
     * Sort by Identified Person's id.
     */
    ID("id"),
    
    /**
     * Sort by Identified Person's status.
     */
    ROLE_STATUS("status"),
    
    /**
     * Sort by Identified Person's type description.
     */
    TYPE_DESC("typeCode.description"),
    
    /**
     * Sort by Identified Person's type code.
     */
    TYPE_CODE("typeCode.code"),
    
    /**
     * Sort by Identified Person's scoper's name.
     */
    SCOPER_NAME("scoper.name"),
    
    /**
     * Sort by Identified Person's scoper's name.
     */
    SCOPER_ID("scoper.id"),
    
    /**
     * Sort by Identified Person's status date.
     */
    STATUS_DATE("statusDate");
    
    private final String orderField;
    private final List<IdentifiedPersonSortCriterion> fields;

    private IdentifiedPersonSortCriterion(String orderField) {
        this.orderField = orderField;
        this.fields = null;
    }

    private IdentifiedPersonSortCriterion(IdentifiedPersonSortCriterion... fields) {
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
    public List<IdentifiedPersonSortCriterion> getOrderByList() {
        if (orderField != null) {
            return Collections.singletonList(this);
        }
        return fields;
    }
}
