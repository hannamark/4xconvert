package gov.nih.nci.pa.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import gov.nih.nci.pa.dto.RegulatoryInformationDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 * 
 */
public class RegulatoryInformationAction extends ActionSupport {
        
        private static final long serialVersionUID = 1L;                
        private RegulatoryInformationDTO regulatoryDTO = new RegulatoryInformationDTO();        
        private List countryList = new ArrayList();     
        private String lst = null;      
        private String selectedAuthOrg = null;

        /**
         * Method to get a list of countries.
         * 
         * @return String list
         */
    public String getListofCountries() {
        try {       
                //Map parameters = ActionContext.getContext().getParameters();  
            //TODO the line below needs to be removed when this page is called from menubar
            HibernateUtil.getHibernateHelper().openAndBindSession(); 
            /**
             * You have the protocol Id; Get the study protocol from database and simply set the
             * regulatoryDTO object;
             */
            StudyProtocolQueryDTO spDTO =  (StudyProtocolQueryDTO)
            ServletActionContext.getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
            regulatoryDTO.setProtocolID(spDTO.getStudyProtocolId());
            //countryList = PaRegistry.getRegulatoryInformationService().getCountryList();
            countryList = PaRegistry.getRegulatoryInformationService().getDistinctCountryNames();
            return SUCCESS;
        } catch (Exception e) {           
          return ERROR;
        }
    }
    
    /**
     * Method to save the regulatory information to the database.
     * 
     * @return String success or failure
     */
    public String saveRegulatoryAuthority() { 
        StudyProtocolQueryDTO spDTO =  (StudyProtocolQueryDTO)
        ServletActionContext.getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
        regulatoryDTO.setProtocolID(spDTO.getStudyProtocolId());
        
        try {
            Long id = Long.valueOf(getLst());
            regulatoryDTO.setTrialOversgtAuthCountry(
                    PaRegistry.getRegulatoryInformationService().getCountryName(id));
            regulatoryDTO.setSelectedAuthOrgId(Long.valueOf(selectedAuthOrg));
            regulatoryDTO.setTrialOversgtAuthOrgName(PaRegistry.getRegulatoryInformationService().
                            getRegulatoryAuthorityName(Long.valueOf(selectedAuthOrg)).get(0).toString());
                    PaRegistry.getRegulatoryInformationService().saveRegulatoryInformation(regulatoryDTO);
            } catch (PAException e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                    return SUCCESS;
            }
            return SUCCESS;         
    }
    
    /**
     * 
     * @return String success or failure
     */
    public String editRegulatoryAuthority() {
         //TODO the line below needs to be removed when this page is called from menubar
        HibernateUtil.getHibernateHelper().openAndBindSession(); 
        try {
                countryList = PaRegistry.getRegulatoryInformationService().getDistinctCountryNames();
                StudyProtocolQueryDTO spDTO =  (StudyProtocolQueryDTO)
                ServletActionContext.getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
                //regulatoryDTO.setProtocolID(spDTO.getStudyProtocolId());
                        regulatoryDTO = PaRegistry.getRegulatoryInformationService().getProtocolForEdit(spDTO.getStudyProtocolId());
                        setLst(Long.valueOf(regulatoryDTO.getCountryId()).toString());                      
                } catch (PAException e) {
                        // TODO Auto-generated catch block
                        //e.printStackTrace();
                        return SUCCESS;
                }
                return SUCCESS;
    }

        /**
         * @return the regulatoryDTO
         */
        public RegulatoryInformationDTO getRegulatoryDTO() {
                return regulatoryDTO;
        }

        /**
         * @param regulatoryDTO the regulatoryDTO to set
         */
        public void setRegulatoryDTO(RegulatoryInformationDTO regulatoryDTO) {
                this.regulatoryDTO = regulatoryDTO;
        }

        /**
         * @return the countryList
         */
        public List getCountryList() {
                return countryList;
        }

        /**
         * @param countryList the countryList to set
         */
        public void setCountryList(List countryList) {
                this.countryList = countryList;
        }
        
        /**
         * @return the lst
         */
        public String getLst() {
                return lst;
        }

        /**
         * @param lst the lst to set
         */
        public void setLst(String lst) {
                this.lst = lst;
        }
        
        /**
         * @return the selectedAuthOrg
         */
        public String getSelectedAuthOrg() {
                return selectedAuthOrg;
        }

        /**
         * @param selectedAuthOrg the selectedAuthOrg to set
         */
        public void setSelectedAuthOrg(String selectedAuthOrg) {
                this.selectedAuthOrg = selectedAuthOrg;
        }       
}

