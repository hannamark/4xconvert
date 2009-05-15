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
package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.ArmServiceBean;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DiseaseServiceBean;
import gov.nih.nci.pa.service.DiseaseServiceRemote;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceBean;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceBean;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceBean;
import gov.nih.nci.pa.service.InterventionServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceBean;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.PoPaMockServiceLocator;
import gov.nih.nci.pa.service.StudyContactServiceBean;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceBean;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceBean;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceBean;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceBean;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyParticipationContactServiceBean;
import gov.nih.nci.pa.service.StudyParticipationContactServiceLocal;
import gov.nih.nci.pa.service.StudyParticipationServiceBean;
import gov.nih.nci.pa.service.StudyParticipationServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceBean;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceBean;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceBean;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceBean;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.SubGroupsServiceBean;
import gov.nih.nci.pa.service.SubGroupsServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.correlation.PoPaRegistry;
import gov.nih.nci.pa.util.TestSchema;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class TSRReportGeneratorServiceTest {
    
    private TSRReportGeneratorServiceBean bean = new TSRReportGeneratorServiceBean();
    
    private final StudyProtocolServiceLocal studyProtocolService = new StudyProtocolServiceBean();
    
    StudyOverallStatusServiceLocal studyOverallStatusService = new StudyOverallStatusServiceBean();
    
    StudyIndldeServiceLocal studyIndldeService  = new StudyIndldeServiceBean();
    
    StudyDiseaseServiceLocal studyDiseaseService = new StudyDiseaseServiceBean();
    
    ArmServiceLocal armService = new ArmServiceBean() ;
    
    PlannedActivityServiceLocal plannedActivityService = new PlannedActivityServiceBean();
    
    SubGroupsServiceLocal subGroupsService = new SubGroupsServiceBean();
    
    StudyParticipationServiceLocal studyParticipationService = new StudyParticipationServiceBean();
    
    StudyParticipationContactServiceLocal studyParticipationContactService = new StudyParticipationContactServiceBean();
    
    StudyContactServiceLocal studyContactService = new StudyContactServiceBean();
    
    StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = new StudySiteAccrualStatusServiceBean();
    
    StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = new StudyOutcomeMeasureServiceBean();
    
    StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService = new StudyRegulatoryAuthorityServiceBean();
    
    OrganizationCorrelationServiceRemote ocsr = new OrganizationCorrelationServiceBean();
    
    DocumentWorkflowStatusServiceLocal documentWorkflowStatusService = new DocumentWorkflowStatusServiceBean();
    
    RegulatoryInformationServiceRemote regulatoryInformationService = new RegulatoryInformationBean();
    
    DiseaseServiceRemote diseaseService = new DiseaseServiceBean();
    
    InterventionServiceRemote interventionService = new InterventionServiceBean();
    
    InterventionAlternateNameServiceRemote interventionAlternateNameService = new InterventionAlternateNameServiceBean();
    
    StudyResourcingServiceRemote  studyResourcingService = new StudyResourcingServiceBean();
    
    PAOrganizationServiceRemote  paOrganizationService = new PAOrganizationServiceBean();
    
    //private TSRReportGeneratorServiceRemote remoteEjb = bean;

    @Before
    public void setUp() throws Exception {
        
        bean.studyProtocolService = studyProtocolService;
        bean.armService = armService;
        bean.documentWorkflowStatusService =documentWorkflowStatusService;
        bean.ocsr = ocsr;
        bean.plannedActivityService = plannedActivityService;
        bean.studyContactService = studyContactService;
        bean.studyDiseaseService = studyDiseaseService;
        bean.studyIndldeService=studyIndldeService;
        bean.studyParticipationService=studyParticipationService;
        bean.studyOutcomeMeasureService=studyOutcomeMeasureService;
        bean.studyOverallStatusService=studyOverallStatusService;
        bean.studyParticipationContactService=studyParticipationContactService;
        bean.studyRegulatoryAuthorityService=studyRegulatoryAuthorityService;
        bean.studySiteAccrualStatusService=studySiteAccrualStatusService;
        bean.subGroupsService=  subGroupsService;  
        bean.regulatoryInformationService = regulatoryInformationService;
        bean.diseaseService = diseaseService;
        bean.interventionAlternateNameService = interventionAlternateNameService;
        bean.interventionService = interventionService; 
        bean.studyResourcingService = studyResourcingService;
        bean.paOrganizationService = paOrganizationService;
        
        TestSchema.reset();
        TestSchema.primeData();
      }
    
    @Test(expected=PAException.class)
    public void nullParameter() throws Exception {
        bean.generateTSRHtml(null);
    }
    @Test
    public void generateTSRHtmlTest() throws Exception {
        Ii ii = IiConverter.converToStudyProtocolIi(Long.valueOf(1));
        String x = bean.generateTSRHtml(ii);
        assertNotNull(x);
    }
}
