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
package gov.nih.nci.pa.util;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.AddressPartType;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * This set of utils handles conversions, etc for domain objects. It cannot be used in any of the grid services.
 * Use PAIsoUtils if you need utilities to use in the grid services.
 *
 * @author Abraham J. Evans-EL
 */
public class PADomainUtils {
    private static final Logger LOG = Logger.getLogger(PADomainUtils.class);
    private static final int EMAIL_IDX = 7;

    /**
     *
     * @param poPerson as arg
     * @return Person as pa object
     */
    public static Person convertToPaPerson(PersonDTO poPerson) {
        Person person = new Person();
        List<Enxp> list = poPerson.getName().getPart();
        Iterator<Enxp> ite = list.iterator();
        while (ite.hasNext()) {
            Enxp part = ite.next();
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
     * @return PaPersonDTO as pa DTO object
     */
    public static PaPersonDTO convertToPaPersonDTO(PersonDTO poPerson) {
        gov.nih.nci.pa.dto.PaPersonDTO personDTO = new gov.nih.nci.pa.dto.PaPersonDTO();
        personDTO.setId(Long.valueOf(poPerson.getIdentifier().getExtension()));
        List<Enxp> nameList = poPerson.getName().getPart();
        Iterator<Enxp> nameIte = nameList.iterator();
        while (nameIte.hasNext()) {
            Enxp part = nameIte.next();
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
        //TelEmail; I need only EMAIL, So I am not using the DSETCONVERTER Class
        DSet<Tel> telList = poPerson.getTelecomAddress();
        Set<Tel> set = telList.getItem();
        Iterator<Tel> iter = set.iterator();
        while (iter.hasNext()) {
            Object obj = iter.next();
            if (obj instanceof TelEmail) {
                personDTO.setEmail(((TelEmail) obj).getValue().toString().substring(EMAIL_IDX));
            }
        }
        //Populate the address information now!
        List<Adxp> addressList = poPerson.getPostalAddress().getPart();
        Iterator<Adxp> addressIte = addressList.iterator();
        while (addressIte.hasNext()) {
            Adxp part = addressIte.next();
            if (AddressPartType.STA == part.getType()) {
                personDTO.setState(part.getValue());
            }
            if (AddressPartType.CTY == part.getType()) {
                personDTO.setCity(part.getValue());
            }
            if (AddressPartType.CNT == part.getType()) {
                personDTO.setCountry(part.getCode());
            }
            if (AddressPartType.ZIP == part.getType()) {
                personDTO.setZip(part.getValue());
            }
        }
        return personDTO;
    }

    /**
     * @return OrganizationWebDTO for display.
     * @param poOrgDto OrganizationDTO
     * @param countryList CountryList
     * @throws PAException on error
     */
    public static PaOrganizationDTO convertPoOrganizationDTO(OrganizationDTO poOrgDto, List<Country> countryList)
            throws PAException {
        PaOrganizationDTO displayElement = null;
        displayElement = new PaOrganizationDTO();
        displayElement.setId(poOrgDto.getIdentifier().getExtension().toString());
        displayElement.setName(poOrgDto.getName().getPart().get(0).getValue());
        
        List<Adxp> addressList = poOrgDto.getPostalAddress().getPart();
        Iterator<Adxp> addressIte = addressList.iterator();
        while (addressIte.hasNext()) {
            Adxp part = addressIte.next();
            if (AddressPartType.STA == part.getType()) {
                displayElement.setState(part.getValue());
            }
            if (AddressPartType.CTY == part.getType()) {
                displayElement.setCity(part.getValue());
            }
            if (AddressPartType.CNT == part.getType()) {
                if (countryList != null) {
                    displayElement.setCountry(getCountryNameUsingCode(part.getCode(), countryList));
                } else {
                    displayElement.setCountry(part.getCode());
                }
            }
            if (AddressPartType.ZIP == part.getType()) {
                displayElement.setZip(part.getValue());
            }
        }
        return displayElement;
    }

    /**
     * Check if value exists.
     *
     * @param value the value
     * @param tableName the table name
     * @param column the column
     *
     * @return true, if successful
     *
     * @throws PAException the PA exception
     */
    public static boolean checkIfValueExists(String value, String tableName, String column) throws PAException {
        String sql = "SELECT * FROM " + tableName + " WHERE " + column + " = '" + value + "'";
        Session session = null;
        boolean exists = false;
        int count = 0;
        try {
            session = PaHibernateUtil.getCurrentSession();
            Statement st = session.connection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                count++;
            }
            if (count > 0) {
                exists = true;
            }

        }  catch (SQLException sqle) {
            LOG.error("Hibernate exception while checking for value " + value + " from table " + tableName , sqle);
        }
        return exists;
    }

    /**
     * Check assigned identifier exists domain.
     *
     * @param sp the sp
     *
     * @return true, if successful
     */
    public static boolean checkAssignedIdentifier(StudyProtocol sp) {
        boolean assignedIdentifierExists = false;
        if (sp.getOtherIdentifiers() != null && !sp.getOtherIdentifiers().isEmpty()) {
            for (Ii spSecId : sp.getOtherIdentifiers()) {
                if (IiConverter.STUDY_PROTOCOL_ROOT.equals(spSecId.getRoot())) {
                    return true;
                }
            }
        }
        return assignedIdentifierExists;
    }

    /**
     * Gets the assigned identifier.
     *
     * @param sp the sp
     *
     * @return the assigned identifier
     */
    public static String getAssignedIdentifierExtension(StudyProtocol sp) {
        String assignedIdentifier = "";
        if (sp.getOtherIdentifiers() != null && !sp.getOtherIdentifiers().isEmpty()) {
            for (Ii spSecId : sp.getOtherIdentifiers()) {
                if (IiConverter.STUDY_PROTOCOL_ROOT.equals(spSecId.getRoot())) {
                    return spSecId.getExtension();
                }
            }
        }
        return assignedIdentifier;
    }

    /**
     * Extract lead org sp id from study protocol.
     * @param sp trial
     * @return lead org id.
     * @throws PAException on error.
     */
    public static String getLeadOrgSpId(StudyProtocol sp) throws PAException {
        String returnVal = null;
        if (CollectionUtils.isNotEmpty(sp.getStudySites())) {
            for (StudySite ss : sp.getStudySites()) {
                if (StudySiteFunctionalCode.LEAD_ORGANIZATION.equals(ss.getFunctionalCode())) {
                    returnVal = ss.getLocalStudyProtocolIdentifier();
                }
            }
        }

        if (returnVal == null) {
            throw new PAException("Trial with id " + sp.getId() + " is missing a Lead Org Id");
        }
        return returnVal;
    }

    /**
     * Returns a listing of a study protocols other identifier extensions.
     * @param sp the study protocol to get the other identifier extensions for
     * @return the other identifier extensions.
     */
    public static List<String> getOtherIdentifierExtensions(StudyProtocol sp) {
        List<String> results = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(sp.getOtherIdentifiers())) {
            for (Ii id : sp.getOtherIdentifiers()) {
                if (StringUtils.equals(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT, id.getRoot())) {
                    results.add(id.getExtension());
                }
            }
        }
        return results;
    }
    
    /**
     * Returns a listing of a disease names associated with a studyProtocol.
     * @param sp the study protocol to get the diseases for
     * @return the disease names.
     */
    public static Set<String> getDiseaseNames(StudyProtocol sp) {
        Set<String> results = new TreeSet<String>();
        if (CollectionUtils.isNotEmpty(sp.getStudyDiseases())) {
            for (StudyDisease sd : sp.getStudyDiseases()) {
                results.add(sd.getDisease().getPreferredName());
            }
        }
        return results;
    }
    
    /**
     * Returns a listing of a intervention type names associated with a studyProtocol.
     * @param sp the study protocol to get the intervention types for
     * @return the intervention type names.
     */
    public static Set<String> getInterventionTypes(StudyProtocol sp) {
        Set<String> results = new TreeSet<String>();
        if (CollectionUtils.isNotEmpty(sp.getPlannedActivities())) {
            for (PlannedActivity pa : sp.getPlannedActivities()) {
                if (pa.getIntervention() != null) { 
                    results.add(pa.getIntervention().getTypeCode().getCode());
                }
            }
        }
        return results;
    }

    private static String getCountryNameUsingCode(String code, List<Country> countryList) {
        for (int i = 0; i < countryList.size(); i++) {
            Country country = countryList.get(i);
            if (country.getAlpha3().toString().equals(code)) {
                return country.getName();
            }
        }
        return null;
    }
    /**
     *
     * @param code Alpha3 countryCode
     * @return Country name
     */
    public static String getCountryNameUsingAlpha3Code(String code) {
        String countryName = "";
        if (StringUtils.isEmpty(code)) {
            return countryName;
        }
        Country criteriaCountry = null;
        try {
            criteriaCountry = new Country();
            criteriaCountry.setAlpha3(code);
            List<Country> countryList = PaRegistry.getLookUpTableService().searchCountry(criteriaCountry);
            if (CollectionUtils.isNotEmpty(countryList)) {
                countryName =  (countryList.get(0)).getName();
           }
        } catch (PAException e) {
            countryName = "";
        }
        return countryName;
    }

    /**
     * Creates a research organization example object with an organization with the given name.
     * @param orgName the name of the organization to create the research organization with
     * @return the research organization with an organization with the given name
     */
    public static ResearchOrganization createROExampleObjectByOrgName(String orgName) {
        Organization org = new Organization();
        org.setName(orgName);
        ResearchOrganization ro = new ResearchOrganization();
        ro.setOrganization(org);
        return ro;
    }
    
    /**
     * Search PO organizations by name, address or ctep id.
     * @param orgSearchCriteria criteria
     * @return PO org list.
     * @throws TooManyResultsException when too many exceptions.
     */ 
    public static List<OrganizationDTO> orgSearchByNameAddressCtepId(PaOrganizationDTO orgSearchCriteria) 
            throws TooManyResultsException {
        
        OrganizationDTO criteria = new OrganizationDTO();
        if (StringUtils.isNotBlank(orgSearchCriteria.getCtepId())) {
            IdentifiedOrganizationDTO identifiedOrganizationDTO = new IdentifiedOrganizationDTO();
            identifiedOrganizationDTO.setAssignedId(IiConverter
                    .convertToIdentifiedOrgEntityIi(orgSearchCriteria.getCtepId()));
            List<IdentifiedOrganizationDTO> identifiedOrgs = PoRegistry
                    .getIdentifiedOrganizationEntityService().search(identifiedOrganizationDTO);
            if (CollectionUtils.isNotEmpty(identifiedOrgs)) {
                criteria.setIdentifier(identifiedOrgs.get(0).getPlayerIdentifier());
            }
        } else {
            criteria.setName(EnOnConverter.convertToEnOn(orgSearchCriteria.getName()));
            criteria.setPostalAddress(AddressConverterUtil.create(
                    null, null, orgSearchCriteria.getCity(), orgSearchCriteria.getState(), 
                        orgSearchCriteria.getZip(), orgSearchCriteria.getCountry()));
        }
        return searchOrgEntityByCriteria(criteria, orgSearchCriteria);
    }
    
    private static List<OrganizationDTO> searchOrgEntityByCriteria(OrganizationDTO criteria, 
            PaOrganizationDTO orgSearchCriteria) throws TooManyResultsException {
        List<OrganizationDTO> orgSearchResults = new ArrayList<OrganizationDTO>();
        if (criteria.getIdentifier() != null
                || criteria.getName() != null
                || criteria.getPostalAddress() != null
                || StringUtils.isNotEmpty(orgSearchCriteria.getFamilyName())) {
            LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
            orgSearchResults = PoRegistry.getOrganizationEntityService().search(criteria,
                    EnOnConverter.convertToEnOn(orgSearchCriteria.getFamilyName()), limit);
        }
        return orgSearchResults;
    }
    

}
