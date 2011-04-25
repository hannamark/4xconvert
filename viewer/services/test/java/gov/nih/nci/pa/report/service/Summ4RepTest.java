/*
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

package gov.nih.nci.pa.report.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.report.dto.criteria.Summ4RepCriteriaDto;
import gov.nih.nci.pa.report.dto.result.Summ4RepResultDto;
import gov.nih.nci.pa.report.util.MockPoServiceLocator;
import gov.nih.nci.pa.report.util.TestSchema;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PoRegistry;

import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class Summ4RepTest
    extends AbstractReportBeanTest<Summ4RepCriteriaDto, Summ4RepResultDto, Summ4ReportBean> {

    @Before
    public void setUp() throws Exception {
        bean = new Summ4RepBeanExtenderForTest();
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
        StudyProtocolServiceLocal spSvcLocal = mock(StudyProtocolServiceLocal.class);
        bean.setStudyProtocolService(spSvcLocal);
        StudyProtocolDTO spDto = new StudyProtocolDTO();
        DSet<Cd> dSetCd = new DSet<Cd>();
        dSetCd.setItem(new HashSet<Cd>());
        Cd cd1 = new Cd();
        cd1.setCode("anatomicSite1");
        Cd cd2 = new Cd();
        cd2.setCode("anatomicSite2");
        dSetCd.getItem().add(cd1);
        dSetCd.getItem().add(cd2);
        spDto.setSummary4AnatomicSites(dSetCd);
        when(spSvcLocal.getStudyProtocol(any(Ii.class))).thenReturn(spDto);
    }

    @Override
    @Test (expected=PAException.class)
    public void criteriaValidateTest() throws Exception {
        Summ4RepCriteriaDto criteria = new Summ4RepCriteriaDto();
        bean.get(criteria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Test
    public void getTest() throws Exception {
        Summ4RepCriteriaDto criteria = new Summ4RepCriteriaDto();
        criteria.setOrgName(StConverter.convertToSt("Duke"));
        Ivl<Ts> wrk = new Ivl<Ts>();
        wrk.setLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/2009")));
        wrk.setHigh(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/2010")));
        criteria.setTimeInterval(wrk);
        List<Summ4RepResultDto> resultList = bean.get(criteria);
        assertEquals(resultList.size(), TestSchema.studySite.size());
        assertEquals(2, resultList.get(0).getAnatomicSiteCodes().getItem().size());
        assertEquals("StudyProtocol", resultList.get(0).getLeadOrgName().getValue());
        assertEquals("StudyProtocol", resultList.get(0).getNciIdentifier().getValue());
        assertEquals("StudyProtocol", resultList.get(0).getNctIdentifier().getValue());
        assertEquals("StudyProtocol", resultList.get(0).getCtepIdentifier().getValue());
    }

    @Test
    public void getAutoComplete() throws Exception  {
        List<String> orgs =  bean.searchPoOrgNames("OrgName", 10);
        assertEquals(5, orgs.size());
    }

    private class Summ4RepBeanExtenderForTest extends Summ4ReportBean {
        /**
         * Overriding the sql string because the postges sql and the hqldb are different enough
         * to cause lots of problems with that huge query in the bean while testing.
         * By using this fake query I am still able to test the rest of the parts of the
         * bean functionality.
         */
        @Override
        protected StringBuffer generateSqlQuery(Summ4RepCriteriaDto criteria) {
            StringBuffer sql = new StringBuffer(
            "select sp.official_title,  "
            + "ss.local_sp_indentifier, "
            + "sp.public_tittle, "
            + "sp.program_code_text, "
            + "null, "
            + "null, "
            + "sp.phase_code, "
            + "sp.primary_purpose_code, "
            + "sp.public_tittle, "
            + "sp.max_target_accrual_num, "
            + "sp.identifier, sp.identifier, "
            + "sp.study_protocol_type,"
            + "sp.public_description, "
            + "sp.identifier, "
            + "sp.study_protocol_type, "
            + "sp.study_protocol_type, "
            + "sp.study_protocol_type, "
            + "sp.study_protocol_type "
            + "from study_protocol sp, study_site ss "
            + "where 'Duke' = :ORG_NAME "
            + "and ss.study_protocol_identifier = sp.identifier "
            + "and :LOW <= now() "
            + "and :HIGH <= now() "
            + "and 'Agent/Device' = :AGENT_DEVICE "
            + "and 'Other Intervention' = :OTHER_INTERVENTION "
            + "and 'Epidemiologic/Other/Outcome' = :EPIDEM_OTHER_OUTCOME "
            + "and 'Ancillary/Correlative' = :ANCILLARY_CORRELATIVE");
            return sql;
        }
    }

}
