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
package gov.nih.nci.services.family;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.FamilyP30;
import gov.nih.nci.po.data.bo.FamilyStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.data.convert.EnConverter;
import gov.nih.nci.po.data.convert.FamilyFunctionalTypeConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.StringConverter;
import gov.nih.nci.po.service.AbstractServiceBeanTest;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.FamilyOrganizationRelationshipServiceLocal;
import gov.nih.nci.po.service.FamilyServiceLocal;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyP30DTO;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.family.FamilyServiceRemoteBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

/**
 * @author mshestopalov
 * 
 */
public class FamilyServiceRemoteBeanTest extends AbstractServiceBeanTest {

    private FamilyServiceRemote remote;
    private Date oldDate;
    private List<FamilyOrganizationRelationship> famOrgRelList = new ArrayList<FamilyOrganizationRelationship>();
    private Long id = 1l;
    private Family fam1;

    /**
     * setup the service.
     */
    @Before
    public void setupService() {
        Calendar cal = Calendar.getInstance();
        cal.set(2008, 01, 02);
        oldDate = DateUtils.truncate(cal.getTime(), Calendar.DATE);
        fam1 = createFamily("FamilyName1", oldDate);
        Family fam2 = createFamily("FamilyName2", oldDate);
        Family fam3 = createFamily("FamilyName3", oldDate);
        Organization org1 = createOrg("OrgName1");
        Organization org2 = createOrg("OrgName2");
        FamilyOrganizationRelationship famOrgRel1 = createFamOrgRel(fam1, org1, oldDate,
                FamilyFunctionalType.ORGANIZATIONAL);
        FamilyOrganizationRelationship famOrgRel2 = createFamOrgRel(fam1, org2, oldDate,
                FamilyFunctionalType.CONTRACTUAL);
        FamilyOrganizationRelationship famOrgRel3 = createFamOrgRel(fam2, org2, oldDate,
                FamilyFunctionalType.CONTRACTUAL);
        FamilyServiceLocal localFam = mock(FamilyServiceLocal.class);
        FamilyOrganizationRelationshipServiceLocal localFamOrgRel = mock(FamilyOrganizationRelationshipServiceLocal.class);
        FamilyServiceRemoteBean famBean = EjbTestHelper.getFamilyServiceRemoteBean();
        famBean.setFamilyServiceBean(localFam);
        famBean.setFamilyOrganizationRelationshipServiceBean(localFamOrgRel);
        when(localFam.getById(anyLong())).thenReturn(fam1);
        List<Family> famList = new ArrayList<Family>();
        famList.add(fam2);
        famList.add(fam3);
        when(localFam.search(any(SearchCriteria.class), any(PageSortParams.class))).thenReturn(famList);
        famOrgRelList.add(famOrgRel1);
        famOrgRelList.add(famOrgRel2);
        when(localFamOrgRel.getActiveRelationships(anyLong())).thenReturn(famOrgRelList);
        Map<Long, Family> famMap = new HashMap<Long, Family>();
        famMap.put(famOrgRel1.getId(), fam1);
        famMap.put(famOrgRel3.getId(), fam2);
        when(localFamOrgRel.getFamilies(any(Set.class))).thenReturn(famMap);
        when(localFamOrgRel.getById(anyLong())).thenReturn(famOrgRel1);
        remote = famBean;
    }

    private Family createFamily(String name, Date date) {
        Family family = new Family();
        family.setId(id++);
        family.setName(name);
        family.setStartDate(date);
        family.setStatusCode(FamilyStatus.ACTIVE);
        return family;
    }

    private FamilyP30 createFamilyP30(Family family, String serialNumber) {
        FamilyP30 p30 = new FamilyP30();
        p30.setId(id++);
        p30.setSerialNumber(serialNumber);
        p30.setFamily(family);
        family.setFamilyP30(p30);
        return p30;
    }

    private Organization createOrg(String name) {
        Country country = new Country("testorg", "996", "IJ", "IJI");
        Organization org = new Organization();
        org.setName(name);
        Address mailingAddress = new Address("test", "test", "test", "test", country);
        org.setPostalAddress(mailingAddress);
        org.setStatusCode(EntityStatus.NULLIFIED);
        org.getEmail().add(new Email("foo@example.com"));
        org.getUrl().add(new URL("http://example.com"));
        return org;
    }

