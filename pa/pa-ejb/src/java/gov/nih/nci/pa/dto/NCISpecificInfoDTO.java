package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.MonitorCode;

import java.io.Serializable;

/**
 * NCISpecificInfoDTO for transferring NCI Specific information object .
 * @author Gil Naveh
 * @since 06/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class NCISpecificInfoDTO implements Serializable {

 
    private static final long serialVersionUID = 1L;
    
    private Long studySiteID;
    private MonitorCode monitorCode;


    /**
     * Default constructor.
     */
    public NCISpecificInfoDTO() {
        // empty constructor
    }

    /**
     * Constructor for creating a dto from a domain object.
     * @param sp domain object
     */
    public NCISpecificInfoDTO(StudyProtocol sp) {
        this.studySiteID = sp.getId();
        this.monitorCode = sp.getMonitorCode();       
    }
    
 
    /**
     * 
     * @return serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

     /**
     * @return Study_Site ID
     */
    public Long getStudySiteID() {
      return studySiteID;
    }
    
    /**
     * @param studySiteID unique Study_Site id
     */
    public void setStudySiteID(Long studySiteID) {
      this.studySiteID = studySiteID;
    }
   /**
    * 
    * @return monitorCode
   */
    public MonitorCode getMonitorCode() {
      return monitorCode;
    }
   /**
    * 
    * @param monitorCode for study site
    */
    public void setMonitorCode(MonitorCode monitorCode) {
      this.monitorCode = monitorCode;
    }
}
