/*
* caBIG Open Source Software License
* 
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
* 
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
* 
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract 
* or otherwise,or
*  
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or 
* 
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to 
* 
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so; 
* 
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof); 
* 
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
* 
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
* 
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally 
* appear.
* 
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
* 
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
* 
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
* 
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN 
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
* 
*/
package gov.nih.nci.pa.action;

import gov.nih.nci.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.cadsr.domain.ClassificationScheme;
import gov.nih.nci.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.pa.domain.UnitOfMeasurement;
import gov.nih.nci.pa.dto.ISDesignDetailsWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.SamplingMethodCode;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.BaseLookUpService;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Kalpana Guthikonda
 * @since 11/12/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength",
  "PMD.NPathComplexity", "PMD.ExcessiveClassLength", "PMD.TooManyMethods", "PMD.TooManyFields" , 
      "PMD.AvoidDeeplyNestedIfStmts" })
public class EligibilityCriteriaAction extends ActionSupport {

    private static final long serialVersionUID = -5307419735675359757L;
    private static final String ELIGIBILITY = "eligibility";
    private static final String ELIGIBILITYADD = "eligibilityAdd";
    private static final int MAXIMUM_CHAR_POPULATION = 800;
    private static final int MAXIMUM_CHAR_DESCRIPTION = 5000;
    private static final String SP = " ";
    private ISDesignDetailsWebDTO webDTO = new ISDesignDetailsWebDTO();
    private Long id = null;
    private String page;
    private String acceptHealthyVolunteersIndicator;
    private String eligibleGenderCode;
    private String maximumValue;
    private String valueUnit;
    private String minimumValue;
    private String eligibleGenderCodeId = null;
    private String valueId = null;
    private List<ISDesignDetailsWebDTO> eligibilityList;
    private List<ISDesignDetailsWebDTO> list = null;
    private static final int RECORDSVALUE = 2;
    private String studyPopulationDescription;
    private String samplingMethodCode;
    private List<DataElement> cadsrResult = new ArrayList<DataElement>();  
    private static final long CADSR_CS_ID = 2960572;
    private static final float CADSR_CS_VERSION = 1;  
    private static List<ClassificationSchemeItem> csisResult = null;
    private static final String CSIS = "csisResult";
    private static final String CDES_BY_CSI = "cdesByCsiResult";
  
