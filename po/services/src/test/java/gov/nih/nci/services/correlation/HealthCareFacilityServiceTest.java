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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.AbstractOrganizationRole;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.external.CtepOrganizationImporter;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoServiceUtil;

import java.util.HashSet;
import java.util.List;

import javax.jms.JMSException;

import org.junit.Test;

import com.fiveamsolutions.nci.commons.search.OneCriterionRequiredException;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

/**
 * Service tests.
 */
public class HealthCareFacilityServiceTest extends AbstractOrganizationalRoleServiceTest<HealthCareFacility> {

    @Override
    HealthCareFacility getSampleStructuralRole() {
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setPlayer(basicOrganization);
        Alias alias = new Alias("hcf alias 1");
        Alias alias2 = new Alias("hcf alias 2");
        hcf.getAlias().add(alias);
        hcf.getAlias().add(alias2);
        try {
            // re-gen new Player Org
            setUpData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return hcf;
    }

    @Override
    void verifyStructuralRole(HealthCareFacility expected, HealthCareFacility actual) {
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void testCurateHCFWithCtepId() throws Exception {
        // create HCF first
        HealthCareFacilityServiceLocal svc = (HealthCareFacilityServiceLocal) getService();
        HealthCareFacility hcf = getSampleStructuralRole();        
        long id= svc.create(hcf);
        
        // get the created HCF from the DB
        hcf = (HealthCareFacility) PoHibernateUtil.getCurrentSession().load(HealthCareFacility.class, id);
        assertTrue(hcf.getAlias().size() == 2);
        assertEquals("hcf alias 1", hcf.getAlias().get(0).getValue());
        assertEquals("hcf alias 2", hcf.getAlias().get(1).getValue()); 
        
        String name = "updated HCF";
        hcf.setName(name);
        hcf.setStatus(RoleStatus.ACTIVE);
        String ctepId ="12345";
        
        // clear existing & add a new Alias
        hcf.getAlias().clear();
        hcf.getAlias().add(new Alias("test updated hcf alias"));

        svc.curate(hcf, ctepId);
        assertEquals(ctepId, PoServiceUtil.getOrgRoleBoCtepId(hcf));
        assertEquals(name, hcf.getName()); 
        
        // get the updated hcf
        hcf = (HealthCareFacility) PoHibernateUtil.getCurrentSession().load(HealthCareFacility.class, id);
        assertTrue(hcf.getAlias().size() == 1);
        assertEquals("test updated hcf alias", hcf.getAlias().get(0).getValue());
        
        // add another Alias to the existing one
        hcf.getAlias().add(new Alias("test alias 2"));
        svc.curate(hcf);        
        hcf = (HealthCareFacility) PoHibernateUtil.getCurrentSession().load(HealthCareFacility.class, id);
        assertTrue(hcf.getAlias().size() == 2);
        assertEquals("test updated hcf alias", hcf.getAlias().get(0).getValue());
        assertEquals("test alias 2", hcf.getAlias().get(1).getValue());               
    }
    
    @Test
    public void testCurateHCFWithBlankCtepId() throws Exception {
        // create HCF first
        HealthCareFacilityServiceLocal svc = (HealthCareFacilityServiceLocal) getService();
        HealthCareFacility hcf = getSampleStructuralRole();        
        long id= svc.create(hcf);
        
        // get the created HCF from the DB
        hcf = (HealthCareFacility) PoHibernateUtil.getCurrentSession().load(HealthCareFacility.class, id);
        String name = "updated HCF";
        hcf.setName(name);
        hcf.setStatus(RoleStatus.ACTIVE);
        String ctepId =""; // blank CtepId

       svc.curate(hcf, ctepId);
       assertEquals(name, hcf.getName());
    }
    
    @Test
    public void testSearch() throws Exception {
        HealthCareFacilityServiceLocal svc = (HealthCareFacilityServiceLocal) getService();

        HealthCareFacility hcf = getSampleStructuralRole();
        svc.create(hcf);


        SearchCriteria<HealthCareFacility> sc = new AnnotatedBeanSearchCriteria<HealthCareFacility>(null);

        try {
            svc.search(null);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        try {
            svc.search(sc);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        testSearchParams(hcf, hcf.getId(), null, null, 1);
        testSearchParams(hcf, null, hcf.getPlayer().getId(), null, 1);
        testSearchParams(hcf, null, null, hcf.getStatus(), 1);
        testSearchParams(hcf, hcf.getId(), hcf.getPlayer().getId(), hcf.getStatus(), 1);

        testSearchParams(hcf, -1L, null, null, 0);
        testSearchParams(hcf, -1L, null, hcf.getStatus(), 0); // verifies that we're doing ANDs (not ORs)

        HealthCareFacility hcf2 = getSampleStructuralRole();
        svc.create(hcf2);

        // WTF? looks like hcf is getting evicted when hcf2 is created (since the fix for PO-628)
        hcf = (HealthCareFacility) PoHibernateUtil.getCurrentSession().load(HealthCareFacility.class, hcf.getId());

        testSearchParams(hcf2, hcf2.getId(), null, null, 1);
        testSearchParams(hcf, null, null, hcf.getStatus(), 2);
        testSearchParams(hcf2, null, null, hcf.getStatus(), 2);

    }

    private void testSearchParams(HealthCareFacility hcf, Long id, Long playerId, RoleStatus rs,
            int numExpected) {
        HealthCareFacilityServiceLocal svc = (HealthCareFacilityServiceLocal) getService();
        HealthCareFacility example = new HealthCareFacility();
        example.setId(id);
        example.setPlayer(new Organization());
        example.getPlayer().setId(playerId);
        example.setStatus(rs);

        SearchCriteria<HealthCareFacility> sc = new AnnotatedBeanSearchCriteria<HealthCareFacility>(example);
        List<HealthCareFacility> l = svc.search(sc);
        assertNotNull(l);
        assertEquals(numExpected, l.size());
        if (numExpected > 0) {
            assertTrue(l.contains(hcf));
        }
    }

    @Test
    public void testUniqueConstraint() throws Exception {
        HealthCareFacility hcf = getSampleStructuralRole();
        HealthCareFacility hcf2 = getSampleStructuralRole();
        hcf.setPlayer(hcf2.getPlayer());
        getService().create(hcf);
        getService().create(hcf2);
    }

    @Test
    public void testHotRoleCount() throws EntityValidationException, JMSException {
        HealthCareFacility hcf = getSampleStructuralRole();
        HealthCareFacilityServiceLocal s = (HealthCareFacilityServiceLocal) getService();
        s.create(hcf);
        int c = s.getHotRoleCount(hcf.getPlayer());
        assertEquals(1, c);
    }

    @Override
    protected HealthCareFacility getSampleCtepOwnedStructuralRole() {
        HealthCareFacility r = getSampleStructuralRole();
        r.setOtherIdentifiers(new HashSet<Ii>());
        Ii ctepRoIi = new Ii();
        ctepRoIi.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        ctepRoIi.setIdentifierName("ro id name");
        ctepRoIi.setExtension("CTEP");
        r.getOtherIdentifiers().add(ctepRoIi);
        return r;
    }

    @Override
    HealthCareFacility getNewStructuralRole() {
        return new HealthCareFacility();
    }

    @Test
    public void testNullifyRoleWithDuplicateSet() throws Exception {

        //role 1 exists with alias1
        HealthCareFacility role1 = getSampleStructuralRole();
        role1.setName("role1");
        role1.getAlias().clear();
        role1.getAlias().add(new Alias("alias1"));

        //role 2 exists with alias2
        HealthCareFacility role2 = getSampleStructuralRole();
        role2.setName("role2");
        role2.getAlias().clear();
        role2.getAlias().add(new Alias("alias2"));

        getService().create(role1);
        long id2 = getService().create(role2);

        //role 1 is nullified with duplicateOf set to role 2
        role1.setStatus(RoleStatus.NULLIFIED);
        role1.setDuplicateOf(role2);
        getService().curate(role1);

        //verify aliases copied
        role2 = getService().getById(id2);
        List<Alias> aliases = role2.getAlias();

        assertEquals(3, aliases.size());
        assertEquals("alias2", aliases.get(0).getValue());
        assertEquals("alias1", aliases.get(1).getValue());
        assertEquals("role1", aliases.get(2).getValue());
    }
}
