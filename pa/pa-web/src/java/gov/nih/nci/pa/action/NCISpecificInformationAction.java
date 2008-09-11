package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.NCISpecificInformationWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.IsoConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;
/**
 * 
 * @author gnaveh
 *
 */

@Validation
@SuppressWarnings({"PMD.CyclomaticComplexity" , "PMD.ExcessiveMethodLength" })

public class NCISpecificInformationAction  extends ActionSupport {

    private static final Logger LOG  = Logger.getLogger(NCISpecificInformationAction.class);
    private NCISpecificInformationWebDTO nciSpecificInformationWebDTO = 
            new NCISpecificInformationWebDTO();
  
     /**  
     * @return result
     */
    public String query()  {
        LOG.info("Entering query");
        try {
            
            // Step 1 : get from StudyProtocol
            StudyProtocolDTO studyProtocolDTO = getStudyProtocol();
            
            // Step 2 : get from StudyResourcing
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
                getAttribute(Constants.STUDY_PROTOCOL_II);
            StudyResourcingDTO studyResourcingDTO = 
                    PaRegistry.getStudyResourcingService().getsummary4ReportedResource(studyProtocolIi);
            
            nciSpecificInformationWebDTO = setNCISpecificDTO(studyProtocolDTO , studyResourcingDTO);
            return SUCCESS;    
            
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    
    /**  
     * @return res
     */
    public String update()  {
        boolean error = false;
        
        //Step1 : check for any errors
        
        if (!PAUtil.isNotNullOrNotEmpty(nciSpecificInformationWebDTO.getAccrualReportingMethodCode())) {
            addActionError(getText("error.studyProtocol.accrualReportingMethodCode"));
            error = true;
        }
        if (!PAUtil.isNotNullOrNotEmpty(nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())) {
            addActionError(getText("error.studyProtocol.summaryFourFundingCategoryCode"));
            error = true;
        }
        if (error) {
            return ERROR;
        }

        
        //Step2 : retrieve the studyprotocol
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        StudyResourcingDTO srDTO = new StudyResourcingDTO();
        try {
            // Step 0 : get the studyprotocol from database
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            // Step1 : update values to StudyProtocol
            spDTO.setUserLastUpdated((StConverter.convertToSt(ServletActionContext.getRequest().getRemoteUser())));
            spDTO.setAccrualReportingMethodCode(IsoConverter.convertEnumCodeToIsoCd(
                     AccrualReportingMethodCode.getByCode(
                             nciSpecificInformationWebDTO.getAccrualReportingMethodCode())));
            // Step2 : update values to StudyResourcing
            srDTO.setTypeCode(IsoConverter.convertEnumCodeToIsoCd(
                     SummaryFourFundingCategoryCode.getByCode(
                             nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())));
            srDTO.setUserLastUpdated((StConverter.convertToSt(ServletActionContext.getRequest().getRemoteUser())));

            srDTO.setStudyProtocolIi(studyProtocolIi);
            //Step3: update studyprotocol 
            spDTO = PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);

            //Step4 : find out if summary 4 records already exists
            StudyResourcingDTO summary4ResoureDTO = 
                PaRegistry.getStudyResourcingService().getsummary4ReportedResource(studyProtocolIi);
            if (summary4ResoureDTO == null) {
                // summary 4 record does not exist,so create a new one
                summary4ResoureDTO = new StudyResourcingDTO();
                summary4ResoureDTO.setStudyProtocolIi(studyProtocolIi);
                summary4ResoureDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.TRUE));    
                summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(
                                            SummaryFourFundingCategoryCode.getByCode(
                                                    nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())));
                summary4ResoureDTO.setUserLastUpdated((StConverter.convertToSt(
                        ServletActionContext.getRequest().getRemoteUser())));
                
                PaRegistry.getStudyResourcingService().createStudyResourcing(summary4ResoureDTO);
            } else {
             // summary 4 record does exist,so so do an update
                summary4ResoureDTO.setStudyProtocolIi(studyProtocolIi);
                summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(
                        SummaryFourFundingCategoryCode.getByCode(
                                nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())));
                summary4ResoureDTO.setUserLastUpdated((StConverter.convertToSt(
                    ServletActionContext.getRequest().getRemoteUser())));
                PaRegistry.getStudyResourcingService().updateStudyResourcing(summary4ResoureDTO);
            }
        } catch (Exception e) {
            addActionError(e.getMessage());
            return ERROR;
        }    
        nciSpecificInformationWebDTO = setNCISpecificDTO(spDTO , srDTO);
        return SUCCESS;
    }

    /**
     * 
     * @return nciSpecificInformationWebDTO
     */
    public NCISpecificInformationWebDTO getNciSpecificInformationWebDTO() {
        return nciSpecificInformationWebDTO;
    }

    /**
     * 
     * @param nciSpecificInformationWebDTO nciSpecificInformationWebDTO
     */
    public void setNciSpecificInformationWebDTO(
            NCISpecificInformationWebDTO nciSpecificInformationWebDTO) {
        this.nciSpecificInformationWebDTO = nciSpecificInformationWebDTO;
    }
    
    //@todo : catch and throw paexception
    private StudyProtocolDTO getStudyProtocol() {
        StudyProtocolQueryDTO spDTO =  (StudyProtocolQueryDTO)
        ServletActionContext.getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
        Long studyProtocolId = spDTO.getStudyProtocolId();
        
        // Step2 : convert from long to Ii
        Ii ii = IsoConverter.convertIdToIsoIi(studyProtocolId);
    
        // Step3 : get the StudyProtocol Remote interface and call getStudyProtocol
        try {
            return PaRegistry.getStudyProtocolService().getStudyProtocol(ii);
        } catch (Exception e) {
            return null;
        }
    }
    
    private NCISpecificInformationWebDTO setNCISpecificDTO(StudyProtocolDTO spDTO , StudyResourcingDTO srDTO) {
        NCISpecificInformationWebDTO nciSpDTO = new NCISpecificInformationWebDTO();
        
        // Step2 : Assign the values to the action form
        if (spDTO != null  && spDTO.getAccrualReportingMethodCode() != null) {
            nciSpDTO.setAccrualReportingMethodCode(spDTO.getAccrualReportingMethodCode().getCode());
        }

        if (srDTO != null && srDTO.getTypeCode() != null) {
                nciSpDTO.setSummaryFourFundingCategoryCode(srDTO.getTypeCode().getCode());
        }
        return nciSpDTO;
        
    }


}
