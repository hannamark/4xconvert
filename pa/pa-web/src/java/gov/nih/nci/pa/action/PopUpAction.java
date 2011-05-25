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
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Common module to call the PO Search and related functionalities.
 *
 * @author Harsha
 *
 */
public class PopUpAction extends ActionSupport {
    private static final long serialVersionUID = 4960297232842560635L;
    private List<Country> countryList = new ArrayList<Country>();
    private List<PaOrganizationDTO> orgs = new ArrayList<PaOrganizationDTO>();
    private List<PaPersonDTO> persons = new ArrayList<PaPersonDTO>();
    private PaOrganizationDTO orgSearchCriteria = new PaOrganizationDTO();
    private PaPersonDTO perSearchCriteria = new PaPersonDTO();
    private String firstName;
    private String lastName;
    private String countryName;
    private String cityName;
    private String zipCode;
    private String stateName;
    private String email;
    private String telephone;
    private String orgName;
    private String familyName;
    
    /**
     *
     * @return String success or failure
     */
    public String lookupcontactpersons() {
        try {
            persons = null;
            getCountriesList();
            if (getEmail() != null) {
                ServletActionContext.getRequest().getSession().setAttribute("emailEntered", getEmail());
            }
            if (getTelephone() != null) {
                ServletActionContext.getRequest().getSession().setAttribute("telephoneEntered", getTelephone());
            }
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
        return "contactpersons";
    }

    /**
     *
     * @return String success or failure
     */
    public String lookuporgs() {
        try {
            getCountriesList();
            orgs.clear();
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
        return "orgs";
    }

    /**
     *
     * @return result
     */
    public String displayOrgList() {
        return processDisplayOrganizations(SUCCESS);
    }

    /**
     *
     * @return result
     */
    public String displayOrgListDisplayTag() {
        return processDisplayOrganizations("orgs");
    }

    /**
     *
     * @return result
     */
    public String displaycontactPersonsList() {
        return processDisplayPersons(SUCCESS);
    }

    /**
     *
     * @return result
     */
    public String displaycontactPersonsListDisplayTag() {
        return processDisplayPersons("contactpersons");
    }

    /**
     *
     * @return result
     */
    public String displayPersonsList() {
        return processDisplayPersons(SUCCESS);
    }

    /**
     *
     * @return result
     */
    public String displayPersonsListDisplayTag() {
        return processDisplayPersons("persons");
    }

    /**
     * @return the orgSearchCriteria
     */
    public PaOrganizationDTO getOrgSearchCriteria() {
        return orgSearchCriteria;
    }

    /**
     * @param orgSearchCriteria the orgSearchCriteria to set
     */
    public void setOrgSearchCriteria(PaOrganizationDTO orgSearchCriteria) {
        this.orgSearchCriteria = orgSearchCriteria;
    }

    /**
     *
     * @return String success or failure
     */
    public String lookuppersons() {
        try {
            getCountriesList();
            orgs.clear();
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
        persons = null;
        return "persons";
    }

    /**
     * @return the countryList
     */
    public List<Country> getCountryList() {
        return countryList;
    }

    /**
     * @param countryList the countryList to set
     */
    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    /**
     * @return the orgs
     */
    public List<PaOrganizationDTO> getOrgs() {
        return orgs;
    }

    /**
     * @param orgs the orgs to set
     */
    public void setOrgs(List<PaOrganizationDTO> orgs) {
        this.orgs = orgs;
    }

    /**
     * @return the persons
     */
    public List<PaPersonDTO> getPersons() {
        return persons;
    }

    /**
     * @param persons the persons to set
     */
    public void setPersons(List<PaPersonDTO> persons) {
        this.persons = persons;
    }

    private String processDisplayOrganizations(String retvalue) {
        try {
            getCountriesList();
            if (isOrgSearchCriteriaSet()) {
                String message = "Please enter at least one search criteria";
                orgs.clear();
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
                return retvalue;
            }
            List<OrganizationDTO> orgList = performOrgSearch();            
            Map<Ii, FamilyDTO> familyMap = getFamilyDTOs(orgList);
            convertToWebDTO(orgList, familyMap);
            return retvalue;
        } catch (Exception e) {
            return retvalue;
        }
    }

    @SuppressWarnings("unchecked")    
    private void convertToWebDTO(List<OrganizationDTO> orgList, Map<Ii, FamilyDTO> familyMap) throws PAException {
        for (OrganizationDTO dto : orgList) {
            PaOrganizationDTO paDTO = PADomainUtils.convertPoOrganizationDTO(dto, countryList);
            paDTO.setFamilies(getFamilies(dto.getFamilyOrganizationRelationships(), familyMap));
            orgs.add(paDTO);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<Ii, FamilyDTO> getFamilyDTOs(List<OrganizationDTO> orgList) {
        Set<Ii> famOrgRelIiList = new HashSet<Ii>();
        for (OrganizationDTO dto : orgList) {
            famOrgRelIiList.addAll(dto.getFamilyOrganizationRelationships().getItem());
        }
        return PoRegistry.getFamilyService().getFamilies(famOrgRelIiList);
    }

    private List<OrganizationDTO> performOrgSearch() throws TooManyResultsException {
        // Set the values; so paging will retain them
        orgSearchCriteria.setName(getOrgName());
        orgSearchCriteria.setFamilyName(getFamilyName());
        orgSearchCriteria.setCity(cityName);
        orgSearchCriteria.setCountry(countryName);
        orgSearchCriteria.setZip(zipCode);
        orgSearchCriteria.setState(stateName);
        OrganizationDTO criteria = new OrganizationDTO();
        criteria.setName(EnOnConverter.convertToEnOn(getOrgName()));
        criteria.setPostalAddress(AddressConverterUtil.create(null, null, cityName,
                                                                        stateName, zipCode, countryName));
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<OrganizationDTO> orgList = PoRegistry.getOrganizationEntityService().search(criteria,
                EnOnConverter.convertToEnOn(getFamilyName()), limit);
        return orgList;
    }

    private Map<Long, String> getFamilies(DSet<Ii> familyOrganizationRelationships, Map<Ii, FamilyDTO> familyMap) {
        Map<Long, String> retMap = new HashMap<Long, String>();
        Set<Ii> famOrgIis = familyOrganizationRelationships.getItem();
        for (Ii ii : famOrgIis) {
            FamilyDTO dto = familyMap.get(ii);
            retMap
                    .put(IiConverter.convertToLong(dto.getIdentifier()), EnOnConverter.convertEnOnToString(dto
                            .getName()));
        }
        return retMap;
    }

    private boolean isOrgSearchCriteriaSet() {
        return StringUtils.isBlank(orgName) && StringUtils.isBlank(familyName) && StringUtils.isBlank(countryName)
                && StringUtils.isBlank(cityName) && StringUtils.isBlank(zipCode);
    }

    private String processDisplayPersons(String retvalue) {
        if (isPersonSearchCriteriaSet()) {
            String message = "Please enter at least one search criteria";
            persons = null;
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
            return retvalue;
        }
        // Set the values; so paging will retain them
        perSearchCriteria.setFirstName(firstName);
        perSearchCriteria.setLastName(lastName);
        perSearchCriteria.setCity(cityName);
        perSearchCriteria.setState(stateName);
        perSearchCriteria.setCountry(countryName);
        perSearchCriteria.setZip(zipCode);
        //
        PersonDTO p = new PersonDTO();
        p.setName(EnPnConverter.convertToEnPn(firstName, null, lastName, null, null));
        p.setPostalAddress(AddressConverterUtil.create(null, null, cityName, stateName, zipCode, countryName));
        try {
            LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
            List<PersonDTO> persList = new ArrayList<PersonDTO>();
            persList = PoRegistry.getPersonEntityService().search(p, limit);
            for (PersonDTO dto : persList) {
                persons.add(PADomainUtils.convertToPaPersonDTO(dto));
            }
        } catch (TooManyResultsException e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return retvalue;
        }
        return retvalue;
    }

    private boolean isPersonSearchCriteriaSet() {
        return StringUtils.isBlank(firstName) && StringUtils.isBlank(lastName) && StringUtils.isBlank(countryName)
                && StringUtils.isBlank(cityName) && StringUtils.isBlank(zipCode) && StringUtils.isBlank(stateName);
    }

    @SuppressWarnings("unchecked")
    private void getCountriesList() throws PAException {
        countryList = (List<Country>) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
        if (countryList == null) {
            countryList = PaRegistry.getLookUpTableService().getCountries();
            ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
        }
    }

    /**
     * @return the perSearchCriteria
     */
    public PaPersonDTO getPerSearchCriteria() {
        return perSearchCriteria;
    }

    /**
     * @param perSearchCriteria the perSearchCriteria to set
     */
    public void setPerSearchCriteria(PaPersonDTO perSearchCriteria) {
        this.perSearchCriteria = perSearchCriteria;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return the cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode the zipCode to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return the stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @param stateName the stateName to set
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param familyName the familyName to set
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * @return the familyName
     */
    public String getFamilyName() {
        return familyName;
    }
}
