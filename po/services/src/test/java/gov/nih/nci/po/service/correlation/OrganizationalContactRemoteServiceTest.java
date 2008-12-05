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
package gov.nih.nci.po.service.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.po.data.bo.OrganizationalContactCR;
import gov.nih.nci.po.data.bo.OrganizationalContactType;
import gov.nih.nci.po.data.convert.BooleanConverter;
import gov.nih.nci.po.data.convert.OrganizationalContactTypeConverter;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.CorrelationService;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author smatyas
 *
 */
public class OrganizationalContactRemoteServiceTest extends
        AbstractPersonRoleDTORemoteServiceTest<OrganizationalContactDTO, OrganizationalContactCR> {
    private final Set<OrganizationalContactType> types = new HashSet<OrganizationalContactType>();

    private Set<OrganizationalContactType> getTypes() {
        return types;
    }

    @Before
    public void initDbData() {
        types.add(new OrganizationalContactType("For drug shipment"));
        types.add(new OrganizationalContactType("For safety issues"));
        for (OrganizationalContactType obj : getTypes()) {
            PoHibernateUtil.getCurrentSession().save(obj);
        }
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    CorrelationService<OrganizationalContactDTO> getCorrelationService() {
        return EjbTestHelper.getOrganizationalContactCorrelationServiceRemote();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected OrganizationalContactDTO getSampleDto() throws Exception {
        OrganizationalContactDTO dto = new OrganizationalContactDTO();
        fillInPersonRoleDate(dto);
        Bl primary = new Bl();
        primary.setValue(Boolean.TRUE);
        dto.setPrimaryIndicator(primary);

        dto.setTypeCode(OrganizationalContactTypeConverter.convertToDsetOfCd(getTypes()));
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    void verifyDto(OrganizationalContactDTO e, OrganizationalContactDTO a) {
        assertEquals(e.getPrimaryIndicator().getValue(), a.getPrimaryIndicator().getValue());

        List<String> expectedValues = OrganizationalContactDTOTest.getCodeValues(e.getTypeCode());
        List<String> actualValues = OrganizationalContactDTOTest.getCodeValues(a.getTypeCode());
        assertEquals(expectedValues.size(), actualValues.size());
        assertTrue(expectedValues.containsAll(actualValues));
        assertTrue(actualValues.containsAll(expectedValues));

        verifyPersonRoleDto(e, a);
    }

    @Test(expected = EntityValidationException.class)
    public void testCreateWithException() throws Exception {
        OrganizationalContactDTO pr = new OrganizationalContactDTO();
        getCorrelationService().createCorrelation(pr);
    }

    @Test
    public void testValidate() throws Exception {
        OrganizationalContactDTO pr = new OrganizationalContactDTO();
        Map<String, String[]> errors = getCorrelationService().validate(pr);
        assertEquals(5, errors.keySet().size());
    }

    @Override
    protected void alter(OrganizationalContactDTO dto) {
        dto.setPrimaryIndicator(BooleanConverter.convertToBl(Boolean.FALSE));
    }

    @Override
    protected void verifyAlterations(OrganizationalContactCR cr) {
        super.verifyAlterations(cr);
        assertEquals(Boolean.FALSE, cr.getPrimaryIndicator());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected OrganizationalContactDTO getEmptySearchCriteria() {
        return new OrganizationalContactDTO();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void modifySubClassSpecificFieldsForCorrelation2(OrganizationalContactDTO correlation2) {
        correlation2.getPrimaryIndicator().setValue(null);
        correlation2.getPrimaryIndicator().setNullFlavor(NullFlavor.NI);
        correlation2.getTypeCode().getItem().clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void testSearchOnSubClassSpecificFields(OrganizationalContactDTO correlation1, Ii id2,
            OrganizationalContactDTO searchCriteria) throws NullifiedRoleException {
        // search by primary indicator
        searchCriteria.getTelecomAddress().getItem().clear();
        searchCriteria.setPrimaryIndicator(new Bl());
        searchCriteria.getPrimaryIndicator().setValue(Boolean.TRUE);
        List<OrganizationalContactDTO> results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());

        searchCriteria.getPrimaryIndicator().setValue(Boolean.FALSE);
        results = getCorrelationService().search(searchCriteria);
        assertEquals(0, results.size());

        // search by typeCode
        searchCriteria.getPrimaryIndicator().setValue(null);
        searchCriteria.getPrimaryIndicator().setNullFlavor(NullFlavor.NI);
        searchCriteria.setTypeCode(new DSet<Cd>());
        searchCriteria.getTypeCode().setItem(new HashSet());
        Cd typeCode = new Cd();
        searchCriteria.getTypeCode().getItem().add(typeCode);

        typeCode.setCode("For drug shipment");
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());

        typeCode.setCode("For safety issues");
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());

        try {
            // attempt to search on an unknown code and it returns null therefore no criterion is ever set
            typeCode.setCode("aaa");
            getCorrelationService().search(searchCriteria);
            fail();
        } catch (IllegalArgumentException e) {
            // expected
        }

        // search by typeCode and primaryIndicator
        searchCriteria.setPrimaryIndicator(new Bl());
        searchCriteria.getPrimaryIndicator().setValue(Boolean.FALSE);
        typeCode.setCode("For safety issues");
        results = getCorrelationService().search(searchCriteria);
        assertEquals(0, results.size());
    }
}
