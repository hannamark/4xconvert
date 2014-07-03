/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The COPPA PO
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This COPPA PO Software License (the License) is between NCI and You. You (or
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
 * its rights in the COPPA PO Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the COPPA PO Software; (ii) distribute and
 * have distributed to and by third parties the COPPA PO Software and any
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
package gov.nih.nci.services.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.po.data.bo.AbstractOrganizationRole;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.FundingMechanism;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationType;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.data.bo.FundingMechanism.FundingMechanismStatus;
import gov.nih.nci.po.data.convert.EnConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.IiDsetConverter;
import gov.nih.nci.po.data.convert.StringConverter;
import gov.nih.nci.po.data.convert.TelDSetConverter;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @author gax
 *
 */
public class ResearchOrganizationDTOTest extends AbstractOrganizationRoleDTOTest {

    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractOrganizationRole getExampleTestClass() {
        ResearchOrganization ro = new ResearchOrganization();
        fillInExampleOrgRoleFields(ro);
        ResearchOrganizationType rot = new ResearchOrganizationType("CCR", "Cancer Center");
        ro.setTypeCode(rot);
        FundingMechanism fm = new FundingMechanism("B09", "Mental Health Services Block Grant", "Block Grants",
                                                   FundingMechanismStatus.ACTIVE);
        ro.setFundingMechanism(fm);
        ro.setName("my name");
        fillInExampleOrgRoleFields(ro);
        ro.setEmail(new ArrayList<Email>());
        ro.getEmail().add(new Email("me@example.com"));
        ro.setPhone(new ArrayList<PhoneNumber>());
        ro.getPhone().add(new PhoneNumber("123-456-7890"));
        ro.setFax(new ArrayList<PhoneNumber>());
        ro.getFax().add(new PhoneNumber("098-765-4321"));
        ro.setTty(new ArrayList<PhoneNumber>());
        ro.getTty().add(new PhoneNumber("111-222-3333"));
        ro.setUrl(new ArrayList<URL>());
        ro.getUrl().add(new URL("http://www.example.com"));
        Address a = new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
                                getDefaultCountry());
        ro.setPostalAddresses(Collections.singleton(a));
        return ro;
    }

    /**
     * {@inheritDoc}
     * @throws URISyntaxException
     */
    @SuppressWarnings("unchecked")
    @Override
    protected AbstractOrganizationRoleDTO getExampleTestClassDTO(Long playerId) throws URISyntaxException {
        ResearchOrganizationDTO dto = new ResearchOrganizationDTO();
        fillInOrgRoleDTOFields(dto, playerId);
        Ii ii = new Ii();
        ii.setExtension("" + 1L);
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setRoot(IdConverter.RESEARCH_ORG_ROOT);
        ii.setIdentifierName(IdConverter.RESEARCH_ORG_IDENTIFIER_NAME);
        dto.setIdentifier(IiConverter.convertToDsetIi(ii));

        Cd type = new Cd();
        type.setCode("CCR");
        dto.setTypeCode(type);

        Cd fm = new Cd();
        fm.setCode("B09");
        dto.setFundingMechanism(fm);


        dto.setName(StringConverter.convertToEnOn("my name"));

        DSet<Tel> tels = new DSet<Tel>();
        tels.setItem(new HashSet<Tel>());
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:me@example.com"));
        tels.getItem().add(email);

        TelPhone phone = new TelPhone();
        phone.setValue(new URI("tel:111-222-3333"));
        tels.getItem().add(phone);

        phone = new TelPhone();
        phone.setValue(new URI("x-text-fax:222-222-3333"));
        tels.getItem().add(phone);

        phone = new TelPhone();
        phone.setValue(new URI("x-text-tel:333-222-3333"));
        tels.getItem().add(phone);

        TelUrl url = new TelUrl();
        url.setValue(new URI("http://www.example.com"));
        tels.getItem().add(url);

        dto.setTelecomAddress(tels);

        Ad ad = AddressConverterUtil.create("streetAddressLine", "deliveryAddressLine", "cityOrMunicipality",
                                            "stateOrProvince", "postalCode", getDefaultCountry().getAlpha3(),
                                            getDefaultCountry().getName());
        dto.setPostalAddress(new DSet<Ad>());
        dto.getPostalAddress().setItem(Collections.singleton(ad));
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verifyTestClassDTOFields(AbstractOrganizationRole ro) {
        ResearchOrganization bo = (ResearchOrganization) ro;
        assertEquals("CCR", bo.getTypeCode().getCode());
        assertEquals("B09", bo.getFundingMechanism().getCode());

        assertEquals("my name", bo.getName());
        assertEquals("me@example.com", bo.getEmail().get(0).getValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verifyTestClassFields(AbstractOrganizationRoleDTO dto) {
        ResearchOrganizationDTO ro = (ResearchOrganizationDTO) dto;
        assertEquals("CCR", ro.getTypeCode().getCode());
        assertEquals("B09", ro.getFundingMechanism().getCode());

        // check id
        Ii expectedIi = new Ii();
        expectedIi.setExtension("" + 1);
        expectedIi.setDisplayable(true);
        expectedIi.setScope(IdentifierScope.OBJ);
        expectedIi.setReliability(IdentifierReliability.ISS);
        expectedIi.setIdentifierName(IdConverter.RESEARCH_ORG_IDENTIFIER_NAME);
        expectedIi.setRoot(IdConverter.RESEARCH_ORG_ROOT);
        Ii actualIi = IiDsetConverter.convertToIi(((ResearchOrganizationDTO) dto).getIdentifier());
        assertTrue(EqualsBuilder.reflectionEquals(expectedIi, actualIi));

        assertEquals("my name", new EnConverter().convertToString(ro.getName()));
        HealthCareFacility tmp = new HealthCareFacility();
        TelDSetConverter.convertToContactList(ro.getTelecomAddress(), tmp);
        assertEquals("me@example.com", tmp.getEmail().get(0).getValue());
    }
}
