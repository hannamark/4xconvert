/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package gov.nih.nci.po.data.convert;

import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;

/**
 *
 * @author gax
 */
@SuppressWarnings("PMD.TooManyMethods")
public class IdConverter extends AbstractXSnapshotConverter<Long> {

    /**
     * The base of all COPPA-related II roots.
     */
    public static final String BASE_ROOT = "2.16.840.1.113883.3.26.4";
    /**
     * The sfx of all COPPA-related structural role II roots.
     */
    public static final String STRUCTURAL_ROLE_SFX = ".4";

    /**
     * The base of all COPPA-related structural roles.
     */
    public static final String STRUCTURAL_ROLE_ROOT = BASE_ROOT + STRUCTURAL_ROLE_SFX;

    /**
     * The identifier name for org ii's.
     */
    public static final String ORG_IDENTIFIER_NAME = "NCI organization entity identifier";

    /**
     * The ii root value for orgs.
     */
    public static final String ORG_ROOT = BASE_ROOT + ".2";

    /**
     * The identifier name for person ii's.
     */
    public static final String PERSON_IDENTIFIER_NAME = "NCI person entity identifier";

    /**
     * The ii root value for people.
     */
    public static final String PERSON_ROOT = BASE_ROOT + ".1";
    
    /**
     * The identifier name for family.
     */
    public static final String FAMILY_IDENTIFIER_NAME = "Family identifier";
    
    /**
     * The ii base root value for family.
     */
    public static final String FAMILY_BASE_ROOT = BASE_ROOT + ".6";
    
    /**
     * The ii base root value for family.
     */
    public static final String FAMILY_ROOT = FAMILY_BASE_ROOT + ".1";

    /**
     * The identifier name for.
     */
    public static final String CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME = "NCI clinical research staff identifier";

    /**
     * The ii root value.
     */
    public static final String CLINICAL_RESEARCH_STAFF_ROOT = STRUCTURAL_ROLE_ROOT + ".1";

    /**
     * The identifier name for.
     */
    public static final String HEALTH_CARE_PROVIDER_IDENTIFIER_NAME = "NCI health care provider identifier";

    /**
     * The ii root value.
     */
    public static final String HEALTH_CARE_PROVIDER_ROOT = STRUCTURAL_ROLE_ROOT + ".2";

    /**
     * The identifier name for.
     */
    public static final String HEALTH_CARE_FACILITY_IDENTIFIER_NAME = "NCI health care facility identifier";

    /**
     * The ii root value.
     */
    public static final String HEALTH_CARE_FACILITY_ROOT = STRUCTURAL_ROLE_ROOT + ".3";

    /**
     * The identifier name for.
     */
    public static final String OVERSIGHT_COMMITTEE_IDENTIFIER_NAME = "NCI oversight committee identifier";

    /**
     * The ii root value.
     */
    public static final String OVERSIGHT_COMMITTEE_ROOT = STRUCTURAL_ROLE_ROOT + ".4";

    /**
     * The identifier name for.
     */
    public static final String RESEARCH_ORG_IDENTIFIER_NAME = "NCI Research Organization identifier";

    /**
     * The ii root value.
     */
    public static final String RESEARCH_ORG_ROOT = STRUCTURAL_ROLE_ROOT + ".5";

    /**
     * The identifier name for.
     */
    public static final String IDENTIFIED_ORG_IDENTIFIER_NAME = "Identified org identifier";

    /**
     * The ii root value.
     */
    public static final String IDENTIFIED_ORG_ROOT = STRUCTURAL_ROLE_ROOT + ".6";

    /**
     * The identifier name for.
     */
    public static final String IDENTIFIED_PERSON_IDENTIFIER_NAME = "Identified person identifier";

    /**
     * The ii root value.
     */
    public static final String IDENTIFIED_PERSON_ROOT = STRUCTURAL_ROLE_ROOT + ".7";
    /**
     * The identifier name for.
     */
    public static final String ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME = "Organizational contact identifier";

