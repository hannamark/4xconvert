package gov.nih.nci.pa.dao;

import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.dto.RegulatoryInformationDTO;
import gov.nih.nci.pa.service.NoStudyProtocolFoundException;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * DAO for accessing Regulatory Information.
 * 
 * @author Harsha
 * @since 08/05/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
//@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
@SuppressWarnings("PMD")
public class RegulatoryInformationDAO {

    private static final Logger LOG = Logger.getLogger(RegulatoryInformationDAO.class);
//    private static final String YES = "Yes";
//    private static final String NO = "No";

    /**
     * 
     * @return List of DiseaseConditionDTO
     * @throws PAException
     *             on error
     */
    @SuppressWarnings("unchecked")
    public List getCountryList() throws PAException {
        LOG.debug("Entering regulatory information dao ");
        List<CountryRegAuthorityDTO> countryDtos = new ArrayList<CountryRegAuthorityDTO>();
        CountryRegAuthorityDTO countryDTO;
        Session session = null;
        try {
            Object[] searchResult = null;
            session = HibernateUtil.getCurrentSession();
            List results = session.createQuery("select id, country from RegulatoryAuthority").list();
            for (int i = 0; i < results.size(); i++) {
                countryDTO = new CountryRegAuthorityDTO();
                searchResult = (Object[]) results.get(i);
                countryDTO.setId((Long) searchResult[0]);
                Country resCountry = (Country) searchResult[1];
                countryDTO.setAlpha2(resCountry.getAlpha2());
                countryDTO.setAlpha3(resCountry.getAlpha3());
                countryDTO.setName(resCountry.getName());
                countryDTO.setNumeric(resCountry.getNumeric());
                countryDtos.add(countryDTO);
            }
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in getCountryList() method ", hbe);
            throw new PAException("Exception at getCountryList() ", hbe);
        }
        LOG.debug("Leaving regulatory information dao ");
        return countryDtos;
    }

    /**
     * Saves the regulatory information.
     * 
     * @param informationDTO
     *            for regulatory information
     * @throws PAException
     *             on error
     * @return boolean status of the save
     */
    public boolean saveRegulatoryInformation(RegulatoryInformationDTO informationDTO) throws PAException {
        LOG.debug("Entering  saveRegulatoryInformation()");
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Object> results = null;
        try {
            StudyProtocol studyProtocol = null;
            results = session.createQuery("select sp from StudyProtocol as sp where sp.id =" 
                    + informationDTO.getProtocolID()).list();
            for (int i = 0; i < results.size(); i++) {
                studyProtocol = (StudyProtocol) results.get(i);
            }
/*****            studyProtocol.setSection801Indicator(informationDTO.isSection801IndicatorP());
            studyProtocol.setFdaRegulatedIndicator(informationDTO.isFdaRegulatedInterventionIndicatorP());
            studyProtocol.setIndIndeIndicator(informationDTO.isIndideIndicatorP());
            studyProtocol.setDataMonitoringCommitteeIndicator(informationDTO.isDataMonitoringIndicatorP());
            studyProtocol.setDelayedpostingIndicator(informationDTO.isDelayedPostingIndicatorP());
*/            
            session.saveOrUpdate(studyProtocol);
            
            // Now save in the 'study_requlatory_authority' table;
            // here if a record exists for one sp update it else create it.
            results = null;
            results = session.createQuery("select sra from StudyRegulatoryAuthority as sra where studyProtocolID =" 
                    + informationDTO.getProtocolID()).list();
            StudyRegulatoryAuthority studyRegulatoryAuth = null;
            if (!results.isEmpty()) {
                for (int i = 0; i < results.size(); i++) {
                    studyRegulatoryAuth = (StudyRegulatoryAuthority) results.get(i);
                    break;
                }
//                studyRegulatoryAuth.setRegulatoryAuthorityID(informationDTO.getSelectedAuthOrgId());
//                studyRegulatoryAuth.setStudyProtocolID(informationDTO.getProtocolID());                
                session.update(studyRegulatoryAuth);
            } else {
                studyRegulatoryAuth = new StudyRegulatoryAuthority();
//                studyRegulatoryAuth.setRegulatoryAuthorityID(informationDTO.getSelectedAuthOrgId());
//                studyRegulatoryAuth.setStudyProtocolID(informationDTO.getProtocolID());
                session.save(studyRegulatoryAuth);
            }
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            LOG.debug("Exception in saveRegulatoryInformation exception is " + e.getMessage());
            return false;
        }
        LOG.debug("Leaving  saveRegulatoryInformation()");
        return true;
    }

