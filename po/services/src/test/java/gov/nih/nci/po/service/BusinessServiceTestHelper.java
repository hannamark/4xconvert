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
package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.services.BusinessServiceRemote;
import gov.nih.nci.services.RoleList;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
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
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * @author mshestopalov
 *
 */
public class BusinessServiceTestHelper {

    public static final String STREET_LINE = "123 Business Service Test Ave.";
    public static final String COUNTRY = "USA";
    public static final String ORG_NAME = "The Org Name";
    public static final String PERSON_LAST_NAME = "The Person Last Name";
    private static final Bl trueBl = new Bl();
    private static final Bl falseBl = new Bl();

    static {
        trueBl.setValue(true);
        falseBl.setValue(false);
    }

    public static void helpTestOrgRoleCorrelationsGetById(
            ResearchOrganizationCorrelationServiceRemote researchOrgService,
            BusinessServiceRemote busService,
            OrganizationEntityServiceRemote orgService) throws Exception {

        Ii newOrgId = createOrganization(orgService);

        ResearchOrganizationDTO roDto = new ResearchOrganizationDTO();
        roDto.setName(TestConvertHelper.convertToEnOn("Research Org 1"));
        roDto.setPlayerIdentifier(newOrgId);

        Ii roDtoId = researchOrgService.createCorrelation(roDto);
        assertNotNull(roDtoId);

        CorrelationNodeDTO corrNodeDto = busService.getCorrelationByIdWithEntities(roDtoId, trueBl, falseBl);
        assertTrue(corrNodeDto.getCorrelation() instanceof ResearchOrganizationDTO);
        assertEquals("Research Org 1", ((ResearchOrganizationDTO)
                corrNodeDto.getCorrelation()).getName().getPart().get(0).getValue());
        assertTrue(corrNodeDto.getPlayer() instanceof OrganizationDTO);
        assertEquals(ORG_NAME, ((OrganizationDTO)
                corrNodeDto.getPlayer()).getName().getPart().get(0).getValue());
        assertNull(corrNodeDto.getScoper());

        corrNodeDto = busService.getCorrelationByIdWithEntities(roDtoId, trueBl, trueBl);
        assertNotNull(corrNodeDto.getPlayer());
        assertNull(corrNodeDto.getScoper());

        corrNodeDto = busService.getCorrelationByIdWithEntities(roDtoId, falseBl, trueBl);
        assertNull(corrNodeDto.getPlayer());
        assertNull(corrNodeDto.getScoper());

        corrNodeDto = busService.getCorrelationByIdWithEntities(roDtoId, falseBl, falseBl);
        assertNull(corrNodeDto.getPlayer());
        assertNull(corrNodeDto.getScoper());

        ResearchOrganizationDTO roDto2 = new ResearchOrganizationDTO();
        roDto2.setName(TestConvertHelper.convertToEnOn("Research Org 2"));
        roDto2.setPlayerIdentifier(newOrgId);

        Ii roDtoId2 = researchOrgService.createCorrelation(roDto2);
        assertNotNull(roDtoId2);

        List<CorrelationNodeDTO> corrNodeDtos = busService.getCorrelationsByIdsWithEntities(
                new Ii[]{roDtoId, roDtoId2}, trueBl, falseBl);
        assertEquals(2, corrNodeDtos.size());

        for (CorrelationNodeDTO corr : corrNodeDtos) {
                assertTrue(((ResearchOrganizationDTO) corr.getCorrelation())
                        .getName().getPart().get(0).getValue().contains("Research Org"));
            assertEquals(newOrgId.getExtension(), corr.getPlayer().getIdentifier().getExtension());
            assertNull(corr.getScoper());
        }

        Cd cd = new Cd();
        cd.setCode(RoleList.RESEARCH_ORGANIZATION.toString());

        corrNodeDtos = busService.getCorrelationsByPlayerIdsWithEntities(cd, new Ii[]{newOrgId}, trueBl, falseBl);
        assertEquals(2, corrNodeDtos.size());
        for (CorrelationNodeDTO corr : corrNodeDtos) {
            assertTrue(corr.getCorrelation() instanceof ResearchOrganizationDTO);
            assertTrue(((ResearchOrganizationDTO) corr.getCorrelation())
                    .getName().getPart().get(0).getValue().contains("Research Org"));
            assertEquals(newOrgId.getExtension(), corr.getPlayer().getIdentifier().getExtension());
            assertNull(corr.getScoper());
        }
    }

