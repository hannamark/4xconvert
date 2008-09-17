package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyResourcingWebDTO;
import gov.nih.nci.pa.enums.InstitutionCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;
/**
 * 
 * @author Kalpana Guthikonda
 *
 */
@Validation
@SuppressWarnings({"PMD.CyclomaticComplexity" , "PMD.ExcessiveMethodLength" })
public class TrialFundingAction extends ActionSupport {
    
    private static final Logger LOG  = Logger.getLogger(TrialFundingAction.class);
    private StudyResourcingWebDTO studyResourcingWebDTO = new StudyResourcingWebDTO();
    private List<StudyResourcingWebDTO> trialFundingList;
    private Long cbValue;
    private String page;
    private List<String> fundingMechanism;
    //private List<String> fundings;
   
    /**  
     * @return result
     */
    public String query()  {
        LOG.info("Entering query");
        try { 
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);             
            List<StudyResourcingDTO> isoList = PaRegistry.getStudyResourcingService().getstudyResource(studyProtocolIi);
            if (!(isoList.isEmpty())) {                
              trialFundingList = new ArrayList<StudyResourcingWebDTO>();                
              for (StudyResourcingDTO dto : isoList) {
                  trialFundingList.add(new StudyResourcingWebDTO(dto));
              }
            } else {
                addActionError(getText("error.trialFunding.noRecords"));
            }
            return SUCCESS;    

        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    /**
     * @return fundingMechanism List
     */
    public List getFundingMechanism() {
        
        fundingMechanism = (List) ServletActionContext.getRequest().getSession()
        .getAttribute(Constants.FUNDING_MECHANISM);       

        try {
            if (fundingMechanism == null) {
                fundingMechanism = PaRegistry.getLookUpTableService().getFundingMechanisms();
                ServletActionContext.getRequest().getSession().
                    setAttribute(Constants.FUNDING_MECHANISM, fundingMechanism);
            }
            return fundingMechanism;   
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return null;
        }
    }
    
    /**
     * @return result
     */
    public String add()  {
        
        LOG.info("Entering add");
        try { 
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II); 
            
            StudyResourcingDTO studyResoureDTO = new StudyResourcingDTO();
            
            if (page.equalsIgnoreCase("Edit")) {
                studyResoureDTO = PaRegistry.getStudyResourcingService().getStudyResourceByID(
                        IiConverter.convertToIi(cbValue)); 
                studyResoureDTO.setStudyProtocolIi(studyProtocolIi);
                studyResoureDTO.setFundingMechanismCode(CdConverter.convertStringToCd(
                            studyResourcingWebDTO.getFundingMechanismCode()));
                studyResoureDTO.setFundingTypeCode(studyResourcingWebDTO.getFundingTypeCode());
                studyResoureDTO.setMonitorCode(CdConverter.convertToCd(
                            MonitorCode.getByCode(studyResourcingWebDTO.getMonitorCode())));
                studyResoureDTO.setInstitutionCode(CdConverter.convertToCd(
                            InstitutionCode.getByCode(studyResourcingWebDTO.getInstitutionCode())));
                studyResoureDTO.setSuffixgrantYear(TsConverter.convertToTs(
                            PAUtil.dateStringToTimestamp(studyResourcingWebDTO.getSuffixgrantYear())));
                studyResoureDTO.setSuffixOther(StConverter.convertToSt(studyResourcingWebDTO.getSuffixOther()));
                studyResoureDTO.setSerialNumber(StConverter.convertToSt(studyResourcingWebDTO.getSerialNumber()));
                studyResoureDTO.setUserLastUpdated((StConverter.convertToSt(
                            ServletActionContext.getRequest().getRemoteUser())));
                PaRegistry.getStudyResourcingService().updateStudyResourcing(studyResoureDTO);
            } else {
                studyResoureDTO.setStudyProtocolIi(studyProtocolIi);
                studyResoureDTO.setFundingMechanismCode(CdConverter.convertStringToCd(
                        studyResourcingWebDTO.getFundingMechanismCode()));
                studyResoureDTO.setFundingTypeCode(studyResourcingWebDTO.getFundingTypeCode());
                studyResoureDTO.setMonitorCode(CdConverter.convertToCd(
                            MonitorCode.getByCode(studyResourcingWebDTO.getMonitorCode())));
                studyResoureDTO.setInstitutionCode(CdConverter.convertToCd(
                            InstitutionCode.getByCode(studyResourcingWebDTO.getInstitutionCode())));
                studyResoureDTO.setSuffixgrantYear(TsConverter.convertToTs(
                            PAUtil.dateStringToTimestamp(studyResourcingWebDTO.getSuffixgrantYear())));
                studyResoureDTO.setSuffixOther(StConverter.convertToSt(studyResourcingWebDTO.getSuffixOther()));
                studyResoureDTO.setSerialNumber(StConverter.convertToSt(studyResourcingWebDTO.getSerialNumber()));
                studyResoureDTO.setUserLastUpdated((StConverter.convertToSt(
                            ServletActionContext.getRequest().getRemoteUser())));
                PaRegistry.getStudyResourcingService().createStudyResourcing(studyResoureDTO);
            }
            query();
            return SUCCESS;    

        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    /**  
     * @return result
     */
    public String edit()  {
        LOG.info("Entering edit");
        try { 
            StudyResourcingDTO studyR = PaRegistry.getStudyResourcingService().getStudyResourceByID(
                    IiConverter.convertToIi(cbValue));
            studyResourcingWebDTO = new StudyResourcingWebDTO(studyR);
            return SUCCESS;    

        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
     
    /**
     * 
     * @return studyResourcingWebDTO
     */
    public StudyResourcingWebDTO getStudyResourcingWebDTO() {
        return studyResourcingWebDTO;
    }
    /**
     * 
     * @param studyResourcingWebDTO studyResourcingWebDTO
     */
    public void setStudyResourcingWebDTO(StudyResourcingWebDTO studyResourcingWebDTO) {
        this.studyResourcingWebDTO = studyResourcingWebDTO;
    }

    /**
     * @return trialFundingList
     */
    public List<StudyResourcingWebDTO> getTrialFundingList() {
        return trialFundingList;
    }

    /**
     * @param trialFundingList trialFundingList
     */
    public void setTrialFundingList(List<StudyResourcingWebDTO> trialFundingList) {
        this.trialFundingList = trialFundingList;
    }

    /**
     * @return cbValue
     */
    public Long getCbValue() {
        return cbValue;
    }

    /**
     * @param cbValue cbValue
     */
    public void setCbValue(Long cbValue) {
        this.cbValue = cbValue;
    }

    /**
     * @return page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page page
     */
    public void setPage(String page) {
        this.page = page;
    }
    /**
     * @param fundingMechanism fundingMechanism
     */
    public void setFundingMechanism(List<String> fundingMechanism) {
        this.fundingMechanism = fundingMechanism;
    }
      
}
