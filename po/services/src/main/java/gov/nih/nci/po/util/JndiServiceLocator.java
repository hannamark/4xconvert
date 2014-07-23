/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
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
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
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
package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.ClinicalResearchStaffCR;
import gov.nih.nci.po.data.bo.HealthCareProviderCR;
import gov.nih.nci.po.data.bo.IdentifiedOrganizationCR;
import gov.nih.nci.po.data.bo.IdentifiedPersonCR;
import gov.nih.nci.po.data.bo.OrganizationalContactCR;
import gov.nih.nci.po.data.bo.OversightCommitteeCR;
import gov.nih.nci.po.service.ClinicalResearchStaffCRServiceLocal;
import gov.nih.nci.po.service.ClinicalResearchStaffServiceLocal;
import gov.nih.nci.po.service.CountryServiceLocal;
import gov.nih.nci.po.service.FamilyOrganizationRelationshipServiceLocal;
import gov.nih.nci.po.service.FamilyServiceLocal;
import gov.nih.nci.po.service.GenericCodeValueServiceLocal;
import gov.nih.nci.po.service.GenericServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.HealthCareFacilityCRServiceLocal;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.HealthCareProviderCRServiceLocal;
import gov.nih.nci.po.service.HealthCareProviderServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationCrServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceLocal;
import gov.nih.nci.po.service.IdentifiedPersonCrServiceLocal;
import gov.nih.nci.po.service.IdentifiedPersonServiceLocal;
import gov.nih.nci.po.service.OrganizationCRServiceLocal;
import gov.nih.nci.po.service.OrganizationRelationshipServiceLocal;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.OrganizationalContactCRServiceLocal;
import gov.nih.nci.po.service.OrganizationalContactServiceLocal;
import gov.nih.nci.po.service.OversightCommitteeCRServiceLocal;
import gov.nih.nci.po.service.OversightCommitteeServiceLocal;
import gov.nih.nci.po.service.PatientServiceLocal;
import gov.nih.nci.po.service.PersonCRServiceLocal;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationCRServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.service.external.CtepImportService;

import com.fiveamsolutions.nci.commons.util.JndiUtils;

/**
 * @author Scott Miller
 */
@SuppressWarnings("PMD.CouplingBetweenObjects")
public class JndiServiceLocator implements ServiceLocator {

