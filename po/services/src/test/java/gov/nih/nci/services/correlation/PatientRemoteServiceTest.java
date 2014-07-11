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
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.Patient;
import gov.nih.nci.po.data.bo.PatientCR;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.data.convert.RoleStatusConverter;
import gov.nih.nci.po.data.convert.StatusCodeConverter;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.CorrelationService;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.Test;

/**
 * @author mshestopalov
 *
 */
public class PatientRemoteServiceTest extends AbstractPersonRoleDTORemoteServiceTest<PatientDTO, PatientCR> {

    /**
     * {@inheritDoc}
     */
    @Override
    CorrelationService<PatientDTO> getCorrelationService() {
        return EjbTestHelper.getPatientCorrelationServiceRemote();
    }

    @Override
    protected void verifyCreatedBy(long id) {
        //not currently stored
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public PatientDTO getSampleDto() throws Exception {
        PatientDTO dto = new PatientDTO();
        createAndSetOrganization();
        basicOrganization.setStatusCode(EntityStatus.ACTIVE);
        EjbTestHelper.getOrganizationServiceBean().curate(basicOrganization);
        fillInPersonRoleDate(dto);
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void verifyDto(PatientDTO expected, PatientDTO actual) {
        verifyPersonRoleDto(expected, actual);
    }

    @Test
    public void testValidate() throws Exception {
        PatientDTO pr = new PatientDTO();
        Map<String, String[]> errors = getCorrelationService().validate(pr);
        assertEquals(2, errors.keySet().size());
    }

    @Override
    protected void alter(PatientDTO dto) throws Exception {
        TelPhone email = new TelPhone();
        String uri = TelPhone.SCHEME_X_TEXT_TEL + ":+1234567890";
        email.setValue(new URI(uri));
        dto.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        uri = "http://example.com";
        url.setValue(new URI(uri));
        dto.getTelecomAddress().getItem().add(url);
    }

    @Override
    @Test
    public void updateCorrelation() throws Exception {
        CorrelationService<PatientDTO> service = getCorrelationService();
        Ii id = service.createCorrelation(getSampleDto());
        PatientDTO dto = service.getCorrelation(id);
        alter(dto);
        service.updateCorrelation(dto);
        List<Patient> l = PoHibernateUtil.getCurrentSession().createCriteria(Patient.class).list();
        assertEquals(1, l.size());
        Patient fresh = l.get(0);
        assertTrue(CollectionUtils.exists(fresh.getTty(), new Predicate() {
            public boolean evaluate(Object object) {
                return ((PhoneNumber) object).getValue().equals("+1234567890");
            }
        }));

        assertTrue(CollectionUtils.exists(fresh.getUrl(), new Predicate() {
            public boolean evaluate(Object object) {
                return ((URL) object).getValue().equals("http://example.com");
            }
        }));
    }

    @Override
    @Test
    public void updateCorrelationStatus() throws Exception {
        CorrelationService<PatientDTO> service = getCorrelationService();
        Ii id = service.createCorrelation(getSampleDto());
        service.getCorrelation(id);
        Cd newStatus = RoleStatusConverter.convertToCd(RoleStatus.NULLIFIED);
        service.updateCorrelationStatus(id, newStatus);
        @SuppressWarnings("unchecked")
        List<Patient> l = PoHibernateUtil.getCurrentSession().createCriteria(Patient.class).list();
        assertEquals(1, l.size());
        Patient cr = l.get(0);
        assertEquals(cr.getStatus(), RoleStatus.NULLIFIED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PatientDTO getEmptySearchCriteria() {
        return new PatientDTO();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void modifySubClassSpecificFieldsForCorrelation2(PatientDTO correlation2) {
        // no fields to handle
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void testSearchOnSubClassSpecificFields(PatientDTO correlation1, Ii id2, PatientDTO searchCriteria)
            throws NullifiedRoleException {

        testNullifiedRoleNotFoundInSearch(id2, searchCriteria, Patient.class);
    }

    /**
     * test get by player ids.
     *
     * @throws Exception
     * @throws CurationException
     * @throws EntityValidationException
     */
    @Test
    public void testByPlayerIds() throws EntityValidationException, CurationException, Exception {
        Ii[] playerIis = new Ii[3];
        CorrelationService<PatientDTO> service = getCorrelationService();

        Ii id1 = service.createCorrelation(getSampleDto());
        PatientDTO dto1 = service.getCorrelation(id1);
        playerIis[0] = dto1.getPlayerIdentifier();

        Ii id2 = service.createCorrelation(getSampleDto());
        PatientDTO dto2 = service.getCorrelation(id2);
        playerIis[1] = dto2.getPlayerIdentifier();

        Ii id3 = service.createCorrelation(getSampleDto());
        PatientDTO dto3 = service.getCorrelation(id3);
        playerIis[2] = dto3.getPlayerIdentifier();

        for (Ii ii : playerIis) {
            assertTrue(ii.getExtension().startsWith("PT"));
        }

        List<PatientDTO> patDtos = service.getCorrelationsByPlayerIds(playerIis);
        assertEquals(3, patDtos.size());
    }

    @Test
    @Override
    public void incorrectUpdateCorrelationStatus() throws Exception {
        // NOOP
    }

    @Override
    protected void searchByStatus(PatientDTO searchCriteria, List<PatientDTO> results) {
        // search by status
        searchCriteria.setPostalAddress(null);
        searchCriteria.setStatus(StatusCodeConverter.convertToCd(EntityStatus.PENDING));
        results = getCorrelationService().search(searchCriteria);
        assertEquals(2, results.size());
    }

    @Override
    protected void verifyPersonRoleDto(AbstractPersonRoleDTO e, AbstractPersonRoleDTO a) {
        assertEquals(e.getScoperIdentifier().getExtension(), a.getScoperIdentifier().getExtension());
        assertEquals("PT" + e.getPlayerIdentifier().getExtension(), a.getPlayerIdentifier().getExtension());
        assertEquals("pending", a.getStatus().getCode());
        assertEquals(e.getPostalAddress().getItem().size(), a.getPostalAddress().getItem().size());
        assertEquals(e.getTelecomAddress().getItem().size(), a.getTelecomAddress().getItem().size());
    }

    @Override
    protected void search2StatusChange(PatientDTO sc) {
        sc.setStatus(RoleStatusConverter.convertToCd(RoleStatus.PENDING));
    }

}