    /**
     * Method to get the regulatory authority name.
     * 
     * @param regAuthID
     *            for the Regulatory Authority table
     * @return List of Authority(s) for any given country
     * @throws PAException
     *             on error
     */
    public List<String> getRegulatoryAuthorityName(Long regAuthID) throws PAException {
        LOG.debug("Entering  getRegulatoryAuthorityName()");
        Session session = null;
        List<String> results = null;
        try {
            session = HibernateUtil.getCurrentSession();
            results = session.createQuery("select authorityName from RegulatoryAuthority as ra where ra.id=" 
                    + regAuthID).list();
        } catch (HibernateException hbe) {
            LOG.error("Exception at getRegulatoryAuthorityName() method ", hbe);
            throw new PAException(" Hibernate exception in getRegulatoryAuthorityName() method ", hbe);
        }
        LOG.debug("Leaving  getRegulatoryAuthorityName()");
        return results;
    }

    /**
     * Method to get country name for a given country id.
     * 
     * @param countryId
     *            for a country
     * @return String countryName
     * @throws PAException
     *             on error
     */
    public String getCountryName(long countryId) throws PAException {
        LOG.debug("Entering  getCountryName()");
        Session session = null;
        String countryName = null;
        List results;
        try {
            session = HibernateUtil.getCurrentSession();
            results = session.createQuery("select name from Country as co where co.id=" + countryId).list();
            for (int i = 0; i < results.size(); i++) {
                countryName = (String) results.get(i);
            }
        } catch (HibernateException hbe) {
            LOG.error("Exception in Hibernate at getCountryName() method ", hbe);
            throw new PAException(" Hibernate exception in getCountryName() method ", hbe);
        }
        LOG.debug("Leaving  getCountryName()");
        return countryName;
    }

