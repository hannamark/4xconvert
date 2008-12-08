package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.person.PersonDTO;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Harsha
 */
@SuppressWarnings("PMD")
public class EnPnConverter {
    private static final int EMAIL_IDX = 7; 

    /**
     * 
     * @param poPerson as arg
     * @return Person as pa object
     */
    public static Person convertToPaPerson(PersonDTO poPerson) {
        Person person = new Person();
        List<Enxp> list = poPerson.getName().getPart();
        Iterator ite = list.iterator();
        while (ite.hasNext()) {
           Enxp part = (Enxp) ite.next();          
           if (EntityNamePartType.FAM == part.getType()) {
               person.setLastName(part.getValue());
           } else if (EntityNamePartType.GIV == part.getType()) {
               if (person.getFirstName() == null) {
                   person.setFirstName(part.getValue());
               } else {
                   person.setMiddleName(part.getValue());
               }
           } 
        }
        return person;
    }
    
    /**
     * 
     * @param poPerson as arg
     * @return Person as pa object
     */
    public static gov.nih.nci.pa.dto.PersonDTO convertToPaPersonDTO(PersonDTO poPerson) {
        gov.nih.nci.pa.dto.PersonDTO personDTO = new gov.nih.nci.pa.dto.PersonDTO();
        personDTO.setId(Long.valueOf(poPerson.getIdentifier().getExtension()));
        List<Enxp> list = poPerson.getName().getPart();
        Iterator ite = list.iterator();
        while (ite.hasNext()) {
           Enxp part = (Enxp) ite.next();          
           if (EntityNamePartType.FAM == part.getType()) {
               personDTO.setLastName(part.getValue());
           } else if (EntityNamePartType.GIV == part.getType()) {
               if (personDTO.getFirstName() == null) {
                   personDTO.setFirstName(part.getValue());
               } else {
                   personDTO.setMiddleName(part.getValue());
               }
           } 
        }
        //TelEmail
        DSet<Tel> telList = poPerson.getTelecomAddress();
        Set<Tel> set = telList.getItem();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            Object obj = iter.next(); 
            if (obj instanceof TelEmail) {
                personDTO.setEmail(((TelEmail) obj).getValue().toString().substring(EMAIL_IDX));
            }
        }
        return personDTO;
    }    
    
    /**
     * converts the given enpn to the data members on the given person.
     * 
     * @param value the source iso person name.
     * @param person the PA person.
     * @throws PAException on e
     */
    public static void convertToPersonName(EnPn value, Person person) throws PAException {
        if (value == null) {
            return;
        }
        // set all name values to null prior to parsing
        person.setLastName(null);
        person.setFirstName(null);
        person.setMiddleName(null);
        // person.setPrefix(null);
        // person.setSuffix(null);
        if (value.getNullFlavor() == null) {
            processParts(value.getPart(), person);
        }
    }

    private static void processParts(List<Enxp> parts, Person person) throws PAException {
        // for handling del we need to know the previous part in the list
        Enxp previousPart = null;
        // for handling del we need to know the type of the previous non-del
        // part
        EntityNamePartType previousType = null;
        for (Enxp part : parts) {
            String delimieter = extractDelimiter(previousPart, previousType, part);
            processPart(part, person, delimieter);
            if (previousPart != null) {
                previousType = previousPart.getType();
            }
            previousPart = part;
        }
    }

    private static String extractDelimiter(Enxp previousPart, EntityNamePartType previousType, Enxp part)
            throws PAException {
        String delimieter = " ";
        if (previousPart != null && EntityNamePartType.DEL.equals(previousPart.getType())) {
            if (previousType == null || previousType != part.getType()) {
                throw new PAException("A delimiter came between two parts of an ENPN that wer not "
                        + "of the same type.");
            } else {
                delimieter = previousPart.getValue();
            }
        }
        return delimieter;
    }

    private static void processPart(Enxp part, Person person, String delimiter) {
        if (EntityNamePartType.FAM == part.getType()) {
            person.setLastName(produceNewValue(person.getLastName(), part.getValue(), delimiter));
        } else if (EntityNamePartType.GIV == part.getType()) {
            if (person.getFirstName() == null) {
                person.setFirstName(part.getValue());
            } else {
                person.setMiddleName(produceNewValue(person.getMiddleName(), part.getValue(), delimiter));
            }
        } /*
             * else if (EntityNamePartType.PFX == part.getType()) {
             * person.setPrefix(produceNewValue(person.getPrefix(),
             * part.getValue(), delimiter)); } else if (EntityNamePartType.SFX ==
             * part.getType()) {
             * person.setSuffix(produceNewValue(person.getSuffix(),
             * part.getValue(), delimiter)); }
             */
    }

    private static String produceNewValue(String oldValue, String addition, String del) {
        if (oldValue == null) {
            return addition;
        } else {
            return oldValue + del + addition;
        }
    }
}
