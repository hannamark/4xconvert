package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.NCISpecificInformationWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.service.PAException;
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
        String ret = null;
        try {
            
            nciSpecificInformationWebDTO = setNciSpDto(getStudyProtocol());
            ret = SUCCESS;    
            
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            ret = ERROR;
        }
        LOG.info("Leaving query");
        return ret;
    }
    
    /**  
     * @return res
     */
    public String update()  {
        boolean error = false;
        
        //Step1 : check for any errors
        //@todo: perform this error only for Interventional trial type, currently trial type
        //is not yet added to the code
        
        if (!PAUtil.isNotNullOrNotEmpty(nciSpecificInformationWebDTO.getMonitorCode())) {
            addActionError(getText("error.studyProtocol.monitorCode"));
            error = true;
        }
        if (!PAUtil.isNotNullOrNotEmpty(nciSpecificInformationWebDTO.getAccrualReportingMethodCode())) {
            addActionError(getText("error.studyProtocol.accrualReportingMethodCode"));
            error = true;
        }
        /*
        if (!PAUtil.isNotNullOrNotEmpty(nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())) {
            addActionError(getText("error.studyProtocol.summaryFourFundingCategoryCode"));
            error = true;
        }
        */
        if (error) {
            return ERROR;
        }

        
        //Step2 : retrieve the studyprotocol
        StudyProtocolDTO spIsoDTO = null;
        try {
             spIsoDTO = getStudyProtocol();

             //Step3: overwrite the new values
             spIsoDTO.setMonitorCode(IsoConverter.convertEnumCodeToIsoCd(
                     MonitorCode.getByCode(nciSpecificInformationWebDTO.getMonitorCode())));
             spIsoDTO.setAccrualReportingMethodCode(IsoConverter.convertEnumCodeToIsoCd(
                     AccrualReportingMethodCode.getByCode(
                             nciSpecificInformationWebDTO.getAccrualReportingMethodCode())));
             ///spIsoDTO.setSummaryFourFundingCategoryCode(IsoConverter.convertEnumCodeToIsoCd(
             ///        SummaryFourFundingCategoryCode.getByCode(
             ///                nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())));
             //Step4: update studyprotocol 
             spIsoDTO = PaRegistry.getStudyProtocolService().updateStudyProtocol(spIsoDTO);
        } catch (Exception e) {
            addActionError(e.getMessage());
            return ERROR;
        }    
        
        nciSpecificInformationWebDTO = setNciSpDto(spIsoDTO);
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
    private StudyProtocolDTO getStudyProtocol()  {
        StudyProtocolQueryDTO spDTO =  (StudyProtocolQueryDTO)
        ServletActionContext.getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
        Long studyProtocolId = spDTO.getStudyProtocolId();
        
        // Step2 : convert from long to Ii
        Ii ii = IsoConverter.convertIdToIsoIi(studyProtocolId);
    
        // Step3 : get the StudyProtocol Remote interface and call getStudyProtocol
        try {
            return PaRegistry.getStudyProtocolService().getStudyProtocol(ii);
        } catch (PAException e) {
            return null;
        }
    }
    
    private NCISpecificInformationWebDTO setNciSpDto(StudyProtocolDTO spIsoDTO) {
        NCISpecificInformationWebDTO nciSpDTO = new NCISpecificInformationWebDTO();
        
        // Step2 : Assign the values to the action form
        if (spIsoDTO.getAccrualReportingMethodCode() != null) {
            nciSpDTO.setAccrualReportingMethodCode(
                spIsoDTO.getAccrualReportingMethodCode().getCode());
        }
        if (spIsoDTO.getMonitorCode() != null) {
            nciSpDTO.setMonitorCode(spIsoDTO.getMonitorCode().getCode());
        }
        /*
        if (spIsoDTO.getSummaryFourFundingCategoryCode() != null) {
            nciSpDTO.setSummaryFourFundingCategoryCode(
                    spIsoDTO.getSummaryFourFundingCategoryCode().getCode());
        }
        */
        
        return nciSpDTO;
        
    }


}
