package gov.nih.nci.pa.dto;

import gov.nih.nci.pa.domain.InterventionalStudyProtocol;

import java.io.Serializable;
import gov.nih.nci.pa.enums.PhaseCode;

/**
 * TrialDesignDTO for transferring TrialDesignDTO object .
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
    private String type;
    private PhaseCode phaseCode;


    /**
     * Default constructor.
     */
    public TrialDesignDTO() {
        // empty constructor
    }

    /**
     * Constructor for creating a dto from a domain object.
     * @param isp domain object
     */
    public TrialDesignDTO(InterventionalStudyProtocol isp) {
        this.studySiteID = isp.getId();
        this.acronym = isp.getAcronym();
        this.officialTitle = isp.getOfficialTitle();
        //this.type = isp.get
        this.phaseCode = isp.getPhaseCode();
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
    
    /**
     * 
     * @return  Type ISP SP or EP
     */
         public String getType() {
           return type;
         }
    /**
     * 
     * @param type ref
     */
        public void setType(String type) {
          this.type = type;
        }

    /**
     * 
     * @return phase Code
     */
        public PhaseCode getPhaseCode() {
          return phaseCode;
        }
    /**
     * 
     * @param phaseCode ref
     */
        public void setPhaseCode(PhaseCode phaseCode) {
          this.phaseCode = phaseCode;
        }

}