    private FamilyOrganizationRelationship createFamOrgRel(Family fam, Organization org, Date date,
            FamilyFunctionalType ft) {
        FamilyOrganizationRelationship famOrgRel = new FamilyOrganizationRelationship();
        famOrgRel.setId(id++);
        famOrgRel.setOrganization(org);
        famOrgRel.setFamily(fam);
        famOrgRel.setFunctionalType(ft);
        famOrgRel.setStartDate(date);
        return famOrgRel;
    }

    @Test
    public void testGetFamilyById() {
        PoHibernateUtil.getCurrentSession().beginTransaction();
        Family savedFam = createFamily("FamilyName", oldDate);

        Ii famIi = new Ii();
        famIi.setRoot(IdConverter.FAMILY_ROOT);
        famIi.setExtension(savedFam.getId().toString());
        FamilyDTO freshDto = remote.getFamily(famIi);
        assertEquals("FamilyName1", (new EnConverter<EnOn>()).convertToString(freshDto.getName()));
    }

    @Test
    public void testSearchForFamilyByName() throws EntityValidationException, TooManyResultsException {
        FamilyDTO exampleDto = new FamilyDTO();
        exampleDto.setName(StringConverter.convertToEnOn("Family"));
        LimitOffset page = new LimitOffset(100, 0);
        List<FamilyDTO> famDtoList = remote.search(exampleDto, page);
        assertEquals(2, famDtoList.size());
        assertEquals("FamilyName2", (new EnConverter<EnOn>()).convertToString(famDtoList.get(0).getName()));
        assertEquals("FamilyName3", (new EnConverter<EnOn>()).convertToString(famDtoList.get(1).getName()));

    }

    @Test
    public void testGetFamilyRelOrgByFamily() throws EntityValidationException, TooManyResultsException, JMSException {
        List<FamilyOrganizationRelationshipDTO> famOrgRelDtoList = remote.getActiveRelationships(1L);
        assertEquals(2, famOrgRelDtoList.size());
        assertEquals(FamilyFunctionalType.ORGANIZATIONAL, FamilyFunctionalTypeConverter
                .convertToTypeEnum(famOrgRelDtoList.get(0).getFunctionalType()));
        assertEquals(FamilyFunctionalType.CONTRACTUAL, FamilyFunctionalTypeConverter.convertToTypeEnum(famOrgRelDtoList
                .get(1).getFunctionalType()));
    }

    @Test
    public void testGetFamiliesByFamOrgRelIiSet() throws EntityValidationException, JMSException {
        Set<Ii> iiSet = new HashSet<Ii>();
        for (FamilyOrganizationRelationship rel : famOrgRelList) {
            iiSet.add(new IdConverter.FamilyOrganizationRelationshipIdConverter().convertToIi(rel.getId()));
        }
        
        Map<Ii, FamilyDTO> retMap = remote.getFamilies(iiSet);
        assertEquals(2, retMap.size());
        for (Ii ii : retMap.keySet()) {
            assertEquals(IdConverter.FAMILY_ORG_REL_ROOT, ii.getRoot());
        }
    }

    @Test
    public void testGetFamilyOrganizationRelationship() throws EntityValidationException, TooManyResultsException, JMSException {
        Ii forIi = new Ii();
        forIi.setRoot(IdConverter.FAMILY_ORG_REL_ROOT);
        forIi.setIdentifierName(IdConverter.FAMILY_ORG_REL_IDENTIFIER_NAME);
        forIi.setExtension("1");
        assertNotNull(remote.getFamilyOrganizationRelationship(forIi));
    }

    @Test
    public void testGetFamilyP30Grant() {
        FamilyP30DTO p30Dto = remote.getP30Grant(1L);
        assertNull(p30Dto);
        fam1.setFamilyP30(createFamilyP30(fam1, "1235"));
        p30Dto = remote.getP30Grant(1L);
        assertEquals("1235",(new EnConverter<EnOn>()).convertToString(p30Dto.getSerialNumber()));
    }
}
