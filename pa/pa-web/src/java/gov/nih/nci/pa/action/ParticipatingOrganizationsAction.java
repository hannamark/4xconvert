/**
 * 
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.util.PAHealthCareFacilityServiceRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class for viewing and editing the participating organizations.
 * 
 * @author Hugh Reinhart
 * @since 08/20/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 * 
 */
@SuppressWarnings("PMD")
public class ParticipatingOrganizationsAction extends ActionSupport implements
           Preparable {
       private static final long serialVersionUID = 123412653L;

       private StudyProtocolServiceRemote sProService;
       private StudyParticipationServiceRemote sPartService;
       private PAHealthCareFacilityServiceRemote hfService;
       private PAOrganizationServiceRemote paoService;

       private Ii spIi;
       
       /** 
        * @see com.opensymphony.xwork2.Preparable#prepare()
        * @throws Exception e
        */
       public void prepare() throws Exception {
           sProService = PaRegistry.getStudyProtocolService();
           sPartService = PaRegistry.getStudyParticipationService();
           hfService = PaRegistry.getPAHealthCareFacilityService();
           paoService = PaRegistry.getPAOrganizationService();

           StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
                   .getRequest().getSession()
                   .getAttribute(Constants.TRIAL_SUMMARY);
           
           spIi = IiConverter.convertToIi(spDTO.getStudyProtocolId());
       }

       /**
        * @return Action result.
        * @throws Exception exception.
        */
       @Override
       public String execute() throws Exception {
           loadForm();
           return SUCCESS;
       }

       /**  
        * @return result
        * @throws Exception exception
        */
       public String update() throws Exception {
           clearErrorsAndMessages();
           enforceBusinessRules();
           if (hasActionErrors()) {
               return Action.SUCCESS;
           }
           if (!hasActionErrors()) {
               addActionError("Update succeeded.");
           }
           loadForm();
           return Action.SUCCESS;
       }
       
    
       private void loadForm() throws Exception {
           StudyProtocolDTO sPro = sProService.getStudyProtocol(spIi);
//           List<StudyParticipationDTO> sPartList = sPartService.getStudyParticipationByStudyProtocol(spIi);
//           ArrayList<HealthCareFacilityDTO> hfList = new ArrayList<HealthCareFacilityDTO>();
//           for (StudyParticipationDTO sPart : sPartList) {
//               hfList.add(hfService.getHealthCareFacility(sPart.getHealthcareFacilityIi()));
//           }
//           ArrayList<OrganizationDTO> paoList = new ArrayList<OrganizationDTO>();
//           for (HealthCareFacilityDTO hf : hfList) {
//               paoService.getOrganization(hf.getOrganizationIi());
//           }
       }

       /**
        * This method is used to enforce the business rules which are form specific or
        * based on an interaction between services.
        */
       private void enforceBusinessRules() {
       }

   }
