package gov.nih.nci.pa.dto;

import java.io.Serializable;



/**
 * Class used to hold criteria used in searching protocols.
 * 
 * <pre>
 * Attr.                         Corresponding bo attribute
 * =====                         ==========================
 * protocolId                    domain.Protocol.id
 * monitorCode                   domain.StudyProtocol.monitorCode

 * </pre>
 * 
 * @author Gnaveh extend code written by Hugh Reinhart
 * @since 05/27/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class NCISpecificInformationData implements Serializable {    
  

    private static final long serialVersionUID = 1L;

    private Long protocolId;
    private String monitorCode;
    
    private String reportingDataSetMethodCode;
    
    private String summary4TypeCode;
    private String summary4Name;
 

   
    /**
     * @return protocolId
     */
    public Long getProtocolId() {
        return protocolId;
    }

    /**
     * @param protocolId protocolId
     */
    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }

    /**
     * @return monitorCode 
     */
    public String getMonitorCode() {
        return monitorCode;
    }
    
    /**
     * @param monitorCode ref
     */
    public void setMonitorCode(String monitorCode) {
        this.monitorCode = monitorCode;
    }

    /**
     * @return reportingDataSetMethodCode
     */
    public String getReportingDataSetMethodCode() {
        return reportingDataSetMethodCode;
    }
    
    /**
     * @param reportingDataSetMethodCode ref
     */
    public void setReportingDataSetMethodCode(String reportingDataSetMethodCode) {
        this.reportingDataSetMethodCode = reportingDataSetMethodCode;
    }

    /**
     * @return summary4TypeCode
     */
    public String getSummary4TypeCode() {
        return summary4TypeCode;
    }

    /**
     * 
     * @param summary4TypeCode summary4TypeCode
     */
    public void setSummary4TypeCode(String summary4TypeCode) {
        this.summary4TypeCode = summary4TypeCode;
    }

    /**
     * @return Summary4Name
     */
    public String getSummary4Name() {
        return summary4Name;
    }

    /**
     * 
     * @param summary4Name ref
     */
    public void setSummary4Name(String summary4Name) {
        this.summary4Name = summary4Name;
    }  
    
}