    /**
     * Method to get a list of distinct countries.
     * 
     * @return List of DiseaseConditionDTO
     * @throws PAException
     *             on error
     */
    @SuppressWarnings("unchecked")
    public List getDistinctCountryList() throws PAException {
        LOG.debug("Entering regulatory information dao ");
        List<CountryRegAuthorityDTO> countryDtos = new ArrayList<CountryRegAuthorityDTO>();
        CountryRegAuthorityDTO countryDTO = null;
        Session session = null;
        try {
            Object[] searchResult = null;
            session = HibernateUtil.getCurrentSession();
            List results = session.createQuery("select distinct country from RegulatoryAuthority").list();
            for (int i = 0; i < results.size(); i++) {
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
            LOG.error(" Hibernate exception in getDistinctCountryList() method ", hbe);
            throw new PAException(" Hibernate exception in getDistinctCountryList()", hbe);
        }
        LOG.debug("Leaving regulatory information dao ");
        return countryDtos;
    }

    /**
     * Method to get a list of the regulatory DTO(s).
     * 
     * @param regAuthID
     *            for the Regulatory Authority table
     * @return List of Authority(s) for any given country
     * @throws PAException
     *             on error
     */
    public List<RegulatoryAuthOrgDTO> getRegulatoryAuthorityNameId(Long regAuthID) throws PAException {
        LOG.debug("Entering  getRegulatoryAuthorityName()");
        Session session = null;
        RegulatoryAuthOrgDTO regAuthDTO = null;
        List retResults = new ArrayList();
        Object[] searchResult = null;
        try {
            session = HibernateUtil.getCurrentSession();
            List results = session.createQuery("select id, authorityName from RegulatoryAuthority " 
                    + "as ra where ra.country.id=" + regAuthID).list();
            for (int i = 0; i < results.size(); i++) {
                regAuthDTO = new RegulatoryAuthOrgDTO();
                searchResult = (Object[]) results.get(i);
                regAuthDTO.setId((Long) searchResult[0]);
                regAuthDTO.setName((String) searchResult[1]);
                retResults.add(regAuthDTO);
            }
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in regulatory information dao ", hbe);
            throw new PAException(" Hibernate exception in getRegulatoryAuthorityNameId() ", hbe);
        }
        LOG.debug("Leaving  getRegulatoryAuthorityName()");
        return retResults;
    }

    /**
     * Method to get Regulatory Information DTO for a protocol id.
     * 
     * @param protocolId
     *            for a given protocol
     * @return RegulatoryInformationDTO
     * @throws PAException
     *             on error
     */
    public RegulatoryInformationDTO getProtocolForEdit(long protocolId) throws PAException {
        LOG.debug("Entering  getRegulatoryInformationForEdit()");
        Session session = HibernateUtil.getCurrentSession();
        List<Object> results = null;
        StudyProtocol studyProtocol = null;
        StudyRegulatoryAuthority authority = null;
        RegulatoryAuthority authority1 = null;
        try {
            results = session.createQuery("select sp from StudyProtocol as sp where sp.id =" + protocolId).list();
            for (int i = 0; i < results.size(); i++) {
                studyProtocol = (StudyProtocol) results.get(i);
            }
            if (results.isEmpty()) {
                LOG.info("The protocol " + protocolId + " has no entry in the Study_Regulatory_Authority table");
                throw new NoStudyProtocolFoundException();
            }
            results = null;
            results = session.createQuery("select sra from StudyRegulatoryAuthority as sra where studyProtocolID =" 
                    + protocolId).list();
            for (int i = 0; i < results.size(); i++) {
                authority = (StudyRegulatoryAuthority) results.get(i);
            }
            if (!results.isEmpty()) {
                results = null;            
///                results = session.createQuery("select ra from RegulatoryAuthority as ra where ra.id=" 
///                        + authority.getRegulatoryAuthorityID()).list();
                for (int i = 0; i < results.size(); i++) {
                    authority1 = (RegulatoryAuthority) results.get(i);
                }
            } else {
                throw new NoStudyProtocolFoundException();
            }
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in regulatory information dao ", hbe);
            throw new PAException(" Hibernate exception getProtocolForEdit() method ", hbe);
        }
        LOG.debug("Leaving  getRegulatoryAuthorityName()");
        return convertProtocolToDTO(studyProtocol, authority1.getCountry().getId());
    }

    private RegulatoryInformationDTO convertProtocolToDTO(StudyProtocol protocol, long country) {
        RegulatoryInformationDTO informationDTO = new RegulatoryInformationDTO();
/*
        if (protocol.getFdaRegulatedIndicator() != null && protocol.getFdaRegulatedIndicator()) {
            informationDTO.setFdaRegulatedInterventionIndicator(YES);
        } else {
            informationDTO.setFdaRegulatedInterventionIndicator(NO);
        }

        if (protocol.getSection801Indicator() != null && protocol.getSection801Indicator()) {
            informationDTO.setSection801Indicator(YES);
        } else {
            informationDTO.setSection801Indicator(NO);
        }      
        
        if (protocol.getDelayedpostingIndicator() != null && protocol.getDelayedpostingIndicator()) {
            informationDTO.setDelayedPostingIndicator(YES);
        } else {
            informationDTO.setDelayedPostingIndicator(NO);                
        }
        
        if (protocol.getDataMonitoringCommitteeIndicator() != null && protocol.getDataMonitoringCommitteeIndicator()) {
            informationDTO.setDataMonitoringIndicator(YES);
        } else {
            informationDTO.setDataMonitoringIndicator(NO);                
        }
        
        if (protocol.getIndIndeIndicator() != null && protocol.getIndIndeIndicator()) {
            informationDTO.setIndideTrialIndicator(YES);
        } else {
            informationDTO.setIndideTrialIndicator(NO);                
        }
*/        
        informationDTO.setCountryId(country);
        return informationDTO;
    }

    /**
     * Method to get the regulatory dto for a given protocol id.
     * 
     * @param protocolId
     *            of a protocol
     * @return RegulatoryAuthOrgDTO
     * @throws PAException
     *             on error
     */
    public RegulatoryAuthOrgDTO getRegulatoryAuthOrgForEdit(long protocolId) throws PAException {
        LOG.debug("Entering  getRegulatoryAuthOrgForEdit()");
        Session session = HibernateUtil.getCurrentSession();
        List<Object> results = null;
        StudyRegulatoryAuthority studyRegulatoryAuthority = null;
        RegulatoryAuthOrgDTO regulatoryAuthOrgDTO = null;
        try {
            results = session.createQuery("select sra from StudyRegulatoryAuthority as sra where studyProtocolID =" 
                    + protocolId).list();
            for (int i = 0; i < results.size(); i++) {
                studyRegulatoryAuthority = (StudyRegulatoryAuthority) results.get(i);
                break;
            }
            if (studyRegulatoryAuthority == null) {
                throw new NoStudyProtocolFoundException();
            }
            // get the reg authority
//            List resultsList = getRegulatoryAuthorityName(studyRegulatoryAuthority.getRegulatoryAuthorityID());
            regulatoryAuthOrgDTO = new RegulatoryAuthOrgDTO();
//            regulatoryAuthOrgDTO.setId(studyRegulatoryAuthority.getRegulatoryAuthorityID());
//            regulatoryAuthOrgDTO.setName(resultsList.get(0).toString());
        } catch (HibernateException hbe) {
            LOG.error("Exception in getRegulatoryAuthOrgForEdit() method ", hbe);
            throw new PAException(" Hibernate exception in getRegulatoryAuthOrgForEdit() method ", hbe);
        }
        LOG.debug("Leaving  getRegulatoryAuthOrgForEdit()");
        return regulatoryAuthOrgDTO;
    }
}
