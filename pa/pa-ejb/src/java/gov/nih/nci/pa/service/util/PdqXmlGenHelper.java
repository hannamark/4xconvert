/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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
package gov.nih.nci.pa.service.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author mshestopalov
 *
 */
@SuppressWarnings({ "PMD.TooManyMethods" })
public class PdqXmlGenHelper {

    /**
     * Get Po RO dto by Pa RO ii.
     * @param paRoIi pa org ii
     * @param corrUtils CorrelationUtils
     * @return po org dto
     * @throws PAException when error.
     */
    protected static ResearchOrganizationDTO getPoRODTOByPaRoIi(Ii paRoIi, CorrelationUtils corrUtils)
        throws PAException {
        ResearchOrganization paRo = corrUtils.getStructuralRoleByIi(paRoIi);
        Ii poRoIi = IiConverter.convertToPoResearchOrganizationIi(paRo.getIdentifier());
        ResearchOrganizationDTO roDTO;
        try {
            roDTO = PoRegistry.getResearchOrganizationCorrelationService().getCorrelation(poRoIi);
        } catch (NullifiedRoleException e) {
            throw new PAException("Nullified Exception for RO.Ii = " + poRoIi, e);
        }
        return roDTO;
    }

    /**
     * Get Po HCF dto by Pa HCF ii.
     * @param paHcfIi pa org ii
     * @param corrUtils CorrelationUtils
     * @return po org dto
     * @throws PAException when error.
     */
    protected static HealthCareFacilityDTO getPoHCFDTOByPaHcfIi(Ii paHcfIi, CorrelationUtils corrUtils)
        throws PAException {
        HealthCareFacility paHcf = corrUtils.getStructuralRoleByIi(paHcfIi);
        Ii poHcfIi = IiConverter.convertToPoHealthCareFacilityIi(paHcf.getIdentifier());
        HealthCareFacilityDTO hcfDTO;
        try {
            hcfDTO = PoRegistry.getHealthCareFacilityCorrelationService().getCorrelation(poHcfIi);
        } catch (NullifiedRoleException e) {
            throw new PAException("Nullified Exception for HCF.Ii = " + poHcfIi, e);
        }
        return hcfDTO;
    }

    /**
     * Get Po Org dto by Pa Org ii.
     * @param paOrgIi pa org ii
     * @param corrUtils CorrelationUtils
     * @return po org dto
     * @throws PAException when error.
     */
    protected static OrganizationDTO getPoOrgDTOByPaOrgIi(Ii paOrgIi, CorrelationUtils corrUtils)
    throws PAException {
        Organization paOrg = corrUtils.getPAOrganizationByIi(paOrgIi);
        Ii poOrgIi = IiConverter.convertToPoOrganizationIi(paOrg.getIdentifier());
        OrganizationDTO orgDTO;
        try {
            orgDTO = PoRegistry.getOrganizationEntityService().getOrganization(poOrgIi);
        } catch (NullifiedEntityException e) {
            throw new PAException("Nullified Exception for Org.Ii = " + poOrgIi, e);
        }
        return orgDTO;
    }

    /**
     * Get Po Org Contact dto by Pa Org Contact ii.
     * @param paOcIi pa org contact ii
     * @param corrUtils CorrelationUtils
     * @return po org dto
     * @throws PAException when error.
     */
    protected static OrganizationalContactDTO getPoOCDTOByPaOcIi(Ii paOcIi, CorrelationUtils corrUtils)
    throws PAException {
        OrganizationalContact paOc = corrUtils.getStructuralRoleByIi(paOcIi);
        Ii poOcIi = IiConverter.convertToPoOrganizationalContactIi(paOc.getIdentifier());
        OrganizationalContactDTO ocDTO;
        try {
            ocDTO = PoRegistry.getOrganizationalContactCorrelationService().getCorrelation(poOcIi);
        } catch (NullifiedRoleException e) {
            throw new PAException("Nullified Exception for OC.Ii = " + poOcIi, e);
        }
        return ocDTO;
    }

