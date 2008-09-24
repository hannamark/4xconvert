package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.TrialFundingWebDTO;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
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
                addActionError(getText("error.trialFunding.noRecords"));
            }
            return QUERY_RESULT;    

        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    
    /**
     * @return result
     */
    public String create()  {
        
        LOG.info("Entering create");
        boolean error = false;
        if (!PAUtil.isNotNullOrNotEmpty(trialFundingWebDTO.getFundingMechanismCode())) {
            addActionError(getText("error.trialFunding.funding.mechanism"));
            error = true;
        }
        if (!PAUtil.isNotNullOrNotEmpty(trialFundingWebDTO.getNihInstitutionCode())) {
            addActionError(getText("error.trialFunding.institution.code"));
            error = true;
        }
        if (!PAUtil.isNotNullOrNotEmpty(trialFundingWebDTO.getNciDivisionProgramCode())) {
            addActionError(getText("error.studyProtocol.monitorCode"));
            error = true;
        }
        if (!PAUtil.isNotNullOrNotEmpty(trialFundingWebDTO.getSerialNumber())) {
            addActionError(getText("error.trialFunding.serial.number"));
            error = true;
        }
        if (error) {
            return ERROR;
        }
        try {            
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II); 
            StudyResourcingDTO studyResoureDTO = new StudyResourcingDTO();            

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
            studyResoureDTO.setSerialNumber(StConverter.convertToSt(trialFundingWebDTO.getSerialNumber()));
            studyResoureDTO.setUserLastUpdated((StConverter.convertToSt(
                    ServletActionContext.getRequest().getRemoteUser())));
            PaRegistry.getStudyResourcingService().createStudyResourcing(studyResoureDTO);

            query();
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
        boolean error = false;
        if (!PAUtil.isNotNullOrNotEmpty(trialFundingWebDTO.getFundingMechanismCode())) {
            addActionError(getText("error.trialFunding.funding.mechanism"));
            error = true;
        }
        if (!PAUtil.isNotNullOrNotEmpty(trialFundingWebDTO.getNihInstitutionCode())) {
            addActionError(getText("error.trialFunding.institution.code"));
            error = true;
        }
        if (!PAUtil.isNotNullOrNotEmpty(trialFundingWebDTO.getNciDivisionProgramCode())) {
            addActionError(getText("error.studyProtocol.monitorCode"));
            error = true;
        }
        if (!PAUtil.isNotNullOrNotEmpty(trialFundingWebDTO.getSerialNumber())) {
            addActionError(getText("error.trialFunding.serial.number"));
            error = true;
        }
        if (error) {
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
            studyResoureDTO.setSerialNumber(StConverter.convertToSt(trialFundingWebDTO.getSerialNumber()));
            studyResoureDTO.setUserLastUpdated((StConverter.convertToSt(
                    ServletActionContext.getRequest().getRemoteUser())));
            PaRegistry.getStudyResourcingService().updateStudyResourcing(studyResoureDTO);

            query();
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
        boolean error = false;
        if (!PAUtil.isNotNullOrNotEmpty(trialFundingWebDTO.getInactiveCommentText())) {
            addActionError(getText("error.trialFunding.delete.reason"));
            error = true;
        }
        if (error) {
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
            return QUERY_RESULT;    

        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
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