    public static void helpTestPersonRoleCorrelationsGetById(
            ClinicalResearchStaffCorrelationServiceRemote crsService,
            BusinessServiceRemote busService,
            OrganizationEntityServiceRemote orgService,
            PersonEntityServiceRemote personService) throws Exception {

        Ii newOrgId = createOrganization(orgService);

        Ii newPersonId = createPerson(personService);

        ClinicalResearchStaffDTO crsdto = new ClinicalResearchStaffDTO();
        crsdto.setScoperIdentifier(newOrgId);
        crsdto.setPlayerIdentifier(newPersonId);
        crsdto.setTelecomAddress(new DSet<Tel>());
        crsdto.getTelecomAddress().setItem(new HashSet<Tel>());

        TelPhone ph1 = new TelPhone();
        ph1.setValue(new URI(TelPhone.SCHEME_TEL + ":123-123-654"));
        crsdto.getTelecomAddress().getItem().add(ph1);

        Ii crsDtoId = crsService.createCorrelation(crsdto);

        CorrelationNodeDTO corrNodeDto = busService.getCorrelationByIdWithEntities(crsDtoId, trueBl, trueBl);
        assertTrue(corrNodeDto.getCorrelation() instanceof ClinicalResearchStaffDTO);
        assertEquals(crsDtoId.getExtension(),
                ((ClinicalResearchStaffDTO) corrNodeDto.getCorrelation())
                .getIdentifier().getItem().iterator().next().getExtension());
        assertTrue(corrNodeDto.getPlayer() instanceof PersonDTO);

        assertEquals(PERSON_LAST_NAME, ((PersonDTO) corrNodeDto.getPlayer()).getName().getPart().get(0).getValue());
        assertTrue(corrNodeDto.getScoper() instanceof OrganizationDTO);
        assertEquals(ORG_NAME, ((OrganizationDTO) corrNodeDto.getScoper())
                .getName().getPart().get(0).getValue());

        corrNodeDto = busService.getCorrelationByIdWithEntities(crsDtoId, falseBl, falseBl);
        assertNull(corrNodeDto.getPlayer());
        assertNull(corrNodeDto.getScoper());

        corrNodeDto = busService.getCorrelationByIdWithEntities(crsDtoId, trueBl, falseBl);
        assertNotNull(corrNodeDto.getPlayer());
        assertNull(corrNodeDto.getScoper());

        corrNodeDto = busService.getCorrelationByIdWithEntities(crsDtoId, falseBl, trueBl);
        assertNull(corrNodeDto.getPlayer());
        assertNotNull(corrNodeDto.getScoper());

        Ii newPersonId2 = createPerson(personService);

        ClinicalResearchStaffDTO crsdto2 = new ClinicalResearchStaffDTO();
        crsdto2.setScoperIdentifier(newOrgId);
        crsdto2.setPlayerIdentifier(newPersonId2);
        crsdto2.setTelecomAddress(new DSet<Tel>());
        crsdto2.getTelecomAddress().setItem(new HashSet<Tel>());


        crsdto2.getTelecomAddress().getItem().add(ph1);

        Ii crsDtoId2 = crsService.createCorrelation(crsdto2);

        List<CorrelationNodeDTO> corrNodeDtos = busService.getCorrelationsByIdsWithEntities(
                new Ii[]{crsDtoId, crsDtoId2}, trueBl, trueBl);
        assertEquals(2, corrNodeDtos.size());

        for (CorrelationNodeDTO corr : corrNodeDtos) {
            assertEquals(new URI(TelPhone.SCHEME_TEL + ":123-123-654"),
                    ((ClinicalResearchStaffDTO) corr.getCorrelation())
                    .getTelecomAddress().getItem().iterator().next().getValue());
            assertEquals(PERSON_LAST_NAME, ((PersonDTO) corr.getPlayer()).getName().getPart().get(0).getValue());
            assertEquals(newOrgId.getExtension(), corr.getScoper().getIdentifier().getExtension());
        }

        Cd cd = new Cd();
        cd.setCode(RoleList.CLINICAL_RESEARCH_STAFF.toString());

        corrNodeDtos = busService.getCorrelationsByPlayerIdsWithEntities(cd, new Ii[]{newPersonId, newPersonId2}, trueBl, trueBl);
        assertEquals(2, corrNodeDtos.size());
        for (CorrelationNodeDTO corr : corrNodeDtos) {
            assertTrue(corr.getCorrelation() instanceof ClinicalResearchStaffDTO);
            assertEquals(new URI(TelPhone.SCHEME_TEL + ":123-123-654"),
                    ((ClinicalResearchStaffDTO) corr.getCorrelation())
                    .getTelecomAddress().getItem().iterator().next().getValue());
            assertEquals(PERSON_LAST_NAME, ((PersonDTO) corr.getPlayer()).getName().getPart().get(0).getValue());
            assertEquals(newOrgId.getExtension(), corr.getScoper().getIdentifier().getExtension());
        }
    }

