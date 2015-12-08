package gov.nih.nci.pa.service.util;




import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import org.hibernate.Query;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.Family;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

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
}
