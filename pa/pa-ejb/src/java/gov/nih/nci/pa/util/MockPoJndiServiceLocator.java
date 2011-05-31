/**
 The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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
package gov.nih.nci.pa.util;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
/**
 * Used for selenium so that po doesn't need to be deployed.
 * @author vrushali
 *
 */
public class MockPoJndiServiceLocator implements PoServiceLocator {
    private static final Logger LOG = Logger.getLogger(MockPoJndiServiceLocator.class);
    private static final String ERROR_MSG = "An error occurred during mock set up process.";

    /**
     * {@inheritDoc}
     */
    public ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService() {
        ClinicalResearchStaffCorrelationServiceRemote crsSvc =
            mock(ClinicalResearchStaffCorrelationServiceRemote.class);
        try {
            ClinicalResearchStaffDTO csrDTO = new ClinicalResearchStaffDTO();
            csrDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoClinicalResearchStaffIi("5")));
            csrDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("1"));
            csrDTO.setStatus(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
            when(crsSvc.getCorrelation(any(Ii.class))).thenReturn(csrDTO);
            when(crsSvc.search(any(ClinicalResearchStaffDTO.class), any(LimitOffset.class)))
                 .thenReturn(Arrays.asList(csrDTO));
            when(crsSvc.search(any(ClinicalResearchStaffDTO.class))).thenReturn(Arrays.asList(csrDTO));
            when(crsSvc.createCorrelation(any(ClinicalResearchStaffDTO.class))).thenReturn(
                    IiConverter.convertToPoClinicalResearchStaffIi("6"));
        } catch (Exception e) {
            LOG.error(ERROR_MSG, e);
        }
        return crsSvc;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() {
        HealthCareFacilityCorrelationServiceRemote hcfSvc = mock(HealthCareFacilityCorrelationServiceRemote.class);
        try {
            HealthCareFacilityDTO hcfDTO = new HealthCareFacilityDTO();
            hcfDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoHealthCareFacilityIi("11")));
            hcfDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("1"));
            hcfDTO.setStatus(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
            when(hcfSvc.getCorrelation(any(Ii.class))).thenReturn(hcfDTO);
            when(hcfSvc.search(any(HealthCareFacilityDTO.class), any(LimitOffset.class)))
                 .thenReturn(Arrays.asList(hcfDTO));
            when(hcfSvc.search(any(HealthCareFacilityDTO.class))).thenReturn(Arrays.asList(hcfDTO));
            when(hcfSvc.createCorrelation(any(HealthCareFacilityDTO.class))).thenReturn(
                    IiConverter.convertToPoHealthCareFacilityIi("12"));
        } catch (Exception e) {
            LOG.error(ERROR_MSG, e);
        }
        return hcfSvc;
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() {
        HealthCareProviderCorrelationServiceRemote hcpSvc = mock(HealthCareProviderCorrelationServiceRemote.class);
        try {
            HealthCareProviderDTO hcpDTO = new HealthCareProviderDTO();
            hcpDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoHealtcareProviderIi("7")));
            hcpDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("1"));
            hcpDTO.setStatus(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
            when(hcpSvc.getCorrelation(any(Ii.class))).thenReturn(hcpDTO);
            when(hcpSvc.search(any(HealthCareProviderDTO.class), any(LimitOffset.class)))
                 .thenReturn(Arrays.asList(hcpDTO));
            when(hcpSvc.search(any(HealthCareProviderDTO.class))).thenReturn(Arrays.asList(hcpDTO));
            when(hcpSvc.createCorrelation(any(HealthCareProviderDTO.class))).thenReturn(
                    IiConverter.convertToPoHealtcareProviderIi("8"));
        } catch (Exception e) {
            LOG.error(ERROR_MSG, e);
        }
        return hcpSvc;
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService() {
        IdentifiedOrganizationCorrelationServiceRemote  idPersonSvc = mock(
                IdentifiedOrganizationCorrelationServiceRemote .class);
        IdentifiedOrganizationDTO identifiedOrgDTO = new IdentifiedOrganizationDTO();
        identifiedOrgDTO.setAssignedId(IiConverter.convertToIdentifiedPersonEntityIi("ctepId"));
        try {
        when(idPersonSvc.getCorrelation(any(Ii.class))).thenReturn(identifiedOrgDTO);
        when(idPersonSvc.search(any(IdentifiedOrganizationDTO.class), any(LimitOffset.class)))
            .thenReturn(Arrays.asList(identifiedOrgDTO));
        } catch (Exception e) {
            LOG.error(ERROR_MSG, e);
        }
        return idPersonSvc;

    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonEntityService() {
        IdentifiedPersonCorrelationServiceRemote idPersonSvc = mock(IdentifiedPersonCorrelationServiceRemote.class);
        IdentifiedPersonDTO identifiedPersonDTO = new IdentifiedPersonDTO();
        identifiedPersonDTO.setAssignedId(IiConverter.convertToIdentifiedPersonEntityIi("ctepId"));
        try {
        when(idPersonSvc.getCorrelation(any(Ii.class))).thenReturn(identifiedPersonDTO);
        when(idPersonSvc.search(any(IdentifiedPersonDTO.class), any(LimitOffset.class)))
            .thenReturn(Arrays.asList(identifiedPersonDTO));
        } catch (Exception e) {
            LOG.error(ERROR_MSG, e);
        }
        return idPersonSvc;
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationEntityServiceRemote getOrganizationEntityService() {
        OrganizationEntityServiceRemote poOrgSvc = mock(OrganizationEntityServiceRemote.class);
        OrganizationDTO orgDto = new OrganizationDTO();
        orgDto.setIdentifier(IiConverter.convertToPoOrganizationIi("1"));
        orgDto.setPostalAddress(getAddress());
        orgDto.setName(EnOnConverter.convertToEnOn("some org name"));
        orgDto.setTelecomAddress(getTelAdd());
        orgDto.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
        DSet famOrgRel = new DSet();
        famOrgRel.setItem(new HashSet());
        orgDto.setFamilyOrganizationRelationships(famOrgRel);
        try {
            when(poOrgSvc.getOrganization(any(Ii.class))).thenReturn(orgDto);
            when(poOrgSvc.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(Arrays.asList(orgDto));
            when(poOrgSvc.search(any(OrganizationDTO.class), any(EnOn.class), any(LimitOffset.class)))
                .thenReturn(Arrays.asList(orgDto));
            when(poOrgSvc.validate(any(OrganizationDTO.class))).thenReturn(new HashMap<String, String[]>());
        } catch (Exception e) {
            LOG.error(ERROR_MSG, e);
        }
        return poOrgSvc;
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationService() {
       OrganizationalContactCorrelationServiceRemote occSvc = mock(OrganizationalContactCorrelationServiceRemote.class);
       try {
           OrganizationalContactDTO orgContDTO = new OrganizationalContactDTO();
           orgContDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoOrganizationalContactIi(
                   "12")));
           orgContDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("1"));
           orgContDTO.setStatus(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
           orgContDTO.setTitle(StConverter.convertToSt("some Title"));
           orgContDTO.setScoperIdentifier(IiConverter.convertToPoPersonIi("2"));
           orgContDTO.setTelecomAddress(getTelAdd());
           when(occSvc.getCorrelation(any(Ii.class))).thenReturn(orgContDTO);
           when(occSvc.search(any(OrganizationalContactDTO.class), any(LimitOffset.class)))
                .thenReturn(Arrays.asList(orgContDTO));
           when(occSvc.search(any(OrganizationalContactDTO.class))).thenReturn(Arrays.asList(orgContDTO));
           when(occSvc.createCorrelation(any(OrganizationalContactDTO.class))).thenReturn(
               IiConverter.convertToPoOrganizationalContactIi("13"));
       } catch (Exception e) {
           LOG.error(ERROR_MSG, e);
       }
       return occSvc;
    }

    /**
     * {@inheritDoc}
     */
    public OversightCommitteeCorrelationServiceRemote getOversightCommitteeCorrelationService() {
        OversightCommitteeCorrelationServiceRemote occSvc = mock(OversightCommitteeCorrelationServiceRemote.class);
        try {
            OversightCommitteeDTO ocDTO = new OversightCommitteeDTO();
            ocDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoOversightCommitteeIi("9")));
            ocDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("1"));
            ocDTO.setTypeCode(CdConverter.convertStringToCd("Institutional Review Board (IRB)"));
            ocDTO.setStatus(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
            when(occSvc.getCorrelation(any(Ii.class))).thenReturn(ocDTO);
            when(occSvc.search(any(OversightCommitteeDTO.class), any(LimitOffset.class)))
                 .thenReturn(Arrays.asList(ocDTO));
            when(occSvc.search(any(OversightCommitteeDTO.class))).thenReturn(Arrays.asList(ocDTO));
            when(occSvc.createCorrelation(any(OversightCommitteeDTO.class))).thenReturn(
                IiConverter.convertToPoOversightCommitteeIi("10"));
        } catch (Exception e) {
            LOG.error(ERROR_MSG, e);
        }

        return occSvc;
    }

    /**
     * {@inheritDoc}
     */
    public PersonEntityServiceRemote getPersonEntityService() {
        PersonEntityServiceRemote poPerSvc = mock(PersonEntityServiceRemote.class);
        PersonDTO personDto = new PersonDTO();
        personDto.setIdentifier(IiConverter.convertToPoPersonIi("2"));
        personDto.setName(EnPnConverter.convertToEnPn("Person", "middle", "LastName", "4", "5"));
        personDto.setPostalAddress(getAddress());
        personDto.setTelecomAddress(getTelAdd());
        personDto.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
        try {
            when(poPerSvc.getPerson(any(Ii.class))).thenReturn(personDto);
            when(poPerSvc.search(any(PersonDTO.class), any(LimitOffset.class))).thenReturn(Arrays.asList(personDto));
            when(poPerSvc.validate(any(PersonDTO.class))).thenReturn(new HashMap<String, String[]>());
        } catch (Exception e) {
            LOG.error(ERROR_MSG, e);
        }
        return poPerSvc;
    }

    /**
     * {@inheritDoc}
     */
    public ResearchOrganizationCorrelationServiceRemote getResearchOrganizationCorrelationService() {
        ResearchOrganizationCorrelationServiceRemote roOrgSvc =
            mock(ResearchOrganizationCorrelationServiceRemote.class);
        try {
            ResearchOrganizationDTO roDTO = new ResearchOrganizationDTO();
            roDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoResearchOrganizationIi("3")));
            roDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("1"));
            roDTO.setStatus(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
            when(roOrgSvc.getCorrelation(any(Ii.class))).thenReturn(roDTO);
            when(roOrgSvc.search(any(ResearchOrganizationDTO.class), any(LimitOffset.class)))
                 .thenReturn(Arrays.asList(roDTO));
            when(roOrgSvc.search(any(ResearchOrganizationDTO.class))).thenReturn(Arrays.asList(roDTO));
            when(roOrgSvc.createCorrelation(any(ResearchOrganizationDTO.class))).thenReturn(
                    IiConverter.convertToPoResearchOrganizationIi("4"));
        } catch (Exception e) {
            LOG.error(ERROR_MSG, e);
        }
        return roOrgSvc;
    }

    /**
     * {@inheritDoc}
     */
    public FamilyServiceRemote getFamilyService() {
        FamilyServiceRemote remote = mock(FamilyServiceRemote.class);
        try {
            FamilyDTO family = new FamilyDTO();
            family.setName(EnOnConverter.convertToEnOn("some family name"));
            family.setStatusCode(CdConverter.convertStringToCd("active"));
            family.setIdentifier(IiConverter.convertToIi(1L));
            List<FamilyDTO> familyList = new ArrayList<FamilyDTO>();
            familyList.add(family);
            when(remote.getFamily(any(Ii.class))).thenReturn(family);
            when(remote.search(any(FamilyDTO.class), any(LimitOffset.class))).thenReturn(familyList);
            when(remote.getActiveRelationships(anyLong())).thenReturn(
                    new ArrayList<FamilyOrganizationRelationshipDTO>());
        } catch (Exception e) {
            LOG.error(ERROR_MSG, e);
        }
        return remote;
    }

    /**
     * @return
     */
    private Ad getAddress() {
        return AddressConverterUtil.create("street", "deliv", "city", "MD", "20000", "USA");
    }
    /**
     *
     * @return
     */
    private DSet<Tel> getTelAdd() {
        DSet<Tel> telAd = new DSet<Tel>();
        Set<Tel> telSet = new HashSet<Tel>();
        try {
            TelEmail email = new TelEmail();
            email.setValue(new URI("mailto:test@example.com"));
            telSet.add(email);
            TelPhone phone = new TelPhone();
            phone.setValue(new URI("tel:111-222-3333"));
            telSet.add(phone);
            TelUrl url = new TelUrl();
            url.setValue(new URI("http://example.com"));
            telSet.add(url);
        } catch (URISyntaxException e) {
            LOG.error(e);
        }
        telAd.setItem(telSet);
        return telAd;
    }
}