    /**
     * 
     * @return String
     */
    public String query() {

    try {
        getClassSchemeItems();
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession()
            .getAttribute(Constants.STUDY_PROTOCOL_II);
        List<PlannedEligibilityCriterionDTO> pecList = PaRegistry.getPlannedActivityService()
        .getPlannedEligibilityCriterionByStudyProtocol(studyProtocolIi);
        if (pecList != null && !pecList.isEmpty()) {
            list = new ArrayList<ISDesignDetailsWebDTO>();
            for (PlannedEligibilityCriterionDTO dto : pecList) {
                list.add(setEligibilityDetailsDTO(dto));
            }
            if (list.size() > RECORDSVALUE) {
                eligibilityList = new ArrayList<ISDesignDetailsWebDTO>();
                for (ISDesignDetailsWebDTO weblist : list) {
                    if (weblist.getCriterionName() == null
                            || (!(weblist.getCriterionName().equalsIgnoreCase("GENDER")))
                            && (!(weblist.getCriterionName().equalsIgnoreCase("AGE")))) {
                        eligibilityList.add(weblist);             
                    }
                }
            }
        }
     
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        if (spDTO.getAcceptHealthyVolunteersIndicator().getValue() != null) {
            acceptHealthyVolunteersIndicator = spDTO.getAcceptHealthyVolunteersIndicator().getValue().toString();
        }
        StudyProtocolQueryDTO spqDTO = (StudyProtocolQueryDTO) ServletActionContext
        .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
        if (spqDTO.getStudyProtocolType().equalsIgnoreCase("ObservationalStudyProtocol")) {
            ObservationalStudyProtocolDTO ospDTO = new ObservationalStudyProtocolDTO();
            ospDTO = PaRegistry.getStudyProtocolService().getObservationalStudyProtocol(studyProtocolIi);
            if (ospDTO != null) {
                if (ospDTO.getSamplingMethodCode().getCode() != null) {
                    samplingMethodCode = ospDTO.getSamplingMethodCode().getCode().toString();
                }   
                if (ospDTO.getStudyPopulationDescription().getValue() != null) {
                    studyPopulationDescription = ospDTO.getStudyPopulationDescription().getValue().toString();
                }
            }
        }   

        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return ELIGIBILITY;
    }

    /**
     * this is will return list of category.
     * @return result
     */
    public String getClassSchemeItems() {
        try {
            if (csisResult == null) {
              csisResult = new ArrayList<ClassificationSchemeItem>();
            }
            ClassificationScheme cs = new ClassificationScheme();
            cs.setPublicID(CADSR_CS_ID);
            cs.setVersion(CADSR_CS_VERSION);
            ApplicationService appService = ApplicationServiceProvider.getApplicationService();
            Collection csResult = appService.search(ClassificationScheme.class, cs);
            ClassificationScheme classifSch = (ClassificationScheme) csResult.iterator().next();
         
            Collection<ClassSchemeClassSchemeItem> csItem = classifSch.getClassSchemeClassSchemeItemCollection();
            csisResult = new ArrayList<ClassificationSchemeItem>();
            for (ClassSchemeClassSchemeItem csCsi : csItem) {
                csisResult.add(csCsi.getClassificationSchemeItem());
            }
            
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }   
      
        return CSIS;
    }
  
    /**
     * call this from pop-search.
     * @return result
     */
    public String getClassifiedCDEs() {
        cadsrResult.clear();
        String csiName = ServletActionContext.getRequest().getParameter("csiName");
        String deName = ServletActionContext.getRequest().getParameter("searchName");
        try {
            ApplicationService appService = ApplicationServiceProvider.getApplicationService();          
            DetachedCriteria criteria = DetachedCriteria.forClass(DataElement.class, "de");
            criteria.add(Expression.ilike("longName", "%" + deName + "%"));
            criteria.add(Expression.eq("workflowStatusName", "RELEASED"));
            DetachedCriteria csCsiCriteria = criteria.createCriteria("administeredComponentClassSchemeItemCollection")
            .createCriteria("classSchemeClassSchemeItem");
          
            DetachedCriteria csCriteria = csCsiCriteria.createCriteria("classificationScheme");
            csCriteria.add(Expression.eq("publicID", CADSR_CS_ID));
            csCriteria.add(Expression.eq("version", CADSR_CS_VERSION));
          
            DetachedCriteria csiCriteria = csCsiCriteria.createCriteria("classificationSchemeItem");
            csiCriteria.add(Expression.eq("longName", csiName));
          
            List classCes = appService.query(criteria);
          
            for (Object obj : classCes) {
                cadsrResult.add((DataElement) obj);
            }
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }     
        return CDES_BY_CSI;      
    }  

    /**
     * can be removed.
     * @return String
     */
    public String displaycadsr() {
        cadsrResult.clear();
        String name = ServletActionContext.getRequest().getParameter("searchName");
        boolean restrictToStandards = 
            Boolean.valueOf(ServletActionContext.getRequest().getParameter("restrictToStandard"));
        try {
            ApplicationService appService = ApplicationServiceProvider.getApplicationService();
            DetachedCriteria criteria = DetachedCriteria.forClass(DataElement.class, "de");
            criteria.add(Expression.ilike("longName", "%" + name + "%"));
            criteria.add(Expression.eq("workflowStatusName", "RELEASED"));
            if (restrictToStandards) {
                criteria.add(Expression.eq("registrationStatus", "Standard"));
            }
      
            Set result = new HashSet();
            result.addAll(appService.query(criteria));
      
            criteria = DetachedCriteria.forClass(DataElement.class, "de");
            criteria.add(Expression.ilike("preferredName", "%" + name + "%"));
            criteria.add(Expression.eq("workflowStatusName", "RELEASED"));
      
            if (restrictToStandards) {
                criteria.add(Expression.eq("registrationStatus", "Standard"));
            } 
      
            result.addAll(appService.query(criteria));
      
            for (Object obj : result) {
                getCadsrResult().add((DataElement) obj);
            }
      
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        } 
      
        return SUCCESS;
    }
  
  /**
   * @return res
   */
  public String save() {
    enforceBusinessRules();
    if (hasFieldErrors()) {
      return ELIGIBILITY;
    }
    try {
      Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession()
      .getAttribute(Constants.STUDY_PROTOCOL_II);

      StudyProtocolQueryDTO spqDTO = (StudyProtocolQueryDTO) ServletActionContext
      .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
      if (spqDTO.getStudyProtocolType().equalsIgnoreCase("ObservationalStudyProtocol")) {
        ObservationalStudyProtocolDTO ospDTO = new ObservationalStudyProtocolDTO();
        ospDTO = PaRegistry.getStudyProtocolService().getObservationalStudyProtocol(studyProtocolIi);
        ospDTO.setStudyPopulationDescription(StConverter.convertToSt(studyPopulationDescription));
        ospDTO.setSamplingMethodCode(CdConverter.convertToCd(SamplingMethodCode
            .getByCode(samplingMethodCode)));
        ospDTO = PaRegistry.getStudyProtocolService().updateObservationalStudyProtocol(ospDTO);
      }

      StudyProtocolDTO spDTO = new StudyProtocolDTO();
      spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
      spDTO.setAcceptHealthyVolunteersIndicator(BlConverter.convertToBl(Boolean
          .valueOf(acceptHealthyVolunteersIndicator)));
      spDTO = PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);

      PlannedEligibilityCriterionDTO pecDTOGender = new PlannedEligibilityCriterionDTO();
      PlannedEligibilityCriterionDTO pecDTOAge = new PlannedEligibilityCriterionDTO();
      
      if (eligibleGenderCode != null) {
       pecDTOGender.setStudyProtocolIdentifier(studyProtocolIi);
       pecDTOGender.setCriterionName(StConverter.convertToSt("GENDER"));
       pecDTOGender.setEligibleGenderCode(CdConverter.convertToCd(EligibleGenderCode
            .getByCode(eligibleGenderCode)));
       pecDTOGender.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.ELIGIBILITY_CRITERION));
       pecDTOGender.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
        if (PAUtil.isNotEmpty(eligibleGenderCodeId)) {
          pecDTOGender.setIdentifier(IiConverter.convertToIi(eligibleGenderCodeId));
          PaRegistry.getPlannedActivityService().updatePlannedEligibilityCriterion(pecDTOGender);
        } else {
          PaRegistry.getPlannedActivityService().createPlannedEligibilityCriterion(pecDTOGender);
        }
      }
      if (minimumValue != null || maximumValue != null) {
        pecDTOAge.setStudyProtocolIdentifier(studyProtocolIi);
        pecDTOAge.setCriterionName(StConverter.convertToSt("AGE"));
        pecDTOAge.setValue(convertToIvlPq(valueUnit, minimumValue, maximumValue));
        pecDTOAge.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.ELIGIBILITY_CRITERION));
        pecDTOAge.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
        if (PAUtil.isNotEmpty(valueId)) {
          pecDTOAge.setIdentifier(IiConverter.convertToIi(valueId));
          PaRegistry.getPlannedActivityService().updatePlannedEligibilityCriterion(pecDTOAge);
        } else {
          PaRegistry.getPlannedActivityService().createPlannedEligibilityCriterion(pecDTOAge);
        }
      }
      if (PAUtil.isNotEmpty(eligibleGenderCodeId)) {
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
      } else {
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
      }
      query();            
    } catch (PAException e) {
      ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
    }
    return ELIGIBILITY;
  }
  
  @SuppressWarnings({"PMD" })   
  private Ivl<Pq> convertToIvlPq(String uom, String minValue, String maxValue) {
   if (uom == null && minValue == null && maxValue == null) {
     return null; 
   }
   IvlConverter.JavaPq low  = new IvlConverter.JavaPq(uom ,
      PAUtil.convertStringToDecimal(minValue), null);
   IvlConverter.JavaPq high  = new IvlConverter.JavaPq(uom , 
      PAUtil.convertStringToDecimal(maxValue), null);
   Ivl<Pq> ivl = IvlConverter.convertPq().convertToIvl(low, high);  
   return ivl;
  }
  

  /**
   * @return result
   */
  @Override
  public String input() {
    return ELIGIBILITYADD;
  }

  /**
   * Re order.
   * 
   * @return the string
   */
  public String reOrder() {
  StringBuffer ruleError = new StringBuffer();
  try {
    for (ISDesignDetailsWebDTO dto : getEligibilityList()) {
      ruleError.append(rulesForDisplayOrder(dto.getDisplayOrder()));
      ruleError.append(
        checkDisplayOrderExists(dto.getDisplayOrder(), Long.parseLong(dto.getId()), buildDisplayOrderUIList()));
    }
    if (ruleError.length() > 0) {
      addFieldError("reOrder", ruleError.toString());
    } else {
        for (ISDesignDetailsWebDTO dto : eligibilityList) {
        PlannedEligibilityCriterionDTO pecDTO = createPlannedEligibilityCriterion(dto, Long.parseLong(dto.getId()));
        PaRegistry.getPlannedActivityService().updatePlannedEligibilityCriterion(pecDTO);
        }
         ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
         query();
    }
  } catch (PAException e) {
      ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
  }
  return ELIGIBILITY;
  }
  
  
  /**
   * Generate.
   * 
   * @return the string
   * 
   * @throws PAException the PA exception
   */
  public String generate() throws PAException {
     StringBuffer generatedName = new StringBuffer();
     if (PAUtil.isNotEmpty(webDTO.getCriterionName())) {
         generatedName.append("Name: ").append(webDTO.getCriterionName());
     }
     if (PAUtil.isNotEmpty(webDTO.getOperator()) && webDTO.getOperator().equalsIgnoreCase("In")) {
         generatedName.append(SP).append("In:");
     }
     if (PAUtil.isNotEmpty(webDTO.getOperator()) && !webDTO.getOperator().equalsIgnoreCase("In")) {
         generatedName.append(SP).append(webDTO.getOperator());
     }
     if (PAUtil.isNotEmpty(webDTO.getValueIntegerMin())) {
      if (PAUtil.isNotEmpty(webDTO.getValueIntegerMin())) {
         generatedName.append(webDTO.getValueIntegerMin());
      }
      if (PAUtil.isNotEmpty(webDTO.getValueIntegerMax())) {
       generatedName.append(" - ").append(webDTO.getValueIntegerMax());
      }
      if (PAUtil.isNotEmpty(webDTO.getUnit())) {
         generatedName.append(SP).append(webDTO.getUnit());
      }
     } else if (PAUtil.isNotEmpty(webDTO.getValueText())) {
         generatedName.append(SP).append(webDTO.getValueText());
     }
     webDTO.setTextDescription(generatedName.toString());
    
     return ELIGIBILITYADD;
  }
  
  /**
   * @return result
   */
  public String create() {
   
   try {
      enforceEligibilityBusinessRules();
      if (hasFieldErrors()) {
       return ELIGIBILITYADD;
      }
      PlannedEligibilityCriterionDTO pecDTO = createPlannedEligibilityCriterion(webDTO, null);
      PaRegistry.getPlannedActivityService().createPlannedEligibilityCriterion(pecDTO);
      query();
      ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
      return ELIGIBILITY;
    } catch (Exception e) {
      ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
      return ELIGIBILITYADD;
    }
  }

  /**
   * @return result
   */
  public String edit() {
    try {
      PlannedEligibilityCriterionDTO sgDTO = PaRegistry.getPlannedActivityService()
      .getPlannedEligibilityCriterion(IiConverter.convertToIi(id));
      webDTO = setEligibilityDetailsDTO(sgDTO);
    } catch (Exception e) {
      ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
    }
    return ELIGIBILITYADD;
  }

  /**
   * @return result
   */
  public String update() {
   try { 
    enforceEligibilityBusinessRules();
    if (hasFieldErrors()) {
      return ELIGIBILITYADD;
    }
      PlannedEligibilityCriterionDTO pecDTO = createPlannedEligibilityCriterion(webDTO, id);
      PaRegistry.getPlannedActivityService().updatePlannedEligibilityCriterion(pecDTO);
      ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
      query();
    } catch (Exception e) {
      ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
      return ELIGIBILITYADD;
    }
    return ELIGIBILITY;
  }

  /**
   * @return result
   */
  public String delete() {

    try {
      PaRegistry.getPlannedActivityService().deletePlannedEligibilityCriterion(IiConverter.convertToIi(id));
      query();
      ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
    } catch (Exception e) {
      ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
    }
    return ELIGIBILITY;
  }
  
  /**
   * Display selected type.
   * 
   * @return the string
   * 
   * @throws PAException the PA exception
   */
  @SuppressWarnings({"PMD" })  
  public String displaySelectedType() throws PAException {
      String uomid = ServletActionContext.getRequest().getParameter("id");
      String className =  ServletActionContext.getRequest().getParameter("className");
      String divName =  ServletActionContext.getRequest().getParameter("divName");
       if (PAUtil.isNotEmpty(uomid)) {
        if ("UnitOfMeasurement".equalsIgnoreCase(className) && divName.contains("loadUOM")) {
           BaseLookUpService<UnitOfMeasurement> lookUpService = 
                        new BaseLookUpService<UnitOfMeasurement>(UnitOfMeasurement.class);
           UnitOfMeasurement uom = lookUpService.getById(Long.parseLong(uomid));
           webDTO.setUnit(uom.getCode());
         }
       }
       return divName; 
  }
  
  private PlannedEligibilityCriterionDTO createPlannedEligibilityCriterion(ISDesignDetailsWebDTO dtoWeb,
                                                                           Long identifier) {
     Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession()
       .getAttribute(Constants.STUDY_PROTOCOL_II);
      PlannedEligibilityCriterionDTO pecDTO = new PlannedEligibilityCriterionDTO();
      pecDTO.setIdentifier(IiConverter.convertToIi(identifier));
      pecDTO.setStudyProtocolIdentifier(studyProtocolIi);
      pecDTO.setCriterionName(StConverter.convertToSt(dtoWeb.getCriterionName()));
      pecDTO.setValue(convertToIvlPq(dtoWeb.getUnit(), dtoWeb.getValueIntegerMin(), dtoWeb.getValueIntegerMax()));
      if (dtoWeb.getInclusionIndicator() == null) {
          pecDTO.setInclusionIndicator(BlConverter.convertToBl(null));
      } else  if (dtoWeb.getInclusionIndicator().equalsIgnoreCase("Inclusion")) {
          pecDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
      } else {
           pecDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.FALSE));
      }
      pecDTO.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.OTHER));
      pecDTO.setTextDescription(StConverter.convertToSt(dtoWeb.getTextDescription()));
      pecDTO.setOperator(StConverter.convertToSt(dtoWeb.getOperator()));
      pecDTO.setDisplayOrder(IntConverter.convertToInt(dtoWeb.getDisplayOrder()));
      pecDTO.setTextValue(StConverter.convertToSt(dtoWeb.getValueText()));
      if (dtoWeb.getStructuredType() == null) {
          pecDTO.setStructuredIndicator(BlConverter.convertToBl(null));
      } else  if (dtoWeb.getStructuredType().equalsIgnoreCase("Structured")) {
          pecDTO.setStructuredIndicator(BlConverter.convertToBl(Boolean.TRUE));
      } else {
           pecDTO.setStructuredIndicator(BlConverter.convertToBl(Boolean.FALSE));
      }
      return pecDTO;
  }

  private ISDesignDetailsWebDTO setEligibilityDetailsDTO(PlannedEligibilityCriterionDTO dto) {
    ISDesignDetailsWebDTO webdto = new ISDesignDetailsWebDTO();
    if (dto != null) {
      if (dto.getEligibleGenderCode().getCode() != null) {
        eligibleGenderCode = dto.getEligibleGenderCode().getCode();
        eligibleGenderCodeId = dto.getIdentifier().getExtension();
      }
      if (dto.getCriterionName().getValue() != null
          && dto.getCriterionName().getValue().equals("AGE")) {
        if (dto.getValue().getHigh().getValue() != null) {  
          maximumValue = dto.getValue().getHigh().getValue().toString();
        }
        valueUnit = dto.getValue().getLow().getUnit();
        if (dto.getValue().getLow().getValue() != null) { 
          minimumValue = dto.getValue().getLow().getValue().toString();
        }
        valueId = dto.getIdentifier().getExtension();
      }
      if (dto.getCriterionName().getValue() != null) {
        webdto.setCriterionName(dto.getCriterionName().getValue());
      }
      if (dto.getInclusionIndicator().getValue() != null) {
        if (BlConverter.covertToBool(dto.getInclusionIndicator())) {
          webdto.setInclusionIndicator(("Inclusion"));
        } else {
          webdto.setInclusionIndicator("Exclusion");
        } 
      }
      if (dto.getIdentifier() != null) {
        webdto.setId(dto.getIdentifier().getExtension());
      }
      if (dto.getOperator() != null) {
        webdto.setOperator(dto.getOperator().getValue());
      }
      if (dto.getTextDescription() != null) {
        webdto.setTextDescription(dto.getTextDescription().getValue());
      }
      if (dto.getValue().getLow().getValue() != null) {
        webdto.setValueIntegerMin(dto.getValue().getLow().getValue().toString());
      }
      if (dto.getValue().getHigh().getValue() != null) {
          webdto.setValueIntegerMax(dto.getValue().getHigh().getValue().toString());
        }
      if (dto.getValue().getLow().getUnit() != null) {
        webdto.setUnit(dto.getValue().getLow().getUnit());
      }
      if (dto.getDisplayOrder() != null) {
          webdto.setDisplayOrder(IntConverter.convertToString(dto.getDisplayOrder()));
      }
      if (dto.getTextValue() != null) {
          webdto.setValueText(StConverter.convertToString(dto.getTextValue()));
      }
      if (dto.getStructuredIndicator().getValue() != null) {
          if (BlConverter.covertToBool(dto.getStructuredIndicator())) {
            webdto.setStructuredType("Structured");
          } else {
            webdto.setStructuredType("Unstructured");
          } 
        }
    }
    return webdto;
  }

  private void enforceBusinessRules() {

    StudyProtocolQueryDTO spqDTO = (StudyProtocolQueryDTO) ServletActionContext
    .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
    if (spqDTO.getStudyProtocolType().equalsIgnoreCase("ObservationalStudyProtocol")) {
      if (PAUtil.isEmpty(studyPopulationDescription)) {
        addFieldError("studyPopulationDescription", getText("error.trialPopulationDescription"));
      }
      if (PAUtil.isNotEmpty(studyPopulationDescription)
          && studyPopulationDescription.length() > MAXIMUM_CHAR_POPULATION) {
        addFieldError("studyPopulationDescription", getText("error.spType.population.maximumChar"));        
      }
      if (PAUtil.isEmpty(samplingMethodCode)) {
        addFieldError("samplingMethodCode", getText("error.samplingMethod"));
      }
    }

    if (PAUtil.isEmpty(acceptHealthyVolunteersIndicator)) {
      addFieldError("acceptHealthyVolunteersIndicator", getText("error.acceptHealthyVolunteersIndicator"));
    }
    if (PAUtil.isEmpty(eligibleGenderCode)) {
      addFieldError("eligibleGenderCode", getText("error.eligibleGenderCode"));
    }
    if (PAUtil.isEmpty(this.maximumValue)) {
      addFieldError("maximumValue", getText("error.maximumValue"));
    }
    if (PAUtil.isNotEmpty(this.maximumValue)) {
      try {
        Long.parseLong(maximumValue);
      } catch (NumberFormatException e) {
        addFieldError("maximumValue", getText("error.numeric"));
      }
    }
    if (PAUtil.isEmpty(this.valueUnit)) {
      addFieldError("valueUnit", getText("error.valueUnit"));
    }
    if (PAUtil.isEmpty(this.minimumValue)) {
      addFieldError("minimumValue", getText("error.minimumValue"));
    }
    if (PAUtil.isNotEmpty(this.minimumValue)) {
      try {
        Long.parseLong(minimumValue);
      } catch (NumberFormatException e) {
        addFieldError("minimumValue", getText("error.numeric"));
      }
    }
  }
  
  private void enforceEligibilityBusinessRules() throws PAException {
   
   String ruleError = rulesForDisplayOrder(webDTO.getDisplayOrder());  
   if (ruleError.length() > 0) {
      addFieldError("webDTO.displayOrder", ruleError);  
    }
    if (PAUtil.isNotEmpty(webDTO.getTextDescription())
        && webDTO.getTextDescription().length() > MAXIMUM_CHAR_DESCRIPTION) {
      addFieldError("webDTO.TextDescription", getText("error.spType.description.maximumChar"));        
    }
    if (PAUtil.isEmpty(webDTO.getTextDescription())) {
      if (PAUtil.isEmpty(webDTO.getCriterionName())  
          && PAUtil.isEmpty(webDTO.getOperator())
          && PAUtil.isEmpty(webDTO.getValueIntegerMin())
          && PAUtil.isEmpty(webDTO.getUnit())) {

        addFieldError("webDTO.mandatory",  getText("error.mandatory"));
        return;
      } else if (PAUtil.isEmpty(webDTO.getCriterionName())
          || PAUtil.isEmpty(webDTO.getOperator())
          || PAUtil.isEmpty(webDTO.getValueIntegerMin())
          || PAUtil.isEmpty(webDTO.getValueText())
          || PAUtil.isEmpty(webDTO.getUnit()))  {

        addFieldError("webDTO.buldcriterion", getText("error.buldcriterion"));

      }
      if (PAUtil.isNotEmpty(webDTO.getValueIntegerMax()) && PAUtil.isEmpty(webDTO.getValueIntegerMin())) {
         addFieldError("webDTO.valueIntegerMin", "Minimum value must be entered");
      }
     }
    String dispOrder = checkDisplayOrderExists(webDTO.getDisplayOrder(), id, buildDisplayOrderDBList());
    if (dispOrder != null && !dispOrder.equals("")) {
      addFieldError("webDTO.displayOrder", dispOrder);
    }
  }
  
  private String rulesForDisplayOrder(String displayOrder) throws PAException {
    StringBuffer err = new StringBuffer();
    if (PAUtil.isEmpty(displayOrder)) {
      err.append("Please enter display order\n");
    }
    if (PAUtil.isNotEmpty(displayOrder)) {
      try {
         Integer.parseInt(displayOrder);
      } catch (NumberFormatException e) {
       err.append("Please enter a numeric value for display order\n");
       }
    }
      return err.toString();
  }
  @SuppressWarnings({"PMD" })  
  private String checkDisplayOrderExists(String displayOrder, Long dtoId, HashMap<String, String> orderList) 
  throws PAException {
    boolean exists = false; 
    StringBuffer order =  new StringBuffer("Display Order(s) exist: ");
     if (orderList != null && !orderList.isEmpty()) {
       if (doesContainInList(displayOrder, dtoId, orderList)) {
             order.append(displayOrder).append(" ,");
             exists = true;
          }
     }
     if (exists) {
      return order.toString().substring(0, order.length() - 1);
     } else {
      return "";
     }
   }
  @SuppressWarnings({"PMD" })  
  private boolean doesContainInList(String displayOrder, Long dtoId, HashMap<String, String> dList) {
    boolean containsInList = false;
    ArrayList<String> valueList = new ArrayList<String>();
     if (dList != null && !dList.isEmpty()) {
        Iterator iterator = dList.keySet().iterator();
        while (iterator.hasNext()) {
         String keyId = (String) iterator.next();
          if (dtoId == null || !dtoId.toString().equals(keyId)) {           
            valueList.add(dList.get(keyId));
          }
        }
     }
     if (!valueList.isEmpty() && valueList.contains(displayOrder)) {
       containsInList = true;
     }
    
     return containsInList;
  }
  @SuppressWarnings({"PMD" }) 
  private HashMap<String, String> buildDisplayOrderDBList() throws PAException {
    HashMap<String, String> orderListDB = new HashMap<String, String>();
    Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(Constants.STUDY_PROTOCOL_II);
    List<PlannedEligibilityCriterionDTO> pecList = PaRegistry.getPlannedActivityService()
      .getPlannedEligibilityCriterionByStudyProtocol(studyProtocolIi);
    if (pecList != null && !pecList.isEmpty()) {
     for (PlannedEligibilityCriterionDTO dto : pecList) {
      if (dto.getCategoryCode() != null 
          && dto.getCategoryCode().getCode().equals(ActivityCategoryCode.OTHER.getCode())) {
           orderListDB
            .put(dto.getIdentifier().getExtension(), Integer.toString(dto.getDisplayOrder().getValue().intValue()));
         }
      }
     }
    return orderListDB;
  }
  @SuppressWarnings({"PMD" }) 
  private HashMap<String, String> buildDisplayOrderUIList() throws PAException {
    HashMap<String, String> orderListUI = new HashMap<String, String>();
    if (getEligibilityList() != null && !getEligibilityList().isEmpty()) {
     for (ISDesignDetailsWebDTO dto : getEligibilityList()) {
       if (dto.getDisplayOrder() != null) {
         orderListUI.put(dto.getId(), dto.getDisplayOrder());
        }
       }
      } 
      return orderListUI;
    }
  /**
   * @return webDTO
   */
  public ISDesignDetailsWebDTO getWebDTO() {
    return webDTO;
  }

  /**
   * @param webDTO
   *            webDTO
   */
  public void setWebDTO(ISDesignDetailsWebDTO webDTO) {
    this.webDTO = webDTO;
  }

  /**
   * @return id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *            id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return page
   */
  public String getPage() {
    return page;
  }

  /**
   * @param page
   *            page
   */
  public void setPage(String page) {
    this.page = page;
  }

  /**
   * @return eligibleGenderCode
   */
  public String getEligibleGenderCode() {
    return eligibleGenderCode;
  }

  /**
   * @param eligibleGenderCode
   *            eligibleGenderCode
   */
  public void setEligibleGenderCode(String eligibleGenderCode) {
    this.eligibleGenderCode = eligibleGenderCode;
  }

  /**
   * @return maximumValue
   */
  public String getMaximumValue() {
    return maximumValue;
  }

  /**
   * @param maximumValue
   *            maximumValue
   */
  public void setMaximumValue(String maximumValue) {
    this.maximumValue = maximumValue;
  }

  /**
   * @return minimumValue
   */
  public String getMinimumValue() {
    return minimumValue;
  }

  /**
   * @param minimumValue
   *            minimumValue
   */
  public void setMinimumValue(String minimumValue) {
    this.minimumValue = minimumValue;
  }

  /**
   * @return eligibleGenderCodeId
   */
  public String getEligibleGenderCodeId() {
    return eligibleGenderCodeId;
  }

  /**
   * @param eligibleGenderCodeId
   *            eligibleGenderCodeId
   */
  public void setEligibleGenderCodeId(String eligibleGenderCodeId) {
    this.eligibleGenderCodeId = eligibleGenderCodeId;
  }

  /**
   * @return acceptHealthyVolunteersIndicator
   */
  public String getAcceptHealthyVolunteersIndicator() {
    return acceptHealthyVolunteersIndicator;
  }

  /**
   * @param acceptHealthyVolunteersIndicator
   *            acceptHealthyVolunteersIndicator
   */
  public void setAcceptHealthyVolunteersIndicator(
      String acceptHealthyVolunteersIndicator) {
    this.acceptHealthyVolunteersIndicator = acceptHealthyVolunteersIndicator;
  }

  /**
   * @return eligibilityList
   */
  public List<ISDesignDetailsWebDTO> getEligibilityList() {
    return eligibilityList;
  }

  /**
   * @param eligibilityList
   *            eligibilityList
   */
  public void setEligibilityList(List<ISDesignDetailsWebDTO> eligibilityList) {
    this.eligibilityList = eligibilityList;
  }

  /**
   * @return studyPopulationDescription
   */
  public String getStudyPopulationDescription() {
    return studyPopulationDescription;
  }

  /**
   * @param studyPopulationDescription
   *            studyPopulationDescription
   */
  public void setStudyPopulationDescription(String studyPopulationDescription) {
    this.studyPopulationDescription = studyPopulationDescription;
  }

  /**
   * @return samplingMethodCode
   */
  public String getSamplingMethodCode() {
    return samplingMethodCode;
  }

  /**
   * @param samplingMethodCode
   *            samplingMethodCode
   */
  public void setSamplingMethodCode(String samplingMethodCode) {
    this.samplingMethodCode = samplingMethodCode;
  }
  
  /**
   * @return list
   */
  public List<ISDesignDetailsWebDTO> getList() {
    return list;
  }

  /**
   * @param list list
   */
  public void setList(List<ISDesignDetailsWebDTO> list) {
    this.list = list;
  }

  /**
   * @return the valueUnit
   */
   public String getValueUnit() {
    return valueUnit;
   }

  /**
   * @param valueUnit the valueUnit to set
   */
  public void setValueUnit(String valueUnit) {
   this.valueUnit = valueUnit;
  }

  /**
   * @return the valueId
   */
  public String getValueId() {
   return valueId;
  }

  /**
   * @param valueId the valueId to set
   */
  public void setValueId(String valueId) {
    this.valueId = valueId;
  }

  /**
   * 
   * @return cadsrResult
   */
  public List<DataElement> getCadsrResult() {
      return cadsrResult;
  }
  /**
   * 
   * @param result data element
   */
  public void setCadsrResult(List<DataElement> result) {
      this.cadsrResult = result;
  }
  
  /**
   * 
   * @return csisResult
   */
  public List<ClassificationSchemeItem> getCsisResult() {
      return csisResult;
  }
  
 }
