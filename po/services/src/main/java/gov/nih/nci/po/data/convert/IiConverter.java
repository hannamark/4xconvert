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

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.services.PoIsoConstraintException;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author gax
 */
// we need these methods and they should all be in this one file.
@SuppressWarnings("PMD.TooManyMethods")
public class IiConverter extends AbstractXSnapshotConverter<Ii> {
    private static final String ROOT_NOT_ALLOWED_ERR_MSG = "The ii.root value '%s' is not allowed.";
    private static final String IDENT_NAME_NOT_ALLOWED_ERR_MSG = "The ii.identifierName value '%s' is not allowed.";

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <TO> TO convert(Class<TO> returnClass, Ii value) {
        if (returnClass == Long.class) {
            return (TO) convertToLong(value);
        } else if (returnClass == Person.class) {
            return (TO) convertToPerson(value);
        } else if (returnClass == Organization.class) {
            return (TO) convertToOrg(value);
        } else if (returnClass == Family.class) {
            return (TO) convertToFamily(value);
        } else if (returnClass == FamilyOrganizationRelationship.class) {
            return (TO) convertToFamilyOrgRel(value);
        }

        throw new UnsupportedOperationException(returnClass.getName());
    }

    /**
     * A converter to translate a Ii to a specific Correlation.
     * NOTE: This could be generalized to handle any PersistentObject types
     */
    public static class CorrelationIiConverter extends IiConverter {
        private static Map<String, String> rtIName = new HashMap<String, String>();
        static {
            rtIName.put(IdConverter.CLINICAL_RESEARCH_STAFF_ROOT, IdConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
            rtIName.put(IdConverter.HEALTH_CARE_FACILITY_ROOT, IdConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
            rtIName.put(IdConverter.HEALTH_CARE_PROVIDER_ROOT, IdConverter.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME);
            rtIName.put(IdConverter.IDENTIFIED_ORG_ROOT, IdConverter.IDENTIFIED_ORG_IDENTIFIER_NAME);
            rtIName.put(IdConverter.IDENTIFIED_PERSON_ROOT, IdConverter.IDENTIFIED_PERSON_IDENTIFIER_NAME);
            rtIName.put(IdConverter.ORGANIZATIONAL_CONTACT_ROOT, IdConverter.ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME);
            rtIName.put(IdConverter.OVERSIGHT_COMMITTEE_ROOT, IdConverter.OVERSIGHT_COMMITTEE_IDENTIFIER_NAME);
            rtIName.put(IdConverter.RESEARCH_ORG_ROOT, IdConverter.RESEARCH_ORG_IDENTIFIER_NAME);
            rtIName.put(IdConverter.PATIENT_ROOT, IdConverter.PATIENT_IDENTIFIER_NAME);
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public <TO> TO convert(Class<TO> returnClass, Ii value) {
            if (!Correlation.class.isAssignableFrom(returnClass)) {
                throw new UnsupportedOperationException(returnClass.getName());
            }
            return convertHelper(returnClass, value);
        }

        @SuppressWarnings("unchecked")
        private <TO> TO convertHelper(Class<TO> returnClass, Ii value) {
            if (value == null || value.getNullFlavor() != null) {
                return null;
            }

            enforcePoIsoConstraints(value);

            Long id = Long.valueOf(value.getExtension());
            if (!rtIName.keySet().contains(value.getRoot())) {
                throw new PoIsoConstraintException(String.format(ROOT_NOT_ALLOWED_ERR_MSG, value.getRoot()));
            }
            if (!rtIName.get(value.getRoot()).equals(value.getIdentifierName())) {
                throw new PoIsoConstraintException(String.format(IDENT_NAME_NOT_ALLOWED_ERR_MSG,
                        value.getIdentifierName()));
            }
            return (TO) PoRegistry.getGenericService().getPersistentObject((Class<PersistentObject>) returnClass, id);
        }
    }

    /**
     * @param value an II used to identify PO entities.
     * @return a long suitable for a hibernate entity Id
     */
    public static Long convertToLong(Ii value) {
        if (value == null || value.getNullFlavor() != null) {
            return null;
        }

        enforcePoIsoConstraints(value);

        // todo https://jira.5amsolutions.com/browse/PO-411
        String root = value.getRoot();
        if (root == null) {
            throw new IllegalArgumentException("root is required");
        }

        return Long.valueOf(value.getExtension());
    }

    /**
     * @param value an II used to identify PO entities.
     * @return a long suitable for a hibernate entity Id
     */
    public static Long convertPatientToLong(Ii value) {
        if (value == null || value.getNullFlavor() != null) {
            return null;
        }

        enforcePoIsoConstraints(value);

        // todo https://jira.5amsolutions.com/browse/PO-411
        String root = value.getRoot();
        if (root == null) {
            throw new IllegalArgumentException("root is required");
        }

        return Long.valueOf(value.getExtension().substring(IdConverter.PATIENT_PREFIX.length()));
    }

    private static void enforcePoIsoConstraints(Ii value) {
        if (StringUtils.isEmpty(value.getExtension())) {
            throw new PoIsoConstraintException("ii.extension is required if a null flavor is not provided.");
        }
    }

    /**
     * Convert the ii to a person by loading from the db.
     * @param value the ii
     * @return the person
     */
    public static Person convertToPerson(Ii value) {
        if (value == null || value.getNullFlavor() != null) {
            return null;
        }

        enforcePoIsoConstraints(value);

        Long id = Long.valueOf(value.getExtension());
        if (!IdConverter.PERSON_ROOT.equals(value.getRoot())) {
            throw new PoIsoConstraintException(String.format(ROOT_NOT_ALLOWED_ERR_MSG, value.getRoot()));
        }
        if (!IdConverter.PERSON_IDENTIFIER_NAME.equals(value.getIdentifierName())) {
            throw new PoIsoConstraintException(String.format(IDENT_NAME_NOT_ALLOWED_ERR_MSG,
                    value.getIdentifierName()));
        }
        return PoRegistry.getPersonService().getById(id);
    }

    /**
     * Convert the ii to an org by loading from the db.
     * @param value the ii
     * @return the Org.
     */
    public static Organization convertToOrg(Ii value) {
        if (value == null || value.getNullFlavor() != null) {
            return null;
        }

        enforcePoIsoConstraints(value);

        Long id = Long.valueOf(value.getExtension());
        if (!IdConverter.ORG_ROOT.equals(value.getRoot())) {
            throw new PoIsoConstraintException(String.format(ROOT_NOT_ALLOWED_ERR_MSG, value.getRoot()));
        }
        if (!IdConverter.ORG_IDENTIFIER_NAME.equals(value.getIdentifierName())) {
            throw new PoIsoConstraintException(String.format(IDENT_NAME_NOT_ALLOWED_ERR_MSG,
                    value.getIdentifierName()));
        }
        return PoRegistry.getOrganizationService().getById(id);
    }
    
    /**
     * Convert the ii to a Family by loading from the db.
     * @param value the ii
     * @return the Family.
     */
    public static Family convertToFamily(Ii value) {
        if (value == null || value.getNullFlavor() != null) {
            return null;
        }

        enforcePoIsoConstraints(value);

        Long id = Long.valueOf(value.getExtension());
        if (!IdConverter.FAMILY_ROOT.equals(value.getRoot())) {
            throw new PoIsoConstraintException(String.format(ROOT_NOT_ALLOWED_ERR_MSG, value.getRoot()));
        }
        if (!IdConverter.FAMILY_IDENTIFIER_NAME.equals(value.getIdentifierName())) {
            throw new PoIsoConstraintException(String.format(IDENT_NAME_NOT_ALLOWED_ERR_MSG,
                    value.getIdentifierName()));
        }
        return PoRegistry.getFamilyService().getById(id);
    }
    
    /**
     * Convert the ii to a FamilyOrganizationRelationship by loading from the db.
     * @param value the ii
     * @return the FamilyOrganizationRelationship.
     */
    public static FamilyOrganizationRelationship convertToFamilyOrgRel(Ii value) {
        if (value == null || value.getNullFlavor() != null) {
            return null;
        }

        enforcePoIsoConstraints(value);

        Long id = Long.valueOf(value.getExtension());
        if (!IdConverter.FAMILY_ORG_REL_ROOT.equals(value.getRoot())) {
            throw new PoIsoConstraintException(String.format(ROOT_NOT_ALLOWED_ERR_MSG, value.getRoot()));
        }
        if (!IdConverter.FAMILY_ORG_REL_IDENTIFIER_NAME.equals(value.getIdentifierName())) {
            throw new PoIsoConstraintException(String.format(IDENT_NAME_NOT_ALLOWED_ERR_MSG,
                    value.getIdentifierName()));
        }
        return PoRegistry.getFamilyOrganizationRelationshipService().getById(id);
    }


    /**
     * Converts a single Ii into a DSet containing only that Ii.
     * @param value Ii to add
     * @return DSet containing the given Ii
     */
    public static DSet<Ii> convertToDsetIi(Ii value) {
        DSet<Ii> dset = new DSet<Ii>();
        dset.setItem(new LinkedHashSet<Ii>());
        dset.getItem().add(value);
        return dset;
    }

    /**
     * Convert from array to set of ids.
     * @param ids array of Ii.
     * @return set of Long.
     */
    public static Set<Long> convertToLongs(Ii[] ids) {
        Set<Long> longIds = new HashSet<Long>();
        for (Ii id : ids) {
            longIds.add(IiConverter.convertToLong(id));
        }
        return longIds;
    }
}
