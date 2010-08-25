package gov.nih.nci.registry.action;


import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.StudyIndldeWebDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.util.CreateIfNull;
import com.opensymphony.xwork2.util.Element;

/**
 * The Class UpdateTrialAction.
 *
 * @author Vrushali
 */
@SuppressWarnings("unchecked")
public class UpdateTrialAction extends ManageFileAction implements ServletResponseAware {

    private static final long serialVersionUID = 1L;

    private HttpServletResponse servletResponse;

    private static final Logger LOG = Logger.getLogger(UpdateTrialAction.class);

    private TrialDTO trialDTO = new TrialDTO();
    private String trialAction = null;
    private String studyProtocolId = null;
    private static String sessionTrialDTO = "trialDTO";

    private final TrialUtil trialUtil = new TrialUtil();

    //for update
    @CreateIfNull(value = true)
    @Element (value = gov.nih.nci.pa.dto.PaOrganizationDTO.class)
    private List<PaOrganizationDTO> collaborators = new ArrayList<PaOrganizationDTO>();

    @CreateIfNull(value = true)
    @Element (value = gov.nih.nci.pa.dto.PaOrganizationDTO.class)
    private List<PaOrganizationDTO> participatingSitesList = new ArrayList<PaOrganizationDTO>();

    @CreateIfNull(value = true)
    @Element (value = gov.nih.nci.registry.dto.StudyIndldeWebDTO.class)
    private List <StudyIndldeWebDTO> indIdeUpdateDtos = new ArrayList<StudyIndldeWebDTO>();

    @CreateIfNull(value = true)
    @Element (value = gov.nih.nci.registry.dto.TrialFundingWebDTO.class)
    private List<TrialFundingWebDTO> fundingAddDtos = new ArrayList<TrialFundingWebDTO>();

    @CreateIfNull(value = true)
    @Element (value = gov.nih.nci.registry.dto.TrialIndIdeDTO.class)
    private List <TrialIndIdeDTO> indIdeAddDtos = new ArrayList<TrialIndIdeDTO>();

    @CreateIfNull(value = true)
    @Element (value = gov.nih.nci.registry.dto.TrialFundingWebDTO.class)
    private List<TrialFundingWebDTO> fundingDtos = new ArrayList<TrialFundingWebDTO>();

    private String programcodenihselectedvalue;
    private String programcodenciselectedvalue;
    private PaOrganizationDTO paOrganizationDTO;
    private StudyIndldeWebDTO studyIndldeWebDTO;
    private TrialFundingWebDTO trialFundingDTO;
    private TrialIndIdeDTO trialIndIdeDTO;
    private int indIdeUpdateDtosLen = 0;

    /**
     * Gets the study indlde web dto.
     *
     * @return the studyIndldeWebDTO
     */
    public StudyIndldeWebDTO getStudyIndldeWebDTO() {
        return studyIndldeWebDTO;
    }

    /**
     * Sets the study indlde web dto.
     *
     * @param studyIndldeWebDTO the studyIndldeWebDTO to set
     */
    public void setStudyIndldeWebDTO(StudyIndldeWebDTO studyIndldeWebDTO) {
        this.studyIndldeWebDTO = studyIndldeWebDTO;
    }

    /**
     * Gets the trial funding dto.
     *
     * @return the trialFundingDTO
     */
    public TrialFundingWebDTO getTrialFundingDTO() {
        return trialFundingDTO;
    }

    /**
     * Sets the trial funding dto.
     *
     * @param trialFundingDTO the trialFundingDTO to set
     */
    public void setTrialFundingDTO(TrialFundingWebDTO trialFundingDTO) {
        this.trialFundingDTO = trialFundingDTO;
    }

    /**
     * Gets the trial ind ide dto.
     *
     * @return the trialIndIdeDTO
     */
    public TrialIndIdeDTO getTrialIndIdeDTO() {
        return trialIndIdeDTO;
    }

    /**
     * Sets the trial ind ide dto.
     *
     * @param trialIndIdeDTO the trialIndIdeDTO to set
     */
    public void setTrialIndIdeDTO(TrialIndIdeDTO trialIndIdeDTO) {
        this.trialIndIdeDTO = trialIndIdeDTO;
    }

    /**
     * Gets the pa organization dto.
     *
     * @return the paOrganizationDTO
     */
    public PaOrganizationDTO getPaOrganizationDTO() {
        return paOrganizationDTO;
    }