    public static Ii createOrganization(OrganizationEntityServiceRemote orgService)
    throws URISyntaxException, EntityValidationException, CurationException {
        return createOrganization(orgService, STREET_LINE, ORG_NAME);
    }

    public static Ii createOrganization(OrganizationEntityServiceRemote orgService,
            String streetLine, String orgName)
    throws URISyntaxException, EntityValidationException, CurationException {
        OrganizationDTO orgDto = new OrganizationDTO();
        orgDto.setName(TestConvertHelper.convertToEnOn(orgName));

        orgDto.setPostalAddress(TestConvertHelper.createAd(streetLine, null, "mycity", "WY", "12345", COUNTRY));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        orgDto.setTelecomAddress(telco);

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:default@example.com"));
        orgDto.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI("http://default.example.com"));
        orgDto.getTelecomAddress().getItem().add(url);

        return orgService.createOrganization(orgDto);
    }

    public static Ii createPerson(PersonEntityServiceRemote personService)
    throws URISyntaxException, EntityValidationException, CurationException {
        return createPerson(personService, PERSON_LAST_NAME);
    }


    public static Ii createPerson(PersonEntityServiceRemote personService, String lastName)
    throws URISyntaxException, EntityValidationException, CurationException {
        PersonDTO p = new PersonDTO();
         p.setName(TestConvertHelper.convertToEnPn("FirstName", "M", lastName, null, null));
         p.setPostalAddress(TestConvertHelper.createAd(STREET_LINE, "delivery", "city", "MD", "20850", COUNTRY));

         DSet<Tel> telco = new DSet<Tel>();
         telco.setItem(new HashSet<Tel>());
         p.setTelecomAddress(telco);

         TelEmail email = new TelEmail();
         email.setValue(new URI("mailto:default@example.com"));
         p.getTelecomAddress().getItem().add(email);

         TelUrl url = new TelUrl();
         url.setValue(new URI("http://default.example.com"));
         p.getTelecomAddress().getItem().add(url);

         return personService.createPerson(p);
     }

     public static void testSearchWithEntities(
             PersonEntityServiceRemote personService,
             OrganizationEntityServiceRemote orgService,
             BusinessServiceRemote busService,
             ClinicalResearchStaffCorrelationServiceRemote crsService,
             HealthCareProviderCorrelationServiceRemote hcpService,
             IdentifiedPersonCorrelationServiceRemote idpService,
             IdentifiedOrganizationCorrelationServiceRemote idoService,
             HealthCareFacilityCorrelationServiceRemote hcfService,
             ResearchOrganizationCorrelationServiceRemote researchOrgService,
             OrganizationalContactCorrelationServiceRemote ocService,
             OversightCommitteeCorrelationServiceRemote oversightComService
     ) throws Exception {

         Ii newPersonId = createPerson(personService);
         Ii newOrgId = createOrganization(orgService);

         TelPhone ph1 = new TelPhone();
         ph1.setValue(new URI(TelPhone.SCHEME_TEL + ":123-688-654"));
         DSet<Tel> telco = new DSet<Tel>();
         telco.setItem(new HashSet<Tel>());
         telco.getItem().add(ph1);


         Bl bl_true = new Bl();
         bl_true.setValue(true);

         Bl bl_false = new Bl();
         bl_false.setValue(false);

         // Test various null cases
         try {
             busService.searchCorrelationsWithEntities(null, null, null, null);
             fail();
         } catch (IllegalArgumentException e) {
         }
         try {
             CorrelationNodeDTO cnDto = new CorrelationNodeDTO();
             busService.searchCorrelationsWithEntities(cnDto, null, null, null);
             fail();
         } catch (IllegalArgumentException e) {
         }

         // ClinicalResearchStaff
         testSearchCRS(busService, crsService, orgService, newPersonId, newOrgId, telco, bl_true);

         // Health Care Provider
         testSearchHealthCareProvider(busService, hcpService, newPersonId, newOrgId, telco, bl_true);

         // Identified Person
         testSearchIdentifiedPerson(busService, idpService, newPersonId, newOrgId, bl_true);

         // Organizational Contact
         testSearchOrgContact(busService, ocService, newPersonId, newOrgId, telco, bl_true);

         // Identified Organization
         testSearchIdentifiedOrganization(busService, idoService, newOrgId, bl_true);

         // Health Care Facility
         testSearchHealthCareFacility(busService, hcfService, newOrgId, telco, bl_true);

         // Oversight Committee
         testSearchOversightCommittee(busService, oversightComService, orgService, newOrgId, bl_true);

         // Research Org.
         testSearchResearchOrg(researchOrgService, busService, newOrgId, telco, bl_true);
    }

    private static void testSearchHealthCareProvider(BusinessServiceRemote busService,
            HealthCareProviderCorrelationServiceRemote hcpService, Ii newPersonId, Ii newOrgId, DSet<Tel> telco,
            Bl bl_true) throws EntityValidationException, CurationException, TooManyResultsException {

        HealthCareProviderDTO hcpDto = new HealthCareProviderDTO();

        hcpDto.setTelecomAddress(telco);

        hcpDto.setPlayerIdentifier(newPersonId);
        hcpDto.setScoperIdentifier(newOrgId);

        hcpService.createCorrelation(hcpDto);

        HealthCareProviderDTO query_hcpDto = new HealthCareProviderDTO();
        query_hcpDto.setTelecomAddress(telco);

        CorrelationNodeDTO query_cnDto = new CorrelationNodeDTO();
        query_cnDto.setCorrelation(query_hcpDto);
        List<CorrelationNodeDTO> results = busService.searchCorrelationsWithEntities(query_cnDto, bl_true, bl_true, null);
        assertEquals(1, results.size());
    }

    private static void testSearchOrgContact(BusinessServiceRemote busService,
            OrganizationalContactCorrelationServiceRemote ocService, Ii newPersonId, Ii newOrgId, DSet<Tel> telco,
            Bl bl) throws EntityValidationException, CurationException, TooManyResultsException {

        OrganizationalContactDTO ocDto = new OrganizationalContactDTO();
        ocDto.setTelecomAddress(telco);
        Cd typeCode = new Cd();
        typeCode.setCode("IRB");

        ocDto.setPlayerIdentifier(newPersonId);
        ocDto.setScoperIdentifier(newOrgId);
        ocDto.setTypeCode(typeCode);
        ocService.createCorrelation(ocDto);

        OrganizationalContactDTO query_ocDto = new OrganizationalContactDTO();
        query_ocDto.setTelecomAddress(telco);

        CorrelationNodeDTO query_cnDto = new CorrelationNodeDTO();
        query_cnDto.setCorrelation(query_ocDto);
        List<CorrelationNodeDTO> results = busService.searchCorrelationsWithEntities(query_cnDto, bl, bl, null);
        assertEquals(1, results.size());
    }

    private static void testSearchHealthCareFacility(BusinessServiceRemote busService,
            HealthCareFacilityCorrelationServiceRemote hcfService, Ii newOrgId, DSet<Tel> telco, Bl bl_true)
            throws EntityValidationException, CurationException, TooManyResultsException {

        HealthCareFacilityDTO hcfDto = new HealthCareFacilityDTO();
        hcfDto.setTelecomAddress(telco);
        hcfDto.setPlayerIdentifier(newOrgId);
        hcfService.createCorrelation(hcfDto);

        HealthCareFacilityDTO query_hcfDto = new HealthCareFacilityDTO();
        query_hcfDto.setTelecomAddress(telco);

        CorrelationNodeDTO query_cnDto = new CorrelationNodeDTO();
        query_cnDto.setCorrelation(query_hcfDto);

        List<CorrelationNodeDTO> results = busService.searchCorrelationsWithEntities(query_cnDto, bl_true, bl_true, null);
        assertEquals(1, results.size());
    }

    private static void testSearchCRS(BusinessServiceRemote busService,
            ClinicalResearchStaffCorrelationServiceRemote crsService,
            OrganizationEntityServiceRemote orgService,
            Ii newPersonId, Ii newOrgId, DSet<Tel> telco,
            Bl bl) throws EntityValidationException, CurationException, TooManyResultsException, URISyntaxException {



        ClinicalResearchStaffDTO crsDto = new ClinicalResearchStaffDTO();
        crsDto.setTelecomAddress(telco);
        crsDto.setPlayerIdentifier(newPersonId);
        crsDto.setScoperIdentifier(newOrgId);
        crsService.createCorrelation(crsDto);

        // create extra crs that should not be returned
        crsDto = new ClinicalResearchStaffDTO();
        crsDto.setTelecomAddress(telco);
        crsDto.setPlayerIdentifier(newPersonId);
        Ii org2Id = createOrganization(orgService, "street", "wrong name");
        crsDto.setScoperIdentifier(org2Id);
        crsService.createCorrelation(crsDto);


        ClinicalResearchStaffDTO query_crsDto = new ClinicalResearchStaffDTO();
        query_crsDto.setTelecomAddress(telco);
        PersonDTO query_personDto = new PersonDTO();
        query_personDto.setIdentifier(newPersonId);

        OrganizationDTO query_orgDto = new OrganizationDTO();
        query_orgDto.setIdentifier(newOrgId);

        CorrelationNodeDTO query_cnDto = new CorrelationNodeDTO();
        query_cnDto.setCorrelation(query_crsDto);
        query_cnDto.setPlayer(query_personDto);
        query_cnDto.setScoper(query_orgDto);

        List<CorrelationNodeDTO> results = busService.searchCorrelationsWithEntities(query_cnDto, bl, bl, null);
        assertEquals(1, results.size());

        CorrelationNodeDTO result = results.get(0);
        assertTrue(result.getCorrelation() instanceof ClinicalResearchStaffDTO);
        assertTrue(result.getPlayer() instanceof PersonDTO);
        if (bl.getValue()) {
            PersonDTO p = (PersonDTO) result.getPlayer();
            assertEquals(PERSON_LAST_NAME, p.getName().getPart().get(0).getValue());
            assertTrue(result.getScoper() instanceof OrganizationDTO);
            OrganizationDTO org = (OrganizationDTO) result.getScoper();
            assertEquals(ORG_NAME, org.getName().getPart().get(0).getValue());
        }
    }

    private static void testSearchIdentifiedPerson(BusinessServiceRemote busService,
            IdentifiedPersonCorrelationServiceRemote idpService, Ii newPersonId, Ii newOrgId, Bl bl)
            throws EntityValidationException, CurationException, TooManyResultsException {

        IdentifiedPersonDTO idpDto = new IdentifiedPersonDTO();
        populateIdentifiedPerson(newPersonId, newOrgId, idpDto);
        idpDto.setAssignedId(newPersonId);
        idpService.createCorrelation(idpDto);

        CorrelationNodeDTO query_cnDto = new CorrelationNodeDTO();
        IdentifiedPersonDTO query_idpDto = new IdentifiedPersonDTO();
        query_idpDto.setAssignedId(newPersonId);
        query_cnDto.setCorrelation(query_idpDto);

        List<CorrelationNodeDTO> results = busService.searchCorrelationsWithEntities(query_cnDto, bl, bl, null);
        assertEquals(1, results.size());
    }

    private static void testSearchIdentifiedOrganization(BusinessServiceRemote busService,
            IdentifiedOrganizationCorrelationServiceRemote idoService, Ii newOrgId, Bl bl)
            throws EntityValidationException, CurationException, TooManyResultsException {

        CorrelationNodeDTO query_cnDto = new CorrelationNodeDTO();
        IdentifiedOrganizationDTO idoDto = new IdentifiedOrganizationDTO();

        Ii idoIi = populateIdentifiedOrganization(newOrgId, idoDto);
        idoService.createCorrelation(idoDto);

        IdentifiedOrganizationDTO query_idoDto = new IdentifiedOrganizationDTO();
        query_idoDto.setAssignedId(idoIi);

        OrganizationDTO query_orgDto = new OrganizationDTO();
        query_orgDto.setName(TestConvertHelper.convertToEnOn(ORG_NAME));
        query_cnDto.setPlayer(query_orgDto);

        query_cnDto.setCorrelation(query_idoDto);
        query_cnDto.setPlayer(query_orgDto);

        List<CorrelationNodeDTO> results = busService.searchCorrelationsWithEntities(query_cnDto, bl, bl, null);
        assertEquals(1, results.size());
    }

    private static void testSearchOversightCommittee(BusinessServiceRemote busService,
            OversightCommitteeCorrelationServiceRemote oversightComService,
            OrganizationEntityServiceRemote orgService, Ii newOrgId, Bl bl)
            throws EntityValidationException, CurationException, TooManyResultsException, URISyntaxException {

        OversightCommitteeDTO ovcDto = new OversightCommitteeDTO();
        ovcDto.setPlayerIdentifier(newOrgId);
        Cd typeCode = new Cd();
        typeCode.setCode("Ethics Committee");
        ovcDto.setTypeCode(typeCode);
        oversightComService.createCorrelation(ovcDto);

        // create one that won't return
        ovcDto = new OversightCommitteeDTO();
        ovcDto.setPlayerIdentifier(createOrganization(orgService, "street", "not the right name"));
        ovcDto.setTypeCode(typeCode);
        oversightComService.createCorrelation(ovcDto);

        OversightCommitteeDTO query_ovcDto = new OversightCommitteeDTO();
        CorrelationNodeDTO query_cnDto = new CorrelationNodeDTO();
        query_cnDto.setCorrelation(query_ovcDto);
        OrganizationDTO query_orgDto = new OrganizationDTO();
        query_orgDto.setIdentifier(newOrgId);
        query_cnDto.setPlayer(query_orgDto);

        List<CorrelationNodeDTO> results = busService.searchCorrelationsWithEntities(query_cnDto, bl, bl, null);

        assertEquals(1, results.size());
    }

     private static void testSearchResearchOrg(ResearchOrganizationCorrelationServiceRemote researchOrgService,
             BusinessServiceRemote busService, Ii newOrgId, DSet<Tel> telco, Bl bl) throws EntityValidationException,
             CurationException, TooManyResultsException {

         // Research Organization
         ResearchOrganizationDTO roDto = new ResearchOrganizationDTO();
         roDto.setPlayerIdentifier(newOrgId);
         roDto.setName(TestConvertHelper.convertToEnOn("RO_NAME"));
         researchOrgService.createCorrelation(roDto);

         // create one that won't return
         roDto = new ResearchOrganizationDTO();
         roDto.setPlayerIdentifier(newOrgId);
         roDto.setName(TestConvertHelper.convertToEnOn("Not the right NAME"));
         researchOrgService.createCorrelation(roDto);

         // query by Player Address
         ResearchOrganizationDTO query_roDto = new ResearchOrganizationDTO();
         query_roDto.setName(TestConvertHelper.convertToEnOn("RO_NAME"));
         CorrelationNodeDTO query_cnDto = new CorrelationNodeDTO();
         query_cnDto.setCorrelation(query_roDto);

         List<CorrelationNodeDTO> results = busService.searchCorrelationsWithEntities(query_cnDto, bl, bl, null);
         assertEquals(1, results.size());
     }

     private static Ii populateIdentifiedPerson(Ii newPersonId, Ii newOrgId, IdentifiedPersonDTO idpDto) {
         idpDto.setPlayerIdentifier(newPersonId);
         idpDto.setScoperIdentifier(newOrgId);
         Cd status = new Cd();
         status.setCode("active");
         idpDto.setStatus(status);

         Ii idpIi = new Ii();
         idpIi.setExtension("" + UUID.randomUUID().getMostSignificantBits());
         idpIi.setDisplayable(true);
         idpIi.setScope(IdentifierScope.OBJ);
         idpIi.setReliability(IdentifierReliability.ISS);
         idpIi.setIdentifierName(IdConverter.IDENTIFIED_ORG_IDENTIFIER_NAME);
         idpIi.setRoot(IdConverter.IDENTIFIED_ORG_ROOT);
         idpDto.setAssignedId(idpIi);
         return idpIi;
     }

     private static Ii populateIdentifiedOrganization(Ii newOrgId, IdentifiedOrganizationDTO idoDto) {
         idoDto.setPlayerIdentifier(newOrgId);
         idoDto.setScoperIdentifier(newOrgId);
         Cd status = new Cd();
         status.setCode("active");
         idoDto.setStatus(status);

         Ii idoIi = new Ii();
         idoIi.setExtension("" + UUID.randomUUID().getMostSignificantBits());
         idoIi.setDisplayable(true);
         idoIi.setScope(IdentifierScope.OBJ);
         idoIi.setReliability(IdentifierReliability.ISS);
         idoIi.setIdentifierName(IdConverter.RESEARCH_ORG_IDENTIFIER_NAME);
         idoIi.setRoot(IdConverter.RESEARCH_ORG_ROOT);
         idoDto.setAssignedId(idoIi);
         return idoIi;
     }

}
