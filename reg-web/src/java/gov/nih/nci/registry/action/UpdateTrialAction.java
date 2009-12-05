package gov.nih.nci.registry.action;


import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
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
import gov.nih.nci.registry.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.registry.dto.StudyIndldeWebDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.CreateIfNull;
import com.opensymphony.xwork2.util.Element;

/**
 * The Class UpdateTrialAction.
 * 
 * @author Vrushali
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity" , "PMD.NPathComplexity" , "PMD.ExcessiveParameterList" ,
    "PMD.ExcessiveClassLength" , "PMD.TooManyMethods" , "PMD.ExcessiveMethodLength" , "PMD.TooManyFields" })

public class UpdateTrialAction extends ActionSupport implements ServletResponseAware {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The servlet response. */
    private HttpServletResponse servletResponse;
    
    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(UpdateTrialAction.class);
    
    /** The trial dto. */
    private TrialDTO trialDTO = new TrialDTO();
    
    /** The irb approval. */
    private File irbApproval;
    
    /** The irb approval file name. */
    private String irbApprovalFileName;
    
    /** The trial action. */
    private String trialAction = null;
    
    /** The study protocol id. */
    private String studyProtocolId = null;
    
    /** The session trial dto. */
    private static String sessionTrialDTO = "trialDTO";

    
    //for update
    /** The collaborators. */
    @CreateIfNull(value = true)
    @Element (value = gov.nih.nci.pa.dto.PaOrganizationDTO.class)
    private List<PaOrganizationDTO> collaborators = new ArrayList<PaOrganizationDTO>();
    
    /** The participating sites. */
    @CreateIfNull(value = true)
    @Element (value = gov.nih.nci.pa.dto.PaOrganizationDTO.class)
    private List<PaOrganizationDTO> participatingSites = new ArrayList<PaOrganizationDTO>();
   
   /** The country list. */
   @CreateIfNull(value = true)
    @Element (value = gov.nih.nci.pa.dto.CountryRegAuthorityDTO.class)
    private List<CountryRegAuthorityDTO> countryList = new ArrayList<CountryRegAuthorityDTO>();
    
    /** The reg id auth org list. */
    @CreateIfNull(value = true)
    @Element (value = gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO.class)
    private List<RegulatoryAuthOrgDTO> regIdAuthOrgList = new ArrayList<RegulatoryAuthOrgDTO>();
    
    /** The ind ide update dtos. */
    @CreateIfNull(value = true)
    @Element (value = gov.nih.nci.registry.dto.StudyIndldeWebDTO.class)
    private List <StudyIndldeWebDTO> indIdeUpdateDtos = new ArrayList<StudyIndldeWebDTO>();
    
    /** The funding add dtos. */
    @CreateIfNull(value = true)
    @Element (value = gov.nih.nci.registry.dto.TrialFundingWebDTO.class)
    private List<TrialFundingWebDTO> fundingAddDtos = new ArrayList<TrialFundingWebDTO>();
    
    /** The ind ide add dtos. */
    @CreateIfNull(value = true)
    @Element (value = gov.nih.nci.registry.dto.TrialIndIdeDTO.class)
    private List <TrialIndIdeDTO> indIdeAddDtos = new ArrayList<TrialIndIdeDTO>();
    
    /** The funding dtos. */
    @CreateIfNull(value = true)
    @Element (value = gov.nih.nci.registry.dto.TrialFundingWebDTO.class)
    private List<TrialFundingWebDTO> fundingDtos = new ArrayList<TrialFundingWebDTO>();
    
    /** The lst. */
    private String lst = null;
    
    /** The selected reg auth. */
    private String selectedRegAuth = null;
    
    /** The programcodenihselectedvalue. */
    private String programcodenihselectedvalue;
    
    /** The programcodenciselectedvalue. */
    private String programcodenciselectedvalue;
    
    /** The pa organization dto. */
    private PaOrganizationDTO paOrganizationDTO;
    
    /** The country reg authority dto. */
    private CountryRegAuthorityDTO countryRegAuthorityDTO;
    
    /** The regulatory auth org dto. */
    private RegulatoryAuthOrgDTO regulatoryAuthOrgDTO;
    
    /** The study indlde web dto. */
    private StudyIndldeWebDTO studyIndldeWebDTO;
    
    /** The trial funding dto. */
    private TrialFundingWebDTO trialFundingDTO;
    
    /** The trial ind ide dto. */
    private TrialIndIdeDTO trialIndIdeDTO;
    
    /** The regulatory authority. */
    private RegulatoryAuthorityWebDTO regulatoryAuthority;
   
    private int indIdeUpdateDtosLen = 0; 
    /**
     * Gets the country reg authority dto.
     * 
     * @return the countryRegAuthorityDTO
     */
    public CountryRegAuthorityDTO getCountryRegAuthorityDTO() {
        return countryRegAuthorityDTO;
    }

    /**
     * Sets the country reg authority dto.
     * 
     * @param countryRegAuthorityDTO the countryRegAuthorityDTO to set
     */
    public void setCountryRegAuthorityDTO(
            CountryRegAuthorityDTO countryRegAuthorityDTO) {
        this.countryRegAuthorityDTO = countryRegAuthorityDTO;
    }

    /**
     * Gets the regulatory auth org dto.
     * 
     * @return the regulatoryAuthOrgDTO
     */
    public RegulatoryAuthOrgDTO getRegulatoryAuthOrgDTO() {
        return regulatoryAuthOrgDTO;
    }

    /**
     * Sets the regulatory auth org dto.
     * 
     * @param regulatoryAuthOrgDTO the regulatoryAuthOrgDTO to set
     */
    public void setRegulatoryAuthOrgDTO(RegulatoryAuthOrgDTO regulatoryAuthOrgDTO) {
        this.regulatoryAuthOrgDTO = regulatoryAuthOrgDTO;
    }

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
     * Gets the irb approval.
     * 
     * @return the irbApproval
     */
    public File getIrbApproval() {
        return irbApproval;
    }
    
    /**
     * Sets the irb approval.
     * 
     * @param irbApproval the irbApproval to set
     */
    public void setIrbApproval(File irbApproval) {
        this.irbApproval = irbApproval;
    }
    
    /**
     * Gets the irb approval file name.
     * 
     * @return the irbApprovalFileName
     */
    public String getIrbApprovalFileName() {
        return irbApprovalFileName;
    }
    
    /**
     * Sets the irb approval file name.
     * 
     * @param irbApprovalFileName the irbApprovalFileName to set
     */
    public void setIrbApprovalFileName(String irbApprovalFileName) {
        this.irbApprovalFileName = irbApprovalFileName;
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
   public List<PaOrganizationDTO> getParticipatingSites() {
       return participatingSites;
   }

   /**
    * Sets the participating sites.
    * 
    * @param participatingSites the participatingSites to set
    */
   public void setParticipatingSites(List<PaOrganizationDTO> participatingSites) {
       this.participatingSites = participatingSites;
   }

   /**
    * Gets the regulatory authority.
    * 
    * @return the regulatoryAuthority
    */
   public RegulatoryAuthorityWebDTO getRegulatoryAuthority() {
       return regulatoryAuthority;
   }

   /**
    * Sets the regulatory authority.
    * 
    * @param regulatoryAuthority the regulatoryAuthority to set
    */
   public void setRegulatoryAuthority(RegulatoryAuthorityWebDTO regulatoryAuthority) {
       this.regulatoryAuthority = regulatoryAuthority;
   }

   /**
    * Gets the country list.
    * 
    * @return the countryList
    */
   public List<CountryRegAuthorityDTO> getCountryList() {
       return countryList;
   }

   /**
    * Sets the country list.
    * 
    * @param countryList the countryList to set
    */
   public void setCountryList(List<CountryRegAuthorityDTO> countryList) {
       this.countryList = countryList;
   }

   /**
    * Gets the reg id auth org list.
    * 
    * @return the regIdAuthOrgList
    */
   public List<RegulatoryAuthOrgDTO> getRegIdAuthOrgList() {
       return regIdAuthOrgList;
   }

   /**
    * Sets the reg id auth org list.
    * 
    * @param regIdAuthOrgList the regIdAuthOrgList to set
    */
   public void setRegIdAuthOrgList(List<RegulatoryAuthOrgDTO> regIdAuthOrgList) {
       this.regIdAuthOrgList = regIdAuthOrgList;
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
    * Gets the lst.
    * 
    * @return the lst
    */
   public String getLst() {
       return lst;
   }

   /**
    * Sets the lst.
    * 
    * @param lst the lst to set
    */
   public void setLst(String lst) {
       this.lst = lst;
   }

   /**
    * Gets the selected reg auth.
    * 
    * @return the selectedRegAuth
    */
   public String getSelectedRegAuth() {
       return selectedRegAuth;
   }

   /**
    * Sets the selected reg auth.
    * 
    * @param selectedRegAuth the selectedRegAuth to set
    */
   public void setSelectedRegAuth(String selectedRegAuth) {
       this.selectedRegAuth = selectedRegAuth;
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
                String pId = (String) ServletActionContext.getRequest().getParameter("studyProtocolId");
                if (studyProtocolId == null) {
                    studyProtocolId = pId;
                }
                Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(Long.parseLong(studyProtocolId));
                TrialUtil util = new TrialUtil();
                util.getTrialDTOFromDb(studyProtocolIi, trialDTO);
                synchActionWithDTO();
                TrialValidator.addSessionAttributesForUpdate(trialDTO);
                setIndIdeUpdateDtosLen(trialDTO.getIndIdeUpdateDtos().size());
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
       setParticipatingSites(trialDTO.getParticipatingSites());
      }
      setCountryList(trialDTO.getCountryList());
      if (trialDTO.getRegIdAuthOrgList() != null && !trialDTO.getRegIdAuthOrgList().isEmpty()) {
       setRegIdAuthOrgList(trialDTO.getRegIdAuthOrgList());
      }
      if (trialDTO.getRegulatoryAuthority() != null) {
          setRegulatoryAuthority(trialDTO.getRegulatoryAuthority());
      }
      if (trialDTO.getSelectedRegAuth() != null) {
          setSelectedRegAuth(trialDTO.getSelectedRegAuth());
      }    
      if (trialDTO.getLst() != null) {
          setLst(trialDTO.getLst());
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
      trialDTO.setParticipatingSites(getParticipatingSites());
      setRegAuthorityDetails(getRegulatoryAuthority());
      trialDTO.setRegulatoryAuthority(getRegulatoryAuthority());
      trialDTO.setSelectedRegAuth(getSelectedRegAuth());
      trialDTO.setIndIdeUpdateDtos(getIndIdeUpdateDtos());
      trialDTO.setLst(getLst());
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

    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    public String reviewUpdate() {
        try {
            //needed to populate the drop downs when error occurs.
            List<CountryRegAuthorityDTO> countriesList = 
                (List<CountryRegAuthorityDTO>) ServletActionContext.getRequest().getSession()
                    .getAttribute(Constants.COUNTRY_LIST);
            if (countriesList != null) {
                trialDTO.setCountryList(countriesList);
                setCountryList(countriesList);
            }
       
          List<RegulatoryAuthOrgDTO> regAuthList = (List<RegulatoryAuthOrgDTO>) ServletActionContext.getRequest()
                .getSession().getAttribute(Constants.REG_AUTH_LIST);
            if (regAuthList != null) {
                trialDTO.setRegIdAuthOrgList(regAuthList);
                setRegIdAuthOrgList(regAuthList);
            }
            if (regulatoryAuthority != null) {
              trialDTO.setRegulatoryAuthority(regulatoryAuthority);
            }
            clearErrorsAndMessages();
            enforceBusinessRules();
            
            if (hasFieldErrors()) {
                ServletActionContext.getRequest().setAttribute(
                        "failureMessage" , "The form has errors and could not be submitted, "
                        + "please check the fields highlighted below");
                TrialValidator.addSessionAttributes(trialDTO);
                synchActionWithDTO();
            return ERROR;
            }
            
            if (hasActionErrors()) {
                TrialValidator.addSessionAttributes(trialDTO);
                synchActionWithDTO();
                return ERROR;
            }
           List<TrialDocumentWebDTO> docDTOList = addDocDTOToList();
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
          
           synchDTOWithAction();

        } catch (IOException e) {
            LOG.error(e.getMessage());
            synchActionWithDTO();
            return ERROR;
        } catch (PAException e) {
            LOG.error(e.getMessage());
            synchActionWithDTO();
            return ERROR;
        }
        TrialValidator.removeSessionAttributes();
        ServletActionContext.getRequest().getSession().setAttribute(sessionTrialDTO, trialDTO);
        LOG.info("Calling the review page...");
        return "review";    
    }

    /**
     * Adds the doc dto to list.
     * 
     * @return the list< trial document web dt o>
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws PAException the PA exception
     */
    private List<TrialDocumentWebDTO> addDocDTOToList() throws IOException, PAException {
        TrialUtil util = new TrialUtil();
        List<TrialDocumentWebDTO> docDTOList = new ArrayList<TrialDocumentWebDTO>();
        if (PAUtil.isNotEmpty(irbApprovalFileName)) {
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode(), 
                        irbApprovalFileName, irbApproval));
        }
        return docDTOList;
    }
    
    /**
     * Edits the.
     * 
     * @return s
     */
    public String edit() {
        trialDTO = (TrialDTO) ServletActionContext.getRequest().getSession().getAttribute(sessionTrialDTO);
        synchActionWithDTO();
        TrialValidator.addSessionAttributes(trialDTO);
        return "edit";
    }
    
    /**
     * Update.
     * 
     * @return s
     */
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
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
            StudyProtocolDTO spDTO = RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            util.updateStudyProtcolDTO(spDTO, trialDTO);
            spDTO.setUserLastCreated(StConverter.convertToSt(ServletActionContext.getRequest().getRemoteUser()));
            //set the NCT number 
            StudySiteDTO ssDto = util.getStudySite(studyProtocolIi, StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
            if (ssDto != null) {
                util.convertToNCTStudySiteDTO(trialDTO, ssDto);
            } else {
                ssDto = new StudySiteDTO();
                ssDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getNctIdentifier()));
            }
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
            StudyResourcingDTO summary4studyResourcingDTO = RegistryServiceLocator.getStudyResourcingService()
                                              .getsummary4ReportedResource(studyProtocolIi); 
            if (summary4studyResourcingDTO != null) {
                util.convertToSummary4StudyResourcingDTO(trialDTO, summary4studyResourcingDTO);
            } else {
                summary4studyResourcingDTO = util.convertToSummary4StudyResourcingDTO(trialDTO); 
            }
            OrganizationDTO summary4orgDTO = util.convertToSummary4OrgDTO(trialDTO);
          
            StudyContactDTO studyContactDTO = null;
            StudySiteContactDTO studyParticipationContactDTO = null;
            Ii responsiblePartyContactIi = null;
            if (trialDTO.getResponsiblePartyType().equalsIgnoreCase("pi")) {
                studyContactDTO = new StudyContactDTO();
                util.convertToStudyContactDTO(trialDTO, studyContactDTO);
                
            } else {
                studyParticipationContactDTO = new StudySiteContactDTO();
                util.convertToStudySiteContactDTO(trialDTO, studyParticipationContactDTO);
                
                if (trialDTO.getResponsiblePersonName() != null && !trialDTO.getResponsiblePersonName().equals("")) {
                 responsiblePartyContactIi = IiConverter.convertToPoPersonIi(trialDTO.getResponsiblePersonIdentifier());
                }
                if (trialDTO.getResponsibleGenericContactName() != null 
                          && !trialDTO.getResponsibleGenericContactName().equals("")) {
                    responsiblePartyContactIi = IiConverter.
                        convertToPoOrganizationalContactIi(trialDTO.getResponsiblePersonIdentifier());
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
            //Regulatory auth update
            populateStudyWithRegualtory(trialDTO.getRegulatoryAuthority(), spDTO);
            
            //update StudyRegulatory 
            StudyRegulatoryAuthorityDTO studyRegAuthDTO = getStudyRegAuth(studyProtocolIi);
            
            //collaborators update - send the collaborators list
            List<StudySiteDTO> collaboratorsDTOList = getCollaboratorsForUpdate(trialDTO.getCollaborators());
            
           //ps update- send the participating sites list
          List<StudySiteAccrualStatusDTO> pssDTOList = getParticipatingSitesForUpdate(trialDTO.getParticipatingSites());
          
          //list of studysite dtos with updated program code
          List<StudySiteDTO> prgCdUpdatedList = getStudySiteToUpdateProgramCode(trialDTO.getParticipatingSites());
          
          updateId = studyProtocolIi; 
          //call the service to invoke the update method
          RegistryServiceLocator.getTrialRegistrationService().
                        update(spDTO, sosDto, ssDto, studyIndldeDTOList, studyResourcingDTOs, documentDTOs, 
                                studyContactDTO, studyParticipationContactDTO,
                               summary4orgDTO, summary4studyResourcingDTO, responsiblePartyContactIi,
                              studyRegAuthDTO, collaboratorsDTOList, 
                              pssDTOList, prgCdUpdatedList, BlConverter.convertToBl(Boolean.FALSE));  
            TrialValidator.removeSessionAttributes();
            ServletActionContext.getRequest().getSession().setAttribute("protocolId", updateId.getExtension());
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", updateId);
        } catch (PAException e) {
            LOG.error(e.getMessage());
            addActionError(e.getMessage());
            TrialValidator.addSessionAttributes(trialDTO);
            synchActionWithDTO();
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
        if (PAUtil.isNotEmpty(trialDTO.getStatusCode())
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
        //Regulatory information validation
        if (PAUtil.isEmpty(getSelectedRegAuth())) {
            addFieldError("regulatory.oversight.auth.name", "Select the Oversight authority organization name");
        }
        if (PAUtil.isEmpty(getLst())) {
            addFieldError("lst", "Select the Oversight authority country");
        }
        if (getCollaborators() != null && !getCollaborators().isEmpty()) {
            for (PaOrganizationDTO coll : getCollaborators()) {
                if (coll.getFunctionalRole() == null) {
                    addFieldError("collaborator.functionalCode", "Functional role should not be null");
                    break;
                }
            }
        }
        if (getParticipatingSites() != null && !getParticipatingSites().isEmpty()) {
            for (PaOrganizationDTO ps : getParticipatingSites()) {
                if (ps.getRecruitmentStatus() == null) {
                    addFieldError("participatingsite.recStatus", "Recruitment Status should not be null");
                    break;
                }
                if (ps.getRecruitmentStatusDate() == null) {
                    addFieldError("participatingsite.recStatusDate", "Recruitment Status date should not be null");
                    break;
                }
            }
        }
        
        if (getFundingDtos() != null && !getFundingDtos().isEmpty()) {
            for (TrialFundingWebDTO fm : getFundingDtos()) {
                if (fm.getFundingMechanismCode() == null) {
                    addFieldError("updfundingMechanismCode", "Funding Mechanism Code should not be null");
                    break;
                }
                if (fm.getNciDivisionProgramCode() == null) {
                    addFieldError("updnciDivisionProgramCode", "NCI Division Code should not be null");
                    break;
                }
                if (fm.getNihInstitutionCode() == null) {
                    addFieldError("updnihInstitutionCode", "NIH Institution Code  should not be null");
                    break;
                }
                if (fm.getSerialNumber() == null) {
                    addFieldError("updserialNumber", "Serial Number should not be null");
                    break;
                }
                
            }
        }
        
        if (getIndIdeUpdateDtos() != null && !getIndIdeUpdateDtos().isEmpty()) {
            for (StudyIndldeWebDTO indide : getIndIdeUpdateDtos()) {
                if (indide.getGrantor() == null) {
                    addFieldError("updindideGrantor", "Grantor should not be null");
                    break;
                }
                if (indide.getIndldeNumber() == null) {
                    addFieldError("updindideNumber", "IND/IDE Number should not be null");
                    break;
                }
                if (indide.getHolderType() == null) {
                    addFieldError("updindideHolderType", "Ind/IDE Holder Type should not be null");
                    break;
                }
                if (indide.getHolderType() != null &&  indide.getHolderType().equalsIgnoreCase("NIH")
                  && indide.getNihInstHolder() == null) {
                    addFieldError("updindideNihInstHolder", "NIH Institute holder should not be null");
                    break;
                }
                if (indide.getHolderType() != null &&  indide.getHolderType().equalsIgnoreCase("NCI")
                   && indide.getNciDivProgHolder() == null) {
                      addFieldError("updindideNciDivPrgHolder", "NCI Divsion Program holder should not be null");
                      break;
                }
                if (indide.getExpandedAccessIndicator() != null 
                    && indide.getExpandedAccessIndicator().equalsIgnoreCase("yes")
                    && indide.getExpandedAccessStatus() == null) {
                      addFieldError("updindideExpandedStatus", "Expanded Access Status should not be null");
                      break;
                }
                
            }
        }
    }   
    
    
    /**
     * Adds the errors.
     * 
     * @param err the err
     */
    private void addErrors(Map<String, String> err) {
        if (!err.isEmpty()) {
            for (String msg : err.keySet()) {
                addFieldError(msg, err.get(msg));
            }
        }
    }
   
    /**
     * Gets the reg authorities list.
     * 
     * @return String success or failure
     */
    public String getRegAuthoritiesList() {
        try {
            String countryId = ServletActionContext.getRequest().getParameter("countryid");
            if (countryId != null && !("".equals(countryId))) {
                regIdAuthOrgList = RegistryServiceLocator.getRegulatoryInformationService()
                                    .getRegulatoryAuthorityNameId(Long.valueOf(countryId));
            } else {
                RegulatoryAuthOrgDTO defaultVal = new RegulatoryAuthOrgDTO();
                defaultVal.setName("-Select Country-");
                regIdAuthOrgList.add(defaultVal);
            }
            if (!regIdAuthOrgList.isEmpty()) {
               ServletActionContext.getRequest().getSession().setAttribute(Constants.REG_AUTH_LIST,
                       regIdAuthOrgList);
           }
        } catch (PAException e) {
            return SUCCESS;
        }
        return SUCCESS;
    }
    
  /**
   * Sets the reg authority details.
   * 
   * @param webDTO the new reg authority details
   */
  private void setRegAuthorityDetails(RegulatoryAuthorityWebDTO webDTO) {
     try {
      if (getSelectedRegAuth() != null  && !"".equals(getSelectedRegAuth())
              && getLst() != null && !"".equals(getLst())) {   
                  String orgName = RegistryServiceLocator.getRegulatoryInformationService()
                         .getCountryOrOrgName(Long.valueOf(getSelectedRegAuth()), "RegulatoryAuthority");
                  String countryName = RegistryServiceLocator.getRegulatoryInformationService().getCountryOrOrgName(
                             Long.valueOf(getLst()), "Country");
                  webDTO.setTrialOversgtAuthOrgName(orgName);
                  webDTO.setTrialOversgtAuthCountry(countryName);
      }
              } catch (PAException e) {
         LOG.error(e.getLocalizedMessage());
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
      studyResoureDTO = RegistryServiceLocator.getStudyResourcingService().getStudyResourceByID(
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
   * Gets the study reg auth.
   * 
   * @param studyProtocolIi the study protocol ii
   * 
   * @return the study reg auth
   * 
   * @throws PAException the PA exception
   */
  private StudyRegulatoryAuthorityDTO getStudyRegAuth(Ii studyProtocolIi) throws PAException {
      
      StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = RegistryServiceLocator.getStudyRegulatoryAuthorityService()
                                                              .getCurrentByStudyProtocol(studyProtocolIi); 
      StudyRegulatoryAuthorityDTO sraDTO = new StudyRegulatoryAuthorityDTO();
      sraDTO.setStudyProtocolIdentifier(studyProtocolIi);
      sraDTO.setRegulatoryAuthorityIdentifier(IiConverter.convertToIi(trialDTO.getSelectedRegAuth()));
      if (sraFromDatabaseDTO == null) {
           return sraDTO;
      } else {
          sraFromDatabaseDTO.setRegulatoryAuthorityIdentifier(IiConverter.convertToIi(trialDTO.getSelectedRegAuth()));
          return sraFromDatabaseDTO;
      }
  }
  
  /**
   * Populate study with regualtory.
   * 
   * @param webDTO the web dto
   * @param spDTO the sp dto
   */
  private void populateStudyWithRegualtory(RegulatoryAuthorityWebDTO webDTO, StudyProtocolDTO spDTO) {
      if (webDTO.getSection801Indicator() == null) {
          spDTO.setSection801Indicator(BlConverter.convertToBl(null));
      } else {
          spDTO.setSection801Indicator(BlConverter.convertToBl(Boolean.valueOf(webDTO.getSection801Indicator())));
      }
      if (webDTO.getFdaRegulatedInterventionIndicator() == null) {
          spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(null));
      } else {
          spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                  .getFdaRegulatedInterventionIndicator())));
      }
      if (webDTO.getDelayedPostingIndicator() == null) {
          spDTO.setDelayedpostingIndicator(BlConverter.convertToBl(null));
      } else {
          spDTO.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                  .getDelayedPostingIndicator())));
      }
      if (webDTO.getDataMonitoringIndicator() == null) {
          spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(null));
      } else {
          spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                  .getDataMonitoringIndicator())));
      }
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
          StudySiteDTO sp = RegistryServiceLocator.getStudySiteService().get(IiConverter.convertToIi(dto.getId()));
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
          StudySiteAccrualStatusDTO ssasOld = RegistryServiceLocator.getStudySiteAccrualStatusService()
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
          StudySiteDTO sp = RegistryServiceLocator.getStudySiteService().get(IiConverter.convertToIi(dto.getId()));
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
      StudyProtocolQueryDTO spqDTO = RegistryServiceLocator.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
                                      Long.parseLong(trialDTO.getIdentifier()));

       //original submission
      if (spqDTO.getDocumentWorkflowStatusCode() != null 
              && spqDTO.getDocumentWorkflowStatusCode().getCode().equalsIgnoreCase("SUBMITTED") 
      && IntConverter.convertToInteger(IntConverter.convertToInt(trialDTO.getSubmissionNumber())) == 1) {
          
          sosDto = RegistryServiceLocator.getStudyOverallStatusService().
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