    /**
     * The ii root value.
     */
    public static final String ORGANIZATIONAL_CONTACT_ROOT = STRUCTURAL_ROLE_ROOT + ".8";
    /**
     * The identifier name for.
     */
    public static final String PATIENT_IDENTIFIER_NAME = "Patient identifier";
    
    /**
     * The ii root value.
     */
    public static final String PATIENT_ROOT = STRUCTURAL_ROLE_ROOT + ".9";
    
   /**
    * The identifier name for family organization relationship.
    */
   public static final String FAMILY_ORG_REL_IDENTIFIER_NAME = "Family Organization Relationship identifier";
   
   /**
    * The ii root value for family organization relationship.
    */
   public static final String FAMILY_ORG_REL_ROOT = FAMILY_BASE_ROOT + ".2";
    

    /**
     * The Patient to Person prefix string.
     */
    public static final String PATIENT_PREFIX = "PT";

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <TO> TO convert(Class<TO> returnClass, Long value) {
        if (returnClass == Ii.class) {
            return (TO) convertToIi(value);
        }

        throw new UnsupportedOperationException(returnClass.getName());
    }

    /**
     * @param value the ID to convert
     * @return a generic Ii without the root or identifierName set
     */
    public Ii convertToIi(Long value) {
        Ii iso = new Ii();
        if (value == null) {
            iso.setNullFlavor(NullFlavor.NI);
        } else {
            iso.setExtension(value.toString());
            iso.setDisplayable(true);
            iso.setScope(IdentifierScope.OBJ);
            // change me if hibernate IDs are no longer autogenerated
            iso.setReliability(IdentifierReliability.ISS);

        }

        return iso;
    }

    /**
     * convert the Id of an org.
     * @see ISOUtils#ID_ORG
     */
    public static class OrgIdConverter extends IdConverter {
        /** {@inheritDoc} */
        @Override
        public Ii convertToIi(Long value) {
            Ii iso = super.convertToIi(value);
            iso.setIdentifierName(ORG_IDENTIFIER_NAME);
            iso.setRoot(ORG_ROOT);
            return iso;
        }
    }
    
    /**
     * convert the Id of an family.
     */
    public static class FamilyIdConverter extends IdConverter {
        /** {@inheritDoc} */
        @Override
        public Ii convertToIi(Long value) {
            Ii iso = super.convertToIi(value);
            iso.setIdentifierName(FAMILY_IDENTIFIER_NAME);
            iso.setRoot(FAMILY_ROOT);
            return iso;
        }
    }
    
    /**
     * convert the Id of a family organizational relationship.
     */
    public static class FamilyOrganizationRelationshipIdConverter extends IdConverter {
        /** {@inheritDoc} */
        @Override
        public Ii convertToIi(Long value) {
            Ii iso = super.convertToIi(value);
            iso.setIdentifierName(FAMILY_ORG_REL_IDENTIFIER_NAME);
            iso.setRoot(FAMILY_ORG_REL_ROOT);
            return iso;
        }
    }

    /**
     * convert the Id of a person.
     * @see ISOUtils#ID_PERSON
     */
    public static class PersonIdConverter extends IdConverter {
        /** {@inheritDoc} */
        @Override
        public Ii convertToIi(Long value) {
            Ii iso = super.convertToIi(value);
            iso.setIdentifierName(PERSON_IDENTIFIER_NAME);
            iso.setRoot(IdConverter.PERSON_ROOT);
            return iso;
        }
    }

    /**
     * convert the id of a clinical research staff.
     * @author Scott Miller
     */
    public static class ClinicalResearchStaffIdConverter extends IdConverter {
        /**
         * {@inheritDoc}
         */
        @Override
        public Ii convertToIi(Long value) {
            Ii iso = super.convertToIi(value);
            iso.setIdentifierName(CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
            iso.setRoot(IdConverter.CLINICAL_RESEARCH_STAFF_ROOT);
            return iso;
        }
    }

