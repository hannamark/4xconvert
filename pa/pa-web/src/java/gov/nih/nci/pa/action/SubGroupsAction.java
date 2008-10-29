package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.AbstractEntity;
import gov.nih.nci.pa.dto.SubGroupsWebDTO;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
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
/**
 * @author Kalpana Guthikonda
 * @since 10/13/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class SubGroupsAction extends ActionSupport {

    private static final Logger LOG  = Logger.getLogger(TrialDocumentAction.class);
    private List<SubGroupsWebDTO> subGroupsList;
    private SubGroupsWebDTO subGroupsWebDTO = new SubGroupsWebDTO();
    private Long id = null;
    private String page;
    /**
     * @return result
     */
    public String query()  {
        LOG.info("Entering query from SubGroupsAction");
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            List<StratumGroupDTO> isoList = PaRegistry.getSubGroupsService().
            getDocumentsByStudyProtocol(studyProtocolIi);
            if (!(isoList.isEmpty())) {
                subGroupsList = new ArrayList<SubGroupsWebDTO>();
                for (StratumGroupDTO dto : isoList) {
                    subGroupsList.add(new SubGroupsWebDTO(dto));
                }
            } else {
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE,
                        getText("error.subGroups.noRecords"));
            }
            return SUCCESS;

        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return ERROR;
        }
    }

    /**
     * @return result
     */
     public String input() {
         return INPUT;
     }

     /**
      * @return result
      */
     public String create() {
         LOG.info("Entering create from SubGroupsAction");
         enforceBusinessRules();
         if (hasFieldErrors()) {
             return INPUT;
         }
         try {
             Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
             getAttribute(Constants.STUDY_PROTOCOL_II);
             StratumGroupDTO sgDTO = new StratumGroupDTO();
             sgDTO.setStudyProtocolIi(studyProtocolIi);
             sgDTO.setDescription(StConverter.convertToSt(subGroupsWebDTO.getDescription()));
             sgDTO.setGroupNumberText(StConverter.convertToSt(subGroupsWebDTO.getGroupNumberText()));
             sgDTO.setUserLastUpdated((StConverter.convertToSt(
                     ServletActionContext.getRequest().getRemoteUser())));
             PaRegistry.getSubGroupsService().create(sgDTO);
             query();
             ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
             return SUCCESS;
         } catch (Exception e) {
             ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
             return INPUT;
         }
     }


     /**
      * @return result
      */
     public String edit() {
         LOG.info("Entering edit from SubGroupsAction");
         try {
             StratumGroupDTO  sgDTO =
                 PaRegistry.getSubGroupsService().get(IiConverter.convertToIi(id));
             subGroupsWebDTO = new SubGroupsWebDTO(sgDTO);
         } catch (Exception e) {
             ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
             return INPUT;
         }
         return INPUT;
     }

     /**
      * @return result
      */
     public String update() {
         LOG.info("Entering update from SubGroupsAction");
         enforceBusinessRules();
         if (hasFieldErrors()) {
             return INPUT;
         }
         try {

             Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
             getAttribute(Constants.STUDY_PROTOCOL_II);
             StratumGroupDTO  sgDTO = new StratumGroupDTO();
             sgDTO.setIdentifier(IiConverter.convertToIi(id));
             sgDTO.setStudyProtocolIi(studyProtocolIi);
             sgDTO.setDescription(StConverter.convertToSt(subGroupsWebDTO.getDescription()));
             sgDTO.setGroupNumberText(StConverter.convertToSt(subGroupsWebDTO.getGroupNumberText()));
             sgDTO.setUserLastUpdated((StConverter.convertToSt(
                     ServletActionContext.getRequest().getRemoteUser())));
             PaRegistry.getSubGroupsService().update(sgDTO);
             ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
             query();
         } catch (Exception e) {
             ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
             return INPUT;
         }
         return SUCCESS;
     }

     /**
      * @return result
      */
     public String delete()  {

         LOG.info("Entering delete from SubGroupsAction");
         try {
             PaRegistry.getSubGroupsService().delete(IiConverter.convertToIi(id));
             query();
             ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
             return SUCCESS;

         } catch (Exception e) {
             ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
             return SUCCESS;
         }
     }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return subGroupsList
     */
    public List<SubGroupsWebDTO> getSubGroupsList() {
        return subGroupsList;
    }

    /**
     * @param subGroupsList subGroupsList
     */
    public void setSubGroupsList(List<SubGroupsWebDTO> subGroupsList) {
        this.subGroupsList = subGroupsList;
    }

    /**
     * @return subGroupsWebDTO
     */
    public SubGroupsWebDTO getSubGroupsWebDTO() {
        return subGroupsWebDTO;
    }

    /**
     * @param subGroupsWebDTO subGroupsWebDTO
     */
    public void setSubGroupsWebDTO(SubGroupsWebDTO subGroupsWebDTO) {
        this.subGroupsWebDTO = subGroupsWebDTO;
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
     * This method is used to enforce the business rules which are form specific or
     * based on an interaction between services.
     */
    private void enforceBusinessRules() {
        if (PAUtil.isEmpty(subGroupsWebDTO.getDescription())) {
            addFieldError("subGroupsWebDTO.description",
                    getText("error.subGroups.description"));
        }
        if (PAUtil.isEmpty(subGroupsWebDTO.getGroupNumberText())) {
            addFieldError("subGroupsWebDTO.code",
                    getText("error.subGroups.code"));
        }
        if (subGroupsWebDTO.getDescription().length() > AbstractEntity.LONG_TEXT_LENGTH) {
            addFieldError("subGroupsWebDTO.description",
                    getText("Cannot enter more than 200 characters"));

        }

    }
}

