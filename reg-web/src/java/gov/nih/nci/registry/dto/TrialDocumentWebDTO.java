package gov.nih.nci.registry.dto;

import gov.nih.nci.pa.iso.dto.DocumentDTO;
/**
 * 
 * @author Bala Nair
 */
public class TrialDocumentWebDTO {

    private String typeCode;
    private String fileName;
    private String id;
    private String inactiveCommentText;
    
    /**
     * @param iso DocumentDTO object
     */
    public TrialDocumentWebDTO(DocumentDTO iso) {
        super();
        this.typeCode = iso.getTypeCode().getCode();
        this.fileName = iso.getFileName().getValue(); 
        this.id = iso.getIdentifier().getExtension();
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

    /**
     * @return inactiveCommentText
     */
    public String getInactiveCommentText() {
        return inactiveCommentText;
    }

    /**
     * @param inactiveCommentText inactiveCommentText
     */
    public void setInactiveCommentText(String inactiveCommentText) {
        this.inactiveCommentText = inactiveCommentText;
    }
}
