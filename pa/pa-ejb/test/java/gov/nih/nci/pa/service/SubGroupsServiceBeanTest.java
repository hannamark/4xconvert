package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SubGroupsServiceBeanTest {

    private SubGroupsServiceRemote remoteEjb = new SubGroupsServiceBean();;
    Ii pid;

    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        pid = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
    }

    @Test
    public void get() throws Exception {
        List<StratumGroupDTO> statusList =
            remoteEjb.getByStudyProtocol(pid);
        assertEquals(2, statusList.size());

        StratumGroupDTO dto =
            remoteEjb.get(statusList.get(1).getIdentifier());
        assertEquals(IiConverter.convertToLong(statusList.get(1).getIdentifier())
                , (IiConverter.convertToLong(dto.getIdentifier())));
        StratumGroupDTO dto2 = null;

            dto2 = new StratumGroupDTO();
            dto2 = remoteEjb.update(dto);
            assertEquals(dto.getDescription().getValue()
                    , dto2.getDescription().getValue());

         remoteEjb.delete(dto.getIdentifier());
    }

    @Test
    public void create() throws Exception {
        StratumGroupDTO dto = new StratumGroupDTO();
        dto.setStudyProtocolIi(pid);
        dto.setDescription(StConverter.convertToSt("Description"));
        dto.setGroupNumberText(StConverter.convertToSt("Code"));
        StratumGroupDTO dto2 = null;
        dto2 = new StratumGroupDTO();
        dto2 = remoteEjb.create(dto);
        assertEquals(dto.getStudyProtocolIi()
                , pid);
    }


}
