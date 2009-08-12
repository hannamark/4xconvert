/***
* caBIG Open Source Software License
* 
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Clinical Trials Protocol Application
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
package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author Harsha
 * 
 */
@SuppressWarnings("PMD")
public class PopupAction extends ActionSupport implements Preparable {
    private List countryList = new ArrayList();
    private List<SearchOrgResultDisplay> orgs = new ArrayList<SearchOrgResultDisplay>();
    private List<PaPersonDTO> persons = new ArrayList<PaPersonDTO>();
    private OrgSearchCriteria orgSearchCriteria = new OrgSearchCriteria();
    private OrgSearchCriteria createOrg = new OrgSearchCriteria();
    private PaPersonDTO personDTO = new PaPersonDTO();
    private static final String PERS_CREATE_RESPONSE = "create_pers_response";
    private final int ausStateCodeLen = 3;
    /**
     * @throws Exception on error
     */
    public void prepare() throws Exception {
        populateCountryList();
    }

    /**
     * 
     * @return String success or failure
     */
    public String lookuppersons() {
        persons = null;
        return "persons";
    }

    /**
     * 
     * @return String success or failure
     */
    public String lookuporgs() {
        try {
            orgs = null;
            populateCountryList();
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
        return populateOrgs(false);
    }
    /**
    * @throws PAException on error
     * @return res
     */
    public String displayPersonsListDisplayTag() throws PAException {
        return populatePersons(true);
    }
    
    /**
     * @throws PAException on error
      * @return res
      */
     public String displayPersonsList() throws PAException {
         return populatePersons(false);
     }    
    

    private String populatePersons(boolean pagination) throws PAException {
        try {
            String firstName = ServletActionContext.getRequest().getParameter("firstName");
            String lastName = ServletActionContext.getRequest().getParameter("lastName");
            String email = ServletActionContext.getRequest().getParameter("email");
            //
            String country = ServletActionContext.getRequest().getParameter("country");
            String city = ServletActionContext.getRequest().getParameter("city");
            String zip = ServletActionContext.getRequest().getParameter("zip");
            String state = ServletActionContext.getRequest().getParameter("state");
            //
            String ctep = ServletActionContext.getRequest().getParameter("ctepId");
            if ((firstName != null) && (firstName.equals("")) && (lastName != null) && (lastName.equals(""))
                    && (email.equals("")) && ctep != null && !(ctep.length() > 0) 
                    && (city != null) && (city.equals("")) 
                    && (zip != null) && (zip.equals("")) && (state != null) && (state.equals(""))
                    && country.equals("")) {
                String message = "Please enter at least one search criteria";
                persons = null;
                addActionError(message);
                ServletActionContext.getRequest().setAttribute("failureMessage", message);
                return SUCCESS;
            }
            //set the values in the DTO
            personDTO.setFirstName(firstName);
            personDTO.setLastName(lastName);
            personDTO.setEmail(email);
            personDTO.setCountry(country);
            personDTO.setCity(city);
            personDTO.setState(state);
            gov.nih.nci.services.person.PersonDTO p = new gov.nih.nci.services.person.PersonDTO();
            //
            if (email != null && email.length() > 0) {
                DSet<Tel> list = new DSet<Tel>();
                list.setItem(new HashSet<Tel>());
                TelEmail telemail = new TelEmail();
                telemail.setValue(new URI("mailto:" + email));
                list.getItem().add(telemail);
                p.setTelecomAddress(list);
            }
            p.setPostalAddress(AddressConverterUtil.create(null, null, city, state, zip, country));
            //
            List<gov.nih.nci.services.person.PersonDTO> poPersonList = 
                                                        new ArrayList<gov.nih.nci.services.person.PersonDTO>();
            if (ctep != null && ctep.length() > 0) {
                IdentifiedPersonDTO identifiedPersonDTO = new IdentifiedPersonDTO();
                identifiedPersonDTO.setAssignedId(IiConverter.convertToIdentifiedPersonEntityIi(ctep));
                List<IdentifiedPersonDTO> retResultList = 
                                  RegistryServiceLocator.getIdentifiedPersonEntityService().search(identifiedPersonDTO);
                if (retResultList != null && retResultList.size() > 0) {
                    p.setIdentifier(retResultList.get(0).getPlayerIdentifier());
                } 
            } else {
                p.setName(RemoteApiUtil.convertToEnPn(firstName, null, lastName, null, null));
            }
            if (p.getIdentifier() != null || p.getName() != null) {
                poPersonList = RegistryServiceLocator.getPoPersonEntityService().search(p);
            }
            for (gov.nih.nci.services.person.PersonDTO poPersonDTO : poPersonList) {
                persons.add(EnPnConverter.convertToPaPersonDTO(poPersonDTO));
            }
        } catch (Exception e) {
            persons = null;
            //ServletActionContext.getRequest().setAttribute("failureMessage", e.getMessage());
            LOG.error("Error occured while searching PO Persons " + e.getMessage(), e);
            return pagination ? "persons" : SUCCESS;
        }
        return pagination ? "persons" : SUCCESS;
    }

    /**
     * 
     * @return result
     */
    public String displayOrgListDisplayTag() {
        return populateOrgs(true);
    }
    
    private String populateOrgs(boolean pagination) {
        try {
            String orgName = ServletActionContext.getRequest().getParameter("orgName");
            String countryName = ServletActionContext.getRequest().getParameter("countryName");
            String cityName = ServletActionContext.getRequest().getParameter("cityName");
            String stateName = ServletActionContext.getRequest().getParameter("stateName");
            String zipCode = ServletActionContext.getRequest().getParameter("zipCode");
            String ctepId = ServletActionContext.getRequest().getParameter("ctepid");
            
            if (orgName.equals("") && countryName.equals("") && cityName.equals("") && zipCode.equals("")
                    && ctepId != null && !(ctepId.length() > 0)) {
                String message = "Please enter at least one search criteria";
                orgs = null;
                addActionError(message);
                ServletActionContext.getRequest().setAttribute("failureMessage", message);
                return pagination ? "orgs" : SUCCESS;
            }
            if (countryName.equals("") && ctepId != null && !(ctepId.length() > 0)) {
                String message = "Please select a country";
                orgs = null;
                addActionError(message);
                ServletActionContext.getRequest().setAttribute("failureMessage", message);
                return pagination ? "orgs" : SUCCESS;
            }
            if (pagination) {
                orgSearchCriteria.setOrgName(orgName);
                orgSearchCriteria.setOrgCity(cityName);
                orgSearchCriteria.setOrgCountry(countryName);
                orgSearchCriteria.setOrgZip(zipCode);
                orgSearchCriteria.setOrgState(stateName);
            }
            OrganizationDTO criteria = new OrganizationDTO();
            if (ctepId != null && ctepId.length() > 0) {
                IdentifiedOrganizationDTO identifiedOrganizationDTO = new IdentifiedOrganizationDTO();
                identifiedOrganizationDTO.setAssignedId(IiConverter.convertToIdentifiedOrgEntityIi(ctepId));
                List<IdentifiedOrganizationDTO> identifiedOrgs = RegistryServiceLocator
                        .getIdentifiedOrganizationEntityService().search(identifiedOrganizationDTO);
                if (identifiedOrgs != null && identifiedOrgs.size() > 0) {
                    criteria.setIdentifier(identifiedOrgs.get(0).getPlayerIdentifier());
                }
            } else {
                criteria.setName(EnOnConverter.convertToEnOn(orgName));
                criteria.setPostalAddress(AddressConverterUtil.create(
                        null, null, cityName, stateName, zipCode, countryName));
            }
            List<OrganizationDTO> callConvert = new ArrayList<OrganizationDTO>();
            if (criteria.getIdentifier() != null 
                    || criteria.getName() != null
                    || criteria.getPostalAddress() != null) {
                callConvert = RegistryServiceLocator.getPoOrganizationEntityService().search(criteria);
            }
            convertPoOrganizationDTO(callConvert);
            return pagination ? "orgs" : SUCCESS;
        } catch (Exception e) {
            orgs = null;
            //addActionError(e.getMessage());
            //ServletActionContext.getRequest().setAttribute("failureMessage", e.getMessage());
            LOG.error("Error occured while searching PO Organizations " + e.getMessage(), e);
            return pagination ? "orgs" : SUCCESS;
        }
    }

    private String getCountryNameUsingCode(String code) {
        for (int i = 0; i < countryList.size(); i++) {
            gov.nih.nci.pa.domain.Country country = (gov.nih.nci.pa.domain.Country) countryList.get(i);
            if (country.getAlpha3().toString().equals(code)) {
                return country.getName();
            }
        }
        return null;
    }

    private void populateCountryList() throws PAException {
        countryList = (List) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
        if (countryList == null) {
            countryList = RegistryServiceLocator.getLookUpTableService().getCountries();
            ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
        }
    }

    /**
     * @throws PAException on error
     * @return result
     */
    public String createOrganization() throws PAException {
        OrganizationDTO orgDto = new OrganizationDTO();
        String orgName = ServletActionContext.getRequest().getParameter("orgName");
        if (orgName != null && !PAUtil.isNotEmpty(orgName)) {
            addActionError("Organization is a required field");
        }
        String orgStAddress = ServletActionContext.getRequest().getParameter("orgStAddress");
        if (orgStAddress != null && !PAUtil.isNotEmpty(orgStAddress)) {
            addActionError("Street address is a required field");
        }
        String countryName = ServletActionContext.getRequest().getParameter("countryName");
        if (countryName != null && countryName.equals("aaa")) {
            addActionError("Country is a required field");
        }
        String cityName = ServletActionContext.getRequest().getParameter("cityName");
        if (cityName != null && !PAUtil.isNotEmpty(cityName)) {
            addActionError("City is a required field");
        }
        String zipCode = ServletActionContext.getRequest().getParameter("zipCode");
        if (zipCode != null && !PAUtil.isNotEmpty(zipCode)) {
            addActionError("Zip is a required field");
        }
        String stateName = ServletActionContext.getRequest().getParameter("stateName");
        if (countryName != null && (countryName.equalsIgnoreCase("USA")
                                || countryName.equalsIgnoreCase("CAN"))) {
            if (PAUtil.isEmpty(stateName) || stateName.trim().length() > 2) {
                addActionError("2-letter State/Province Code required for USA/Canada");
            }           
        }
        if (countryName != null && countryName.equalsIgnoreCase("AUS")) {
            if (PAUtil.isEmpty(stateName) || stateName.trim().length() > ausStateCodeLen) {
                addActionError("2/3-letter State/Province Code required for Australia");
            }           
        }

        String email = ServletActionContext.getRequest().getParameter("email");
        if (email != null && !PAUtil.isNotEmpty(email)) {
            addActionError("Email is a required field");
        } else if (!PAUtil.isValidEmail(email)) {
            addActionError("Email address is invalid");
        }
        String phoneNumer = ServletActionContext.getRequest().getParameter("phoneNumber");
        String faxNumber = ServletActionContext.getRequest().getParameter("fax");
        String ttyNumber = ServletActionContext.getRequest().getParameter("tty");
        String url = ServletActionContext.getRequest().getParameter("url");
        if (hasActionErrors()) {
            StringBuffer sb = new StringBuffer();
            Iterator<String> i = getActionErrors().iterator();
            while (i.hasNext()) {
                sb.append(" - " + i.next().toString());
            }
            ServletActionContext.getRequest().setAttribute("failureMessage", sb.toString());
            return "create_org_response";
        }
        orgDto.setName(EnOnConverter.convertToEnOn(orgName));
        //PO Service requires upper case state codes for US and Canada
        if (PAUtil.isNotEmpty(stateName)) {
            stateName = stateName.trim().toUpperCase();            
        }
        orgDto.setPostalAddress(AddressConverterUtil.create(orgStAddress, null, cityName, stateName, zipCode,
                countryName));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        try {
            if (phoneNumer != null && phoneNumer.length() > 0) {
                Tel t = new Tel();
                t.setValue(new URI("tel", phoneNumer, null));
                telco.getItem().add(t);
            }
            if (faxNumber != null && faxNumber.length() > 0) {
                Tel fax = new Tel();
                fax.setValue(new URI("x-text-fax", faxNumber, null));
                telco.getItem().add(fax);
            }
            if (ttyNumber != null && ttyNumber.length() > 0) {
                Tel tty = new Tel();
                tty.setValue(new URI("x-text-tel", ttyNumber, null));
                telco.getItem().add(tty);
            }
            if (url != null && url.length() > 0) {
                TelUrl telurl = new TelUrl();
                telurl.setValue(new URI(url));
                telco.getItem().add(telurl);
            }
            TelEmail telemail = new TelEmail();
            telemail.setValue(new URI("mailto:" + email));
            telco.getItem().add(telemail);
            orgDto.setTelecomAddress(telco);
            Ii id = RegistryServiceLocator.getPoOrganizationEntityService().createOrganization(orgDto);
            List<OrganizationDTO> callConvert = new ArrayList<OrganizationDTO>();
            callConvert.add(RegistryServiceLocator.getPoOrganizationEntityService().getOrganization(id));
            convertPoOrganizationDTO(callConvert);
        } catch (NullifiedEntityException e) {
            handleError(e.getMessage());
        } catch (URISyntaxException e) {
            handleError(e.getMessage());
        } catch (EntityValidationException e) {
            handleError(e.getMessage());
        } catch (PAException e) {
            handleError(e.getMessage());
        }
        return "create_org_response";
    }

    private String handleError(String message) {
        addActionError(message);
        ServletActionContext.getRequest().setAttribute("failureMessage", message);
        return "create_org_response";
    }

    /**
     * 
     * @return String rest
     */
    public String createPerson() {
        String firstName = ServletActionContext.getRequest().getParameter("firstName");
        if (firstName != null && !PAUtil.isNotEmpty(firstName)) {
            addActionError("First Name is a required field");
        }
        String lastName = ServletActionContext.getRequest().getParameter("lastName");
        if (lastName != null && !PAUtil.isNotEmpty(lastName)) {
            addActionError("Last Name is a required field");
        }
        String email = ServletActionContext.getRequest().getParameter("email");
        if (email != null && !PAUtil.isNotEmpty(email)) {
            addActionError("Email is a required field");
        } else if (!PAUtil.isValidEmail(email)) {
            addActionError("Email address is invalid");
        }
        String streetAddr = ServletActionContext.getRequest().getParameter("streetAddr");
        if (streetAddr != null && !PAUtil.isNotEmpty(streetAddr)) {
            addActionError("Street address is a required field");
        }
        String city = ServletActionContext.getRequest().getParameter("city");
        if (city != null && !PAUtil.isNotEmpty(city)) {
            addActionError("City is a required field");
        }
        String zip = ServletActionContext.getRequest().getParameter("zip");
        if (zip != null && !PAUtil.isNotEmpty(zip)) {
            addActionError("Zip is a required field");
        }
        String country = ServletActionContext.getRequest().getParameter("country");
        if (country != null && country.equals("aaa")) {
            addActionError("Country is a required field");
        }
        String state = ServletActionContext.getRequest().getParameter("state");
        if (country != null && (country.equalsIgnoreCase("USA")
                            || country.equalsIgnoreCase("CAN"))) {
            if (PAUtil.isEmpty(state) || state.trim().length() > 2) {
                addActionError("2-letter State/Province Code required for USA/Canada");
            }            
        }        
        if (country != null && country.equalsIgnoreCase("AUS")) {
            if (PAUtil.isEmpty(state) || state.trim().length() > ausStateCodeLen) {
                addActionError("2/3-letter State/Province Code required for Australia");
            }            
        }        

        if (hasActionErrors()) {
            StringBuffer sb = new StringBuffer();
            Iterator<String> i = getActionErrors().iterator();
            while (i.hasNext()) {
                sb.append(" - " + i.next().toString());
            }
            ServletActionContext.getRequest().setAttribute("failureMessage", sb.toString());
            return PERS_CREATE_RESPONSE;
        }
        
        String preFix = ServletActionContext.getRequest().getParameter("preFix");
        String midName = ServletActionContext.getRequest().getParameter("midName");        
        String phone = ServletActionContext.getRequest().getParameter("phone");
        String tty = ServletActionContext.getRequest().getParameter("tty");
        String fax = ServletActionContext.getRequest().getParameter("fax");
        String url = ServletActionContext.getRequest().getParameter("url");
        String suffix = ServletActionContext.getRequest().getParameter("suffix");
        //
        gov.nih.nci.services.person.PersonDTO dto = new gov.nih.nci.services.person.PersonDTO();
        dto.setName(new EnPn());
        Enxp part = new Enxp(EntityNamePartType.GIV);
        part.setValue(firstName);
        dto.getName().getPart().add(part);
        // if middel name exists stick it in here!
        if (midName != null && PAUtil.isNotEmpty(midName)) {
            Enxp partMid = new Enxp(EntityNamePartType.GIV);
            partMid.setValue(midName);
            dto.getName().getPart().add(partMid);
        }
        Enxp partFam = new Enxp(EntityNamePartType.FAM);
        partFam.setValue(lastName);
        dto.getName().getPart().add(partFam);
        if (preFix != null && PAUtil.isNotEmpty(preFix)) {
            Enxp partPfx = new Enxp(EntityNamePartType.PFX);
            partPfx.setValue(preFix);
            dto.getName().getPart().add(partPfx);
        }
        if (suffix != null && PAUtil.isNotEmpty(suffix)) {
            Enxp partSfx = new Enxp(EntityNamePartType.SFX);
            partSfx.setValue(suffix);
            dto.getName().getPart().add(partSfx);
        }
       // dto.getName().getPart().add(part);
        DSet<Tel> list = new DSet<Tel>();
        list.setItem(new HashSet<Tel>());
        try {
            if (phone != null && phone.length() > 0) {
                Tel t = new Tel();
                t.setValue(new URI("tel", phone, null));
                list.getItem().add(t);
            }
            if (fax != null && fax.length() > 0) {
                Tel faxTel = new Tel();
                faxTel.setValue(new URI("x-text-fax", fax, null));
                list.getItem().add(faxTel);
            }
            if (tty != null && tty.length() > 0) {
                Tel ttyTel = new Tel();
                ttyTel.setValue(new URI("x-text-tel", tty, null));
                list.getItem().add(ttyTel);
            }
            if (url != null && url.length() > 0) {
                TelUrl telurl = new TelUrl();
                telurl.setValue(new URI(url));
                list.getItem().add(telurl);
            }
            TelEmail telemail = new TelEmail();
            telemail.setValue(new URI("mailto:" + email));
            list.getItem().add(telemail);
            dto.setTelecomAddress(list);
            //PO Service requires upper case state codes for US and Canada
            if (PAUtil.isNotEmpty(state)) {
                state = state.trim().toUpperCase();            
            }
            dto.setPostalAddress(AddressConverterUtil.create(streetAddr, null, city, state, zip, country));
            Ii id = RegistryServiceLocator.getPoPersonEntityService().createPerson(dto);
            persons.add(EnPnConverter.convertToPaPersonDTO(RegistryServiceLocator.getPoPersonEntityService().getPerson(
                    id)));
        } catch (NullifiedEntityException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        } catch (EntityValidationException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        } catch (PAException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        } catch (URISyntaxException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        }
        return PERS_CREATE_RESPONSE;
    }

    private String handleExceptions(String message, String returnString) {
        addActionError(message);
        ServletActionContext.getRequest().setAttribute("failureMessage", message);
        return returnString;
    }

    private void convertPoOrganizationDTO(List<OrganizationDTO> poOrgDtos) {
        SearchOrgResultDisplay displayElement = null;
        for (int i = 0; i < poOrgDtos.size(); i++) {
            displayElement = new SearchOrgResultDisplay();
            displayElement.setId(poOrgDtos.get(i).getIdentifier().getExtension().toString());
            displayElement.setName(poOrgDtos.get(i).getName().getPart().get(0).getValue());
            //
            int partSize = poOrgDtos.get(i).getPostalAddress().getPart().size();
            AddressPartType type = null;
            for (int k = 0; k < partSize; k++) {
                type = poOrgDtos.get(i).getPostalAddress().getPart().get(k).getType();
                if (type.name().equals("CNT")) {
                    displayElement.setCountry(getCountryNameUsingCode(poOrgDtos.get(i).getPostalAddress().getPart()
                            .get(k).getCode()));
                }
                if (type.name().equals("ZIP")) {
                    displayElement.setZip(poOrgDtos.get(i).getPostalAddress().getPart().get(k).getValue());
                }
                if (type.name().equals("CTY")) {
                    displayElement.setCity(poOrgDtos.get(i).getPostalAddress().getPart().get(k).getValue());
                }
                if (type.name().equals("STA")) {
                    displayElement.setState(poOrgDtos.get(i).getPostalAddress().getPart().get(k).getValue());
                }
            }
            orgs.add(displayElement);
        }
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

    /**
     * @return the orgSearchCriteria
     */
    public OrgSearchCriteria getOrgSearchCriteria() {
        return orgSearchCriteria;
    }

    /**
     * @param orgSearchCriteria the orgSearchCriteria to set
     */
    public void setOrgSearchCriteria(OrgSearchCriteria orgSearchCriteria) {
        this.orgSearchCriteria = orgSearchCriteria;
    }

    /**
     * @return the createOrg
     */
    public OrgSearchCriteria getCreateOrg() {
        return createOrg;
    }

    /**
     * @param createOrg the createOrg to set
     */
    public void setCreateOrg(OrgSearchCriteria createOrg) {
        this.createOrg = createOrg;
    }

    /**
     * @return the personDTO
     */
    public PaPersonDTO getPersonDTO() {
        return personDTO;
    }

    /**
     * @param personDTO the personDTO to set
     */
    public void setPersonDTO(PaPersonDTO personDTO) {
        this.personDTO = personDTO;
    }

    /**
     * @return the orgs
     */
    public List<SearchOrgResultDisplay> getOrgs() {
        return orgs;
    }

    /**
     * @param orgs the orgs to set
     */
    public void setOrgs(List<SearchOrgResultDisplay> orgs) {
        this.orgs = orgs;
    }
}