    /**
     * {@inheritDoc}
     */
    public GenericServiceLocal getGenericService()  {
        return (GenericServiceLocal) JndiUtils.lookup("java:app/po-services/GenericServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationServiceLocal getOrganizationService() {
        return (OrganizationServiceLocal) JndiUtils.lookup("java:app/po-services/OrganizationServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public FamilyServiceLocal getFamilyService() {
        return (FamilyServiceLocal) JndiUtils.lookup("java:app/po-services/FamilyServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public PersonServiceLocal getPersonService() {
        return (PersonServiceLocal) JndiUtils.lookup("java:app/po-services/PersonServiceBean");
    }

    @Override
    public PersonCRServiceLocal getPersonCRService() {
        return (PersonCRServiceLocal) JndiUtils.lookup("java:app/po-services/PersonCRServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public CountryServiceLocal getCountryService() {
        return (CountryServiceLocal) JndiUtils.lookup("java:app/po-services/CountryServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareProviderServiceLocal getHealthCareProviderService() {
        return (HealthCareProviderServiceLocal) JndiUtils.lookup("java:app/po-services/HealthCareProviderServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public OversightCommitteeServiceLocal getOversightCommitteeService() {
        return (OversightCommitteeServiceLocal) JndiUtils.lookup("java:app/po-services/OversightCommitteeServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public ResearchOrganizationServiceLocal getResearchOrganizationService() {
        return (ResearchOrganizationServiceLocal) JndiUtils
                .lookup("java:app/po-services/ResearchOrganizationServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacilityServiceLocal getHealthCareFacilityService() {
        return (HealthCareFacilityServiceLocal) JndiUtils.lookup("java:app/po-services/HealthCareFacilityServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public ClinicalResearchStaffServiceLocal getClinicalResearchStaffService() {
        return (ClinicalResearchStaffServiceLocal) JndiUtils
                .lookup("java:app/po-services/ClinicalResearchStaffServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public PatientServiceLocal getPatientService() {
        return (PatientServiceLocal) JndiUtils.lookup("java:app/po-services/PatientServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedOrganizationServiceLocal getIdentifiedOrganizationService() {
        return (IdentifiedOrganizationServiceLocal) JndiUtils
                .lookup("java:app/po-services/IdentifiedOrganizationServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedPersonServiceLocal getIdentifiedPersonService() {
        return (IdentifiedPersonServiceLocal) JndiUtils.lookup("java:app/po-services/IdentifiedPersonServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationalContactServiceLocal getOrganizationalContactService() {
        return (OrganizationalContactServiceLocal) JndiUtils
                .lookup("java:app/po-services/OrganizationalContactServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public GenericCodeValueServiceLocal getGenericCodeValueService() {
        return (GenericCodeValueServiceLocal) JndiUtils.lookup("java:app/po-services/GenericCodeValueServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public CtepImportService getCtepImportService() {
        return (CtepImportService) JndiUtils.lookup("java:app/po-services/CtepImportServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationCRServiceLocal getOrganizationCRService() {
        return (OrganizationCRServiceLocal) JndiUtils.lookup("java:app/po-services/OrganizationCRServiceBean");
    }
    /**
     * {@inheritDoc}
     */
    public GenericStructrualRoleCRServiceLocal<ClinicalResearchStaffCR> getClinicalResearchStaffCRService() {
        return (ClinicalResearchStaffCRServiceLocal) JndiUtils
                .lookup("java:app/po-services/ClinicalResearchStaffCRServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacilityCRServiceLocal getHealthCareFacilityCRService() {

        return (HealthCareFacilityCRServiceLocal) JndiUtils
                .lookup("java:app/po-services/HealthCareFacilityCRServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public GenericStructrualRoleCRServiceLocal<HealthCareProviderCR> getHealthCareProviderCRService() {
        return (HealthCareProviderCRServiceLocal) JndiUtils
                .lookup("java:app/po-services/HealthCareProviderCRServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public GenericStructrualRoleCRServiceLocal<IdentifiedOrganizationCR> getIdentifiedOrganizationCRService() {
        return (IdentifiedOrganizationCrServiceLocal) JndiUtils
                .lookup("java:app/po-services/IdentifiedOrganizationCrServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public GenericStructrualRoleCRServiceLocal<IdentifiedPersonCR> getIdentifiedPersonCRService() {
        return (IdentifiedPersonCrServiceLocal) JndiUtils
                .lookup("java:app/po-services/IdentifiedPersonCrServiceBean");
    }

    @Override
    public GenericStructrualRoleCRServiceLocal<OrganizationalContactCR> getOrganizationalContactCRService() {
        return (OrganizationalContactCRServiceLocal) JndiUtils
                .lookup("java:app/po-services/OrganizationalContactCRServiceBean");
    }

    @Override
    public GenericStructrualRoleCRServiceLocal<OversightCommitteeCR> getOversightCommitteeCRService() {
        return (OversightCommitteeCRServiceLocal) JndiUtils
                .lookup("java:app/po-services/OversightCommitteeCRServiceBean");
    }

    @Override
    public ResearchOrganizationCRServiceLocal getResearchOrganizationCRService() {
        return (ResearchOrganizationCRServiceLocal) JndiUtils
                .lookup("java:app/po-services/ResearchOrganizationCRServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public FamilyOrganizationRelationshipServiceLocal getFamilyOrganizationRelationshipService() {
        return (FamilyOrganizationRelationshipServiceLocal) JndiUtils
            .lookup("java:app/po-services/FamilyOrganizationRelationshipServiceBean");
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationRelationshipServiceLocal getOrganizationRelationshipService() {
        return (OrganizationRelationshipServiceLocal) JndiUtils
                .lookup("java:app/po-services/OrganizationRelationshipServiceBean");
    }
}
