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
package gov.nih.nci.services.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedOrganizationCR;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.List;

import com.fiveamsolutions.nci.commons.search.OneCriterionRequiredException;

/**
 * @author Scott Miller
 *
 */
public class IdentifiedOrganizationRemoteServiceTest
    extends AbstractStructrualRoleRemoteServiceTest<IdentifiedOrganizationDTO, IdentifiedOrganizationCR> {

    private int ext = 0;
    /**
     * {@inheritDoc}
     */
    @Override
    IdentifiedOrganizationCorrelationServiceRemote getCorrelationService() {
        return EjbTestHelper.getIdentifiedOrganizationServiceBeanAsRemote();
    }


    @Override
    protected void verifyCreatedBy(long id) {
        IdentifiedOrganization oversightCommittee =
                (IdentifiedOrganization) PoHibernateUtil.getCurrentSession().get(IdentifiedOrganization.class, id);

        assertEquals(getUser(), oversightCommittee.getCreatedBy());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected IdentifiedOrganizationDTO getSampleDto() throws Exception {
        IdentifiedOrganizationDTO dto = new IdentifiedOrganizationDTO();

        Ii ii = new Ii();
        ii.setExtension("" + basicOrganization.getId());
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.ORG_ROOT);
        dto.setPlayerIdentifier(ii);

        ii = new Ii();
        ii.setExtension("" + basicOrganization.getId());
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.ORG_ROOT);
        dto.setScoperIdentifier(ii);

        Cd status = new Cd();
        status.setCode("pending");
        dto.setStatus(status);

        ii = new Ii();
        ii.setExtension("myExtension" + (ext++));
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.IDENTIFIED_ORG_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.IDENTIFIED_ORG_ROOT);
        dto.setAssignedId(ii);

        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void verifyDto(IdentifiedOrganizationDTO expected, IdentifiedOrganizationDTO actual) {
        assertEquals(expected.getPlayerIdentifier().getExtension(), actual.getPlayerIdentifier().getExtension());
        assertEquals(expected.getScoperIdentifier().getExtension(), actual.getScoperIdentifier().getExtension());
        assertEquals("pending", actual.getStatus().getCode());

        // really probe the assignedId, since that's different than other StructuralRoles
        Ii ii1 = expected.getAssignedId();
        Ii ii2 = actual.getAssignedId();
        assertEquals(ii1.getExtension(), ii2.getExtension());
        assertEquals(ii1.getDisplayable(), ii2.getDisplayable());
        assertEquals(ii1.getScope(), ii2.getScope());
        assertEquals(ii1.getReliability(), ii2.getReliability());
        assertEquals(ii1.getIdentifierName(), ii2.getIdentifierName());
        assertEquals(ii2.getRoot(), ii2.getRoot());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verifyAlterations(IdentifiedOrganizationCR cr) {
        super.verifyAlterations(cr);
        assertEquals("9999", cr.getAssignedIdentifier().getExtension());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void alter(IdentifiedOrganizationDTO dto) throws Exception {
        dto.getAssignedId().setExtension("9999");
    }

    @Override
    public void testSearch() throws Exception {
        Organization org2 = new Organization();
        org2.setName("org2 name");
        org2.setPostalAddress(new Address("1600 Penn Ave", "Washington", "DC", "20202", getDefaultCountry()));
        org2.setStatusCode(EntityStatus.ACTIVE);
        org2.getEmail().add(new Email("foo@example.com"));
        org2.getUrl().add(new URL("http://example.com"));
        PoHibernateUtil.getCurrentSession().saveOrUpdate(org2);

        IdentifiedOrganizationDTO correlation1 = getSampleDto();
        Ii correlation1Id = getCorrelationService().createCorrelation(correlation1);

        IdentifiedOrganizationDTO correlation2 = getSampleDto();
        Ii ii = new Ii();
        ii.setExtension("" + org2.getId());
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.ORG_ROOT);
        correlation2.setPlayerIdentifier(ii);

        ii = new Ii();
        ii.setExtension("" + org2.getId());
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.ORG_ROOT);
        correlation2.setScoperIdentifier(ii);
        correlation2.getAssignedId().setExtension(correlation2.getAssignedId().getExtension() + "2");
        Ii correlation2Id = getCorrelationService().createCorrelation(correlation2);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        IdentifiedOrganizationDTO searchCriteria = null;

        try {
            getCorrelationService().search(searchCriteria);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        searchCriteria = new IdentifiedOrganizationDTO();
        try {
            getCorrelationService().search(searchCriteria);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        // test search by primary id
        Ii id = new Ii();
        id.setExtension(correlation1Id.getExtension());
        id.setRoot(correlation1Id.getRoot());
        id.setIdentifierName(correlation1Id.getIdentifierName());
        id.setDisplayable(correlation1Id.getDisplayable());
        id.setReliability(correlation1Id.getReliability());
        id.setScope(correlation1Id.getScope());

        searchCriteria.setIdentifier(IiConverter.convertToDsetIi(id));
        List<IdentifiedOrganizationDTO> results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), correlation1Id.getExtension());

        searchCriteria.getIdentifier().getItem().iterator().next().setExtension(correlation2Id.getExtension());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), correlation2Id.getExtension());

        searchCriteria.getIdentifier().getItem().iterator().next().setExtension("999");
        results = getCorrelationService().search(searchCriteria);
        assertEquals(0, results.size());

        // test search by player id
        searchCriteria.setIdentifier(null);
        searchCriteria.setPlayerIdentifier(correlation1.getPlayerIdentifier());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), correlation1Id.getExtension());

        // test search by scoper id
        searchCriteria.setPlayerIdentifier(null);
        searchCriteria.setScoperIdentifier(correlation2.getScoperIdentifier());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), correlation2Id.getExtension());

        // test by assigned id
        searchCriteria.setScoperIdentifier(null);
        searchCriteria.setAssignedId(correlation1.getAssignedId());
        searchCriteria.getAssignedId().setExtension(searchCriteria.getAssignedId().getExtension().toUpperCase());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());

        searchCriteria.setAssignedId(correlation2.getAssignedId());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals("2", results.get(0).getIdentifier().getItem().iterator().next().getExtension());

        // test by assigned id and scoper id
        searchCriteria.setAssignedId(correlation1.getAssignedId());
        searchCriteria.setScoperIdentifier(correlation1.getScoperIdentifier());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals("1", results.get(0).getIdentifier().getItem().iterator().next().getExtension());

        // test by invalid assigned id
        searchCriteria.getAssignedId().setExtension("invalid extension");
        results = getCorrelationService().search(searchCriteria);
        assertEquals(0, results.size());

        // test by type1
        searchCriteria.setAssignedId(null);
        searchCriteria.setScoperIdentifier(null);

        testNullifiedRoleNotFoundInSearch(correlation1Id, searchCriteria, IdentifiedOrganization.class);
    }

    @Override
    protected IdentifiedOrganizationDTO getEmptySearchCriteria() {
        return new IdentifiedOrganizationDTO();
    }
}
