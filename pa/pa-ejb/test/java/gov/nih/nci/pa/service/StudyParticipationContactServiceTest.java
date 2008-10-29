/**
 *
 */
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyParticipationContact;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.convert.StudyParticipationContactConverter;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.util.PAHealthCareProviderRemote;
import gov.nih.nci.pa.service.util.PAHealthCareProviderServiceBean;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test service and converter.
 * @author hreinhart
 */
public class StudyParticipationContactServiceTest {
    private StudyParticipationContactServiceRemote remoteEjb = new StudyParticipationContactServiceBean();
    private PAHealthCareProviderRemote paRemote = new PAHealthCareProviderServiceBean();
    Long protocolId;
    Ii protocolIi;
    Long participationId;
    Ii participationIi;
    Long contactId;
    Ii contactIi;
    Long healthCareProviderId;
    Ii healthCareProviderIi;

    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        protocolId = TestSchema.studyProtocolIds.get(0);
        protocolIi = IiConverter.convertToIi(protocolId);
        participationId = TestSchema.studyParticipationIds.get(0);
        participationIi = IiConverter.convertToIi(participationId);
        contactId = TestSchema.studyParticipationContactIds.get(0);
        contactIi = IiConverter.convertToIi(contactId);
        healthCareProviderId = TestSchema.healthCareFacilityIds.get(0);
        healthCareProviderIi = IiConverter.convertToIi(healthCareProviderId);
    }
    @Test
    public void getPersons() throws Exception {
        //List a = paRemote.getPersonsByStudyParticpationId(participationId);
        assertEquals("", "");

    }
   // @Test
    public void get() throws Exception {
        StudyParticipationContactDTO spcDto = remoteEjb.get(contactIi);
        StudyParticipationContact spcBo = StudyParticipationContactConverter.convertFromDtoToDomain(spcDto);
        assertEquals(participationId, spcBo.getStudyParticipation().getId());
        assertEquals(protocolId, spcBo.getStudyProtocol().getId());
    }
   // @Test
    public void create() throws Exception {
        StudyParticipationContactDTO spc = new StudyParticipationContactDTO();
        spc.setIdentifier(IiConverter.convertToIi((Long) null));
        spc.setPostalAddress(AddressConverterUtil.create("1", "2", "3", "4", "5", "ZZZ"));
        spc.setPrimaryIndicator(BlConverter.convertToBl(true));
        spc.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.COORDINATING_INVESTIGATOR));
        spc.setStatusCode(CdConverter.convertToCd(StatusCode.ACTIVE));
        spc.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/2005")));
        spc.setStudyParticipationIi(participationIi);
        spc.setStudyProtocolIi(protocolIi);
        spc.setHealthCareProvider(healthCareProviderIi);
        StudyParticipationContactDTO result = remoteEjb.create(spc);
        assertFalse(PAUtil.isIiNull(result.getIdentifier()));
    }



   // @Test
    public void delete() throws Exception {
        remoteEjb.delete(contactIi);
        try {
            remoteEjb.get(participationIi);
            fail("get() should have thrown an exception after delete().");
        } catch(PAException e) {
            // expected behavior
        }
    }
}
