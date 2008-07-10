package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.TrialDesignDTO;

/**
 * 
 * @author gnaveh
 *
 */
public interface ITrialDesignService {

     /**
     * 
     * @param pId ProtocolID
     * @return SafetyRegulationDto   
     * @throws PAException on error 
     */
     TrialDesignDTO getTrialDesign(String pId) throws PAException;

}