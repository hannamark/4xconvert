package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
/**
 * 
 * @author Reshma Koganti
 * 
 */
public enum BioMarkerAttributesCode implements CodedEnum<String> {
    /**
     * ASSAY TYPE.
     */
    ASSAY_TYPE("ASSAY TYPE"),
    /**
     * EVALUATION TYPE.
     */
    EVALUATION_TYPE("EVALUATION TYPE"),
    /**
     * BIOMARKER PURPOSE.
     */
    BIOMARKER_PURPOSE("BIOMARKER PURPOSE"),
    /**
     * BIOMARKER USE.
     */
    BIOMARKER_USE("BIOMARKER USE"),
    /**
     * SPECIMEN TYPE.
     */
    SPECIMEN_TYPE("SPECIMEN TYPE"),
    /**
     * SPECIMEN COLLECTION.
     */
    SPECIMEN_COLLECTION("SPECIMEN COLLECTION");
    
    private String code;
    
    private BioMarkerAttributesCode(String code) {
        this.code = code;
        register(this);  
    }
    
    /**
     * @return the coded value of the enum
     */
    @Override
    public String getCode() {
        return this.code;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName() {
        return getCode();
    }

    /**
     * @return the display name
     */
    public String getName() {
        return name();
    }
    

    /**
     * 
     * @param code code
     * @return bioMarker attribute type
     */
    public static BioMarkerAttributesCode getByCode(String code) {
        return getByClassAndCode(BioMarkerAttributesCode.class, code);     
    }
    
    
}
