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
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.AmendmentReasonCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.InterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.MockPAServiceUtils;
import gov.nih.nci.pa.service.util.MockRegistryUserServiceBean;
import gov.nih.nci.pa.service.util.RegistryUserServiceBean;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.AnatomicSiteComparator;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author Naveen Amiruddin
 * @since 08/26/2008
 */
public class StudyProtocolServiceBeanTest extends AbstractHibernateTestCase {

    @Rule public ExpectedException thrown = ExpectedException.none();
    private final StudyProtocolBeanLocal bean = new StudyProtocolBeanLocal();
    private final StudyProtocolServiceLocal remoteEjb = bean;
    
    private final RegistryUserServiceBean registryService = mock(MockRegistryUserServiceBean.class);
    private final MailManagerServiceLocal mailManagerServiceLocal = mock(MailManagerServiceLocal.class);
        
    
    @Before
    public void setUp() throws Exception {   	
    	
        CSMUserService.setInstance(new MockCSMUserService());
        UsernameHolder.setUser(TestSchema.getUser().getLoginName());
        AnatomicSite as = new AnatomicSite();
        as.setCode("Lung");
        as.setCodingSystem("Summary 4 Anatomic Sites");
        TestSchema.addUpdObject(as);
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        LookUpTableServiceRemote lookupSvc = mock(LookUpTableServiceRemote.class);
        when(lookupSvc.getLookupEntityByCode(any(Class.class), any(String.class))).thenReturn(as);
        when(paRegSvcLoc.getLookUpTableService()).thenReturn(lookupSvc);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        
        bean.setRegistryUserService(registryService);
        bean.setMailManagerService(mailManagerServiceLocal);
        
    }

    @Test(expected=PAException.class)
    public void nullParameter1() throws Exception {
        remoteEjb.createInterventionalStudyProtocol(null);
    }

    @Test(expected=PAException.class)
    public void nullParameter2() throws Exception {
        remoteEjb.getStudyProtocol(null);
    }

