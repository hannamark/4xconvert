/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.util.PAUtil;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Vrushali
 *
 */
public class MockStudySiteContactService extends MockAbstractRoleIsoService<StudySiteContactDTO> implements
        StudySiteContactServiceLocal {
    static List<StudySiteContactDTO> contactList;
    static {
        contactList = new ArrayList<StudySiteContactDTO>();
        StudySiteContactDTO dto = new StudySiteContactDTO();
        dto.setRoleCode(CdConverter.convertToCd(StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT)); 
        dto.setOrganizationalContactIi(IiConverter.convertToPoOrganizationalContactIi("1"));
        DSet<Tel> telecomAddresses = new DSet<Tel>();
        Set<Tel> telSet = new HashSet<Tel>();
        TelPhone phone = new TelPhone();
        phone.setValue(URI.create("tel:phone"));
        telSet.add(phone);
        TelEmail email = new TelEmail();
        email.setValue(URI.create("mailto:email"));
        telSet.add(email);
        telecomAddresses.setItem(telSet);
        dto.setTelecomAddresses(telecomAddresses);
        contactList.add(dto);
    }
    public List<StudySiteContactDTO> getByStudySite(Ii studySiteIi)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }
    public List<StudySiteContactDTO> getByStudyProtocol(Ii studyProtocolIi, StudySiteContactDTO dto)
    throws PAException {
        List<StudySiteContactDTO> returnList = new ArrayList<StudySiteContactDTO>();
        for (StudySiteContactDTO contactDto : contactList) {
            if (!PAUtil.isCdNull(dto.getRoleCode())
                &&  dto.getRoleCode().getCode().equals(contactDto.getRoleCode().getCode())) {
                returnList.add(contactDto);
            }
        }
        return returnList;
    }



}
