/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.AbstractStudyProtocol;
import gov.nih.nci.pa.domain.InterventionalStudyProtocolTest;
import gov.nih.nci.pa.domain.ObservationalStudyProtocolTest;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.iso.dto.AbstractStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * test class for studyprotocol converter.
 * @author NAmiruddin
 *
 */
public class BaseStudyProtocolConverterTest  {

    /**
     *
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
    }
    
    @Test
    public void initObj() throws Exception {
        AbstractStudyProtocolConverter aSpC = new AbstractStudyProtocolConverter();
        CSMUserUtil cSm = AbstractStudyProtocolConverter.getCsmUserUtil();
    }

    /**
     *
     */
    @Test
    public void convertFromDomainToDTOTest() {
        Session session  = HibernateUtil.getCurrentSession();
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj(new StudyProtocol());
        session.save(sp);
        assertNotNull(sp.getId());
        AbstractStudyProtocolDTO spDTO = AbstractStudyProtocolConverter.convertFromDomainToDTO(sp, new StudyProtocolDTO());
        assertStudyProtocol(sp , spDTO);

        sp.setPrimaryCompletionDate(null);
        spDTO = AbstractStudyProtocolConverter.convertFromDomainToDTO(sp, new StudyProtocolDTO());
        assertNotNull(spDTO.getPrimaryCompletionDate());
        assertEquals(NullFlavor.UNK, spDTO.getPrimaryCompletionDate().getNullFlavor());
    }
      
    @Test
    public void convertFromDomainToDTOTest1() {
        Session session  = HibernateUtil.getCurrentSession();
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj(new StudyProtocol());
        session.save(sp);
        assertNotNull(sp.getId());
        AbstractStudyProtocolDTO spDTO = AbstractStudyProtocolConverter.convertFromDomainToDTO(sp, new StudyProtocolDTO());
        assertStudyProtocol(sp , spDTO);

        sp.setPrimaryCompletionDate(null);
        spDTO = AbstractStudyProtocolConverter.convertFromDomainToDTO(sp, new StudyProtocolDTO());
        assertNotNull(spDTO.getPrimaryCompletionDate());
        assertEquals(NullFlavor.UNK, spDTO.getPrimaryCompletionDate().getNullFlavor()); 
    }
    
