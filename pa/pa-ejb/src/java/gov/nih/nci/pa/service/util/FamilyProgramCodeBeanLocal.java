package gov.nih.nci.pa.service.util;




import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.Family;
import gov.nih.nci.pa.domain.ProgramCode;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.Query;

/**
 * FamilyProgramCodeBeanLocal
 * @author lalit 
 */
@Stateless
@Interceptors({ RemoteAuthorizationInterceptor.class,
       PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FamilyProgramCodeBeanLocal implements FamilyProgramCodeServiceLocal {
    
    /**
     * DUPE_PROGRAM_CODE
     */
    public static final String DUPE_PROGRAM_CODE = "This program code already exists in the system."
            + " Please add another program code";

    /**
     * Returns the associated Family DTO for a given family po id
     * @param familyPoId familyPoId
     * @return FamityDTO
     */
    @SuppressWarnings("unchecked")
    private Family getFamilyByPoId(Long familyPoId) {        
        
        Query familyFetchQuery = PaHibernateUtil
                .getCurrentSession()
                .createQuery(
                        "select fm from Family fm where fm.poId=:poId");
        
        familyFetchQuery.setParameter("poId", familyPoId);        
        
        Object result = familyFetchQuery.uniqueResult();
                    
        if (result != null) {
            return ((Family) result);
        } else {
            gov.nih.nci.services.family.FamilyDTO dto = FamilyHelper.getPOFamilyByPOID(familyPoId);
            if (dto != null) {
                FamilyDTO familyDTO = new FamilyDTO(IiConverter.convertToLong(dto.getIdentifier()));
                familyDTO.setName(EnOnConverter.convertEnOnToString(dto.getName()));
                return convert(create(familyDTO));
            } 
        }
        return null;                      
    }
    
    /**
     * Returns the associated Family DTO for a given family po id
     * @param familyPoId familyPoId
     * @return FamityDTO
     */
    @Override
    @SuppressWarnings("unchecked")
    public FamilyDTO getFamilyDTOByPoId(Long familyPoId) {       
        
        Object result = getFamilyByPoId(familyPoId);        
        if (result != null) {
            return convert((Family) result);
        }
        return null;                              
    }

    /**
     * Updates a family DTO in db
     * @param familyDTO familyDTO
     * @return familyDTO updated familyDTO
     */
    @Override
    public FamilyDTO update(FamilyDTO familyDTO) {
        Family family = getFamilyByPoId(familyDTO.getPoId());
        family.setReportingPeriodEnd(familyDTO.getReportingPeriodEndDate());
        family.setReportingPeriodLength(familyDTO.getReportingPeriodLength());
        PaHibernateUtil.getCurrentSession().saveOrUpdate(family);
        return convert(family);     
    }
    
    /**
     * Inserts a new family DTO in db
     * @param familyDTO familyDTO
     * @return familyDTO new familyDTO
     */
    @Override
    public FamilyDTO create(FamilyDTO familyDTO) {        
        Family family = convert(familyDTO);
        PaHibernateUtil.getCurrentSession().saveOrUpdate(family);
        return convert(family);
    }

    FamilyDTO convert(Family bo) {
        FamilyDTO familyDTO = new FamilyDTO(bo.getId(), bo.getPoId(),
                bo.getReportingPeriodEnd(), bo.getReportingPeriodLength());
        for (ProgramCode pg : bo.getProgramCodes()) {
            ProgramCodeDTO dto = new ProgramCodeDTO();
            dto.setId(pg.getId());
            dto.setProgramName(pg.getProgramName());
            dto.setProgramCode(pg.getProgramCode());
            dto.setActive(pg.getStatusCode() == ActiveInactiveCode.ACTIVE);
            familyDTO.getProgramCodes().add(dto);
        }
        return familyDTO;
    }
    
    Family convert(FamilyDTO dto) {
        Family createFamily = new Family();
        createFamily.setId(dto.getId());
        createFamily.setPoId(dto.getPoId());
        createFamily.setReportingPeriodEnd(dto.getReportingPeriodEndDate());
        createFamily.setReportingPeriodLength(dto.getReportingPeriodLength());
        return createFamily;        
    }
    
    /**
     * Creates and inserts a new Program Code in db
     * @param familyDTO family dto
     * @param programCodeDTO programCodeDTO
     * @return ProgramCodeDTO
     * @throws PAValidationException if program code already exists
     */

    @Override
    public ProgramCodeDTO createProgramCode(FamilyDTO familyDTO, ProgramCodeDTO programCodeDTO)
            throws PAValidationException {
        Family family = getFamilyByPoId(familyDTO.getPoId());
        ProgramCode domainProgramCode = convertProgramCodeToDomain(programCodeDTO);
        validateProgramCodeUniqueness(family, programCodeDTO.getProgramCode());
        domainProgramCode.setFamily(family);
        family.getProgramCodes().add(domainProgramCode);
        PaHibernateUtil.getCurrentSession().saveOrUpdate(domainProgramCode);

        return convertDomainProgramCodeToDTO(domainProgramCode);

    }

    private void validateProgramCodeUniqueness(Family family,
            String programCode) throws PAValidationException {
        if (family.findActiveProgramCodeByCode(programCode) != null) {
            throw new PAValidationException(
                    DUPE_PROGRAM_CODE);
        }
    }
    
    private ProgramCode convertProgramCodeToDomain(ProgramCodeDTO programCodeDTO) {
        ProgramCode programCode = new ProgramCode();
        programCode.setProgramCode(programCodeDTO.getProgramCode());
        programCode.setProgramName(programCodeDTO.getProgramName());
        programCode.setStatusCode(programCodeDTO.isActive()? ActiveInactiveCode.ACTIVE 
                : ActiveInactiveCode.INACTIVE);
       return programCode;
    }
    
    private ProgramCodeDTO convertDomainProgramCodeToDTO(ProgramCode programCode) {
        ProgramCodeDTO programCodeDTO = new ProgramCodeDTO();
        programCodeDTO.setId(programCode.getId());
        programCodeDTO.setProgramName(programCode.getProgramName());
        programCodeDTO.setProgramCode(programCode.getProgramCode());
        programCodeDTO.setActive(programCode.getStatusCode() == ActiveInactiveCode.ACTIVE);
       return programCodeDTO;
    }
}
