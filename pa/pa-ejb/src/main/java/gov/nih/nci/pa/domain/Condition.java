package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Condition class.  
 * @author Naveen Amiruddin
 * @since 07/01/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
/*
 * table name is conditions and not conditions as condition is a reserved 
 * data base word
 */
@Table(name =  "CONDITIONS")
public class Condition extends AbstractEntity {
    
    private static final long serialVersionUID = 1234567890L;
    
    private Long id;
    private String name;
    private String code;
    private String parentCode;
    
    /**
     * Get the id of the object.
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    public Long getId() {
        return id;
    }

    /**
     * set id.
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * 
     * @return name 
     */
    public String getName() {
        return name;
    }
    /**
     * 
     * @param name name of the disease code
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * @param code code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * @return parentCode
     */
    @Column(name = "PARENT_CODE")
    public String getParentCode() {
        return parentCode;
    }
    /**
     * 
     * @param parentCode parentCode
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