    /**
     * convert the id of a clinical research staff.
     * @author Scott Miller
     */
    public static class HealthCareProviderIdConverter extends IdConverter {
        /**
         * {@inheritDoc}
         */
        @Override
        public Ii convertToIi(Long value) {
            Ii iso = super.convertToIi(value);
            iso.setIdentifierName(HEALTH_CARE_PROVIDER_IDENTIFIER_NAME);
            iso.setRoot(IdConverter.HEALTH_CARE_PROVIDER_ROOT);
            return iso;
        }
    }

    /**
     * convert the id of a clinical research staff.
     * @author Scott Miller
     */
    public static class HealthCareFacilityIdConverter extends IdConverter {
        /**
         * {@inheritDoc}
         */
        @Override
        public Ii convertToIi(Long value) {
            Ii iso = super.convertToIi(value);
            iso.setIdentifierName(HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
            iso.setRoot(IdConverter.HEALTH_CARE_FACILITY_ROOT);
            return iso;
        }
    }

    /**
     * convert the id of a clinical research staff.
     * @author Scott Miller
     */
    public static class ResearchOrganizationIdConverter extends IdConverter {
        /**
         * {@inheritDoc}
         */
        @Override
        public Ii convertToIi(Long value) {
            Ii iso = super.convertToIi(value);
            iso.setIdentifierName(RESEARCH_ORG_IDENTIFIER_NAME);
            iso.setRoot(IdConverter.RESEARCH_ORG_ROOT);
            return iso;
        }
    }

    /**
     * convert the id of a clinical research staff.
     * @author Scott Miller
     */
    public static class OversightCommitteeIdConverter extends IdConverter {
        /**
         * {@inheritDoc}
         */
        @Override
        public Ii convertToIi(Long value) {
            Ii iso = super.convertToIi(value);
            iso.setIdentifierName(OVERSIGHT_COMMITTEE_IDENTIFIER_NAME);
            iso.setRoot(IdConverter.OVERSIGHT_COMMITTEE_ROOT);
            return iso;
        }
    }

    /**
     * convert the id of a identfied organization.
     * @author Scott Miller
     */
    public static class IdentifiedOrganizationIdConverter extends IdConverter {
        /**
         * {@inheritDoc}
         */
        @Override
        public Ii convertToIi(Long value) {
            Ii iso = super.convertToIi(value);
            iso.setIdentifierName(IDENTIFIED_ORG_IDENTIFIER_NAME);
            iso.setRoot(IdConverter.IDENTIFIED_ORG_ROOT);
            return iso;
        }
    }

    /**
     * convert the id of an identified organization.
     * @author Scott Miller
     */
    public static class IdentifiedPersonIdConverter extends IdConverter {
        /**
         * {@inheritDoc}
         */
        @Override
        public Ii convertToIi(Long value) {
            Ii iso = super.convertToIi(value);
            iso.setIdentifierName(IDENTIFIED_PERSON_IDENTIFIER_NAME);
            iso.setRoot(IdConverter.IDENTIFIED_PERSON_ROOT);
            return iso;
        }
    }

    /**
     * convert the id of an organizational contact.
     * @author smatyas
     */
    public static class OrganizationalContactIdConverter extends IdConverter {
        /**
         * {@inheritDoc}
         */
        @Override
        public Ii convertToIi(Long value) {
            Ii iso = super.convertToIi(value);
            iso.setIdentifierName(ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME);
            iso.setRoot(IdConverter.ORGANIZATIONAL_CONTACT_ROOT);
            return iso;
        }
    }

    /**
     * convert the id of an patient.
     * @author mshestopalov
     */
    public static class PatientIdConverter extends IdConverter {
        /**
         * {@inheritDoc}
         */
        @Override
        public Ii convertToIi(Long value) {
            Ii iso = super.convertToIi(value);
            iso.setIdentifierName(PATIENT_IDENTIFIER_NAME);
            iso.setRoot(IdConverter.PATIENT_ROOT);
            return iso;
        }
    }

}
