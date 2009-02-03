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
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.RemoteApiUtil;
import gov.nih.nci.pa.util.SearchOrgResultDisplay;
import gov.nih.nci.pa.util.SearchPersonResultDisplay;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Common module to call the PO Search and related functionalities.
 * 
 * @author Harsha
 * 
 */
@SuppressWarnings("PMD")
public class PopUpAction extends ActionSupport {
    private List<Country> countryList = new ArrayList<Country>();
    private List<SearchOrgResultDisplay> orgs = new ArrayList<SearchOrgResultDisplay>();
    private List<SearchPersonResultDisplay> persons = new ArrayList<SearchPersonResultDisplay>();
    private OrgSearchCriteria orgSearchCriteria = new OrgSearchCriteria();

    /**
     * @return the orgSearchCriteria
     */
    public OrgSearchCriteria getOrgSearchCriteria() {
        return orgSearchCriteria;
    }

    /**
     * @param orgSearchCriteria
     *            the orgSearchCriteria to set
     */
    public void setOrgSearchCriteria(OrgSearchCriteria orgSearchCriteria) {
        this.orgSearchCriteria = orgSearchCriteria;
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
    public String lookupcontactpersons() {
        persons = null;
        String email = ServletActionContext.getRequest().getParameter("email");
        String telephone = ServletActionContext.getRequest().getParameter("tel");
        if (email != null) {
            ServletActionContext.getRequest().getSession().setAttribute("emailEntered", email);
        }
        if (telephone != null) {
            ServletActionContext.getRequest().getSession().setAttribute("telephoneEntered", telephone);
        }
        return "contactpersons";
    }

    /**
     * 
     * @return String success or failure
     */
    public String lookuporgs() {
        try {
            countryList = (List<Country>) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
            if (countryList == null) {
                countryList = PaRegistry.getLookUpTableService().getCountries();
                ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
            }
            orgs = null;
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
        try {
            String orgName = ServletActionContext.getRequest().getParameter("orgName");
            // String nciOrgName =
            // ServletActionContext.getRequest().getParameter("nciNumber");
            String countryName = ServletActionContext.getRequest().getParameter("countryName");
            String cityName = ServletActionContext.getRequest().getParameter("cityName");
            String zipCode = ServletActionContext.getRequest().getParameter("zipCode");
            if (orgName.equals("") && countryName.equals("aaa") && cityName.equals("") && zipCode.equals("")) {
                String message = "Please enter at least one search criteria";
                orgs = null;
                addActionError(message);
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
                return SUCCESS;
            }
            OrganizationDTO criteria = new OrganizationDTO();
            criteria.setName(EnOnConverter.convertToEnOn(orgName));
            criteria.setPostalAddress(AddressConverterUtil.create("", "", cityName, "", zipCode, countryName));
            List<OrganizationDTO> callConvert = new ArrayList<OrganizationDTO>();
            callConvert = PaRegistry.getPoOrganizationEntityService().search(criteria);
            convertPoOrganizationDTO(callConvert);
            return SUCCESS;
        } catch (Exception e) {
            return SUCCESS;
        }
    }

    /**
     * 
     * @return result
     */
    public String displaycontactPersonsList() {
        String firstName = ServletActionContext.getRequest().getParameter("firstName");
        String lastName = ServletActionContext.getRequest().getParameter("lastName");
        if (firstName.equals("") && lastName.equals("")) {
            String message = "Please enter at least one search criteria";
            persons = null;
            addActionError(message);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
            return SUCCESS;
        }
        PersonDTO p = new PersonDTO();
        // (RemoteApiUtils.convertToEnPn(fName, mName, lName, prefix, suffix));
        p.setName(RemoteApiUtil.convertToEnPn(firstName, null, lastName, null, null));
        try {
            List<PersonDTO> list = new ArrayList<PersonDTO>();
            list = PaRegistry.getPoPersonEntityService().search(p);
            for (PersonDTO dto : list) {
                persons.add(convertToPaPerson(dto));
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return SUCCESS;
        }
        return SUCCESS;
    }

    /**
     * 
     * @return result
     */
    public String displaycontactPersonsListDisplayTag() {

        String firstName = ServletActionContext.getRequest().getParameter("firstName");
        String lastName = ServletActionContext.getRequest().getParameter("lastName");
        if (firstName.equals("") && lastName.equals("")) {
            String message = "Please enter at least one search criteria";
            persons = null;
            addActionError(message);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
            return SUCCESS;
        }
        PersonDTO p = new PersonDTO();
        // (RemoteApiUtils.convertToEnPn(fName, mName, lName, prefix, suffix));
        p.setName(RemoteApiUtil.convertToEnPn(firstName, null, lastName, null, null));
        try {
            List<PersonDTO> list = new ArrayList<PersonDTO>();
            list = PaRegistry.getPoPersonEntityService().search(p);
            for (PersonDTO dto : list) {
                persons.add(convertToPaPerson(dto));
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return "contactpersons";
        }
        return "contactpersons";
    }

    /**
     * 
     * @return result
     */
    public String displayPersonsList() {
        String firstName = ServletActionContext.getRequest().getParameter("firstName");
        String lastName = ServletActionContext.getRequest().getParameter("lastName");
        if (firstName.equals("") && lastName.equals("")) {
            String message = "Please enter at least one search criteria";
            persons = null;
            addActionError(message);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
            return SUCCESS;
        }
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtil.convertToEnPn(firstName, null, lastName, null, null));
        try {
            List<PersonDTO> list = new ArrayList<PersonDTO>();
            list = PaRegistry.getPoPersonEntityService().search(p);
            for (PersonDTO dto : list) {
                persons.add(convertToPaPerson(dto));
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return SUCCESS;
        }
        return SUCCESS;
    }

    /**
     * 
     * @return result
     */
    public String displayPersonsListDisplayTag() {
        String firstName = ServletActionContext.getRequest().getParameter("firstName");
        String lastName = ServletActionContext.getRequest().getParameter("lastName");
        if (firstName.equals("") && lastName.equals("")) {
            String message = "Please enter at least one search criteria";
            persons = null;
            addActionError(message);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
            return SUCCESS;
        }
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtil.convertToEnPn(firstName, null, lastName, null, null));
        try {
            List<PersonDTO> list = new ArrayList<PersonDTO>();
            list = PaRegistry.getPoPersonEntityService().search(p);
            for (PersonDTO dto : list) {
                persons.add(convertToPaPerson(dto));
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return SUCCESS;
        }
        return "persons";
    }

    /**
     * 
     * @return result
     */
    public String displayOrgListDisplayTag() {
        try {
            countryList = (List) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
            if (countryList == null) {
                countryList = PaRegistry.getLookUpTableService().getCountries();
                ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
            }
            String orgName = ServletActionContext.getRequest().getParameter("orgName");
            // String nciOrgName =
            // ServletActionContext.getRequest().getParameter("nciNumber");
            String countryName = ServletActionContext.getRequest().getParameter("countryName");
            String cityName = ServletActionContext.getRequest().getParameter("cityName");
            String zipCode = ServletActionContext.getRequest().getParameter("zipCode");
            if (orgName.equals("") && countryName.equals("aaa") && cityName.equals("") && zipCode.equals("")) {
                String message = "Please enter at least one search criteria";
                orgs = null;
                addActionError(message);
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
                return SUCCESS;
            }
            // Set the values
            orgSearchCriteria.setOrgName(orgName);
            orgSearchCriteria.setOrgCity(cityName);
            orgSearchCriteria.setOrgCountry(countryName);
            orgSearchCriteria.setOrgZip(zipCode);
            //
            OrganizationDTO criteria = new OrganizationDTO();
            criteria.setName(EnOnConverter.convertToEnOn(orgName));
            criteria.setPostalAddress(AddressConverterUtil.create(null, null, cityName, null, zipCode, countryName));
            List<OrganizationDTO> callConvert = new ArrayList<OrganizationDTO>();
            callConvert = PaRegistry.getPoOrganizationEntityService().search(criteria);
            convertPoOrganizationDTO(callConvert);
            return "orgs";
        } catch (Exception e) {
            return "orgs";
        }
    }

    private void convertPoOrganizationDTO(List<OrganizationDTO> poOrgDtos) throws PAException {
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

    private String getCountryNameUsingCode(String code) throws PAException {
        if (!(countryList.size() > 0)) {
            countryList = (List<Country>) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
            if (countryList == null) {
                countryList = PaRegistry.getLookUpTableService().getCountries();
                ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
            }
        }
        for (int i = 0; i < countryList.size(); i++) {
            gov.nih.nci.pa.domain.Country country = (gov.nih.nci.pa.domain.Country) countryList.get(i);
            if (country.getAlpha3().toString().equals(code)) {
                return country.getName();
            }
        }
        return null;
    }

    private SearchPersonResultDisplay convertToPaPerson(PersonDTO poPerson) {
        SearchPersonResultDisplay prs = new SearchPersonResultDisplay();
        List<Enxp> list = poPerson.getName().getPart();
        Iterator ite = list.iterator();
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
        List<String> emails = DSetConverter.convertDSetToList(poPerson.getTelecomAddress(), "EMAIL");
        for (String email : emails) {
            emailList.append(email + ", \n");
        }
        prs.setId(Long.valueOf(poPerson.getIdentifier().getExtension()));
        prs.setEmail(emailList.toString());
        prs.setId(Long.valueOf(poPerson.getIdentifier().getExtension().toString()));
        return prs;
    }

    /**
     * @return the countryList
     */
    public List<Country> getCountryList() {
        return countryList;
    }

    /**
     * @param countryList
     *            the countryList to set
     */
    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    /**
     * @return the orgs
     */
    public List<SearchOrgResultDisplay> getOrgs() {
        return orgs;
    }

    /**
     * @param orgs
     *            the orgs to set
     */
    public void setOrgs(List<SearchOrgResultDisplay> orgs) {
        this.orgs = orgs;
    }

    /**
     * @return the persons
     */
    public List<SearchPersonResultDisplay> getPersons() {
        return persons;
    }

    /**
     * @param persons
     *            the persons to set
     */
    public void setPersons(List<SearchPersonResultDisplay> persons) {
        this.persons = persons;
    }
}