    @Test
    public void convertFromDomainToDTOTest2() {
        Session session  = HibernateUtil.getCurrentSession();
        StudyProtocol sp = InterventionalStudyProtocolTest.createInterventionalStudyProtocolObj();
        sp.setPhaseCode(PhaseCode.I);
        sp.setPhaseAdditionalQualifierCode(PhaseAdditionalQualifierCode.PILOT);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.BASIC_SCIENCE);
        sp.setPrimaryPurposeAdditionalQualifierCode(PrimaryPurposeAdditionalQualifierCode.ANCILLARY);
        User user = new User();
        user.setLoginName("Abstractor: " + new Date());
        user.setFirstName("Joe");
        user.setLastName("Smith");
        user.setUpdateDate(new Date());
        session.save(user);
        sp.setUserLastCreated(user);
        session.save(sp);
        assertNotNull(sp.getId());
        AbstractStudyProtocolDTO spDTO = AbstractStudyProtocolConverter.convertFromDomainToDTO(sp, new StudyProtocolDTO());
        assertStudyProtocol(sp , spDTO);
    }
    
    @Test
    public void convertFromDomainToDTOTest3() {
        Session session  = HibernateUtil.getCurrentSession();
        
        StudyProtocol sp = ObservationalStudyProtocolTest.createObservationalStudyProtocolObj();
        sp.setPhaseCode(PhaseCode.I);
        sp.setPhaseAdditionalQualifierCode(PhaseAdditionalQualifierCode.PILOT);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.BASIC_SCIENCE);
        sp.setPrimaryPurposeAdditionalQualifierCode(PrimaryPurposeAdditionalQualifierCode.ANCILLARY);
        session.save(sp);
        assertNotNull(sp.getId());
        AbstractStudyProtocolDTO spDTO = AbstractStudyProtocolConverter.convertFromDomainToDTO(sp, new StudyProtocolDTO());
        assertStudyProtocol(sp , spDTO);    
    }

    /**
     * @throws PAException 
     *
     */
    @Test
    public void convertFromDtoToDomainTest() throws PAException {
        Session session  = HibernateUtil.getCurrentSession();
        StudyProtocol create = StudyProtocolTest.createStudyProtocolObj(new StudyProtocol());
        create.setPhaseCode(PhaseCode.I);
        create.setPhaseAdditionalQualifierCode(PhaseAdditionalQualifierCode.PILOT);
        create.setStartDate(new Timestamp(new Date().getTime()));
        create.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        create.setProgramCodeText("program code");
        User user = new User();
        user.setLoginName("Abstractor: " + new Date());
        user.setFirstName("Joe");
        user.setLastName("Smith");
        user.setUpdateDate(new Date());
        session.save(user);
        create.setUserLastCreated(user);
        session.save(create);
        assertNotNull(create.getId());

        //convert to DTO
        AbstractStudyProtocolDTO spDTO = AbstractStudyProtocolConverter.convertFromDomainToDTO(create, new StudyProtocolDTO());
        AbstractStudyProtocolConverter.setCsmUserUtil(new MockCSMUserService());
        AbstractStudyProtocol sp = AbstractStudyProtocolConverter.convertFromDTOToDomain(spDTO, new StudyProtocol());
        assertStudyProtocol(sp , spDTO);

        Ts unknownTs = new Ts();
        unknownTs.setNullFlavor(NullFlavor.UNK);
        spDTO.setPrimaryCompletionDate(unknownTs);
        sp = AbstractStudyProtocolConverter.convertFromDTOToDomain(spDTO, sp);
        assertNull(sp.getPrimaryCompletionDate());
    }

    @Test
    public void convertFromDtoToDomainTest1() throws PAException {
        Session session  = HibernateUtil.getCurrentSession();
        StudyProtocol create = StudyProtocolTest.createStudyProtocolObj(new StudyProtocol());
        session.save(create);
        assertNotNull(create.getId());
        //convert to DTO
        AbstractStudyProtocolDTO spDTO = AbstractStudyProtocolConverter.convertFromDomainToDTO(create, new StudyProtocolDTO());
        AbstractStudyProtocolConverter.setCsmUserUtil(new MockCSMUserService());
        AbstractStudyProtocol sp = AbstractStudyProtocolConverter.convertFromDTOToDomain(spDTO, new StudyProtocol());
        assertStudyProtocol(sp , spDTO);

        Ts unknownTs = new Ts();
        unknownTs.setNullFlavor(NullFlavor.UNK);
        spDTO.setPrimaryCompletionDate(unknownTs);
        sp = AbstractStudyProtocolConverter.convertFromDTOToDomain(spDTO, new StudyProtocol());
        assertNull(sp.getPrimaryCompletionDate());
    }

    /**
     *
     * @param sp sp
     * @param spDTO spDTO
     */
    public void assertStudyProtocol(AbstractStudyProtocol sp, AbstractStudyProtocolDTO spDTO) {
        assertEquals(sp.getDataMonitoringCommitteeAppointedIndicator(),
                spDTO.getDataMonitoringCommitteeAppointedIndicator().getValue());
        assertEquals(sp.getDelayedpostingIndicator(), spDTO.getDelayedpostingIndicator().getValue());
        assertEquals(sp.getFdaRegulatedIndicator(), spDTO.getFdaRegulatedIndicator().getValue());
        assertEquals(sp.getOfficialTitle() , spDTO.getOfficialTitle().getValue());
        assertEquals(sp.getPhaseCode().getCode(), spDTO.getPhaseCode().getCode());
        assertEquals(sp.getPhaseAdditionalQualifierCode().getCode(), spDTO.getPhaseAdditionalQualifierCode().getCode());
        assertEquals(sp.getPrimaryCompletionDate(), TsConverter.convertToTimestamp(spDTO.getPrimaryCompletionDate()));
        assertEquals(sp.getPrimaryCompletionDateTypeCode().getCode(),
                spDTO.getPrimaryCompletionDateTypeCode().getCode());
        assertEquals(sp.getPrimaryPurposeCode().getCode(), spDTO.getPrimaryPurposeCode().getCode());
        assertEquals(sp.getPrimaryPurposeAdditionalQualifierCode().getCode(),
                spDTO.getPrimaryPurposeAdditionalQualifierCode().getCode());
        assertEquals(sp.getPrimaryPurposeOtherText(), spDTO.getPrimaryPurposeOtherText().getValue());       
        assertEquals(sp.getSection801Indicator(), spDTO.getSection801Indicator().getValue());
        assertEquals(sp.getStartDate() , TsConverter.convertToTimestamp(spDTO.getStartDate()));
        assertEquals(sp.getStartDateTypeCode().getCode() , spDTO.getStartDateTypeCode().getCode());
    }
}
