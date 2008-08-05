package gov.nih.nci.pa.dao;

import gov.nih.nci.pa.domain.Condition;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.StudyCondition;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.DiseaseConditionDTO;
import gov.nih.nci.pa.dto.OrganizationDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.dto.RegulatoryInformationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * DAO for accessing Regulatory Information
 *
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class RegulatoryInformationDAO {

    private static final Logger LOG  = Logger.getLogger(RegulatoryInformationDAO.class); 
    
    /**
     * 
     * @param protocolID Long
     * @return List of DiseaseConditionDTO
     * @throws PAException on error
     */
    @SuppressWarnings("unchecked")
    public List getCountryList() throws PAException {    	
       LOG.debug("Entering regulatory information dao ");           
       List<CountryRegAuthorityDTO> countryDtos = new ArrayList<CountryRegAuthorityDTO>();
       CountryRegAuthorityDTO countryDTO=null;
        Session session = null;
        try {
          Object[] searchResult = null;
          session = HibernateUtil.getCurrentSession();
          List results = session.createQuery("select id, country from RegulatoryAuthority").list();
          for(int i=0; i<results.size() ; i++) {            	  
        	  countryDTO = new CountryRegAuthorityDTO();
        	  searchResult = (Object[]) results.get(i);
        	  countryDTO.setId((Long)searchResult[0]);        	  
              Country resCountry = (Country) searchResult[1];
        	  countryDTO.setAlpha2(resCountry.getAlpha2());
        	  countryDTO.setAlpha3(resCountry.getAlpha3());
        	  countryDTO.setName(resCountry.getName());
        	  countryDTO.setNumeric(resCountry.getNumeric());
        	  countryDtos.add(countryDTO);
          }
       } catch (HibernateException hbe) {
        	LOG.error(" Hibernate exception in regulatory information dao ", hbe);
            throw new PAException(" Hibernate exception in regulatory information dao ", hbe);
       }
       LOG.debug("Leaving regulatory information dao ");
       return countryDtos;
    }
    
    /**
     * 
     * @param informationDTO
     * @throws PAException
     */
    public boolean saveRegulatoryInformation(RegulatoryInformationDTO informationDTO) throws PAException{    	
    	 LOG.debug("Entering  saveRegulatoryInformation()");
		 Session session = HibernateUtil.getCurrentSession();
		 Transaction transaction = session.beginTransaction();
		 List<Object> results=null;
    	 try {			
			 StudyProtocol studyProtocol = null;
			 results = session.createQuery("select sp from StudyProtocol as sp where sp.id =" + informationDTO.getProtocolID()).list();
			 for (int i=0;i<results.size();i++){
				 studyProtocol = (StudyProtocol) results.get(i);
			 }
			 studyProtocol.setSection801Indicator(informationDTO.isSection801IndicatorP());
			 studyProtocol.setFdaRegulatedIndicator(informationDTO.isFdaRegulatedInterventionIndicatorP());			 
			 session.saveOrUpdate(studyProtocol);
			 // Now save in the 'study_requlatory_authority' table; here if a record exists for one sp update it else create it. 
			 results = null;
			 results = session.createQuery("select sra from StudyRegulatoryAuthority as sra where studyProtocolID =" + informationDTO.getProtocolID()).list();
			 StudyRegulatoryAuthority studyRegulatoryAuthority=null;
			 if(results.size()>0){				 
				 for (int i=0;i<results.size();i++){
					 studyRegulatoryAuthority = (StudyRegulatoryAuthority) results.get(i);
					 break;
				 }
				 studyRegulatoryAuthority.setRegulatoryAuthorityID(informationDTO.getSelectedAuthOrgId());
				 studyRegulatoryAuthority.setStudyProtocolID(informationDTO.getProtocolID());
				 session.update(studyRegulatoryAuthority);
			 } else {
				 studyRegulatoryAuthority = new StudyRegulatoryAuthority();
				 studyRegulatoryAuthority.setRegulatoryAuthorityID(informationDTO.getSelectedAuthOrgId());
				 studyRegulatoryAuthority.setStudyProtocolID(informationDTO.getProtocolID());
				 session.save(studyRegulatoryAuthority);
			 }
			 transaction.commit();			 
		} catch (HibernateException e) {			
			transaction.rollback();
			LOG.debug("Exception in saveRegulatoryInformation exception is "+e.getMessage());
			return false;
		}
    	 LOG.debug("Leaving  saveRegulatoryInformation()");
    	 return true;
    }
    
    /**
     * 
     * @param regAuthID for the Regulatory Authority table
     * @return List of Authority(s) for any given country
     * @throws PAException
     */
    public List<String> getRegulatoryAuthorityName(Long regAuthID) throws PAException{    	
    	LOG.debug("Entering  getRegulatoryAuthorityName()");
        Session session = null;         
        List<String> results=null;
        try {           
          session = HibernateUtil.getCurrentSession();
          results = session.createQuery("select authorityName from RegulatoryAuthority as ra where ra.id="+regAuthID).list();           
        } catch (HibernateException hbe) {
        	LOG.error(" Hibernate exception in regulatory information dao ", hbe);
            throw new PAException(" Hibernate exception in regulatory information dao ", hbe);
        }    	 
    	LOG.debug("Leaving  getRegulatoryAuthorityName()");
    	return results;
    }
    
    /**
     * 
     * @param countryId
     * @return String countryName
     * @throws PAException
     */
    public String getCountryName(long countryId) throws PAException {
    	LOG.debug("Entering  getCountryName()");
        Session session = null;        
        String countryName=null;
        List results;
        try {
          session = HibernateUtil.getCurrentSession();
          results= session.createQuery("select name from Country as co where co.id="+countryId).list();
          for(int i=0; i<results.size(); i++){
        	  countryName = (String) results.get(i);
          }          
        } catch (HibernateException hbe) {
        	LOG.error(" Hibernate exception in regulatory information dao ", hbe);
            throw new PAException(" Hibernate exception in regulatory information dao ", hbe);
        }    	
    	LOG.debug("Leaving  getCountryName()");
    	return countryName;
    }
    
    /**
     * 
     * @param protocolID Long
     * @return List of DiseaseConditionDTO
     * @throws PAException on error
     */
    @SuppressWarnings("unchecked")
    public List getDistinctCountryList() throws PAException {    	
       LOG.debug("Entering regulatory information dao ");           
       List<CountryRegAuthorityDTO> countryDtos = new ArrayList<CountryRegAuthorityDTO>();
       CountryRegAuthorityDTO countryDTO=null;
        Session session = null;
        try {
          Object[] searchResult = null;
          session = HibernateUtil.getCurrentSession();
          List results = session.createQuery("select distinct country from RegulatoryAuthority").list();
          for(int i=0; i<results.size() ; i++) {            	  
        	  countryDTO = new CountryRegAuthorityDTO();
              Country resCountry = (Country) results.get(i);
              countryDTO.setId(resCountry.getId());
        	  countryDTO.setAlpha2(resCountry.getAlpha2());
        	  countryDTO.setAlpha3(resCountry.getAlpha3());
        	  countryDTO.setName(resCountry.getName());
        	  countryDTO.setNumeric(resCountry.getNumeric());
        	  countryDtos.add(countryDTO);
          }
       } catch (HibernateException hbe) {
        	LOG.error(" Hibernate exception in regulatory information dao ", hbe);
            throw new PAException(" Hibernate exception in regulatory information dao ", hbe);
       }
       LOG.debug("Leaving regulatory information dao ");
       return countryDtos;
    }
    
    /**
     * 
     * @param regAuthID for the Regulatory Authority table
     * @return List of Authority(s) for any given country
     * @throws PAException
     */
    public List<RegulatoryAuthOrgDTO> getRegulatoryAuthorityNameId(Long regAuthID) throws PAException{    	
    	LOG.debug("Entering  getRegulatoryAuthorityName()");
        Session session = null;         
        RegulatoryAuthOrgDTO regAuthDTO=null;
        List retResults=new ArrayList();
        Object[] searchResult = null;
        try {           
          session = HibernateUtil.getCurrentSession();
          List results = session.createQuery("select id, authorityName from RegulatoryAuthority as ra where ra.country.id="+regAuthID).list();
          for(int i=0; i<results.size() ; i++) {
        	  regAuthDTO = new RegulatoryAuthOrgDTO();
        	  searchResult = (Object[]) results.get(i);
        	  regAuthDTO.setId((Long)searchResult[0]);        	  
              regAuthDTO.setName((String)searchResult[1]);
              retResults.add(regAuthDTO);
          }          
        } catch (HibernateException hbe) {
        	LOG.error(" Hibernate exception in regulatory information dao ", hbe);
            throw new PAException(" Hibernate exception in regulatory information dao ", hbe);
        }    	 
    	LOG.debug("Leaving  getRegulatoryAuthorityName()");
    	return retResults;
    }
    
    /**
     * 
     * @param protocolId
     * @return RegulatoryInformationDTO
     * @throws PAException
     */
    public RegulatoryInformationDTO getProtocolForEdit(long protocolId) throws PAException{
    	LOG.debug("Entering  getRegulatoryInformationForEdit()");    	
    	Session session = HibernateUtil.getCurrentSession();		
		List<Object> results=null;
		StudyProtocol studyProtocol = null;
		StudyRegulatoryAuthority authority = null;
		RegulatoryAuthority authority1 = null;
		Country country=null;
		try {						 
			results = session.createQuery("select sp from StudyProtocol as sp where sp.id =" + protocolId).list();
			for (int i=0;i<results.size();i++){
				studyProtocol = (StudyProtocol) results.get(i);
			}
			results=null;
			results = session.createQuery("select sra from StudyRegulatoryAuthority as sra where studyProtocolID =" + protocolId).list();
			for (int i=0;i<results.size();i++){
				authority = (StudyRegulatoryAuthority) results.get(i);
			}
			results=null;
			//---authority.getRegulatoryAuthorityID();
			results=session.createQuery("select ra from RegulatoryAuthority as ra where ra.id="+authority.getRegulatoryAuthorityID()).list();
			for (int i=0;i<results.size();i++){
				authority1 = (RegulatoryAuthority) results.get(i);
			}
    	 } catch (HibernateException hbe){
         	LOG.error(" Hibernate exception in regulatory information dao ", hbe);
            throw new PAException(" Hibernate exception in regulatory information dao ", hbe);    		
    	 }    	
    	LOG.debug("Leaving  getRegulatoryAuthorityName()");
    	return convertProtocolToDTO(studyProtocol, authority1.getCountry().getId());
    }
    
    private RegulatoryInformationDTO convertProtocolToDTO(StudyProtocol protocol, long country ){
    	RegulatoryInformationDTO informationDTO = new RegulatoryInformationDTO();
    	if(protocol.getFdaRegulatedIndicator() != null && protocol.getFdaRegulatedIndicator())
    		informationDTO.setFdaRegulatedInterventionIndicator("Yes");
    	else
    		informationDTO.setFdaRegulatedInterventionIndicator("No");
    	
    	if(protocol.getSection801Indicator() != null && protocol.getSection801Indicator())
    		informationDTO.setSection801Indicator("Yes");
    	else
    		informationDTO.setSection801Indicator("No");
    	informationDTO.setCountryId(country);
    	return informationDTO;
    }
    
    /**
     * 
     * @param protocolId
     * @return RegulatoryAuthOrgDTO
     * @throws PAException
     */
    public RegulatoryAuthOrgDTO getRegulatoryAuthOrgForEdit(long protocolId) throws PAException{
    	LOG.debug("Entering  getRegulatoryAuthOrgForEdit()");    	    	
    	Session session = HibernateUtil.getCurrentSession();		
		List<Object> results=null;
		StudyRegulatoryAuthority studyRegulatoryAuthority=null;
		List regAuthorityResult=null;
		RegulatoryAuthOrgDTO regulatoryAuthOrgDTO=null;
		try {
			results = session.createQuery("select sra from StudyRegulatoryAuthority as sra where studyProtocolID =" + protocolId).list();						 				 
			for (int i=0;i<results.size();i++){
			 studyRegulatoryAuthority = (StudyRegulatoryAuthority) results.get(i);
			 break;
			}
			//get the reg authority
			 List resultsList = getRegulatoryAuthorityName(studyRegulatoryAuthority.getRegulatoryAuthorityID());
			 
	    	 regulatoryAuthOrgDTO = new RegulatoryAuthOrgDTO();	    	
	    	 regulatoryAuthOrgDTO.setId(studyRegulatoryAuthority.getRegulatoryAuthorityID());	    	
	    	 regulatoryAuthOrgDTO.setName(resultsList.get(0).toString());			 
		} catch (HibernateException hbe) {
         	LOG.error(" Hibernate exception in regulatory information dao ", hbe);
            throw new PAException(" Hibernate exception in regulatory information dao ", hbe);    		
		}
    	LOG.debug("Leaving  getRegulatoryAuthOrgForEdit()");
    	return regulatoryAuthOrgDTO;
    }
}
