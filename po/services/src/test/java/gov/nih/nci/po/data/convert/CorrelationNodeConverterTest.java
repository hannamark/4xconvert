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
package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.AddressPartType;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * Tests for the persistent object converter.
 * @author mshestopalov
 */
public class CorrelationNodeConverterTest  {

    private HealthCareProvider createHcpForTest() {
        Person player = new Person();
        player.setId(new Long(10));
        Address addr1 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("United States", "840", "US", "USA"));
        player.setPostalAddress(addr1);

        Organization scoper = new Organization();
        scoper.setId(new Long(11));

        HealthCareProvider hcp = new HealthCareProvider();
        hcp.setId(new Long(12));
        Email e1 = new Email();
        e1.setValue("email@email.com");
        hcp.getEmail().add(e1);
        hcp.setPlayer(player);
        hcp.setScoper(scoper);

        return hcp;
    }

    private void verifyHcp(CorrelationNodeDTO cNode) {
        assertEquals(new Long(12).toString(),
                cNode.getCorrelation().getIdentifier().getItem().iterator().next().getExtension());
        assertEquals(IdConverter.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME,
                cNode.getCorrelation().getIdentifier().getItem().iterator().next().getIdentifierName());
        assertEquals(IdConverter.HEALTH_CARE_PROVIDER_ROOT,
                cNode.getCorrelation().getIdentifier().getItem().iterator().next().getRoot());
        assertEquals("mailto:email@email.com", ((HealthCareProviderDTO)
                cNode.getCorrelation()).getTelecomAddress().getItem().iterator().next().getValue().toString());
    }

    private void verifyPerson(CorrelationNodeDTO cNode) {
        assertEquals(new Long(10).toString(), cNode.getPlayer().getIdentifier().getExtension());
        assertEquals(IdConverter.PERSON_IDENTIFIER_NAME, cNode.getPlayer().getIdentifier().getIdentifierName());
        assertEquals(IdConverter.PERSON_ROOT, cNode.getPlayer().getIdentifier().getRoot());
        assertEquals(IdConverter.PERSON_ROOT, cNode.getPlayer().getIdentifier().getRoot());
        List<Adxp> addrParts = ((PersonDTO) cNode.getPlayer()).getPostalAddress().getPart();
        for (Adxp part : addrParts) {
            if (AddressPartType.SAL.equals(part.getType()) || AddressPartType.DAL.equals(part.getType())) {
                assertEquals("defaultStreetAddress", part.getValue());
                break;
            }
        }
    }

    private void verifyOrg(CorrelationNodeDTO cNode) {
        assertEquals(new Long(11).toString(), cNode.getScoper().getIdentifier().getExtension());
        assertEquals(IdConverter.ORG_IDENTIFIER_NAME, cNode.getScoper().getIdentifier().getIdentifierName());
        assertEquals(IdConverter.ORG_ROOT, cNode.getScoper().getIdentifier().getRoot());
    }


    @Test
    public void testConvertCorrelationNodeDTOArray() {
        List<Correlation> corrs = new ArrayList<Correlation>();
        corrs.add(this.createHcpForTest());
        corrs.add(this.createHcpForTest());

        List<CorrelationNodeDTO> cNodes = CorrelationNodeDTOConverter.convertToCorrelationNodeDTOList(corrs, true, true);

        for (CorrelationNodeDTO cNode : cNodes) {
            verifyPerson(cNode);
            verifyOrg(cNode);
            verifyHcp(cNode);
        }

        assertNull(CorrelationNodeDTOConverter.convertToCorrelationNodeDTOList(null, true, true));

    }

    /**
     * Test converting a person role.
     */
    @Test
    public void testConvertPersonRole() {
        HealthCareProvider hcp = this.createHcpForTest();

        CorrelationNodeDTO cNode = CorrelationNodeDTOConverter.convertToCorrelationNodeDTO(hcp, true, true);

        verifyPerson(cNode);
        verifyOrg(cNode);
        verifyHcp(cNode);
    }

    @Test
    public void testConvertPersonRoleFalsePlayer() {
        HealthCareProvider hcp = this.createHcpForTest();

        CorrelationNodeDTO cNode = CorrelationNodeDTOConverter.convertToCorrelationNodeDTO(hcp, false, true);

        assertNull(cNode.getPlayer());
        verifyOrg(cNode);
        verifyHcp(cNode);
    }

    @Test
    public void testConvertPersonRoleFalseScoper() {
        HealthCareProvider hcp = this.createHcpForTest();

        CorrelationNodeDTO cNode = CorrelationNodeDTOConverter.convertToCorrelationNodeDTO(hcp, true, false);

        verifyPerson(cNode);
        assertNull(cNode.getScoper());
        verifyHcp(cNode);
    }

    @Test
    public void testConvertPersonRoleFalsePlayerAndScoper() {

        HealthCareProvider hcp = this.createHcpForTest();

        CorrelationNodeDTO cNode = CorrelationNodeDTOConverter.convertToCorrelationNodeDTO(hcp, false, false);

        assertNull(cNode.getPlayer());
        assertNull(cNode.getScoper());

        verifyHcp(cNode);
    }

    /**
     * Test converting a org role.
     */
    @Test
    public void testConvertOrgRole() {

        Organization player = new Organization();
        player.setId(new Long(10));

        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setId(new Long(11));
        hcf.setPlayer(player);
        hcf.setName("name");

        CorrelationNodeDTO cNode = CorrelationNodeDTOConverter.convertToCorrelationNodeDTO(hcf, true, true);

        assertEquals(new Long(10).toString(), cNode.getPlayer().getIdentifier().getExtension());
        assertEquals(IdConverter.ORG_IDENTIFIER_NAME, cNode.getPlayer().getIdentifier().getIdentifierName());
        assertEquals(IdConverter.ORG_ROOT, cNode.getPlayer().getIdentifier().getRoot());

        assertEquals(new Long(11).toString(),
                cNode.getCorrelation().getIdentifier().getItem().iterator().next().getExtension());
        assertEquals(IdConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME,
                cNode.getCorrelation().getIdentifier().getItem().iterator().next().getIdentifierName());
        assertEquals(IdConverter.HEALTH_CARE_FACILITY_ROOT,
                cNode.getCorrelation().getIdentifier().getItem().iterator().next().getRoot());
        assertEquals("name",
                ((HealthCareFacilityDTO) cNode.getCorrelation()).getName().getPart().get(0).getValue());
    }

    /**
     * Test converting a org role.
     */
    @Test
    public void testConvertOrgRoleDTO() {

        Ii orgIi = new Ii();
        orgIi.setExtension("10");
        orgIi.setRoot(IdConverter.ORG_ROOT);
        orgIi.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        orgIi.setReliability(IdentifierReliability.ISS);

        Ii hcfIi = new Ii();
        hcfIi.setExtension("11");
        hcfIi.setRoot(IdConverter.HEALTH_CARE_FACILITY_ROOT);
        hcfIi.setReliability(IdentifierReliability.ISS);
        hcfIi.setIdentifierName(IdConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME);

        OrganizationDTO orgDto = new OrganizationDTO();
        orgDto.setIdentifier(orgIi);
        orgDto.setName(StringConverter.convertToEnOn("org name"));

        HealthCareFacilityDTO hcfDto = new HealthCareFacilityDTO();
        DSet<Ii> dset = new DSet<Ii>();
        dset.setItem(new HashSet<Ii>());
        dset.getItem().add(hcfIi);
        hcfDto.setIdentifier(dset);
        hcfDto.setPlayerIdentifier(orgIi);
        hcfDto.setName(StringConverter.convertToEnOn("hcf name"));

        CorrelationNodeDTO cNode = new CorrelationNodeDTO();
        cNode.setCorrelation(hcfDto);
        cNode.setPlayer(orgDto);

        PersistentObject myRole =  CorrelationNodeDTOConverter.convertFromCorrelationNodeDTO(cNode);
        assertTrue(myRole instanceof HealthCareFacility);
        HealthCareFacility hcf = (HealthCareFacility) myRole;

        assertEquals("hcf name", "hcf name");
        assertEquals(new Long(11), hcf.getId());

        assertEquals(new Long(10), hcf.getPlayer().getId());
        assertEquals("org name", hcf.getPlayer().getName());
    }


}
