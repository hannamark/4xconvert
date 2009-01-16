/*
 * Copyright Notice. Copyright 2008, ScenPro, Inc, (“caBIG™ Participant”). The Protocol Abstraction (PA) Application was
 * created with NCI funding and is part of the caBIG™ initiative. The software subject to this notice and license
 * includes both human readable source code form and machine readable, binary, object code form (the “caBIG™ Software”).
 * This caBIG™ Software License (the “License”) is between caBIG™ Participant and You. “You (or “Your”) shall mean a
 * person or an entity, and all other entities that control, are controlled by, or are under common control with the
 * entity. “Control” for purposes of this definition means (i) the direct or indirect power to cause the direction or
 * management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the
 * outstanding shares, or (iii) beneficial ownership of such entity. License. Provided that You agree to the conditions
 * described below, caBIG™ Participant grants You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge,
 * irrevocable, transferable and royalty-free right and license in its rights in the caBIG™ Software, including any
 * copyright or patent rights therein, to (i) use, install, disclose, access, operate, execute, reproduce, copy, modify,
 * translate, market, publicly display, publicly perform, and prepare derivative works of the caBIG™ Software in any
 * manner and for any purpose, and to have or permit others to do so; (ii) make, have made, use, practice, sell, and
 * offer for sale, import, and/or otherwise dispose of caBIG™ Software (or portions thereof); (iii) distribute and have
 * distributed to and by third parties the caBIG™ Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third parties, including the right to license such
 * rights to further third parties. For sake of clarity, and not by way of limitation, caBIG™ Participant shall have no
 * right of accounting or right of payment from You or Your sub licensees for the rights granted under this License.
 * This License is granted at no charge to You. Your downloading, copying, modifying, displaying, distributing or use of
 * caBIG™ Software constitutes acceptance of all of the terms and conditions of this Agreement. If You do not agree to
 * such terms and conditions, You have no right to download, copy, modify, display, distribute or use the caBIG™
 * Software. 1. Your redistributions of the source code for the caBIG™ Software must retain the above copyright notice,
 * this list of conditions and the disclaimer and limitation of liability of Article 6 below. Your redistributions in
 * object code form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6
 * in the documentation and/or other materials provided with the distribution, if any. 2. Your end-user documentation
 * included with the redistribution, if any, must include the following acknowledgment: “This product includes software
 * developed by ScenPro, Inc.” If You do not include such end-user documentation, You shall include this acknowledgment
 * in the caBIG™ Software itself, wherever such third-party acknowledgments normally appear. 3. You may not use the
 * names “ScenPro, Inc.”, “The National Cancer Institute”, “NCI”, “Cancer Bioinformatics Grid” or “caBIG™” to endorse or
 * promote products derived from this caBIG™ Software. This License does not authorize You to use any trademarks,
 * service marks, trade names, logos or product names of either caBIG™ Participant, NCI or caBIG™, except as required to
 * comply with the terms of this License. 4. For sake of clarity, and not by way of limitation, You may incorporate this
 * caBIG™ Software into Your proprietary programs and into any third party proprietary programs. However, if You
 * incorporate the caBIG™ Software into third party proprietary programs, You agree that You are solely responsible for
 * obtaining any permission from such third parties required to incorporate the caBIG™ Software into such third party
 * proprietary programs and for informing Your sub licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before incorporating the caBIG™ Software into
 * such third party proprietary software programs. In the event that You fail to obtain such permissions, You agree to
 * indemnify caBIG™ Participant for any claims against caBIG™ Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5. For sake of clarity, and not by way of
 * limitation, You may add Your own copyright statement to Your modifications and to the derivative works, and You may
 * provide additional or different license terms and conditions in Your sublicenses of modifications of the caBIG™
 * Software, or any derivative works of the caBIG™ Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in this License. 6. THIS caBIG™ SOFTWARE IS
 * PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
 * ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */

package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;

