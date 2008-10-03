package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.iso.dto.DocumentDTO;
/**
 * 
 * @author Kalpana Guthikonda
 * @since 09/30/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class TrialDocumentWebDTO {

    private String typeCode;
    private String fileName;
    private String id;
    
    /**
     * @param iso StudyResourcingDTO object
     */
    public TrialDocumentWebDTO(DocumentDTO iso) {
        super();
        this.typeCode = iso.getTypeCode().getCode();
        this.fileName = iso.getFileName().getValue(); 
        this.id = iso.getIi().getExtension();
    }
    
    /** .
     *  Default Constructor
     */
    public TrialDocumentWebDTO() {
        super();
    }

    /**
     * @return typeCode
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * @param typeCode typeCode
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

}
