package gov.nih.nci.pa.service.util;


import gov.nih.nci.pa.dto.FamilyDTO;

/**
 * FamilyProgramCodeService
 * @author lalit
 */
public interface FamilyProgramCodeService {
    
    
    /**
     * Returns the associated Family DTO for a given family po id
     * @param familyPoId family po id 
     * @return FamityDTO
     */
    FamilyDTO getFamilyByPoId(Long familyPoId);
    
    /**
     * Updates a family DTO in db
     * @param familyDTO family dto
     */
    void update(FamilyDTO familyDTO);
    
    /**
     * Inserts a new family DTO in db
     * @param familyDTO family dto object
     */
    void create(FamilyDTO familyDTO);

}
