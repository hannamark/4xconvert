/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.domain.StructuralRole;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.convert.StudyContactConverter;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;

/**
 * @author asharma
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.NPathComplexity" })
@Interceptors(HibernateSessionInterceptor.class)
public class StudyContactBeanLocal extends AbstractRoleIsoService<StudyContactDTO, StudyContact, StudyContactConverter>
implements StudyContactServiceLocal { 
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyContactDTO> search(StudyContactDTO dto, LimitOffset pagingParams) throws PAException,
    TooManyResultsException {
        if (dto == null) {
            getLogger().error(" StudyContactDTO should not be null ");
            throw new PAException(" StudyContactDTO should not be null ");
        }
        Session session = null;
        List<StudyContact> studyContactList = null;
        session = HibernateUtil.getCurrentSession();
        StudyContact exampleDO = new StudyContact();

        Criteria criteria = session.createCriteria(StudyContact.class);
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            exampleDO.setId(IiConverter.convertToLong(dto.getIdentifier()));
        }
        if (!PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            criteria.createAlias("studyProtocol", "sp")
            .add(Expression.eq("sp.id", IiConverter.convertToLong(dto.getStudyProtocolIdentifier())));
        }
        if (!PAUtil.isIiNull(dto.getHealthCareProviderIi())) {
            if (PAConstants.PA_INTERNAL.equals(dto.getHealthCareProviderIi().getIdentifierName())) { 
                criteria.createAlias("healthCareProvider", "hcp")
                .add(Expression.eq("hcp.id", IiConverter.convertToLong(dto.getHealthCareProviderIi())));
            } else {
                criteria.createAlias("healthCareProvider", "hcp")
                .add(Expression.eq("hcp.identifier", IiConverter.convertToString(dto.getHealthCareProviderIi())));
            }
        }

        if (!PAUtil.isIiNull(dto.getClinicalResearchStaffIi())) {
            if (PAConstants.PA_INTERNAL.equals(dto.getClinicalResearchStaffIi().getIdentifierName())) { 
                criteria.createAlias("clinicalResearchStaff", "crs")
                .add(Expression.eq("crs.id", IiConverter.convertToLong(dto.getClinicalResearchStaffIi())));
            } else {
                criteria.createAlias("clinicalResearchStaff", "crs")
                .add(Expression.eq("crs.identifier", IiConverter.convertToString(dto.getClinicalResearchStaffIi())));
            }
        }

        if (!PAUtil.isIiNull(dto.getOrganizationalContactIi())) {
            if (PAConstants.PA_INTERNAL.equals(dto.getOrganizationalContactIi().getIdentifierName())) { 
                criteria.createAlias("organizationalContact", "oc")
                .add(Expression.eq("oc.id", IiConverter.convertToLong(dto.getOrganizationalContactIi())));
            } else {
                criteria.createAlias("organizationalContact", "oc")
                .add(Expression.eq("oc.identifier", IiConverter.convertToString(dto.getOrganizationalContactIi())));
            }
        }

        if (dto.getStatusCode() != null) {
          exampleDO.setStatusCode(FunctionalRoleStatusCode.getByCode(dto.getStatusCode().getCode()));
        }
        if (dto.getRoleCode() != null) {
            exampleDO.setRoleCode(StudyContactRoleCode.getByCode(dto.getRoleCode().getCode()));            
        }
        if (dto.getTelecomAddresses() != null) {
            List<String> retList = null;            
            retList = DSetConverter.convertDSetToList(dto.getTelecomAddresses(), "EMAIL");
            if (retList != null && !retList.isEmpty()) {
                exampleDO.setEmail(retList.get(0).toString());
            }
            retList = DSetConverter.convertDSetToList(dto.getTelecomAddresses(), "PHONE");
            if (retList != null && !retList.isEmpty()) {
                exampleDO.setPhone(retList.get(0).toString());
            }
        }
        Example example = Example.create(exampleDO);
        criteria.add(example);
        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        criteria.setMaxResults(maxLimit);
        criteria.setFirstResult(pagingParams.getOffset());
        studyContactList = criteria.list();

        if (studyContactList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        return convertFromDomainToDTO(studyContactList);
    }
    
    private List<StudyContactDTO> convertFromDomainToDTO(List<StudyContact> studyContactList) throws PAException {
        List<StudyContactDTO> studyContactDTOList = null;
        StudyContactConverter scConverter = new StudyContactConverter();
        if (studyContactList != null) { 
            studyContactDTOList = new ArrayList<StudyContactDTO>();
            for (StudyContact ss : studyContactList) {
                StudyContactDTO studyContactDTO = scConverter.convertFromDomainToDto(ss);
                studyContactDTOList.add(studyContactDTO);
            }
        }
        return studyContactDTOList;
    }
    /**
     * @param dto dto
     * @throws PAException e
     * @return updated dto
     */
    @Override
    public StudyContactDTO update(StudyContactDTO dto) throws PAException {
        validate(dto);
        getStatusCode(dto);
        return super.update(dto);
    }
    /**
     * validates the dto.
     * @param dto dto to validate.
     * @throws PAException e
     */
    @Override
    public void validate(StudyContactDTO dto) throws PAException {
        PAServiceUtils paServiceUtil = new PAServiceUtils();
        if (!PAUtil.isIiNull(dto.getClinicalResearchStaffIi()) && !PAUtil.isDSetTelNull(dto.getTelecomAddresses())) {
            StructuralRole sr = paServiceUtil.getStructuralRole(IiConverter.convertToPoClinicalResearchStaffIi(
                    dto.getClinicalResearchStaffIi().getExtension()));
            if (sr != null) {
                ClinicalResearchStaffDTO  poSrDto = (ClinicalResearchStaffDTO) paServiceUtil.getCorrelationByIi(
                        IiConverter.convertToPoClinicalResearchStaffIi(sr.getIdentifier()));
                if (paServiceUtil.isEntityCountryUSAOrCanada(poSrDto.getPlayerIdentifier())
                        && !PAUtil.isPhoneValidForUSA(DSetConverter.convertDSetToList(
                                dto.getTelecomAddresses(), PAConstants.PHONE).get(0))) {
                    throw new PAException("Please enter phone in xxx-xxx-xxxx format for USA or CANADA");
                }
            }
        }
        
        
    }
    /**
     * @param dto dto to validate
     * @throws PAException e
     * @return dto
     */
    @Override
    public StudyContactDTO create(StudyContactDTO dto) throws PAException {
        validate(dto);
        return super.create(dto);
    }
    private void getStatusCode(StudyContactDTO dto) throws PAException {
        PAServiceUtils paServiceUtil = new PAServiceUtils();
        StructuralRole sr =  null;
        if (!PAUtil.isIiNull(dto.getClinicalResearchStaffIi())) {
            sr = paServiceUtil.getStructuralRole(IiConverter.convertToPoClinicalResearchStaffIi(
                    dto.getClinicalResearchStaffIi().getExtension()));
        }
        if (!PAUtil.isIiNull(dto.getHealthCareProviderIi())) {
            sr = paServiceUtil.getStructuralRole(IiConverter.convertToPoHealtcareProviderIi(
                    dto.getHealthCareProviderIi().getExtension()));
        }
        if (!PAUtil.isIiNull(dto.getOrganizationalContactIi())) {
            sr = paServiceUtil.getStructuralRole(IiConverter.convertToPoOrganizationalContactIi(
                    dto.getOrganizationalContactIi().getExtension()));
        }
        if (sr != null) {
               dto.setStatusCode(getFunctionalRoleStatusCode(CdConverter.convertStringToCd(
                       sr.getStatusCode().getCode()), ActStatusCode.ACTIVE));    
         }
        
    }
}