    /**
     * Get Po Crs dto by Pa Crs ii.
     * @param paCrsIi pa crs ii
     * @param corrUtils CorrelationUtils
     * @return po org dto
     * @throws PAException when error.
     */
    protected static ClinicalResearchStaffDTO getPoCrsDTOByPaCrsIi(Ii paCrsIi, CorrelationUtils corrUtils)
        throws PAException {
        ClinicalResearchStaff paCrs = corrUtils.getStructuralRoleByIi(paCrsIi);
        ClinicalResearchStaffDTO crsDTO;
        try {
            crsDTO = PoRegistry.getClinicalResearchStaffCorrelationService().getCorrelation(
                    IiConverter.convertToPoClinicalResearchStaffIi(paCrs.getIdentifier()));
        } catch (NullifiedRoleException e) {
            throw new PAException(e);
        }
        return crsDTO;
    }

    /**
     * addPoOrganizationByPaRoIi.
     * @param root Element
     * @param childName of element
     * @param paRoIi pa ro ii
     * @param doc Document
     * @param corrUtils utility
     * @throws PAException when error
     */
    protected static void addPoOrganizationByPaRoIi(Element root, String childName,
            Ii paRoIi, Document doc, CorrelationUtils corrUtils)
        throws PAException {
        ResearchOrganizationDTO roDTO = PdqXmlGenHelper.getPoRODTOByPaRoIi(paRoIi, corrUtils);
        if (roDTO == null) {
            return;
        }
        OrganizationDTO orgDTO;
        try {
            orgDTO = PoRegistry.getOrganizationEntityService()
                .getOrganization(roDTO.getPlayerIdentifier());
        } catch (NullifiedEntityException e) {
            throw new PAException(e);
        }
        Ii roCtepId = DSetConverter
            .getFirstInDSetByRoot(roDTO.getIdentifier(), IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
        loadOrgParts(childName, root, doc, roCtepId, orgDTO);
    }

    /**
     * addPoOrganizationByPaHcfIi.
     * @param root Element
     * @param childName of element
     * @param paHcfIi pa ro ii
     * @param doc Document
     * @param corrUtils utility
     * @throws PAException when error
     */
    protected static void addPoOrganizationByPaHcfIi(Element root, String childName,
            Ii paHcfIi, Document doc, CorrelationUtils corrUtils)
        throws PAException {
        HealthCareFacilityDTO hcfDTO = PdqXmlGenHelper.getPoHCFDTOByPaHcfIi(paHcfIi, corrUtils);
        if (hcfDTO == null) {
            return;
        }
        OrganizationDTO orgDTO;
        try {
            orgDTO = PoRegistry.getOrganizationEntityService()
                .getOrganization(hcfDTO.getPlayerIdentifier());
        } catch (NullifiedEntityException e) {
            throw new PAException(e);
        }
        Ii hcfCtepId = DSetConverter
            .getFirstInDSetByRoot(hcfDTO.getIdentifier(), IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
        loadOrgParts(childName, root, doc, hcfCtepId, orgDTO);
    }

    private static void loadOrgParts(String childName, Element root, Document doc, Ii ctepId, OrganizationDTO orgDTO) {
        Element child = null;
        if (StringUtils.isEmpty(childName)) {
            child = root;
        } else {
            child = doc.createElement(childName);
        }
        BaseXmlGenHelper.appendElement(child, BaseXmlGenHelper.createElement("name", StringUtils.substring(EnOnConverter
                .convertEnOnToString(orgDTO.getName()), 0,
                PAAttributeMaxLen.LEN_160), doc));
        XmlGenHelper.loadPoOrganization(orgDTO, child, doc, ctepId);
        if (!StringUtils.isEmpty(childName)) {
           BaseXmlGenHelper.appendElement(root, child);
        }
    }

    /**
     * addPoPersonByPaCrsIi.
     * @param root element
     * @param childName of element
     * @param paCrsIi pa crs ii.
     * @param doc Document
     * @param corrUtils utility
     * @throws PAException when error.
     */
    protected static void addPoPersonByPaCrsIi(Element root, String childName,
            Ii paCrsIi, Document doc, CorrelationUtils corrUtils)
        throws PAException {
        Person p = corrUtils.getPAPersonByIi(paCrsIi);
        ClinicalResearchStaffDTO crsDTO = PdqXmlGenHelper.getPoCrsDTOByPaCrsIi(paCrsIi, corrUtils);
        if (p == null || crsDTO == null) {
            return;
        }
        PersonDTO perDTO;
        try {
            perDTO = PoRegistry.getPersonEntityService().getPerson(crsDTO.getPlayerIdentifier());
        } catch (NullifiedEntityException e) {
            throw new PAException(e);
        }
        List<IdentifiedPersonDTO> ipDtos;
        try {
            ipDtos =
                PoRegistry.getIdentifiedPersonEntityService().getCorrelationsByPlayerIds(
                        new Ii[]{crsDTO.getPlayerIdentifier()});
        } catch (NullifiedRoleException e) {
            throw new PAException(e);
        }

        if (StringUtils.isEmpty(childName)) {
            loadPoPerson(root, doc, p, findCtepIdForPerson(ipDtos), perDTO);
        } else {
            Element child = doc.createElement(childName);
            loadPoPerson(child, doc, p, findCtepIdForPerson(ipDtos), perDTO);
            BaseXmlGenHelper.appendElement(root, child);
        }
    }

    private static void loadPoPerson(Element child, Document doc, Person p, Ii ctepId, PersonDTO perDTO) {
        BaseXmlGenHelper.appendElement(child,
                BaseXmlGenHelper.createElementWithTextblock(XmlGenHelper.FIRST_NAME, p.getFirstName(), doc));
        BaseXmlGenHelper.appendElement(child,
                BaseXmlGenHelper.createElementWithTextblock(XmlGenHelper.MIDDLE_INITIAL,
                        StringUtils.substring(p.getMiddleName(), 0 , 1), doc));
        BaseXmlGenHelper.appendElement(child,
                BaseXmlGenHelper.createElementWithTextblock(XmlGenHelper.LAST_NAME, p.getLastName(), doc));
        XmlGenHelper.loadPoPerson(perDTO, child, doc, ctepId);
    }

    private static Ii findCtepIdForPerson(List<IdentifiedPersonDTO> ipDtos) {
        Ii ctepId =  null;
        if (!CollectionUtils.isEmpty(ipDtos)) {
            for (IdentifiedPersonDTO ipDto : ipDtos) {
                if (!ISOUtil.isIiNull(ipDto.getAssignedId()) && IiConverter.CTEP_PERSON_IDENTIFIER_ROOT.equals(
                        ipDto.getAssignedId().getRoot())) {
                    ctepId = ipDto.getAssignedId();
                    break;
                }
            }
        }
        return ctepId;
    }

    /**
     * addPoOrganizationalContactByPaOcIi.
     * @param root element
     * @param childName of element
     * @param paOcIi pa oc ii
     * @param doc Document
     * @param corrUtils utility
     * @throws PAException when error.
     */
    protected static void addPoOrganizationalContactByPaOcIi(Element root, String childName,
            Ii paOcIi, Document doc, CorrelationUtils corrUtils)
    throws PAException {
        OrganizationalContactDTO ocDTO = PdqXmlGenHelper.getPoOCDTOByPaOcIi(paOcIi, corrUtils);
        if (ocDTO == null) {
            return;
        }
        Ii ocCtepId = DSetConverter
        .getFirstInDSetByRoot(ocDTO.getIdentifier(), IiConverter.CTEP_PERSON_IDENTIFIER_ROOT);
        Element child = null;
        if (StringUtils.isEmpty(childName)) {
            child = root;
        } else {
            child = doc.createElement(childName);
        }
        BaseXmlGenHelper.appendElement(child, BaseXmlGenHelper.createElementWithTextblock("name", StringUtils.substring(
                StConverter.convertToString(ocDTO.getTitle()), 0,
                PAAttributeMaxLen.LEN_160), doc));
        XmlGenHelper.loadPoOrgContact(ocDTO, child, doc, ocCtepId);
        if (!StringUtils.isEmpty(childName)) {
           BaseXmlGenHelper.appendElement(root, child);
        }
    }

    /**
     * getMaxAge.
     * @param pq list
     * @return BigDecimal
     */
    protected static BigDecimal getMaxAge(Ivl<Pq> pq) {
        if (pq.getHigh() != null) {
            return pq.getHigh().getValue();
        }
        return BigDecimal.ZERO;
    }

    /**
     * getMaxUnit.
     * @param pq list
     * @return string
     */
    protected static String getMaxUnit(Ivl<Pq> pq) {
        if (pq.getHigh() != null) {
            return pq.getHigh().getUnit();
        }
        return "";
    }

    /**
     * getMinAge.
     * @param pq list
     * @return BigDecimal
     */
    protected static BigDecimal getMinAge(Ivl<Pq> pq) {
        if (pq.getLow() != null) {
            return pq.getLow().getValue();
        }
        return BigDecimal.ZERO;
    }

    /**
     * getMinUnit.
     * @param pq list
     * @return string
     */
    protected static String getMinUnit(Ivl<Pq> pq) {
        if (pq.getLow() != null) {
            return pq.getLow().getUnit();
        }
        return "";
    }

    private static void loadEligCritNoAgeOrGenderOrDescTxt(PlannedEligibilityCriterionDTO paEC, Ivl<Pq> pq,
            StringBuffer incCrit, StringBuffer nullCrit, StringBuffer exCrit) {

        String criterionName = StConverter.convertToString(paEC.getCriterionName());
        Boolean incIndicator = BlConverter.convertToBoolean(paEC.getInclusionIndicator());
        String operator = (!ISOUtil.isStNull(paEC.getOperator())) ? paEC.getOperator().getValue() : "";
        BigDecimal value = pq.getLow().getValue();
        String unit = pq.getLow().getUnit();
        if (incIndicator == null) {
            nullCrit.append(
                    XmlGenHelper.DASH).append(criterionName).append(' ').append(value).append(' ')
                    .append(operator).append(' ').append(unit).append('\n');
        } else if (incIndicator) {
            incCrit.append(
                    XmlGenHelper.DASH).append(criterionName).append(' ').append(value).append(' ')
                    .append(operator).append(' ').append(unit).append('\n');
        } else {
            exCrit.append(
                    XmlGenHelper.DASH).append(criterionName).append(' ').append(value).append(' ').append(
                    operator).append(' ').append(unit).append('\n');
        }
    }

    private static  Element loadCriteriaElement(StringBuffer incCrit, StringBuffer nullCrit,
            StringBuffer exCrit, Document doc) throws PAException {
        Element criteriaElement = doc.createElement("criterion");
        if (nullCrit.length() > 1) {
            BaseXmlGenHelper.createCdataBlock("data",
                    StConverter.convertToSt(nullCrit.toString()), PAAttributeMaxLen.LEN_15000, doc,
                    criteriaElement);
        } else if (incCrit.length() > 1) {
            BaseXmlGenHelper.appendElement(criteriaElement,
                    BaseXmlGenHelper.createElementWithTextblock("type", "Inclusion Criteria", doc));
            BaseXmlGenHelper.createCdataBlock("data",
                    StConverter.convertToSt(incCrit.toString()), PAAttributeMaxLen.LEN_15000, doc,
                    criteriaElement);
        } else if (exCrit.length() > 1) {
            BaseXmlGenHelper.appendElement(criteriaElement,
                    BaseXmlGenHelper.createElementWithTextblock("type", "Exclusion Criteria", doc));
            BaseXmlGenHelper.createCdataBlock("data",
                    StConverter.convertToSt(exCrit.toString()), PAAttributeMaxLen.LEN_15000, doc,
                    criteriaElement);
        }
        return criteriaElement;

    }

    /**
     * handleEligCritTraversal.
     * @param paEC PlannedEligibilityCriterionDTO
     * @param eligHelper component class
     * @param eligibility element
     * @param doc document
     * @throws PAException when error.
     */
    protected static void handleEligCritTraversal(PlannedEligibilityCriterionDTO paEC,
            EligibilityComponentHelper eligHelper, Element eligibility, Document doc)
        throws PAException {
        StringBuffer incCrit = new StringBuffer();
        StringBuffer nullCrit = new StringBuffer();
        StringBuffer exCrit = new StringBuffer();

        String criterionName = StConverter.convertToString(paEC.getCriterionName());
        String descriptionText = StConverter.convertToString(paEC.getTextDescription());
        Boolean incIndicator = BlConverter.convertToBoolean(paEC.getInclusionIndicator());
        Ivl<Pq> pq = paEC.getValue();
        if ("GENDER".equalsIgnoreCase(criterionName)
                && paEC.getEligibleGenderCode() != null) {
            eligHelper.setGenderCode(paEC.getEligibleGenderCode().getCode());
        } else if ("AGE".equalsIgnoreCase(criterionName)) {
            eligHelper.setMaxAge(getMaxAge(pq));
            eligHelper.setMaxUnit(getMaxUnit(pq));
            eligHelper.setMinAge(getMinAge(pq));
            eligHelper.setMinUnit(getMinUnit(pq));

        } else if (descriptionText != null) {
            loadEligCritDescTxt(incIndicator, descriptionText,
                    incCrit, nullCrit, exCrit);
        } else {
            loadEligCritNoAgeOrGenderOrDescTxt(paEC, pq, incCrit, nullCrit, exCrit);
        }

        BaseXmlGenHelper.appendElement(eligibility, loadCriteriaElement(incCrit, nullCrit,
                exCrit, doc));
    }

    /**
     * handleDiseaseCollection.
     * @param diseases list of diseases
     * @param doc Document
     * @param root element
     */
    protected static void handleDiseaseCollection(List<PDQDiseaseDTO> diseases, Document doc, Element root) {
        if (!CollectionUtils.isEmpty(diseases)) {
            Element diseaseConditionElement = doc.createElement("disease_conditions");
            for (PDQDiseaseDTO d : diseases) {
                Element conditionElement = doc.createElement("condition_info");
                BaseXmlGenHelper.appendElement(conditionElement,
                        BaseXmlGenHelper.createElementWithTextblock("preferred_name",
                                StringUtils.substring(StConverter.convertToString(d
                        .getPreferredName()), 0, PAAttributeMaxLen.LEN_160), doc));
                BaseXmlGenHelper.appendElement(conditionElement,
                        BaseXmlGenHelper.createElementWithTextblock("disease_code",
                                StringUtils.substring(StConverter.convertToString(d
                        .getDiseaseCode()), 0, PAAttributeMaxLen.LEN_160), doc));
                BaseXmlGenHelper.appendElement(conditionElement,
                        BaseXmlGenHelper.createElementWithTextblock("nci_thesaurus_id",
                                StringUtils.substring(StConverter.convertToString(d
                        .getNtTermIdentifier()), 0, PAAttributeMaxLen.LEN_160), doc));
                BaseXmlGenHelper.appendElement(conditionElement,
                        BaseXmlGenHelper.createElementWithTextblock("menu_display_name",
                                StringUtils.substring(StConverter.convertToString(d
                        .getDisplayName()), 0, PAAttributeMaxLen.LEN_160), doc));
                BaseXmlGenHelper.appendElement(diseaseConditionElement, conditionElement);
            }

            BaseXmlGenHelper.appendElement(root, diseaseConditionElement);
        }
    }

    private static void loadEligCritDescTxt(Boolean incIndicator, String descriptionText,
            StringBuffer incCrit, StringBuffer nullCrit, StringBuffer exCrit) {

            if (incIndicator == null) {
                nullCrit.append(XmlGenHelper.TAB);
                nullCrit.append(XmlGenHelper.DASH);
                nullCrit.append(descriptionText);
                nullCrit.append('\n');
            } else if (incIndicator) {
                incCrit.append(XmlGenHelper.TAB);
                incCrit.append(XmlGenHelper.DASH);
                incCrit.append(descriptionText);
                incCrit.append('\n');
            } else {
                exCrit.append(XmlGenHelper.TAB);
                exCrit.append(XmlGenHelper.DASH);
                exCrit.append(descriptionText);
                exCrit.append('\n');
            }
    }
}
