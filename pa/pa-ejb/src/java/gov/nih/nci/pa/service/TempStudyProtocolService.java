package gov.nih.nci.pa.service;

import java.util.List;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.TempStudyFundingDTO;
import gov.nih.nci.pa.iso.dto.TempStudyIndIdeDTO;
import gov.nih.nci.pa.iso.dto.TempStudyProtocolDTO;

/**
 * 
 * @author Vrushali
 *
 */
public interface TempStudyProtocolService  {
    
    /**
     * 
     * @param ii primary id of StudyProtocol
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    TempStudyProtocolDTO getTempStudyProtocol(Ii ii) throws PAException;
    /**
     * for creating a new ISP.
     * @param ispDTO  for isp
     * @return ii ii
     * @throws PAException exception
     */
    Ii createTempStudyProtocol(TempStudyProtocolDTO ispDTO) throws PAException;
    /**
     * 
     * @param dto criteria
     * @param pagingParams p
     * @return list of dto
     * @throws PAException on err
     * @throws TooManyResultsException on err
     */
    List<TempStudyProtocolDTO> search(TempStudyProtocolDTO dto, LimitOffset pagingParams) throws PAException,
    TooManyResultsException; 
    /**
     * 
     * @param isoDTO dto
     * @return dto
     * @throws PAException e
     */
    TempStudyProtocolDTO updateTempStudyProtocol(TempStudyProtocolDTO isoDTO) throws PAException; 
    /**
     * 
     * @param ii ii to delete
     * @return 
     * @throws PAException e
     */
    void delete(Ii ii) throws PAException;
    /**
     * 
     * @param tempStudyFundingDTO dto to create
     * @throws PAException on error
     */
    void createGrant(TempStudyFundingDTO tempStudyFundingDTO) throws PAException;
    /**
     * 
     * @param tempStudyProtocolIi temp Study ProtocolIi to delete
     * @throws PAException on error
     */
    void deleteGrantsForTempStudyProtocol(Ii tempStudyProtocolIi) throws PAException;
    /**
     * 
     * @param tempStudyProtocolIi ii
     * @return list
     * @throws PAException on err
     */
    List<TempStudyFundingDTO> getGrantsByTempStudyProtocol(Ii tempStudyProtocolIi)throws PAException;
    
    /**
     * 
     * @param tempStudyIndIdeDTO dto
     * @throws PAException on err
     */
    void createIndIde(TempStudyIndIdeDTO tempStudyIndIdeDTO) throws PAException;
    /**
     * 
     * @param tempStudyProtocolIi ii
     * @throws PAException on error
     */
    void deleteIndIdeForTempStudyProtocol(Ii tempStudyProtocolIi) throws PAException;
    
    /**
     * 
     * @param tempStudyProtocolIi ii
     * @return list
     * @throws PAException on err
     */
    List <TempStudyIndIdeDTO> getIndIdeByTempStudyProtocol(Ii tempStudyProtocolIi) throws PAException;
}
