/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.service.PAException;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * @author mshestopalov
 *
 */
public class PDQXmlGeneratorServiceTest extends CTGovXmlGeneratorServiceTest {

    private final PDQXmlGeneratorServiceBean pdqBean = new PDQXmlGeneratorServiceBean();

    @Override
    public PDQXmlGeneratorServiceBean getBean() {
        return pdqBean;
    }

    @Override
    @Before
    public void setup() throws Exception {
        super.setup();
    }

    @Test
    public void testXsdValidation() throws Exception {
        when(studyIndIdeSvc.getByStudyProtocol(any(Ii.class))).thenReturn(null);

        List<StudySiteDTO> ssList = new ArrayList<StudySiteDTO>();
        ssList.add(studySiteDto);
        when(studySiteSvc.getByStudyProtocol(any(Ii.class),
                argThat(new StudySiteWrongFCMatcher()))).thenReturn(new ArrayList<StudySiteDTO>());
        when(studySiteSvc.getByStudyProtocol(any(Ii.class), argThat(new StudySiteMatcher()))).thenReturn(ssList);

        interventionalSPDto.setBlindedRoleCode(null);

        when(stratumGroupSvc.getByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<StratumGroupDTO>());

        when(studyContactSvc.getByStudyProtocol(any(Ii.class),
                argThat(new CentralContactMatcher()))).thenReturn(new ArrayList<StudyContactDTO>());

        String pdqXml = pdqBean.generatePdqXml(spId);
        DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schemaXSD = schemaFactory
            .newSchema(new File("../public/CTRPProtocolExport.xsd"));
        Validator validator = schemaXSD.newValidator();

        Document document = parser.parse(new InputSource(new StringReader(pdqXml)));
        validator.validate(new DOMSource(document));
    }

    class CentralContactMatcher extends ArgumentMatcher<StudyContactDTO> {
        /* (non-Javadoc)
         * @see org.mockito.ArgumentMatcher#matches(java.lang.Object)
         */
        @Override
        public boolean matches(Object argument) {
            if (argument instanceof StudyContactDTO) {
                StudyContactDTO sc = (StudyContactDTO) argument;
                return sc.getRoleCode().getCode().equals(StudyContactRoleCode.CENTRAL_CONTACT.getCode());
            }
            return false;
        }
    }

    class StudySiteMatcher extends ArgumentMatcher<StudySiteDTO> {
        /* (non-Javadoc)
         * @see org.mockito.ArgumentMatcher#matches(java.lang.Object)
         */
        @Override
        public boolean matches(Object o) {
            if (o instanceof StudySiteDTO) {
                StudySiteDTO ss = (StudySiteDTO)o;
                String code = ss.getFunctionalCode().getCode();
                return code.equals(StudySiteFunctionalCode.FUNDING_SOURCE.getCode())
                    || code.equals(StudySiteFunctionalCode.LEAD_ORGANIZATION.getCode());
            }
            return false;
        }
    }

    class StudySiteWrongFCMatcher extends ArgumentMatcher<StudySiteDTO> {
        /* (non-Javadoc)
         * @see org.mockito.ArgumentMatcher#matches(java.lang.Object)
         */
        @Override
        public boolean matches(Object o) {
            if (o instanceof StudySiteDTO) {
                StudySiteDTO ss = (StudySiteDTO)o;
                return !ss.getFunctionalCode().getCode().equals(StudySiteFunctionalCode.FUNDING_SOURCE.getCode());
            }
            return false;
        }
    }

    @Override
    @Test
    public void testCrsNull() throws PAException {
        //NOOP - can't be null any more
    }

    @Override
    @Test
    public void testExpAccIndFalse() throws PAException {
        studySiteIndIdeDto.setExpandedAccessIndicator(BlConverter.convertToBl(false));
        assertTrue(getBean().generateCTGovXml(spId).contains("<expanded_access_status>No longer available</expanded_access_status>"));
    }

    @Override
    @Test
    public void testRbApNull() throws PAException {
        //NOOP - irb
    }

    @Override
    @Test
    public void testRbAppFalse() throws PAException {
        //NOOP - irb
    }

    @Override
    @Test
    public void testRBStatSub() throws PAException {
        //NOOP - irb
    }

    @Override
    @Test
    public void testSIndGetNull() throws PAException {
        when(studyIndIdeSvc.getByStudyProtocol(any(Ii.class))).thenReturn(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<trial_ind_ide>"));
    }

    @Override
    @Test
    public void testOrgTelEmpty() throws PAException {
        orgDto.setTelecomAddress(new DSet<Tel>());
        assertTrue(getBean().generateCTGovXml(spId).contains("<lead_org>\n"
                + "<name>some org name</name>\n"
                + "<po_id>1</po_id>\n"
                + "<ctep_id>ctep org id</ctep_id>\n"
                + "<address>\n"
                + "<street>street</street>\n"
                + "<city>city</city>\n"
                + "<state>MD</state>\n"
                + "<zip>20000</zip>\n"
                + "<country>United States</country>\n"
                + "</address>\n"
                + "</lead_org>"));
    }

    @Override
    @Test
    public void testOrgNameNull() throws PAException {
        orgDto.setName(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<lead_sponsor>"));
    }

    @Override
    @Test
    public void testOrgBySSNull() throws PAException {
        when(studySiteSvc.getByStudyProtocol(any(Ii.class), any(StudySiteDTO.class))).thenReturn(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<collaborator>"));
    }

    @Override
    @Test
    public void testOrgBySsEmpty() throws PAException {
        when(studySiteSvc.getByStudyProtocol(any(Ii.class), any(StudySiteDTO.class)))
           .thenReturn(new ArrayList<StudySiteDTO>());
        assertFalse(getBean().generateCTGovXml(spId).contains("<collaborator>"));
    }

    @Test
    public void testPoAndCtepIds() throws PAException {
        assertTrue(getBean().generateCTGovXml(spId).contains("<facility>\n<name>some org name</name>\n"
                + "<po_id>1</po_id>\n<ctep_id>ctep org id</ctep_id>"));

        assertTrue(getBean().generateCTGovXml(spId).contains("<contact>\n<first_name>first name</first_name>\n"
                + "<last_name>last Name</last_name>\n<po_id>1</po_id>\n<ctep_id>ctep</ctep_id>"));

        assertTrue(getBean().generateCTGovXml(spId).contains("<investigator>\n<first_name>first name</first_name>\n"
                + "<last_name>last Name</last_name>\n"
                + "<po_id>1</po_id>\n<ctep_id>ctep</ctep_id>"));

        assertTrue(getBean().generateCTGovXml(spId).contains("<overall_official>\n<first_name>first name</first_name>\n"
                + "<last_name>last Name</last_name>\n"
                + "<po_id>1</po_id>\n<ctep_id>ctep</ctep_id>"));

        assertTrue(getBean().generateCTGovXml(spId).contains("<affiliation>\n<name>some org name</name>\n"
                + "<po_id>1</po_id>\n<ctep_id>ctep org id</ctep_id>"));
    }

}