    @Test(expected=PAException.class)
    public void nullParameter3() throws Exception {
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS , 0);
        remoteEjb.search(null,limit);
    }

    @Test(expected=PAException.class)
    public void nullParameter4() throws Exception {
        remoteEjb.updateStudyProtocol(null);
    }

    @Test(expected=PAException.class)
    public void nullParameter6() throws Exception {
        remoteEjb.getInterventionalStudyProtocol(null);
    }

    @Test(expected=PAException.class)
    public void nullParameter7() throws Exception {
        remoteEjb.updateInterventionalStudyProtocol(null, null);
    }

    @Test(expected=PAException.class)
    public void nullParameter8() throws Exception {
        remoteEjb.getObservationalStudyProtocol(null);
    }

    @Test(expected=PAException.class)
    public void nullParameter9() throws Exception {
        remoteEjb.updateObservationalStudyProtocol(null);
    }

    @Test(expected=PAException.class)
    public void nullParameter10() throws Exception {
        ObservationalStudyProtocolDTO dto = new ObservationalStudyProtocolDTO();
        dto.setIdentifier(IiConverter.convertToIi("111"));
        remoteEjb.createObservationalStudyProtocol(dto);
    }

    @Test(expected=PAException.class)
    public void nullParameter11() throws Exception {
        remoteEjb.createObservationalStudyProtocol(null);
    }

    @Test(expected=PAException.class)
    public void nullExtension() throws Exception {
        InterventionalStudyProtocolDTO ispDTO = new InterventionalStudyProtocolDTO();
        Ii ii = new Ii();
        ii.setExtension("xxx");
        ispDTO.setIdentifier(ii);
        remoteEjb.createInterventionalStudyProtocol(ispDTO);
    }

    @Test(expected=PAException.class)
    public void businessRulesException1() throws Exception {
        InterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setStartDateTypeCode(null);
        remoteEjb.createInterventionalStudyProtocol(ispDTO);
    }

    @Test(expected=PAException.class)
    public void businessRulesException2() throws Exception {
        InterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setPrimaryCompletionDateTypeCode(null);
        remoteEjb.createInterventionalStudyProtocol(ispDTO);
    }

    @Test(expected = PAException.class)
    public void businessRulesException3() throws Exception {
        InterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setStartDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("01/01/9999")));
        remoteEjb.createInterventionalStudyProtocol(ispDTO);
    }

    @Test(expected = PAException.class)
    public void businessRulesException4() throws Exception {
        InterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setPrimaryCompletionDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("01/01/9999")));
        remoteEjb.createInterventionalStudyProtocol(ispDTO);
    }

    @Test(expected = PAException.class)
    public void businessRulesException5() throws Exception {
        InterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setStartDateTypeCode(CdConverter.convertStringToCd(ActualAnticipatedTypeCode.ANTICIPATED.getCode()));
        ispDTO.setStartDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("01/01/2000")));
        remoteEjb.createInterventionalStudyProtocol(ispDTO);
    }

    @Test(expected = PAException.class)
    public void businessRulesException6() throws Exception {
        InterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setPrimaryCompletionDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("01/01/2000")));
        remoteEjb.createInterventionalStudyProtocol(ispDTO);
    }

    @Test(expected = PAException.class)
    public void businessRulesException7() throws Exception {
        InterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setStartDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("01/01/2001")));
        ispDTO.setPrimaryCompletionDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("01/01/2000")));
        remoteEjb.createInterventionalStudyProtocol(ispDTO);
    }

    @Test
    public void businessRulesExceptionForUpdate() throws Exception {
        InterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        Ii ii = remoteEjb.createInterventionalStudyProtocol(ispDTO);
        InterventionalStudyProtocolDTO saved =  remoteEjb.getInterventionalStudyProtocol(ii);
        saved.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(Boolean.FALSE));
        saved.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.FALSE));
        remoteEjb.updateInterventionalStudyProtocol(saved, null);
        saved =  remoteEjb.getInterventionalStudyProtocol(ii);
        saved.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(Boolean.TRUE));
        StudyIndldeDTO sIndDto = new StudyIndldeDTO();
        sIndDto.setExpandedAccessIndicator(BlConverter.convertToBl(true));
        List<StudyIndldeDTO> sIndDtoList = new ArrayList<StudyIndldeDTO>();
        sIndDtoList.add(sIndDto);
        StudyIndldeServiceLocal sIndSvc = mock(StudyIndldeServiceLocal.class);
        when(sIndSvc.getByStudyProtocol(any(Ii.class))).thenReturn(sIndDtoList);
        bean.setStudyIndldeService(sIndSvc);
        remoteEjb.updateInterventionalStudyProtocol(saved, null);
        sIndDto = new StudyIndldeDTO();
        sIndDto.setExemptIndicator(BlConverter.convertToBl(Boolean.FALSE));
        sIndDtoList.add(sIndDto);
        sIndSvc = mock(StudyIndldeServiceLocal.class);
        when(sIndSvc.getByStudyProtocol(any(Ii.class))).thenReturn(sIndDtoList);
        bean.setStudyIndldeService(sIndSvc);
        try {
            remoteEjb.updateInterventionalStudyProtocol(saved, null);
            fail("Unable to set FDARegulatedIndicator to 'No', Please remove IND/IDEs and try again");
        } catch (PAException e) {
            assertEquals("Unable to set FDARegulatedIndicator to 'No',  Please remove IND/IDEs and try again",
                    e.getMessage());
        }
        remoteEjb.updateInterventionalStudyProtocol(saved, "DesignDetails");
    }

    @Test
    public void createInterventionalStudyProtocol() throws Exception {
        InterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        Ii ii = remoteEjb.createInterventionalStudyProtocol(ispDTO);
        assertNotNull(ii.getExtension());
    }
    
	@Test
	public void changeOwnershipSuccess() throws Exception {

		RegistryUser trialOwner = createRegistryUser();
		Ii ii = createProtocolID();
		DSet<Tel> owners = createDSetTelEmail();

		when(registryService.getAllTrialOwners(any(Long.class))).thenReturn(
				new HashSet<RegistryUser>(Arrays.asList(trialOwner)));
		when(
				registryService
						.getLoginNamesByEmailAddress("username@nci.nih.gov"))
				.thenReturn(Arrays.asList(trialOwner));
		Collection<String> emails = remoteEjb.changeOwnership(ii, owners);
		assertTrue(emails.isEmpty());
		verify(registryService, times(1)).removeOwnership(Long.MIN_VALUE,
				IiConverter.convertToLong(ii));
		verify(registryService, times(1)).assignOwnership(Long.MIN_VALUE,
				IiConverter.convertToLong(ii));

	}

	/**
	 * @return
	 * @throws URISyntaxException
	 */
	private DSet<Tel> createDSetTelEmail() throws URISyntaxException {
		DSet<Tel> owners = new DSet<Tel>();
		owners.setItem(new HashSet<Tel>());
		Tel tel = new Tel();
		tel.setValue(new URI("mailto:username@nci.nih.gov"));
		owners.getItem().add(tel);
		return owners;
	}

	/**
	 * @return
	 * @throws PAException 
	 */
	private Ii createProtocolID() throws PAException {
        createStudyProtocols(1, PAConstants.DCP_ORG_NAME, "DCP-1", false);
        Ii ii = new Ii();
        ii.setRoot(IiConverter.DCP_STUDY_PROTOCOL_ROOT);
        ii.setExtension("DCP-1");
        StudyProtocolDTO spDTO = remoteEjb.getStudyProtocol(ii);    
        ii = spDTO.getIdentifier();
		return ii;
	}

	/**
	 * @return
	 */
	private RegistryUser createRegistryUser() {
		RegistryUser trialOwner = new RegistryUser();
		trialOwner.setId(Long.MIN_VALUE);
		return trialOwner;
	}

	@Test
	public void changeOwnershipFailure() throws Exception {	    
        Ii ii = createProtocolID();
        
		DSet<Tel> owners = createDSetTelEmail();

		when(registryService.getAllTrialOwners(any(Long.class))).thenReturn(
				new HashSet<RegistryUser>());
		when(
				registryService
						.getLoginNamesByEmailAddress("username@nci.nih.gov"))
				.thenReturn(new ArrayList<RegistryUser>());
		
		Collection<String> emails = remoteEjb.changeOwnership(ii, owners);
		assertEquals(1, emails.size());
		assertEquals("username@nci.nih.gov", emails.iterator().next());
		verify(registryService, never()).assignOwnership(Long.MIN_VALUE,
				IiConverter.convertToLong(ii));
		verify(mailManagerServiceLocal, times(1)).sendUnidentifiableOwnerEmail(eq(IiConverter.convertToLong(ii)), 
		        eq(Arrays.asList("username@nci.nih.gov")));

	}

	@Test
	public void changeOwnershipFailureBadEmail() throws Exception {
        Ii ii = createProtocolID();

        DSet<Tel> owners = new DSet<Tel>();
        owners.setItem(new HashSet<Tel>());
        Tel tel = new Tel();
        tel.setValue(new URI("mailto:bademail"));
        owners.getItem().add(tel);

        when(registryService.getAllTrialOwners(any(Long.class))).thenReturn(
                new HashSet<RegistryUser>());

        Collection<String> emails = remoteEjb.changeOwnership(ii, owners);
        assertEquals(1, emails.size());
        assertEquals("bademail", emails.iterator().next());

        verify(registryService, never()).getLoginNamesByEmailAddress(
                anyString());
        verify(registryService, never()).assignOwnership(Long.MIN_VALUE,
                IiConverter.convertToLong(ii));
        verify(mailManagerServiceLocal, times(1)).sendUnidentifiableOwnerEmail(eq(IiConverter.convertToLong(ii)), 
                eq(Arrays.asList("bademail")));        

	}
    
    

    @Test
    public void deleteStudyProtocol() throws Exception {
        InterventionalStudyProtocol sp = new InterventionalStudyProtocol();
        sp = (InterventionalStudyProtocol) TestSchema.createStudyProtocolObj(sp);
        sp = TestSchema.createInterventionalStudyProtocolObj(sp);
        remoteEjb.deleteStudyProtocol(IiConverter.convertToStudyProtocolIi(sp.getId()));
    }

    @Test
    public void search() throws Exception {
        createStudyProtocols(6, null, null, false);

        StudyProtocolDTO criteria = new StudyProtocolDTO();
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);

        List<StudyProtocolDTO> results = remoteEjb.search(criteria, limit);
        assertEquals(6, results.size());

        criteria.setOfficialTitle(StConverter.convertToSt("Cancer for kids"));
        criteria.setStatusCode(CdConverter.convertToCd(ActStatusCode.ACTIVE));
        assertEquals(6, results.size());

        limit = new LimitOffset(3, 0);
        results = remoteEjb.search(criteria, limit);
        assertEquals(3, results.size());


        Set<Ii> secondaryIdentifiers =  new HashSet<Ii>();
        Ii spSecId = new Ii();
        spSecId.setExtension("NCI-2010-00001");
        spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        secondaryIdentifiers.add(spSecId);
        criteria.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(secondaryIdentifiers));

        results = remoteEjb.search(criteria, limit);
        assertEquals(1, results.size());

        Ii otherId = new Ii();
        otherId.setExtension("OTHER-1");
        otherId.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
        secondaryIdentifiers.add(otherId);
        criteria.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(secondaryIdentifiers));

        results = remoteEjb.search(criteria, limit);
        assertEquals(1, results.size());
    }


    @Test
    public void getAbstractedCollaborativeTrials() throws Exception {
        createStudyProtocols(1, PAConstants.DCP_ORG_NAME, null, true);
        createStudyProtocols(1, PAConstants.DCP_ORG_NAME, null, false);
        List<StudyProtocolDTO> results = remoteEjb.getAbstractedCollaborativeTrials();
        assertEquals(1, results.size());

        createStudyProtocols(1, PAConstants.CTEP_ORG_NAME, null, true);
        createStudyProtocols(1, PAConstants.CTEP_ORG_NAME, null, false);
        results = remoteEjb.getAbstractedCollaborativeTrials();
        assertEquals(2, results.size());

        createStudyProtocols(1, null, null, true);
        createStudyProtocols(1, null, null, false);
        results = remoteEjb.getAbstractedCollaborativeTrials();
        assertEquals(2, results.size());
    }

    @Test
    public void getInterventionalStudyProtocol() throws Exception {
        InterventionalStudyProtocolDTO create =
                StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        Ii ii = remoteEjb.createInterventionalStudyProtocol(create);
        assertNotNull(ii.getExtension());
        InterventionalStudyProtocolDTO saved =  remoteEjb.getInterventionalStudyProtocol(ii);
        assertNotNull(saved);
        assertEquals(create.getAccrualReportingMethodCode().getCode(), saved.getAccrualReportingMethodCode().getCode());
        assertEquals(create.getAcronym().getValue(),saved.getAcronym().getValue());
        assertEquals(create.getAllocationCode().getCode(),saved.getAllocationCode().getCode());
        assertEquals(create.getDelayedpostingIndicator().getValue(),saved.getDelayedpostingIndicator().getValue());
        assertEquals(create.getExpandedAccessIndicator().getValue(),saved.getExpandedAccessIndicator().getValue());
        assertEquals(create.getFdaRegulatedIndicator().getValue(),saved.getFdaRegulatedIndicator().getValue());
        assertEquals(create.getOfficialTitle().getValue(),saved.getOfficialTitle().getValue());
        assertEquals(create.getPhaseCode().getCode(),saved.getPhaseCode().getCode());
        assertEquals(create.getSummary4AnatomicSites().getItem().iterator().next().getCode(),
                saved.getSummary4AnatomicSites().getItem().iterator().next().getCode());
        assertNotNull(saved.getIdentifier().getExtension());
    }

    @Test
    public void updateInterventionalStudyProtocol() throws Exception {
        InterventionalStudyProtocol create =
                TestSchema.createInterventionalStudyProtocolObj(new InterventionalStudyProtocol());

        InterventionalStudyProtocolDTO createDTO = InterventionalStudyProtocolConverter.convertFromDomainToDTO(create);

        Ii ii = remoteEjb.createInterventionalStudyProtocol(createDTO);
        assertNotNull(ii.getExtension());
        InterventionalStudyProtocolDTO saved =  remoteEjb.getInterventionalStudyProtocol(ii);

        saved.setAcronym(StConverter.convertToSt("1234"));

        InterventionalStudyProtocolDTO update =  remoteEjb.updateInterventionalStudyProtocol(saved, null);

        assertNotNull(saved);
        assertEquals(saved.getAccrualReportingMethodCode().getCode(), update.getAccrualReportingMethodCode().getCode());
        assertEquals(saved.getAcronym().getValue(),update.getAcronym().getValue());
        assertEquals(saved.getAllocationCode().getCode(),update.getAllocationCode().getCode());
        assertEquals(saved.getDelayedpostingIndicator().getValue(),update.getDelayedpostingIndicator().getValue());
        assertEquals(saved.getExpandedAccessIndicator().getValue(),update.getExpandedAccessIndicator().getValue());
        assertEquals(saved.getFdaRegulatedIndicator().getValue(),update.getFdaRegulatedIndicator().getValue());
        assertEquals(saved.getOfficialTitle().getValue(),update.getOfficialTitle().getValue());
        assertEquals(saved.getPhaseCode().getCode(),update.getPhaseCode().getCode());
        assertNotNull(update.getIdentifier().getExtension());
    }

    @Test
    public void nullInDatesTest() throws Exception {
        StudyProtocol sp = new InterventionalStudyProtocol();
        sp.setOfficialTitle("cacncer for THOLA");
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2009"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setProprietaryTrialIndicator(Boolean.FALSE);
        sp.setSubmissionNumber(Integer.valueOf(1));
        TestSchema.addUpdObject(sp);

        StudyProtocolDTO spDto = remoteEjb.getStudyProtocol(IiConverter.convertToIi(sp.getId()));
        spDto.setStartDate(null);
        try {
            remoteEjb.updateStudyProtocol(spDto);
            fail("PAException should have been thrown for null in start date.");
        } catch (PAException e) {
            // expected behavior
        }
        spDto.setStartDate(TsConverter.convertToTs(null));
        try {
            remoteEjb.updateStudyProtocol(spDto);
            fail("PAException should have been thrown for Ts null in start date.");
        } catch (PAException e) {
            // expected behavior
        }
        spDto = remoteEjb.getStudyProtocol(IiConverter.convertToIi(sp.getId()));
        spDto.setPrimaryCompletionDate(null);
        try {
            remoteEjb.updateStudyProtocol(spDto);
            fail("PAException should have been thrown for null in completion date.");
        } catch (PAException e) {
            // expected behavior
        }
        spDto.setPrimaryCompletionDate(TsConverter.convertToTs(null));
        try {
            remoteEjb.updateStudyProtocol(spDto);
            fail("PAException should have been thrown for Ts null in completion date.");
        } catch (PAException e) {
            // expected behavior
        }
    }

    @Test
    public void testGetStudyProtocol() throws Exception {
        createStudyProtocols(1, PAConstants.DCP_ORG_NAME, "DCP-1", false);
        createStudyProtocols(1, PAConstants.DCP_ORG_NAME, "DCP-11", false);
        createStudyProtocols(1, PAConstants.CTGOV_ORG_NAME, "NCT-1", false);
        createStudyProtocols(1, PAConstants.CTGOV_ORG_NAME, "NCT-11", false);
        createStudyProtocols(1, PAConstants.CTEP_ORG_NAME, "CTEP-1", false);
        createStudyProtocols(1, PAConstants.CTEP_ORG_NAME, "CTEP-11", false);

        Ii ii = new Ii();
        ii.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        ii.setExtension("DCP-1");
        StudyProtocolDTO spDTO = remoteEjb.getStudyProtocol(ii);
        assertNull(spDTO);

        ii = new Ii();
        ii.setRoot(IiConverter.DCP_STUDY_PROTOCOL_ROOT);
        ii.setExtension("DCP-1");
        spDTO = remoteEjb.getStudyProtocol(ii);
        assertNotNull(spDTO);


        ii = new Ii();
        ii.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        ii.setExtension("NCT-1");
        spDTO = remoteEjb.getStudyProtocol(ii);
        assertNull(spDTO);

        ii = new Ii();
        ii.setRoot(IiConverter.NCT_STUDY_PROTOCOL_ROOT);
        ii.setExtension("NCT-1");
        spDTO = remoteEjb.getStudyProtocol(ii);
        assertNotNull(spDTO);

        ii = new Ii();
        ii.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        ii.setExtension("CTEP-1");
        spDTO = remoteEjb.getStudyProtocol(ii);
        assertNull(spDTO);

        ii = new Ii();
        ii.setRoot(IiConverter.CTEP_STUDY_PROTOCOL_ROOT);
        ii.setExtension("CTEP-1");
        spDTO = remoteEjb.getStudyProtocol(ii);
        assertNotNull(spDTO);
    }

    @Test
    public void loadStudyProtocol() throws Exception {
        createStudyProtocols(1, PAConstants.DCP_ORG_NAME, "DCP-1", false);
        createStudyProtocols(1, PAConstants.DCP_ORG_NAME, "DCP-11", false);
        createStudyProtocols(1, PAConstants.CTGOV_ORG_NAME, "NCT-1", false);
        createStudyProtocols(1, PAConstants.CTGOV_ORG_NAME, "NCT-11", false);
        createStudyProtocols(1, PAConstants.CTEP_ORG_NAME, "CTEP-1", false);
        createStudyProtocols(1, PAConstants.CTEP_ORG_NAME, "CTEP-11", false);

        Ii ii = new Ii();
        ii = new Ii();
        ii.setRoot(IiConverter.DCP_STUDY_PROTOCOL_ROOT);
        ii.setExtension("DCP-1");
        StudyProtocolDTO spDTO = remoteEjb.loadStudyProtocol(ii);
        assertNotNull(spDTO);

        ii = new Ii();
        ii.setRoot(IiConverter.NCT_STUDY_PROTOCOL_ROOT);
        ii.setExtension("NCT-1");
        spDTO = remoteEjb.loadStudyProtocol(ii);
        assertNotNull(spDTO);

        ii = new Ii();
        ii.setRoot(IiConverter.CTEP_STUDY_PROTOCOL_ROOT);
        ii.setExtension("CTEP-1");
        spDTO = remoteEjb.loadStudyProtocol(ii);
        assertNotNull(spDTO);
    }

    @Test
    public void iiRootTest() throws Exception {
        InterventionalStudyProtocolDTO ispDTO =
            StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        Ii ii = remoteEjb.createInterventionalStudyProtocol(ispDTO);
        assertEquals(ii.getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
        assertTrue(StringUtils.isNotEmpty(ii.getIdentifierName()));
    }

    @Test
    public void primaryPurposeCodeTest1() throws Exception {
        InterventionalStudyProtocolDTO ispDTO =
            StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setPrimaryPurposeCode(null);
        thrown.expect(PAException.class);
        remoteEjb.createInterventionalStudyProtocol(ispDTO);
        thrown.expectMessage("Primary Purpose Code must be set.");
    }

    @Test
    public void primaryPurposeCodeTest2() throws Exception {
        InterventionalStudyProtocolDTO ispDTO =
            StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setPrimaryPurposeCode(CdConverter.convertStringToCd("wrong code"));
        thrown.expect(PAException.class);
        remoteEjb.createInterventionalStudyProtocol(ispDTO);
        thrown.expectMessage("Invalid Primary Purpose Code.");
    }

    @Test
    public void primaryPurposeCodeOtherTest1() throws Exception {
        InterventionalStudyProtocolDTO ispDTO =
            StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.OTHER));
        ispDTO.setPrimaryPurposeAdditionalQualifierCode(CdConverter.convertToCd(PrimaryPurposeAdditionalQualifierCode.ANCILLARY));
        ispDTO.setPrimaryPurposeOtherText(null);
        thrown.expect(PAException.class);
        remoteEjb.createInterventionalStudyProtocol(ispDTO);
        thrown.expectMessage("Primary Purpose Other Text is required when Primary Purpose Code is Other.");
    }
    @Test
    public void getTrialNciIdTest() throws Exception {
        List<Long> listOfTrialIDs = new ArrayList<Long>();
        listOfTrialIDs.add(1L);
        listOfTrialIDs.add(2L);
        createStudyProtocols(1, PAConstants.DCP_ORG_NAME, "DCP-1", false);

        Ii ii = new Ii();
        ii = new Ii();
        ii.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        ii.setExtension("NCT-1");
        StudyProtocolDTO spDTO = remoteEjb.loadStudyProtocol(ii);
        
        Map<Long, String> map = bean.getTrialNciId(listOfTrialIDs);
        assertTrue(map.size() > 0);     
    }
    @Test
    public void primaryPurposeCodeOtherTest2() throws Exception {
        InterventionalStudyProtocolDTO ispDTO =
            StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.OTHER));
        ispDTO.setPrimaryPurposeAdditionalQualifierCode(CdConverter.convertToCd(PrimaryPurposeAdditionalQualifierCode.ANCILLARY));
        ispDTO.setPrimaryPurposeOtherText(StConverter.convertToSt(""));
        thrown.expect(PAException.class);
        remoteEjb.createInterventionalStudyProtocol(ispDTO);
        thrown.expectMessage("Primary Purpose Other Text is required when Primary Purpose Code is Other.");
    }

    @Test
    public void primaryPurposeCodeOtherAddCodeTest1() throws Exception {
        InterventionalStudyProtocolDTO ispDTO =
            StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.OTHER));
        ispDTO.setPrimaryPurposeOtherText(StConverter.convertToSt("other"));
        ispDTO.setPrimaryPurposeAdditionalQualifierCode(null);
        thrown.expect(PAException.class);
        remoteEjb.createInterventionalStudyProtocol(ispDTO);
        thrown.expectMessage("Valid Primary Purpose Additional Qualifier Code is required when Primary Purpose Code is Other.");
    }

    @Test
    public void primaryPurposeCodeOtherAddCodeTest2() throws Exception {
        InterventionalStudyProtocolDTO ispDTO =
            StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        ispDTO.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.OTHER));
        ispDTO.setPrimaryPurposeOtherText(StConverter.convertToSt("other"));
        ispDTO.setPrimaryPurposeAdditionalQualifierCode(CdConverter.convertStringToCd("wrong code"));
        thrown.expect(PAException.class);
        remoteEjb.createInterventionalStudyProtocol(ispDTO);
        thrown.expectMessage("Valid Primary Purpose Additional Qualifier Code is required when Primary Purpose Code is Other.");
    }


    /**
     * Creates study protocols
     * @param count the number of study protocols to create
     * @param sponsorName the name of the sponsor
     * @param identifierAssignerId the id of the identifier assigner
     * @param abstracted whether the trial should be abstracted
     */
    private void createStudyProtocols(int count, String sponsorName, String identifierAssignerId, boolean abstracted) {
        for (int i = 1; i <= count; i++) {
            InterventionalStudyProtocol sp = new InterventionalStudyProtocol();
            sp = (InterventionalStudyProtocol) TestSchema.createStudyProtocolObj(sp);
            sp = TestSchema.createInterventionalStudyProtocolObj(sp);

            Set<Ii> secondaryIdentifiers =  new HashSet<Ii>();
            Ii spSecId = new Ii();
            spSecId.setExtension("NCI-2010-0000" + i);
            spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);

            Ii otherId = new Ii();
            otherId.setExtension("OTHER-" + i);
            otherId.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);

            secondaryIdentifiers.add(spSecId);
            secondaryIdentifiers.add(otherId);
            sp.setOtherIdentifiers(secondaryIdentifiers);
            TestSchema.addUpdObject(sp);

            DocumentWorkflowStatus dws = new DocumentWorkflowStatus();
            dws.setStudyProtocol(sp);
            dws.setStatusCode(DocumentWorkflowStatusCode.REJECTED);
            dws.setCommentText("Rejected");
            dws.setUserLastUpdated(sp.getUserLastUpdated());
            TestSchema.addUpdObject(dws);

            dws = new DocumentWorkflowStatus();
            dws.setStudyProtocol(sp);
            dws.setStatusCode(DocumentWorkflowStatusCode.VERIFICATION_PENDING);
            dws.setCommentText("Verification Pending.");
            dws.setUserLastUpdated(sp.getUserLastUpdated());
            TestSchema.addUpdObject(dws);

            dws = new DocumentWorkflowStatus();
            dws.setStudyProtocol(sp);
            dws.setStatusCode(DocumentWorkflowStatusCode.ACCEPTED);
            dws.setCommentText("Accepted");
            dws.setUserLastUpdated(sp.getUserLastUpdated());
            TestSchema.addUpdObject(dws);

            if (abstracted) {
                dws = new DocumentWorkflowStatus();
                dws.setStudyProtocol(sp);
                dws.setStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
                dws.setCommentText("Abstracted");
                dws.setUserLastUpdated(sp.getUserLastUpdated());
                TestSchema.addUpdObject(dws);
            }

            AnatomicSite as1 = TestSchema.createAnatomicSiteObj("Lung");
            TestSchema.addUpdObject(as1);

            sp.setSummary4AnatomicSites(new TreeSet<AnatomicSite>(new AnatomicSiteComparator()));
            sp.getSummary4AnatomicSites().add(as1);
            TestSchema.addUpdObject(sp);

            if (StringUtils.isNotEmpty(sponsorName)) {
                Organization org = TestSchema.createOrganizationObj();
                org.setName(sponsorName);
                TestSchema.addUpdObject(org);

                ResearchOrganization ro = new ResearchOrganization();
                ro.setOrganization(org);
                ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
                ro.setIdentifier("abc");
                TestSchema.addUpdObject(ro);

                StudySite studySite = new StudySite();
                studySite.setFunctionalCode(StudySiteFunctionalCode.SPONSOR);
                studySite.setLocalStudyProtocolIdentifier("foo");
                studySite.setUserLastUpdated(sp.getUserLastUpdated());
                Timestamp now = new Timestamp((new Date()).getTime());
                studySite.setDateLastUpdated(now);
                studySite.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
                studySite.setStatusDateRangeLow(now);
                studySite.setStudyProtocol(sp);
                studySite.setResearchOrganization(ro);

                sp.getStudySites().add(studySite);
                TestSchema.addUpdObject(studySite);
            }

            if (StringUtils.isNotEmpty(identifierAssignerId)) {
                ResearchOrganization ro = null;
                if (StringUtils.isNotEmpty(sponsorName)) {
                    Organization org = TestSchema.createOrganizationObj();
                    org.setName(sponsorName);
                    TestSchema.addUpdObject(org);

                    ro = new ResearchOrganization();
                    ro.setOrganization(org);
                    ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
                    ro.setIdentifier("abc");
                    TestSchema.addUpdObject(ro);
                }

                StudySite studySite = new StudySite();
                studySite.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
                studySite.setLocalStudyProtocolIdentifier(identifierAssignerId);
                studySite.setUserLastUpdated(sp.getUserLastUpdated());
                Timestamp now = new Timestamp((new Date()).getTime());
                studySite.setDateLastUpdated(now);
                studySite.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
                studySite.setStatusDateRangeLow(now);
                studySite.setStudyProtocol(sp);

                if (ro != null) {
                    studySite.setResearchOrganization(ro);
                }
                sp.getStudySites().add(studySite);
                TestSchema.addUpdObject(studySite);
            }
        }
    }

    @Test
    public void testAddNciId() throws PAException {
        InterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createInterventionalStudyProtocolDTOObj();
        Ii ii = bean.createInterventionalStudyProtocol(ispDTO);
        (new MockPAServiceUtils()).addNciIdentifierToTrial(ii);
        StudyProtocolDTO spDto = bean.getStudyProtocol(ii);
        assertNotNull(spDto.getIdentifier().getExtension());
        assertNotNull(spDto.getSecondaryIdentifiers().getItem().iterator().next().getExtension());
    }

    public static InterventionalStudyProtocolDTO createInterventionalStudyProtocolDTOObj() {
        InterventionalStudyProtocolDTO ispDTO = new InterventionalStudyProtocolDTO();
        ispDTO.setAccrualReportingMethodCode(CdConverter.convertStringToCd(AccrualReportingMethodCode.ABBREVIATED.getCode()));
        ispDTO.setAcronym(StConverter.convertToSt("abcd"));
        ispDTO.setAllocationCode(CdConverter.convertStringToCd(AllocationCode.NA.getCode()));
        ispDTO.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.FALSE));
        ispDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
        ispDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.TRUE));
        ispDTO.setOfficialTitle(StConverter.convertToSt("Phase Ii trial"));
        Timestamp now = new Timestamp((new Date()).getTime());
        ispDTO.setStartDate(TsConverter.convertToTs(now));
        ispDTO.setStartDateTypeCode(CdConverter.convertStringToCd(ActualAnticipatedTypeCode.ACTUAL.getCode()));
        ispDTO.setPrimaryCompletionDate(TsConverter.convertToTs(now));
        ispDTO.setPrimaryCompletionDateTypeCode(CdConverter.convertStringToCd(ActualAnticipatedTypeCode.ACTUAL.getCode()));
        ispDTO.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.BASIC_SCIENCE));
        ispDTO.setPhaseCode(CdConverter.convertStringToCd(PhaseCode.I.getCode()));
        ispDTO.setStatusCode(CdConverter.convertStringToCd(ActStatusCode.ACTIVE.getCode()));
        ispDTO.setAmendmentReasonCode(CdConverter.convertStringToCd(AmendmentReasonCode.BOTH.getCode()));
        ispDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));
        ispDTO.setSubmissionNumber(IntConverter.convertToInt(Integer.valueOf(1)));
        DSet<Cd> dsetSa = new DSet<Cd>();
        dsetSa.setItem(new HashSet<Cd>());
        Cd cdSas = new Cd();
        cdSas.setCode("Lung");
        cdSas.setCodeSystem("Summary 4 Anatomic Sites");
        dsetSa.getItem().add(cdSas);
        ispDTO.setSummary4AnatomicSites(dsetSa);
        return ispDTO;
    }

}
