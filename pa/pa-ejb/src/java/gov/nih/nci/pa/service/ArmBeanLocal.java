/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.iso.convert.ArmConverter;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({ HibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class ArmBeanLocal extends AbstractStudyIsoService<ArmDTO, Arm, ArmConverter> implements ArmServiceLocal {
   
   @EJB
    PlannedActivityServiceLocal plannedActivityService = null;

  /**
   * @param ii index of planned activity
   * @return list of arms associated w/planned activity
   * @throws PAException exception
   */
  @SuppressWarnings("unchecked")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<ArmDTO> getByPlannedActivity(Ii ii) throws PAException {
    if (PAUtil.isIiNull(ii)) {
        throw new PAException("Check the Ii value; null found.  ");
    }
    getLogger().info("Entering getByPlannedActivity.  ");

    Session session = null;
    List<Arm> queryList = new ArrayList<Arm>();
    session = HibernateUtil.getCurrentSession();
    Query query = null;

    // step 1: form the hql
    String hql = "select ar "
        + "from Arm ar "
        + "join ar.interventions pa "
        + "where pa.id = :plannedActivityId "
        + "order by ar.id ";
    getLogger().info("query Arm = " + hql + ".  ");

    // step 2: construct query object
    query = session.createQuery(hql);
    query.setParameter("plannedActivityId", IiConverter.convertToLong(ii));

    // step 3: query the result
    queryList = query.list();
    ArrayList<ArmDTO> resultList = new ArrayList<ArmDTO>();
    for (Arm bo : queryList) {
        resultList.add(convertFromDomainToDto(bo));
    }
    getLogger().info("Leaving getByArm, returning " + resultList.size() + " object(s).  ");
    return resultList;
 }

  private void businessRules(ArmDTO dto)  throws PAException {
    if (dto == null) {
        return;
    }
    if (PAUtil.isEmpty(StConverter.convertToString(dto.getName()))) {
        throw new PAException("The arm/group label (name) must be set.  ");
    }
  }

  /**
   * @param dto Arm transer object
   * @return created Arm
   * @throws PAException exception
   */
   @Override
   public ArmDTO create(ArmDTO dto) throws PAException {
      businessRules(dto);
    return super.create(dto);
   }

 /**
  * @param dto Arm transer object
  * @return updated Arm
  * @throws PAException exception
  */
  @Override
  public ArmDTO update(ArmDTO dto) throws PAException {
    businessRules(dto);
    return super.update(dto);
  }

 /**
  * creates a new record of arm and arm intervetions by changing to new studyprotocol identifier.
  * @param fromStudyProtocolIi from where the study protocol objects to be copied  
  * @param toStudyProtocolIi to where the study protocol objects to be copied
  * @param armMap map of ii
  * @return map 
  * @throws PAException on error
  */
  public Map<Ii , Ii> copy(Ii fromStudyProtocolIi , Ii toStudyProtocolIi , Map<Ii, Ii> armMap) throws PAException {
    List<ArmDTO> armDtos = getByStudyProtocol(fromStudyProtocolIi);
    Map<Ii, Ii> map = new HashMap<Ii, Ii>();
    for (ArmDTO armDto : armDtos) {
        Ii fromIi = armDto.getIdentifier();
        armDto.setInterventions(getAssociatedInterventions(armDto.getIdentifier() , armMap));
        armDto.setIdentifier(null);
        armDto.setStudyProtocolIdentifier(toStudyProtocolIi);
        Ii toIi = create(armDto).getIdentifier();
        map.put(fromIi, toIi);
    }
    return map;
  }

  private DSet<Ii> getAssociatedInterventions(Ii armIi , Map<Ii , Ii> armMap) throws PAException {
    List<PlannedActivityDTO> dtos = null;
    Set<Ii> iiSet = new HashSet<Ii>();
    boolean armIntFound = false;
    dtos = plannedActivityService.getByArm(armIi);
    for (PlannedActivityDTO paDto : dtos) {
        Ii value = PAUtil.containsIi(armMap, paDto.getIdentifier());
        if (value != null) {
            armIntFound = true;
            iiSet.add(value);
        }
    }
    DSet<Ii> interventions = null;
    if (armIntFound) {
        interventions = new DSet<Ii>();
        interventions.setItem(iiSet);
    }
    return interventions;
  }


 }
