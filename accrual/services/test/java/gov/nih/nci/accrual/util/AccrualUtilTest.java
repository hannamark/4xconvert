/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The accrual
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This accrual Software License (the License) is between NCI and You. You (or 
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
 * its rights in the accrual Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the accrual Software; (ii) distribute and 
 * have distributed to and by third parties the accrual Software and any 
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
package gov.nih.nci.accrual.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.service.util.AccrualCsmUtil;
import gov.nih.nci.accrual.service.util.MockCsmUtil;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceRemote;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
/**
 * @author mshestopalov
 *
 */
public class AccrualUtilTest extends AbstractAccrualHibernateTestCase {

    StudySiteDTO studySiteDto;

    @Before
    public void setup() throws PAException {
        TestSchema.primeData();
        AccrualCsmUtil.setCsmUtil(new MockCsmUtil());
        ServiceLocatorPaInterface svcLocal = mock(ServiceLocatorPaInterface.class);
        RegistryUserServiceRemote regSvc = mock(RegistryUserServiceRemote.class);
        when(regSvc.getUser(any(String.class))).thenReturn(TestSchema.registryUsers.get(0));
        when(svcLocal.getRegistryUserService()).thenReturn(regSvc);

        studySiteDto = new StudySiteDTO();
        StudySiteServiceRemote studySiteSvc = mock(StudySiteServiceRemote.class);
        when(studySiteSvc.get(any(Ii.class))).thenReturn(studySiteDto);
        when(svcLocal.getStudySiteService()).thenReturn(studySiteSvc);
        PaServiceLocator.getInstance().setServiceLocator(svcLocal);
    }
    
    @Test
    public void testAccrualAccessCheck() throws PAException {
        // doesn't find access due to status
        StudySiteAccrualAccess bo = new StudySiteAccrualAccess();
        bo.setRegistryUser(TestSchema.registryUsers.get(0));
        bo.setStudySite(TestSchema.studySites.get(0));
        bo.setStatusCode(ActiveInactiveCode.INACTIVE);
        bo.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        bo.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        TestSchema.addUpdObject(bo);
        assertFalse(AccrualUtil.isUserAllowedAccrualAccess(
                IiConverter.convertToIi(TestSchema.studySites.get(0).getId())));
       
        
        // finds access
        bo.setStatusCode(ActiveInactiveCode.ACTIVE);       
        TestSchema.addUpdObject(bo);
        assertTrue(AccrualUtil.isUserAllowedAccrualAccess(
                IiConverter.convertToIi(TestSchema.studySites.get(0).getId())));
      
        // doesn't find access due to site id
        bo.setStatusCode(ActiveInactiveCode.INACTIVE);       
        TestSchema.addUpdObject(bo);
        
        StudySite ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("T1 Local SP 001");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        ss.setStudyProtocol(TestSchema.studyProtocols.get(0));
        TestSchema.addUpdObject(ss);
        StudySiteAccrualAccess bo2 = new StudySiteAccrualAccess();
        bo2.setRegistryUser(TestSchema.registryUsers.get(0));
        bo2.setStudySite(ss);
        bo2.setStatusCode(ActiveInactiveCode.ACTIVE);
        bo2.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        bo2.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        TestSchema.addUpdObject(bo2);
        
        assertFalse(AccrualUtil.isUserAllowedAccrualAccess(
                IiConverter.convertToIi(TestSchema.studySites.get(0).getId())));
    }

    @Test
    public void isValidTreatingSite() throws Exception {
        assertFalse(AccrualUtil.isValidTreatingSite(null));

        Ii ii = IiConverter.convertToStudySiteIi(null);
        assertFalse(AccrualUtil.isValidTreatingSite(ii));

        ii = IiConverter.convertToStudySiteIi(1L);
        studySiteDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        assertTrue(AccrualUtil.isValidTreatingSite(ii));

        studySiteDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        assertFalse(AccrualUtil.isValidTreatingSite(ii));
    }

    @Test 
    public void safeGetTest() {
        assertNull(AccrualUtil.safeGet(null, 1));
        assertNull(AccrualUtil.safeGet(new ArrayList<String>(), 2));
        List<String> list = new ArrayList<String>();
        list.add("value1");
        assertEquals("value1", AccrualUtil.safeGet(list, 0));
        assertNull(AccrualUtil.safeGet(list, 1));
        list.add(" value2 ");
        assertEquals("value2", AccrualUtil.safeGet(list, 1));
        assertNull(AccrualUtil.safeGet(list, 2));
    }

    @Test
    public void csvParseAndTrimTest() {
        String[] arr = AccrualUtil.csvParseAndTrim("\"12 \",,A,null,\"xyz\"     ");
        assertEquals("12", arr[0]);
        assertEquals("", arr[1]);
        assertEquals("A", arr[2]);
        assertEquals("null", arr[3]);
        assertEquals("xyz", arr[4]);
        assertEquals(5, arr.length);
        
        String[] arr2 = AccrualUtil.csvParseAndTrim(null);
        assertNull(arr2);
        
        String[] arr3 = AccrualUtil.csvParseAndTrim("");
        assertEquals("", arr3[0]);
        assertEquals(1, arr3.length);
    }

    @Test
    public void getCodeTest() {
        assertNull(AccrualUtil.getCode(null));
        assertEquals(AccrualSubmissionTypeCode.BATCH.getCode(), AccrualUtil.getCode(AccrualSubmissionTypeCode.BATCH));
    }
    
    @Test
    public void getDisplayNameTest() {
        assertEquals("", AccrualUtil.getDisplayName(null));
        RegistryUser ru = new RegistryUser();
        User cu = new User();
        ru.setCsmUser(cu);
        assertEquals("", AccrualUtil.getDisplayName(ru));
        cu.setFirstName("x");
        assertEquals("x", AccrualUtil.getDisplayName(ru));
        cu.setLastName("y");
        assertEquals("x y", AccrualUtil.getDisplayName(ru));
        ru.setLastName("b");
        assertEquals("b", AccrualUtil.getDisplayName(ru));
        ru.setFirstName("a");
        assertEquals("a b", AccrualUtil.getDisplayName(ru));
    }
}
