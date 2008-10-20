package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.TrialFundingWebDTO;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
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
    private static final String QUERY_RESULT = "query";
    private static final String DELETE_RESULT = "delete";
    private static final Logger LOG  = Logger.getLogger(TrialFundingAction.class);
    private TrialFundingWebDTO trialFundingWebDTO = new TrialFundingWebDTO();
    private List<TrialFundingWebDTO> trialFundingList;
    private Long cbValue;
    private String page;
    
    /**  
     * @return result
     */
    public String displayJs() {
        return SUCCESS;
    }
   
    /**  
     * @return result
     */
    public String query()  {
        LOG.info("Entering query");
        try { 
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);             
            List<StudyResourcingDTO> isoList = PaRegistry.getStudyResourcingService().
                getstudyResourceByStudyProtocol(studyProtocolIi);
            if (!(isoList.isEmpty())) {                
              trialFundingList = new ArrayList<TrialFundingWebDTO>();                
              for (StudyResourcingDTO dto : isoList) {
                  trialFundingList.add(new TrialFundingWebDTO(dto));
              }
            } else {
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, 
                        getText("error.trialDocument.noRecords"));
            }
            return QUERY_RESULT;    

        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return QUERY_RESULT;
        }
    }
    
    /**
     * @return result
     */
    public String create()  {
        
        LOG.info("Entering create");
        enforceBusinessRules();
        if (hasFieldErrors()) {
            return ERROR;
        }
        try {            
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II); 
            StudyResourcingDTO studyResoureDTO = new StudyResourcingDTO();            

            studyResoureDTO.setStudyProtocolIi(studyProtocolIi);
            studyResoureDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.FALSE));
            studyResoureDTO.setFundingMechanismCode(CdConverter.convertStringToCd(
                    trialFundingWebDTO.getFundingMechanismCode()));
            studyResoureDTO.setFundingTypeCode(
                    CdConverter.convertStringToCd(trialFundingWebDTO.getFundingTypeCode()));
            studyResoureDTO.setNciDivisionProgramCode(CdConverter.convertToCd(
                    MonitorCode.getByCode(trialFundingWebDTO.getNciDivisionProgramCode())));
            studyResoureDTO.setNihInstitutionCode(CdConverter.convertStringToCd(
                    trialFundingWebDTO.getNihInstitutionCode()));
            studyResoureDTO.setSuffixGrantYear(StConverter.convertToSt(trialFundingWebDTO.getSuffixgrantYear()));
            studyResoureDTO.setSuffixOther(StConverter.convertToSt(trialFundingWebDTO.getSuffixOther()));
            studyResoureDTO.setSerialNumber(IntConverter.convertToInt(trialFundingWebDTO.getSerialNumber()));
            studyResoureDTO.setUserLastUpdated((StConverter.convertToSt(
                    ServletActionContext.getRequest().getRemoteUser())));
            PaRegistry.getStudyResourcingService().createStudyResourcing(studyResoureDTO);

            query();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
            return QUERY_RESULT;    

        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    /**
     * @return result
     */
    public String update()  {
        
        LOG.info("Entering update");
        enforceBusinessRules();
        if (hasFieldErrors()) {
            return ERROR;
        }
        try { 
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II); 
            StudyResourcingDTO studyResoureDTO = new StudyResourcingDTO();            

            studyResoureDTO = PaRegistry.getStudyResourcingService().getStudyResourceByID(
                    IiConverter.convertToIi(cbValue)); 
            studyResoureDTO.setStudyProtocolIi(studyProtocolIi);
            studyResoureDTO.setFundingMechanismCode(CdConverter.convertStringToCd(
                    trialFundingWebDTO.getFundingMechanismCode()));
            studyResoureDTO.setFundingTypeCode(
                    CdConverter.convertStringToCd(trialFundingWebDTO.getFundingTypeCode()));
            studyResoureDTO.setNciDivisionProgramCode(CdConverter.convertToCd(
                    MonitorCode.getByCode(trialFundingWebDTO.getNciDivisionProgramCode())));
            studyResoureDTO.setNihInstitutionCode(CdConverter.convertStringToCd(
                    trialFundingWebDTO.getNihInstitutionCode()));
            studyResoureDTO.setSuffixGrantYear(StConverter.convertToSt(trialFundingWebDTO.getSuffixgrantYear()));
            studyResoureDTO.setSuffixOther(StConverter.convertToSt(trialFundingWebDTO.getSuffixOther()));
            studyResoureDTO.setSerialNumber(IntConverter.convertToInt(trialFundingWebDTO.getSerialNumber()));
            studyResoureDTO.setUserLastUpdated((StConverter.convertToSt(
                    ServletActionContext.getRequest().getRemoteUser())));
            PaRegistry.getStudyResourcingService().updateStudyResourcing(studyResoureDTO);

            query();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
            return QUERY_RESULT;    

        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    /**
     * @return result
     */
    public String delete()  {
        
        LOG.info("Entering delete");
        if (PAUtil.isEmpty(trialFundingWebDTO.getInactiveCommentText())) {
            addFieldError("trialFundingWebDTO.inactiveCommentText",
                    getText("error.trialFunding.delete.reason"));
        }
        if (hasFieldErrors()) {
            return DELETE_RESULT;
        }
        try { 
            StudyResourcingDTO studyResoureDTO = new StudyResourcingDTO();            

            studyResoureDTO = PaRegistry.getStudyResourcingService().getStudyResourceByID(
                    IiConverter.convertToIi(cbValue)); 
            studyResoureDTO.setInactiveCommentText(StConverter.convertToSt(
                    trialFundingWebDTO.getInactiveCommentText()));
            studyResoureDTO.setUserLastUpdated((StConverter.convertToSt(
                    ServletActionContext.getRequest().getRemoteUser())));
            PaRegistry.getStudyResourcingService().deleteStudyResourceByID(studyResoureDTO);

            query();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
            return QUERY_RESULT;    

        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return DELETE_RESULT;
        }
    }
    /**  
     * @return result
     */
    public String edit()  {
        LOG.info("Entering editValues");
        try { 
            StudyResourcingDTO studyR = PaRegistry.getStudyResourcingService().getStudyResourceByID(
                    IiConverter.convertToIi(cbValue));
            trialFundingWebDTO = new TrialFundingWebDTO(studyR);
            return SUCCESS;    

        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }

    private void enforceBusinessRules() {
        if (PAUtil.isEmpty(trialFundingWebDTO.getFundingMechanismCode())) {
            addFieldError("trialFundingWebDTO.fundingMechanismCode",
                    getText("error.trialFunding.funding.mechanism"));
        }
        if (PAUtil.isEmpty(trialFundingWebDTO.getNihInstitutionCode())) {
            addFieldError("trialFundingWebDTO.nihInstitutionCode",
                    getText("error.trialFunding.institution.code"));           
        }
        if (PAUtil.isEmpty(trialFundingWebDTO.getNciDivisionProgramCode())) {
            addFieldError("trialFundingWebDTO.nciDivisionProgramCode",
                    getText("error.studyProtocol.monitorCode"));
        }
        if (PAUtil.isEmpty(trialFundingWebDTO.getSerialNumber())) {
            addFieldError("trialFundingWebDTO.serialNumber",
                    getText("error.trialFunding.serial.number"));
        }
        if (PAUtil.isNotEmpty(trialFundingWebDTO.getSerialNumber())) {
            try {
                Integer i = Integer.valueOf(trialFundingWebDTO.getSerialNumber());
            } catch (NumberFormatException e) {
                addFieldError("trialFundingWebDTO.serialNumber",
                        "Please Enter a numeric value");
                
            }
        }
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
     * @return trialFundingWebDTO
     */
    public TrialFundingWebDTO getTrialFundingWebDTO() {
        return trialFundingWebDTO;
    }

    /**
     * @param trialFundingWebDTO trialFundingWebDTO
     */
    public void setTrialFundingWebDTO(TrialFundingWebDTO trialFundingWebDTO) {
        this.trialFundingWebDTO = trialFundingWebDTO;
    }

    /**
     * @return trialFundingList
     */
    public List<TrialFundingWebDTO> getTrialFundingList() {
        return trialFundingList;
    }

    /**
     * @param trialFundingList trialFundingList
     */
    public void setTrialFundingList(List<TrialFundingWebDTO> trialFundingList) {
        this.trialFundingList = trialFundingList;
    }
      
}
