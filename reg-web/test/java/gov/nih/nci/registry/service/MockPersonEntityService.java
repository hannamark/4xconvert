/**
 * 
 */
package gov.nih.nci.registry.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * @author Vrushali
 *
 */
public class MockPersonEntityService implements PersonEntityServiceRemote {
    static List<PersonDTO> personList;
    static {
        personList = new ArrayList<PersonDTO>();
        PersonDTO dto = new PersonDTO();
        dto.setIdentifier(IiConverter.convertToIi("2"));
        dto.setName(EnPnConverter.convertToEnPn("firstName", null, "lastName", null, null));
        dto.setPostalAddress(AddressConverterUtil.create("streetAddressLine", null, "cityOrMunicipality",
                "stateOrProvince", "postalCode", "USA"));
        try {
            DSet<Tel> list = new DSet<Tel>();
            list.setItem(new HashSet<Tel>());
            TelEmail telemail = new TelEmail();
            telemail.setValue(new URI("mailto:mail@mail.com"));
            list.getItem().add(telemail);
            dto.setTelecomAddress(list);
        } catch (URISyntaxException e) {
        }
        personList.add(dto);
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.services.person.PersonEntityServiceRemote#createPerson(gov.nih.nci.services.person.PersonDTO)
     */
    public Ii createPerson(PersonDTO arg0) throws EntityValidationException {
        return IiConverter.convertToIi("2");
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.person.PersonEntityServiceRemote#getPerson(gov.nih.nci.coppa.iso.Ii)
     */
    public PersonDTO getPerson(Ii arg0) throws NullifiedEntityException {
        for(PersonDTO dto:personList){
            if(dto.getIdentifier().getExtension().equals(arg0.getExtension())){
                return dto;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.person.PersonEntityServiceRemote#search(gov.nih.nci.services.person.PersonDTO)
     */
    public List<PersonDTO> search(PersonDTO arg0) {
        List<PersonDTO> matchingDTO = new ArrayList<PersonDTO>();
        List<Enxp> nameList = arg0.getName().getPart();
        for(PersonDTO dto:personList){
            List<Enxp> dtoNameList = dto.getName().getPart();
            if(dtoNameList.get(0).getValue().equals(nameList.get(0).getValue())){
                matchingDTO.add(dto);
            }
        }
        return matchingDTO;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.person.PersonEntityServiceRemote#updatePerson(gov.nih.nci.services.person.PersonDTO)
     */
    public void updatePerson(PersonDTO arg0) throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.person.PersonEntityServiceRemote#updatePersonStatus(gov.nih.nci.coppa.iso.Ii, gov.nih.nci.coppa.iso.Cd)
     */
    public void updatePersonStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.person.PersonEntityServiceRemote#validate(gov.nih.nci.services.person.PersonDTO)
     */
    public Map<String, String[]> validate(PersonDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
