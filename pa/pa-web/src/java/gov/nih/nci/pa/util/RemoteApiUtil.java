package gov.nih.nci.pa.util;

import static gov.nih.nci.coppa.iso.EntityNamePartType.SFX;
import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.commons.lang.StringUtils;

/**
 * Adapted from PO modified for PA to call the PO API's.
 * 
 * @author Harsha
 * 
 */
@SuppressWarnings({"PMD" })
public class RemoteApiUtil {
    /**
     * @param firstName
     *            given name
     * @param middleName
     *            middle name
     * @param lastName
     *            family name
     * @param prefix
     *            prefix
     * @param suffix
     *            suffix
     * @return ISO EN Person Name
     */
    public static final EnPn convertToEnPn(String firstName, String middleName,
            String lastName, String prefix, String suffix) {
        EnPn enpn = new EnPn();
        addEnxp(enpn, lastName, EntityNamePartType.FAM);
        addEnxp(enpn, firstName, EntityNamePartType.GIV);
        addEnxp(enpn, middleName, EntityNamePartType.GIV);
        addEnxp(enpn, prefix, EntityNamePartType.PFX);
        addEnxp(enpn, suffix, SFX);
        return enpn;
    }

    private static void addEnxp(EnPn enpn, String value, EntityNamePartType type) {
        if (StringUtils.isNotEmpty(value)) {
            Enxp part = new Enxp(type);
            part.setValue(value);
            enpn.getPart().add(part);
        }
    }

    /**
     * 
     * @param value
     *            a boolean to parse.
     * @return an iso BL
     */
    public static Bl convertToBl(Boolean value) {
        Bl iso = new Bl();
        if (value == null) {
            iso.setNullFlavor(NullFlavor.NI);
        } else {
            iso.setValue(value);
        }
        return iso;
    }

    /**
     * @param email
     *            email
     * @param fax
     *            fax
     * @param phone
     *            phone
     * @param url
     *            url
     * @param text
     *            tty
     * @return a list containg all the params' content converted to a Tel type.
     */
    public static DSet<Tel> convertToDSetTel(List<String> email,
            List<String> fax, List<String> phone, List<String> url,
            List<String> text) {
        DSet<Tel> dset = new DSet<Tel>();
        @SuppressWarnings("unchecked")
        Set<Tel> set = new ListOrderedSet();
        dset.setItem(set);
        for (String c : email) {
            TelEmail t = new TelEmail();
            t.setValue(createURI(TelEmail.SCHEME_MAILTO, c));
            set.add(t);
        }
        for (String c : fax) {
            TelPhone t = new TelPhone();
            t.setValue(createURI(TelPhone.SCHEME_X_TEXT_FAX, c));
            set.add(t);
        }
        for (String c : phone) {
            TelPhone t = new TelPhone();
            t.setValue(createURI(TelPhone.SCHEME_TEL, c));
            set.add(t);
        }
        for (String c : url) {
            TelUrl t = new TelUrl();
            t.setValue(URI.create(c));
            set.add(t);
        }
        for (String c : text) {
            TelPhone t = new TelPhone();
            t.setValue(createURI(TelPhone.SCHEME_X_TEXT_TEL, c));
            set.add(t);
        }
        return dset;
    }

    private static URI createURI(String scheme, String schemeSpecificPart) {
        try {
            return new URI(scheme, schemeSpecificPart, null);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * 
     * @param poPerson
     *            PersonDTO
     * @return SearchPersonResultDisplay
     */
    public static SearchPersonResultDisplay convertToPaPerson(PersonDTO poPerson) {
        SearchPersonResultDisplay prs = new SearchPersonResultDisplay();
        List<Enxp> list = poPerson.getName().getPart();
        Iterator<Enxp> ite = list.iterator();
        while (ite.hasNext()) {
            Enxp part = (Enxp) ite.next();
            if (EntityNamePartType.FAM == part.getType()) {
                prs.setLastName(part.getValue());
            } else if (EntityNamePartType.GIV == part.getType()) {
                if (prs.getFirstName() == null) {
                    prs.setFirstName(part.getValue());
                } else {
                    prs.setMiddleName(part.getValue());
                }
            }
        }
        StringBuffer emailList = new StringBuffer();
        List<String> emails = DSetConverter.convertDSetToList(poPerson
                .getTelecomAddress(), "EMAIL");
        for (String email : emails) {
            emailList.append(email + ", \n");
        }
        prs.setId(Long.valueOf(poPerson.getIdentifier().getExtension()));
        prs.setEmail(emailList.toString());
        prs.setId(Long.valueOf(poPerson.getIdentifier().getExtension()
                .toString()));
        List<Adxp> adList = poPerson.getPostalAddress().getPart();
        Iterator<Adxp> adListite = adList.iterator();
        while (adListite.hasNext()) {
            Adxp adpart = (Adxp) adListite.next();
            if (AddressPartType.CTY == adpart.getType()) {
                prs.setCity(adpart.getValue());
            }
            if (AddressPartType.STA == adpart.getType()) {
                prs.setState(adpart.getValue());
            }
            if (AddressPartType.CNT == adpart.getType()) {
                prs.setCountry(adpart.getCode());
            }
            if (AddressPartType.ZIP == adpart.getType()) {
                prs.setZip(adpart.getValue());
            }            
        }
        return prs;
    }
}
