package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyIndldeWebDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;
/**
 * @author Hong Gao
 *
 */
@Validation
@SuppressWarnings({"PMD.CyclomaticComplexity" , "PMD.ExcessiveMethodLength" })
public class TrialIndideAction extends ActionSupport {
    private static final String QUERY_RESULT = "query";
    private static final String EDIT_RESULT = "edit";
    private static final Logger LOG  = Logger.getLogger(TrialIndideAction.class);
    private StudyIndldeWebDTO studyIndldeWebDTO = new StudyIndldeWebDTO();
    private List<StudyIndldeWebDTO> studyIndideList;
    private IndIdeHolder holder = new IndIdeHolder();
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
            List<StudyIndldeDTO> isoList = PaRegistry.getStudyIndldeService().getByStudyProtocol(studyProtocolIi);
            if (!(isoList.isEmpty())) {                
                studyIndideList = new ArrayList<StudyIndldeWebDTO>();                
              for (StudyIndldeDTO dto : isoList) {
                  studyIndideList.add(new StudyIndldeWebDTO(dto));
              }
            } else {
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, 
                        getText("error.studyIndlde.noRecords"));
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
        if (hasFieldErrors()) {
            return ERROR;
        }
        try {            
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            StudyIndldeDTO studyIndldeDTO = new StudyIndldeDTO();
            studyIndldeDTO.setStudyProtocolIi(studyProtocolIi);
            studyIndldeDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.valueOf(holder.getGroup4())));
            studyIndldeDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(holder.getExpandedStatus()));
            studyIndldeDTO.setGrantorCode(CdConverter.convertStringToCd(holder.getSubCat()));
            studyIndldeDTO.setHolderTypeCode(CdConverter.convertStringToCd(holder.getHolderType()));
            studyIndldeDTO.setIndldeNumber(StConverter.convertToSt(holder.getIndidenumber()));
            if (holder.getHolderType().equalsIgnoreCase("NIH")) {
                studyIndldeDTO.setNihInstHolderCode(CdConverter.convertStringToCd(
                        holder.getProgramcodenihselectedvalue()));
             }
            if (holder.getHolderType().equalsIgnoreCase("NCI")) {
                studyIndldeDTO.setNciDivProgHolderCode(CdConverter.convertStringToCd(
                        holder.getProgramcodenciselectedvalue()));
            }
            studyIndldeDTO.setIndldeTypeCode(CdConverter.convertStringToCd(holder.getGroup3()));      
            PaRegistry.getStudyIndldeService().create(studyIndldeDTO);
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
        if (hasFieldErrors()) {
            return ERROR;
        }
        try { 
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II); 
            StudyIndldeDTO studyIndldeDTO = new StudyIndldeDTO();            
            studyIndldeDTO = PaRegistry.getStudyIndldeService().get(IiConverter.convertToIi(cbValue)); 
            studyIndldeDTO.setStudyProtocolIi(studyProtocolIi);
            studyIndldeDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.valueOf(holder.getGroup4())));
            studyIndldeDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(holder.getExpandedStatus()));
            studyIndldeDTO.setGrantorCode(CdConverter.convertStringToCd(holder.getSubCat()));
            studyIndldeDTO.setHolderTypeCode(CdConverter.convertStringToCd(holder.getHolderType()));
            studyIndldeDTO.setIndldeNumber(StConverter.convertToSt(holder.getIndidenumber()));
            if (holder.getHolderType().equalsIgnoreCase("NIH")) {
                studyIndldeDTO.setNihInstHolderCode(CdConverter.convertStringToCd(
                        holder.getProgramcodenihselectedvalue()));
             }
            if (holder.getHolderType().equalsIgnoreCase("NCI")) {
                studyIndldeDTO.setNciDivProgHolderCode(CdConverter.convertStringToCd(
                        holder.getProgramcodenciselectedvalue()));
            }
            studyIndldeDTO.setIndldeTypeCode(CdConverter.convertStringToCd(holder.getGroup3()));  
            PaRegistry.getStudyIndldeService().update(studyIndldeDTO);
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

        LOG.info("Entering delete from SubGroupsAction");
        try {
            PaRegistry.getStudyIndldeService().delete(IiConverter.convertToIi(cbValue));
            query();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
            return QUERY_RESULT;

        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return SUCCESS;
        }
    }

    /**  
     * @return result
     */
    public String edit()  {
        LOG.info("Entering editValues");
        try {
            StudyIndldeDTO studyIndlde = PaRegistry.getStudyIndldeService().get(IiConverter.convertToIi(cbValue)); 
            holder = new IndIdeHolder(studyIndlde);
            return EDIT_RESULT;    
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
     * @return the studyIndldeWebDTO
     */
    public StudyIndldeWebDTO getStudyIndldeWebDTO() {
        return studyIndldeWebDTO;
    }

    /**
     * @param studyIndldeWebDTO the studyIndldeWebDTO to set
     */
    public void setStudyIndldeWebDTO(StudyIndldeWebDTO studyIndldeWebDTO) {
        this.studyIndldeWebDTO = studyIndldeWebDTO;
    }

    /**
     * @return the studyIndideList
     */
    public List<StudyIndldeWebDTO> getStudyIndideList() {
        return studyIndideList;
    }

    /**
     * @param studyIndideList the studyIndideList to set
     */
    public void setStudyIndideList(List<StudyIndldeWebDTO> studyIndideList) {
        this.studyIndideList = studyIndideList;
    }

    /**
     * @return the holder
     */
    public IndIdeHolder getHolder() {
        return holder;
    }

    /**
     * @param holder the holder to set
     */
    public void setHolder(IndIdeHolder holder) {
        this.holder = holder;
    }
}
