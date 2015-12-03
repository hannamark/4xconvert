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
    FamilyDTO getFamilyDTOByPoId(Long familyPoId);
    
    /**
     * Updates a family DTO in db
     * @param familyDTO family dto
     * @return familydto family dto
     */
    FamilyDTO update(FamilyDTO familyDTO);
    
    /**
     * Inserts a new family DTO in db
     * @param familyDTO family dto object
     * @return familkyDTO family dto
     */
    FamilyDTO create(FamilyDTO familyDTO);

}
