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
package gov.nih.nci.coppa.test.remoteapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.CorrelationService;
import gov.nih.nci.services.correlation.AbstractIdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.AbstractIdentifiedPersonDTO;
import gov.nih.nci.services.correlation.AbstractOrganizationRoleDTO;
import gov.nih.nci.services.correlation.AbstractPersonRoleDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.junit.After;
import org.junit.Test;

public abstract class CorrelationTestBase<DTO extends CorrelationDto, SERVICE extends CorrelationService<DTO>> {

    private Ii correlationId;
    protected Ii orgId;
    private Ii personId;
    private String tableNameCR;
    private Connection c;

    protected CorrelationTestBase(String tableNameCR) {
        this.tableNameCR = tableNameCR;
    }

    /**
     * cleanup after test is complete.
     *
     * @throws Exception on error.
     */
    @After
    public void cleanup() throws Exception {
        RemoteServiceHelper.close();
        if (c != null) {
            c.close();
        }
    }

    protected void createMinimal() throws Exception {
        if (correlationId != null) {
            return; // test already run from another test case.
        }
        DTO dto = makeCorrelation();
        try {
            correlationId = getCorrelationService().createCorrelation(dto);
        } catch (EntityValidationException e) {
            fail(e.getErrorMessages());
        }
        assertNotNull(correlationId);
        assertNotNull(correlationId.getExtension());

        dto = getCorrelationService().getCorrelation(correlationId);
        verifyCreated(dto);
    }

    protected abstract DTO makeCorrelation() throws Exception;

    protected abstract void verifyCreated(DTO dto) throws Exception;

    @Test
    public void getByPlayerIds() throws Exception {
        DTO sr1 = makeCorrelation();
        getCorrelationService().createCorrelation(sr1);

        Ii[] pids = null;

        if (sr1 instanceof AbstractPersonRoleDTO || sr1 instanceof AbstractIdentifiedPersonDTO) {
            pids = new Ii[1];
            pids[0] = this.getPersonId();
        } else if (sr1 instanceof AbstractOrganizationRoleDTO || sr1 instanceof AbstractIdentifiedOrganizationDTO) {
            pids = new Ii[1];
            pids[0] = this.getOrgId();
        } else {
            throw new Exception("Could not determine what type of DTO this is.");
        }

        List<DTO> returnVals = getCorrelationService().getCorrelationsByPlayerIds(pids);
        assertEquals(1, returnVals.size());

        returnVals = getCorrelationService().getCorrelationsByPlayerIds(new Ii[1]);
        assertEquals(0, returnVals.size());
    }

    @Test
    public void update() throws Exception {
        if (correlationId == null) {
            createMinimal();
        }

        c = DataGeneratorUtil.getJDBCConnection();
        ResultSet rs =
                c.createStatement().executeQuery(
                        "select count(*) from " + tableNameCR + " where target = " + correlationId.getExtension());
        assertTrue(rs.next());
        int count0 = rs.getInt(1);
        rs.close();

        DTO dto = getCorrelationService().getCorrelation(correlationId);
        getCorrelationService().updateCorrelation(dto);
        getCorrelationService().updateCorrelation(dto);
        getCorrelationService().updateCorrelation(dto);

        rs =
                c.createStatement().executeQuery(
                        "select count(*) from " + tableNameCR + " where target = " + correlationId.getExtension());
        assertTrue(rs.next());
        int count1 = rs.getInt(1);
        rs.close();
        assertEquals(count0 + 3, count1);
    }

    @Test(expected = javax.ejb.EJBException.class)
    public void updateWithNoIdentifier() throws Exception {
        if (correlationId == null) {
            createMinimal();
        }

        DTO dto = getCorrelationService().getCorrelation(correlationId);
        dto.getIdentifier().getItem().clear();
        getCorrelationService().updateCorrelation(dto);
    }

    @Test(expected = javax.ejb.EJBException.class)
    public void updateWithWrongIdentifier() throws Exception {
        if (correlationId == null) {
            createMinimal();
        }

        DTO dto = getCorrelationService().getCorrelation(correlationId);
        dto.getIdentifier().getItem().clear();
        Ii wrongId = new Ii();
        wrongId.setExtension("999");
        dto.getIdentifier().getItem().add(wrongId);
        getCorrelationService().updateCorrelation(dto);
    }

    @Test(expected = javax.ejb.EJBException.class)
    public void updateStatusWithWrongIdentifier() throws Exception {
        Cd cd = new Cd();
        cd.setCode("suspended"); // maps to SUSPENDED

        Ii wrongId = new Ii();
        wrongId.setExtension("999");

        getCorrelationService().updateCorrelationStatus(wrongId, cd);
    }

    @Test
    public void updateStatus() throws Exception {
        if (correlationId == null) {
            createMinimal();
        }

        c = DataGeneratorUtil.getJDBCConnection();
        ResultSet rs =
                c.createStatement().executeQuery(
                        "select count(*) from " + tableNameCR + " where target = " + correlationId.getExtension()
                                + " and status = 'DEPRECATED'");
        assertTrue(rs.next());
        int count0 = rs.getInt(1);
        rs.close();

        Cd cd = new Cd();
        cd.setCode("suspended"); // maps to SUSPENDED
        getCorrelationService().updateCorrelationStatus(correlationId, cd);

        rs =
                c.createStatement().executeQuery(
                        "select count(*) from " + tableNameCR + " where target = " + correlationId.getExtension()
                                + " and status = 'SUSPENDED'");
        assertTrue(rs.next());
        int count1 = rs.getInt(1);
        rs.close();
        assertEquals(count0 + 1, count1);
    }

    protected abstract SERVICE getCorrelationService() throws Exception;

    public Ii getOrgId() throws Exception {
        if (orgId == null) {
            OrganizationEntityServiceTest test = new OrganizationEntityServiceTest();
            test.init();
            test.createMinimal();
            orgId = test.getOrgId();
            assertNotNull(orgId);
        }
        return orgId;
    }

    public Ii getPersonId() throws Exception {
        if (personId == null) {
            PersonEntityServiceTest test = new PersonEntityServiceTest();
            test.init();
            test.createMinimal();
            personId = test.getPersonId();
            assertNotNull(personId);
        }
        return personId;
    }

    /**
     * get correlation id.
     *
     * @return correlation id
     */
    protected Ii getCorrelationId() {
        return this.correlationId;
    }

    /**
     * Get connection.
     *
     * @return connection.
     */
    protected Connection getConnection() {
        return this.c;
    }

    protected void createConnection() {
        c = DataGeneratorUtil.getJDBCConnection();
    }
}
