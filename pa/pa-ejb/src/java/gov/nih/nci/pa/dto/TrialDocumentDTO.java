/**
 * 
 */
package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

/**
 * @author Vrushali
 *
 */
@SuppressWarnings({"PMD.MethodReturnsInternalArray" })
public class TrialDocumentDTO {
    private String typeCode;
    private String fileName;
    private String id;
    private String inactiveCommentText;
    private byte[] text;
    /**
     * 
     * @param isoDto dto
     */
    public TrialDocumentDTO(DocumentDTO isoDto) {
       super(); 
       this.typeCode = CdConverter.convertCdToString(isoDto.getTypeCode());
       this.fileName = StConverter.convertToString(isoDto.getFileName());
       this.id = IiConverter.convertToString(isoDto.getIdentifier());
       this.inactiveCommentText = StConverter.convertToString(isoDto.getInactiveCommentText());
       this.text = EdConverter.convertToByte(isoDto.getText());
    }
    /**
     * 
     */
    public TrialDocumentDTO() {
        super();
    }
    
    /**
     * @return the typeCode
     */
    public String getTypeCode() {
        return typeCode;
    }
    /**
     * @param typeCode the typeCode to set
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the inactiveCommentText
     */
    public String getInactiveCommentText() {
        return inactiveCommentText;
    }
    /**
     * @param inactiveCommentText the inactiveCommentText to set
     */
    public void setInactiveCommentText(String inactiveCommentText) {
        this.inactiveCommentText = inactiveCommentText;
    }
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * @return the text
     */
    public byte[] getText() {
        return text;
    }
    /**
     * @param text the text to set
     */
    public void setText(byte[] text) {
        byte[] temp = text;
        this.text = temp;
    }
    
}