/**
 * utility method for converting Ii and Id.
 *
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({ "PMD.TooManyMethods" })
public class IiConverter {
    /**
     * The identifier name for org ii's.
     */
    public static final String ORG_IDENTIFIER_NAME = "NCI organization entity identifier";

    /**
     * The ii root value for orgs.
     */
    public static final String ORG_ROOT = "2.16.840.1.113883.3.26.4.2";

    /**
     * The identifier name for person ii's.
     */
    public static final String PERSON_IDENTIFIER_NAME = "NCI person entity identifier";

    /**
     * The ii root value for people.
     */
    public static final String PERSON_ROOT = "2.16.840.1.113883.3.26.4.1";

    /**
     * The identifier name for.
     */
    public static final String CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME = "NCI clinical research staff identifier";

    /**
     * The ii root value.
     */
    public static final String CLINICAL_RESEARCH_STAFF_ROOT = "2.16.840.1.113883.3.26.4.4.1";

    /**
     * The identifier name for.
     */
    public static final String HEALTH_CARE_PROVIDER_IDENTIFIER_NAME = "NCI health care provider identifier";

    /**
     * The ii root value.
     */
    public static final String HEALTH_CARE_PROVIDER_ROOT = "2.16.840.1.113883.3.26.4.4.2";

    /**
     * The identifier name for.
     */
    public static final String HEALTH_CARE_FACILITY_IDENTIFIER_NAME = "NCI health care facility identifier";

    /**
     * The ii root value.
     */
    public static final String HEALTH_CARE_FACILITY_ROOT = "2.16.840.1.113883.3.26.4.4.3";

    /**
     * The identifier name for.
     */
    public static final String OVERSIGHT_COMMITTEE_IDENTIFIER_NAME = "NCI oversight committee identifier";

    /**
     * The ii root value.
     */
    public static final String OVERSIGHT_COMMITTEE_ROOT = "2.16.840.1.113883.3.26.4.4.4";

    /**
     * The identifier name for.
     */
    public static final String RESEARCH_ORG_IDENTIFIER_NAME = "NCI Research Organization identifier";

    /**
     * The ii root value.
     */
    public static final String RESEARCH_ORG_ROOT = "2.16.840.1.113883.3.26.4.4.5";

    /**
     * The identifier name for.
     */
    public static final String IDENTIFIED_ORG_IDENTIFIER_NAME = "Identified org identifier";

    /**
     * The ii root value.
     */
    public static final String IDENTIFIED_ORG_ROOT = "2.16.840.1.113883.3.26.4.4.6";

    /**
     * The identifier name for.
     */
    public static final String IDENTIFIED_PERSON_IDENTIFIER_NAME = "Identified person identifier";

    /**
     * The ii root value.
     */
    public static final String IDENTIFIED_PERSON_ROOT = "2.16.840.1.113883.3.26.4.4.7";
    /**
     * The identifier name for.
     */
    public static final String ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME = "Organizational contact identifier";

    /**
     * The ii root value.
     */
    public static final String ORGANIZATIONAL_CONTACT_ROOT = "2.16.840.1.113883.3.26.4.4.8";


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
     * converts to Po Org Ii Ii.
     * @param id id
     * @return Ii
     */
    public static Ii converToIdentifiedEntityIi(String id) {
        Ii ii = convertToIi(id);
        //ii.setIdentifierName(CTEP_IDENTIFIED_NAME);
        ii.setRoot("Cancer Therapy Evaluation Program");
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
        ii.setRoot(HEALTH_CARE_PROVIDER_ROOT);
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
        ii.setRoot(HEALTH_CARE_FACILITY_ROOT);
        return ii;
    }

    /**
     * converts to Po crs contact  Ii Ii.
     * @param id id
     * @return Ii
     */
    public static Ii converToPoOversightCommitteeIi(String id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(OVERSIGHT_COMMITTEE_IDENTIFIER_NAME);
        ii.setRoot(OVERSIGHT_COMMITTEE_ROOT);
        return ii;
    }

}