    /**
     * Sets the pa organization dto.
     *
     * @param paOrganizationDTO the paOrganizationDTO to set
     */
    public void setPaOrganizationDTO(PaOrganizationDTO paOrganizationDTO) {
        this.paOrganizationDTO = paOrganizationDTO;
    }

    /**
     * Sets the servlet response.
     *
     * @param response servletResponse
     */
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }

    /**
     * Gets the servlet response.
     *
     * @return servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }

    /**
     * Gets the trial dto.
     *
     * @return the trialDTO
     */
    public TrialDTO getTrialDTO() {
        return trialDTO;
    }

    /**
     * Sets the trial dto.
     *
     * @param trialDTO the trialDTO to set
     */
    public void setTrialDTO(TrialDTO trialDTO) {
        this.trialDTO = trialDTO;
    }

   /**
    * Gets the trial action.
    *
    * @return the trialAction
    */
   public String getTrialAction() {
       return trialAction;
   }

   /**
    * Sets the trial action.
    *
    * @param trialAction the trialAction to set
    */
   public void setTrialAction(String trialAction) {
       this.trialAction = trialAction;
   }

   /**
    * Gets the study protocol id.
    *
    * @return the studyProtocolId
    */
   public String getStudyProtocolId() {
       return studyProtocolId;
   }

   /**
    * Sets the study protocol id.
    *
    * @param studyProtocolId the studyProtocolId to set
    */
   public void setStudyProtocolId(String studyProtocolId) {
       this.studyProtocolId = studyProtocolId;
   }

   /**
    * Gets the collaborators.
    *
    * @return the collaborators
    */
   public List<PaOrganizationDTO> getCollaborators() {
       return collaborators;
   }

   /**
    * Sets the collaborators.
    *
    * @param collaborators the collaborators to set
    */
   public void setCollaborators(List<PaOrganizationDTO> collaborators) {
       this.collaborators = collaborators;
   }

   /**
    * Gets the participating sites.
    *
    * @return the participatingSites
    */
   public List<PaOrganizationDTO> getParticipatingSitesList() {
       return participatingSitesList;
   }

   /**
    * Sets the participating sites.
    *
    * @param participatingSites the participatingSites to set
    */
   public void setParticipatingSitesList(List<PaOrganizationDTO> participatingSites) {
       this.participatingSitesList = participatingSites;
   }

   /**
    * Gets the ind ide update dtos.
    *
    * @return the indIdeUpdateDtos
    */
   public List<StudyIndldeWebDTO> getIndIdeUpdateDtos() {
       return indIdeUpdateDtos;
   }

   /**
    * Sets the ind ide update dtos.
    *
    * @param indIdeUpdateDtos the indIdeUpdateDtos to set
    */
   public void setIndIdeUpdateDtos(List<StudyIndldeWebDTO> indIdeUpdateDtos) {
       this.indIdeUpdateDtos = indIdeUpdateDtos;
   }

   /**
    * Gets the funding add dtos.
    *
    * @return the fundingAddDtos
    */
   public List<TrialFundingWebDTO> getFundingAddDtos() {
       return fundingAddDtos;
   }

   /**
    * Sets the funding add dtos.
    *
    * @param fundingAddDtos the fundingAddDtos to set
    */
   public void setFundingAddDtos(List<TrialFundingWebDTO> fundingAddDtos) {
       this.fundingAddDtos = fundingAddDtos;
   }

   /**
    * Gets the ind ide add dtos.
    *
    * @return the indIdeAddDtos
    */
   public List<TrialIndIdeDTO> getIndIdeAddDtos() {
       return indIdeAddDtos;
   }

   /**
    * Sets the ind ide add dtos.
    *
    * @param indIdeAddDtos the indIdeAddDtos to set
    */
   public void setIndIdeAddDtos(List<TrialIndIdeDTO> indIdeAddDtos) {
       this.indIdeAddDtos = indIdeAddDtos;
   }
   /**
    * Gets the programcodenihselectedvalue.
    *
    * @return the programcodenihselectedvalue
    */
   public String getProgramcodenihselectedvalue() {
       return programcodenihselectedvalue;
   }

   /**
    * Sets the programcodenihselectedvalue.
    *
    * @param programcodenihselectedvalue the programcodenihselectedvalue to set
    */
   public void setProgramcodenihselectedvalue(String programcodenihselectedvalue) {
       this.programcodenihselectedvalue = programcodenihselectedvalue;
   }

   /**
    * Gets the programcodenciselectedvalue.
    *
    * @return the programcodenciselectedvalue
    */
   public String getProgramcodenciselectedvalue() {
       return programcodenciselectedvalue;
   }

   /**
    * Sets the programcodenciselectedvalue.
    *
    * @param programcodenciselectedvalue the programcodenciselectedvalue to set
    */
   public void setProgramcodenciselectedvalue(String programcodenciselectedvalue) {
       this.programcodenciselectedvalue = programcodenciselectedvalue;
   }

   /**
    * Gets the funding dtos.
    *
    * @return the fundingDtos
    */
   public List<TrialFundingWebDTO> getFundingDtos() {
       return fundingDtos;
   }

   /**
    * Sets the funding dtos.
    *
    * @param fundingDtos the fundingDtos to set
    */
   public void setFundingDtos(List<TrialFundingWebDTO> fundingDtos) {
       this.fundingDtos = fundingDtos;
   }

    /**
     * View.
     *
     * @return res
     */
    public String view() {
        //clear the session
        TrialValidator.removeSessionAttributes();
        try {
                String pId = ServletActionContext.getRequest().getParameter("studyProtocolId");
                if (studyProtocolId == null) {
                    studyProtocolId = pId;
                }
                Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(Long.parseLong(studyProtocolId));
                TrialUtil util = new TrialUtil();
                util.getTrialDTOFromDb(studyProtocolIi, trialDTO);
                synchActionWithDTO();
                TrialValidator.addSessionAttributesForUpdate(trialDTO);
                setIndIdeUpdateDtosLen(trialDTO.getIndIdeUpdateDtos().size());
                ServletActionContext.getRequest().getSession().setAttribute(sessionTrialDTO, trialDTO);
                setPageFrom("updateTrial");
            LOG.info("Trial retrieved: " + trialDTO.getOfficialTitle());
        } catch (Exception e) {
            LOG.error("Exception occured while querying trial " + e);
            return ERROR;
        }
        return SUCCESS;
    }


  /**
   * Synch action with dto.
   */
  private void synchActionWithDTO() {

      if (trialDTO.getCollaborators() != null && !trialDTO.getCollaborators().isEmpty()) {
        setCollaborators(trialDTO.getCollaborators());
      }
      if (trialDTO.getParticipatingSites() != null && !trialDTO.getParticipatingSites().isEmpty()) {
       setParticipatingSitesList(trialDTO.getParticipatingSites());
      }
      if (trialDTO.getIndIdeUpdateDtos() != null && !trialDTO.getIndIdeUpdateDtos().isEmpty()) {
          setIndIdeUpdateDtos(trialDTO.getIndIdeUpdateDtos());
      }

      if (trialDTO.getFundingDtos() != null && !trialDTO.getFundingDtos().isEmpty()) {
          setFundingDtos(trialDTO.getFundingDtos());
      }
      if (trialDTO.getIndIdeAddDtos() != null && !trialDTO.getIndIdeAddDtos().isEmpty()) {
       setIndIdeAddDtos(trialDTO.getIndIdeAddDtos());
      }
      if (trialDTO.getFundingAddDtos() != null && !trialDTO.getFundingAddDtos().isEmpty()) {
       setFundingAddDtos(trialDTO.getFundingAddDtos());
      }
  }

 /**
  * Synch dto with action.
  */
 private void synchDTOWithAction() {

      trialDTO.setCollaborators(getCollaborators());
      trialDTO.setParticipatingSites(getParticipatingSitesList());
      trialDTO.setIndIdeUpdateDtos(getIndIdeUpdateDtos());
      trialDTO.setFundingDtos(getFundingDtos());

   }

    /**
     * Clears the session variables and redirect to search.
     *
     * @return s
     */
    public String cancel() {
        TrialValidator.removeSessionAttributes();
        ServletActionContext.getRequest().getSession().removeAttribute("grantAddList");
        ServletActionContext.getRequest().getSession().removeAttribute("indIdeAddList");
        return "redirect_to_search";
    }

    /**
     * Review update.
     *
     * @return s
     */
    public String reviewUpdate() {
        try {
            clearErrorsAndMessages();
            enforceBusinessRules();
            List<TrialDocumentWebDTO> docDTOList = addDocDTOToList();
            if (hasFieldErrors()) {
                ServletActionContext.getRequest().setAttribute(
                        "failureMessage" , "The form has errors and could not be submitted, "
                        + "please check the fields highlighted below");
                TrialValidator.addSessionAttributes(trialDTO);
                trialUtil.populateRegulatoryList(trialDTO);
                synchActionWithDTO();
            return ERROR;
            }

            if (hasActionErrors()) {
                TrialValidator.addSessionAttributes(trialDTO);
                synchActionWithDTO();
                trialUtil.populateRegulatoryList(trialDTO);
                return ERROR;
            }

            populateList(docDTOList);
            trialDTO.setDocDtos(docDTOList);

            //add the IndIde,FundingList
            List<TrialIndIdeDTO> indAddList = (List<TrialIndIdeDTO>) ServletActionContext.getRequest()
           .getSession().getAttribute(Constants.INDIDE_ADD_LIST);
           if (indAddList != null) {
               trialDTO.setIndIdeAddDtos(indAddList);
               setIndIdeAddDtos(indAddList);
           }

           List<TrialFundingWebDTO> grantAddList = (List<TrialFundingWebDTO>) ServletActionContext.getRequest()
           .getSession().getAttribute(Constants.GRANT_ADD_LIST);
           if (grantAddList != null) {
               trialDTO.setFundingAddDtos(grantAddList);
               setFundingAddDtos(grantAddList);
           }
           if (trialDTO.isXmlRequired()) {
              trialUtil.setOversgtInfo(trialDTO);
           }
           List<Ii> otherIdsList =
               (List<Ii>) ServletActionContext.getRequest()
                 .getSession().getAttribute(Constants.SECONDARY_IDENTIFIERS_LIST);
              if (otherIdsList != null) {
                  trialDTO.setSecondaryIdentifierAddList(otherIdsList);
              }
           synchDTOWithAction();

        } catch (IOException e) {
            LOG.error(e.getMessage());
            synchActionWithDTO();
            trialUtil.populateRegulatoryList(trialDTO);
            return ERROR;
        } catch (PAException e) {
            LOG.error(e.getMessage());
            synchActionWithDTO();
            trialUtil.populateRegulatoryList(trialDTO);
            return ERROR;
        }
        TrialValidator.removeSessionAttributes();
        ServletActionContext.getRequest().getSession().setAttribute(sessionTrialDTO, trialDTO);
        LOG.info("Calling the review page...");
        return "review";
    }

    /**
     * Edits the.
     *
     * @return s
     */
    public String edit() {
        trialDTO = (TrialDTO) ServletActionContext.getRequest().getSession().getAttribute(sessionTrialDTO);
        setDocumentsInSession(trialDTO);
        synchActionWithDTO();
        trialUtil.populateRegulatoryList(trialDTO);
        TrialValidator.addSessionAttributes(trialDTO);
        return "edit";
    }

    /**
     * Update.
     *
     * @return s
     */
    public String update() {
        trialDTO = (TrialDTO) ServletActionContext.getRequest().getSession().getAttribute(sessionTrialDTO);
        if (trialDTO == null) {
           synchActionWithDTO();
           return ERROR;
        }
        TrialUtil util = new TrialUtil();
        Ii updateId = null;
        try {
            Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(Long.parseLong(trialDTO.getIdentifier()));
            //get the studyProtocol DTO
            StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            util.addSecondaryIdentifiers(spDTO, trialDTO);
            util.updateStudyProtcolDTO(spDTO, trialDTO);
            spDTO.setUserLastCreated(StConverter.convertToSt(ServletActionContext.getRequest().getRemoteUser()));

            //set the overall status
            StudyOverallStatusDTO sosDto = null;
            sosDto = getOverallStatusForUpdate(util);
            //add the new irb doc
             DocumentDTO documentDTO = util.convertToISODocument(trialDTO.getDocDtos(), studyProtocolIi);
             List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
             if (documentDTO != null) {
                 documentDTOs.add(documentDTO);
             }
           //summary4 info
            StudyResourcingDTO summary4studyResourcingDTO = util.convertToSummary4StudyResourcingDTO(
                    trialDTO, studyProtocolIi);
            OrganizationDTO summary4orgDTO = util.convertToSummary4OrgDTO(trialDTO);

            StudyContactDTO studyContactDTO = null;
            StudySiteContactDTO studyParticipationContactDTO = null;
            Ii responsiblePartyContactIi = null;
            // updated only if the ctGovXmlRequired is true
            if (spDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                if (trialDTO.getResponsiblePartyType().equalsIgnoreCase("pi")) {
                    studyContactDTO = new StudyContactDTO();
                    util.convertToStudyContactDTO(trialDTO, studyContactDTO);

                } else {
                    studyParticipationContactDTO = new StudySiteContactDTO();
                    util.convertToStudySiteContactDTO(trialDTO, studyParticipationContactDTO);

                   if (trialDTO.getResponsiblePersonName() != null && !trialDTO.getResponsiblePersonName().equals("")) {
                        responsiblePartyContactIi =
                          IiConverter.convertToPoPersonIi(trialDTO.getResponsiblePersonIdentifier());
                   }
                   if (trialDTO.getResponsibleGenericContactName() != null
                          && !trialDTO.getResponsibleGenericContactName().equals("")) {
                       responsiblePartyContactIi = IiConverter.
                          convertToPoOrganizationalContactIi(trialDTO.getResponsiblePersonIdentifier());
                   }
               }
            }
            //indide updates and adds

            List<StudyIndldeDTO> studyIndldeDTOList = new ArrayList<StudyIndldeDTO>();
            //updated
            if (trialDTO.getIndIdeUpdateDtos() != null && trialDTO.getIndIdeUpdateDtos().size() > 0) {
                for (StudyIndldeWebDTO webdto : trialDTO.getIndIdeUpdateDtos()) {
                    studyIndldeDTOList.add(convetToIndIdeWebDTO(webdto, studyProtocolIi));
                }
            }
            //newly added
            if (trialDTO.getIndIdeAddDtos() != null && trialDTO.getIndIdeAddDtos().size() > 0) {
                List<StudyIndldeDTO> studyIndldeDTOs =
                        util.convertISOINDIDEList(trialDTO.getIndIdeAddDtos(), studyProtocolIi);
                studyIndldeDTOList.addAll(studyIndldeDTOs);
            }
            //funding updates and adds
            List<StudyResourcingDTO> studyResourcingDTOs = new ArrayList<StudyResourcingDTO>();
            if (trialDTO.getFundingDtos() != null && trialDTO.getFundingDtos().size() > 0) {
                for (TrialFundingWebDTO webdto : trialDTO.getFundingDtos()) {
                    studyResourcingDTOs.add(convertToStudyResourcingDTO(webdto, studyProtocolIi));
                }
            }
            //newly added
            if (trialDTO.getFundingAddDtos() != null && trialDTO.getFundingAddDtos().size() > 0) {
             List<StudyResourcingDTO> studyResourcingAddDTOs =
                         util.convertISOGrantsList(trialDTO.getFundingAddDtos(), studyProtocolIi);
             studyResourcingDTOs.addAll(studyResourcingAddDTOs);
            }

            // updated only if the ctGovXmlRequired is true
            StudyRegulatoryAuthorityDTO studyRegAuthDTO = null;
            if (spDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
              //update StudyRegulatory
              studyRegAuthDTO = util.getStudyRegAuth(studyProtocolIi, trialDTO);
            }
            //collaborators update - send the collaborators list
            List<StudySiteDTO> collaboratorsDTOList = getCollaboratorsForUpdate(trialDTO.getCollaborators());

            //ps update- send the participating sites list
            List<StudySiteAccrualStatusDTO> pssDTOList =
               getParticipatingSitesForUpdate(trialDTO.getParticipatingSites());

            //list of studysite dtos with updated program code
            List<StudySiteDTO> prgCdUpdatedList = getStudySiteToUpdateProgramCode(trialDTO.getParticipatingSites());

            updateId = studyProtocolIi;
            List<StudySiteDTO> studyIdentifierDTOs = new ArrayList<StudySiteDTO>();
            studyIdentifierDTOs.add(util.convertToNCTStudySiteDTO(trialDTO, studyProtocolIi));
            studyIdentifierDTOs.add(util.convertToDCPStudySiteDTO(trialDTO, studyProtocolIi));
            studyIdentifierDTOs.add(util.convertToCTEPStudySiteDTO(trialDTO, studyProtocolIi));
            //call the service to invoke the update method
            PaRegistry.getTrialRegistrationService().
                        update(spDTO, sosDto, studyIdentifierDTOs, studyIndldeDTOList, studyResourcingDTOs,
                                documentDTOs, studyContactDTO, studyParticipationContactDTO,
                               summary4orgDTO, summary4studyResourcingDTO, responsiblePartyContactIi,
                              studyRegAuthDTO, collaboratorsDTOList,
                              pssDTOList, prgCdUpdatedList, BlConverter.convertToBl(Boolean.FALSE));
            TrialValidator.removeSessionAttributes();
            ServletActionContext.getRequest().getSession().setAttribute("protocolId", updateId.getExtension());
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", updateId);
        } catch (PAException e) {
            if (!RegistryUtil.setFailureMessage(e)) {
                addActionError("Error occured, please try again");
            }
            LOG.error("Exception occured while updating trial", e);
            TrialValidator.addSessionAttributes(trialDTO);
            trialUtil.populateRegulatoryList(trialDTO);
            synchActionWithDTO();
            ServletActionContext.getRequest().getSession().removeAttribute("secondaryIdentifiersList");
            trialDTO.setSecondaryIdentifierAddList(null);
            trialUtil.removeAssignedIdentifierFromSecondaryIds(trialDTO);
            setDocumentsInSession(trialDTO);
            return ERROR;
        }
        setTrialAction("update");
        ServletActionContext.getRequest().getSession().removeAttribute("grantAddList");
        ServletActionContext.getRequest().getSession().removeAttribute("indIdeAddList");
     return "redirect_to_search";
    }


    /**
     * validate the submit trial form elements.
     *
     * @throws PAException the PA exception
     */
    private void enforceBusinessRules() throws PAException {
        TrialValidator validator = new TrialValidator();
        Map<String, String> err = new HashMap<String, String>();
        err = validator.validateTrialDTO(trialDTO);
        addErrors(err);
        // validate trial status and dates specific for amendment
        if (StringUtils.isNotEmpty(trialDTO.getStatusCode())
                && RegistryUtil.isValidDate(trialDTO.getStatusDate())
                && RegistryUtil.isValidDate(trialDTO.getCompletionDate())
                && RegistryUtil.isValidDate(trialDTO.getStartDate())
                && validator.isTrialStatusOrDateChanged(trialDTO)) {
            Collection<String> errDate = validator.enforceBusinessRulesForDates(trialDTO);
            if (!errDate.isEmpty()) {
                for (String msg : errDate) {
                    addActionError(msg);
                }
            }
        }
         //Add other validation rules
        //Regulatory information validation moved to validator

        int ind = 0;
        if (getCollaborators() != null && !getCollaborators().isEmpty()) {
            for (PaOrganizationDTO coll : getCollaborators()) {
                if (coll.getFunctionalRole() == null) {
                    addFieldError("collaborator.functionalCode" + ind, "Functional role should not be null");
                }
                ind++;
            }
        }
        ind = 0;
        if (getParticipatingSitesList() != null && !getParticipatingSitesList().isEmpty()) {
            for (PaOrganizationDTO ps : getParticipatingSitesList()) {
                if (ps.getRecruitmentStatus() == null) {
                    addFieldError("participatingsite.recStatus" + ind, "Recruitment Status should not be null");
                }
                if (ps.getRecruitmentStatusDate() == null) {
                    addFieldError("participatingsite.recStatusDate" + ind,
                            "Recruitment Status date should not be null");
                }
                ind++;
            }
        }
        ind = 0;
        if (getFundingDtos() != null && !getFundingDtos().isEmpty()) {
            for (TrialFundingWebDTO fm : getFundingDtos()) {
                if (fm.getFundingMechanismCode() == null) {
                    addFieldError("updfundingMechanismCode" + ind, "Funding Mechanism Code should not be null");
                }
                if (fm.getNciDivisionProgramCode() == null) {
                    addFieldError("updnciDivisionProgramCode" + ind, "NCI Division Code should not be null");
                }
                if (fm.getNihInstitutionCode() == null) {
                    addFieldError("updnihInstitutionCode" + ind, "NIH Institution Code  should not be null");
                }
                if (fm.getSerialNumber() == null) {
                    addFieldError("updserialNumber" + ind, "Serial Number should not be null");
                }
                ind++;
            }
        }
        ind = 0;
        if (getIndIdeUpdateDtos() != null && !getIndIdeUpdateDtos().isEmpty()) {
            for (StudyIndldeWebDTO indide : getIndIdeUpdateDtos()) {
                if (indide.getGrantor() == null) {
                    addFieldError("updindideGrantor" + ind, "Grantor should not be null");
                }
                if (indide.getIndldeNumber() == null) {
                    addFieldError("updindideNumber" + ind, "IND/IDE Number should not be null");
                }
                if (indide.getHolderType() == null) {
                    addFieldError("updindideHolderType" + ind, "Ind/IDE Holder Type should not be null");
                }
                if (indide.getHolderType() != null &&  indide.getHolderType().equalsIgnoreCase("NIH")
                  && indide.getNihInstHolder() == null) {
                    addFieldError("updindideNihInstHolder" + ind, "NIH Institute holder should not be null");
                }
                if (indide.getHolderType() != null &&  indide.getHolderType().equalsIgnoreCase("NCI")
                   && indide.getNciDivProgHolder() == null) {
                      addFieldError("updindideNciDivPrgHolder" + ind, "NCI Division Program holder should not be null");
                }
                if (indide.getExpandedAccessIndicator() != null
                    && indide.getExpandedAccessIndicator().equalsIgnoreCase("yes")
                    && indide.getExpandedAccessStatus() == null) {
                      addFieldError("updindideExpandedStatus" + ind, "Expanded Access Status should not be null");
                }
                ind++;
            }
        }
    }


  /**
   * Convet to ind ide web dto.
   *
   * @param indldeWebDTO the indlde web dto
   *
   * @return the study indlde dto
   */
  private StudyIndldeDTO convetToIndIdeWebDTO(StudyIndldeWebDTO indldeWebDTO, Ii studyProtocolIi) {
      StudyIndldeDTO studyIndldeDTO = new StudyIndldeDTO();
      studyIndldeDTO.setIdentifier(IiConverter.convertToStudyIndIdeIi(
                                  Long.valueOf(indldeWebDTO.getId())));

      studyIndldeDTO.setStudyProtocolIdentifier(studyProtocolIi);
      if (indldeWebDTO.getExpandedAccessIndicator().equalsIgnoreCase("Yes")) {
          studyIndldeDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.TRUE));
          studyIndldeDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(
                  indldeWebDTO.getExpandedAccessStatus()));
      } else {
          studyIndldeDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
          studyIndldeDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(null));
      }
      studyIndldeDTO.setGrantorCode(CdConverter.convertStringToCd(indldeWebDTO.getGrantor()));
      studyIndldeDTO.setHolderTypeCode(CdConverter.convertStringToCd(indldeWebDTO.getHolderType()));
      studyIndldeDTO.setIndldeNumber(StConverter.convertToSt(indldeWebDTO.getIndldeNumber()));
      if (indldeWebDTO.getHolderType().equalsIgnoreCase(HolderTypeCode.NIH.getCode().toString())) {
          studyIndldeDTO.setNihInstHolderCode(CdConverter.convertStringToCd(
                  indldeWebDTO.getNihInstHolder()));
       }
      if (indldeWebDTO.getHolderType().equalsIgnoreCase(HolderTypeCode.NCI.getCode().toString())) {
          studyIndldeDTO.setNciDivProgHolderCode(CdConverter.convertStringToCd(
                  indldeWebDTO.getNciDivProgHolder()));
      }
      studyIndldeDTO.setIndldeTypeCode(CdConverter.convertStringToCd(indldeWebDTO.getIndldeType()));

      return studyIndldeDTO;

  }

  /**
   * Convert to study resourcing dto.
   *
   * @param trialFundingWebDTO the trial funding web dto
   * @param studyProtocolIi the study protocol ii
   *
   * @return the study resourcing dto
   */
  private StudyResourcingDTO convertToStudyResourcingDTO(TrialFundingWebDTO trialFundingWebDTO, Ii studyProtocolIi)
  throws PAException {
      StudyResourcingDTO studyResoureDTO = new StudyResourcingDTO();
      studyResoureDTO = PaRegistry.getStudyResourcingService().getStudyResourcingById(
              IiConverter.convertToIi(Long.parseLong(trialFundingWebDTO.getId())));
      studyResoureDTO.setStudyProtocolIdentifier(studyProtocolIi);
      studyResoureDTO.setFundingMechanismCode(CdConverter.convertStringToCd(
          trialFundingWebDTO.getFundingMechanismCode()));
     studyResoureDTO.setNciDivisionProgramCode(CdConverter.convertToCd(
              NciDivisionProgramCode.getByCode(trialFundingWebDTO.getNciDivisionProgramCode())));
      studyResoureDTO.setNihInstitutionCode(CdConverter.convertStringToCd(
          trialFundingWebDTO.getNihInstitutionCode()));
      studyResoureDTO.setSerialNumber(StConverter.convertToSt(trialFundingWebDTO.getSerialNumber()));
      return studyResoureDTO;
  }

  /**
   * Gets the collaborators for update.
   *
   * @param collaboratorsList the collaborators list
   *
   * @return the collaborators for update
   *
   * @throws PAException the PA exception
   */
  private List<StudySiteDTO> getCollaboratorsForUpdate(List<PaOrganizationDTO> collaboratorsList) throws PAException {
      List<StudySiteDTO> ssDTO = new ArrayList<StudySiteDTO>();
      for (PaOrganizationDTO dto : collaboratorsList) {
          StudySiteDTO sp = PaRegistry.getStudySiteService().get(IiConverter.convertToIi(dto.getId()));
          sp.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.getByCode(dto.getFunctionalRole())));
          ssDTO.add(sp);
      }
      return ssDTO;
  }

  /**
   * Gets the participating sites for update.
   *
   * @param ps the ps
   *
   * @return the participating sites for update
   *
   * @throws PAException the PA exception
   */
  private List<StudySiteAccrualStatusDTO> getParticipatingSitesForUpdate(List<PaOrganizationDTO> ps)
  throws PAException {
      List<StudySiteAccrualStatusDTO> ssaDTO = new ArrayList<StudySiteAccrualStatusDTO>();
      for (PaOrganizationDTO dto : ps) {
          StudySiteAccrualStatusDTO ssasOld = PaRegistry.getStudySiteAccrualStatusService()
                                              .getCurrentStudySiteAccrualStatusByStudySite(
                                                      IiConverter.convertToIi(dto.getId()));


          StudySiteAccrualStatusDTO ssas =  new StudySiteAccrualStatusDTO();
          ssas.setStudySiteIi(ssasOld.getStudySiteIi());
          ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(dto.getRecruitmentStatus())));
          ssas.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(dto.getRecruitmentStatusDate())));
          ssaDTO.add(ssas);
      }
      return ssaDTO;
  }

  private List<StudySiteDTO> getStudySiteToUpdateProgramCode(List<PaOrganizationDTO> ps) throws PAException {
      List<StudySiteDTO> ssDTO = new ArrayList<StudySiteDTO>();
      for (PaOrganizationDTO dto : ps) {
          StudySiteDTO sp = PaRegistry.getStudySiteService().get(IiConverter.convertToIi(dto.getId()));
          sp.setProgramCodeText(StConverter.convertToSt(dto.getProgramCode()));
          ssDTO.add(sp);
      }
       return ssDTO;

  }

  /**
  * Gets the overall status for update.
  *
  * @param util the util
  *
  * @return the overall status for update
  *
  * @throws PAException the PA exception
  */
 private StudyOverallStatusDTO getOverallStatusForUpdate(TrialUtil util) throws PAException {
      StudyOverallStatusDTO sosDto = null;
      StudyProtocolQueryDTO spqDTO = PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
                                      Long.parseLong(trialDTO.getIdentifier()));

       //original submission
      if (spqDTO.getDocumentWorkflowStatusCode() != null
              && spqDTO.getDocumentWorkflowStatusCode().getCode().equalsIgnoreCase("SUBMITTED")
      && IntConverter.convertToInteger(IntConverter.convertToInt(trialDTO.getSubmissionNumber())) == 1) {

          sosDto = PaRegistry.getStudyOverallStatusService().
                      getCurrentByStudyProtocol(IiConverter.convertToIi(trialDTO.getIdentifier()));

      } else {
          sosDto = new StudyOverallStatusDTO();
          sosDto.setIdentifier(IiConverter.convertToIi((Long) null));
          sosDto.setStudyProtocolIdentifier(IiConverter.convertToIi(trialDTO.getIdentifier()));
      }
      util.convertToStudyOverallStatusDTO(trialDTO, sosDto);
      return sosDto;
  }

/**
 * @return the indIdeUpdateDtosLen
 */
public int getIndIdeUpdateDtosLen() {
    return indIdeUpdateDtosLen;
}

/**
 * @param indIdeUpdateDtosLen the indIdeUpdateDtosLen to set
 */
public void setIndIdeUpdateDtosLen(int indIdeUpdateDtosLen) {
    this.indIdeUpdateDtosLen = indIdeUpdateDtosLen;
}

}
