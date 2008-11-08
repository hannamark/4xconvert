package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.po.data.convert.IdConverter;

/**
 * utility method for converting Ii and Id.
 *
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class IiConverter {

    /**
     * The identifier name for org ii's.
     */
    public static final String ORG_IDENTIFIER_NAME = "NCI organization entity identifier";

    /**
     * The ii root value for orgs.
     */
    public static final String ORG_ROOT = "UID.for.nci.entity.organization";

    /**
     * The identifier name for person ii's.
     */
    public static final String PERSON_IDENTIFIER_NAME = "NCI person entity identifier";

    /**
     * The ii root value for people.
     */
    public static final String PERSON_ROOT = "UID.for.nci.entity.person";

    /**
     * The identifier name for.
     */
    public static final String CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME = "NCI clinical research staff identifier";

    /**
     * The ii root value.
     */
    public static final String CLINICAL_RESEARCH_STAFF_ROOT = "UID.for.nci.role.clinicalresearchstaff";

    /**
     * The identifier name for.
     */
    public static final String HEALTH_CARE_PROVIDER_IDENTIFIER_NAME = "NCI health care provider identifier";

    /**
     * The ii root value.
     */
    public static final String HEALTH_CARE_PROVIDER_ROOT = "UID.for.nci.role.healthcareprovider";

    /**
     * The identifier name for.
     */
    public static final String HEALTH_CARE_FACILITY_IDENTIFIER_NAME = "NCI health care facility identifier";

    /**
     * The ii root value.
     */
    public static final String HEALTH_CARE_FACILITY_ROOT = "UID.for.nci.role.healthcarefacility";

    /**
     * The identifier name for.
     */
    public static final String OVERSIGHT_COMMITTEE_IDENTIFIER_NAME = "NCI oversight committee identifier";

    /**
     * The ii root value.
     */
    public static final String OVERSIGHT_COMMITTEE_ROOT = "UID.for.nci.role.oversightcommittee";

    /**
     * The identifier name for.
     */
    public static final String RESEARCH_ORG_IDENTIFIER_NAME = "NCI Research Organization identifier";

    /**
     * The ii root value.
     */
    public static final String RESEARCH_ORG_ROOT = "UID.for.nci.role.researchorganization";

    /**
     * The identifier name for.
     */
    public static final String PERSON_RESOURCE_PROVIDER_IDENTIFIER_NAME = "Person Resource Provider identifier";

    /**
     * The ii root value.
     */
    public static final String PERSON_RESOURCE_PROVIDER_ROOT = "UID.for.nci.role.personresourceprovider";

    /**
     * The identifier name for.
     */
    public static final String ORG_RESOURCE_PROVIDER_IDENTIFIER_NAME = "Org Resource Provider identifier";

    /**
     * The ii root value.
     */
    public static final String ORG_RESOURCE_PROVIDER_ROOT = "UID.for.nci.role.orgresourceprovider";

    /**
     * The identifier name for.
     */
    public static final String IDENTIFIED_ORG_IDENTIFIER_NAME = "Identified org identifier";

    /**
     * The ii root value.
     */
    public static final String IDENTIFIED_ORG_ROOT = "UID.for.nci.role.identifiedorg";

    /**
     * The identifier name for.
     */
    public static final String IDENTIFIED_PERSON_IDENTIFIER_NAME = "Identified person identifier";

    /**
     * The ii root value.
     */
    public static final String IDENTIFIED_PERSON_ROOT = "UID.for.nci.role.identifiedperson";
    /**
     * The identifier name for.
     */
    public static final String ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME = "Organizational contact identifier";
    
    /**
     * The ii root value.
     */
    public static final String ORGANIZATIONAL_CONTACT_ROOT = "UID.for.nci.role.organizationalcontact";

    /**
     * The identifier name for.
     */
    public static final String QUALIFIED_ENTITY_IDENTIFIER_NAME = "Qualified entity identifier";

    /**
     * The ii root value.
     */
    public static final String QUALIFIED_ENTITY_ROOT = "UID.for.nci.role.qualifiedentity";
    
    /**
     * 
     * @param id id
     * @return Ii ii
     */
    public static Ii convertToIi(Long id) {
        Ii ii = new Ii();
        if (id == null) {
            ii.setNullFlavor(NullFlavor.NI);
        } else {
            ii.setExtension(id.toString());
            //@todo : set others attributes of II;
        }
        return ii;
    }
    
    /**
     * 
     * @param str string
     * @return Ii
     */
    public static Ii convertToIi(String str) {
        Ii ii = new Ii();
        if (str == null) {
            ii.setNullFlavor(NullFlavor.NI);
        } else {
            ii.setExtension(str);
            //ii.setRoot("UID.for.nci.entity.organization");
        }
        return ii;
        
    }
    
    /**
     * 
     * @param ii ii
     * @return long
     */
    public static Long convertToLong(Ii ii) {
        if (ii == null || ii.getNullFlavor() != null) {
            return null;
        }
        if (ii.getExtension() == null) {
            return null;
        }
        return Long.valueOf(ii.getExtension());
    }
    
    /**
     * 
     * @param ii ii
     * @return String str
     */
    public static String convertToString(Ii ii) {
        if (ii == null || ii.getNullFlavor() != null) {
            return null;
        }
        if (ii.getExtension() == null) {
         // @todo : throw proper exception 
            ii.setNullFlavor(NullFlavor.NI);
        }
        return ii.getExtension();
    }
    
    /**
     * converts to Po Person Ii.
     * @param id id
     * @return Ii
     */
    public static Ii converToPoPersonIi(String id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(PERSON_IDENTIFIER_NAME);
        ii.setRoot(PERSON_ROOT);
        return ii;
    }
    
    /**
     * converts to Po Org Ii Ii.
     * @param id id
     * @return Ii
     */
    public static Ii converToPoOrganizationIi(String id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(ORG_IDENTIFIER_NAME);
        ii.setRoot(ORG_ROOT);
        return ii;
    }

    /**
     * converts to Po Org contact  Ii Ii.
     * @param id id
     * @return Ii
     */
    public static Ii converToPoOrganizationalContactIi(String id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME);
        ii.setRoot(ORGANIZATIONAL_CONTACT_ROOT);
        return ii;
    }
    
    /**
     * converts to Po crs contact  Ii Ii.
     * @param id id
     * @return Ii
     */
    public static Ii converToPoClinicalResearchStaffIi(String id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
        ii.setRoot(CLINICAL_RESEARCH_STAFF_ROOT);
        return ii;
    }

    /**
     * converts to Po crs contact  Ii Ii.
     * @param id id
     * @return Ii
     */
    public static Ii converToPoHealtcareProviderIi(String id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(HEALTH_CARE_PROVIDER_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.HEALTH_CARE_PROVIDER_ROOT);
        return ii;
    }

    /**
     * converts to Po crs contact  Ii Ii.
     * @param id id
     * @return Ii
     */
    public static Ii converToPoHealthCareFacilityIi(String id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.HEALTH_CARE_FACILITY_ROOT);
        return ii;
    }

}
