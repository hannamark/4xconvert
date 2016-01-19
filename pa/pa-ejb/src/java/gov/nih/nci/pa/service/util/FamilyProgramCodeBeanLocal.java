package gov.nih.nci.pa.service.util;


import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.Family;
import gov.nih.nci.pa.domain.ProgramCode;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * FamilyProgramCodeBeanLocal
 * @author lalit 
 */
@Stateless
@Interceptors({ RemoteAuthorizationInterceptor.class,
       PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FamilyProgramCodeBeanLocal implements FamilyProgramCodeServiceLocal {

    private static final String PROGRAM_CODE_END_DATE_PROPERTY_NAME = "programcodes.reporting.default.end_date";
    private static final String PROGRAM_CODE_LENGTH_PROPERTY_NAME = "programcodes.reporting.default.length";
    /**
     * DUPE_PROGRAM_CODE
     */
    public static final String DUPE_PROGRAM_CODE = "This program code already exists in the system."
            + " Please add another program code";

    private static final Logger LOG = Logger.getLogger(FamilyProgramCodeBeanLocal.class);

    @EJB
    private LookUpTableServiceRemote lookUpTableService;

    /**
     * Will set the LookUpTableService
     * @param lookUpTableService - lookup table service
     */
    public void setLookUpTableService(LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }

    /**
     * Will creates family in PA based on PO
     *
     * @throws PAException when there is an error
     */
    @Override
    public void populate() throws PAException {

        String strPgcEndDate = lookUpTableService.
                getPropertyValue(PROGRAM_CODE_END_DATE_PROPERTY_NAME);
        String strPgcLength = lookUpTableService.
                getPropertyValue(PROGRAM_CODE_LENGTH_PROPERTY_NAME);
        Date endDate = PAUtil.dateStringToDate(strPgcEndDate);
        Integer length = Integer.parseInt(strPgcLength);
        LOG.info("Copying families into PA. [default EndDate:" + strPgcEndDate + ", default Length:" + length + "]");

        List<gov.nih.nci.services.family.FamilyDTO> familiesInPoList = FamilyHelper.getAllFamilies();
        LinkedHashMap<Long, FamilyDTO> index = new LinkedHashMap<Long, FamilyDTO>();
        if (CollectionUtils.isNotEmpty(familiesInPoList)) {
            for (gov.nih.nci.services.family.FamilyDTO f : familiesInPoList) {
                Long poId = IiConverter.convertToLong(f.getIdentifier());
                FamilyDTO dto = new FamilyDTO(null, poId, endDate, length);
                dto.setName(EnOnConverter.convertEnOnToString(f.getName()));
                index.put(poId, new FamilyDTO(null, poId, endDate, length));
            }

            //fetch all family poids from PA
            List<Long> existingPoIds = PaHibernateUtil.getCurrentSession()
                    .createQuery("select fm.poId from Family fm").list();

            LOG.info(" Found [" + index.size() + "] families in PO and [" + existingPoIds.size() + "] families in PA");

            for (Long poId : existingPoIds) {
                index.remove(poId);
            }

            for (FamilyDTO dto : index.values()) {
                FamilyDTO persisted = create(dto);
                if (LOG.isInfoEnabled()) {
                    LOG.info(String.format("Created PA family [id:%s, poID:%s,name:%s, length:%s, endDate:%s]",
                            persisted.getId(), persisted.getPoId(), persisted.getName(),
                            persisted.getReportingPeriodLength(), persisted.getReportingPeriodEndDate()));
                }
            }
        }
        LOG.info("Finished copying families[created:" + index.size() + "]");
    }

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
     * @throws PAValidationException - when there is a validation error
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
