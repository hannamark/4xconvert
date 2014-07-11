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
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.OversightCommitteeCR;
import gov.nih.nci.po.data.bo.OversightCommitteeType;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.StatusCodeConverter;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.CorrelationService;

import java.util.List;

import org.junit.Test;

import com.fiveamsolutions.nci.commons.search.OneCriterionRequiredException;

/**
 * Remote service test.
 */
public class OversightCommitteeRemoteServiceTest extends AbstractOrganizationalRoleRemoteServiceTest<OversightCommitteeDTO, OversightCommitteeCR> {

    @Override
    CorrelationService<OversightCommitteeDTO> getCorrelationService() {
        return EjbTestHelper.getOversightCommitteeCorrelationServiceRemote();
    }

    @Override
    protected void verifyCreatedBy(long id) {
        OversightCommittee oversightCommittee =
                (OversightCommittee) PoHibernateUtil.getCurrentSession().get(OversightCommittee.class, id);

        assertEquals(getUser(), oversightCommittee.getCreatedBy());
    }


    @Override
    protected OversightCommitteeDTO getSampleDto() throws Exception {
        OversightCommitteeDTO dto = new OversightCommitteeDTO();
        Ii ii = new Ii();
        ii.setExtension("" + basicOrganization.getId());
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.ORG_ROOT);
        dto.setPlayerIdentifier(ii);

        Cd type = new Cd();
        type.setCode("Ethics Committee");
        dto.setTypeCode(type);

        // re-gen a player org for next sample for uniqueness
        createAndSetOrganization();

        return dto;
    }

    @Override
    void verifyDto(OversightCommitteeDTO expected, OversightCommitteeDTO actual) {
        assertEquals(expected.getPlayerIdentifier().getExtension(), actual.getPlayerIdentifier().getExtension());
        assertEquals("pending", actual.getStatus().getCode());
        assertEquals(expected.getTypeCode().getCode(), actual.getTypeCode().getCode());
    }

    @Override
    protected void alter(OversightCommitteeDTO dto) {
        OversightCommitteeType other = new OversightCommitteeType("Foo");
        PoHibernateUtil.getCurrentSession().saveOrUpdate(other);
        PoHibernateUtil.getCurrentSession().flush();

        Cd type = new Cd();
        type.setCode("Foo");
        dto.setTypeCode(type);
    }

    @Override
    protected void verifyAlterations(OversightCommitteeCR cr) {
        super.verifyAlterations(cr);

        assertEquals("Foo", cr.getTypeCode().getCode());
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

        OversightCommitteeType other = new OversightCommitteeType("Foo");
        PoHibernateUtil.getCurrentSession().saveOrUpdate(other);
        PoHibernateUtil.getCurrentSession().flush();

        OversightCommitteeDTO correlation1 = getSampleDto();
        Ii id1 = getCorrelationService().createCorrelation(correlation1);

        OversightCommitteeDTO correlation2 = getSampleDto();
        Ii org2Ii = new Ii();
        org2Ii.setExtension("" + org2.getId());
        org2Ii.setDisplayable(true);
        org2Ii.setScope(IdentifierScope.OBJ);
        org2Ii.setReliability(IdentifierReliability.ISS);
        org2Ii.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        org2Ii.setRoot(IdConverter.ORG_ROOT);
        correlation2.setPlayerIdentifier(org2Ii);
        Cd type = new Cd();
        type.setCode("Foo");
        correlation2.setTypeCode(type);
        Ii id2 = getCorrelationService().createCorrelation(correlation2);

        // test search by null / empty criteria
        OversightCommitteeDTO searchCriteria = null;
        try {
            getCorrelationService().search(searchCriteria);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        searchCriteria = new OversightCommitteeDTO();
        try {
            getCorrelationService().search(searchCriteria);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        // test search by primary id
        Ii ii = new Ii();
        ii.setExtension(id1.getExtension());
        ii.setRoot(id1.getRoot());
        ii.setIdentifierName(id1.getIdentifierName());
        ii.setDisplayable(id1.getDisplayable());
        ii.setReliability(id1.getReliability());
        ii.setScope(id1.getScope());

        searchCriteria.setIdentifier(IiConverter.convertToDsetIi(ii));
        List<OversightCommitteeDTO> results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id1.getExtension());

        searchCriteria.getIdentifier().getItem().iterator().next().setExtension(id2.getExtension());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id2.getExtension());

        // search by status
        searchCriteria.setIdentifier(null);
        searchCriteria.setStatus(StatusCodeConverter.convertToCd(EntityStatus.PENDING));
        results = getCorrelationService().search(searchCriteria);
        assertEquals(2, results.size());
        searchCriteria.setStatus(StatusCodeConverter.convertToCd(EntityStatus.ACTIVE));
        results = getCorrelationService().search(searchCriteria);
        assertEquals(0, results.size());

        // search by player id
        searchCriteria.setStatus(null);
        searchCriteria.setPlayerIdentifier(correlation1.getPlayerIdentifier());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id1.getExtension());

        searchCriteria.setPlayerIdentifier(correlation2.getPlayerIdentifier());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id2.getExtension());

        searchCriteria.setPlayerIdentifier(correlation2.getPlayerIdentifier());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id2.getExtension());

        // search by type code
        searchCriteria.setPlayerIdentifier(null);
        searchCriteria.setTypeCode(correlation1.getTypeCode());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id1.getExtension());

        searchCriteria.setTypeCode(correlation2.getTypeCode());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id2.getExtension());

        testNullifiedRoleNotFoundInSearch(id2, searchCriteria, OversightCommittee.class);
    }

    @Test
    public void testNestedTypeCode() throws Exception {
        OversightCommitteeDTO oc = getSampleDto();
        Ii id1 = getCorrelationService().createCorrelation(oc);


        OversightCommitteeDTO searchCriteria = getEmptySearchCriteria();

        List<OversightCommitteeDTO> results = null;

        Cd type = new Cd();
        type.setCode("Ethics Committee");
        searchCriteria.setTypeCode(type);
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals("Ethics Committee", results.get(0).getTypeCode().getCode());
    }

    @Override
    protected OversightCommitteeDTO getEmptySearchCriteria() {
        return new OversightCommitteeDTO();
    }

    @Override
    protected void verifyCreatedBy(Ii id1) {
        //retrieve the researchorg
        OversightCommittee oversightCommittee =
                (OversightCommittee) PoHibernateUtil.getCurrentSession()
                        .get(OversightCommittee.class, Long.parseLong(id1.getExtension()));

        //verify createdby set
        assertEquals(getUser(), oversightCommittee.getCreatedBy());
    }
}
