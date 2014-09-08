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
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.AbstractOrganizationRole;
import gov.nih.nci.po.data.bo.FundingMechanism;
import gov.nih.nci.po.data.bo.FundingMechanism.FundingMechanismStatus;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationType;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.service.external.CtepOrganizationImporter;
import gov.nih.nci.po.util.PoConstants;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoServiceUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidStateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.search.SearchCriteria;

/**
 * Service test.
 */
public class ResearchOrganizationServiceTest extends AbstractOrganizationalRoleServiceTest<ResearchOrganization> {

    private ResearchOrganizationType sampleType = null;
    private FundingMechanism fm = null;

    @Before
    public void setupType() throws Exception {
        fm = new FundingMechanism("BXX", "Mental Health Services Block Grant", "Block Grants",
                        FundingMechanismStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().save(fm);
        sampleType = new ResearchOrganizationType("ST", "sampleType");
        sampleType.getFundingMechanisms().add(fm);
        PoHibernateUtil.getCurrentSession().save(sampleType);
    }

    @Override
    ResearchOrganization getSampleStructuralRole() {
        ResearchOrganization ro = new ResearchOrganization();
        ro.setPlayer(basicOrganization);
        ro.setTypeCode(sampleType);
        ro.setFundingMechanism(fm);
        
        Alias alias = new Alias("ro alias 1");
        Alias alias2 = new Alias("ro alias 2");
        ro.getAlias().add(alias);
        ro.getAlias().add(alias2);

        try {
            // re-gen new Player Org
            setUpData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return ro;
    }

    @Override
    void verifyStructuralRole(ResearchOrganization expected, ResearchOrganization actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTypeCode().getCode(), actual.getTypeCode().getCode());
        assertEquals(RoleStatus.PENDING, actual.getStatus());
        assertEquals(expected.getFundingMechanism().getCode(), actual.getFundingMechanism().getCode());
    }

    @Test
    public void testCurateROAlias() throws Exception {
        // create RO first        
        ResearchOrganizationServiceLocal svc = (ResearchOrganizationServiceLocal) getService();
        ResearchOrganization ro = getSampleStructuralRole();        
        long id= svc.create(ro);
        
        // get the created RO from the DB
        ro = (ResearchOrganization) PoHibernateUtil.getCurrentSession().load(ResearchOrganization.class, id);
        assertTrue(ro.getAlias().size() == 2);
        assertEquals("ro alias 1", ro.getAlias().get(0).getValue());
        assertEquals("ro alias 2", ro.getAlias().get(1).getValue()); 
        
        String name = "updated RO";
        ro.setName(name);
        ro.setStatus(RoleStatus.ACTIVE);
        String ctepId ="12345";
        
        // clear existing & add a new Alias
        ro.getAlias().clear();
        ro.getAlias().add(new Alias("test updated ro alias"));

        svc.curate(ro, ctepId);
        assertEquals(ctepId, PoServiceUtil.getOrgRoleBoCtepId(ro));
        assertEquals(name, ro.getName()); 
        
        // get the updated RO
        ro = (ResearchOrganization) PoHibernateUtil.getCurrentSession().load(ResearchOrganization.class, id);
        assertTrue(ro.getAlias().size() == 1);
        assertEquals("test updated ro alias", ro.getAlias().get(0).getValue());
        
        // add another Alias to the existing one
        ro.getAlias().add(new Alias("test alias 2"));
        svc.curate(ro);        
        ro = (ResearchOrganization) PoHibernateUtil.getCurrentSession().load(ResearchOrganization.class, id);
        assertTrue(ro.getAlias().size() == 2);
        assertEquals("test updated ro alias", ro.getAlias().get(0).getValue());
        assertEquals("test alias 2", ro.getAlias().get(1).getValue());          
    }
    
    @Test
    public void testUnique() throws Exception {
        ResearchOrganization ro1 = super.createSample();
        ResearchOrganization ro2 = super.createSample();

        ro1.setPlayer(ro2.getPlayer());
        ro1.setTypeCode(ro2.getTypeCode());
        ro1.setFundingMechanism(ro2.getFundingMechanism());
        try {
            PoHibernateUtil.getCurrentSession().flush();
            PoHibernateUtil.getCurrentSession().update(ro1);
            PoHibernateUtil.getCurrentSession().flush();
            Assert.fail();
        } catch (InvalidStateException e) {
            PoHibernateUtil.getCurrentSession().clear();
            ro1 = (ResearchOrganization) PoHibernateUtil.getCurrentSession().get(ResearchOrganization.class, ro1.getId());
            ro2 = (ResearchOrganization) PoHibernateUtil.getCurrentSession().get(ResearchOrganization.class, ro2.getId());
        }

        FundingMechanism otherfm = new FundingMechanism("ZZZ", "bla", "bla", FundingMechanismStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().save(otherfm);
        PoHibernateUtil.getCurrentSession().flush();

        ResearchOrganizationType otherType = new ResearchOrganizationType("otherType", "Some other stuff");
        otherType.getFundingMechanisms().add(fm);
        otherType.getFundingMechanisms().add(otherfm);
        PoHibernateUtil.getCurrentSession().save(otherType);
        PoHibernateUtil.getCurrentSession().flush();

        ro1.setTypeCode(otherType);
        PoHibernateUtil.getCurrentSession().update(ro1);
        PoHibernateUtil.getCurrentSession().flush();

        ro2.getTypeCode().getFundingMechanisms().add(otherfm);
        PoHibernateUtil.getCurrentSession().update(ro2.getTypeCode());
        PoHibernateUtil.getCurrentSession().flush();

        ro1.setTypeCode(ro2.getTypeCode());
        ro1.setFundingMechanism(otherfm);
        PoHibernateUtil.getCurrentSession().update(ro1);
        PoHibernateUtil.getCurrentSession().flush();

        ro2.setStatus(RoleStatus.NULLIFIED);
        PoHibernateUtil.getCurrentSession().update(ro2);
        PoHibernateUtil.getCurrentSession().flush();

        ro1.setTypeCode(ro2.getTypeCode());
        ro1.setFundingMechanism(ro2.getFundingMechanism());
        PoHibernateUtil.getCurrentSession().update(ro1);
        PoHibernateUtil.getCurrentSession().flush();
    }

    @Test
    public void testResearchOrganizationTypeCodeValidator() throws Exception {
        ResearchOrganization ro = super.createSample();
        assertEquals(RoleStatus.PENDING, ro.getStatus());

        ro.setTypeCode(null);
        PoHibernateUtil.getCurrentSession().update(ro);

        ro.setStatus(RoleStatus.ACTIVE);
        Map<String, String[]> errors = EjbTestHelper.getResearchOrganizationServiceBean().validate(ro);
        assertEquals(1, errors.size());
    }

    @Test
    public void testByFundingMechanism() throws Exception {
        ResearchOrganization ro = super.createSample();

        ResearchOrganization searchRo = new ResearchOrganization();
        FundingMechanism fm = new FundingMechanism("BXX", null, null, null);
        searchRo.setFundingMechanism(fm);
        SearchCriteria<ResearchOrganization> sc = new AnnotatedBeanSearchCriteria<ResearchOrganization>(searchRo);
        List<ResearchOrganization> result = EjbTestHelper.getResearchOrganizationServiceBean().search(sc);
        assertEquals(1, result.size());

        fm = new FundingMechanism(null, "Mental", null, null);
        searchRo.setFundingMechanism(fm);
        sc = new AnnotatedBeanSearchCriteria<ResearchOrganization>(searchRo);
        result = EjbTestHelper.getResearchOrganizationServiceBean().search(sc);
        assertEquals(1, result.size());

        fm = new FundingMechanism(null, null, "Block", null);
        searchRo.setFundingMechanism(fm);
        sc = new AnnotatedBeanSearchCriteria<ResearchOrganization>(searchRo);
        result = EjbTestHelper.getResearchOrganizationServiceBean().search(sc);
        assertEquals(1, result.size());

        fm = new FundingMechanism(null, null, null, FundingMechanismStatus.ACTIVE);
        searchRo.setFundingMechanism(fm);
        sc = new AnnotatedBeanSearchCriteria<ResearchOrganization>(searchRo);
        result = EjbTestHelper.getResearchOrganizationServiceBean().search(sc);
        assertEquals(1, result.size());

        fm = new FundingMechanism(null, null, "Block", FundingMechanismStatus.INACTIVE);
        searchRo.setFundingMechanism(fm);
        sc = new AnnotatedBeanSearchCriteria<ResearchOrganization>(searchRo);
        result = EjbTestHelper.getResearchOrganizationServiceBean().search(sc);
        assertEquals(0, result.size());

        fm = new FundingMechanism("BX", null, null, null);
        searchRo.setFundingMechanism(fm);
        sc = new AnnotatedBeanSearchCriteria<ResearchOrganization>(searchRo);
        result = EjbTestHelper.getResearchOrganizationServiceBean().search(sc);
        assertEquals(0, result.size());

    }

    @Override
    protected ResearchOrganization getSampleCtepOwnedStructuralRole() {
        ResearchOrganization r = getSampleStructuralRole();
        r.setOtherIdentifiers(new HashSet<Ii>());
        Ii ctepRoIi = new Ii();
        ctepRoIi.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        ctepRoIi.setIdentifierName("ro id name");
        ctepRoIi.setExtension("CTEP");
        r.getOtherIdentifiers().add(ctepRoIi);
        return r;
    }

    @Override
    ResearchOrganization getNewStructuralRole() {
        return new ResearchOrganization();
    }

    @Test
    public void testNullifyRoleWithDuplicateSet() throws Exception {

        Ii oldCtepId = new Ii();
        oldCtepId.setRoot(PoConstants.ORG_CTEP_ID_ROOT);
        oldCtepId.setIdentifierName(PoConstants.ORG_CTEP_ID_IDENTIFIER_NAME);
        oldCtepId.setExtension("OLD CTEP ID");

        //role 1 exists with alias1
        ResearchOrganization role1 = getSampleStructuralRole();
        role1.setName("role1");
        role1.getAlias().clear();
        role1.getAlias().add(new Alias("alias1"));
        role1.setOtherIdentifiers(new HashSet<Ii>());
        role1.getOtherIdentifiers().add(oldCtepId);
        getService().create(role1);

        //role 2 exists with alias2
        ResearchOrganization role2 = getSampleStructuralRole();
        role2.setName("role2");
        role2.getAlias().clear();
        role2.getAlias().add(new Alias("alias2"));

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

        assertTrue(role2.getOtherIdentifiers().isEmpty());
    }
}

