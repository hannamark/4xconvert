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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.Patient;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.service.CurateEntityValidationException;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.HibernateHelper;

/**
 *
 * @author ludetc
 *
 */
public class MergeOrganizationHelperTest {

    private final MergeOrganizationHelperImpl testee = new MergeOrganizationHelperImpl();
    private final UniquePlayerScoperIdentifierValidator upsiValidator = mock(UniquePlayerScoperIdentifierValidator.class);
    private final UniquePlayerScoperValidator upsValidator = mock(UniquePlayerScoperValidator.class);
    private final UniqueOversightCommitteeValidator uocValidator = mock(UniqueOversightCommitteeValidator.class);
    private final UniqueOrganizationalContactTitleScoperTypeValidator uoctsValidator
        = mock(UniqueOrganizationalContactTitleScoperTypeValidator.class);
    private final Session session = mock(Session.class);


    @Before
    public void setup() {
        testee.setUniquePlayerScoperIdentifierValidator(upsiValidator);
        testee.setUniquePlayerScoperValidator(upsValidator);
        testee.setUniqueOversightCommitteeValidator(uocValidator);
        testee.setUniqueOrganizationalContactScoperTypeValidator(uoctsValidator);


        HibernateHelper hibernateHelper = mock(HibernateHelper.class);
        when(hibernateHelper.getCurrentSession()).thenReturn(session);
        when(session.merge(any(HealthCareProvider.class))).thenReturn(new HealthCareProvider());
        when(session.merge(any(OrganizationalContact.class))).thenReturn(new OrganizationalContact());
        when(session.merge(any(ClinicalResearchStaff.class))).thenReturn(new ClinicalResearchStaff());
        testee.setHibernateHelper(hibernateHelper);

    }

    @Test
    public void testHandleConflictingScopedRoleCorrelation() {
        try {
            testee.handleConflictingScopedRoleCorrelation(null, new Patient());
            fail("we should see a runtime exception here.");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().startsWith("Conflict found for Patient "));
        }

        IdentifiedPerson ip = new IdentifiedPerson();
        doReturn(new IdentifiedPerson()).when(upsiValidator).getConflictingRole(any(IdentifiedPerson.class));
        testee.handleConflictingScopedRoleCorrelation(null, ip);
        assertNotNull(ip.getDuplicateOf());


        HealthCareProvider hcp = new HealthCareProvider();
        when(upsValidator.getConflictingRole(hcp)).thenReturn(new HealthCareProvider());
        testee.handleConflictingScopedRoleCorrelation(null, hcp);
        assertNotNull(hcp.getDuplicateOf());

        OrganizationalContact oc = new OrganizationalContact();
        when(uoctsValidator.getConflictingRole(oc)).thenReturn(new OrganizationalContact());
        testee.handleConflictingScopedRoleCorrelation(null, oc);
        assertNotNull(oc.getDuplicateOf());

        ClinicalResearchStaff crs = new ClinicalResearchStaff();
        when(upsValidator.getConflictingRole(crs)).thenReturn(new ClinicalResearchStaff());
        testee.handleConflictingScopedRoleCorrelation(null, crs);
        assertNotNull(crs.getDuplicateOf());

    }

    @Test
    public void testHandleConflictingPlayedRoleCorrelation() {
        try {
            testee.handleConflictingPlayedRoleCorrelation(null, null);
            fail("we should see a runtime exception here.");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid correlation: null", e.getMessage());
        }

        HealthCareFacility hcf = new HealthCareFacility();
        try {
            testee.handleConflictingPlayedRoleCorrelation(null, hcf);
            fail("we should see a runtime exception here.");
        } catch (CurateEntityValidationException e) {            
        }

        IdentifiedOrganization io = new IdentifiedOrganization();
        doReturn(new IdentifiedOrganization()).when(upsiValidator).getConflictingRole(any(IdentifiedOrganization.class));
        testee.handleConflictingPlayedRoleCorrelation(null, io);
        assertNotNull(io.getDuplicateOf());

        OversightCommittee returnOC = new OversightCommittee();
        OversightCommittee oc = new OversightCommittee();
        when(uocValidator.getConflictingRole(oc)).thenReturn(returnOC);

        testee.handleConflictingPlayedRoleCorrelation(null, oc);
        assertNotNull(oc.getDuplicateOf());

        Set<Address> addresses = new HashSet<Address>();
        Address ad = new Address();
        ad.setCityOrMunicipality("city");
        ad.setCountry(new Country("", "", "", ""));
        ad.setDeliveryAddressLine("delivery");
        ad.setPostalCode("20888");
        ad.setStateOrProvince("MD");
        ad.setStreetAddressLine("street");
        addresses.add(ad);

        ResearchOrganization ro = new ResearchOrganization();
        ro.setPostalAddresses(addresses);

        ro.setPostalAddresses(addresses);
        UniqueResearchOrganizationValidator uroVal = mock(UniqueResearchOrganizationValidator.class);
        testee.setUniqueResearchOrganizationValidator(uroVal);

        ResearchOrganization returnRO = new ResearchOrganization();
        when(uroVal.getConflictingRole(any(ResearchOrganization.class))).thenReturn(returnRO);
        when(session.merge(any(ResearchOrganization.class))).thenReturn(new ResearchOrganization());
        testee.handleConflictingPlayedRoleCorrelation(null, ro);
        assertNotNull(ro.getDuplicateOf());

    }

}
