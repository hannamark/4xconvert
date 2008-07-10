package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.domain.StudyProtocol;

import java.io.Serializable;

/**
 * SafetyRegulationDTO for transferring Safety & Regulation object .
 * @author Gil Naveh
 * @since 06/2008
 
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class TrialDesignDTO implements Serializable {

 
    private static final long serialVersionUID = 1L;
    
    private Long studySiteID;
    private String acronym;
    private String officialTitle;
    

     /**
     * Default constructor.
     */
    public TrialDesignDTO() {
        // empty constructor
    }

    /**
     * Constructor for creating a dto from a domain object.
     * @param sp domain object
     */
    public TrialDesignDTO(StudyProtocol sp) {
        this.studySiteID = sp.getId();
        this.acronym = sp.getAcronym();
        this.officialTitle = sp.getOfficialTitle();       
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
    * @return acronym
   */
    public String getAcronym() {
      return acronym;
    }
   /**
    * 
    * @param acronym for study site
    */
    public void setAcronym(String acronym) {
      this.acronym = acronym;
    }
    /**
     * 
     * @return officialTitle
     */
    public String getOfficialTitle() {
      return officialTitle;
    }
    /**
     * 
     * @param officialTitle for study site
     */
    public void setOfficialTitle(String officialTitle) {
      this.officialTitle = officialTitle;
    }
}