/**
 * 
 */
package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;

/**
 * DTO class for displaying study status history as a list.
 * 
 * @author Hugh Reinhart
 * @since 09/10/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class StudyOverallStatusWebDTO {
    private Long id;
    private String statusCode;
    private String statusDate;

    /**
     * @param dto The iso dto object.
     */
    public StudyOverallStatusWebDTO(StudyOverallStatusDTO dto) {
        super();
        this.id = IiConverter.convertToLong(dto.getIi());
        this.statusCode = CdConverter.convertToStudyStatusCode(dto.getStatusCode()).getDisplayName();
        this.statusDate = PAUtil.normalizeDateString(TsConverter.convertToTimestamp(dto.getStatusDate()).toString());
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the statusDate
     */
    public String getStatusDate() {
        return statusDate;
    }
    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }
}
