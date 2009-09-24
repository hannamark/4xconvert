/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The PA Grid
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This PA Grid Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the PA Grid Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the PA Grid Software; (ii) distribute and
 * have distributed to and by third parties the PA Grid Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.pa.iso.dto.BaseDTO;
import gov.nih.nci.pa.iso.dto.StudyDTO;
import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.BasePaService;
import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.service.StudyContactServiceRemote;
import gov.nih.nci.pa.service.StudyCurrentPaService;
import gov.nih.nci.pa.service.StudyDiseaseServiceRemote;
import gov.nih.nci.pa.service.StudyIndldeServiceRemote;
import gov.nih.nci.pa.service.StudyOnholdServiceRemote;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyPaService;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyRelationshipServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.StudySiteContactServiceRemote;
import gov.nih.nci.pa.service.StudySiteServiceRemote;
import gov.nih.nci.pa.service.TrialRegistrationServiceRemote;

import javax.naming.NamingException;

/**
 * Locator interface for PA services.
 */
/**
 * @author Steve Lustbader
 */
public interface ServiceLocator {

    /**
     * @return the remote Arm service
     * @throws NamingException if unable to lookup
     */
    ArmServiceRemote getArmService() throws NamingException;

    /**
     * @return the remote StudyProtocol service
     * @throws NamingException if unable to lookup
     */
    StudyProtocolServiceRemote getStudyProtocolService() throws NamingException;

    /**
     * Gets the StudyResourcing service.
     * @return the remote StudyResourcingService
     * @throws NamingException if unable to lookup
     */
    StudyResourcingServiceRemote getStudyResourcingService() throws NamingException;

    /**
     * Gets the StudyRegulatoryAuthority service.
     * @return the remote StudyRegulatoryAuthorityService
     * @throws NamingException if unable to lookup
     */
    StudyRegulatoryAuthorityServiceRemote getStudyRegulatoryAuthorityService() throws NamingException;

    /**
     * Gets the StudyRecruitmentStatusService.
     * @return the remote StudyRecruitmentStatusService.
     * @throws NamingException if unable to lookup.
     */
    StudyRecruitmentStatusServiceRemote getStudyRecruitmentStatusService() throws NamingException;

    /**
     * Gets the StudySiteAccrualStatus service.
     * @return the remote StudySiteAccrualStatusService
     * @throws NamingException if unable to lookup
     */
    StudySiteAccrualStatusServiceRemote getStudySiteAccrualStatusService() throws NamingException;

    /**
     * Gets the StudySiteContact service.
     * @return the remote StudySiteContactService
     * @throws NamingException if unable to lookup.
     */
    StudySiteContactServiceRemote getStudySiteContactService() throws NamingException;

    /**
     * Gets the StudyOutcomeMeasure Service.
     * @return the remote StudyOutcomeMeasureService
     * @throws NamingException if unable to lookup.
     */
    StudyOutcomeMeasureServiceRemote getStudyOutcomeMeasureService() throws NamingException;

    /**
     * Gets the StudySite Service.
     * @return the remote StudySiteService.
     * @throws NamingException if unable to lookup.
     */
    StudySiteServiceRemote getStudySiteService() throws NamingException;

    /**
     * Gets the StudyOverallStatus service.
     * @return the remote StudyOverallStatus service
     * @throws NamingException if unable to lookup.
     */
    StudyOverallStatusServiceRemote getStudyOverallStatusService() throws NamingException;

    /**
     * Gets the StudyDisease service.
     * @return the remote StudyDisease service
     * @throws NamingException if unable to lookup.
     */
    StudyDiseaseServiceRemote getStudyDiseaseService() throws NamingException;

    /**
     * Gets the StudyOnhold service.
     * @return the remote StudyOnhold service
     * @throws NamingException if unable to lookup.
     */
    StudyOnholdServiceRemote getStudyOnholdService() throws NamingException;

    /**
     * Gets the StudyContact service.
     * @return the remote StudyContact service
     * @throws NamingException if unable to lookup.
     */
    StudyContactServiceRemote getStudyContactService() throws NamingException;

    /**
     * Gets the StudyIndlde service.
     * @return the remote StudyIndlde service
     * @throws NamingException if unable to lookup.
     */
    StudyIndldeServiceRemote getStudyIndldeService() throws NamingException;

    /**
     * Gets the StudyRelationship service.
     * @return the remote StudyRelationship service
     * @throws NamingException if unable to lookup.
     */
    StudyRelationshipServiceRemote getStudyRelationshipService() throws NamingException;

    /**
     * Gets the Document service.
     * @return the remote Document service
     * @throws NamingException if unable to lookup.
     */
    DocumentServiceRemote getDocumentService() throws NamingException;

    /**
     * Gets the PlannedActivity service.
     * @return the remote PlannedActivity service
     * @throws NamingException if unable to lookup.
     */
    PlannedActivityServiceRemote getPlannedActivityService() throws NamingException;

   /**
    * Gets a base generic service.
    * @param <Z> BasePa DTO type
    * @param type Correlation DTO class
    * @return BasePaService
    * @throws NamingException on error looking up the service
    */
   @SuppressWarnings("unchecked")
   <Z extends BaseDTO> BasePaService getBasePaService(Class<Z> type) throws NamingException;

   /**
    * Gets a study generic service.
    * @param <S> StudyPa DTO type
    * @param type Correlation DTO class
    * @return StudyPaService
    * @throws NamingException on error looking up the service
    */
   @SuppressWarnings("unchecked")
   <S extends StudyDTO> StudyPaService getStudyPaService(Class<S> type) throws NamingException;

   /**
    * Gets a study current service.
    * @param <S> StudyPa DTO type
    * @param type Correlation DTO class
    * @return StudyCurrentPaService
    * @throws NamingException on error looking up the service
    */
   @SuppressWarnings("unchecked")
   <S extends StudyDTO> StudyCurrentPaService getStudyCurrentPaService(Class<S> type) throws NamingException;

   /**
    * Gets the TrialRegistrationService service.
    * @return the remote TrialRegistrationService service
    * @throws NamingException if unable to lookup.
    */
   TrialRegistrationServiceRemote getTrialRegistrationService() throws NamingException;
}
