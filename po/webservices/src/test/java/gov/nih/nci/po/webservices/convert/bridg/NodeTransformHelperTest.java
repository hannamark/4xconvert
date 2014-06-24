/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The CoppaPO
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This CoppaPO Software License (the License) is between NCI and You. You (or 
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
 * its rights in the CoppaPO Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the CoppaPO Software; (ii) distribute and 
 * have distributed to and by third parties the CoppaPO Software and any 
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
package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.Correlation;
import gov.nih.nci.coppa.po.CorrelationType;
import gov.nih.nci.coppa.po.EntityType;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.po.service.OversightCommitteeServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.ServiceLocator;
import gov.nih.nci.services.CorrelationDto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test NodeTransformHelper
 * @author mshestopalov
 *
 */
public class NodeTransformHelperTest {
    
    private class MyCorrClass extends Correlation {
        
    }
    
    private class MyCorrDtoClass implements CorrelationDto {
        public Ii getDuplicateOf() {
            return null;
        }
        public DSet<Ii> getIdentifier() {
            return null;
        }
        public Cd getStatus() {
            return null;
        }
        public void setDuplicateOf(Ii arg0) {
        }
        public void setIdentifier(DSet<Ii> arg0) {
        }
        public void setStatus(Cd arg0) {
        }
    }

    @Before
    public void setup() {
        ServiceLocator serviceLocator = mock(ServiceLocator.class);
        PoRegistry.getInstance().setServiceLocator(serviceLocator);
        OversightCommitteeServiceLocal oversightCommitteeServiceLocal = mock(OversightCommitteeServiceLocal.class);
        when(serviceLocator.getOversightCommitteeService()).thenReturn(oversightCommitteeServiceLocal);

    }

    @Test
    public void testToDtoCorrelation() throws DtoTransformException {
        assertNull(NodeTransformHelper.toDtoCorrelation(null));
        NodeTransformHelper.toDtoCorrelation(new ClinicalResearchStaffTransformerTest().makeXmlSimple());
        NodeTransformHelper.toDtoCorrelation(new HealthCareFacilityTransformerTest().makeXmlSimple());
        NodeTransformHelper.toDtoCorrelation(new HealthCareProviderTransformerTest().makeXmlSimple());
        NodeTransformHelper.toDtoCorrelation(new IdentifiedOrganizationTransformerTest().makeXmlSimple());
        NodeTransformHelper.toDtoCorrelation(new IdentifiedPersonTransformerTest().makeXmlSimple());
        NodeTransformHelper.toDtoCorrelation(new OrganizationalContactTransformerTest().makeXmlSimple());
        NodeTransformHelper.toDtoCorrelation(new OversightCommitteeTransformerTest().makeXmlSimple());
        NodeTransformHelper.toDtoCorrelation(new ResearchOrganizationTransformerTest().makeXmlSimple());
        NodeTransformHelper.toDtoCorrelation(new MyCorrClass());
    }
    
    @Test
    public void testTypeToDtoCorrelations() throws DtoTransformException {
        NodeTransformHelper.typeToDtoCorrelation(null);
        CorrelationType corrType = new CorrelationType();
        assertNull(NodeTransformHelper.typeToDtoCorrelation(corrType));
        corrType.getContent().add(new ClinicalResearchStaffTransformerTest().makeXmlSimple());
        corrType.getContent().add(new HealthCareFacilityTransformerTest().makeXmlSimple());
        corrType.getContent().add(new HealthCareProviderTransformerTest().makeXmlSimple());
        corrType.getContent().add(new IdentifiedOrganizationTransformerTest().makeXmlSimple());
        corrType.getContent().add(new IdentifiedPersonTransformerTest().makeXmlSimple());
        corrType.getContent().add(new OrganizationalContactTransformerTest().makeXmlSimple());
        corrType.getContent().add(new OversightCommitteeTransformerTest().makeXmlSimple());
        corrType.getContent().add(new ResearchOrganizationTransformerTest().makeXmlSimple());
        corrType.getContent().add(new MyCorrClass());
        
        assertNotNull(NodeTransformHelper.typeToDtoCorrelation(corrType));
        assertNotNull(NodeTransformHelper.toDtoCorrelations(corrType));
        assertNull(NodeTransformHelper.toDtoCorrelations(null));
        
        
        
    }
    
    @Test
    public void testTypeToDtoCorrelation() throws DtoTransformException {
        EntityType entType = new EntityType();
        entType.getContent().add(new PersonTransformerTest().makeXmlSimple());
        entType.getContent().add(new OrganizationTransformerTest().makeXmlSimple());
            
        NodeTransformHelper.toDtoEntity(entType);
    }
    
    @Test
    public void testToXmlCorrelationDTO() throws DtoTransformException {
        NodeTransformHelper.toXmlCorrelation(null);
        NodeTransformHelper.toXmlCorrelation(new ClinicalResearchStaffTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelation(new HealthCareFacilityTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelation(new HealthCareProviderTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelation(new IdentifiedOrganizationTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelation(new IdentifiedPersonTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelation(new OrganizationalContactTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelation(new OversightCommitteeTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelation(new ResearchOrganizationTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelation(new MyCorrDtoClass());
    }
    
    @Test
    public void testToXmlCorrelationType() throws DtoTransformException {
        NodeTransformHelper.toXmlCorrelationType(null);
        NodeTransformHelper.toXmlCorrelationType(new ClinicalResearchStaffTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelationType(new HealthCareFacilityTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelationType(new HealthCareProviderTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelationType(new IdentifiedOrganizationTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelationType(new IdentifiedPersonTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelationType(new OrganizationalContactTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelationType(new OversightCommitteeTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelationType(new ResearchOrganizationTransformerTest().makeDtoSimple());
        NodeTransformHelper.toXmlCorrelationType(new MyCorrDtoClass());
    }
    
    @Test
    public void testListToXmlCorrelationDTO() throws DtoTransformException {
        CorrelationDto[] corrList = new CorrelationDto[10];
        corrList[0] = new ClinicalResearchStaffTransformerTest().makeDtoSimple();
        corrList[1] = new HealthCareFacilityTransformerTest().makeDtoSimple();
        corrList[2] = new HealthCareProviderTransformerTest().makeDtoSimple();
        corrList[3] = new IdentifiedOrganizationTransformerTest().makeDtoSimple();
        corrList[4] = new IdentifiedPersonTransformerTest().makeDtoSimple();
        corrList[5] = new OrganizationalContactTransformerTest().makeDtoSimple();
        corrList[6] = new OversightCommitteeTransformerTest().makeDtoSimple();
        corrList[7] = new ResearchOrganizationTransformerTest().makeDtoSimple();
        corrList[8] = new MyCorrDtoClass();
        corrList[9] = null;
        assertEquals(8, NodeTransformHelper.toXmlCorrelations(corrList).getContent().size());
        assertEquals(NodeTransformHelper.toXmlCorrelations(null), 
                NodeTransformHelper.toXmlCorrelations(new CorrelationDto[0]));
        
        
    }
    
    @Test
    public void testListToDtoCorrelationDTO() throws DtoTransformException {
        List<Correlation> corrList = new ArrayList<Correlation>();
        corrList.add(new ClinicalResearchStaffTransformerTest().makeXmlSimple());
        corrList.add(new HealthCareFacilityTransformerTest().makeXmlSimple());
        corrList.add(new HealthCareProviderTransformerTest().makeXmlSimple());
        corrList.add(new IdentifiedOrganizationTransformerTest().makeXmlSimple());
        corrList.add(new IdentifiedPersonTransformerTest().makeXmlSimple());
        corrList.add(new OrganizationalContactTransformerTest().makeXmlSimple());
        corrList.add(new OversightCommitteeTransformerTest().makeXmlSimple());
        corrList.add(new ResearchOrganizationTransformerTest().makeXmlSimple());
        corrList.add(new MyCorrClass());
        corrList.add(null);
        CorrelationType corrType = new CorrelationType();
        corrType.getContent().addAll(corrList);
        NodeTransformHelper.toDtoCorrelations(corrType);
    }

}
