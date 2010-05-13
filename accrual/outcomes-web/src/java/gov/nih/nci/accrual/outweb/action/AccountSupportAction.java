/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The OutcomesServices
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This OutcomesServices Software License (the License) is between NCI and You. You (or
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
 * its rights in the OutcomesServices Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the OutcomesServices Software; (ii) distribute and
 * have distributed to and by third parties the OutcomesServices Software and any
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
package gov.nih.nci.accrual.outweb.action;

import gov.nih.nci.accrual.outweb.dto.util.AddressConverter;
import gov.nih.nci.accrual.outweb.dto.util.PersonNameConverter;
import gov.nih.nci.accrual.outweb.dto.util.PhysicianWebDTO;
import gov.nih.nci.accrual.outweb.dto.util.TreatmentSiteWebDTO;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;

/**
 * @author smatyas
 *
 */
@SuppressWarnings({ "PMD" })
public class AccountSupportAction extends AbstractAccrualAction {
    
    private static final long serialVersionUID = 1L;
    
    private TreatmentSiteWebDTO treatmentSiteSearchCriteria = new TreatmentSiteWebDTO();
    private List<TreatmentSiteWebDTO> treatmentSites = new ArrayList<TreatmentSiteWebDTO>();
    private PhysicianWebDTO physicianSearchCriteria = new PhysicianWebDTO();
    private List<PhysicianWebDTO> physicians = new ArrayList<PhysicianWebDTO>();


    /**
     * Lookup data.
     * @return result for next action
     */
    public String lookupTreatmentSite() {
        OrganizationDTO criteria = createOrgSearchCriteria();

        try {
            List<OrganizationDTO> poOrgDtos = new ArrayList<OrganizationDTO>();
            if (criteria.getIdentifier() != null || criteria.getName() != null || criteria.getPostalAddress() != null) {
                LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
                poOrgDtos = PoRegistry.getOrganizationEntityService().search(criteria, limit);
            }

            for (OrganizationDTO organizationDTO : poOrgDtos) {
                TreatmentSiteWebDTO treatmentSite = convert(organizationDTO);
                getTreatmentSites().add(treatmentSite);
            }
        } catch (Exception e) {
            treatmentSites = null;
            LOG.error("Error occured while searching PO Organizations " + e.getMessage(), e);
        }

        return SUCCESS;
    }

    private TreatmentSiteWebDTO convert(OrganizationDTO organizationDTO) {
        TreatmentSiteWebDTO treatmentSite = new TreatmentSiteWebDTO();
        treatmentSite.setId(organizationDTO.getIdentifier().getExtension().toString());
        treatmentSite.setName(organizationDTO.getName().getPart().get(0).getValue());
        new AddressConverter().convert(organizationDTO.getPostalAddress(), treatmentSite);
        return treatmentSite;
    }

    private OrganizationDTO createOrgSearchCriteria() {
        OrganizationDTO criteria = new OrganizationDTO();
        criteria.setName(EnOnConverter.convertToEnOn(getTreatmentSiteSearchCriteria().getName()));
        criteria.setPostalAddress(AddressConverterUtil.create(null,
                                                              null,
                                                              getTreatmentSiteSearchCriteria().getCity(),
                                                              getTreatmentSiteSearchCriteria().getState(),
                                                              getTreatmentSiteSearchCriteria().getZipCode(),
                                                              getTreatmentSiteSearchCriteria().getCountry()));
        return criteria;
    }
    
    /**
     * Lookup Persons in PO.
     * @return the result
     */
    public String lookupPhysician() {
        PersonDTO criteria = createPersonSearchCriteria();

        try {
            List<PersonDTO> poPersonDtos = new ArrayList<PersonDTO>();
            if (criteria.getIdentifier() != null || criteria.getName() != null) {
                LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
                poPersonDtos = PoRegistry.getPersonEntityService().search(criteria, limit);
            }

            for (PersonDTO poPersonDto : poPersonDtos) {
                PhysicianWebDTO physician = convert(poPersonDto);
                getPhysicians().add(physician);
            }
        } catch (Exception e) {
            setPhysicians(null);
            LOG.error("Error occured while searching PO Persons " + e.getMessage(), e);
        }

        return SUCCESS;
    }

    private PhysicianWebDTO convert(PersonDTO poPersonDto) {
        PhysicianWebDTO physician = new PhysicianWebDTO();
        physician.setId(poPersonDto.getIdentifier().getExtension().toString());

        List<Enxp> nameList = poPersonDto.getName().getPart();
        new PersonNameConverter().convert(nameList, physician);
        new AddressConverter().convert(poPersonDto.getPostalAddress(), physician);
        return physician;
    }

    private PersonDTO createPersonSearchCriteria() {
        PersonDTO criteria = new PersonDTO();
        criteria.setName(EnPnConverter.convertToEnPn(
            getPhysicianSearchCriteria().getFirstName(), null, getPhysicianSearchCriteria().getLastName(), null, null));
        criteria.setPostalAddress(AddressConverterUtil.create(null,
                                                              null,
                                                              getPhysicianSearchCriteria().getCity(),
                                                              getPhysicianSearchCriteria().getState(),
                                                              getPhysicianSearchCriteria().getZipCode(),
                                                              getPhysicianSearchCriteria().getCountry()));
        return criteria;
    }

    /**
     * @param treatmentSiteSearchCriteria the treatmentSiteSearchCriteria to set
     */
    public void setTreatmentSiteSearchCriteria(TreatmentSiteWebDTO treatmentSiteSearchCriteria) {
        this.treatmentSiteSearchCriteria = treatmentSiteSearchCriteria;
    }

    /**
     * @return the treatmentSiteSearchCriteria
     */
    public TreatmentSiteWebDTO getTreatmentSiteSearchCriteria() {
        return treatmentSiteSearchCriteria;
    }

    /**
     * @param treatmentSites the treatmentSites to set
     */
    public void setTreatmentSites(List<TreatmentSiteWebDTO> treatmentSites) {
        this.treatmentSites = treatmentSites;
    }

    /**
     * @return the treatmentSites
     */
    public List<TreatmentSiteWebDTO> getTreatmentSites() {
        return treatmentSites;
    }

    /**
     * @param physicianSearchCriteria the physicianSearchCriteria to set
     */
    public void setPhysicianSearchCriteria(PhysicianWebDTO physicianSearchCriteria) {
        this.physicianSearchCriteria = physicianSearchCriteria;
    }

    /**
     * @return the physicianSearchCriteria
     */
    public PhysicianWebDTO getPhysicianSearchCriteria() {
        return physicianSearchCriteria;
    }

    /**
     * @param physicians the physicians to set
     */
    public void setPhysicians(List<PhysicianWebDTO> physicians) {
        this.physicians = physicians;
    }

    /**
     * @return the physicians
     */
    public List<PhysicianWebDTO> getPhysicians() {
        return physicians;
    }
    

    /**
     * Lookup data.
     * @return result for next action
     */
    public String initTreatmentSiteLookup() {
        treatmentSites = null;
        return "initTreatmentSiteLookup";
    }

    /**
     * Lookup data.
     * @return result for next action
     */
    public String initPhysicianLookup() {
        physicians = null;
        return "initPhysicianLookup";
    }
    

    /**
     *
     * @return String result
     */
    public String lookupCreatePerson() {
        String firstName = ServletActionContext.getRequest().getParameter("firstName");
        required(firstName, "First Name");
        String lastName = ServletActionContext.getRequest().getParameter("lastName");
        required(lastName, "Last Name");
        String email = ServletActionContext.getRequest().getParameter("email");
        required(email, "Email");
        if (!PAUtil.isValidEmail(email)) {
            addActionError("Email address is invalid");
        }
        String streetAddr = ServletActionContext.getRequest().getParameter("streetAddr");
        required(streetAddr, "Street address");
        String city = ServletActionContext.getRequest().getParameter("city");
        required(city, "City");
        String zip = ServletActionContext.getRequest().getParameter("zip");
        required(zip, "Zip");
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
            PoRegistry.getPersonEntityService().createPerson(dto);
            physicianSearchCriteria.setFirstName(firstName);
            physicianSearchCriteria.setLastName(lastName);
            physicianSearchCriteria.setCity(city);
            physicianSearchCriteria.setState(state);
            physicianSearchCriteria.setZipCode(zip);
            physicianSearchCriteria.setCountry(country);
            lookupPhysician();
            physicianSearchCriteria = new PhysicianWebDTO();
        } catch (EntityValidationException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        } catch (RemoteException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        } catch (URISyntaxException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        } catch (CurationException e) {
            handleExceptions(e.getMessage(), PERS_CREATE_RESPONSE);
        }
        return PERS_CREATE_RESPONSE;
    }

    private void required(String value, String fieldName) {
        if (value != null && !PAUtil.isNotEmpty(value)) {
            addActionError(String.format("%s is a required field", fieldName));
        }
    }

    /**
     * @return result
     */
    public String lookupCreateOrganization() {
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
            PoRegistry.getOrganizationEntityService().createOrganization(orgDto);
            treatmentSiteSearchCriteria.setName(orgName);
            treatmentSiteSearchCriteria.setCity(cityName);
            treatmentSiteSearchCriteria.setState(stateName);
            treatmentSiteSearchCriteria.setZipCode(zipCode);
            treatmentSiteSearchCriteria.setCountry(countryName);
            lookupTreatmentSite();
            treatmentSiteSearchCriteria = new TreatmentSiteWebDTO();
        } catch (URISyntaxException e) {
            handleExceptions(e.getMessage(), ORG_CREATE_RESPONSE);
        } catch (EntityValidationException e) {
            handleExceptions(e.getMessage(), ORG_CREATE_RESPONSE);
        } catch (CurationException e) {
            handleExceptions(e.getMessage(), ORG_CREATE_RESPONSE);
        } catch (RemoteException e) {
            handleExceptions(e.getMessage(), ORG_CREATE_RESPONSE);
        }
        return ORG_CREATE_RESPONSE;
    }

    private static final String PERS_CREATE_RESPONSE = "create_pers_response";
    private static final String ORG_CREATE_RESPONSE = "create_org_response";
    private final int ausStateCodeLen = 3;


    private String handleExceptions(String message, String returnString) {
        addActionError(message);
        ServletActionContext.getRequest().setAttribute("failureMessage", message);
        return returnString;
    }

}
