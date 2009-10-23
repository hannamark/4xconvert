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
package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.pa.util.PAConstants;



/**
 * utility method for converting Ii and Id.
 * 
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({ "PMD.TooManyMethods" , "PMD.ExcessiveClassLength"  })
public class IiConverter {
    
    /** Study Protocol Root. * */
    public static final String STUDY_PROTOCOL_ROOT = "2.16.840.1.113883.3.26.4.3";
    
    /** Study Protocol Identifier. */
    public static final String STUDY_PROTOCOL_IDENTIFIER_NAME = "NCI study protocol entity identifier";
    
    /** Study Outcome Measure Root. * */
    public static final String STUDY_OUTCOME_MEASURE_ROOT = "2.16.840.1.113883.3.26.4.3.1";
    
    /** Study Outcome Measure Identifier. */
    public static final String STUDY_OUTCOME_MEASURE_IDENTIFIER_NAME = "NCI study outcome measure entity identifier";
    
    /** Study Ind Ide Root. * */
    public static final String STUDY_IND_IDE_ROOT = "2.16.840.1.113883.3.26.4.3.2";
    
    /** Study Ind Ide Identifier. */
    public static final String STUDY_IND_IDE_IDENTIFIER_NAME = "NCI study ind ide entity identifier";
    
    /** Arm Root. * */
    public static final String ARM_ROOT = "2.16.840.1.113883.3.26.4.3.3";
    
    /** Arm Identifier. */
    public static final String ARM_IDENTIFIER_NAME = "NCI arm entity identifier";
    
    /** Stratum Group Root. * */
    public static final String STRATUM_GROUP_ROOT = "2.16.840.1.113883.3.26.4.3.4";
    
    /** Stratum Group Identifier. */
    public static final String STRATUM_GROUP_IDENTIFIER_NAME = "NCI stratum group entity identifier";
    
    /** Study Overall Status Root. * */
    public static final String STUDY_OVERALL_STATUS_ROOT = "2.16.840.1.113883.3.26.4.3.5";
    
    /** Study Overall Status Identifier. */
    public static final String STUDY_OVERALL_STATUS_IDENTIFIER_NAME = "NCI study overall status entity identifier";
    
    /** Activity Root. * */
    public static final String ACTIVITY_ROOT = "2.16.840.1.113883.3.26.4.3.6";
    
    /** Activity Identifier. */
    public static final String ACTIVITY_IDENTIFIER_NAME = "NCI activity entity identifier";
    
    /** Study Resourcing Root. * */
    public static final String STUDY_RESOURCING_ROOT = "2.16.840.1.113883.3.26.4.3.7";
    
    /** Study Resourcing Identifier. */
    public static final String STUDY_RESOURCING_IDENTIFIER_NAME = "NCI study resourcing entity identifier";
    
    /** Document Root. * */
    public static final String DOCUMENT_ROOT = "2.16.840.1.113883.3.26.4.3.8";
    
    /** Document Identifier. */
    public static final String DOCUMENT_WORKFLOW_STATUS_IDENTIFIER_NAME = "NCI document work flow status identifier";
    
    /** Document Root. * */
    public static final String DOCUMENT_WORKFLOW_STATUS_ROOT = "2.16.840.1.113883.3.26.4.3.9";
    
    /** Document Identifier. */
    public static final String DOCUMENT_IDENTIFIER_NAME = "NCI document entity identifier";
    
    /** The identifier name for. */
    public static final String STUDY_RELATIONSHIP_IDENTIFIER_NAME = "Study Relationship identifier";

    /** The ii root value. */
    public static final String STUDY_RELATIONSHIP_ROOT = "2.16.840.1.113883.3.26.4.3.10";

    /** The identifier name for. */
    public static final String STUDY_DISEASE_IDENTIFIER_NAME = "Study Disease identifier";

    /** The ii root value. */
    public static final String STUDY_DISEASE_ROOT = "2.16.840.1.113883.3.26.4.3.11";

    /** The identifier name for. */
    public static final String STUDY_RECRUITMENT_STATUS_IDENTIFIER_NAME = "Study recruitment status identifier";

    /** The ii root value. */
    public static final String STUDY_RECRUITMENT_STATUS_ROOT = "2.16.840.1.113883.3.26.4.3.12";

    /** The identifier name for. */
    public static final String PLANNED_ACTIVITY_IDENTIFIER_NAME = "Planned Activity identifier";

    /** The ii root value. */
    public static final String PLANNED_ACTIVITY_ROOT = "2.16.840.1.113883.3.26.4.3.13";
 
    /** The identifier name for. */
    public static final String STUDY_ONHOLD_IDENTIFIER_NAME = "Study OnHold identifier";

    /** The ii root value. */
    public static final String STUDY_ONHOLD_ROOT = "2.16.840.1.113883.3.26.4.3.14";
   
    /** The identifier name for org ii's. */
    public static final String ORG_IDENTIFIER_NAME = "NCI organization entity identifier";

    /** The identifier name for org ii's. */
    public static final String ORG_PA_IDENTIFIER_NAME = "NCI-Pa organization entity identifier";

    /** The ii root value for orgs. */
    public static final String ORG_ROOT = "2.16.840.1.113883.3.26.4.2";

    /** The identifier name for person ii's. */
    public static final String PERSON_IDENTIFIER_NAME = "NCI person entity identifier";

    /** The identifier name for person ii's. */
    public static final String PERSON_PA_IDENTIFIER_NAME = "NCI-Pa person entity identifier";
    
    /** The ii root value for people. */
    public static final String PERSON_ROOT = "2.16.840.1.113883.3.26.4.1";

    /** The identifier name for. */
    public static final String CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME = "NCI clinical research staff identifier";

    /** The ii root value. */
    public static final String CLINICAL_RESEARCH_STAFF_ROOT = "2.16.840.1.113883.3.26.4.4.1";

     /** The identifier name for. */
    public static final String HEALTH_CARE_PROVIDER_IDENTIFIER_NAME = "NCI health care provider identifier";

    /** The ii root value. */
    public static final String HEALTH_CARE_PROVIDER_ROOT = "2.16.840.1.113883.3.26.4.4.2";

    /** The identifier name for. */
    public static final String HEALTH_CARE_FACILITY_IDENTIFIER_NAME = "NCI health care facility identifier";

    /** The ii root value. */
    public static final String HEALTH_CARE_FACILITY_ROOT = "2.16.840.1.113883.3.26.4.4.3";

    /** The identifier name for. */
    public static final String OVERSIGHT_COMMITTEE_IDENTIFIER_NAME = "NCI oversight committee identifier";

    /** The ii root value. */
    public static final String OVERSIGHT_COMMITTEE_ROOT = "2.16.840.1.113883.3.26.4.4.4";

    /** The identifier name for. */
    public static final String RESEARCH_ORG_IDENTIFIER_NAME = "NCI Research Organization identifier";

    /** The ii root value. */
    public static final String RESEARCH_ORG_ROOT = "2.16.840.1.113883.3.26.4.4.5";

    /** The identifier name for. */
    public static final String IDENTIFIED_ORG_IDENTIFIER_NAME = "Identified org identifier";

    /** The ii root value. */
    public static final String IDENTIFIED_ORG_ROOT = "2.16.840.1.113883.3.26.4.4.6";

    /** The identifier name for. */
    public static final String IDENTIFIED_PERSON_IDENTIFIER_NAME = "Identified person identifier";

    /** The ii root value. */
    public static final String IDENTIFIED_PERSON_ROOT = "2.16.840.1.113883.3.26.4.4.7";
    
    /** The identifier name for. */
    public static final String ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME = "Organizational contact identifier";

    /** The ii root value. */
    public static final String ORGANIZATIONAL_CONTACT_ROOT = "2.16.840.1.113883.3.26.4.4.8";
       /** The identifier name for. */
    public static final String STUDY_REGULATORY_AUTHORITY_IDENTIFIER_NAME = "Study Regulatory authority identifier";

    /** The ii root value. */
    public static final String STUDY_REGULATORY_AUTHORITY_ROOT = "2.16.840.1.113883.3.26.4.4.9";
    /** The identifier name for. */
    public static final String PERSON_RESOURCE_PROVIDER_IDENTIFIER_NAME = "Person Resource Provider identifier";

    /** The ii root value. */
    public static final String PERSON_RESOURCE_PROVIDER_ROOT = "UID.for.nci.role.personresourceprovider";

    /** The identifier name for. */
    public static final String ORG_RESOURCE_PROVIDER_IDENTIFIER_NAME = "Org Resource Provider identifier";

    /** The ii root value. */
    public static final String ORG_RESOURCE_PROVIDER_ROOT = "UID.for.nci.role.orgresourceprovider";

    /** The identifier name for. */
    public static final String QUALIFIED_ENTITY_IDENTIFIER_NAME = "Qualified entity identifier";

    /** The ii root value. */
    public static final String QUALIFIED_ENTITY_ROOT = "UID.for.nci.role.qualifiedentity";
    
    /** The CTEP Person ii root value. */
    public static final String CTEP_PERSON_IDENTIFIER_ROOT = 
                                    "Cancer Therapy Evaluation Program Person Other Identifier";
    /** The CTEP Organization ii root value. */
    public static final String CTEP_ORG_IDENTIFIER_ROOT = 
                                    "2.16.840.1.113883.3.26.6.2";
    /** The Constant STUDY_CONTACT_IDENTIFIER_NAME. */
    public static final String STUDY_CONTACT_IDENTIFIER_NAME = "Study Contact identifier";
    
    /** The Constant STUDY_CONTACT_ROOT. */
    public static final String STUDY_CONTACT_ROOT = "2.16.840.1.113883.3.26.4.5.1";
    
    /** The Constant STUDY_SITE_IDENTIFIER_NAME. */
    public static final String STUDY_SITE_IDENTIFIER_NAME = "Study Site identifier";
    
    /** The Constant STUDY_SITE_ROOT. */
    public static final String STUDY_SITE_ROOT = "2.16.840.1.113883.3.26.4.5.2";
    
    /** The Constant STUDY_SITE_CONTACT_IDENTIFIER_NAME. */
    public static final String STUDY_SITE_CONTACT_IDENTIFIER_NAME = "Study Site Contact identifier";
    
    /** The Constant STUDY_SITE_CONTACT_ROOT. */
    public static final String STUDY_SITE_CONTACT_ROOT = "2.16.840.1.113883.3.26.4.5.3";
    
    
    /** The Constant STUDY_SITE_ACCURAL_STATUS_IDENTIFIER_NAME. */
    public static final String STUDY_SITE_ACCURAL_STATUS_IDENTIFIER_NAME = "Study site accural status identifier";
    
   
    /** The Constant STUDY_SITE_ACCURAL_STATUS_ROOT. */
    public static final String STUDY_SITE_ACCURAL_STATUS_ROOT = "2.16.840.1.113883.3.26.4.5.4";
    
    
    /** The identifier name for. */
    public static final String STUDY_OBJECTIVE_IDENTIFIER_NAME = "Study Objective identifier";

    /** The ii root value. */
    public static final String STUDY_OBJECTIVE_ROOT = "2.16.840.1.113883.3.26.4.3.15";
 
    /** The identifier name for. */
    public static final String STUDY_MILESTONE_IDENTIFIER_NAME = "Study Milestone identifier";

    /** The ii root value. */
    public static final String STUDY_MILESTONE_ROOT = "2.16.840.1.113883.3.26.4.3.16";
    
    /** The identifier name for. */
    public static final String REGULATORY_AUTHORITY_IDENTIFIER_NAME = "Regulatory authority identifier";

    /** The ii root value. */
    public static final String REGULATORY_AUTHORITY_ROOT = "2.16.840.1.113883.3.26.4.4.17";

    
    /** The identifier name for. */
    public static final String COUNTRY_IDENTIFIER_NAME = "Country identifier";

    /** The ii root value. */
    public static final String COUNTRY_ROOT = "2.16.840.1.113883.3.26.4.4.18";

    private static final String STUDY_SITE_OVERALL_STATUS_IDENTIFIER_NAME = "Study site overall status identifier";

    private static final String STUDY_SITE_OVERALL_STATUS_ROOT = "2.16.840.1.113883.3.26.4.5.5";
    
    private static final String PO_PATIENT_IDENTIFIER_NAME = "PO Patient identifier";

    private static final String PO_PATIENT_ROOT = "2.16.840.1.113883.3.26.4.5.6";


    
    /**
     * Convert to ii.
     * @param id id
     * @return Ii ii
     */
    public static Ii convertToIi(Long id) {
        Ii ii = new Ii();
        if (id == null) {
            ii.setNullFlavor(NullFlavor.NI);
        } else {
            ii.setExtension(id.toString());
            ii.setIdentifierName(PAConstants.PA_INTERNAL);
        }
        return ii;
    }
    
    
    /**
     * Convert to ii.
     * @param str string
     * @return Ii
     */
    public static Ii convertToIi(String str) {
        Ii ii = new Ii();
        if (str == null) {
            ii.setNullFlavor(NullFlavor.NI);
        } else {
            ii.setExtension(str);
            ii.setIdentifierName(PAConstants.PA_INTERNAL);
        }
        return ii;
        
    }
    
    /**
     * Convert to long.
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
     * Convert to string.
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
     * converts to StudyProtocol Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToStudyProtocolIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_PROTOCOL_IDENTIFIER_NAME);
        ii.setRoot(STUDY_PROTOCOL_ROOT);
        return ii;
    }

    /**
     * converts to StudyOutcomeMeasure Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToStudyOutcomeMeasureIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_OUTCOME_MEASURE_IDENTIFIER_NAME);
        ii.setRoot(STUDY_OUTCOME_MEASURE_ROOT);
        return ii;
    }

    /**
     * converts to StudyIndIde Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToStudyIndIdeIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_IND_IDE_IDENTIFIER_NAME);
        ii.setRoot(STUDY_IND_IDE_ROOT);
        return ii;
    }

    /**
     * converts to Arm Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToArmIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(ARM_IDENTIFIER_NAME);
        ii.setRoot(ARM_ROOT);
        return ii;
    }

    /**
     * converts to StratumGroup Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToStratumGroupIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STRATUM_GROUP_IDENTIFIER_NAME);
        ii.setRoot(STRATUM_GROUP_ROOT);
        return ii;
    }

    /**
     * converts to StudyOverallStatus Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToStudyOverallStatusIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_OVERALL_STATUS_IDENTIFIER_NAME);
        ii.setRoot(STUDY_OVERALL_STATUS_ROOT);
        return ii;
    }

    /**
     * converts to Activity Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToActivityIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(ACTIVITY_IDENTIFIER_NAME);
        ii.setRoot(ACTIVITY_ROOT);
        return ii;
    }

    /**
     * converts to StudyResourcing Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToStudyResourcingIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_RESOURCING_IDENTIFIER_NAME);
        ii.setRoot(STUDY_RESOURCING_ROOT);
        return ii;
    }

    /**
     * converts to Document Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToDocumentIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(DOCUMENT_IDENTIFIER_NAME);
        ii.setRoot(DOCUMENT_ROOT);
        return ii;
    }

    /**
     * converts to Po Person Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToPoPersonIi(String id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(PERSON_IDENTIFIER_NAME);
        ii.setRoot(PERSON_ROOT);
        return ii;
    }
    
    /**
     * converts to Po Person Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToPaPersonIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(PERSON_PA_IDENTIFIER_NAME);
        ii.setRoot(PERSON_ROOT);
        return ii;
    }

    /**
     * converts to Po Org Ii Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToPoOrganizationIi(String id) {
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
    public static Ii convertToPaOrganizationIi(long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(ORG_PA_IDENTIFIER_NAME);
        ii.setRoot(ORG_ROOT);
        return ii;
    }

    /**
     * converts to Po Org Ii Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToIdentifiedEntityIi(String id) {
        Ii ii = convertToIi(id);
        //ii.setIdentifierName(CTEP_IDENTIFIED_NAME);
        ii.setRoot("Cancer Therapy Evaluation Program");
        return ii;
    }
    
    /**
     * converts to Identified PO Org Ii Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToIdentifiedOrgEntityIi(String id) {
        Ii ii = convertToIi(id);
        ii.setRoot(CTEP_ORG_IDENTIFIER_ROOT);
        return ii;
    }
    
    /**
     * converts to Identified PO Person Ii Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToIdentifiedPersonEntityIi(String id) {
        Ii ii = convertToIi(id);
        ii.setRoot(CTEP_PERSON_IDENTIFIER_ROOT);
        return ii;
    }
    
    /**
     * converts to Po Org contact  Ii Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToPoOrganizationalContactIi(String id) {
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
    public static Ii convertToPoClinicalResearchStaffIi(String id) {
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
    public static Ii convertToPoHealtcareProviderIi(String id) {
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
    public static Ii convertToPoHealthCareFacilityIi(String id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
        ii.setRoot(HEALTH_CARE_FACILITY_ROOT);
        return ii;
    }


    /**
     * converts to Po Research Organization Ii Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToPoResearchOrganizationIi(String id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(RESEARCH_ORG_IDENTIFIER_NAME);
        ii.setRoot(RESEARCH_ORG_ROOT);
        return ii;
    }
    

    /**
     * converts to Po crs contact  Ii Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToPoOversightCommitteeIi(String id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(OVERSIGHT_COMMITTEE_IDENTIFIER_NAME);
        ii.setRoot(OVERSIGHT_COMMITTEE_ROOT);
        return ii;
    }
    
    /**
     * Conver to study relationship ii.
     * @param id the id
     * @return the ii
     */
    public static Ii convertToStudyRelationshipIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_RELATIONSHIP_IDENTIFIER_NAME);
        ii.setRoot(STUDY_RELATIONSHIP_ROOT);
        return ii;
    }
    
    /**
     * Convert to study contact ii.
     * @param id the id
     * @return the ii
     */
    public static Ii convertToStudyContactIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_CONTACT_IDENTIFIER_NAME);
        ii.setRoot(STUDY_CONTACT_ROOT);
        return ii;
    }
    
    /**
     * Convert to study site contact ii.
     * @param id the id
     * @return the ii
     */
    public static Ii convertToStudySiteContactIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_SITE_CONTACT_IDENTIFIER_NAME);
        ii.setRoot(STUDY_SITE_CONTACT_ROOT);
        return ii;
    }
    
    /**
     * Convert to study site ii.
     * @param id the id
     * @return the ii
     */
    public static Ii convertToStudySiteIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_SITE_IDENTIFIER_NAME);
        ii.setRoot(STUDY_SITE_ROOT);
        return ii;
    }
    
    /**
     * Convert to study recruitment status ii.
     * @param id the id
     * @return the ii
     */
    public static Ii convertToStudyRecruitmentStatusIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_RECRUITMENT_STATUS_IDENTIFIER_NAME);
        ii.setRoot(STUDY_RECRUITMENT_STATUS_ROOT);
        return ii;
    }
    
    /**
     * Convert to regulatory authority ii.
     * @param id the id
     * @return the ii
     */
    public static Ii convertToStudyRegulatoryAuthorityIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_REGULATORY_AUTHORITY_IDENTIFIER_NAME);
        ii.setRoot(STUDY_REGULATORY_AUTHORITY_ROOT);
        return ii;
    }
    
    /**
     * Convert to study disease ii.
     * @param id the id
     * @return the ii
     */
    public static Ii convertToStudyDiseaseIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_DISEASE_IDENTIFIER_NAME);
        ii.setRoot(STUDY_DISEASE_ROOT);
        return ii;
    }
    
    /**
     * converts to Document Ii.
     * @param id id
     * @return Ii
     */
    public static Ii convertToDocumentWorkFlowStatusIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(DOCUMENT_WORKFLOW_STATUS_IDENTIFIER_NAME);
        ii.setRoot(DOCUMENT_WORKFLOW_STATUS_ROOT);
        return ii;
    }
    
    /**
     * Convert to study site accural status ii.
     * @param id the id
     * @return the ii
     */
    public static Ii convertToStudySiteAccuralStatusIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_SITE_ACCURAL_STATUS_IDENTIFIER_NAME);
        ii.setRoot(STUDY_SITE_ACCURAL_STATUS_ROOT);
        return ii;
    }
    
    /**
     * Convert to study on hold ii.
     * @param id the id
     * @return the ii
     */
    public static Ii convertToStudyOnHoldIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_ONHOLD_IDENTIFIER_NAME);
        ii.setRoot(STUDY_ONHOLD_ROOT);
        return ii;
    }
    
    /**
     * Convert to planned activity ii.
     * @param id the id
     * @return the ii
     */
    public static Ii convertToPlannedActivityIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(PLANNED_ACTIVITY_IDENTIFIER_NAME);
        ii.setRoot(PLANNED_ACTIVITY_ROOT);
        return ii;
    }
    /**
     * Convert to study Objective ii.
     * @param id the id
     * @return the ii
     */
    public static Ii convertToStudyObjectiveIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_OBJECTIVE_IDENTIFIER_NAME);
        ii.setRoot(STUDY_OBJECTIVE_ROOT);
        return ii;
    }
    /**
     * Convert to study Milestone ii.
     * @param id the id
     * @return the ii
     */
    public static Ii convertToStudyMilestoneIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_MILESTONE_IDENTIFIER_NAME);
        ii.setRoot(STUDY_MILESTONE_ROOT);
        return ii;
    }
    
    /**
     * Convert to regulatory authority ii.
     * @param id the id
     * @return the ii
     */
    public static Ii convertToRegulatoryAuthorityIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(REGULATORY_AUTHORITY_IDENTIFIER_NAME);
        ii.setRoot(REGULATORY_AUTHORITY_ROOT);
        return ii;
    }
    
    /**
     * Convert to Country authority ii.
     * @param id the id
     * @return the ii
     */
    public static Ii convertToCountryIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(COUNTRY_IDENTIFIER_NAME);
        ii.setRoot(COUNTRY_ROOT);
        return ii;
    }

    /**
     * Convert to StudySiteOverallStatus Ii.
     * @param id id
     * @return ii
     */
    public static Ii convertToStudySiteOverallStatusIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(STUDY_SITE_OVERALL_STATUS_IDENTIFIER_NAME);
        ii.setRoot(STUDY_SITE_OVERALL_STATUS_ROOT);
        return ii;

    }
    
    /**
     * Convert to po patient ii.
     * @param id the id
     * @return the ii
     */
    public static Ii convertToPOPatientIi(Long id) {
        Ii ii = convertToIi(id);
        ii.setIdentifierName(PO_PATIENT_IDENTIFIER_NAME);
        ii.setRoot(PO_PATIENT_ROOT);
        return ii;

    }

}
