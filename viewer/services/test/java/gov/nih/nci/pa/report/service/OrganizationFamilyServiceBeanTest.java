/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The viewer
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This viewer Software License (the License) is between NCI and You. You (or 
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
 * its rights in the viewer Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the viewer Software; (ii) distribute and 
 * have distributed to and by third parties the viewer Software and any 
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
package gov.nih.nci.pa.report.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * @author imerenko
 */
public class OrganizationFamilyServiceBeanTest {
    
    /**
     * Test getOrganizations() method
     * @throws TooManyResultsException
     * @throws PAException
     */
    @Test
    public void getOrganizations() throws TooManyResultsException, PAException {
        OrganizationFamilyServiceBean bean = createAdHocReportBeanMock();
        when(bean.getFamilyService().getFamily(any(Ii.class))).thenReturn(new FamilyDTO());
        when(
             bean.getOrganizationEntityService().search(any(OrganizationDTO.class), any(EnOn.class),
                                                        any(LimitOffset.class)))
            .thenReturn(createOrganizationDTOList());
        String organizationType = PAConstants.LEAD_ORGANIZATION;       
        when(bean.getPaOrganizationService().getOrganizationsAssociatedWithStudyProtocol(organizationType))
            .thenReturn(createListPaOrganizationDTOList());
        
        doCallRealMethod().when(bean).getOrganizations(organizationType, "1", 100);
        List<PaOrganizationDTO> result = bean.getOrganizations(organizationType, "1", 100);
        assertEquals(2, result.size());
        List<String> resultList = Arrays.asList(new String[]{"2", "4" });
        assertTrue(resultList.contains(result.get(0).getId()));
        assertTrue(resultList.contains(result.get(1).getId()));

    }

    private OrganizationFamilyServiceBean  createAdHocReportBeanMock() {
        OrganizationFamilyServiceBean bean = mock(OrganizationFamilyServiceBean.class);
        FamilyServiceRemote familyService = mock(FamilyServiceRemote.class);
        OrganizationEntityServiceRemote organizationEntityService = mock(OrganizationEntityServiceRemote.class);
        PAOrganizationServiceRemote paOrganizationService = mock(PAOrganizationServiceRemote.class);   
        when(bean.getFamilyService()).thenReturn(familyService);
        when(bean.getOrganizationEntityService()).thenReturn(organizationEntityService);        
        when(bean.getPaOrganizationService()).thenReturn(paOrganizationService);
        return bean;
    }

    private List<PaOrganizationDTO> createListPaOrganizationDTOList() {
        List<PaOrganizationDTO> result = new ArrayList<PaOrganizationDTO>();

        PaOrganizationDTO dto1 = new PaOrganizationDTO();
        dto1.setId("1");
        dto1.setIdentifier("11");
        result.add(dto1);

        PaOrganizationDTO dto2 = new PaOrganizationDTO();
        dto2.setId("2");
        dto2.setIdentifier("22");
        result.add(dto2);

        PaOrganizationDTO dto3 = new PaOrganizationDTO();
        dto3.setId("3");
        dto3.setIdentifier("33");
        result.add(dto3);

        PaOrganizationDTO dto4 = new PaOrganizationDTO();
        dto4.setId("4");
        dto4.setIdentifier("44");
        result.add(dto4);

        PaOrganizationDTO dto5 = new PaOrganizationDTO();
        dto5.setId("5");
        dto5.setIdentifier("55");
        result.add(dto5);

        return result;
    }

    private List<OrganizationDTO> createOrganizationDTOList() {
        List<OrganizationDTO> result = new ArrayList<OrganizationDTO>();

        OrganizationDTO dto1 = new OrganizationDTO();
        Ii ii1 = new Ii();
        ii1.setExtension("22");
        dto1.setIdentifier(ii1);
        result.add(dto1);

        OrganizationDTO dto2 = new OrganizationDTO();
        Ii ii2 = new Ii();
        ii2.setExtension("44");
        dto2.setIdentifier(ii2);
        result.add(dto2);

        OrganizationDTO dto3 = new OrganizationDTO();
        Ii ii3 = new Ii();
        ii3.setExtension("454545");
        dto3.setIdentifier(ii3);
        result.add(dto3);

        return result;

    }

}
