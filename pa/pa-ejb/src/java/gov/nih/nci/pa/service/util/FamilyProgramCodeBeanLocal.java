package gov.nih.nci.pa.service.util;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

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
    @Override
    public FamilyDTO getFamilyByPoId(Long familyPoId) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Updates a family DTO in db
     * @param familyDTO familyDTO
     */
    @Override
    public void update(FamilyDTO familyDTO) {
        // TODO Auto-generated method stub
        
    }

    /**
     * Inserts a new family DTO in db
     * @param familyDTO familyDTO
     */
    @Override
    public void create(FamilyDTO familyDTO) {
        // TODO Auto-generated method stub
        
    }

}
