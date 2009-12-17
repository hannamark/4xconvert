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

package gov.nih.nci.accrual.service;

import gov.nih.nci.accrual.convert.Converters;
import gov.nih.nci.accrual.convert.UserConverter;
import gov.nih.nci.accrual.dto.UserDto;
import gov.nih.nci.accrual.service.util.AccrualCsmUtil;
import gov.nih.nci.accrual.util.AccrualHibernateSessionInterceptor;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.USStateCode;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

/**
 * @author Lisa Kelley
 * @since Dec 9, 2009
 */
@Stateless
@Interceptors(AccrualHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
public class UserBeanLocal
        extends AbstractBaseAccrualBean<UserDto, RegistryUser, UserConverter>
        implements UserService {

    private static final String DEFAULT_CONTEXT_NAME = "ejbclient";
    private static final int MIN_PASSWORD_LENGTH = 8;    
    
    /**
     * {@inheritDoc}
     */
    public UserDto getUser(St isoLoginName) throws RemoteException {
        getLogger().info("Entering getUser().");
        String loginName = StConverter.convertToString(isoLoginName);
        validateLoginName(loginName);
        RegistryUser registryUser;
        try {
            registryUser = PaRegistry.getRegisterUserService().getUser(loginName);
        } catch (PAException e) {
            throw new RemoteException("Exception while retrieving user.", e);
        }
        
        UserDto resultDto = null;
        if (registryUser != null) { // user exists
            registryUser.setCountry(getCountryCode(registryUser.getCountry()));
            registryUser.setState(USStateCode.getByCode(registryUser.getState()).getName());
        
            try {
                resultDto = Converters.get(UserConverter.class).convertFromDomainToDto(registryUser);
            } catch (DataFormatException e) {
                throw new RemoteException("Iso conversion exception in getUser().", e);
            }
            
            resultDto.setPoOrganizationIdentifier(checkPoOrganizationId(resultDto.getPoOrganizationIdentifier()));
            resultDto.setPoPersonIdentifier(checkPoPersonId(resultDto.getPoPersonIdentifier()));
        
            // get the CSM user
            User csmUser = AccrualCsmUtil.getInstance().getCSMUser(loginName);
            resultDto.setLoginName(StConverter.convertToSt(csmUser.getLoginName()));
            resultDto.setPassword(StConverter.convertToSt(csmUser.getPassword()));
        }
    
        return resultDto;
    }
    
    /**
     * {@inheritDoc}
     */
    public UserDto createUser(UserDto dto) throws RemoteException {
        getLogger().info("Entering createUser().");
        String loginName = StConverter.convertToString(dto.getLoginName());
        validateLoginName(loginName);
        
        RegistryUser registryUser;
        try {
            registryUser = PaRegistry.getRegisterUserService().getUser(loginName);
        } catch (PAException e) {
            throw new RemoteException("Exception while retrieving user.", e);
        }
        
        if (registryUser != null) {
            throw new RemoteException("User already exists.");
        }
        
        validateData(dto);
        
        try {
            registryUser = Converters.get(UserConverter.class).convertFromDtoToDomain(dto);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in createUser().", e);
        }
        
        registryUser.setCountry(getCountryName(registryUser.getCountry()));
        registryUser.setState(USStateCode.valueOf(registryUser.getState()).getCode());        
        
        // create the CSM user
        User csmUser = AccrualCsmUtil.getInstance().createCSMUser(registryUser, 
            loginName, StConverter.convertToString(dto.getPassword()));
        registryUser.setCsmUserId(csmUser.getUserId());

        // create the user
        try {
            registryUser = PaRegistry.getRegisterUserService().createUser(registryUser);
        } catch (PAException e) {
            throw new RemoteException("Exception while creating user.", e);
        }
        
        registryUser.setCountry(getCountryCode(registryUser.getCountry()));
        registryUser.setState(USStateCode.getByCode(registryUser.getState()).getName());
        
        UserDto resultDto;
        try {
            resultDto = Converters.get(UserConverter.class).convertFromDomainToDto(registryUser);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in createUser().", e);
        }
        
        resultDto.setLoginName(StConverter.convertToSt(csmUser.getLoginName()));
        resultDto.setPassword(StConverter.convertToSt(csmUser.getPassword()));
        return resultDto;
    }
    
    /**
     * {@inheritDoc}
     */
    @RolesAllowed({"gridClient", "client" , "Abstractor" , "Submitter" , "Outcomes" })
    public UserDto updateUser(UserDto dto) throws RemoteException {
        getLogger().info("Entering updateUser().");
        String loginName = StConverter.convertToString(dto.getLoginName());
        validateLoginName(loginName);
        
        RegistryUser registryUser;
        try {
            registryUser = PaRegistry.getRegisterUserService().getUser(loginName);
        } catch (PAException e) {
            throw new RemoteException("Exception while retrieving user.", e);
        }
        
        if (registryUser == null) {
            throw new RemoteException("User does not exist.");
        }
        
        Long csmUserId = registryUser.getCsmUserId();
        validateData(dto);
        
        try {
            registryUser = Converters.get(UserConverter.class).convertFromDtoToDomain(dto);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in updateUser().", e);
        }
        
        registryUser.setCsmUserId(csmUserId);
        registryUser.setCountry(getCountryName(registryUser.getCountry()));
        registryUser.setState(USStateCode.valueOf(registryUser.getState()).getCode());
        
        // update the CSM user
        User csmUser = AccrualCsmUtil.getInstance().updateCSMUser(registryUser, 
            loginName, StConverter.convertToString(dto.getPassword()));
        
        // update the user
        try {
            registryUser = PaRegistry.getRegisterUserService().updateUser(registryUser);
        } catch (PAException e) {
            throw new RemoteException("Exception while updating user.", e);
        }
        
        registryUser.setCountry(getCountryCode(registryUser.getCountry()));
        registryUser.setState(USStateCode.getByCode(registryUser.getState()).getName());
        
        UserDto resultDto;
        try {
            resultDto = Converters.get(UserConverter.class).convertFromDomainToDto(registryUser);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in updateUser().", e);
        }
    
        resultDto.setLoginName(StConverter.convertToSt(csmUser.getLoginName()));
        resultDto.setPassword(StConverter.convertToSt(csmUser.getPassword()));
        return resultDto;
    }

    private void validateLoginName(String loginName) throws RemoteException {
        if (PAUtil.isEmpty(loginName)) {
            throw new RemoteException("LoginName must be set.");
        }
        
        String contextName;
        try {
            contextName =
                getEjbContext() != null ? getEjbContext().getCallerPrincipal().getName() : DEFAULT_CONTEXT_NAME;
        } catch (Exception e) {
            contextName = DEFAULT_CONTEXT_NAME;
        }
        if (!DEFAULT_CONTEXT_NAME.equals(contextName) && !loginName.equals(contextName)) {
            throw new RemoteException("LoginName does not match context.");
        }
    }
    
    private void validateData(UserDto dto) throws RemoteException {        
        // validate password
        validatePassword(StConverter.convertToString(dto.getPassword()));
        
        // validate first name
        if (PAUtil.isEmpty(StConverter.convertToString(dto.getFirstName()))) {
            throw new RemoteException("First Name must be set.");
        }
        
        // validate last name
        if (PAUtil.isEmpty(StConverter.convertToString(dto.getLastName()))) {
            throw new RemoteException("Last Name must be set.");
        }
        
        // validate country
        validateCountry(StConverter.convertToString(dto.getCountry()));
        
        // validate state
        validateState(StConverter.convertToString(dto.getState()), StConverter.convertToString(dto.getCountry()));
        
        // validate phone number
        if (PAUtil.isEmpty(StConverter.convertToString(dto.getPhone()))) {
            throw new RemoteException("Phone must be set.");
        }
        
        // validate organization
        if (PAUtil.isEmpty(StConverter.convertToString(dto.getAffiliateOrg()))) {
            throw new RemoteException("Organization Affiliation must be set.");
        }
        
        // validate PO organization identifier
        if (PAUtil.isIiNull(dto.getPoOrganizationIdentifier())) {
            throw new RemoteException("PO Organization Identifier must be set.");
        }
        
        // validate PO person identifier
        if (PAUtil.isIiNull(dto.getPoPersonIdentifier())) {
            throw new RemoteException("PO Person Identifier must be set.");
        }
    }
    
    private void validatePassword(String password) throws RemoteException {
        // password must have min 8 chars with at least one not alpha-numeric char and one digit
        if (PAUtil.isEmpty(password)) {
            throw new RemoteException("Password must be set.");
        } 
    
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RemoteException("Password must be at least 8 characters in length.");
        }
        
        Pattern oneNonAlphaNumeric = Pattern.compile("(.*\\p{Punct}.*)+");
        Matcher oneNonAlphaNumericMatcher = oneNonAlphaNumeric.matcher(password);       
        if (!oneNonAlphaNumericMatcher.find(0)) {
            throw new RemoteException("Password must contain at least one special character.");
        }
        
        Pattern oneDigit = Pattern.compile("(.*\\p{Digit}.*)+");
        Matcher oneDigitMatcher = oneDigit.matcher(password);
        if (!oneDigitMatcher.find(0)) {
            throw new RemoteException("Password must contain at least one digit.");
        }
    }
    
    private void validateCountry(String country) throws RemoteException {
        if (PAUtil.isEmpty(country)) {
            throw new RemoteException("Country must be set.");
        }
        
        List<Country> countries;
        try {
            countries = PaRegistry.getLookUpTableService().getCountries();
        } catch (PAException e) {
            throw new RemoteException("Exception while retrieving countries.", e);
        }
        
        boolean countryFound = false;
        for (Country countryIterator : countries) {
           if (countryIterator.getAlpha3().equals(country)) {
               countryFound = true;
               break; // since match has been found
           }
        }
        
        if (!countryFound) {
            throw new RemoteException("Invalid country specified.");
        }
    }
    
    private void validateState(String state, String country) throws RemoteException {
        if (PAUtil.isEmpty(state)) {
            throw new RemoteException("State must be set.");
        }
    
        List<USStateCode> states = Arrays.asList(USStateCode.values());
        boolean stateFound = false;
        for (USStateCode stateIterator : states) {
           if (stateIterator.getName().equals(state)) {
               stateFound = true;
               break; // since match has been found
           }
        }
        
        if (!stateFound) {
            throw new RemoteException("Invalid state specified.");
        }
        
        if ("USA".equals(country)) {
            if ("INTERNATIONAL".equals(state)) {
                throw new RemoteException("State must be a State of the United States.");
            }
        } else {
            if (!"INTERNATIONAL".equals(state)) {
                throw new RemoteException("State must be 'None' for non-US countries");
            }
        }
    }
    
    private String getCountryCode(String countryName) throws RemoteException {
        List<Country> countries;
        try {
            countries = PaRegistry.getLookUpTableService().getCountries();
        } catch (PAException e) {
            throw new RemoteException("Exception while retrieving countries.", e);
        }
        
        for (int i = 0; i < countries.size(); i++) {
            Country country = (Country) countries.get(i);
            if (country.getName().equals(countryName)) {
                return country.getAlpha3();
            }
        }
        
        throw new RemoteException("Error while retrieving country code");
    }
    
    private String getCountryName(String countryCode) throws RemoteException {
        List<Country> countries;
        try {
            countries = PaRegistry.getLookUpTableService().getCountries();
        } catch (PAException e) {
            throw new RemoteException("Exception while retrieving countries.", e);
        }
        
        for (int i = 0; i < countries.size(); i++) {
            Country country = (Country) countries.get(i);
            if (country.getAlpha3().equals(countryCode)) {
                return country.getName();
            }
        }
        
        throw new RemoteException("Error while retrieving country name");
    }
    
    private Ii checkPoOrganizationId(Ii poOrganizationId) throws RemoteException {
        if (poOrganizationId == null) {
            return null;
        }
        
        OrganizationDTO organization;
        try {
            organization = PoRegistry.getOrganizationEntityService().getOrganization(poOrganizationId);            
        } catch (NullifiedEntityException e) {
            return null;
        }
        
        return organization.getIdentifier();
    }
    
    private Ii checkPoPersonId(Ii poPersonId) throws RemoteException {
        if (poPersonId == null) {
            return null;
        }
        
        PersonDTO person;
        try {
            person = PoRegistry.getPersonEntityService().getPerson(poPersonId);
        } catch (NullifiedEntityException e) {
            return null;
        }
        
        return person.getIdentifier();
    }
}
